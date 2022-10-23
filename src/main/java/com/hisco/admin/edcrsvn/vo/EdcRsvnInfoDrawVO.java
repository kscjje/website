package com.hisco.admin.edcrsvn.vo;

import java.util.List;

import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;

import lombok.Data;

@Data
public class EdcRsvnInfoDrawVO {
    private List<DrawTargetProgramVO> targetProgramList; // 대상프로그램목록
    private List<DrawMemberVO> firstConsiderMemberList; // 1순위(관리자선택)

    private String periodCondition = Constant.CM_PERIOD_CONDITION_예약년월;
    private String searchYear;
    private String searchMonth;
    private String channel = Constant.CM_CHANNEL_BO;
    private String edcRsvnRectype = Constant.SM_LEREC_TYPE_추첨대기제;
    private String prevMonthOutYn = Config.NO; // 2순위(지난달탈락회원우선처리), 나머지 3순위
}
