package com.hisco.cmm.modules.extend;

import java.sql.SQLException;

public interface InterfaceDao {

    public boolean Exists() throws SQLException;

    public boolean Create() throws SQLException;

    public boolean Drop() throws SQLException;

}
