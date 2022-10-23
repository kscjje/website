package com.hisco.user.evtrsvn.service;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

import egovframework.com.cmm.ComDefaultVO;

/**
 * @Class Name : EventProgramVO.java
 * @Description : 강연/행사/영화 프로그램 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 8. 27 김희택
 * @author 김희택
 * @since 2020. 8. 27
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class EventProgramVO extends ComDefaultVO implements Serializable {

    private String comcd;// 기관고유번호
    private int evtNo;// 강연/행사/영화고유번호
    private String evtPartcd;// 강연/행사/영화업장고유번호
    private String evtName;// 강연/행사/영화제목
    private String evtPlacenm;// 강연,행사,영화 장소명
    private String evtTimeinfo;// 강연,행사,영화시간정보
    private String evtRuningTime;// 강연,행사,영화 러닝타임
    private String evtTargetage;// 강연,행사,영화 관람 대상
    private String comCtgcd;// 분류코드
    private String evtUseSdate;// 강연/행사/영화운영시작일자
    private String evtUseEdate;// 강연/행사/영화운영종료일자
    private String evtAppPeriodGbn;// 강연/행사/영화접수기간운영기준
    private String evtReqSdate;// 강연/행사/영화접수시작일자
    private String evtReqStime;// 강연/행사/영화접수시작일자
    private String evtReqEdate;// 강연/행사/영화접수시작시간
    private String evtReqEtime;// 강연/행사/영화접수종료일자
    private String evtOpendate;// 강연/행사/영화접수종료시간
    private String evtOpentime;// 상시접수예약오픈일자
    private String evtSexdstn;// 예약가능성별
    private String evtAnualmembyn;// 연간회원예약가능여부
    private String evtSpeclmembyn;// 특별회원예약가능여부
    private String evtStdmembyn;// 일반회원예약가능여부
    private String evtNonmebyn;// 비회원예약가능여부
    private int evtOrgMincnt;// 단체신청가능최소기준인원
    private int evtOrgMaxcnt;// 단체신청가능최대기준인원
    private String evtFeeType;// 가격정책
    private String evtOnlineRsvyn;// 온라인당일예약운영여부
    private String evtPaywaitGbn;// 강연/행사/영화결제대기시간적용기준
    private String evtPaywaitTime;// 강연/행사/영화예약결제마감설정시간
    private int evtMaxtimeCnt;// 관람일기준ID최대예약가능회차
    private String evtGuideTelno;// 강연/행사/영화문의담당전화번호
    private String evtIntrcn;// 강연/행사/영화소개내용
    private String evtIntrimgFinnb;// 강연/행사/영화소개IMAGE파일ID
    private String evtPlanFinnb;// 강연/행사/영화계획서파일ID
    private String evtTicketNotice;// 강연/행사/영화티켓노출한줄요약
    private String evtTicketChkyn;// 강연/행사/영화티켓검표대상여부
    private int evtBfticketMsgtime;// 관람시간전검표시메시지노출기준시간
    private String openyn;// 공개여부
    private String useyn;// 사용여부
    private Date regdate;
    private String reguser;
    private Date moddate;
    private String moduser;
    private String evtThmbImgFinnb;// 강연/행사/영화썸네일이미지파일ID
    private String evtPostrImgFinnb;// 강연/행사/영화대표이미지파일ID
    private String rsvAbleYn; // 접수 가능여부
    private String evtTime;// 강연/행사/영화일
    private String memberType;// 특별회원 S, 유료회원 U
    private String evtPriorStimeyn;
    private String evtAnualmembStime;
    private String evtSpecmembStime;
    private String evtGememberStime;

    // 기타변수
    private String comPrnctgcd;
    private String comPrnctgcdnm;
    private String comTopctgcdnm;
    private String comCtgnm;
    private String comCtgLvl;
    private String evtFeeTypenm;
    private String orderField;
    private long itemPrice;
    private String dcReasonCd;
    private int dcRate;

    private String evtGbn; // 강연/행사/영화 구분
    private String evtGbnNm; // 강연/행사/영화 구분 명칭

    private String eduUserBtn;

    private String evtIntrimgFinnbOrginFileName;
    private String evtIntrimgFinnbFilePath;
    private String evtIntrimgFinnbFileName;
    private String evtIntrimgFinnbOnlyOrginExtrn;

    // 휴일 체크
    private int holdCnt;

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public int getEvtNo() {
        return evtNo;
    }

    public void setEvtNo(int evtNo) {
        this.evtNo = evtNo;
    }

    public String getEvtPartcd() {
        return evtPartcd;
    }

    public void setEvtPartcd(String evtPartcd) {
        this.evtPartcd = evtPartcd;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtPlacenm() {
        return evtPlacenm;
    }

    public void setEvtPlacenm(String evtPlacenm) {
        this.evtPlacenm = evtPlacenm;
    }

    public String getEvtTimeinfo() {
        return evtTimeinfo;
    }

    public void setEvtTimeinfo(String evtTimeinfo) {
        this.evtTimeinfo = evtTimeinfo;
    }

    public String getEvtRuningTime() {
        return evtRuningTime;
    }

    public void setEvtRuningTime(String evtRuningTime) {
        this.evtRuningTime = evtRuningTime;
    }

    public String getEvtTargetage() {
        return evtTargetage;
    }

    public void setEvtTargetage(String evtTargetage) {
        this.evtTargetage = evtTargetage;
    }

    public String getComCtgcd() {
        return comCtgcd;
    }

    public void setComCtgcd(String comCtgcd) {
        this.comCtgcd = comCtgcd;
    }

    public String getEvtUseSdate() {
        return evtUseSdate;
    }

    public void setEvtUseSdate(String evtUseSdate) {
        this.evtUseSdate = evtUseSdate;
    }

    public String getEvtUseEdate() {
        return evtUseEdate;
    }

    public void setEvtUseEdate(String evtUseEdate) {
        this.evtUseEdate = evtUseEdate;
    }

    public String getEvtAppPeriodGbn() {
        return evtAppPeriodGbn;
    }

    public void setEvtAppPeriodGbn(String evtAppPeriodGbn) {
        this.evtAppPeriodGbn = evtAppPeriodGbn;
    }

    public String getEvtReqSdate() {
        return evtReqSdate;
    }

    public void setEvtReqSdate(String evtReqSdate) {
        this.evtReqSdate = evtReqSdate;
    }

    public String getEvtReqStime() {
        return evtReqStime;
    }

    public void setEvtReqStime(String evtReqStime) {
        this.evtReqStime = evtReqStime;
    }

    public String getEvtReqEdate() {
        return evtReqEdate;
    }

    public void setEvtReqEdate(String evtReqEdate) {
        this.evtReqEdate = evtReqEdate;
    }

    public String getEvtReqEtime() {
        return evtReqEtime;
    }

    public void setEvtReqEtime(String evtReqEtime) {
        this.evtReqEtime = evtReqEtime;
    }

    public String getEvtOpendate() {
        return evtOpendate;
    }

    public void setEvtOpendate(String evtOpendate) {
        this.evtOpendate = evtOpendate;
    }

    public String getEvtOpentime() {
        return evtOpentime;
    }

    public void setEvtOpentime(String evtOpentime) {
        this.evtOpentime = evtOpentime;
    }

    public String getEvtSexdstn() {
        return evtSexdstn;
    }

    public void setEvtSexdstn(String evtSexdstn) {
        this.evtSexdstn = evtSexdstn;
    }

    public String getEvtAnualmembyn() {
        return evtAnualmembyn;
    }

    public void setEvtAnualmembyn(String evtAnualmembyn) {
        this.evtAnualmembyn = evtAnualmembyn;
    }

    public String getEvtSpeclmembyn() {
        return evtSpeclmembyn;
    }

    public void setEvtSpeclmembyn(String evtSpeclmembyn) {
        this.evtSpeclmembyn = evtSpeclmembyn;
    }

    public String getEvtStdmembyn() {
        return evtStdmembyn;
    }

    public void setEvtStdmembyn(String evtStdmembyn) {
        this.evtStdmembyn = evtStdmembyn;
    }

    public String getEvtNonmebyn() {
        return evtNonmebyn;
    }

    public void setEvtNonmebyn(String evtNonmebyn) {
        this.evtNonmebyn = evtNonmebyn;
    }

    public int getEvtOrgMincnt() {
        return evtOrgMincnt;
    }

    public void setEvtOrgMincnt(int evtOrgMincnt) {
        this.evtOrgMincnt = evtOrgMincnt;
    }

    public int getEvtOrgMaxcnt() {
        return evtOrgMaxcnt;
    }

    public void setEvtOrgMaxcnt(int evtOrgMaxcnt) {
        this.evtOrgMaxcnt = evtOrgMaxcnt;
    }

    public String getEvtFeeType() {
        return evtFeeType;
    }

    public void setEvtFeeType(String evtFeeType) {
        this.evtFeeType = evtFeeType;
    }

    public String getEvtOnlineRsvyn() {
        return evtOnlineRsvyn;
    }

    public void setEvtOnlineRsvyn(String evtOnlineRsvyn) {
        this.evtOnlineRsvyn = evtOnlineRsvyn;
    }

    public String getEvtPaywaitGbn() {
        return evtPaywaitGbn;
    }

    public void setEvtPaywaitGbn(String evtPaywaitGbn) {
        this.evtPaywaitGbn = evtPaywaitGbn;
    }

    public String getEvtPaywaitTime() {
        return evtPaywaitTime;
    }

    public void setEvtPaywaitTime(String evtPaywaitTime) {
        this.evtPaywaitTime = evtPaywaitTime;
    }

    public int getEvtMaxtimeCnt() {
        return evtMaxtimeCnt;
    }

    public void setEvtMaxtimeCnt(int evtMaxtimeCnt) {
        this.evtMaxtimeCnt = evtMaxtimeCnt;
    }

    public String getEvtGuideTelno() {
        return evtGuideTelno;
    }

    public void setEvtGuideTelno(String evtGuideTelno) {
        this.evtGuideTelno = evtGuideTelno;
    }

    public String getEvtIntrcn() {
        return evtIntrcn;
    }

    public void setEvtIntrcn(String evtIntrcn) {
        this.evtIntrcn = evtIntrcn;
    }

    public String getEvtIntrimgFinnb() {
        return evtIntrimgFinnb;
    }

    public void setEvtIntrimgFinnb(String evtIntrimgFinnb) {
        this.evtIntrimgFinnb = evtIntrimgFinnb;
    }

    public String getEvtPlanFinnb() {
        return evtPlanFinnb;
    }

    public void setEvtPlanFinnb(String evtPlanFinnb) {
        this.evtPlanFinnb = evtPlanFinnb;
    }

    public String getEvtTicketNotice() {
        return evtTicketNotice;
    }

    public void setEvtTicketNotice(String evtTicketNotice) {
        this.evtTicketNotice = evtTicketNotice;
    }

    public String getEvtTicketChkyn() {
        return evtTicketChkyn;
    }

    public void setEvtTicketChkyn(String evtTicketChkyn) {
        this.evtTicketChkyn = evtTicketChkyn;
    }

    public int getEvtBfticketMsgtime() {
        return evtBfticketMsgtime;
    }

    public void setEvtBfticketMsgtime(int evtBfticketMsgtime) {
        this.evtBfticketMsgtime = evtBfticketMsgtime;
    }

    public String getOpenyn() {
        return openyn;
    }

    public void setOpenyn(String openyn) {
        this.openyn = openyn;
    }

    public String getUseyn() {
        return useyn;
    }

    public void setUseyn(String useyn) {
        this.useyn = useyn;
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

    public String getEvtThmbImgFinnb() {
        return evtThmbImgFinnb;
    }

    public void setEvtThmbImgFinnb(String evtThmbImgFinnb) {
        this.evtThmbImgFinnb = evtThmbImgFinnb;
    }

    public String getEvtPostrImgFinnb() {
        return evtPostrImgFinnb;
    }

    public void setEvtPostrImgFinnb(String evtPostrImgFinnb) {
        this.evtPostrImgFinnb = evtPostrImgFinnb;
    }

    public String getComPrnctgcd() {
        return comPrnctgcd;
    }

    public void setComPrnctgcd(String comPrnctgcd) {
        this.comPrnctgcd = comPrnctgcd;
    }

    public String getComCtgnm() {
        return comCtgnm;
    }

    public void setComCtgnm(String comCtgnm) {
        this.comCtgnm = comCtgnm;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getComPrnctgcdnm() {
        return comPrnctgcdnm;
    }

    public void setComPrnctgcdnm(String comPrnctgcdnm) {
        this.comPrnctgcdnm = comPrnctgcdnm;
    }

    public String getEvtFeeTypenm() {
        return evtFeeTypenm;
    }

    public void setEvtFeeTypenm(String evtFeeTypenm) {
        this.evtFeeTypenm = evtFeeTypenm;
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

    public String getRsvAbleYn() {
        return rsvAbleYn;
    }

    public void setRsvAbleYn(String rsvAbleYn) {
        this.rsvAbleYn = rsvAbleYn;
    }

    public String getEvtTime() {
        return evtTime;
    }

    public void setEvtTime(String evtTime) {
        this.evtTime = evtTime;
    }

    public int getHoldCnt() {
        return holdCnt;
    }

    public void setHoldCnt(int holdCnt) {
        this.holdCnt = holdCnt;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getEvtPriorStimeyn() {
        return evtPriorStimeyn;
    }

    public void setEvtPriorStimeyn(String evtPriorStimeyn) {
        this.evtPriorStimeyn = evtPriorStimeyn;
    }

    public String getEvtAnualmembStime() {
        return evtAnualmembStime;
    }

    public void setEvtAnualmembStime(String evtAnualmembStime) {
        this.evtAnualmembStime = evtAnualmembStime;
    }

    public String getEvtSpecmembStime() {
        return evtSpecmembStime;
    }

    public void setEvtSpecmembStime(String evtSpecmembStime) {
        this.evtSpecmembStime = evtSpecmembStime;
    }

    public String getEvtGememberStime() {
        return evtGememberStime;
    }

    public void setEvtGemeberStime(String evtGememberStime) {
        this.evtGememberStime = evtGememberStime;
    }

    public String getComCtgLvl() {
        return comCtgLvl;
    }

    public void setComCtgLvl(String comCtgLvl) {
        this.comCtgLvl = comCtgLvl;
    }

    public String getComTopctgcdnm() {
        return comTopctgcdnm;
    }

    public void setComTopctgcdnm(String comTopctgcdnm) {
        this.comTopctgcdnm = comTopctgcdnm;
    }

    public String getEvtGbn() {
        return evtGbn;
    }

    public void setEvtGbn(String evtGbn) {
        this.evtGbn = evtGbn;
    }

    public String getEvtGbnNm() {
        return evtGbnNm;
    }

    public void setEvtGbnNm(String evtGbnNm) {
        this.evtGbnNm = evtGbnNm;
    }

    public String getEduUserBtn() {
        return eduUserBtn;
    }

    public void setEduUserBtn(String eduUserBtn) {
        this.eduUserBtn = eduUserBtn;
    }

    public String getEvtIntrimgFinnbOrginFileName() {
        return evtIntrimgFinnbOrginFileName;
    }

    public void setEvtIntrimgFinnbOrginFileName(String evtIntrimgFinnbOrginFileName) {
        this.evtIntrimgFinnbOrginFileName = evtIntrimgFinnbOrginFileName;
    }

    public String getEvtIntrimgFinnbFilePath() {
        return evtIntrimgFinnbFilePath;
    }

    public void setEvtIntrimgFinnbFilePath(String evtIntrimgFinnbFilePath) {
        this.evtIntrimgFinnbFilePath = evtIntrimgFinnbFilePath;
    }

    public String getEvtIntrimgFinnbFileName() {
        return evtIntrimgFinnbFileName;
    }

    public void setEvtIntrimgFinnbFileName(String evtIntrimgFinnbFileName) {
        this.evtIntrimgFinnbFileName = evtIntrimgFinnbFileName;
    }

    public String getEvtIntrimgFinnbOnlyOrginExtrn() {
        return evtIntrimgFinnbOnlyOrginExtrn;
    }

    public void setEvtIntrimgFinnbOnlyOrginExtrn(String evtIntrimgFinnbOnlyOrginExtrn) {
        this.evtIntrimgFinnbOnlyOrginExtrn = evtIntrimgFinnbOnlyOrginExtrn;
    }

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
