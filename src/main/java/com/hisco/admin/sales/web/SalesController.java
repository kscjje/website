/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.sales.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;

import com.hisco.admin.member.vo.MemberUserVO;
import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgInfoVO;
import com.hisco.admin.sales.service.SalesSettleService;
import com.hisco.admin.sales.vo.SalesDiscountVO;
import com.hisco.admin.sales.vo.SalesPGHistVO;
import com.hisco.admin.sales.vo.SalesReceiptVO;
import com.hisco.admin.sales.vo.SalesRefundVO;
import com.hisco.admin.sales.vo.SalesSettleDetailVO;
import com.hisco.admin.sales.vo.SalesSettleVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.ibm.icu.util.Calendar;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * @Class Name : SalesController.java
 * @Description : 수입금관련 컨트롤러(수입금정산일지/수입금정산상세내역/수입금전표현황)
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 12. 24
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}/sales", "#{dynamicConfig.managerRoot}/sales" })
public class SalesController {

    @Resource(name = "salesSettleService")
    private SalesSettleService salesSettleService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    @Value("${Globals.DbEncKey}")
    private String dbEncKey;

    @GetMapping("/settleList")
    @PageActionInfo(title = "수입금정산일지 조회", action = "R")
    public String viewSettleList(@ModelAttribute("searchVO") SalesSettleVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        int totCnt = 0;
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setMyOrgList(getMyOrgList(request));
        setDefaultDate(searchVO);

        SalesSettleVO total = salesSettleService.selectSettleTotal(searchVO);
        List<SalesSettleVO> list = salesSettleService.selectSettleList(searchVO);

        if (total == null) {
            total = new SalesSettleVO();
        }

        if (list != null && !list.isEmpty()) {
            totCnt = ((SalesSettleVO) list.get(0)).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }

        model.addAttribute("total", total);
        model.addAttribute("list", list);
        model.addAttribute("orgList", getOrgList(request));
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);
        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/sales/salesSettleList");
    }

    @PageActionInfo(title = "수입금정산일지 엑셀다운로드", action = "R")
    @GetMapping(value = {"/settleListExcel"})
    public void edcProgramListExcel(@ModelAttribute("searchVO") SalesSettleVO searchVO,  CommandMap commandMap, HttpServletResponse response,
            HttpServletRequest request, ModelMap model) throws Exception {

    	searchVO.setExcelyn("Y");
    	searchVO.setMyOrgList(getMyOrgList(request));
        setDefaultDate(searchVO);

        SalesSettleVO total = salesSettleService.selectSettleTotal(searchVO);
        List<SalesSettleVO> list = salesSettleService.selectSettleList(searchVO);

        String templete = "settleList";
        String file_name = "수입금정산일지_목록";

        //엑셀 데이터 변환 시 사용 되는 data
        Map data = new HashMap();
        data.put("list", list);
        data.put("total", total);

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



    @GetMapping("/settleDetail")
    @PageActionInfo(title = "수입금정산상세내역 조회", action = "R")
    public String viewSettleDetail(@ModelAttribute("searchVO") SalesSettleVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        int totCnt = 0;
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setMyOrgList(getMyOrgList(request));
        setDefaultDate(searchVO);

        SalesSettleDetailVO total = salesSettleService.selectSettlePayTotal(searchVO);
        List<SalesSettleDetailVO> list = salesSettleService.selectSettlePayList(searchVO);
        if (total == null) {
            total = new SalesSettleDetailVO();
        }
        if (list != null && !list.isEmpty()) {
            totCnt = ((SalesSettleVO) list.get(0)).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }

        model.addAttribute("total", total);
        model.addAttribute("list", list);
        model.addAttribute("orgList", getOrgList(request));
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);
        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/sales/salesSettleDetail");
    }

    @PageActionInfo(title = "수입금정산상세내역 엑셀다운로드", action = "R")
    @GetMapping(value = {"/settleDetailExcel"})
    public void settleDetailExcel(@ModelAttribute("searchVO") SalesSettleVO searchVO,  CommandMap commandMap, HttpServletResponse response,
            HttpServletRequest request, ModelMap model) throws Exception {

    	searchVO.setExcelyn("Y");
    	searchVO.setMyOrgList(getMyOrgList(request));
        setDefaultDate(searchVO);

        SalesSettleDetailVO total = salesSettleService.selectSettlePayTotal(searchVO);
        List<SalesSettleDetailVO> list = salesSettleService.selectSettlePayList(searchVO);

        String templete = "settleDetail";
        String file_name = "수입금정산상세내역_목록";

        //엑셀 데이터 변환 시 사용 되는 data
        Map data = new HashMap();
        data.put("list", list);
        data.put("total", total);

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

    @GetMapping("/refundList")
    @PageActionInfo(title = "환불취소현황 조회", action = "R")
    public String viewRefundList(@ModelAttribute("searchVO") SalesRefundVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        int totCnt = 0;
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setMyOrgList(getMyOrgList(request));
        setDefaultDate(searchVO);

        SalesRefundVO total = salesSettleService.selectRefundTotal(searchVO);
        List<SalesRefundVO> list = salesSettleService.selectRefundList(searchVO);
        if (total == null) {
            total = new SalesRefundVO();
        }
        if (list != null && !list.isEmpty()) {
            totCnt = ((SalesRefundVO) list.get(0)).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }
        model.addAttribute("total", total);
        model.addAttribute("list", list);
        model.addAttribute("orgList", getOrgList(request));
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);
        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/sales/salesRefundList");
    }


    @PageActionInfo(title = "환불취소현황 엑셀다운로드", action = "R")
    @GetMapping(value = {"/refundListExcel"})
    public void refundListExcel(@ModelAttribute("searchVO") SalesRefundVO searchVO,  CommandMap commandMap, HttpServletResponse response,
            HttpServletRequest request, ModelMap model) throws Exception {

    	searchVO.setExcelyn("Y");
    	searchVO.setMyOrgList(getMyOrgList(request));
        setDefaultDate(searchVO);

        SalesRefundVO total = salesSettleService.selectRefundTotal(searchVO);
        List<SalesRefundVO> list = salesSettleService.selectRefundList(searchVO);

        String templete = "refundList";
        String file_name = "환불취소현황_목록";

        //엑셀 데이터 변환 시 사용 되는 data
        Map data = new HashMap();
        data.put("list", list);
        data.put("total", total);

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

    @GetMapping("/receiptList")
    @PageActionInfo(title = "수입금전표현황 조회", action = "R")
    public String viewReceiptList(@ModelAttribute("searchVO") SalesReceiptVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        int totCnt = 0;
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setMyOrgList(getMyOrgList(request));
        setDefaultDate(searchVO);

        List<SalesReceiptVO> list = salesSettleService.selectReceiptList(searchVO);

        if (list != null && !list.isEmpty()) {
            totCnt = ((SalesReceiptVO) list.get(0)).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }
        // model.addAttribute("total", total);

        model.addAttribute("list", list);
        model.addAttribute("orgList", getOrgList(request));
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);
        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/sales/salesReceiptList");
    }

    @PageActionInfo(title = "수입금전표현황 엑셀다운로드", action = "R")
    @GetMapping(value = {"/receiptListExcel"})
    public void receiptListExcel(@ModelAttribute("searchVO") SalesReceiptVO searchVO,  CommandMap commandMap, HttpServletResponse response,
            HttpServletRequest request, ModelMap model) throws Exception {

    	searchVO.setExcelyn("Y");
    	searchVO.setMyOrgList(getMyOrgList(request));
        setDefaultDate(searchVO);

        List<SalesReceiptVO> list = salesSettleService.selectReceiptList(searchVO);

        String templete = "receiptList";
        String file_name = "수입금전표현황_목록";

        //엑셀 데이터 변환 시 사용 되는 data
        Map data = new HashMap();
        data.put("list", list);

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

    @GetMapping("/PGAcceptList")
    @PageActionInfo(title = "PG거래승인현황 조회", action = "R")
    public String viewPGAcceptList(@ModelAttribute("searchVO") SalesPGHistVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        int totCnt = 0;
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setMyOrgList(getMyOrgList(request));
        setDefaultDate(searchVO);

        List<SalesPGHistVO> list = salesSettleService.selectPGHistory(searchVO);

        if (list != null && !list.isEmpty()) {
            totCnt = ((SalesPGHistVO) list.get(0)).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }

        model.addAttribute("list", list);
        model.addAttribute("orgList", getOrgList(request));
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);
        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/sales/salesPGAcceptList");
    }

    @PageActionInfo(title = "PG거래승인현황 엑셀다운로드", action = "R")
    @GetMapping(value = {"/PGAcceptListExcel"})
    public void PGAcceptListExcel(@ModelAttribute("searchVO") SalesPGHistVO searchVO,  CommandMap commandMap, HttpServletResponse response,
            HttpServletRequest request, ModelMap model) throws Exception {

    	searchVO.setExcelyn("Y");
    	searchVO.setMyOrgList(getMyOrgList(request));
        setDefaultDate(searchVO);

        List<SalesPGHistVO> list = salesSettleService.selectPGHistory(searchVO);

        String templete = "pgAcceptList";
        String file_name = "거래승인현황_목록";

        //엑셀 데이터 변환 시 사용 되는 data
        Map data = new HashMap();
        data.put("list", list);

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

    @GetMapping("/discountList")
    @PageActionInfo(title = "할인사유별 수입금집계현황 조회", action = "R")
    public String viewDiscountList(@ModelAttribute("searchVO") SalesDiscountVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        int totCnt = 0;
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setMyOrgList(getMyOrgList(request));
        setDefaultDate(searchVO);

        SalesDiscountVO total = salesSettleService.selectDiscountedTotal(searchVO);
        List<SalesDiscountVO> list = salesSettleService.selectDiscounted(searchVO);
        if (total == null) {
            total = new SalesDiscountVO();
        }

        if (list != null && !list.isEmpty()) {
            totCnt = ((SalesDiscountVO) list.get(0)).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }

        model.addAttribute("total", total);
        model.addAttribute("list", list);
        model.addAttribute("orgList", getOrgList(request));
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);
        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/sales/salesDiscountList");
    }


    @PageActionInfo(title = "할인사유별 수입금집계현황 엑셀다운로드", action = "R")
    @GetMapping(value = {"/discountListExcel"})
    public void discountListExcel(@ModelAttribute("searchVO") SalesDiscountVO searchVO,  CommandMap commandMap, HttpServletResponse response,
            HttpServletRequest request, ModelMap model) throws Exception {

    	searchVO.setExcelyn("Y");
    	searchVO.setMyOrgList(getMyOrgList(request));
        setDefaultDate(searchVO);

        SalesDiscountVO total = salesSettleService.selectDiscountedTotal(searchVO);
        List<SalesDiscountVO> list = salesSettleService.selectDiscounted(searchVO);

        String templete = "discountList";
        String file_name = "할인사유별_수입금집계현황_목록";

        //엑셀 데이터 변환 시 사용 되는 data
        Map data = new HashMap();
        data.put("list", list);
        data.put("total", total);

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

    @GetMapping("/discountDetailAjax")
    @PageActionInfo(title = "할인사유별 수입금집계현황 상세 조회", action = "R")
    public String viewDiscountDetail(@ModelAttribute("searchVO") SalesReceiptVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setRecordCountPerPage(9999999);
        searchVO.setMyOrgList(getMyOrgList(request));
        searchVO.setPaginationInfo(paginationInfo);
        // setDefaultDate(searchVO);

        List<SalesReceiptVO> list = salesSettleService.selectReceiptList(searchVO);

        // model.addAttribute("total", total);
        model.addAttribute("list", list);
        model.addAttribute("orgList", getOrgList(request));
        model.addAttribute("commandMap", commandMap);
        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/sales/salesDiscountDetailAjax");
    }


    @GetMapping("/receiptDetail")
    @PageActionInfo(title = "영수증거래내역 조회", action = "R")
    public String receiptDetail(@ModelAttribute("searchVO") SalesReceiptVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

    	SalesReceiptVO detailVO = salesSettleService.selectReceiptDetail(searchVO);

    	List<SalesReceiptVO> list = salesSettleService.selectReceiptList(searchVO);

    	SalesRefundVO refundVO = new SalesRefundVO();
    	refundVO.setReceiptNo(searchVO.getReceiptNo());

    	List<SalesRefundVO> refundList = salesSettleService.selectRefundList(refundVO);

        model.addAttribute("detailVO",detailVO);
        model.addAttribute("list",list);
        model.addAttribute("refundList",refundList);
        model.addAttribute("commandMap", commandMap);
        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/sales/receiptDetail");
    }

    @PageActionInfo(title = "할인사유별 수입금집계현황 엑셀다운로드", action = "R")
    @GetMapping(value = {"/discountDetailExcel"})
    public void discountDetailExcel(@ModelAttribute("searchVO") SalesReceiptVO searchVO,  CommandMap commandMap, HttpServletResponse response,
            HttpServletRequest request, ModelMap model) throws Exception {

    	searchVO.setExcelyn("Y");
    	searchVO.setMyOrgList(getMyOrgList(request));
        setDefaultDate(searchVO);

        List<SalesReceiptVO> list = salesSettleService.selectReceiptList(searchVO);

        String templete = "discountDetail";
        String file_name = "할인사유별_수입금집계현황_상세목록";

        //엑셀 데이터 변환 시 사용 되는 data
        Map data = new HashMap();
        data.put("list", list);

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

    
    
    @GetMapping("/centerInOutList")
    @PageActionInfo(title = "주민자치센터 수입지출내역", action = "R")
    public String viewCenterInOutList(@ModelAttribute("searchVO") SalesDiscountVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        int totCnt = 0;
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setMyOrgList(getMyOrgList(request));
        setDefaultDate(searchVO);

        SalesDiscountVO total = salesSettleService.selectDiscountedTotal(searchVO);
        List<SalesDiscountVO> list = salesSettleService.selectDiscounted(searchVO);
        if (total == null) {
            total = new SalesDiscountVO();
        }

        if (list != null && !list.isEmpty()) {
            totCnt = ((SalesDiscountVO) list.get(0)).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }

        model.addAttribute("total", total);
        model.addAttribute("list", list);
        model.addAttribute("orgList", getOrgList(request));
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);
        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/sales/centerInOutList");
    }

    
    private void setDefaultDate(ComDefaultVO vo) {
        String startDt = vo.getSearchStartDts();
        String endDt = vo.getSearchEndDts();

        if ((startDt == null || startDt.isEmpty()) && (endDt == null || endDt.isEmpty())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(Calendar.getInstance().getTime());
            vo.setSearchStartDts(today);
            vo.setSearchEndDts(today);
        }
    }

    private List<String> getMyOrgList(HttpServletRequest request) {
        Object _userSession = request.getSession().getAttribute(Config.USER_SESSION);
        List<String> myOrg = null;

        if (_userSession != null) {
            myOrg = ((UserSession) _userSession).getMyOrgList();
        }

        return myOrg;
    }

    private List<OrgInfoVO> getOrgList(HttpServletRequest request) {
        return getOrgList(getMyOrgList(request));
    }

    private List<OrgInfoVO> getOrgList(List<String> myOrg) {
        List<OrgInfoVO> orgList = null;

        OrgInfoVO orgInfoVO = new OrgInfoVO();
        orgInfoVO.setComcd(Config.COM_CD);
        orgInfoVO.setMyOrgList(myOrg);

        orgList = (List<OrgInfoVO>) orgInfoService.selectOrgInfoList(orgInfoVO);

        return orgList;
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
}
