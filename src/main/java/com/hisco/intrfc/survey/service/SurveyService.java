package com.hisco.intrfc.survey.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.intrfc.survey.mapper.SurveyMapper;
import com.hisco.intrfc.survey.vo.SurveyMstVO;
import com.hisco.intrfc.survey.vo.SurveyQstItemVO;
import com.hisco.intrfc.survey.vo.SurveyQstVO;
import com.hisco.intrfc.survey.vo.SurveyResultDetailVO;
import com.hisco.intrfc.survey.vo.SurveyResultVO;
import com.hisco.intrfc.survey.vo.SurveyRsvnVO;
import com.hisco.intrfc.survey.vo.SurveySendVO;
import com.hisco.intrfc.survey.vo.SurveyStdrmngVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 설문 대상 전송
 * 
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.05 최초작성
 */
@Slf4j
@Service("surveyService")
public class SurveyService extends EgovAbstractServiceImpl {

    @Resource(name = "surveyMapper")
    private SurveyMapper surveyMapper;

    /**
     * 설문조사주제등록
     */
    public int insertThema(SurveyMstVO vo) {
        surveyMapper.insertThema(vo);
        return 1;
    }

    /**
     * 설문조사주제갱신
     */
    public int updateThema(SurveyMstVO vo) {
        surveyMapper.updateThema(vo);
        return 1;
    }

    /**
     * openYn, useYn 수정
     */
    public int stdrmngFieldUpdate(SurveyMstVO vo) {
        surveyMapper.stdrmngFieldUpdate(vo);
        return 1;
    }

    /**
     * 설문조사주세삭제
     */
    public int deleteThema(SurveyMstVO vo) {
        surveyMapper.deleteThema(vo);
        return 1;
    }

    /**
     * 설문조사주세삭제
     */
    public List<SurveyMstVO> selectThemaList(SurveyMstVO vo) {
        return surveyMapper.selectThemaList(vo);
    }

    public List<SurveyMstVO> selectThemaDetailList(SurveyMstVO vo) {
        return surveyMapper.selectThemaDetailList(vo);
    }

    public SurveyMstVO selectThemaDetail(int qestnarId) {
        SurveyMstVO vo = new SurveyMstVO();
        vo.setQestnarId(qestnarId);
        return surveyMapper.selectThemaDetail(vo);
    }

    public int saveQuestion(List<SurveyQstVO> questionList) {
        if (questionList == null || questionList.isEmpty())
            return 0;

        int qestnarId = questionList.get(0).getQestnarId();
        // 전체질문항목삭제
        deleteQuestionItem(qestnarId);
        // 전체질문삭제
        deleteQuestion(qestnarId);

        questionList.stream().forEach(qst -> {
            insertQuestion(qst); // 질문입력
            if (qst.getItemList() != null) {
                qst.getItemList().stream().forEach(item -> {
                    if (StringUtils.isNotBlank(item.getQestnarItemnm()))
                        insertQuestionItem(item); // 질문항목입력
                });
            }
        });

        return 1;
    }

    /**
     * 설문조사질문등록
     */
    public int insertQuestion(SurveyQstVO vo) {
        return surveyMapper.insertQuestion(vo);
    }

    /**
     * 설문조사질문갱신
     */
    public int updateQuestion(SurveyQstVO vo) {
        return surveyMapper.updateQuestion(vo);
    }

    /**
     * 설문조사질문삭제
     */
    public int deleteQuestion(int qestnarId) {
        SurveyQstVO vo = new SurveyQstVO();
        vo.setQestnarId(qestnarId);
        return surveyMapper.deleteQuestion(vo);
    }

    /**
     * 설문조사질문목록
     */
    public List<SurveyQstVO> selectQuestionList(SurveyQstVO vo) {
        return surveyMapper.selectQuestionList(vo);
    }

    public SurveyQstVO selectQuestionDetail(SurveyQstVO vo) {
        return surveyMapper.selectQuestionDetail(vo);
    }

