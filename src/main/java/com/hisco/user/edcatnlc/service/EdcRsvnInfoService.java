package com.hisco.user.edcatnlc.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hisco.admin.eduadm.vo.EdcTargetAgeVO;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.intrfc.charge.mapper.ChargeMapper;
import com.hisco.intrfc.sale.service.TotalSaleService;
import com.hisco.user.edcatnlc.mapper.EdcRsvnInfoMapper;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 교육프로그램 예약 서비스 구현
 *
 * @Class Name : EdcRsvnInfoService.java
 * @Description : 자세한 클래스 설명
 * @author woojinp@legitsys.co.kr
 * @since 2021. 11. 17.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Slf4j
@Service("edcRsvnInfoService")
public class EdcRsvnInfoService extends EgovAbstractServiceImpl {

    @Resource(name = "edcRsvnInfoMapper")
    private EdcRsvnInfoMapper edcRsvnInfoMapper;

    @Resource(name = "chargeMapper")
    private ChargeMapper chargeMapper;

    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

    @Resource(name = "totalSaleService")
    private TotalSaleService totalSaleService;

    // 암/복호화 키
    @Value("${Globals.DbEncKey}")
    String DbEncKey;

    // 개인 정보 암화화 시작
    @Value("${Globals.SpowiseCms.Key}")
    String SpowiseCmsKey;

    // @formatter:off

    /**
     * 교육 예약 예약 정보를 조회한다.
     */
    public List<EdcRsvnInfoVO> selectRsvnList(EdcRsvnInfoVO vo) throws Exception {
        return edcRsvnInfoMapper.selectRsvnInfoList(vo);
    }

    /* 예약번호로 예약목록 조회 */
    public List<EdcRsvnInfoVO> selectRsvnListForPay(EdcRsvnInfoVO vo) throws Exception {
        return edcRsvnInfoMapper.selectRsvnInfoListForPay(vo);
    }

    /* 주문번호로 예약회원정보 조회 */
    public EdcRsvnInfoVO selectRsvnInfoMember(EdcRsvnInfoVO vo) throws Exception {
        return edcRsvnInfoMapper.selectRsvnInfoMember(vo);
    }

    /**
     * 교육 예약 정보를 저장한다.
     * EDC_RSVN_INFO
     * 입금대기 상태 : 입금종료일시 설정 후 insert
     */
    public int saveRsvnInfo(EdcRsvnInfoVO rsvnInfoParam) throws Exception {

        if (StringUtils.isBlank(rsvnInfoParam.getEdcRsvnNo()))
            rsvnInfoParam.setEdcRsvnNo(this.selectNextRsvnNumber());

        if (Constant.SM_RSVN_STAT_입금대기.equals(rsvnInfoParam.getEdcStat())) {
            rsvnInfoParam.setEdcPaywaitEnddatetime(this.selectPaywaitTime(rsvnInfoParam));
        }

        return edcRsvnInfoMapper.insertRsvnInfo(rsvnInfoParam);
    }

    public int completeRsvnInfo(EdcRsvnInfoVO rsvnInfoParam) throws Exception {
        return edcRsvnInfoMapper.completeRsvnInfo(rsvnInfoParam);
    }

    public int updateRsvnInfo(EdcRsvnInfoVO rsvnInfoParam) throws Exception {
        return edcRsvnInfoMapper.updateRsvnInfo(rsvnInfoParam);
    }

    /**
     * 예약취소
     */
    public int cancelRsvnInfo(EdcRsvnInfoVO rsvnInfoParam) throws Exception {
        return edcRsvnInfoMapper.cancelRsvnInfo(rsvnInfoParam);
    }

    /**
     * PG건과 관련된 정보 입력
     * CARD_APP_HIST
     * PG_ORD_MST
     * PG_ORD_DET
     */
    /*
     * public int saveTossTransactionInfo(TossResponseVO tossResponseVO, String edcRsvnNo) throws Exception {
     * if (StringUtils.isBlank(edcRsvnNo))
     * throw new RuntimeException("필수파라미터 누락(edcRsvnNo)");
     * EdcRsvnInfoVO rsvnInfoParam = new EdcRsvnInfoVO();
     * rsvnInfoParam.setEdcRsvnNo(edcRsvnNo);
     * List<EdcRsvnInfoVO> rsvnInfoList = this.selectRsvnListForPay(rsvnInfoParam);
     * return edcRsvnInfoMapper.insertRsvnInfo(rsvnInfoParam);
     * }
     */

