package com.hisco.cmm.modules.site.thissite.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hisco.cmm.modules.extend.CacheService;
import com.hisco.cmm.modules.extend.InterfaceService;
import com.hisco.cmm.modules.site.thissite.key.KeyService;
import com.hisco.cmm.modules.site.thissite.key.KeyVo;
import com.hisco.cmm.modules.ErrorMessageInfo;
import com.hisco.cmm.modules.FileUtil;
import com.hisco.cmm.modules.ResponseUtil;
import com.hisco.cmm.modules.StringUtil;

@Service("FileService")
public class FileService extends CacheService implements InterfaceService {

    // 섬네일 업로드 경로
    public final static String THUMBNAIL_PATH = "/thumbnail";

    private final String CACHE_KEY = "fileCache";
    private final String CACHE_KEY__FILE_SRL = CACHE_KEY.concat("_file_srl__");
    private final String CACHE_KEY__ID = CACHE_KEY.concat("_id__");
    private final String CACHE_KEY__SID = CACHE_KEY.concat("_sid__");
    private boolean UPDATE_CACHE = false;

    public static final String FILE_KEY = "attachfile";
    public static final String FILE_INPUT_NAME = "file";

    @Resource(name = "FileDao")
    private FileDao fileDao;

    @Resource(name = "KeyService")
    private KeyService keyService;

    public FileService() {
        this.CACHE_NAME = "FileService";
    }

    @Override
    public boolean Exists() throws Exception {
        return fileDao.Exists();
    }

    @Override
    public boolean Create() throws Exception {

        if (fileDao.Exists())
            return true;
        else {
            return fileDao.Create();
        }
    }

    @Override
    public boolean Drop() throws Exception {
        if (fileDao.Exists()) {
            boolean ret = fileDao.Drop();
            if (ret) {
                KeyVo keyParam = new KeyVo();
                keyParam.setCode(FILE_KEY);

                keyService.Delete(keyParam);
            }
            return ret;
        } else
            return true;
    }

    public int Count(FileDto parameter) throws SQLException {
        return fileDao.Count(parameter);
    }

    public List<FileVo> List(FileDto parameter) throws SQLException {
        return fileDao.List(parameter);
    }

    public List<FileVo> List(long file_id) throws SQLException {
        FileDto parameter = new FileDto();
        parameter.setFile_id(file_id);

        return List(parameter);
    }

    public FileVo Select(FileDto parameter) throws SQLException {
        return fileDao.Select(parameter);
    }

    public FileVo Select(long file_id, int flag) throws SQLException {
        FileDto parameter = new FileDto();
        parameter.setFile_id(file_id);
        parameter.setFlag(flag);

        return Select(parameter);
    }

    public boolean Insert(FileVo parameter) throws SQLException {
        return fileDao.Insert(parameter);
    }

    public boolean Update(FileVo parameter) throws SQLException {
        return fileDao.Update(parameter);
    }

    public boolean UpdateLife(FileVo parameter) throws SQLException {
        return fileDao.UpdateLife(parameter);
    }

    public boolean UpdateDownload(FileVo parameter) throws SQLException {
        return fileDao.UpdateDownload(parameter);
    }

    public boolean UpdateDownload(long file_id, int flag) throws SQLException {
        FileVo parameter = new FileVo();
        parameter.setFile_id(file_id);
        parameter.setFlag(flag);

        return UpdateDownload(parameter);
    }

    public boolean Delete(FileVo parameter) throws SQLException {
        FileDto fileParam = new FileDto();
        fileParam.push(parameter);

        List<FileVo> fileList = List(fileParam);
        if (fileList != null && fileList.size() > 0) {
            boolean ret = fileDao.Delete(parameter);
            if (ret) {
                for (FileVo data : fileList) {
                    File file = FileUtil.GetFile(data.getUpload_path(), data.getUpload_filename());
                    if (file.exists() && file.isFile())
                        file.delete();

                    File[] files = FileUtil.GetFileSearch(THUMBNAIL_PATH, data.getUpload_filename(), "start");
                    if (files != null && files.length > 0) {
                        for (int i = 0, length = files.length; i < length; i++) {
                            if (files[i].exists() && files[i].isFile())
                                files[i].delete();
                        }
                    }

                    ResetCacheFileSrl(data.getFile_srl());
                    ResetCache(data.getFile_id());
                    ResetCache(data.getSid());
                }
            }
            return ret;
        } else {
            return false;
        }
    }

