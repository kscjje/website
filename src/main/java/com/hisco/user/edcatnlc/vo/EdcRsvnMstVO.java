package com.hisco.user.edcatnlc.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.object.CamelMap;

import lombok.Data;

/**
 * @Class Name : EdcRsvnMstVO.java
 * @Description : 교육 예약 마스터 VO
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------------ ------- -------------------
 *               2020.10.06 전영석
 * @author 전영석
 * @since 2020.10.06
 * @version
 * @see
 */
@SuppressWarnings("serial")
@Data
public class EdcRsvnMstVO implements Serializable {

    private String comcd;// 기관고유번호
    private String edcPartcd;// 교육예약업장고유번호
    private String edcRsvnno;// 예약번호
    private String edcRsvnMemtype;// 예약자회원구분
    private String edcRsvnMemno;// 예약회원번호
    private String edcRsvnWebid;// 예약신청회원온라인ID
    private String edcRsvnCustnm;// 비회원예약자명
    private String edcPersnGbn;// 단체개인구분
    private String edcRsvnGrpnm;// 단체명
    private String edcRsvnFax;// 예약단체FAX
    private String edcRsvnTel;// 비회원예약신청자일반전화_암호화
    private String edcRsvnMoblphon;// 비회원예약신청자휴대폰번호_암호화
    private String edcEmail;// 비회원_예약자이메일_암호화
    private String edcVisitCustnm;// 방문고객성명
    private String edcVisitMoblphon;// 방문고객휴대폰번호
    private String edcNonmembCertno;// 비회원휴대폰본인인증
    private String edcVistnmpr;// 관람인원
    private String edcVeingdate;// 관람일자
    private String edcRsvnEtc;// 기타사항및세부내역
    private String edcRsvnApptype;// 매표상태
    private String edcRsvnSaleamt;// 이용총액
    private int edcDcamt;
    private String edcRsvnPayamt;// 결제총액
    private String edcRsvnPaydate;// 결제일자
    private String edcRsvnRetyn;// 환불완료여부
    private String edcReceiptno;// 매출거래영수증번호
    private String edcCancelMemo;// 취소사유
    private String edcOnoffintype;// 온,오프라인예약등록구분
    private String edcOnoffpyntype;// 온,오프라인결제수납유무
    private String edcTrmnltype;// 등록터미널타입
    private String edcIndvdlinfoAgrid;// 예약개인정보수집동의약관ID
    private String edcIndvdlinfoAgryn;// 예약개인정보수집동의여부
    private String edcAgreeid;// 관람이용동의약관ID
    private String edcAgreeyn;// 관람이용동의여부
    private String edcRefndAgrid;// 관람이용료환불동의약관ID
    private String edcRefndAgryn;// 관람이용료환불동의여부

    private String edcReqCustnm;// 예약자명
    private String edcReqMoblphon; // 예약자폰

    private Date edcPaywaitEnddatetime;// 결제대기만료일시
    private String edcVisitcarno;// 방문차량번호
    private Date regdate;// 등록일시
    private String reguser;// 등록자
    private Date moddate;// 수정일시
    private String moduser;// 수정자

    private String ctgCd; // 카테고리
    private String edcTime; // 예약일
    private String dayWeek; // 요일
    private String guideTelno; // 문의전화
    private String edcDaygbnNm;// 교육요일
    private String edcPlacenm;// 교육장소
    private String onoffPaytype;// 온오프 결제 타입 10 , 20 온라인 오프라인

    private String edcTimeseq;
    private String edcTimestdSeq;
    private String edcRsvnTimeidx;
    private String edcRsvnOrgname;// 교육신청단체명
    private String dbEnckey; // 암호화키
    private String edcApptypeNm;
    private String edcStime;
    private String edcEtime;
    private String edcReqItemCd; // 항목 코드
    private String dcName; // 이벤트 할인명
    private String dcRate;// 이벤트 할인 율(%)
    private long amtSum; // 합계금액(파라미터)

    private EdcrsvnTimeVO timeVO; // 시간정보
    private List<EdcItemAmountVO> chargeList; // 요금정보
    private List<EdcRsvnItemVO> itemList; // 예약상세내역

    private String oid; // 결제번호
    private int totCount; // 총 게시물 수

    // ------------------------------------------------------------------>SPOWISECMS

    private String edcRsvnReqid;// 교육예약고유번호

    private String edcReqDate; // 예약일자
    private String edcReqTime; // 예약시간

    private long edcTotamt;
    private String edcPrgmnm;
    private String edcReqSdate; // 교육시작일자
    private String edcReqStime; // 교육시작시간
    private String edcReqEdate; // 교육종료일자
    private String edcReqEtime; // 교육종료시간

    private String edcStat; // 교육 신청 상태 코드

    private String edcMemNoNm;
    private String edcMemNoId;
    private String edcMemNoEmail;
    private String edcPartCd;
    private String edcPartCdNm;
    private String edcProgmCost;
    private Date payRegdate;
    private String eduPeriod;
    private String edcProgmType;
    private int familyCnt;
    private String exclDcyn; // 할인가능여부
    private String cpnName; // 쿠폰명
    private String dcTypeNm; // 할인 사유명
    private String dcKindCd; // 할인 종류 코드
    private String cpnDcrate;// 쿠폰 할인 율(%)

    private int rfndSeq; // 환불 정책
    private CamelMap rfndRule;
    private String cancelAbleYn; // 취소 가능여부
    private long cancelAmt; // 환불 가능 금액
    private long breakAmt; // 공제 금액
    private String rfndRate; // 환불 %

    private String selngId; // 매출번호 SELNG_ID
    private String vatYn; // 부가세 여부
    private String editYn; // 수정가능여부
    private String passYn; // 지난일시여부
    private String webPaymentId; // 결제 PG id
    private String edcComplyn; // 수료여부
    private String edcAddrOpenyn; // 주소 수집여부
    private String edcOnlineyn;// 온라인 교육여부

    private String edcInterStatus; // 교육 내부 상태

    private String edcCanceldate; // 교육 신청 취소 일자

    private String edcLeadCustnm; // 방문 인솔자명
    private String edcLeadMoblphon; // 방문 인솔자 연락처

    private String cancelYn; // 취소여부

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
