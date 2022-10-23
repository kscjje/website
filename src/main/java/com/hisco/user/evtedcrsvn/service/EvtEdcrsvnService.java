package com.hisco.user.evtedcrsvn.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hisco.admin.terms.vo.TermsVO;
import com.hisco.cmm.object.CamelMap;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.vo.MyRsvnVO;

import egovframework.com.cmm.LoginVO;

/**
 * 강연/행사/영화 예약 서비스
 * 
 * @author 진수진
 * @since 2020.09.01
 * @version 1.0, 2020.09.01
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          김희택 2020.09.01 최초작성
 */
public interface EvtEdcrsvnService {

    /**
     * 강연/행사/영화 기본 데이타를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    CamelMap selectProgramData(Map<?, ?> vo) throws Exception;

    /**
     * 파일 아이디를 수정한다
     * 
     * @param vo
     *            CamelMap
     * @return void
     * @exception Exception
     */
    void updateProgramData(Map<?, ?> vo) throws Exception;

    /**
     * 강연/행사/영화 요금을 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return Map<String,Object>
     * @exception Exception
     */
    List<EvtEdcItemAmountVO> selectEvtEdcChargeList(EvtEdcStdmngVO vo) throws Exception;

    /**
     * 강연/행사/영화 예약 횟수를 체크한다.
     * 
     * @param vo
     *            CamelMap
     * @return Map<String,Object>
     * @exception Exception
     */
    int selectEvtEdcrsvnCnt(Map<String, Object> map) throws Exception;

    /**
     * 예약 번호 가져오기
     * 
     * @param
     * @return String
     * @exception Exception
     */
    String selectRsvnNumber() throws Exception;

    /**
     * 강연/행사/영화 예약정보를 입력한다
     * 
     * @param vo
     *            CamelMap
     * @return Map<String,String>
     * @exception Exception
     */
    Map<String, String> insertEvtEdcrsvnInfo(HttpServletRequest request, Map<String, Object> paramMap,
            MemberVO memberVO, EvtEdcrsvnMstVO evtrsvnMstVO, EventProgramVO detailVO, MyRsvnVO myRsvnVO)
            throws Exception;

    /**
     * 강연/행사/영화 예약가능 정보를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return map
     * @exception Exception
     */
    EvtEdcrsvnMstVO selectEvtEdcrsvnDetail(EvtEdcrsvnMstVO evtrsvnMstVO) throws Exception;

    /**
     * 강연/행사/영화 예약가능 정보를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return map
     * @exception Exception
     */
    List<?> selectGrpRsvnFamlyinfo(EvtEdcrsvnMstVO evtrsvnMstVO) throws Exception;

    /**
     * 약관 정보를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return map
     * @exception Exception
     */
    Map<String, Object> selectEvtEdcTerms(TermsVO vo) throws Exception;

    /**
     * 노쇼정보를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return map
     * @exception Exception
     */
    public int selectMemNshw(Map<String, Object> map) throws Exception;

    /**
     * 잔여인원을 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return map
     * @exception Exception
     */
    Map<String, Object> selectEvtEdcRmnAmnt(EvtEdcrsvnMstVO vo) throws Exception;

    /**
     * 티켓번호
     * 
     * @param maserVO
     *            EvtEdcrsvnMstVO
     * @return String
     * @exception Exception
     */
    String selectTicketNumber(EvtEdcrsvnMstVO maserVO) throws Exception;

    /**
     * 결제마감시간
     * 
     * @param ruleVo
     *            ExbtBaseruleVO
     * @return String
     * @exception Exception
     */
    String selectPayWaitTime(EventProgramVO ruleVo) throws Exception;

    /**
     * 단체할인을 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return map
     * @exception Exception
     */
    CamelMap selectEvtEdcGrpDscnt(EvtEdcrsvnMstVO vo) throws Exception;

    /**
     * 예약정보를 수정한다
     * 
     * @param vo
     *            CamelMap
     * @return void
     * @exception Exception
     */
    String updateEvtEdcrsvnData(EvtEdcrsvnMstVO vo) throws Exception;

    /**
     * 예약인원을 수정한다
     * 
     * @param vo
     *            CamelMap
     * @return void
     * @exception Exception
     */
    public String updateEvtEdcrsvnItem(MemberVO memberVO, EvtEdcrsvnMstVO vo) throws Exception;
}