    public boolean Delete(long file_id) throws SQLException {
        FileDto parameter = new FileDto();
        parameter.setFile_id(file_id);

        return Delete(parameter);
    }

    public boolean Delete(long file_id, int flag) throws SQLException {
        FileVo parameter = new FileVo();
        parameter.setFile_id(file_id);
        parameter.setFlag(flag);

        return Delete(parameter);
    }

    public long LastFileId() throws SQLException {
        return keyService.IncreaseKeyValue(FILE_KEY);
    }

    /**
     * 첨부파일 저장 처리
     * 
     * @param request
     *            리퀘스트
     * @param fileId
     *            파일 아이디
     * @param uploadPath
     *            업로드 경로
     * @return FileSaveReturnDto 파일 리턴 객체
     * @throws SQLException
     * @throws IllegalStateException
     * @throws IOException
     */
    public FileSaveReturnDto FileSave(HttpServletRequest request, long fileId, String uploadPath)
            throws SQLException, IllegalStateException, IOException {
        return FileSave(request, fileId, FILE_INPUT_NAME, uploadPath, "N", null, null, null);
    }

    /**
     * 첨부파일 저장 처리
     * 
     * @param request
     *            리퀘스트
     * @param fileId
     *            파일 아이디
     * @param inputName
     *            입력폼 명
     * @param uploadPath
     *            업로드 경로
     * @param delete_YN
     *            삭제여부
     * @param date_start
     *            게시 시작일
     * @param date_end
     *            게시 종료일
     * @param date_limit
     *            만료일
     * @return
     * @throws SQLException
     * @throws IllegalStateException
     * @throws IOException
     */
    public FileSaveReturnDto FileSave(HttpServletRequest request, long fileId, String uploadPath, String delete_YN,
            Date date_start, Date date_end, Date date_limit) throws SQLException, IllegalStateException, IOException {
        return FileSave(request, fileId, FILE_INPUT_NAME, uploadPath, delete_YN, date_start, date_end, date_limit);
    }

