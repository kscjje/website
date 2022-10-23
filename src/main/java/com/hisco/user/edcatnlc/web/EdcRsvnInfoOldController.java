package com.hisco.user.edcatnlc.web;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.area.service.AreaCdService;
import com.hisco.admin.comctgr.service.ComCtgrService;
import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.terms.service.TermsService;
import com.hisco.admin.terms.vo.TermsVO;
import com.hisco.cmm.exception.MyException;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.intrfc.charge.service.ChargeService;
import com.hisco.intrfc.charge.vo.OrderIdVO;
import com.hisco.intrfc.sale.service.TotalSaleService;
import com.hisco.intrfc.sms.service.SmsService;
import com.hisco.user.edcatnlc.service.EdcProgramService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;
import com.hisco.user.member.service.UserJoinService;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.service.MyInforService;
import com.hisco.user.mypage.service.MyRsvnService;
import com.hisco.user.mypage.service.RsvnCommService;
import com.hisco.user.mypage.vo.MyRsvnVO;
import com.hisco.user.mypage.vo.RsvnCommVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * 교육프로그램 목록/상세 조회
 *
 * @Class Name : EducationController.java
 * @Description : 자세한 클래스 설명
 * @author woojinp@legitsys.co.kr
 * @since 2021. 11. 11.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */

@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/edc/rsvn")
public class EdcRsvnInfoOldController {

    @Resource(name = "chargeService")
    private ChargeService chargeService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    @Resource(name = "smsService")
    private SmsService smsService;

    @Resource(name = "userJoinService")
    private UserJoinService userJoinService;

    @Resource(name = "rsvnCommService")
    private RsvnCommService rsvnCommService;

    @Resource(name = "myInforService")
    private MyInforService myInforService;

