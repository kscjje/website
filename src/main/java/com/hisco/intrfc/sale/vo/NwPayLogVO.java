package com.hisco.intrfc.sale.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NwPayLogVO extends ComDefaultVO {
    private int logSeq;
    private String logGbn;
    private String requestInfo;
    private String resultInfo;
    private String erroryn;
    private String errorcode;
    private Timestamp regdate;
    private String errormsg;
}