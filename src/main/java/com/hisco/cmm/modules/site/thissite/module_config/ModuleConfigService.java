package com.hisco.cmm.modules.site.thissite.module_config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.modules.extend.CacheService;
import com.hisco.cmm.modules.extend.InterfaceService;

@Service("ModuleConfigService")
public class ModuleConfigService extends CacheService implements InterfaceService {
    private final String CACHE_KEY = "moduleConfigList";
    private boolean UPDATE_CACHE = false;

    @Resource(name = "ModuleConfigDao")
    private ModuleConfigDao moduleConfigDao;

    public ModuleConfigService() {
        this.CACHE_NAME = "ModuleConfigService";
    }

    @Override
    public boolean Exists() throws Exception {
        return moduleConfigDao.Exists();
    }

    @Override
    public boolean Create() throws Exception {
        if (moduleConfigDao.Exists())
            return true;
        else {
            boolean ret = moduleConfigDao.Create();

            if (ret) {
                // 기본 모듈 정보 삽입

                // 캐시 리셋
                ResetCache();
            }

            return ret;
        }
    }

    @Override
    public boolean Drop() throws Exception {
        if (moduleConfigDao.Exists())
            return moduleConfigDao.Drop();
        else
            return true;
    }

    public int Count(ModuleConfigDto parameter) throws SQLException {
        return moduleConfigDao.Count(parameter);
    }

    public List<ModuleConfigVo> List(ModuleConfigDto parameter) throws SQLException {
        return moduleConfigDao.List(parameter);
    }

    public ModuleConfigVo Select(ModuleConfigDto parameter) throws SQLException {
        return moduleConfigDao.Select(parameter);
    }

    public ModuleConfigVo Select(long module_config_srl) throws SQLException {
        ModuleConfigDto parameter = new ModuleConfigDto();
        parameter.setModule_config_srl(module_config_srl);

        return Select(parameter);
    }

    public boolean Insert(ModuleConfigVo parameter) throws SQLException {
        boolean ret = moduleConfigDao.Insert(parameter);
        if (ret)
            ResetCache();
        return ret;
    }

    public boolean Update(ModuleConfigVo parameter) throws SQLException {
        boolean ret = moduleConfigDao.Update(parameter);
        if (ret)
            ResetCache();
        return ret;
    }

    public boolean UpdateId(ModuleConfigVo parameter) throws SQLException {
        boolean ret = moduleConfigDao.UpdateId(parameter);
        if (ret)
            ResetCache();
        return ret;
    }

    public boolean Delete(long module_config_srl) throws SQLException {
        boolean ret = moduleConfigDao.Delete(module_config_srl);
        if (ret)
            ResetCache();
        return ret;
    }

    public boolean Delete(ModuleConfigVo parameter) throws SQLException {
        return Delete(parameter.getModule_config_srl());
    }

    public int IdCheck(ModuleConfigDto parameter) throws SQLException {
        return moduleConfigDao.IdCheck(parameter);
    }

    public void ResetCache() {
        try {
            UPDATE_CACHE = true;

            this.remove(CACHE_KEY);

            List<ModuleConfigVo> list = this.List(new ModuleConfigDto());
            this.setCacheData(CACHE_KEY, list);
        } catch (SQLException e) {
            // e.printStackTrace();
        } finally {
            UPDATE_CACHE = false;
        }
    }

    public void CheckCache() throws SQLException {
        if (UPDATE_CACHE) {
            this.sleep();
            CheckCache();
            return;
        }

        if (!this.hasCacheData(CACHE_KEY)) {
            ResetCache();
        }
    }

    /**
     * 캐시 모듈 설정 목록
     * 
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<ModuleConfigVo> CacheList() throws SQLException {
        CheckCache();
        return (List<ModuleConfigVo>) this.getCacheData(CACHE_KEY);
    }

    /**
     * 사이트별 캐시 모듈 설정 목록
     * 
     * @param site_srl
     * @return
     * @throws SQLException
     */
    public List<ModuleConfigVo> CacheList(long site_srl) throws SQLException {
        CheckCache();
        List<ModuleConfigVo> list = CacheList();
        if (list == null || list.size() <= 0)
            return null;

        List<ModuleConfigVo> ret = new ArrayList<ModuleConfigVo>();

        for (ModuleConfigVo data : list) {
            if (data.getSite_srl() == 0 || data.getSite_srl() == site_srl) {
                ret.add(data);
            }
        }

        return ret;
    }

    /**
     * 모듈 설정 정보
     * 
     * @param module_config_srl
     *            사이트 고유번호
     * @return
     * @throws SQLException
     */
    public ModuleConfigVo CacheSelect(long module_config_srl) throws SQLException {
        CheckCache();
        List<ModuleConfigVo> list = this.CacheList();
        if (list != null && list.size() > 0) {
            for (ModuleConfigVo data : list) {
                if (data.getModule_config_srl() == module_config_srl)
                    return data;
            }
        }
        return null;
    }

    /**
     * 모듈 설정 정보
     * 
     * @param id
     *            모듈 아이디
     * @return
     * @throws SQLException
     */
    public ModuleConfigVo CacheSelect(String id) throws SQLException {
        CheckCache();
        List<ModuleConfigVo> list = this.CacheList();

        if (list != null && list.size() > 0) {
            for (ModuleConfigVo data : list) {
                if (data.getId().equals(id))
                    return data;
            }
        }
        return null;
    }

    /**
     * 모듈 설정 정보
     * 
     * @param key
     *            모듈 키
     * @return
     * @throws SQLException
     */
    public ModuleConfigVo CacheSelectKey(String key) throws SQLException {
        CheckCache();
        List<ModuleConfigVo> list = this.CacheList();

        if (list != null && list.size() > 0) {
            for (ModuleConfigVo data : list) {
                if (data.getModule_key().equals(key))
                    return data;
            }
        }
        return null;
    }
}
