package com.hisco.intrfc.email.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.util.Config;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * EMail 송신 처리
 * 
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.05 최초작성
 */
@Service("eMailService")
public class EMailService extends EgovAbstractServiceImpl {

    @Resource(name = "commonDAO")
    private CommonDAO commonDAO;

    public void insertSeokLogMyBatis(Map<String, Object> paramMap) throws Exception {
        commonDAO.getExecuteResult("EMailDAO.insertSeokLogMyBatis1", paramMap);
        commonDAO.getExecuteResult("EMailDAO.insertSeokLogMyBatis2", paramMap);
    }

    public void insertSeokLogMyBatis1(Map<String, Object> paramMap) throws Exception {
        commonDAO.getExecuteResult("EMailDAO.insertSeokLogMyBatis1", paramMap);
    }

    public void insertSeokLogMyBatis2(Map<String, Object> paramMap) throws Exception {
        commonDAO.getExecuteResult("EMailDAO.insertSeokLogMyBatis2", paramMap);
    }

    public List<?> selectSeokLogMyBatis(Map<String, Object> paramMap) throws Exception {
        return commonDAO.queryForList("EMailDAO.selectSeokLogMyBatis", paramMap);
    }

    public List<?> selectSeokLogProcedure(Map<String, Object> paramMap) throws Exception {

        paramMap.put("RETURN_CD", "");
        paramMap.put("RETURN_MSG", "");
        paramMap.put("RETURN_OBJ", "");
        paramMap.put("RETURN_OID", "");
        paramMap.put("comcd", Config.COM_CD);

        // List<EgovMap> listMap1 = (List<EgovMap>)commonDAO.queryForList("EMailDAO.selectCallReferProc", paramMap);
        // List<EgovMap> listMap1 = (List<EgovMap>)commonDAO.queryForList("EMailDAO.selectCallReferProc2", paramMap);

        // log.debug("listMap1 = ");
        // log.debug(listMap1);

        commonDAO.queryForList("EMailDAO.selectCallReferProc2", paramMap);

        // log.debug("paramMap = ");
        // log.debug(paramMap);

        return null;
    }

}
