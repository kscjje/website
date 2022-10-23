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

public class HttpHeaderSecurityFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // log.debug("call HttpHeaderSecurityFilter :: doFilter()");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String strReqUri = req.getRequestURI();

        // log.debug("strReqUri = " + strReqUri);

        /*
         * if ( ("/web/main".equals(strReqUri)) ) {
         * res.setHeader("Content-Security-Policy", "default-src 'self'");
         * }
         */

        /*
         * if ( ("/web/mobileSideMenu.html".equals(strReqUri)) || ("/web/exbtrsvn/calendarCheckAjax".equals(strReqUri))
         * || ("/web/evtrsvn/evtrsvnSelectAjax".equals(strReqUri)) ) {
         * res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
         * res.setHeader("Pragma", "no-cache");
         * }
         */

        /*
         * if ( ("/web/autocontents/contents/main/115".equals(strReqUri)) ) {
         * } else {
         * res.setHeader("Content-Security-Policy",
         * "script-src 'strict-dynamic' 'nonce-rAnd0m123' 'unsafe-inline' http: https:;object-src 'none';base-uri 'none';require-trusted-types-for 'script';"
         * );
         * }
         */

        // default-src ‘self’; img-src *; media-src media1.com media2.com; script-src script.example.com;
        // res.setHeader("Content-Security-Policy", "script-src 'unsafe-inline' http: https:;object-src 'none';base-uri
        // 'none';");
        // res.setHeader("Content-Security-Policy", "default-src 'self'; img-src *; media-src *; script-src
        // 'unsafe-inline'");

        res.setHeader("X-Frame-Options", "SAMEORIGIN");
        res.setHeader("X-Content-Type-Options", "nosniff");
        res.setHeader("X-XSS-Protection", "1; mode=block");

        chain.doFilter(request, response);
    }

    public void destroy() {
        // log.debug("call HttpHeaderSecurityFilter :: destroy()");
    }

    public void init(FilterConfig filterConfig) {
        // log.debug("call HttpHeaderSecurityFilter :: init()");
    }
}
