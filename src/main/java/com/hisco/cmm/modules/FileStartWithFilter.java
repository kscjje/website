package com.hisco.cmm.modules;

import java.io.File;
import java.io.FilenameFilter;

public class FileStartWithFilter implements FilenameFilter {

    private String fileName;

    public FileStartWithFilter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean accept(File dir, String name) {
        if (!StringUtil.IsEmpty(name))
            return name.startsWith(fileName);
        else
            return false;
    }

}