    /**
     * 첨부파일 저장 처리
     * 
     * @param request
     *            리퀘스트
     * @param fileId
     *            파일 아이디
     * @param inputName
     *            입력폼 명
     * @param uploadPath
     *            업로드 경로
     * @param delete_YN
     *            삭제여부
     * @param date_start
     *            게시 시작일
     * @param date_end
     *            게시 종료일
     * @param date_limit
     *            만료일
     * @return FileSaveReturnDto 파일 리턴 객체
     * @throws SQLException
     * @throws IllegalStateException
     * @throws IOException
     */
    public FileSaveReturnDto FileSave(HttpServletRequest request, long fileId, String inputName, String uploadPath,
            String delete_YN, Date date_start, Date date_end, Date date_limit)
            throws SQLException, IllegalStateException, IOException {

        long llThisField = fileId;

        FileSaveReturnDto ret = new FileSaveReturnDto();
        ret.setFile_id(llThisField);

        if (!request.getMethod().toUpperCase().equals("POST"))
            return ret;

        List<FileVo> fileDbList = null;

        if (llThisField > 0) {
            fileDbList = List(llThisField);
        }

        // 업로드 파일 목록
        if (fileDbList == null) {
            fileDbList = new ArrayList<FileVo>();
            ret.setFileList(new ArrayList<FileVo>());
        } else {
            ret.setFileList(new ArrayList<FileVo>(fileDbList));
        }

        // 업로드 경로 체크
        FileUtil.MakeDir(uploadPath);

        // 업로드 처리
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(multipartHttpServletRequest.getSession().getServletContext());
        if (!commonsMultipartResolver.isMultipart(multipartHttpServletRequest))
            return ret;

        // String key = null;
        // int file_flag = 0;
        // String file_delete = null;
        // MultipartFile mFile = null;
        // String upload_filename = null;
        // String description = null;

        Iterator<String> filenameIter = multipartHttpServletRequest.getFileNames();
        while (filenameIter.hasNext()) {

            String key = filenameIter.next();
            if (!key.startsWith(inputName)) {
                continue;
            }

            int file_flag = StringUtil.String2Int(key.replace(inputName, ""), 0);
            if (file_flag <= 0) {
                continue;
            }

            String file_delete = multipartHttpServletRequest.getParameter(key.concat(".delete"));
            String description = multipartHttpServletRequest.getParameter(key.concat(".description"));

            MultipartFile mFile = multipartHttpServletRequest.getFile(key);

            // 첨부 파일 정보 없을 경우 다음으로
            if (mFile == null) {

                FileVo fileData = ret.FileSearch(file_flag);
                if (fileData != null) {
                    fileData.setDelete_YN(delete_YN);
                    fileData.setDate_start(date_start);
                    fileData.setDate_end(date_end);
                    fileData.setDate_limit(date_limit);
                }

                continue;

            } else if ("Y".equals(file_delete) && mFile.isEmpty() && llThisField > 0) { // 첨부 파일 정보가 없고 첨부파일 삭제 체크 되어 있을
                                                                                        // 경우 처리

                FileVo fileData = ret.FileSearch(file_flag);
                if (fileData != null) {
                    if (Delete(llThisField, file_flag)) {
                        ret.getFileList().remove(fileData);
                    }
                }
                continue;

            } else if (mFile.isEmpty() && llThisField > 0) { // 첨부 파일 정보가 없고 변경 정보 없을 경우

                FileVo fileData = ret.FileSearch(file_flag);
                /*
                 * if (fileData == null) continue;
                 * if (StringUtil.Equals(description, fileData.getDescription()))
                 * {
                 * ret.getFileList().remove(fileData);
                 * continue;
                 * }
                 */
                if (fileData != null) {
                    fileData.setDescription(description);
                    fileData.setDelete_YN(delete_YN);
                    fileData.setDate_start(date_start);
                    fileData.setDate_end(date_end);
                    fileData.setDate_limit(date_limit);
                }
                continue;
            }

            String upload_filename = FileUtil.GetAutoMakeFilename(uploadPath);

            FileVo fileParam = ret.FileSearch(file_flag);
            if (fileParam == null) {
                fileParam = new FileVo();
                ret.getFileList().add(fileParam);
            }

            fileParam.setFile_id(llThisField);
            fileParam.setFlag(file_flag);
            if (mFile != null && !mFile.isEmpty()) {
                fileParam.NewSID();
                fileParam.setUpload_path(uploadPath);
                fileParam.setUpload_filename(upload_filename);
                fileParam.setFilename(mFile.getOriginalFilename());
                fileParam.setSize(mFile.getSize());
                fileParam.setMime(mFile.getContentType());
                fileParam.setDescription(description);

                fileParam.setDelete_YN(delete_YN);
                fileParam.setDate_start(date_start);
                fileParam.setDate_end(date_end);
                fileParam.setDate_limit(date_limit);
            }

            fileParam.setDescription(description);

            // 파일 저장
            FileUtil.SaveUploadFile(mFile, uploadPath, upload_filename);
        }

        // 파일 아이디 정보 체크
        if (llThisField <= 0 && ret.getFileList() != null && ret.getFileList().size() > 0) {
            llThisField = LastFileId();

            for (FileVo item : ret.getFileList()) {
                item.setFile_id(llThisField);
            }

            ret.setFile_id(llThisField);
        }

        // 파일 저장 처리
        if (ret.getFileList() != null && ret.getFileList().size() > 0) {
            // flag 순서로 재정렬
            Collections.sort(ret.getFileList(), new Comparator<FileVo>() {
                @Override
                public int compare(FileVo o1, FileVo o2) {
                    return o1.getFlag() - o2.getFlag();
                }
            });

            ret.Recount();

            if (llThisField > 0) {
                fileDbList = List(llThisField);
            }

            FileVo fileData = null;
            for (FileVo item : ret.getFileList()) {
                // 이전 파일 정보
                fileData = null;
                if (fileDbList != null && fileDbList.size() > 0) {
                    for (FileVo data : fileDbList) {
                        if (data.getFile_id() == item.getFile_id() && data.getFlag() == item.getFlag()) {
                            fileData = data;
                            break;
                        }
                    }
                }

                if (fileData != null) {
                    // 캐쉬 리셋
                    ResetCache(fileData);

                    // 파일이 변경 된 경우 이전 파일 삭제
                    if (!StringUtil.NotNull(fileData.getSid()).equals(StringUtil.NotNull(item.getSid()))) {

                        File file = FileUtil.GetFile(fileData.getUpload_path(), fileData.getUpload_filename());
                        if (file.isFile() && file.exists())
                            file.delete();

                        File[] files = FileUtil.GetFileSearch(THUMBNAIL_PATH, fileData.getUpload_filename(), "start");
                        if (files != null && files.length > 0) {
                            for (int i = 0, length = files.length; i < length; i++) {
                                if (files[i].exists() && files[i].isFile())
                                    files[i].delete();
                            }
                        }

                        item.NewSID();
                    } else {
                        item.setSid(fileData.getSid());
                        item.setUpload_path(fileData.getUpload_path());
                        item.setUpload_filename(fileData.getUpload_filename());
                        item.setFilename(fileData.getFilename());
                        item.setSize(fileData.getSize());
                        item.setMime(fileData.getMime());
                        item.setDownload_count(fileData.getDownload_count());

                        if (StringUtil.IsEmpty(item.getDescription()))
                            item.setDescription(fileData.getDescription());
                    }

                    Update(item);

                } else if (!StringUtil.IsEmpty(item.getUpload_path()) && !StringUtil.IsEmpty(item.getUpload_filename())) {
                    Insert(item);
                }

                // 캐쉬 리셋
                ResetCache(item);
            }

            // 캐쉬 리셋
            ResetCache(llThisField);
        }

        return ret;
    }

