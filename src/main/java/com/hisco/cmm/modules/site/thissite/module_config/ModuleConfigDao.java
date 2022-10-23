package com.hisco.cmm.modules.site.thissite.module_config;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hisco.cmm.modules.extend.AbstractDao;
import com.hisco.cmm.modules.extend.InterfaceDao;
import com.hisco.cmm.modules.site.thissite.module_config.ModuleConfigDto;
import com.hisco.cmm.modules.site.thissite.module_config.ModuleConfigVo;

@Repository("ModuleConfigDao")
public class ModuleConfigDao extends AbstractDao implements InterfaceDao {

    @Override
    public boolean Exists() throws SQLException {
        int ret = getSqlSession().selectOne("module_config.exists");

        if (ret <= 0)
            return false;

        try {
            int idx = 1;
            do {
                ret = getSqlSession().selectOne("module_config.exists".concat(String.valueOf(idx)));
                idx++;
            } while (ret > 0);
        } catch (Exception e) {
        }

        return ret > 0;
    }

    @Override
    public boolean Create() throws SQLException {
        getSqlSession().insert("module_config.create");

        try {
            int idx = 1;
            do {
                getSqlSession().insert("module_config.create".concat(String.valueOf(idx)));
                idx++;
            } while (idx < 99);
        } catch (Exception e) {
        }

        return this.Exists();
    }

    @Override
    public boolean Drop() throws SQLException {
        getSqlSession().insert("module_config.drop");

        try {
            int idx = 1;
            do {
                getSqlSession().insert("module_config.drop".concat(String.valueOf(idx)));
                idx++;
            } while (idx < 99);
        } catch (Exception e) {
        }

        return !this.Exists();
    }

    public int Count(ModuleConfigDto parameter) throws SQLException {
        return getSqlSession().selectOne("module_config.count", parameter);
    }

    public List<ModuleConfigVo> List(ModuleConfigDto parameter) throws SQLException {
        return getSqlSession().selectList("module_config.list", parameter);
    }

    public ModuleConfigVo Select(ModuleConfigDto parameter) throws SQLException {
        return getSqlSession().selectOne("module_config.select", parameter);
    }

    public boolean Insert(ModuleConfigVo parameter) throws SQLException {
        return getSqlSession().insert("module_config.insert", parameter) > 0;
    }

    public boolean Update(ModuleConfigVo parameter) throws SQLException {
        return getSqlSession().update("module_config.update", parameter) > 0;
    }

    public boolean UpdateId(ModuleConfigVo parameter) throws SQLException {
        return getSqlSession().update("module_config.update_id", parameter) > 0;
    }

    public boolean Delete(long module_config_srl) throws SQLException {
        return getSqlSession().delete("module_config.delete", module_config_srl) > 0;
    }

    public int IdCheck(ModuleConfigDto parameter) throws SQLException {
        return getSqlSession().selectOne("module_config.id_check", parameter);
    }
}
