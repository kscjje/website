package com.hisco.intrfc.cmmService.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.cmmService.mapper.CmmServiceMapper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 교육 예약 서비스 구현
 *
 * @author 전영석
 * @since 2020.09.15
 * @version 1.0, 2020.09.15
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.09.15 최초작성
 */
@Service("cmmServiceService")
public class CmmServiceService extends EgovAbstractServiceImpl {

    @Resource(name = "cmmServiceMapper")
    private CmmServiceMapper cmmServiceMapper;

    /**
     * 운영관리시스템 비밀번호 변경을 위한 사용자 휴대폰 조회
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectUserInfor(Map<String, Object> paramMap) throws Exception {
        return cmmServiceMapper.selectUserInfor(paramMap);
    }

}