    /**
     * 교육 예약 예약 안내 정보를 상세 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public EdcRsvnInfoVO selectRsvnInfo(EdcRsvnInfoVO vo) throws Exception {
        return edcRsvnInfoMapper.selectRsvnInfo(vo);
    }

    public int selectRsvnInfoLimitCheck(EdcRsvnInfoVO vo) throws Exception {
        return edcRsvnInfoMapper.selectRsvnInfoLimitCheck(vo);
    }

    /**
     * 재료 받을 배송 주소 정보를 변경한다
     *
     * @param Map
     * @return void
     * @throws Exception
     */
    public void updateRsvnInfoAddr(EdcRsvnInfoVO vo, CommandMap commandMap) throws Exception {
        // 암/복호화 키----------------------------------------------------시작
        String strDbEncKey = EgovProperties.getProperty("Globals.DbEncKey");
        // TODO:dbEncKey값을 설정해야하나?
        // paramMap.put("dbEncKey", strDbEncKey);
        // 암/복호화 키----------------------------------------------------끝
        edcRsvnInfoMapper.updateRsvnInfoAddr(vo);

    }

    /**
     * 결제 마감시간 가져온다
     *
     * @param Map
     * @return String
     * @throws Exception
     */
    public Timestamp selectPaywaitTime(EdcRsvnInfoVO vo) throws Exception {
        return edcRsvnInfoMapper.selectPaywaitTime(vo);
    }

    /**
     * 예약번호
     *
     * @param Map
     * @return String
     * @throws Exception
     */
    public String selectNextRsvnNumber() throws Exception {
        return edcRsvnInfoMapper.selectNextRsvnNumber();
    }

    /**
     * 교육 제한횟수 체크
     *
     * @param Map
     * @return int
     * @exception Exception
     */
    public int selectProgramRsvnLimitCheck(EdcRsvnInfoVO vo) throws Exception {
        return edcRsvnInfoMapper.selectRsvnInfoLimitCheck(vo);
    }

    /**
     * 교육 예약 정보를 수정 한다.
     *
     * @param Map
     * @return void
     * @throws Exception
     */
    public int saveEduRsvnModify(Map<String, Object> paramMap, CommandMap commandMap) throws Exception {
        paramMap.put("comcd", Config.COM_CD);

        // 암/복호화 키----------------------------------------------------시작
        String strDbEncKey = EgovProperties.getProperty("Globals.DbEncKey");
        paramMap.put("dbEncKey", strDbEncKey);
        // 암/복호화 키----------------------------------------------------끝

        // TODO: 2021.10.30 상황에 따라 쿼리 추가해야 할 지도.
        // int cnt = edcRsvnMapper.updateEduRsvnMst(paramMap);
        // int cnt = edcRsvnInfoMapper.updateEdcRsvnInfoGroup(paramMap);

        EdcRsvnInfoVO vo = new EdcRsvnInfoVO();
        int cnt = edcRsvnInfoMapper.completeRsvnInfo(vo);

        if (cnt > 0) {
            Map<String, Object> newParamMap = new HashMap<String, Object>();

            // 추가 입력 사항-------------------------------------------------------------------
            int intEdcarsvnProgMItemCnt = 0;
            String strEdcarsvnProgMItemCnt = CommonUtil.getString(paramMap.get("edcarsvnProgMItemCnt"));
            if (!strEdcarsvnProgMItemCnt.equals("")) {
                intEdcarsvnProgMItemCnt = Integer.parseInt(strEdcarsvnProgMItemCnt);
            }
            /*
             * for (int i = 1; i <= intEdcarsvnProgMItemCnt; i++) {
             * newParamMap.clear();
             * newParamMap.put("comcd", Config.COM_CD);
             * newParamMap.put("edcRsvnReqid", paramMap.get("edcRsvnReqid"));
             * newParamMap.put("edcMngitemid", paramMap.get("edcMngitemid" + i));
             * newParamMap.put("edcMngitemValue", paramMap.get("edcMngitemnm" + i));
             * newParamMap.put("reguser", paramMap.get("reguser"));
             * newParamMap.put("moduser", paramMap.get("moduser"));
             * edcRsvnInfoMapper.deleteEdcRsvnAddinfo(newParamMap);
             * edcRsvnInfoMapper.insertEdcRsvnAddinfo(newParamMap);
             * }
             */

            // 첨석자 저장 ---------------------------------------------------------------------
            int intEdcVisitfamilyCntRow = 0;
            String strEdcVisitfamilyCnt = CommonUtil.getString(paramMap.get("edcVisitfamilyCnt"));
            if (!strEdcVisitfamilyCnt.equals("")) {
                intEdcVisitfamilyCntRow = Integer.parseInt(strEdcVisitfamilyCnt);
            }

            // 기존 데이타 삭제
            /*
             * edcRsvnInfoMapper.deleteEdcRsvnFamlyinfo(paramMap);
             * for (int i = 1; i <= intEdcVisitfamilyCntRow; i++) {
             * String name = CommonUtil.getString(paramMap.get("edcRsvnfmName" + i));
             * if (!name.equals("")) {
             * newParamMap.clear();
             * newParamMap.put("comcd", Config.COM_CD);
             * newParamMap.put("edcRsvnReqid", paramMap.get("edcRsvnReqid"));
             * newParamMap.put("edcRsvnfmName", paramMap.get("edcRsvnfmName" + i));
             * newParamMap.put("edcRsvnfmSexgbn", paramMap.get("edcRsvnfmSexgbn" + i));
             * newParamMap.put("edcRsvnfmTelno", paramMap.get("edcRsvnfmTelno" + i));
             * newParamMap.put("dbEncKey", strDbEncKey);
             * // newParamMap.put("reguser", paramMap.get("reguser"));
             * // newParamMap.put("moduser", paramMap.get("moduser"));
             * edcRsvnInfoMapper.insertEdcRsvnFamlyinfo(newParamMap);
             * }
             * }
             */

            return cnt;
        } else {
            return 0;
        }

    }

