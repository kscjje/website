package com.hisco.intrfc.charge.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @Class Name : MemberCarVO.java
 * @Description : 주차정보 처리를 위한 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 8. 14 김희택
 * @author 김희택
 * @since 2020. 8. 14
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class OrderIdVO implements Serializable {

    private String retOid; /* 주문번호 */
    private String retMsg; /* 리턴 메시지 */
    private String retCd; /* 리턴 코드 */
    private List<?> retCursor; /* 리턴 커서 */

    private String comcd /* 기관고유번호 */;
    private String userId /* 회원아이디 */;
    private String rsvnNo /* 예약번호(,분리하여 전송) */;
    private String itemCd /* 상품코드 (연회원 가입일떄) */;
    private long rsvnCnt /* 요청건수 */;
    private long rsvnAmt /* 요청금액 */;

    public String getRetOid() {
        return retOid;
    }

    public void setRetOid(String retOid) {
        this.retOid = retOid;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getRetCd() {
        return retCd;
    }

    public void setRetCd(String retCd) {
        this.retCd = retCd;
    }

    public List<?> getRetCursor() {
        return retCursor;
    }

    public void setRetCursor(List<?> retCursor) {
        this.retCursor = retCursor;
    }

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRsvnNo() {
        return rsvnNo;
    }

    public void setRsvnNo(String rsvnNo) {
        this.rsvnNo = rsvnNo;
    }

    public String getItemCd() {
        return itemCd;
    }

    public void setItemCd(String itemCd) {
        this.itemCd = itemCd;
    }

    public long getRsvnCnt() {
        return rsvnCnt;
    }

    public void setRsvnCnt(long rsvnCnt) {
        this.rsvnCnt = rsvnCnt;
    }

    public long getRsvnAmt() {
        return rsvnAmt;
    }

    public void setRsvnAmt(long rsvnAmt) {
        this.rsvnAmt = rsvnAmt;
    }

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
