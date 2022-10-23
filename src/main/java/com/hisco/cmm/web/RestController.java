package com.hisco.cmm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hisco.admin.area.service.AreaCdService;
import com.hisco.admin.comctgr.service.ComCtgrService;
import com.hisco.admin.eduadm.vo.EdcProgramVO;
import com.hisco.admin.log.service.LogService;
import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.Utils;
import com.hisco.extend.AbstractController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RestController extends AbstractController{
	/*공통코드 서비스*/
    @Resource(name = "codeService")
    private CodeService codeService;
    /*로그 서비스*/
    @Resource(name = "logService")
    private LogService logService;
    /*카테고리(분야) 서비스*/
    @Resource(name = "comCtgrService")
    private ComCtgrService comCtgrService;
    /*지역 서비스*/
    @Resource(name = "areaCdService")
    private AreaCdService areaCdService;
    /*기관정보 서비스*/
    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;
    
    /**
     * 카테고리(분야) 목록 조회
     * @param req
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PageActionInfo(title = "카테고리(분야) 목록 조회", action = "R")
    @RequestMapping(value = "/web/rest/getComCtgrAjax", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
    public String getComCtgrAjax(
  		  @RequestParam HashMap<String
  		, Object> req
  		, HttpServletRequest request
  		, ModelMap model
  		) throws Exception {
    	
    	
		//String jsonData = MakeJsonData(request, ReturnMap);
    	String jsonData = ""; 
		return jsonData;
    }
    
    /**
     * 공통코드 조회
     * @param req
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PageActionInfo(title = "코드조회 목록 조회", action = "R")
    @RequestMapping(value = "/web/rest/getCodeAjax", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
    public String getCodeAjax(
  		  @RequestParam HashMap<String
  		, Object> req
  		, HttpServletRequest request
  		, ModelMap model
  		) throws Exception {
    	
    	//SM_EDCPROGM_TYPE		프로그램유형
    	//SM_LERECPTYPE			접수방식
    	//SM_EDC_RETCHARGE_TYPE	환불방식
    	//CM_AGEGBN				교육대상
    	//SM_RSVN_PEROD_TYPE	접수기간운영방법

		//String jsonData = MakeJsonData(request, ReturnMap);
    	String jsonData = ""; 
		return jsonData;
    }
    
    /**
     * 기관정보 목록 조회
     * @param req
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PageActionInfo(title = "기관정보 목록 조회", action = "R")
    @RequestMapping(value = "/web/rest/getOrgInfoAjax", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
    public String getOrgInfoAjax(
  		  @RequestParam HashMap<String
  		, Object> req
  		, HttpServletRequest request
  		, ModelMap model
  		) throws Exception {
    	
    	
    	//List<?> orgList = 
		//String jsonData = MakeJsonData(request, ReturnMap);
    	String jsonData = ""; 
		return jsonData;
    }
    
    /**
     * 지역코드 목록 조회
     * @param req
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PageActionInfo(title = "지역코드 목록 조회", action = "R")
    @RequestMapping(value = "/web/rest/getAreaInfoAjax", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
    public String getAreaInfoAjax(
  		  @RequestParam HashMap<String
  		, Object> req
  		, HttpServletRequest request
  		, ModelMap model
  		) throws Exception {
    	
    	Map<String, Object> ReturnMap = new HashMap<String, Object>();
    	int TotalRecordCount = 0;
		ReturnMap.put("result", true);

    	List<HashMap<String, Object>> areaList = (List<HashMap<String, Object>>) areaCdService.selectAreaCdList(null);
    	if(areaList.size()>0)
    	{
    		TotalRecordCount = areaList.size();
    	}
		ReturnMap.put("TotalRecordCount", TotalRecordCount);
		//ReturnMap.put("data", Utils.keyChangeLower(areaList));
		ReturnMap.put("data", areaList);
		String jsonData = MakeJsonData(request, ReturnMap);
		return jsonData;
    }
}
