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
 * 관람 > 관람상품관련 컨트롤러
 * 
 * @author 전영석
 * @since 2021.05.19
 * @version 1.0, 2021.05.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.05.19 최초작성
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
     * 관람 메인
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
     * 관람 메인
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
                // 접수 가능 날짜 셋팅
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
     * 관람 달력
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
     * 예약가능 일정 체크
     * 
     * @param commandMap
     * @return ModelAndView - 결과
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
     * 예약가능 상품 목록
     * 
     * @param commandMap
     * @return ModelAndView - 결과
     * @exception Exception
     */
    @GetMapping(value = "/exbtBaseListAjax")
    public String selectExbtBaseList(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        commandMap.put("comcd", Config.COM_CD);

        String dayWeek = dspyDsService.selectExbtWeek(commandMap.getParam());

        commandMap.put("dayWeek", dayWeek); // 요일체크

        model.addAttribute("holidayCheck", dspyDsService.selectHolidayCheck(commandMap.getParam())); // 휴관일인지
        model.addAttribute("baseList", dspyDsService.selectExbtBaseList(commandMap.getParam()));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 관람프로그램 상세 페이지
     * 
     * @param commandMap
     * @return jsp 페이지
     * @exception Exception
     */
    @GetMapping(value = "/exbtDetailAjax")
    public String selectExbtBaseDetail(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        commandMap.put("comcd", Config.COM_CD);

        String dayWeek = dspyDsService.selectExbtWeek(commandMap.getParam());

        commandMap.put("dayWeek", dayWeek); // 요일체크

        int limitCount = 0;

        if (!commandMap.getString("exbtSeq").equals("")) {
            ExbtBaseruleVO baseDataVO = dspyDsService.selectExbtBaseDetail(commandMap.getParam());
            model.addAttribute("baseDataVO", baseDataVO);

            // 대표 이미지
            if (baseDataVO != null) {
                String fileId = EgovStringUtil.isNullToString(baseDataVO.getExbtImgFinnb());

                // 파일 리스트 검색
                if (!"".equals(fileId)) {
                    FileVO fileVO = new FileVO();
                    fileVO.setFileGrpinnb(fileId);
                    model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
                }

                // 시간표
                model.addAttribute("timeList", dspyDsService.selectExbtTimeList(commandMap.getParam()));

                LoginVO loginVO = commandMap.getUserInfo();

                RsvnMasterVO maserVO = new RsvnMasterVO();
                maserVO.setYmd(commandMap.getString("ymd"));
                maserVO.setComcd(commandMap.getString("comcd"));
                maserVO.setMemNo(loginVO != null && loginVO.isMember() ? commandMap.getUserInfo().getUniqId() : "");
                maserVO.setHpcertno(loginVO != null && !loginVO.isMember() ? loginVO.getHpcertno() : "");
                maserVO.setLimitMethod(baseDataVO.getRsvnLimitMethod());

                // 이용횟수 조회
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
            HttpUtility.sendBack(request, response, "관람 데이타가 없습니다. 선택 내용을 다시 확인해 주세요.");
            return false;
        } else if (baseDataVO.getHoldCnt() > 0) {
            HttpUtility.sendRedirect(request, response, "선택하신 날짜는 휴관일입니다.", url);
            return false;
        } else if (!"Y".equals(baseDataVO.getReserveAbleYn()) || !"Y".equals(timeVO.getReserveAbleYn()) || !"Y".equals(baseDataVO.getTodayYn())) {
            HttpUtility.sendRedirect(request, response, "예약 시간이 종료되었습니다.", url);
            return false;
        } else if (timeVO.getTotCapa() <= timeVO.getReserveCnt()) {
            HttpUtility.sendRedirect(request, response, "예약이 마감되었습니다.", url);
            return false;
        } else if (!"Y".equals(timeVO.getGroupyn()) && "20".equals(rsvnMasterVO.getTarget())) {
            HttpUtility.sendRedirect(request, response, "단체 예약이 불가능한 관람 유형 입니다.", url);
            return false;
        } else if (!"Y".equals(timeVO.getPersonyn()) && "10".equals(rsvnMasterVO.getTarget())) {
            HttpUtility.sendRedirect(request, response, "개인 예약이 불가능한 관람 유형 입니다.", url);
            return false;
        } else if (userVO == null) { // 로그인 정보 없음
            String returnURL = Config.USER_ROOT + "/exbtrsvn/reserveStep1?target=" + rsvnMasterVO.getTarget() + "&exbtSeq=" + rsvnMasterVO.getExbtSeq() + "&stdseq=" + rsvnMasterVO.getStdseq() + "&timeseq=" + rsvnMasterVO.getTimeseq() + "&ymd=" + rsvnMasterVO.getYmd();

            HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/member/login?returnURL=" + URLEncoder.encode(returnURL, "UTF-8"));
            return false;
        } else {
            rsvnMasterVO.setComcd(Config.COM_CD);
            rsvnMasterVO.setMemNo(userVO != null && userVO.isMember() ? userVO.getUniqId() : "");
            rsvnMasterVO.setHpcertno(userVO != null && !userVO.isMember() ? userVO.getHpcertno() : "");
            rsvnMasterVO.setLimitMethod(baseDataVO.getRsvnLimitMethod());

            // 이용횟수 조회
            int limitCount = dspyDsService.selectReserveLimitCount(rsvnMasterVO);

            String msg = "";

            if (baseDataVO.getRsvnLimitMethod().equals("20") && baseDataVO.getRsvnLimitCnt() > 0 && limitCount >= baseDataVO.getRsvnLimitCnt()) {
                msg = "예약 가능 횟수 가 초과되었습니다. (하루 최대 " + baseDataVO.getRsvnLimitCnt() + "회)";
            } else if (baseDataVO.getRsvnLimitMethod().equals("21") && baseDataVO.getRsvnLimitCnt() > 0 && limitCount >= baseDataVO.getRsvnLimitCnt()) {
                msg = "예약 가능 횟수 가 초과되었습니다. (한달 최대 " + baseDataVO.getRsvnLimitCnt() + "회)";
            } else if (baseDataVO.getRsvnLimitMethod().equals("22") && baseDataVO.getRsvnLimitCnt() > 0 && limitCount >= baseDataVO.getRsvnLimitCnt()) {
                msg = "예약 가능 횟수 가 초과되었습니다. (1년 최대 " + baseDataVO.getRsvnLimitCnt() + "회)";
            }

            if (!msg.equals("")) {
                HttpUtility.sendRedirect(request, response, msg, url);

                return false;
            } else {
                // 예약 가능 대상 체크

                return true;
            }
        }

    }

    /**
     * 예약 안내 및 주문
     * 
     * @param commandMap
     * @return String - jsp 페이지
     * @exception Exception
     */
    @GetMapping(value = "/reserveStep1")
    public String selectExbtReserveStep1(@ModelAttribute("rsvnMasterVO") RsvnMasterVO rsvnMasterVO,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        commandMap.put("comcd", Config.COM_CD);
        // 관람 기준 정보
        ExbtBaseruleVO baseDataVO = dspyDsService.selectExbtBaseDetail(commandMap.getParam());

        // 요일체크
        String dayWeek = dspyDsService.selectExbtWeek(commandMap.getParam());
        commandMap.put("dayWeek", dayWeek);

        // 회차 정보
        ExbtTimeVO timeVO = dspyDsService.selectExbtTimeData(commandMap.getParam());

        // 제한 사항 체크
        if (checkExbtReserve(commandMap.getUserInfo(), rsvnMasterVO, baseDataVO, timeVO, request, response)) {
            // 회원정보
            LoginVO userVO = commandMap.getUserInfo();

            if (("Y".equals(baseDataVO.getRsvnNonmebyn()) && !userVO.isMember()) || ("Y".equals(baseDataVO.getRsvnStdmembyn()) && userVO.isMember()) || ("Y".equals(baseDataVO.getRsvnAnualmembyn()) && userVO != null && "Y".equals(userVO.getYearYn())) || ("Y".equals(baseDataVO.getRsvnSpeclmembyn()) && userVO != null && "Y".equals(userVO.getSpecialYn()))) {

                rsvnMasterVO.setWebId(userVO.getId());
                rsvnMasterVO.setCustNm(userVO.getName());
                rsvnMasterVO.setExbtHp(userVO.getIhidNum());
                rsvnMasterVO.setExbtEmail(userVO.getEmail());
                rsvnMasterVO.setMemType(userVO.isMember() ? "1001" : "2001"); // 회원 , 비회원
                rsvnMasterVO.setHpcertno(userVO.getHpcertno()); // 비회원 인증정보
                rsvnMasterVO.setGrpnm(userVO.getOrgnztNm()); // 단체명

                //// JYS 2021.05.19 List<ExbtChargeVO> chargeList =
                //// dspyDsService.selectExbtChargeList(commandMap.getParam());

                List<ExbtChargeVO> chargeList = null; // JYS 2021.05.19

                // 이벤트 할인 여부 조회
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
                            // 이미 이벤트 할당됨
                            break;
                        } else if (userVO.isMember() && "Y".equals(discVO.getMemberyn())) {
                            discInfoVO = discVO;
                        } else if (!userVO.isMember() && "Y".equals(discVO.getNonmebyn())) {
                            discInfoVO = discVO;
                        }
                    }
                }

                // 유료회원
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
                            rsvnVO.setMemberGbn("1"); // 연간회원
                        } else {
                            rsvnVO.setMemberGbn("2"); // 특별회원
                        }
                        RsvnCommVO annualData = rsvnCommService.selectAnnualDcData(rsvnVO);
                        if (annualData != null && annualData.getLimitCnt() == 0 && (discInfoVO == null || discInfoVO.getDcRate() < annualData.getDcRate())) {
                            // 제한 없이
                            discAnnualVO = annualData;
                            // dcTypeCd = "8001";

                            rsvnMasterVO.setDcAnnualLimit(annualData.getLimitQty());
                        }
                    }
                }

                /*
                 * 2021.05.19
                 * for (ExbtChargeVO itemVO : chargeList) {
                 * // 단체 할인 적용
                 * if ("20".equals(rsvnMasterVO.getTarget()) && baseDataVO.getDcReasonCd() != null) {
                 * if ( "Y".equals(itemVO.getGroupdcYn())) {
                 * itemVO.setDcKindCd("6001");
                 * itemVO.setDcType(baseDataVO.getDcReasonCd());
                 * itemVO.setDcRate(baseDataVO.getDcRate());
                 * itemVO.setDcName("단체할인");
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
                 * itemVO.setDcAnnualNm("유료회원 할인");
                 * }
                 * }
                 * }
                 */

                rsvnMasterVO.setChargeList(chargeList);

                model.addAttribute("baseDataVO", baseDataVO);
                model.addAttribute("termsList", dspyDsService.selectTermsList());
                model.addAttribute("timeVO", timeVO);
                model.addAttribute("discInfoVO", discInfoVO);
                model.addAttribute("optData", rsvnCommService.selectOptData(commandMap.getParam())); // 금액단위 설정

                /*
                 * JYS 2021.05.19
                 * if (chargeList == null || chargeList.size() < 1) {
                 * HttpUtility.sendBack(request, response, "요금이 설정되어 있지 않습니다.\n관리자에게 문의해 주세요.");
                 * return null;
                 * } else {
                 * return HttpUtility.getViewUrl(request);
                 * }
                 */

                return HttpUtility.getViewUrl(request);

            } else {
                String msg = "예약 가능 대상이 아닙니다.";
                if (!"Y".equals(baseDataVO.getRsvnNonmebyn()) && !userVO.isMember()) {
                    msg = "일반 회원 이상 예약가능 합니다.회원 로그인 후 예약해 주세요.";
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
     * 예약 안내 및 주문
     * 
     * @param commandMap
     * @return String - jsp 페이지
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
        // 관람 기준 정보
        ExbtBaseruleVO baseDataVO = dspyDsService.selectExbtBaseDetail(commandMap.getParam());

        // 요일체크
        String dayWeek = dspyDsService.selectExbtWeek(commandMap.getParam());
        commandMap.put("dayWeek", dayWeek);

        // 회차 정보
        ExbtTimeVO timeVO = dspyDsService.selectExbtTimeData(commandMap.getParam());
        LoginVO userVO = commandMap.getUserInfo();

        // 제한 사항 체크
        if (checkExbtReserve(commandMap.getUserInfo(), rsvnMasterVO, baseDataVO, timeVO, request, response)) {

            rsvnMasterVO.setWebId(userVO.getId());
            rsvnMasterVO.setMemNo(userVO.getUniqId());
            rsvnMasterVO.setCustNm(userVO.getName());
            rsvnMasterVO.setExbtHp(userVO.getIhidNum());
            rsvnMasterVO.setExbtEmail(userVO.getEmail());
            rsvnMasterVO.setMemType(userVO.isMember() ? "1001" : "2001"); // 회원 , 비회원
            rsvnMasterVO.setHpcertno(userVO.getHpcertno()); // 비회원 인증정보
            rsvnMasterVO.setGrpnm(userVO.getOrgnztNm()); // 단체명

            RsvnCommVO discOpt = rsvnCommService.selectOptData(commandMap.getParam()); // 금액단위 설정

            long totalCnt = 0;
            long dcAmountLong = 0;
            long totalAmount = 0;

            int limitQty = rsvnMasterVO.getDcAnnualLimit();
            int remainCnt = 0;

            /*
             * JYS 2021.05.19
             * for (ExbtChargeVO charge : rsvnMasterVO.getChargeList() ) {
             * //유료회원 무제한 할인
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
             * //유료회원할인을 먹인다
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
                HttpUtility.sendBack(request, response, "잔여정원을 초과하여 신청하실 수 없습니다.(잔여정원:" + (timeVO.getTotCapa() - timeVO.getReserveCnt()) + "명)");
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
                msg = "예약 가능 대상이 아닙니다.";
                if (!"Y".equals(baseDataVO.getRsvnNonmebyn()) && !userVO.isMember()) {
                    msg = "일반 회원 이상 예약가능 합니다.회원 로그인 후 예약해 주세요.";
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
     * 예악정보 저장
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
            throw new MyException("로그아웃 되었습니다.로그인 후 다시 이용해 주세요.", -3);
        }

        // 관람 기준 정보
        commandMap.put("comcd", Config.COM_CD);
        ExbtBaseruleVO baseDataVO = dspyDsService.selectExbtBaseDetail(commandMap.getParam());

        // 회원정보
        LoginVO userVO = new LoginVO();
        userVO.setUniqId(rsvnMasterVO.getMemNo());
        userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        MemberVO memberVO = myInforService.selectMemberData(userVO);

        if (baseDataVO.getHoldCnt() > 0) {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "선택하신 날짜는 휴관일입니다.");
        } else if (!"Y".equals(baseDataVO.getReserveAbleYn()) && !"Y".equals(baseDataVO.getTodayYn())) {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "예약가능 시간이 종료되었습니다.");
            // 예약 가능 대상 체크
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

            // 예약번호 생성
            String rsvnNo = dspyDsService.selectRsvnNumber();
            rsvnMasterVO.setRsvnNo(rsvnNo);

            // 주문번호 생성
            // OID 생성
            OrderIdVO orderVO = new OrderIdVO();
            orderVO.setComcd(rsvnMasterVO.getComcd());
            orderVO.setUserId(rsvnMasterVO.getWebId());
            orderVO.setRsvnNo(rsvnMasterVO.getRsvnNo()); // 예약번호
            orderVO.setRsvnCnt(1);
            orderVO.setRsvnAmt(0);
            chargeService.insertDbprocLog(orderVO);

            //// chargeService.selectOrderId(orderVO); for Tibero 2021.03.17

            chargeService.selectOrderIdNoPg(orderVO);

            String oid = orderVO.getRetOid();

            log.debug("oid = " + oid);

            // insert
            MyRsvnVO myRsvnVO = new MyRsvnVO(); // 결제 프로시져 리턴 값 용
            myRsvnVO.setLgdOID(oid);
            String errorMsg = "";
            try {

                returnMap = dspyDsService.insertRegistMst(memberVO, rsvnMasterVO, baseDataVO, myRsvnVO);

                errorMsg = "OK";

            } catch (Exception e) {
                errorMsg = e.getMessage();
                returnMap.put("resultCd", Config.FAIL);
                returnMap.put("resultMsg", "시스템 오류");

            } finally {
                if (myRsvnVO.getLgdAMOUNT() != null && myRsvnVO.getLgdAMOUNT().equals("0") && (errorMsg.equals("OK") || errorMsg.indexOf("SP_CREATE_PAMENTINFO") > 0)) {
                    chargeService.insertDbprocLog(myRsvnVO);
                }
            }

            if (Config.SUCCESS.equals(returnMap.get("resultCd"))) {
                // 문자 발송
                try {
                    String ticketInfo = "";
                    Map<String, String> smsParam = new HashMap<String, String>();
                    smsParam.put("msgcd", "2");
                    smsParam.put("msgno", "2");
                    smsParam.put("sndHp", baseDataVO.getExbtGuideTelno());
                    smsParam.put("hp", rsvnMasterVO.getExbtHp());
                    smsParam.put("예약자명", rsvnMasterVO.getCustNm());
                    smsParam.put("관명", baseDataVO.getExbtPartNm());
                    smsParam.put("관람제목", baseDataVO.getExbtName());
                    smsParam.put("예약상품", returnMap.get("itemInfo"));
                    smsParam.put("예약번호", rsvnNo);
                    smsParam.put("관람일시", rsvnMasterVO.getYmd().substring(0, 4) + "." + rsvnMasterVO.getYmd().substring(4, 6) + "." + rsvnMasterVO.getYmd().substring(6, 8) + " " + rsvnMasterVO.getStartTime().substring(0, 2) + ":" + rsvnMasterVO.getStartTime().substring(2));
                    smsParam.put("문의전화", baseDataVO.getExbtGuideTelno());

                    if ("Y".equals(returnMap.get("freeYn"))) {
                        if ("Y".equals(baseDataVO.getExbtTicketChkyn())) {
                            String ticketNo = dspyDsService.selectTicketNumber(rsvnMasterVO);
                            if (ticketNo != null && !ticketNo.equals("")) {
                                String strKey = EgovProperties.getProperty("Globals.Ticket.Key");

                                String strAedValue = ScrEncDecUtil.fn_encrypt_url(rsvnNo, strKey);
                                ticketInfo += "▶티켓번호 : " + ticketNo;
                                ticketInfo += "\n관람 입장시 아래   티켓확인하기를 눌러 티켓 바코드를 보여 주세요.";
                                ticketInfo += "\n티켓확인하기 : " + EgovProperties.getProperty("Globals.Domain") + "/ticket/" + strAedValue;
                            }
                        }
                    } else if ("10".equals(rsvnMasterVO.getTarget())) {
                        smsParam.put("msgno", "18"); // 유료
                        String payWaitTime = rsvnMasterVO.getPayWaitTime();
                        smsParam.put("결제마감시간", payWaitTime.substring(0, 4) + "." + payWaitTime.substring(4, 6) + "." + payWaitTime.substring(6, 8) + " " + payWaitTime.substring(8, 10) + ":" + payWaitTime.substring(10));
                    } else {
                        smsParam.put("msgno", "18"); // 유료
                        smsParam.put("결제마감시간", "관람 시작 전까지 결제 가능하며 당일날 현장에서도 결제할 수 있습니다.");
                    }
                    smsParam.put("티켓정보", ticketInfo);
                    smsService.sendMessage(smsParam, commandMap.getUserInfo());

                } catch (Exception e) {
                    // sms발송 오류 체크
                    log.error("회원가입 문자발송 오류:" + e.getMessage());
                }
            }
        } else {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "예약 가능한 대상에 속하지 않습니다. 예약 가능한 대상을 다시 확인해 주세요.");
        }

        mav.addObject("result", returnMap);
        return mav;
    }

    /**
     * 예약완료
     * 
     * @param commandMap
     * @return String - jsp 페이지
     * @exception Exception
     */
    @PostMapping(value = "/reserveResult")
    public String selectExbtReserveEnd(@ModelAttribute("rsvnMasterVO") RsvnMasterVO rsvnMasterVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        rsvnMasterVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        // 예약 정보
        RsvnMasterVO resultVO = dspyDsService.selectRegistMst(rsvnMasterVO);
        resultVO.setChargeList(dspyDsService.selectRegistItemList(rsvnMasterVO));

        model.addAttribute("resultVO", resultVO);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 예약완료
     * 
     * @param commandMap
     * @return String - jsp 페이지
     * @exception Exception
     */
    @PostMapping(value = "/reserveIndiResult")
    public String selectExbtIndiReserveEnd(@ModelAttribute("rsvnMasterVO") RsvnMasterVO rsvnMasterVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        rsvnMasterVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        // 예약 정보
        RsvnMasterVO resultVO = dspyDsService.selectRegistMst(rsvnMasterVO);
        resultVO.setChargeList(dspyDsService.selectRegistItemList(rsvnMasterVO));

        model.addAttribute("resultVO", resultVO);

        return HttpUtility.getViewUrl(request);
    }

}
