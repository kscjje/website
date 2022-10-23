package com.hisco.cmm.modules.site.thissite.key;

import java.io.Serializable;
import java.util.Date;

import com.hisco.cmm.modules.extend.DefaultObject;

public class KeyVo extends DefaultObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private long key_srl; // 고유번호
    private String code; // 키 코드
    private long value; // 키 값
    private Date regdate; // 등록일
    private Date editdate; // 수정일

    public long getKey_srl() {
        return key_srl;
    }

    public void setKey_srl(long key_srl) {
        this.key_srl = key_srl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Date getEditdate() {
        return editdate;
    }

    public void setEditdate(Date editdate) {
        this.editdate = editdate;
    }
}
