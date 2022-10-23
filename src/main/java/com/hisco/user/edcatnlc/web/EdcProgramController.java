package com.hisco.user.edcatnlc.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hisco.admin.area.service.AreaCdService;
import com.hisco.admin.area.vo.AreaCdVO;
import com.hisco.admin.comctgr.service.ComCtgrService;
import com.hisco.admin.comctgr.vo.ComCtgrVO;
import com.hisco.admin.twedu.service.TweduService;
import com.hisco.admin.twedu.vo.TweduVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.Utils;
import com.hisco.extend.AbstractController;
import com.hisco.intrfc.charge.service.ChargeService;
import com.hisco.user.edcatnlc.service.EdcProgramService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;
import com.hisco.user.mypage.service.MyInforService;
import com.hisco.user.mypage.service.RsvnCommService;

import egovframework.com.cmm.LoginVO;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 교육프로그램 목록/상세 조회
 *
 * @Class Name : EducationController.java
 * @Description : 자세한 클래스 설명
 * @author woojinp@legitsys.co.kr
 * @since 2021. 11. 11.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */

@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/edc/program")
public class EdcProgramController extends AbstractController{

    @Resource(name = "chargeService")
    private ChargeService chargeService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    @Resource(name = "rsvnCommService")
    private RsvnCommService rsvnCommService;

    @Resource(name = "myInforService")
    private MyInforService myInforService;

