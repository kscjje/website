package com.hisco.intrfc.coupon.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.coupon.mapper.CouponMapper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 쿠폰 대상 전송
 * 
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.05 최초작성
 */
@Service("couponService")
public class CouponService extends EgovAbstractServiceImpl {

    @Resource(name = "couponMapper")
    private CouponMapper couponMapper;

    /**
     * 쿠폰 번호 복호화
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectCouponNumber(Map<String, Object> paramMap) throws Exception {
        return couponMapper.selectCouponNumber(paramMap);
    }

    /**
     * 쿠폰 정보 노출 및 실시 관련 정보를 조회한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectCouponExecuteList(Map<String, Object> paramMap) throws Exception {
        return couponMapper.selectCouponExecuteList(paramMap);
    }

    /**
     * 쿠폰 대상 메인 정보를 조회한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    // public Map<String, Object> selectCouponDetail(CouponMstVO vo) throws Exception {
    // Map<String, Object> rtnMap = new HashMap<String, Object>();
    //
    // CouponMstVO mstVO = (CouponMstVO) commonDAO.queryForObject("CouponDAO.selectCouponDetail", vo);
    // List<CouponQstVO> qstList = (List<CouponQstVO>) couponMapper.selectCouponQstList", mstVO);
    //
    // for (CouponQstVO couponQstVO : qstList) {
    // List<CouponQstItemVO> itemList = (List<CouponQstItemVO>) couponMapper.selectQstItemList",
    // mstVO);
    // couponQstVO.setItemList(itemList);
    // }
    //
    // mstVO.setQstList(qstList);
    // rtnMap.put("vo", mstVO);
    // return rtnMap;
    // }

    /**
     * 쿠폰 대상 예약 정보를 조회한다
     *
     * @param vo
     *            CouponRsvnVO
     * @return map
     * @throws Exception
     */
    // public Map<String, Object> selectRsvnMst(CouponRsvnVO vo) throws Exception {
    // Map<String, Object> rtnMap = new HashMap<String, Object>();
    // vo.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
    // CouponSendVO sendVo = (CouponSendVO) commonDAO.queryForObject("CouponDAO.selectCouponSendList", vo);
    // CouponRsvnVO rsvnVO = (CouponRsvnVO) commonDAO.queryForObject("CouponDAO.selectRsvnDetail", vo);
    // rtnMap.put("vo", rsvnVO);
    // rtnMap.put("sendVO", sendVo);
    // return rtnMap;
    // }

    /**
     * 쿠폰 대상 예약 정보를 조회한다
     *
     * @param vo
     *            CouponResultVO
     * @return map
     * @throws Exception
     */
    // public Map<String, Object> insertCouponResult(CouponResultVO vo) throws Exception {
    // Map<String, Object> rtnMap = new HashMap<String, Object>();
    // int dtlCnt = 0;
    // int rsltCnt = commonDAO.getExecuteResult("CouponDAO.insertCouponResult", vo);
    // List<CouponResultDetailVO> dtlList = vo.getDtlList();
    // for (CouponResultDetailVO dtl : dtlList) {
    // dtl.setComcd(Config.COM_CD);
    // dtl.setQestnarResltNo(vo.getQestnarResltNo());
    // int dtlc = commonDAO.getExecuteResult("CouponDAO.insertSrvResultDtl", dtl);
    // dtlCnt += dtlc;
    // }
    // rtnMap.put("resultCnt", rsltCnt);
    // rtnMap.put("dtlCnt", dtlCnt);
    // return rtnMap;
    // }

    /**
     * 쿠폰 대상 예약 정보를 조회한다
     *
     * @param vo
     *            CouponResultVO
     * @return map
     * @throws Exception
     */
    // public int countCouponResult(CouponSendVO vo) throws Exception {
    // int rsltCnt = commonDAO.queryForInt("CouponDAO.countCouponResult", vo);
    // return rsltCnt;
    // }

}
