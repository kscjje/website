package com.hisco.admin.edcrsvn.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.eduadm.service.EduAdmService;
import com.hisco.user.edcatnlc.service.EdcProgramService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : EdcRsvnInfoDrawService.java
 * @Description : 추첨강좌 추첨
 * @author woojinp@legitsys.co.kr
 * @since 2021. 11. 28.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */

@Slf4j
@Service("edcRsvnInfoAssignWaitService")
public class EdcRsvnInfoAssignWaitService extends EgovAbstractServiceImpl {

    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

    @Resource(name = "eduAdmService")
    private EduAdmService eduAdmService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    /*
     * 선착대기프로그램 신청자 대기 취소
     */
    public void cancelWait(EdcRsvnInfoVO paramVO) throws Exception {
        edcRsvnInfoService.cancelRsvnInfoWating(paramVO);
    }
}
