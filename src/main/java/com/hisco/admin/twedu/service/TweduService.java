/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.twedu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hisco.admin.eduadm.mapper.EduAdmMapper;
import com.hisco.admin.eduadm.vo.EdcDaysVO;
import com.hisco.admin.eduadm.vo.EdcProgramVO;
import com.hisco.admin.eduadm.vo.EdcTargetAgeVO;
import com.hisco.admin.instrctr.mapper.InstrctrMapper;
import com.hisco.admin.instrctr.vo.InstrctrVO;
import com.hisco.admin.twedu.mapper.TweduMapper;
import com.hisco.admin.twedu.vo.TweduAttendVO;
import com.hisco.admin.twedu.vo.TweduDetailVO;
import com.hisco.admin.twedu.vo.TweduJoinVO;
import com.hisco.admin.twedu.vo.TweduPlanVO;
import com.hisco.admin.twedu.vo.TweduReportVO;
import com.hisco.admin.twedu.vo.TweduStudentVO;
import com.hisco.admin.twedu.vo.TweduVO;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : TweduService.java
 * @Description : 마을배움터 관리 Servie
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 3
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Service("tweduService")
public class TweduService extends EgovAbstractServiceImpl {

    @Resource(name = "tweduMapper")
    private TweduMapper tweduMapper;

    @Resource(name = "eduAdmMapper")
    private EduAdmMapper eduAdmMapper;

    @Resource(name = "instrctrMapper")
    private InstrctrMapper instrctrMapper;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    public List<TweduVO> selectList(TweduVO searchVO) {
        searchVO.setComcd(Config.COM_CD);
        return tweduMapper.selectList(searchVO);
    }

    public TweduDetailVO select(TweduVO searchVO) {
        searchVO.setComcd(Config.COM_CD);

        TweduDetailVO result = tweduMapper.select(searchVO); // 기본정보

        List<TweduPlanVO> plans = tweduMapper.selectPlans(searchVO); // 학습계획

        EdcProgramVO paramMap = new EdcProgramVO();
        paramMap.setComcd(searchVO.getComcd());
        paramMap.setEdcPrgmNo(searchVO.getEdcPrgmNo());

        List<EdcDaysVO> days = eduAdmMapper.selectProgramDays(paramMap); // 교육요일

        // 신청연령제한
        if ("Y".equals(result.getEdcLimitAgeyn())) {
            List<EdcTargetAgeVO> targets = eduAdmMapper.selectEdcTargetAge(paramMap);
            if (targets != null && targets.size() > 0) {
                result.setEdcTargetSage(String.valueOf(targets.get(0).getEdcTargetSage()));
                result.setEdcTargetEage(String.valueOf(targets.get(0).getEdcTargetEage()));
            }
        }

        result.setEdcPlanList(plans);
        result.setEdcDaysList(days);

        return result;
    }

    public List<TweduPlanVO> selectPlans(TweduVO searchVO) {
        searchVO.setComcd(Config.COM_CD);
        List<TweduPlanVO> plans = tweduMapper.selectPlans(searchVO);
        return plans;
    }

