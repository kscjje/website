package com.hisco.cmm.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RedirectUtil {
	/**
	 * 로그인 페이지로 리다이렉트
	 */
	public static String redirectLogin(ModelMap model) {
//		System.out.println("call redirectLogin(model)");
		return redirectLogin(getReturnUrl(false), model);
	}

	/**
	 * 로그인 페이지로 리다이렉트
	 */
	public static String redirectLogin(String returnUrl, ModelMap model) {
//		System.out.println("call redirectLogin(returnUrl,model)");
//		System.out.println(returnUrl);
		redirectLogin(returnUrl, model, false);
		return "/web/redirect/login";
	}

	/**
	 * 로그인 페이지로 리다이렉트
	 */
	public static String redirectLoginParam(ModelMap model) {
//		System.out.println("call redirectLogin(returnUrl,model)");
//		System.out.println("model:"+model);
		return redirectLogin(getReturnUrl(true), model);
	}

	/**
	 * 로그인 페이지로 리다이렉트
	 */
	public static String redirectLoginParam(String returnUrl, ModelMap model) {
//		System.out.println("call redirectLoginParam(returnUrl,model)");
//		System.out.println("model:"+model);
		redirectLogin(returnUrl, model, true);
//		redirectLogin("/", model, true);
		return "/web/redirect/login";
	}

	/**
	 * 로그인 페이지로 리다이렉트
	 */
	public static String redirectLogin(String returnUrl, ModelMap model, boolean isQueryString) {
//		System.out.println("call redirectLogin(returnUrl,model,isQueryString)");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String fullPath = returnUrl;
		if (isQueryString) {
			if (fullPath.indexOf("?") < 0) {
				fullPath = fullPath + getQueryString(request);
			} else {
				fullPath = fullPath + "&" + getQueryString(request).substring(1);
			}
		}
		model.addAttribute("LOGIN_URL", "/web/member/login");
		model.addAttribute("RETURN_URL", fullPath);
		model.addAttribute("PARAM_MESSAGE", "로그인 후 이용이 가능합니다.");
		return "/web/redirect/login";
	}

	/**
	 * Login용 Return Url 조회
	 */
	public static String getReturnUrl(boolean isQueryString) {
//		System.out.println("call getReturnUrl(isQueryString)");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return getReturnUrl(request, isQueryString);
	}

	/**
	 * Login용 Return Url 조회
	 */
	public static String getReturnUrl(HttpServletRequest request, boolean isQueryString) {
//		System.out.println("call getReturnUrl(req,isQueryString)");
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(SessionUtil.getPath(request));
		if (request.getPathInfo() != null) {
			urlBuilder.append(request.getPathInfo());
		}
		if (isQueryString) {
			urlBuilder.append(getQueryString(request));
		}
		return urlBuilder.toString();
	}

	/**
	 * Login용 Return Url 조회
	 */
	public static String getQueryString(HttpServletRequest request) {
//		System.out.println("call getQueryString(request)");
		StringBuilder urlBuilder = new StringBuilder();
		Enumeration<String> paramNames = request.getParameterNames();
		if (paramNames != null) {
			int i = 0;
			while (paramNames.hasMoreElements()) {
				String name = paramNames.nextElement();
				String value = request.getParameter(name);
				urlBuilder.append(i == 0 ? "?" : "&").append(name).append("=").append(value);
				i++;
			}
		}
		return urlBuilder.toString();
	}
}