    /* 당첨가승인 */
    public int doDrawRsvnInfo(EdcRsvnInfoVO vo) throws Exception {
        if (StringUtils.isNotBlank(vo.getPrzwinyn()))
            edcRsvnInfoMapper.insertDrawHistory(vo);

        return edcRsvnInfoMapper.doDrawRsvnInfo(vo);
    }

    /* 당첨되돌리기 */
    public int undoDrawRsvnInfo(EdcRsvnInfoVO vo) throws Exception {
        edcRsvnInfoMapper.insertDrawHistory(vo);
        return edcRsvnInfoMapper.undoDrawRsvnInfo(vo);
    }

    /* 당첨확정 */
    public int confirmDrawRsvnInfo(EdcRsvnInfoVO vo) throws Exception {
        edcRsvnInfoMapper.insertDrawHistory(vo);
        return edcRsvnInfoMapper.confirmDrawRsvnInfo(vo);
    }

    public int updateRsvnInfo1000To2001(EdcRsvnInfoVO vo) throws Exception {
    	vo.setEdcPaywaitEnddatetime(this.selectPaywaitTime(vo));
    	return edcRsvnInfoMapper.updateRsvnInfo1000To2001(vo);
    }
    
    /* 선착대기(1002) 프로그램. 대기취소 */
    public int cancelRsvnInfoWating(EdcRsvnInfoVO vo) throws Exception {
        return edcRsvnInfoMapper.cancelRsvnInfoWating(vo);
    }

    /**
     * 교육 인원정보를 수정한다
     *
     * @param Map
     * @return int
     * @throws Exception
     */
    public int updateRsvnInfoPerson(EdcRsvnInfoVO vo) throws Exception {
        return edcRsvnInfoMapper.updateRsvnInfoPerson(vo);
    }

    /**
     * 이메일 정보를 수정한다
     *
     * @param vo
     *            MemberVO
     * @return int
     * @exception Exception
     */
    public int updateRsvnInfoEmail(EdcRsvnInfoVO vo) throws Exception {
        return edcRsvnInfoMapper.updateRsvnInfoEmail(vo);
    }

    public String checkApply(EdcProgramVO programDetailVO, LoginVO loginVO) {
        // 유효강좌여부체크
    	if (programDetailVO == null)
            return "유효하지 않는 강좌입니다.";

        if("종료".equals(programDetailVO.getEdcStatus())){ 
       		
        	return "접수 기간이 종료되었습니다.";
        }
    
        if ("준비".equals(programDetailVO.getEdcStatus())){
        	return "접수기간이 아닙니다.";
        }

        if(programDetailVO.getEdcStatus().indexOf("마감") >= 0){
        	return "접수가 마감되었습니다.";
        }

        if (Constant.SM_LEREC_TYPE_타기관링크.equals(programDetailVO.getEdcRsvnRectype())) {
            return "외부기관을 통해 접수가능한 강좌입니다.";
        }

        // 비회원신청불가체크
        if ((loginVO == null || !loginVO.isMember()) && Config.NO.equals(programDetailVO.getRsvnNonmebyn())) {// 비회원
            return "회원만 예약 가능한 강좌입니다.";
        }

        // 성별제한체크
        String edcReqGender = StringUtils.defaultIfEmpty(programDetailVO.getEdcReqGender(), Constant.EDC_REQ_GENDER_제한없음);
        String gender = loginVO.getGender(); // 1(남성), 2(여성)
        if (Constant.CM_MEMBER_GENDER_남성.equals(gender)) {
            gender = Constant.EDC_REQ_GENDER_남성;
        } else if (Constant.CM_MEMBER_GENDER_여성.equals(gender)) {
            gender = Constant.EDC_REQ_GENDER_여성;
        }

        // 성별제한 && 회원의성별과 신청강좌의필요성별이다른경우
        if (!Constant.EDC_REQ_GENDER_제한없음.equals(edcReqGender) && !edcReqGender.equals(gender)) {
            return "성별제한이 있는 강좌로 회원님은 신청불가능합니다.";
        }

        // 나이제한체크
        if (Config.YES.equalsIgnoreCase(programDetailVO.getEdcLimitAgeyn())) {
            int age = loginVO.getAge(programDetailVO.getAgeAppgbn()); // 나이계산 (한국나이 , 만나이 체크)

            List<EdcTargetAgeVO> limitAgeList = edcProgramService.selectTargetAgeList(programDetailVO);
            Optional<EdcTargetAgeVO> targetAge = limitAgeList.stream().filter(ageBand -> age >= ageBand.getEdcTargetSage() && age <= ageBand.getEdcTargetEage()).findAny();
            if (!targetAge.isPresent()) {
                return "신청가능한 나이가 아닙니다.";
            }
        }

        return "OK";
    }