    /**
     * 파일 복사
     * 
     * @param fileId
     *            원본 파일 아이디
     * @param uploadPath
     *            새로운 업로드 경로
     * @return
     * @throws Exception
     */
    public FileSaveReturnDto FileCopy(long fileId, String uploadPath) throws Exception {
        FileSaveReturnDto ret = new FileSaveReturnDto();

        List<FileVo> fileList = List(fileId);
        if (fileList != null && fileList.size() > 0) {
            long new_fileId = LastFileId();
            String upload_path, upload_filename;
            File file, newFile;
            Path source, target;

            for (FileVo data : fileList) {
                upload_path = data.getUpload_path();
                upload_filename = data.getUpload_filename();

                data.setFile_srl(0);
                data.setFile_id(new_fileId);
                data.NewSID();
                data.setUpload_path(uploadPath);
                data.setUpload_filename(FileUtil.GetAutoMakeFilename(uploadPath));

                // 파일 복사
                file = FileUtil.GetFile(upload_path, upload_filename);
                newFile = FileUtil.GetFile(data.getUpload_path(), data.getUpload_filename());
                if (file != null && file.exists() && file.isFile()) {
                    source = Paths.get(file.getAbsolutePath());
                    target = Paths.get(newFile.getAbsolutePath());

                    java.nio.file.Files.copy(source, target, StandardCopyOption.COPY_ATTRIBUTES);
                }

                Insert(data);
            }

            ret.setFile_id(new_fileId);
            ret.setCount(fileList.size());
            ret.setFileList(fileList);
        }

        return ret;
    }

    /**
     * 파일 다운로드 처리
     * 
     * @param request
     *            리퀘스트
     * @param response
     *            리스폰스
     * @param file_srl
     *            파일 고유번호
     * @throws Exception
     */
    public void FileDownload(HttpServletRequest request, HttpServletResponse response, long file_srl) throws Exception {
        FileDownload(request, response, CacheSelectFileSrl(file_srl));
    }

    /**
     * 파일 다운로드 처리
     * 
     * @param request
     *            리퀘스트
     * @param response
     *            리스폰스
     * @param id
     *            파일 아이디
     * @param flag
     *            파일 플래그
     * @throws Exception
     */
    public void FileDownload(HttpServletRequest request, HttpServletResponse response, long id, int flag)
            throws Exception {
        FileDownload(request, response, CacheSelect(id, flag));
    }

    /**
     * Zip 일괄 다운로드 처리
     * 
     * @param request
     *            리퀘스트
     * @param response
     *            리스폰스
     * @param id
     *            파일 아이디
     * @throws Exception
     */
    public void FileZip(HttpServletRequest request, HttpServletResponse response, long id, String title)
            throws Exception {
        FileZip(request, response, CacheSelect(id), title);
    }

    /**
     * 파일 다운로드 처리
     * 
     * @param request
     *            리퀘스트
     * @param response
     *            리스폰스
     * @param sid
     *            파일 고유 sid
     * @throws Exception
     */
    public void FileDownload(HttpServletRequest request, HttpServletResponse response, String sid) throws Exception {
        FileDownload(request, response, CacheSelect(sid));
    }

