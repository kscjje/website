package com.hisco.admin.log.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hisco.admin.log.service.LogService;
import com.hisco.admin.log.vo.AdminLogVO;
import com.hisco.admin.log.vo.ErrorLogVO;
import com.hisco.admin.menu.util.SiteGubun;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.sec.ram.service.AuthorManageVO;
import egovframework.com.sec.ram.service.EgovAuthorManageService;
import egovframework.com.sym.mnu.mpm.service.EgovMenuManageService;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * 로그 목록 관리
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
public class LogController {

    /** logService */
    @Resource(name = "logService")
    private LogService logService;

    /** EgovMenuManageService */
    @Resource(name = "meunManageService")
    private EgovMenuManageService menuManageService;

    @Resource(name = "egovAuthorManageService")
    private EgovAuthorManageService egovAuthorManageService;

    /**
     * 관리자 작업 내역 목록
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "시스템접속이력 조회", action = "R")
    @GetMapping("/log/jobList")
    public String selectSysUserlist(@ModelAttribute("searchVO") AdminLogVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);

        int totCnt = logService.selectAdminLogCount(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);

        List<?> list = logService.selectAdminLogList(searchVO);
        model.addAttribute("list", list);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 에러 내역 목록
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "오류로그 조회", action = "R")
    @GetMapping("/log/errorList")
    public String selectErrorlist(@ModelAttribute("searchVO") ErrorLogVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setComcd(Config.COM_CD);

        int totCnt = logService.selectErrorLogCount(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);

        List<?> list = logService.selectErrorLogList(searchVO);
        model.addAttribute("list", list);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 웹로그 목록
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "웹로그 조회", action = "R")
    @GetMapping("/log/webList")
    public String webList(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
    	String startYmd = commandMap.getString("startYmd").replaceAll("-", "");
        String endYmd = commandMap.getString("endYmd").replaceAll("-", "");
        String searchCondition = commandMap.getString("searchCondition");

        String startMonth = commandMap.getString("startYear") + commandMap.getString("startMonth");
        String endMonth = commandMap.getString("endYear") + commandMap.getString("endMonth");

        log.debug("startYmd1 = " + startYmd);
        log.debug("endYmd1   = " + endYmd);

        if ("".equals(startYmd)) {
            endYmd = CommonUtil.getDate();
            startYmd = CommonUtil.getDayfromToday("yyyyMMdd", -6);
        }

        if ("".equals(endYmd)) {
            endYmd = CommonUtil.getLastDayOfMonth(startYmd);
        }

        if("".equals(searchCondition)){
        	searchCondition = "monthly";
        }

        if(startMonth.length() != 6){
        	startMonth = CommonUtil.getDayfromToday("yyyyMMdd", -90).substring(0,6);
        }
        if(endMonth.length() != 6){
        	endMonth = CommonUtil.getDate().substring(0,6);
        }


        commandMap.put("searchStartMonth", startMonth);
        commandMap.put("searchEndMonth", endMonth);
        commandMap.put("startYear", startMonth.substring(0,4));
        commandMap.put("startMonth", startMonth.substring(4,6));
        commandMap.put("endtYear", endMonth.substring(0,4));
        commandMap.put("endMonth", endMonth.substring(4,6));

        commandMap.put("searchCondition", searchCondition);

        commandMap.put("startYmd", CommonUtil.getDateFormat(startYmd, "yyyyMMdd", "yyyy-MM-dd"));
        commandMap.put("endYmd", CommonUtil.getDateFormat(endYmd, "yyyyMMdd", "yyyy-MM-dd"));

        List<MenuManageVO> menuList = getMenuLogList(startYmd, endYmd, commandMap);

        if (log.isDebugEnabled()) {
            log.debug("************************************************************.S");
            for (int j = 0; j < menuList.size(); j++) {

                String selog = "-----.S.E. = " + j;
                selog += " | menuList.get(j).getMenuNo() = " + menuList.get(j).getMenuNo();
                selog += " | menuList.get(j).getUpperMenuId() = " + menuList.get(j).getUpperMenuId();
                selog += " | menuList.get(j).getMenuNm() = " + menuList.get(j).getMenuNm();
                selog += " | menuList.get(j).getLogCount() = " + menuList.get(j).getLogCount();
                log.debug(selog);
            }
            log.debug("************************************************************.E");
        }
        model.addAttribute("menuList", menuList);

        long maxCnt = Long.parseLong(commandMap.getString("maxCnt"));
        maxCnt = (long) (Math.ceil(maxCnt / 100.0) * 100);

        model.addAttribute("maxCnt", maxCnt);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paramMap", commandMap.getParam());

        return HttpUtility.getViewUrl(request);
    }

    // 웹 로그 엑셀 다운로드
    @PageActionInfo(title = "메뉴별 접속통계 다운로드", action = "R")
    @GetMapping("/log/webListExcel")
    public void webListExcel(CommandMap commandMap, HttpServletRequest request,HttpServletResponse response , ModelMap model)
            throws Exception {
    	String startYmd = commandMap.getString("startYmd").replaceAll("-", "");
        String endYmd = commandMap.getString("endYmd").replaceAll("-", "");
        String searchCondition = commandMap.getString("searchCondition");

        String startMonth = commandMap.getString("startYear") + commandMap.getString("startMonth");
        String endMonth = commandMap.getString("endYear") + commandMap.getString("endMonth");

        log.debug("startYmd1 = " + startYmd);
        log.debug("endYmd1   = " + endYmd);

        if ("".equals(startYmd)) {
            endYmd = CommonUtil.getDate();
            startYmd = CommonUtil.getDayfromToday("yyyyMMdd", -6);
        }

        if ("".equals(endYmd)) {
            endYmd = CommonUtil.getLastDayOfMonth(startYmd);
        }

        if("".equals(searchCondition)){
        	searchCondition = "monthly";
        }

        if(startMonth.length() != 6){
        	startMonth = CommonUtil.getDayfromToday("yyyyMMdd", -90).substring(0,6);
        }
        if(endMonth.length() != 6){
        	endMonth = CommonUtil.getDate().substring(0,6);
        }


        commandMap.put("searchStartMonth", startMonth);
        commandMap.put("searchEndMonth", endMonth);
        commandMap.put("startYear", startMonth.substring(0,4));
        commandMap.put("startMonth", startMonth.substring(4,6));
        commandMap.put("endtYear", endMonth.substring(0,4));
        commandMap.put("endMonth", endMonth.substring(4,6));

        commandMap.put("searchCondition", searchCondition);

        commandMap.put("startYmd", CommonUtil.getDateFormat(startYmd, "yyyyMMdd", "yyyy-MM-dd"));
        commandMap.put("endYmd", CommonUtil.getDateFormat(endYmd, "yyyyMMdd", "yyyy-MM-dd"));

        List<MenuManageVO> menuList = getMenuLogList(startYmd, endYmd, commandMap);
        ArrayList<HashMap> dataList = new ArrayList<HashMap>();

        HashMap prevMap = null;

        for(MenuManageVO vo : menuList){


        	if( !CommonUtil.isEmpty(vo.getMenuUrl())){

	        	for(CamelMap dataVO : vo.getLogCount()){
	        		HashMap data = new HashMap();
	            	data.put("menuNm", vo.getMenuNm());
	            	data.put("upperMenuNm", vo.getUpperMenuNm());

	            	data.put("menuNo", vo.getMenuNo());
	            	data.put("upperMenuNo", vo.getUpperMenuNo());
	        		data.put("cnt", Integer.parseInt(dataVO.get("cnt")+""));
	        		data.put("ymd", dataVO.get("ymd"));

	        		dataList.add(data);
	        	}
        	}



        }


        String templete = "webMenuList";
        String file_name = "메뉴별_접속통계_목록";

        //엑셀 데이터 변환 시 사용 되는 data
        Map data = new HashMap();
        data.put("list", dataList);
        data.put("title", searchCondition.equals("monthly")?"년월":"일자");


        XLSTransformer transformer = new XLSTransformer();

        InputStream is = readTemplate(templete+".xls");
        Workbook workbook = null;
        try {
            workbook = transformer.transformXLS(is, data);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            response.getWriter().println("엑셀 데이터 변환 시 에러 발생<br>"+ e.getMessage());
        }

        //엑셀 데이터 contentType 정의
        response.setContentType( "application/vnd.ms-excel" );
        //엑셀 파일명 설정
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName(file_name + ".xls"));
        workbook.write(response.getOutputStream());
    }

    /** 엑셀 템플릿을 읽는다. */
    private InputStream readTemplate(String finalTemplate) throws FileNotFoundException {

    	String templateFilePath = FileMngUtil.GetRealRootPath().concat("WEB-INF/excelTemplate/" + finalTemplate);

        return new FileInputStream(templateFilePath);
    }

