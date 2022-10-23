package com.hisco.cmm.vo;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

/**
 * @Class Name : FileVO.java
 * @Description : 파일정보 처리를 위한 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 7. 23. 진수진
 * @author 진수진
 * @since 2020. 7. 23.
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class FileVO implements Serializable {

    /**
     * 기관코드
     */
    public String comcd = Config.COM_CD;

    /**
     * 파일 그룹 고유번호
     */
    public String fileGrpinnb = "";

    /**
     * 파일 그룹명
     */
    public String fileGrpnm = "";

    /**
     * 생성일자
     */
    public Date createDate = null;

    /**
     * 수정일자
     */
    public Date updateDate = null;

    /**
     * 파일확장자
     */
    public String fileExtsn = "";
    /**
     * 파일크기
     */
    public String fileSize = "";
    /**
     * 파일연번
     */
    public String fileSn = "";
    /**
     * 파일저장경로
     */
    public String filePath = "";
    /**
     * 원파일명
     */
    public String orginFileName = "";
    /**
     * 저장파일명
     */
    public String fileName = "";
    
    /**
     * 파일캡션
     * */
    public String fileCaption = "";

    /**
     * 생성자
     */
    public String creator = "";

    /**
     * 수정자
     */
    public String updator = "";

    /**
     * 삭제 파일 목록
     */
    public String deleteFiles = "";

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public String getFileGrpinnb() {
        return fileGrpinnb;
    }

    public void setFileGrpinnb(String fileGrpinnb) {
        this.fileGrpinnb = fileGrpinnb;
    }

    public String getFileGrpnm() {
        return fileGrpnm;
    }

    public void setFileGrpnm(String fileGrpnm) {
        this.fileGrpnm = fileGrpnm;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getFileExtsn() {
        return fileExtsn;
    }

    public void setFileExtsn(String fileExtsn) {
        this.fileExtsn = fileExtsn;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSn() {
        return fileSn;
    }

    public void setFileSn(String fileSn) {
        this.fileSn = fileSn;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getOrginFileName() {
        return orginFileName;
    }

    public void setOrginFileName(String orginFileName) {
        this.orginFileName = orginFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public String getDeleteFiles() {
        return deleteFiles;
    }

    public void setDeleteFiles(String deleteFiles) {
        this.deleteFiles = deleteFiles;
    }

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
