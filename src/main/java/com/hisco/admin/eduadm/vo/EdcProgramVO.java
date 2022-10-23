package com.hisco.admin.eduadm.vo;

import java.sql.Timestamp;
import java.util.List;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;

@Data
public class EdcProgramVO extends ComDefaultVO {
    // private String comcd; //super로 대체
    private int edcPrgmNo;
    private int orgNo;
    private String orgName;
    private String orgNm;
    private String CtgCd;
    private String CtgNm;
    private String edcPartCd;
    private int areaCd;
    private String areaName;
    private String areaNm;
    private String edcPlacenm;
    private String classAddr;
    private String edcProgmDate;
    private String edcProgmType;
    private String edcPrgmnm;
    private int edcOdr;
    private int edcRtime;
    private String edcSdate;
    private String edcEdate;
    private String edcStime;

    private String edcStimeHour;
    private String edcStimeMin;

    private String edcEtime;

    private String edcEtimeHour;
    private String edcEtimeMin;

    private String edcTimeguide;

    private String edcTchmtrGuideyn;
    private String edcTchmtrGuide;
    private int edcPncpa;
    private int edcOncapa;
    private int edcOffcapa;
    private String edcTargetinfo;
    private String edcTargetAgegbn;
    private String edcOnlineyn;
    private String edcAddrOpenyn;
    private String exclDcyn;
    private String edcImgFileid;
    private String edcPrgmintrcn;
    private String edcPrgmIntroCnts;
    private String edcPrgmIntroCntsFileid;
    private String edcPlanFileid;
    private String edcRsvnSdate;
    private String edcRsvnStime;

    private String edcRsvnStimeHour;
    private String edcRsvnStimeMin;

    private String edcRsvnEdate;
    private String edcRsvnEtime;

    private String edcRsvnEtimeHour;
    private String edcRsvnEtimeMin;

    private String edcRsvnRectype;
    
    private String edcRsvnPerodType;
    
    private String edcRsvnLinkurl;
    private String drwtNtcedate;
    private int drwtPreparpnt;
    private String edcRefundchagetype;
    private int edcGrpMincnt;
    private int edcGrpMaxcnt;
    private String edcAnualmembyn;
    private String edcStdmembyn;
    private String edcLimitAgeyn;
    private String edcRsvnlimitYn;
    private String edcRsvnlmitGbn;
    private int edcRsvnlimitCnt;
    private String edcRsvnlimitPd;
    private String edcReqGender;
    private String edcPaywaitGbn;
    private int edcPaywaitTime;
    private String edcPaywaitDate;
    private String edcPaywaitHhmm;
    private String edcShtlbusYn;
    private String edcShtlbusGuide;
    private String edcVisitfamilyYn;
    private int edcVisitfamilyCnt;
    private String edcGuideTelno;
    private String edcGuideComment;
    private String edcFeeAccNo;
    private String edcFeeAccNm;
    private String edcFeeBnkNm;
    private String edcFeeAccno;
    private String edcFeeType;
    private String edcOpenyn;
    private String memNo;
    private String edcPrg;
    private String edcCtfhvyn;
    private String recomendPryn;
    private String bestPryn;
    private String rsvnForceCloseyn;
    private String useYn;
    private String userIp;
    private int sortOrder;
    private Timestamp regdate;
    private String reguser;
    private Timestamp moddate;
    private String moduser;

    private String edcRelDeptname;
    private String edcRelDeptNm;
    private int curEdcClcnt;
    private int edcClcnt;

    private int totCount;

    private List<EdcDaysVO> edcDaysList;
    private List<EdcProgmLimitVO> edcLimitList;
    private List<EdcTargetAgeVO> edcAgeList;
    private EdcNoticeVO noticeVO;

    private int itemCd;
    private int itemNo;
    private int costAmt;
    private String salamt;
    private String saleAmt;

    private int edcEndwaitCapa;
    private String edcRsvnAccssrd;
    private String edcRefundchangetype;

    private String edcPaywaitHour;
    private String edcPaywaitMin;
    private String rsvnNonmebyn;

    private int instrctrNo;
    private String instrctrName;
    private String instrctrNm;
    private String instrctrHp;
    private String instrctrYn;

    private String edcImgFielid;
    private String edcStatus;
    private String edcDaygbnNm;
    private String edcRsvnsetSeq;
    private String edcRsvnsetNm;
    private String edcCapaDvdyn;

    private String rsvnTypeNm;
    private String rsvnsetSeq;

    private String edcImgFilenm;
    private String edcImgOrigin;
    private String edcImgPath;

    private String edcPrgmIntroCntsFilenm;
    private String edcPrgmIntroCntsOrigin;
    private String edcPrgmIntroCntsPath;

    private String edcPlanFilenm;
    private String edcPlanOrigin;
    private String edcPlanPath;

    private String edcRsvnnotiHpno;

    private String searchDate;
    private String searchOrgNo;
    private String searchStatus;
    private String searchFree;
    private String searchRectype;
    private String searchCtgnm;
    private String searchCtgcd;
    private String searchUse;
    private String searchOpen;

    private String targetCtgcd;
    private String targetOrgNo;

    private String searchOrgLtype;
    private String searchTarget;
    private String searchOrgkind;
    private String searchRsvnsetNm;

    private List<String> targetList;

    public String getSalamt() {
        return salamt == null ? "0" : salamt.replaceAll(",", "");
    }

}
