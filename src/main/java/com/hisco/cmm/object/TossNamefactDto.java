package com.hisco.cmm.object;

import java.io.Serializable;
import java.util.Date;

import com.hisco.cmm.object.DefaultObject;

public class TossNamefactDto extends DefaultObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String crc_type; // 본인인증 종류
    private String crc_data_ci; // 본인인증 연계정보 결과값
    private String crc_data_di; // 본인인증 중복가입 결과값

    private String type; // 회원 분류(일반 : normal, 어린이 : child, 외국인 : foreigner, 기타 : etc, 외부 로그인 api 이용시 해당 메인 도메인 정보에 접두사
                         // 'api-' 추가후 삽입)

    private String name; // 실명
    private String sex; // 성별(M : 남성, F : 여성)
    private int age; // 나이
    private Date birthday; // 생년월일
    private String tel; // 전화번호
    private String foreigner; // 외국인 여부(Y : 외국인, N : 내국인)

    public String getCrc_type() {
        return crc_type;
    }

    public void setCrc_type(String crc_type) {
        this.crc_type = crc_type;
    }

    public String getCrc_data_ci() {
        return crc_data_ci;
    }

    public void setCrc_data_ci(String crc_data_ci) {
        this.crc_data_ci = crc_data_ci;
    }

    public String getCrc_data_di() {
        return crc_data_di;
    }

    public void setCrc_data_di(String crc_data_di) {
        this.crc_data_di = crc_data_di;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getForeigner() {
        return foreigner;
    }

    public void setForeigner(String foreigner) {
        this.foreigner = foreigner;
    }
}
