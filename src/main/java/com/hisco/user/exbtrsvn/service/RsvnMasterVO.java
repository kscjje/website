package com.hisco.user.exbtrsvn.service;

import java.sql.Date;
import java.util.List;

import com.hisco.cmm.object.CamelMap;

/**
 * 관람 예약 VO
 * 
 * @author 진수진
 * @since 2020.09.01
 * @version 1.0, 2020.09.01
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.09.01 최초작성
 */
public class RsvnMasterVO {
    /* 기관코드 */
    private String comcd;

    /* 관람 제목 */
    private String exbtName;

    /* 관람관 명칭 */
    private String partNm;

    /* 관람 타입 */
    private String exbtType;

    /* 관람 타입 명 */
    private String exbtTypeNm;

    /* 관람기준 코드 */
    private String exbtSeq;

    /* 관람 회차 코드 */
    private String timeseq;

    /* 시작시간 */
    private String startTime;

    /* 관람 시간 코드 */
    private String stdseq;

    /* 관람 일자 */
    private String ymd;

    /* 관람 요일 */
    private String dayWeek;

    /* 개인/단체 구분 */
    private String target;

    /* 회원/비회원 구분 */
    private String memType;

    /* 회원코드 */
    private String memNo;

    /* 회원아이디 */
    private String webId;

    /* 휴대폰 */
    private String exbtHp;

    /* 이메일 */
    private String exbtEmail;

    /* 이름 */
    private String custNm;

    /* 예약고유번호 */
    private String rsvnIdx;

    /* 예약번호 */
    private String rsvnNo;

    /* 단체명 */
    private String grpnm;

    /* 비회원 휴대폰 본인인증 */
    private String hpcertno;

    /* 이용인원 */
    private int visitnum;

    /* 기타 */
    private String etc;

    /* 총금액 */
    private long saleamt;

    /* 결제금액 */
    private long payamt;

    /* 결제일자 */
    private String paydate;

    /* 결제대기 만료일시 */
    private Date paywaitEnddt;

    /* 온라인 , 모바일 (2001 , 2002 */
    private String terminalType;

    /* 등록자 */
    private String reguser;

    /* 암호화 키 */
    private String dbEncKey;

    /* 관람사업장고유번호 */
    private String exbtPartcd;

    /* 등록일자 YMD */
    private String appDate;

    /* 예약상태 */
    private String apptype;

    /* 예약상태 */
    private String exbtApptypeNm;

    /* 환불 여부 */
    private String retyn;
    /* 등록일 */
    private Date regdate;

    /* 수정일 */
    private Date moddate;

    /* 결제일 */
    private Date payregdate;

    /* 시작시간 */
    private String exbtStime;
    /* 종료시간 */
    private String exbtEtime;

    /* 이용 횟수 제한 적용 구분 */
    private String limitMethod;

    /* 방문고객성명 */
    private String visitcustNm;

    /* 방문고객핸드폰 */
    private String visitcustHpno;

    private List<ExbtChargeVO> chargeList;

    private int totCount; // 총게시물 수

    private String oid; // 주문번호

    private String itemCd;

    private String ticketNo; // 티켓번호

    private String ticketChkyn; // 티켓발급대상여부

    private String payWaitTime;// 마감시간

    private String itemInfo; // 구매상품 정보

    private String guideTelno; // 문의처

    private String onoffPaytype;// 온오프 결제 타입 10 , 20 온라인 오프라인

    private String editYn;// 수정 가능여부

    /* 단체최소예약인원 */
    private String grpMinCnt;
    /* 단체최대예약인원 */
    private String grpMaxCnt;

    private int rfndSeq; // 환불 정책
    private CamelMap rfndRule;
    private String cancelAbleYn; // 취소 가능여부
    private long cancelAmt; // 환불 가능 금액
    private String rfndRate; // 환불 %
    private long breakAmt;// 공제 금액

    private int ticketChkCnt;

    private String exbtVistnmpr;

    /* 유료회원 할인 제한 */
    private int dcAnnualLimit;

    private Date cancelDate;

    private String interStatus;

    private String inputDate;

    public String getExbtName() {
        return exbtName;
    }

    public void setExbtName(String exbtName) {
        this.exbtName = exbtName;
    }

    public String getExbtType() {
        return exbtType;
    }

    public void setExbtType(String exbtType) {
        this.exbtType = exbtType;
    }

    public String getExbtTypeNm() {
        return exbtTypeNm;
    }

    public void setExbtTypeNm(String exbtTypeNm) {
        this.exbtTypeNm = exbtTypeNm;
    }

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public String getExbtSeq() {
        return exbtSeq;
    }

    public void setExbtSeq(String exbtSeq) {
        this.exbtSeq = exbtSeq;
    }

    public String getTimeseq() {
        return timeseq;
    }

