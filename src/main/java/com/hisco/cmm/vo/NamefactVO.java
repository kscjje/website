package com.hisco.cmm.vo;

import java.io.Serializable;
import java.util.Date;

public class NamefactVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String dbEncKey; // DB 암호화 키
    private String crcType; // 본인인증 종류
    private String crcDataci; // 본인인증 연계정보 결과값
    private String crcDatadi; // 본인인증 중복가입 결과값

    private String type; // 회원 분류(일반 : normal, 어린이 : child, 외국인 : foreigner, 기타 : etc, 외부 로그인 api 이용시 해당 메인 도메인 정보에 접두사
                         // 'api-' 추가후 삽입)

    private String name; // 실명
    private String sex; // 성별(M : 남성, F : 여성)
    private int age; // 나이
    private Date birthday; // 생년월일
    private String tel; // 전화번호
    private String foreigner; // 외국인 여부(Y : 외국인, N : 내국인)
    private String id;
    private String birthYmd; // 생년월일
    private String memNo; // 회원 번호
    private String snsId; // sns id

    private String respCode; // 응답코드
    private String respMsg; // 응답 메시지

    public String getCrc_type() {
        return crcType;
    }

    public void setCrc_type(String crcType) {
        this.crcType = crcType;
    }

    public String getCrc_data_ci() {
        return crcDataci;
    }

    public void setCrc_data_ci(String crcDataci) {
        this.crcDataci = crcDataci;
    }

    public String getCrc_data_di() {
        return crcDatadi;
    }

    public void setCrc_data_di(String crcDatadi) {
        this.crcDatadi = crcDatadi;
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

    public String getDbEncKey() {
        return dbEncKey;
    }

    public void setDbEncKey(String dbEncKey) {
        this.dbEncKey = dbEncKey;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthYmd() {
        return birthYmd;
    }

    public void setBirthYmd(String birthYmd) {
        this.birthYmd = birthYmd;
    }

    public String getMemNo() {
        return memNo;
    }

    public void setMemNo(String memNo) {
        this.memNo = memNo;
    }

    public String getSnsId() {
        return snsId;
    }

    public void setSnsId(String snsId) {
        this.snsId = snsId;
    }

}
