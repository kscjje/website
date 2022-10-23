package com.hisco.cmm.modules.extend;

import javax.annotation.Resource;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import org.apache.ibatis.session.SqlSessionFactory;

// public class AbstractDao extends SqlSessionDaoSupport {
public class AbstractDao extends EgovComAbstractDAO {

    public AbstractDao() {
        super();
    }

    /*
     * @Override
     * @Resource(name="")
     * public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
     * super.setSqlSessionTemplate(sqlSessionTemplate);
     * }
     */

    @Resource(name = "egov.sqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
}
