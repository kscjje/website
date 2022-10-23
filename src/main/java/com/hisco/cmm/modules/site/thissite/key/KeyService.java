package com.hisco.cmm.modules.site.thissite.key;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.modules.extend.InterfaceService;

@Service("KeyService")
public class KeyService implements InterfaceService {

    @Resource(name = "KeyDao")
    private KeyDao keyDao;

    @Override
    public boolean Exists() throws Exception {
        return keyDao.Exists();
    }

    @Override
    public boolean Create() throws Exception {
        if (keyDao.Exists())
            return true;
        else
            return keyDao.Create();
    }

    @Override
    public boolean Drop() throws Exception {
        if (keyDao.Exists())
            return keyDao.Drop();
        else
            return true;
    }

    public List<KeyVo> List(KeyVo parameter) throws SQLException {
        return keyDao.List(parameter);
    }

    public KeyVo Select(KeyVo parameter) throws SQLException {
        return keyDao.Select(parameter);
    }

    public KeyVo Select(String code) throws SQLException {
        KeyVo parameter = new KeyVo();
        parameter.setCode(code);

        return Select(parameter);
    }

    public boolean Insert(KeyVo parameter) throws SQLException {
        return keyDao.Insert(parameter);
    }

    public boolean Update(KeyVo parameter) throws SQLException {
        return keyDao.Update(parameter);
    }

    public boolean Delete(KeyVo parameter) throws SQLException {
        return keyDao.Delete(parameter);
    }

    /**
     * 키값 체크
     * 
     * @param code
     *            코드값
     * @return
     * @throws SQLException
     */
    public long CheckKeyValue(String code) throws SQLException {

        KeyVo keyData = Select(code);

        if (keyData == null) {
            KeyVo keyParam = new KeyVo();
            keyParam.setCode(code);
            keyParam.setValue(1);

            boolean ret = Insert(keyParam);
            if (ret)
                return 1;
            else
                return -1;
        } else {
            return keyData.getValue();
        }
    }

    /**
     * 키 값 증가
     * 
     * @param code
     * @return
     * @throws SQLException
     */
    public long IncreaseKeyValue(String code) throws SQLException {
        long value = CheckKeyValue(code);
        if (value <= 0)
            return -1;
        else {
            KeyVo keyParam = new KeyVo();
            keyParam.setCode(code);
            keyParam.setValue(value + 1);

            if (Update(keyParam))
                return value;
            else
                return -1;
        }
    }
}
