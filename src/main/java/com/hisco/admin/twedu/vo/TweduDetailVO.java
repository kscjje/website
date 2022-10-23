/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.twedu.vo;

import java.util.List;

import com.hisco.admin.eduadm.vo.EdcProgramVO;

import lombok.Data;

/**
 * @Class Name : TweduDetailVO.java
 * @Description : 마을배움터 상세 VO
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 12. 1
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
public class TweduDetailVO extends EdcProgramVO {
    /* 신청회원번호 */
    private String memNo;
    /* 신청회원명 */
    private String memNm;
    /* 신청회원휴대폰번호 */
    private String memHp;

    private String id;
    /* 수업계획리스트 */
    private List<TweduPlanVO> edcPlanList;

    private String edcTargetSage;
    private String edcTargetEage;
    private String edcTargetAge;

    private int stdCnt;

    private String reportDate;

    private String comTopctgcd;

    private String targetName;
}

/*
 * ##기본정보
 * member.memNo #신청회원명 - hidden
 * member.memNm #신청회원아이디 - text
 * member.hp #신청회원휴대폰번호 - text
 * edc_program.edcFeeAccname #입금계좌예금주명 - text
 * edc_program.edcFeeBnkNm #입금계좌은행명 - text
 * edc_program.edcFeeAccno #입금계좌번호 - text
 * edc_program.orgNo #운영기관 - select
 * edc_program.edcFeeType #수강료유무료여부(SM_PRICE_TYPE) - checkbox
 * edc_prgm_item.salamt #수강료 - text
 * edc_program.edcPrgmid #프로그램아이디 - text
 * edc_program.edcPrgmnm #프로그램명 - text
 * edc_program.edcPrgmintrcn #프로그램소개 - text
 * edc_program.edcImgFileid #대표이미지파일ID - file
 * edc_program.edcTargetAgegbn #교육대상 - select
 * com_ctgr.comCtgnm #프로그램분야명 - text
 * edc_program.CtgCd #프로그램분야코드 - text
 * edc_program.instrctrNo #강사코드(?) - hidden
 * edc_program.instrctrName #강사명 - text
 * edc_program.areaCd #강의지역코드 - select
 * edc_program.edcPlacenm #강의장소 - text
 * edc_program.edcPncpa #모집정원 - text
 * edc_program.edcLimitAgeyn #연령제한여부 - checkbox
 * target_agelist.edcAgeItemSeq #신청제한연련일련번호 - hidden
 * target_agelist.edcTargetSage #신청제한연령(시작) - text
 * target_agelist.edcTargetEage #신청제한연령(종료) - text
 * edc_program.edcClcnt #수업횟수 - text
 * edc_program.edcOdr #교육찻수 - text
 * edc_program.edcRtime #회자당교육시간 - text
 * edc_program.edcSdate #교육기간(시작) - text
 * edc_program.edcEdate #교육기간(종료) - text
 * edc_program.edcStime #교육시간(시작) - select
 * edc_program.edcStime #교육기간(종료) - select
 * edc_program.edcRsvnSdate #접수오픈기간(시작) - text
 * edc_program.edcRsvnStime #접수오픈시간(시작) - select
 * edc_days.edc_daygbn #교육요일 - checkbox(multi)
 * edc_program.edcGuideTelno #교육문의연락처 - text
 * edc_program.edcOpenyn #노출여부 - select
 * edc_program.edcPrg #승인상태 - select
 * edc_program.edcTchmtrGuide #재료비/유의사항 - textarea
 * ##수업계획서
 * edc_plan.edcClassNo #회차 - text
 * edc_plan.edcDate #수업일자 - text
 * edc_plan.edcStime #수업시간(시작) - select
 * edc_plan.edcEtime #수업시간(종료) - select
 * edc_plan.edcTitle #수업주제 - text
 * edc_plan.edcCnts #내용 - textarea
 */