package com.hisco.admin.edcrsvn.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.eduadm.service.EduAdmService;
import com.hisco.admin.log.service.LogService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.annotation.PageActionType;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.cmm.util.ResponseUtil;
import com.hisco.cmm.util.StringUtil;
import com.hisco.intrfc.sale.service.PayListService;
import com.hisco.intrfc.sale.service.SaleChargeService;
import com.hisco.intrfc.sale.vo.PayMethodVO;
import com.hisco.intrfc.sale.vo.PaySummaryVO;
import com.hisco.user.edcatnlc.service.EdcProgramService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;
import com.hisco.user.mypage.service.MyRsvnService;
import com.hisco.user.mypage.vo.MyRsvnVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * 수강접수현황
 *
 * @author
 * @since
 * @version
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}/edcrsvn", "#{dynamicConfig.managerRoot}/edcrsvn" })
public class EdcAdmRsvnInfoController {

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "logService")
    private LogService logService;

    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

    @Resource(name = "eduAdmService")
    private EduAdmService eduAdmService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    @Resource(name = "myRsvnService")
    private MyRsvnService myRsvnService;

    @Resource(name = "saleChargeService")
    private SaleChargeService saleChargeService;

    @Resource(name = "payListService")
    private PayListService payListService;

    @PageActionInfo(title = "강좌신청회원현황", action = PageActionType.READ, inqry = true)
    @GetMapping("/edcRsvnInfoList")
    public String edcRsvnInfoList(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        searchVO.setChannel(Constant.CM_CHANNEL_BO);
        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(searchVO);

        if (detailVO == null)
            HttpUtility.sendBack(request, response, "유효하지 않는 강좌 입니다.");

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("detailVO", detailVO);
        model.addAttribute("referer", request.getHeader("Referer"));

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "강좌접수 현황", action = "R")
    @GetMapping(value = { "/edcRsvnInfoListAjax" })
    public String edcRsvnInfoListAjax(@ModelAttribute("searchVO") EdcRsvnInfoVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setRecordCountPerPage(searchVO.getRecordCountPerPage());
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setChannel(Constant.CM_CHANNEL_BO);

        if ("edcRsvnCustnm".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_CUST_NM_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_CUST_NM_ASC");
            }
        } else if ("id".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_ID_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_ID_ASC");
            }
        } else if ("edcReqDate".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_REQ_DATE_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_REQ_DATE_ASC");
            }
        } else if ("waitEnddate".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_WAIT_DATE_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_WAIT_DATE_ASC");
            }
        } else if ("edcStat".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_STAT_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_STAT_ASC");
            }
        } else if ("tel".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_TEL_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_TEL_ASC");
            }
        } else {
            if ("asc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_RSVN_NO_ASC");
            } else {
                searchVO.setSearchOrderBy("BY_RSVN_NO_DESC");
            }
        }

        if (Config.YES.equalsIgnoreCase((String) commandMap.getParam().get("exceldown"))) {
            searchVO.setUsePagingYn(Config.NO);
        }

        List<EdcRsvnInfoVO> rsvnInfoList = edcRsvnInfoService.selectRsvnList(searchVO);

        int totCount = 0;
        if (rsvnInfoList != null && rsvnInfoList.size() >= 1) {
            totCount = ((EdcRsvnInfoVO) rsvnInfoList.get(0)).getTotCount();
        }

        paginationInfo.setTotalRecordCount(totCount);

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("rsvnInfoList", rsvnInfoList);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("totCount", totCount);
        model.addAttribute("exceldown", (String) commandMap.getParam().get("exceldown"));

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "강좌신청상세", action = PageActionType.READ)
    @GetMapping("/edcRsvnInfoDetail")
    public String edcRsvnInfoDetail(@ModelAttribute("searchVO") MyRsvnVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdcRsvnInfoVO rsvnInfo = myRsvnService.selectMyEdcRsvnDtl(searchVO);
        if (rsvnInfo == null)
            ResponseUtil.SendMessage(request, response, "조회결과가 존재하지 않습니다.", "history.back()");

        // 기관 설정 정보 가져오기
        PaySummaryVO paySummary = new PaySummaryVO();
        paySummary.setComcd(rsvnInfo.getComcd());
        paySummary.setEdcRsvnReqid(rsvnInfo.getEdcRsvnReqid());
        paySummary = saleChargeService.selectPaySummary(paySummary);

        model.addAttribute("rsvnInfo", rsvnInfo);
        model.addAttribute("paySummary", paySummary);

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "환불취소", action = PageActionType.READ, ajax = true)
    @GetMapping("/edcRsvnPartialCancelAjax")
    public String edcRsvnPartialCancelAjax(EdcRsvnInfoVO searchVO, CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        searchVO.setChannel(Constant.CM_CHANNEL_BO);
        searchVO.setEdcStat(null);
        List<EdcRsvnInfoVO> rsvnList = edcRsvnInfoService.selectRsvnList(searchVO);
        if (rsvnList == null)
            ResponseUtil.SendMessage(request, response, "예약내역이 존재하지 않습니다.", "location.reload()");

        // 결제정보
        for (EdcRsvnInfoVO rsvnInfo : rsvnList) {
            PaySummaryVO paySummary = new PaySummaryVO();
            paySummary.setComcd(rsvnInfo.getComcd());
            paySummary.setEdcRsvnReqid(rsvnInfo.getEdcRsvnReqid());
            rsvnInfo.setPaySummary(saleChargeService.selectPaySummary(paySummary));
            if (rsvnInfo.getCancelAmt() < 0) {
                rsvnInfo.setCancelHistory(saleChargeService.selectCancelHistory(paySummary));
            }

            rsvnInfo.setLectDateList(edcRsvnInfoService.selectLectDateList(rsvnInfo));
        }

        PayMethodVO pmParam = new PayMethodVO();
        pmParam.setPComcd(Constant.PG_TOSS);
        pmParam.setPType(Constant.SITE_P_TYPE_실시간계좌이체);
        model.addAttribute("bankList", payListService.selectPayMethodList(pmParam));

        model.addAttribute("rsvnInfoList", rsvnList);
        model.addAttribute("rsvnInfoListJson", JsonUtil.Object2String(rsvnList));
        model.addAttribute("today", DateUtil.getTodate("yyyy-MM-dd"));

        return Config.ADMIN_ROOT + "/edcrsvn/modalPartialCancelAjax";
    }

    @PageActionInfo(title = "수강등록 회원현황", action = PageActionType.READ)
    @GetMapping("/studentList")
    public String studentList(@ModelAttribute("prgmVO") EdcProgramVO prgmVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        model.addAttribute("commandMap", commandMap);

        if (StringUtils.isNotBlank(prgmVO.getSearchOrgNo())) {
            model.addAttribute("rsvnsetNmList", edcProgramService.selectRsvnSetNmList(Integer.parseInt(prgmVO.getSearchOrgNo())));
        }

        return HttpUtility.getViewUrl(request);
    }

    @GetMapping(value = { "/studentMemListAjax" })
    public String studentMemListAjax(@ModelAttribute("searchVO") EdcRsvnInfoVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String showColumnList = commandMap.getString("showColumnList");

        if (showColumnList.equals("")) {
            showColumnList = "edcOnoffintype|edcPrgmnm|edcDaygbnNm|edcMemNo|edcRsvnCustnm|id|edcRsvnMoblphon|edcSdate|edcEdate|cancelDtime|edcStatNm|payAmt";
        }

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setRecordCountPerPage(searchVO.getRecordCountPerPage());
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setChannel(Constant.CM_CHANNEL_BO);

        if ("edcRsvnCustnm".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_CUST_NM_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_CUST_NM_ASC");
            }
        } else if ("id".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_ID_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_ID_ASC");
            }
        } else if ("edcReqDate".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_REQ_DATE_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_REQ_DATE_ASC");
            }
        } else if ("edcOnoffintype".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_ONOFF_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_ONOFF_ASC");
            }
        } else if ("edcStatNm".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_STAT_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_STAT_ASC");
            }
        } else if ("edcRsvnMoblphon".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_TEL_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_TEL_ASC");
            }
        } else if ("edcMemNo".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_MEMNO_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_MEMNO_ASC");
            }
        } else if ("cancelDtime".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_CANCEL_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_CANCEL_ASC");
            }
        } else if ("payMethodNm".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_PAYMETHOD_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_PAYMETHOD_ASC");
            }
        } else {
            if ("asc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_RSVN_NO_ASC");
            } else {
                searchVO.setSearchOrderBy("BY_RSVN_NO_DESC");
            }
        }

        String exceldown = commandMap.getString("exceldown");
        if (Config.YES.equalsIgnoreCase(exceldown)) {
            searchVO.setUsePagingYn(Config.NO);
        }

        List<EdcRsvnInfoVO> rsvnInfoList = edcRsvnInfoService.selectRsvnList(searchVO);
        List<HashMap> newList = new ArrayList<HashMap>();

        int totCount = 0;
        if (rsvnInfoList != null && rsvnInfoList.size() >= 1) {
            totCount = ((EdcRsvnInfoVO) rsvnInfoList.get(0)).getTotCount();

            Class cs = Class.forName("com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO");

            int i = 0;
            for (EdcRsvnInfoVO vo : rsvnInfoList) {
                HashMap map = new HashMap();

                for (String record : showColumnList.split("[|]")) {
                    String value = "";
                    if (record.equals("edcReqDate")) {
                        value = vo.getEdcReqDate() + vo.getEdcReqTime();
                        value = DateUtil.printDatetime(DateUtil.string2date(value, "yyyyMMddHHmmss"), "yyyy.MM.dd HH:mm");
                    } else if (record.equals("payDate")) {
                        if (!StringUtil.IsEmpty(vo.getPayDate())) {
                            value = vo.getPayDate() + vo.getPayTime();
                            value = DateUtil.printDatetime(DateUtil.string2date(value, "yyyyMMddHHmmss"), "yyyy.MM.dd HH:mm");
                        }
                    } else if (record.equals("edcHomeAddr")) {
                        if (vo.getEdcHomeAddr1() != null) {
                            value = vo.getEdcHomeAddr1() + " " + vo.getEdcHomeAddr2();
                        }
                    } else {
                        Method mDao = cs.getDeclaredMethod("get" + StringUtil.upperCaseFirst(record));
                        Object obj = mDao.invoke(vo);
                        if (obj != null) {
                            value = obj.toString();
                        }

                        if (record.equals("cancelDtime") && !value.equals("")) {
                            value = DateUtil.printDatetime(DateUtil.string2date(value, "yyyyMMddHHmmss"), "yyyy.MM.dd HH:mm");
                        } else if (record.equals("edcSdate") || record.equals("edcEdate")) {
                            value = DateUtil.printDatetime(DateUtil.string2date(value, "yyyyMMdd"), "yyyy.MM.dd");
                        } else if (record.equals("edcProgmCost") || record.equals("edcDcamt") || record.equals("cancelAmt") || record.equals("cancelAmt") || record.equals("payAmt")) {
                            if (!StringUtil.IsEmpty(value)) {
                                value = NumberFormat.getInstance(Locale.US).format(Integer.parseInt(value));
                            }
                        }
                    }

                    map.put(record, value);
                }

                String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";

                map.put("edcStat", vo.getEdcStat());
                map.put("payAmt", NumberFormat.getInstance(Locale.US).format(vo.getPayAmt()));
                map.put("payAmount", vo.getPayAmt());
                map.put("edcStatNm", vo.getEdcStatNm());
                map.put("edcMemNo", vo.getEdcMemNo());
                map.put("edcRsvnCustnm", vo.getEdcRsvnCustnm());
                map.put("edcRsvnMoblphon", StringUtil.IsEmpty(vo.getEdcRsvnMoblphon())
                        ? "" : vo.getEdcRsvnMoblphon().replaceAll(regEx, "$1-$2-$3"));
                map.put("smsYn", vo.getSmsYn());
                map.put("edcRsvnNo", vo.getEdcRsvnNo());
                map.put("edcRsvnReqid", vo.getEdcRsvnReqid());
                map.put("payCancelYn", vo.getPayCancelYn());
                map.put("tid", vo.getTid());
                map.put("editYn", vo.getEditYn());
                map.put("orgTel", vo.getOrgTel());

                newList.add(i++, map);

            }
        }

        paginationInfo.setTotalRecordCount(totCount);

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("newList", newList);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("totCount", totCount);
        model.addAttribute("showColumnList", showColumnList);

        if (StringUtils.isNotBlank(exceldown)) {
            List<String> thList = new ArrayList<String>();
            for (String record : showColumnList.split("[|]")) {
                thList.add(TH_MAP.get(record));
            }
            model.addAttribute("exceldown", exceldown);
            model.addAttribute("thList", thList);
        }

        return HttpUtility.getViewUrl(request);
    }

    @RequestMapping(value = "/studentPrgListAjax")
    public String edcProgramReceptionListAjax(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setRecordCountPerPage(1000);
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setChannel(Constant.CM_CHANNEL_BO);
        searchVO.setSearchTab("CATE");

        if ("programNm".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_PROGRAM_NM_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_PROGRAM_NM_ASC");
            }
        } else if ("apply".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_APPLY_CLOSE_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_APPLY_CLOSE");
            }
        } else if ("rsvnType".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_RSVN_TYPE_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_RSVN_TYPE_ASC");
            }
        } else if ("cateSort".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_CATE_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_CATE_ASC");
            }
        }

        List<EdcProgramVO> programList = null;

        if (!StringUtil.IsEmpty(searchVO.getSearchOrgNo())) {
            programList = edcProgramService.selectProgramList(searchVO);
        }

        model.addAttribute("programList", programList);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    @PostMapping("/studentMemCompUpdate.json")
    @PageActionInfo(title = "수강회원 수료처리", action = "U", ajax = true)
    @ResponseBody
    public ModelAndView studentMemCompUpdate(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        try {
            if (request.getParameterValues("rsvnReqid") == null) {
                resultInfo = HttpUtility.getErrorResultInfo("수료처리 가능 대상이 없습니다.");
            } else {
                int result = edcRsvnInfoService.updateRsvnInfoComplstat(Config.COM_CD, user.getId(), request.getParameterValues("rsvnReqid"));

                if (result > 0)
                    resultInfo = HttpUtility.getSuccessResultInfo("수료처리가 완료되었습니다.");
                else
                    resultInfo = HttpUtility.getErrorResultInfo("수료처리 가능 대상이 없습니다.");
            }

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo(e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @PageActionInfo(title = "수강신청회원 통합검색", action = PageActionType.READ)
    @GetMapping("/totalRsvnList")
    public String totalRsvnList(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String showColumnList = commandMap.getString("showColumnList");

        if (showColumnList.equals("")) {
            showColumnList = "edcOnoffintype|edcPrgmnm|edcRsvnsetNm|edcDaygbnNm|edcMemNo|edcRsvnCustnm|id|edcRsvnMoblphon|edcReqDate|edcPaywaitEnddatetime|edcStatNm|payAmt";
        }

        model.addAttribute("commandMap", commandMap);
        model.addAttribute("showColumnList", showColumnList);

        if (StringUtils.isNotBlank(searchVO.getSearchOrgNo())) {
            model.addAttribute("rsvnsetNmList", edcProgramService.selectRsvnSetNmList(Integer.parseInt(searchVO.getSearchOrgNo())));
        }

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "수강신청회원 엑셀다운로드", action = PageActionType.READ)
    @GetMapping(value = { "/totalRsvnListAjax" })
    public String totalRsvnListAjax(@ModelAttribute("searchVO") EdcRsvnInfoVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String showColumnList = commandMap.getString("showColumnList");

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setRecordCountPerPage(searchVO.getRecordCountPerPage());
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setChannel(Constant.CM_CHANNEL_BO);

        if ("edcRsvnCustnm".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_CUST_NM_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_CUST_NM_ASC");
            }
        } else if ("id".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_ID_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_ID_ASC");
            }
        } else if ("edcReqDate".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_REQ_DATE_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_REQ_DATE_ASC");
            }
        } else if ("edcOnoffintype".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_ONOFF_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_ONOFF_ASC");
            }
        } else if ("edcStatNm".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_STAT_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_STAT_ASC");
            }
        } else if ("edcRsvnMoblphon".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_TEL_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_TEL_ASC");
            }
        } else if ("edcMemNo".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_MEMNO_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_MEMNO_ASC");
            }
        } else if ("cancelDtime".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_CANCEL_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_CANCEL_ASC");
            }
        } else if ("payMethodNm".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_PAYMETHOD_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_PAYMETHOD_ASC");
            }
        } else if ("edcPaywaitEnddatetime".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_WAITTIME_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_WAITTIME_ASC");
            }
        } else {
            if ("asc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_RSVN_NO_ASC");
            } else {
                searchVO.setSearchOrderBy("BY_RSVN_NO_DESC");
            }
        }

        String exceldown = commandMap.getString("exceldown");
        if (Config.YES.equalsIgnoreCase(exceldown)) {
            searchVO.setUsePagingYn(Config.NO);
        }

        if (!StringUtil.IsEmpty(searchVO.getSearchOrgNo())) {
            List<EdcRsvnInfoVO> rsvnInfoList = edcRsvnInfoService.selectRsvnList(searchVO);
            List<HashMap> newList = new ArrayList<HashMap>();

            int totCount = 0;
            if (rsvnInfoList != null && rsvnInfoList.size() >= 1) {
                totCount = ((EdcRsvnInfoVO) rsvnInfoList.get(0)).getTotCount();

                Class cs = Class.forName("com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO");

                int i = 0;
                for (EdcRsvnInfoVO vo : rsvnInfoList) {
                    HashMap map = new HashMap();

                    for (String record : showColumnList.split("[|]")) {
                        String value = "";
                        if (record.equals("edcReqDate")) {
                            value = vo.getEdcReqDate() + vo.getEdcReqTime();
                            value = DateUtil.printDatetime(DateUtil.string2date(value, "yyyyMMddHHmmss"), "yyyy.MM.dd HH:mm");
                        } else if (record.equals("payDate")) {
                            if (!StringUtil.IsEmpty(vo.getPayDate())) {
                                value = vo.getPayDate() + vo.getPayTime();
                                value = DateUtil.printDatetime(DateUtil.string2date(value, "yyyyMMddHHmmss"), "yyyy.MM.dd HH:mm");
                            }
                        } else if (record.equals("edcPaywaitEnddatetime")) {
                            value = String.valueOf(vo.getEdcPaywaitEnddatetime());
                            if (vo.getEdcPaywaitEnddatetime() != null && !value.equals("") && !value.equals("null") && value.length() >= 16) {
                                value = DateUtil.printDatetime(DateUtil.string2date(value.substring(0, 16), "yyyy-MM-dd HH:mm"), "yyyy.MM.dd HH:mm");
                            } else {
                                value = "";
                            }
                        } else if (record.equals("edcHomeAddr")) {
                            if (vo.getEdcHomeAddr1() != null) {
                                value = vo.getEdcHomeAddr1() + " " + vo.getEdcHomeAddr2();
                            }
                        } else {
                            Method mDao = cs.getDeclaredMethod("get" + StringUtil.upperCaseFirst(record));
                            Object obj = mDao.invoke(vo);
                            if (obj != null) {
                                value = obj.toString();
                            }

                            if (record.equals("cancelDtime") && !value.equals("")) {
                                value = DateUtil.printDatetime(DateUtil.string2date(value, "yyyyMMddHHmmss"), "yyyy.MM.dd HH:mm");
                            } else if (record.equals("edcSdate") || record.equals("edcEdate")) {
                                value = DateUtil.printDatetime(DateUtil.string2date(value, "yyyyMMdd"), "yyyy.MM.dd");

                            }
                        }

                        map.put(record, value);
                    }

                    String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";

                    map.put("edcStat", vo.getEdcStat());
                    map.put("payAmount", vo.getPayAmt());
                    map.put("edcStatNm", vo.getEdcStatNm());
                    map.put("edcMemNo", vo.getEdcMemNo());
                    map.put("edcRsvnCustnm", vo.getEdcRsvnCustnm());
                    map.put("edcRsvnMoblphon", StringUtil.IsEmpty(vo.getEdcRsvnMoblphon())
                            ? "" : vo.getEdcRsvnMoblphon().replaceAll(regEx, "$1-$2-$3"));
                    map.put("smsYn", vo.getSmsYn());
                    map.put("edcRsvnNo", vo.getEdcRsvnNo());
                    map.put("edcRsvnReqid", vo.getEdcRsvnReqid());
                    map.put("payCancelYn", vo.getPayCancelYn());
                    map.put("tid", vo.getTid());
                    map.put("editYn", vo.getEditYn());
                    map.put("cancelAmt", vo.getCancelAmt());
                    map.put("orgTel", vo.getOrgTel());

                    newList.add(i++, map);
                }
            }

            paginationInfo.setTotalRecordCount(totCount);
            model.addAttribute("newList", newList);
            model.addAttribute("totCount", totCount);
        }

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("showColumnList", showColumnList);

        if (StringUtils.isNotBlank(exceldown)) {
            List<String> thList = new ArrayList<String>();
            for (String record : showColumnList.split("[|]")) {
                thList.add(TH_MAP.get(record));
            }
            model.addAttribute("exceldown", exceldown);
            model.addAttribute("thList", thList);
        }

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "수강신청회원통합검색 엑셀다운로드", action = PageActionType.READ)
    @GetMapping(value = { "/totalRsvnListExcel", "/studentListExcel" })
    public void totalRsvnListExcel(@ModelAttribute("searchVO") EdcRsvnInfoVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String showColumnList = commandMap.getString("showColumnList");
        searchVO.setChannel(Constant.CM_CHANNEL_BO);
        searchVO.setUsePagingYn(Config.NO);

        if ("edcRsvnCustnm".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_CUST_NM_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_CUST_NM_ASC");
            }
        } else if ("id".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_ID_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_ID_ASC");
            }
        } else if ("edcReqDate".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_REQ_DATE_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_REQ_DATE_ASC");
            }
        } else if ("edcOnoffintype".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_ONOFF_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_ONOFF_ASC");
            }
        } else if ("edcStatNm".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_STAT_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_STAT_ASC");
            }
        } else if ("edcRsvnMoblphon".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_TEL_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_TEL_ASC");
            }
        } else if ("edcMemNo".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_MEMNO_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_MEMNO_ASC");
            }
        } else if ("cancelDtime".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_CANCEL_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_CANCEL_ASC");
            }
        } else if ("payMethodNm".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_PAYMETHOD_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_PAYMETHOD_ASC");
            }
        } else if ("edcPaywaitEnddatetime".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_WAITTIME_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_WAITTIME_ASC");
            }
        } else {
            if ("asc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_RSVN_NO_ASC");
            } else {
                searchVO.setSearchOrderBy("BY_RSVN_NO_DESC");
            }
        }

        List<HashMap> newList = new ArrayList<HashMap>();
        List<EdcRsvnInfoVO> rsvnInfoList = null;

        if (!StringUtil.IsEmpty(searchVO.getSearchOrgNo()) || searchVO.getEdcPrgmNo() > 0) {
            rsvnInfoList = edcRsvnInfoService.selectRsvnList(searchVO);
        }

        String requestUrl = request.getRequestURI();
        String templete = "studentList";
        String file_name = "수강등록_회원현황_목록";

        if (requestUrl.indexOf("totalRsvn") > 0) {
            templete = "totalRsvnList";
            file_name = "수강신청회원_통합검색_목록";
        }

        // 엑셀 데이터 변환 시 사용 되는 data
        Map data = new HashMap();
        data.put("list", rsvnInfoList);

        XLSTransformer transformer = new XLSTransformer();
        InputStream is = readTemplate(templete + ".xls");
        Workbook workbook = null;
        try {
            workbook = transformer.transformXLS(is, data);

            Sheet sh = workbook.getSheetAt(0);
            Row titleRow = sh.getRow(0);
            CellStyle headStyle = titleRow.getCell(0).getCellStyle();

            int k = 1;
            for (String record : showColumnList.split("[|]")) {
                Cell cell = titleRow.createCell(k++);
                cell.setCellStyle(headStyle);
                cell.setCellValue(TH_MAP.get(record));
            }
            // 형식에 맞게 데이타 변환
            if (rsvnInfoList != null && rsvnInfoList.size() >= 1) {
                Class cs = Class.forName("com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO");

                int rowno = 1; // 2 라인 부터 시작
                for (EdcRsvnInfoVO vo : rsvnInfoList) {
                    HashMap map = new HashMap();

                    for (String record : showColumnList.split("[|]")) {
                        String value = "";
                        if (record.equals("edcReqDate")) {
                            value = vo.getEdcReqDate() + vo.getEdcReqTime();
                            value = DateUtil.printDatetime(DateUtil.string2date(value, "yyyyMMddHHmmss"), "yyyy.MM.dd HH:mm");
                        } else if (record.equals("payDate")) {
                            if (!StringUtil.IsEmpty(vo.getPayDate())) {
                                value = vo.getPayDate() + vo.getPayTime();
                                value = DateUtil.printDatetime(DateUtil.string2date(value, "yyyyMMddHHmmss"), "yyyy.MM.dd HH:mm");
                            }
                        } else if (record.equals("edcPaywaitEnddatetime")) {
                            value = String.valueOf(vo.getEdcPaywaitEnddatetime());
                            if (vo.getEdcPaywaitEnddatetime() != null && !value.equals("") && !value.equals("null") && value.length() >= 16) {
                                value = DateUtil.printDatetime(DateUtil.string2date(value.substring(0, 16), "yyyy-MM-dd HH:mm"), "yyyy.MM.dd HH:mm");
                            } else {
                                value = "";
                            }
                        } else if (record.equals("edcHomeAddr")) {
                            if (vo.getEdcHomeAddr1() != null) {
                                value = vo.getEdcHomeAddr1() + " " + vo.getEdcHomeAddr2();
                            }
                        } else {
                            Method mDao = cs.getDeclaredMethod("get" + StringUtil.upperCaseFirst(record));
                            Object obj = mDao.invoke(vo);
                            if (obj != null) {
                                value = obj.toString();
                            }

                            if (record.equals("cancelDtime") && !value.equals("")) {
                                value = DateUtil.printDatetime(DateUtil.string2date(value, "yyyyMMddHHmmss"), "yyyy.MM.dd HH:mm");
                            } else if (record.equals("edcSdate") || record.equals("edcEdate")) {
                                value = DateUtil.printDatetime(DateUtil.string2date(value, "yyyyMMdd"), "yyyy.MM.dd");

                            }
                        }

                        map.put(record, value);
                    }

                    String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";

                    map.put("edcStat", vo.getEdcStat());
                    map.put("payAmount", vo.getPayAmt());
                    map.put("edcStatNm", vo.getEdcStatNm());
                    map.put("edcMemNo", vo.getEdcMemNo());
                    map.put("edcRsvnCustnm", vo.getEdcRsvnCustnm());
                    map.put("edcRsvnMoblphon", StringUtil.IsEmpty(vo.getEdcRsvnMoblphon())
                            ? "" : vo.getEdcRsvnMoblphon().replaceAll(regEx, "$1-$2-$3"));
                    map.put("smsYn", vo.getSmsYn());
                    map.put("edcRsvnNo", vo.getEdcRsvnNo());
                    map.put("edcRsvnReqid", vo.getEdcRsvnReqid());
                    map.put("payCancelYn", vo.getPayCancelYn());
                    map.put("tid", vo.getTid());

                    k = 1;
                    Row dataRow = sh.getRow(rowno++);
                    CellStyle cellStyle = dataRow.getCell(0).getCellStyle();
                    for (String record : showColumnList.split("[|]")) {
                        Cell cell = dataRow.createCell(k++);
                        cell.setCellStyle(cellStyle);

                        if (record.equals("payAmt")) {
                            cell.setCellValue(vo.getPayAmt());
                        } else if (record.endsWith("amt") || record.endsWith("Cost") || record.endsWith("Amt")) {
                            try {
                                cell.setCellValue(Integer.getInteger(String.valueOf(map.get(record))));
                            } catch (Exception e) {
                                cell.setCellValue(0);
                            }

                        } else {
                            cell.setCellValue(String.valueOf(map.get(record)));
                        }

                    }

                }
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            response.getWriter().println("엑셀 데이터 변환 시 에러 발생<br>" + e.getMessage());
        }

        // 엑셀 데이터 contentType 정의
        response.setContentType("application/vnd.ms-excel");

        // 엑셀 파일명 설정
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName(file_name + ".xls"));
        workbook.write(response.getOutputStream());
    }

    @GetMapping(value = { "/studentBookExcel" })
    public void studentListExcel(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 교육프로그램 정보
        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(searchVO);
        // 교육일정 날짜 목록
        List<EgovMap> dateList = eduAdmService.selectClassSchedule(detailVO);

        List<HashMap> newList = new ArrayList<HashMap>();
        List<EdcRsvnInfoVO> rsvnInfoList = null;

        // 수강생목록
        EdcRsvnInfoVO rsvnVO = new EdcRsvnInfoVO();
        rsvnVO.setUsePagingYn(Config.NO);
        rsvnVO.setEdcPrgmNo(searchVO.getEdcPrgmNo());
        rsvnVO.setEdcRsvnsetSeq(searchVO.getEdcRsvnsetSeq());
        rsvnVO.setChannel("BO");
        rsvnVO.setEdcStat("");

        rsvnInfoList = edcRsvnInfoService.selectRsvnList(rsvnVO);

        String templete = "attendanceBook";
        String file_name = "수강등록_출석부_" + detailVO.getEdcPrgmnm() + "_" + commandMap.getString("showYear") + commandMap.getString("showMonth");

        // 형식에 맞게 데이타 변환
        int rowno = 0;
        if (rsvnInfoList != null && rsvnInfoList.size() >= 1) {
            Class cs = Class.forName("com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO");

            for (EdcRsvnInfoVO vo : rsvnInfoList) {
                HashMap map = new HashMap();

                String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
                String hp = vo.getEdcRsvnMoblphon();
                if (!StringUtil.IsEmpty(hp)) {
                    try {
                        hp = vo.getEdcRsvnMoblphon().replaceAll(regEx, "$1-$2-$3");
                    } catch (Exception e) {
                        // 핸드폰 오류
                    }
                }

                // 나이 계산
                int age = 0;

                String birthDate = vo.getEdcRsvnBirthdate();
                if (!StringUtils.isBlank(birthDate) && birthDate.length() >= 4) {
                    int birthYear = Integer.parseInt(birthDate.substring(0, 4));
                    int nowYear = Integer.parseInt(DateUtil.getTodate("yyyy"));
                    age = nowYear - birthYear + 1;
                }

                String gender = vo.getEdcRsvnGender();
                if ("1".equals(gender)) {
                    gender = "남성";
                } else if ("2".equals(gender)) {
                    gender = "여성";
                }

                map.put("edcRsvnCustnm", vo.getEdcRsvnCustnm());
                map.put("age", age);
                map.put("gender", gender);
                map.put("hp", hp);

                String cancelTime = vo.getCancelDtime();
                boolean refundCheck = false;

                if (!StringUtil.IsEmpty(cancelTime) && Long.parseLong(cancelTime) > Long.parseLong(detailVO.getEdcSdate() + detailVO.getEdcStime() + "00")) {
                    refundCheck = true;
                }

                // 등록완료 상태이거나 중도환불자 포함이고 취소일이 교육시작일보다 큰경우
                if ("4001".equals(vo.getEdcStat()) || (commandMap.getString("showRefundYn").equals("Y") && "3004".equals(vo.getEdcStat()) && refundCheck)) {
                    // 중도환불자 제외
                    newList.add(rowno, map);
                    rowno++;
                }
            }
        }

        // 엑셀 데이터 변환 시 사용 되는 data
        detailVO.setEdcSdate(DateUtil.DateCheck(detailVO.getEdcSdate(), "yyyy-MM-dd"));
        detailVO.setEdcEdate(DateUtil.DateCheck(detailVO.getEdcEdate(), "yyyy-MM-dd"));
        detailVO.setEdcRsvnSdate(DateUtil.DateCheck(detailVO.getEdcRsvnSdate(), "yyyy-MM-dd"));
        detailVO.setEdcRsvnEdate(DateUtil.DateCheck(detailVO.getEdcRsvnEdate(), "yyyy-MM-dd"));
        detailVO.setEdcStime(detailVO.getEdcStime().substring(0, 2) + ":" + detailVO.getEdcStime().substring(2, 4));
        detailVO.setEdcEtime(detailVO.getEdcEtime().substring(0, 2) + ":" + detailVO.getEdcEtime().substring(2, 4));
        detailVO.setStatApplyCnt(rowno);

        Map data = new HashMap();
        data.put("list", newList);
        data.put("data", detailVO);

        XLSTransformer transformer = new XLSTransformer();

        InputStream is = readTemplate(templete + ".xls");
        Workbook workbook = null;
        try {
            workbook = transformer.transformXLS(is, data);

            Sheet sh = workbook.getSheetAt(0);
            Row titleRow = sh.getRow(8);
            Row scheduleRow = sh.getRow(7);

            CellStyle headStyle = titleRow.getCell(0).getCellStyle();
            CellStyle headStyle2 = scheduleRow.getCell(0).getCellStyle();

            int k = 2; // 3번째 칸부터

            scheduleRow.getCell(0).setCellValue("");

            if (commandMap.getString("showHpYn").equals("Y")) {
                Cell cell = titleRow.createCell(k++);
                cell.setCellStyle(headStyle);
                cell.setCellValue("휴대폰 번호");
            }

            if (commandMap.getString("showAgeYn").equals("Y")) {
                Cell cell = titleRow.createCell(k++);
                cell.setCellStyle(headStyle);
                cell.setCellValue("나이");
            }

            if (commandMap.getString("showGenderYn").equals("Y")) {
                Cell cell = titleRow.createCell(k++);
                cell.setCellStyle(headStyle);
                cell.setCellValue("성별");
            }

            String yyyyMM = commandMap.getString("showYear") + "-" + commandMap.getString("showMonth");
            int sortNo = 0;

            for (EgovMap map : dateList) {
                sortNo++;

                if (map.get("dt").toString().startsWith(yyyyMM)) {
                    Cell cell = titleRow.createCell(k);
                    cell.setCellStyle(headStyle);
                    String dt = map.get("dt").toString();
                    dt = dt.substring(5) + "(" + map.get("weekName") + ")";
                    cell.setCellValue(dt);

                    Cell cell2 = scheduleRow.createCell(k++);
                    cell2.setCellStyle(headStyle2);
                    cell2.setCellValue(sortNo + "회차");

                }
            }

            rowno = 9; // 10번째 줄부터

            for (Map vo : newList) {
                k = 2; // 3번째 칸부터
                Row dataRow = sh.getRow(rowno++);
                CellStyle dataStyle = dataRow.getCell(1).getCellStyle();
                // CellStyle dataStyle2 = dataRow.getCell(0).getCellStyle();

                if (commandMap.getString("showHpYn").equals("Y")) {
                    Cell cell = dataRow.createCell(k++);
                    cell.setCellStyle(dataStyle);
                    cell.setCellValue(String.valueOf(vo.get("hp")));
                }
                if (commandMap.getString("showAgeYn").equals("Y")) {
                    Cell cell = dataRow.createCell(k++);
                    cell.setCellStyle(dataStyle);
                    cell.setCellValue(String.valueOf(vo.get("age")));
                }
                if (commandMap.getString("showGenderYn").equals("Y")) {
                    Cell cell = dataRow.createCell(k++);
                    cell.setCellStyle(dataStyle);
                    cell.setCellValue(String.valueOf(vo.get("gender")));
                }
            }

        } catch (InvalidFormatException e) {
            e.printStackTrace();
            response.getWriter().println("엑셀 데이터 변환 시 에러 발생<br>" + e.getMessage());
        }

        // 엑셀 데이터 contentType 정의
        response.setContentType("application/vnd.ms-excel");

        // 엑셀 파일명 설정
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

    // 수강생관리 > 수강등록 회원현황에서 가변헤더 설정을 위한 map
    private static Map<String, String> TH_MAP = new HashMap<String, String>();
    static {
        TH_MAP.put("edcOnoffintype", "접수경로");
        TH_MAP.put("edcReqDate", "등록일자");
        TH_MAP.put("edcPrgmnm", "프로그램명");
        TH_MAP.put("edcDaygbnNm", "수업요일");
        TH_MAP.put("edcSdate", "수업시작일");
        TH_MAP.put("edcEdate", "수업종료일");
        TH_MAP.put("edcMemNo", "회원번호");
        TH_MAP.put("edcRsvnCustnm", "회원명");
        TH_MAP.put("id", "ID");
        TH_MAP.put("edcRsvnMoblphon", "휴대폰번호");
        TH_MAP.put("genderNm", "성별");
        TH_MAP.put("edcRsvnBirthdate", "생년월일");
        TH_MAP.put("edcHomeAddr", "주소");
        TH_MAP.put("edcEmail", "이메일");
        TH_MAP.put("edcStatNm", "등록상태");
        TH_MAP.put("payMethodNm", "결제수단");
        TH_MAP.put("edcProgmCost", "수강료");
        TH_MAP.put("payAmt", "결제금액");
        TH_MAP.put("edcDcamt", "할인금액");
        TH_MAP.put("edcReasondc", "할인종류");
        TH_MAP.put("payDate", "결제일시");
        TH_MAP.put("oid", "주문번호");
        TH_MAP.put("receiptNo", "영수증번호");
        TH_MAP.put("cancelDtime", "취소일시");
        TH_MAP.put("cancelAmt", "환불금액");
        TH_MAP.put("retAcountNum", "환불계좌번호");
        TH_MAP.put("retDpstrNm", "환불계좌명");
        TH_MAP.put("retBankNm", "환불계좌은행명");
        TH_MAP.put("edcComplstat", "수료여부");
        TH_MAP.put("edcPaywaitEnddatetime", "결제마감일시");

    }

}
