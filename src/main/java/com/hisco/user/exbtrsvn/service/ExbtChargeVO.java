package com.hisco.user.exbtrsvn.service;

/**
 * 관람 가격 VO
 * 
 * @author 진수진
 * @since 2020.09.01
 * @version 1.0, 2020.08.31
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.08.31 최초작성
 */
public class ExbtChargeVO {
    /* 기관코드 */
    private String comcd;

    /* 기준 코드 */
    private String exbtSeq;

    // 세부내역 코드
    private String saleSeq;

    /* 품목코드 */
    private String itemCd;

    /* 품목명 */
    private String itemNm;

    /* 상품 금액 */
    private long amount;

    /* 최종 결제금액 */
    private long totAmount;

    /* 할인 금액 */
    private long dcAmount;

    /* 할인 율 */
    private long dcRate;

    /* 할인 타입 */
    private String dcType;

    /* 쿠폰 번호 */
    private String dcCpnno;

    /* 할인 이벤트 코드 */
    private String dcEventId;

    /* 티켓 번호 */
    private String ticketNo;

    /* 가격 */
    private long price;

    /* 수량 */
    private long itemCnt;

    /* 수량 */
    private long itemSubCnt;

    /* 예약 코드 */
    private String rsvnIdx;

    /* 온라인 , 모바일 (2001 , 2002 */
    private String terminalType;

    /* 할인명 */
    private String dcName;

    /* 쿠폰명 */
    private String cpnName;

    /* 할인 사유명 */
    private String dcTypeNm;

    /* 할인 종류 */
    private String dcKindCd;

    /* 연간회원 할인 */
    private String dcAnnualCd;

    private String dcAnnualNm;

    private int dcAnnualRate;

    private int dcAnnualLimit;

    /* 등록자 */
    private String reguser;

    /* 매출번호 */
    private long selngId;

    /* 환불금액 */
    private long cancelAmt;

    /* 공제금액 */
    private long breakAmt;

    /* 부과세 여부 */
    private String vatYn;

    /* 아이템별 결제 금액 */
    private long itemAmount;

    /* 개인/단체 구분 1001 , 2001 */
    private String groupGbn;

    /* 단체 할인 여부 */
    private String groupdcYn;

    /* 성인 여부 */
    private String adultYn;

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

    public String getItemCd() {
        return itemCd;
    }

    public void setItemCd(String itemCd) {
        this.itemCd = itemCd;
    }

    public String getItemNm() {
        return itemNm;
    }

    public void setItemNm(String itemNm) {
        this.itemNm = itemNm;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getItemCnt() {
        return itemCnt;
    }

    public void setItemCnt(long itemCnt) {
        this.itemCnt = itemCnt;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getTotAmount() {
        return totAmount;
    }

    public void setTotAmount(long totAmount) {
        this.totAmount = totAmount;
    }

    public String getRsvnIdx() {
        return rsvnIdx;
    }

    public void setRsvnIdx(String rsvnIdx) {
        this.rsvnIdx = rsvnIdx;
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

    public long getDcAmount() {
        return dcAmount;
    }

    public void setDcAmount(long dcAmount) {
        this.dcAmount = dcAmount;
    }

    public long getDcRate() {
        return dcRate;
    }

    public void setDcRate(long dcRate) {
        this.dcRate = dcRate;
    }

    public String getDcType() {
        return dcType;
    }

    public void setDcType(String dcType) {
        this.dcType = dcType;
    }

    public String getDcCpnno() {
        return dcCpnno;
    }

    public void setDcCpnno(String dcCpnno) {
        this.dcCpnno = dcCpnno;
    }

    public String getDcEventId() {
        return dcEventId;
    }

    public void setDcEventId(String dcEventId) {
        this.dcEventId = dcEventId;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getDcName() {
        return dcName;
    }

    public void setDcName(String dcName) {
        this.dcName = dcName;
    }

    public String getCpnName() {
        return cpnName;
    }

    public void setCpnName(String cpnName) {
        this.cpnName = cpnName;
    }

    public String getDcTypeNm() {
        return dcTypeNm;
    }

    public void setDcTypeNm(String dcTypeNm) {
        this.dcTypeNm = dcTypeNm;
    }

    public String getSaleSeq() {
        return saleSeq;
    }

    public void setSaleSeq(String saleSeq) {
        this.saleSeq = saleSeq;
    }

    public long getItemSubCnt() {
        return itemSubCnt;
    }

    public void setItemSubCnt(long itemSubCnt) {
        this.itemSubCnt = itemSubCnt;
    }

    public String getDcKindCd() {
        return dcKindCd;
    }

    public void setDcKindCd(String dcKindCd) {
        this.dcKindCd = dcKindCd;
    }

    public String getGroupGbn() {
        return groupGbn;
    }

    public void setGroupGbn(String groupGbn) {
        this.groupGbn = groupGbn;
    }

    public long getSelngId() {
        return selngId;
    }

    public void setSelngId(long selngId) {
        this.selngId = selngId;
    }

    public long getCancelAmt() {
        return cancelAmt;
    }

    public void setCancelAmt(long cancelAmt) {
        this.cancelAmt = cancelAmt;
    }

    public String getVatYn() {
        return vatYn;
    }

    public void setVatYn(String vatYn) {
        this.vatYn = vatYn;
    }

    public long getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(long itemAmount) {
        this.itemAmount = itemAmount;
    }

    public long getBreakAmt() {
        return breakAmt;
    }

    public void setBreakAmt(long breakAmt) {
        this.breakAmt = breakAmt;
    }

    public String getGroupdcYn() {
        return groupdcYn;
    }

    public void setGroupdcYn(String groupdcYn) {
        this.groupdcYn = groupdcYn;
    }

    public String getDcAnnualCd() {
        return dcAnnualCd;
    }

    public void setDcAnnualCd(String dcAnnualCd) {
        this.dcAnnualCd = dcAnnualCd;
    }

    public String getDcAnnualNm() {
        return dcAnnualNm;
    }

    public void setDcAnnualNm(String dcAnnualNm) {
        this.dcAnnualNm = dcAnnualNm;
    }

    public int getDcAnnualRate() {
        return dcAnnualRate;
    }

    public void setDcAnnualRate(int dcAnnualRate) {
        this.dcAnnualRate = dcAnnualRate;
    }

    public int getDcAnnualLimit() {
        return dcAnnualLimit;
    }

    public void setDcAnnualLimit(int dcAnnualLimit) {
        this.dcAnnualLimit = dcAnnualLimit;
    }

    public String getAdultYn() {
        return adultYn;
    }

    public void setAdultYn(String adultYn) {
        this.adultYn = adultYn;
    }

}
