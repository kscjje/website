package com.hisco.cmm.modules.site.thissite.file;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hisco.cmm.modules.extend.AbstractDao;
import com.hisco.cmm.modules.extend.InterfaceDao;

@Repository("FileDao")
public class FileDao extends AbstractDao implements InterfaceDao {
    @Override
    public boolean Exists() throws SQLException {

        int ret = getSqlSession().selectOne("file.exists");

        if (ret <= 0)
            return false;

        try {

            int idx = 1;
            do {
                ret = getSqlSession().selectOne("file.exists".concat(String.valueOf(idx)));
                idx++;
            }

            while (ret > 0);
        } catch (Exception e) {
        }

        return ret > 0;
    }

    @Override
    public boolean Create() throws SQLException {
        getSqlSession().insert("file.create");

        try {

            int idx = 1;
            do {
                getSqlSession().insert("file.create".concat(String.valueOf(idx)));
                idx++;
            } while (idx < 99);
        } catch (Exception e) {
        }

        return this.Exists();
    }

    @Override
    public boolean Drop() throws SQLException {
        getSqlSession().insert("file.drop");

        try {
            int idx = 1;
            do {
                getSqlSession().insert("file.drop".concat(String.valueOf(idx)));
                idx++;
            } while (idx < 99);
        } catch (Exception e) {
        }

        return !this.Exists();
    }

    public int Count(FileDto parameter) throws SQLException {
        return getSqlSession().selectOne("file.count", parameter);
    }

    public List<FileVo> List(FileDto parameter) throws SQLException {
        return getSqlSession().selectList("file.list", parameter);
    }

    public FileVo Select(FileDto parameter) throws SQLException {
        return getSqlSession().selectOne("file.select", parameter);
    }

    public boolean Insert(FileVo parameter) throws SQLException {
        return getSqlSession().insert("file.insert", parameter) > 0;
    }

    public boolean Update(FileVo parameter) throws SQLException {
        return getSqlSession().update("file.update", parameter) > 0;
    }

    public boolean UpdateLife(FileVo parameter) throws SQLException {
        return getSqlSession().update("file.update_life", parameter) > 0;
    }

    public boolean UpdateDownload(FileVo parameter) throws SQLException {
        return getSqlSession().update("file.update_download", parameter) > 0;
    }

    public boolean Delete(FileVo parameter) throws SQLException {
        return getSqlSession().delete("file.delete", parameter) > 0;
    }
}
