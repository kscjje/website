package com.hisco.cmm.exception;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.log.service.LogService;
import com.hisco.admin.log.vo.ErrorLogVO;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.ExceptionUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.JsonUtil;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * 공통 예외 처리
 *
 * @author 전영석
 * @since 2020.08.14
 * @version 1.0, 2020.08.14
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.14 최초작성
 */

@Slf4j
@ControllerAdvice
public class SystemException {

    @Resource(name = "logService")
    private LogService logService;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest request, HttpServletResponse response, Exception ex)
            throws Exception {

        log.error("call Exception :: handleException :: {}", ExceptionUtil.getErrorLine(ex));

        int intResponseStatus = response.getStatus();
        String strRequestURI = String.valueOf(request.getRequestURI());
        String strRequestURL = String.valueOf(request.getRequestURL());
        // String strUserIp = request.getRemoteAddr();

        String strUserIp = HttpUtility.getClientIP(request);
        ;

        log.error("intResponseStatus = " + intResponseStatus);

        try {

            String strUserId = "";
            Boolean lbAdminLogin = EgovUserDetailsHelper.isAuthenticated();

            if (lbAdminLogin) {
                LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
                strUserId = user.getId();
            } else {
                HttpSession session = request.getSession();
                UserSession userSession = (UserSession) session.getAttribute(Config.USER_SESSION);
                if (userSession != null) {
                    LoginVO loginVO = userSession.getUserInfo();
                    if (loginVO != null) {
                        strUserId = loginVO.getId();
                    }
                }
            }

            int idx = 1;

            StringBuffer log = new StringBuffer();

            log.append("[ERROR]\n");
            log.append(ex.toString());
            log.append("\n\n[StackTrace]\n");
            for (StackTraceElement elem : ex.getStackTrace()) {
                log.append(idx).append(" : ").append(elem.toString()).append("\n");
                idx++;
            }

            ErrorLogVO errorWebLog = new ErrorLogVO();

            errorWebLog.setComcd(Config.COM_CD);
            errorWebLog.setConnectUrl(strRequestURL);
            errorWebLog.setConectId(strUserId);
            errorWebLog.setConectIp(strUserIp);
            errorWebLog.setErrormsg(log.toString());
            errorWebLog.setParamVal(JsonUtil.Object2String(request.getParameterMap()));
            errorWebLog.setRefUrl(request.getHeader("referer"));

            logService.insertErrorLog(errorWebLog);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/web/exception/system/errorPage");

        return mav;// "forward:/web/exception/system/errorPage";

        // com.hisco.cmm.util.HttpUtility.sendRedirect(request, response, "", "/web/exception/system/errorPage");

    }

    @ExceptionHandler(MyException.class)
    public String handleException(ServletWebRequest servletWebRequest, MyException ex) {

        log.error("call MyException :: handleException");

        String strRequestURI = String.valueOf(servletWebRequest.getRequest().getRequestURI());
        String strRequestURL = String.valueOf(servletWebRequest.getRequest().getRequestURL());

        log.error("strRequestURI = " + strRequestURI);
        log.error("strRequestURL = " + strRequestURL);

        ex.printStackTrace();

        String strExceptionType = "CUSTOM";
        String strErrCode = String.valueOf(ex.getErrorCode());
        String strErrMsg = ex.getMessage();

        Map<String, Object> exceptionMap = new HashMap<String, Object>();

        String strExceptionArr[] = CommonUtil.getExcepFirstValue(ex.getStackTrace());

        String strClassName = strExceptionArr[0];
        if (strClassName == null)
            strClassName = "";

        String strFileName = strExceptionArr[1];
        if (strFileName == null)
            strFileName = "";

        String strMethoName = strExceptionArr[2];
        if (strMethoName == null)
            strMethoName = "";

        String strLineNumbr = strExceptionArr[3];
        if (strLineNumbr == null)
            strLineNumbr = "";

        exceptionMap.put("ERROR_TYPE", strExceptionType);
        exceptionMap.put("ERROR_CODE", strErrCode);
        exceptionMap.put("ERROR_MSG", strErrMsg);
        exceptionMap.put("REQUEST_URI", strRequestURI);
        exceptionMap.put("REQUEST_URL", strRequestURL);
        exceptionMap.put("CLASS_NAME", strClassName);
        exceptionMap.put("FILE_NAME", strFileName);
        exceptionMap.put("FILE_METHOD", strMethoName);
        exceptionMap.put("FILE_NUMBER", strLineNumbr);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        return "/web/exception/system/errorPage";
    }
}
