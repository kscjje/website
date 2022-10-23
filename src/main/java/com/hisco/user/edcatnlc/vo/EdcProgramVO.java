package com.hisco.user.edcatnlc.vo;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hisco.admin.eduadm.vo.EdcDaysVO;
import com.hisco.admin.eduadm.vo.EdcNoticeVO;
import com.hisco.admin.eduadm.vo.EdcProgmLimitVO;
import com.hisco.admin.eduadm.vo.EdcTargetAgeVO;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.SessionUtil;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EdcProgramVO extends ComDefaultVO {
    // private String comcd; //super로 대체
    private int edcPrgmNo; // 교육프로그램고유번호. 시퀀스 : SEQ_EDC_PROGRAMIDX
    private String orgNo; // 평생학습포털을 강좌를 운영하는 등록된 기관의 고유번호로. 시퀀스(SEQ_ORGNO) 를 사용
    private String orgName;
    private String orgTel;
    private String CtgCd; // 분류코드
    private String CtgNm; // 분류코드명
    private String edcPartCd; // 사업장코드
    private int areaCd; // 교육장소지역(행정동기준)
    private String edcPlacenm; // 교육장소명
    private String edcRelDeptname; // 유관부서
    private String edcProgmDate; // 교육프로그램개설일자
    private String edcProgmType; // 교육프로그램의 유형을 설정한다.. COT_GRPCD.GRP_CD = 'SM_EDCPROGM_TYPE'
    private String edcPrgmnm; // 교육프로그램명
    private String edcOdr; // 교육차수
    private String edcOdrNm; // 교육차수
    private int edcClcnt; // 수업총횟수
    private int edcRtime; // 교육차수별 학습시간을 분단위로 기록한다.
    private String edcSdate; // 교육시작일자
    private String edcEdate; // 교육종료일자
    private String edcStime; // 교육시작시간
    private String edcEtime; // 교육종료시간
    private String edcTimeGuide; // 시간안내
    private String edcTchmtrGuide; // 교육재료비안내노출설명
    private String edcTchmtrGuideyn; // 교육재료비안내노출여부
    private int edcPncpa; // 교육총정원
    private int edcOncapa; // 온라인접수정원
    private int edcOffcapa; // 오프라인접수정원
    private int edcEndwaitCapa; // 선착마감대기정원
    private String edcTargetinfo; // 교육대상
    private String edcTargetAgegbn; // 교육대상코드
    private String edcTargetAgegbnNm; // 교육대상명
    private String edcOnlineyn; // 온라인학습교육여부. . Y，N : 온라인으로 학습받는 교육인경우 'Y' 를 기록한다..
    private String edcAddrOpenyn; // 주소수집정보노출여부
    private String exclDcyn; // 할인적용대상제외여부
    private String edcImgFileid; // 교육프로그램대표이미지파일ID
    private String edcPrgmintrcn; // 교육프로그램소개내용
    private String edcPrgmintrcnFileid; // 교육프로그램소개이미지파일ID
    private String edcPlanFileid; // 교육프로그램계획서파일ID
    private String edcRsvnSdate; // 교육예약신청시작일자
    private String edcRsvnStime; // 교육예약신청시작시간
    private String edcRsvnEdate; // 교육예약신청종료일자
    private String edcRsvnEtime; // 교육예약신청종료시간
    private String edcRsvnRectype; // 교육예약신청접수방법
    private String edcRsvnPerodType; // 예약접수기간운영방법 정기, 수시
    private String edcRsvnRectypeNm; // 교육예약신청접수방법
    private String edcRsvnAccssrd; // 교육예약신청접수경로
    private String edcRsvnAccssrdNm; // 교육예약신청접수경로
    private String edcRsvnLinkurl; // 교육예약링크주소. 교육예약접수방식이 타기관 URL LINK 인경우 URL 입력정보를 수집한다.
    private String drwtNtcedate; // 교육추첨당첨자발표일. 교육예약 추첨제접수방식인경우 추첨발표일을 관리한다
    private int drwtPreparpnt; // 교육추첨예비당첨인원수
    private String edcRefundchagetype; // 교육료중도환불금액계산방식. 교육시작이우 중도환불시 환불요금계산방식을 지정한다.
    
    private int edcGrpMincnt; // 단체교육신청최소예약인원. 단체인 경우 최소예약인원 설정. (0 : 제한 무)
    private int edcGrpMaxcnt; // 단체교육신청최대예약인원
    private String edcAnualmembyn; // 연간회원예약가능여부
    private String edcStdmembyn; // 일반회원에약가능여부
    private String rsvnNonmebyn; // 비회원예약가능여부
    private String edcLimitAgeyn; // 교육신청연령제한여부
    private String edcRsvnLimitYn; // 교육예약신청횟수제한여부
    private String edcRsvnlmitGbn; // 교육예약신청프로그램갯수제한 범위설정구분.
    private int edcRsvnLimitCnt; // 교육예약신청제한횟수. 온라인강좌신청시 강습기간내에 회원당 신청가능한 최대강좌수를 설정한다.. 0으로 설정시 신청강좌수의 제한이 없다.
    private String edcRsvnLimitPd; // 교육예약신청제한횟수기간적용구분
    private String edcReqGender; // 교육신청가능성별. 교육 신청가능한 회원의 성별을 구분하는 시스템 분류코드 . 회원제 예약대상인경우만 해당됨. . . . . . . .
    private String edcPaywaitGbn; // 교육예약신청결제마감시간적용기준
    private int edcPaywaitTime; // 교육예약결제마감설정시간. 예약신청후 결제 가능 대기시간을 분단위로 기록한다.. 최소 60분이상 기록해야함.
    private String edcPaywaitDate; // 교육신청결제마감일자. 결제대기시간 적용기준이 3001:마감일시직접설정 인경우에. 직접 설정한 결제대기 마감일자를 기록한다.
    private String edcPaywaitHhmm; // 교육신청결제마감일자. 결제대기시간 적용기준이 3001:마감일시직접설정 인경우에. 직접 설정한 결제대기 마감시간을 시분(HHMM) 으로 기록한다..
                                   // 분설정시에는 10분단위로만 기록할수있다.
    private String edcShtlbusYn; // 교육셔틀버스운행여부. 교육프로그램이 셔틀버스운행 프로그램이면 여부('Y'，'N')을 기록한다
    private String edcShtlbusGuide; // 셔틀버스탑승장소위치안내. 교육프로그램 셔틀버스 운행 프로그램이면 셔틀탑승장소 위치를 안내내용을
    private String edcVisitfamilyYn; // 가족참여교육여부. 가족참여가 가능한강좌로 강좌참여가족신청을 받고자하는 강좌에 'Y' 를 설정한다..
    private int edcVisitfamilyCnt; // 가족구성원정보수집인원설정
    private String edcGuideTelno; // 교육문의담당전화번호
    private String edcGuideComment; // 교육신청안내한줄특이사항. 온라인 교육신청상세정보에 한줄요약으로 노출되 안내정보기록 컬럼
    private String edcFeeAccname; // 교육비입금계좌예금주명
    private String edcFeeBnkname; // 교육비입금계좌은행명
    private String edcFeeAccno; // 교육비입금계좌번호 '-' 포함 입력
    private String edcFeeType; // 가격정책
    private String edcOpenyn; // 공개여부
    private String memNo; // 회원번호
    private int instrctrNo; // 교육강사고유번호
    private String instrctrName; // 교육강사명
    private String edcPrg; // 강좌개설상태
    private String edcCtfhvyn; // 교육수료증제공유무. . 수료증제공프로그램인경우 'Y' 그렇지 않으면 'N'을 기록
    private String recomendPryn; // 추천강좌여부
    private String bestPryn; // 인기강좌여부
    private String rsvnForceCloseyn;
    private String useYn; // 사용여부
    private String userIp; // 등록수정아이피주소
    private int sortOrder; // 정렬순서
    private Timestamp regdate; // 등록일시
    private String reguser; // 등록자
    private Timestamp moddate; // 수정일시
    private String moduser; // 수정자

    private int totCount;

    private List<EdcDaysVO> edcDaysList;
    private List<EdcProgmLimitVO> edcLimitList;
    private List<EdcTargetAgeVO> edcAgeList;
    private EdcNoticeVO noticeVO;

    private int itemCd;
    private int costAmt;
    private int salamt;
    private int saleAmt;
    private int monthCnt;

    private String edcRefundchangetype;

    private String edcPaywaitHour;
    private String edcPaywaitMin;

    private String instrctrYn;

    private String edcImgFielid;
    private String edcStatus; // 상태
    private String edcStatusClass; // 상태클래스
    private String alreadyApplyYn; // 이미 신청여부
    private String edcDaygbnNm;
    private int edcRsvnsetSeq;
    private String edcRsvnsetNm;
    private String edcCapaDvdyn;

    private String rsvnTypeNm;
    private String rsvnsetSeq;

    private String edcImgFilenm;
    private String edcImgOrigin;
    private String edcImgPath;

    private String edcPrgmintrcnFilenm;
    private String edcPrgmintrcnOrigin;
    private String edcPrgmintrcnPath;

    private String edcPlanFilenm; // 강의계획서저장파일명
    private String edcPlanOrigin; // 강의계획서명
    private String edcPlanPath; // 저장위치

    private String searchDate;
    private String searchStatus;
    private String searchFree;
    private String searchRectype;
    private String searchCtgnm;
    private String searchCtgcd;
    private String searchUse;
    private String searchHurry;

    private String targetCtgcd;
    private String targetOrgNo;

    private String edcStimeHour;
    private String edcStimeMin;
    private String edcEtimeHour;
    private String edcEtimeMin;

    private String edcRsvnStimeHour;
    private String edcRsvnStimeMin;
    private String edcRsvnEtimeHour;
    private String edcRsvnEtimeMin;

    private int edcRsvnSdday; // 예약시작dday
    private int edcRsvnEdday; // 예약종료dday
    private int edcSdday; // 수업시작dday
    private String programNo; // NW01-210802-00000001
    private int edcRsvnCnt; // 프로그램예약건수

    /** 검색 **/
    private String searchTab = Constant.EDC_SEARCH_TAB_강좌명; // 강좌명, 지역별, 대상별, 분야별, 기관별, 시간대
    private String searchOnOff = Constant.EDC_SEARCH_ONOFF_전체; // 전체, 온라인, 온라인+방문
    private String searchOrderBy; // 접수마감순, 최신등록순
    private String orgLtype; // 기관유형1의 기관번호(상위)
    private String orgMtype; // 기관유형2의 기관번호(하위)
    private String dayGbn; // 평일/주말 구분
    private String hourbandGbn; // 시간대. 오전/오후/야간
    private String edcStat;

    private String przwinStat; // 추첨상태(SM_PRZWIN_STAT)
    private String przWinGbn; // 당첨구분(SM_PRZWIN_GBN)

    private int appCnt;
    private String ageAbleYn;
    private String dupChk;
    private String edufreeYn;

    // BO 수강접수현황
    private String channel; // FO, BO
    private int statApplyCnt;
    private int statApplyOnCnt;
    private int statApplyOffCnt;
    private int statPaydoneCnt;
    private int statPaywaitCnt;
    private int statCancelCnt;
    private int statAssignWaitCnt;

    private String periodCondition;
    private String searchYear;
    private String searchMonth;
    private String usePagingYn = Config.YES;

    private String areaName;
    private String targetName; // 교육대상(나이대)
    private String userTargetName; // 로그인유저의 대상정보(나이대)
    private String orgRetdcGuide;

    private String ageAppgbn; // 1001 : 한국나이적용 , 2001 : (만)나이적용
    private String applyNumStr;

    
    private String edcPrgmIntroCnts;	//강좌소개
    private String instrctrNm;			//강사명
    
    //private String orgTel;				//기관연락처
    private String orgAddr;				//기관주소
    private String orgEmail;			//기관대표이메일주소
    private String orgFax;				//기관fax번호 
    
    public void setApplyNumStr(String applyNumStr) {
        this.applyNumStr = applyNumStr;

        String[] numArr = applyNumStr.split("[|]");

        if (numArr.length >= 7) {
            this.statApplyCnt = Integer.parseInt(numArr[0]);
            this.statApplyOnCnt = Integer.parseInt(numArr[1]);
            this.statApplyOffCnt = Integer.parseInt(numArr[2]);
            this.statPaydoneCnt = Integer.parseInt(numArr[3]);
            this.statPaywaitCnt = Integer.parseInt(numArr[4]);
            this.statCancelCnt = Integer.parseInt(numArr[5]);
            this.statAssignWaitCnt = Integer.parseInt(numArr[6]);
        }
    }

    public int getStatRemainCnt() {
        int remainCnt = this.edcPncpa - statApplyCnt;
        if (remainCnt < 0)
            return 0;
        return remainCnt;
    }

    public String getEdcSdatetime() {
        return edcSdate + edcStime;
    }

    public String getEdcEdatetime() {
        return edcEdate + edcEtime;
    }

    public String getEdcRsvnPeriod() {
        if (StringUtils.isBlank(edcRsvnSdate))
            return edcRsvnSdate;
        // @formatter:off
        StringBuilder sb = new StringBuilder();
        sb.append(edcRsvnSdate.substring(0, 4)).append("-").append(this.edcRsvnSdate.substring(4, 6)).append("-").append(edcRsvnSdate.substring(6, 8))
                /*
                 * .append(" ")
                 * .append(edcRsvnStime.substring(0, 2))
                 * .append(":")
                 * .append(edcRsvnStime.substring(2, 4))
                 */
                .append(" ~ ").append(edcRsvnEdate.substring(0, 4)).append("-").append(edcRsvnEdate.substring(4, 6)).append("-").append(edcRsvnEdate.substring(6, 8))
        /*
         * .append(" ")
         * .append(edcRsvnEtime.substring(0, 2))
         * .append(":")
         * .append(edcRsvnEtime.substring(2, 4))
         */
        ;
        return sb.toString();
    }

    public String getEdcPeriod() {
        if (StringUtils.isBlank(edcSdate))
            return edcSdate;
        // @formatter:off
        StringBuilder sb = new StringBuilder();
        sb.append(edcSdate.substring(0, 4)).append("-").append(this.edcSdate.substring(4, 6)).append("-").append(edcSdate.substring(6, 8)).append(" ~ ").append(edcEdate.substring(0, 4)).append("-").append(edcEdate.substring(4, 6)).append("-").append(edcEdate.substring(6, 8));
        return sb.toString();
    }

    public String getEdcTime() {
        // @formatter:off
        StringBuilder sb = new StringBuilder();
        if (edcStime != null && edcStime.length() == 4) {
            sb.append(edcStime.substring(0, 2)).append(":").append(edcStime.substring(2, 4)).append(" ~ ");
        } else if (edcStime != null) {
            sb.append(edcStime).append(" ~ ");
        }
        if (edcEtime != null && edcEtime.length() == 4) {
            sb.append(edcEtime.substring(0, 2)).append(":").append(edcEtime.substring(2, 4));
        } else if (edcStime != null) {
            sb.append(edcEtime);
        }

        return sb.toString();
    }

    public String getUserId() {
        UserSession userSession = (UserSession) SessionUtil.getAttribute(Config.USER_SESSION);
        if (userSession == null)
            return null;
        LoginVO loginVO = userSession.getUserInfo();
        if (loginVO == null)
            return null;
        return loginVO.getId();
    }

    /*
     * public String getEdcRsvnRectypeNm() {
     * StringBuffer buf = new StringBuffer(this.edcRsvnRectypeNm);
     * buf.append("(");
     * if (Config.YES.equals(this.edcCapaDvdyn)) {
     * if (this.edcOncapa > 0 && this.edcOffcapa > 0) {
     * buf.append("온라인+현장");
     * } else if (this.edcOncapa > 0 && this.edcOffcapa < 1) {
     * buf.append("온라인");
     * } else if (this.edcOncapa < 1 && this.edcOffcapa > 0) {
     * buf.append("현장");
     * }
     * } else {
     * buf.append("온라인+현장");
     * }
     * buf.append(")");
     * return buf.toString();
     * }
     */

}