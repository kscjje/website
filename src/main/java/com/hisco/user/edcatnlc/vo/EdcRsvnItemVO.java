package com.hisco.user.edcatnlc.vo;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

/**
 * @Class Name : EvtRsvnItemVO.java
 * @Description : 강연/행사/영화 예약상세정보 VO
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 9. 04 김희택
 * @author 김희택
 * @since 2020. 9. 04
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class EdcRsvnItemVO implements Serializable {

    private String evtRsvnIdx;// 강연,행사,영화 예약고유번호
    private String evtSaleSeq;// 매표품목순번
    private String comcd;// 기관고유번호
    private String evtRsvnItemcd;// 강연,행사,영화 예약품목고유번호
    private long evtCost;// 단가
    private long evtRsvnItemcnt;// 수량(인원)
    private String evtMaleCnt;// 남자인원(확장성)
    private String evtFemaleCnt;// 여자인원(확장성)
    private long evtSalamt;// 금액
    private String evtDcType;// 할인사유종류
    private String evtDcCpnno;// 할인쿠폰번호
    private String evtEventDcid;// 이벤트할인ID
    private long evtDcRate;// 할인율
    private long evtDcamt;// 할인금액
    private long evtTotamt;// 합계금액
    private String evtOnoffIntype;// 온오프라인등록구분
    private String returnYn;// 환불여부
    private String cancelYn;// 취소여부
    private String slipNo;// 정산번호
    private String evtTicketNo;// 강연,행사,영화 티켓번호
    private String evtEtc;// 기타사항및세부내역
    private String evtTerminalType;// 등록터미널타입
    private Date regdate;// 등록일시
    private String reguser;// 등록자
    private Date moddate;// 수정일시
    private String moduser;// 수정자

    private long itemSubCnt;// 할인 수량
    private String dcName; // 할인명
    private String cpnName; // 쿠폰명
    private String dcTypeNm; // 할인 타입명
    private String dcKindCd; // 할인 타입
    private String dcCpnno; // 쿠폰 코드추가
    private String dcEventId;// 이벤트 코드 추가

    // PARAM
    private String evtItemNm;

    public String getEvtRsvnIdx() {
        return evtRsvnIdx;
    }

    public void setEvtRsvnIdx(String evtRsvnIdx) {
        this.evtRsvnIdx = evtRsvnIdx;
    }

    public String getEvtSaleSeq() {
        return evtSaleSeq;
    }

    public void setEvtSaleSeq(String evtSaleSeq) {
        this.evtSaleSeq = evtSaleSeq;
    }

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public String getEvtRsvnItemcd() {
        return evtRsvnItemcd;
    }

    public void setEvtRsvnItemcd(String evtRsvnItemcd) {
        this.evtRsvnItemcd = evtRsvnItemcd;
    }

    public long getEvtCost() {
        return evtCost;
    }

    public void setEvtCost(long evtCost) {
        this.evtCost = evtCost;
    }

    public long getEvtRsvnItemcnt() {
        return evtRsvnItemcnt;
    }

    public void setEvtRsvnItemcnt(long evtRsvnItemcnt) {
        this.evtRsvnItemcnt = evtRsvnItemcnt;
    }

    public String getEvtMaleCnt() {
        return evtMaleCnt;
    }

    public void setEvtMaleCnt(String evtMaleCnt) {
        this.evtMaleCnt = evtMaleCnt;
    }

    public String getEvtFemaleCnt() {
        return evtFemaleCnt;
    }

    public void setEvtFemaleCnt(String evtFemaleCnt) {
        this.evtFemaleCnt = evtFemaleCnt;
    }

    public long getEvtSalamt() {
        return evtSalamt;
    }

    public void setEvtSalamt(long evtSalamt) {
        this.evtSalamt = evtSalamt;
    }

    public String getEvtDcType() {
        return evtDcType;
    }

    public void setEvtDcType(String evtDcType) {
        this.evtDcType = evtDcType;
    }

    public String getEvtDcCpnno() {
        return evtDcCpnno;
    }

    public void setEvtDcCpnno(String evtDcCpnno) {
        this.evtDcCpnno = evtDcCpnno;
    }

    public String getEvtEventDcid() {
        return evtEventDcid;
    }

    public void setEvtEventDcid(String evtEventDcid) {
        this.evtEventDcid = evtEventDcid;
    }

    public long getEvtDcRate() {
        return evtDcRate;
    }

    public void setEvtDcRate(long evtDcRate) {
        this.evtDcRate = evtDcRate;
    }

    public long getEvtDcamt() {
        return evtDcamt;
    }

    public void setEvtDcamt(long evtDcamt) {
        this.evtDcamt = evtDcamt;
    }

    public long getEvtTotamt() {
        return evtTotamt;
    }

    public void setEvtTotamt(long evtTotamt) {
        this.evtTotamt = evtTotamt;
    }

    public String getEvtOnoffIntype() {
        return evtOnoffIntype;
    }

    public void setEvtOnoffIntype(String evtOnoffIntype) {
        this.evtOnoffIntype = evtOnoffIntype;
    }

    public String getReturnYn() {
        return returnYn;
    }

    public void setReturnYn(String returnYn) {
        this.returnYn = returnYn;
    }

    public String getCancelYn() {
        return cancelYn;
    }

    public void setCancelYn(String cancelYn) {
        this.cancelYn = cancelYn;
    }

    public String getSlipNo() {
        return slipNo;
    }

    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo;
    }

    public String getEvtTicketNo() {
        return evtTicketNo;
    }

    public void setEvtTicketNo(String evtTicketNo) {
        this.evtTicketNo = evtTicketNo;
    }

    public String getEvtEtc() {
        return evtEtc;
    }

    public void setEvtEtc(String evtEtc) {
        this.evtEtc = evtEtc;
    }

    public String getEvtTerminalType() {
        return evtTerminalType;
    }

    public void setEvtTerminalType(String evtTerminalType) {
        this.evtTerminalType = evtTerminalType;
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

    public String getEvtItemNm() {
        return evtItemNm;
    }

    public void setEvtItemNm(String evtItemNm) {
        this.evtItemNm = evtItemNm;
    }

    public long getItemSubCnt() {
        return itemSubCnt;
    }

    public void setItemSubCnt(long itemSubCnt) {
        this.itemSubCnt = itemSubCnt;
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

    public String getDcKindCd() {
        return dcKindCd;
    }

    public void setDcKindCd(String dcKindCd) {
        this.dcKindCd = dcKindCd;
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

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
