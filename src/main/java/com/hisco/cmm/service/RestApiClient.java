package com.hisco.cmm.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.user.member.vo.MemberNowon;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@Slf4j
@Component
public class RestApiClient {

    private static final String HEADER_PREFIX = "Bearer ";

    /**
     * 기본 API 콜 모듈
     *
     * @param strRestAPI
     * @param objReqParam
     * @return
     */
    public JSONObject callRestAPI(String strRestAPI, MemberNowon objReqParam) {

        String strResult = "";
        JSONObject jsonResult = new JSONObject();
        // String restServerURL = "";

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // HttpEntity<String> entity = new HttpEntity<String>(objectMapper.writeValueAsString(objReqParam),
            // generateHeader());

            // restServerURL = "";
            log.info("[API][POST  :" + strRestAPI + "]");
            log.info("[API][param :" + objectMapper.writeValueAsString(objReqParam) + "]");

            if (!StringUtils.isEmpty(strRestAPI)) {

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                RestTemplate restTemplate = getRestTemplate();

                MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
                map.add("userId", objReqParam.getUserId());
                map.add("userPassword", objReqParam.getUserPassword());

                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

                strResult = restTemplate.postForObject(strRestAPI, request, String.class);

                jsonResult = JSONObject.fromObject(strResult);

                log.info("[API][result:" + jsonResult.toString());
            } else {
                log.error("[API][restServerURL empty]");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonResult;
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity<Object> get(String url, Map<String, String> parameters, Object retObj) throws Exception {

        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("url은 필수입니다.");
        }

        log.info("[API][GET] URL : {}]", url);
        log.info("[API][GET] PARAMETERS : {}", JsonUtil.Map2String(parameters));

        RestTemplate restTemplate = getRestTemplate();
        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) restTemplate.getForEntity(url, retObj.getClass(), parameters);

        log.info("[API][GET] result : {}", JsonUtil.Object2String(responseEntity));

        return responseEntity;
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity<Object> post(String url, Map<String, String> parameters, Object retObj) throws Exception {

        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("url은 필수입니다.");
        }

        log.info("[API][POST] URL : {}]", url);
        log.info("[API][POST] PARAMETERS : {}", JsonUtil.Map2String(parameters));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate restTemplate = getRestTemplate();

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        for (String key : parameters.keySet()) {
            map.add(key, parameters.get(key));
        }

        HttpEntity<MultiValueMap<String, String>> reqHttpEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) restTemplate.postForEntity(url, reqHttpEntity, retObj.getClass());

        log.info("[API][POST] result : {}", JsonUtil.Object2String(responseEntity));

        return responseEntity;
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

    private HttpHeaders generateHeader() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpHeaders header = new HttpHeaders();

        // header.setContentType(mediaType);
        // header.setContentType(MediaType.APPLICATION_JSON);
        header.set("Content-Type", "application/json");
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return header;
    }

}