    // woojinp added
    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "comCtgrService")
    private ComCtgrService comCtgrService;

    @Resource(name = "areaCdService")
    private AreaCdService areaCdService;

    @Resource(name = "tweduService")
    private TweduService tweduService;

    /**
     * 교육 예약 목록 리스트 조회
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = { "/list", "/hurry" })
    public String programList(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        // 접수방식
        model.addAttribute("receptType", codeService.selectCodeList("SM_LERECPTYPE"));
        model.addAttribute("commandMap", commandMap);

        // 지역
        List<AreaCdVO> areaList = (List<AreaCdVO>) areaCdService.selectAreaCdList(new AreaCdVO());
        model.addAttribute("areaList", areaList);

        // 분야 1depth 인 것만 선택
        ComCtgrVO ctgParam = new ComCtgrVO();
        ctgParam.setComcd(Config.COM_CD);
        ctgParam.setUseYn("Y");
        log.error("comCtgrService = {}", comCtgrService);
        List<ComCtgrVO> cateList = (List<ComCtgrVO>) comCtgrService.selectComctgrListForTree(ctgParam);

        // 지역
        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));
        // 프로그램유형
        model.addAttribute("programType", codeService.selectCodeList("SM_EDCPROGM_TYPE"));
        // 접수방식
        model.addAttribute("receptType", codeService.selectCodeList("SM_LERECPTYPE"));
        // 교육대상        
        model.addAttribute("targetType", codeService.selectCodeList("CM_AGEGBN"));
        //접수기간운영방법
        model.addAttribute("perodType", codeService.selectCodeList("SM_RSVN_PEROD_TYPE"));
        // 교육대상
        model.addAttribute("targetType", codeService.selectCodeList("CM_AGEGBN"));

        
        
        String retrul = request.getRequestURI();
        if (retrul.indexOf("hurry") > 0) {
            // 마감임박 만 조회 (7일)
            model.addAttribute("ajaxUrl", "./hurryAjax");
        } else {
            model.addAttribute("ajaxUrl", "./listAjax");
        }

        model.addAttribute("cateList", cateList);
        model.addAttribute("userInfo", commandMap.getUserInfo());

        return Config.USER_ROOT + "/edc/program/programList";
    }

    
    @RequestMapping(value = { "/lecturelist" })
    public String programLectureList(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        // 접수방식
        model.addAttribute("receptType", codeService.selectCodeList("SM_LERECPTYPE"));
        model.addAttribute("commandMap", commandMap);

        // 지역
        List<AreaCdVO> areaList = (List<AreaCdVO>) areaCdService.selectAreaCdList(new AreaCdVO());
        model.addAttribute("areaList", areaList);

        // 분야 1depth 인 것만 선택
        ComCtgrVO ctgParam = new ComCtgrVO();
        ctgParam.setComcd(Config.COM_CD);
        ctgParam.setUseYn("Y");
        log.error("comCtgrService = {}", comCtgrService);
        List<ComCtgrVO> cateList = (List<ComCtgrVO>) comCtgrService.selectComctgrListForTree(ctgParam);

        String retrul = request.getRequestURI();

        model.addAttribute("ajaxUrl", "./listAjax");

        model.addAttribute("cateList", cateList);
        model.addAttribute("userInfo", commandMap.getUserInfo());

        return Config.USER_ROOT + "/edc/program/programLectureList";
    }    
    
    @RequestMapping(value = { "/listAjax", "/hurryAjax" })
    public String programListAjax(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        LoginVO loginVO = commandMap.getUserInfo();

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setRecordCountPerPage(8);
        searchVO.setPaginationInfo(paginationInfo); // 페이징 파라미터 추가
        searchVO.setEdcOpenyn("Y"); // 공개된 것만

        String retrul = request.getRequestURI();
        if (retrul.indexOf("hurry") > 0) {
            // 마감임박 만 조회 (7일)
            searchVO.setSearchHurry("Y");
        }

        if (loginVO != null && loginVO.isMember())
            searchVO.setMemNo(loginVO.getUniqId());

        List<?> programList = edcProgramService.selectProgramList(searchVO);

        int totCount = 0;
        if (programList != null && programList.size() >= 1) {
            totCount = ((EdcProgramVO) programList.get(0)).getTotCount();
        }

        paginationInfo.setTotalRecordCount(totCount);

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("programList", programList);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("userInfo", commandMap.getUserInfo());
        model.addAttribute("totCount", totCount);

        return HttpUtility.getViewUrl(request);
    }

    
    
    @RequestMapping(value = "/list2Ajax", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
    @ResponseBody
    public String edcProgramListAjax(
    		  @RequestParam HashMap<String, Object> req
    		, @ModelAttribute("searchVO") EdcProgramVO searchVO
    		, CommandMap commandMap
    		, HttpServletRequest request
    		, ModelMap model
    		) throws Exception {


    	/*Client Parameters*/
    	HashMap<String, Object> params = new HashMap<String, Object>(); 
    	params.putAll(req);  
    	params.put("comcd", searchVO.getComcd());
    	
    	/*목록페이징처리 부분*/
    	Integer RecordCount = searchVO.getRecordCountPerPage();
    	Integer pageIndex = Integer.parseInt(params.get("pageIndex").toString());
    	params.put("firstIndex", ((pageIndex-1)*RecordCount));
    	
    	params.put("recordCountPerPage", searchVO.getRecordCountPerPage());
    	//System.out.println("searchVO.getRecordCountPerPage()==="+searchVO.getRecordCountPerPage());
    	List<HashMap<String, Object>> list = edcProgramService.getProgramList(params);

		Integer TotalRecordCount = 0;
		if(list != null && list.size()>0) {
			HashMap<String, Object> item = (HashMap<String, Object>)list.get(0);
			TotalRecordCount = Integer.parseInt(item.get("TOT_COUNT").toString());
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
		System.out.println("jsonData==="+jsonData);
		return jsonData;
    }
    
    
    @RequestMapping(value = "/listMain2Ajax", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
    @ResponseBody
    public String edcProgramListMainAjax(
    		  @RequestParam HashMap<String, Object> req
    		, @ModelAttribute("searchVO") EdcProgramVO searchVO
    		, CommandMap commandMap
    		, HttpServletRequest request
    		, ModelMap model
    		) throws Exception {


    	/*Client Parameters*/
    	HashMap<String, Object> params = new HashMap<String, Object>(); 
    	params.putAll(req);  
    	params.put("comcd", searchVO.getComcd());
    	
    	/*목록페이징처리 부분*/
    	//Integer RecordCount = searchVO.getRecordCountPerPage();
    	//Integer pageIndex = Integer.parseInt(params.get("pageIndex").toString());
    	//params.put("firstIndex", ((pageIndex-1)*RecordCount));
    	
    	params.put("recordCountPerPage", searchVO.getRecordCountPerPage());
    	//System.out.println("searchVO.getRecordCountPerPage()==="+searchVO.getRecordCountPerPage());
    	List<HashMap<String, Object>> list = edcProgramService.getProgramList(params);

		Integer TotalRecordCount = 0;
		if(list != null && list.size()>0) {
			HashMap<String, Object> item = (HashMap<String, Object>)list.get(0);
			TotalRecordCount = Integer.parseInt(item.get("TOT_COUNT").toString());
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
		System.out.println("jsonData==="+jsonData);
		return jsonData;
    }
    
    
    
    
    /**
     * 교육프로그램 상세
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/{edcPrgmNo}")
    public String programDetail(@PathVariable("edcPrgmNo") int edcPrgmNo,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model, Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        LoginVO loginVO = commandMap.getUserInfo();
        EdcProgramVO param = new EdcProgramVO();
        param.setEdcPrgmNo(edcPrgmNo);

        String edcRsvnsetSeq = edcProgramService.selectProgramRsvnSetMax(param);
        param.setEdcRsvnsetSeq(Integer.parseInt(edcRsvnsetSeq));
        param.setProgramNo(Config.EDC_PROGRAM_NO_PREFIX);
        param.setEdcOpenyn("Y"); // 공개된 것만

        if (loginVO != null && loginVO.isMember())
            param.setMemNo(loginVO.getUniqId());

        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(param);

        if (detailVO == null)
            HttpUtility.sendRedirect(request, response, "유효하지 않는 강좌입니다.", Config.USER_ROOT + "/edc/program/list");

        model.addAttribute("detailVO", detailVO);
        model.addAttribute("noticeVO", edcProgramService.selectProgramNotice(param));
        model.addAttribute("ageList", edcProgramService.selectTargetAgeList(param)); // 나이 제한
        model.addAttribute("userInfo", commandMap.getUserInfo());

        if (Constant.SM_EDCPROGM_TYPE_마을배움터.equals(detailVO.getEdcProgmType())) {
            // 마을 배움터
            TweduVO townVO = new TweduVO();
            townVO.setComcd(param.getComcd());
            townVO.setEdcPrgmNo(param.getEdcPrgmNo());
            model.addAttribute("planList", tweduService.selectPlans(townVO));
        }

        // setGenderAndAge(model, commandMap);

        return Config.USER_ROOT + "/edc/program/programDetail";
    }

}