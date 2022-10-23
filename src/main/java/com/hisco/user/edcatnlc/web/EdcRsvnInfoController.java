package com.hisco.user.edcatnlc.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgMemberDcVO;
import com.hisco.admin.orginfo.vo.OrgOptinfoVO;
import com.hisco.admin.terms.service.TermsService;
import com.hisco.admin.terms.vo.TermsVO;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.ExceptionUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.RequestUtil;
import com.hisco.cmm.util.SessionUtil;
import com.hisco.intrfc.sale.service.SaleChargeService;
import com.hisco.intrfc.sale.service.TotalSaleService;
import com.hisco.intrfc.sale.vo.PaySummaryVO;
import com.hisco.intrfc.sale.vo.SaleFormItemVO;
import com.hisco.intrfc.sale.vo.SaleFormMemberVO;
import com.hisco.intrfc.sale.vo.SaleFormPaymentVO;
import com.hisco.intrfc.sale.vo.SaleFormVO;
import com.hisco.intrfc.sms.service.BizMsgService;
import com.hisco.user.edcatnlc.service.EdcProgramService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;
import com.hisco.user.mypage.service.MyRsvnService;
import com.hisco.user.mypage.vo.MyRsvnVO;

import egovframework.com.cmm.LoginVO;
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
public class EdcRsvnInfoController {

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    // woojinp added
    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

    @Resource(name = "termsService")
    private TermsService termsService;

    @Resource(name = "totalSaleService")
    private TotalSaleService totalSaleService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    @Resource(name = "myRsvnService")
    private MyRsvnService myRsvnService;

    @Resource(name = "bizMsgService")
    private BizMsgService bizMsgService;

    @Resource(name = "saleChargeService")
    private SaleChargeService saleChargeService;
    
    @GetMapping(value = { "/termsAgree/{edcPrgmid}/{edcRsvnsetSeq}", "/termsAgreeAjax/{edcPrgmNo}/{edcRsvnsetSeq}" })
    public String termsAgree(@PathVariable("edcPrgmNo") int edcPrgmid, @PathVariable("edcRsvnsetSeq") int edcRsvnsetSeq,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model, Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        EdcProgramVO param = new EdcProgramVO();
        param.setEdcPrgmNo(edcPrgmid);
        param.setEdcRsvnsetSeq(edcRsvnsetSeq);
        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(param);

        if (detailVO == null)
            HttpUtility.sendRedirect(request, response, "유효하지 않는 강좌입니다.", Config.USER_ROOT + "/edc/program/list");

        model.addAttribute("detailVO", detailVO);

        TermsVO termsParam = new TermsVO();
        // termsParam.setOrgNo(detailVO.getOrgNo());
        termsParam.setStplatIdList(Arrays.asList(Constant.CM_TERMS_ID_개인정보수집이용동의, Constant.CM_TERMS_ID_교육예약유의사항동의));
        model.addAttribute("termsList", termsService.selectTermsList(termsParam));

        // TODO:Constant.CM_TERMS_ID_교육예약유의사항동의는 약관이 아니라 강좌별로 입력하는 값. 해당강좌에 국한되는 유의사항임
        // 따라서 edcProgramService.selectProgramDetail에서 가져오는 것으로 기능 추가를 해야함. 2012.11.18

        LoginVO loginVO = commandMap.getUserInfo();
        if (loginVO == null) {
            loginVO = new LoginVO();
        }
        
        if(detailVO.getEdcRsvnRectype().equals("2001")) {
        	// 노원구청 3기본(공릉평생교육원+노원평생교육원+장미실습장) 할인감면제한
            EdcRsvnInfoVO limitVO = new EdcRsvnInfoVO();
            limitVO.setEdcRsvnCustnm(loginVO.getName());
            limitVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
            limitVO.setEdcReqSdate(detailVO.getEdcSdate());
            limitVO.setEdcPrgmNo(detailVO.getEdcPrgmNo());
            limitVO.setEdcRsvnNo("");
            List<EdcRsvnInfoVO> limitList = edcRsvnInfoService.selectNowon3BasicOrgRegiList(limitVO);

            if (limitList == null || limitList.isEmpty()) {
                model.addAttribute("dcLimitYn", Config.NO);
            } else {
                model.addAttribute("dcLimitYn", Config.YES);
            }
            
            if (Config.NO.equals(detailVO.getExclDcyn())) { // 감면적용예외가 아닌경우
                String memNo = null;
                if (commandMap.getUserInfo() != null) {
                    memNo = commandMap.getUserInfo().getUniqId();
                }

                OrgMemberDcVO orgMemberDcVO = new OrgMemberDcVO();
                orgMemberDcVO.setComcd(detailVO.getComcd());
                orgMemberDcVO.setOrgNo(Integer.parseInt(detailVO.getOrgNo()));
                orgMemberDcVO.setMemNo(memNo);
                model.addAttribute("dcList", orgInfoService.selectOrgMemberDcList(orgMemberDcVO));
            }
        }
        

        model.addAttribute("userInfo", commandMap.getUserInfo());

        String requestUri = request.getRequestURI();

        if (requestUri.indexOf("termsAgreeAjax") >= 0) {
            // 회원인 경우 바로신청 가능해서 layerpop 으로 표시함
            // 나이 / 성별 등 예약 제한 사항 체크
            String checkStr = edcRsvnInfoService.checkApply(detailVO, loginVO);

            if (!checkStr.equals("OK")) {
                // 예약불가인 경우 에러 메시지
                model.addAttribute("errorMsg", "ERR|" + checkStr);
            }

            return Config.USER_ROOT + "/edc/rsvn/rsvnTermsAgreeAjax";
        } else {
            return Config.USER_ROOT + "/edc/rsvn/rsvnTermsAgree";
        }

    }
    
