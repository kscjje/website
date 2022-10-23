package com.hisco.user.evtrsvn.web;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.cmm.exception.MyException;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.ScrEncDecUtil;
import com.hisco.intrfc.charge.service.ChargeService;
import com.hisco.intrfc.charge.vo.OrderIdVO;
import com.hisco.intrfc.sms.service.SmsService;
import com.hisco.user.evtrsvn.service.EventProgramVO;
import com.hisco.user.evtrsvn.service.EvtItemAmountVO;
import com.hisco.user.evtrsvn.service.EvtStdmngVO;
import com.hisco.user.evtrsvn.service.EvtrsvnMstVO;
import com.hisco.user.evtrsvn.service.EvtrsvnSMainService;
import com.hisco.user.evtrsvn.service.EvtrsvnService;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.service.MyInforService;
import com.hisco.user.mypage.service.RsvnCommService;
import com.hisco.user.mypage.vo.MyRsvnVO;
import com.hisco.user.mypage.vo.RsvnCommVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * 강연/행사/영화 예약관련 컨트롤러
 * 
 * @author 김희택
 * @since 2020.09.01
 * @version 1.0, 2020.09.01
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          김희택 2020.09.01 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/evtrsvn")
public class EvtrsvnController {

    @Resource(name = "evtrsvnService")
    private EvtrsvnService evtrsvnService;

    @Resource(name = "evtrsvnSMainService")
    private EvtrsvnSMainService evtrsvnMainService;

    @Resource(name = "rsvnCommService")
    private RsvnCommService rsvnCommService;

    @Resource(name = "myInforService")
    private MyInforService myInforService;

    @Resource(name = "smsService")
    private SmsService smsService;

    @Resource(name = "chargeService")
    private ChargeService chargeService;

    /**
     * 강연/행사/영화 예약 안내 및 주문
     *
     * @param partCd
     * @param model
     * @return
     * @throws Exception
     */