    // woojinp added
    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "comCtgrService")
    private ComCtgrService comCtgrService;

    @Resource(name = "areaCdService")
    private AreaCdService areaCdService;

    @Resource(name = "termsService")
    private TermsService termsService;

    @Resource(name = "totalSaleService")
    private TotalSaleService totalSaleService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    @Resource(name = "myRsvnService")
    private MyRsvnService myRsvnService;

    // 암/복호화 키
    @Value("${Globals.DbEncKey}")
    String DbEncKey;

    // 개인 정보 암화화 시작
    @Value("${Globals.SpowiseCms.Key}")
    String SpowiseCmsKey;

    /**
     * 교육 예약 등록 관련 정보를 조회한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "edcarsvnRegist", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    public String selectEdcarsvnRegist(HttpServletRequest request, HttpServletResponse response, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        log.debug("call selectEdcarsvnRegist()");

        Map<String, Object> paramPMap = commandMap.getParam();
        log.debug("paramPMap = " + paramPMap);

        paramMap.put("comCd", Config.COM_CD);
        Map<String, Object> finalResult = new HashMap<String, Object>();

        LoginVO userVO = commandMap.getUserInfo();

        if (userVO != null) {
            if (userVO.isMember()) {
                paramMap.put("memNo", userVO.getUniqId());
            } else {
                paramMap.put("hpCertno", userVO.getHpcertno());
            }
        }

        TermsVO termsVO = new TermsVO();
        termsVO.setStplatIdList(Arrays.asList(Constant.CM_TERMS_ID_개인정보수집이용동의, Constant.CM_TERMS_ID_교육예약유의사항동의));
        List<?> lstCmStplatInfoResult = termsService.selectTermsList(termsVO);
        EdcProgramVO programVO = new EdcProgramVO();
        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(programVO);

        if (checkReserve(commandMap.getUserInfo(), detailVO, request, response)) {

            finalResult.put("EXE_YN", "Y");
            finalResult.put("DB_DATA", "{DB_YN : Y}");
            finalResult.put("DB_DROW", 1);

            model.addAttribute("cmStplatInfoList", lstCmStplatInfoResult);
            model.addAttribute("detailVO", detailVO);
            model.addAttribute("paramMap", paramMap);
            model.addAttribute("RESULT", finalResult);
            model.addAttribute("email", commandMap.getUserInfo().getEmail());

            // 이벤트 할인 여부 조회
            commandMap.put("COMCD", Config.COM_CD);
            commandMap.put("YMD", detailVO.getEdcSdate());
            commandMap.put("PART_CD", detailVO.getEdcPartCd());
            commandMap.put("PGM_CD", detailVO.getEdcPrgmNo());
            commandMap.put("PGM_GUBUN", "EDC");

            RsvnCommVO discInfoVO = null;
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
            long dcAmountLong2 = 0;
            if (discInfoVO != null) {
                RsvnCommVO discOpt = rsvnCommService.selectOptData(commandMap.getParam()); // 금액단위 설정
                long price = Long.parseLong(detailVO.getSalamt() + "");
                dcAmountLong2 = CommonUtil.DoubleToLongCalc(price * discInfoVO.getDcRate() * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
            }
            model.addAttribute("discInfoVO", discInfoVO);
            model.addAttribute("dcAmount", dcAmountLong2);

            return HttpUtility.getViewUrl(request);

        } else {
            return null;
        }

    }

    /**
     * 교육 예약 등록 관련 정보를 입력한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "edcarsvnRegInput", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String selectEdcarsvnRegInput(HttpServletRequest request, HttpServletResponse response, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {
        paramMap.put("comCd", Config.COM_CD);

        LoginVO userVO = commandMap.getUserInfo();

        if (userVO != null) {
            if (userVO.isMember()) {
                paramMap.put("memNo", userVO.getUniqId());
            } else {
                paramMap.put("hpCertno", userVO.getHpcertno());
            }
        }

        EdcProgramVO programVO = new EdcProgramVO();
        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(programVO);
        // List<?> lstProgmMngiteResult = edcRsvnInfoService.selectProgmMngiteminfoList(paramMap);

        if (checkReserve(commandMap.getUserInfo(), detailVO, request, response)) {
            model.addAttribute("detailVO", detailVO);
            /*
             * model.addAttribute("edcarsvnProgMItemList", lstProgmMngiteResult);
             * model.addAttribute("edcarsvnProgMItemCnt", lstProgmMngiteResult.size());
             */
            model.addAttribute("paramMap", paramMap);

            String strIhidNum = userVO.getIhidNum();
            String strEMail = userVO.getEmail();
            String strUserName = userVO.getName();

            model.addAttribute("ihidNum", strIhidNum);
            model.addAttribute("email", strEMail);
            model.addAttribute("userName", strUserName);
            model.addAttribute("orgName", commandMap.getUserInfo().getOrgnztNm());

            model.addAttribute("loginVO", userVO);

            return HttpUtility.getViewUrl(request);
        } else {
            return null;
        }
    }

    /**
     * 교육 예약 등록 관련 정보를 저장한다
     * EDC_RSVN_INFO 테이블에 저장
     * TODO: 삭제대상 소스이며 /apply.json로 대체
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @Deprecated
    @RequestMapping(value = "edcarsvnRegSave", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView selectEdcarsvnRegSave(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        log.debug("call selectEdcarsvnRegSave()");

        Map<String, Object> paramPMap = commandMap.getParam();
        log.debug("paramPMap = " + paramPMap);

        paramMap.put("comCd", Config.COM_CD);

        int intEdcRsvnReqid = 0;

        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();

        LoginVO userVO = commandMap.getUserInfo();
        if (userVO == null) {
            throw new MyException("로그인 정보를 찾을 수 없습니다.", -3);
        }

        if (userVO != null) {
            if (userVO.isMember()) {
                paramMap.put("memNo", userVO.getUniqId());
            } else {
                paramMap.put("hpCertno", userVO.getHpcertno());
            }
        }

        EdcProgramVO programVO = new EdcProgramVO();
        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(programVO);
        String strUserId = userVO.getId();

        paramMap.put("regUser", strUserId);
        paramMap.put("modUser", strUserId);
        paramMap.put("edcNonmembCertno", userVO.getHpcertno());
        paramMap.put("edcRsvnCustnm", userVO.getName());

        paramMap.put("edcRsvnMoblphon", userVO.getIhidNum());
        paramMap.put("edcEmail", userVO.getEmail());

        if (userVO.isMember()) {
            paramMap.put("edcRsvnMemtype", "1001");
        } else {
            paramMap.put("edcRsvnMemtype", "2001");
            // 교육대상자 연락처
            if (paramMap.get("edcReqMoblphon") == null || CommonUtil.getString(paramMap.get("edcReqMoblphon")).equals("")) {
                paramMap.put("edcReqMoblphon", userVO.getIhidNum());
            }
            // 교육대상자 이름
            if (paramMap.get("edcReqCustnm") == null || CommonUtil.getString(paramMap.get("edcReqCustnm")).equals("")) {
                paramMap.put("edcReqCustnm", userVO.getName());
            }
        }

        if (detailVO == null) {
            throw new MyException("교육예약 데이타가 없습니다. 선택 내용을 다시 확인해 주세요.", -1);
        }

        String programType = CommonUtil.getString(detailVO.getEdcProgmType());
        String reqGender = CommonUtil.getString(detailVO.getEdcReqGender());

        int applyCnt = CommonUtil.getInt(paramMap.get("edcVistnmpr")); // 방문인원
        if (applyCnt < 1)
            applyCnt = 1;

        int remainCnt = CommonUtil.getInt(detailVO.getEdcPncpa()) - CommonUtil.getInt(detailVO.getAppCnt());

        if (!CommonUtil.getString(detailVO.getEdcStatus()).equals("ING")) {
            throw new MyException("접수 기간이 아닙니다.", -1);
        } else if (remainCnt < 1) {
            throw new MyException("접수 인원이 마감되었습니다.", -1);
        } else if (remainCnt < applyCnt) {
            throw new MyException("잔여 인원이 부족합니다. 현재 잔여인원 : " + remainCnt + "명", -1);

        } else if (!userVO.isMember() && (programType.equals("1001") || programType.equals("2001"))) {
            throw new MyException("회원만 신청하실 수 있습니다.", -1);
        } else if (CommonUtil.getString(detailVO.getAgeAbleYn()).equals("N")) {
            // 나이 체크
            throw new MyException("교육 신청 가능한 연령이 아니므로 신청하실 수 없습니다. 신청 가능 연령을 확인 해 주시기 바랍니다.", -1);
        } else if ((programType.equals("1001") || programType.equals("2001")) && reqGender.equals("1001") && !"1".equals(userVO.getGender())) {
            // 성별 체크
            throw new MyException("교육 신청 가능한 성별이 아니므로 신청하실 수 없습니다. 남성만 신청가능한 교육입니다.", -1);
        } else if ((programType.equals("1001") || programType.equals("2001")) && reqGender.equals("2001") && !"2".equals(userVO.getGender())) {
            // 성별 체크
            throw new MyException("교육 신청 가능한 성별이 아니므로 신청하실 수 없습니다. 여성만 신청가능한 교육입니다.", -1);
        } else if (CommonUtil.getInt(programVO.getDupChk()) > 0) {
            // 중복 신청 체크
            throw new MyException("예약하신 교육프로그램으로 이미 예약 신청이 되어있습니다.", -1);
        }

        if (detailVO != null) {
            String strEdcProgmType = String.valueOf(detailVO.getEdcProgmType());
            String strEdufreeYn = String.valueOf(detailVO.getEdufreeYn());
            String strEduInterStatus = String.valueOf(detailVO.getEdcStatus());
            String strEdcOnlineyn = String.valueOf(detailVO.getEdcOnlineyn());
            long intSaleAmt = 0; // Long.parseLong(String.valueOf(programVO.get("salamt")));

            // 이벤트 할인 여부 조회
            commandMap.put("COMCD", Config.COM_CD);
            commandMap.put("YMD", detailVO.getEdcSdate());
            commandMap.put("PART_CD", detailVO.getEdcPartCd());
            commandMap.put("PGM_CD", detailVO.getEdcPrgmNo());
            commandMap.put("PGM_GUBUN", "EDC");
            RsvnCommVO discInfoVO = null;
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

            long dcAmountLong2 = 0;
            if (discInfoVO != null) {
                RsvnCommVO discOpt = rsvnCommService.selectOptData(commandMap.getParam()); // 금액단위 설정
                dcAmountLong2 = CommonUtil.DoubleToLongCalc(intSaleAmt * discInfoVO.getDcRate() * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
            }

            paramMap.put("edcProgmType", strEdcProgmType);
            paramMap.put("edufreeYn", strEdufreeYn);
            paramMap.put("eduInterStatus", strEduInterStatus);
            paramMap.put("edcOnlineyn", strEdcOnlineyn);
            paramMap.put("edcProgmCost", intSaleAmt);
            paramMap.put("edcDcamt", dcAmountLong2);
            paramMap.put("edcTotamt", intSaleAmt - dcAmountLong2);
            if (discInfoVO != null) {
                paramMap.put("edcReasondc", discInfoVO.getDcReasonCd());
                paramMap.put("edcEventDcid", discInfoVO.getDcid());
            }

            if (request.getAttribute("IS_MOBILE") == null) {
                paramMap.put("edcTrmnlType", "2001");
            } else {
                boolean isMobile = (boolean) request.getAttribute("IS_MOBILE");
                paramMap.put("edcTrmnlType", (isMobile ? "2002" : "2001"));
            }

            if (!"Y".equals(strEdufreeYn)) {
                String strEdcPaywaitGbn = CommonUtil.getString(detailVO.getEdcPaywaitGbn());
                paramMap.put("edcPaywaitGbn", strEdcPaywaitGbn);

                if ("1001".equals(strEdcPaywaitGbn)) {
                    paramMap.put("edcPaywaitTime", CommonUtil.getString(detailVO.getEdcPaywaitTime()));

                } else if ("2001".equals(strEdcPaywaitGbn)) {
                    paramMap.put("edcPaywaitDate", CommonUtil.getString(detailVO.getEdcPaywaitDate()));
                    paramMap.put("edcPaywaitHhmm", CommonUtil.getString(detailVO.getEdcPaywaitHhmm()));
                }
            }
            // 결제 마감시간
            EdcRsvnInfoVO paramVO = new EdcRsvnInfoVO();
            // String edcPaywaitData = edcRsvnInfoService.selectPaywaitTime(paramVO);
            String edcPaywaitData = ""; // sselectPaywaitTime결과를 Timestamp로 변경하면서 일시적으로 처리.
            paramMap.put("edcPaywaitData", edcPaywaitData);
            // 예약번호
            String rsvnNo = edcRsvnInfoService.selectNextRsvnNumber();
            paramMap.put("rsvnNo", rsvnNo);

            if ("Y".equals(strEdufreeYn)) {
                // 주문번호 생성
                OrderIdVO orderVO = new OrderIdVO();
                orderVO.setComcd(String.valueOf(paramMap.get("comcd")));
                orderVO.setUserId(String.valueOf(paramMap.get("regUser")));
                orderVO.setRsvnNo(String.valueOf(paramMap.get("rsvnNo"))); // 예약번호
                orderVO.setRsvnCnt(1);
                orderVO.setRsvnAmt(0);

                chargeService.insertDbprocLog(orderVO); // 로그남기기
                //// JYS 2021.05.18 chargeService.selectOrderId(orderVO);
                String oid = orderVO.getRetOid();
                paramMap.put("OID", oid);
            }
            String errorMsg = "";
            try {

                intEdcRsvnReqid = edcRsvnInfoService.saveRsvnInfo(new EdcRsvnInfoVO());
            } catch (MyException e) {
                throw e;
            } catch (Exception e) {
                errorMsg = e.getMessage();
                throw new MyException("교육 신청 내부 오류", -1);
            } finally {
                if ("Y".equals(strEdufreeYn) && (intEdcRsvnReqid > 0 || errorMsg.indexOf("SP_CREATE_PAMENTINFO") > 0)) {
                    MyRsvnVO myRsvnVO = new MyRsvnVO();
                    myRsvnVO.setComcd(Config.COM_CD);
                    myRsvnVO.setPartGbn("2001");
                    myRsvnVO.setTerminalType("2001");
                    myRsvnVO.setLgdBUYERID(String.valueOf(paramMap.get("regUser")));
                    myRsvnVO.setLgdOID(String.valueOf(paramMap.get("OID")));
                    chargeService.insertDbprocLog(myRsvnVO);
                    // 로그 남기기
                }
            }

            if (intEdcRsvnReqid > 0) {
                // 문자 발송
                try {
                    Map<String, String> smsParam = new HashMap<String, String>();
                    smsParam.put("msgcd", "4");
                    smsParam.put("msgno", "0");
                    smsParam.put("sndHp", CommonUtil.getString(detailVO.getEdcGuideTelno()));
                    smsParam.put("hp", userVO.getIhidNum());
                    smsParam.put("예약자명", userVO.getName());
                    smsParam.put("교육프로그램명", String.valueOf(detailVO.getEdcPrgmnm()));

                    if (strEdcProgmType.equals("3001")) {
                        smsParam.put("참여인원", paramMap.get("edcVistnmpr") + "명");
                    } else if (strEdcProgmType.equals("2001") || strEdcProgmType.equals("9001")) {
                        int intEdcVisitfamilyCntRow = CommonUtil.getInt(paramMap.get("edcVisitfamilyCnt"));
                        int cnt = 0;
                        for (int i = 1; i <= intEdcVisitfamilyCntRow; i++) {
                            String name = CommonUtil.getString(paramMap.get("edcRsvnfmName" + i));
                            if (!name.equals("")) {
                                cnt++;
                            }
                        }
                        if (cnt < 1)
                            cnt = 1;
                        smsParam.put("참여인원", cnt + "명");
                    } else {
                        smsParam.put("참여인원", "1명"); // 개인
                    }

                    smsParam.put("예약번호", rsvnNo);
                    smsParam.put("교육장소", String.valueOf(detailVO.getEdcPlacenm())); // 이거 채워야 함
                    smsParam.put("문의전화", String.valueOf(detailVO.getEdcGuideTelno())); // 이거 채워야 함

                    String period = CommonUtil.EduPeriodTime(detailVO);

                    if (strEdcProgmType.equals("1001")) {
                        period = period + "\n▶ 교육요일 : " + detailVO.getEdcDaygbnNm();
                    }

                    smsParam.put("교육기간", period);
                    //// smsParam.put("환불규정", chargeService.selectRefundRule(commandMap));

                    if (!"Y".equals(strEdufreeYn)) {
                        smsParam.put("msgno", "38"); // 유료
                        if (edcPaywaitData != null && edcPaywaitData.length() == 12) {
                            smsParam.put("결제마감시간", edcPaywaitData.substring(0, 4) + "." + edcPaywaitData.substring(4, 6) + "." + edcPaywaitData.substring(6, 8) + " " + edcPaywaitData.substring(8, 10) + ":" + edcPaywaitData.substring(10));
                        } else {
                            smsParam.put("결제마감시간", "");
                        }

                        smsParam.put("결제금액", CommonUtil.AddComma(paramMap.get("edcProgmCost")) + "원");
                    }

                    smsService.sendMessage(smsParam, userVO);

                } catch (Exception e) {
                    // sms발송 오류 체크
                    log.error("교육신청 문자발송 오류:" + e.getMessage());
                }
            }

        }

        // model.addAttribute("edcarsvnDtlList", lstResult);

        finalResult.put("EXE_YN", "Y");
        finalResult.put("RSVN_REQ_ID", intEdcRsvnReqid);
        finalResult.put("DB_DATA", "{DB_YN : Y}");
        finalResult.put("DB_DROW", 1);

        mav.addObject("RESULT", finalResult);

        return mav;

    }

    /**
     * 교육 예약 등록 완료 정보를 조회한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "edcarsvnRegEnd", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    public String selectEdcarsvnRegEnd(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap,
            @ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO) throws Exception {
        String strDbEncKey = EgovProperties.getProperty("Globals.DbEncKey");
        paramMap.put("dbEncKey", strDbEncKey);
        paramMap.put("comCd", Config.COM_CD);

        LoginVO loginUser = commandMap.getUserInfo();
        if (loginUser == null) {
            throw new MyException("로그인 정보를 찾을 수 없습니다.", -3);
        }

        EdcProgramVO paramVO = new EdcProgramVO();
        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(paramVO);

        EdcRsvnInfoVO paramRsvnInfoVO = new EdcRsvnInfoVO();

        model.addAttribute("detailVO", detailVO);
        model.addAttribute("rsvnInfo", edcRsvnInfoService.selectRsvnInfo(paramRsvnInfoVO));
        // model.addAttribute("itemList", edcRsvnInfoService.selectProgmMngiteminfoList2(paramMap));
        model.addAttribute("paramMap", paramMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 재료 받을 배송 주소 정보를 변경한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "edcarsvnJusoSave", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView edcarsvnJusoSave(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {
        paramMap.put("comCd", Config.COM_CD);

        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();

        EdcRsvnInfoVO paramRsvnInfoVO = new EdcRsvnInfoVO();
        edcRsvnInfoService.updateRsvnInfoAddr(paramRsvnInfoVO, commandMap);

        finalResult.put("EXE_YN", "Y");
        finalResult.put("DB_DATA", "{DB_YN : Y}");
        finalResult.put("DB_DROW", 1);

        mav.addObject("RESULT", finalResult);

        return mav;

    }

    public boolean checkReserve(LoginVO userVO, EdcProgramVO programVO, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        if (programVO == null) {
            HttpUtility.sendBack(request, response, "교육예약 데이타가 없습니다. 선택 내용을 다시 확인해 주세요.");
            return false;
        }

        String referer = request.getHeader("referer");
        boolean backFlag = false;
        if (referer != null && referer.indexOf("edcarsvnList") > 0) {
            backFlag = true;
        }

        String url = Config.USER_ROOT + "/edcarsvn/edcarsvnDetail?edcPrgmid=" + programVO.getEdcPrgmNo();
        String programType = CommonUtil.getString(programVO.getEdcProgmType());
        String reqGender = CommonUtil.getString(programVO.getEdcReqGender());

        if (!CommonUtil.getString(programVO.getEdcStatus()).equals("ING")) {
            HttpUtility.sendRedirect(request, response, "접수 기간이 아닙니다.", url);
            return false;
        } else if (CommonUtil.getInt(programVO.getEdcPncpa()) <= CommonUtil.getInt(programVO.getAppCnt())) {
            HttpUtility.sendRedirect(request, response, "접수 인원이 마감되었습니다.", url);
            return false;

        } else if (userVO == null) { // 로그인 정보 없음
            String returnURL = Config.USER_ROOT + "/edcarsvn/edcarsvnRegist?edcPrgmid=" + programVO.getEdcPrgmNo();
            HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/member/login?returnURL=" + URLEncoder.encode(returnURL, "UTF-8"));
            return false;
        } else if (!userVO.isMember() && (programType.equals("1001") || programType.equals("2001"))) {
            HttpUtility.sendRedirect(request, response, "회원만 신청하실 수 있습니다.", url);
            return false;
        } else if (CommonUtil.getString(programVO.getAgeAbleYn()).equals("N")) {
            // 나이 체크
            if (backFlag) {
                HttpUtility.sendBack(request, response, "교육 신청 가능한 연령이 아니므로 신청하실 수 없습니다. 신청 가능 연령을 확인해 주시기 바랍니다.");
            } else {
                HttpUtility.sendRedirect(request, response, "교육 신청 가능한 연령이 아니므로 신청하실 수 없습니다. 신청 가능 연령을 확인해 주시기 바랍니다.", url);
            }
            return false;

        } else if ((programType.equals("1001") || programType.equals("2001")) && reqGender.equals("1001") && !"1".equals(userVO.getGender())) {
            // 성별 체크
            if (backFlag) {
                HttpUtility.sendBack(request, response, "교육 신청 가능한 성별이 아니므로 신청하실 수 없습니다. 신청 가능 성별을 확인해 주시기 바랍니다.");
            } else {
                HttpUtility.sendRedirect(request, response, "교육 신청 가능한 성별이 아니므로 신청하실 수 없습니다. 신청 가능 성별을 확인해 주시기 바랍니다.", url);
            }
            return false;
        } else if ((programType.equals("1001") || programType.equals("2001")) && reqGender.equals("2001") && !"2".equals(userVO.getGender())) {
            // 성별 체크
            if (backFlag) {
                HttpUtility.sendBack(request, response, "교육 신청 가능한 성별이 아니므로 신청하실 수 없습니다. 신청 가능 성별을 확인해 주시기 바랍니다.");
            } else {
                HttpUtility.sendRedirect(request, response, "교육 신청 가능한 성별이 아니므로 신청하실 수 없습니다. 신청 가능 성별을 확인해 주시기 바랍니다.", url);
            }
            return false;
        } else if (CommonUtil.getInt(programVO.getDupChk()) > 0) {
            // 중복 신청 체크
            if (backFlag) {
                HttpUtility.sendBack(request, response, "예약하신 교육프로그램으로 이미 예약 신청이 되어있습니다.");
            } else {
                HttpUtility.sendRedirect(request, response, "예약하신 교육프로그램으로 이미 예약 신청이 되어있습니다.", url);
            }
            return false;
        } else {
            String msg = "";
            if (CommonUtil.getString(programVO.getEdcRsvnLimitYn()).equals("Y")) {
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("memNo", userVO.getUniqId());
                paramMap.put("comCd", (String) programVO.getComcd());
                paramMap.put("edcPrgmid", CommonUtil.getString(programVO.getEdcPrgmNo()));
                // 이용횟수 조회
                EdcRsvnInfoVO paramRsvnInfoVO = new EdcRsvnInfoVO();
                int limitCount = edcRsvnInfoService.selectRsvnInfoLimitCheck(paramRsvnInfoVO);

                if (CommonUtil.getInt(programVO.getEdcRsvnLimitCnt()) <= limitCount) {
                    msg = "예약 가능 횟수 가 초과되었습니다. \n(";
                    if (CommonUtil.getString(programVO.getEdcRsvnlmitGbn()).equals("1001")) {
                        msg += "신청하신 교육프로그램의 ";
                    } else {
                        msg += "신청하신 교육프로그램 분류의 ";
                    }
                    if (CommonUtil.getString(programVO.getEdcRsvnLimitPd()).equals("1001")) {
                        msg += "교육기간 기준 ";
                    } else if (CommonUtil.getString(programVO.getEdcRsvnLimitPd()).equals("2001")) {
                        msg += "교육월 기준";
                    } else {
                        msg += "교육년 기준 ";
                    }

                    msg += " 최대 " + programVO.getEdcRsvnLimitCnt() + "회)";

                }

            }

            if (!msg.equals(""))

            {
                HttpUtility.sendRedirect(request, response, msg, url);

                return false;
            } else {
                // 예약 가능 대상 체크

                return true;
            }
        }

    }

    /**
     * 교육 예약 등록 관련 정보를 저장한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "edcarsvnEmailSave", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView edcarsvnEmailSave(HttpServletRequest request, ModelMap model, CommandMap commandMap)
            throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        ResultInfo resultInfo = new ResultInfo();
        LoginVO loginVO = commandMap.getUserInfo();

        if (loginVO == null) {
            resultInfo = HttpUtility.getErrorResultInfo("로그아웃상태입니다. 로그인 후 다시 시도해 주세요.");
        } else if (!loginVO.isMember()) {
            resultInfo = HttpUtility.getSuccessResultInfo(commandMap.getString("eduEmail"));
        } else if (commandMap.getString("edcEmail").equals("")) {
            resultInfo = HttpUtility.getErrorResultInfo("이메일 주소 정보가 없습니다.");
        } else {
            loginVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
            MemberVO memberVO = myInforService.selectMemberData(loginVO);
            memberVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
            memberVO.setComcd(Config.COM_CD);

            if (!commandMap.getString("edcRsvnReqid").equals("") && commandMap.getString("edcEmail").equals(memberVO.getEmail())) {
                EdcRsvnInfoVO rsvnInfoVO = new EdcRsvnInfoVO();
                edcRsvnInfoService.updateRsvnInfoEmail(rsvnInfoVO);
                resultInfo = HttpUtility.getSuccessResultInfo(memberVO.getEmail());
            } else {
                memberVO.setEmail(commandMap.getString("edcEmail")); // 변경할 이메일

                int check = userJoinService.selectMemberEmailCheck(memberVO);
                if (check < 1) {
                    // 회원 이메일 정보 수정하기
                    memberVO.setUserIp(commandMap.getIp());
                    int n = myInforService.updateMemberEmail(memberVO, commandMap.getString("edcRsvnReqid"));

                    if (n > 0) {
                        // 세션정보 업데이트
                        loginVO.setEmail(memberVO.getEmail());
                        resultInfo = HttpUtility.getSuccessResultInfo(memberVO.getEmail());
                    } else {
                        resultInfo = HttpUtility.getErrorResultInfo("업데이트 실패.");
                    }

                } else {
                    resultInfo = HttpUtility.getErrorResultInfo("이미 사용중인 이메일 입니다. 다른 이메일을 입력해 주세요.");
                }
            }
        }

        mav.addObject("RESULT", resultInfo);

        return mav;
    }

}