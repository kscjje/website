package com.hisco.cmm.modules.site.thissite.file;

import java.util.List;
import com.hisco.cmm.modules.site.thissite.file.FileVo;

public class FileSaveReturnDto {

    private long file_id; // 파일 아이디
    private List<FileVo> fileList; // 파일 목록
    private int count; // 파일 카운트

    public FileSaveReturnDto() {

        this.file_id = 0;
        this.fileList = null;
        this.count = 0;

    }

    public void Recount() {

        if (fileList != null && fileList.size() > 0) {
            this.count = fileList.size();
        } else {
            this.count = 0;
        }

    }

    public FileVo FileSearch(int flag) {

        if (fileList != null && fileList.size() > 0) {
            for (FileVo data : fileList) {
                if (data != null && data.getFile_id() == file_id && data.getFlag() == flag) {
                    return data;
                }
            }
        }

        return null;
    }

    public long getFile_id() {
        return file_id;
    }

    public void setFile_id(long file_id) {
        this.file_id = file_id;
    }

    public List<FileVo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileVo> fileList) {
        this.fileList = fileList;
        if (fileList != null && fileList.size() > 0)
            this.count = fileList.size();
        else
            this.count = 0;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
