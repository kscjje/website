package com.hisco.cmm.mapper;

import java.util.List;
import java.util.Map;
import com.hisco.cmm.object.CommandMap;

/**
 * 단순 쿼리 실행 DAO 를 처리하는 편의 클래스.
 * 
 * @author 진수진
 * @since 2020.07.01
 * @version 1.0, 2020.07.01
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.01 최초작성
 */
public interface CommonDAO {

    /**
     * 단건 조회(int) 연산을 수행한다.
     *
     * @param statementId
     *            MyBatis namespace + statementId
     * @param parameter
     *            입력 parameter
     * @return 조회된 데이터
     */
    Integer queryForInt(String statementId, Object parameter);

    /**
     * 단건 조회(long) 연산을 수행한다.
     *
     * @param statementId
     *            MyBatis namespace + statementId
     * @param parameter
     *            입력 parameter
     * @return 조회된 데이터
     */
    Long queryForLong(String statementId, Object parameter);

    /**
     * 단건 조회 연산을 수행한다.
     *
     * @param <T>
     *            generic type class
     * @param statementId
     *            MyBatis namespace + statementId
     * @param parameter
     *            입력 parameter
     * @param clazz
     *            generic type class
     * @return 조회된 데이터 (단건) or null
     */
    <T> T queryForObject(String statementId, Object parameter, Class<T> clazz);

    /**
     * 단건 조회 연산을 수행한다.
     *
     * @param statementId
     *            MyBatis namespace + statementId
     * @param parameter
     *            입력 parameter
     * @return 조회된 데이터 (단건) or null
     */
    Object queryForObject(String statementId, Object parameter);

    /**
     * 단건 조회 연산을 수행한다.
     *
     * @param statementId
     *            MyBatis namespace + statementId
     * @param mapKey
     *            resultMap key
     * @param parameter
     *            입력 parameter
     * @return 조회된 데이터 (단건) or null
     */
    Map<?, ?> queryForMap(String statementId, String mapKey, Object parameter);

    /**
     * 다건 조회 연산을 수행한다.
     *
     * @param <T>
     *            generic type class
     * @param statementId
     *            MyBatis namespace + statementId
     * @param parameter
     *            입력 parameter
     * @param clazz
     *            generic type class
     * @return 조회된 데이터 (0건 이상)
     */
    <T> List<T> queryForList(String statementId, Object parameter, Class<T> clazz);

    /**
     * 다건 조회 연산을 수행한다.
     *
     * @param statementId
     *            MyBatis namespace + statementId
     * @param parameter
     *            입력 parameter
     * @return 조회된 데이터 (0건 이상)
     */
    List<?> queryForList(String statementId, Object parameter);

    /**
     * 쿼리실행 후 쿼리결과 수를 리턴하는 공통 메소드
     * 
     * @param sqlId
     * @param paramMap
     * @return
     * @throws Exception
     */
    public int getExecuteResult(String sqlId, Object paramMap) throws Exception;

    /**
     * 쿼리후 페이지를 리턴하는 공통 메소드
     * 
     * @param sqlId
     * @param paramMap
     * @return
     * @throws Exception
     */
    public List getSelectPaginatedResult(String sqlMapId, CommandMap parameterMap) throws Exception;

    public List getSelectPaginatedResult(String sqlMapId, Map parameterMap) throws Exception;

}