    @GetMapping(value = { "/termsDcAjax/{edcPrgmid}/{edcRsvnsetSeq}" })
    public String termsDc(EdcRsvnInfoVO rsvnInfoParam, @PathVariable("edcPrgmid") int edcPrgmid, @PathVariable("edcRsvnsetSeq") int edcRsvnsetSeq,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model, Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        EdcProgramVO param = new EdcProgramVO();
        param.setEdcPrgmNo(edcPrgmid);
        param.setEdcRsvnsetSeq(edcRsvnsetSeq);
        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(param);

        if (detailVO == null)
            HttpUtility.sendRedirect(request, response, "유효하지 않는 강좌입니다.", Config.USER_ROOT + "/edc/program/list");

        System.out.println(commandMap.getParam().get("dccd"));
        model.addAttribute("dccd", commandMap.getParam().get("dccd"));
        
       
        LoginVO loginVO = commandMap.getUserInfo();
        // 비회원정보로 세션생성
        if (loginVO == null || !loginVO.isMember() || StringUtils.isBlank(loginVO.getName())) {
            loginVO = new LoginVO();
            loginVO.setGender(rsvnInfoParam.getEdcRsvnGender());
            loginVO.setBirthDate(rsvnInfoParam.getEdcRsvnBirthdate());
            loginVO.setName(rsvnInfoParam.getEdcRsvnCustnm());
            loginVO.setIhidNum(rsvnInfoParam.getEdcRsvnMoblphon());

            UserSession userSession = new UserSession();
            userSession.setUserInfo(loginVO);
            SessionUtil.setAttribute(Config.USER_SESSION, userSession);
        }
        
        
        if(detailVO.getEdcRsvnRectype().equals("2001")) {
        	// 노원구청 3기본(공릉평생교육원+노원평생교육원+장미실습장) 할인감면제한
            EdcRsvnInfoVO limitVO = new EdcRsvnInfoVO();
            limitVO.setEdcRsvnCustnm(loginVO.getName());
            limitVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
            limitVO.setEdcReqSdate(detailVO.getEdcSdate());
            limitVO.setEdcPrgmNo(detailVO.getEdcPrgmNo());
            limitVO.setEdcRsvnNo("");
            List<EdcRsvnInfoVO> limitList = edcRsvnInfoService.selectNowon3BasicOrgRegiList(limitVO);

            if (limitList == null || limitList.isEmpty()) {
                model.addAttribute("dcLimitYn", Config.NO);
            } else {
                model.addAttribute("dcLimitYn", Config.YES);
            }
            
            if (Config.NO.equals(detailVO.getExclDcyn())) { // 감면적용예외가 아닌경우
                String memNo = null;
                if (commandMap.getUserInfo() != null) {
                    memNo = commandMap.getUserInfo().getUniqId();
                }

                OrgMemberDcVO orgMemberDcVO = new OrgMemberDcVO();
                orgMemberDcVO.setComcd(detailVO.getComcd());
                orgMemberDcVO.setOrgNo(Integer.parseInt(detailVO.getOrgNo()));
                orgMemberDcVO.setMemNo(memNo);
                model.addAttribute("dcList", orgInfoService.selectOrgMemberDcList(orgMemberDcVO));
            }
            
        }
        

        model.addAttribute("userInfo", commandMap.getUserInfo());

        String requestUri = request.getRequestURI();

       
        // 회원인 경우 바로신청 가능해서 layerpop 으로 표시함
        // 나이 / 성별 등 예약 제한 사항 체크
        String checkStr = edcRsvnInfoService.checkApply(detailVO, loginVO);

        if (!checkStr.equals("OK")) {
            // 예약불가인 경우 에러 메시지
            model.addAttribute("errorMsg", "ERR|" + checkStr);
        }

        return Config.USER_ROOT + "/edc/rsvn/rsvnTermsDcAjax";
        

    }

