package com.hisco.cmm.modules.site.thissite.module_config;

import org.springframework.validation.BindingResult;

import com.hisco.cmm.modules.extend.InterfaceDtoPaging;
import com.hisco.cmm.modules.extend.InterfaceDtoSearch;

public class ModuleConfigDto extends ModuleConfigVo implements InterfaceDtoPaging, InterfaceDtoSearch {
    private static final long serialVersionUID = 1L;

    private boolean search_mode;
    private String order_by;
    private int page;
    private int page_size;
    private int jump_size;

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

    /**
     * 기본값 설정
     */
    public void Default() {
        this.setUse_YN("Y");
    }

    /**
     * 폼 체크
     * 
     * @param bindingResult
     */
    public void CheckForm(BindingResult bindingResult) {
    }
}
