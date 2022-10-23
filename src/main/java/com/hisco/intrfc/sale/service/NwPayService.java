package com.hisco.intrfc.sale.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgOptinfoVO;
import com.hisco.cmm.service.RestApiClient;
import com.hisco.cmm.util.ExceptionUtil;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.cmm.util.SessionUtil;
import com.hisco.intrfc.sale.vo.NwPayApiType;
import com.hisco.intrfc.sale.vo.NwPayLogVO;
import com.hisco.intrfc.sale.vo.NwPayVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : NwpayService.java
 * @Description : 노원페이 interface
 *              테슽 계정 정보
 *              ID : user220119
 *              PW : test1234!!
 * @author woojinp@legitsys.co.kr
 * @since 2022. 1. 6.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Slf4j
@Service("nwPayService")
public class NwPayService extends EgovAbstractServiceImpl {

    @Value("${nwpay.url}")
    private String nwpayUrl;

    @Value("${nwpay.mid}")
    private String nwpayMid;

    @Value("${nwpay.mpassword}")
    private String nwpayMpassword;

    @Autowired
    RestApiClient restApiClient;

    @Resource(name = "nwPayLogService")
    private NwPayLogService nwPayLogService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    final static private String NW_PAY_APIKEY = "NW_PAY_APIKEY";

    @SuppressWarnings("incomplete-switch")
    public NwPayVO call(NwPayVO reqPayVO) throws Exception {

        Map<String, String> params = new HashMap<String, String>();
        NwPayVO resPayVO = null;

        if (reqPayVO.getOrgNo() < 1) {
            throw new RuntimeException("NWPAY 기관정보(PARAM)가 필요합니다.");
        }

        OrgOptinfoVO orgOptinfoParam = new OrgOptinfoVO();
        orgOptinfoParam.setOrgNo(reqPayVO.getOrgNo());
        OrgOptinfoVO orgOptinfo = orgInfoService.selectOrgOptinfo(orgOptinfoParam);

        if (orgOptinfo == null) {
            throw new RuntimeException("NWPAY 기관정보(DB)가 필요합니다.");
        }

        nwpayMid = orgOptinfo.getAreaPayId();
        nwpayMpassword = orgOptinfo.getAreaPayPwd();

        try {

            if (StringUtils.isBlank(nwpayMid)) {
                throw new RuntimeException("NWPAY 정보(지역화폐-연동 ID)가 존재하지 않습니다.");
            }

            if (StringUtils.isBlank(nwpayMpassword)) {
                throw new RuntimeException("NWPAY 정보(지역화폐-연동 PW)가 존재하지 않습니다.");
            }

            String apikey = this.getApiKey(reqPayVO);

            if (StringUtils.isBlank(apikey)) {
                throw new RuntimeException("NWPAY 서버로부터 apikey를 가져오지 못했습니다.");
            }

            switch (reqPayVO.getApiType()) {
            /*
             * case APIKEY:
             * params.put("id", nwpayMid);
             * params.put("pw", nwpayMpassword);
             * break;
             */
            case APIKEYCHECK:
                params.put("id", nwpayMid);
                params.put("pw", nwpayMpassword);
                params.put("apikey", apikey);
                break;
            case BALANCE:
                params.put("id", nwpayMid);
                params.put("pw", nwpayMpassword);
                params.put("apikey", apikey);
                params.put("useid", reqPayVO.getUseid());
                break;
            case SENDORDER:
                params.put("id", nwpayMid);
                params.put("pw", nwpayMpassword);
                params.put("apikey", apikey);
                params.put("useid", reqPayVO.getUseid());
                params.put("usenw", String.valueOf(reqPayVO.getUsenw()));
                break;
            case WITHDRAWAL:
                params.put("orderID", reqPayVO.getOrderID());
                params.put("apikey", apikey);
                break;
            }

            resPayVO = this.callNwPayApi(reqPayVO.getApiType().getApi(), params);

        } catch (Exception ex) {
            log.error(ExceptionUtil.getErrorLine(ex));

            NwPayLogVO logVO = new NwPayLogVO();
            logVO.setLogGbn("1");
            if (StringUtils.isNotBlank(params.get("pw")))
                params.put("pw", "***");
            logVO.setRequestInfo(JsonUtil.Map2String(params));
            logVO.setErroryn("Y");
            logVO.setErrorcode("999");
            logVO.setErrormsg(ex.getMessage());
            nwPayLogService.newTxInsertNwPayLog(logVO);

            throw ex;
        }

        return resPayVO;
    }

