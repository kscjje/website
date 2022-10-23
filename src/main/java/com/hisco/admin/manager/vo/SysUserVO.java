package com.hisco.admin.manager.vo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.service.EgovProperties;
import lombok.Data;

/**
 * 관리자 계정 model
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
public class SysUserVO extends ComDefaultVO {

    private List<String> orgList;

    private List<SysUserIpVO> ipList;

    /* 디비 암호화 key */
    private String dbEncKey = EgovProperties.getProperty("Globals.DbEncKey");

    /**
     * 권한코드
     */
    private String roleCd;

    /**
     * 권한이름
     */
    private String roleNm;

    /**
     * 아이디
     */
    private String userId;
    /**
     * 비밀번호 암호화
     */
    private String password;
    
    private String userSalt;
    /**
     * 한글이름
     */
    private String korName;
    /**
     * 부서코드
     */
    private String depCd;
    /**
     * 사용여부
     */
    private String useYn;
    /**
     * 전화번호
     */
    private String telNo;
    /**
     * 입사일
     */
    private String enterDate;
    /**
     * 퇴사일
     */
    private String outDate;
    /**
     * 상태
     */
    private String stats;
    /**
     * 사무실 전화번호
     */
    private String offiTel;
    /**
     * 이메일
     */
    private String email;
    /**
     * 비고
     */
    private String remark;
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

    /**
     * 마지막 로그인 날짜
     */
    private Timestamp lastLogin;

    /** 로그 명 */
    private String lognm;

    /** 그룹 아이디 */
    private int groupId;

    /** 그룹 명 */
    private String groupNm;

    /** 그룹 명 */
    private String groupDesc;

    /** 컨텐츠 바로 수정 */
    private String contentsYn;

    /**
     * 접속 ip
     */
    private String ip;

    private String delSeq;

    private String creator = "";

    private String updator = "";

    private Date createDate = null;

    private Date updatedate = null;

    private String userType;
    private String userTypeNm;

    private String acntStats;
    private int failCnt;
    private String pwdChangeyn;
    private Timestamp pwdChangeDate;

    private int orgNo;
    private String orgNm;
    private String depNm;

    private int totCnt;

    private String orgLtype;
    private String orgMtype;
    private String searchOrgNo;
    private String searchDepcd;
    private String searchRole;

}