    public int insert(TweduDetailVO detailVO, InstrctrVO instrctrVO, Map<String, MultipartFile> files, LoginVO user)
            throws Exception {
        int result = 0;
        String userId = user.getId();

        detailVO.setComcd(Config.COM_CD);
        detailVO.setModuser(userId);
        detailVO.setReguser(userId);
        detailVO.setEdcOpenyn("Y");
        detailVO.setUseYn("Y");

        String edcFileid = "";
        String edcPlanFileid = "";

        if (files != null) {
            List<FileVO> result1 = fileUtil.parseFileInf(files, "TWEDU_", 0, "", "", user.getId(), "file_1");
            if (result1 != null && result1.size() > 0) {
                fileMngService.insertFileInfs(result1);
                edcFileid = result1.get(0).getFileGrpinnb();
                detailVO.setEdcImgFielid(edcFileid);
            }

            List<FileVO> result3 = fileUtil.parseFileInf(files, "TWEDU_", 0, "", "", user.getId(), "file_3");
            if (result3 != null && result3.size() > 0) {
                fileMngService.insertFileInfs(result3);
                edcPlanFileid = result3.get(0).getFileGrpinnb();
                detailVO.setEdcPlanFileid(edcPlanFileid);
            }
        }

        /* 강사정보 저장 (마을배움터는 별도 저장 안함) */
        /*
         * instrctrVO.setReguser(detailVO.getReguser());
         * result += instrctrMapper.insert(instrctrVO);
         * int instrctrNo = instrctrMapper.selectLastSeq();
         * detailVO.setinstrctrNo(instrctrNo);
         */

        /* 기본정보 저장 */
        result = eduAdmMapper.insertEdcProgram(detailVO);

        /* 모집정보 저장 */
        String seq = eduAdmMapper.selectProgramSetSeq(detailVO);
        detailVO.setEdcRsvnsetSeq(seq);
        eduAdmMapper.insertEdcProgramSetInfo(detailVO);

        /* 품목(요금)정보 저장 */
        result += eduAdmMapper.insertProgramItem(detailVO);
        result += eduAdmMapper.insertEdcProgramItem(detailVO);

        /* 연령제한 정보 저장 */
        if (detailVO.getEdcAgeList() != null) {
            for (EdcTargetAgeVO targetVO : detailVO.getEdcAgeList()) {
                targetVO.setComcd(detailVO.getComcd());
                targetVO.setEdcPrgmNo(detailVO.getEdcPrgmNo());
                targetVO.setReguser(detailVO.getReguser());
                targetVO.setEdcAgeItemcd(detailVO.getItemCd());
                result += eduAdmMapper.insertEdcProgramTargetAge(targetVO);
            }
        }

        /* 교육요일 정보 저장 */
        if (detailVO.getEdcDaysList() != null) {
            for (EdcDaysVO daysVO : detailVO.getEdcDaysList()) {
                if (daysVO.getDayChk() != null && !daysVO.getDayChk().equals("")) {
                    daysVO.setComcd(detailVO.getComcd());
                    daysVO.setEdcPrgmNo(detailVO.getEdcPrgmNo());
                    daysVO.setReguser(detailVO.getReguser());
                    result += eduAdmMapper.insertEdcDays(daysVO);
                }
            }
        }

        /* 수업계획서 정보 저장 */
        List<TweduPlanVO> plans = detailVO.getEdcPlanList();
        if (plans != null && !plans.isEmpty()) {
            tweduMapper.deletePlans(detailVO);
            result += tweduMapper.insertPlans(plans);
        }

        return result;
    }

    public int update(TweduDetailVO vo, LoginVO user) throws Exception {
        return update(vo, null, null, user);
    }

    public int update(TweduDetailVO vo, InstrctrVO instrctrVO, Map<String, MultipartFile> files, LoginVO user)
            throws Exception {
        int result = 0;
        String userId = user.getId();

        vo.setComcd(Config.COM_CD);
        vo.setModuser(userId);
        vo.setReguser(userId);

        String edcFileid = "";
        String edcPlanFileid = "";

        if (files != null) {
            List<FileVO> result1 = fileUtil.parseFileInf(files, "TWEDU_", 0, "", "", user.getId(), "file_1");
            if (result1 != null && result1.size() > 0) {
                fileMngService.insertFileInfs(result1);
                edcFileid = result1.get(0).getFileGrpinnb();
                vo.setEdcImgFielid(edcFileid);
            }

            List<FileVO> result3 = fileUtil.parseFileInf(files, "TWEDU_", 0, "", "", user.getId(), "file_3");
            if (result3 != null && result3.size() > 0) {
                fileMngService.insertFileInfs(result3);
                edcPlanFileid = result3.get(0).getFileGrpinnb();
                vo.setEdcPlanFileid(edcPlanFileid);
            }
        }
        /* 강사정보 저장 */
        if (instrctrVO != null) {
            instrctrVO.setModuser(vo.getReguser());
            result += instrctrMapper.updateSimple(instrctrVO);
        }

        /* 기본정보 저장 */
        result = tweduMapper.update(vo);

        /* 품목(요금)정보 저장 - 유료일시에만 저장 */
        result += eduAdmMapper.updateProgramItem(vo);

        /* 모집정보 저장 */
        result += eduAdmMapper.updateEdcProgramRsvnSetInfo(vo);

        /* 연령제한 정보 저장 */
        eduAdmMapper.deleteEdcProgramTarget(vo.getComcd(), vo.getEdcPrgmNo(), null);
        if (vo.getEdcAgeList() != null) {
            for (EdcTargetAgeVO targetVO : vo.getEdcAgeList()) {
                targetVO.setComcd(vo.getComcd());
                targetVO.setEdcPrgmNo(vo.getEdcPrgmNo());
                targetVO.setReguser(vo.getReguser());
                targetVO.setEdcAgeItemcd(vo.getItemCd());

                result += eduAdmMapper.insertEdcProgramTargetAge(targetVO);

            }
        }

        /* 교육요일 정보 저장 */
        eduAdmMapper.deleteEdcProgaramWeek(vo); // 삭제
        if (vo.getEdcDaysList() != null) {
            for (EdcDaysVO daysVO : vo.getEdcDaysList()) {
                if (daysVO.getDayChk() != null && !daysVO.getDayChk().equals("")) {
                    daysVO.setComcd(vo.getComcd());
                    daysVO.setEdcPrgmNo(vo.getEdcPrgmNo());
                    daysVO.setReguser(vo.getReguser());
                    result += eduAdmMapper.insertEdcDays(daysVO);
                }
            }
        }

        // eduAdmMapper.deleteEdcProgaramWeek
        /* 수업계획서 정보 저장 */
        List<TweduPlanVO> plans = vo.getEdcPlanList();
        if (plans != null && !plans.isEmpty()) {
            tweduMapper.deletePlans(vo);
            result += tweduMapper.insertPlans(plans);
        }

        return result;
    }

