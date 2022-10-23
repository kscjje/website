package com.hisco.user.exbtrsvn.service;

/**
 * 관람 기준설정 VO
 * 
 * @author 진수진
 * @since 2020.08.31
 * @version 1.0, 2020.08.31
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.08.31 최초작성
 */
public class ExbtBaseruleVO {

    /* 기관 코드 */
    private String comcd;
    /* 관람종류고유번호 */
    private String exbtSeq;
    /* 관람사업장고유번호 */
    private String exbtPartcd;

    /* 관람사업장이름 */
    private String exbtPartNm;

    /* 관람유형 */
    private String exbtType;
    /* 관람유형명 */
    private String exbtTypeNm;
    /* 관람명 */
    private String exbtName;
    /* 예약접수기간운영방법 */
    private String rsvnPerodType;
    /* 예약접수시작일자 */
    private String rsvnAppSdate;
    /* 예약접수시작시간 */
    private String rsvnStartTime;
    /* 예약접수종료일자 */
    private String rsvnAppEdate;
    /* 예약접수종료시간 */
    private String rsvnEndTime;
    /* 예약가능시작일수 */
    private String rsvnFromDays;
    /* 예약가능종료일수 */
    private String rsvnEndDays;
    /* 예약제한횟수적용기준 */
    private String rsvnLimitMethod;
    /* 예약제한횟수 */
    private int rsvnLimitCnt;
    /* 가격정책 */
    private String feeType;
    /* 연간회원예약가능여부 */
    private String rsvnAnualmembyn;
    /* 특별회원예약가능여부 */
    private String rsvnSpeclmembyn;
    /* 일반회원에약가능여부 */
    private String rsvnStdmembyn;
    /* 비회원예약가능여부 */
    private String rsvnNonmebyn;
    /* 온라인당일예약운영여부 */
    private String onlineResyn;
    /* 단체최소예약인원 */
    private String grpMinCnt;
    /* 단체최대예약인원 */
    private String grpMaxCnt;
    /* 관람결제마감시간적용기준 */
    private String exbtPaywaitGbn;
    /* 관람예약결제마감설정시간 */
    private String rsvnPaywaitTime;
    /* 관람티켓노출한줄요약 */
    private String exbtTicketNotice;
    /* 관람티켓검표대상여부 */
    private String exbtTicketChkyn;
    /* 관람시간전검표시메세지노출기준시간 */
    private String exbtBefticketMsgtime;
    /* 관람대표이미지파일ID */
    private String exbtImgFinnb;
    /* 관람소개내용 */
    private String exbtIntrcn;
    /* 관람계획서파일ID */
    private String exbtPlanFinnb;
    /* 관람문의담당전화번호 */
    private String exbtGuideTelno;
    /* 예약가능 여부 */
    private String reserveAbleYn;

    /* 당일 예약 여부 */
    private String todayYn;
    private String useYn;
    private String openyn;

    /* 할인 코드 */
    private String dcReasonCd;
    /* 휴일 여부 체크 */
    private int holdCnt;
    /* 시간표 존재여부 */
    private int timeCnt;

    /* 할인 % */
    private int dcRate;

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

    public String getExbtPartcd() {
        return exbtPartcd;
    }

    public void setExbtPartcd(String exbtPartcd) {
        this.exbtPartcd = exbtPartcd;
    }

    public String getExbtPartNm() {
        return exbtPartNm;
    }