    public void setTimeseq(String timeseq) {
        this.timeseq = timeseq;
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<ExbtChargeVO> getChargeList() {
        return chargeList;
    }

    public void setChargeList(List<ExbtChargeVO> chargeList) {
        this.chargeList = chargeList;
    }

    public String getMemType() {
        return memType;
    }

    public void setMemType(String memType) {
        this.memType = memType;
    }

    public String getMemNo() {
        return memNo;
    }

    public void setMemNo(String memNo) {
        this.memNo = memNo;
    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

    public String getExbtHp() {
        return exbtHp;
    }

    public void setExbtHp(String exbtHp) {
        this.exbtHp = exbtHp;
    }

    public String getExbtEmail() {
        return exbtEmail;
    }

    public void setExbtEmail(String exbtEmail) {
        this.exbtEmail = exbtEmail;
    }

    public String getCustNm() {
        return custNm;
    }

    public void setCustNm(String custNm) {
        this.custNm = custNm;
    }

    public String getRsvnIdx() {
        return rsvnIdx;
    }

    public void setRsvnIdx(String rsvnIdx) {
        this.rsvnIdx = rsvnIdx;
    }

    public String getRsvnNo() {
        return rsvnNo;
    }

    public void setRsvnNo(String rsvnNo) {
        this.rsvnNo = rsvnNo;
    }

    public String getGrpnm() {
        return grpnm;
    }

    public void setGrpnm(String grpnm) {
        this.grpnm = grpnm;
    }

    public String getHpcertno() {
        return hpcertno;
    }

    public void setHpcertno(String hpcertno) {
        this.hpcertno = hpcertno;
    }

    public int getVisitnum() {
        return visitnum;
    }

    public void setVisitnum(int visitnum) {
        this.visitnum = visitnum;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public long getSaleamt() {
        return saleamt;
    }

    public void setSaleamt(long saleamt) {
        this.saleamt = saleamt;
    }

    public long getPayamt() {
        return payamt;
    }

    public void setPayamt(long payamt) {
        this.payamt = payamt;
    }

    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public Date getPaywaitEnddt() {
        return paywaitEnddt;
    }

    public void setPaywaitEnddt(Date paywaitEnddt) {
        this.paywaitEnddt = paywaitEnddt;
    }

    public String getDbEncKey() {
        return dbEncKey;
    }

    public void setDbEncKey(String dbEncKey) {
        this.dbEncKey = dbEncKey;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getReguser() {
        return reguser;
    }

    public void setReguser(String reguser) {
        this.reguser = reguser;
    }

    public String getStdseq() {
        return stdseq;
    }

    public void setStdseq(String stdseq) {
        this.stdseq = stdseq;
    }

    public String getExbtPartcd() {
        return exbtPartcd;
    }

    public void setExbtPartcd(String exbtPartcd) {
        this.exbtPartcd = exbtPartcd;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getApptype() {
        return apptype;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype;
    }

    public String getRetyn() {
        return retyn;
    }

    public void setRetyn(String retyn) {
        this.retyn = retyn;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public String getExbtStime() {
        return exbtStime;
    }

    public void setExbtStime(String exbtStime) {
        this.exbtStime = exbtStime;
    }

    public String getExbtEtime() {
        return exbtEtime;
    }

    public void setExbtEtime(String exbtEtime) {
        this.exbtEtime = exbtEtime;
    }

    public String getLimitMethod() {
        return limitMethod;
    }

    public void setLimitMethod(String limitMethod) {
        this.limitMethod = limitMethod;
    }

    public String getVisitcustNm() {
        return visitcustNm;
    }

    public void setVisitcustNm(String visitcustNm) {
        this.visitcustNm = visitcustNm;
    }

    public String getVisitcustHpno() {
        return visitcustHpno;
    }

    public void setVisitcustHpno(String visitcustHpno) {
        this.visitcustHpno = visitcustHpno;
    }

    public String getPartNm() {
        return partNm;
    }

    public void setPartNm(String partNm) {
        this.partNm = partNm;
    }

    public int getTotCount() {
        return totCount;
    }

    public void setTotCount(int totCount) {
        this.totCount = totCount;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getItemCd() {
        return itemCd;
    }

    public void setItemCd(String itemCd) {
        this.itemCd = itemCd;
    }

    public Date getPayregdate() {
        return payregdate;
    }

    public void setPayregdate(Date payregdate) {
        this.payregdate = payregdate;
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

    public Date getModdate() {
        return moddate;
    }

    public void setModdate(Date moddate) {
        this.moddate = moddate;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public String getOnoffPaytype() {
        return onoffPaytype;
    }

    public void setOnoffPaytype(String onoffPaytype) {
        this.onoffPaytype = onoffPaytype;
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

    public String getEditYn() {
        return editYn;
    }

    public void setEditYn(String editYn) {
        this.editYn = editYn;
    }

    public String getExbtApptypeNm() {
        return exbtApptypeNm;
    }

    public void setExbtApptypeNm(String exbtApptypeNm) {
        this.exbtApptypeNm = exbtApptypeNm;
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

    public String getExbtVistnmpr() {
        return exbtVistnmpr;
    }

    public void setExbtVistnmpr(String exbtVistnmpr) {
        this.exbtVistnmpr = exbtVistnmpr;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getInterStatus() {
        return interStatus;
    }

    public void setInterStatus(String interStatus) {
        this.interStatus = interStatus;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

}
