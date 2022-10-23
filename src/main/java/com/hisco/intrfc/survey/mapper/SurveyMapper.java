package com.hisco.intrfc.survey.mapper;

import java.util.List;
import java.util.Map;

import com.hisco.intrfc.survey.vo.SurveyMstVO;
import com.hisco.intrfc.survey.vo.SurveyQstItemVO;
import com.hisco.intrfc.survey.vo.SurveyQstVO;
import com.hisco.intrfc.survey.vo.SurveyResultDetailVO;
import com.hisco.intrfc.survey.vo.SurveyResultVO;
import com.hisco.intrfc.survey.vo.SurveyRsvnVO;
import com.hisco.intrfc.survey.vo.SurveySendVO;
import com.hisco.intrfc.survey.vo.SurveyStdrmngVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

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
@Mapper("surveyMapper")
public interface SurveyMapper {

    /** 설문조사주제등록 */
    public int insertThema(SurveyMstVO vo);

    /** 설문조사주제갱신 */
    public int updateThema(SurveyMstVO vo);

    /** openYn, useYn 수정 */
    public int stdrmngFieldUpdate(SurveyMstVO vo);

    /** 설문조사주제삭제 */
    public int deleteThema(SurveyMstVO vo);

    /** 설문조사주제목록 */
    public List<SurveyMstVO> selectThemaList(SurveyMstVO vo);

    /** 설문조사주제목록 */
    public List<SurveyMstVO> selectThemaDetailList(SurveyMstVO vo);

    /** 설문조사주제상세 */
    public SurveyMstVO selectThemaDetail(SurveyMstVO vo);

    /** 설문조사질문등록 */
    public int insertQuestion(SurveyQstVO vo);

    /** 설문조사질문갱신 */
    public int updateQuestion(SurveyQstVO vo);

    /** 설문조사질문삭제 */
    public int deleteQuestion(SurveyQstVO vo);

    /** 설문조사질문목록 */
    public List<SurveyQstVO> selectQuestionList(SurveyQstVO vo);

    public SurveyQstVO selectQuestionDetail(SurveyQstVO vo);

    /** 설문조사질문항목등록 */
    public int insertQuestionItem(SurveyQstItemVO vo);

    /** 설문조사질문항목갱신 */
    public int updateQuestionItem(SurveyQstItemVO vo);

    /** 설문조사질문항목삭제 */
    public int deleteQuestionItem(SurveyQstItemVO vo);

    /** 설문조사질문항목목록 */
    public List<SurveyQstItemVO> selectQuestionItemList(SurveyQstItemVO vo);

    public SurveyQstItemVO selectQuestionItemDetail(SurveyQstItemVO vo);

    /** 설문노출기준등록 */
    public int insertExposeStandard(SurveyStdrmngVO vo);

    /** 설문노출기준갱신 */
    public int updateExposeStandard(SurveyStdrmngVO vo);

    /** 설문노출기준삭제 */
    public int deleteExposeStandard(SurveyStdrmngVO vo);

    /** 설문노출기준목록 */
    public List<SurveyStdrmngVO> selectExposeStandardList(SurveyStdrmngVO vo);

    public SurveyStdrmngVO selectExposeStandardDetail(SurveyStdrmngVO vo);

    /**
     * 설문 정보 노출 및 실시 관련 정보를 조회한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectSurveyExecuteList(Map<String, Object> paramMap);

    /**
     * 설문 대상 메인 정보를 조회한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public SurveyMstVO selectSurveyDetail(SurveyMstVO vo);

    public List<SurveyQstVO> selectSurveyQstList(SurveyMstVO vo);

    public List<SurveyQstItemVO> selectQstItemList(SurveyMstVO vo);

    /**
     * 설문 대상 예약 정보를 조회한다
     *
     * @param vo
     *            SurveyRsvnVO
     * @return map
     * @throws Exception
     */
    public SurveySendVO selectSurveySendList(SurveyRsvnVO vo);

    public SurveyRsvnVO selectRsvnDetail(SurveyRsvnVO vo);

    /**
     * 설문 대상 예약 정보를 조회한다
     *
     * @param vo
     *            SurveyResultVO
     * @return map
     * @throws Exception
     */
    public int insertSurveyResult(SurveyResultVO vo);

    public int insertSrvResultDtl(SurveyResultDetailVO vo);

    /**
     * 설문 대상 예약 정보를 조회한다
     *
     * @param vo
     *            SurveyResultVO
     * @return map
     * @throws Exception
     */
    public int countSurveySendResult(SurveySendVO vo);

    /**
     * 설문 대상 참여 정보를 조회한다
     *
     * @param vo
     *            SurveyResultVO
     * @return map
     * @throws Exception
     */
    public int countSurveyResult(SurveyResultVO vo);

    /**
     * 설문 대상 참여 목록
     *
     * @param vo
     *            SurveyResultVO
     * @return map
     * @throws Exception
     */
    public List<SurveyResultVO> selectSurveyResultList(SurveyResultVO vo);

    /**
     * 설문 대상 참여 목록
     *
     * @param vo
     *            SurveyResultVO
     * @return map
     * @throws Exception
     */
    public List<SurveyResultDetailVO> selectSurveyResultDetailList(SurveyResultVO vo);

}