    /**
     * apikey값을 가져와 세션에 넣고 이용
     */
    private String getApiKey(NwPayVO reqPayVO) throws Exception {

        String apiKey = (String) SessionUtil.getAttribute(NW_PAY_APIKEY);

        if (StringUtils.isBlank(apiKey)) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", nwpayMid);
            params.put("pw", nwpayMpassword);

            NwPayVO resPayVO = this.callNwPayApi(NwPayApiType.APIKEY.getApi(), params);
            if ("200,303".contains(resPayVO.getStatus())) { // 200(성공), 303(apikey가 생성되어 존재)
                SessionUtil.setAttribute(NW_PAY_APIKEY, resPayVO.getApikey());
                apiKey = resPayVO.getApikey();
            }
        }

        return apiKey;
    }

    private NwPayVO callNwPayApi(String api, Map<String, String> params) throws Exception {

        MultiValueMap<String, String> mvmParams = new LinkedMultiValueMap<>();

        Set<String> keys = params.keySet();
        for (String key : keys) {
            String value = params.get(key);
            mvmParams.add(key, value);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(mvmParams, headers);
        RestTemplate rt = this.getRestTemplate();
        ResponseEntity<String> response = rt.exchange(nwpayUrl + "/" + api, // {요청할 서버 주소}
                HttpMethod.POST, // {요청할 방식}
                entity, // {요청할 때 보낼 데이터}
                String.class // {요청시 반환되는 데이터 타입}
        );

        if (response == null) {
            throw new RuntimeException(String.format("NWPAY 서버로부터 [%s]호출에대한 정상적인 응답을 받지 못했습니다.", api));
        }

        log.debug("response.body = {}", response.getBody());

        if ("balance".equals(api) && response.getBody().contains("\"userId\":\"N\"")) {
            throw new RuntimeException("존재하지 않는 아이디입니다.");
        }

        if ("sendorder".equals(api) && !response.getBody().contains("\"orderID\"")) {
            throw new RuntimeException("정상적으로 차감되지 않았습니다.NWPAY 담당자에게 문의바랍니다.");
        }

        Gson gson = new Gson();
        NwPayVO resNwPayVO = gson.fromJson(response.getBody(), NwPayVO.class);

        if (log.isDebugEnabled()) {
            log.debug("{}/{} request => {}", nwpayUrl, api, JsonUtil.toPrettyJson(params));
            log.debug("{}/{} response => {}", nwpayUrl, api, JsonUtil.toPrettyJson(resNwPayVO));
        }

        if (resNwPayVO != null) {
            params.put("api", api);

            NwPayLogVO logVO = new NwPayLogVO();
            logVO.setLogGbn("1");
            if (StringUtils.isNotBlank(params.get("pw")))
                params.put("pw", "***");
            logVO.setRequestInfo(JsonUtil.Map2String(params));
            logVO.setResultInfo(resNwPayVO.toString());
            logVO.setErroryn("200".equals(resNwPayVO.getStatus()) ? "N" : "Y");
            logVO.setErrorcode("200".equals(resNwPayVO.getStatus()) ? null : resNwPayVO.getStatus());
            nwPayLogService.newTxInsertNwPayLog(logVO);
        }

        return resNwPayVO;
    }

    private RestTemplate getRestTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(5000); // 읽기시간초과, ms
        factory.setConnectTimeout(3000); // 연결시간초과, ms

        CloseableHttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(100) // connection pool 적용 .
                .setMaxConnPerRoute(5) // connection pool 적용
                .build();

        factory.setHttpClient(httpClient); // 동기실행에 사용될 HttpClient 세팅
        RestTemplate restTemplate = new RestTemplate(factory);

        return restTemplate;
    }
}
