/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package egovframework.com.uat.uia.service;

/**
 * @Class Name : LockStatus.java
 * @Description : 자세한 클래스 설명
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 10. 28
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
public class LockStatus {
    public static final String LOCKED = "L"; // 계정 잠금

    public static final String LOCKING = "C"; // 계정 잠금 진행 중

    public static final String UNLOCKED = "E"; // 계정 잠금 해제

    public static final String NOT_FOUND = "N"; // 계정 잠금 파악 불가

    public static final String NOT_ALLOWED = "D"; // 허가되지 않은 사용자
}
