package com.hisco.cmm.aspect;

import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.log.mapper.LogMapper;
import com.hisco.admin.log.vo.AdminLogVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.annotation.PageActionType;
import com.hisco.cmm.exception.DeniedException;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminLogAop {
    @Resource(name = "logMapper")
    private LogMapper logMapper;

    public Object loggerAop(ProceedingJoinPoint proceedingJoinPoint, PageActionInfo infoAnnotation)
            throws Throwable {

        // 공통 기능이 적용되는 메서드가 어떤 메서드인지 출력하기 위해 메서드명을 얻어옴
        String signatureStr = proceedingJoinPoint.getSignature().toShortString();

        log.debug(signatureStr + "시작"); // 메서드 실행
        // 공통기능
        log.debug("핵심 기능 전에 실행 할 공통 기능입니다. " + System.currentTimeMillis());

        CommandMap commandMap = null;

        for (Object arg : proceedingJoinPoint.getArgs()) {
            log.debug("getArgs:" + arg.getClass().getName());
            if (arg instanceof CommandMap) {
                commandMap = (CommandMap) arg;
            }
        }

        if (infoAnnotation == null)
            return proceedingJoinPoint.proceed();

        String methodGubun = infoAnnotation.action();
        String methodTitle = infoAnnotation.title();
        boolean methodAjax = infoAnnotation.ajax();

        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if (isAuthenticated) {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            commandMap.setAdminUser(user);
        }

        Object result = null;
        try {
            // 권한 체크
            MenuManageVO menuVO = commandMap.getSelectedMenu();

            checkAuth(menuVO, commandMap, methodGubun);

            if(infoAnnotation.inqry()){
            	//개인정보 접속이력 저장
                AdminLogVO logVO = new AdminLogVO();
                logVO.setConectId(commandMap.getAdminUser() == null ? "" : commandMap.getAdminUser().getId());
                logVO.setConectIp(commandMap.getIp());
                logVO.setMenuid(menuVO.getMenuNo());
                logVO.setMethodGubun("R");
                logVO.setTargetUrl(commandMap.getTrgetUrl());
                logVO.setInqryMemberinfo("목록");
                logMapper.logInsertPrivateLog(logVO);
            }else{
            	 insertLog(menuVO, commandMap, methodGubun);
            }

            result = proceedingJoinPoint.proceed();

        } catch (DeniedException de) {
            if (methodAjax) {
                ModelAndView mav = new ModelAndView("jsonView");
                mav.addObject("result", HttpUtility.getErrorResultInfo("권한이 없습니다."));
                result = mav;
            } else {
                result = HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        } catch (Throwable e) {
            throw e;
        }

        return result;
    }

    private void checkAuth(MenuManageVO menuVO, CommandMap commandMap, String methodGubun) throws DeniedException {
        boolean authFlag = false;

        if (menuVO != null) {
            if (PageActionType.READ.equals(methodGubun) || (PageActionType.CREATE.equals(methodGubun) && "Y".equals(menuVO.getInsYn())) || (PageActionType.UPDATE.equals(methodGubun) && "Y".equals(menuVO.getUpdYn())) || (PageActionType.DELETE.equals(methodGubun) && "Y".equals(menuVO.getDelYn()))) {
                authFlag = true;
            } else {
                log.debug("No authorization exists for the menu: " + menuVO.getMenuUrl());
            }
        }else if(commandMap.getTrgetUrl().indexOf("/myinfo/") >0 ){
        	authFlag = true; //내정보 관리 이므로 접속당사자에게 권한이 있음
        } else {
            log.debug("getSelectedMenu() is null");
        }

        if (!authFlag)
            throw new DeniedException();
    }

    private void insertLog(MenuManageVO menuVO, CommandMap commandMap, String methodGubun) throws UnknownHostException {
        String strIpAdress = commandMap.getIp();

        AdminLogVO logVO = new AdminLogVO();
        logVO.setConectId(commandMap.getAdminUser() == null ? "" : commandMap.getAdminUser().getId());
        logVO.setConectIp(strIpAdress);
        if(menuVO != null){
        	 logVO.setMenuid(menuVO.getMenuNo());
        }

        logVO.setMethodGubun(methodGubun);
        logVO.setTargetUrl(commandMap.getTrgetUrl());
        logMapper.logInsertLoginLog(logVO);
    }
}