    /** 관리자 page 현장예약등록시 예약 제한조건을 체크한다.**/
    public String mngrRsvnValidation(EdcProgramVO programDetailVO, LoginVO loginVO) {
        // 유효강좌여부체크
    	if (programDetailVO == null)
            return "유효하지 않는 강좌입니다.";

            
        if ("준비".equals(programDetailVO.getEdcStatus())){
        	return "접수기간이 아닙니다.";
        }

        if(programDetailVO.getEdcStatus().indexOf("마감") >= 0){
        	return "접수가 마감되었습니다.";
        }

        if (Constant.SM_LEREC_TYPE_타기관링크.equals(programDetailVO.getEdcRsvnRectype())) {
            return "외부기관을 통해 접수가능한 강좌입니다.";
        }

        // 비회원신청불가체크
        if ((loginVO == null || !loginVO.isMember()) && Config.NO.equals(programDetailVO.getRsvnNonmebyn())) {// 비회원
            return "회원만 예약 가능한 강좌입니다.";
        }

        // 성별제한체크
        String edcReqGender = StringUtils.defaultIfEmpty(programDetailVO.getEdcReqGender(), Constant.EDC_REQ_GENDER_제한없음);
        String gender = loginVO.getGender(); // 2(남성), 3(여성)
        if (Constant.CM_MEMBER_GENDER_남성.equals(gender)) {
            gender = Constant.EDC_REQ_GENDER_남성;
        } else if (Constant.CM_MEMBER_GENDER_남성.equals(gender)) {
            gender = Constant.EDC_REQ_GENDER_여성;
        }

        // 성별제한 && 회원의성별과 신청강좌의필요성별이다른경우
        if (!Constant.EDC_REQ_GENDER_제한없음.equals(edcReqGender) && !edcReqGender.equals(gender)) {
            return "성별제한이 있는 강좌로 회원님은 신청불가능합니다.";
        }

        // 나이제한체크
        if (Config.YES.equalsIgnoreCase(programDetailVO.getEdcLimitAgeyn())) {
            int age = loginVO.getAge(programDetailVO.getAgeAppgbn()); // 나이계산 (한국나이 , 만나이 체크)

            List<EdcTargetAgeVO> limitAgeList = edcProgramService.selectTargetAgeList(programDetailVO);
            Optional<EdcTargetAgeVO> targetAge = limitAgeList.stream().filter(ageBand -> age >= ageBand.getEdcTargetSage() && age <= ageBand.getEdcTargetEage()).findAny();
            if (!targetAge.isPresent()) {
                return "신청가능한 나이가 아닙니다.";
            }
        }

        return "OK";
    }
    /**
     * 수업일자목록 조회
     */
    public List<String> selectLectDateList(EdcRsvnInfoVO vo) {
        return edcRsvnInfoMapper.selectLectDateList(vo);
    }

    /**
     * 수료처리를 한다
     *
     * @param vo
     *            MemberVO
     * @return int
     * @exception Exception
     */
    public int updateRsvnInfoComplstat(String comcd, String reguser, String[] rsvnReqId) throws Exception {

        int result = 0;
        for (String seq : rsvnReqId) {
            EdcRsvnInfoVO vo = new EdcRsvnInfoVO();
            vo.setComcd(comcd);
            vo.setReguser(reguser);
            vo.setEdcRsvnReqid(Integer.parseInt(seq));
            vo.setEdcComplstat("2001"); // 수료
            result += edcRsvnInfoMapper.updateRsvnInfoComplstat(vo);
        }

        return result;
    }

    /**
     * 노원구청 3기본(공릉평생교육원+노원평생교육원+장미실습장) 할인감면 횟수제한을 위한 조회
     */
    public List<EdcRsvnInfoVO> selectNowon3BasicOrgRegiList(EdcRsvnInfoVO vo) {
        return edcRsvnInfoMapper.selectNowon3BasicOrgRegiList(vo);
    }
}
