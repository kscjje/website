package com.hisco.cmm.modules;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class FileUtil {
    /**
     * 파일 업로드 경로
     */
    public static final String UPLOAD_PATH = File.separator.concat("WEB-INF").concat(File.separator).concat("upload");

    // 섬네일 파일 타입
    public static final String THUMBNAIL_FILE_TYPE = "jpeg";

    /**
     * 경로 구분자 처리
     * 
     * @param path
     * @return
     */
    public static String checkPathSeparator(String path) {
        if (StringUtil.IsEmpty(path))
            return null;

        if ("\\".equals(File.separator))
            return path.replaceAll("/", "\\\\").replaceAll("\\\\", "\\\\");
        else
            return path.replaceAll("/", File.separator).replaceAll("\\\\", File.separator);
    }

    /**
     * 파일 경로 분리 처리
     * 
     * @param path
     * @return
     */
    public static String[] splitPathSeparator(String path) {
        if (StringUtil.IsEmpty(path))
            return null;

        String p = checkPathSeparator(path);
        if (StringUtil.IsEmpty(p))
            return null;

        if ("\\".equals(File.separator)) {
            return p.split("\\\\");
        } else {
            return p.split(File.separatorChar == '\\' ? "\\\\" : File.separator);
        }
    }

    /**
     * 서버 실제 경로 찾기
     * 
     * @return
     */
    public static String GetRealRootPath() {
        // String[] class_paths = splitPathSeparator(FileUtil.class.getResource(File.separator).getPath());
        URL url = FileUtil.class.getResource("/");
        if (url == null)
            return "/";

        String[] class_paths = splitPathSeparator(url.getPath());
        if (class_paths == null)
            return "/";

        StringBuffer path = new StringBuffer();
        for (int i = 0; i < class_paths.length - 2; i++) {
            if (!StringUtil.IsEmpty(class_paths[i])) {
                path.append(File.separator);
                path.append(class_paths[i]);
            }
        }

        return path.toString();
    }

    /**
     * 서버 실제 경로 찾기
     * 
     * @param path
     *            경로
     * @return
     */
    public static String GetRealPath(String path) {
        StringBuffer sb = new StringBuffer();
        sb.append(GetRealRootPath());
        sb.append(checkPathSeparator(path));

        return sb.toString();
    }

    /**
     * 업로드 루트 실제 경로 반환
     * 
     * @return
     */
    public static String GetUploadRootRealPath() {
        return GetRealPath(UPLOAD_PATH);
    }

    /**
     * 디렉토리 생성 : 루트 경로는 자동 삽입
     * 
     * @param request
     * @param path
     */
    public static void MakeDir(String path) {
        String real_path = GetUploadRootRealPath().concat(File.separator).concat(path);

        File dir = new File(real_path);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        dir = null;
        real_path = null;
    }

    /**
     * 파일 정보
     * 
     * @param request
     * @param path
     * @param name
     * @return
     */
    public static File GetFile(String path, String name) {
        StringBuffer sb = new StringBuffer();
        sb.append(path);
        sb.append(File.separator);
        sb.append(name);

        return GetFile(sb.toString());
    }

    /**
     * 파일 정보
     * 
     * @param request
     * @param path
     * @return
     */
    public static File GetFile(String path) {
        StringBuffer sb = new StringBuffer();
        sb.append(GetUploadRootRealPath());
        sb.append(File.separator);
        sb.append(path);

        return new File(sb.toString());
    }

    /**
     * 파일 존재 여부
     * 
     * @param request
     * @param path
     * @param name
     * @return
     */
    public static boolean GetFileExists(String path, String name) {
        StringBuffer sb = new StringBuffer();
        sb.append(path);
        sb.append(File.separator);
        sb.append(name);

        return GetFileExists(sb.toString());
    }

    /**
     * 파일 존재 여부
     * 
     * @param request
     * @param path
     * @return
     */
    public static boolean GetFileExists(String path) {
        File file = GetFile(path);
        if (file == null)
            return false;
        return file.isFile() && file.exists();
    }

    /**
     * 업로드 파일 정보
     * 
     * @param request
     * @param name
     * @return
     */
    public static MultipartFile GetUploadFile(HttpServletRequest request, String name) {
        if (request == null)
            return null;
        else if (StringUtil.IsEmpty(name))
            return null;
        else if (request.getMethod().toUpperCase().equals("POST")) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(multipartHttpServletRequest.getSession().getServletContext());

            if (commonsMultipartResolver.isMultipart(multipartHttpServletRequest)) {
                MultipartFile multipartFile = multipartHttpServletRequest.getFile(name);
                if (!multipartFile.isEmpty()) {
                    return multipartFile;
                }
            }
        }

        return null;
    }

    /**
     * 업로드 파일 정보
     * 
     * @param request
     * @param name
     * @return
     */
    public static List<MultipartFile> GetUploadFiles(HttpServletRequest request, String name) {
        if (request == null)
            return null;
        else if (StringUtil.IsEmpty(name))
            return null;
        else if (request.getMethod().toUpperCase().equals("POST")) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(multipartHttpServletRequest.getSession().getServletContext());

            if (commonsMultipartResolver.isMultipart(multipartHttpServletRequest)) {
                return multipartHttpServletRequest.getFiles(name);
            }
        }

        return null;
    }

    /**
     * 파일 저장 처리
     * 
     * @param request
     * @param mfile
     * @param path
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public static File SaveUploadFile(MultipartFile mfile, String path) throws IllegalStateException, IOException {
        return SaveUploadFile(mfile, path, null);
    }

    /**
     * 파일 저장 처리(파일 확장자 없음)
     * 
     * @param request
     * @param mfile
     * @param path
     * @param filename
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public static File SaveUploadFile(MultipartFile mfile, String path, String filename)
            throws IllegalStateException, IOException {
        if (mfile == null || mfile.isEmpty())
            return null;

        String sava_filename;
        if (StringUtil.IsEmpty(filename))
            sava_filename = GetAutoMakeFilename(path);
        else
            sava_filename = filename;

        File targetFile = GetFile(path, sava_filename);

        mfile.transferTo(targetFile);

        return targetFile;
    }

    /**
     * 파일 저장 처리(파일 확장자 있음)
     * 
     * @param request
     * @param mfile
     * @param path
     * @param filename
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public static File SaveUploadFileExt(MultipartFile mfile, String path, String filename)
            throws IllegalStateException, IOException {
        if (mfile == null)
            return null;

        String name = mfile.getOriginalFilename();
        String ext = null;
        if (name.lastIndexOf(".") > 0)
            ext = name.substring(name.lastIndexOf(".") + 1).trim().toLowerCase();

        StringBuffer save_filename = new StringBuffer();
        if (StringUtil.IsEmpty(filename)) {
            save_filename.append(GetAutoMakeFilename(path, ext));
        } else {
            save_filename.append(filename);

            String filename_ext = null;
            if (filename.lastIndexOf(".") > 0)
                filename_ext = filename.substring(filename.lastIndexOf(".") + 1).trim().toLowerCase();

            if (filename_ext != null && !filename_ext.equals(ext)) {
                save_filename.setLength(0);
                save_filename.append(filename.substring(0, filename.lastIndexOf(".")).trim());
                save_filename.append(".");
                save_filename.append(ext);
            }

            String new_filename = GetNotExistsFilename(path, save_filename.toString());

            save_filename.setLength(0);
            save_filename.append(new_filename);
        }

        File targetFile = GetFile(path, save_filename.toString());

        mfile.transferTo(targetFile);

        return targetFile;
    }

    /**
     * 파일명 자동 생성 : 중복 확인 처리
     * 
     * @param request
     * @param path
     * @return
     */
    public static String GetAutoMakeFilename(String path) {
        return GetAutoMakeFilename(path, null);
    }

    /**
     * 파일명 자동 생성 : 중복 확인 처리
     * 
     * @param request
     * @param path
     * @param ext
     * @return
     */
    public static String GetAutoMakeFilename(String path, String ext) {

        String strThisExt = ext;

        StringBuffer extension = new StringBuffer();

        if (!StringUtil.IsEmpty(strThisExt) && strThisExt.indexOf(".") > -1) {
            strThisExt = strThisExt.substring(strThisExt.lastIndexOf(".") + 1);
        }

        if (StringUtil.IsEmpty(strThisExt) || ".".equals(strThisExt.trim())) {
            extension.setLength(0);
        } else {
            extension.append(".");
            extension.append(strThisExt.replaceAll("\\.", "").trim().toLowerCase());
        }

        // 파일 중복 막기 위해 1ms 쉬었다 시작
        Sleep.Call(1);

        StringBuffer filename = new StringBuffer();
        filename.append(DateUtil.printDatetime(new Date(), "yyyyMMddHHmmssS"));
        filename.append("_");
        filename.append(Math.abs((new java.security.SecureRandom()).nextInt(100000)));

        StringBuffer dumyFilename = new StringBuffer();
        dumyFilename.append(filename);
        dumyFilename.append(extension);

        int idx = 0;
        boolean fileExist = true;

        do {

            fileExist = GetFileExists(path, dumyFilename.toString());

            if (fileExist) {

                dumyFilename.setLength(0);
                dumyFilename.append(filename).append("(").append(idx).append(")").append(extension);

                idx++;

            } else {
                break;
            }

        } while (!fileExist);

        return dumyFilename.toString();
    }

    /**
     * 파일명 중복 체크
     * 
     * @param request
     * @param path
     * @param filename
     * @return
     */
    public static String GetNotExistsFilename(String path, String filename) {

        String new_filename;
        if (StringUtil.IsEmpty(filename) || filename.lastIndexOf(".") < 0) {
            return GetAutoMakeFilename(path);
        } else {
            new_filename = filename.trim();
        }

        String name = new_filename.substring(0, new_filename.lastIndexOf("."));
        String ext = new_filename.substring(new_filename.lastIndexOf(".") + 1);

        StringBuffer dumyFilename = new StringBuffer();
        dumyFilename.append(name).append(ext);

        int idx = 0;
        boolean fileExist = true;
        do {
            fileExist = GetFileExists(path, dumyFilename.toString());
            if (fileExist) {
                dumyFilename.setLength(0);
                dumyFilename.append(name).append("(").append(idx).append(")").append(ext);
                idx++;
            } else {
                break;
            }

        } while (!fileExist);

        return dumyFilename.toString();
    }

    /**
     * 해당 위치에서 파일 검색
     * 
     * @param request
     *            HttpServletRequest
     * @param path
     *            파일 경로
     * @param filename
     *            검색할 파일명
     * @param searchType
     *            검색 종류(start : 앞에서부터 동일, end : 뒤에서부터 동일, any : 동일한것이 포함 되어 있을경우)
     * @return
     */
    public static File[] GetFileSearch(String path, String filename, String searchType) {
        String search_type = StringUtil.IsEmpty(searchType) ? "any" : searchType.toLowerCase();

        File dir = GetFile(path);
        if (dir != null && dir.exists() && dir.isDirectory()) {
            FilenameFilter fileFilter = null;

            if ("start".equals(search_type))
                fileFilter = new FileStartWithFilter(filename);
            else if ("end".equals(search_type))
                fileFilter = new FileEndWithFilter(filename);
            else if ("any".equals(search_type))
                fileFilter = new FileIndexOfFilter(filename);

            if (fileFilter != null) {
                return dir.listFiles(fileFilter);
            }
        }

        return null;
    }

    /**
     * 섬네일 생성
     * 
     * @param thumbnailPath
     * @param thumnailFileName
     * @param target_file_path
     * @param target_file_name
     * @param width
     * @param height
     * @return
     */
    public static File MakeThumbnail(String thumbnailPath, String thumnailFileName, String target_file_path,
            String target_file_name, int width, int height, boolean forceMake) {

        int intThisWidth = width;
        int intThisHeight = height;

        // 기본 크기
        if (intThisWidth <= 0) {
            intThisWidth = 400;
        }

        if (intThisHeight <= 0) {
            intThisHeight = 300;
        }

        MakeDir(thumbnailPath);

        File thumbnail = GetFile(thumbnailPath, thumnailFileName);
        if (!forceMake && thumbnail != null && thumbnail.exists() && thumbnail.isFile()) {
            return thumbnail;
        }

        File target = GetFile(target_file_path, target_file_name);
        if (target == null || !target.exists() || !target.isFile()) {
            return null;
        }

        BufferedImage bufferImage = null;
        try {
            bufferImage = ImageIO.read(target);
        } catch (IOException e) {
            return null;
        }

        int origin_width = bufferImage.getWidth();
        int origin_height = bufferImage.getHeight();
        float origin_rate = (float) origin_width / (float) origin_height;

        // 섬네일 이미지 크기 조정
        if (intThisHeight > intThisWidth / origin_rate) {
            intThisHeight = (int) (intThisWidth / origin_rate);
        } else if (intThisHeight < intThisWidth / origin_rate) {
            intThisWidth = (int) (intThisHeight * origin_rate);
        }

        BufferedImage thumbImage = Scalr.resize(bufferImage, Scalr.Method.BALANCED, intThisWidth, intThisHeight);

        try {
            ImageIO.write(thumbImage, THUMBNAIL_FILE_TYPE, thumbnail);
        } catch (IOException e) {
            return null;
        }

        return thumbnail;
    }

    public static File MakeThumbnail_old(String thumbnailPath, String thumnailFileName, String target_file_path,
            String target_file_name, int width, int height, boolean forceMake) {

        int intThisWidth = width;
        int intThisHeight = height;

        // 기본 크기
        if (intThisWidth <= 0) {
            intThisWidth = 400;
        }

        if (intThisHeight <= 0) {
            intThisHeight = 300;
        }

        MakeDir(thumbnailPath);

        File thumbnail = GetFile(thumbnailPath, thumnailFileName);
        if (!forceMake && thumbnail != null && thumbnail.exists() && thumbnail.isFile()) {
            return thumbnail;
        }

        File target = GetFile(target_file_path, target_file_name);
        if (target == null || !target.exists() || !target.isFile()) {
            return null;
        }

        FileInputStream fis = null;
        BufferedInputStream bis = null;

        BufferedImage bufferImage = null;
        BufferedImage tmpBufferImage = null;
        Image tmpImage = null;
        Graphics2D g2 = null;
        // FileOutputStream fos = null;

        try {
            JpegReader jpegReader = new JpegReader();
            bufferImage = jpegReader.readImage(target);
        } catch (Exception e1) {
            try {
                fis = new FileInputStream(target);
                bis = new BufferedInputStream(fis);

                bufferImage = ImageIO.read(bis);
            } catch (Exception e2) {
            }
        }

        if (bufferImage == null) {
            try {
                if (bis != null)
                    bis.close();
            } catch (IOException e1) {
            }
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e2) {
            }

            return null;
        }

        try {

            int origin_width = bufferImage.getWidth();
            int origin_height = bufferImage.getHeight();
            float origin_rate = (float) origin_width / (float) origin_height;

            // 섬네일 이미지 크기 조정
            if (intThisHeight > intThisWidth / origin_rate) {
                intThisHeight = (int) (intThisWidth / origin_rate);
            } else if (intThisHeight < intThisWidth / origin_rate) {
                intThisWidth = (int) (intThisHeight * origin_rate);
            }

            // xx : old : start
            tmpBufferImage = new BufferedImage(intThisWidth, intThisHeight, BufferedImage.TYPE_INT_RGB);
            tmpImage = bufferImage.getScaledInstance(intThisWidth, intThisHeight, Image.SCALE_AREA_AVERAGING);

            g2 = tmpBufferImage.createGraphics();
            g2.drawImage(tmpImage, 0, 0, intThisWidth, intThisHeight, null);
            g2.dispose();
            ImageIO.write(tmpBufferImage, THUMBNAIL_FILE_TYPE, thumbnail);
            // xx : old : end

            /*
             * //MultiStepRescaleOp rescale = new MultiStepRescaleOp(width, height);
             * ThumbnailRescaleOp rescale = new ThumbnailRescaleOp(width, height);
             * rescale.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Soft);
             * tmpBufferImage = rescale.filter(bufferImage, null);
             * //ImageIO.write(tmpBufferImage, THUMBNAIL_FILE_TYPE, thumbnail);
             * fos = new FileOutputStream(thumbnail);
             * JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
             * JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tmpBufferImage);
             * param.setQuality(0.8f, true);
             * encoder.setJPEGEncodeParam(param);
             * encoder.encode(tmpBufferImage);
             */
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        } finally {
            // g2 = null;
            // tmpImage = null;
            tmpBufferImage = null;
            bufferImage = null;

            try {
                if (bis != null)
                    bis.close();
            } catch (IOException e1) {
            }
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e2) {
            }
            // try { if (fos != null) fos.close(); } catch (IOException e2) {}
        }

        return thumbnail;
    }

    /**
     * 파일 소스 불러오기
     * 
     * @param file
     *            File
     * @param charset
     *            파일 인코딩
     * @return String
     * @throws IOException
     */
    public static String LoadFileSource(File file, String charset) throws IOException {

        if (file == null || !file.exists() || !file.isFile()) {
            return null;
        }

        StringBuffer ret = new StringBuffer();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {

            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, charset);
            br = new BufferedReader(isr);

            String line = null;

            /*
             * while((line=br.readLine()) != null) {
             * ret.append(line);
             * ret.append("\n");
             * }
             */

            while (true) {

                line = br.readLine();
                if (line == null) {
                    break;
                }

                ret.append(line);
                ret.append("\n");
            }

        } catch (Exception e) {
        } finally {

            if (br != null) {
                br.close();
            }

            if (isr != null) {
                isr.close();
            }

            if (fis != null) {
                fis.close();
            }
        }

        return ret.toString();
    }

    /**
     * 파일 소스 쓰기
     * 
     * @param file
     *            File
     * @param source
     *            파일 소스
     * @param charset
     *            파일 인코딩
     * @throws IOException
     */
    public static void SaveFileSource(File file, String source, String charset) throws IOException {
        if (file == null || !file.exists() || !file.isFile())
            return;

        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
        BufferedWriter bw = new BufferedWriter(osw);

        bw.write(source);

        if (bw != null)
            bw.close();
        if (osw != null)
            osw.close();
        if (fos != null)
            fos.close();
    }

    /**
     * 디렉토리 삭제
     * 
     * @param dir
     *            경로 객체
     */
    public static void DirDelete(File dir) {
        if (dir == null)
            return;

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    DirDelete(file);
                }
            }
        }

        dir.delete();
    }
}
