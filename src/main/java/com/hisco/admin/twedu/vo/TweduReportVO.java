/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.twedu.vo;

import java.util.List;

import lombok.Data;

/**
 * @Class Name : TweduReportVO.java
 * @Description : 마을배움터 보고서 VO
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 12. 7
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
public class TweduReportVO {
    private TweduDetailVO header;
    private List<TweduAttendVO> attendList;
    private List<TweduPlanVO> logList;
    private List<TweduPlanVO> planList;
}
