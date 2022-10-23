package com.hisco.cmm.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 단순 쿼리 실행 DAO 를 처리하는 편의 클래스.
 * {@link CommonDAO}의 MyBatis 구현체 .
 * 
 * @author 진수진
 * @since 2020.07.01
 * @version 1.0, 2020.07.01
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.01 최초작성
 */
@Slf4j
@Repository("commonDAO")
public class CommonDAOMyBatis extends EgovComAbstractDAO implements CommonDAO {

    protected SqlSessionTemplate template;

    private static final String PAGE_UNIT = Integer.toString(Config.PAGE_UNIT);
    private static final String PAGE_SIZE = Integer.toString(Config.PAGE_SIZE);

    /*
     * (non-Javadoc)
     * @see com.hisco.cmm.service.impl.CommonDAO#
     * queryForInt(java.lang.String, java.lang.Object)
     */
    @Override
    public Integer queryForInt(String statementId, Object parameter) {

        Integer value = (Integer) selectOne(statementId, parameter);

        if (value == null) {
            value = 0;
        }

        return value;
    }

    /*
     * (non-Javadoc)
     * @see com.hisco.cmm.service.impl.CommonDAO#
     * queryForLong(java.lang.String, java.lang.Object)
     */
    @Override
    public Long queryForLong(String statementId, Object parameter) {
        return (Long) selectOne(statementId, parameter);
    }

    /*
     * (non-Javadoc)
     * @see com.hisco.cmm.service.impl.CommonDAO#
     * queryForObject(java.lang.String, java.lang.Object, java.lang.Class)
     */
    @Override
    public <T> T queryForObject(String statementId, Object parameter, Class<T> clazz) {
        return clazz.cast(selectOne(statementId, parameter));
    }

    /*
     * (non-Javadoc)
     * @see com.hisco.cmm.service.impl.CommonDAO#
     * queryForObject(java.lang.String, java.lang.Object)
     */
    @Override
    public Object queryForObject(String statementId, Object parameter) {
        return selectOne(statementId, parameter);
    }

    /*
     * (non-Javadoc)
     * @see com.hisco.cmm.service.impl.CommonDAO#
     * queryForMap(java.lang.String, java.lang.String, java.lang.Object)
     */
    @Override
    public Map<?, ?> queryForMap(String statementId, String mapKey, Object parameter) {
        return selectMap(statementId, parameter, mapKey);
    }

    /*
     * (non-Javadoc)
     * @see com.hisco.cmm.service.impl.CommonDAO#
     * queryForList(java.lang.String, java.lang.Object, java.lang.Class)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> queryForList(String statementId, Object parameter, Class<T> clazz) {
        List<T> list = (List<T>) selectList(statementId, parameter);

        // log.debug("Statement[{}] Executed ({}) : {} Records retrieved.", new Object[] { statementId, new Date(),
        // list == null ? 0 : list.size() });

        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.hisco.cmm.service.impl.CommonDAO#
     * queryForList(java.lang.String, java.lang.Object)
     */
    @Override
    public List<?> queryForList(String statementId, Object parameter) {
        List<?> list = selectList(statementId, parameter);
        // log.debug("Statement[{}] Executed ({}) : {} Records retrieved.", new Object[] { statementId, "", list ==
        // null ? 0 : list.size() });
        return list;
    }

    /**
     * Method CUD쿼리 결과 리턴
     * 
     * @param sqlMapId
     *            - ibatis sqlMapId
     * @param parameterMap
     *            - FORM 객체
     * @return CUD쿼리 결과
     * @exception Exception
     * @see
     */
    @Override
    public int getExecuteResult(String sqlMapId, Object parameterMap) throws Exception {

        int updatedRowCount = 0;

        try {
            long startTime = System.currentTimeMillis();
            updatedRowCount = update(sqlMapId, parameterMap);

            /*
             * long endTime = System.currentTimeMillis();
             * if (log.isDebugEnabled())
             * {
             * log.debug("[sqlMapId:"+sqlMapId+"]query execute TIME : " + (endTime - startTime) + "(ms)]]");
             * }
             */
        } catch (Exception e) {
            log.debug("DAO ERROR : " + e.getMessage());
            throw e;
        }

        return updatedRowCount;
    }