    @GetMapping(value = "/evtrsvnStep1")
    public String rsvnStepFrst(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, EvtStdmngVO vo, @ModelAttribute("evtrsvnMstVO") EvtrsvnMstVO evtrsvnMstVO)
            throws Exception {

        log.debug("call rsvnStepFrst()");

        Map<String, Object> paramMap = commandMap.getParam();

        log.debug("paramMap = " + paramMap);

        Map<String, Object> pMap = new HashMap<String, Object>();
        String msg = "";
        LoginVO userVO = commandMap.getUserInfo();

        // set comcd
        vo.setComcd(Config.COM_CD);

        // 약관
        Map<String, Object> tMap = evtrsvnService.selectEvtTerms(null);

        EventProgramVO eVo = new EventProgramVO();
        eVo.setComcd(Config.COM_CD);
        eVo.setEvtNo(Integer.parseInt(vo.getEvtNo()));
        eVo.setEvtTime(vo.getEvtTime());

        // 요일정보
        String dayWeek = evtrsvnMainService.selectEvtWeek(commandMap.getParam());
        vo.setDayWeek(dayWeek);

        // 상세정보
        EventProgramVO detailVO = evtrsvnMainService.selectEvtPrgDetail(eVo);

        // 회차정보
        CamelMap rsTimeData = evtrsvnMainService.selectEvtTimeDetail(vo);

        // 요금정보
        List<EvtItemAmountVO> chrgList = evtrsvnService.selectEvtChargeList(vo);

        if (checkReserve(userVO, commandMap, detailVO, rsTimeData, request, response)) {
            int nshw = 0;
            if ("2001".equals(detailVO.getEvtFeeType())) {
                // 노쇼체크
                pMap.put("comcd", Config.COM_CD);
                pMap.put("evtRsvnMemno", userVO.getUniqId());
                pMap.put("evtPartcd", detailVO.getEvtPartcd());
                pMap.put("ctgCd", detailVO.getComCtgcd());

                nshw = evtrsvnService.selectMemNshw(pMap);
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

            // 예약 가능 대상 체크
            if (("Y".equals(detailVO.getEvtNonmebyn()) && !userVO.isMember()) || ("Y".equals(detailVO.getEvtStdmembyn()) && userVO.isMember()) || ("Y".equals(detailVO.getEvtAnualmembyn()) && "Y".equals(userVO.getYearYn())) || ("Y".equals(detailVO.getEvtSpeclmembyn()) && "Y".equals(userVO.getSpecialYn())) || nshw <= 0) {
                // 이벤트 할인 여부 조회
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
                            // 이미 이벤트 할당됨
                            commandMap.put("PGM_GUBUN", "EVT"); // 빈구문 피하기위함
                        } else if (userVO.isMember() && "Y".equals(discVO.getMemberyn())) {
                            discInfoVO = discVO;
                        } else if (!userVO.isMember() && "Y".equals(discVO.getNonmebyn())) {
                            discInfoVO = discVO;
                        }
                    }
                }
                // 유료회원 이고 개인예약인 경우만
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
                            rsvnVO.setMemberGbn("1"); // 연간회원
                        } else {
                            rsvnVO.setMemberGbn("2"); // 특별회원
                        }
                        RsvnCommVO annualData = rsvnCommService.selectAnnualDcData(rsvnVO);
                        if (annualData != null && annualData.getLimitCnt() == 0 && (discInfoVO == null || discInfoVO.getDcRate() < annualData.getDcRate())) {
                            // 제한 없이
                            discAnnualVO = annualData;
                            evtrsvnMstVO.setDcAnnualLimit(annualData.getLimitQty());
                        }
                    }
                }

                for (EvtItemAmountVO itemVO : chrgList) {
                    // 단체 할인 적용
                    if ("20".equals(vo.getEvtPersnGbn()) && detailVO.getDcReasonCd() != null) {
                        if ("Y".equals(itemVO.getGroupdcyn())) {
                            itemVO.setDcRate(detailVO.getDcRate());
                            itemVO.setDcName("단체할인");
                        }

                    } else {
                        if (discInfoVO != null) {
                            itemVO.setDcRate(discInfoVO.getDcRate());
                            itemVO.setDcName(discInfoVO.getDcName());
                        }
                        if (discAnnualVO != null) {
                            itemVO.setDcAnnualCd(discAnnualVO.getDcReasonCd());
                            itemVO.setDcAnnualRate(discAnnualVO.getDcRate());
                            itemVO.setDcAnnualNm("유료회원 할인");
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
                model.addAttribute("optData", rsvnCommService.selectOptData(commandMap.getParam())); // 금액단위 설정
                model.addAttribute("paramMap", paramMap);

                log.debug("final model = " + model);

                return HttpUtility.getViewUrl(request);
            } else {
                msg = "예약 가능 대상이 아닙니다.";
                if ("Y".equals(detailVO.getEvtStdmembyn()) && !userVO.isMember()) {
                    msg = "일반 회원 이상 예약가능 합니다.회원 로그인 후 예약해 주세요.";
                } else if (nshw > 0) {
                    msg = "예약이 제한된 회원입니다. 강연/행사/영화 담당자에게 문의해주십시오.";
                }
                HttpUtility.sendBack(request, response, msg);
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 강연/행사/영화 예약자 정보 입력
     * 
     * @param commandMap
     * @return ModelAndView - 결과
     * @exception Exception
     */
    @PostMapping(value = "/evtrsvnStep2")
    public String rsvnStepScnd(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("evtrsvnMstVO") EvtrsvnMstVO evtrsvnMstVO, ModelMap model) throws Exception {

        log.debug("call rsvnStepScnd()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        // Declare
        Map<String, Object> pMap = new HashMap<String, Object>();
        LoginVO userVO = commandMap.getUserInfo();
        String msg = "";

        // set parameter
        EvtStdmngVO vo = new EvtStdmngVO();
        vo.setComcd(Config.COM_CD);
        vo.setEvtNo(evtrsvnMstVO.getEvtNo());
        vo.setEvtTimeseq(evtrsvnMstVO.getEvtTimeseq());
        vo.setEvtTime(evtrsvnMstVO.getEvtVeingdate());

        EventProgramVO eVo = new EventProgramVO();
        eVo.setComcd(Config.COM_CD);
        eVo.setEvtNo(Integer.parseInt(vo.getEvtNo()));
        eVo.setEvtTime(vo.getEvtTime());

        // 상세정보
        EventProgramVO detailVO = evtrsvnMainService.selectEvtPrgDetail(eVo);

        // 요일정보
        String dayWeek = evtrsvnMainService.selectEvtWeek(commandMap.getParam());
        vo.setDayWeek(dayWeek);

        // 회차정보
        CamelMap timeVO = evtrsvnMainService.selectEvtTimeDetail(vo);
        commandMap.put("COMCD", Config.COM_CD);

        if (checkReserve(userVO, commandMap, detailVO, timeVO, request, response)) {

            long totalCnt = 0;
            long dcAmountLong = 0;
            long totalAmount = 0;

            RsvnCommVO discOpt = rsvnCommService.selectOptData(commandMap.getParam()); // 금액단위 설정
            int limitQty = evtrsvnMstVO.getDcAnnualLimit();
            int remainCnt = 0;

            /*
             * JYS 2021.05.19
             * for (EvtItemAmountVO charge : evtrsvnMstVO.getChargeList()) {
             * //유료회원 무제한 할인
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
             * //유료회원할인을 먹인다
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
                HttpUtility.sendBack(request, response, "잔여정원을 초과하여 신청하실 수 없습니다.(잔여정원:" + (CommonUtil.getInt(timeVO.get("totCapa")) - CommonUtil.getInt(timeVO.get("rsvCnt"))) + "명)");
                return null;
            }

            int nshw = 0;
            if ("2001".equals(detailVO.getEvtFeeType())) {
                // 노쇼체크
                pMap.put("comcd", Config.COM_CD);
                pMap.put("evtRsvnMemno", userVO.getUniqId());
                pMap.put("evtPartcd", detailVO.getEvtPartcd());
                pMap.put("ctgCd", detailVO.getComCtgcd());

                nshw = evtrsvnService.selectMemNshw(pMap);
            } else {
                nshw = 0;
            }

            evtrsvnMstVO.setEvtPartcd(detailVO.getEvtPartcd());
            // 예약 가능 대상 체크
            if (("Y".equals(detailVO.getEvtNonmebyn()) && !userVO.isMember()) || ("Y".equals(detailVO.getEvtStdmembyn()) && userVO.isMember()) || ("Y".equals(detailVO.getEvtAnualmembyn()) && "Y".equals(userVO.getYearYn())) || ("Y".equals(detailVO.getEvtSpeclmembyn()) && "Y".equals(userVO.getSpecialYn())) || nshw <= 0) {

                if (userVO.isMember()) {
                    evtrsvnMstVO.setEvtRsvnMemtype("1001");
                    evtrsvnMstVO.setEvtRsvnMemno(userVO.getUniqId());
                    evtrsvnMstVO.setEvtRsvnWebid(userVO.getId());
                    evtrsvnMstVO.setEvtEmail(userVO.getEmail());
                    evtrsvnMstVO.setEvtRsvnGrpnm(userVO.getOrgnztNm()); // 단체명
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
                msg = "예약 가능 대상이 아닙니다.";
                if ("Y".equals(detailVO.getEvtStdmembyn()) && !userVO.isMember()) {
                    msg = "일반 회원 이상 예약가능 합니다.회원 로그인 후 예약해 주세요.";
                } else if (nshw > 0) {
                    msg = "예약이 제한된 회원입니다. 강연/행사/영화 담당자에게 문의해주십시오.";
                }
                HttpUtility.sendBack(request, response, msg);
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 강연/행사/영화 정보 입력
     * 
     * @param commandMap
     * @return ModelAndView - 결과
     * @exception Exception
     */
    @ResponseBody
    @PostMapping(value = "/evtrsvnAction")
    public ModelAndView rsvnStepInsert(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("evtrsvnMstVO") EvtrsvnMstVO evtrsvnMstVO, ModelMap model) throws Exception {

        log.debug("call rsvnStepInsert()");

        Map<String, Object> paramMap = commandMap.getParam();

        log.debug("paramMap = " + paramMap);

        ModelAndView mav = new ModelAndView("jsonView");
        Map<String, String> returnMap = new HashMap<String, String>();
        String rtnCd = "";

        if (commandMap.getUserInfo() == null) {
            throw new MyException("로그아웃 되었습니다.로그인 후 다시 이용해 주세요.", -3);
        }

        // 회원정보
        LoginVO userVO = new LoginVO();
        userVO.setUniqId(evtrsvnMstVO.getEvtRsvnMemno());
        userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        MemberVO memberVO = myInforService.selectMemberData(userVO);

        EventProgramVO eVo = new EventProgramVO();
        eVo.setComcd(Config.COM_CD);
        eVo.setEvtNo(Integer.parseInt(evtrsvnMstVO.getEvtNo()));
        eVo.setEvtTime(evtrsvnMstVO.getEvtTime());

        // 상세정보
        EventProgramVO detailVO = evtrsvnMainService.selectEvtPrgDetail(eVo);

        // 예약 가능 대상 체크
        if (!"Y".equals(detailVO.getRsvAbleYn())) {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "예약 가능 기간이 아닙니다.");
        } else if (detailVO.getHoldCnt() > 0) {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "예약 가능한 날짜가 아닙니다.");
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
            // 예약번호 생성
            String evtRsvnno = evtrsvnService.selectRsvnNumber();
            evtrsvnMstVO.setEvtRsvnno(evtRsvnno);
            evtrsvnMstVO.setEvtVeingnmpr(String.valueOf(paramMap.get("evtVeingnmpr")));

            // OID 생성
            OrderIdVO orderVO = new OrderIdVO();
            orderVO.setComcd(evtrsvnMstVO.getComcd());
            orderVO.setUserId(evtrsvnMstVO.getEvtRsvnWebid());
            orderVO.setRsvnNo(evtrsvnMstVO.getEvtRsvnno()); // 예약번호
            orderVO.setRsvnCnt(1);
            orderVO.setRsvnAmt(0);
            // 주문번호 생성
            chargeService.insertDbprocLog(orderVO); // 로그남기기
            //// JYS 2021.05.18 chargeService.selectOrderId(orderVO);
            String oid = orderVO.getRetOid();

            MyRsvnVO myRsvnVO = new MyRsvnVO(); // 결제 프로시져 리턴 값 용
            myRsvnVO.setLgdOID(oid);
            String errorMsg = "";
            try {
                returnMap = evtrsvnService.insertEvtrsvnInfo(memberVO, evtrsvnMstVO, detailVO, myRsvnVO);
                rtnCd = returnMap.get("resultCd");
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

            if (Config.SUCCESS.equals(rtnCd)) {

                // 문자 발송
                try {
                    String ticketInfo = "";
                    Map<String, String> smsParam = new HashMap<String, String>();
                    smsParam.put("msgcd", "3");
                    smsParam.put("msgno", "3");
                    smsParam.put("sndHp", CommonUtil.getString(detailVO.getEvtGuideTelno()));
                    smsParam.put("hp", evtrsvnMstVO.getEvtRsvnMoblphon());
                    smsParam.put("예약자명", evtrsvnMstVO.getEvtRsvnCustnm());
                    smsParam.put("결제금액", CommonUtil.getCommaNumber(evtrsvnMstVO.getEvtRsvnPayamt(), "###,###") + "원");
                    smsParam.put("강연/행사/영화명", detailVO.getEvtName());
                    smsParam.put("참여인원", evtrsvnMstVO.getEvtVeingnmpr() + "명");
                    smsParam.put("예약번호", evtRsvnno);
                    smsParam.put("관람일자", evtrsvnMstVO.getEvtVeingdate().substring(0, 4) + "." + evtrsvnMstVO.getEvtVeingdate().substring(4, 6) + "." + evtrsvnMstVO.getEvtVeingdate().substring(6, 8));
                    smsParam.put("시작시간", evtrsvnMstVO.getEvtStime().substring(0, 2) + ":" + evtrsvnMstVO.getEvtStime().substring(2));
                    smsParam.put("종료시간", evtrsvnMstVO.getEvtEtime().substring(0, 2) + ":" + evtrsvnMstVO.getEvtEtime().substring(2));

                    if ("Y".equals(returnMap.get("freeYn"))) {
                        if ("Y".equals(detailVO.getEvtTicketChkyn())) {
                            String ticketNo = evtrsvnService.selectTicketNumber(evtrsvnMstVO);
                            if (ticketNo != null && !ticketNo.equals("")) {
                                String strKey = EgovProperties.getProperty("Globals.Ticket.Key");
                                String strAedValue = ScrEncDecUtil.fn_encrypt_url(evtRsvnno, strKey);
                                ticketInfo += "▶티켓번호 : " + ticketNo;
                                ticketInfo += "\n관람 입장시 아래   티켓확인하기를 눌러 티켓 바코드를 보여 주세요.";
                                ticketInfo += "\n티켓확인하기 : " + EgovProperties.getProperty("Globals.Domain") + "/ticket/" + strAedValue;
                            }
                        }
                    } else if ("10".equals(evtrsvnMstVO.getEvtPersnGbn())) {
                        smsParam.put("msgno", "28"); // 유료
                        String payWaitTime = evtrsvnMstVO.getPayWaitTime();
                        smsParam.put("결제마감시간", payWaitTime.substring(0, 4) + "." + payWaitTime.substring(4, 6) + "." + payWaitTime.substring(6, 8) + " " + payWaitTime.substring(8, 10) + ":" + payWaitTime.substring(10));
                    } else {
                        smsParam.put("msgno", "28"); // 유료
                        smsParam.put("결제마감시간", "관람 시작 전까지 결제 가능하며 당일날 현장에서도 결제할 수 있습니다.");
                    }
                    smsParam.put("티켓정보", ticketInfo);
                    smsService.sendMessage(smsParam, userVO);

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
    @PostMapping(value = { "/evtrsvnResult", "/evtrsvnPop" })
    public String rsvnResult(@ModelAttribute("evtrsvnMstVO") EvtrsvnMstVO evtrsvnMstVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        commandMap.put("comcd", Config.COM_CD);
        evtrsvnMstVO.setDbEnckey(EgovProperties.getProperty("Globals.DbEncKey"));
        // 예약 정보

        model.addAttribute("resultVO", evtrsvnService.selectEvtrsvnDetail(evtrsvnMstVO));
        return HttpUtility.getViewUrl(request);
    }

    /**
     * 아이디별 예약횟수 체크
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
        int result = evtrsvnService.selectEvtrsvnCnt(map);

        mav.addObject("rsvnCnt", result);
        return mav;
    }

    /**
     * 잔여인원 확인 ajax
     *
     * @param partCd
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping(value = "/evtCapaCntAjax")
    public ModelAndView selectEvtCapaCnt(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            EvtrsvnMstVO evtrsvnMstVO) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        evtrsvnMstVO.setComcd(Config.COM_CD);

        // 잔여인원 체크
        Map<String, Object> memChk = evtrsvnService.selectEvtRmnAmnt(evtrsvnMstVO);

        mav.addObject("result", memChk.get("result"));
        return mav;
    }

    /**
     * 강연/행사/영화 상세정보 ajax
     *
     * @param partCd
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping(value = "/rsvnDetailAjax")
    public ModelAndView selectRsvnDetail(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            EvtStdmngVO vo) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        vo.setComcd(Config.COM_CD);
        // 상세정보
        List<CamelMap> timeList = evtrsvnMainService.selectEvtTimeDetailList(vo);
        mav.addObject("detail", timeList);
        return mav;
    }

    public boolean checkReserve(LoginVO userVO, CommandMap param, EventProgramVO detailVO, CamelMap timeVO,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String url = Config.USER_ROOT + "/evtrsvn/evtrsvnDetail?evtNo=" + detailVO.getEvtNo() + "&ymd=" + param.getString("evtTime");
        if (detailVO == null || timeVO == null) {
            HttpUtility.sendBack(request, response, "강연/행사/영화 데이타가 없습니다. 선택 내용을 다시 확인해 주세요.");
            return false;
        } else if (!"Y".equals(detailVO.getRsvAbleYn())) {
            HttpUtility.sendRedirect(request, response, "예약 가능 기간이 아닙니다.", url);
            return false;
        } else if (detailVO.getHoldCnt() > 0) {
            HttpUtility.sendRedirect(request, response, "예약 가능한 날짜가 아닙니다.", url);
            return false;
        } else if (!"Y".equals((String) timeVO.get("rsvAbleYn"))) {
            HttpUtility.sendRedirect(request, response, "예약 시간이 종료되었습니다.", url);
            return false;
        } else if (CommonUtil.getInt(timeVO.get("totCapa")) <= CommonUtil.getInt(timeVO.get("rsvCnt"))) {
            HttpUtility.sendRedirect(request, response, "예약이 마감되었습니다.", url);
            return false;
        } else if (userVO == null) { // 로그인 정보 없음
            String returnURL = Config.USER_ROOT + "/evtrsvn/evtrsvnStep1?evtPersnGbn=" + param.getString("evtPersnGbn") + "&evtTimeseq=" + param.getString("evtTimeseq") + "&evtTimestdseq=" + param.getString("evtTimestdseq") + "&evtNo=" + param.getString("evtNo") + "&evtTime=" + param.getString("evtTime");
            HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/member/login?returnURL=" + URLEncoder.encode(returnURL, "UTF-8"));
            return false;

        } else if ("10".equals(param.getString("evtPersnGbn")) && userVO.getGender().equals("2") && detailVO.getEvtSexdstn().equals("1001")) {
            HttpUtility.sendBack(request, response, "신청 가능한 성별이 아니므로 신청하실 수 없습니다. 신청 가능 성별을 확인해 주시기 바랍니다.");
            return false;
        } else if ("10".equals(param.getString("evtPersnGbn")) && userVO.getGender().equals("1") && detailVO.getEvtSexdstn().equals("2001")) {
            HttpUtility.sendBack(request, response, "신청 가능한 성별이 아니므로 신청하실 수 없습니다. 신청 가능 성별을 확인해 주시기 바랍니다.");
            return false;
        } else {

            // 이용횟수 조회
            param.put("evtNonmembCertno", userVO.getHpcertno());
            param.put("evtRsvnMemno", userVO.getUniqId());
            int myCnt = evtrsvnService.selectEvtrsvnCnt(param.getParam());

            String msg = "";

            /*
             * if (detailVO.getEvtMaxtimeCnt() > 0 && myCnt >= detailVO.getEvtMaxtimeCnt() ) {
             * msg = "예약 가능 횟수 가 초과되었습니다. (최대 "+detailVO.getEvtMaxtimeCnt() +"회)";
             * }
             */

            if (myCnt > 1) {
                msg = "예약하신 강연/행사/영화가 이미 예약 신청이 되어있습니다.";
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
     * 예약완료
     * 
     * @param commandMap
     * @return String - jsp 페이지
     * @exception Exception
     */
    @PostMapping(value = { "/evtrsvnIndiResult" })
    public String rsvnIndiResult(@ModelAttribute("evtrsvnMstVO") EvtrsvnMstVO evtrsvnMstVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        commandMap.put("comcd", Config.COM_CD);
        evtrsvnMstVO.setDbEnckey(EgovProperties.getProperty("Globals.DbEncKey"));
        // 예약 정보

        model.addAttribute("resultVO", evtrsvnService.selectEvtrsvnDetail(evtrsvnMstVO));
        return HttpUtility.getViewUrl(request);
    }

}
