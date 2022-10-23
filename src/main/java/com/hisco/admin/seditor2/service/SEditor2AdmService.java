package com.hisco.admin.seditor2.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hisco.cmm.modules.FileUtil;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.modules.extend.CacheService;
import com.hisco.cmm.modules.site.thissite.file.FileDto;
import com.hisco.cmm.modules.site.thissite.file.FileSaveReturnDto;

/*
 * import com.knsoft.extend.CacheService;
 * import com.knsoft.extend.InterfaceService;
 * import com.knsoft.kntool.MODULES.key.KeyService;
 * import com.knsoft.kntool.MODULES.key.KeyVo;
 * import com.knsoft.support.ErrorMessageInfo;
 * import com.knsoft.support.FileUtil;
 * import com.knsoft.support.ResponseUtil;
 * import com.knsoft.support.StringUtil;
 */

import com.hisco.cmm.modules.site.thissite.file.FileVo;
import com.hisco.cmm.modules.site.thissite.key.KeyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("SEditor2AdmService")
public class SEditor2AdmService extends CacheService {

    public final static String THUMBNAIL_PATH = "/thumbnail";

    private final String CACHE_KEY = "fileCache";
    private final String CACHE_KEY__FILE_SRL = CACHE_KEY.concat("_file_srl__");
    private final String CACHE_KEY__ID = CACHE_KEY.concat("_id__");
    private final String CACHE_KEY__SID = CACHE_KEY.concat("_sid__");

    public static final String FILE_INPUT_NAME = "file";
    public static final String FILE_KEY = "attachfile";

    private boolean UPDATE_CACHE = false;

    @Resource(name = "KeyService")
    private KeyService keyService;

    //// @Resource(name="FileDao")
    //// private FileDao fileDao;

    public FileSaveReturnDto FileSave(MultipartHttpServletRequest multipartHttpServletRequest, long fileId,
            String uploadPath) throws SQLException, IllegalStateException, IOException {
        return FileSave(multipartHttpServletRequest, fileId, FILE_INPUT_NAME, uploadPath, "N", null, null, null);
    }

    public FileSaveReturnDto FileSave(MultipartHttpServletRequest multipartHttpServletRequest, long fileId,
            String uploadPath, String delete_YN, Date date_start, Date date_end, Date date_limit)
            throws SQLException, IllegalStateException, IOException {
        return FileSave(multipartHttpServletRequest, fileId, FILE_INPUT_NAME, uploadPath, delete_YN, date_start, date_end, date_limit);
    }

    public FileSaveReturnDto FileSave(MultipartHttpServletRequest multipartHttpServletRequest, long fileId,
            String inputName, String uploadPath, String delete_YN, Date date_start, Date date_end, Date date_limit)
            throws SQLException, IllegalStateException, IOException {

        FileSaveReturnDto ret = new FileSaveReturnDto();

        ret.setFile_id(fileId);

        if (!multipartHttpServletRequest.getMethod().toUpperCase().equals("POST")) {
            return ret;
        }

        List<FileVo> fileDbList = null;
        if (fileId > 0) {
            fileDbList = List(fileId);
        }

        if (fileDbList == null) {
            fileDbList = new ArrayList<FileVo>();
            ret.setFileList(new ArrayList<FileVo>());
        } else {
            ret.setFileList(new ArrayList<FileVo>(fileDbList));
        }

        FileUtil.MakeDir(uploadPath);

        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(multipartHttpServletRequest.getSession().getServletContext());
        if (!commonsMultipartResolver.isMultipart(multipartHttpServletRequest)) {
            return ret;
        }

        Iterator<String> filenameIter = multipartHttpServletRequest.getFileNames();
        while (filenameIter.hasNext()) {

            String key = filenameIter.next();
            if (!key.startsWith(inputName))
                continue;

            int file_flag = StringUtil.String2Int(key.replace(inputName, ""), 0);
            if (file_flag <= 0)
                continue;

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

            } else if ("Y".equals(file_delete) && mFile.isEmpty() && fileId > 0) {

                FileVo fileData = ret.FileSearch(file_flag);
                if (fileData != null) {
                    if (Delete(fileId, file_flag)) {
                        ret.getFileList().remove(fileData);
                    }
                }
                continue;

            } else if (mFile.isEmpty() && fileId > 0) {

                FileVo fileData = ret.FileSearch(file_flag);

                // if(fileData == null) continue;
                // if(StringUtil.Equals(description, fileData.getDescription())) {
                // ret.getFileList().remove(fileData);
                // continue;
                // }

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

            fileParam.setFile_id(fileId);
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
        if (fileId <= 0 && ret.getFileList() != null && ret.getFileList().size() > 0) {

            fileId = LastFileId();

            for (FileVo item : ret.getFileList()) {
                item.setFile_id(fileId);
            }

            ret.setFile_id(fileId);
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

            if (fileId > 0)
                fileDbList = List(fileId);

            FileVo fileData = null;
            for (FileVo item : ret.getFileList()) {

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
            ResetCache(fileId);
        }

        return ret;

    }

    public long LastFileId() throws SQLException {
        return keyService.IncreaseKeyValue(FILE_KEY);
    }

    public void ResetCache(FileVo fileData) {

        if (fileData == null)
            return;

        log.debug("reset cache srl : " + fileData.getFile_srl());
        log.debug("reset cache sid : " + fileData.getSid());
        ResetCacheFileSrl(fileData.getFile_srl());
        ResetCache(fileData.getSid());
    }

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

    public boolean Insert(FileVo parameter) throws SQLException {
        //// return fileDao.Insert(parameter);
        return true;
    }

    public boolean Update(FileVo parameter) throws SQLException {
        //// return fileDao.Update(parameter);
        return true;
    }

    public boolean Delete(FileVo parameter) throws SQLException {

        FileDto fileParam = new FileDto();
        fileParam.push(parameter);

        List<FileVo> fileList = List(fileParam);
        if (fileList != null && fileList.size() > 0) {

            //// boolean ret = fileDao.Delete(parameter);
            boolean ret = true;
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

        return true;
        //// return Delete(parameter);
    }

    public boolean Delete(long file_id, int flag) throws SQLException {
        FileVo parameter = new FileVo();
        parameter.setFile_id(file_id);
        parameter.setFlag(flag);

        return true;
        //// return Delete(parameter);
    }

    public List<FileVo> List(FileDto parameter) throws SQLException {
        return null;
        //// return fileDao.List(parameter);
    }

    public List<FileVo> List(long file_id) throws SQLException {

        FileDto parameter = new FileDto();
        parameter.setFile_id(file_id);

        return null;
        //// return List(parameter);
    }

}