    public int updateStatus(TweduVO tweduVO, LoginVO user) {
        tweduVO.setComcd(Config.COM_CD);
        tweduVO.setModuser(user.getId());
        return tweduMapper.updateStatus(tweduVO);
    }

    public int batchUpdateStatus(List<TweduVO> list, LoginVO user) {
        int result = 0;
        for (TweduVO vo : list) {
            result += updateStatus(vo, user);
        }
        return result;
    }

    public int batchUpdateRsvn(List<TweduDetailVO> list, LoginVO user) {
        int result = 0;
        for (TweduDetailVO vo : list) {
            vo.setComcd(Config.COM_CD);
            vo.setModuser(user.getId());

            tweduMapper.updateRsvnSet(vo);
            result += tweduMapper.updateRsvn(vo);

        }
        return result;
    }

    public List<TweduStudentVO> selectStudentList(TweduVO vo) {
        vo.setComcd(Config.COM_CD);
        return tweduMapper.selectStudentList(vo);
    }

    public int updateStudentStatus(TweduStudentVO vo) {
        vo.setComcd(Config.COM_CD);
        return tweduMapper.updateStudStatus(vo);
    }

    public int batchUpdateStudentStatus(List<TweduStudentVO> list) {
        int result = 0;

        for (TweduStudentVO vo : list) {
            vo.setComcd(Config.COM_CD);
            result += tweduMapper.updateStudStatus(vo);
        }

        return result;
    }

    public List<TweduPlanVO> selectPlanList(TweduVO vo) {
        vo.setComcd(Config.COM_CD);
        return tweduMapper.selectPlanList(vo);
    }

    public List<TweduAttendVO> selectAttendanceList(TweduVO vo) {
        vo.setComcd(Config.COM_CD);
        return tweduMapper.selectAttendanceList(vo);
    }

    public List<TweduAttendVO> selectAttendStudents(TweduVO vo) {
        vo.setComcd(Config.COM_CD);
        return tweduMapper.selectAttendStudents(vo);
    }

    public int updateAttendanceList(List<TweduAttendVO> attendances, LoginVO user, int edcPrgmid) {

        int result = 0;
        for (TweduAttendVO vo : attendances) {
            vo.setComcd(Config.COM_CD);
            vo.setReguser(user.getId());
            vo.setModuser(user.getId());
            vo.setEdcPrgmid(edcPrgmid);

            int cnt = tweduMapper.updateAttendance(vo);
            if (cnt <= 0) {
                cnt = tweduMapper.insertAttendance(vo);
            }

            result += cnt;
        }

        return result;
    }

    public List<TweduPlanVO> selectLogList(TweduVO vo) {
        vo.setComcd(Config.COM_CD);
        return tweduMapper.selectReportLog(vo);
    }

