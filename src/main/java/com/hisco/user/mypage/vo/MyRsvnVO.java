package com.hisco.user.mypage.vo;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Constant;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;

/**
 * @Class Name : MyRsvnVO.java
 * @Description : 예약 결제 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 9. 08 진수진
 * @author 진수진
 * @since 2020. 9. 08
 * @version
 * @see
 */
@SuppressWarnings("serial")
@Data
public class MyRsvnVO extends ComDefaultVO {

    private int orgNo; /* 평생학습포털을 강좌를 운영하는 등록된 기관의 고유번호로 시퀀스(SEQ_ORGNO) 를 사용 */
    private String uniqId /* 회원번호 */;
    private String id /* 아이디 */;
    private String memNm; // 이름

    private String edcRsvnMemno; // 예약회원번호
    private String edcRsvnNo; // 예약번호
    private int edcRsvnReqid; // 예약신청고유번호

    /** 본인인증 키 */
    private String hpcertno;
    private String edcRsvnMoblphon; // 신청자 휴대폰
    private String edcRsvnBirthdate; // 신청자 생일

    private String gubun; // 관람 강연,행사,영화 교육 구분
    private String subGubun; // Math 부메뉴
    private String mode; // 파라미터
    private String orderId; // 주문번호

    private List<String> rsvnIdx;

    private String itemCd; // 품목코드
    private String target; // 단체 20 개인 10
    private String ymd;
    private String rsvnIdxOne; // 예약 idx
    private String programCd; // 프로그램 코드
    private String partCd; // 사업장
    private String dbEncKey; // 암호화키
    private String memberGbn; // 1: 유료회원 2: 특별회원
    private String subSeq; // 서브 seq
    private String appStatus; // 예약상태

    private String appStatusNew; //결제구분상태 분기처리를 위한 변수
    /*		
    1000	배정대기
    1002	추첨대기
    2001	입금대기
    3001	신청취소
    3002	당첨취소
    3003	결제기한만료자동취소
    3004	환불취소
    4001	등록완료
    */
    
    private String paymentId;
    private String paymentPw;

    private String selngId; // 매출고유번호
    private String slipNo; // 정산번호

    // 프로시져 관련
    private String memYearSeq; /* 시퀀스 */
    private String partGbn; /* 사업장구분 */
    private String retOid; /* 주문번호 */
    private String retMsg; /* 리턴 메시지 */
    private String retCd; /* 리턴 코드 */
    private List<?> retCursor; /* 리턴 커서 */
    private String retReceiptNo; /* 리턴 영수증 번호 */
    private String terminalType; /* 2001 : 온라인WEB , 2002: 모바일 */
    private String memYearDccd; /* 연간회원할인코드 */
    private long memYearDcrate; /* 연간회원할인율 */
    private long memYearSaleamt; /* 연간회원판매금액 */
    private long memYearDcamt; /* 연간회원할인금액 */

    private String cancelType; /* 취소구분 : F:전체취소 , P:부분취소 */
    private String rsvnNo;// 예약번호(,분리하여 전송)

    private List<String> rsvnNoList;// 예약번호(,분리하여 전송)

    // 취소 관련
    private long payAmt; // 결제금액
    private long cancelAmt; // 취소금액
    private long minusAmt; // 공제금액(위약금)
    private String cancelCd; // 취소사유
    private String partialYn;// 부분취소 여부
    private String ip; // IP
    private double cardRateAmt; // 카드 수수료
    private double cardRate; // 카드 수료
    private String appDate; // 승인날짜
    private String cancelDate; // 취소 날짜
    private String cancelAppNo;// 부분 취소 번호

    // 할인 관련
    private List<String> couponIds; /* 적용쿠폰 */
    private List<String> annualReasonCds; /* 연간회원 할인코드 */
    private String eventReasonCd; // 이벤트할인
    private String couponIdOne;
    private String discReasonDc; // 할인 사유코드

    private long dcAmt; // 할인금액
    private int itemCnt; // 수량

    private String updownAmtUnit; // 절삭 단위
    private String updownAmtGbn; // 절삭 구분
    private String pComcd; // pg 사
    private String pType; // 결제방법
    private String cardCd; // 카드사

    // 결제 관련
    private String lgdMID; // 결제 아이디
    private String cstMID; // 결제 아이디
    private String cstPW; // 결제 비밀번호
    private String cstPLATFORM; // 테스트, 서비스 구분
    private String lgdBUYER; /* 이름 */
    private String lgdBUYERID; /* 구매자 아이디 */
    private String lgdPRODUCTINFO; // 상품정보
    private String lgdPRODUCTCODE;// 상품코드
    private String lgdAMOUNT; // 결제금액
    private String lgdBUYEREMAIL; // 구매자 이메일
    private String lgdOID;
    private String lgdTIMESTAMP; // 타임스탬프
    private String lgdCUSTOMFIRSTPAY;
    private String lgdHASHDATA;
    private String lgdRETURNURL;
    private String lgdCASNOTEURL;

    private Date regdate /* 등록일시 */;
    private String reguser /* 등록자 */;
    private Date moddate /* 수정일시 */;
    private String moduser /* 수정자 */;

    private String pcancelNo;
    private String oldPcancelNo;
    private String oidSeq;

    private String channel = Constant.CM_CHANNEL_FO;

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
