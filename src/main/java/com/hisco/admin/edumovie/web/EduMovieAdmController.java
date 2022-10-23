package com.hisco.admin.edumovie.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.area.service.AreaCdService;
import com.hisco.admin.comctgr.service.ComCtgrService;
import com.hisco.admin.comctgr.vo.ComCtgrVO;
import com.hisco.admin.edumovie.service.EduMovieAdmService;
import com.hisco.admin.edumovie.vo.EduMovieVO;
import com.hisco.admin.log.service.LogService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.config.DynamicConfigUtil;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.Utils;
import com.hisco.cmm.vo.FileVO;
import com.hisco.extend.AbstractController;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.tpl.service.TemplateInfVO;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * 동영상강좌
 *
 * @author 김범수
 * @since 2022.10.14
 * @version 1.0, 2022.10.14
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          김범수 2022.10.14 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class EduMovieAdmController extends AbstractController{

    @Resource(name = "eduMovieAdmService")
    private EduMovieAdmService eduMovieAdmService;
    
    @Resource(name = "comCtgrService")
    private ComCtgrService comCtgrService;

    @Resource(name = "areaCdService")
    private AreaCdService areaCdService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "dynamicConfigUtil")
    private DynamicConfigUtil configUtil;

    @Resource(name = "logService")
    private LogService logService;

    @Resource(name = "EgovCmmUseService")
    private EgovCmmUseService cmmUseService;


    /**
     * 교육 프로그램 관리 목록
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "교육프로그램 목록 조회", action = "R")
    @RequestMapping("/edumovie/eduMovieList")
    public String selectGrpEnvReser(@ModelAttribute("searchVO") EduMovieVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();

        //searchVO.setEdcProgmType(Constant.SM_EDCPROGM_TYPE_일반교육); // 일반교육
        searchVO.setComcd(Config.COM_CD);
        searchVO.setPaginationInfo(paginationInfo);
        int movieList = eduMovieAdmService.selectEduMovieCount(searchVO);

        // 접수방식
        model.addAttribute("receptType", codeService.selectCodeList("SM_LERECPTYPE"));
        // 교육대상
        model.addAttribute("targetType", codeService.selectCodeList("CM_AGEGBN"));

        paginationInfo.setTotalRecordCount(movieList);

        model.addAttribute("paginationInfo", paginationInfo);
        //model.addAttribute("programList", programList);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    @ResponseBody
    @PageActionInfo(title = "교육프로그램 목록 조회", action = "R")
    @RequestMapping(value = "/edumovie/eduMovieListAjax", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
    public String eduMovieListAjax(
    		  @RequestParam HashMap<String, Object> req
    		, @ModelAttribute("searchVO") EduMovieVO searchVO
    		, CommandMap commandMap
    		, HttpServletRequest request
    		, ModelMap model
    		) throws Exception {

        //PaginationInfo paginationInfo = commandMap.getPagingInfo();

        //searchVO.setEdcProgmType(Constant.SM_EDCPROGM_TYPE_일반교육); // 일반교육
        //searchVO.setComcd(Config.COM_CD);
        //searchVO.setPaginationInfo(paginationInfo);
        //List<?> programList = eduAdmService.selectEdcProgramList(searchVO);

        //int totCount = 0;
        //if (programList != null && programList.size() >= 1) {
        //   totCount = ((EdcProgramVO) programList.get(0)).getTotCount();
        //}

        // 접수방식
        //model.addAttribute("receptType", codeService.selectCodeList("SM_LERECPTYPE"));

        // 교육대상
        //model.addAttribute("targetType", codeService.selectCodeList("CM_AGEGBN"));

        //paginationInfo.setTotalRecordCount(totCount);

        //model.addAttribute("paginationInfo", paginationInfo);
        //model.addAttribute("programList", programList);
        //model.addAttribute("commandMap", commandMap);

    	/*Client Parameters*/
    	HashMap<String, Object> params = new HashMap<String, Object>(); 
    	params.putAll(req);  
    	params.put("comcd", searchVO.getComcd());
    	
    	/*목록페이징처리 부분*/
    	Integer RecordCount = searchVO.getRecordCountPerPage();
    	Integer pageIndex = 1;//Integer.parseInt(params.get("pageIndex").toString());
    	params.put("firstIndex", ((pageIndex-1)*RecordCount));
    	
    	
    	params.put("recordCountPerPage", searchVO.getRecordCountPerPage());
    	
    	List<HashMap<String, Object>> list = eduMovieAdmService.selectEduMovieList(searchVO);
		Integer TotalRecordCount = eduMovieAdmService.selectEduMovieCount(searchVO);
		TotalRecordCount = list.size();
		
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
    
    /**
     * 교육 프로그램 등록 화면
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping(value = { "/edumovie/eduMovieRegist", "/edumovie/eduMovieUpdt" })
    public String selectEdcProgramRegist(@ModelAttribute("eduMovieVO") EduMovieVO eduMovieVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        // 지역
        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));
        // 프로그램유형
        model.addAttribute("programType", codeService.selectCodeList("SM_EDCPROGM_TYPE"));

        eduMovieVO.setComcd(Config.COM_CD);

        // 복사 할때
        if (eduMovieVO.getOrgMovieLecNo() > 0) {
        	List<HashMap<String, Object>> list = eduMovieAdmService.selectEduMovieList(eduMovieVO);
        	model.addAttribute("eduMovieVO", list.get(0));
        } else {
        	model.addAttribute("eduMovieVO", eduMovieVO);
        }
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("EduMovieVO", eduMovieVO);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/edumovie/eduMovieRegist");
    }

	@PageActionInfo(title = "프로그램 정보 등록", action = "C")
    @PostMapping(value = { "/edumovie/eduMovieRegistSave" })
    public String insertEduMovieRegist(@ModelAttribute("eduMovieVO") EduMovieVO eduMovieVO,
            CommandMap commandMap, final MultipartHttpServletRequest multiRequest, HttpServletResponse response,
            ModelMap model) throws Exception {

        try {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            String edcFileid = "";
            String edcPrgmImage = "";
            String edcPlanFileid = "";

            // 파일 업로드
            final Map<String, MultipartFile> files = multiRequest.getFileMap();

            int result = eduMovieAdmService.insertEduMovie(eduMovieVO, user, files);
            
            if(result>0) {
            	HttpUtility.sendRedirect(multiRequest, response, "처리되었습니다.", configUtil.getAdminDynamicPath(multiRequest, Config.ADMIN_ROOT + "/edumovie/eduMovieList") + commandMap.getString("searchQuery"));
            } else {
            	HttpUtility.getErrorResultInfo("처리 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }
            return null;
        } catch (Exception e) {

            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("commandMap", commandMap);
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/edumovie/eduMovieRegist");
        }

    }

    /**
     * 기본정보 수정
     *
     * @param edcProgramVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = {"/edumovie/eduMovieUpdtSave"})
    @PageActionInfo(title = "프로그램 기본 정보 수정", action = "U")
    public String eduMovieUpdtSave(@ModelAttribute("eduMovieVO") EduMovieVO eduMovieVO,
    		final MultipartHttpServletRequest multiRequest, CommandMap commandMap, HttpServletResponse response, ModelMap model) throws Exception {
    	try {
	        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
	        eduMovieVO.setModuser(user.getId());
	        
	        // 파일 업로드
	        final Map<String, MultipartFile> files = multiRequest.getFileMap();
	
	        int result = eduMovieAdmService.updateEduMovie(eduMovieVO, files, user);
	        
	        if(result>0) {
	        	HttpUtility.sendRedirect(multiRequest, response, "처리되었습니다.", configUtil.getAdminDynamicPath(multiRequest, Config.ADMIN_ROOT + "/edumovie/eduMovieList") + commandMap.getString("searchQuery"));
	        } else {
	        	HttpUtility.getErrorResultInfo("처리 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
	        }
	        return null;
	    } catch (Exception e) {
	
	        model.addAttribute("errorMsg", e.getMessage());
	        model.addAttribute("commandMap", commandMap);
	        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/edumovie/eduMovieUpdt?orgMovieLecNo="+eduMovieVO.getOrgMovieLecNo());
	    }
    }

    
  /**
   * 외부기관 프로그램 모집정보 수정
   *
   * @param edcProgramVO
   * @param model
   * @return
   * @throws Exception
   */
  @PostMapping(value = {"/edumovie/eduMovieDelete"})
  @PageActionInfo(title = "프로그램 모집정보  삭제", action = "D")
  public String eduMovieDelete(@ModelAttribute("eduMovieVO") EduMovieVO eduMovieVO,
  		final MultipartHttpServletRequest multiRequest, CommandMap commandMap, HttpServletResponse response, ModelMap model) throws Exception {
	  try {
	        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
	        eduMovieVO.setModuser(user.getId());
	        
	        // 파일 업로드
	        final Map<String, MultipartFile> files = multiRequest.getFileMap();
	
	        int result = eduMovieAdmService.deleteEduMovie(eduMovieVO, files);
	        
	        if(result>0) {
	        	HttpUtility.sendRedirect(multiRequest, response, "처리되었습니다.", configUtil.getAdminDynamicPath(multiRequest, Config.ADMIN_ROOT + "/edumovie/eduMovieList") + commandMap.getString("searchQuery"));
	        } else {
	        	HttpUtility.getErrorResultInfo("처리 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
	        }
	        return null;
	    } catch (Exception e) {
	
	        model.addAttribute("errorMsg", e.getMessage());
	        model.addAttribute("commandMap", commandMap);
	        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/edumovie/eduMovieUpdt?orgMovieLecNo="+eduMovieVO.getOrgMovieLecNo());
	    }
  }

  /**
   * 모집차수명 목록
   *
   * @param edcProgramVO
   * @param model
   * @return
   * @throws Exception
   */
  @GetMapping(value = {"/edumovie/eduRsvnsetName.json"})
  @ResponseBody
  public ModelAndView eduRsvnsetName(@ModelAttribute("eduMovieVO") EduMovieVO eduMovieVO,
          HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

      ModelAndView mav = new ModelAndView("jsonView");
      mav.addObject("list", eduMovieAdmService.selectRsvnsetNameList(eduMovieVO));
      return mav;
  }

  /**
   * 템플릿에 대한 상세정보를 조회한다.
   *
   * @param eduMovieUserAjax
   * @param model
   * @return
   * @throws Exception
   */
  @GetMapping("/edumovie/eduMovieUserAjax")
  public String selectEduMovieUserAjax(@ModelAttribute("eduMovieVO") EduMovieVO eduMovieVO,
          HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

      ComDefaultCodeVO vo = new ComDefaultCodeVO();
      vo.setCodeId("COM005");

      List<?> result = cmmUseService.selectCmmCodeDetail(vo);

      List<EduMovieVO> dataVO = eduMovieAdmService.selectEduMovieUserAjax(eduMovieVO);
      model.addAttribute("eduMovieList", dataVO);

      model.addAttribute("resultList", result);
      model.addAttribute("commandMap", commandMap);

      return HttpUtility.getViewUrl(request);
  }
  
//
//    @PageActionInfo(title = "교육프로그램 파일 수정", action = "U", ajax = true)
//    @PostMapping(value = { "/eduadm/edcProgramUpload.json", "/eduadm/extProgramUpload.json" })
//    @ResponseBody
//    public ModelAndView updateFileUpload(CommandMap commandMap,
//            final MultipartHttpServletRequest multiRequest,
//            HttpServletResponse response,
//            ModelMap model)
//            throws Exception {
//
//        final Map<String, MultipartFile> files = multiRequest.getFileMap();
//        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
//
//        ModelAndView mav = new ModelAndView("jsonView");
//        ResultInfo resultInfo = new ResultInfo();
//
//        String uploadFolder = "";
//        String originFileName = "";
//        String realFilePath = "";
//        String atchFileId = "";
//
//        if (!files.isEmpty()) {
//            try {
//                // 기존 파일 Id
//                atchFileId = commandMap.getString("atchFileId");
//                List<FileVO> result = fileUtil.parseFileInf(files, "EDC_", 0, atchFileId, uploadFolder, user.getId());
//                Iterator<FileVO> iter = result.iterator();
//
//                if (!atchFileId.equals("")) {
//                    // 기존 파일 삭제
//                    fileMngService.deleteAndInsert(result.get(0), result);
//                } else {
//                    atchFileId = fileMngService.insertFileInfs(result);
//                }
//
//                eduAdmService.updateEdcProgramFileid(commandMap.getString("comcd"), Integer.parseInt(commandMap.getString("edcPrgmNo")), commandMap.getString("inputid"), atchFileId, user.getId());
//
//                while (iter.hasNext()) {
//                    FileVO vo = iter.next();
//                    originFileName = vo.getOrginFileName();
//                    uploadFolder = vo.getFilePath();
//                    realFilePath = multiRequest.getContextPath() + Config.USER_ROOT + "/common/file/view/" + uploadFolder + vo.getFileName() + "?originName=" + java.net.URLEncoder.encode(originFileName, "UTF-8");
//                }
//
//                resultInfo = HttpUtility.getSuccessResultInfo("저장 되었습니다.");
//
//            } catch (Exception e) {
//                resultInfo = HttpUtility.getErrorResultInfo(e.getMessage());
//            }
//        }
//
//        mav.addObject("result", resultInfo);
//        mav.addObject("originFileName", originFileName);
//        mav.addObject("realFilePath", realFilePath);
//        mav.addObject("atchFileId", atchFileId);
//        return mav;
//
//    }

//    /**
//     * 프로그램 소개 수정
//     *
//     * @param edcProgramVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @PostMapping(value = { "/eduadm/edcProgramIntroSave.json", "/eduadm/extProgramIntroSave.json" })
//    @PageActionInfo(title = "프로그램 소개 정보 수정", action = "U", ajax = true)
//    @ResponseBody
//    public ModelAndView edcProgramIntroSave(@ModelAttribute("edcProgramVO") EdcProgramVO edcProgramVO,
//            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {
//
//        ModelAndView mav = new ModelAndView("jsonView");
//        ResultInfo resultInfo = null;
//
//        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
//        edcProgramVO.setModuser(user.getId());
//        edcProgramVO.setUserIp(commandMap.getIp());
//
//        eduAdmService.updateEdcProgramIntro(edcProgramVO);
//        resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");
//
//        mav.addObject("result", resultInfo);
//        return mav;
//    }
//
//    /**
//     * 프로그램 공지사항 수정
//     *
//     * @param edcProgramVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @PostMapping(value = { "/eduadm/edcProgramNoticeSave.json", "/eduadm/extProgramNoticeSave.json" })
//    @PageActionInfo(title = "프로그램 공지사항  수정", action = "U", ajax = true)
//    @ResponseBody
//    public ModelAndView edcProgramNoticeSave(@ModelAttribute("edcProgramVO") EdcProgramVO edcProgramVO,
//            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {
//
//        ModelAndView mav = new ModelAndView("jsonView");
//        ResultInfo resultInfo = null;
//
//        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
//
//        EdcNoticeVO noticeVO = edcProgramVO.getNoticeVO();
//        noticeVO.setComcd(edcProgramVO.getComcd());
//        noticeVO.setEdcPrgmNo(edcProgramVO.getEdcPrgmNo());
//
//        eduAdmService.updateEdcProgramNotice(noticeVO);
//        resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");
//
//        mav.addObject("result", resultInfo);
//        return mav;
//    }
//
//    /**
//     * 프로그램 공지사항 수정
//     *
//     * @param edcProgramVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/eduadm/edcProgramTargetSave.json")
//    @PageActionInfo(title = "프로그램 신청제한  수정", action = "U", ajax = true)
//    @ResponseBody
//    public ModelAndView edcProgramTargetSave(@ModelAttribute("edcProgramVO") EdcProgramVO edcProgramVO,
//            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {
//
//        ModelAndView mav = new ModelAndView("jsonView");
//        ResultInfo resultInfo = null;
//
//        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
//        edcProgramVO.setModuser(user.getId());
//
//        String[] targetAgenm = request.getParameterValues("targetAgenm");
//        String[] targetSage = request.getParameterValues("targetSage");
//        String[] targetEage = request.getParameterValues("targetEage");
//        String[] targetAgeSeq = request.getParameterValues("targetAgeSeq");
//
//        if ("Y".equals(edcProgramVO.getEdcLimitAgeyn()) && targetAgenm != null) {
//            List<EdcTargetAgeVO> ageList = new ArrayList<EdcTargetAgeVO>();
//            for (int i = 0; i < targetAgenm.length; i++) {
//                EdcTargetAgeVO ageTarget = new EdcTargetAgeVO();
//                ageTarget.setEdcTargetAgeNm(targetAgenm[i]);
//                ageTarget.setEdcTargetSage(Integer.parseInt(targetSage[i]));
//                ageTarget.setEdcTargetEage(Integer.parseInt(targetEage[i]));
//
//                if (targetAgeSeq != null && targetAgeSeq.length > i) {
//                    ageTarget.setEdcAgeTargetSeq(Integer.parseInt(targetAgeSeq[i]));
//                }
//
//                ageList.add(ageTarget);
//            }
//
//            edcProgramVO.setEdcAgeList(ageList);
//        }
//
//        eduAdmService.updateEdcProgramTarget(edcProgramVO, targetAgeSeq);
//        resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");
//
//        mav.addObject("result", resultInfo);
//        return mav;
//    }
//
//    /**
//     * 프로그램 모집정보 수정
//     *
//     * @param edcProgramVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @PostMapping(  value= {"/eduadm/edcProgramRsvnSave.json" , "/eduadm/extProgramRsvnSave.json"})
//    @PageActionInfo(title = "프로그램 모집정보  수정", action = "U", ajax = true)
//    @ResponseBody
//    public ModelAndView edcProgramRsvnSave(@ModelAttribute("edcProgramVO") EdcProgramVO edcProgramVO,
//            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {
//
//        ModelAndView mav = new ModelAndView("jsonView");
//        ResultInfo resultInfo = null;
//
//        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
//
//        edcProgramVO.setModuser(user.getId());
//        edcProgramVO.setReguser(user.getId());
//
//        String msg = eduAdmService.updateEdcProgramRsvn(edcProgramVO);
//        resultInfo = HttpUtility.getSuccessResultInfo(msg);
//
//        mav.addObject("result", resultInfo);
//        return mav;
//    }
//
//    /**
//     * 프로그램 상태 수정
//     *
//     * @param edcProgramVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @PostMapping(value = { "/eduadm/extProgramStatusChange.json", "/eduadm/edcProgramStatusChange.json" })
//    @PageActionInfo(title = "프로그램 상태  수정", action = "U", ajax = true)
//    @ResponseBody
//    public ModelAndView edcProgramStatusChange(HttpServletRequest request, CommandMap commandMap, ModelMap model)
//            throws Exception {
//
//        ModelAndView mav = new ModelAndView("jsonView");
//        ResultInfo resultInfo = null;
//
//        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
//
//        eduAdmService.updateProgramStatus(Config.COM_CD, Integer.parseInt(commandMap.getString("prgmId")), commandMap.getString("type"), commandMap.getString("val"), user.getId());
//        resultInfo = HttpUtility.getSuccessResultInfo("변경완료");
//
//        mav.addObject("result", resultInfo);
//        return mav;
//    }
//
//    /**
//     * 교육 프로그램 모집정보 상세
//     *
//     * @param searchVO
//     * @param request
//     * @return
//     * @exception Exception
//     */
//    @GetMapping( value = {"/eduadm/edcProgramDetailAjax" , "/eduadm/extProgramDetailAjax"})
//    public String edcProgramDetailAjax(@ModelAttribute("edcProgramVO") EdcProgramVO edcProgramVO, CommandMap commandMap,
//            HttpServletRequest request, ModelMap model) throws Exception {
//
//        // 프로그램 모집정보 설정이력
//        model.addAttribute("rsvnsetList", eduAdmService.selectProgramRsvnSetList(edcProgramVO));
//
//        if (edcProgramVO.getEdcRsvnsetSeq() == null || "".equals(edcProgramVO.getEdcRsvnsetSeq())) {
//            edcProgramVO.setEdcRsvnsetSeq(eduAdmService.selectProgramRsvnSetMax(edcProgramVO));
//        }
//
//        if (edcProgramVO.getEdcRsvnsetSeq() != null && !"".equals(edcProgramVO.getEdcRsvnsetSeq())) {
//            edcProgramVO = eduAdmService.selectProgramRsvnSetDetail(edcProgramVO);
//            model.addAttribute("edcProgramVO", edcProgramVO);
//        }
//        // 접수방식
//        model.addAttribute("receptType", codeService.selectCodeList("SM_LERECPTYPE"));
//        
//        //접수기간운영방법
//        model.addAttribute("perodType", codeService.selectCodeList("SM_RSVN_PEROD_TYPE"));
//        
//        
//        // 요일
//        edcProgramVO.setEdcDaysList(eduAdmService.selectProgramDays(edcProgramVO));
//
//        model.addAttribute("commandMap", commandMap);
//
//        return HttpUtility.getViewUrl(request);
//    }
//
//    /**
//     * 일괄 모집 설정
//     *
//     * @param searchVO
//     * @param request
//     * @return
//     * @exception Exception
//     */
//    @GetMapping(value = {"/eduadm/edcProgramSetAjax" , "/eduadm/extProgramSetAjax"})
//    public String edcProgramSetAjax(CommandMap commandMap,
//            HttpServletRequest request, ModelMap model) throws Exception {
//
//        // 접수방식
//        model.addAttribute("receptType", codeService.selectCodeList("SM_LERECPTYPE"));
//        //접수기간운영방법
//        model.addAttribute("perodType", codeService.selectCodeList("SM_RSVN_PEROD_TYPE"));
//        
//        model.addAttribute("dayList", eduAdmService.selectProgramDays(null)); // 요일
//        model.addAttribute("mode", commandMap.getString("mode")); // NEW , EDIT
//        model.addAttribute("commandMap", commandMap);
//        model.addAttribute("requestURI", request.getRequestURI());
//
//        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/eduadm/edcProgramSetAjax");
//    }
//
//    /**
//     * 일괄 모집정보 등록
//     *
//     * @param edcProgramVO
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    /*
//    @PostMapping(value = {"/eduadm/edcProgramSetSave.json" , "/eduadm/extProgramSetSave.json"})
//    @PageActionInfo(title = "프로그램 모집정보  일괄등록", action = "C", ajax = true)
//    @ResponseBody
//    public ModelAndView edcProgramSetSave(@ModelAttribute("edcProgramVO") EdcProgramVO edcProgramVO,
//            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {
//
//        ModelAndView mav = new ModelAndView("jsonView");
//        ResultInfo resultInfo = null;
//
//        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
//
//        edcProgramVO.setComcd(Config.COM_CD);
//        edcProgramVO.setModuser(user.getId());
//        edcProgramVO.setReguser(user.getId());
//
//        edcProgramVO.setEdcProgmType(Constant.SM_EDCPROGM_TYPE_일반교육); // 일반교육
//        edcProgramVO.setSearchUse("Y"); //사용 Y 인것만
//        edcProgramVO.setExcelyn("Y"); // 페이징 없이 모두
//        edcProgramVO.setSearchOrgNo(edcProgramVO.getTargetOrgNo());
//
//        List<EdcProgramVO> programList = eduAdmService.selectEdcProgramList(edcProgramVO);
//
//        if(programList == null || programList.size() < 1){
//        	 resultInfo = HttpUtility.getErrorResultInfo("일괄 설정 대상 프로그램이 없습니다.");
//        }else{
//        	
//
//            //String targetCtg = edcProgramVO.getTargetCtgcd();
//
//            //if (targetCtg.lastIndexOf("0000000") > 0) {
//            //    edcProgramVO.setTargetCtgcd(targetCtg.substring(0, 3));
//            //} else if (targetCtg.lastIndexOf("0000") > 0) {
//            //    edcProgramVO.setTargetCtgcd(targetCtg.substring(0, 6));
//            //}
//            
//
//        	ArrayList<String> targetList = new ArrayList<String>();
//
//        	for(EdcProgramVO listVO : programList){
//        		targetList.add(listVO.getEdcPrgmNo() +"");
//        	}
//
//            if (commandMap.getString("mode").equals("EDIT")) {
//                String[] changeColumn = request.getParameterValues("changeColumn");
//
//                EdcProgramVO newProgramVO = new EdcProgramVO();
//                //대상 프로그램 목록 셋팅
//                newProgramVO.setTargetList(targetList);
//                newProgramVO.setComcd(edcProgramVO.getComcd());
//                newProgramVO.setModuser(edcProgramVO.getModuser());
//                newProgramVO.setReguser(edcProgramVO.getReguser());
//                newProgramVO.setTargetOrgNo(edcProgramVO.getTargetOrgNo());
//                newProgramVO.setSearchRsvnsetNm(edcProgramVO.getSearchRsvnsetNm());
//                //newProgramVO.setTargetCtgcd(edcProgramVO.getTargetCtgcd());
//
//                for (int i = 0; i < changeColumn.length; i++) {
//                    if ("edcOdr".equals(changeColumn[i])) {
//                        if (edcProgramVO.getEdcOdr() > 0) {
//                            newProgramVO.setEdcOdr(edcProgramVO.getEdcOdr());
//                        }
//                    } else if ("edcRsvnsetNm".equals(changeColumn[i])) {
//
//                        newProgramVO.setEdcRsvnsetNm(edcProgramVO.getEdcRsvnsetNm());
//
//                    } else if ("CtgCd".equals(changeColumn[i])) {
//                        if (edcProgramVO.getCtgCd() != null && !edcProgramVO.getCtgCd().equals("")) {
//                            newProgramVO.setCtgCd(edcProgramVO.getCtgCd());
//                        }
//                    } else if ("edcRsvnRectype".equals(changeColumn[i])) {
//                        if (edcProgramVO.getEdcRsvnRectype() != null && !edcProgramVO.getEdcRsvnRectype().equals("")) {
//                            newProgramVO.setEdcRsvnRectype(edcProgramVO.getEdcRsvnRectype());
//                        }
//                    } else if ("edcRsvnAccssrd".equals(changeColumn[i])) {
//                        if (edcProgramVO.getEdcRsvnAccssrd() != null && !edcProgramVO.getEdcRsvnAccssrd().equals("")) {
//                            newProgramVO.setEdcRsvnAccssrd(edcProgramVO.getEdcRsvnAccssrd());
//                        }
//                    } else if ("edcRsvnSdate".equals(changeColumn[i])) {
//                        if (edcProgramVO.getEdcRsvnSdate() != null && !edcProgramVO.getEdcRsvnSdate().equals("")) {
//                            newProgramVO.setEdcRsvnSdate(edcProgramVO.getEdcRsvnSdate());
//                            newProgramVO.setEdcRsvnStimeHour(edcProgramVO.getEdcRsvnStimeHour());
//                            newProgramVO.setEdcRsvnStimeMin(edcProgramVO.getEdcRsvnStimeMin());
//                        }
//                        if (edcProgramVO.getEdcRsvnEdate() != null && !edcProgramVO.getEdcRsvnEdate().equals("")) {
//                            newProgramVO.setEdcRsvnEdate(edcProgramVO.getEdcRsvnEdate());
//                            newProgramVO.setEdcRsvnEtimeHour(edcProgramVO.getEdcRsvnEtimeHour());
//                            newProgramVO.setEdcRsvnEtimeMin(edcProgramVO.getEdcRsvnEtimeMin());
//                        }
//                    } else if ("edcSdate".equals(changeColumn[i])) {
//                        if (edcProgramVO.getEdcSdate() != null && !edcProgramVO.getEdcSdate().equals("")) {
//                            newProgramVO.setEdcSdate(edcProgramVO.getEdcSdate());
//                        }
//                        if (edcProgramVO.getEdcEdate() != null && !edcProgramVO.getEdcEdate().equals("")) {
//                            newProgramVO.setEdcEdate(edcProgramVO.getEdcEdate());
//                        }
//                    } else if ("dayChk".equals(changeColumn[i])) {
//                        List<EdcDaysVO> daysList = edcProgramVO.getEdcDaysList();
//                        if (daysList != null) {
//                            newProgramVO.setEdcDaysList(daysList);
//                        }
//                    } else if ("edcStime".equals(changeColumn[i])) {
//                        newProgramVO.setEdcStimeHour(edcProgramVO.getEdcStimeHour());
//                        newProgramVO.setEdcStimeMin(edcProgramVO.getEdcStimeMin());
//                        newProgramVO.setEdcEtimeHour(edcProgramVO.getEdcEtimeHour());
//                        newProgramVO.setEdcEtimeMin(edcProgramVO.getEdcEtimeMin());
//                    } else if ("edcPncpa".equals(changeColumn[i])) {
//                        newProgramVO.setEdcPncpa(edcProgramVO.getEdcPncpa());
//                        newProgramVO.setEdcCapaDvdyn((edcProgramVO.getEdcCapaDvdyn() == null || edcProgramVO.getEdcCapaDvdyn().equals(""))
//                                ? "N" : edcProgramVO.getEdcCapaDvdyn());
//
//                        newProgramVO.setEdcOncapa(edcProgramVO.getEdcOncapa());
//                        newProgramVO.setEdcOffcapa(edcProgramVO.getEdcOffcapa());
//                    } else if ("edcEndwaitCapa".equals(changeColumn[i])) {
//                        newProgramVO.setEdcEndwaitCapa(edcProgramVO.getEdcEndwaitCapa());
//                    } else if ("edcPaywaitGbn".equals(changeColumn[i])) {
//                    	newProgramVO.setEdcPaywaitGbn(edcProgramVO.getEdcPaywaitGbn());
//                        if ("1001".equals(edcProgramVO.getEdcPaywaitGbn())) {
//                            newProgramVO.setEdcPaywaitTime(edcProgramVO.getEdcPaywaitTime());
//                        } else if ("2001".equals(edcProgramVO.getEdcPaywaitGbn())) {
//                            newProgramVO.setEdcPaywaitDate(edcProgramVO.getEdcPaywaitDate());
//                            newProgramVO.setEdcPaywaitHour(edcProgramVO.getEdcPaywaitHour());
//                            newProgramVO.setEdcPaywaitMin(edcProgramVO.getEdcPaywaitMin());
//                        }
//                    } else if ("edcRsvnLinkurl".equals(changeColumn[i])) {
//                        newProgramVO.setEdcRsvnLinkurl(edcProgramVO.getEdcRsvnLinkurl());
//                    } else if ("drwtNtcedate".equals(changeColumn[i])) {
//                        newProgramVO.setDrwtNtcedate(edcProgramVO.getDrwtNtcedate());
//                    }
//                }
//
//                String msg = eduAdmService.updateEdcProgramOneclick(newProgramVO);
//                resultInfo = HttpUtility.getSuccessResultInfo(msg);
//            } else {
//            	//대상 프로그램 목록 셋팅
//            	edcProgramVO.setTargetList(targetList);
//
//                String msg = eduAdmService.insertEdcProgramRsvnOneclick(edcProgramVO);
//                resultInfo = HttpUtility.getSuccessResultInfo(msg);
//            }
//
//        }
//        mav.addObject("result", resultInfo);
//        return mav;
//    }
//	*/
//    /**
//     * 유관기관 교육 프로그램 관리 목록
//     *
//     * @param searchVO
//     * @param request
//     * @return
//     * @exception Exception
//     */
//    /*
//    @PageActionInfo(title = "유관기관 교육프로그램 목록 조회", action = "R")
//    @GetMapping("/eduadm/extProgramList")
//    public String selectExtProgramList(@ModelAttribute("searchVO") EdcProgramVO searchVO, CommandMap commandMap,
//            HttpServletRequest request, ModelMap model) throws Exception {
//
//        PaginationInfo paginationInfo = commandMap.getPagingInfo();
//
//        searchVO.setEdcProgmType(Constant.SM_EDCPROGM_TYPE_일반교육); // 일반교육
//        searchVO.setComcd(Config.COM_CD);
//        searchVO.setPaginationInfo(paginationInfo);
//        searchVO.setSearchOrgkind("2001"); // 유관기관만
//        searchVO.setSearchRectype("5001"); // 타기관 링크
//        List<?> programList = eduAdmService.selectEdcProgramList(searchVO);
//
//        int totCount = 0;
//        if (programList != null && programList.size() >= 1) {
//            totCount = ((EdcProgramVO) programList.get(0)).getTotCount();
//        }
//
//        // 접수방식
//        model.addAttribute("receptType", codeService.selectCodeList("SM_LERECPTYPE"));
//        //접수기간운영방법
//        model.addAttribute("perodType", codeService.selectCodeList("SM_RSVN_PEROD_TYPE"));
//        // 교육대상
//        model.addAttribute("targetType", codeService.selectCodeList("CM_AGEGBN"));
//
//        paginationInfo.setTotalRecordCount(totCount);
//
//        model.addAttribute("paginationInfo", paginationInfo);
//        model.addAttribute("programList", programList);
//        model.addAttribute("commandMap", commandMap);
//
//        if (StringUtil.IsEmpty(searchVO.getSearchStartDts())) {
//            // 오늘부터 30일
//            java.util.Date todate = new java.util.Date();
//            java.util.Date prevDate = DateUtil.DateAddDay(todate, -30);
//
//            searchVO.setSearchStartDts(DateUtil.printDatetime(prevDate, "yyyy-MM-dd"));
//            searchVO.setSearchEndDts(DateUtil.printDatetime(todate, "yyyy-MM-dd"));
//        }
//
//        return HttpUtility.getViewUrl(request);
//    }
//     */
	
//    /**
//     * 유관기관 교육 프로그램 등록 화면
//     *
//     * @param searchVO
//     * @param request
//     * @return
//     * @exception Exception
//     */
//    @GetMapping(value = { "/eduadm/extProgramRegist", "/eduadm/extProgramUpdt" })
//    public String selectEdcExtProgramRegist(@ModelAttribute("edcProgramVO") EdcProgramVO edcProgramVO,
//            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
//
//        // 지역
//        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));
//        // 프로그램유형
//        model.addAttribute("programType", codeService.selectCodeList("SM_EDCPROGM_TYPE"));
//        // 접수방식
//        model.addAttribute("receptType", codeService.selectCodeList("SM_LERECPTYPE"));
//        //접수기간운영방법
//        model.addAttribute("perodType", codeService.selectCodeList("SM_RSVN_PEROD_TYPE"));
//        // 환불방식
//        model.addAttribute("refundType", codeService.selectCodeList("SM_EDC_RETCHARGE_TYPE"));
//
//        // 교육대상
//        model.addAttribute("targetType", codeService.selectCodeList("CM_AGEGBN"));
//
//        edcProgramVO.setEdcProgmDate(DateUtil.getTodate("yyyy-MM-dd"));
//        // edcProgramVO.setEdcSdate(DateUtil.getTodate("yyyy-MM-dd"));
//        // edcProgramVO.setEdcEdate(DateUtil.getTodate("yyyy-MM-dd"));
//        // edcProgramVO.setEdcRsvnSdate(DateUtil.getTodate("yyyy-MM-dd"));
//        // edcProgramVO.setEdcRsvnEdate(DateUtil.getTodate("yyyy-MM-dd"));
//
//        edcProgramVO.setEdcProgmType(Constant.SM_EDCPROGM_TYPE_일반교육); // 일반교육
//        edcProgramVO.setComcd(Config.COM_CD);
//
//        // 복사 할때
//        if (!commandMap.getString("copyPrgmNo").equals("")) {
//            edcProgramVO.setEdcPrgmNo(Integer.parseInt(commandMap.getString("copyPrgmNo")));
//        }
//
//        if (edcProgramVO.getEdcPrgmNo() > 0) {
//
//            edcProgramVO = eduAdmService.selectEdcProgramDetail(edcProgramVO);
//            edcProgramVO.setNoticeVO(eduAdmService.selectEdcNotice(edcProgramVO));
//            edcProgramVO.setEdcAgeList(eduAdmService.selectEdcTargetAge(edcProgramVO));
//
//            ComCtgrVO ctgrVO = new ComCtgrVO();
//            ctgrVO.setComcd(edcProgramVO.getComcd());
//            ctgrVO.setCtgCd(edcProgramVO.getCtgCd());
//            ctgrVO = comCtgrService.selectComctgrDetail(ctgrVO);
//            if (ctgrVO != null) {
//                String ctgNm = ctgrVO.getCtgNm();
//                if (ctgrVO.getParentCtgNm() != null)
//                    ctgNm = ctgrVO.getParentCtgNm() + " > " + ctgNm;
//                if (ctgrVO.getTopCtgNm() != null && !ctgrVO.getTopCtgCd().equals(ctgrVO.getParentCtgCd()))
//                    ctgNm = ctgrVO.getTopCtgNm() + " > " + ctgNm;
//                edcProgramVO.setCtgNm(ctgNm);
//            }
//
//            // 프로그램 모집정보 설정이력
//            if (commandMap.getString("copyPrgmNo").equals("")) {
//                List<EdcProgramVO> rsvnsetList = eduAdmService.selectProgramRsvnSetList(edcProgramVO);
//                if (rsvnsetList != null && rsvnsetList.size() > 0) {
//                    edcProgramVO.setEdcRsvnsetSeq(rsvnsetList.get(0).getEdcRsvnsetSeq());
//                }
//                model.addAttribute("rsvnsetList", rsvnsetList);
//            }
//
//            if (edcProgramVO.getInstrctrNo() < 1) {
//                edcProgramVO.setInstrctrYn("Y");
//            }
//        } else {
//            edcProgramVO.setEdcStimeHour("09");
//            edcProgramVO.setEdcEtimeHour("09");
//            edcProgramVO.setEdcRsvnStimeHour("09");
//            edcProgramVO.setEdcRsvnEtimeHour("09");
//            edcProgramVO.setEdcReqGender("3001");
//            edcProgramVO.setEdcOpenyn("Y");
//            edcProgramVO.setUseYn("Y");
//            edcProgramVO.setEdcOdr(1);
//            edcProgramVO.setEdcPaywaitTime(60);
//            edcProgramVO.setExclDcyn("2001");
//            edcProgramVO.setEdcPaywaitGbn("1001");
//            edcProgramVO.setEdcCtfhvyn("N");
//            edcProgramVO.setEdcRsvnsetNm(DateUtil.getTodate("yyyy") + "년도 01차 모집");
//        }
//
//        // 요일
//        edcProgramVO.setEdcDaysList(eduAdmService.selectProgramDays(edcProgramVO));
//        edcProgramVO.setEdcRsvnRectype("5001"); // 타기관 링크
//
//        // 복사 할때
//        if (!commandMap.getString("copyPrgmNo").equals("")) {
//            edcProgramVO.setEdcPrgmNo(0);
//            edcProgramVO.setEdcProgmDate(DateUtil.getTodate("yyyy-MM-dd"));
//        }
//
//        model.addAttribute("edcProgramVO", edcProgramVO);
//        model.addAttribute("commandMap", commandMap);
//        model.addAttribute("searchQuery", commandMap.getSearchQuery("edcPrgmNo"));
//
//        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/eduadm/extProgramRegist");
//    }


//    /**
//     * 교육 프로그램 관리 목록
//     *
//     * @param searchVO
//     * @param request
//     * @return
//     * @exception Exception
//     */
//    /*
//    @PageActionInfo(title = "교육프로그램 목록 다운로드", action = "R")
//    @GetMapping(value = {"/eduadm/edcProgramListExcel" , "/eduadm/extProgramListExcel"})
//    public void edcProgramListExcel(@ModelAttribute("searchVO") EdcProgramVO searchVO, CommandMap commandMap, HttpServletResponse response,
//            HttpServletRequest request, ModelMap model) throws Exception {
//
//    	String requestUrl =  request.getRequestURI();
//    	searchVO.setEdcProgmType(Constant.SM_EDCPROGM_TYPE_일반교육); // 일반교육
//        searchVO.setComcd(Config.COM_CD);
//        searchVO.setExcelyn("Y");
//
//        String templete = "edcProgramList";
//        String file_name = "교육프로그램_개설목록";
//
//    	if(requestUrl.indexOf("extProgram") > 0){
//    		 searchVO.setSearchOrgkind("2001"); // 유관기관만
//    	     searchVO.setSearchRectype("5001"); // 타기관 링크
//
//    	     templete = "extProgramList";
//    	     file_name = "유관기관_교육프로그램_개설목록";
//    	}
//
//
//        List<EdcProgramVO> programList = (List<EdcProgramVO>)eduAdmService.selectEdcProgramList(searchVO);
//
//
//        for(EdcProgramVO vo : programList){
//        	vo.setEdcRsvnSdate( DateUtil.DateCheck(vo.getEdcRsvnSdate(), "yyyy-MM-dd"));
//        	vo.setEdcRsvnEdate( DateUtil.DateCheck(vo.getEdcRsvnEdate(), "yyyy-MM-dd"));
//
//        	vo.setEdcSdate( DateUtil.DateCheck(vo.getEdcSdate(), "yyyy-MM-dd"));
//        	vo.setEdcEdate( DateUtil.DateCheck(vo.getEdcEdate(), "yyyy-MM-dd"));
//
//        	vo.setEdcStime( DateUtil.DateCheck(vo.getEdcStime(), "HH:mm"));
//        	vo.setEdcEtime( DateUtil.DateCheck(vo.getEdcEtime(), "HH:mm"));
//
//        	vo.setEdcRsvnStime( DateUtil.DateCheck(vo.getEdcRsvnStime(), "HH:mm"));
//        	vo.setEdcRsvnEtime( DateUtil.DateCheck(vo.getEdcRsvnEtime(), "HH:mm"));
//        }
//
//
//        //엑셀 데이터 변환 시 사용 되는 data
//        Map data = new HashMap();
//        data.put("list", programList);
//
//
//        XLSTransformer transformer = new XLSTransformer();
//
//        InputStream is = readTemplate(templete+".xls");
//        Workbook workbook = null;
//        try {
//            workbook = transformer.transformXLS(is, data);
//        } catch (InvalidFormatException e) {
//            e.printStackTrace();
//            response.getWriter().println("엑셀 데이터 변환 시 에러 발생<br>"+ e.getMessage());
//        }
//
//        //엑셀 데이터 contentType 정의
//        response.setContentType( "application/vnd.ms-excel" );
//
//
//		if(file_name.equals("")) {
//			file_name = templete;
//		}
//
//        //엑셀 파일명 설정
//        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName(file_name + ".xls"));
//
//        workbook.write(response.getOutputStream());
//
//    }
//     */
//    /** 엑셀 템플릿을 읽는다. */
//    private InputStream readTemplate(String finalTemplate) throws FileNotFoundException {
//
//    	String templateFilePath = FileMngUtil.GetRealRootPath().concat("WEB-INF/excelTemplate/" + finalTemplate);
//
//        return new FileInputStream(templateFilePath);
//    }
//
//	 /** 파일이름 인코딩 */
//    private String encodeFileName(String filename) {
//        try {
//            return URLEncoder.encode(filename, "UTF-8").replaceAll("[+]", " ");
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e.getMessage(), e);
//        }
//    }

}
