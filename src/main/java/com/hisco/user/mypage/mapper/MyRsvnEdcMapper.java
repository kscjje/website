package com.hisco.user.mypage.mapper;

import java.util.List;
import java.util.Map;

import com.hisco.cmm.object.CamelMap;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;
import com.hisco.user.mypage.vo.MyRsvnVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 예약 정보 조회
 *
 * @author 진수진
 * @since 2020.09.14
 * @version 1.0, 2020.09.14
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.09.14 최초작성
 */
@Mapper("myRsvnEdcMapper")
public interface MyRsvnEdcMapper {
    public CamelMap selectReserveCountData(MyRsvnVO vo);

    public CamelMap selectReserveCountGrpData(MyRsvnVO vo);

    public List<EdcRsvnInfoVO> selectMyEdcRsvnList(MyRsvnVO vo);

    public List<EdcRsvnInfoVO> selectMyEdcRsvnList(Map map);

    public EdcRsvnInfoVO selectMyEdcRsvnDtl(MyRsvnVO vo);

    public int updateEdcMasterCancel(MyRsvnVO vo);

    public List<EdcRsvnInfoVO> selectMyEdcPagingList(MyRsvnVO vo);

    public List<EdcRsvnInfoVO> selectMyPgHistory(MyRsvnVO vo);

    public CamelMap selectEdcCancelInfo(MyRsvnVO vo);

    public EdcRsvnInfoVO selectEdcDetailData(MyRsvnVO vo);

    @Deprecated
    public int updateCouponCancel(MyRsvnVO vo);
}
