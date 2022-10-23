package com.hisco.cmm.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.MDC;
// import org.apache.log4j.MDC; log4j의존성 제거 - by 이기태 2022.02.09
import org.springframework.stereotype.Component;

import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.DateUtil;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;

@Component
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        MDC.put("TXID", this.getTxId((HttpServletRequest) request));
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private String getTxId(HttpServletRequest req) {
        String uri = req.getRequestURI();
        String txid = DateUtil.getTodate("yyyyMMdd_HHmmss");
        txid = txid.substring(txid.lastIndexOf("-") + 1);

        if (uri.contains(Config.USER_ROOT + "/")) {
            HttpSession session = req.getSession();
            UserSession userSession = (UserSession) session.getAttribute(Config.USER_SESSION);
            if (userSession != null) {
                LoginVO loginVO = userSession.getUserInfo();

                if (loginVO != null)
                	
                	if(loginVO.getIhidNum() == null || loginVO.getIhidNum().length() < 8) {
                		txid = String.format("%s_%s%s", txid, loginVO.getName(), "");
                	}else {
                		txid = String.format("%s_%s%s", txid, loginVO.getName(), loginVO.getIhidNum().replaceAll("-", "").substring(7));
                	}
            }
        } else {
            LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            if (loginVO != null)
                txid = String.format("%s_%s", txid, loginVO.getId());
        }

        return txid;
    }

}
