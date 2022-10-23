/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.twedu.mapper;

import java.util.List;

import com.hisco.admin.twedu.vo.TweduAttendVO;
import com.hisco.admin.twedu.vo.TweduDetailVO;
import com.hisco.admin.twedu.vo.TweduJoinVO;
import com.hisco.admin.twedu.vo.TweduPlanVO;
import com.hisco.admin.twedu.vo.TweduStudentVO;
import com.hisco.admin.twedu.vo.TweduVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : TweduMapper.java
 * @Description : 마을배움터 관리 Mapper
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 3
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Mapper("tweduMapper")
public interface TweduMapper {
    List<TweduVO> selectList(TweduVO searchVO);

    TweduDetailVO select(TweduVO tweduVO);

    List<TweduPlanVO> selectPlans(TweduVO searchVO);

    int update(TweduDetailVO vo);

    int deletePlans(TweduDetailVO vo);

    int insertPlans(List<TweduPlanVO> list);

    int updateStatus(TweduVO tweduVO);

    int updateRsvn(TweduDetailVO vo);

    int updateRsvnSet(TweduDetailVO vo);

    List<TweduStudentVO> selectStudentList(TweduVO vo);

    int updateStudStatus(TweduStudentVO vo);

    List<TweduPlanVO> selectPlanList(TweduVO vo);

    List<TweduAttendVO> selectAttendanceList(TweduVO vo);

    List<TweduAttendVO> selectAttendStudents(TweduVO vo);

    int insertAttendance(TweduAttendVO vo);

    int updateAttendance(TweduAttendVO vo);

    int insertLog(TweduPlanVO vo);

    int updateLog(TweduPlanVO vo);

    TweduDetailVO selectReportHead(TweduVO vo);

    List<TweduAttendVO> selectReportAtend(TweduVO vo);

    List<TweduPlanVO> selectReportLog(TweduVO vo);

    List<TweduPlanVO> selectReportPlan(TweduVO vo);

    Integer selectOrgNo();

    TweduVO selectEduTerm(TweduVO vo);

    List<TweduJoinVO> selectJoinList(TweduJoinVO vo);

    int insertReportLogPlan(TweduVO vo);
}
