package com.hisco.admin.manager.vo;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 관리사용자IP접근보안정책
 *
 * @author 진수진
 * @since 2020.07.14
 * @version 1.0, 2020.07.14
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.14 최초작성
 */
@Data
public class SysUserIpVO {

    private String comcd;

    private String userId;

    private int ipSeq;
    private String ipInfo;

    /**
     * 등록자 id
     */
    private String reguser;

    /**
     * 등록일
     */
    private Timestamp regdate;

    /**
     * 변경자 id
     */
    private String moduser;

    /**
     * 변경일
     */
    private Timestamp moddate;
}