    /**
     * 무료강의: 예약 및 완료처리
     * 유료간의: 예약처리
     */
    @PostMapping(value = "/apply.json")
    public String applyJson(EdcRsvnInfoVO rsvnInfoParam,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model, Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        ResultInfo result = new ResultInfo(Config.SUCCESS, "성공적으로 신청이 완료되었습니다");

        LoginVO loginVO = commandMap.getUserInfo();

        int saveCnt = 0;
        String payYn = "N";// 결제 페이지로 이동 여부

        try {
            EdcProgramVO param = new EdcProgramVO();
            param.setEdcPrgmNo(rsvnInfoParam.getEdcPrgmNo());
            param.setEdcRsvnsetSeq(rsvnInfoParam.getEdcRsvnsetSeq());
            EdcProgramVO programDetailVO = edcProgramService.selectProgramDetail(param);

            // 기관 설정 정보 가져오기
            OrgOptinfoVO orgOptinfoVO = new OrgOptinfoVO();
            orgOptinfoVO.setOrgNo(Integer.parseInt(programDetailVO.getOrgNo()));
            orgOptinfoVO = orgInfoService.selectOrgOptinfo(orgOptinfoVO);

            // 1차 제한 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            // 2차제한은 trigger에 존재
            if (!RequestUtil.getReferer(request).contains(Config.USER_ROOT + "/edc/rsvn/termsAgree/" + rsvnInfoParam.getEdcPrgmNo()) && !RequestUtil.getReferer(request).contains(Config.USER_ROOT + "/edc/program")) {
                // HttpUtility.sendRedirect(request, response, "잘못된 접근입니다.", Config.USER_ROOT + "/edc/program/list");
                throw new RuntimeException("잘못된 접근입니다.");
            }

            // 비회원정보로 세션생성
            if (loginVO == null || !loginVO.isMember() || StringUtils.isBlank(loginVO.getName())) {
                loginVO = new LoginVO();
                loginVO.setGender(rsvnInfoParam.getEdcRsvnGender());
                loginVO.setBirthDate(rsvnInfoParam.getEdcRsvnBirthdate());
                loginVO.setName(rsvnInfoParam.getEdcRsvnCustnm());
                loginVO.setIhidNum(rsvnInfoParam.getEdcRsvnMoblphon());

                UserSession userSession = new UserSession();
                userSession.setUserInfo(loginVO);
                SessionUtil.setAttribute(Config.USER_SESSION, userSession);
            }

            // 나이 / 성별 등 예약 제한 사항 체크
            String checkStr = edcRsvnInfoService.checkApply(programDetailVO, loginVO);

            if (!checkStr.equals("OK")) {
                throw new RuntimeException(checkStr);
            }
            // ---------------나이/성별 제약사항 체크 끝

            // 예약가능기간체크 및 마감대기정원체크 (TRIGGER에서 구현)
            /*
             * if (!Constant.EDC_PROGRAM_STATUS_신청.equals(programDetailVO.getEdcStatus())) {// 신청가능
             * if (Constant.SM_LEREC_TYPE_선착대기.equals(programDetailVO.getEdcRsvnRectype())) {
             * int edcEndWaitCapa = programDetailVO.getEdcEndwaitCapa();// 마감대기정원
             * int statAssignWaitCnt = programDetailVO.getStatAssignWaitCnt(); // 배정대기건수
             * if (statAssignWaitCnt >= edcEndWaitCapa) {
             * throw new RuntimeException("대기정원이 마감되었습니다.");
             * }
             * } else {
             * throw new RuntimeException("예약 가능 기간이 아닙니다.");
             * }
             * }
             */

            // 추가 값 설정 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            if (!loginVO.isMember()) {// 예약회원타입을 비회원으로 변경
                rsvnInfoParam.setEdcRsvnMemtype(Constant.EDC_RSVN_MEMTYPE_비회원);
            } else {
                // ID이므로 저장시에는 memno를 조회하여 저장
                rsvnInfoParam.setEdcRsvnMemtype(Constant.EDC_RSVN_MEMTYPE_회원);
                rsvnInfoParam.setEdcRsvnMemno(loginVO.getUniqId());
                rsvnInfoParam.setEdcMemNo(loginVO.getUniqId());
            }

            /*
             * TODO:삭제
             * if (RequestUtil.isMobile(request)) { // default PC
             * rsvnInfoParam.setEdcTrmnlType(Constant.EDC_TRMNL_TYPE_MO);
             * }
             */

            /*
             * 암호화는 DB암호화로 변경
             * if (StringUtils.isNotBlank(rsvnInfoParam.getEdcRsvnMoblphon())) {
             * String nonMemberHp = rsvnInfoParam.getEdcRsvnMoblphon();
             * String encryptedHp = WebEncDecUtil.fn_encrypt(nonMemberHp, SpowiseCmsKey);
             * rsvnInfoParam.setEdcRsvnMoblphon(StringUtils.isBlank(encryptedHp) ? nonMemberHp : encryptedHp);
             * }
             */

            // 저장. (저장시 trigger를 통한 > 정원가능, 중복등로 가능 여부)
            try {
                String rsvnStat = Constant.SM_RSVN_STAT_입금대기; // 선착접수, 선착대기의 예약상태값은 "입금대기"

                if (programDetailVO.getSaleAmt() == 0) { // 무료강좌
                    rsvnStat = Constant.SM_RSVN_STAT_등록완료;
                }

                if (Constant.SM_LEREC_TYPE_추첨대기제.equals(programDetailVO.getEdcRsvnRectype())) {// 추첨대기
                    rsvnStat = Constant.SM_RSVN_STAT_추첨대기;

                } else if (Constant.SM_LEREC_TYPE_선착대기.equals(programDetailVO.getEdcRsvnRectype())) {
                    if (programDetailVO.getStatAssignWaitCnt() > 0 || programDetailVO.getStatApplyCnt() >= programDetailVO.getEdcPncpa() || ("Y".equals(programDetailVO.getEdcCapaDvdyn()) && programDetailVO.getStatApplyOnCnt() >= programDetailVO.getEdcOncapa())) { // 정원마감
                        rsvnStat = Constant.SM_RSVN_STAT_배정대기;
                    }
                }

                if (rsvnStat.equals(Constant.SM_RSVN_STAT_입금대기)) {
                    payYn = "Y"; // 유료이고 입금대기면 결제페이지로 이동한다
                }

                String edcTrmnlType = Constant.SM_TERMINAL_TYPE_온라인WEB;
                if (request.getAttribute("IS_MOBILE") != null) {
                    boolean isMobile = (boolean) request.getAttribute("IS_MOBILE");
                    if (isMobile)
                        edcTrmnlType = Constant.SM_TERMINAL_TYPE_모바일;
                }

                if (rsvnStat.equals(Constant.SM_RSVN_STAT_등록완료)) {// 무료인경우 관련테이블(6개) 모두에 insert
                    SaleFormVO saleFormVO = new SaleFormVO();
                    saleFormVO.setRsvnStat(rsvnStat);

                    SaleFormMemberVO member = new SaleFormMemberVO();
                    member.setMemNo(rsvnInfoParam.getEdcMemNo());
                    member.setMemNm(rsvnInfoParam.getEdcRsvnCustnm());
                    member.setMemHp(rsvnInfoParam.getEdcRsvnMoblphon());
                    member.setMemBirthdate(rsvnInfoParam.getEdcRsvnBirthdate());

                    SaleFormItemVO item = new SaleFormItemVO();
                    item.setComcd(rsvnInfoParam.getComcd());
                    item.setOrgNo(Integer.parseInt(programDetailVO.getOrgNo()));
                    item.setItemCd(programDetailVO.getItemCd());
                    item.setCostAmt(programDetailVO.getCostAmt());
                    item.setSalamt(programDetailVO.getSalamt());
                    item.setSaleAmt(programDetailVO.getSaleAmt());
                    item.setMonthCnt(programDetailVO.getMonthCnt());
                    item.setEdcPrgmNo(programDetailVO.getEdcPrgmNo());
                    item.setEdcSdate(programDetailVO.getEdcSdate());
                    item.setEdcEdate(programDetailVO.getEdcEdate());
                    item.setEdcRsvnsetSeq(programDetailVO.getEdcRsvnsetSeq());
                    item.setVatYn(Config.NO);

                    SaleFormPaymentVO payment = new SaleFormPaymentVO();
                    payment.setOnoff(Constant.EDC_ONOFFIN_TYPE_ON);
                    payment.setRergistGbn(Constant.SM_REGIST_GBN_신규등록);
                    payment.setPayAmt(0);
                    payment.setDcAmt(0);
                    payment.setPayMethod("CASH"); // 무료인경우 현금으로 셋팅
                    payment.setPayComcd(Constant.PG_SELF);
                    payment.setFinanceCd("CH");

                    payment.setTerminalType(edcTrmnlType);

                    saleFormVO.setMember(member);
                    saleFormVO.setItemList(Arrays.asList(item));
                    saleFormVO.setPayment(payment);

                    saveCnt = totalSaleService.register(saleFormVO);
                    rsvnInfoParam.setEdcRsvnNo(saleFormVO.getPayment().getRsvnNo());
                } else {
                    rsvnInfoParam.setEdcMonthcnt(programDetailVO.getMonthCnt());
                    rsvnInfoParam.setEdcOnoffintype(Constant.EDC_ONOFFIN_TYPE_ON);
                    rsvnInfoParam.setEdcTrmnlType(edcTrmnlType);
                    rsvnInfoParam.setEdcStat(rsvnStat);
                    saveCnt = edcRsvnInfoService.saveRsvnInfo(rsvnInfoParam);

                    bizMsgService.sendRsvnMessage(rsvnInfoParam.getEdcRsvnNo());
                }

                // 오프라인 계좌 입금만 받을 경우
                if (payYn.equals("Y") && "0".equals(orgOptinfoVO.getWebPaymentMethod())) {
                    payYn = "N";
                }
            } catch (TransientDataAccessResourceException ex) {
                throw new RuntimeException(ExceptionUtil.getSQLErrorMessage(ex, "RSVN"));
            } catch (RuntimeException ex) {
                throw ex;
            }
        } catch (Exception ex) {
            rsvnInfoParam.setEdcRsvnNo("");
            result.setCode(Config.FAIL);
            result.setMsg(ex.getMessage());
            log.error(ExceptionUtil.getErrorLine(ex));
        }