    /**
     * 파일 다운로드 처리
     * 
     * @param request
     *            리퀘스트
     * @param response
     *            리스폰스
     * @param fileData
     *            파일 데이터
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public void FileDownload(HttpServletRequest request, HttpServletResponse response, FileVo fileData)
            throws Exception {
        if (fileData == null) {
            ResponseUtil.SendMessage(request, response, "첨부파일이 없습니다.", "history.back()");
            return;
        }

        // 조회 여부 확인
        Date now = new Date();
        Boolean is_read = (Boolean) request.getSession().getAttribute("PERMISSION_FILE_".concat(String.valueOf(fileData.getFile_id())));
        if ((is_read == null || is_read != true) && !StringUtil.EqualsNotCase("N", fileData.getDelete_YN())) {
            ResponseUtil.SendMessage(request, response, "첨부파일이 삭제되었습니다.", "history.back()");
            return;
        }
        /*
         * else if (
         * (is_read == null || is_read != true)
         * &&
         * (
         * (fileData.getDate_start() != null && fileData.getDate_start().before(now))
         * || (fileData.getDate_end() != null && fileData.getDate_end().after(now))
         * || (fileData.getDate_limit() != null && fileData.getDate_limit().after(now))
         * )
         * )
         * {
         * ResponseUtil.SendMessage(request, response, "첨부파일을 다운로드 기간이 경과하였습니다.", "history.back()");
         * return;
         * }
         */

        if (StringUtil.IsEmpty(request.getQueryString())) {
            // 파일 다운로드 카운트 증가
            UpdateDownload(fileData);
        }

        String mime = null;
        if (!StringUtil.IsEmpty(fileData.getMime()))
            mime = fileData.getMime();
        if (StringUtil.IsEmpty(mime))
            mime = URLConnection.guessContentTypeFromName(fileData.getFilename());
        if (StringUtil.IsEmpty(mime))
            mime = "application/unknown";

        String file_name = fileData.getFilename().substring(0, fileData.getFilename().lastIndexOf("."));
        String file_ext = fileData.getFilename().substring(fileData.getFilename().lastIndexOf(".") + 1);
        StringBuffer download_filename = new StringBuffer(fileData.getFilename());

        String userAgent = request.getHeader("user-agent");
        if (StringUtil.IsEmpty(userAgent))
            userAgent = "";
        else
            userAgent = userAgent.toLowerCase();

        boolean isMSIE = userAgent.indexOf("msie") > -1 || userAgent.indexOf("trident/") > -1 || userAgent.indexOf("edge/") > -1
                ? true : false;

        if (isMSIE) {
            download_filename.setLength(0);
            download_filename.append(URLEncoder.encode(file_name, "UTF-8").replaceAll("\\+", " ")).append(".").append(file_ext);
        } else {
            download_filename.setLength(0);
            download_filename.append(new String(file_name.getBytes("UTF-8"), "ISO-8859-1")).append(".").append(file_ext);
        }

        File file = FileUtil.GetFile(fileData.getUpload_path(), fileData.getUpload_filename());
        if (!file.exists() || !file.isFile()) {
            ResponseUtil.SendMessage(request, response, "첨부파일을 찾을 수 없습니다.", "history.back()");
            return;
        }

        long file_size = file.length();
        response.setHeader("Content-Disposition", "attachment; filename=\"".concat(download_filename.toString()).concat("\";"));
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setContentType(mime);
        // response.setBufferSize((int)file_size);
        response.setContentLength((int) file_size);
        // response.setHeader("Connection", "close");

        OutputStream os = null;
        FileInputStream fis = null;

