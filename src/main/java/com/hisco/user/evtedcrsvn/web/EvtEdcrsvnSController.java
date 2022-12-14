package com.hisco.user.evtedcrsvn.web;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.hisco.cmm.util.WebEncDecUtil;
import com.hisco.cmm.vo.FileVO;
import com.hisco.intrfc.charge.service.ChargeService;
import com.hisco.intrfc.charge.vo.OrderIdVO;
import com.hisco.intrfc.sms.service.SmsUmsService;
import com.hisco.user.evtedcrsvn.service.CalendarVO;
import com.hisco.user.evtedcrsvn.service.ComCtgrVO;
import com.hisco.user.evtedcrsvn.service.EventProgramVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcItemAmountVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcStdmngVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcrsvnMstVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcrsvnSMainService;
import com.hisco.user.evtedcrsvn.service.EvtEdcrsvnService;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.service.MyInforService;
import com.hisco.user.mypage.service.RsvnCommService;
import com.hisco.user.mypage.vo.MyRsvnVO;
import com.hisco.user.mypage.vo.RsvnCommVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * ???????????? ?????? ????????????
 * 
 * @author ?????????
 * @since 2021.07.13
 * @version 1.0, 2021.07.13
 *          ------------------------------------------------------------------------
 *          ????????? ?????? ??????
 *          ------------------------------------------------------------------------
 *          ????????? 2021.07.13 ????????????
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/evtedcrsvn")
public class EvtEdcrsvnSController {

    @Resource(name = "chargeService")
    private ChargeService chargeService;

    @Resource(name = "rsvnCommService")
    private RsvnCommService rsvnCommService;

    @Resource(name = "myInforService")
    private MyInforService myInforService;

    @Resource(name = "evtEdcrsvnService")
    private EvtEdcrsvnService evtEdcrsvnService;

