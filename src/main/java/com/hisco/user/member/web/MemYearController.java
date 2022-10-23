package com.hisco.user.member.web;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.intrfc.charge.service.ChargeService;
import com.hisco.intrfc.charge.vo.OrderIdVO;
import com.hisco.intrfc.sms.service.SmsService;
import com.hisco.user.member.service.MemYearService;
import com.hisco.user.member.service.UserJoinService;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.member.vo.MemberYearVO;
import com.hisco.user.mypage.service.MyInforService;
import com.hisco.user.mypage.service.RsvnCommService;
import com.hisco.user.mypage.vo.MyRsvnVO;
import com.hisco.user.mypage.vo.RsvnCommVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.uss.ion.bnr.service.BannerVO;
import egovframework.com.utl.fcc.service.EgovDateUtil;
/* import lgdacom.XPayClient.XPayClient; 2021. JYS */

/**
 * 연간회원 처리 컨트롤러
 * 
 * @author 진수진
 * @since 2020.09.10
 * @version 1.0, 2020.09.10
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.09.10 최초작성
 */

@Controller
@RequestMapping(value = Config.USER_ROOT)
public class MemYearController {

    @Resource(name = "myInforService")
    private MyInforService myInforService;

    @Resource(name = "memYearService")
    private MemYearService memYearService;

    @Resource(name = "userJoinService")
    private UserJoinService userJoinService;

    @Resource(name = "chargeService")
    private ChargeService chargeService;

    @Resource(name = "rsvnCommService")
    private RsvnCommService rsvnCommService;

    @Resource(name = "smsService")
    private SmsService smsService;

    /**
     * 연간회원 가입 안내 페이지
     * 
     * @param
     * @return callback page
     * @exception Exception
     */
    @RequestMapping("/mypage/memYear/memYearMain")
    public String memYearMain(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        LoginVO userVO = commandMap.getUserInfo();
        MemberYearVO memberYearVO = new MemberYearVO();
        memberYearVO.setMemNo(userVO.getUniqId());
        memberYearVO.setComcd(userVO.getComcd());

        MemberYearVO memberYearData = memYearService.selectMemYearData(memberYearVO);

        if (memberYearData != null) {
            memberYearVO = memberYearData;
        }
        BannerVO vo = new BannerVO();
        vo.setBannerGbn("2001"); // 연간회원 혜택
        model.addAttribute("bList", userJoinService.selectBannerList(vo));

        model.addAttribute("memberYearVO", memberYearVO);
        model.addAttribute("userVO", userVO);
        return HttpUtility.getViewUrl(request);
    }