        model.clear();

        if (result.getMsg() != null && result.getMsg().contains("|")) {// "이미예약신청이되어있습니다.|기예약번호|예약상태"
            String[] arr = StringUtils.split(result.getMsg(), "|");
            result.setMsg(arr[0]);
            rsvnInfoParam.setEdcRsvnNo(arr[1]);
            rsvnInfoParam.setEdcStat(arr[2]);
        }

        model.addAttribute("result", result);
        model.addAttribute("PAY_YN", payYn);
        model.addAttribute("EDC_RSVN_NO", rsvnInfoParam.getEdcRsvnNo());
        model.addAttribute("EDC_PRGMID", rsvnInfoParam.getEdcPrgmNo());
        model.addAttribute("EDC_STAT", rsvnInfoParam.getEdcStat());

        // 신청이 완료되었고 비회원이라면 비회원 세션을 만들어 마이페이지로 이동시킨다
        if ((saveCnt > 0 || !StringUtil.IsEmpty(rsvnInfoParam.getEdcRsvnNo())) && !loginVO.isMember()) {
            HttpSession session = request.getSession();
            UserSession userSession = (UserSession) session.getAttribute(Config.USER_SESSION);
            userSession.setUserInfo(loginVO);
            session.setAttribute(Config.USER_SESSION, userSession);
        }

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 강좌(edc)예약(rsvn)완료(complete) : 강좌신청완료
     * 이전페이지: 약관동의(~/termsAgree/edcPrgmid)
     */
    @GetMapping(value = "/complete/{edcRsvnNo}")
    public String complete(@PathVariable("edcRsvnNo") String edcRsvnNo,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model, Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        MyRsvnVO myRsvnVO = new MyRsvnVO();
        myRsvnVO.setEdcRsvnNo(edcRsvnNo);
        EdcRsvnInfoVO rsvnInfo = myRsvnService.selectMyEdcRsvnDtl(myRsvnVO);

        if (rsvnInfo == null)
            HttpUtility.sendRedirect(request, response, "유효하지 않는 예약건입니다.", Config.USER_ROOT + "/edc/program/list");

        // 기관 설정 정보 가져오기
        OrgOptinfoVO orgOptinfoVO = new OrgOptinfoVO();
        orgOptinfoVO.setOrgNo(rsvnInfo.getOrgNo());
        orgOptinfoVO = orgInfoService.selectOrgOptinfo(orgOptinfoVO);

        PaySummaryVO paySummary = new PaySummaryVO();
        paySummary.setComcd(rsvnInfo.getComcd());
        paySummary.setEdcRsvnReqid(rsvnInfo.getEdcRsvnReqid());
        paySummary = saleChargeService.selectPaySummary(paySummary);

        model.addAttribute("rsvnInfo", rsvnInfo);
        model.addAttribute("orgOptinfoVO", orgOptinfoVO);
        model.addAttribute("paySummary", paySummary);

        return Config.USER_ROOT + "/edc/rsvn/rsvnComplete";
    }

}