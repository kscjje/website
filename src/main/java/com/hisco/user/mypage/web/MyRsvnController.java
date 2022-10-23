package com.hisco.user.mypage.web;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgOptinfoVO;
import com.hisco.cmm.exception.MyException;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.ResponseUtil;
import com.hisco.intrfc.charge.service.ChargeService;
import com.hisco.intrfc.charge.vo.OrderIdVO;
import com.hisco.intrfc.sale.service.SaleChargeService;
import com.hisco.intrfc.sale.service.TotalCancelService;
import com.hisco.intrfc.sale.vo.PaySummaryVO;
import com.hisco.intrfc.sms.service.BizMsgService;
import com.hisco.intrfc.sms.service.SmsService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;
import com.hisco.user.edcatnlc.vo.EdcRsvnMstVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcrsvnService;
import com.hisco.user.evtrsvn.service.EvtItemAmountVO;
import com.hisco.user.evtrsvn.service.EvtStdmngVO;
import com.hisco.user.evtrsvn.service.EvtrsvnMstVO;
import com.hisco.user.evtrsvn.service.EvtrsvnService;
import com.hisco.user.exbtrsvn.service.DspyDsService;
import com.hisco.user.exbtrsvn.service.ExbtChargeVO;
import com.hisco.user.exbtrsvn.service.RsvnMasterVO;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.service.MyInforService;
import com.hisco.user.mypage.service.MyRsvnService;
import com.hisco.user.mypage.service.RsvnCommService;
import com.hisco.user.mypage.vo.MyRsvnVO;
import com.hisco.user.mypage.vo.RsvnCommVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
/* import lgdacom.XPayClient.XPayClient; 2021.06.06 JYS */
import lombok.extern.slf4j.Slf4j;

