package com.hisco.user.mypage.mapper;

import java.util.List;
import java.util.Map;

import com.hisco.cmm.object.CamelMap;
import com.hisco.user.mypage.vo.MyRsvnVO;

import egovframework.com.cmm.LoginVO;
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
@Mapper("myRsvnMapper")
public interface MyRsvnMapper {

    public MyRsvnVO selectPartSystem(MyRsvnVO vo);

    public List<MyRsvnVO> selectCardAppHistData(MyRsvnVO vo);

    public int selectEdcReserveCnt(LoginVO vo);

    public CamelMap selectRefundRuleData(MyRsvnVO vo);

    public int insertCancelTemp(Map<String, Object> paramMap);

    public int deleteCancelTemp(Map<String, Object> paramMap);

}
