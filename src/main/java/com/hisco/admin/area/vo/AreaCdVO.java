package com.hisco.admin.area.vo;

import java.util.List;

import lombok.Data;

@Data
public class AreaCdVO {

    private int areaCd;
    private String areaNm;
    private String areaGbn;
    private int parentAreaCd;
    private int sortOrder;

    private String areaName;

    
    private List<AreaCdVO> subAreaList;

}
