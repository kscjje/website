package com.hisco.user.mypage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.user.member.vo.MemberCouponVO;
import com.hisco.user.mypage.mapper.MyCouponMapper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

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
@Service("myCouponService")
public class MyCouponService extends EgovAbstractServiceImpl {

    @Resource(name = "myCouponMapper")
    private MyCouponMapper myCouponMapper;

    /**
     * 로그인 정보로 회원 데이타를 조회한다
     * 
     * @param vo
     *            LoginVO
     * @return MemberVO
     * @exception Exception
     */

    public Map<String, Object> selectCouponList(MemberCouponVO vo) throws Exception {
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        List<MemberCouponVO> cList = (List<MemberCouponVO>) myCouponMapper.selectMemberCoupon(vo);
        int cnt = myCouponMapper.countMemberCoupon(vo);
        rtnMap.put("cList", cList);
        rtnMap.put("listSize", cnt);
        return rtnMap;
    }

    /**
     * 티켓정보를 조회한다
     * 
     * @param vo
     *            LoginVO
     * @return MemberCouponVO
     * @exception Exception
     */
    public MemberCouponVO selectCouponDetail(MemberCouponVO vo) throws Exception {
        return myCouponMapper.selectMyCouponDtl(vo);
    }

    /**
     * 티켓정보를 조회한다
     * 
     * @param vo
     *            LoginVO
     * @return MemberCouponVO
     * @exception Exception
     */
    public MemberCouponVO selectCouponMobileDetail(MemberCouponVO vo) throws Exception {
        return myCouponMapper.selectMyCouponMobileDtl(vo);
    }

}
