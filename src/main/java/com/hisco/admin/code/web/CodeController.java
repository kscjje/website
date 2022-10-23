package com.hisco.admin.code.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.google.gson.Gson;
import com.hisco.admin.code.service.CodeManageService;
import com.hisco.admin.log.service.LogService;
import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgInfoVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.CodeVO;
import com.hisco.extend.AbstractController;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;
import com.hisco.cmm.util.JsonData;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.cmm.util.Utils;

/**
 * 코드 관리
 *
 * @author 진수진
 * @since 2020.07.17
 * @version 1.0, 2020.07.17
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.17 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class CodeController extends AbstractController{

    @Resource(name = "codeManageService")
    private CodeManageService codeManageService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    @Resource(name = "codeService")
    private CodeService codeService;

    /** EgovPropertyService */
    /*
     * @Resource(name = "propertiesService")
     * private EgovPropertyService propertiesService;
     */

    /** logService */
    @Resource(name = "logService")
    private LogService logService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    /**
     * 공통분류코드 목록을 조회한다.
     *
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return String
     * @throws Exception
     */
    @PageActionInfo(title = "공통코드그룹관리 목록", action = "R")
    @GetMapping(value = "/code/parentList")
    public String selectCmmnCodeList(@ModelAttribute("searchVO") CodeVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

        //PaginationInfo paginationInfo = commandMap.getPagingInfo();
        //searchVO.setPaginationInfo(paginationInfo);
        //searchVO.setComcd(Config.COM_CD);

        //int totCnt = codeManageService.selectCodeGrpCnt(searchVO);
        //paginationInfo.setTotalRecordCount(totCnt);

        //List<?> list = codeManageService.selectCodeGrpList(searchVO);
        //model.addAttribute("resultList", list);
        //model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    @ResponseBody
    @PageActionInfo(title = "공통코드그룹관리 목록", action = "R")
    @RequestMapping(value = "/code/parentListAjax2", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
    public String ajaxSelectCmmnCodeList(
    		  @RequestParam HashMap<String, Object> req
    		, @ModelAttribute("searchVO") CodeVO searchVO
    		, CommandMap commandMap
    		, HttpServletRequest request
    		, Model model)
            throws Exception {
    	
    	/*Client Parameters*/
    	HashMap<String, Object> params = new HashMap<String, Object>(); 
    	params.putAll(req);    	
    	
    	params.put("comcd", searchVO.getComcd());
    	params.put("recordCountPerPage", searchVO.getRecordCountPerPage());
    	
    	List<HashMap<String, Object>> list = codeManageService.selectCodeGrpList2(params);
		Integer TotalRecordCount = 0;
		if(list != null && list.size()>0) {
			HashMap<String, Object> item = (HashMap<String, Object>)list.get(0);
			TotalRecordCount = Integer.parseInt(item.get("TOT_CNT").toString());
		}
		
		Map<String, Object> ReturnMap = new HashMap<String, Object>();
		Map<String, Object> ReturnData 		= new HashMap<String, Object>();
		Map<String, Object> ReturnPagination = new HashMap<String, Object>();
		ReturnMap.put("result", true);
		ReturnPagination.put("TotalRecordCount", TotalRecordCount);
		ReturnData.put("pagination", ReturnPagination);
		ReturnData.put("contents", Utils.keyChangeLower(list));
		ReturnMap.put("data", ReturnData);
		String jsonData = MakeJsonData(request, ReturnMap);

		return jsonData;
    }
    
	@PageActionInfo(title = "공통코드그룹관리 목록", action = "R")
    @GetMapping(value = "/code/parentList_bak20220913")
    public String selectCmmnCodeList_bak20220913(@ModelAttribute("searchVO") CodeVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setComcd(Config.COM_CD);

        int totCnt = codeManageService.selectCodeGrpCnt(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);

        List<?> list = codeManageService.selectCodeGrpList(searchVO);
        model.addAttribute("resultList", list);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }
    
    /**
     * 공통분류코드 목록을 조회한다.
     *
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return String
     * @throws Exception
     */
    @PageActionInfo(title = "공통코드그룹관리 목록(엑셀용)", action = "R")
    @GetMapping("/code/parentListAjax")
    public String parentListAjax(@ModelAttribute("searchVO") CodeVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setPageSize(99999);
        paginationInfo.setRecordCountPerPage(99999);

        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setComcd(Config.COM_CD);

        int totCnt = codeManageService.selectCodeGrpCnt(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);

        List<?> list = codeManageService.selectCodeGrpList(searchVO);

        model.addAttribute("resultList", list);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 공통코드 그룹 등록을 위한 등록페이지로 이동한다.
     *
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "공통코드그룹관리 등록", action = "R")
    @GetMapping("/code/parentDetailAjax")
    public String selectCmmnCodeDetail(@ModelAttribute("codeVO") CodeVO codeVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        CodeVO thisCodeVO = new CodeVO();

        // 수정일경우 불러오기
        if (!commandMap.getString("MODE").equals("INSERT")) {
            codeVO.setComcd(Config.COM_CD);
            thisCodeVO = codeManageService.selectCodeGrpDetail(codeVO);
        }

        if (!commandMap.getString("MODE").equals("INSERT")) {
            model.addAttribute("codeVO", thisCodeVO);
        } else {
            model.addAttribute("codeVO", codeVO);
        }

        // model.addAttribute("codeVO", codeVO); 원본 2021.06.21
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 공통코드를 등록한다.
     *
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PostMapping("/code/parentSave")
    @ResponseBody
    public ModelAndView insertCmmnCode(@ModelAttribute("codeVO") CodeVO codeVO, CommandMap commandMap, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        codeVO.setReguser((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
        codeVO.setComcd(Config.COM_CD);

        if (commandMap.getString("MODE").equals("INSERT")) {
            if (logService.checkAdminLog(commandMap, "C", "공통코드 그룹 등록")) {
                CodeVO vo = codeManageService.selectCodeGrpDetail(codeVO);
                if (vo != null) {
                    // 중복 에러
                    resultInfo = HttpUtility.getErrorResultInfo("그룹 ID 가 중복되었습니다.");
                } else {
                    codeManageService.insertCodeGrp(codeVO);
                    resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");
                }
            } else {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            }

        } else {
            // 수정
            if (!logService.checkAdminLog(commandMap, "U", "공통코드 그룹 수정")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else {
                codeManageService.updateCodeGrp(codeVO);
                resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");
            }
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 공통상세코드를 삭제한다.
     *
     * @PathVariable codeId
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/code/parentDelete")
    @ResponseBody
    public ModelAndView parentDelete(@ModelAttribute("codeVO") CodeVO codeVO,
            CommandMap commandMap, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        // 삭제
        if (!logService.checkAdminLog(commandMap, "U", "공통상세코드 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {

            codeVO.setComcd(Config.COM_CD);
            CodeVO vo = codeManageService.selectCodeGrpDetail(codeVO);

            if (vo == null) {
                resultInfo = HttpUtility.getErrorResultInfo("공통코드 정보가 없습니다.");
            } else {
                // 자식 코드 있는지 검사
                List<?> list = null;
                // 기관별 코드 구분
                if (vo.getOrgGrpcdyn().equals("Y")) {
                    list = codeManageService.selectOrgCodeDetailList(codeVO);
                } else {
                    list = codeManageService.selectCodeDetailList(codeVO);
                }

                if (list != null && list.size() > 0) {
                    resultInfo = HttpUtility.getErrorResultInfo("코드가 등록된 그룹코드는 삭제불가합니다!");
                } else {
                    codeManageService.deleteCodeGrp(codeVO);
                    resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
                }
            }
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 공통상세코드 목록을 조회한다.
     *
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return string
     * @throws Exception
     */
    @PageActionInfo(title = "공통코드관리 목록", action = "R")
    @GetMapping(value = "/code/childList")
    public String selectCmmnDetailCodeList(@ModelAttribute("searchVO") CodeVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        // paginationInfo.setPageSize(99999);
        // paginationInfo.setRecordCountPerPage(99999);

        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setComcd(Config.COM_CD);
        searchVO.setOrgGrpcdyn("N");

        int totCnt = codeManageService.selectCodeGrpCnt(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);

        List<?> list = codeManageService.selectCodeGrpList(searchVO);
        model.addAttribute("resultList", list);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        CodeVO grpInfo = null;
        List<?> childList = null;
        if (!StringUtil.IsEmpty(searchVO.getGrpCd())) {
            grpInfo = codeManageService.selectCodeGrpDetail(searchVO);
            childList = codeManageService.selectCodeDetailList(searchVO);
        }
        model.addAttribute("grpInfo", grpInfo);
        model.addAttribute("childList", childList);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 공통상세코드 등록을 위한 등록페이지로 이동한다.
     *
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "공통코드관리 등록", action = "R")
    @GetMapping("/code/childDetailAjax")
    public String selectCmmnDetailCodeDetail(@ModelAttribute("codeVO") CodeVO codeVO,
            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        // 부모코드 정보
        codeVO.setComcd(Config.COM_CD);
        CodeVO parentCode = codeManageService.selectCodeGrpDetail(codeVO);
        model.addAttribute("parentCode", parentCode);

        // 수정일경우 불러오기
        CodeVO result = new CodeVO();
        if (codeVO.getCd() != null && !codeVO.getCd().equals("")) {
            result = codeManageService.selectCodeDetail(codeVO);
        } else {
            result.setComcd(Config.COM_CD);
            result.setGrpCd(parentCode.getGrpCd());
            result.setGrpNm(parentCode.getGrpNm());
        }

        model.addAttribute("codeVO", result);
        model.addAttribute("commandMap", commandMap);

        if (codeVO.getGrpCd().equals("CM_REASON_DC")) {
            // 할인할증구분
            model.addAttribute("dcTypeList", codeService.selectCodeList("SM_DCPR_TYPE"));

            // 할인할증사유그룹
            model.addAttribute("dcKindList", codeService.selectCodeList("SM_DSCNTKIND_GRP"));
        }

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 공통상세코드를 등록한다.
     *
     * @PathVariable codeId
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/code/childSave")
    @ResponseBody
    public ModelAndView insertCmmnDetailCode(@ModelAttribute("codeVO") CodeVO codeVO,
            CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        codeVO.setComcd(Config.COM_CD);
        codeVO.setReguser((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

        if (commandMap.getString("MODE").equals("INSERT")) {
            // 신규 등록
            // 코드 중복체크를 한다
            CodeVO result = codeManageService.selectCodeDetail(codeVO);

            if (!logService.checkAdminLog(commandMap, "C", "공통상세코드 등록")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else if (result != null) {
                resultInfo = HttpUtility.getErrorResultInfo("코드가 중복되었습니다.");
            } else {
                codeManageService.insertCodeDetail(codeVO);
                resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");
            }
        } else {
            // 수정
            if (!logService.checkAdminLog(commandMap, "U", "공통상세코드 수정")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else {
                codeManageService.updateCodeDetail(codeVO);
                resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");
            }
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 공통상세코드를 삭제한다.
     *
     * @PathVariable codeId
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/code/childDelete")
    @ResponseBody
    public ModelAndView deleteCmmnDetailCode(@ModelAttribute("codeVO") CodeVO codeVO,
            CommandMap commandMap, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        codeVO.setComcd(Config.COM_CD);
        // 삭제
        if (!logService.checkAdminLog(commandMap, "D", "공통상세코드 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            codeManageService.deleteCodeDetail(codeVO);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 공통상세코드 순서를 일괄변경
     *
     * @PathVariable codeId
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/code/childSort")
    @ResponseBody
    public ModelAndView sortCmmnDetailCode(
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        ResultInfo resultInfo = null;
        try {

            if (!logService.checkAdminLog(commandMap, "U", "공통상세코드 수정")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else {
                if (request.getParameterValues("cd").length > 0) {
                    codeManageService.sortAllCodeDetail(commandMap, request, model);
                    resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");
                } else {
                    resultInfo = HttpUtility.getErrorResultInfo("데이터가 없습니다.");
                }
            }

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo(e.getMessage());
        } finally {
            mav.addObject("result", resultInfo);
            return mav;
        }
    }

    /**
     * 공통상세코드 사용여부 변경
     *
     * @PathVariable codeId
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/code/childUse")
    @ResponseBody
    public ModelAndView childUse(@ModelAttribute("codeVO") CodeVO codeVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        codeVO.setComcd(Config.COM_CD);
        // 삭제
        if (!logService.checkAdminLog(commandMap, "U", "공통상세코드 변경")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            codeManageService.useCodeDetail(codeVO);
            resultInfo = HttpUtility.getSuccessResultInfo("변경 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 기관별상세코드 목록을 조회한다.
     *
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return string
     * @throws Exception
     */
    @PageActionInfo(title = "기관별코드관리 목록", action = "R")
    @GetMapping(value = "/code/orgList")
    public String selectOrgDetailCodeList(@ModelAttribute("searchVO") CodeVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        // paginationInfo.setPageSize(99999);
        // paginationInfo.setRecordCountPerPage(99999);

        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setComcd(Config.COM_CD);
        searchVO.setOrgGrpcdyn("Y");

        int totCnt = codeManageService.selectCodeGrpCnt(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);

        List<?> list = codeManageService.selectCodeGrpList(searchVO);
        model.addAttribute("resultList", list);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);


        CodeVO grpInfo = null;
        List<?> orgCdList = null;
        if (!StringUtil.IsEmpty(searchVO.getGrpCd())) {
            grpInfo = codeManageService.selectCodeGrpDetail(searchVO);
            orgCdList = codeManageService.selectOrgCodeDetailList(searchVO);
        }
        model.addAttribute("grpInfo", grpInfo);
        model.addAttribute("orgCdList", orgCdList);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 공통상세코드 등록을 위한 등록페이지로 이동한다.
     *
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "공통코드관리 등록", action = "R")
    @GetMapping("/code/orgDetailAjax")
    public String selectOrgDetailCodeDetail(@ModelAttribute("codeVO") CodeVO codeVO,
            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        // 부모코드 정보
        codeVO.setComcd(Config.COM_CD);
        CodeVO parentCode = codeManageService.selectCodeGrpDetail(codeVO);
        model.addAttribute("parentCode", parentCode);

        // 내용 셀렉트
        OrgInfoVO orgInfoVO = new OrgInfoVO();
        orgInfoVO.setComcd(Config.COM_CD);
        orgInfoVO.setLevel("1");
        orgInfoVO.setOrgNo(codeVO.getOrgNo());
        orgInfoVO = orgInfoService.selectOrgInfoDetail(orgInfoVO);
        model.addAttribute("orgInfoVO", orgInfoVO);

        // 수정일경우 불러오기
        CodeVO result = new CodeVO();

        if (codeVO.getCd() != null && !codeVO.getCd().equals("")) {
            result = codeManageService.selectOrgCodeDetail(codeVO);
        } else {
            result.setComcd(Config.COM_CD);
            result.setGrpCd(parentCode.getGrpCd());
            result.setGrpNm(parentCode.getGrpNm());
            result.setOrgNo(orgInfoVO.getOrgNo());
        }

        model.addAttribute("codeVO", result);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 공통상세코드를 등록한다.
     *
     * @PathVariable codeId
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/code/orgSave")
    @ResponseBody
    public ModelAndView insertOrgDetailCode(@ModelAttribute("codeVO") CodeVO codeVO,
            CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        codeVO.setComcd(Config.COM_CD);
        codeVO.setReguser((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

        if (commandMap.getString("MODE").equals("INSERT")) {
            // 신규 등록
            // 코드 중복체크를 한다
            CodeVO result = codeManageService.selectOrgCodeDetail(codeVO);

            if (!logService.checkAdminLog(commandMap, "C", "공통상세코드 등록")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else if (result != null) {
                resultInfo = HttpUtility.getErrorResultInfo("코드가 중복되었습니다.");
            } else {
                codeManageService.insertOrgCodeDetail(codeVO);
                resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");
            }
        } else {
            // 수정
            if (!logService.checkAdminLog(commandMap, "U", "공통상세코드 수정")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else {
                codeManageService.updateOrgCodeDetail(codeVO);
                resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");
            }
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 공통상세코드를 삭제한다.
     *
     * @PathVariable codeId
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/code/orgDelete")
    @ResponseBody
    public ModelAndView deleteOrgDetailCode(@ModelAttribute("codeVO") CodeVO codeVO,
            CommandMap commandMap, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        codeVO.setComcd(Config.COM_CD);
        // 삭제
        if (!logService.checkAdminLog(commandMap, "U", "공통상세코드 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            codeManageService.deleteOrgCodeDetail(codeVO);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 공통상세코드 순서를 일괄변경
     *
     * @PathVariable codeId
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/code/orgSort")
    @ResponseBody
    public ModelAndView sortOrgDetailCode(
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        ResultInfo resultInfo = null;
        try {

            if (!logService.checkAdminLog(commandMap, "U", "기관별코드코드 수정")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else {
                if (request.getParameterValues("cd").length > 0) {
                    codeManageService.sortAllOrgCodeDetail(commandMap, request, model);
                    resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");
                } else {
                    resultInfo = HttpUtility.getErrorResultInfo("데이터가 없습니다.");
                }
            }

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo(e.getMessage());
        } finally {
            mav.addObject("result", resultInfo);
            return mav;
        }
    }

    /**
     * 기관별코드 사용여부 변경
     *
     * @PathVariable codeId
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/code/orgUse")
    @ResponseBody
    public ModelAndView orgUse(@ModelAttribute("codeVO") CodeVO codeVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        codeVO.setComcd(Config.COM_CD);
        // 삭제
        if (!logService.checkAdminLog(commandMap, "U", "공통상세코드 변경")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            codeManageService.useOrgCodeDetail(codeVO);
            resultInfo = HttpUtility.getSuccessResultInfo("변경 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 코드 목록을 json 형태로 반환
     *
     * @param codeGrpCd
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping(value = "/code/{codeGrpCd}/list.json")
    @ResponseBody
    public ModelAndView codeList(@PathVariable String codeGrpCd, CommandMap commandMap,
            HttpServletResponse response, HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("list", codeService.selectCodeList(codeGrpCd));
        return mav;

    }

    /**
     * 기관멸 코드 목록을 json 형태로 반환
     *
     * @param codeGrpCd
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping(value = "/code/{codeGrpCd}/orgList.json")
    @ResponseBody
    public ModelAndView codeListByOrgno(@PathVariable String codeGrpCd, CommandMap commandMap,
            HttpServletResponse response, HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        CodeVO vo = new CodeVO();
        vo.setGrpCd(codeGrpCd);
        vo.setOrgNo(StringUtil.String2Int(commandMap.getString("orgNo"), 0));
        vo.setUseYn("Y");

        mav.addObject("list", codeManageService.selectOrgCodeDetailList(vo));
        return mav;

    }

    /**
     * 공통코드를 삭제한다.
     *
     * @param cmmnCodeVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    /*
     * @PostMapping("/sym/ccm/cca/RemoveCcmCmmnCode.do")
     * public String setDeleteCmmnCode(@ModelAttribute("cmmnCodeVO") CmmnCodeVO cmmnCodeVO,
     * BindingResult bindingResult, ModelMap model) throws Exception {
     * LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
     * cmmnCodeVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
     * cmmnCodeManageService.deleteCmmnCode(cmmnCodeVO);
     * return "forward:/sym/ccm/cca/SelectCcmCmmnCodeList.do";
     * }
     */
}
