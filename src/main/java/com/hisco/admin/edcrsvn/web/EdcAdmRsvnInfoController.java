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
 * ??????????????????
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

    @PageActionInfo(title = "????????????????????????", action = PageActionType.READ, inqry = true)
    @GetMapping("/edcRsvnInfoList")
    public String edcRsvnInfoList(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        searchVO.setChannel(Constant.CM_CHANNEL_BO);
        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(searchVO);

        if (detailVO == null)
            HttpUtility.sendBack(request, response, "???????????? ?????? ?????? ?????????.");

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("detailVO", detailVO);
        model.addAttribute("referer", request.getHeader("Referer"));

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "???????????? ??????", action = "R")
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

    @PageActionInfo(title = "??????????????????", action = PageActionType.READ)
    @GetMapping("/edcRsvnInfoDetail")
    public String edcRsvnInfoDetail(@ModelAttribute("searchVO") MyRsvnVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdcRsvnInfoVO rsvnInfo = myRsvnService.selectMyEdcRsvnDtl(searchVO);
        if (rsvnInfo == null)
            ResponseUtil.SendMessage(request, response, "??????????????? ???????????? ????????????.", "history.back()");

        // ?????? ?????? ?????? ????????????
        PaySummaryVO paySummary = new PaySummaryVO();
        paySummary.setComcd(rsvnInfo.getComcd());
        paySummary.setEdcRsvnReqid(rsvnInfo.getEdcRsvnReqid());
        paySummary = saleChargeService.selectPaySummary(paySummary);

        model.addAttribute("rsvnInfo", rsvnInfo);
        model.addAttribute("paySummary", paySummary);

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "????????????", action = PageActionType.READ, ajax = true)
    @GetMapping("/edcRsvnPartialCancelAjax")
    public String edcRsvnPartialCancelAjax(EdcRsvnInfoVO searchVO, CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        searchVO.setChannel(Constant.CM_CHANNEL_BO);
        searchVO.setEdcStat(null);
        List<EdcRsvnInfoVO> rsvnList = edcRsvnInfoService.selectRsvnList(searchVO);
        if (rsvnList == null)
            ResponseUtil.SendMessage(request, response, "??????????????? ???????????? ????????????.", "location.reload()");

        // ????????????
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
        pmParam.setPType(Constant.SITE_P_TYPE_?????????????????????);
        model.addAttribute("bankList", payListService.selectPayMethodList(pmParam));

        model.addAttribute("rsvnInfoList", rsvnList);
        model.addAttribute("rsvnInfoListJson", JsonUtil.Object2String(rsvnList));
        model.addAttribute("today", DateUtil.getTodate("yyyy-MM-dd"));

        return Config.ADMIN_ROOT + "/edcrsvn/modalPartialCancelAjax";
    }

    @PageActionInfo(title = "???????????? ????????????", action = PageActionType.READ)
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
    @PageActionInfo(title = "???????????? ????????????", action = "U", ajax = true)
    @ResponseBody
    public ModelAndView studentMemCompUpdate(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        try {
            if (request.getParameterValues("rsvnReqid") == null) {
                resultInfo = HttpUtility.getErrorResultInfo("???????????? ?????? ????????? ????????????.");
            } else {
                int result = edcRsvnInfoService.updateRsvnInfoComplstat(Config.COM_CD, user.getId(), request.getParameterValues("rsvnReqid"));

                if (result > 0)
                    resultInfo = HttpUtility.getSuccessResultInfo("??????????????? ?????????????????????.");
                else
                    resultInfo = HttpUtility.getErrorResultInfo("???????????? ?????? ????????? ????????????.");
            }

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo(e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @PageActionInfo(title = "?????????????????? ????????????", action = PageActionType.READ)
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

    @PageActionInfo(title = "?????????????????? ??????????????????", action = PageActionType.READ)
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

    @PageActionInfo(title = "?????????????????????????????? ??????????????????", action = PageActionType.READ)
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
        String file_name = "????????????_????????????_??????";

        if (requestUrl.indexOf("totalRsvn") > 0) {
            templete = "totalRsvnList";
            file_name = "??????????????????_????????????_??????";
        }

        // ?????? ????????? ?????? ??? ?????? ?????? data
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
            // ????????? ?????? ????????? ??????
            if (rsvnInfoList != null && rsvnInfoList.size() >= 1) {
                Class cs = Class.forName("com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO");

                int rowno = 1; // 2 ?????? ?????? ??????
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
            response.getWriter().println("?????? ????????? ?????? ??? ?????? ??????<br>" + e.getMessage());
        }

        // ?????? ????????? contentType ??????
        response.setContentType("application/vnd.ms-excel");

        // ?????? ????????? ??????
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName(file_name + ".xls"));
        workbook.write(response.getOutputStream());
    }

    @GetMapping(value = { "/studentBookExcel" })
    public void studentListExcel(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        // ?????????????????? ??????
        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(searchVO);
        // ???????????? ?????? ??????
        List<EgovMap> dateList = eduAdmService.selectClassSchedule(detailVO);

        List<HashMap> newList = new ArrayList<HashMap>();
        List<EdcRsvnInfoVO> rsvnInfoList = null;

        // ???????????????
        EdcRsvnInfoVO rsvnVO = new EdcRsvnInfoVO();
        rsvnVO.setUsePagingYn(Config.NO);
        rsvnVO.setEdcPrgmNo(searchVO.getEdcPrgmNo());
        rsvnVO.setEdcRsvnsetSeq(searchVO.getEdcRsvnsetSeq());
        rsvnVO.setChannel("BO");
        rsvnVO.setEdcStat("");

        rsvnInfoList = edcRsvnInfoService.selectRsvnList(rsvnVO);

        String templete = "attendanceBook";
        String file_name = "????????????_?????????_" + detailVO.getEdcPrgmnm() + "_" + commandMap.getString("showYear") + commandMap.getString("showMonth");

        // ????????? ?????? ????????? ??????
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
                        // ????????? ??????
                    }
                }

                // ?????? ??????
                int age = 0;

                String birthDate = vo.getEdcRsvnBirthdate();
                if (!StringUtils.isBlank(birthDate) && birthDate.length() >= 4) {
                    int birthYear = Integer.parseInt(birthDate.substring(0, 4));
                    int nowYear = Integer.parseInt(DateUtil.getTodate("yyyy"));
                    age = nowYear - birthYear + 1;
                }

                String gender = vo.getEdcRsvnGender();
                if ("1".equals(gender)) {
                    gender = "??????";
                } else if ("2".equals(gender)) {
                    gender = "??????";
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

                // ???????????? ??????????????? ??????????????? ???????????? ???????????? ????????????????????? ?????????
                if ("4001".equals(vo.getEdcStat()) || (commandMap.getString("showRefundYn").equals("Y") && "3004".equals(vo.getEdcStat()) && refundCheck)) {
                    // ??????????????? ??????
                    newList.add(rowno, map);
                    rowno++;
                }
            }
        }

        // ?????? ????????? ?????? ??? ?????? ?????? data
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

            int k = 2; // 3?????? ?????????

            scheduleRow.getCell(0).setCellValue("");

            if (commandMap.getString("showHpYn").equals("Y")) {
                Cell cell = titleRow.createCell(k++);
                cell.setCellStyle(headStyle);
                cell.setCellValue("????????? ??????");
            }

            if (commandMap.getString("showAgeYn").equals("Y")) {
                Cell cell = titleRow.createCell(k++);
                cell.setCellStyle(headStyle);
                cell.setCellValue("??????");
            }

            if (commandMap.getString("showGenderYn").equals("Y")) {
                Cell cell = titleRow.createCell(k++);
                cell.setCellStyle(headStyle);
                cell.setCellValue("??????");
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
                    cell2.setCellValue(sortNo + "??????");

                }
            }

            rowno = 9; // 10?????? ?????????

            for (Map vo : newList) {
                k = 2; // 3?????? ?????????
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
            response.getWriter().println("?????? ????????? ?????? ??? ?????? ??????<br>" + e.getMessage());
        }

        // ?????? ????????? contentType ??????
        response.setContentType("application/vnd.ms-excel");

        // ?????? ????????? ??????
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName(file_name + ".xls"));
        workbook.write(response.getOutputStream());

    }

    /** ?????? ???????????? ?????????. */
    private InputStream readTemplate(String finalTemplate) throws FileNotFoundException {

        String templateFilePath = FileMngUtil.GetRealRootPath().concat("WEB-INF/excelTemplate/" + finalTemplate);

        return new FileInputStream(templateFilePath);
    }

    /** ???????????? ????????? */
    private String encodeFileName(String filename) {
        try {
            return URLEncoder.encode(filename, "UTF-8").replaceAll("[+]", " ");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    // ??????????????? > ???????????? ?????????????????? ???????????? ????????? ?????? map
    private static Map<String, String> TH_MAP = new HashMap<String, String>();
    static {
        TH_MAP.put("edcOnoffintype", "????????????");
        TH_MAP.put("edcReqDate", "????????????");
        TH_MAP.put("edcPrgmnm", "???????????????");
        TH_MAP.put("edcDaygbnNm", "????????????");
        TH_MAP.put("edcSdate", "???????????????");
        TH_MAP.put("edcEdate", "???????????????");
        TH_MAP.put("edcMemNo", "????????????");
        TH_MAP.put("edcRsvnCustnm", "?????????");
        TH_MAP.put("id", "ID");
        TH_MAP.put("edcRsvnMoblphon", "???????????????");
        TH_MAP.put("genderNm", "??????");
        TH_MAP.put("edcRsvnBirthdate", "????????????");
        TH_MAP.put("edcHomeAddr", "??????");
        TH_MAP.put("edcEmail", "?????????");
        TH_MAP.put("edcStatNm", "????????????");
        TH_MAP.put("payMethodNm", "????????????");
        TH_MAP.put("edcProgmCost", "?????????");
        TH_MAP.put("payAmt", "????????????");
        TH_MAP.put("edcDcamt", "????????????");
        TH_MAP.put("edcReasondc", "????????????");
        TH_MAP.put("payDate", "????????????");
        TH_MAP.put("oid", "????????????");
        TH_MAP.put("receiptNo", "???????????????");
        TH_MAP.put("cancelDtime", "????????????");
        TH_MAP.put("cancelAmt", "????????????");
        TH_MAP.put("retAcountNum", "??????????????????");
        TH_MAP.put("retDpstrNm", "???????????????");
        TH_MAP.put("retBankNm", "?????????????????????");
        TH_MAP.put("edcComplstat", "????????????");
        TH_MAP.put("edcPaywaitEnddatetime", "??????????????????");

    }

}
