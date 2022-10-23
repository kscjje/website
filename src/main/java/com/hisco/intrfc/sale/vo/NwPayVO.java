package com.hisco.intrfc.sale.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hisco.cmm.modules.JsonUtil;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NwPayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private NwPayApiType apiType;

    private String id; // 가맹점 user id
    private String pw; // 가맹점 user pw

    private String apikey; // 성성시 api를 호출할때 사용될 키
    private String data; // 성공메시지
    private int amount; // balance금액

    private int usenw; // 차감할 NW

    private String useid; // 요청유저id
    private String user; // 응답유저id
    private String orderID; // 주문기록idx

    private String status; // 호출결과코드
    private String statusDesc; // 상태정보

    private int orgNo; // 기관번호

    public void setStatus(String status) {
        this.status = status;
        this.statusDesc = STATUS_CODE_MAP.get(apiType.getApi() + status);
    }

    @Override
    public String toString() {
        Map<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(id))
            map.put("id", id);
        if (StringUtils.isNotBlank(pw))
            map.put("pw", pw);
        if (StringUtils.isNotBlank(status))
            map.put("status", status);
        if (StringUtils.isNotBlank(apikey))
            map.put("apikey", apikey);
        if (StringUtils.isNotBlank(data))
            map.put("data", data);

        if (NwPayApiType.BALANCE.equals(apiType))
            map.put("amount", amount);

        if (usenw > 0)
            map.put("usenw", usenw);

        if (StringUtils.isNotBlank(user))
            map.put("user", user);
        if (StringUtils.isNotBlank(orderID))
            map.put("orderID", orderID);

        return JsonUtil.Map2String(map);
    }

    public boolean success() {
        if ("200".equals(this.status))
            return true;
        return false;
    }

    public final static Map<String, String> STATUS_CODE_MAP = new HashMap<String, String>();
    static {
        STATUS_CODE_MAP.put("apikey200", "성공");
        STATUS_CODE_MAP.put("apikey300", "ID/PW값이 없음");
        STATUS_CODE_MAP.put("apikey301", "DB에 ID가 존재하지 않음");
        STATUS_CODE_MAP.put("apikey302", "DB에 PW가 일치하지 않음");
        STATUS_CODE_MAP.put("apikey303", "API KEY가 생성되어 존재");
        STATUS_CODE_MAP.put("apikey304", "QUERY ERROR (ap key 생성에러)");

        STATUS_CODE_MAP.put("apikeycheck200", "성공");
        STATUS_CODE_MAP.put("apikeycheck300", "ID/PW/APIKEY 값이 없음");
        STATUS_CODE_MAP.put("apikeycheck301", "DB에 ID가 존재 하지 않음");
        STATUS_CODE_MAP.put("apikeycheck302", "DB에 PW가 일치하지 않음");
        STATUS_CODE_MAP.put("apikeycheck303", "API KEY가 존재하지 않거나 관리자 승인을 받지 않음");

        STATUS_CODE_MAP.put("balance200", "성공");
        STATUS_CODE_MAP.put("balance300", "ID/PW/APIKEY/조회할 유저ID 값이 없음");
        STATUS_CODE_MAP.put("balance301", "DB에 ID가 존재 하지 않음");
        STATUS_CODE_MAP.put("balance302", "DB에 PW가 일치하지 않음");
        STATUS_CODE_MAP.put("balance303", "API KEY가 존재하지 않거나 관리자 승인을 받지 않음");
        STATUS_CODE_MAP.put("balance304", "QUERY ERROR (balance 조회 오류)");

        STATUS_CODE_MAP.put("sendorder200", "성공");
        STATUS_CODE_MAP.put("sendorder300", "ID/PW/APIKEY/조회할 유저ID 값이 없음");
        STATUS_CODE_MAP.put("sendorder301", "DB에 ID가 존재 하지 않음");
        STATUS_CODE_MAP.put("sendorder302", "DB에 PW가 일치하지 않음");
        STATUS_CODE_MAP.put("sendorder303", "API KEY가 존재하지 않거나 관리자 승인을 받지 않음");
        STATUS_CODE_MAP.put("sendorder304", "차감할 유저가 존재하지 않음(체크1)");
        STATUS_CODE_MAP.put("sendorder305", "차감할 NW이 유저가 보유한 NW보다 적음");
        STATUS_CODE_MAP.put("sendorder306", "차감할 유저가 존재하지 않음(체크2)");
        STATUS_CODE_MAP.put("sendorder307", "QUERY ERROR (INSERT ERROR - user)");
        STATUS_CODE_MAP.put("sendorder308", "QUERY ERROR (UPDATE ERROR - user)");
        STATUS_CODE_MAP.put("sendorder309", "QUERY ERROR (INSERT ERROR - store)");
        STATUS_CODE_MAP.put("sendorder310", "QUERY ERROR (UPDATE ERROR - store)");

        STATUS_CODE_MAP.put("withdrawal200", "성공");
        STATUS_CODE_MAP.put("withdrawal300", "orderID 값이 없음");
        STATUS_CODE_MAP.put("withdrawal301", "오늘날짜가 지남");
        STATUS_CODE_MAP.put("withdrawal302", "취소가 되어있음");
        STATUS_CODE_MAP.put("withdrawal303", "QUERY ERROR(SELECT 환불대상 BALANCE)");
        STATUS_CODE_MAP.put("withdrawal304", "QUERY ERROR(SELECT 환불대상 DATA)");
        STATUS_CODE_MAP.put("withdrawal305", "QUERY ERROR(SELECT 가맹점 DATA)");
        STATUS_CODE_MAP.put("withdrawal306", "환불 시 50000NW 초과");
        STATUS_CODE_MAP.put("withdrawal307", "QUERY ERROR (INSERT 환불대상 DATA)");
        STATUS_CODE_MAP.put("withdrawal308", "QUERY ERROR (INSERT 가맹점 DATA)");
        STATUS_CODE_MAP.put("withdrawal309", "QUERY ERROR (UPDATE 가맹점 BALANCE)");
        STATUS_CODE_MAP.put("withdrawal310", "QUERY ERROR (UPDATE 환불대상 BALANCE)");
        STATUS_CODE_MAP.put("withdrawal311", "QUERY ERROR (UPDATE 기존 환불대상 POINT tale ROW use_type=7, content=결제취소)");
        STATUS_CODE_MAP.put("withdrawal312", "QUERY ERROR (UPDATE 기존 스토어 POINT tale ROW use_type=7, content=결제취소)");
    }
}
