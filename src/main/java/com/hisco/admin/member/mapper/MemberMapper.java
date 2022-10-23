package com.hisco.admin.member.mapper;

import java.util.List;
import java.util.Map;

import com.hisco.admin.member.vo.MemberUserVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 회원 관리 Service 구현 클래스
 *
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 *          진수진 2021.10.20 수정
 */
@Mapper("memberMapper")
public interface MemberMapper {

    /**
     * 공통 코드 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectCotGrpCdList(Map<String, Object> paramMap);

    /**
     * 개인 교육 신청 현황 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectMemberList(MemberUserVO paramMap);

    public List<?> selectMemberList(Map<String, Object> paramMap);

    /**
     * 회원 상세 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public MemberUserVO selectMemberDetail(MemberUserVO paramMap);

    /**
     * 회원 상세 정보를 조회한다. (ID로)
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public MemberUserVO selectMemberDetailById(MemberUserVO paramMap);
}
