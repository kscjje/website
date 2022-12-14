package com.hisco.user.exbtrsvn.web;

import java.net.URLEncoder;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.cmm.exception.MyException;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CmnCalendar;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.ScrEncDecUtil;
import com.hisco.cmm.vo.FileVO;
import com.hisco.intrfc.charge.service.ChargeService;
import com.hisco.intrfc.charge.vo.OrderIdVO;
import com.hisco.intrfc.sms.service.SmsService;
import com.hisco.user.exbtrsvn.service.CalendarVO;
import com.hisco.user.exbtrsvn.service.DspyDsService;
import com.hisco.user.exbtrsvn.service.ExbtBaseruleVO;
import com.hisco.user.exbtrsvn.service.ExbtChargeVO;
import com.hisco.user.exbtrsvn.service.ExbtTimeVO;
import com.hisco.user.exbtrsvn.service.RsvnMasterVO;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.service.MyInforService;
import com.hisco.user.mypage.service.RsvnCommService;
import com.hisco.user.mypage.vo.MyRsvnVO;
import com.hisco.user.mypage.vo.RsvnCommVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * ?????? > ?????????????????? ????????????
 * 
 * @author ?????????
 * @since 2021.05.19
 * @version 1.0, 2021.05.19
 *          ------------------------------------------------------------------------
 *          ????????? ?????? ??????
 *          ------------------------------------------------------------------------
 *          ????????? 2021.05.19 ????????????
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/exbtrsvn")
public class DspyDsController {

