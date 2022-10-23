package com.hisco.cmm.modules;

import java.io.File;
import java.io.FilenameFilter;

public class FileEndWithFilter implements FilenameFilter {
    private String fileName;

    public FileEndWithFilter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean accept(File dir, String name) {
        if (!StringUtil.IsEmpty(name))
            return name.endsWith(fileName);
        else
            return false;
    }
}
