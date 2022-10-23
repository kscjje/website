package com.hisco.cmm.util;

import java.util.Arrays;
import java.util.List;

/**
 * 공통사용 변수
 *
 * @author 김희택
 * @since 2020.08.20
 * @version 1.0, 2020.08.20
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          김희택 2020.08.20 최초작성
 */
public class Constant {

    public static final String SMS_MEMB_NAME = "$회원명$";
    public static final String SMS_MEMB_STTIME = "$유료시작일$";
    public static final String SMS_MEMB_EDTIME = "$유료종료일$";
    public static final String SMS_MEMBER_EDTIME = "$휴면계정전환시행일$";

    // 예약
    public static final String SMS_RSVN_NAME = "$예약자명$";
    public static final String SMS_USE_NAME = "$이용고객명$";
    public static final String SMS_RSVN_NO = "$예약번호$";
    public static final String SMS_RSVN_DT = "$예약일시$";
    public static final String SMS_RSVN_PERSON = "$예약인원명$";

    // 강연/행사/영화
    public static final String SMS_EVT_NAME = "$공연행사명$";
    public static final String SMS_EVT_PERSON = "$참여인원수$";
    public static final String SMS_EVT_STTIME = "$회차시작시간$";
    public static final String SMS_EVT_EDTIME = "$회차종료시간$";
    public static final String SMS_EVT_VEW_DE = "$관람일자$";

    // 관람

    public static final String SMS_EXBT_NAME = "$전시관람명$";
    public static final String SMS_EXBT_HALL_NAME = "$전시관명$";
    public static final String SMS_EXBT_PAY_EDTIME = "$결제마감시간$";
    public static final String SMS_EXBT_VEW_DT = "$관람일시$";

    // 교육
    public static final String SMS_EDC_NAME = "$교육프로그램명$";
    public static final String SMS_EDC_DT = "$교육기간$";
    public static final String SMS_EDC_TIME = "$교육시간$";
    public static final String SMS_EDC_DAY = "$교육요일$";
    public static final String SMS_EDC_PLACE = "$교육장소$";

    // 기타
    public static final String SMS_TELNO = "$문의전화$";
    public static final String SMS_CANCEL_AMNT = "$취소금액$";
    public static final String SMS_REFND_INFO = "$환불정보$";
    public static final String SMS_PAY_AMNT = "$결제금액$";
    public static final String SMS_URL = "$URL$";
    public static final String SMS_CPN_NAME = "$쿠폰명$";
    public static final String SMS_TICKET_NO = "$티켓번호$";
    public static final String SMS_TICKET_URL = "$관람티켓확인URL$";

    public static final String SM_QESTNAR_SRCH_COND_CD_주제명 = "1001";

    // admin > 설문 > 설문항목유형
    public static final String SM_QESTNAR_TYPE = "SM_QESTNAR_TYPE";

    // user > 강죄신청 > 검색조건1(요형별)
    public static final String EDC_SEARCH_TAB_강좌명 = "EDCANM";
    public static final String EDC_SEARCH_TAB_지역별 = "AREA";
    public static final String EDC_SEARCH_TAB_대상별 = "TARGET";
    public static final String EDC_SEARCH_TAB_분야별 = "CATE";
    public static final String EDC_SEARCH_TAB_기관별 = "ORGAN";
    public static final String EDC_SEARCH_TAB_시간대 = "TIME";
    // user > 강죄신청 > 검색조건2(온라인,방문)
    public static final String EDC_SEARCH_ONOFF_전체 = "ALL";
    public static final String EDC_SEARCH_ONOFF_온라인 = "ON";
    public static final String EDC_SEARCH_ONOFF_온라인_방문 = "ONOFF";
    // user > 강좌신청 > 정렬순서기준
    public static final String EDC_SEARCH_ORDER_접수마감순 = "BY_APPLY_CLOSE";
    public static final String EDC_SEARCH_ORDER_최신등록 = "BY_RECENT_UP";
    public static final String EDC_SEARCH_ORDER_프로그램명_올림차순 = "BY_PROGRAM_NM_ASC";
    public static final String EDC_SEARCH_ORDER_프로그램명_내림차순 = "BY_PROGRAM_NM_DESC";

    public static final String EDC_PROGRAM_STATUS_예정 = "예정";
    public static final String EDC_PROGRAM_STATUS_신청 = "신청";
    public static final String EDC_PROGRAM_STATUS_마감 = "마감";
    public static final String EDC_PROGRAM_STATUS_종료 = "종료";

