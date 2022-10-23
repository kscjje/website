package com.hisco.cmm.modules.site.thissite.file;

import java.io.File;
import java.net.URLConnection;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceFileDto {

    private String dir; // 경로
    private String type; // 종류(D : 디렉토리, F : 파일)
    private String name; // 이름(확장자 포함)
    private String ext; // 확장자
    private String mime; // MIME
    private boolean hidden; // 숨긴 파일 여부
    private long size; // 크기(byte)
    private Date lastModified; // 최종 수정일

    public ResourceFileDto(File file) {

        if (file == null || !file.exists())
            return;

        dir = file.getAbsolutePath();
        if (file.isDirectory()) {
            type = "D";
        } else if (file.isFile()) {
            type = "F";
        }

        name = file.getName();

        if (file.isFile() && name.lastIndexOf(".") > -1) {
            ext = name.substring(name.lastIndexOf(".") + 1).toUpperCase();
        }

        if (file.isFile()) {
            mime = URLConnection.guessContentTypeFromName(name);
            log.debug("mime = " + mime);
        }

        hidden = file.isHidden();

        size = file.length();

        lastModified = new Date(file.lastModified());
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}