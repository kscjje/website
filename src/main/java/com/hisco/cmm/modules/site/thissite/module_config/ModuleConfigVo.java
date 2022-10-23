package com.hisco.cmm.modules.site.thissite.module_config;

import java.io.Serializable;
import java.util.Date;

import com.hisco.cmm.modules.extend.ExtraData;

public class ModuleConfigVo extends ExtraData implements Serializable {
    private static final long serialVersionUID = 1L;

    private long module_config_srl; // 모듈 설정 고유번호
    private long site_srl; // 사이트 고유번호
    private String module_key; // 모듈 키
    private String id; // 모듈 아이디
    private String name; // 모듈 이름
    private String use_YN; // 모듈 사용 유무(Y/N)
    private String search_YN; // 사이트 검색 허용 여부(Y/N)
    // private String extra_data; // 추가정보
    private Date regdate; // 등록일
    private Date editdate; // 수정일

    public long getModule_config_srl() {
        return module_config_srl;
    }

    public void setModule_config_srl(long module_config_srl) {
        this.module_config_srl = module_config_srl;
    }

    public long getSite_srl() {
        return site_srl;
    }

    public void setSite_srl(long site_srl) {
        this.site_srl = site_srl;
    }

    public String getModule_key() {
        return module_key;
    }

    public void setModule_key(String module_key) {
        this.module_key = module_key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUse_YN() {
        return use_YN;
    }

    public void setUse_YN(String use_YN) {
        this.use_YN = use_YN;
    }

    public String getSearch_YN() {
        return search_YN;
    }

    public void setSearch_YN(String search_YN) {
        this.search_YN = search_YN;
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
