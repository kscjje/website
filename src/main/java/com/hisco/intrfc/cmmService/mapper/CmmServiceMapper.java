package com.hisco.intrfc.cmmService.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

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
@Mapper("cmmServiceMapper")
public interface CmmServiceMapper {

    /**
     * 운영관리시스템 비밀번호 변경을 위한 사용자 휴대폰 조회
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectUserInfor(Map<String, Object> paramMap);
}