    public int createLog(TweduPlanVO vo, Object tempFile) {
        vo.setComcd(Config.COM_CD);
        int result = 0;
        try {
            String lectFileid = saveTempFile(vo.getLectFileid(), vo.getReguser(), tempFile);
            vo.setLectFileid(lectFileid);
            result = tweduMapper.insertLog(vo);
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }

    public int updateLog(TweduPlanVO vo, Object tempFile) {
        vo.setComcd(Config.COM_CD);
        int result = 0;
        try {
            String lectFileid = saveTempFile(vo.getLectFileid(), vo.getModuser(), tempFile);
            vo.setLectFileid(lectFileid);
            result = tweduMapper.updateLog(vo);
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }

    public int batchUpdateLog(List<TweduPlanVO> list, Map<String, MultipartFile> files, LoginVO user) throws Exception {
        /* 파일 저장 */
        if (files != null) {
            Set<String> keys = files.keySet();
            for (String key : keys) {
                int idx = Integer.parseInt(key.replace("log_file_", ""));
                String lectFileid = list.get(idx).getLectFileid();

                List<FileVO> result = fileUtil.parseFileInf(files, "TWEDU_", 0, lectFileid, "", user.getId(), key);

                if (result != null && result.size() > 0) {
                    // 기존 파일이 있으면 삭제
                    if (lectFileid != null && !lectFileid.equals("")) {
                        // fileMngService.deleteFileInf(result.get(0));
                        fileMngService.updateFileInfs(result);

                    } else {
                        fileMngService.insertFileInfs(result);
                    }

                    lectFileid = result.get(0).getFileGrpinnb();
                    list.get(idx).setLectFileid(lectFileid);
                }
            }
        }
        /* 데이터 저장 */
        int result = 0;
        for (TweduPlanVO vo : list) {
            int cnt = tweduMapper.updateLog(vo);
            if (cnt < 1) {
                cnt = tweduMapper.insertLog(vo);
            }

            result += cnt;
        }

        return result;
    }

    public TweduReportVO selectReport(TweduVO vo) {
        vo.setComcd(Config.COM_CD);
        TweduReportVO result = new TweduReportVO();
        TweduDetailVO header = tweduMapper.selectReportHead(vo);
        vo.setSearchStartDts(header.getEdcSdate().replaceAll("-", ""));
        vo.setSearchEndDts(header.getEdcEdate().replaceAll("-", ""));

        result.setHeader(header);
        result.setAttendList(tweduMapper.selectReportAtend(vo));
        result.setLogList(tweduMapper.selectReportLog(vo));
        result.setPlanList(tweduMapper.selectReportPlan(vo));
        return result;
    }

    public int selectOrgNo() {
        Integer result = tweduMapper.selectOrgNo();

        return result == null ? 0 : result;
    }

    public TweduVO selectEduTerm(TweduVO vo) {
        vo.setComcd(Config.COM_CD);
        return tweduMapper.selectEduTerm(vo);
    }

    public void deleteTempFile(Object tempFile) {
        if (tempFile != null) {
            List<FileVO> tempFiles = new ArrayList<>();
            tempFiles.add((FileVO) tempFile);
            fileUtil.deleteTempFile(tempFiles);
        }
    }

    public List<TweduJoinVO> selectJoinList(TweduJoinVO vo) {
        return tweduMapper.selectJoinList(vo);
    }

    private String saveTempFile(String atchFileId, String userId, Object tempFile) throws Exception {

        String _atchFileId = atchFileId;
        if (tempFile != null) {
            FileVO _tempFile = (FileVO) tempFile;
            List<FileVO> list = new ArrayList<>();
            list.add(_tempFile);

            if (!_atchFileId.equals("")) {
                // 기존 파일 삭제
                _tempFile.setFileGrpinnb(_atchFileId);
                fileMngService.deleteAndInsert(_tempFile, list);
            } else {
                fileUtil.prepareToInsert(list, userId);
                _atchFileId = fileMngService.insertFileInfs(list);
            }
        }
        return _atchFileId;

    }

    public int insertReportLogPlan(TweduVO vo) {
        return tweduMapper.insertReportLogPlan(vo);
    }
}
