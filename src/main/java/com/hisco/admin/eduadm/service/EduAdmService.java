package com.hisco.admin.eduadm.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.eduadm.mapper.EduAdmMapper;
import com.hisco.admin.eduadm.vo.EdcDaysVO;
import com.hisco.admin.eduadm.vo.EdcNoticeVO;
import com.hisco.admin.eduadm.vo.EdcProgmLimitVO;
import com.hisco.admin.eduadm.vo.EdcProgramVO;
import com.hisco.admin.eduadm.vo.EdcTargetAgeVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 교육프로그램신청관리 Service 구현 클래스
 *
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 */
@Service("eduAdmService")
public class EduAdmService extends EgovAbstractServiceImpl {

    @Resource(name = "eduAdmMapper")
    private EduAdmMapper eduAdmMapper;

    /**
     * 교육프로그램 목록을 조회한다.
     *
     * @param EdcProgramVO
     * @return List
     * @throws Exception
     */
    public List<HashMap<String, Object>> selectEdcProgramList(HashMap<String, Object> parameter) throws Exception {
        return eduAdmMapper.selectEdcProgramList(parameter);
    }

    /**
     * 교육프로그램 을 등록한다
     *
     * @param EdcProgramVO
     * @return int
     * @throws Exception
     */
    public int insertEdcProgram(EdcProgramVO paramMap) throws Exception {
        int cnt = eduAdmMapper.insertEdcProgram(paramMap);

        if (paramMap.getItemCd() > 0) {
            eduAdmMapper.insertProgramItem(paramMap);
            eduAdmMapper.insertEdcProgramItem(paramMap);
        }

        String seq = eduAdmMapper.selectProgramSetSeq(paramMap);
        paramMap.setEdcRsvnsetSeq(seq);
        eduAdmMapper.insertEdcProgramSetInfo(paramMap);

        // 공지 등록
        paramMap.getNoticeVO().setEdcPrgmNo(paramMap.getEdcPrgmNo());
        paramMap.getNoticeVO().setComcd(paramMap.getComcd());
        eduAdmMapper.insertEdcProgramNotice(paramMap.getNoticeVO());

        if (paramMap.getEdcAgeList() != null) {
            for (EdcTargetAgeVO targetVO : paramMap.getEdcAgeList()) {
                targetVO.setComcd(paramMap.getComcd());
                targetVO.setEdcPrgmNo(paramMap.getEdcPrgmNo());
                targetVO.setReguser(paramMap.getReguser());
                targetVO.setEdcAgeItemcd(paramMap.getItemCd());
                eduAdmMapper.insertEdcProgramTargetAge(targetVO);
            }
        }

        if (paramMap.getEdcDaysList() != null) {
            for (EdcDaysVO daysVO : paramMap.getEdcDaysList()) {
                if (daysVO.getDayChk() != null && !daysVO.getDayChk().equals("")) {
                    daysVO.setComcd(paramMap.getComcd());
                    daysVO.setEdcPrgmNo(paramMap.getEdcPrgmNo());
                    daysVO.setReguser(paramMap.getReguser());
                    eduAdmMapper.insertEdcDays(daysVO);
                }
            }
        }

        if (paramMap.getEdcLimitList() != null) {
            for (EdcProgmLimitVO limitVO : paramMap.getEdcLimitList()) {
                limitVO.setReguser(paramMap.getReguser());
                limitVO.setComcd(paramMap.getComcd());
                limitVO.setEdcPrgmNo(paramMap.getEdcPrgmNo());
                eduAdmMapper.insertEdcProgramLimit(limitVO);
            }
        }
        return cnt;
    }

    public List<EdcDaysVO> selectProgramDays(EdcProgramVO paramMap) {
        return eduAdmMapper.selectProgramDays(paramMap);
    }

    public List<EdcProgramVO> selectProgramRsvnSetList(EdcProgramVO paramMap) {
        return eduAdmMapper.selectProgramRsvnSetList(paramMap);
    }

    public String selectProgramRsvnSetMax(EdcProgramVO paramMap) {
        return eduAdmMapper.selectProgramRsvnSetMax(paramMap);
    }