    /** 파일이름 인코딩 */
    private String encodeFileName(String filename) {
        try {
            return URLEncoder.encode(filename, "UTF-8").replaceAll("[+]", " ");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<MenuManageVO> getMenuLogList(String startYmd , String endYmd , CommandMap commandMap) throws Exception{

		List<CamelMap> list = logService.selectWebLogList(commandMap.getParam());
		List<CamelMap> dateList  = logService.selectDateList(commandMap.getParam());
		HashMap<String , Long> parentList = new HashMap();
		long maxCnt = 0;

		MenuManageVO vo = new MenuManageVO();
		vo.setSiteGubun("USER");
		List<MenuManageVO> menuList = (List<MenuManageVO>) menuManageService.selectMenuListSite(vo);


		for(MenuManageVO menuVO : menuList){
			for(CamelMap data : list){
				if(CommonUtil.getString(data.get("menuNo")).equals(menuVO.getMenuNo()+"")){
					menuVO.getLogCount().add(data);

					if(maxCnt < CommonUtil.getLong(data.get("cnt"))){
						maxCnt =  CommonUtil.getLong(data.get("cnt"));
					}

					//부모가 있을 경우
					if( Integer.parseInt(menuVO.getUpperMenuId()) > 0 ){
						String mapKey = menuVO.getUpperMenuId() + "_" + data.get("ymd");
						Long cnt = parentList.get(mapKey);
						if(cnt == null) cnt = 0L;
						cnt = cnt +  Long.parseLong(String.valueOf(data.get("cnt")));
						parentList.put(mapKey , cnt);

						if(maxCnt < cnt){
							maxCnt = cnt;
						}
					}
				}
			}
// 날짜 없는 데이타 날짜 넣어줌
		}
//1 depth 메뉴 2depth 합계 로 처리
		for(MenuManageVO menuVO : menuList){
			if( Integer.parseInt(menuVO.getUpperMenuId()) == 0 && CommonUtil.isEmpty(menuVO.getMenuUrl())){
				for(CamelMap data : dateList){
					String mapKey = menuVO.getMenuNo() + "_" + data.get("calDate");

					if(parentList.containsKey(mapKey)){
						CamelMap temp = new CamelMap();
						temp.put("cnt", parentList.get(mapKey));
						temp.put("ymd", data.get("calDate"));
						menuVO.getLogCount().add(temp);
					}else{
						CamelMap temp = new CamelMap();
						temp.put("cnt", "0");
						temp.put("ymd", data.get("calDate"));
						menuVO.getLogCount().add(temp);
					}
				}
			}

		}
		commandMap.put("maxCnt", maxCnt);
		return menuList;
	}

    /**
     * 시스템 접속 통계
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "웹방문자통계 조회", action = "R")
    @GetMapping("/log/connList")
    public String connList(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {
        String startYmd = commandMap.getString("startYmd").replaceAll("-", "");
        String endYmd = commandMap.getString("endYmd").replaceAll("-", "");
        String searchCondition = commandMap.getString("searchCondition");

        String startMonth = commandMap.getString("startYear") + commandMap.getString("startMonth");
        String endMonth = commandMap.getString("endYear") + commandMap.getString("endMonth");

        log.debug("startYmd1 = " + startYmd);
        log.debug("endYmd1   = " + endYmd);

        if ("".equals(startYmd)) {
            endYmd = CommonUtil.getDate();
            startYmd = CommonUtil.getDayfromToday("yyyyMMdd", -6);
        }

        if ("".equals(endYmd)) {
            endYmd = CommonUtil.getLastDayOfMonth(startYmd);
        }

        if("".equals(searchCondition)){
        	searchCondition = "monthly";
        }

        if(startMonth.length() != 6){
        	startMonth = CommonUtil.getDayfromToday("yyyyMMdd", -90).substring(0,6);
        }
        if(endMonth.length() != 6){
        	endMonth = CommonUtil.getDate().substring(0,6);
        }

        java.util.Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("startYmd", CommonUtil.getDateFormat(startYmd, "yyyyMMdd", "yyyy-MM-dd"));
        paramMap.put("endYmd", CommonUtil.getDateFormat(endYmd, "yyyyMMdd", "yyyy-MM-dd"));
        paramMap.put("searchStartMonth", startMonth);
        paramMap.put("searchEndMonth", endMonth);

        paramMap.put("startYear", startMonth.substring(0,4));
        paramMap.put("startMonth", startMonth.substring(4,6));
        paramMap.put("endtYear", endMonth.substring(0,4));
        paramMap.put("endMonth", endMonth.substring(4,6));

        paramMap.put("searchCondition", searchCondition);

        List<?> dbResultList = logService.selectConnLogList(paramMap);

        model.addAttribute("dbResultList", dbResultList);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paramMap", paramMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 시스템 접속 통계
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "웹방문자통계 다운로드", action = "R")
    @GetMapping("/log/connListExcel")
    public void connListExcel(CommandMap commandMap, HttpServletRequest request,HttpServletResponse response , ModelMap model)
            throws Exception {
        String startYmd = commandMap.getString("startYmd").replaceAll("-", "");
        String endYmd = commandMap.getString("endYmd").replaceAll("-", "");
        String searchCondition = commandMap.getString("searchCondition");

        String startMonth = commandMap.getString("startYear") + commandMap.getString("startMonth");
        String endMonth = commandMap.getString("endYear") + commandMap.getString("endMonth");

        if ("".equals(startYmd)) {
            endYmd = CommonUtil.getDate();
            startYmd = CommonUtil.getDayfromToday("yyyyMMdd", -6);
        }

        if ("".equals(endYmd)) {
            endYmd = CommonUtil.getLastDayOfMonth(startYmd);
        }

        if("".equals(searchCondition)){
        	searchCondition = "monthly";
        }

        if(startMonth.length() != 6){
        	startMonth = CommonUtil.getDayfromToday("yyyyMMdd", -90).substring(0,6);
        }
        if(endMonth.length() != 6){
        	endMonth = CommonUtil.getDate().substring(0,6);
        }

        java.util.Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("startYmd", CommonUtil.getDateFormat(startYmd, "yyyyMMdd", "yyyy-MM-dd"));
        paramMap.put("endYmd", CommonUtil.getDateFormat(endYmd, "yyyyMMdd", "yyyy-MM-dd"));
        paramMap.put("searchStartMonth", startMonth);
        paramMap.put("searchEndMonth", endMonth);

        paramMap.put("startYear", startMonth.substring(0,4));
        paramMap.put("startMonth", startMonth.substring(4,6));
        paramMap.put("endtYear", endMonth.substring(0,4));
        paramMap.put("endMonth", endMonth.substring(4,6));
        paramMap.put("searchCondition", searchCondition);

        List<CamelMap> dbResultList = logService.selectConnLogList(paramMap);
        double totalA = 0.0;
        double totalB = 0.0;

        for(CamelMap vo : dbResultList){
        	totalA += Double.parseDouble((vo.get("memCount")+".0"));
        	totalB +=  Double.parseDouble((vo.get("nonCount")+".0"));
        }

        String templete = "connList";
        String file_name = "웹방문자통계_목록";

        //엑셀 데이터 변환 시 사용 되는 data
        Map data = new HashMap();
        data.put("list", dbResultList);
        data.put("title", searchCondition.equals("monthly")?"년월":"일자");
        data.put("totalA", totalA);
        data.put("totalB", totalB);

        XLSTransformer transformer = new XLSTransformer();

        InputStream is = readTemplate(templete+".xls");
        Workbook workbook = null;
        try {
            workbook = transformer.transformXLS(is, data);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            response.getWriter().println("엑셀 데이터 변환 시 에러 발생<br>"+ e.getMessage());
        }

        //엑셀 데이터 contentType 정의
        response.setContentType( "application/vnd.ms-excel" );
        //엑셀 파일명 설정
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName(file_name + ".xls"));
        workbook.write(response.getOutputStream());
    }

    /**
     * 개인정보 변경 내역 목록
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "개인정보변경 이력 조회", action = "R")
    @GetMapping("/log/memberlogList")
    public String selectMemberLoglist(@ModelAttribute("searchVO") AdminLogVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);

        List<EgovMap> list = logService.selectMemberLogList(searchVO);

        if (list != null && list.size() > 0) {
            paginationInfo.setTotalRecordCount(Integer.parseInt(list.get(0).get("totCount").toString()));
        }

        model.addAttribute("list", list);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 개인정보 접속이력 목록
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "개인정보 접속이력 조회", action = "R")
    @GetMapping("/log/privateList")
    public String selectPrivateList(@ModelAttribute("searchVO") AdminLogVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setInqryMemberinfo("");

        int totCnt = logService.selectAdminLogCount(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);

        List<?> list = logService.selectAdminLogList(searchVO);
        model.addAttribute("list", list);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 권한변경 이력 목록
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "권한변경 이력 조회", action = "R")
    @GetMapping("/log/authorList")
    public String selecAuthorList(@ModelAttribute("searchVO") AdminLogVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);
        List<EgovMap> list = logService.selectAuthorLogList(searchVO);

        if (list != null && list.size() > 0) {
            paginationInfo.setTotalRecordCount(Integer.parseInt(list.get(0).get("totCount").toString()));
        }

        // 관리그룹 목록
        AuthorManageVO authorVo = new AuthorManageVO();
        authorVo.setSearchCondition("3"); // 권한 부모 코드가 ROLE_ADMIN 인것만...
        List<?> authorList = egovAuthorManageService.selectAuthorList(authorVo);
        model.addAttribute("authorList", authorList);

        model.addAttribute("list", list);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 웹 서비스 페이지뷰
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "웹 서비스 페이지뷰", action = "R")
    @GetMapping("/log/weblogList")
    public String selectWebLoglist(@ModelAttribute("searchVO") AdminLogVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);

        List<EgovMap> list = logService.selectWebVisitLogList(searchVO);

        if (list != null && list.size() > 0) {
            paginationInfo.setTotalRecordCount(Integer.parseInt(list.get(0).get("totCount").toString()));
        }

        model.addAttribute("list", list);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "웹회원 로그인 접속", action = "R")
    @GetMapping("/log/loginlogList")
    public String selectLoginLoglist(@ModelAttribute("searchVO") AdminLogVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);

        List<EgovMap> list = logService.selectLoginLogList(searchVO);

        if (list != null && list.size() > 0) {
            paginationInfo.setTotalRecordCount(Integer.parseInt(list.get(0).get("totCount").toString()));
        }

        model.addAttribute("list", list);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "결제 대기 로그", action = "R")
    @GetMapping("/log/paywaitlogList")
    public String selectPaywaitLoglist(@ModelAttribute("searchVO") AdminLogVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);

        List<EgovMap> list = logService.selectPaywaitLogList(searchVO);

        if (list != null && list.size() > 0) {
            paginationInfo.setTotalRecordCount(Integer.parseInt(list.get(0).get("totCount").toString()));
        }

        model.addAttribute("list", list);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "노원페이 로그", action = "R")
    @GetMapping("/log/nwpaylogList")
    public String selectNwpayLoglist(@ModelAttribute("searchVO") AdminLogVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);

        List<EgovMap> list = logService.selectNwpayLogList(searchVO);

        if (list != null && list.size() > 0) {
            paginationInfo.setTotalRecordCount(Integer.parseInt(list.get(0).get("totCount").toString()));
        }

        model.addAttribute("list", list);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }
}
