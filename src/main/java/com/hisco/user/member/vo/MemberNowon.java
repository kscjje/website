package com.hisco.user.member.vo;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

import lombok.Data;

/**
 * @Class Name : MemberNowon.java
 * @Description : 노원 회원연계를 위한 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2021.12. 28 진수진
 * @author 진수진
 * @since 2021.12. 28
 * @version
 * @see
 */
@Data
@SuppressWarnings("serial")
public class MemberNowon implements Serializable {

    private String comcd = Config.COM_CD /* 기관고유번호 */;

    private String userId;

    private String userPassword;
    private String rtnUrl;
    private String loginResultCode;
    private String userName;

    private String sexCode;
    private String birthDay;
    private String email;
    private String telNo;
    private String mobileNo;
    private String zip;
    private String address;
    private String detailAddress;

    private String ipAdres;

    private HashMap paramMap;

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