    public EdcProgramVO selectProgramRsvnSetDetail(EdcProgramVO paramMap) {
        EdcProgramVO edcProgramVO = eduAdmMapper.selectProgramRsvnSetDetail(paramMap);

        String eduStime = edcProgramVO.getEdcStime();
        String eduEtime = edcProgramVO.getEdcEtime();
        String eduRsvnStime = edcProgramVO.getEdcRsvnStime();
        String eduRsvnEtime = edcProgramVO.getEdcRsvnEtime();
        String paywaitTime = edcProgramVO.getEdcPaywaitHhmm();

        if (eduStime != null && eduStime.length() == 4) {
            edcProgramVO.setEdcStimeHour(eduStime.substring(0, 2));
            edcProgramVO.setEdcStimeMin(eduStime.substring(2, 4));
            edcProgramVO.setEdcEtimeHour(eduEtime.substring(0, 2));
            edcProgramVO.setEdcEtimeMin(eduEtime.substring(2, 4));
        }

        if (eduStime != null && eduStime.length() == 4) {
            edcProgramVO.setEdcRsvnStimeHour(eduRsvnStime.substring(0, 2));
            edcProgramVO.setEdcRsvnStimeMin(eduRsvnStime.substring(2, 4));
            edcProgramVO.setEdcRsvnEtimeHour(eduRsvnEtime.substring(0, 2));
            edcProgramVO.setEdcRsvnEtimeMin(eduRsvnEtime.substring(2, 4));
        }

        if (paywaitTime != null && paywaitTime.length() == 4) {
            edcProgramVO.setEdcPaywaitHour(paywaitTime.substring(0, 2));
            edcProgramVO.setEdcPaywaitMin(paywaitTime.substring(2, 4));
        }

        return edcProgramVO;
    }

    public EdcProgramVO selectEdcProgramDetail(EdcProgramVO paramMap) {

        EdcProgramVO edcProgramVO = eduAdmMapper.selectEdcProgramDetail(paramMap);
        String eduStime = edcProgramVO.getEdcStime();
        String eduEtime = edcProgramVO.getEdcEtime();
        String eduRsvnStime = edcProgramVO.getEdcRsvnStime();
        String eduRsvnEtime = edcProgramVO.getEdcRsvnEtime();
        String paywaitTime = edcProgramVO.getEdcPaywaitHhmm();

        if (eduStime != null && eduStime.length() == 4) {
            edcProgramVO.setEdcStimeHour(eduStime.substring(0, 2));
            edcProgramVO.setEdcStimeMin(eduStime.substring(2, 4));
            edcProgramVO.setEdcEtimeHour(eduEtime.substring(0, 2));
            edcProgramVO.setEdcEtimeMin(eduEtime.substring(2, 4));
        }

        if (eduStime != null && eduStime.length() == 4) {
            edcProgramVO.setEdcRsvnStimeHour(eduRsvnStime.substring(0, 2));
            edcProgramVO.setEdcRsvnStimeMin(eduRsvnStime.substring(2, 4));
            edcProgramVO.setEdcRsvnEtimeHour(eduRsvnEtime.substring(0, 2));
            edcProgramVO.setEdcRsvnEtimeMin(eduRsvnEtime.substring(2, 4));
        }

        if (paywaitTime != null && paywaitTime.length() == 4) {
            edcProgramVO.setEdcPaywaitHour(paywaitTime.substring(0, 2));
            edcProgramVO.setEdcPaywaitMin(paywaitTime.substring(2, 4));
        }

        return edcProgramVO;
    }

    public EdcNoticeVO selectEdcNotice(EdcProgramVO paramMap) {
        return eduAdmMapper.selectEdcNotice(paramMap);
    }

    public List<EdcTargetAgeVO> selectEdcTargetAge(EdcProgramVO paramMap) {
        return eduAdmMapper.selectEdcTargetAge(paramMap);
    }

    // 강좌 이미지 수정
    public void updateEdcProgramFileid(String comcd, int EdcPrgmNo, String inputid, String atchFileId, String moduser) {
        eduAdmMapper.updateEdcProgramFileid(comcd, EdcPrgmNo, inputid, atchFileId, moduser);
    }

    public int updateEdcProgram(EdcProgramVO paramMap) {
        eduAdmMapper.updateProgramItem(paramMap);
        return eduAdmMapper.updateEdcProgram(paramMap);
    }

