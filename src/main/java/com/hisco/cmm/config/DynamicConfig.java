/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.cmm.config;

import com.hisco.cmm.util.Config;

/**
 * @Class Name : DynamicConfig.java
 * @Description : 자세한 클래스 설명
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 10. 29
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
public class DynamicConfig {
    // 내부 관리자 루트 경로
    private String adminRoot = Config.ADMIN_ROOT;
    // 외부 관리자 루트 경로
    private String managerRoot = Config.MNG_ROOT;

    /**
     * @return the adminRoot
     */
    public String getAdminRoot() {
        return adminRoot;
    }

    /**
     * @return the managerRoot
     */
    public String getManagerRoot() {
        return managerRoot;
    }

    /**
     * @param adminRoot
     *            the adminRoot to set
     */
    public void setAdminRoot(String adminRoot) {
        if (adminRoot == null || adminRoot.isEmpty()) {
            this.adminRoot = Config.ADMIN_ROOT;
        } else {
            if (adminRoot.startsWith("/"))
                this.adminRoot = adminRoot.trim();
            else
                this.adminRoot = "/" + adminRoot.trim();

        }
    }

    /**
     * @param managerRoot
     *            the managerRoot to set
     */
    public void setManagerRoot(String managerRoot) {
        if (managerRoot == null || managerRoot.isEmpty()) {
            this.managerRoot = Config.MNG_ROOT;
        } else {
            if (managerRoot.startsWith("/"))
                this.managerRoot = managerRoot.trim();
            else
                this.managerRoot = "/" + managerRoot.trim();

        }
    }
}