        try {
            os = response.getOutputStream();
            fis = new FileInputStream(file);

            FileCopyUtils.copy(fis, os);
        } catch (Exception e) {
            response.setContentType("text/html; charset=UTF-8");
            ResponseUtil.SendMessage(request, response, "다운로드 처리중 오류가 발생하였습니다.", "history.back()");
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
            if (fis != null) {
                fis.close();
            }

            os = null;
            fis = null;
        }
    }

    /**
     * 파일 ZIP 일괄 다운로드 처리
     * 
     * @param request
     *            리퀘스트
     * @param response
     *            리스폰스
     * @param fileData
     *            파일 데이터
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public void FileZip(HttpServletRequest request, HttpServletResponse response, List<FileVo> fileList, String title)
            throws Exception {

        if (fileList == null) {
            ResponseUtil.SendMessage(request, response, "첨부파일이 없습니다.", "history.back()");
            return;
        }

        String strThisTitle = title;

        if (strThisTitle == null || "".equals(strThisTitle)) {
            strThisTitle = "download";
        }

        OutputStream os = null;
        FileInputStream fis = null;

        try {

            String downloadFileName = strThisTitle;

            if (request.getHeader("User-Agent").indexOf("MSIE 5.5") > -1 || request.getHeader("User-Agent").indexOf("Trident") > -1) {
                downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8").replaceAll("\\+", "%20");
                response.setHeader("Content-Disposition", "filename=" + downloadFileName + ".zip" + ";");
            } else {
                downloadFileName = new String(downloadFileName.getBytes("UTF-8"), "ISO-8859-1");
                response.setHeader("Content-Disposition", "attachment; filename=" + downloadFileName + ".zip" + ";");
            }

            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Type", "application/octet-stream");

            os = response.getOutputStream();
            ZipOutputStream zout = new ZipOutputStream(os);

            for (FileVo fileData : fileList) {

                // 조회 여부 확인
                Date now = new Date();
                Boolean is_read = (Boolean) request.getSession().getAttribute("PERMISSION_FILE_".concat(String.valueOf(fileData.getFile_id())));
                if ((is_read == null || is_read != true) && !StringUtil.EqualsNotCase("N", fileData.getDelete_YN())) {
                    ResponseUtil.SendMessage(request, response, "첨부파일이 삭제되었습니다.", "history.back()");
                    return;
                }

                if (StringUtil.IsEmpty(request.getQueryString())) {
                    // 파일 다운로드 카운트 증가
                    UpdateDownload(fileData);
                }

                String mime = null;
                if (!StringUtil.IsEmpty(fileData.getMime()))
                    mime = fileData.getMime();
                if (StringUtil.IsEmpty(mime))
                    mime = URLConnection.guessContentTypeFromName(fileData.getFilename());
                if (StringUtil.IsEmpty(mime))
                    mime = "application/unknown";

                String file_name = fileData.getFilename().substring(0, fileData.getFilename().lastIndexOf("."));
                String file_ext = fileData.getFilename().substring(fileData.getFilename().lastIndexOf(".") + 1);
                StringBuffer download_filename = new StringBuffer(fileData.getFilename());

                File file = FileUtil.GetFile(fileData.getUpload_path(), fileData.getUpload_filename());
                if (!file.exists() || !file.isFile()) {
                    ResponseUtil.SendMessage(request, response, "첨부파일을 찾을 수 없습니다.", "history.back()");
                    return;
                }

                // 본래 파일명 유지, 경로제외 파일압축을 위해 new File로
                ZipEntry zipEntry = new ZipEntry(fileData.getFilename());
                zout.putNextEntry(zipEntry);

                FileInputStream fin = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int length;

                // input file을 1024바이트로 읽음, zip stream에 읽은 바이트를 씀
                /*
                 * while((length = fin.read(buffer)) > 0) {
                 * zout.write(buffer, 0, length);
                 * }
                 */

                while (true) {

                    length = fin.read(buffer);
                    if (length <= 0) {
                        break;
                    }
                    zout.write(buffer, 0, length);

                }

                zout.closeEntry();
                fin.close();
            }
            zout.close();
            // ZIP파일 압축 END
        } catch (IOException e) {
            // Exception
            ResponseUtil.SendMessage(request, response, "다운로드 처리중 오류가 발생하였습니다.", "history.back()");
            return;
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
            if (fis != null) {
                fis.close();
            }

            os = null;
            fis = null;
        }
        return;
    }

    /**
     * 썸네일 처리
     * 
     * @param request
     * @param response
     * @param fileData
     * @param width
     * @param height
     * @throws Exception
     */
    public ErrorMessageInfo FileThumbnail(HttpServletRequest request, HttpServletResponse response, FileVo fileData,
            int width, int height, boolean forceMake) throws Exception {
        ErrorMessageInfo error = new ErrorMessageInfo();

        if (fileData == null) {
            error.setError(true);
            error.setMessage("파일 정보가 없습니다.");
            error.setObject(HttpServletResponse.SC_NOT_FOUND);
            return error;
        } else if (StringUtil.IsEmpty(fileData.getMime()) || fileData.getMime().toLowerCase().indexOf("image/") < 0) {
            error.setError(true);
            error.setMessage("이미지 파일이 아닙니다.");
            error.setObject(HttpServletResponse.SC_NOT_FOUND);
            return error;
        }

        // 조회 여부 확인
        // Date now = new Date();
        Boolean is_read = (Boolean) request.getSession().getAttribute("PERMISSION_FILE_".concat(String.valueOf(fileData.getFile_id())));
        if ((is_read == null || is_read != true) && !StringUtil.EqualsNotCase("N", fileData.getDelete_YN())) {
            error.setError(true);
            error.setMessage("첨부파일이 삭제되었습니다.");
            error.setObject(HttpServletResponse.SC_NOT_FOUND);
            return error;
        }
        /*
         * else if (
         * (is_read == null || is_read != true)
         * &&
         * (
         * (fileData.getDate_start() != null && fileData.getDate_start().before(now))
         * || (fileData.getDate_end() != null && fileData.getDate_end().after(now))
         * || (fileData.getDate_limit() != null && fileData.getDate_limit().after(now))
         * )
         * )
         * {
         * error.setError(true);
         * error.setMessage("첨부파일을 열람 기간이 지났습니다.");
         * error.setObject(HttpServletResponse.SC_NOT_FOUND);
         * return error;
         * }
         */

        String thumbnain_file_name = fileData.getUpload_filename() + "__" + width + "__" + height;

        File thubmail = FileUtil.MakeThumbnail(FileService.THUMBNAIL_PATH, thumbnain_file_name, fileData.getUpload_path(), fileData.getUpload_filename(), width, height, forceMake);
        if (thubmail == null) {
            error.setError(true);
            error.setMessage("섬네일 파일을 만들 수 없습니다.");
            error.setObject(HttpServletResponse.SC_NOT_FOUND);
            return error;
        }

        // 만료 날짜 정보
        String ETag = String.valueOf(fileData.getRegdate().getTime());
        Date regDate = fileData.getRegdate();

        // 만료 날짜 확인
        long Expires = request.getDateHeader("If-Modified-Since");
        if (Expires > 0) {
            Date expires = new Date(Expires);

            if (expires.equals(regDate) || expires.after(regDate)) {
                error.setError(false);
                error.setMessage("캐쉬 이미지 정보를 사용");
                error.setObject(HttpServletResponse.SC_NOT_MODIFIED);
                return error;
            }
        }

        // 만료 정보(ETag) 확인
        String eTag = request.getHeader("ETag");
        if (StringUtil.Equals(eTag, ETag)) {
            error.setError(false);
            error.setMessage("캐쉬 이미지 정보를 사용");
            error.setObject(HttpServletResponse.SC_NOT_MODIFIED);
            return error;
        }

        // 파일 만료 처리
        if (regDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(regDate);
            cal.add(Calendar.YEAR, 1);

            long max_age = (cal.getTimeInMillis() - regDate.getTime()) / 1000;

            response.setHeader("Cache-Control", "private, max-age=".concat(String.valueOf(max_age)));
            response.setIntHeader("Age", (int) max_age);
            response.setDateHeader("Date", new Date().getTime());
            response.setDateHeader("Last-Modified", regDate.getTime());
            response.setDateHeader("Expires", cal.getTime().getTime());
            response.setHeader("ETag", ETag);
        }

        // 파일 다운로드 처리
        String mime = "image/".concat(FileUtil.THUMBNAIL_FILE_TYPE);
        long file_size = thubmail.length();
        response.setContentType(mime);
        // response.setBufferSize((int)file_size);
        response.setContentLength((int) file_size);
        response.setHeader("Accept-Ranges", "bytes");

        OutputStream os = null;
        FileInputStream fis = null;

        try {
            os = response.getOutputStream();
            fis = new FileInputStream(thubmail);

            FileCopyUtils.copy(fis, os);
        } catch (Exception e) {
            error.setError(true);
            error.setMessage("섬네일 다운로드 처리 오류 발생");
            error.setObject(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
            if (fis != null) {
                fis.close();
            }

            os = null;
            fis = null;
        }

        return error;
    }

    /**
     * 캐시 데이터 불러오기
     * 
     * @param file_srl
     *            파일 고유번호
     * @return 파일 데이터
     * @throws SQLException
     */
    public FileVo CacheSelectFileSrl(long file_srl) throws SQLException {
        if (UPDATE_CACHE) {
            this.sleep();
            return CacheSelectFileSrl(file_srl);
        }

        String key = CACHE_KEY__FILE_SRL.concat(String.valueOf(file_srl));

        if (this.hasCacheData(key)) {
            return (FileVo) this.getCacheData(key);
        } else {
            FileDto fileParam = new FileDto();
            fileParam.setFile_srl(file_srl);

            FileVo fileData = Select(fileParam);
            if (fileData != null) {
                this.setCacheData(key, fileData);
                return fileData;
            } else {
                this.setCacheData(key, null);
                return null;
            }
        }
    }

    /**
     * 캐시 목록 불러오기
     * 
     * @param id
     *            파일 아이디
     * @return 파일 목록
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<FileVo> CacheSelect(long file_id) throws SQLException {
        if (UPDATE_CACHE) {
            this.sleep();
            return CacheSelect(file_id);
        }

        String key = CACHE_KEY__ID.concat(String.valueOf(file_id));
        if (this.hasCacheData(key)) {
            return (List<FileVo>) this.getCacheData(key);
        } else {
            FileDto fileParam = new FileDto();
            fileParam.setFile_id(file_id);

            List<FileVo> fileList = List(fileParam);
            if (fileList != null && fileList.size() > 0) {
                this.setCacheData(key, fileList);
                return fileList;
            } else {
                this.setCacheData(key, null);
                return null;
            }
        }
    }

    /**
     * 캐시 데이터 불러오기
     * 
     * @param id
     *            파일 아이디
     * @param flag
     *            파일 플래그
     * @return 파일 데이터
     * @throws SQLException
     */
    public FileVo CacheSelect(long id, int flag) throws SQLException {
        if (UPDATE_CACHE) {
            this.sleep();
            return CacheSelect(id, flag);
        }

        List<FileVo> fileList = CacheSelect(id);
        if (fileList == null || fileList.size() <= 0)
            return null;

        for (FileVo data : fileList) {
            if (data.getFlag() == flag) {
                return data;
            }
        }

        return null;
    }

    /**
     * 캐시 데이터 불러오기
     * 
     * @param sid
     *            파일 SID
     * @return 파일 데이터
     * @throws SQLException
     */
    public FileVo CacheSelect(String sid) throws SQLException {
        if (UPDATE_CACHE) {
            this.sleep();
            return CacheSelect(sid);
        }

        String key = CACHE_KEY__SID.concat(sid);
        if (this.hasCacheData(key)) {
            return (FileVo) this.getCacheData(key);
        } else {
            FileDto fileParam = new FileDto();
            fileParam.setSid(sid);

            FileVo fileData = Select(fileParam);
            if (fileData != null) {
                this.setCacheData(key, fileData);
                return fileData;
            } else {
                this.setCacheData(key, null);
                return null;
            }
        }
    }

    /**
     * 파일 정보로 캐쉬 리셋
     * 
     * @param fileData
     */
    public void ResetCache(FileVo fileData) {
        if (fileData == null)
            return;

        // log.debug("reset cache srl : "+ fileData.getFile_srl());
        // log.debug("reset cache sid : "+ fileData.getSid());
        ResetCacheFileSrl(fileData.getFile_srl());
        ResetCache(fileData.getSid());
    }

    /**
     * 파일 고유번호 캐시 삭제
     * 
     * @param file_srl
     *            파일 고유번호
     */
    public void ResetCacheFileSrl(long file_srl) {
        try {
            UPDATE_CACHE = true;
            this.remove(CACHE_KEY__FILE_SRL.concat(String.valueOf(file_srl)));
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            UPDATE_CACHE = false;
        }
    }

    /**
     * 파일 아이디 캐시 삭제
     * 
     * @param id
     *            파일 아이디
     */
    public void ResetCache(long id) {
        try {
            UPDATE_CACHE = true;
            this.remove(CACHE_KEY__ID.concat(String.valueOf(id)));
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            UPDATE_CACHE = false;
        }
    }

    /**
     * 파일 SID 캐시 삭제
     * 
     * @param sid
     *            파일 SID
     */
    public void ResetCache(String sid) {
        try {
            UPDATE_CACHE = true;
            this.remove(CACHE_KEY__SID.concat(sid));
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            UPDATE_CACHE = false;
        }
    }

    /**
     * 파일 캐시 전체 삭제
     */
    public void ResetCache() {
        try {
            UPDATE_CACHE = true;

            this.removeStartsWith(CACHE_KEY__FILE_SRL);
            this.removeStartsWith(CACHE_KEY__ID);
            this.removeStartsWith(CACHE_KEY__SID);
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            UPDATE_CACHE = false;
        }
    }
}