    /**
     * 연간회원 가입 페이지
     * 
     * @param
     * @return callback page
     * @exception Exception
     */
    @RequestMapping({ "/member/join/joinYearRegist", "/mypage/memYear/memYearRegist" })
    public String memberJoinYearRegist(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @ModelAttribute("memberYearVO") MemberYearVO memberYearVO) throws Exception {
        OrderIdVO orderVO = new OrderIdVO();
        commandMap.put("comcd", Config.COM_CD);
        commandMap.put("COMCD", Config.COM_CD);
        MemberYearVO itemData = memYearService.selectProgramItemData(commandMap.getParam());

        String startYmd = memYearService.selectToday();
        memberYearVO.setComcd(Config.COM_CD);

        LoginVO userVO = commandMap.getUserInfo();
        CamelMap dscVO = null;

        RsvnCommVO discOpt = rsvnCommService.selectOptData(commandMap.getParam());

        if (userVO != null && userVO.isMember()) {
            memberYearVO.setMemNo(userVO.getUniqId());
            MemberYearVO memberYearData = memYearService.selectMemYearData(memberYearVO);

            if ("Y".equals(memberYearData.getValidYn())) {
                startYmd = EgovDateUtil.addDay(memberYearData.getEndYmd(), 1);
                memberYearVO.setValidYn(memberYearData.getValidYn());

                if (memberYearData.getRemainDate() > 30) {
                    HttpUtility.sendBack(request, response, "유료회원 만료일 기준 30일전 부터 갱신하실 수 있습니다.");
                    return null;
                }

                // 재가입시 할인 코드
                dscVO = memYearService.selectDiscountValue(memberYearVO);
            }
        } else {
            userVO = new LoginVO();
            // 파라미터로 회원코드를 받는다
            userVO.setUniqId(memberYearVO.getMemNo());
            userVO.setName(memberYearVO.getMemNm());
        }
        userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        String endYmd = EgovDateUtil.addMonth(startYmd, 12);
        String cstPLATFORM = EgovProperties.getProperty("tosspayments.platform");

        memberYearVO.setCST_PLATFORM(cstPLATFORM);

        MemberVO memberVO = myInforService.selectMemberData(userVO);

        if (memberVO != null && "0000".equals(memberVO.getStatus())) {
            memberYearVO.setMemNm(memberVO.getMemNm());
            memberYearVO.setId(memberVO.getId());
            memberYearVO.setLgdBUYER(memberVO.getMemNm());
            memberYearVO.setLgdBUYERID(memberVO.getId());
            memberYearVO.setLgdBUYEREMAIL(memberVO.getEmail());
            model.addAttribute("userInfo", memberVO);
        }

        if (itemData != null) {
            memberYearVO.setItemCd(itemData.getItemCd());
            memberYearVO.setItemNm(itemData.getItemNm());
            memberYearVO.setSaleamt(itemData.getSaleamt());
            memberYearVO.setMonthCnt(itemData.getMonthCnt());
            endYmd = EgovDateUtil.addMonth(startYmd, itemData.getMonthCnt());

            long saleAmt = itemData.getSaleamt();
            if (dscVO != null) {
                // 재가입 할인적용
                long dscAmt = CommonUtil.DoubleToLongCalc(saleAmt * Double.parseDouble(CommonUtil.getString(dscVO.get("percent"))) * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
                saleAmt = saleAmt - dscAmt;
                model.addAttribute("dscAmt", dscAmt);
            }

            memberYearVO.setLgdAMOUNT(Long.toString(saleAmt));
            memberYearVO.setLgdPRODUCTINFO(itemData.getItemNm());
            memberYearVO.setLgdPRODUCTCODE(itemData.getItemCd() + (dscVO != null ? ("|" + dscVO.get("cd")) : ""));
            memberYearVO.setCST_MID(itemData.getPaymentId());
            memberYearVO.setLgdMID(("test".equals(cstPLATFORM.trim()) ? "t" : "") + itemData.getPaymentId());

            String lgdMERTKEY = EgovProperties.getProperty("tosspayments.mertkey." + itemData.getPaymentId()); // 상점키

            String lgdTIMESTAMP = DateUtil.printDatetime(null, "yyyyMMddHHmmss");

            /* 주문번호 생성 시작 */
            orderVO.setComcd(memberYearVO.getComcd());
            orderVO.setItemCd(memberYearVO.getItemCd());
            orderVO.setUserId(memberYearVO.getId());
            orderVO.setRsvnCnt(1);
            orderVO.setRsvnAmt(Integer.parseInt(memberYearVO.getLgdAMOUNT()));

            chargeService.insertDbprocLog(orderVO);
            //// JYS 2021.05.18 chargeService.selectOrderId(orderVO);

            // OID 만들기
            String lgdOID = orderVO.getRetOid(); // 주문 번호 프로시져에서 생성
            /* 주문번호 생성 끝 */

            memberYearVO.setLgdOID(lgdOID);
            memberYearVO.setLgdTIMESTAMP(lgdTIMESTAMP);

            try {
                StringBuffer sb = new StringBuffer();
                sb.append(memberYearVO.getLgdMID());
                sb.append(memberYearVO.getLgdOID());
                sb.append(memberYearVO.getLgdAMOUNT());
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
                memberYearVO.setLgdHASHDATA(lgdHASHDATA);

            } catch (Exception e) {
                memberYearVO.setLgdTIMESTAMP("");
                memberYearVO.setLgdHASHDATA("");
            }

        }
        endYmd = EgovDateUtil.addDay(endYmd, -1);
        boolean isMobile = false;
        if (request.getAttribute("IS_MOBILE") != null) {
            isMobile = (boolean) request.getAttribute("IS_MOBILE");
        }

        String lgdReturnURL = EgovProperties.getProperty("Globals.Domain") + Config.INTRFC_ROOT + (isMobile
                ? "/charge/return/reCallNsmMbUrl" : "/charge/return/reCallNsmUrl");

        memberYearVO.setStartYmd(startYmd);
        memberYearVO.setEndYmd(endYmd);
        memberYearVO.setLgdRETURNURL(lgdReturnURL);
        memberYearVO.setLgdCASNOTEURL(EgovProperties.getProperty("Globals.Domain") + Config.INTRFC_ROOT + "/charge/return/casNoteUrl");

        model.addAttribute("CST_WINDOW_TYPE", (isMobile ? "submit" : "iframe"));
        model.addAttribute("LGD_CUSTOM_SWITCHINGTYPE", (isMobile ? "SUBMIT" : "IFRAME"));
        model.addAttribute("orderVO", orderVO);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 연간회원 가입 페이지
     * 
     * @param
     * @return callback page
     * @exception Exception
     */
    @RequestMapping("/member/join/joinYearSave")
    public String memberJoinYearSave(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("memberYearVO") MemberYearVO memberYearVO) throws Exception {
        commandMap.put("comcd", Config.COM_CD);
        commandMap.put("COMCD", Config.COM_CD);
        String productCode = commandMap.getString("LGD_PRODUCTCODE");
        String dscCode = ""; // 할인코드
        if (productCode != null && productCode.indexOf("|") > 0) {
            String[] productInfo = productCode.split("[|]");
            if (productInfo.length > 1) {
                productCode = productInfo[0];
                dscCode = productInfo[1];

                commandMap.put("LGD_PRODUCTCODE", productCode);
                commandMap.put("DSC_CODE", dscCode); // 할인코드
            }
        }
        MemberYearVO itemData = memYearService.selectProgramItemData(commandMap.getParam());
        RsvnCommVO discOpt = rsvnCommService.selectOptData(commandMap.getParam());
        MyRsvnVO rsvnVO = new MyRsvnVO();
        long saleAmt = 0;

        Map<String, String> smsParam = new HashMap<String, String>();

        smsParam.put("msgno", "15");
        smsParam.put("msgcd", "1");
        smsParam.put("sndHp", "");

        // 가격
        if (itemData != null) {
            saleAmt = itemData.getSaleamt();
            // 재가입 할인적용
            if (!dscCode.equals("")) {
                long dscAmt = CommonUtil.DoubleToLongCalc(saleAmt * Double.parseDouble(CommonUtil.getString(itemData.getDscRate())) * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
                saleAmt = saleAmt - dscAmt;
                rsvnVO.setMemYearDccd(dscCode);
                rsvnVO.setMemYearDcrate(itemData.getDscRate());
                rsvnVO.setMemYearDcamt(dscAmt);
            }
        }
        rsvnVO.setMemYearSaleamt(saleAmt);

        String configPath = FileMngUtil.GetRealRootPath().concat("WEB-INF/lgdacom"); // LG유플러스에서 제공한
                                                                                     // 환경파일("/conf/lgdacom.conf,/conf/mall.conf")
                                                                                     // 위치 지정.
        LoginVO userVO = new LoginVO();
        userVO.setId(memberYearVO.getLgdBUYERID());
        userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        MemberVO memberVO = myInforService.selectMemberData(userVO);
        String startYmd = memYearService.selectToday();
        String regdate = ""; // 가입기간 이메일발송용 YYYY-MM-DD ~ YYYY-MM-DD

        userVO.setName(memberVO.getMemNm());

        /*
         ************************************************* 1.최종결제 요청 - BEGIN
         * (단, 최종 금액체크를 원하시는 경우 금액체크 부분 주석을 제거 하시면 됩니다.)
         */
        String cstPLATFORM = EgovProperties.getProperty("tosspayments.platform");
        String lgdOID = request.getParameter("LGD_OID");
        String lgdMID = request.getParameter("LGD_MID");
        String lgdPAYKEY = request.getParameter("LGD_PAYKEY");
        String lgdRESPCODE = request.getParameter("LGD_RESPCODE");
        String lgdRESPMSG = request.getParameter("LGD_RESPMSG");
        String lgdBUYERID = request.getParameter("LGD_BUYERID");
        if (lgdBUYERID == null || lgdBUYERID.equals("")) {
            lgdBUYERID = "NONMEMBER";
        }

        if (saleAmt == 0) {
            lgdRESPCODE = "0000"; // 0원 결제
        }

        StringBuffer resultMsg = new StringBuffer();
        boolean isDBOK = false;
        boolean isInitOK = false;

        if (lgdRESPCODE != null && !lgdRESPCODE.equals("0000")) {
            resultMsg.append(lgdRESPCODE);
        } else if (itemData == null) {
            resultMsg.append("결제 상품 정보를 찾을 수 없습니다.");
        } else if (memberVO == null) {
            resultMsg.append("회원 정보를 찾을 수 없습니다.");
        } else {
            isInitOK = true;
        }
        if (isInitOK) {
            commandMap.put("MEM_NO", memberVO.getMemNo());
            commandMap.put("APP_GBN", "1"); // 승인구분 카드 승인 1
            commandMap.put("P_COMCD", "DACOM"); // 결제대행사

            memberYearVO.setComcd(Config.COM_CD);
            memberYearVO.setItemCd(itemData.getItemCd());
            memberYearVO.setMemNo(memberVO.getMemNo());
            memberYearVO.setId(memberVO.getId());
            memberYearVO.setRgistGbn("1001");
            memberYearVO.setPartGbn(itemData.getPartGbn());// 사업장구분

            // 기존 유료회원의 종료날짜 조회
            MemberYearVO memberYearData = memYearService.selectMemYearData(memberYearVO);

            if (memberYearData != null && "Y".equals(memberYearData.getValidYn())) {
                startYmd = EgovDateUtil.addDay(memberYearData.getEndYmd(), 1);
                memberYearVO.setRgistGbn("1101"); // 재등록 구분

                smsParam.put("msgno", "15");
            }
            memberYearVO.setStartYmd(startYmd);
            memberYearVO.setEndYmd(EgovDateUtil.addDay(EgovDateUtil.addMonth(startYmd, itemData.getMonthCnt()), -1));

            regdate = startYmd.substring(0, 4) + "-" + startYmd.substring(4, 6) + "-" + startYmd.substring(6, 8) + " ~ " + memberYearVO.getEndYmd().substring(0, 4) + "-" + memberYearVO.getEndYmd().substring(4, 6) + "-" + memberYearVO.getEndYmd().substring(6, 8);

            smsParam.put("유료시작일", startYmd.substring(0, 4) + "년 " + startYmd.substring(4, 6) + "월 " + startYmd.substring(6, 8) + "일");
            smsParam.put("유료종료일", memberYearVO.getEndYmd().substring(0, 4) + "년 " + memberYearVO.getEndYmd().substring(4, 6) + "월 " + memberYearVO.getEndYmd().substring(6, 8) + "일");
        }

        // 해당 API를 사용하기 위해 WEB-INF/lib/XPayClient.jar 를 Classpath 로 등록하셔야 합니다.
        // (1) XpayClient의 사용을 위한 xpay 객체 생성
        /*
         * XPayClient xpay = new XPayClient(); 2021.06.06 JYS
         * // (2) Init: XPayClient 초기화(환경설정 파일 로드)
         * // configPath: 설정파일
         * // CST_PLATFORM: - test, service 값에 따라 lgdacom.conf의 test_url(test) 또는 url(srvice) 사용
         * // - test, service 값에 따라 테스트용 또는 서비스용 아이디 생성
         * if (isInitOK) {
         * isInitOK = xpay.Init(configPath, cstPLATFORM);
         * }
         * if ( !isInitOK ) {
         * //API 초기화 실패 화면처리
         * resultMsg.append( "결제요청을 초기화 하는데 실패하였습니다.<br>");
         * resultMsg.append( "LG유플러스에서 제공한 환경파일이 정상적으로 설치 되었는지 확인하시기 바랍니다.<br>");
         * resultMsg.append( "mall.conf에는 Mert ID = Mert Key 가 반드시 등록되어 있어야 합니다.<br><br>");
         * resultMsg.append( "문의전화 LG유플러스 1544-7772<br>");
         * } else {
         * try {
         * // (3) Init_TX: 메모리에 mall.conf, lgdacom.conf 할당 및 트랜잭션의 고유한 키 TXID 생성
         * xpay.Init_TX(lgdMID);
         * xpay.Set("LGD_TXNAME", "PaymentByKey");
         * xpay.Set("LGD_PAYKEY", lgdPAYKEY);
         * //금액을 체크하시기 원하는 경우 아래 주석을 풀어서 이용하십시요.
         * //String DB_AMOUNT = "DB나 세션에서 가져온 금액"; //반드시 위변조가 불가능한 곳(DB나 세션)에서 금액을 가져오십시요.
         * xpay.Set("LGD_AMOUNTCHECKYN", "Y");
         * xpay.Set("LGD_AMOUNT", String.valueOf(saleAmt));
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
         * commandMap.put("UIP", commandMap.getIp());
         * if (request.getAttribute("IS_MOBILE") == null) {
         * commandMap.put("TERMINAL_TYPE", "2001");
         * } else {
         * boolean isMobile = (boolean)request.getAttribute("IS_MOBILE");
         * commandMap.put("TERMINAL_TYPE", (isMobile?"2002":"2001"));
         * }
         * commandMap.put("REGUSER", lgdBUYERID);
         * // 0 원 결제
         * if (saleAmt <= 0) {
         * try {
         * isDBOK = memYearService.insertMemberYear(commandMap.getParam() , memberYearVO , rsvnVO);
         * //가입완료되면 세션 정보 업데이트
         * if (isDBOK && commandMap.getUserInfo() != null) {
         * MemberVO resultVO = myInforService.selectMemberData(userVO);
         * if (resultVO!=null) {
         * userVO = commandMap.getUserInfo();
         * userVO.setYearYn(resultVO.getYearYn()); // 연회원여부
         * userVO.setAnlmbEdate(resultVO.getAnlmbEdate()); // 연회원 종료일
         * commandMap.setUserInfo(userVO);
         * }
         * }
         * } catch (Exception e) {
         * isDBOK = false;
         * }
         * } else if (isInitOK) {
         * if ( xpay.TX() ) {
         * //1)결제결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
         * resultMsg.append( "결제요청이 완료되었습니다.  <br>");
         * resultMsg.append( "TX 결제요청 통신 응답코드 = " );//통신 응답코드("0000" 일 때 통신 성공)
         * resultMsg.append( xpay.m_szResCode );
         * resultMsg.append( "<br>");
         * resultMsg.append( "TX 결제요청 통신 응답메시지 = " );
         * resultMsg.append( xpay.m_szResMsg);
         * //out.println("거래번호 : " + xpay.Response("LGD_TID",0) + "<br>");
         * //out.println("상점아이디 : " + xpay.Response("LGD_MID",0) + "<br>");
         * //out.println("상점주문번호 : " + xpay.Response("LGD_OID",0) + "<br>");
         * //out.println("결제금액 : " + xpay.Response("LGD_AMOUNT",0) + "<br>");
         * //out.println("결과코드 : " + xpay.Response("LGD_RESPCODE",0) + "<br>"); //LGD_RESPCODE 결제요청 응답코드
         * //out.println("결과메세지 : " + xpay.Response("LGD_RESPMSG",0) + "<p>");
         * for (int i = 0; i < xpay.ResponseNameCount(); i++)
         * {
         * //out.println(xpay.ResponseName(i) + " = ");
         * for (int j = 0; j < xpay.ResponseCount(); j++)
         * {
         * //out.println("\t" + xpay.Response(xpay.ResponseName(i), j) + "<br>");
         * commandMap.put(xpay.ResponseName(i) , xpay.Response(xpay.ResponseName(i), j));
         * }
         * }
         * lgdRESPCODE = xpay.Response("LGD_RESPCODE",0) ;
         * lgdRESPMSG = xpay.Response("LGD_RESPMSG",0) ;
         * String lgdPAYTYPE = xpay.Response("LGD_PAYTYPE", 0);
         * //결제 실패했을 경우 호출하기 위해
         * Map<String, Object> cancelMap = new HashMap<String,Object>();
         * cancelMap.put("COMCD", memberYearVO.getComcd());
         * cancelMap.put("LGD_OID", lgdOID);
         * cancelMap.put("OID_STAT", "4001");
         * cancelMap.put("MODUSER",lgdBUYERID);
         * // (5)DB에 요청 결과 처리
         * if ( "0000".equals( xpay.m_szResCode ) && "0000".equals(lgdRESPCODE)) {
         * // 통신상의 문제가 없을시
         * // 최종결제요청 결과 성공 DB처리(LGD_RESPCODE 값에 따라 결제가 성공인지, 실패인지 DB처리)
         * // 최종결제요청 결과를 DB처리합니다. (결제성공 또는 실패 모두 DB처리 가능)
         * // 상점내 DB에 어떠한 이유로 처리를 하지 못한경우 false로 변경해 주세요.
         * // 만약 DB처리 실패시 Rollback 처리, isDBOK 파라미터를 false 로 변경
         * //
         * commandMap.put("P_TYPE", "SC0010".equals(lgdPAYTYPE)?"CARD":"BANK"); // 결제방법
         * commandMap.put("PG_VAN" , "PG");
         * if ("SC0010".equals(lgdPAYTYPE)) {
         * commandMap.put("CARD_CD", xpay.Response("LGD_FINANCECODE", 0) != null && xpay.Response("LGD_FINANCECODE",
         * 0).length() > 2 ? xpay.Response("LGD_FINANCECODE", 0).substring(0, 2) : xpay.Response("LGD_FINANCECODE", 0));
         * commandMap.put("BANK_CD", xpay.Response("LGD_FINANCECODE", 0) != null && xpay.Response("LGD_FINANCECODE",
         * 0).length() > 2 ? xpay.Response("LGD_FINANCECODE", 0).substring(0, 2) : xpay.Response("LGD_FINANCECODE", 0));
         * } else {
         * commandMap.put("CARD_CD", xpay.Response("LGD_FINANCECODE", 0) != null && xpay.Response("LGD_FINANCECODE",
         * 0).length() > 3 ? xpay.Response("LGD_FINANCECODE", 0).substring(0, 3) : xpay.Response("LGD_FINANCECODE", 0));
         * commandMap.put("BANK_CD", xpay.Response("LGD_FINANCECODE", 0) != null && xpay.Response("LGD_FINANCECODE",
         * 0).length() > 3 ? xpay.Response("LGD_FINANCECODE", 0).substring(0, 3) : xpay.Response("LGD_FINANCECODE", 0));
         * }
         * try {
         * isDBOK = memYearService.insertMemberYear(commandMap.getParam() , memberYearVO , rsvnVO);
         * //가입완료되면 세션 정보 업데이트
         * if (isDBOK && commandMap.getUserInfo() != null) {
         * MemberVO resultVO = myInforService.selectMemberData(userVO);
         * if (resultVO!=null) {
         * userVO = commandMap.getUserInfo();
         * userVO.setYearYn(resultVO.getYearYn()); // 연회원여부
         * userVO.setAnlmbEdate(resultVO.getAnlmbEdate()); // 연회원 종료일
         * commandMap.setUserInfo(userVO);
         * }
         * }
         * } catch (Exception e) {
         * isDBOK = false;
         * }
         * if ( !isDBOK ) {
         * xpay.Rollback("상점 DB처리 실패로 인하여 Rollback 처리 [TID:" +xpay.Response("LGD_TID",0)+",MID:" +
         * xpay.Response("LGD_MID",0)+",OID:"+xpay.Response("LGD_OID",0)+"]");
         * resultMsg.setLength(0);
         * //resultMsg.append( "TX Rollback Response_code = " + xpay.Response("LGD_RESPCODE",0) + "<br>");
         * //resultMsg.append( "TX Rollback Response_msg = " + xpay.Response("LGD_RESPMSG",0) + "<p>");
         * resultMsg.append( "결제 오류 코드 : " + rsvnVO.getRetCd() + "<br>");
         * resultMsg.append( "결제 오류 메시지 : " + rsvnVO.getRetMsg() + "<br>");
         * if ( "0000".equals( xpay.m_szResCode ) ) {
         * resultMsg.append("자동취소가 정상적으로 완료 되었습니다.<br>");
         * } else {
         * resultMsg.append("자동취소가 정상적으로 처리되지 않았습니다.<br>");
         * }
         * try {
         * cancelMap.put("REQUEST_RESULT", rsvnVO.getRetMsg());
         * chargeService.updatePgOrdMst(cancelMap);
         * } catch (Exception e) {
         * resultMsg.append(e.getMessage());
         * }
         * } else {
         * //메일발송
         * try {
         * Map<String, Object> paramMap = new HashMap<String,Object>();
         * paramMap.put("MAIL_ID", memberVO.getEmail());
         * paramMap.put("MAIL_TITLE", "노원수학문화관 유료회원 가입이 완료되었습니다.");
         * paramMap.put("TEMPLATE_ID", "joinMember");
         * paramMap.put("ID", memberVO.getId());
         * paramMap.put("NAME", memberVO.getMemNm());
         * paramMap.put("REGDATE", regdate);
         * EMailUtil webEMailUtil = new EMailUtil();
         * webEMailUtil.sendToEmail(paramMap, null);
         * } catch (Exception e) {
         * //메일발송 오류 체크
         * log.error("유료회원가입 메일발송 오류:" + e.getMessage());
         * }
         * //SMS 발송
         * try {
         * smsParam.put("hp", memberVO.getHp());
         * smsParam.put("회원명",memberVO.getMemNm());
         * smsService.sendMessage(smsParam , userVO);
         * } catch (Exception e) {
         * //sms발송 오류 체크
         * log.error("회원가입 문자발송 오류:" + e.getMessage());
         * }
         * }
         * } else {
         * //최종결제요청 결과 실패 DB처리
         * // out.println("최종결제요청 결과 실패 DB처리하시기 바랍니다.<br>");
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
         * //out.println("최종결제요청 결과 실패 DB처리하시기 바랍니다.<br>");
         * }
         * } 2021.06.06 JYS
         */

        if (commandMap.getUserInfo() != null) {
            model.addAttribute("resultUrl", Config.USER_ROOT + "/mypage/memYear/memYearResult");
        } else {
            model.addAttribute("resultUrl", Config.USER_ROOT + "/member/join/joinYearResult");
        }
        model.addAttribute("resultMsg", resultMsg.toString());
        model.addAttribute("resultFlag", isDBOK);
        return HttpUtility.getViewUrl(request);
    }

    /**
     * 연간회원 결과 페이지
     * 
     * @param
     * @return callback page
     * @exception Exception
     */
    @RequestMapping({ "/mypage/memYear/memYearResult", "/member/join/joinYearResult" })
    public String memYearResult(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("memberYearVO") MemberYearVO memberYearVO) throws Exception {

        memberYearVO.setComcd(Config.COM_CD);
        MemberYearVO memberYearData = memYearService.selectMemYearResult(memberYearVO);

        if (memberYearData != null) {
            model.addAttribute("memberYearVO", memberYearData);
        } else {
            model.addAttribute("memberYearVO", memberYearVO);
        }

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 가족 회원 가입
     * 
     * @param
     * @return result - 결과
     * @exception Exception
     */
    @RequestMapping({ "/member/family/checkFamily" })
    public String familyJoinStep1(CommandMap commandMap, HttpServletResponse response, HttpServletRequest request,
            ModelMap model) throws Exception {
        LoginVO userVO = commandMap.getUserInfo();

        MemberYearVO memberYearData = null;

        if (userVO != null && userVO.isMember()) {
            MemberYearVO memberYearVO = new MemberYearVO();
            memberYearVO.setMemNo(userVO.getUniqId());

            memberYearData = memYearService.selectMemYearData(memberYearVO);

        } else {
            userVO = new LoginVO();
            // 파라미터로 회원코드를 받는다
            userVO.setUniqId(commandMap.getString("memNo"));

        }

        userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        MemberVO memberVO = myInforService.selectMemberData(userVO);

        if (memberVO == null) {
            String returnURL = Config.USER_ROOT + "/member/family/checkFamily";
            HttpUtility.sendRedirect(request, response, "회원 정보가 없습니다.로그인 후 다시 시도해 주세요.", Config.USER_ROOT + "/member/login?returnURL=" + URLEncoder.encode(returnURL, "UTF-8"));
            return null;
        }
        // 유료회원 상품
        commandMap.put("comcd", Config.COM_CD);
        MemberYearVO itemData = memYearService.selectProgramItemData(commandMap.getParam());

        model.addAttribute("memberYearData", memberYearData);
        model.addAttribute("memberVO", memberVO);
        model.addAttribute("itemData", itemData);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 가족회원 가입 수행
     * 
     * @param
     * @return result - 결과
     * @exception Exception
     */
    @PostMapping(value = "/member/family/familyAction")
    public String familyAction(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @ModelAttribute("memberVO") MemberVO vo) throws Exception {
        vo.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        LoginVO userVO = new LoginVO();
        userVO.setUniqId(commandMap.getString("memNo"));
        userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        MemberVO memberVO = myInforService.selectMemberData(userVO);

        String msg = "";

        if ("Y".equals(memberVO.getYearYn())) {
            msg = "이미 유료회원으로 등록되었습니다.";
        } else {
            // 주문번호
            OrderIdVO orderVO = new OrderIdVO();
            /* 주문번호 생성 시작 */
            orderVO.setComcd(Config.COM_CD);
            orderVO.setItemCd(commandMap.getString("itemCd"));
            orderVO.setUserId(memberVO.getId());
            orderVO.setRsvnCnt(1);
            orderVO.setRsvnAmt(0);

            chargeService.insertDbprocLog(orderVO);
            //// JYS 2021.05.18 chargeService.selectOrderId(orderVO);

            // OID 만들기
            String lgdOID = orderVO.getRetOid(); // 주문 번호 프로시져에서 생성
            /* 주문번호 생성 끝 */
            String terminalType = "2001";
            if (request.getAttribute("IS_MOBILE") != null) {
                boolean isMobile = (boolean) request.getAttribute("IS_MOBILE");
                terminalType = (isMobile ? "2002" : "2001");
            }

            // 멤버 등록
            vo.setId(memberVO.getId());
            vo.setComcd(Config.COM_CD);
            msg = memYearService.insertFamilyMember(memberVO, vo, lgdOID, terminalType);
        }

        model.addAttribute("resultMsg", msg);
        model.addAttribute("memberVO", memberVO);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 가족회원 결합 결과
     * 
     * @param
     * @return result - 결과
     * @exception Exception
     */
    @PostMapping(value = "/member/family/familyResult")
    public String familyResult(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @ModelAttribute("memberVO") MemberVO vo) throws Exception {

        LoginVO userVO = new LoginVO();
        userVO.setUniqId(commandMap.getString("memNo"));
        userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        MemberVO memberVO = myInforService.selectMemberData(userVO);

        // 가입완료되면 세션 정보 업데이트
        if (memberVO != null) {
            LoginVO userInfo = commandMap.getUserInfo();
            userInfo.setYearYn(memberVO.getYearYn()); // 연회원여부
            userInfo.setAnlmbEdate(memberVO.getAnlmbEdate()); // 연회원 종료일
            commandMap.setUserInfo(userInfo);
        }

        model.addAttribute("memberVO", memberVO);
        model.addAttribute("resultMsg", commandMap.getString("resultMsg"));
        return HttpUtility.getViewUrl(request);
    }

}
