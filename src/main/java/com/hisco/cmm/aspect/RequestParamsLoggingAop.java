package com.hisco.cmm.aspect;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.json.simple.JSONObject;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hisco.cmm.config.DynamicConfigUtil;
import com.hisco.cmm.util.Config;

import lombok.extern.slf4j.Slf4j;

@Slf4j

public class RequestParamsLoggingAop {

    @Resource(name = "dynamicConfigUtil")
    private DynamicConfigUtil configUtil;

    public Object loggingParams(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        StopWatch watch = new StopWatch("controllerExecuteTime");
        watch.start();

        // request 정보를가져온다.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // path 설정이 되어 있는거 체크
        String userUri = (request.getContextPath() + "/web/").replaceAll("\\/\\/", "\\/");
        if (request.getRequestURI().startsWith(userUri)) {
            request.setAttribute("resourceContext", request.getContextPath() + Config.USER_ROOT + "/resources");
        } else {
            String url = configUtil.getAdminDynamicPath(request, Config.ADMIN_ROOT + "/resources");
            request.setAttribute("resourceContext", request.getContextPath() + url);
        }

        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            watch.stop();

            String controllerName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
            String methodName = proceedingJoinPoint.getSignature().getName();

            Map<String, Object> params = new HashMap<>();

            try {
                params.put("controller.method", controllerName + "." + methodName);
                params.put("params", getParams(request));
                // params.put("log_time", new Date());
                params.put("request_uri", request.getRequestURI());
                params.put("http_method", request.getMethod());
                params.put("elapsed(ms)", watch.getTotalTimeMillis());
            } catch (Exception e) {
                log.error("error", e);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            log.debug("{}", gson.toJson(params)); // param에 담긴 정보들을 한번에 로깅한다.
        }
    }

    /**
     * request 에 담긴 정보를 JSONObject 형태로 반환한다.
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private static JSONObject getParams(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        Enumeration<String> params = request.getParameterNames();

        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replaceAll("\\.", "-");
            jsonObject.put(replaceParam, request.getParameter(param));
        }

        return jsonObject;
    }

}
