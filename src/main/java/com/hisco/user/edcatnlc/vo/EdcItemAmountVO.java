package com.hisco.user.edcatnlc.vo;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

/**
 * @Class Name : EvtItemAmountVO.java
 * @Description : 강연/행사/영화 가격정보 VO
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 8. 28 김희택
 * @author 김희택
 * @since 2020. 8. 28
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class EdcItemAmountVO implements Serializable {

    private String comcd;// 기관고유번호
    private String evtNo;// 강연,행사,영화 고유번호
    private String itemCd;// 품목코드
    private long itemPrice;// 가격
    private String useyn;// 사용여부

    // param
    private String itemNm;
    private long itemCnt;

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public String getEvtNo() {
        return evtNo;
    }

    public void setEvtNo(String evtNo) {
        this.evtNo = evtNo;
    }

    public String getItemCd() {
        return itemCd;
    }

    public void setItemCd(String itemCd) {
        this.itemCd = itemCd;
    }

    public long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getUseyn() {
        return useyn;
    }

    public void setUseyn(String useyn) {
        this.useyn = useyn;
    }

    public long getItemCnt() {
        return itemCnt;
    }

    public void setItemCnt(long itemCnt) {
        this.itemCnt = itemCnt;
    }

    public String getItemNm() {
        return itemNm;
    }

    public void setItemNm(String itemNm) {
        this.itemNm = itemNm;
    }

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
