package com.hisco.cmm.modules.site.thissite.key;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hisco.cmm.modules.extend.AbstractDao;
import com.hisco.cmm.modules.extend.InterfaceDao;

@Repository("KeyDao")
public class KeyDao extends AbstractDao implements InterfaceDao {
    @Override
    public boolean Exists() throws SQLException {

        /*
         * int ret = getSqlSession().selectOne("key.exists");
         * if (ret <= 0) return false;
         * try {
         * int idx = 1;
         * do {
         * ret = getSqlSession().selectOne("key.exists".concat(String.valueOf(idx)));
         * idx++;
         * }
         * while(ret > 0);
         * }
         * catch (Exception e)
         * {}
         * return ret > 0;
         */
        return true;
    }

    @Override
    public boolean Create() throws SQLException {

        /*
         * getSqlSession().insert("key.create");
         * try {
         * int idx = 1;
         * do {
         * getSqlSession().insert("key.create".concat(String.valueOf(idx)));
         * idx++;
         * }
         * while(idx < 99);
         * } catch (Exception e) {}
         * return this.Exists();
         */
        return true;
    }

    @Override
    public boolean Drop() throws SQLException {

        /*
         * getSqlSession().insert("key.drop");
         * try {
         * int idx = 1;
         * do {
         * getSqlSession().insert("key.drop".concat(String.valueOf(idx)));
         * idx++;
         * }
         * while(idx < 99);
         * } catch (Exception e) {}
         * return !this.Exists();
         */
        return true;
    }

    public List<KeyVo> List(KeyVo parameter) throws SQLException {
        return null;
        //// return getSqlSession().selectList("key.list", parameter);
    }

    public KeyVo Select(KeyVo parameter) throws SQLException {
        return null;
        //// return getSqlSession().selectOne("key.select", parameter);
    }

    public boolean Insert(KeyVo parameter) throws SQLException {
        return true;
        //// return getSqlSession().insert("key.insert", parameter) > 0;
    }

    public boolean Update(KeyVo parameter) throws SQLException {
        return true;
        //// return getSqlSession().update("key.update", parameter) > 0;
    }

    public boolean UpdateIncrease(KeyVo parameter) throws SQLException {
        return true;
        //// return getSqlSession().update("key.update_increase", parameter) > 0;
    }

    public boolean Delete(KeyVo parameter) throws SQLException {
        return true;
        //// return getSqlSession().delete("key.delete", parameter) > 0;
    }
}