    @Resource(name = "dspyDsService")
    private DspyDsService dspyDsService;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "myInforService")
    private MyInforService myInforService;

    @Resource(name = "rsvnCommService")
    private RsvnCommService rsvnCommService;

    @Resource(name = "smsService")
    private SmsService smsService;

    @Resource(name = "chargeService")
    private ChargeService chargeService;

    /**
     * ?????? ??????
     *
     * @param EventProgramVO
     *            vo
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/exbtrsvnList")
    public String exbtList(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        log.debug("call exbtList()");

        Map<String, Object> paramMap = commandMap.getParam();

        log.debug("paramMap = " + paramMap);

        commandMap.put("comcd", Config.COM_CD);

        String ymd = commandMap.getString("ymd");

        log.debug("ymd = " + ymd);

        String strPartCd = "0027";

        commandMap.put("uniqueId", strPartCd);
        commandMap.put("partCd", strPartCd);

        CamelMap partInfo = dspyDsService.selectselectPartCd(commandMap.getParam());

        if (partInfo != null) {
            String fileId = EgovStringUtil.isNullToString(partInfo.get("partImgFinnb"));
            if (!fileId.equals("")) {
                FileVO fileVO = new FileVO();
                fileVO.setFileGrpinnb(fileId);
                model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
            }
        }

        CmnCalendar cal = new CmnCalendar();
        java.util.Map<String, Object> calMap = cal.getCalendar(request, ymd);

        log.debug("calMap = " + calMap);

        model.addAttribute("ymd", ymd);
        model.addAttribute("calMap", calMap);
        model.addAttribute("partInfo", partInfo);
        model.addAttribute("paramMap", paramMap);

        log.debug("final model = " + model);

        return HttpUtility.getViewUrl(request);

    }

    /**
     * ?????? ??????
     *
     * @param partCd
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/dspy/{partCd}")
    public String selectExbtMain(@PathVariable String partCd, CommandMap commandMap, HttpServletRequest request,
            ModelMap model) throws Exception {

        commandMap.put("comcd", Config.COM_CD);

        String ymd = commandMap.getString("ymd");

        log.debug("ymd = " + ymd);

        String selPartCd = partCd;

        if (partCd.equals("detail")) {

            CamelMap baseDataVO = dspyDsService.selectBaseRule(commandMap.getParam());

            if (baseDataVO != null) {
                selPartCd = CommonUtil.getString(baseDataVO.get("exbtPartcd"));
            }

            if (baseDataVO != null && "Y".equals(baseDataVO.get("useYn")) && "Y".equals(baseDataVO.get("openyn"))) {
                // ?????? ?????? ?????? ??????
                commandMap.put("exbtSeq", baseDataVO.get("exbtSeq"));
                ymd = dspyDsService.selectStartYmdByBase(commandMap.getParam());

                if (ymd == null)
                    ymd = "";

                model.addAttribute("exbtSeq", baseDataVO.get("exbtSeq"));
            }
        }

        commandMap.put("uniqueId", selPartCd);
        commandMap.put("partCd", selPartCd);

        CamelMap partInfo = dspyDsService.selectselectPartCd(commandMap.getParam());
        if (partInfo != null) {
            String fileId = EgovStringUtil.isNullToString(partInfo.get("partImgFinnb"));
            if (!fileId.equals("")) {
                FileVO fileVO = new FileVO();
                fileVO.setFileGrpinnb(fileId);
                model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
            }
        }
        CmnCalendar cal = new CmnCalendar();
        java.util.Map<String, Object> calMap = cal.getCalendar(request, ymd);

        log.debug("calMap = " + calMap);

        model.addAttribute("ymd", ymd);
        model.addAttribute("calMap", calMap);
        model.addAttribute("partInfo", partInfo);

        log.debug("model = " + model);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/exbtrsvn/dspyMain");
    }

    /**
     * ?????? ??????
     *
     * @param partCd
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/calendarAjax")
    public String selecCalendar(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        CmnCalendar cal = new CmnCalendar();

        java.util.Map<String, Object> calMap = cal.getCalendar(request, "");
        model.addAttribute("calMap", calMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ???????????? ?????? ??????
     * 
     * @param commandMap
     * @return ModelAndView - ??????
     * @exception Exception
     */
    @GetMapping(value = "/calendarCheckAjax")
    @ResponseBody
    public ModelAndView selectScheduleList(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        log.debug("call selectScheduleList()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        ModelAndView mav = new ModelAndView("jsonView");

        commandMap.put("comcd", Config.COM_CD);

        List<CalendarVO> calendarVOList = (List<CalendarVO>) dspyDsService.selectScheduleList(commandMap.getParam());

        mav.addObject("schedule", calendarVOList);

        return mav;
    }

    /**
     * ???????????? ?????? ??????
     * 
     * @param commandMap
     * @return ModelAndView - ??????
     * @exception Exception
     */
    @GetMapping(value = "/exbtBaseListAjax")
    public String selectExbtBaseList(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        commandMap.put("comcd", Config.COM_CD);

        String dayWeek = dspyDsService.selectExbtWeek(commandMap.getParam());

        commandMap.put("dayWeek", dayWeek); // ????????????

        model.addAttribute("holidayCheck", dspyDsService.selectHolidayCheck(commandMap.getParam())); // ???????????????
        model.addAttribute("baseList", dspyDsService.selectExbtBaseList(commandMap.getParam()));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????????????????? ?????? ?????????
     * 
     * @param commandMap
     * @return jsp ?????????
     * @exception Exception
     */
    @GetMapping(value = "/exbtDetailAjax")
    public String selectExbtBaseDetail(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        commandMap.put("comcd", Config.COM_CD);

        String dayWeek = dspyDsService.selectExbtWeek(commandMap.getParam());

        commandMap.put("dayWeek", dayWeek); // ????????????

        int limitCount = 0;

        if (!commandMap.getString("exbtSeq").equals("")) {
            ExbtBaseruleVO baseDataVO = dspyDsService.selectExbtBaseDetail(commandMap.getParam());
            model.addAttribute("baseDataVO", baseDataVO);

            // ?????? ?????????
            if (baseDataVO != null) {
                String fileId = EgovStringUtil.isNullToString(baseDataVO.getExbtImgFinnb());

                // ?????? ????????? ??????
                if (!"".equals(fileId)) {
                    FileVO fileVO = new FileVO();
                    fileVO.setFileGrpinnb(fileId);
                    model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
                }

                // ?????????
                model.addAttribute("timeList", dspyDsService.selectExbtTimeList(commandMap.getParam()));

                LoginVO loginVO = commandMap.getUserInfo();

                RsvnMasterVO maserVO = new RsvnMasterVO();
                maserVO.setYmd(commandMap.getString("ymd"));
                maserVO.setComcd(commandMap.getString("comcd"));
                maserVO.setMemNo(loginVO != null && loginVO.isMember() ? commandMap.getUserInfo().getUniqId() : "");
                maserVO.setHpcertno(loginVO != null && !loginVO.isMember() ? loginVO.getHpcertno() : "");
                maserVO.setLimitMethod(baseDataVO.getRsvnLimitMethod());

                // ???????????? ??????
                limitCount = dspyDsService.selectReserveLimitCount(maserVO);
            }
        }

        model.addAttribute("limitCount", limitCount);
        model.addAttribute("dayWeekNm", dayWeek);
        model.addAttribute("userInfo", commandMap.getUserInfo());
        model.addAttribute("userRoot", Config.USER_ROOT);

        return HttpUtility.getViewUrl(request);
    }

    public boolean checkExbtReserve(LoginVO userVO, RsvnMasterVO rsvnMasterVO, ExbtBaseruleVO baseDataVO,
            ExbtTimeVO timeVO, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // String url = Config.USER_ROOT + "/exbtrsvn/dspy/"+baseDataVO.getExbtPartcd()+"?ymd="+rsvnMasterVO.getYmd();

        String url = Config.USER_ROOT + "/exbtrsvn/exbtrsvnList?ymd=" + rsvnMasterVO.getYmd();

        if (baseDataVO == null || timeVO == null) {
            HttpUtility.sendBack(request, response, "?????? ???????????? ????????????. ?????? ????????? ?????? ????????? ?????????.");
            return false;
        } else if (baseDataVO.getHoldCnt() > 0) {
            HttpUtility.sendRedirect(request, response, "???????????? ????????? ??????????????????.", url);
            return false;
        } else if (!"Y".equals(baseDataVO.getReserveAbleYn()) || !"Y".equals(timeVO.getReserveAbleYn()) || !"Y".equals(baseDataVO.getTodayYn())) {
            HttpUtility.sendRedirect(request, response, "?????? ????????? ?????????????????????.", url);
            return false;
        } else if (timeVO.getTotCapa() <= timeVO.getReserveCnt()) {
            HttpUtility.sendRedirect(request, response, "????????? ?????????????????????.", url);
            return false;
        } else if (!"Y".equals(timeVO.getGroupyn()) && "20".equals(rsvnMasterVO.getTarget())) {
            HttpUtility.sendRedirect(request, response, "?????? ????????? ???????????? ?????? ?????? ?????????.", url);
            return false;
        } else if (!"Y".equals(timeVO.getPersonyn()) && "10".equals(rsvnMasterVO.getTarget())) {
            HttpUtility.sendRedirect(request, response, "?????? ????????? ???????????? ?????? ?????? ?????????.", url);
            return false;
        } else if (userVO == null) { // ????????? ?????? ??????
            String returnURL = Config.USER_ROOT + "/exbtrsvn/reserveStep1?target=" + rsvnMasterVO.getTarget() + "&exbtSeq=" + rsvnMasterVO.getExbtSeq() + "&stdseq=" + rsvnMasterVO.getStdseq() + "&timeseq=" + rsvnMasterVO.getTimeseq() + "&ymd=" + rsvnMasterVO.getYmd();

            HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/member/login?returnURL=" + URLEncoder.encode(returnURL, "UTF-8"));
            return false;
        } else {
            rsvnMasterVO.setComcd(Config.COM_CD);
            rsvnMasterVO.setMemNo(userVO != null && userVO.isMember() ? userVO.getUniqId() : "");
            rsvnMasterVO.setHpcertno(userVO != null && !userVO.isMember() ? userVO.getHpcertno() : "");
            rsvnMasterVO.setLimitMethod(baseDataVO.getRsvnLimitMethod());

            // ???????????? ??????
            int limitCount = dspyDsService.selectReserveLimitCount(rsvnMasterVO);

            String msg = "";

            if (baseDataVO.getRsvnLimitMethod().equals("20") && baseDataVO.getRsvnLimitCnt() > 0 && limitCount >= baseDataVO.getRsvnLimitCnt()) {
                msg = "?????? ?????? ?????? ??? ?????????????????????. (?????? ?????? " + baseDataVO.getRsvnLimitCnt() + "???)";
            } else if (baseDataVO.getRsvnLimitMethod().equals("21") && baseDataVO.getRsvnLimitCnt() > 0 && limitCount >= baseDataVO.getRsvnLimitCnt()) {
                msg = "?????? ?????? ?????? ??? ?????????????????????. (?????? ?????? " + baseDataVO.getRsvnLimitCnt() + "???)";
            } else if (baseDataVO.getRsvnLimitMethod().equals("22") && baseDataVO.getRsvnLimitCnt() > 0 && limitCount >= baseDataVO.getRsvnLimitCnt()) {
                msg = "?????? ?????? ?????? ??? ?????????????????????. (1??? ?????? " + baseDataVO.getRsvnLimitCnt() + "???)";
            }

            if (!msg.equals("")) {
                HttpUtility.sendRedirect(request, response, msg, url);

                return false;
            } else {
                // ?????? ?????? ?????? ??????

                return true;
            }
        }

    }

    /**
     * ?????? ?????? ??? ??????
     * 
     * @param commandMap
     * @return String - jsp ?????????
     * @exception Exception
     */
    @GetMapping(value = "/reserveStep1")
    public String selectExbtReserveStep1(@ModelAttribute("rsvnMasterVO") RsvnMasterVO rsvnMasterVO,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        commandMap.put("comcd", Config.COM_CD);
        // ?????? ?????? ??????
        ExbtBaseruleVO baseDataVO = dspyDsService.selectExbtBaseDetail(commandMap.getParam());

        // ????????????
        String dayWeek = dspyDsService.selectExbtWeek(commandMap.getParam());
        commandMap.put("dayWeek", dayWeek);

        // ?????? ??????
        ExbtTimeVO timeVO = dspyDsService.selectExbtTimeData(commandMap.getParam());

        // ?????? ?????? ??????
        if (checkExbtReserve(commandMap.getUserInfo(), rsvnMasterVO, baseDataVO, timeVO, request, response)) {
            // ????????????
            LoginVO userVO = commandMap.getUserInfo();

            if (("Y".equals(baseDataVO.getRsvnNonmebyn()) && !userVO.isMember()) || ("Y".equals(baseDataVO.getRsvnStdmembyn()) && userVO.isMember()) || ("Y".equals(baseDataVO.getRsvnAnualmembyn()) && userVO != null && "Y".equals(userVO.getYearYn())) || ("Y".equals(baseDataVO.getRsvnSpeclmembyn()) && userVO != null && "Y".equals(userVO.getSpecialYn()))) {

                rsvnMasterVO.setWebId(userVO.getId());
                rsvnMasterVO.setCustNm(userVO.getName());
                rsvnMasterVO.setExbtHp(userVO.getIhidNum());
                rsvnMasterVO.setExbtEmail(userVO.getEmail());
                rsvnMasterVO.setMemType(userVO.isMember() ? "1001" : "2001"); // ?????? , ?????????
                rsvnMasterVO.setHpcertno(userVO.getHpcertno()); // ????????? ????????????
                rsvnMasterVO.setGrpnm(userVO.getOrgnztNm()); // ?????????

                //// JYS 2021.05.19 List<ExbtChargeVO> chargeList =
                //// dspyDsService.selectExbtChargeList(commandMap.getParam());

                List<ExbtChargeVO> chargeList = null; // JYS 2021.05.19

                // ????????? ?????? ?????? ??????
                commandMap.put("COMCD", Config.COM_CD);
                commandMap.put("YMD", commandMap.getString("ymd"));
                commandMap.put("PART_CD", baseDataVO.getExbtPartcd());
                commandMap.put("PGM_CD", baseDataVO.getExbtSeq());
                commandMap.put("PGM_GUBUN", "EXBT");

                RsvnCommVO discInfoVO = null;
                RsvnCommVO discAnnualVO = null;
                List<RsvnCommVO> discList = rsvnCommService.selectEventStdmngList(commandMap.getParam());
                if (discList != null) {
                    for (RsvnCommVO discVO : discList) {
                        if (discInfoVO != null) {
                            // ?????? ????????? ?????????
                            break;
                        } else if (userVO.isMember() && "Y".equals(discVO.getMemberyn())) {
                            discInfoVO = discVO;
                        } else if (!userVO.isMember() && "Y".equals(discVO.getNonmebyn())) {
                            discInfoVO = discVO;
                        }
                    }
                }

                // ????????????
                if (userVO.isMember() && "10".equals(rsvnMasterVO.getTarget())) {
                    userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
                    MemberVO memberVO = myInforService.selectMemberData(userVO);
                    if (memberVO.getSpecialYn().equals("Y") || memberVO.getYearYn().equals("Y")) {
                        MyRsvnVO rsvnVO = new MyRsvnVO();
                        rsvnVO.setComcd(Config.COM_CD);
                        rsvnVO.setUniqId(memberVO.getMemNo());
                        rsvnVO.setRsvnIdxOne("0");
                        rsvnVO.setGubun("EXBT");
                        rsvnVO.setProgramCd(baseDataVO.getExbtSeq());
                        if ("Y".equals(memberVO.getYearYn())) {
                            rsvnVO.setMemberGbn("1"); // ????????????
                        } else {
                            rsvnVO.setMemberGbn("2"); // ????????????
                        }
                        RsvnCommVO annualData = rsvnCommService.selectAnnualDcData(rsvnVO);
                        if (annualData != null && annualData.getLimitCnt() == 0 && (discInfoVO == null || discInfoVO.getDcRate() < annualData.getDcRate())) {
                            // ?????? ??????
                            discAnnualVO = annualData;
                            // dcTypeCd = "8001";

                            rsvnMasterVO.setDcAnnualLimit(annualData.getLimitQty());
                        }
                    }
                }

                /*
                 * 2021.05.19
                 * for (ExbtChargeVO itemVO : chargeList) {
                 * // ?????? ?????? ??????
                 * if ("20".equals(rsvnMasterVO.getTarget()) && baseDataVO.getDcReasonCd() != null) {
                 * if ( "Y".equals(itemVO.getGroupdcYn())) {
                 * itemVO.setDcKindCd("6001");
                 * itemVO.setDcType(baseDataVO.getDcReasonCd());
                 * itemVO.setDcRate(baseDataVO.getDcRate());
                 * itemVO.setDcName("????????????");
                 * }
                 * } else {
                 * if (discInfoVO != null) {
                 * itemVO.setDcKindCd("4010");
                 * itemVO.setDcType(discInfoVO.getEventReasoncd());
                 * itemVO.setDcRate(discInfoVO.getEventDcRate());
                 * itemVO.setDcName(discInfoVO.getEventDcname());
                 * }
                 * if (discAnnualVO != null) {
                 * itemVO.setDcAnnualCd(discAnnualVO.getEventReasoncd());
                 * itemVO.setDcAnnualRate(discAnnualVO.getEventDcRate());
                 * itemVO.setDcAnnualNm("???????????? ??????");
                 * }
                 * }
                 * }
                 */

                rsvnMasterVO.setChargeList(chargeList);

                model.addAttribute("baseDataVO", baseDataVO);
                model.addAttribute("termsList", dspyDsService.selectTermsList());
                model.addAttribute("timeVO", timeVO);
                model.addAttribute("discInfoVO", discInfoVO);
                model.addAttribute("optData", rsvnCommService.selectOptData(commandMap.getParam())); // ???????????? ??????

                /*
                 * JYS 2021.05.19
                 * if (chargeList == null || chargeList.size() < 1) {
                 * HttpUtility.sendBack(request, response, "????????? ???????????? ?????? ????????????.\n??????????????? ????????? ?????????.");
                 * return null;
                 * } else {
                 * return HttpUtility.getViewUrl(request);
                 * }
                 */

                return HttpUtility.getViewUrl(request);

            } else {
                String msg = "?????? ?????? ????????? ????????????.";
                if (!"Y".equals(baseDataVO.getRsvnNonmebyn()) && !userVO.isMember()) {
                    msg = "?????? ?????? ?????? ???????????? ?????????.?????? ????????? ??? ????????? ?????????.";
                }
                // HttpUtility.sendRedirect(request, response, msg , Config.USER_ROOT +
                // "/exbtrsvn/dspy/"+baseDataVO.getExbtPartcd()+"?ymd="+rsvnMasterVO.getYmd());
                HttpUtility.sendRedirect(request, response, msg, Config.USER_ROOT + "/exbtrsvn/exbtrsvnList?ymd=" + rsvnMasterVO.getYmd());
                return null;
            }
        } else {
            return null;
        }

    }

    /**
     * ?????? ?????? ??? ??????
     * 
     * @param commandMap
     * @return String - jsp ?????????
     * @exception Exception
     */
    @PostMapping(value = "/reserveStep2")
    public String selectExbtReserveStep2(@ModelAttribute("rsvnMasterVO") RsvnMasterVO rsvnMasterVO,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        log.debug("call selectExbtReserveStep2()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        commandMap.put("comcd", Config.COM_CD);
        commandMap.put("COMCD", Config.COM_CD);
        // ?????? ?????? ??????
        ExbtBaseruleVO baseDataVO = dspyDsService.selectExbtBaseDetail(commandMap.getParam());

        // ????????????
        String dayWeek = dspyDsService.selectExbtWeek(commandMap.getParam());
        commandMap.put("dayWeek", dayWeek);

        // ?????? ??????
        ExbtTimeVO timeVO = dspyDsService.selectExbtTimeData(commandMap.getParam());
        LoginVO userVO = commandMap.getUserInfo();

        // ?????? ?????? ??????
        if (checkExbtReserve(commandMap.getUserInfo(), rsvnMasterVO, baseDataVO, timeVO, request, response)) {

            rsvnMasterVO.setWebId(userVO.getId());
            rsvnMasterVO.setMemNo(userVO.getUniqId());
            rsvnMasterVO.setCustNm(userVO.getName());
            rsvnMasterVO.setExbtHp(userVO.getIhidNum());
            rsvnMasterVO.setExbtEmail(userVO.getEmail());
            rsvnMasterVO.setMemType(userVO.isMember() ? "1001" : "2001"); // ?????? , ?????????
            rsvnMasterVO.setHpcertno(userVO.getHpcertno()); // ????????? ????????????
            rsvnMasterVO.setGrpnm(userVO.getOrgnztNm()); // ?????????

            RsvnCommVO discOpt = rsvnCommService.selectOptData(commandMap.getParam()); // ???????????? ??????

            long totalCnt = 0;
            long dcAmountLong = 0;
            long totalAmount = 0;

            int limitQty = rsvnMasterVO.getDcAnnualLimit();
            int remainCnt = 0;

            /*
             * JYS 2021.05.19
             * for (ExbtChargeVO charge : rsvnMasterVO.getChargeList() ) {
             * //???????????? ????????? ??????
             * if (charge.getDcAnnualRate() > 0 && limitQty == 0) {
             * limitQty = 1000;
             * }
             * int annualCnt = limitQty - remainCnt;
             * int orignCnt = Integer.parseInt(charge.getItemCnt()+"");
             * int cnt = orignCnt;
             * totalCnt += charge.getItemCnt();
             * if (charge.getItemCnt() > 0) {
             * long dcAmountLong3 = 0;
             * if (charge.getDcAnnualRate() > 0 && annualCnt > 0) {
             * //????????????????????? ?????????
             * if (annualCnt >= orignCnt) {
             * annualCnt = orignCnt;
             * cnt = 0;
             * } else {
             * cnt = orignCnt - annualCnt;
             * }
             * dcAmountLong3 = CommonUtil.DoubleToLongCalc(charge.getPrice() * charge.getDcAnnualRate() * 0.01 *
             * annualCnt, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
             * remainCnt += orignCnt;
             * }
             * long dcAmountLong2 = CommonUtil.DoubleToLongCalc(charge.getPrice() * charge.getDcRate() * 0.01 * cnt,
             * discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
             * totalAmount += charge.getPrice()* charge.getItemCnt();
             * dcAmountLong += dcAmountLong2;
             * dcAmountLong += dcAmountLong3;
             * }
             * }
             */

            if (timeVO.getTotCapa() < (timeVO.getReserveCnt() + totalCnt)) {
                HttpUtility.sendBack(request, response, "??????????????? ???????????? ???????????? ??? ????????????.(????????????:" + (timeVO.getTotCapa() - timeVO.getReserveCnt()) + "???)");
                return null;
            }

            String msg = "";

            if (("Y".equals(baseDataVO.getRsvnNonmebyn()) && !userVO.isMember()) || ("Y".equals(baseDataVO.getRsvnStdmembyn()) && userVO.isMember()) || ("Y".equals(baseDataVO.getRsvnAnualmembyn()) && userVO != null && "Y".equals(userVO.getYearYn())) || ("Y".equals(baseDataVO.getRsvnSpeclmembyn()) && userVO != null && "Y".equals(userVO.getSpecialYn()))) {

                model.addAttribute("baseDataVO", baseDataVO);
                model.addAttribute("timeVO", timeVO);
                model.addAttribute("userVO", userVO);
                model.addAttribute("totalAmount", totalAmount);
                model.addAttribute("dcAmount", dcAmountLong);

                return HttpUtility.getViewUrl(request);

            } else {
                msg = "?????? ?????? ????????? ????????????.";
                if (!"Y".equals(baseDataVO.getRsvnNonmebyn()) && !userVO.isMember()) {
                    msg = "?????? ?????? ?????? ???????????? ?????????.?????? ????????? ??? ????????? ?????????.";
                }
                // HttpUtility.sendRedirect(request, response, msg , Config.USER_ROOT +
                // "/exbtrsvn/dspy/"+baseDataVO.getExbtPartcd()+"?ymd="+rsvnMasterVO.getYmd());
                HttpUtility.sendRedirect(request, response, msg, Config.USER_ROOT + "/exbtrsvn/exbtrsvnList?ymd=" + rsvnMasterVO.getYmd());

                return null;
            }
        } else {
            return null;
        }

    }

    /**
     * ???????????? ??????
     *
     * @param rsvnMasterVO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/reserveAction")
    @ResponseBody
    public ModelAndView insertExbtReserve(CommandMap commandMap, HttpServletRequest request,
            HttpServletResponse response, @ModelAttribute("rsvnMasterVO") RsvnMasterVO rsvnMasterVO, ModelMap model)
            throws Exception {

        log.debug("call insertExbtReserve()");

        Map<String, Object> paramMap = commandMap.getParam();

        log.debug("paramMap = " + paramMap);

        ModelAndView mav = new ModelAndView("jsonView");
        Map<String, String> returnMap = new HashMap<String, String>();

        if (commandMap.getUserInfo() == null) {
            throw new MyException("???????????? ???????????????.????????? ??? ?????? ????????? ?????????.", -3);
        }

        // ?????? ?????? ??????
        commandMap.put("comcd", Config.COM_CD);
        ExbtBaseruleVO baseDataVO = dspyDsService.selectExbtBaseDetail(commandMap.getParam());

        // ????????????
        LoginVO userVO = new LoginVO();
        userVO.setUniqId(rsvnMasterVO.getMemNo());
        userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        MemberVO memberVO = myInforService.selectMemberData(userVO);

        if (baseDataVO.getHoldCnt() > 0) {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "???????????? ????????? ??????????????????.");
        } else if (!"Y".equals(baseDataVO.getReserveAbleYn()) && !"Y".equals(baseDataVO.getTodayYn())) {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "???????????? ????????? ?????????????????????.");
            // ?????? ?????? ?????? ??????
        } else if (("Y".equals(baseDataVO.getRsvnNonmebyn()) && memberVO == null) || ("Y".equals(baseDataVO.getRsvnStdmembyn()) && memberVO != null) || ("Y".equals(baseDataVO.getRsvnAnualmembyn()) && memberVO != null && "Y".equals(memberVO.getYearYn())) || ("Y".equals(baseDataVO.getRsvnSpeclmembyn()) && memberVO != null && "Y".equals(memberVO.getSpecialYn()))) {
            rsvnMasterVO.setComcd(Config.COM_CD);
            rsvnMasterVO.setReguser(rsvnMasterVO.getWebId());
            rsvnMasterVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

            if (request.getAttribute("IS_MOBILE") == null) {
                rsvnMasterVO.setTerminalType("2001");
            } else {
                boolean isMobile = (boolean) request.getAttribute("IS_MOBILE");
                rsvnMasterVO.setTerminalType(isMobile ? "2002" : "2001");
            }

            // ???????????? ??????
            String rsvnNo = dspyDsService.selectRsvnNumber();
            rsvnMasterVO.setRsvnNo(rsvnNo);

            // ???????????? ??????
            // OID ??????
            OrderIdVO orderVO = new OrderIdVO();
            orderVO.setComcd(rsvnMasterVO.getComcd());
            orderVO.setUserId(rsvnMasterVO.getWebId());
            orderVO.setRsvnNo(rsvnMasterVO.getRsvnNo()); // ????????????
            orderVO.setRsvnCnt(1);
            orderVO.setRsvnAmt(0);
            chargeService.insertDbprocLog(orderVO);

            //// chargeService.selectOrderId(orderVO); for Tibero 2021.03.17

            chargeService.selectOrderIdNoPg(orderVO);

            String oid = orderVO.getRetOid();

            log.debug("oid = " + oid);

            // insert
            MyRsvnVO myRsvnVO = new MyRsvnVO(); // ?????? ???????????? ?????? ??? ???
            myRsvnVO.setLgdOID(oid);
            String errorMsg = "";
            try {

                returnMap = dspyDsService.insertRegistMst(memberVO, rsvnMasterVO, baseDataVO, myRsvnVO);

                errorMsg = "OK";

            } catch (Exception e) {
                errorMsg = e.getMessage();
                returnMap.put("resultCd", Config.FAIL);
                returnMap.put("resultMsg", "????????? ??????");

            } finally {
                if (myRsvnVO.getLgdAMOUNT() != null && myRsvnVO.getLgdAMOUNT().equals("0") && (errorMsg.equals("OK") || errorMsg.indexOf("SP_CREATE_PAMENTINFO") > 0)) {
                    chargeService.insertDbprocLog(myRsvnVO);
                }
            }

            if (Config.SUCCESS.equals(returnMap.get("resultCd"))) {
                // ?????? ??????
                try {
                    String ticketInfo = "";
                    Map<String, String> smsParam = new HashMap<String, String>();
                    smsParam.put("msgcd", "2");
                    smsParam.put("msgno", "2");
                    smsParam.put("sndHp", baseDataVO.getExbtGuideTelno());
                    smsParam.put("hp", rsvnMasterVO.getExbtHp());
                    smsParam.put("????????????", rsvnMasterVO.getCustNm());
                    smsParam.put("??????", baseDataVO.getExbtPartNm());
                    smsParam.put("????????????", baseDataVO.getExbtName());
                    smsParam.put("????????????", returnMap.get("itemInfo"));
                    smsParam.put("????????????", rsvnNo);
                    smsParam.put("????????????", rsvnMasterVO.getYmd().substring(0, 4) + "." + rsvnMasterVO.getYmd().substring(4, 6) + "." + rsvnMasterVO.getYmd().substring(6, 8) + " " + rsvnMasterVO.getStartTime().substring(0, 2) + ":" + rsvnMasterVO.getStartTime().substring(2));
                    smsParam.put("????????????", baseDataVO.getExbtGuideTelno());

                    if ("Y".equals(returnMap.get("freeYn"))) {
                        if ("Y".equals(baseDataVO.getExbtTicketChkyn())) {
                            String ticketNo = dspyDsService.selectTicketNumber(rsvnMasterVO);
                            if (ticketNo != null && !ticketNo.equals("")) {
                                String strKey = EgovProperties.getProperty("Globals.Ticket.Key");

                                String strAedValue = ScrEncDecUtil.fn_encrypt_url(rsvnNo, strKey);
                                ticketInfo += "??????????????? : " + ticketNo;
                                ticketInfo += "\n?????? ????????? ??????   ????????????????????? ?????? ?????? ???????????? ?????? ?????????.";
                                ticketInfo += "\n?????????????????? : " + EgovProperties.getProperty("Globals.Domain") + "/ticket/" + strAedValue;
                            }
                        }
                    } else if ("10".equals(rsvnMasterVO.getTarget())) {
                        smsParam.put("msgno", "18"); // ??????
                        String payWaitTime = rsvnMasterVO.getPayWaitTime();
                        smsParam.put("??????????????????", payWaitTime.substring(0, 4) + "." + payWaitTime.substring(4, 6) + "." + payWaitTime.substring(6, 8) + " " + payWaitTime.substring(8, 10) + ":" + payWaitTime.substring(10));
                    } else {
                        smsParam.put("msgno", "18"); // ??????
                        smsParam.put("??????????????????", "?????? ?????? ????????? ?????? ???????????? ????????? ??????????????? ????????? ??? ????????????.");
                    }
                    smsParam.put("????????????", ticketInfo);
                    smsService.sendMessage(smsParam, commandMap.getUserInfo());

                } catch (Exception e) {
                    // sms?????? ?????? ??????
                    log.error("???????????? ???????????? ??????:" + e.getMessage());
                }
            }
        } else {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "?????? ????????? ????????? ????????? ????????????. ?????? ????????? ????????? ?????? ????????? ?????????.");
        }

        mav.addObject("result", returnMap);
        return mav;
    }

    /**
     * ????????????
     * 
     * @param commandMap
     * @return String - jsp ?????????
     * @exception Exception
     */
    @PostMapping(value = "/reserveResult")
    public String selectExbtReserveEnd(@ModelAttribute("rsvnMasterVO") RsvnMasterVO rsvnMasterVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        rsvnMasterVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        // ?????? ??????
        RsvnMasterVO resultVO = dspyDsService.selectRegistMst(rsvnMasterVO);
        resultVO.setChargeList(dspyDsService.selectRegistItemList(rsvnMasterVO));

        model.addAttribute("resultVO", resultVO);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ????????????
     * 
     * @param commandMap
     * @return String - jsp ?????????
     * @exception Exception
     */
    @PostMapping(value = "/reserveIndiResult")
    public String selectExbtIndiReserveEnd(@ModelAttribute("rsvnMasterVO") RsvnMasterVO rsvnMasterVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        rsvnMasterVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        // ?????? ??????
        RsvnMasterVO resultVO = dspyDsService.selectRegistMst(rsvnMasterVO);
        resultVO.setChargeList(dspyDsService.selectRegistItemList(rsvnMasterVO));

        model.addAttribute("resultVO", resultVO);

        return HttpUtility.getViewUrl(request);
    }

}