    public static final String EDC_RSVN_MEMTYPE_회원 = "1001";
    public static final String EDC_RSVN_MEMTYPE_비회원 = "2001";

    public static final String SM_EDCPROGM_TYPE_일반교육 = "1001";
    public static final String SM_EDCPROGM_TYPE_마을배움터 = "4001";

    public static final String EDC_ONOFFIN_TYPE_ON = "10";
    public static final String EDC_ONOFFIN_TYPE_OFF = "20";

    public static final String EDC_ONOFFPYN_TYPE_ON = "10";
    public static final String EDC_ONOFFPYN_TYPE_OFF = "20";

    public static final String EDC_TRMNL_TYPE_PC = "PC";
    public static final String EDC_TRMNL_TYPE_MO = "MO"; // 모바일

    /* SM_LERECTYPE(교육프로그램신청접수방식) */
    public static final String SM_LEREC_TYPE_선착접수 = "1001";
    public static final String SM_LEREC_TYPE_선착대기 = "1002";
    public static final String SM_LEREC_TYPE_추첨대기제 = "2001";
    public static final String SM_LEREC_TYPE_타기관링크 = "5001";

    /* SM_PAYWAIT_GBN(교육예약신청결제마감시간적용기준) */
    public static final String SM_PAYWAIT_GBN_오프셋이용 = "1001"; // EDC_PAYWAIT_TIME 이용
    public static final String SM_PAYWAIT_GBN_특정일시이용 = "3001"; // EDC_PAYWAIT_DATE + EDC_PAYWAIT_HHMM 이용

    /* SM_RSVN_STAT(예약신청상태) */
    public static final String SM_RSVN_STAT_배정대기 = "1000";
    public static final String SM_RSVN_STAT_추첨대기 = "1002";
    public static final String SM_RSVN_STAT_입금대기 = "2001"; // 11
    public static final String SM_RSVN_STAT_신청취소 = "3001"; // 12
    public static final String SM_RSVN_STAT_당첨취소 = "3002";
    public static final String SM_RSVN_STAT_기한만료자동취소 = "3003"; // 32
    public static final String SM_RSVN_STAT_환불취소 = "3004"; // 31
    public static final String SM_RSVN_STAT_등록완료 = "4001"; // 20

    /* 추천프로그램 추첨상태 */
    public static final String SM_PRZWIN_STAT_당첨대기 = "1001";
    public static final String SM_PRZWIN_STAT_당첨확정 = "1101";
    public static final String SM_PRZWIN_STAT_되돌리기 = "3001";

    /* 추천프로그램 추첨상태 */
    public static final String SM_PRZWIN_GBN_당첨 = "1001";
    public static final String SM_PRZWIN_GBN_예비당첨 = "2001";

    /* SM_RSVNLMIT_GBN(예약제한구분) */
    public static final String SM_RSVNLMIT_GBN_전체프로그램적용 = "1001";
    public static final String SM_RSVNLMIT_GBN_프로그램분류별적용 = "2001";
    public static final String SM_RSVNLMIT_GBN_프로그램지정적용 = "3001";

    /* SM_RSVNLMIT_PD(예약제한기간구분) */
    public static final String SM_RSVNLMIT_PD_월간 = "1001";
    public static final String SM_RSVNLMIT_PD_년간 = "2001";

    public static final String EDC_REQ_GENDER_남성 = "1001";
    public static final String EDC_REQ_GENDER_여성 = "2001";
    public static final String EDC_REQ_GENDER_제한없음 = "3001";

    public static final String CM_MEMBER_GENDER_남성 = "1";
    public static final String CM_MEMBER_GENDER_여성 = "2";

    /* 약관정보 */
    public static final String CM_TERMS_ID_회원가입약관동의 = "1001";
    public static final String CM_TERMS_ID_이용약관동의 = "1002";
    public static final String CM_TERMS_ID_취급위탁동의 = "1003";
    public static final String CM_TERMS_ID_법정대리인동의 = "1005";
    public static final String CM_TERMS_ID_개인정보수집이용동의 = "1008";
    public static final String CM_TERMS_ID_교육예약유의사항동의 = "3001";
    public static final String CM_TERMS_ID_교육환불유의사항동의 = "3002";

