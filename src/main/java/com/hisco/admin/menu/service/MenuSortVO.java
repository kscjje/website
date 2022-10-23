package com.hisco.admin.menu.service;

public class MenuSortVO {
    private int parentMenuSrl;
    private int alignStart;
    private int alignEnd;
    private int depthStart;
    private int depthEnd;
    private int alignNew;
    private int depthNew;
    private String siteGubun;

    public int getParentMenuSrl() {
        return parentMenuSrl;
    }

    public void setParentMenuSrl(int parentMenuSrl) {
        this.parentMenuSrl = parentMenuSrl;
    }

    public int getAlignStart() {
        return alignStart;
    }

    public void setAlignStart(int alignStart) {
        this.alignStart = alignStart;
    }

    public int getAlignEnd() {
        return alignEnd;
    }

    public void setAlignEnd(int alignEnd) {
        this.alignEnd = alignEnd;
    }

    public int getDepthStart() {
        return depthStart;
    }

    public void setDepthStart(int depthStart) {
        this.depthStart = depthStart;
    }

    public int getDepthEnd() {
        return depthEnd;
    }

    public void setDepthEnd(int depthEnd) {
        this.depthEnd = depthEnd;
    }

    public int getAlignNew() {
        return alignNew;
    }

    public void setAlignNew(int alignNew) {
        this.alignNew = alignNew;
    }

    public int getDepthNew() {
        return depthNew;
    }

    public void setDepthNew(int depthNew) {
        this.depthNew = depthNew;
    }

    public String getSiteGubun() {
        return siteGubun;
    }

    public void setSiteGubun(String siteGubun) {
        this.siteGubun = siteGubun;
    }

}
