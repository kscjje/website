package com.hisco.cmm.modules.extend;

public interface InterfaceDtoPaging {

    public void CheckPage();

    public void CheckPage(int default_page_size, int default_jump_size);

    public int getPage();

    public void setPage(int page);

    public int getPage_size();

    public void setPage_size(int page_size);

    public int getJump_size();

    public void setJump_size(int jump_size);

}