    public int updateEdcProgramForceClose(EdcProgramVO paramVO) {
        // 모집정보 설정이력 수정
        int cnt = eduAdmMapper.updateEdcProgramForceClose(paramVO);
        if (cnt > 0) {
            // 최신 설정이력인 경우 교육프로그램도 업데이트
            eduAdmMapper.updateEdcProgramForceClose2(paramVO);
        }

        return cnt;
    }

    public int updateEdcProgramIntro(EdcProgramVO paramMap) {
        return eduAdmMapper.updateEdcProgramIntro(paramMap);
    }

    public int updateEdcProgramNotice(EdcNoticeVO paramMap) {
        return eduAdmMapper.updateEdcProgramNotice(paramMap);
    }

    public int updateEdcProgramTarget(EdcProgramVO paramMap, String[] targetSeq) {
        int cnt = eduAdmMapper.updateEdcProgramGender(paramMap);

        if (targetSeq != null && targetSeq.length > 0) {
            // 삭제
            eduAdmMapper.deleteEdcProgramTarget(paramMap.getComcd(), paramMap.getEdcPrgmNo(), Arrays.asList(targetSeq));
        } else {
            eduAdmMapper.deleteEdcProgramTarget(paramMap.getComcd(), paramMap.getEdcPrgmNo(), null);
        }

        if (paramMap.getEdcAgeList() != null && paramMap.getEdcAgeList().size() > 0) {
            for (EdcTargetAgeVO targetVO : paramMap.getEdcAgeList()) {
                targetVO.setComcd(paramMap.getComcd());
                targetVO.setEdcPrgmNo(paramMap.getEdcPrgmNo());
                targetVO.setModuser(paramMap.getModuser());
                targetVO.setReguser(paramMap.getModuser());

                if (targetVO.getEdcAgeTargetSeq() > 0) {
                    eduAdmMapper.updateEdcProgramTarget(targetVO);
                } else {
                    eduAdmMapper.insertEdcProgramTargetAge(targetVO);

                }
            }
        }

        return cnt;
    }

    public String updateEdcProgramRsvn(EdcProgramVO paramMap) {
        int cnt = 0;
        String msg = "";

        // 신규 모집 등록
        if (paramMap.getEdcRsvnsetSeq() == null || paramMap.getEdcRsvnsetSeq().equals("")) {
            cnt = eduAdmMapper.updateEdcProgramRsvn(paramMap); // 모집정보 업데이트 하고

            // 설정 이력 등록한다
            if (cnt > 0) {
                String seq = eduAdmMapper.selectProgramSetSeq(paramMap);
                paramMap.setEdcRsvnsetSeq(seq);
                eduAdmMapper.insertEdcProgramSetInfo(paramMap);

                msg = "신규 모집정보가 등록되었습니다.";
            } else {
                msg = "데이타 없음";
            }
        } else {
            String maxSeq = eduAdmMapper.selectProgramRsvnSetMax(paramMap);

            cnt = eduAdmMapper.updateEdcProgramRsvnSetInfo(paramMap);

            // 마지막 차수를 수정했을 경우 edc_program 의 데이타도 수정한다
            if (maxSeq != null && maxSeq.equals(paramMap.getEdcRsvnsetSeq())) {
                eduAdmMapper.updateEdcProgramRsvn(paramMap);
            }

            msg = "모집정보가 수정되었습니다.";
        }

        // 요일정보 수정
        eduAdmMapper.deleteEdcProgaramWeek(paramMap);
        if (paramMap.getEdcDaysList() != null) {
            for (EdcDaysVO daysVO : paramMap.getEdcDaysList()) {
                if (daysVO.getDayChk() != null && !daysVO.getDayChk().equals("")) {
                    daysVO.setComcd(paramMap.getComcd());
                    daysVO.setEdcPrgmNo(paramMap.getEdcPrgmNo());
                    daysVO.setReguser(paramMap.getReguser());
                    eduAdmMapper.insertEdcDays(daysVO);
                }
            }
        }

        return msg;
    }

    // 상태변경
    public void updateProgramStatus(String comcd, int prgmId, String type, String val, String moduser) {
        eduAdmMapper.updateProgramStatus(comcd, prgmId, type, val, moduser);
    }