    public static final String CM_CHANNEL_BO = "BO"; // backoffice
    public static final String CM_CHANNEL_FO = "FO"; // frontoffice

    public static final String CM_PERIOD_CONDITION_수강년월 = "EDCYM";
    public static final String CM_PERIOD_CONDITION_예약년월 = "RSVNYM";

    public static final String TOSS_P_TYPE_카드 = "SC0010"; // CARD
    public static final String TOSS_P_TYPE_실시간계좌이체 = "SC0030";// BANK
    public static final String TOSS_P_TYPE_가상계좌 = "SC0040";// VBANK
    public static final String SITE_P_TYPE_카드 = "CARD"; // CARD
    public static final String SITE_P_TYPE_실시간계좌이체 = "BANK";// BANK
    public static final String SITE_P_TYPE_가상계좌 = "VBANK";// VBANK

    public static final String SITE_P_TYPE_현금 = "CASH";
    public static final String SITE_DEFAULT_FINANCECD = "CH";// 단순히 CASH일경우 db입력을 위해 존재

    public static final String SM_SLIP_TYPE_정상전표 = "1";
    public static final String SM_SLIP_TYPE_환불전표 = "2";
    public static final String SM_SLIP_TYPE_취소전표 = "3";

    public static final String SM_SLIP_STATE_정상 = "10";
    public static final String SM_SLIP_STATE_환불 = "20";
    public static final String SM_SLIP_STATE_취소 = "30";

    public static final String SM_TERMINAL_TYPE_현장PC = "1001";
    public static final String SM_TERMINAL_TYPE_키오스크 = "1002";
    public static final String SM_TERMINAL_TYPE_온라인WEB = "2001";
    public static final String SM_TERMINAL_TYPE_모바일 = "2002";

    public static final String SM_SALE_UPDN_UNIT_원 = "1001";
    public static final String SM_SALE_UPDN_UNIT_십원 = "1010";

    public static final String SM_SALE_UPDN_GBN_없음 = "1001";
    public static final String SM_SALE_UPDN_GBN_절상 = "1010";
    public static final String SM_SALE_UPDN_GBN_절사 = "1020";

    public static final String SM_REGIST_GBN_신규등록 = "1001";
    public static final String SM_REGIST_GBN_재등록 = "1101";

    public static final String SM_OID_STAT_거래요청 = "1001";
    public static final String SM_OID_STAT_거래완료 = "2001";
    public static final String SM_OID_STAT_거래취소 = "3001";
    public static final String SM_OID_STAT_거래실패 = "4001";

    public static final String SM_APP_GBN_카드승인 = "1";
    public static final String SM_APP_GBN_카드취소 = "2";
    public static final String SM_APP_GBN_현금영수증승인 = "3";
    public static final String SM_APP_GBN_현금영수증취소 = "4";

    public static final String SM_VBANK_REQTYPE_교육프로그램및사물함신청결제 = "1001";
    public static final String SM_VBANK_REQTYPE_교육프로그램및사물함환불재결제 = "1002";

    public static final String SM_VBANK_PAYMENT_STATUS_입금대기 = "1001";
    public static final String SM_VBANK_PAYMENT_STATUS_입금신청취소 = "2001";
    public static final String SM_VBANK_PAYMENT_STATUS_고객입금취소 = "2002";
    public static final String SM_VBANK_PAYMENT_STATUS_대기시간경과취소 = "2003";
    public static final String SM_VBANK_PAYMENT_STATUS_입금완료 = "3001";
    public static final String SM_VBANK_PAYMENT_STATUS_과오납입금완료 = "3002";

    public static final String PG_TOSS = "TOSS";
    public static final String PG_SELF = "NOWON";

    public static final String PG_TOSS_TXNAME_CANCEL = "Cancel";
    public static final String PG_TOSS_TXNAME_GET_PART_CANCELABLE = "GetPartCancelable";
    public static final String PG_TOSS_TXNAME_PARTIAL_CANCEL = "PartialCancel";

    public static final String NWPAY_ORDERID_SESSION_KEY = "NWPAY_ORDERID";

    public static final List<String> LOGGING_PROFILES = Arrays.asList("local", "dev");
    public static final List<String> ING_RSVN_STAT_LIST = Arrays.asList(Constant.SM_RSVN_STAT_배정대기, Constant.SM_RSVN_STAT_추첨대기, Constant.SM_RSVN_STAT_입금대기);
}
