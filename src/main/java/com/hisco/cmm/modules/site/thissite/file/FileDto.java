package com.hisco.cmm.modules.site.thissite.file;

import java.util.Date;

import com.hisco.cmm.modules.extend.InterfaceDtoPaging;
import com.hisco.cmm.modules.extend.InterfaceDtoSearch;

public class FileDto extends FileVo implements InterfaceDtoPaging, InterfaceDtoSearch {
    private static final long serialVersionUID = 1L;

    private boolean search_mode;
    private String order_by;
    private int page;
    private int page_size;
    private int jump_size;

    private Date regdate_s; // 등록일 시작
    private Date regdate_e; // 등록일 종료

    private String search_file; // 파일 검색

    @Override
    public boolean getSearch_mode() {
        return search_mode;
    }

    @Override
    public void setSearch_mode(boolean search_mode) {
        this.search_mode = search_mode;
    }

    @Override
    public String getOrder_by() {
        return order_by;
    }

    @Override
    public void setOrder_by(String order_by) {
        this.order_by = order_by;
    }

    @Override
    public void CheckPage() {
        CheckPage(20, 10);
    }

    @Override
    public void CheckPage(int default_page_size, int default_jump_size) {
        if (this.page <= 0)
            this.page = 1;
        if (this.page_size <= 0)
            this.page_size = default_page_size;
        if (this.jump_size <= 0)
            this.jump_size = default_jump_size;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getPage_size() {
        return page_size;
    }

    @Override
    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    @Override
    public int getJump_size() {
        return jump_size;
    }

    @Override
    public void setJump_size(int jump_size) {
        this.jump_size = jump_size;
    }

    public Date getRegdate_s() {
        return regdate_s;
    }

    public void setRegdate_s(Date regdate_s) {
        this.regdate_s = regdate_s;
    }

    public Date getRegdate_e() {
        return regdate_e;
    }

    public void setRegdate_e(Date regdate_e) {
        this.regdate_e = regdate_e;
    }

    public String getSearch_file() {
        return search_file;
    }

    public void setSearch_file(String search_file) {
        this.search_file = search_file;
    }
}
