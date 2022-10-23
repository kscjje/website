/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.cmm.config;

import javax.servlet.http.HttpServletRequest;

import com.hisco.cmm.util.Config;

import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : DynamicConfigUtil.java
 * @Description : 자세한 클래스 설명
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 12. 31
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Slf4j
public class DynamicConfigUtil {
    private DynamicConfig config;

    /**
     * @return the config
     */
    public DynamicConfig getConfig() {
        return config;
    }

    /**
     * @param config
     *            the config to set
     */
    public void setConfig(DynamicConfig config) {
        this.config = config;
    }

    public String getAdminPath(HttpServletRequest request) {
        return getAdminPath(request, false);
    }

    public String getAdminPath(HttpServletRequest request, boolean includeContext) {
        if (includeContext) {
            return getAdminPath(request.getContextPath(), request.getRequestURI());
        } else {
            return getAdminPath("", request.getRequestURI().replace(request.getContextPath(), ""));
        }
    }

    public String getAdminPath(String contextPath, String requestUri) {
        String _requestUri = requestUri.replace(contextPath, "");
        if (_requestUri.startsWith(config.getManagerRoot())) {
            return contextPath + config.getManagerRoot();
        } else {
            return contextPath + config.getAdminRoot();
        }
    }

    public String getAdminDynamicPath(HttpServletRequest request, String uri) {
        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI().replace(contextPath, "");
        String mngRoot = config.getManagerRoot();
        String admRoot = config.getAdminRoot();

        if (requestUri.startsWith(mngRoot)) {
            return uri.replace(Config.ADMIN_ROOT, mngRoot);
        } else if (requestUri.startsWith(admRoot)) {
            return uri.replace(Config.ADMIN_ROOT, admRoot);
        } else {
            return uri;
        }
    }

    public boolean isAdmin(HttpServletRequest request) {
        String requestUri = getRequestUriWithoutContext(request);
        log.debug("getRequestUriWithoutContext=" + requestUri);
        return isAdmin(requestUri);
    }

    public boolean isAdmin(String requestUri) {
        return requestUri.startsWith(config.getAdminRoot()) || requestUri.startsWith(config.getManagerRoot());
    }

    public boolean isAdminEX(String requestUri) {
        return requestUri.startsWith(config.getManagerRoot());
    }

    public boolean isAdminEX(HttpServletRequest request) {
        String requestUri = getRequestUriWithoutContext(request);

        return isAdminEX(requestUri);
    }

    public boolean isUser(String requestUri) {
        return requestUri.startsWith(Config.USER_ROOT);
    }

    public boolean isUser(HttpServletRequest request) {
        String requestUri = getRequestUriWithoutContext(request);

        return isUser(requestUri);
    }

    private String getRequestUriWithoutContext(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI();

        if (!"".equals(contextPath) && requestUri.startsWith(contextPath)) {
            return requestUri.replace(contextPath, "");
        } else {
            return requestUri;
        }
    }
}