    /**
     * 설문조사질문항목등록
     */
    public int insertQuestionItem(SurveyQstItemVO vo) {
        return surveyMapper.insertQuestionItem(vo);
    }

    /**
     * 설문조사질문항목갱신
     */
    public int updateSurveyQuestionItem(SurveyQstItemVO vo) {
        return surveyMapper.updateQuestionItem(vo);
    }

    /**
     * 설문조사질문항목삭제
     */
    public int deleteQuestionItem(int qestnarId) {
        SurveyQstItemVO vo = new SurveyQstItemVO();
        vo.setQestnarId(qestnarId);
        return surveyMapper.deleteQuestionItem(vo);
    }

    /**
     * 설문조사질문항목목록
     */
    public List<SurveyQstItemVO> selectQuestionItemList(int qestnarId, int qestnsSeq) {
        SurveyQstItemVO vo = new SurveyQstItemVO();
        vo.setQestnarId(qestnarId);
        vo.setQestnsSeq(qestnsSeq);
        return selectQuestionItemList(vo);
    }

    /**
     * 설문조사질문항목목록 (결과 포함)
     */
    public List<SurveyQstItemVO> selectQuestionItemList(int qestnarId, int qestnsSeq, int qestnarStdno, int total) {
        SurveyQstItemVO vo = new SurveyQstItemVO();
        vo.setQestnarId(qestnarId);
        vo.setQestnsSeq(qestnsSeq);
        vo.setQestnarStdno(qestnarStdno);
        vo.setResultTotal(total);
        vo.setShowResult("Y");
        return selectQuestionItemList(vo);
    }

    public List<SurveyQstItemVO> selectQuestionItemList(SurveyQstItemVO vo) {
        return surveyMapper.selectQuestionItemList(vo);
    }

    public SurveyQstItemVO selectQuestionItemDetail(SurveyQstItemVO vo) {
        return surveyMapper.selectQuestionItemDetail(vo);
    }

    /**
     * 설문노출기준등록
     */
    public int insertExposeStandard(SurveyStdrmngVO vo) {
        return surveyMapper.insertExposeStandard(vo);
    }

    /**
     * 설문노출기준갱신
     */
    public int updateExposeStandard(SurveyStdrmngVO vo) {
        return surveyMapper.updateExposeStandard(vo);
    }

    /**
     * 설문노출기준삭제
     */
    public int deleteExposeStandard(SurveyStdrmngVO vo) {
        return surveyMapper.deleteExposeStandard(vo);
    }

    /**
     * 설문노출기준목록
     */
    public List<SurveyStdrmngVO> selectExposeStandardList(SurveyStdrmngVO vo) {
        return surveyMapper.selectExposeStandardList(vo);
    }

    public SurveyStdrmngVO selectExposeStandardDetail(SurveyStdrmngVO vo) {
        return surveyMapper.selectExposeStandardDetail(vo);
    }

    public void sndSurveyToMem(Map<String, Object> paramMap) throws Exception {
        // surveyMapper.sndSurveyToMem(paramMap);
        // TODO: 호출부 미존재
    }

    /**
     * 설문 정보 노출 및 실시 관련 정보를 조회한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectSurveyExecuteList(Map<String, Object> paramMap) throws Exception {
        return surveyMapper.selectSurveyExecuteList(paramMap);
    }

    /**
     * 설문 대상 메인 정보를 조회한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public Map<String, Object> selectSurveyDetail(SurveyMstVO vo) throws Exception {
        Map<String, Object> rtnMap = new HashMap<String, Object>();

        SurveyMstVO mstVO = surveyMapper.selectSurveyDetail(vo);
        List<SurveyQstVO> qstList = surveyMapper.selectSurveyQstList(mstVO);

        for (SurveyQstVO surveyQstVO : qstList) {
            List<SurveyQstItemVO> itemList = surveyMapper.selectQstItemList(mstVO);
            surveyQstVO.setItemList(itemList);
        }

        mstVO.setQuestionList(qstList);
        rtnMap.put("vo", mstVO);
        return rtnMap;
    }

    /**
     * 설문 대상 예약 정보를 조회한다
     *
     * @param vo
     *            SurveyRsvnVO
     * @return map
     * @throws Exception
     */
    public Map<String, Object> selectRsvnMst(SurveyRsvnVO vo) throws Exception {
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        vo.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        SurveySendVO sendVo = surveyMapper.selectSurveySendList(vo);
        SurveyRsvnVO rsvnVO = surveyMapper.selectRsvnDetail(vo);
        rtnMap.put("vo", rsvnVO);
        rtnMap.put("sendVO", sendVo);
        return rtnMap;
    }

