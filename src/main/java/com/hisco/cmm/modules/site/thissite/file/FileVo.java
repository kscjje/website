package com.hisco.cmm.modules.site.thissite.file;

import java.io.Serializable;
import java.util.Date;

import com.hisco.cmm.modules.extend.DefaultObject;
import com.hisco.cmm.modules.CryptoUtil;
import com.hisco.cmm.modules.DateUtil;
import com.hisco.cmm.modules.Sleep;

public class FileVo extends DefaultObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private long file_srl; // 고유번호
    private long file_id; // 파일 아이디
    private int flag; // 파일 플래그
    private String sid; // 파일 sid
    private String upload_path; // 업로드 경로
    private String upload_filename; // 업로드 파일명
    private String filename; // 실제 파일명
    private long size; // 파일 크기(byte)
    private String mime; // 파일 MIME
    private long download_count; // 다운로드 수
    private String description; // 파일 설명
    private Date regdate; // 등록일

    private String delete_YN; // 삭제 여부
    private Date date_start; // 사용 기간 시작
    private Date date_end; // 사용 기간 종료
    private Date date_limit; // 사용 기간 만료

    public void NewSID() {
        Sleep.Call(1);
        // sid = CryptoUtil.encodeBase64String(CryptoUtil.getEncPassword("FILE_" + DateUtil.printDatetime(null,
        // "yyyy-MM-dd HH:mm:ss:SSS") +"@"+ Math.abs((new java.security.SecureRandom()).nextInt(100000))));
        sid = CryptoUtil.getEncPassword("FILE_" + DateUtil.printDatetime(null, "yyyy-MM-dd HH:mm:ss:SSS") + "@" + Math.abs((new java.security.SecureRandom()).nextInt(100000)));
    }

    public String DownloadUrl() {
        return "/File/Download/" + this.sid;
    }

    public String ViewUrl() {
        return "/File/" + this.sid;
    }

    public String ThumbnailUrl(int width, int height) {
        if (width > 0 || height > 0)
            return "/File/Thumbnail/" + this.sid + "/" + width + "/" + height;
        else
            return DownloadUrl();
    }

    public long getFile_srl() {
        return file_srl;
    }

    public void setFile_srl(long file_srl) {
        this.file_srl = file_srl;
    }

    public long getFile_id() {
        return file_id;
    }

    public void setFile_id(long file_id) {
        this.file_id = file_id;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUpload_path() {
        return upload_path;
    }

    public void setUpload_path(String upload_path) {
        this.upload_path = upload_path;
    }

    public String getUpload_filename() {
        return upload_filename;
    }

    public void setUpload_filename(String upload_filename) {
        this.upload_filename = upload_filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public long getDownload_count() {
        return download_count;
    }

    public void setDownload_count(long download_count) {
        this.download_count = download_count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public String getDelete_YN() {
        return delete_YN;
    }

    public void setDelete_YN(String delete_YN) {
        this.delete_YN = delete_YN;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public Date getDate_limit() {
        return date_limit;
    }

    public void setDate_limit(Date date_limit) {
        this.date_limit = date_limit;
    }
}
