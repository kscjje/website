package com.hisco.user.evtedcrsvn.service;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;

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
@Data
@SuppressWarnings("serial")
public class EventProgramVO extends ComDefaultVO implements Serializable {

    private String comcd;// 기관고유번호
    private int evtNo;// 강연/행사/영화고유번호
    private String evtPartCd;// 강연/행사/영화업장고유번호
    private String evtName;// 강연/행사/영화제목
    private String evtPlaceNm;// 강연,행사,영화 장소명
    private String evtTimeinfo;// 강연,행사,영화시간정보
    private String evtRuningTime;// 강연,행사,영화 러닝타임
    private String evtTargetAge;// 강연,행사,영화 관람 대상
    private String ctgCd;// 분류코드
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
    private String useinforyn;// 이용정보노출사용여부

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

    private String evtGbn;
    private String evtGbnNm;

    private String evtExcepDay;

    private String eduUserBtn;

    private String evtIntrimgFinnbOrginFileName;
    private String evtIntrimgFinnbFilePath;
    private String evtIntrimgFinnbFileName;
    private String evtIntrimgFinnbOnlyOrginExtrn;

    private String cancelYn;

    private String evtReqStimeHh;
    private String evtReqStimeMm;

    private String evtReqEtimeHh;
    private String evtReqEtimeMm;

    private String evtOpentimeHh;
    private String evtOpentimeMm;

    //	휴일 체크
    private int holdCnt;
    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