    /**
     * 설문 대상 예약 정보를 조회한다
     *
     * @param vo
     *            SurveyResultVO
     * @return map
     * @throws Exception
     */
    public Map<String, Object> insertSurveyResult(SurveyResultVO vo) throws Exception {

        Map<String, Object> rtnMap = new HashMap<String, Object>();

        int dtlCnt = 0;
        int rsltCnt = surveyMapper.insertSurveyResult(vo);

        List<SurveyResultDetailVO> dtlList = vo.getDtlList();

        log.debug("dtlList = {}", dtlList);
        log.debug("vo = {}", vo);

        for (SurveyResultDetailVO dtl : dtlList) {

            dtl.setComcd(Config.COM_CD);
            dtl.setQestnarResltNo(vo.getQestnarResltNo());

            log.debug("dtl = {}", dtl);

            int dtlc = surveyMapper.insertSrvResultDtl(dtl);

            dtlCnt += dtlc;
        }

        rtnMap.put("resultCnt", rsltCnt);
        rtnMap.put("dtlCnt", dtlCnt);

        return rtnMap;
    }

    /**
     * 설문 대상 예약 정보를 조회한다
     *
     * @param vo
     *            SurveyResultVO
     * @return map
     * @throws Exception
     */
    public int countSurveySendResult(SurveySendVO vo) throws Exception {
        int rsltCnt = surveyMapper.countSurveySendResult(vo);
        return rsltCnt;
    }

    /**
     * 설문 대상 참여 정보를 조회한다
     *
     * @param vo
     *            SurveyResultVO
     * @return map
     * @throws Exception
     */
    public int countSurveyResult(SurveyResultVO vo) throws Exception {
        int rsltCnt = surveyMapper.countSurveyResult(vo);
        return rsltCnt;
    }

    /**
     * 설문 대상 참여 정보를 조회한다
     *
     * @param vo
     *            SurveyResultVO
     * @return map
     * @throws Exception
     */
    public List<SurveyResultVO> selectSurveyResultList(SurveyResultVO vo) throws Exception {
        return surveyMapper.selectSurveyResultList(vo);
    }

    /**
     * 설문 대상 참여 상세 정보를 조회한다
     *
     * @param vo
     *            SurveyResultVO
     * @return map
     * @throws Exception
     */
    public List<SurveyResultDetailVO> selectSurveyResultDetailList(SurveyResultVO vo) throws Exception {
        return surveyMapper.selectSurveyResultDetailList(vo);
    }

