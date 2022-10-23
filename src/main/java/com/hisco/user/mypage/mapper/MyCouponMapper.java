package com.hisco.user.mypage.mapper;

import java.util.List;

import com.hisco.user.member.vo.MemberCouponVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 회원정보 수정 처리 구현 클래스
 * 
 * @author 김희택
 * @since 2020.09.09
 * @version 1.0, 2020.09.09
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          김희택 2020.09.09 최초작성
 */
@Mapper("myCouponMapper")
public interface MyCouponMapper {

    /**
     * 쿠폰목록조회
     */
    public List<MemberCouponVO> selectMemberCoupon(MemberCouponVO vo);

    /**
     * 보유쿠폰건수조회
     */
    public int countMemberCoupon(MemberCouponVO vo);

    /**
     * 보유쿠폰상세
     */
    public MemberCouponVO selectMyCouponDtl(MemberCouponVO vo);

    /**
     * 모바일쿠폰상세조회
     * 
     * @param vo
     *            LoginVO
     * @return MemberCouponVO
     * @exception Exception
     */
    public MemberCouponVO selectMyCouponMobileDtl(MemberCouponVO vo);

}
