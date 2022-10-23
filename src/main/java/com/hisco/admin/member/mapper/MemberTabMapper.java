package com.hisco.admin.member.mapper;

import java.util.List;

import com.hisco.admin.member.vo.MemberDiscVO;
import com.hisco.admin.member.vo.MemberUserVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 회원 현황 Service 구현 클래스
 *
 * @author 진수진
 * @since 2021.11.18
 * @version 1.0, 2021.11.18
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2021.11.18 최초작성
 */
@Mapper("memberTabMapper")
public interface MemberTabMapper {

    /**
     * 회원카드 목록
     *
     * @param MemberUserVO
     * @return List
     * @throws Exception
     */
    public List<?> selectCardList(MemberUserVO paramMap);

    /**
     * 회원감면정보 목록
     *
     * @param MemberUserVO
     * @return List
     * @throws Exception
     */
    public List<?> selectDiscountList(MemberUserVO paramMap);

    public int insertDiscount(MemberDiscVO paramMap);

    public int selectDiscountCheck(MemberDiscVO paramMap);

    public MemberDiscVO selectDiscountRecord(MemberDiscVO paramMap);

    public int updateDiscount(MemberDiscVO paramMap);

    public int deleteDiscount(MemberDiscVO paramMap);

}