    /**
     * Method 쿼리 결과 페이징 리턴
     * 
     * @param sqlMapId
     *            - ibatis sqlMapId
     * @param parameterMap
     *            - FORM 객체
     * @return 쿼리 결과 페이징 List
     * @throws FdlException
     * @exception Exception
     * @see
     */
    public List getSelectPaginatedResult(String sqlMapId, CommandMap parameterMap) throws Exception {
        List list;

        try {
            long startTime = System.currentTimeMillis();

            PaginationInfo paginationInfo = parameterMap.getPagingInfo();

            // pagingInfo.calculation();

            list = selectList(sqlMapId, parameterMap);

            // 총개수
            String totalCnt = list.size() > 0 ? ((Map) list.get(0)).get("TOTAL_ROWS").toString() : "0";
            paginationInfo.setTotalRecordCount(Integer.parseInt(totalCnt));

            // int pageLastIndex = (int)Math.ceil((Double.parseDouble(totalCnt)/size));
            // int pageStartIndex = (((index-1)/unit)*unit) + 1;

            // 페이징 관련 처리
            /*
             * pagingInfo.setPage(pageIndex);
             * pagingInfo.setPageUnit(pageUnit);
             * pagingInfo.setPageSize(pageSize);
             * pagingInfo.setPageTotalCnt(totalCnt);
             * pagingInfo.setPageLastIndex(pageLastIndex);
             * pagingInfo.setPageStartIndex(pageStartIndex);
             */
            /*
             * long endTime = System.currentTimeMillis();
             * if (log.isDebugEnabled()) {
             * log.debug("[sqlMapId:"+sqlMapId+"]query execute TIME : " + (endTime - startTime) + "(ms)]]");
             * }
             */
        } catch (Exception e) {
            log.debug("DAO ERROR : " + e.getMessage());
            throw e;
        }

        return list;
    }

    /**
     * Method 쿼리 결과 페이징 리턴
     * 
     * @param sqlMapId
     *            - ibatis sqlMapId
     * @param parameterMap
     *            - FORM 객체
     * @return 쿼리 결과 페이징 List
     * @throws FdlException
     * @exception Exception
     * @see
     */
    public List getSelectPaginatedResult(String sqlMapId, Map parameterMap) throws Exception {
        List list;

        try {
            long startTime = System.currentTimeMillis();

            String pageIndex = CommonUtil.getStringEmpty(parameterMap.get("pageIndex"), "1");
            String pageUnit = ObjectUtils.toString(parameterMap.get("pageUnit"), PAGE_UNIT);
            String pageSize = ObjectUtils.toString(parameterMap.get("pageSize"), PAGE_SIZE);

            log.debug("pageIndex : " + parameterMap.get("pageIndex"));
            log.debug("pageIndex : " + pageIndex);

            int index = Integer.parseInt(pageIndex);
            int size = Integer.parseInt(pageSize);
            int unit = Integer.parseInt(pageUnit);

            parameterMap.put("startPageingNumber", String.valueOf(((index - 1) * size) + 1));
            parameterMap.put("endPageingNumber", String.valueOf(index * size));

            list = selectList(sqlMapId, parameterMap);

            // 총개수
            String totalCnt = list.size() > 0 ? ((Map) list.get(0)).get("TOTAL").toString() : "0";

            int pageLastIndex = (int) Math.ceil((Double.parseDouble(totalCnt) / size));
            int pageStartIndex = (((index - 1) / unit) * unit) + 1;

            // 페이징 관련 처리
            parameterMap.put("pageIndex", pageIndex);
            parameterMap.put("pageUnit", pageUnit);
            parameterMap.put("pageSize", pageSize);
            parameterMap.put("pageTotalCnt", totalCnt);
            parameterMap.put("pageLastIndex", pageLastIndex);
            parameterMap.put("pageStartIndex", pageStartIndex);
            /*
             * long endTime = System.currentTimeMillis();
             * if (log.isDebugEnabled()) {
             * log.debug("[sqlMapId:"+sqlMapId+"]query execute TIME : " + (endTime - startTime) + "(ms)]]");
             * }
             */
        } catch (Exception e) {
            log.debug("DAO ERROR : " + e.getMessage());
            throw e;
        }

        return list;
    }

}