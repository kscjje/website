package com.hisco.user.exbtrsvn.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hisco.admin.terms.vo.TermsVO;
import com.hisco.cmm.object.CamelMap;
import com.hisco.intrfc.charge.vo.OrderIdVO;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.vo.MyRsvnVO;

import egovframework.com.cmm.LoginVO;

/**
 * 관람 정보 조회
 * 
 * @author 진수진
 * @since 2020.08.12
 * @version 1.0, 2020.08.12
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.08.12 최초작성
 */
public interface DspyDsService {

    /**
     * 기본 데이타를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    CamelMap selectselectPartCd(Map<?, ?> vo) throws Exception;

    /**
     * 파일 아이디를 수정한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    void updatePartCdFile(Map<?, ?> vo) throws Exception;

    /**
     * 기준설정 데이타를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    CamelMap selectBaseRule(Map<?, ?> vo) throws Exception;

    void updateBaseRule(Map<?, ?> vo) throws Exception;

    /**
     * 일정 데이타를 조회한다
     * 
     * @param vo
     *            Map
     * @return List<CalendarVO>
     * @exception Exception
     */
    List<CalendarVO> selectScheduleList(Map<?, ?> vo) throws Exception;

    /**
     * 관람 기준 설정 목록을 조회한다
     * 
     * @param vo
     *            Map
     * @return ExbtBaseruleVO
     * @exception Exception
     */
    List<ExbtBaseruleVO> selectExbtBaseList(Map<?, ?> vo) throws Exception;

    /**
     * 관람 기준 설정 상세를 조회한다
     * 
     * @param vo
     *            Map
     * @return ExbtBaseruleVO
     * @exception Exception
     */
    ExbtBaseruleVO selectExbtBaseDetail(Map<?, ?> vo) throws Exception;

    /**
     * 관람 회차 목록을 조회한다
     * 
     * @param vo
     *            Map
     * @return List<ExbtTimeVO>
     * @exception Exception
     */
    List<ExbtTimeVO> selectExbtTimeList(Map<String, Object> vo) throws Exception;

    /**
     * 관람 회차 상세정보를 조회한다
     * 
     * @param vo
     *            Map
     * @return ExbtTimeVO
     * @exception Exception
     */
    ExbtTimeVO selectExbtTimeData(Map<String, Object> vo) throws Exception;

    /**
     * 선택날짜 요일을 조회한다
     * 
     * @param vo
     *            Map
     * @return String
     * @exception Exception
     */
    String selectExbtWeek(Map<?, ?> vo) throws Exception;

    /**
     * 관람 요금을 조회한다
     * 
     * @param vo
     *            Map
     * @return List<ExbtChargeVO>
     * @exception Exception
     */
    List<ExbtChargeVO> selectExbtChargeList(Map<?, ?> vo) throws Exception;

    /**
     * 예약 번호 가져오기
     * 
     * @param
     * @return String
     * @exception Exception
     */
    public String selectRsvnNumber() throws Exception;

    /**
     * 예약 정보를 저장한다
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return Map<String,String>
     * @exception Exception
     */
    Map<String, String> insertRegistMst(MemberVO memberVO, RsvnMasterVO maserVO, ExbtBaseruleVO baseDataVO,
            MyRsvnVO myRsvnVO) throws Exception;

    /**
     * 예약 정보를 조회한다
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return RsvnMasterVO
     * @exception Exception
     */
    RsvnMasterVO selectRegistMst(RsvnMasterVO maserVO) throws Exception;

    /**
     * 예약 품목을 조회한다
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return List<ExbtChargeVO>
     * @exception Exception
     */
    List<ExbtChargeVO> selectRegistItemList(RsvnMasterVO maserVO) throws Exception;

    /**
     * 휴관일 여부를 체크한다
     * 
     * @param vo
     *            Map
     * @return int
     * @exception Exception
     */
    int selectHolidayCheck(Map<?, ?> vo) throws Exception;

    /**
     * 이용횟수 제한 기준에 따른 이용횟수 조회
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return int
     * @exception Exception
     */
    int selectReserveLimitCount(RsvnMasterVO maserVO) throws Exception;

    /**
     * 약관을 조회한다
     * 
     * @return List<TermsVO>
     * @exception Exception
     */
    List<TermsVO> selectTermsList() throws Exception;

    /**
     * 티켓번호
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return String
     * @exception Exception
     */
    String selectTicketNumber(RsvnMasterVO maserVO) throws Exception;

    /**
     * 결제마감시간
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return String
     * @exception Exception
     */
    String selectPayWaitTime(ExbtBaseruleVO ruleVo) throws Exception;

    /**
     * 예약정보 수정
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return int
     * @exception Exception
     */
    int updateReserveMaster(RsvnMasterVO maserVO) throws Exception;

    /**
     * 그룹할인 여부 조회
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return String
     * @exception Exception
     */
    CamelMap selectGroupDiscount(RsvnMasterVO maserVO) throws Exception;

    /**
     * 예약정보 인원 수정
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return String
     * @exception Exception
     */
    String updateReserveItem(MemberVO memberVO, RsvnMasterVO maserVO) throws Exception;

    /**
     * 관람 상품 예약가능 시작일
     * 
     * @param param
     * @return String
     * @exception Exception
     */
    String selectStartYmdByBase(Map<?, ?> param) throws Exception;

}
