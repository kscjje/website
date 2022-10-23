/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.cmm.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : AdminAcessFilter.java
 * @Description : 자세한 클래스 설명
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 10. 29
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Slf4j
public class AdminAccessFilter implements Filter {
    private String adminRoot;
    private String mngRoot;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        adminRoot = filterConfig.getInitParameter("ADMIN_ROOT");
        mngRoot = filterConfig.getInitParameter("MNG_ROOT");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String contextPath = httpRequest.getContextPath();
        String requestUri = httpRequest.getRequestURI().replace(contextPath, "");

        log.debug("PRCESS ADMIN ACCESS FILTER..");

        chain.doFilter(request, new RedirectResponseWrapper(httpResponse, adminRoot, mngRoot, requestUri, contextPath));
    }

    @Override
    public void destroy() {
        /* DO NOTHING */
    }

    static class RedirectResponseWrapper extends HttpServletResponseWrapper {
        private String adminRoot;
        private String mngRoot;
        private String requestUri;
        private String contextPath;

        /**
         * @param response
         */
        public RedirectResponseWrapper(HttpServletResponse response, String adminRoot, String mngRoot,
                String requestUri, String contextPath) {
            super(response);
            this.adminRoot = adminRoot;
            this.mngRoot = mngRoot;
            this.requestUri = requestUri;
            this.contextPath = contextPath;
        }

        @Override
        public void sendRedirect(String location) throws IOException {
            if (requestUri.startsWith(mngRoot)) {

                String _contextPath = contextPath;
                if ("/".equals(_contextPath))
                    _contextPath = "";
                log.debug("SEND REDIRECT TO " + location);
                String _location = location.replace(_contextPath, "");

                if (_location.startsWith(adminRoot)) {

                    String newLocation = _contextPath + _location.replace(adminRoot, mngRoot);

                    super.sendRedirect(newLocation);
                } else {
                    super.sendRedirect(location);
                }
            } else {
                super.sendRedirect(location);
            }
        }
    }
}
