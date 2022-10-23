package com.hisco.user.evtrsvn.service;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.object.CamelMap;

/**
 * @Class Name : EvtrsvnMstVO.java
 * @Description : 강연/행사/영화 예약 마스터 VO
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 9. 01 김희택
 * @author 김희택
 * @since 2020. 9. 01
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class EvtrsvnMstVO implements Serializable {

    private String evtRsvnIdx;// 강연,행사,영화예약고유번호
    private String comcd;// 기관고유번호
    private String evtNo;// 강연,행사,영화고유번호
    private String evtPartcd;// 강연,행사,영화예약업장고유번호
    private String evtPartnm;// 강연,행사,영화예약업장명
    private String evtAppdate;// 매표등록일자
    private String evtRsvnno;// 예약번호
    private String evtRsvnMemtype;// 예약자회원구분
    private String evtRsvnMemno;// 예약회원번호
    private String evtRsvnWebid;// 예약신청회원온라인ID
    private String evtRsvnCustnm;// 비회원예약자명
    private String evtPersnGbn;// 단체개인구분
    private String evtRsvnGrpnm;// 단체명
    private String evtRsvnFax;// 예약단체FAX
    private String evtRsvnTel;// 비회원예약신청자일반전화_암호화
    private String evtRsvnMoblphon;// 비회원예약신청자휴대폰번호_암호화
    private String evtEmail;// 비회원_예약자이메일_암호화
    private String evtVisitCustnm;// 방문고객성명
    private String evtVisitMoblphon;// 방문고객휴대폰번호
    private String evtNonmembCertno;// 비회원휴대폰본인인증
    private String evtVeingnmpr;// 관람인원
    private String evtVeingdate;// 관람일자
    private String evtRsvnEtc;// 기타사항및세부내역
    private String evtRsvnApptype;// 매표상태
    private String evtRsvnSaleamt;// 이용총액
    private String evtRsvnPayamt;// 결제총액
    private String evtRsvnPaydate;// 결제일자
    private String evtRsvnRetyn;// 환불완료여부
    private String evtReceiptno;// 매출거래영수증번호
    private String evtCancelMemo;// 취소사유
    private String evtOnoffintype;// 온,오프라인예약등록구분
    private String evtOnoffpyntype;// 온,오프라인결제수납유무 10 , 20
    private String evtTrmnltype;// 등록터미널타입
    private String evtIndvdlinfoAgrid;// 예약개인정보수집동의약관ID
    private String evtIndvdlinfoAgryn;// 예약개인정보수집동의여부
    private String evtAgreeid;// 관람이용동의약관ID
    private String evtAgreeyn;// 관람이용동의여부
    private String evtRefndAgrid;// 관람이용료환불동의약관ID
    private String evtRefndAgryn;// 관람이용료환불동의여부
    private Date evtPaywaitEnddatetime;// 결제대기만료일시
    private String evtVisitcarno;// 방문차량번호
    private Date regdate;// 등록일시
    private String reguser;// 등록자
    private Date moddate;// 수정일시
    private String moduser;// 수정자

    private String ctgCd; // 카테고리
    private String evtTime; // 예약일
    private String dayWeek; // 요일

    // param
    private String evtTimeseq;// timeseq
    private String evtTimestdSeq;// timeseq
    private String evtRsvnTimeidx;// timeidx
    private String dbEnckey; // 암호화키
    private String evtName; // 강연/행사/영화명
    private String evtApptypeNm; // 결제상태
    private String evtStime;
    private String evtEtime;
    private String itemCd; // 항목 코드
    private long amtSum; // 합계금액(파라미터)
    private EvtrsvnTimeVO timeVO;// 시간정보
    private List<EvtItemAmountVO> chargeList;// 요금정보
    private List<EvtRsvnItemVO> itemList;// 예약상세내역

    private String oid; // 결제번호
    private int totCount; // 총 게시물 수

    private String ticketNo; // 티켓번호
    private String ticketChkyn; // 티켓발급대상여부
    private Date payregdate;// 결제일
    private String payWaitTime;// 결제 마감일시

    private int rfndSeq; // 환불 정책
    private CamelMap rfndRule;
    private String cancelAbleYn; // 취소 가능여부
    private long cancelAmt; // 환불 가능 금액
    private long breakAmt; // 공제 금액
    private String rfndRate; // 환불 %

    private String itemInfo; // 인원정보
    private String guideTelno; // 문의저화
    private String editYn; // edityn

    private String grpMinCnt;
    private String grpMaxCnt;

    private String evtGradeTarget;
    private String evtWantPrgmnm;

    public String getEvtGradeTarget() {
        return evtGradeTarget;
    }

    public void setEvtGradeTarget(String evtGradeTarget) {
        this.evtGradeTarget = evtGradeTarget;
    }

    public String getEvtWantPrgmnm() {
        return evtWantPrgmnm;
    }

    public void setEvtWantPrgmnm(String evtWantPrgmnm) {
        this.evtWantPrgmnm = evtWantPrgmnm;
    }

    private int dcAnnualLimit;

    private int ticketChkCnt;

    private String evtPlacenm;

    private String interStatus;

    private String evtRsvnApptypeNm;

    private String cancelDate;

    private String inputDate;

    public String getEvtRsvnIdx() {
        return evtRsvnIdx;
    }

    public void setEvtRsvnIdx(String evtRsvnIdx) {
        this.evtRsvnIdx = evtRsvnIdx;
    }

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public String getEvtNo() {
        return evtNo;
    }

    public void setEvtNo(String evtNo) {
        this.evtNo = evtNo;
    }

    public String getEvtPartcd() {
        return evtPartcd;
    }

    public void setEvtPartcd(String evtPartcd) {
        this.evtPartcd = evtPartcd;
    }

    public String getEvtAppdate() {
        return evtAppdate;
    }

    public void setEvtAppdate(String evtAppdate) {
        this.evtAppdate = evtAppdate;
    }

    public String getEvtRsvnno() {
        return evtRsvnno;
    }

    public void setEvtRsvnno(String evtRsvnno) {
        this.evtRsvnno = evtRsvnno;
    }

    public String getEvtRsvnMemtype() {
        return evtRsvnMemtype;
    }

    public void setEvtRsvnMemtype(String evtRsvnMemtype) {
        this.evtRsvnMemtype = evtRsvnMemtype;
    }

    public String getEvtRsvnMemno() {
        return evtRsvnMemno;
    }

    public void setEvtRsvnMemno(String evtRsvnMemno) {
        this.evtRsvnMemno = evtRsvnMemno;
    }

    public String getEvtRsvnWebid() {
        return evtRsvnWebid;
    }

    public void setEvtRsvnWebid(String evtRsvnWebid) {
        this.evtRsvnWebid = evtRsvnWebid;
    }

    public String getEvtRsvnCustnm() {
        return evtRsvnCustnm;
    }

    public void setEvtRsvnCustnm(String evtRsvnCustnm) {
        this.evtRsvnCustnm = evtRsvnCustnm;
    }

    public String getEvtPersnGbn() {
        return evtPersnGbn;
    }

    public void setEvtPersnGbn(String evtPersnGbn) {
        this.evtPersnGbn = evtPersnGbn;
    }

    public String getEvtRsvnGrpnm() {
        return evtRsvnGrpnm;
    }

    public void setEvtRsvnGrpnm(String evtRsvnGrpnm) {
        this.evtRsvnGrpnm = evtRsvnGrpnm;
    }

    public String getEvtRsvnFax() {
        return evtRsvnFax;
    }

    public void setEvtRsvnFax(String evtRsvnFax) {
        this.evtRsvnFax = evtRsvnFax;
    }

    public String getEvtRsvnTel() {
        return evtRsvnTel;
    }

    public void setEvtRsvnTel(String evtRsvnTel) {
        this.evtRsvnTel = evtRsvnTel;
    }

    public String getEvtRsvnMoblphon() {
        return evtRsvnMoblphon;
    }

    public void setEvtRsvnMoblphon(String evtRsvnMoblphon) {
        this.evtRsvnMoblphon = evtRsvnMoblphon;
    }

    public String getEvtEmail() {
        return evtEmail;
    }

    public void setEvtEmail(String evtEmail) {
        this.evtEmail = evtEmail;
    }

    public String getEvtVisitCustnm() {
        return evtVisitCustnm;
    }

    public void setEvtVisitCustnm(String evtVisitCustnm) {
        this.evtVisitCustnm = evtVisitCustnm;
    }

    public String getEvtVisitMoblphon() {
        return evtVisitMoblphon;
    }

    public void setEvtVisitMoblphon(String evtVisitMoblphon) {
        this.evtVisitMoblphon = evtVisitMoblphon;
    }

    public String getEvtNonmembCertno() {
        return evtNonmembCertno;
    }

    public void setEvtNonmembCertno(String evtNonmembCertno) {
        this.evtNonmembCertno = evtNonmembCertno;
    }

    public String getEvtVeingnmpr() {
        return evtVeingnmpr;
    }

    public void setEvtVeingnmpr(String evtVeingnmpr) {
        this.evtVeingnmpr = evtVeingnmpr;
    }

    public String getEvtVeingdate() {
        return evtVeingdate;
    }

    public void setEvtVeingdate(String evtVeingdate) {
        this.evtVeingdate = evtVeingdate;
    }

    public String getEvtRsvnEtc() {
        return evtRsvnEtc;
    }

    public void setEvtRsvnEtc(String evtRsvnEtc) {
        this.evtRsvnEtc = evtRsvnEtc;
    }

    public String getEvtRsvnApptype() {
        return evtRsvnApptype;
    }

    public void setEvtRsvnApptype(String evtRsvnApptype) {
        this.evtRsvnApptype = evtRsvnApptype;
    }

    public String getEvtRsvnSaleamt() {
        return evtRsvnSaleamt;
    }

    public void setEvtRsvnSaleamt(String evtRsvnSaleamt) {
        this.evtRsvnSaleamt = evtRsvnSaleamt;
    }

    public String getEvtRsvnPayamt() {
        return evtRsvnPayamt;
    }

    public void setEvtRsvnPayamt(String evtRsvnPayamt) {
        this.evtRsvnPayamt = evtRsvnPayamt;
    }

    public String getEvtRsvnPaydate() {
        return evtRsvnPaydate;
    }

    public void setEvtRsvnPaydate(String evtRsvnPaydate) {
        this.evtRsvnPaydate = evtRsvnPaydate;
    }

    public String getEvtRsvnRetyn() {
        return evtRsvnRetyn;
    }

    public void setEvtRsvnRetyn(String evtRsvnRetyn) {
        this.evtRsvnRetyn = evtRsvnRetyn;
    }

    public String getEvtReceiptno() {
        return evtReceiptno;
    }

    public void setEvtReceiptno(String evtReceiptno) {
        this.evtReceiptno = evtReceiptno;
    }

    public String getEvtCancelMemo() {
        return evtCancelMemo;
    }

    public void setEvtCancelMemo(String evtCancelMemo) {
        this.evtCancelMemo = evtCancelMemo;
    }

    public String getEvtOnoffintype() {
        return evtOnoffintype;
    }

    public void setEvtOnoffintype(String evtOnoffintype) {
        this.evtOnoffintype = evtOnoffintype;
    }

    public String getEvtOnoffpyntype() {
        return evtOnoffpyntype;
    }

    public void setEvtOnoffpyntype(String evtOnoffpyntype) {
        this.evtOnoffpyntype = evtOnoffpyntype;
    }

    public String getEvtTrmnltype() {
        return evtTrmnltype;
    }

    public void setEvtTrmnltype(String evtTrmnltype) {
        this.evtTrmnltype = evtTrmnltype;
    }

    public String getEvtIndvdlinfoAgrid() {
        return evtIndvdlinfoAgrid;
    }

    public void setEvtIndvdlinfoAgrid(String evtIndvdlinfoAgrid) {
        this.evtIndvdlinfoAgrid = evtIndvdlinfoAgrid;
    }

    public String getEvtIndvdlinfoAgryn() {
        return evtIndvdlinfoAgryn;
    }

    public void setEvtIndvdlinfoAgryn(String evtIndvdlinfoAgryn) {
        this.evtIndvdlinfoAgryn = evtIndvdlinfoAgryn;
    }

    public String getEvtAgreeid() {
        return evtAgreeid;
    }

    public void setEvtAgreeid(String evtAgreeid) {
        this.evtAgreeid = evtAgreeid;
    }

    public String getEvtAgreeyn() {
        return evtAgreeyn;
    }

    public void setEvtAgreeyn(String evtAgreeyn) {
        this.evtAgreeyn = evtAgreeyn;
    }

    public String getEvtRefndAgrid() {
        return evtRefndAgrid;
    }

    public void setEvtRefndAgrid(String evtRefndAgrid) {
        this.evtRefndAgrid = evtRefndAgrid;
    }

    public String getEvtRefndAgryn() {
        return evtRefndAgryn;
    }

    public void setEvtRefndAgryn(String evtRefndAgryn) {
        this.evtRefndAgryn = evtRefndAgryn;
    }

    public Date getEvtPaywaitEnddatetime() {
        return evtPaywaitEnddatetime;
    }

    public void setEvtPaywaitEnddatetime(Date evtPaywaitEnddatetime) {
        this.evtPaywaitEnddatetime = evtPaywaitEnddatetime;
    }

    public String getEvtVisitcarno() {
        return evtVisitcarno;
    }

    public void setEvtVisitcarno(String evtVisitcarno) {
        this.evtVisitcarno = evtVisitcarno;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public String getReguser() {
        return reguser;
    }

    public void setReguser(String reguser) {
        this.reguser = reguser;
    }

    public Date getModdate() {
        return moddate;
    }

    public void setModdate(Date moddate) {
        this.moddate = moddate;
    }

    public String getModuser() {
        return moduser;
    }

    public void setModuser(String moduser) {
        this.moduser = moduser;
    }

    public String getEvtTimeseq() {
        return evtTimeseq;
    }

    public void setEvtTimeseq(String evtTimeseq) {
        this.evtTimeseq = evtTimeseq;
    }

    public String getEvtRsvnTimeidx() {
        return evtRsvnTimeidx;
    }

    public void setEvtRsvnTimeidx(String evtRsvnTimeidx) {
        this.evtRsvnTimeidx = evtRsvnTimeidx;
    }

    public String getDbEnckey() {
        return dbEnckey;
    }

    public void setDbEnckey(String dbEnckey) {
        this.dbEnckey = dbEnckey;
    }

    public EvtrsvnTimeVO getTimeVO() {
        return timeVO;
    }

    public void setTimeVO(EvtrsvnTimeVO timeVO) {
        this.timeVO = timeVO;
    }

    public List<EvtItemAmountVO> getChargeList() {
        return chargeList;
    }

    public void setChargeList(List<EvtItemAmountVO> chargeList) {
        this.chargeList = chargeList;
    }

    public List<EvtRsvnItemVO> getItemList() {
        return itemList;
    }

    public void setItemList(List<EvtRsvnItemVO> itemList) {
        this.itemList = itemList;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtStime() {
        return evtStime;
    }

    public void setEvtStime(String evtStime) {
        this.evtStime = evtStime;
    }

    public String getEvtEtime() {
        return evtEtime;
    }

    public void setEvtEtime(String evtEtime) {
        this.evtEtime = evtEtime;
    }

    public long getAmtSum() {
        return amtSum;
    }

    public void setAmtSum(long amtSum) {
        this.amtSum = amtSum;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getTotCount() {
        return totCount;
    }

    public void setTotCount(int totCount) {
        this.totCount = totCount;
    }

    public String getEvtApptypeNm() {
        return evtApptypeNm;
    }

    public void setEvtApptypeNm(String evtApptypeNm) {
        this.evtApptypeNm = evtApptypeNm;
    }

    public String getEvtTimestdSeq() {
        return evtTimestdSeq;
    }

    public void setEvtTimestdSeq(String evtTimestdSeq) {
        this.evtTimestdSeq = evtTimestdSeq;
    }

    public String getCtgCd() {
        return ctgCd;
    }

    public void setCtgCd(String ctgCd) {
        this.ctgCd = ctgCd;
    }

    public String getEvtTime() {
        return evtTime;
    }

    public void setEvtTime(String evtTime) {
        this.evtTime = evtTime;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }

    public String getItemCd() {
        return itemCd;
    }

    public void setItemCd(String itemCd) {
        this.itemCd = itemCd;
    }

    public String getEvtPartnm() {
        return evtPartnm;
    }

    public void setEvtPartnm(String evtPartnm) {
        this.evtPartnm = evtPartnm;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getTicketChkyn() {
        return ticketChkyn;
    }

    public void setTicketChkyn(String ticketChkyn) {
        this.ticketChkyn = ticketChkyn;
    }

    public Date getPayregdate() {
        return payregdate;
    }

    public void setPayregdate(Date payregdate) {
        this.payregdate = payregdate;
    }

    public int getRfndSeq() {
        return rfndSeq;
    }

    public void setRfndSeq(int rfndSeq) {
        this.rfndSeq = rfndSeq;
    }

    public CamelMap getRfndRule() {
        return rfndRule;
    }

    public void setRfndRule(CamelMap rfndRule) {
        this.rfndRule = rfndRule;
    }

    public String getCancelAbleYn() {
        return cancelAbleYn;
    }

    public void setCancelAbleYn(String cancelAbleYn) {
        this.cancelAbleYn = cancelAbleYn;
    }

    public long getCancelAmt() {
        return cancelAmt;
    }

    public void setCancelAmt(long cancelAmt) {
        this.cancelAmt = cancelAmt;
    }

    public String getRfndRate() {
        return rfndRate;
    }

    public void setRfndRate(String rfndRate) {
        this.rfndRate = rfndRate;
    }

    public long getBreakAmt() {
        return breakAmt;
    }

    public void setBreakAmt(long breakAmt) {
        this.breakAmt = breakAmt;
    }

    public String getPayWaitTime() {
        return payWaitTime;
    }

    public void setPayWaitTime(String payWaitTime) {
        this.payWaitTime = payWaitTime;
    }

    public String getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }

    public String getGuideTelno() {
        return guideTelno;
    }

    public void setGuideTelno(String guideTelno) {
        this.guideTelno = guideTelno;
    }

    public String getEditYn() {
        return editYn;
    }

    public void setEditYn(String editYn) {
        this.editYn = editYn;
    }

    public String getGrpMinCnt() {
        return grpMinCnt;
    }

    public void setGrpMinCnt(String grpMinCnt) {
        this.grpMinCnt = grpMinCnt;
    }

    public String getGrpMaxCnt() {
        return grpMaxCnt;
    }

    public void setGrpMaxCnt(String grpMaxCnt) {
        this.grpMaxCnt = grpMaxCnt;
    }

    public int getDcAnnualLimit() {
        return dcAnnualLimit;
    }

    public void setDcAnnualLimit(int dcAnnualLimit) {
        this.dcAnnualLimit = dcAnnualLimit;
    }

    public int getTicketChkCnt() {
        return ticketChkCnt;
    }

    public void setTicketChkCnt(int ticketChkCnt) {
        this.ticketChkCnt = ticketChkCnt;
    }

    public String getEvtPlacenm() {
        return evtPlacenm;
    }

    public void setEvtPlacenm(String evtPlacenm) {
        this.evtPlacenm = evtPlacenm;
    }

    public String getInterStatus() {
        return interStatus;
    }

    public void setInterStatus(String interStatus) {
        this.interStatus = interStatus;
    }

    public String getEvtRsvnApptypeNm() {
        return evtRsvnApptypeNm;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public void setEvtRsvnApptypeNm(String evtRsvnApptypeNm) {
        this.evtRsvnApptypeNm = evtRsvnApptypeNm;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