    @Resource(name = "evtEdcrsvnSMainService")
    private EvtEdcrsvnSMainService evtEdcrsvnSMainService;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "smsUmsService")
    private SmsUmsService smsUmsService;

    /**
     * ???????????? ??????
     *
     * @param EventProgramVO
     *            vo
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/evtEdcrsvnList")
    public String evtEdcList(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("eventProgramVO") EventProgramVO vo) throws Exception {

        log.debug("call evtEdcList()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        String strEvtGbn = String.valueOf(paramMap.get("evtGbn"));
        log.debug("strEvtGbn = " + strEvtGbn);

        if (("null".equals(strEvtGbn)) || (strEvtGbn == null)) {
            strEvtGbn = "ALL";
        }

        ComCtgrVO ctgVO = new ComCtgrVO();

        model.addAttribute("cList", evtEdcrsvnSMainService.selectEvtEdcCategList(ctgVO));
        model.addAttribute("fCtg", vo.getComPrnctgcd());
        model.addAttribute("sCtg", vo.getCtgCd());
        model.addAttribute("sKeyword", vo.getEvtName());
        model.addAttribute("odr", vo.getOrderField());
        model.addAttribute("evtGbn", strEvtGbn);

        log.debug("final model = " + model);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ???????????? ??????
     *
     * @param EventProgramVO
     *            vo
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/evtEdcrsvnListAjax")
    public String evtEdcListAjax(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("eventProgramVO") EventProgramVO vo) throws Exception {

        log.debug("call evtListAjax()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        String strEvtGbn = String.valueOf(paramMap.get("evtGbn"));
        log.debug("strEvtGbn = " + strEvtGbn);

        if (("null".equals(strEvtGbn)) || (strEvtGbn == null)) {
            strEvtGbn = "ALL";
        }

        LoginVO userInfo = commandMap.getUserInfo();
        if (userInfo != null) {
            if ("Y".equals(userInfo.getSpecialYn())) {
                vo.setMemberType("S");
            } else if ("Y".equals(userInfo.getYearYn())) {
                vo.setMemberType("U");
            }
        }

        vo.setComcd(Config.COM_CD);
        vo.setEvtGbn(strEvtGbn);

        ComCtgrVO ctgVO = new ComCtgrVO();

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        log.debug("lastRecordIndex = {}", paginationInfo.getLastRecordIndex());
        vo.setPaginationInfo(paginationInfo);

        Map<String, Object> rMap = evtEdcrsvnSMainService.selectEvtEdcProgList(vo);

        String listSize = rMap.get("size").toString();
        paginationInfo.setTotalRecordCount((Integer) rMap.get("size"));
        vo.setPaginationInfo(paginationInfo);

        model.addAttribute("cList", evtEdcrsvnSMainService.selectEvtEdcCategList(ctgVO));
        model.addAttribute("fCtg", vo.getComPrnctgcd());
        model.addAttribute("sCtg", vo.getCtgCd());
        model.addAttribute("sKeyword", vo.getEvtName());
        model.addAttribute("rList", rMap.get("list"));
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("listSize", listSize);
        model.addAttribute("odr", vo.getOrderField());

        if (!StringUtils.isEmpty(vo.getComPrnctgcd())) {
            ctgVO.setCtgLvl("1");
            ctgVO.setParentCtgCd(vo.getComPrnctgcd());
            model.addAttribute("sList", evtEdcrsvnSMainService.selectEvtEdcCategList(ctgVO));
        }

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ???????????? ??????
     *
     * @param partCd
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/evtEdcrsvnDetail")
    public String evtrsvnDetail(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("eventProgramVO") EventProgramVO vo) throws Exception {
        LoginVO userInfo = commandMap.getUserInfo();
        if (userInfo != null) {
            if ("Y".equals(userInfo.getSpecialYn())) {
                vo.setMemberType("S");
            } else if ("Y".equals(userInfo.getYearYn())) {
                vo.setMemberType("U");
            }
        }

        CmnCalendar cal = new CmnCalendar();
        vo.setComcd(Config.COM_CD);

        EventProgramVO tmpVo = evtEdcrsvnSMainService.selectEvtEdcPrgDetail(vo);
        Map<String, Object> calMap = cal.getCalendar(request, tmpVo.getEvtUseSdate());

        if (tmpVo != null) {
            String imgId = EgovStringUtil.isNullToString(tmpVo.getEvtIntrimgFinnb());
            String fileId = EgovStringUtil.isNullToString(tmpVo.getEvtPlanFinnb());
            FileVO fileVO = new FileVO();
            if (!imgId.equals("")) {
                fileVO.setFileGrpinnb(imgId);
                model.addAttribute("imgList", fileMngService.selectFileInfs(fileVO));
            }
            if (!fileId.equals("")) {
                fileVO.setFileGrpinnb(fileId);
                model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
            }
        }
        model.addAttribute("calMap", calMap);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("vo", tmpVo);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ???????????? ??????
     *
     * @param partCd
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/calendarAjax")
    public String selectCalendarAj(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        CmnCalendar cal = new CmnCalendar();

        java.util.Map<String, Object> calMap = cal.getCalendar(request, "");
        model.addAttribute("calMap", calMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ???????????? ?????? ??????
     *
     * @param MainSearchVO
     * @param model
     * @return Map
     * @throws Exception
     */
    @GetMapping(value = "/evtEdcShowPrgmAjax")
    public String selectTimeAj(CommandMap commandMap, HttpServletRequest request, ModelMap model, EvtEdcStdmngVO vo)
            throws Exception {

        log.debug("call selectTimeAj()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        LoginVO userInfo = commandMap.getUserInfo();

        EventProgramVO prgVO = new EventProgramVO();

        prgVO.setEvtNo(Integer.parseInt(vo.getEvtNo()));
        prgVO.setComcd(Config.COM_CD);
        prgVO.setEvtTime(vo.getEvtTime());

        if (userInfo != null) {
            if ("Y".equals(userInfo.getSpecialYn())) {
                prgVO.setMemberType("S");
            } else if ("Y".equals(userInfo.getYearYn())) {
                prgVO.setMemberType("U");
            }
        }

        model.addAttribute("programVO", evtEdcrsvnSMainService.selectEvtEdcPrgDetail(prgVO));

        commandMap.put("comcd", Config.COM_CD);
        String dayWeek = evtEdcrsvnSMainService.selectEvtEdcWeek(commandMap.getParam());
        vo.setComcd(Config.COM_CD);
        vo.setDayWeek(dayWeek);

        model.addAttribute("timeList", evtEdcrsvnSMainService.selectEvtEdcTimeDetailList(vo));
        model.addAttribute("dayWeek", dayWeek);

        log.debug("final model = " + model);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ???????????? ?????? ??????
     * 
     * @param commandMap
     * @return ModelAndView - ??????
     * @exception Exception
     */
    @GetMapping(value = "/evtEdcrsvnSelectAjax")
    @ResponseBody
    public ModelAndView selectEvtEdcSchedList(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            EvtEdcStdmngVO vo) throws Exception {

        log.debug("call selectEvtSchedList()");

        Map<String, Object> paramMap = commandMap.getParam();

        log.debug("paramMap = " + paramMap);

        ModelAndView mav = new ModelAndView("jsonView");
        commandMap.put("comcd", Config.COM_CD);

        List<CalendarVO> sList = evtEdcrsvnSMainService.selectEvtEdcRsvnList(commandMap.getParam());

        log.debug("sList = " + sList);

        mav.addObject("scheList", sList);
        mav.addObject("listSize", (sList != null ? sList.size() : 0));
        return mav;
    }

    /**
     * ???????????? ????????? (????????????)
     *
     * @param commandMap
     * @param SurveyVO
     * @return modelandview
     * @throws Exception
     */
    @PostMapping("/join/carDupliCheckAjax")
    @ResponseBody
    public ModelAndView checkCarInfo(CommandMap commandMap, ModelMap model, ComCtgrVO vo) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("cList", evtEdcrsvnSMainService.selectEvtEdcCategList(vo));
        return mav;
    }

    /**
     * ???????????? ?????? ?????? ??? ??????
     *
     * @param partCd
     * @param model
     * @return
     * @throws Exception
     */

    @GetMapping(value = "/evtEdcrsvnStep1")
    public String rsvnStepFrst(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, EvtEdcStdmngVO vo, @ModelAttribute("evtEdcrsvnMstVO") EvtEdcrsvnMstVO evtrsvnMstVO)
            throws Exception {

        log.debug("call rsvnStepFrst()");

        Map<String, Object> paramMap = commandMap.getParam();

        log.debug("paramMap = " + paramMap);

        Map<String, Object> pMap = new HashMap<String, Object>();
        String msg = "";
        LoginVO userVO = commandMap.getUserInfo();

        // set comcd
        vo.setComcd(Config.COM_CD);

        // ??????
        Map<String, Object> tMap = evtEdcrsvnService.selectEvtEdcTerms(null);

        EventProgramVO eVo = new EventProgramVO();
        eVo.setComcd(Config.COM_CD);
        eVo.setEvtNo(Integer.parseInt(vo.getEvtNo()));
        eVo.setEvtTime(vo.getEvtTime());

        // ????????????
        String dayWeek = evtEdcrsvnSMainService.selectEvtEdcWeek(commandMap.getParam());
        vo.setDayWeek(dayWeek);

        // ????????????
        EventProgramVO detailVO = evtEdcrsvnSMainService.selectEvtEdcPrgDetail(eVo);

        // ????????????
        CamelMap rsTimeData = evtEdcrsvnSMainService.selectEvtEdcTimeDetail(vo);

        // ????????????
        List<EvtEdcItemAmountVO> chrgList = evtEdcrsvnService.selectEvtEdcChargeList(vo);

        if (checkReserve(userVO, commandMap, detailVO, rsTimeData, request, response)) {
            int nshw = 0;
            if ("2001".equals(detailVO.getEvtFeeType())) {
                // ????????????
                pMap.put("comcd", Config.COM_CD);
                pMap.put("evtRsvnMemno", userVO.getUniqId());
                pMap.put("evtPartcd", detailVO.getEvtPartCd());
                pMap.put("ctgCd", detailVO.getCtgCd());

                nshw = evtEdcrsvnService.selectMemNshw(pMap);
            } else {
                nshw = 0;
            }

            log.debug("detailVO.getEvtNonmebyn() = " + detailVO.getEvtNonmebyn());
            log.debug("detailVO.getEvtStdmembyn() = " + detailVO.getEvtStdmembyn());
            log.debug("detailVO.getEvtAnualmembyn() = " + detailVO.getEvtAnualmembyn());
            log.debug("detailVO.getEvtSpeclmembyn() = " + detailVO.getEvtSpeclmembyn());
            log.debug("userVO.isMember() = " + userVO.isMember());
            log.debug("userVO.getYearYn() = " + userVO.getYearYn());
            log.debug("userVO.getSpecialYn() = " + userVO.getSpecialYn());
            log.debug("nshw = " + nshw);

            // ?????? ?????? ?????? ??????
            if (("Y".equals(detailVO.getEvtNonmebyn()) && !userVO.isMember()) || ("Y".equals(detailVO.getEvtStdmembyn()) && userVO.isMember()) || ("Y".equals(detailVO.getEvtAnualmembyn()) && "Y".equals(userVO.getYearYn())) || ("Y".equals(detailVO.getEvtSpeclmembyn()) && "Y".equals(userVO.getSpecialYn())) || nshw <= 0) {
                // ????????? ?????? ?????? ??????
                commandMap.put("COMCD", Config.COM_CD);
                commandMap.put("YMD", commandMap.getString("evtTime"));
                commandMap.put("PART_CD", vo.getEvtPartcd());
                commandMap.put("PGM_CD", vo.getEvtNo());
                commandMap.put("PGM_GUBUN", "EVT");

                RsvnCommVO discInfoVO = null;
                RsvnCommVO discAnnualVO = null;
                List<RsvnCommVO> discList = rsvnCommService.selectEventStdmngList(commandMap.getParam());
                if (discList != null) {
                    for (RsvnCommVO discVO : discList) {
                        if (discInfoVO != null) {
                            // ?????? ????????? ?????????
                            commandMap.put("PGM_GUBUN", "EVT"); // ????????? ???????????????
                        } else if (userVO.isMember() && "Y".equals(discVO.getMemberyn())) {
                            discInfoVO = discVO;
                        } else if (!userVO.isMember() && "Y".equals(discVO.getNonmebyn())) {
                            discInfoVO = discVO;
                        }
                    }
                }
                // ???????????? ?????? ??????????????? ?????????
                if (userVO.isMember() && "10".equals(vo.getEvtPersnGbn())) {
                    userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
                    MemberVO memberVO = myInforService.selectMemberData(userVO);
                    if (memberVO.getSpecialYn().equals("Y") || memberVO.getYearYn().equals("Y")) {
                        MyRsvnVO rsvnVO = new MyRsvnVO();
                        rsvnVO.setComcd(Config.COM_CD);
                        rsvnVO.setUniqId(memberVO.getMemNo());
                        rsvnVO.setRsvnIdxOne("0");
                        rsvnVO.setGubun("EVT");
                        rsvnVO.setProgramCd(vo.getEvtNo());
                        if ("Y".equals(memberVO.getYearYn())) {
                            rsvnVO.setMemberGbn("1"); // ????????????
                        } else {
                            rsvnVO.setMemberGbn("2"); // ????????????
                        }
                        RsvnCommVO annualData = rsvnCommService.selectAnnualDcData(rsvnVO);
                        if (annualData != null && annualData.getLimitCnt() == 0 && (discInfoVO == null || discInfoVO.getDcRate() < annualData.getDcRate())) {
                            // ?????? ??????
                            discAnnualVO = annualData;
                            evtrsvnMstVO.setDcAnnualLimit(annualData.getLimitQty());
                        }
                    }
                }

                for (EvtEdcItemAmountVO itemVO : chrgList) {
                    // ?????? ?????? ??????
                    if ("20".equals(vo.getEvtPersnGbn()) && detailVO.getDcReasonCd() != null) {
                        if ("Y".equals(itemVO.getGroupdcyn())) {
                            itemVO.setDcRate(detailVO.getDcRate());
                            itemVO.setDcName("????????????");
                        }

                    } else {
                        if (discInfoVO != null) {
                            itemVO.setDcRate(discInfoVO.getDcRate());
                            itemVO.setDcName(discInfoVO.getDcName());
                        }
                        if (discAnnualVO != null) {
                            itemVO.setDcAnnualCd(discAnnualVO.getDcReasonCd());
                            itemVO.setDcAnnualRate(discAnnualVO.getDcRate());
                            itemVO.setDcAnnualNm("???????????? ??????");
                        }
                    }
                }

                model.addAttribute("evtTime", rsTimeData);
                model.addAttribute("evtData", detailVO);
                model.addAttribute("termList", tMap.get("list"));
                model.addAttribute("chrgList", chrgList);
                model.addAttribute("discInfoVO", discInfoVO);
                model.addAttribute("target", vo.getEvtPersnGbn());
                model.addAttribute("loginVO", userVO);
                model.addAttribute("optData", rsvnCommService.selectOptData(commandMap.getParam())); // ???????????? ??????
                model.addAttribute("paramMap", paramMap);

                log.debug("final model = " + model);

                return HttpUtility.getViewUrl(request);
            } else {
                msg = "?????? ?????? ????????? ????????????.";
                if ("Y".equals(detailVO.getEvtStdmembyn()) && !userVO.isMember()) {
                    msg = "?????? ?????? ?????? ???????????? ?????????.?????? ????????? ??? ????????? ?????????.";
                } else if (nshw > 0) {
                    msg = "????????? ????????? ???????????????. ???????????? ??????????????? ?????????????????????.";
                }
                HttpUtility.sendBack(request, response, msg);
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * ???????????? ????????? ?????? ??????
     * 
     * @param commandMap
     * @return ModelAndView - ??????
     * @exception Exception
     */
    @PostMapping(value = "/evtEdcrsvnStep2")
    public String rsvnStepScnd(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("evtrsvnMstVO") EvtEdcrsvnMstVO evtrsvnMstVO, ModelMap model) throws Exception {

        log.debug("call rsvnStepScnd()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        // Declare
        Map<String, Object> pMap = new HashMap<String, Object>();
        LoginVO userVO = commandMap.getUserInfo();
        String msg = "";

        // set parameter
        EvtEdcStdmngVO vo = new EvtEdcStdmngVO();
        vo.setComcd(Config.COM_CD);
        vo.setEvtNo(evtrsvnMstVO.getEvtNo());
        vo.setEvtTimeseq(evtrsvnMstVO.getEvtTimeseq());
        vo.setEvtTime(evtrsvnMstVO.getEvtVeingdate());

        EventProgramVO eVo = new EventProgramVO();
        eVo.setComcd(Config.COM_CD);
        eVo.setEvtNo(Integer.parseInt(vo.getEvtNo()));
        eVo.setEvtTime(vo.getEvtTime());

        // ????????????
        EventProgramVO detailVO = evtEdcrsvnSMainService.selectEvtEdcPrgDetail(eVo);

        // ????????????
        String dayWeek = evtEdcrsvnSMainService.selectEvtEdcWeek(commandMap.getParam());
        vo.setDayWeek(dayWeek);

        // ????????????
        CamelMap timeVO = evtEdcrsvnSMainService.selectEvtEdcTimeDetail(vo);
        commandMap.put("COMCD", Config.COM_CD);

        if (checkReserve(userVO, commandMap, detailVO, timeVO, request, response)) {

            long totalCnt = 0;
            long dcAmountLong = 0;
            long totalAmount = 0;

            RsvnCommVO discOpt = rsvnCommService.selectOptData(commandMap.getParam()); // ???????????? ??????
            int limitQty = evtrsvnMstVO.getDcAnnualLimit();
            int remainCnt = 0;

            /*
             * JYS 2021.05.19
             * for (EvtItemAmountVO charge : evtrsvnMstVO.getChargeList()) {
             * //???????????? ????????? ??????
             * if (charge.getDcAnnualRate() > 0 && limitQty == 0) {
             * limitQty = 1000;
             * }
             * totalCnt += charge.getItemCnt();
             * int annualCnt = limitQty - remainCnt;
             * int orignCnt = Integer.parseInt(charge.getItemCnt()+"");
             * int cnt = orignCnt;
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
             * dcAmountLong3 = CommonUtil.DoubleToLongCalc(charge.getItemPrice() * charge.getDcAnnualRate() * 0.01 *
             * annualCnt, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
             * remainCnt += orignCnt;
             * }
             * long dcAmountLong2 = CommonUtil.DoubleToLongCalc(charge.getItemPrice() * charge.getDcRate() * 0.01 * cnt
             * , discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
             * totalAmount += charge.getItemPrice()* charge.getItemCnt();
             * dcAmountLong += dcAmountLong2;
             * dcAmountLong += dcAmountLong3;
             * }
             * }
             */

            if (CommonUtil.getInt(timeVO.get("totCapa")) < (CommonUtil.getInt(timeVO.get("rsvCnt")) + totalCnt)) {
                HttpUtility.sendBack(request, response, "??????????????? ???????????? ???????????? ??? ????????????.(????????????:" + (CommonUtil.getInt(timeVO.get("totCapa")) - CommonUtil.getInt(timeVO.get("rsvCnt"))) + "???)");
                return null;
            }

            int nshw = 0;
            if ("2001".equals(detailVO.getEvtFeeType())) {
                // ????????????
                pMap.put("comcd", Config.COM_CD);
                pMap.put("evtRsvnMemno", userVO.getUniqId());
                pMap.put("evtPartcd", detailVO.getEvtPartCd());
                pMap.put("ctgCd", detailVO.getCtgCd());

                nshw = evtEdcrsvnService.selectMemNshw(pMap);
            } else {
                nshw = 0;
            }

            evtrsvnMstVO.setEvtPartcd(detailVO.getEvtPartCd());
            // ?????? ?????? ?????? ??????
            if (("Y".equals(detailVO.getEvtNonmebyn()) && !userVO.isMember()) || ("Y".equals(detailVO.getEvtStdmembyn()) && userVO.isMember()) || ("Y".equals(detailVO.getEvtAnualmembyn()) && "Y".equals(userVO.getYearYn())) || ("Y".equals(detailVO.getEvtSpeclmembyn()) && "Y".equals(userVO.getSpecialYn())) || nshw <= 0) {

                if (userVO.isMember()) {
                    evtrsvnMstVO.setEvtRsvnMemtype("1001");
                    evtrsvnMstVO.setEvtRsvnMemno(userVO.getUniqId());
                    evtrsvnMstVO.setEvtRsvnWebid(userVO.getId());
                    evtrsvnMstVO.setEvtEmail(userVO.getEmail());
                    evtrsvnMstVO.setEvtRsvnGrpnm(userVO.getOrgnztNm()); // ?????????
                } else {
                    evtrsvnMstVO.setEvtRsvnMemtype("2001");
                    evtrsvnMstVO.setEvtRsvnWebid("NONMEMBER");
                    evtrsvnMstVO.setEvtNonmembCertno(userVO.getHpcertno());
                }
                evtrsvnMstVO.setEvtRsvnCustnm(userVO.getName());
                evtrsvnMstVO.setEvtRsvnMoblphon(userVO.getIhidNum());

                model.addAttribute("timeVO", timeVO);
                model.addAttribute("detail", detailVO);
                model.addAttribute("chrgList", evtrsvnMstVO.getChargeList());
                model.addAttribute("userVO", userVO);
                model.addAttribute("dcAmount", dcAmountLong);
                model.addAttribute("totalAmount", totalAmount);
                model.addAttribute("paramMap", paramMap);

                log.debug("final model = " + model);

                return HttpUtility.getViewUrl(request);
            } else {
                msg = "?????? ?????? ????????? ????????????.";
                if ("Y".equals(detailVO.getEvtStdmembyn()) && !userVO.isMember()) {
                    msg = "?????? ?????? ?????? ???????????? ?????????.?????? ????????? ??? ????????? ?????????.";
                } else if (nshw > 0) {
                    msg = "????????? ????????? ???????????????. ???????????? ??????????????? ?????????????????????.";
                }
                HttpUtility.sendBack(request, response, msg);
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * ???????????? ?????? ??????
     * 
     * @param commandMap
     * @return ModelAndView - ??????
     * @exception Exception
     */
    @ResponseBody
    @PostMapping(value = "/evtEdcrsvnAction")
    public ModelAndView rsvnStepInsert(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("evtrsvnMstVO") EvtEdcrsvnMstVO evtrsvnMstVO, ModelMap model) throws Exception {

        log.debug("call rsvnStepInsert()");

        Map<String, Object> paramMap = commandMap.getParam();

        log.debug("paramMap = " + paramMap);

        ModelAndView mav = new ModelAndView("jsonView");
        Map<String, String> returnMap = new HashMap<String, String>();
        String rtnCd = "";

        if (commandMap.getUserInfo() == null) {
            throw new MyException("???????????? ???????????????.????????? ??? ?????? ????????? ?????????.", -3);
        }

        // ????????????
        LoginVO userVO = new LoginVO();
        userVO.setUniqId(evtrsvnMstVO.getEvtRsvnMemno());
        userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        MemberVO memberVO = myInforService.selectMemberData(userVO);

        EventProgramVO eVo = new EventProgramVO();
        eVo.setComcd(Config.COM_CD);
        eVo.setEvtNo(Integer.parseInt(evtrsvnMstVO.getEvtNo()));
        eVo.setEvtTime(evtrsvnMstVO.getEvtTime());

        // ????????????
        EventProgramVO detailVO = evtEdcrsvnSMainService.selectEvtEdcPrgDetail(eVo);

        // ?????? ?????? ?????? ??????
        if (!"Y".equals(detailVO.getRsvAbleYn())) {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "?????? ?????? ????????? ????????????.");
        } else if (detailVO.getHoldCnt() > 0) {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "?????? ????????? ????????? ????????????.");
        } else if (("Y".equals(detailVO.getEvtNonmebyn()) && memberVO == null) || ("Y".equals(detailVO.getEvtStdmembyn()) && memberVO != null) || ("Y".equals(detailVO.getEvtAnualmembyn()) && memberVO != null && "Y".equals(memberVO.getYearYn())) || ("Y".equals(detailVO.getEvtSpeclmembyn()) && memberVO != null && "Y".equals(memberVO.getSpecialYn()))) {
            // set parameter
            evtrsvnMstVO.setComcd(Config.COM_CD);
            evtrsvnMstVO.setDbEnckey(EgovProperties.getProperty("Globals.DbEncKey"));
            evtrsvnMstVO.setReguser(evtrsvnMstVO.getEvtRsvnWebid());
            evtrsvnMstVO.setEvtOnoffintype(Config.ONLINE);

            if (request.getAttribute("IS_MOBILE") == null) {
                evtrsvnMstVO.setEvtTrmnltype("2001");
            } else {
                boolean isMobile = (boolean) request.getAttribute("IS_MOBILE");
                evtrsvnMstVO.setEvtTrmnltype(isMobile ? "2002" : "2001");
            }
            // ???????????? ??????
            String evtRsvnno = evtEdcrsvnService.selectRsvnNumber();
            evtrsvnMstVO.setEvtRsvnno(evtRsvnno);
            evtrsvnMstVO.setEvtVeingnmpr(String.valueOf(paramMap.get("evtVeingnmpr")));

            // OID ??????
            OrderIdVO orderVO = new OrderIdVO();
            orderVO.setComcd(evtrsvnMstVO.getComcd());
            orderVO.setUserId(evtrsvnMstVO.getEvtRsvnWebid());
            orderVO.setRsvnNo(evtrsvnMstVO.getEvtRsvnno()); // ????????????
            orderVO.setRsvnCnt(1);
            orderVO.setRsvnAmt(0);
            // ???????????? ??????
            chargeService.insertDbprocLog(orderVO); // ???????????????
            //// JYS 2021.05.18 chargeService.selectOrderId(orderVO);
            String oid = orderVO.getRetOid();

            MyRsvnVO myRsvnVO = new MyRsvnVO(); // ?????? ???????????? ?????? ??? ???
            myRsvnVO.setLgdOID(oid);
            String errorMsg = "";
            try {

                returnMap = evtEdcrsvnService.insertEvtEdcrsvnInfo(request, paramMap, memberVO, evtrsvnMstVO, detailVO, myRsvnVO);
                rtnCd = returnMap.get("resultCd");
                errorMsg = "OK";

            } catch (Exception e) {
                errorMsg = e.getMessage();
                returnMap.put("resultCd", Config.FAIL);
                returnMap.put("resultMsg", "????????? ??????");

            } finally {
                if (myRsvnVO.getLgdAMOUNT() != null && myRsvnVO.getLgdAMOUNT().equals("0") && (errorMsg.equals("OK") || errorMsg.indexOf("SP_CREATE_PAMENTINFO") > 0)) {
                    //// chargeService.insertDbprocLog(myRsvnVO);
                }
            }

            log.debug("rtnCd = " + rtnCd);

            if (Config.SUCCESS.equals(rtnCd)) {

                // ?????? ??????
                try {

                    String ticketInfo = "";

                    Map<String, String> smsParam = new HashMap<String, String>();

                    String strHp = evtrsvnMstVO.getEvtRsvnMoblphon();

                    // -------------------------------------------------------------------?????? ?????? ????????? ??????
                    String strSpowiseCmsKey = EgovProperties.getProperty("Globals.SpowiseCms.Key");

                    String strDecKeyword = WebEncDecUtil.fn_decrypt(strHp, strSpowiseCmsKey);
                    smsParam.put("hp", strDecKeyword);
                    // -------------------------------------------------------------------?????? ?????? ????????? ??????

                    smsParam.put("msgcd", "3");
                    smsParam.put("msgno", "50");
                    smsParam.put("sndHp", CommonUtil.getString(detailVO.getEvtGuideTelno()));
                    smsParam.put("????????????", evtrsvnMstVO.getEvtRsvnCustnm());
                    // smsParam.put("????????????", CommonUtil.getCommaNumber(evtrsvnMstVO.getEvtRsvnPayamt(),"###,###")+"???");
                    smsParam.put("???????????????", detailVO.getEvtName());
                    smsParam.put("????????????", evtrsvnMstVO.getEvtVeingnmpr() + "???");
                    smsParam.put("????????????", evtRsvnno);
                    smsParam.put("????????????", evtrsvnMstVO.getEvtVeingdate().substring(0, 4) + "." + evtrsvnMstVO.getEvtVeingdate().substring(4, 6) + "." + evtrsvnMstVO.getEvtVeingdate().substring(6, 8));

                    /*
                     * log.debug("smsParam = 9");
                     * smsParam.put("????????????",evtrsvnMstVO.getEvtStime().substring(0,2)+":"+evtrsvnMstVO.getEvtStime().
                     * substring(2));
                     * log.debug("smsParam = 10");
                     * smsParam.put("????????????",evtrsvnMstVO.getEvtEtime().substring(0,2)+":"+evtrsvnMstVO.getEvtEtime().
                     * substring(2));
                     */

                    /*
                     * if ("Y".equals(returnMap.get("freeYn"))) {
                     * if ("Y".equals(detailVO.getEvtTicketChkyn())) {
                     * String ticketNo = evtEdcrsvnService.selectTicketNumber(evtrsvnMstVO);
                     * if (ticketNo !=null && !ticketNo.equals("")) {
                     * String strKey = EgovProperties.getProperty("Globals.Ticket.Key");
                     * String strAedValue = ScrEncDecUtil.fn_encrypt_url(evtRsvnno,strKey);
                     * ticketInfo += "??????????????? : " + ticketNo;
                     * ticketInfo += "\n?????? ????????? ??????   ????????????????????? ?????? ?????? ???????????? ?????? ?????????.";
                     * ticketInfo += "\n?????????????????? : "+EgovProperties.getProperty("Globals.Domain") +"/ticket/"+strAedValue;
                     * }
                     * }
                     * } else if ("10".equals(evtrsvnMstVO.getEvtPersnGbn())) {
                     * smsParam.put("msgno", "28"); //??????
                     * String payWaitTime =evtrsvnMstVO.getPayWaitTime();
                     * smsParam.put("??????????????????",payWaitTime.substring(0,4)+"."+payWaitTime.substring(4,6)+"."+payWaitTime.
                     * substring(6,8)+" "+payWaitTime.substring(8,10)+":"+payWaitTime.substring(10));
                     * } else {
                     * smsParam.put("msgno", "28"); //??????
                     * smsParam.put("??????????????????","?????? ?????? ????????? ?????? ???????????? ????????? ??????????????? ????????? ??? ????????????.");
                     * }
                     */

                    log.debug("smsParam = " + smsParam);

                    smsParam.put("????????????", ticketInfo);
                    smsUmsService.sendMessage(smsParam, userVO);

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
    @PostMapping(value = { "/evtEdcrsvnResult", "/evtEdcrsvnPop" })
    public String rsvnResult(@ModelAttribute("evtrsvnMstVO") EvtEdcrsvnMstVO evtrsvnMstVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        commandMap.put("comcd", Config.COM_CD);
        evtrsvnMstVO.setDbEnckey(EgovProperties.getProperty("Globals.DbEncKey"));

        model.addAttribute("resultVO", evtEdcrsvnService.selectEvtEdcrsvnDetail(evtrsvnMstVO));

        List<?> grpRsvnFamlyinfo = evtEdcrsvnService.selectGrpRsvnFamlyinfo(evtrsvnMstVO);
        model.addAttribute("grpRsvnFamlyinfo", grpRsvnFamlyinfo);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ???????????? ???????????? ??????
     *
     * @param partCd
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping(value = "/rsvnCntAjax")
    public ModelAndView selectRsvnCnt(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        Map<String, Object> map = commandMap.getParam();
        int result = evtEdcrsvnService.selectEvtEdcrsvnCnt(map);

        mav.addObject("rsvnCnt", result);
        return mav;
    }

    /**
     * ???????????? ?????? ajax
     *
     * @param partCd
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping(value = "/evtEdcCapaCntAjax")
    public ModelAndView selectEvtCapaCnt(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            EvtEdcrsvnMstVO evtrsvnMstVO) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        evtrsvnMstVO.setComcd(Config.COM_CD);

        // ???????????? ??????
        Map<String, Object> memChk = evtEdcrsvnService.selectEvtEdcRmnAmnt(evtrsvnMstVO);

        mav.addObject("result", memChk.get("result"));
        return mav;
    }

    /**
     * ???????????? ???????????? ajax
     *
     * @param partCd
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping(value = "/rsvnDetailAjax")
    public ModelAndView selectRsvnDetail(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            EvtEdcStdmngVO vo) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        vo.setComcd(Config.COM_CD);
        // ????????????
        List<CamelMap> timeList = evtEdcrsvnSMainService.selectEvtEdcTimeDetailList(vo);
        mav.addObject("detail", timeList);
        return mav;
    }

    public boolean checkReserve(LoginVO userVO, CommandMap param, EventProgramVO detailVO, CamelMap timeVO,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String url = Config.USER_ROOT + "/evtedcrsvn/evtEdcrsvnDetail?evtNo=" + detailVO.getEvtNo() + "&ymd=" + param.getString("evtTime");

        if (detailVO == null || timeVO == null) {
            HttpUtility.sendBack(request, response, "???????????? ???????????? ????????????. ?????? ????????? ?????? ????????? ?????????.");
            return false;
        } else if (!"Y".equals(detailVO.getRsvAbleYn())) {
            HttpUtility.sendRedirect(request, response, "?????? ?????? ????????? ????????????.", url);
            return false;
        } else if (detailVO.getHoldCnt() > 0) {
            HttpUtility.sendRedirect(request, response, "?????? ????????? ????????? ????????????.", url);
            return false;
        } else if (!"Y".equals((String) timeVO.get("rsvAbleYn"))) {
            HttpUtility.sendRedirect(request, response, "?????? ????????? ?????????????????????.", url);
            return false;
        } else if (CommonUtil.getInt(timeVO.get("totCapa")) <= CommonUtil.getInt(timeVO.get("rsvCnt"))) {
            HttpUtility.sendRedirect(request, response, "????????? ?????????????????????.", url);
            return false;
        } else if (userVO == null) { // ????????? ?????? ??????
            String returnURL = Config.USER_ROOT + "/evtrsvn/evtEdcrsvnStep1?evtPersnGbn=" + param.getString("evtPersnGbn") + "&evtTimeseq=" + param.getString("evtTimeseq") + "&evtTimestdseq=" + param.getString("evtTimestdseq") + "&evtNo=" + param.getString("evtNo") + "&evtTime=" + param.getString("evtTime");
            HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/member/login?returnURL=" + URLEncoder.encode(returnURL, "UTF-8"));
            return false;

        } else if ("10".equals(param.getString("evtPersnGbn")) && userVO.getGender().equals("2") && detailVO.getEvtSexdstn().equals("1001")) {
            HttpUtility.sendBack(request, response, "?????? ????????? ????????? ???????????? ???????????? ??? ????????????. ?????? ?????? ????????? ????????? ????????? ????????????.");
            return false;
        } else if ("10".equals(param.getString("evtPersnGbn")) && userVO.getGender().equals("1") && detailVO.getEvtSexdstn().equals("2001")) {
            HttpUtility.sendBack(request, response, "?????? ????????? ????????? ???????????? ???????????? ??? ????????????. ?????? ?????? ????????? ????????? ????????? ????????????.");
            return false;
        } else {

            // ???????????? ??????
            param.put("evtNonmembCertno", userVO.getHpcertno());
            param.put("evtRsvnMemno", userVO.getUniqId());
            int myCnt = evtEdcrsvnService.selectEvtEdcrsvnCnt(param.getParam());

            String msg = "";

            if (detailVO.getEvtMaxtimeCnt() > 0 && myCnt >= detailVO.getEvtMaxtimeCnt()) {
                msg = "?????? ?????? ?????? ??? ?????????????????????. (?????? " + detailVO.getEvtMaxtimeCnt() + "???)";
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
     * ????????????
     * 
     * @param commandMap
     * @return String - jsp ?????????
     * @exception Exception
     */
    @PostMapping(value = { "/evtEdcrsvnIndiResult" })
    public String rsvnIndiResult(@ModelAttribute("evtEdcrsvnMstVO") EvtEdcrsvnMstVO evtrsvnMstVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        commandMap.put("comcd", Config.COM_CD);
        evtrsvnMstVO.setDbEnckey(EgovProperties.getProperty("Globals.DbEncKey"));
        // ?????? ??????

        model.addAttribute("resultVO", evtEdcrsvnService.selectEvtEdcrsvnDetail(evtrsvnMstVO));
        return HttpUtility.getViewUrl(request);
    }

}