    /**
     * 셋팅 일괄 저장
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public void masterSave(SurveyMstVO surveyMstVO, HttpServletRequest request, HttpServletResponse response,
            CommandMap commandMap, ModelMap model)
            throws Exception {

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        SurveyStdrmngVO stdrmngVo = new SurveyStdrmngVO();
        if (surveyMstVO.getQestnarId() < 1) {
            insertThema(surveyMstVO);
        } else {
            updateThema(surveyMstVO);
            SurveyStdrmngVO searchVO2 = new SurveyStdrmngVO();
            searchVO2.setQestnarId(surveyMstVO.getQestnarId());
            stdrmngVo = selectExposeStandardDetail(searchVO2);
        }

        if (stdrmngVo == null) {
            stdrmngVo = new SurveyStdrmngVO();
        }

        String qestnarOpertype = request.getParameter("qestnarOpertype");
        String qestnarOpersdate = request.getParameter("qestnarOpersdate");
        String qestnarOperedate = request.getParameter("qestnarOperedate");
        String qestnarOpenDate = request.getParameter("qestnarOpenDate");
        String qestnarOpenTimeHH = request.getParameter("qestnarOpenTimeHH");
        String qestnarOpenTimeMM = request.getParameter("qestnarOpenTimeMM");
        if (qestnarOpenTimeHH == null || "".equals(qestnarOpenTimeHH))
            qestnarOpenTimeHH = "00";
        if (qestnarOpenTimeMM == null || "".equals(qestnarOpenTimeMM))
            qestnarOpenTimeMM = "00";
        String qestnarOpenTime = qestnarOpenTimeHH + "" + qestnarOpenTimeMM;
        String openYn = request.getParameter("openYn");
        stdrmngVo.setQestnarId(surveyMstVO.getQestnarId());
        stdrmngVo.setQestnarOpertype(qestnarOpertype);
        stdrmngVo.setQestnarOpersdate(qestnarOpersdate.replaceAll("-", ""));
        stdrmngVo.setQestnarOperedate(qestnarOperedate.replaceAll("-", ""));
        stdrmngVo.setQestnarOpenDate(qestnarOpenDate.replaceAll("-", ""));
        stdrmngVo.setQestnarOpenTime(qestnarOpenTime);
        stdrmngVo.setOpenYn(openYn);
        stdrmngVo.setUseYn(surveyMstVO.getUseYn());
        if (stdrmngVo.getQestnarStdno() > 0) {
            updateExposeStandard(stdrmngVo);
        } else {
            insertExposeStandard(stdrmngVo);
        }

        String[] qestnsSeqArr = request.getParameterValues("qestnsSeq");
        String[] qestnsNameArr = request.getParameterValues("qestnsName");
        String[] qestnsTypeArr = request.getParameterValues("qestnsType");

        int qestnarId = surveyMstVO.getQestnarId();
        deleteQuestionItem(qestnarId);// 전체질문항목삭제
        deleteQuestion(qestnarId);// 전체질문삭제

        List<SurveyQstVO> questionList = new ArrayList<SurveyQstVO>();
        if (qestnsSeqArr != null && qestnsSeqArr.length > 0) {
            for (int i = 0; i < qestnsSeqArr.length; i++) {
                SurveyQstVO surveyQstVO = new SurveyQstVO();
                surveyQstVO.setQestnarId(surveyMstVO.getQestnarId());
                surveyQstVO.setQestnsName(qestnsNameArr[i]);
                surveyQstVO.setQestnsType(qestnsTypeArr[i]);
                insertQuestion(surveyQstVO); // 질문입력

                String itemNmKey = "item_" + i + "_nm";
                String itemScoreKey = "item_" + i + "_score";
                String[] nmArr = request.getParameterValues(itemNmKey);
                String[] scoreArr = request.getParameterValues(itemScoreKey);

                List<SurveyQstItemVO> itemList = new ArrayList<SurveyQstItemVO>();
                if (nmArr != null && nmArr.length > 0) {
                    for (int j = 0; j < nmArr.length; j++) {
                        SurveyQstItemVO surveyQstItemVO = new SurveyQstItemVO();
                        surveyQstItemVO.setQestnarId(surveyMstVO.getQestnarId());
                        surveyQstItemVO.setQestnarItemnm(nmArr[j]);
                        surveyQstItemVO.setQestnarScore(StringUtil.String2Int(scoreArr[j], 0));
                        surveyQstItemVO.setQestnsSeq(surveyQstVO.getQestnsSeq());
                        insertQuestionItem(surveyQstItemVO); // 질문항목입력
                    }
                }
            }
        }

        if (true) {
            // throw new Exception("zzzzzzzzzzzzz");
        }
    }

    /**
     * 설문 제출 답변 일괄 저장
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public void answerSave(SurveyResultVO surveyResultVO, HttpServletRequest request, HttpServletResponse response,
            CommandMap commandMap, ModelMap model)
            throws Exception {

        LoginVO user = commandMap.getUserInfo();
        boolean isMobile = (boolean) request.getAttribute("IS_MOBILE");

        surveyResultVO.setQestnarMembgbn(user.getMemGbn()); // 설문조사응답회원구분
        surveyResultVO.setQestnarMemno(user.getUniqId());// 설문조사응답회원번호
        surveyResultVO.setQestnarMembWebid(user.getId()); // 설문조사응답웹ID
        surveyResultVO.setQestnarTerminalType(isMobile ? "2002" : "2001"); // 설문조사응답경로구분 1001 : 현장PC, 1002 : 키오스크, 2001:
                                                                           // 온라인WEB, 2002 : 모바일
        surveyResultVO.setReguser(user.getName());
        int rsltCnt = surveyMapper.insertSurveyResult(surveyResultVO);

        // 질문조회
        SurveyQstVO searchQVO = new SurveyQstVO();
        searchQVO.setQestnarId(surveyResultVO.getQestnarId());
        List<SurveyQstVO> questionList = selectQuestionList(searchQVO);

        // 질문항목조회설정
        if (questionList != null && !questionList.isEmpty()) {
            questionList.stream().forEach(q -> {
                q.setItemList(selectQuestionItemList(q.getQestnarId(), q.getQestnsSeq()));
            });
        }

        if (questionList != null && !questionList.isEmpty()) {
            String[] qestnsSeqArr = request.getParameterValues("qestnsSeq");
            if (qestnsSeqArr != null && qestnsSeqArr.length > 0) {
                for (int i = 0; i < qestnsSeqArr.length; i++) {
                    String qestnsSeqStr = qestnsSeqArr[i];
                    int qestnsSeq = StringUtil.String2Int(qestnsSeqStr, 0);

                    // DB에 있는 질문 정보를 가져 온다.
                    questionList.stream().forEach(q -> {
                        if (q.getQestnsSeq() == qestnsSeq) {
                            String qid = "q" + qestnsSeqStr;
                            String answer = request.getParameter(qid);

                            SurveyResultDetailVO surveyResultDetailVO = new SurveyResultDetailVO();
                            surveyResultDetailVO.setComcd(surveyResultVO.getComcd());
                            surveyResultDetailVO.setQestnarId(surveyResultVO.getQestnarId());
                            surveyResultDetailVO.setQestnarStdno(surveyResultVO.getQestnarStdno());
                            surveyResultDetailVO.setQestnsSeq(qestnsSeq);
                            surveyResultDetailVO.setQestnarResltNo(surveyResultVO.getQestnarResltNo());
                            surveyResultDetailVO.setReguser(user.getName());
                            if (answer == null) {
                                answer = "";
                            }
                            // 요약
                            if (q.getQestnsType().equals("0000")) {
                            } else if (q.getQestnsType().equals("3001")) {
                                surveyResultDetailVO.setResltSbjct(answer);
                                int dtlc = surveyMapper.insertSrvResultDtl(surveyResultDetailVO);
                            } else {
                                int qestnarItemseq = StringUtil.String2Int(answer, 0);
                                if (qestnarItemseq > 0) {
                                    q.setItemList(selectQuestionItemList(q.getQestnarId(), q.getQestnsSeq()));
                                    q.getItemList().stream().forEach(qi -> {
                                        if (qestnarItemseq == qi.getQestnarItemseq()) {
                                            surveyResultDetailVO.setResltItemseq(qestnarItemseq);
                                            surveyResultDetailVO.setResltItemscore(qi.getQestnarScore());
                                        }
                                    });
                                }
                                int dtlc = surveyMapper.insertSrvResultDtl(surveyResultDetailVO);
                            }
                        }
                    });
                }
            }
        }

        if (true) {
            // throw new Exception("zzzzzzzzzzzzz");
        }
    }
}
