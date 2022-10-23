package com.hisco.admin.manager.vo;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 이용기관별 접근권한설정 model
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
public class SysUserOrgVO {

    private String comcd;

    private String userId;

    private int orgNo;
    private String orgNm;
    private String orgKind;

    private int seq;

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
