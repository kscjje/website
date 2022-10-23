package com.hisco.user.edcatnlc.mapper;

import java.sql.Timestamp;
import java.util.List;

import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 교육프로그램예약정보 Mapper
 */
@Mapper("edcRsvnInfoMapper")
public interface EdcRsvnInfoMapper {

    /**
     * 결제 마감시간 가져온다
     */
    public Timestamp selectPaywaitTime(EdcRsvnInfoVO vo);

    /**
     * 예약번호 시퀀스조회
     */
    public String selectNextRsvnNumber();

    public int selectNextRsvnReqid();

    /**
     * 교육예약등록.insertEduRsvnMst
     */
    public int insertRsvnInfo(EdcRsvnInfoVO vo);

    /**
     * 교육 예약 예약 안내 정보를 상세 조회한다.selectEdcRsvnInfoData
     */
    public EdcRsvnInfoVO selectRsvnInfo(EdcRsvnInfoVO vo);

    public List<EdcRsvnInfoVO> selectRsvnInfoList(EdcRsvnInfoVO vo);

    public List<EdcRsvnInfoVO> selectRsvnInfoListForPay(EdcRsvnInfoVO vo);

    /**
     * 예약회원정보조회
     */
    public EdcRsvnInfoVO selectRsvnInfoMember(EdcRsvnInfoVO vo);

    /**
     * 재료 받을 배송 주소 정보를 변경한다
     */
    public void updateRsvnInfoAddr(EdcRsvnInfoVO vo);

    /**
     * 교육 제한횟수 체크
     */
    public int selectRsvnInfoLimitCheck(EdcRsvnInfoVO vo);

    /**
     * 교육프로그램예약건수
     */
    public int selectRsvnInfoCount(EdcRsvnInfoVO vo);

    /**
     * 추첨정보 수정
     */
    public int doDrawRsvnInfo(EdcRsvnInfoVO vo);

    public int undoDrawRsvnInfo(EdcRsvnInfoVO vo);

    public int confirmDrawRsvnInfo(EdcRsvnInfoVO vo);

    public int insertDrawHistory(EdcRsvnInfoVO vo);

    public int cancelRsvnInfo(EdcRsvnInfoVO vo);

    public int completeRsvnInfo(EdcRsvnInfoVO vo);

    public int updateRsvnInfo(EdcRsvnInfoVO vo);

	/**
	 * 배정대기->입금대기처리
	 */
	public int updateRsvnInfo1000To2001(EdcRsvnInfoVO vo);

    public int cancelRsvnInfoWating(EdcRsvnInfoVO vo);

    /**
     * 교육 인원정보 수정
     */
    public int updateRsvnInfoPerson(EdcRsvnInfoVO vo);

    /**
     * 이메일 정보 수정
     */
    public int updateRsvnInfoEmail(EdcRsvnInfoVO vo);

    /**
     * 수업일자목록 조회
     */
    public List<String> selectLectDateList(EdcRsvnInfoVO vo);

    /**
     * 수료 상태 수정
     */
    public int updateRsvnInfoComplstat(EdcRsvnInfoVO vo);

    /**
     * 노원구청 3기본(공릉평생교육원+노원평생교육원+장미실습장) 할인감면 횟수제한을 위한 조회
     */
    public List<EdcRsvnInfoVO> selectNowon3BasicOrgRegiList(EdcRsvnInfoVO vo);
}
