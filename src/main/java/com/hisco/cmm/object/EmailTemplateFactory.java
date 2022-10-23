package com.hisco.cmm.object;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.hisco.cmm.util.FileMngUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailTemplateFactory {

    /**
     * 템플릿 Map key 는 html 파일 명 (.html 을 제외)
     */
    private Map<String, String> templateMap = new HashMap<String, String>();

    private static EmailTemplateFactory instance;

    static {
        instance = new EmailTemplateFactory();
    }

    private EmailTemplateFactory() {
    }

    public static EmailTemplateFactory getInstance() {
        return instance;
    }

    public void read() {

        String path = FileMngUtil.GetRealRootPath().concat("WEB-INF/templates/email");
        log.debug("path :" + path);

        // 메일 폼 dir
        File dir = new File(path);

        try {
            File[] files = dir.listFiles();

            for (File form : files) {
                String name = form.getName();
                if (form.isFile()) {
                    String contents = null;
                    contents = FileUtils.readFileToString(form, "UTF-8");
                    name = name.replaceAll(".html", "");
                    templateMap.put(name, contents);
                }
            }

        } catch (IOException e) {
            log.error("템플릿 읽기 에러", e);
        }

    }

    public String read(String templateId) {
        String path = FileMngUtil.GetRealRootPath().concat("WEB-INF/templates/email");
        log.debug("path :" + path);
        // 메일 폼 dir
        File file = new File(path + File.separator + templateId + ".html");

        try {
            if (file.exists()) {
                return FileUtils.readFileToString(file, "UTF-8");
            } else {
                return "";
            }

        } catch (IOException e) {
            // e.printStackTrace();
            log.error("템플릿 읽기 에러", e);
            return "";
        }

    }

    public Map<String, String> getTemplateMap() {
        return templateMap;
    }

    public void setTemplateMap(Map<String, String> templateMap) {
        log.debug("setTemplateMap : start");

        this.templateMap = templateMap;
    }

    public String get(String templateId) {
        return this.templateMap.get(templateId);
    }
}
