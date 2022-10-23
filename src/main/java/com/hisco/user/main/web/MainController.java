package com.hisco.user.main.web;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.area.service.AreaCdService;
import com.hisco.admin.area.vo.AreaCdVO;
import com.hisco.admin.comctgr.service.ComCtgrService;
import com.hisco.admin.comctgr.vo.ComCtgrVO;
import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgInfoVO;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.RedirectUtil;
import com.hisco.cmm.util.SessionUtil;
import com.hisco.cmm.util.WebEncDecUtil;
import com.hisco.cmm.vo.CodeVO;
import com.hisco.user.main.service.MainSearchVO;
import com.hisco.user.main.service.MainService;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovArticleService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT)
public class MainController {

    @Resource(name = "mainService")
    private MainService mainService;

    @Resource(name = "EgovArticleService")
    private EgovArticleService egovArticleService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "comCtgrService")
    private ComCtgrService comCtgrService;

    @Resource(name = "areaCdService")
    private AreaCdService areaCdService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    @GetMapping(value = "/loginmsg")
    public String loginmsg(HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
    	return RedirectUtil.redirectLoginParam(model);
    }
    
    //인터셉터에서 처리할 때
    @GetMapping(value = "/loginmsg2")
    public String loginmsg2(HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
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
		
    	return RedirectUtil.redirectLogin(urlBuilder.toString().split("returnURL=")[1].toString(),model);
    }

    /*
     * 메인 화면
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/main")
    public String main(CommandMap commandMap, BoardVO boardVO, HttpServletRequest request, ModelMap model)
            throws Exception {

        log.debug("call main");

        LoginVO loginVO = commandMap.getUserInfo();

        boardVO.setUseAt("Y");
        boardVO.setBbsId("notice");
        List<BoardVO> noticeList = egovArticleService.selectNoticeArticleMainList(boardVO);

        boardVO.setUseAt("Y");
        boardVO.setBbsId("org");
        List<BoardVO> orgBbsList = egovArticleService.selectNoticeArticleMainList(boardVO);

        // FrontPopupTag에서 처리
        // List<?> popList = mainService.selectMainPopupList();

        String strUploadExt = EgovProperties.getProperty("Globals.fileUpload.Extensions");
        String strUploadRoot = EgovProperties.getProperty("upload.folder");

        log.debug("strUploadExt = " + strUploadExt);
        log.debug("strUploadRoot  = " + strUploadRoot);

        /*
         * for (int i = 0; i < popList.size(); i++) {
         * CamelMap camelMap = (CamelMap) popList.get(i);
         * strPopShName = String.valueOf(camelMap.get("popupSjNm"));
         * strFilePath = String.valueOf(camelMap.get("filePath"));
         * strOrgFileName = String.valueOf(camelMap.get("popupImage"));
         * strImgFileName = String.valueOf(camelMap.get("imageFileName"));
         * String strFromFile = strUploadRoot + strFilePath + strOrgFileName;
         * String strResourcesPopDir = strResourcesDir + "/popup";
         * java.io.File popDir = new java.io.File(strResourcesPopDir);
         * if (!popDir.exists()) {
         * popDir.mkdir();
         * }
         * log.debug("strResourcesPopDir = " + strResourcesPopDir);
         * File fromFile = new File(strFromFile);
         * File toFile = new File(strResourcesPopDir + "/" + strImgFileName);
         * if (!toFile.exists()) {
         * try {
         * fis = new FileInputStream(fromFile);
         * fos = new FileOutputStream(toFile);
         * int intFileByte = 0;
         * while (true) {
         * intFileByte = fis.read();
         * if (intFileByte == -1) {
         * break;
         * }
         * fos.write(intFileByte);
         * }
         * fis.close();
         * fos.close();
         * } catch (FileNotFoundException e) {
         * e.printStackTrace();
         * } catch (IOException e) {
         * e.printStackTrace();
         * }
         * }
         * }
         * model.addAttribute("popList", popList);
         * model.addAttribute("notice", noticeList);
         */

        Map<String, Object> paramMap = new HashMap<String, Object>();

        /*
         * List<String> strBBsIdsList = new java.util.ArrayList<String>();
         * strBBsIdsList.add("notice");
         * strBBsIdsList.add("qna");
         * strBBsIdsList.add("mathinfor");
         * paramMap.put("bbsIds", strBBsIdsList);
         * List<?> bbsList = mainService.selectBBSList(paramMap);
         * model.addAttribute("bbsList", bbsList);
         */

        model.addAttribute("loginVO", loginVO);
        model.addAttribute("currentUrl", "main");
        model.addAttribute("comCd", Config.COM_CD);

        model.addAttribute("noticeList", noticeList);
        model.addAttribute("orgBbsList", orgBbsList);

        // 지역별
        List<AreaCdVO> areaList = (List<AreaCdVO>) areaCdService.selectAreaCdList(new AreaCdVO());
        model.addAttribute("areaList", areaList);

        // 분야별 ( 1depth 인 것만 선택 )
        ComCtgrVO ctgParam = new ComCtgrVO();
        ctgParam.setComcd(Config.COM_CD);
        ctgParam.setUseYn("Y");
        log.error("comCtgrService = {}", comCtgrService);
        List<ComCtgrVO> cateList = (List<ComCtgrVO>) comCtgrService.selectComctgrListForTree(ctgParam);
        model.addAttribute("cateList", cateList);

        List<ComCtgrVO> cateList2 = (List<ComCtgrVO>) comCtgrService.selectComctgrList(null);
        model.addAttribute("cateList2", cateList2);

        // 대상별
        List<CodeVO> targetList = codeService.selectCodeList("CM_AGEGBN");
        model.addAttribute("targetList", targetList);

        // 기관별
        List<CodeVO> orgList = codeService.selectCodeList("SM_ORG_LTYPE");
        model.addAttribute("orgList", orgList);

        //기관목록
        OrgInfoVO orgInfoVO = new OrgInfoVO();
        orgInfoVO.setComcd(Config.COM_CD);
        List<OrgInfoVO> list = (List<OrgInfoVO>) orgInfoService.selectOrgInfoList(orgInfoVO);
        model.addAttribute("orgInfoList", list);
        
        log.debug("main model = " + model);

        // return HttpUtility.getViewUrl(request);

        return "/web/main/main";
    }

    /*
     * 메인 화면
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/mainSearch")
    public String mainSearch(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("mainSearchVO") MainSearchVO vo) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        PaginationInfo pagination = commandMap.getPagingInfo();
        vo.setPaginationInfo(pagination);

        if (vo.getTitle() != null && !vo.getTitle().equals("")) {
            map = mainService.selectMainSearch(vo);
            pagination.setTotalRecordCount((int) map.get("listsize"));
            model.addAttribute("list", map.get("rList"));
            model.addAttribute("odr", vo.getOrderType());
            model.addAttribute("listsize1", map.get("listsize1"));
            model.addAttribute("listsize2", map.get("listsize2"));
            model.addAttribute("paginationInfo", pagination);
        }
        model.addAttribute("searchVO", vo);
        return HttpUtility.getViewUrl(Config.USER_ROOT, "/main/mainSearch");
    }

    /*
     * 오늘의 티켓
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/main/ticketAjax")
    public String ticketAjax(CommandMap commandMap, BoardVO boardVO, HttpServletRequest request, ModelMap model)
            throws Exception {
        LoginVO loginVO = commandMap.getUserInfo();

        if (loginVO != null) {
            model.addAttribute("ticketList", mainService.selectTodayTicketList(loginVO));
        }

        return HttpUtility.getViewUrl(request);
    }

    /*
     * 로그인 연장
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/loginExtend")
    @ResponseBody
    public ModelAndView loginExtend(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {

        ResultInfo resultInfo = new ResultInfo();
        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO vo = commandMap.getUserInfo();

        if (vo == null) {
            resultInfo.setCode("FAIL");
            resultInfo.setMsg("로그아웃 상태입니다.");
        } else {
            resultInfo.setCode("OK");
            resultInfo.setMsg("10분 연장되었습니다");
        }

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(600);
        mav.addObject("resultInfo", resultInfo);
        return mav;
    }

    /*
     * 로그인 여부 체크
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/loginCheckAjax")
    @ResponseBody
    public ModelAndView loginCheckAjax(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO vo = commandMap.getUserInfo();

        if (vo == null) {
            mav.addObject("result", "FAIL");
        } else {
            mav.addObject("result", "OK");
        }

        return mav;
    }

    /**
     * 세션체크 @todo SSO연결 확인을 위한 세션체크, 실서버 반영시 확인 후 삭제
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/ss")
    public String s(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model,
            HttpSession session)
            throws Exception {

        // System.out.println("session.getAttributeNames");
        // System.out.println(session.getAttributeNames());

        String txt = "";
        try {
            Enumeration<String> e = session.getAttributeNames();
            for (String key : Collections.list(e)) {
                // System.out.println("========== > key:" + key);
                txt += "<br>========== > key:" + key;
                // System.out.println(session.getAttribute(key));
                txt += "<p style='color:blue;'>" + session.getAttribute(key) + "</p>";
            }
        } catch (Exception e) {
            // 에러시 수행
            e.printStackTrace(); // 오류 출력(방법은 여러가지)
            txt += "<br>========== > 에러발생:" + e.getMessage();
            throw e; // 최상위 클래스가 아니라면 무조건 던져주자
        } finally {
            model.addAttribute("txt", txt);
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/main/ss");
        }

    }

    /**
     * 세션체크 @todo 암호화 WebEncDecUtil.fn_encrypt 테스트 , 실서버 반영시 확인 후 삭제
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/test")
    public String test(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model,
            HttpSession session)
            throws Exception {

        String txt = "";
        try {
            // ---------------------------------------------------개인정보 관련 암호화 시작
            String strSpowiseCmsKey = EgovProperties.getProperty("Globals.SpowiseCms.Key");

            log.debug("strSpowiseCmsKey = " + strSpowiseCmsKey);
            txt += "<br>========== > strSpowiseCmsKey:" + strSpowiseCmsKey;

            String strHp = "01025426565";
            log.debug("strHp = " + strHp);
            txt += "<br>========== > strHp:" + strHp;
            String strEncHp = WebEncDecUtil.fn_encrypt(strHp, strSpowiseCmsKey);
            log.debug("WebEncDecUtil.fn_encrypt(strHp, strSpowiseCmsKey) = " + WebEncDecUtil.fn_encrypt(strHp, strSpowiseCmsKey));
            txt += "<br>========== > strEncHp = WebEncDecUtil.fn_encrypt(strHp, strSpowiseCmsKey):" + strEncHp;
        } catch (Exception e) {
            // 에러시 수행
            e.printStackTrace(); // 오류 출력(방법은 여러가지)
            txt += "<br>========== > 에러발생:" + e.getMessage();
            throw e; // 최상위 클래스가 아니라면 무조건 던져주자
        } finally {
            model.addAttribute("txt", txt);
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/main/ss");
        }

    }
}
