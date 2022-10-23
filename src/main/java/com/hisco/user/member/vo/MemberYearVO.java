package com.hisco.user.member.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

/**
 * @Class Name : MemberYearVO.java
 * @Description : 연회원 정보 VO 클래스
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
public class MemberYearVO implements Serializable {

    private String comcd /* 기관고유번호 */;
    private String memNo /* 회원번호 */;
    private String id /* 아이디 */;
    private String memNm; // 이름

    private String itemCd /* 연회원 상품 아이디 */;
    private String itemNm /* 연회원 상품 이름 */;
    private long saleamt /* 연회원 상품 가격 */;
    private String startYmd /* 연회원 시작기간 */;
    private String endYmd /* 연회원 종료기간 */;
    private int monthCnt; // 연간회원 개월수

    private String paymentId;
    private String paymentPw;

    private String rgistGbn; // 등록구분 1001 신규 ,1101 재등록
    private String selngId; // 매출고유번호
    private String slipNo; // 정산번호

    private String validYn;// 유효성 여부
    private String applyDate;// 갱신 가능 시작일
    private int remainDate; // 남은 날짜
    private String dscCode; /* 할인코드 */
    private long dscRate; /* 할인율 */

    private String familyAddYn;// 가족구성원연회원가입영부
    private String familyMemNo;// 기존가족대표회원번호
    // 프로시져 관련
    private long memYearSeq; /* 시퀀스 */
    private String partGbn; /* 사업장구분 */
    private String retOid; /* 주문번호 */
    private String retMsg; /* 리턴 메시지 */
    private String retCd; /* 리턴 코드 */
    private List<?> retCursor; /* 리턴 커서 */
    private String terminalType; /* 2001 : 온라인WEB , 2002: 모바일 */

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

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public String getMemNo() {
        return memNo;
    }

    public void setMemNo(String memNo) {
        this.memNo = memNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public long getSaleamt() {
        return saleamt;
    }

    public void setSaleamt(long saleamt) {
        this.saleamt = saleamt;
    }

    public String getStartYmd() {
        return startYmd;
    }

    public void setStartYmd(String startYmd) {
        this.startYmd = startYmd;
    }

    public String getEndYmd() {
        return endYmd;
    }

    public void setEndYmd(String endYmd) {
        this.endYmd = endYmd;
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

    public int getMonthCnt() {
        return monthCnt;
    }

    public void setMonthCnt(int monthCnt) {
        this.monthCnt = monthCnt;
    }

    public String getRgistGbn() {
        return rgistGbn;
    }

    public void setRgistGbn(String rgistGbn) {
        this.rgistGbn = rgistGbn;
    }

    public String getSelngId() {
        return selngId;
    }

    public void setSelngId(String selngId) {
        this.selngId = selngId;
    }

    public String getSlipNo() {
        return slipNo;
    }

    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo;
    }

    public String getValidYn() {
        return validYn;
    }

    public void setValidYn(String validYn) {
        this.validYn = validYn;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public int getRemainDate() {
        return remainDate;
    }

    public void setRemainDate(int remainDate) {
        this.remainDate = remainDate;
    }

    public String getCST_MID() {
        return cstMID;
    }

    public void setCST_MID(String cstMID) {
        this.cstMID = cstMID;
    }

    public String getCST_PW() {
        return cstPW;
    }

    public void setCST_PW(String cstPW) {
        this.cstPW = cstPW;
    }

    public String getCST_PLATFORM() {
        return cstPLATFORM;
    }

    public void setCST_PLATFORM(String cstPLATFORM) {
        this.cstPLATFORM = cstPLATFORM;
    }

    public String getLgdBUYER() {
        return lgdBUYER;
    }

    public void setLgdBUYER(String lgdBUYER) {
        this.lgdBUYER = lgdBUYER;
    }

    public String getLgdPRODUCTINFO() {
        return lgdPRODUCTINFO;
    }

    public void setLgdPRODUCTINFO(String lgdPRODUCTINFO) {
        this.lgdPRODUCTINFO = lgdPRODUCTINFO;
    }

    public String getLgdAMOUNT() {
        return lgdAMOUNT;
    }

    public void setLgdAMOUNT(String lgdAMOUNT) {
        this.lgdAMOUNT = lgdAMOUNT;
    }

    public String getLgdBUYEREMAIL() {
        return lgdBUYEREMAIL;
    }

    public void setLgdBUYEREMAIL(String lgdBUYEREMAIL) {
        this.lgdBUYEREMAIL = lgdBUYEREMAIL;
    }

    public String getLgdOID() {
        return this.lgdOID;
    }

    public void setLgdOID(String lgdOID) {
        this.lgdOID = lgdOID;
    }

    public String getLgdTIMESTAMP() {
        return lgdTIMESTAMP;
    }

    public void setLgdTIMESTAMP(String lgdTIMESTAMP) {
        this.lgdTIMESTAMP = lgdTIMESTAMP;
    }

    public String getLgdCUSTOM_FIRSTPAY() {
        return lgdCUSTOMFIRSTPAY;
    }

    public void setLgdCUSTOM_FIRSTPAY(String lGDCUSTOMFIRSTPAY) {
        this.lgdCUSTOMFIRSTPAY = lGDCUSTOMFIRSTPAY;
    }

    public String getMemNm() {
        return memNm;
    }

    public void setMemNm(String memNm) {
        this.memNm = memNm;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentPw() {
        return paymentPw;
    }

    public void setPaymentPw(String paymentPw) {
        this.paymentPw = paymentPw;
    }

    public String getLgdHASHDATA() {
        return lgdHASHDATA;
    }

    public void setLgdHASHDATA(String lgdHASHDATA) {
        this.lgdHASHDATA = lgdHASHDATA;
    }

    public String getLgdRETURNURL() {
        return lgdRETURNURL;
    }

    public void setLgdRETURNURL(String lgdRETURNURL) {
        this.lgdRETURNURL = lgdRETURNURL;
    }

    public String getLgdMID() {
        return lgdMID;
    }

    public void setLgdMID(String lgdMID) {
        this.lgdMID = lgdMID;
    }

    public String getLgdCASNOTEURL() {
        return lgdCASNOTEURL;
    }

    public void setLgdCASNOTEURL(String lgdCASNOTEURL) {
        this.lgdCASNOTEURL = lgdCASNOTEURL;
    }

    public String getLgdBUYERID() {
        return lgdBUYERID;
    }

    public void setLgdBUYERID(String lgdBUYERID) {
        this.lgdBUYERID = lgdBUYERID;
    }

    public String getLgdPRODUCTCODE() {
        return lgdPRODUCTCODE;
    }

    public void setLgdPRODUCTCODE(String lgdPRODUCTCODE) {
        this.lgdPRODUCTCODE = lgdPRODUCTCODE;
    }

    public long getMemYearSeq() {
        return memYearSeq;
    }

    public void setMemYearSeq(long memYearSeq) {
        this.memYearSeq = memYearSeq;
    }

    public String getPartGbn() {
        return partGbn;
    }

    public void setPartGbn(String partGbn) {
        this.partGbn = partGbn;
    }

    public String getRetOid() {
        return retOid;
    }

    public void setRetOid(String retOid) {
        this.retOid = retOid;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getRetCd() {
        return retCd;
    }

    public void setRetCd(String retCd) {
        this.retCd = retCd;
    }

    public List<?> getRetCursor() {
        return retCursor;
    }

    public void setRetCursor(List<?> retCursor) {
        this.retCursor = retCursor;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getDscCode() {
        return dscCode;
    }

    public void setDscCode(String dscCode) {
        this.dscCode = dscCode;
    }

    public long getDscRate() {
        return dscRate;
    }

    public void setDscRate(long dscRate) {
        this.dscRate = dscRate;
    }

    public String getFamilyAddYn() {
        return familyAddYn;
    }

    public void setFamilyAddYn(String familyAddYn) {
        this.familyAddYn = familyAddYn;
    }

    public String getFamilyMemNo() {
        return familyMemNo;
    }

    public void setFamilyMemNo(String familyMemNo) {
        this.familyMemNo = familyMemNo;
    }

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
