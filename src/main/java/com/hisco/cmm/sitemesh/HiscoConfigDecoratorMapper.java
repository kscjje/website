/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.cmm.sitemesh;

import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hisco.cmm.config.DynamicConfig;
import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.DecoratorMapper;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.mapper.ConfigDecoratorMapper;
import com.opensymphony.module.sitemesh.mapper.ConfigLoader;

import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : HiscoConfigDecoratorMapper.java
 * @Description : 자세한 클래스 설명
 * @author vivasoul@legitsys.co.kr
 * @since 2022. 1. 10
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Slf4j
public class HiscoConfigDecoratorMapper extends ConfigDecoratorMapper {
    private ConfigLoader configLoader = null;
    private String adminPath;
    private String managerPath;

    @Override
    public void init(Config config, Properties properties, DecoratorMapper parent) throws InstantiationException {
        super.init(config, properties, parent);
        try {
            String fileName = properties.getProperty("config", "/WEB-INF/decorators.xml");
            this.configLoader = new ConfigLoader(fileName, config);
        } catch (Exception e) {
            throw new InstantiationException(e.toString());
        }
    }

    @Override
    public Decorator getDecorator(HttpServletRequest request, Page page) {
        String thisPath = request.getServletPath();
        if (thisPath == null) {
            String requestURI = request.getRequestURI();
            if (request.getPathInfo() != null) {
                thisPath = requestURI.substring(0, requestURI.indexOf(request.getPathInfo()));
            } else {
                thisPath = requestURI;
            }
        } else if ("".equals(thisPath)) {
            thisPath = request.getPathInfo();
        }

        if (isAdmin(request, thisPath)) {
            thisPath = thisPath.replaceFirst("(" + adminPath + "|" + managerPath + ")", com.hisco.cmm.util.Config.ADMIN_ROOT);
        }

        String name = null;
        try {
            name = this.configLoader.getMappedName(thisPath);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        Decorator result = getNamedDecorator(request, name);
        return (result == null) ? super.getDecorator(request, page) : result;
    }

    private boolean isAdmin(HttpServletRequest request, String path) {
        return path.startsWith(getAdminPath(request)) || path.startsWith(getManagerPath(request));
    }

    private String getAdminPath(HttpServletRequest request) {
        if (adminPath == null) {
            DynamicConfig dynamicConfig = getDynamicConfig(request);
            adminPath = dynamicConfig.getAdminRoot();
        }
        return adminPath;
    }

    private String getManagerPath(HttpServletRequest request) {
        if (managerPath == null) {
            DynamicConfig dynamicConfig = getDynamicConfig(request);
            managerPath = dynamicConfig.getManagerRoot();
        }
        return managerPath;
    }

    private DynamicConfig getDynamicConfig(HttpServletRequest request) {
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return (DynamicConfig) wac.getBean("dynamicConfig");
    }
}
