package com.hisco.user.mypage.mapper;

import java.util.List;
import java.util.Map;

import com.hisco.cmm.object.CamelMap;
import com.hisco.user.mypage.vo.MyRsvnVO;
import com.hisco.user.mypage.vo.RsvnCommVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 예약 공통 기능 서비스 구현
 * 
 * @author 진수진
 * @since 2020.09.14
 * @version 1.0, 2020.09.14
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.09.14 최초작성
 */
@Mapper("rsvnCommMapper")
public interface RsvnCommMapper {

    /**
     * 운영사업자 부가정보 > 절사정보 *
     */
    public RsvnCommVO selectOrgOptInfo(MyRsvnVO vo);

    /**
     * 이벤트 할인 내역 조회
     */
    @Deprecated
    public List<RsvnCommVO> selectEventStdmngList(Map<?, ?> param);

    /**
     * 감면 기준 조회 *
     */
    public List<RsvnCommVO> selectDcReasonList(MyRsvnVO vo);

    /**
     * 그룹 할인 정책
     */
    public RsvnCommVO selectGroupDcCheck(MyRsvnVO vo);

    @Deprecated
    public RsvnCommVO updateMyCouponDataUse(MyRsvnVO vo);

    /**
     * 예약목록 조회
     */
    public CamelMap selectEdcItemCharge(MyRsvnVO vo);

    public int updateEucRsvnInfoCoupon(MyRsvnVO vo);

    public int updateEucRsvnCouponCancel(MyRsvnVO vo);

}