/**
 * 마이페이지 예약정보 컨트롤러
 *
 * @author 진수진
 * @since 2020.09.14
 * @version 1.0, 2020.09.14
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.08.19 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/mypage/myRsvn")
public class MyRsvnController {

    @Resource(name = "myRsvnService")
    private MyRsvnService myRsvnService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "chargeService")
    private ChargeService chargeService;

    @Resource(name = "dspyDsService")
    private DspyDsService dspyDsService;

    @Resource(name = "rsvnCommService")
    private RsvnCommService rsvnCommService;

    @Resource(name = "evtrsvnService")
    private EvtrsvnService evtrsvnService;

    @Resource(name = "evtEdcrsvnService")
    private EvtEdcrsvnService evtEdcrsvnService;

    @Resource(name = "smsService")
    private SmsService smsService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnService;

    @Resource(name = "myInforService")
    private MyInforService myInforService;

    @Resource(name = "totalCancelService")
    private TotalCancelService totalCancelService;

    @Resource(name = "saleChargeService")
    private SaleChargeService saleChargeService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    @Resource(name = "bizMsgService")
    private BizMsgService bizMsgService;

    /**
     * 예약 목록
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/myRsvnList")
    public String selecRsvnList(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        LoginVO loginVO = commandMap.getUserInfo();

        myRsvnVO.setEdcRsvnMemno(loginVO.getUniqId());
        myRsvnVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
        myRsvnVO.setMemNm(loginVO.getName());
        myRsvnVO.setEdcRsvnBirthdate(loginVO.getBirthDate());
        myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        myRsvnVO.setPaginationInfo(paginationInfo); // 페이징 파라미터 추가
        List<EdcRsvnInfoVO> rsvnList = myRsvnService.selectMyEdcPagingList(myRsvnVO);

        int totCount = 0;
        if (rsvnList != null && rsvnList.size() >= 1) {
            totCount = ((EdcRsvnInfoVO) rsvnList.get(0)).getTotCount();
        }

        paginationInfo.setTotalRecordCount(totCount);

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("list", rsvnList);
        model.addAttribute("userInfo", commandMap.getUserInfo());
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 환불목록
     * @param myRsvnVO
     * @param commandMap
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/myRefundList")
    public String selectMyRefundList(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        LoginVO loginVO = commandMap.getUserInfo();

        /*환불신청*/
        myRsvnVO.setAppStatusNew("CANCEL");
        
        myRsvnVO.setEdcRsvnMemno(loginVO.getUniqId());
        myRsvnVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
        myRsvnVO.setMemNm(loginVO.getName());
        myRsvnVO.setEdcRsvnBirthdate(loginVO.getBirthDate());
        myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        myRsvnVO.setPaginationInfo(paginationInfo); // 페이징 파라미터 추가
        List<EdcRsvnInfoVO> rsvnList = myRsvnService.selectMyEdcPagingList(myRsvnVO);

        int totCount = 0;
        if (rsvnList != null && rsvnList.size() >= 1) {
            totCount = ((EdcRsvnInfoVO) rsvnList.get(0)).getTotCount();
        }

        paginationInfo.setTotalRecordCount(totCount);

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("list", rsvnList);
        model.addAttribute("userInfo", commandMap.getUserInfo());
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }
    
    /**
     * 예매상세내역
     * @param myRsvnVO
     * @param commandMap
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/myRsvnDetail")
    public String myRsvnDetail(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        LoginVO loginVO = commandMap.getUserInfo();
        myRsvnVO.setEdcRsvnMemno(loginVO.getUniqId());
        // 비회원용
        myRsvnVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
        myRsvnVO.setMemNm(loginVO.getName());
        myRsvnVO.setEdcRsvnBirthdate(loginVO.getBirthDate());
        myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        EdcRsvnInfoVO rsvnInfo = myRsvnService.selectMyEdcRsvnDtl(myRsvnVO);
        if (rsvnInfo == null)
            ResponseUtil.SendMessage(request, response, "조회결과가 존재하지 않습니다.", "history.back()");

        // 기관 설정 정보 가져오기
        OrgOptinfoVO orgOptinfoVO = new OrgOptinfoVO();
        orgOptinfoVO.setOrgNo(rsvnInfo.getOrgNo());
        orgOptinfoVO = orgInfoService.selectOrgOptinfo(orgOptinfoVO);

        PaySummaryVO paySummary = new PaySummaryVO();
        paySummary.setComcd(rsvnInfo.getComcd());
        paySummary.setEdcRsvnReqid(rsvnInfo.getEdcRsvnReqid());
        paySummary = saleChargeService.selectPaySummary(paySummary);

        model.addAttribute("rsvnInfo", rsvnInfo);
        model.addAttribute("paySummary", paySummary);
        model.addAttribute("orgOptinfoVO", orgOptinfoVO);
        model.addAttribute("queryString", commandMap.getSearchQuery("edcRsvnReqid"));
        model.addAttribute("mypage", "Y");

        return Config.USER_ROOT + "/edc/rsvn/rsvnComplete";
    }

    /**
     * 취소상세내역
     * @param myRsvnVO
     * @param commandMap
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/myCancelDetail")
    public String myCancelDetail(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        LoginVO loginVO = commandMap.getUserInfo();
        myRsvnVO.setEdcRsvnMemno(loginVO.getUniqId());
        // 비회원용
        myRsvnVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
        myRsvnVO.setMemNm(loginVO.getName());
        myRsvnVO.setEdcRsvnBirthdate(loginVO.getBirthDate());
        myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        EdcRsvnInfoVO rsvnInfo = myRsvnService.selectMyEdcRsvnDtl(myRsvnVO);
        if (rsvnInfo == null)
            ResponseUtil.SendMessage(request, response, "조회결과가 존재하지 않습니다.", "history.back()");

        // 기관 설정 정보 가져오기
        OrgOptinfoVO orgOptinfoVO = new OrgOptinfoVO();
        orgOptinfoVO.setOrgNo(rsvnInfo.getOrgNo());
        orgOptinfoVO = orgInfoService.selectOrgOptinfo(orgOptinfoVO);

        PaySummaryVO paySummary = new PaySummaryVO();
        paySummary.setComcd(rsvnInfo.getComcd());
        paySummary.setEdcRsvnReqid(rsvnInfo.getEdcRsvnReqid());
        paySummary = saleChargeService.selectPaySummary(paySummary);

        model.addAttribute("rsvnInfo", rsvnInfo);
        model.addAttribute("paySummary", paySummary);
        model.addAttribute("orgOptinfoVO", orgOptinfoVO);
        model.addAttribute("queryString", commandMap.getSearchQuery("edcRsvnReqid"));
        model.addAttribute("mypage", "Y");

        return Config.USER_ROOT + "/edc/rsvn/rsvnCancel";
    }
    
    /**
     * 예약 취소
     *
     * @param
     * @return result - 결과
     * @exception Exception
     */
    @PostMapping(value = "/rsvnCancel.json")
    public String rsvnCancelJson(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        ResultInfo resultInfo = new ResultInfo(Config.SUCCESS, "예약 취소가 완료되었습니다.");
        resultInfo.setSuccess(true);

        LoginVO loginVO = commandMap.getUserInfo();

        try {
            if (loginVO == null) {
                throw new RuntimeException("로그인 정보가 없습니다.");
            }

            if (myRsvnVO.getEdcRsvnReqid() < 1 || StringUtils.isBlank(myRsvnVO.getEdcRsvnNo())) {
                throw new RuntimeException("취소를 위한 파라미터 값이 존재하지 않습니다.");
            }

            myRsvnVO.setEdcRsvnMemno(loginVO.getUniqId());
            // 비회원용
            myRsvnVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
            myRsvnVO.setMemNm(loginVO.getName());
            myRsvnVO.setEdcRsvnBirthdate(loginVO.getBirthDate());

            EdcRsvnInfoVO rsvnInfo = myRsvnService.selectMyEdcRsvnDtl(myRsvnVO);

            if (rsvnInfo == null)
                throw new RuntimeException("수강신청 현황이 존재하지 않습니다.");

            if (Config.NO.equals(rsvnInfo.getEditYn()) && Constant.SM_RSVN_STAT_등록완료.equals(rsvnInfo.getEdcStat())) {
                throw new RuntimeException("취소할 수 있는 조건이 아닙니다.");
            }

            /*
             * PG사에서 알아서 대응하므로 취소처리 가능(commented by 박명서이사)
             * if (Config.YES.equals(rsvnInfo.getEditYn()) && Constant.SM_RSVN_STAT_등록완료.equals(rsvnInfo.getEdcStat())
             * && rsvnInfo.getVbankSeq() > 0) { // 가상계좌
             * // 입금완료&등록완료일경우
             * throw new RuntimeException("(가상계좌) 취소할 수 있는 조건이 아닙니다.");
             * }
             */

            if (Constant.SM_RSVN_STAT_신청취소.equals(rsvnInfo.getEdcStat())) {
                throw new RuntimeException("이미 취소된 건 입니다.");
            }

            if (Constant.SM_RSVN_STAT_환불취소.equals(rsvnInfo.getEdcStat())) {
                throw new RuntimeException("이미 환불된 건 입니다.");
            }

            int insertOrUpdateCnt = 0;
            List<EdcRsvnInfoVO> rsvnList = myRsvnService.selectMyEdcPagingList(myRsvnVO);

            if (Constant.ING_RSVN_STAT_LIST.contains(rsvnInfo.getEdcStat())) { // 1000, 1002, 2001
                insertOrUpdateCnt = myRsvnService.updateReserveCancel(rsvnList);
            } else {
                insertOrUpdateCnt = totalCancelService.cancel(rsvnList);
                if (insertOrUpdateCnt > 0) {
                    for (EdcRsvnInfoVO rsvnVO : rsvnList) {
                        bizMsgService.sendMessageToAdmin(rsvnVO);
                    }
                }
            }

            if (insertOrUpdateCnt < 1) {
                resultInfo.setMsg("취소가능한 예약 데이타가 없습니다.");
            }
        } catch (Exception ex) {
            resultInfo.setSuccess(false);
            resultInfo.setMsg(ex.getMessage());
        }

        model.clear();
        model.addAttribute("result", resultInfo);
        return HttpUtility.getViewUrl(request);
    }

    /**
     * 결제 상세
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnPay")
    public String selecRsvnPay(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        String gubun = myRsvnVO.getGubun();
        if (gubun == null)
            gubun = "";

        String cstPLATFORM = EgovProperties.getProperty("tosspayments.platform");

        myRsvnVO.setCstPLATFORM(cstPLATFORM);

        boolean isMobile = false;
        if (request.getAttribute("IS_MOBILE") != null) {
            isMobile = (boolean) request.getAttribute("IS_MOBILE");
        }

        String returnURL = EgovProperties.getProperty("Globals.Domain") + Config.INTRFC_ROOT + (isMobile
                ? "/charge/return/reCallNsmMbUrl" : "/charge/return/reCallNsmUrl");

        myRsvnVO.setLgdRETURNURL(returnURL);
        myRsvnVO.setLgdCASNOTEURL(EgovProperties.getProperty("Globals.Domain") + Config.INTRFC_ROOT + "/charge/return/casNoteUrl");

        model.addAttribute("CstWINDOW_TYPE", (isMobile ? "submit" : "iframe"));
        model.addAttribute("LgdCUSTOM_SWITCHINGTYPE", (isMobile ? "SUBMIT" : "IFRAME"));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 결제 상세
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnPayAjax")
    public String selecRsvnPayList(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        String gubun = myRsvnVO.getGubun();
        if (gubun == null)
            gubun = "";

        LoginVO loginVO = commandMap.getUserInfo();
        loginVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        myRsvnVO.setComcd(Config.COM_CD);
        myRsvnVO.setUniqId(loginVO.getUniqId());
        myRsvnVO.setHpcertno(loginVO.getHpcertno());
        myRsvnVO.setMode("pay");
        myRsvnVO.setReguser(loginVO.getId() == null ? "NONMEMBER" : loginVO.getId()); // 비회원은?
        myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        String partCd = ""; // 사업장 코드
        long payAmount = 0; // 총 결제 금액
        long totalAmount = 0; // 최종 결제 금액
        long cnt = 0;
        String productInfo = "";// 상품명
        String rsvnNo = ""; // oid 생성을 위한 예약번호 , 로 연결

        request.getSession().setAttribute("LgdPRODUCTCODE", gubun);

        List<EdcRsvnInfoVO> list = myRsvnService.selectEdcPaylist(myRsvnVO, "");

        if (list != null && list.size() > 0) {
            for (EdcRsvnInfoVO vo : list) {
                cnt++;

                productInfo = vo.getEdcPrgmnm();

                totalAmount += vo.getEdcTotamt(); // 결제금액 더하기
                payAmount += (vo.getEdcTotamt() + vo.getEdcDcamt()); // 결제금액 더하기

                if (!rsvnNo.equals(""))
                    rsvnNo += ",";
                rsvnNo += vo.getEdcRsvnNo();

                myRsvnVO.setLgdBUYER(vo.getEdcReqCustnm());
                myRsvnVO.setLgdBUYERID(vo.getId());
                myRsvnVO.setLgdBUYEREMAIL(vo.getEdcEmail());
            }

            if (list.size() > 1) {
                productInfo += (" 외 " + (cnt - 1) + "건");
            }
        }

        model.addAttribute("list", list);

        model.addAttribute("payAmount", payAmount);

        myRsvnVO.setPartCd(partCd);
        // 결제 아이디 검색
        MyRsvnVO systemInfo = myRsvnService.selectPartSystem(myRsvnVO);
        if (systemInfo != null) {
            myRsvnVO.setPaymentId(systemInfo.getPaymentId());
            myRsvnVO.setPaymentPw(systemInfo.getPaymentPw());
        }

        String cstPLATFORM = myRsvnVO.getCstPLATFORM();
        // 로그인 회원정보
        if (loginVO.isMember()) {
            myRsvnVO.setLgdBUYER(loginVO.getName());
            myRsvnVO.setLgdBUYERID(loginVO.getId());
            myRsvnVO.setLgdBUYEREMAIL(loginVO.getEmail());
        }

        myRsvnVO.setLgdAMOUNT(Long.toString(totalAmount));
        myRsvnVO.setLgdPRODUCTINFO(productInfo);
        myRsvnVO.setLgdPRODUCTCODE(gubun);
        myRsvnVO.setCstMID(myRsvnVO.getPaymentId());
        myRsvnVO.setLgdMID(("test".equals(cstPLATFORM.trim()) ? "t" : "") + myRsvnVO.getPaymentId());

        String lgdMERTKEY = EgovProperties.getProperty("tosspayments.mertkey." + myRsvnVO.getPaymentId()); // 상점키
        String lgdTIMESTAMP = DateUtil.printDatetime(null, "yyyyMMddHHmmss");

        /* 주문번호 생성 시작 */
        String lgdOID = "";
        if (!rsvnNo.equals("") && rsvnNo.length() > 0) {
            OrderIdVO orderVO = new OrderIdVO();
            orderVO.setComcd(myRsvnVO.getComcd());
            orderVO.setUserId(myRsvnVO.getReguser());
            orderVO.setRsvnNo(rsvnNo); // 예약번호
            orderVO.setRsvnCnt(cnt);
            orderVO.setRsvnAmt(Long.parseLong(myRsvnVO.getLgdAMOUNT()));

            chargeService.insertDbprocLog(orderVO);
            //// JYS 2021.05.18 chargeService.selectOrderId(orderVO);

            // OID 만들기
            lgdOID = orderVO.getRetOid(); // 주문 번호 프로시져에서 생성
            /* 주문번호 생성 끝 */

            myRsvnVO.setLgdOID(lgdOID);
            myRsvnVO.setLgdTIMESTAMP(lgdTIMESTAMP);

            try {
                StringBuffer sb = new StringBuffer();
                sb.append(myRsvnVO.getLgdMID());
                sb.append(myRsvnVO.getLgdOID());
                sb.append(myRsvnVO.getLgdAMOUNT());
                sb.append(lgdTIMESTAMP);
                sb.append(lgdMERTKEY);

                byte[] bNoti = sb.toString().getBytes();
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] digest = md.digest(bNoti);

                StringBuffer strBuf = new StringBuffer();
                for (int i = 0; i < digest.length; i++) {
                    int c = digest[i] & 0xff;
                    if (c <= 15) {
                        strBuf.append("0");
                    }
                    strBuf.append(Integer.toHexString(c));
                }

                String lgdHASHDATA = strBuf.toString();
                myRsvnVO.setLgdHASHDATA(lgdHASHDATA);

            } catch (Exception e) {
                myRsvnVO.setLgdTIMESTAMP("");
                myRsvnVO.setLgdHASHDATA("");
            }
        }

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 예약 상세 ajax
     *
     * @param commandMap
     * @return String - jsp 페이지
     * @exception Exception
     */
    @GetMapping(value = "/detailAjax")
    public String myRsvnDetailAjax(MyRsvnVO rsvnVO, HttpServletRequest request, ModelMap model) throws Exception {

        EdcRsvnInfoVO resultVO = myRsvnService.selectMyEdcRsvnDtl(rsvnVO);
        // String strEncEdcRsvnfmTelno = com.hisco.cmm.util.WebEncDecUtil.fn_decrypt((String)
        // resultVO.get("edcRsvnfmTelnoDec"), strSpowiseCmsKey);

        model.addAttribute("resultVO", resultVO);
        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/myRsvn/edc/detailAjax");
    }

    /**
     * 상품 결제 저장 페이지
     *
     * @param
     * @return callback page
     * @exception Exception
     */
    @PostMapping(value = "/myRsvnListSave")
    public String myRsvnSave(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO) throws Exception {
        myRsvnVO.setComcd(Config.COM_CD);
        myRsvnVO.setMode("save");
        myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        String configPath = FileMngUtil.GetRealRootPath().concat("WEB-INF/lgdacom"); // LG유플러스에서 제공한
                                                                                     // 환경파일("/conf/lgdacom.conf,/conf/mall.conf")
                                                                                     // 위치 지정.

        long totalAmount = 0; // 최종결제금액

        List<?> list = null;

        if (myRsvnVO.getLgdPRODUCTCODE().equals("EXBT")) {
            list = myRsvnService.selectExbtPayList(myRsvnVO, "");
            if (list != null && list.size() > 0) {
                for (RsvnMasterVO vo : ((List<RsvnMasterVO>) list)) {
                    totalAmount += vo.getPayamt();
                }
            }
        } else if (myRsvnVO.getLgdPRODUCTCODE().equals("EVT")) {
            list = myRsvnService.selectEvtPaylist(myRsvnVO, "");
            if (list != null && list.size() > 0) {
                for (EvtrsvnMstVO vo : ((List<EvtrsvnMstVO>) list)) {
                    totalAmount += Integer.parseInt(vo.getEvtRsvnPayamt());
                }
            }
        } else if (myRsvnVO.getLgdPRODUCTCODE().equals("EDC")) {
            list = myRsvnService.selectEdcPaylist(myRsvnVO, "");
            if (list != null && list.size() > 0) {
                for (EdcRsvnMstVO vo : ((List<EdcRsvnMstVO>) list)) {
                    totalAmount += vo.getEdcTotamt(); // 결제금액 더하기
                }
            }
        }
        /*
         ************************************************* 1.최종결제 요청 - BEGIN
         * (단, 최종 금액체크를 원하시는 경우 금액체크 부분 주석을 제거 하시면 됩니다.)
         */

        String cstPLATFORM = EgovProperties.getProperty("tosspayments.platform");
        String lgdMID = request.getParameter("LgdMID");
        String lgdPAYKEY = request.getParameter("LgdPAYKEY");
        String lgdRESPCODE = request.getParameter("LgdRESPCODE");
        String lgdRESPMSG = request.getParameter("LgdRESPMSG");

        StringBuffer resultMsg = new StringBuffer();
        boolean isDBOK = false;
        boolean isInitOK = false;

        if (lgdRESPCODE != null && !lgdRESPCODE.equals("0000")) {
            resultMsg.append(lgdRESPMSG);
        } else {
            isInitOK = true;
        }

        commandMap.put("APP_GBN", "1"); // 승인구분 카드 승인 1
        commandMap.put("P_COMCD", "DACOM"); // 결제대행사

        if (totalAmount == 0) {
            try {

                log.debug("myRsvnSave 1 :: commandMap = " + commandMap);
                log.debug("myRsvnSave 1 :: commandMap.getParam() = " + commandMap.getParam());
                log.debug("myRsvnSave 1 :: myRsvnVO = " + myRsvnVO);

                isDBOK = myRsvnService.insertPaymentInfo(commandMap.getParam(), myRsvnVO);
            } catch (Exception e) {
                isDBOK = false;
            }
            if (!isDBOK) {
                resultMsg.append("결제 오류 코드 : ");
                resultMsg.append(myRsvnVO.getRetCd());
                resultMsg.append("<br>");
                resultMsg.append("결제 오류 메시지 : ");
                resultMsg.append(myRsvnVO.getRetMsg());
                resultMsg.append("<br>");
            }

        } else {

            // 해당 API를 사용하기 위해 WEB-INF/lib/XPayClient.jar 를 Classpath 로 등록하셔야 합니다.
            // (1) XpayClient의 사용을 위한 xpay 객체 생성

            /***
             * XPayClient xpay = new XPayClient(); 2021.06.06 JYS
             * // (2) Init: XPayClient 초기화(환경설정 파일 로드)
             * // configPath: 설정파일
             * // CstPLATFORM: - test, service 값에 따라 lgdacom.conf의 test_url(test) 또는 url(srvice) 사용
             * // - test, service 값에 따라 테스트용 또는 서비스용 아이디 생성
             * if (isInitOK) {
             * isInitOK = xpay.Init(configPath, cstPLATFORM);
             * }
             * if ( !isInitOK ) {
             * //API 초기화 실패 화면처리
             * resultMsg.append( "결제요청을 초기화 하는데 실패하였습니다.<br>
             * ");
             * resultMsg.append( "LG유플러스에서 제공한 환경파일이 정상적으로 설치 되었는지 확인하시기 바랍니다.<br>
             * ");
             * resultMsg.append( "mall.conf에는 Mert ID = Mert Key 가 반드시 등록되어 있어야 합니다.<br>
             * <br>
             * ");
             * resultMsg.append( "문의전화 LG유플러스 1544-7772<br>
             * ");
             * } else {
             * try {
             * // (3) Init_TX: 메모리에 mall.conf, lgdacom.conf 할당 및 트랜잭션의 고유한 키 TXID 생성
             * xpay.Init_TX(lgdMID);
             * xpay.Set("LgdTXNAME", "PaymentByKey");
             * xpay.Set("LgdPAYKEY", lgdPAYKEY);
             * //금액을 체크하시기 원하는 경우 아래 주석을 풀어서 이용하십시요.
             * //String DB_AMOUNT = "DB나 세션에서 가져온 금액"; //반드시 위변조가 불가능한 곳(DB나 세션)에서 금액을 가져오십시요.
             * xpay.Set("LgdAMOUNTCHECKYN", "Y");
             * xpay.Set("LgdAMOUNT", String.valueOf(totalAmount));
             * } catch (Exception e) {
             * // LG U+ API 사용 불가, 설정파일 확인 등 필요(예외처리)
             * resultMsg.append("LG유플러스 제공 API를 사용할 수 없습니다. 환경파일 설정을 확인해 주시기 바랍니다. ");
             * resultMsg.append(e.getMessage());
             * isInitOK = false;
             * }
             * }
             */

            /*
             ************************************************* 1.최종결제 요청(수정하지 마세요) - END
             */

            /*
             * 2. 최종결제 요청 결과처리
             * 최종 결제요청 결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
             */
            // (4) TX: lgdacom.conf에 설정된 URL로 소켓 통신하여 최종취소요청, 결과값으로 true, false 리턴

            /*
             * 2021.06.06 JYS
             * if (isInitOK) {
             * if ( xpay.TX() ) {
             * //1)결제결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
             * resultMsg.append( "결제요청이 완료되었습니다.  <br>");
             * resultMsg.append( "TX 결제요청 통신 응답코드 = "); //통신 응답코드("0000" 일 때 통신 성공)
             * resultMsg.append( xpay.m_szResCode);
             * resultMsg.append( "<br>");
             * resultMsg.append( "TX 결제요청 통신 응답메시지 = " );
             * resultMsg.append( xpay.m_szResMsg);
             * log.debug("거래번호 : " + xpay.Response("LgdTID",0) + "<br>");
             * log.debug("상점아이디 : " + xpay.Response("LgdMID",0) + "<br>");
             * log.debug("상점주문번호 : " + xpay.Response("LgdOID",0) + "<br>");
             * log.debug("결제금액 : " + xpay.Response("LgdAMOUNT",0) + "<br>");
             * log.debug("결과코드 : " + xpay.Response("LgdRESPCODE",0) + "<br>"); //LgdRESPCODE 결제요청 응답코드
             * log.debug("결과메세지 : " + xpay.Response("LgdRESPMSG",0) + "<p>");
             * for (int i = 0; i < xpay.ResponseNameCount(); i++)
             * {
             * //out.println(xpay.ResponseName(i) + " = ");
             * for (int j = 0; j < xpay.ResponseCount(); j++)
             * {
             * //out.println("\t" + xpay.Response(xpay.ResponseName(i), j) + "<br>");
             * commandMap.put(xpay.ResponseName(i) , xpay.Response(xpay.ResponseName(i), j));
             * }
             * }
             * lgdRESPCODE = xpay.Response("LgdRESPCODE",0) ;
             * lgdRESPMSG = xpay.Response("LgdRESPMSG",0) ;
             * String lgdPAYTYPE = xpay.Response("LgdPAYTYPE", 0);
             * //결제 실패했을 경우 호출하기 위해
             * Map<String, Object> cancelMap = new HashMap<String,Object>();
             * cancelMap.put("COMCD", myRsvnVO.getComcd());
             * cancelMap.put("LgdOID", xpay.Response("LgdOID",0));
             * cancelMap.put("OID_STAT", "4001");
             * cancelMap.put("MODUSER",xpay.Response("LgdBUYERID",0));
             * // (5)DB에 요청 결과 처리
             * if ( "0000".equals( xpay.m_szResCode ) && "0000".equals(lgdRESPCODE)) {
             * // 통신상의 문제가 없을시
             * // 최종결제요청 결과 성공 DB처리(LgdRESPCODE 값에 따라 결제가 성공인지, 실패인지 DB처리)
             * // 최종결제요청 결과를 DB처리합니다. (결제성공 또는 실패 모두 DB처리 가능)
             * // 상점내 DB에 어떠한 이유로 처리를 하지 못한경우 false로 변경해 주세요.
             * // 만약 DB처리 실패시 Rollback 처리, isDBOK 파라미터를 false 로 변경
             * //
             * commandMap.put("P_TYPE", "SC0010".equals(lgdPAYTYPE)?"CARD":"BANK"); // 결제방법
             * commandMap.put("PG_VAN" , "PG");
             * if ("SC0010".equals(lgdPAYTYPE)) {
             * commandMap.put("CARD_CD", xpay.Response("LgdFINANCECODE", 0) != null && xpay.Response("LgdFINANCECODE",
             * 0).length() > 2 ? xpay.Response("LgdFINANCECODE", 0).substring(0, 2) : xpay.Response("LgdFINANCECODE",
             * 0));
             * commandMap.put("BANK_CD", xpay.Response("LgdFINANCECODE", 0) != null && xpay.Response("LgdFINANCECODE",
             * 0).length() > 2 ? xpay.Response("LgdFINANCECODE", 0).substring(0, 2) : xpay.Response("LgdFINANCECODE",
             * 0));
             * } else {
             * commandMap.put("CARD_CD", xpay.Response("LgdFINANCECODE", 0) != null && xpay.Response("LgdFINANCECODE",
             * 0).length() > 3 ? xpay.Response("LgdFINANCECODE", 0).substring(0, 3) : xpay.Response("LgdFINANCECODE",
             * 0));
             * commandMap.put("BANK_CD", xpay.Response("LgdFINANCECODE", 0) != null && xpay.Response("LgdFINANCECODE",
             * 0).length() > 3 ? xpay.Response("LgdFINANCECODE", 0).substring(0, 3) : xpay.Response("LgdFINANCECODE",
             * 0));
             * }
             * commandMap.put("UIP", commandMap.getIp());
             * if (request.getAttribute("IS_MOBILE") == null) {
             * commandMap.put("TERMINAL_TYPE", "2001");
             * } else {
             * boolean isMobile = (boolean)request.getAttribute("IS_MOBILE");
             * commandMap.put("TERMINAL_TYPE", (isMobile?"2002":"2001"));
             * }
             * commandMap.put("REGUSER", xpay.Response("LgdBUYERID", 0));
             * commandMap.put("comcd", myRsvnVO.getComcd());
             * try {
             * log.debug("myRsvnSave 2 :: commandMap = " + commandMap);
             * log.debug("myRsvnSave 2 :: commandMap.getParam() = " + commandMap.getParam());
             * log.debug("myRsvnSave 2 :: myRsvnVO = " + myRsvnVO);
             * isDBOK = myRsvnService.insertPaymentInfo(commandMap.getParam() , myRsvnVO);
             * } catch (Exception e) {
             * isDBOK = false;
             * }finally{
             * //로그 저장
             * try {
             * chargeService.insertDbprocLog(myRsvnVO);
             * } catch (Exception e) {
             * e.printStackTrace();
             * }
             * }
             * if ( !isDBOK ) {
             * xpay.Rollback("상점 DB처리 실패로 인하여 Rollback 처리 [TID:" +xpay.Response("LgdTID",0)+",MID:" +
             * xpay.Response("LgdMID",0)+",OID:"+xpay.Response("LgdOID",0)+"]");
             * resultMsg.setLength(0);
             * //resultMsg.append( "TX Rollback Response_code = " + xpay.Response("LgdRESPCODE",0) + "<br>");
             * //resultMsg.append( "TX Rollback Response_msg = " + xpay.Response("LgdRESPMSG",0) + "<p>");
             * resultMsg.append( "결제 오류 코드 : " +myRsvnVO.getRetCd() + "<br>");
             * resultMsg.append( "결제 오류 메시지 : " +myRsvnVO.getRetMsg() + "<br>");
             * if ( "0000".equals( xpay.m_szResCode ) ) {
             * resultMsg.append("자동취소가 정상적으로 완료 되었습니다.<br>");
             * } else {
             * resultMsg.append("자동취소가 정상적으로 처리되지 않았습니다. 고객센터로 문의해 주시기 바랍니다.<br>");
             * }
             * try {
             * cancelMap.put("REQUEST_RESULT", myRsvnVO.getRetMsg());
             * chargeService.updatePgOrdMst(cancelMap);
             * } catch (Exception e) {
             * e.printStackTrace();
             * }
             * }
             * } else {
             * //최종결제요청 결과 실패 DB처리
             * resultMsg.append( "결제요청이 실패하였습니다(1).  <br>");
             * resultMsg.append( "TX 결제요청 Response_code = " + lgdRESPCODE + "<br>");
             * resultMsg.append( "TX 결제요청 Response_msg = " + lgdRESPMSG + "<p>");
             * try {
             * cancelMap.put("REQUEST_RESULT", lgdRESPMSG);
             * chargeService.updatePgOrdMst(cancelMap);
             * } catch (Exception e) {
             * e.printStackTrace();
             * }
             * }
             * } else {
             * //2)API 요청실패 화면처리
             * resultMsg.append( "결제요청이 실패하였습니다(2).  <br>");
             * resultMsg.append( "TX 결제요청 Response_code = " + xpay.m_szResCode + "<br>");
             * resultMsg.append( "TX 결제요청 Response_msg = " + xpay.m_szResMsg + "<p>");
             * //최종결제요청 결과 실패 DB처리
             * }
             * }
             */
        }
        // 문자 전송
        if (isDBOK) {
            LoginVO loginVO = commandMap.getUserInfo();

            if (list != null && list.size() > 0) {
                for (EdcRsvnMstVO vo : ((List<EdcRsvnMstVO>) list)) {
                    try {
                        Map<String, String> smsParam = new HashMap<String, String>();
                        smsParam.put("msgcd", "4");
                        smsParam.put("msgno", "44");
                        smsParam.put("sndHp", vo.getGuideTelno());
                        smsParam.put("hp", vo.getEdcReqMoblphon());
                        smsParam.put("예약자명", vo.getEdcReqCustnm());
                        smsParam.put("교육프로그램명", vo.getEdcPrgmnm());
                        smsParam.put("문의전화", vo.getGuideTelno());
                        smsParam.put("결제금액", CommonUtil.AddComma(vo.getEdcTotamt()) + "원");
                        if (vo.getFamilyCnt() > 0) {
                            smsParam.put("참여인원", vo.getFamilyCnt() + "명");
                        } else {
                            smsParam.put("참여인원", vo.getEdcVistnmpr() + "명");
                        }

                        smsParam.put("예약번호", vo.getEdcRsvnno());
                        String period = CommonUtil.EduPeriodDate(vo);

                        String strEdcProgmType = vo.getEdcProgmType();

                        if (strEdcProgmType.equals("1001")) {
                            period = period + "\n▶ 교육요일 : " + vo.getEdcDaygbnNm();
                        }

                        commandMap.put("COMCD", Config.COM_CD);
                        commandMap.put("PART_CD", vo.getEdcPartcd());

                        smsParam.put("교육기간", period);
                        smsParam.put("교육장소", vo.getEdcPlacenm());
                        smsParam.put("문의전화", vo.getGuideTelno());
                        smsParam.put("환불규정", chargeService.selectRefundRule(commandMap));

                        if (loginVO == null) {
                            loginVO = new LoginVO();
                            loginVO.setUniqId(vo.getEdcRsvnMemno());
                            loginVO.setName(vo.getEdcRsvnCustnm());
                        }

                        smsService.sendMessage(smsParam, loginVO);
                    } catch (Exception e) {
                        log.error("교육 결제완료 SMS 전송 오류 :" + e.getMessage());
                    }
                }
            }

        }

        model.addAttribute("resultUrl", Config.USER_ROOT + "/mypage/myRsvn/myRsvnResult");
        model.addAttribute("resultMsg", resultMsg.toString());
        model.addAttribute("resultFlag", isDBOK);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 상품 결제 결과 페이지
     *
     * @param
     * @return callback page
     * @exception Exception
     */
    @RequestMapping(value = "/myRsvnResult")
    public String myRsvnResult(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO) throws Exception {
        myRsvnVO.setComcd(Config.COM_CD);
        myRsvnVO.setOrderId(myRsvnVO.getLgdOID());
        model.addAttribute("pgData", myRsvnService.selectCardAppHistData(myRsvnVO));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 결제 or 예약 상세 페이지
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/myRsvnDetailX")
    public String selecRsvnDetailX(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        myRsvnVO.setComcd(Config.COM_CD);
        // myRsvnVO.setUniqId(commandMap.);
        Map payInfo = myRsvnService.selectCardAppHistData(myRsvnVO);
        model.addAttribute("payInfo", payInfo);

        String cstPLATFORM = "service";
        String mid = CommonUtil.getString(payInfo.get("mid"));
        if (mid.startsWith("t")) {
            mid = mid.substring(1, mid.length()); // 테스트 모드이면 t를 뺀다
            cstPLATFORM = "test";
        }
        myRsvnVO.setCstPLATFORM(cstPLATFORM);

        try {
            String lgdMERTKEY = EgovProperties.getProperty("tosspayments.mertkey." + mid); // 상점키

            StringBuffer sb = new StringBuffer();
            sb.append(CommonUtil.getString(payInfo.get("mid")));
            sb.append(CommonUtil.getString(payInfo.get("tid")));
            sb.append(lgdMERTKEY);

            byte[] bNoti = sb.toString().getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bNoti);

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                int c = digest[i] & 0xff;
                if (c <= 15) {
                    strBuf.append("0");
                }
                strBuf.append(Integer.toHexString(c));
            }

            String lgdHASHDATA = strBuf.toString();
            model.addAttribute("LgdHASHDATA", lgdHASHDATA);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 결제 결과 목록 include
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = { "/incPayDetail" })
    public String selecPayDetail(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        LoginVO loginVO = commandMap.getUserInfo();
        if (loginVO != null) {
            myRsvnVO.setUniqId(loginVO.getUniqId());
            myRsvnVO.setHpcertno(loginVO.getHpcertno());
        }

        String gubun = commandMap.getString("gubun");
        myRsvnVO.setMode("complete");
        myRsvnVO.setComcd(Config.COM_CD);

        int cnt = 0;

        List<EdcRsvnInfoVO> list = myRsvnService.selectEdcPaylist(myRsvnVO, commandMap.getString("appGbn"));
        model.addAttribute("list", list);

        cnt = (list == null ? 0 : list.size());

        if (cnt < 1) {
            HttpUtility.sendRedirect(request, response, "해당 내역이 존재하지 않습니다.", Config.USER_ROOT + "/mypage/myRsvn/myRsvnCompList?gubun=" + gubun);
            return null;
        }
        model.addAttribute("appGbn", commandMap.getString("appGbn"));
        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/myRsvn/" + gubun + "/incPayDetail");
    }

    /**
     * 취소요청목록 확인
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = { "/cancelDetailAjax" })
    public String cancelDetailAjax(@ModelAttribute("cancelVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        LoginVO loginVO = commandMap.getUserInfo();
        if (loginVO != null) {
            myRsvnVO.setUniqId(loginVO.getUniqId());
            myRsvnVO.setHpcertno(loginVO.getHpcertno());
        }

        Map payInfo = myRsvnService.selectCardAppHistData(myRsvnVO);

        String gubun = commandMap.getString("gubun");
        myRsvnVO.setMode("complete");
        myRsvnVO.setComcd(Config.COM_CD);

        myRsvnVO.setRsvnIdx(Arrays.asList(commandMap.getString("rsvnIdx").split("[,]")));

        List<EdcRsvnInfoVO> list = myRsvnService.selectEdcPaylist(myRsvnVO, CommonUtil.getString(payInfo.get("appGbn")));
        model.addAttribute("list", list);

        model.addAttribute("payInfo", payInfo);
        model.addAttribute("reasonList", codeService.selectCodeList("CM_RET_REASON"));

        model.addAttribute("cancelVO", myRsvnVO);
        return HttpUtility.getViewUrl(request);
    }

    /**
     * 결제 취소
     *
     * @param
     * @return result - 결과
     * @exception Exception
     */
    @PostMapping(value = "/payCancel")
    @ResponseBody
    public ModelAndView payCancel(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();
        LoginVO loginVO = commandMap.getUserInfo();

        if (loginVO == null) {
            resultInfo.setSuccess(false);
            resultInfo.setMsg("로그인 정보가 없습니다.");
        } else {
            if (loginVO != null) {
                myRsvnVO.setUniqId(loginVO.getUniqId());
                myRsvnVO.setHpcertno(loginVO.getHpcertno());
                myRsvnVO.setModuser(loginVO.getId());
            }
            myRsvnVO.setComcd(Config.COM_CD);
            myRsvnVO.setRsvnNoList(Arrays.asList(commandMap.getString("rsvnNo").split(",")));

            Map<String, Object> payInfo = myRsvnService.selectCardAppHistData(myRsvnVO);

            // 결제 금액 호출
            Map<String, Object> payAmtInfo = new HashMap<String, Object>();

            if ("EVT".equals(myRsvnVO.getGubun())) {
                myRsvnVO.setPartGbn("3001");
                payAmtInfo = myRsvnService.selectEvtCancelInfo(myRsvnVO, CommonUtil.getString(payInfo.get("appGbn")));
            } else if ("EXBT".equals(myRsvnVO.getGubun())) {
                myRsvnVO.setPartGbn("1001");
                payAmtInfo = myRsvnService.selectExbtCancelInfo(myRsvnVO, CommonUtil.getString(payInfo.get("appGbn")));
            } else if ("EDC".equals(myRsvnVO.getGubun())) {
                myRsvnVO.setPartGbn("2001");
                // payAmtInfo = myRsvnService.selectEdcCancelInfo(myRsvnVO,
                // CommonUtil.getString(payInfo.get("appGbn")));
            }

            if (CommonUtil.getString(payAmtInfo.get("totCnt")).equals(CommonUtil.getString(payAmtInfo.get("cancelCnt")))) {
                myRsvnVO.setCancelType("F"); // 취소완료
            } else {
                myRsvnVO.setCancelType("P"); // 부분취소
            }

            long cancelAmt = myRsvnVO.getCancelAmt(); // 취소할금액
            int cancelCnt = myRsvnVO.getRsvnNoList().size(); // 취소 할 건수

            StringBuffer resultMsg = new StringBuffer();

            if (cancelCnt != CommonUtil.getInt(payAmtInfo.get("validCnt"))) {
                resultMsg.append("취소가 완료되었거나 취소불가능한 예약건이 있습니다. 새로고침 하셔서 취소 상태를 다시 확인해 주세요.");
                resultInfo.setSuccess(false);
                resultInfo.setMsg(resultMsg.toString());

            } else if (cancelAmt != CommonUtil.getLong(payAmtInfo.get("cancelAmt"))) {
                resultMsg.append("취소 금액이 맞지 않습니다. 새로고침 하셔서 취소 가능 예약을 다시 확인해 주세요.");
                resultInfo.setSuccess(false);
                resultInfo.setMsg(resultMsg.toString());

            } else if (cancelAmt == 0) {
                resultMsg.append("취소 금액이 0원 입니다. 새로고침 하셔서 취소 가능 예약을 다시 확인해 주세요.");
                resultInfo.setSuccess(false);
                resultInfo.setMsg(resultMsg.toString());
            } else {

                /*
                 * [결제취소 요청 페이지]
                 * 매뉴얼 "6. 결제 취소를 위한 개발사항(API)"의 "단계 3. 결제 취소 요청 및 요청 결과 처리" 참고
                 * LG유플러스으로 부터 내려받은 거래번호(LgdTID)를 가지고 취소 요청을 합니다.(파라미터 전달시 POST를 사용하세요)
                 * (승인시 LG유플러스으로 부터 내려받은 PAYKEY와 혼동하지 마세요.)
                 */

                String appGbn = CommonUtil.getString(payInfo.get("appGbn"));
                String lgdMID = String.valueOf(payInfo.get("mid")); // 테스트 아이디는 't'를 제외하고 입력하세요.
                String lgdTID = String.valueOf(payInfo.get("tid")); // LG유플러스으로 부터 내려받은 거래번호(LgdTID)
                String cstPLATFORM = lgdMID.startsWith("t") ? "test" : "service"; // LG유플러스 결제서비스 선택(test:테스트,
                                                                                  // service:서비스)
                String configPath = FileMngUtil.GetRealRootPath().concat("WEB-INF/lgdacom");// LG유플러스에서 제공한
                                                                                            // 환경파일("/conf/lgdacom.conf")
                                                                                            // 위치 지정.
                String lgdPcancelCnt = CommonUtil.getString(payInfo.get("pcancelNo")); // 부분취소 seq 기존 번호가 있을 경우
                int pcancelCnt = CommonUtil.getInt(payInfo.get("pcancelCnt")); // 기존 부분취소 갯수

                lgdTID = (lgdTID == null) ? "" : lgdTID;

                // 기존 취소 실패 건이 존재하고 취소 건수가 맞지 않을 경우
                if (!lgdPcancelCnt.equals("") && pcancelCnt != myRsvnVO.getRsvnIdx().size()) {
                    resultInfo.setSuccess(false);

                    if (pcancelCnt == 1) {
                        resultInfo.setMsg("취소 오류 건이 존재합니다. 개별 취소만 가능합니다. 같은 오류 반복시 고객센터에 문의해 주세요.");
                    } else {
                        resultInfo.setMsg("취소 오류 건이 존재합니다. 전체 취소만 가능합니다.  같은 오류 반복시 고객센터에 문의해 주세요.");
                    }

                } else {

                    /*
                     * // (1) XpayClient의 사용을 위한 xpay 객체 생성
                     * XPayClient xpay = new XPayClient();
                     * // (2) Init: XPayClient 초기화(환경설정 파일 로드)
                     * // configPath: 설정파일
                     * // CstPLATFORM: - test, service 값에 따라 lgdacom.conf의 test_url(test) 또는 url(srvice) 사용
                     * // - test, service 값에 따라 테스트용 또는 서비스용 아이디 생성
                     * xpay.Init(configPath, cstPLATFORM);
                     * // (3) Init_TX: 메모리에 mall.conf, lgdacom.conf 할당 및 트랜잭션의 고유한 키 TXID 생성
                     * xpay.Init_TX(lgdMID);
                     * //남아있는 결제금액 ( 부분취소 되었을 경우 대비)
                     * int appAmt = CommonUtil.getInt(payInfo.get("appAmt")) -
                     * CommonUtil.getInt(payInfo.get("cancelAmt2"));
                     * if (CommonUtil.getInt(payInfo.get("cancelAmt2")) > 0 || "P".equals(myRsvnVO.getCancelType())) {
                     * myRsvnVO.setPartialYn("Y"); // 기존 부분환불 내역이 있을 경우
                     * } else {
                     * myRsvnVO.setPartialYn("N");
                     * }
                     * myRsvnVO.setAppDate(String.valueOf(payInfo.get("appDate"))); // 승인날짜
                     * myRsvnVO.setCancelAmt(cancelAmt); // 취소금액
                     * myRsvnVO.setIp(commandMap.getIp());
                     * myRsvnVO.setLgdOID(myRsvnVO.getOrderId());
                     * myRsvnVO.setOldPcancelNo(lgdPcancelCnt); //기취소 SEQ
                     * if (request.getAttribute("IS_MOBILE") == null) {
                     * myRsvnVO.setTerminalType("2001");
                     * } else {
                     * boolean isMobile = (boolean)request.getAttribute("IS_MOBILE");
                     * myRsvnVO.setTerminalType(isMobile?"2002":"2001");
                     * }
                     * //기존 번호가 없을 경우
                     * if (lgdPcancelCnt.equals("")) {
                     * lgdPcancelCnt = CommonUtil.getString(payInfo.get("pcancelNoMax")) ; //취소 seq
                     * }
                     * myRsvnVO.setPcancelNo(lgdPcancelCnt);
                     * if ((myRsvnVO.getPartialYn().equals("N") && cancelAmt >= appAmt) || appGbn.equals("2")) {
                     * xpay.Set("LgdTXNAME", "Cancel");
                     * log.debug("-------------------전체 취소--------------------");
                     * } else {
                     * //부분 취소
                     * myRsvnVO.setPartialYn("Y");
                     * xpay.Set("LgdTXNAME", "PartialCancel");
                     * xpay.Set("LgdCANCELAMOUNT",String.valueOf(cancelAmt));
                     * xpay.Set("LgdREMAINAMOUNT", String.valueOf(appAmt));
                     * xpay.Set("LgdPCANCELCNT", lgdPcancelCnt); //부분환불 요청 SEQ
                     * log.debug("-------------------부분 취소--------------------");
                     * log.debug("-------------cancelAmt------" +String.valueOf(cancelAmt)+
                     * "--------------------");
                     * log.debug("-------------appAmt------" +String.valueOf(appAmt)+ "--------------------");
                     * log.debug("-------------lgdPcancelCnt------" +lgdPcancelCnt+ "--------------------");
                     * log.debug("-------------------부분 취소--------------------");
                     * }
                     * xpay.Set("LgdTID", lgdTID);
                     * //
                     * // 1. 결제취소 요청 결과처리
                     * //
                     * // 취소결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
                     * // * [[[중요]]] 고객사에서 정상취소 처리해야할 응답코드
                     * // 1. 신용카드 : 0000, AV11
                     * // 2. 계좌이체 : 0000, RF00, RF10, RF09, RF15, RF19, RF23, RF25 (환불진행중 응답건-> 환불결과코드.xls 참고)
                     * // 3. 나머지 결제수단의 경우 0000(성공) 만 취소성공 처리
                     * //
                     * //
                     * // (4) TX: lgdacom.conf에 설정된 URL로 소켓 통신하여 최종 인증요청, 결과값으로 true, false 리턴
                     * if (xpay.TX()) {
                     * // (5) 결제취소요청 결과 처리
                     * //1)결제취소결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
                     * for (int i = 0; i < xpay.ResponseNameCount(); i++)
                     * {
                     * log.debug(xpay.ResponseName(i) + " = ");
                     * for (int j = 0; j < xpay.ResponseCount(); j++)
                     * {
                     * log.debug("\t" + xpay.Response(xpay.ResponseName(i), j) + "<br>");
                     * }
                     * }
                     * String resCode = xpay.m_szResCode;
                     * if
                     * (resCode.equals("0000")||resCode.equals("S020")||resCode.equals("AV11")||resCode.equals("RF00")||
                     * resCode.equals("RF10")||resCode.equals("RF09")||resCode.equals("RF15")||resCode.equals("RF19")||
                     * resCode.equals("RF23")||resCode.equals("RF25")) {
                     * int n = 0;
                     * try {
                     * //myRsvnVO.setCancelAppNo(xpay.Response("LgdPARTIALCANCEL_SEQNO_SUB", 0));
                     * myRsvnVO.setCancelDate(xpay.Response("LgdCANREQDATE", 0) ); // 취소날짜
                     * n = myRsvnService.updateCardAppCancel(myRsvnVO);
                     * if ( n > 0) {
                     * String cancelDate = CommonUtil.getString(payInfo.get("cancelDate"));
                     * if (myRsvnVO.getPartialYn().equals("N")) {
                     * myRsvnVO.setCancelDate( CommonUtil.getString(payInfo.get("appDate")) );
                     * } else if (!cancelDate.equals("") ) {
                     * myRsvnVO.setCancelDate(cancelDate ); // 취소날짜
                     * } else if (myRsvnVO.getCancelDate() == null || myRsvnVO.getCancelDate().equals("")) {
                     * myRsvnVO.setCancelDate( CommonUtil.getString(payInfo.get("appDate")) );
                     * }
                     * // 기 승인번호
                     * if (myRsvnVO.getCancelAppNo() == null || myRsvnVO.getCancelAppNo().equals("")) {
                     * myRsvnVO.setCancelAppNo(String.valueOf(payInfo.get("cancelAppno")) ); // 취소번호
                     * }
                     * //노원은 PG가 없으므로 막음 2021.03.17
                     * //n = myRsvnService.updatePayCancel(myRsvnVO);
                     * //log.debug("================msg:" +myRsvnVO.getRetMsg());
                     * //if (!"OK".equals(myRsvnVO.getRetCd())) {
                     * //resultMsg.append( "결제취소 오류 코드 : " +myRsvnVO.getRetCd() + "\n");
                     * //resultMsg.append( "결제취소 오류 메시지 : " +myRsvnVO.getRetMsg());
                     * //}
                     * } else {
                     * resultMsg.append( "결제취소 오류.");
                     * }
                     * } catch (Exception e) {
                     * resultMsg.append( "결제취소 오류 코드 : " +myRsvnVO.getRetCd() + "<br>");
                     * resultMsg.append( "결제취소 오류 메시지 : " +myRsvnVO.getRetMsg() + "<br>");
                     * n = 0;
                     * }finally{
                     * try {
                     * chargeService.insertCancelDbprocLog(myRsvnVO);
                     * } catch (Exception e) {
                     * e.printStackTrace();
                     * }
                     * //부분 취소 SEQ 업데이트
                     * if (!lgdPcancelCnt.equals("")) {
                     * try {
                     * chargeService.updatePartialNo(myRsvnVO);
                     * } catch (Exception e) {
                     * e.printStackTrace();
                     * }
                     * }
                     * }
                     * if (n > 0) {
                     * resultInfo.setSuccess(true);
                     * resultInfo.setMsg("예약 취소가 완료되었습니다.");
                     * } else {
                     * resultInfo.setSuccess(false);
                     * resultInfo.setMsg(resultMsg.toString());
                     * }
                     * } else {
                     * resultInfo.setSuccess(false);
                     * resultInfo.setMsg("결제 취소요청이 실패하였습니다 ( TX Response_msg : " +
                     * xpay.m_szResMsg+" , resCode: "+resCode+" )");
                     * }
                     * } else {
                     * //2)API 요청 실패 화면처리
                     * //out.println( "TX Response_code = " + xpay.m_szResCode + "<br>");
                     * //out.println( "TX Response_msg = " + xpay.m_szResMsg + "<p>");
                     * resultInfo.setSuccess(false);
                     * resultInfo.setMsg("결제 취소요청이 실패하였습니다 ( TX Response_msg : " + xpay.m_szResMsg+" )");
                     * }
                     */
                }

            }
        }

        if (resultInfo.isSuccess()) {
            // 문자는 오류가 나더라도 패스 한다
            try {
                String gubun = commandMap.getString("gubun");
                myRsvnVO.setMode("complete");
                myRsvnVO.setComcd(Config.COM_CD);
                myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

                List<EdcRsvnInfoVO> list = myRsvnService.selectEdcPaylist(myRsvnVO, "1");
                if (list != null && list.size() > 0) {
                    for (EdcRsvnInfoVO vo : ((List<EdcRsvnInfoVO>) list)) {
                        try {
                            Map<String, String> smsParam = new HashMap<String, String>();
                            smsParam.put("msgcd", "4");
                            smsParam.put("msgno", "41");
                            smsParam.put("sndHp", vo.getGuideTelno());
                            smsParam.put("예약일시", CommonUtil.getDateString(vo.getRegdate()));
                            smsParam.put("hp", vo.getEdcReqMoblphon());
                            smsParam.put("예약자명", vo.getEdcReqCustnm());
                            smsParam.put("교육프로그램명", vo.getEdcPrgmnm());
                            smsParam.put("결제금액", CommonUtil.AddComma(vo.getEdcTotamt()) + "원");
                            smsParam.put("예약번호", vo.getEdcRsvnNo());
                            smsParam.put("환불금액", CommonUtil.AddComma(vo.getCancelAmt()) + "원");
                            smsParam.put("문의전화", vo.getGuideTelno());

                            smsService.sendMessage(smsParam, loginVO);
                        } catch (Exception e) {
                            log.error("교육 결제취소 SMS 전송 오류 :" + e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 할인 사유 선택 ajax
     *
     * @param commandMap
     * @return String - jsp 페이지
     * @exception Exception
     */
    @PostMapping(value = "/myRsvnCouponAjax")
    public String myRsvnCouponAjax(@ModelAttribute("couponForm") MyRsvnVO couponForm, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        String strGubun = commandMap.getString("gubun");

        LoginVO userVO = commandMap.getUserInfo();
        couponForm.setComcd(userVO.getComcd());
        couponForm.setUniqId(userVO.getUniqId());

        CamelMap data = rsvnCommService.selectReserveData(couponForm);

        if (data != null) {
            couponForm.setPartCd(CommonUtil.getString(data.get("partcd")));
            couponForm.setProgramCd(CommonUtil.getString(data.get("programCd")));
            couponForm.setYmd(CommonUtil.getString(data.get("ymd")));
            couponForm.setTarget(CommonUtil.getString(data.get("target")));
            couponForm.setAppStatus(CommonUtil.getString(data.get("appStatus")));
        }
        // 이벤트 할인 여부 조회
        commandMap.put("COMCD", Config.COM_CD);
        commandMap.put("YMD", couponForm.getYmd());
        commandMap.put("PART_CD", couponForm.getPartCd());
        commandMap.put("PGM_CD", couponForm.getProgramCd());
        commandMap.put("PGM_GUBUN", strGubun);

        List<RsvnCommVO> discList = rsvnCommService.selectEventStdmngList(commandMap.getParam());
        List<RsvnCommVO> newDiscList = new ArrayList<RsvnCommVO>();

        if (discList != null) {
            for (RsvnCommVO discVO : discList) {
                if (userVO.isMember() && "Y".equals(discVO.getMemberyn())) {
                    newDiscList.add(discVO);
                } else if (!userVO.isMember() && "Y".equals(discVO.getNonmebyn())) {
                    newDiscList.add(discVO);
                }
            }
        }

        // 연간회원 or 특별회원 인 경우 할인 적용여부를 조회한다
        if ("Y".equals(userVO.getYearYn()) || "Y".equals(userVO.getSpecialYn())) {
            if ("Y".equals(userVO.getYearYn())) {
                couponForm.setMemberGbn("1"); // 연간회원
            } else {
                couponForm.setMemberGbn("2"); // 특별회원
            }
            RsvnCommVO annualData = rsvnCommService.selectAnnualDcData(couponForm);
            model.addAttribute("annualData", annualData);
        }

        if ("Y".equals((String) data.get("cpnYn"))) {
            // 쿠폰 목록 가져온다
            model.addAttribute("couponList", rsvnCommService.selectCouponList(couponForm));
        }
        model.addAttribute("discList", newDiscList);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 쿠폰 및 기타 할인을 적용한다
     *
     * @param commandMap
     * @return ModelAndView
     * @exception Exception
     */
    @RequestMapping(value = "myRsvnCouponSave")
    @ResponseBody
    public ModelAndView myRsvnCouponSave(@ModelAttribute("couponForm") MyRsvnVO couponForm, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO userVO = commandMap.getUserInfo();
        ResultInfo resultInfo = new ResultInfo();
        couponForm.setComcd(Config.COM_CD);
        couponForm.setUniqId(userVO.getUniqId());

        if (request.getAttribute("IS_MOBILE") == null) {
            couponForm.setTerminalType("2001");
        } else {
            boolean isMobile = (boolean) request.getAttribute("IS_MOBILE");
            couponForm.setTerminalType(isMobile ? "2002" : "2001");
        }

        String returnMsg = "";
        if ("EXBT".equals(couponForm.getGubun())) {
            returnMsg = rsvnCommService.insertExbtRsvnItem(couponForm);
        } else if ("EVT".equals(couponForm.getGubun())) {
            returnMsg = rsvnCommService.insertEvtRsvnItem(couponForm);
        } else if ("EDC".equals(couponForm.getGubun())) {
            returnMsg = rsvnCommService.insertEdcRsvnItem(couponForm, userVO);
        }
        resultInfo.setMsg(returnMsg);
        resultInfo.setSuccess(true);
        mav.addObject("result", resultInfo);

        return mav;
    }

    /**
     * 쿠폰 및 기타 할인을 취소한다
     *
     * @param commandMap
     * @return ModelAndView
     * @exception Exception
     */
    @RequestMapping(value = "myRsvnCouponCancel")
    @ResponseBody
    public ModelAndView myRsvnCouponCancel(@ModelAttribute("couponForm") MyRsvnVO couponForm, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();
        couponForm.setComcd(Config.COM_CD);
        if (request.getAttribute("IS_MOBILE") == null) {
            couponForm.setTerminalType("2001");
        } else {
            boolean isMobile = (boolean) request.getAttribute("IS_MOBILE");
            couponForm.setTerminalType(isMobile ? "2002" : "2001");
        }

        CamelMap data = rsvnCommService.selectReserveData(couponForm);

        if (data != null) {
            couponForm.setPartCd(CommonUtil.getString(data.get("partcd")));
            couponForm.setProgramCd(CommonUtil.getString(data.get("programCd")));
            couponForm.setYmd(CommonUtil.getString(data.get("ymd")));
            couponForm.setTarget(CommonUtil.getString(data.get("target")));
            couponForm.setAppStatus(CommonUtil.getString(data.get("appStatus")));
        }

        // 이벤트 할인 여부 조회
        commandMap.put("COMCD", Config.COM_CD);
        commandMap.put("YMD", couponForm.getYmd());
        commandMap.put("PART_CD", couponForm.getPartCd());
        commandMap.put("PGM_CD", couponForm.getProgramCd());
        commandMap.put("PGM_GUBUN", commandMap.getString("gubun"));

        LoginVO userVO = commandMap.getUserInfo();
        couponForm.setUniqId(userVO.getUniqId());

        List<RsvnCommVO> discList = rsvnCommService.selectEventStdmngList(commandMap.getParam());

        // 할인 조회
        RsvnCommVO eventDcInfo = null;

        if (discList != null) {
            for (RsvnCommVO discVO : discList) {
                if (userVO.isMember() && "Y".equals(discVO.getMemberyn())) {
                    if (eventDcInfo == null || eventDcInfo.getDcRate() > discVO.getDcRate()) {
                        eventDcInfo = discVO;
                    }
                } else if (!userVO.isMember() && "Y".equals(discVO.getNonmebyn())) {
                    if (eventDcInfo == null || eventDcInfo.getDcRate() > discVO.getDcRate()) {
                        eventDcInfo = discVO;
                    }
                }
            }
        }
        String annualLimitYn = "";
        // 연간회원 or 특별회원 인 경우 할인 적용여부를 조회한다
        if ("Y".equals(userVO.getYearYn()) || "Y".equals(userVO.getSpecialYn())) {
            if ("Y".equals(userVO.getYearYn())) {
                couponForm.setMemberGbn("1"); // 연간회원
            } else {
                couponForm.setMemberGbn("2"); // 특별회원
            }
            RsvnCommVO annualData = rsvnCommService.selectAnnualDcData(couponForm);
            if (annualData != null && annualData.getLimitQty() == 0) {
                if (annualData.getLimitCnt() == 0 || (annualData.getLimitCnt() > 0 && annualData.getLimitCnt() > annualData.getApplyCnt())) {
                    annualLimitYn = "N";
                }
            }
        }

        if ("EXBT".equals(couponForm.getGubun())) {
            rsvnCommService.deleteExbtRsvnItem(couponForm, eventDcInfo, annualLimitYn);
        } else if ("EVT".equals(couponForm.getGubun())) {
            rsvnCommService.deleteEvtRsvnItem(couponForm, eventDcInfo, annualLimitYn);
        } else if ("EDC".equals(couponForm.getGubun())) {
            rsvnCommService.deleteEdcRsvnItem(couponForm, eventDcInfo);
        }

        resultInfo.setSuccess(true);
        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 관람 예약확인증
     *
     * @param commandMap
     * @return ModelAndView
     * @exception Exception
     */
    @RequestMapping(value = "/EXBT/exbtPop")
    public String myRsvnExbtPop(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        // 관람
        RsvnMasterVO rsvnMasterVO = new RsvnMasterVO();
        rsvnMasterVO.setRsvnIdx(commandMap.getString("rsvnIdx"));
        rsvnMasterVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        RsvnMasterVO resultVO = dspyDsService.selectRegistMst(rsvnMasterVO);
        resultVO.setChargeList(dspyDsService.selectRegistItemList(rsvnMasterVO));
        model.addAttribute("resultVO", resultVO);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/myRsvn/EXBT/exbtPop");
    }

    /**
     * 교육 예약증 출력 / 교육 수료증 출력 (개인)
     *
     * @param commandMap
     * @return ModelAndView
     * @exception Exception
     */
    @RequestMapping(value = { "/edcCompPopup" })
    public String myRsvnEdcPop(MyRsvnVO myRsvnVO, CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        LoginVO loginVO = commandMap.getUserInfo();
        myRsvnVO.setEdcRsvnMemno(loginVO.getUniqId());
        // 비회원용
        myRsvnVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
        myRsvnVO.setMemNm(loginVO.getName());
        myRsvnVO.setEdcRsvnBirthdate(loginVO.getBirthDate());
        myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        model.addAttribute("resultVO", myRsvnService.selectMyEdcRsvnDtl(myRsvnVO));
        return HttpUtility.getViewUrl(request);
    }

    /**
     * 교육예약정보 수정
     *
     * @param commandMap
     * @return String - jsp 페이지
     * @exception Exception
     */
    @GetMapping(value = "/myRsvnEdcModify")
    public String myRsvnEdcModify(MyRsvnVO myRsvnVO, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {

        EdcRsvnInfoVO resultVO = myRsvnService.selectMyEdcRsvnDtl(myRsvnVO);
        if (resultVO != null) {
            if (Config.NO.equals(resultVO.getEditYn())) {
                HttpUtility.sendBack(request, response, "교육 시작일 전날 까지 수정할 수 있습니다.");
                return null;
            }
        }

        /*
         * List<?> itemList = edcRsvnService.selectProgmMngiteminfoList2(commandMap.getParam());
         * model.addAttribute("edcarsvnProgMItemCnt", itemList != null ? itemList.size() : 0);
         * model.addAttribute("detailVO", resultVO);
         * model.addAttribute("email", commandMap.getUserInfo().getEmail());
         * model.addAttribute("edcarsvnFamlyinfoList",
         * edcRsvnService.selectEdcRsvnFamlyinfoList(commandMap.getParam()));
         * model.addAttribute("itemList", itemList);
         */

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 교육 예약 등록 관련 정보를 수정한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnEdcSave", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView selectEdcarsvnRegSave(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {
        paramMap.put("comCd", Config.COM_CD);
        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();
        LoginVO loginUser = commandMap.getUserInfo();
        if (loginUser == null) {
            throw new MyException("로그인 정보를 찾을 수 없습니다.", -3);
        }

        paramMap.put("uniqId", commandMap.getUserInfo().getUniqId());
        paramMap.put("modUser", loginUser.getId());
        paramMap.put("edcNonmembCertno", loginUser.getHpcertno());
        paramMap.put("dbEncKey", EgovProperties.getProperty("Globals.DbEncKey"));

        int intEdcRsvnReqid = edcRsvnService.saveEduRsvnModify(paramMap, commandMap);
        finalResult.put("EXE_YN", "Y");
        finalResult.put("RSVN_REQ_ID", intEdcRsvnReqid);
        finalResult.put("DB_DATA", "{DB_YN : Y}");
        finalResult.put("DB_DROW", 1);

        mav.addObject("RESULT", finalResult);

        return mav;

    }

    /**
     * 교육 예약 등록 관련 인원을 수정한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnEdcNumSave", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView myRsvnEdcNumSave(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {
        paramMap.put("comCd", Config.COM_CD);
        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();
        LoginVO loginUser = commandMap.getUserInfo();
        if (loginUser == null) {
            throw new MyException("로그인 정보를 찾을 수 없습니다.", -3);
        }

        paramMap.put("uniqId", commandMap.getUserInfo().getUniqId());
        paramMap.put("modUser", loginUser.getId());
        paramMap.put("edcNonmembCertno", loginUser.getHpcertno());

        EdcRsvnInfoVO rsvnInfoParam = new EdcRsvnInfoVO();
        // int intEdcRsvnReqid = edcRsvnService.updateRsvnInfoPerson(paramMap, commandMap);
        int intEdcRsvnReqid = edcRsvnService.updateRsvnInfoPerson(rsvnInfoParam);
        finalResult.put("EXE_YN", "Y");
        finalResult.put("NUM_SAVE", "Y");
        finalResult.put("RSVN_REQ_ID", intEdcRsvnReqid);
        finalResult.put("DB_DATA", "{DB_YN : Y}");
        finalResult.put("DB_DROW", 1);

        mav.addObject("RESULT", finalResult);

        return mav;

    }

    /**
     * 교육예약정보 수정
     *
     * @param commandMap
     * @return String - jsp 페이지
     * @exception Exception
     */
    @GetMapping(value = "/myRsvnExbtModify")
    public String myRsvnExbtModify(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {
        // 관람
        commandMap.put("comcd", Config.COM_CD);
        RsvnMasterVO rsvnMasterVO = new RsvnMasterVO();
        rsvnMasterVO.setRsvnIdx(commandMap.getString("rsvnIdx"));
        rsvnMasterVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        RsvnMasterVO resultVO = dspyDsService.selectRegistMst(rsvnMasterVO);
        commandMap.put("exbtSeq", resultVO.getExbtSeq());
        commandMap.put("target", resultVO.getTarget());
        List<ExbtChargeVO> chargeList = dspyDsService.selectExbtChargeList(commandMap.getParam());

        LoginVO userVO = commandMap.getUserInfo();

        // 이벤트 할인 여부 조회
        commandMap.put("COMCD", Config.COM_CD);
        commandMap.put("YMD", resultVO.getYmd());
        commandMap.put("PART_CD", resultVO.getExbtPartcd());
        commandMap.put("PGM_CD", resultVO.getExbtSeq());
        commandMap.put("PGM_GUBUN", "EXBT");

        RsvnCommVO discAnnualVO = null;
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

        // 유료회원
        /*
         * if (userVO.isMember() ) {
         * userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
         * MemberVO memberVO = myInforService.selectMemberData(userVO);
         * if (memberVO.getSpecialYn().equals("Y") || memberVO.getYearYn().equals("Y")) {
         * MyRsvnVO rsvnVO = new MyRsvnVO();
         * rsvnVO.setComcd(Config.COM_CD);
         * rsvnVO.setUniqId(memberVO.getMemNo());
         * rsvnVO.setRsvnIdxOne("0");
         * rsvnVO.setGubun("EXBT");
         * rsvnVO.setProgramCd(resultVO.getExbtSeq());
         * if ("Y".equals(memberVO.getYearYn())) {
         * rsvnVO.setMemberGbn("1"); // 연간회원
         * } else {
         * rsvnVO.setMemberGbn("2"); // 특별회원
         * }
         * RsvnCommVO annualData = rsvnCommService.selectAnnualDcData(rsvnVO);
         * if (annualData != null && annualData.getLimitCnt() == 0 && (discInfoVO == null || discInfoVO.getEventDcRate()
         * < annualData.getEventDcRate())) {
         * //제한 없이
         * discAnnualVO = annualData;
         * resultVO.setDcAnnualLimit(annualData.getLimitQty());
         * }
         * }
         * }
         */

        CamelMap groupdisc = dspyDsService.selectGroupDiscount(resultVO);

        for (ExbtChargeVO itemVO : chargeList) {
            // 단체 할인 적용
            if (groupdisc != null && !CommonUtil.getString(groupdisc.get("dcReasonCd")).equals("") && "Y".equals(itemVO.getGroupdcYn())) {
                itemVO.setDcKindCd("6001");
                itemVO.setDcType(CommonUtil.getString(groupdisc.get("dcReasonCd")));
                itemVO.setDcRate(Long.parseLong(CommonUtil.getString(groupdisc.get("dcRate"))));
                itemVO.setDcName("단체할인");
            } else {
                if (discInfoVO != null) {
                    itemVO.setDcKindCd("4010");
                    itemVO.setDcType(discInfoVO.getDcReasonCd());
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

        resultVO.setChargeList(chargeList);
        model.addAttribute("rsvnMasterVO", resultVO);
        model.addAttribute("optData", rsvnCommService.selectOptData(commandMap.getParam())); // 금액단위 설정

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 관람 예약 데이타를 수정한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnExbtSave", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView myRsvnExbtSave(@ModelAttribute("rsvnMasterVO") RsvnMasterVO rsvnMasterVO,
            HttpServletRequest request, ModelMap model, CommandMap commandMap) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO loginUser = commandMap.getUserInfo();
        if (loginUser == null) {
            throw new MyException("로그인 정보를 찾을 수 없습니다.", -3);
        }
        rsvnMasterVO.setComcd(Config.COM_CD);
        rsvnMasterVO.setMemNo(commandMap.getUserInfo().getUniqId());
        rsvnMasterVO.setHpcertno(loginUser.getHpcertno());
        rsvnMasterVO.setReguser(loginUser.getId());
        rsvnMasterVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        int cnt = dspyDsService.updateReserveMaster(rsvnMasterVO);

        if (cnt > 0) {
            mav.addObject("RESULT", "OK");
        } else {
            mav.addObject("RESULT", "수정할 데이타가 없습니다");
        }

        return mav;

    }

    /**
     * 관람 예약 인원을 수정한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnExbtItemSave", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView myRsvnExbtItemSave(@ModelAttribute("rsvnMasterVO") RsvnMasterVO rsvnMasterVO,
            HttpServletRequest request, ModelMap model, CommandMap commandMap) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO loginUser = commandMap.getUserInfo();
        if (loginUser == null) {
            throw new MyException("로그인 정보를 찾을 수 없습니다.", -3);
        }
        rsvnMasterVO.setComcd(Config.COM_CD);
        rsvnMasterVO.setMemNo(commandMap.getUserInfo().getUniqId());
        rsvnMasterVO.setHpcertno(loginUser.getHpcertno());
        rsvnMasterVO.setReguser(loginUser.getId());
        rsvnMasterVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        MemberVO memberVO = null;

        if (loginUser.isMember()) {
            loginUser.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
            memberVO = myInforService.selectMemberData(loginUser);
        }

        String resultMsg = dspyDsService.updateReserveItem(memberVO, rsvnMasterVO);

        mav.addObject("RESULT", resultMsg);

        return mav;

    }

    /**
     * 강연/행사/영화 예약정보 수정
     *
     * @param commandMap
     * @return String - jsp 페이지
     * @exception Exception
     */
    @GetMapping(value = "/myRsvnEvtModify")
    public String myRsvnEvtModify(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {
        // 관람
        commandMap.put("comcd", Config.COM_CD);

        // 예약 정보
        EvtrsvnMstVO evtrsvnMstVO = new EvtrsvnMstVO();
        evtrsvnMstVO.setEvtRsvnIdx(commandMap.getString("evtRsvnIdx"));
        evtrsvnMstVO.setDbEnckey(EgovProperties.getProperty("Globals.DbEncKey"));
        evtrsvnMstVO.setComcd(Config.COM_CD);
        // 예약 정보
        evtrsvnMstVO = evtrsvnService.selectEvtrsvnDetail(evtrsvnMstVO);

        // 요금정보
        EvtStdmngVO tempVO = new EvtStdmngVO();
        tempVO.setComcd(Config.COM_CD);
        tempVO.setEvtNo(evtrsvnMstVO.getEvtNo());
        tempVO.setEvtRsvnIdx(evtrsvnMstVO.getEvtRsvnIdx());
        tempVO.setEvtPersnGbn(evtrsvnMstVO.getEvtPersnGbn());

        List<EvtItemAmountVO> chargeList = evtrsvnService.selectEvtChargeList(tempVO);

        LoginVO userVO = commandMap.getUserInfo();
        // 회원정보
        userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        MemberVO memberVO = myInforService.selectMemberData(userVO);

        // 이벤트 할인 여부 조회
        commandMap.put("COMCD", Config.COM_CD);
        commandMap.put("YMD", evtrsvnMstVO.getEvtVeingdate());
        commandMap.put("PART_CD", evtrsvnMstVO.getEvtPartcd());
        commandMap.put("PGM_CD", evtrsvnMstVO.getEvtNo());
        commandMap.put("PGM_GUBUN", "EVT");

        RsvnCommVO discAnnualVO = null;
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

        // 유료회원
        /*
         * if (memberVO != null) {
         * if (memberVO.getSpecialYn().equals("Y") || memberVO.getYearYn().equals("Y")) {
         * MyRsvnVO rsvnVO = new MyRsvnVO();
         * rsvnVO.setComcd(Config.COM_CD);
         * rsvnVO.setUniqId(memberVO.getMemNo());
         * rsvnVO.setRsvnIdxOne("0");
         * rsvnVO.setGubun("EVT");
         * rsvnVO.setProgramCd(evtrsvnMstVO.getEvtNo());
         * if ("Y".equals(memberVO.getYearYn())) {
         * rsvnVO.setMemberGbn("1"); // 연간회원
         * } else {
         * rsvnVO.setMemberGbn("2"); // 특별회원
         * }
         * RsvnCommVO annualData = rsvnCommService.selectAnnualDcData(rsvnVO);
         * if (annualData != null && annualData.getLimitCnt() == 0 && (discInfoVO == null || discInfoVO.getEventDcRate()
         * < annualData.getEventDcRate())) {
         * //제한 없이
         * discAnnualVO = annualData;
         * evtrsvnMstVO.setDcAnnualLimit(annualData.getLimitQty());
         * }
         * }
         * }
         */
        CamelMap groupdisc = evtrsvnService.selectEvtGrpDscnt(evtrsvnMstVO);
        for (EvtItemAmountVO itemVO : chargeList) {
            // 단체 할인 적용
            if (groupdisc != null && !CommonUtil.getString(groupdisc.get("dcReasonCd")).equals("")) {
                if ("Y".equals(itemVO.getGroupdcyn())) {
                    itemVO.setDcRate(Integer.parseInt(groupdisc.get("dcRate").toString() != null
                            ? groupdisc.get("dcRate").toString() : "0"));
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

        evtrsvnMstVO.setChargeList(chargeList);
        model.addAttribute("email", userVO.getEmail());
        model.addAttribute("mstVO", evtrsvnMstVO);
        model.addAttribute("optData", rsvnCommService.selectOptData(commandMap.getParam())); // 금액단위 설정

        log.debug("end- rsvnDetailCall");

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 강연/행사/영화 예약정보를 수정한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnEvtSave", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView myRsvnEvtSave(@ModelAttribute EvtrsvnMstVO mstVO, HttpServletRequest request, ModelMap model,
            CommandMap commandMap) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        log.debug("myrsvnevtitemsave 진입");
        LoginVO loginUser = commandMap.getUserInfo();
        if (loginUser == null) {
            throw new MyException("로그인 정보를 찾을 수 없습니다.", -3);
        }
        mstVO.setComcd(Config.COM_CD);
        mstVO.setEvtRsvnMemno(commandMap.getUserInfo().getUniqId());
        mstVO.setEvtNonmembCertno(loginUser.getHpcertno());
        mstVO.setReguser(loginUser.getId());
        mstVO.setDbEnckey(EgovProperties.getProperty("Globals.DbEncKey"));

        String resultMsg = evtrsvnService.updateEvtrsvnData(mstVO);

        mav.addObject("RESULT", resultMsg);

        return mav;

    }

    /**
     * 강연/행사/영화 예약정보를 수정한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnEvtItemSave", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView myRsvnEvtItemSave(HttpServletRequest request, ModelMap model, CommandMap commandMap,
            EvtrsvnMstVO mstVO) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        log.debug("myrsvnevtitemsave 진입");
        LoginVO loginUser = commandMap.getUserInfo();
        if (loginUser == null) {
            throw new MyException("로그인 정보를 찾을 수 없습니다.", -3);
        }
        mstVO.setComcd(Config.COM_CD);
        mstVO.setEvtRsvnMemno(commandMap.getUserInfo().getUniqId());
        mstVO.setEvtNonmembCertno(loginUser.getHpcertno());
        mstVO.setReguser(loginUser.getId());
        mstVO.setDbEnckey(EgovProperties.getProperty("Globals.DbEncKey"));

        // 회원정보
        loginUser.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        MemberVO memberVO = myInforService.selectMemberData(loginUser);

        String resultMsg = evtrsvnService.updateEvtrsvnItem(memberVO, mstVO);

        mav.addObject("RESULT", resultMsg);

        return mav;

    }
}
