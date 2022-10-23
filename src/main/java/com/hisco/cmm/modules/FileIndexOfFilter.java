package com.hisco.cmm.modules;

import java.io.File;
import java.io.FilenameFilter;

public class FileIndexOfFilter implements FilenameFilter {

    private String fileName;

    public FileIndexOfFilter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean accept(File dir, String name) {
        if (!StringUtil.IsEmpty(name))
            return name.indexOf(fileName) > -1;
        else
            return false;
    }
}
