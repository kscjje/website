package com.hisco.user.edcatnlc.vo;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.DateUtil;
import com.hisco.intrfc.sale.vo.PaySummaryVO;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EdcRsvnInfoVO extends ComDefaultVO {
    private int edcRsvnReqid; // 교육신청고유번호. 시퀀스 : SEQ_EDC_REQID
    private String edcRsvnNo; // 예약번호. . 교육프로그램 예약신청시 부여되는 예약번호. . 매표예약번호구성 : 'R'+예약구분(1)+매표일자(6)+예약일련번호 5자리
                              // 시퀀스(SEQ_EDC_RSVNNO ). 예약구분 : 1:전시관람 ，2 공연행사，3 교육프로그램. 매표예약일련번호 5자리 시퀀스(SEQ_EXBT_RSVNNO)
                              // 뒷5자리 : 5자리가 안될경우 앞자리를 '0'으로 채움. . 예시> R320071400001
    private String edcReqDate; // 교육신청일자
    private String edcReqTime; // 교육신청시간
    private String edcReqDtime; // 교육신청일자+시간
    private String edcMemNo; // 교육대상회원번호
    private String edcRsvnMemno; // 교육예약신청회원번호. 예약신청한 ID회원의 회원번호를 기록한다.
    private String edcRsvnMemtype = Constant.EDC_RSVN_MEMTYPE_회원; // 교육신청회원구분
    private String edcRsvnOrgname; // 교육신청단체명
    private String edcRsvnScnm; // 교육신청학교명(개인교육프로그램한아여 제한적 정보수집)
    private String edcRsvnCustnm; // 비회원교육예약고객명
    private String edcRsvnTel; // 비회원교육예약자일반전화_암호화(확장성)
    private String edcRsvnMoblphon; // 비회원교육예약자휴대폰번호_암호화. . 비회원인경우 필수적으로 입력받는다.
    private String edcRsvnGender;
    private String edcRsvnBirthdate;
    private String edcEmail; // 비회원교육예약자이메일정보_암호화
    private String edcReqCustnm; // 비회원교육대상자고객성명
    private String edcReqMoblphon; // 비회원교육대상자고객휴대폰번호_암호화
    private String edcNonmembCertno; // 사무실전화
    private int edcVistnmpr = 1; // 교육참가신청인원
    private String edcVistorGradenm; // 단체프프로그램참가학년. 단체프로그램참가인원의 학년정보를 기록한다.
    private String edcRegistgbn; // 등록구분
    private int edcPrgmNo; // 교육프로그램고유번호
    private int edcReqItemCd; // 교육프로그램요금품목고유번호
    private int edcRsvnsetSeq; // 교육프로그램 차수
    private String edcReqSdate; // 교육시작일자
    private String edcReqEdate; // 교육종료일자
    private String edcReqStime; // 교육시작시간
    private String edcReqEtime; // 교육종료시간
    private String edcTimeGuide; // 교육시간 안내
    private int edcMonthcnt = 1; // 교육이용개월수
    private int edcProgmCost = 0; // 교육비단가
    private String edcReasondc; // 교육비할인사유. 교육비할인사유코드를 기록한다.. COT_GRPCD.GRP_CD = 'CM_REASON_DC'
    private int edcDcamt = 0; // 할인금액
    private int edcDcrate = 0; // 할인율
    private int edcTotamt = 0; // 교육비총액
    private String edcHomeZipno; // 온라인교육재료발송우편번호. 온라인학습프로그램인경우 교육재료발송을 위한 주소정보를 기록한다.
    private String edcHomeAddr1; // 온라인교육재료발송주소1_암호화. . 온라인학습프로그램인경우 교육재료발송을 위한 주소정보를 기록한다.
    private String edcHomeAddr2; // 온라인교육재료발송주소2_암호화. 온라인학습프로그램인경우 교육재료발송을 위한 주소정보를 기록한다.
    private String edcInBusloct; // 교육입소셔틀버스탑승가능장소
    private String edcOutBusloct; // 교육퇴소셔틀버스탑승가능장소
    private String edcEtc; // 교육신청예약비고사항. 비고사항을 기록한다.
    private String edcIndvdlinfoAgrid; // 교육예약개인정보수집동의약관ID
    private String edcIndvdlinfoAgryn; // 교육예약개인정보수집동의여부
    private String edcAgreeid; // 교육이용동의약관ID. 공통약관고유번호를 기록한다.
    private String edcAgreeyn; // 교육이용동의여부. Y，N
    private String edcRefndAgrid; // 교육이용료환불동의약관ID. 공통약관고유번호를 기록한다.
    private String edcRefndAgryn; // 교육이용료환불동의여부. Y，N
    private String edcOnoffintype = Constant.EDC_ONOFFIN_TYPE_ON; // 온/오프라인예약등록구분(ON,OFF)
    private String edcOnoffpyntype = Constant.EDC_ONOFFPYN_TYPE_ON; // 온/오프라인결제등록구분(ON,OFF)
    private String edcTrmnlType = Constant.EDC_TRMNL_TYPE_PC; // 등록터미널타입(PC,MO)
    private Timestamp edcPaywaitEnddatetime; // 결제대기만료일시. . 결제마감시간은 10분단위로 반올림. 예시> 2020.10.04 14:22 분이 산정된경우 .
                                             // 결제대기마감시간은 14:30분으로 기록함. 교육신청후 결제대기 만료일자를 산출하여 기록한다. . 결제대기 만료일자가 설정되면
                                             // 자동으로 해당 일자가 지나 결제가 안된 . 교육신청 은 스케쥴에의해 자동취소됨. . .
    private String closeDate; // 입금마감일시
    private String closeYn; // 입금마감여부
    private String edcConfirmDate;
    private String edcRetyn = Config.NO; // 환불여부
    private int selngId; // 매출원장의 고유번호
    private int retSelngId; // 매출취소장의 고유번호
    private int paySeq; // pay_list.pay_seq
    private String edcBefEdate; // 연장이전종료일자(확장성)
    private String edcStat = Constant.SM_RSVN_STAT_입금대기; // 예약상태. . 교육프로그램 신청접수상태를 분류한다.. COT_GRPCD.GRP_CD =
                                                         // 'SM_RSVN_STAT'
    private List<String> edcStatList;
    private String edcStatNm;
    private Timestamp drwtDate; // 추첨처리한 일자시간을 기록한다.
    private String drwtUserid; // 추첨처리한 담당자 USERID 를 기록한다.
    private String przwinStat; // 당첨상태. 당첨상태를 기록하는 시스템분류코드 . 공통코드 그룹 (SM_PRZWIN_STAT)
    private String przwinyn; // 당첨구분. 추첨시 당첨된경우 당첨구분을 분류하는 시스템 분류코드
    private String przwinGbnNm; // 당첨,탈락

    private String edcSdate; // 교육시작일
    private String edcEdate; // 교육종료일
    private String edcProgmType; // 교육 유형 (4001- 마을배움터)

    private String cancelDtime; // 취소 일시 string 형

    private String drwtNtcedate; // 추첨예정일
    private String rsvnDrwtConftn; // 추첨확정 여부
    private int waitNo; // 대기순번

    private Timestamp regdate; // 등록일시
    private String reguser; // 등록자
    private Timestamp moddate; // 수정일시
    private String moduser; // 수정자

    private String editYn; // 교육시작전 취소 가능여부
    private String oid; // 주문번호
    private String edcComplstat; // 수료여부 1001 : 중도만료 2001 : 수료

    private String payCancelYn; // 지불취소여부
    private Timestamp cancelDate; // 취소 일
    private String payMethod; // 결제방법
    private String guideTelno; // 문의전화 SMS 발송시 사용

    private String receiptNo; // 영수증번호
    private String payDate; // 결제일
    private String payTime; // 결제시간
    private int payAmt; // 결제금액
    private int cancelAmt; // 취소(환불) 금액
    private int cancelDcAmt; // 취소(환불)처리 DC 금액
    private int remainAmt; // 결제금액-취소금액

    private int edcClcnt; // 수업횟수
    private String exclDcyn; // 감면적용예외여부(Y:감면미적용, N:감면적용)

    // 검색조건
    private String edcPrgmidList;
    private String edcRsvnRectype; // 교육예약신청접수방법
    private String edcRsvnRectypeNm;

    private String periodCondition;
    private String searchYear;
    private String searchMonth;
    private String usePagingYn = Config.YES;
    private String url;

    // 조회결과건수
    private int totCount;

    // 쿼리 결과 항목 추가
    private int orgNo;
    private String orgNm;
    private String orgTel;
    private String edcRelDeptname;
    private String edcPrgmnm;
    private String instrctrName;
    private String edcGuideTelno;
    private String payComcd;
    private String payMethodNm; // 결제수단코드
    private String financeNm; // 은행명, 카드사명
    private String refundAmt;
    private String refundDate;
    private String prevMonthOutYn; // 이전월접수탈락여부
    private int edcPncpa; // edc_program.EDC_PNCPA 정원
    private String edcDaygbnNm; // 수업요일,,
    private String edcRsvnSdate;
    private String edcRsvnEdate;
    private String edcRsvnStime;
    private String edcRsvnEtime;
    private String edcPlacenm;
    private String comCtgnm; // 프로그램분야
    private String edcTargetAgegbnNm; // 대상나이구분명
    private String edcOdr;
    private String edcRsvnsetNm;
    private String edcProgmTypeNm; // 선착/선착대기/추첨
    private String edcRsvnAccssrdNm; // 접수경로

    private String appDate; // pay_list.app_date 년월일시분초(14자리)
    private String appNo;
    private String mid; // 상점id
    private String tid; // pg transaction id
    private String edcReasondcNm; // 할인명
    private String edcRsvnEdday; // 접무마감까지남은일수
    private String dpstrNm; // 환불계좌예금자명
    private String bankNm; // 환불계좌은행이름
    private String acountNum; // 환불계좌번호

    private int vbankSeq;// 가상계좌 순번
    private String vbankName;
    private String vbankPname;
    private String vbankAccountNo;
    private String vbankAmount;
    private String vbankPayamt; // 입금액
    private String vbankCloseYn; // 계좌마감여부
    private String vbankStatus; // 가상계좌 진행상태
    private String vbankStatusNm; // 가상계좌 진행상태명
    private String retDpstrNm;
    private String retBankNm;
    private String retBankCd;
    private String retAcountNum;

    private int pCancelCnt; // 부분취소횟수

    private String webPaymentMethod; // WEB결제수단적용설정

    // 노원PAY 사용자아이디
    private String nwpayUserid;
    private String nwpayOrdid; // 노원페이주문번호

    // 회원정보
    private String genderNm;
    private String id; /*사용하지 않음*/
    private String userId;
    private String joinDate;
    private String birthSecNm;
    private String smsYn;// sms수신동의여부

    // program_item정보
    private int costAmt; // 원가
    private int saleAmt; // 판매가
    private int salamt; // 판매가

    // BO, FO
    private String channel = Constant.CM_CHANNEL_FO;

    /* 디비 암호화 key */
    private String dbEncKey;
    private String searchOrderBy;

    /* bizclient (kakao 알림톡) */
    private String kakaoSenderKey;

    private PaySummaryVO paySummary; // 결제요약정보
    private List<PaySummaryVO> cancelHistory; // 취소요약정보
    private List<String> lectDateList; // 강의수업일자목록

    private String excludeCancelListYn = Config.NO; // 취소내역제외

    public int getAge() {
        if (StringUtils.isBlank(edcRsvnBirthdate) || edcRsvnBirthdate.length() <= 4)
            return 0;
        int birthYear = Integer.parseInt(edcRsvnBirthdate.substring(0, 4));
        int nowYear = Integer.parseInt(DateUtil.getTodate("yyyy"));
        return nowYear - birthYear + 1;
    }
}
