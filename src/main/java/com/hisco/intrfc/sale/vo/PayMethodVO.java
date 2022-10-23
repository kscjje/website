package com.hisco.intrfc.sale.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayMethodVO {
    private String pComcd; // 운영사업자고유번호
    private String pType; // 운영사업자고유번호
    private String methodCd; // 운영사업자고유번호
    private String methodNm; // 운영사업자고유번호
}