    // 모집설정 일괄등록
    public String insertEdcProgramRsvnOneclick(EdcProgramVO paramMap) {
        int cnt = 0;
        String msg = "";

        cnt = eduAdmMapper.updateEdcProgramRsvn(paramMap); // 모집정보 업데이트 하고

        // 설정 이력 등록한다
        if (cnt > 0) {
            eduAdmMapper.insertEdcProgramSetInfo(paramMap);

            msg = cnt + "개의 프로그램이 일괄 차수개설되었습니다.";
        } else {
            msg = "데이타 없음";
        }

        return msg;
    }

    // 모집설정 일괄 수정
    public String updateEdcProgramOneclick(EdcProgramVO paramMap) {
        int cnt = 0;
        String msg = "";

        eduAdmMapper.updateEdcProgramRsvnSetInfoOneclick(paramMap); //차수 정보 수정

        cnt = eduAdmMapper.updateEdcProgramOneclick(paramMap); // 모집정보 업데이트

        // 요일정보 수정
        /*
        if (paramMap.getEdcDaysList() != null) {
            eduAdmMapper.deleteEdcProgaramWeek(paramMap);

            for (EdcDaysVO daysVO : paramMap.getEdcDaysList()) {
                if (daysVO.getDayChk() != null && !daysVO.getDayChk().equals("")) {
                    daysVO.setComcd(paramMap.getComcd());
                    daysVO.setTargetCtgcd(paramMap.getTargetCtgcd());
                    daysVO.setTargetOrgNo(paramMap.getTargetOrgNo());
                    daysVO.setReguser(paramMap.getReguser());
                    eduAdmMapper.insertEdcDays(daysVO);
                }
            }
        }
        */

        // 설정 이력 수정 ( 최신 회차)
        if (cnt > 0) {
           msg = cnt + "개의 프로그램이 일괄 수정 되었습니다.";
        } else {
            msg = "데이타 없음";
        }

        return msg;
    }

    // 외부기관 기본 정보 수정
    public String updateExtEdcProgramRsvn(EdcProgramVO paramMap) {
        int cnt = 0;
        String msg = "";

        String maxSeq = eduAdmMapper.selectProgramRsvnSetMax(paramMap);
        paramMap.setEdcRsvnsetSeq(maxSeq);

        cnt = eduAdmMapper.updateEdcProgramRsvnSetInfo(paramMap);

        // edc_program 의 데이타도 수정한다
        eduAdmMapper.updateEdcProgramRsvn(paramMap);

        // 기본정보
        eduAdmMapper.updateEdcProgram(paramMap);

        msg = "모집정보가 수정되었습니다.";

        // 요일정보 수정
        eduAdmMapper.deleteEdcProgaramWeek(paramMap);
        if (paramMap.getEdcDaysList() != null) {
            for (EdcDaysVO daysVO : paramMap.getEdcDaysList()) {
                if (daysVO.getDayChk() != null && !daysVO.getDayChk().equals("")) {
                    daysVO.setComcd(paramMap.getComcd());
                    daysVO.setEdcPrgmNo(paramMap.getEdcPrgmNo());
                    daysVO.setReguser(paramMap.getReguser());
                    eduAdmMapper.insertEdcDays(daysVO);
                }
            }
        }

        return msg;
    }


    public void deleteEdcProgramRsvn(EdcProgramVO paramMap) {
        // 요일정보 삭제
        eduAdmMapper.deleteEdcProgaramWeek(paramMap);
        //나이제한 삭제
        eduAdmMapper.deleteEdcProgramTarget(paramMap.getComcd(), paramMap.getEdcPrgmNo(), null);

        eduAdmMapper.deleteEdcProgramNotice(paramMap);

        eduAdmMapper.deleteEdcProgramItem(paramMap);

        eduAdmMapper.deleteEdcProgramSetinfo(paramMap);

        eduAdmMapper.deleteEdcProgram(paramMap);

    }


    public List<EgovMap> selectClassSchedule(com.hisco.user.edcatnlc.vo.EdcProgramVO vo){
    	return  eduAdmMapper.selectClassSchedule(vo);
    }

    public List<EgovMap> selectRsvnsetNameList(EdcProgramVO vo){
    	return  eduAdmMapper.selectRsvnsetNameList(vo);
    }
}

