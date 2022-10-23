/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.cmm.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.hisco.cmm.config.DynamicConfigUtil;

/**
 * @Class Name : AdminResTag.java
 * @Description : 설정된 관리자 경로에 따른 리소스 웹경로 반환
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 2
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
public class AdminResTag extends AbstractTagLib {
    private static final long serialVersionUID = 1L;

    private String adminRoot;

    private String referer;

    private String path;

    public AdminResTag() {
        super();

    }

    @Override
    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        System.out.println("referer=" + referer);
        if (request == null)
            return super.doStartTag();

        if (this.path != null) {
            this.value = getAdminRoot() + (this.path.startsWith("/") ? this.path : "/" + this.path);
        } else {
            this.value = "";
        }

        return super.doEndTag();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    private String getAdminRoot() {
        if (adminRoot == null) {
            DynamicConfigUtil dynamicConfig = (DynamicConfigUtil) getBean("dynamicConfigUtil");

            // adminRoot == null 일때 context path 설정 처리
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            pageContext.getSession();

            if (referer == null)
                adminRoot = dynamicConfig.getAdminPath(request);
            else
                adminRoot = dynamicConfig.getAdminPath(request.getContextPath(), referer);

            String contextPath = request.getContextPath();
            if (contextPath.length() > 1)
                adminRoot = contextPath + adminRoot;
        }
        return adminRoot;
    }
}
