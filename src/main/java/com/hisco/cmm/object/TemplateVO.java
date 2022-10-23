package com.hisco.cmm.object;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @Class Name : TemplateVO.java
 * @Description : TemplateVO class
 * @Modification Information
 * @
 *   @ 수정일 수정자 수정내용
 *   @ ------- -------- ---------------------------
 *   @ 2021.03.19 전영석 최초 생성
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0
 * @see
 */
@SuppressWarnings("serial")
public class TemplateVO implements Serializable {

    private String comcd = Config.COM_CD;

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

}
