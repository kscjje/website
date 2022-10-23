package com.hisco.user.mypage.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hisco.admin.twedu.vo.TweduVO;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;

import egovframework.com.cmm.LoginVO;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;
/**
 * 사물함 이력 및 환불 현황 컨트롤러
 *
 * @author 김영철
 * @since 2022.10.12
 * @version 1.0, 2022.10.12
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          김영철 2022.10.12 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/mypage/lckr")
public class MyLckrController {
	
	
	/**
     * 사물함 이력현황 목록
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @RequestMapping("/myLckrList")
    public String selectLckrdList(CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

    	log.debug("call selectLckrList");
    	
    	LoginVO loginVO = commandMap.getUserInfo();
    	
    	PaginationInfo paginationInfo = commandMap.getPagingInfo();
    	
    	
    	int totCount = 0;
    	paginationInfo.setTotalRecordCount(totCount);
        //PaginationInfo paginationInfo = commandMap.getPagingInfo();
        //searchVO.setEdcProgmType(Constant.SM_EDCPROGM_TYPE_일반교육); // 일반교육
        //searchVO.setComcd(Config.COM_CD);
        //searchVO.setPaginationInfo(paginationInfo);
        //List<?> programList = eduAdmService.selectEdcProgramList(searchVO);

        //int totCount = 0;
        //if (programList != null && programList.size() >= 1) {
        //    totCount = ((EdcProgramVO) programList.get(0)).getTotCount();
        //}

        // 접수방식
//        model.addAttribute("receptType", codeService.selectCodeList("SM_LERECPTYPE"));
        // 교육대상
//        model.addAttribute("targetType", codeService.selectCodeList("CM_AGEGBN"));

        //paginationInfo.setTotalRecordCount(totCount);
    	//model.addAttribute("programList", programList);

        model.addAttribute("paginationInfo", paginationInfo);
    	model.addAttribute("userInfo", commandMap.getUserInfo());
    	model.addAttribute("commandMap", commandMap);
    	return HttpUtility.getViewUrl(request);
    }
    
    /**
     * 교육프로그램 상세
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/{lckrRentappIdx}")
    public String programDetail(@PathVariable("lckrRentappIdx") int lckrRentappIdx,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model, Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        LoginVO loginVO = commandMap.getUserInfo();

        // setGenderAndAge(model, commandMap);

        return Config.USER_ROOT + "/mypage/lckr/myLckrDetail";
    }
    
    
    
    /**
     * 사물함 이력현황 목록
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @RequestMapping("/myLckrRefundList")
    public String selectLckrdRefundList(CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

    	log.debug("call selectLckrList");
    	
    	LoginVO loginVO = commandMap.getUserInfo();
        //PaginationInfo paginationInfo = commandMap.getPagingInfo();
        //searchVO.setEdcProgmType(Constant.SM_EDCPROGM_TYPE_일반교육); // 일반교육
        //searchVO.setComcd(Config.COM_CD);
        //searchVO.setPaginationInfo(paginationInfo);
        //List<?> programList = eduAdmService.selectEdcProgramList(searchVO);

        //int totCount = 0;
        //if (programList != null && programList.size() >= 1) {
        //    totCount = ((EdcProgramVO) programList.get(0)).getTotCount();
        //}

        // 접수방식
//        model.addAttribute("receptType", codeService.selectCodeList("SM_LERECPTYPE"));
        // 교육대상
//        model.addAttribute("targetType", codeService.selectCodeList("CM_AGEGBN"));

        //paginationInfo.setTotalRecordCount(totCount);

        //model.addAttribute("paginationInfo", paginationInfo);
        //model.addAttribute("programList", programList);
    	model.addAttribute("userInfo", commandMap.getUserInfo());
        model.addAttribute("commandMap", commandMap);
        return HttpUtility.getViewUrl(request);
    }
}