    public void setExbtPartNm(String exbtPartNm) {
        this.exbtPartNm = exbtPartNm;
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

    public String getExbtName() {
        return exbtName;
    }

    public void setExbtName(String exbtName) {
        this.exbtName = exbtName;
    }

    public String getRsvnPerodType() {
        return rsvnPerodType;
    }

    public void setRsvnPerodType(String rsvnPerodType) {
        this.rsvnPerodType = rsvnPerodType;
    }

    public String getRsvnAppSdate() {
        return rsvnAppSdate;
    }

    public void setRsvnAppSdate(String rsvnAppSdate) {
        this.rsvnAppSdate = rsvnAppSdate;
    }

    public String getRsvnStartTime() {
        return rsvnStartTime;
    }

    public void setRsvnStartTime(String rsvnStartTime) {
        this.rsvnStartTime = rsvnStartTime;
    }

    public String getRsvnAppEdate() {
        return rsvnAppEdate;
    }

    public void setRsvnAppEdate(String rsvnAppEdate) {
        this.rsvnAppEdate = rsvnAppEdate;
    }

    public String getRsvnEndTime() {
        return rsvnEndTime;
    }

    public void setRsvnEndTime(String rsvnEndTime) {
        this.rsvnEndTime = rsvnEndTime;
    }

    public String getRsvnFromDays() {
        return rsvnFromDays;
    }

    public void setRsvnFromDays(String rsvnFromDays) {
        this.rsvnFromDays = rsvnFromDays;
    }

    public String getRsvnEndDays() {
        return rsvnEndDays;
    }

    public void setRsvnEndDays(String rsvnEndDays) {
        this.rsvnEndDays = rsvnEndDays;
    }

    public String getRsvnLimitMethod() {
        return rsvnLimitMethod == null ? "" : rsvnLimitMethod;
    }

    public void setRsvnLimitMethod(String rsvnLimitMethod) {
        this.rsvnLimitMethod = rsvnLimitMethod;
    }

    public int getRsvnLimitCnt() {
        return rsvnLimitCnt;
    }

    public void setRsvnLimitCnt(int rsvnLimitCnt) {
        this.rsvnLimitCnt = rsvnLimitCnt;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getRsvnAnualmembyn() {
        return rsvnAnualmembyn;
    }

    public void setRsvnAnualmembyn(String rsvnAnualmembyn) {
        this.rsvnAnualmembyn = rsvnAnualmembyn;
    }

    public String getRsvnSpeclmembyn() {
        return rsvnSpeclmembyn;
    }

    public void setRsvnSpeclmembyn(String rsvnSpeclmembyn) {
        this.rsvnSpeclmembyn = rsvnSpeclmembyn;
    }

    public String getRsvnStdmembyn() {
        return rsvnStdmembyn;
    }

    public void setRsvnStdmembyn(String rsvnStdmembyn) {
        this.rsvnStdmembyn = rsvnStdmembyn;
    }

    public String getRsvnNonmebyn() {
        return rsvnNonmebyn;
    }

    public void setRsvnNonmebyn(String rsvnNonmebyn) {
        this.rsvnNonmebyn = rsvnNonmebyn;
    }

    public String getOnlineResyn() {
        return onlineResyn;
    }

    public void setOnlineResyn(String onlineResyn) {
        this.onlineResyn = onlineResyn;
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

    public String getExbtPaywaitGbn() {
        return exbtPaywaitGbn;
    }

    public void setExbtPaywaitGbn(String exbtPaywaitGbn) {
        this.exbtPaywaitGbn = exbtPaywaitGbn;
    }

    public String getRsvnPaywaitTime() {
        return rsvnPaywaitTime;
    }

    public void setRsvnPaywaitTime(String rsvnPaywaitTime) {
        this.rsvnPaywaitTime = rsvnPaywaitTime;
    }

    public String getExbtTicketNotice() {
        return exbtTicketNotice;
    }

    public void setExbtTicketNotice(String exbtTicketNotice) {
        this.exbtTicketNotice = exbtTicketNotice;
    }

    public String getExbtTicketChkyn() {
        return exbtTicketChkyn;
    }

    public void setExbtTicketChkyn(String exbtTicketChkyn) {
        this.exbtTicketChkyn = exbtTicketChkyn;
    }

    public String getExbtBefticketMsgtime() {
        return exbtBefticketMsgtime;
    }

    public void setExbtBefticketMsgtime(String exbtBefticketMsgtime) {
        this.exbtBefticketMsgtime = exbtBefticketMsgtime;
    }

    public String getExbtImgFinnb() {
        return exbtImgFinnb;
    }

    public void setExbtImgFinnb(String exbtImgFinnb) {
        this.exbtImgFinnb = exbtImgFinnb;
    }

    public String getExbtIntrcn() {
        return exbtIntrcn;
    }

    public void setExbtIntrcn(String exbtIntrcn) {
        this.exbtIntrcn = exbtIntrcn;
    }

    public String getExbtPlanFinnb() {
        return exbtPlanFinnb;
    }

    public void setExbtPlanFinnb(String exbtPlanFinnb) {
        this.exbtPlanFinnb = exbtPlanFinnb;
    }

    public String getExbtGuideTelno() {
        return exbtGuideTelno;
    }

    public void setExbtGuideTelno(String exbtGuideTelno) {
        this.exbtGuideTelno = exbtGuideTelno;
    }

    public String getReserveAbleYn() {
        return reserveAbleYn;
    }

    public void setReserveAbleYn(String reserveAbleYn) {
        this.reserveAbleYn = reserveAbleYn;
    }

    public String getDcReasonCd() {
        return dcReasonCd;
    }

    public void setDcReasonCd(String dcReasonCd) {
        this.dcReasonCd = dcReasonCd;
    }

    public int getDcRate() {
        return dcRate;
    }

    public void setDcRate(int dcRate) {
        this.dcRate = dcRate;
    }

    public int getHoldCnt() {
        return holdCnt;
    }

    public void setHoldCnt(int holdCnt) {
        this.holdCnt = holdCnt;
    }

    public int getTimeCnt() {
        return timeCnt;
    }

    public void setTimeCnt(int timeCnt) {
        this.timeCnt = timeCnt;
    }

    public String getTodayYn() {
        return todayYn;
    }

    public void setTodayYn(String todayYn) {
        this.todayYn = todayYn;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getOpenyn() {
        return openyn;
    }

    public void setOpenyn(String openyn) {
        this.openyn = openyn;
    }

}
