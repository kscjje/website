package com.hisco.cmm.object;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ajax 실행 후 반환되는 객체
 * 
 * @author 진수진
 * @since 2020.07.01
 * @version 1.0, 2020.07.01
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.01 최초작성
 */
public class ResultInfo {
    /**
     * 결과 코드 정보
     */
    private String code;

    /**
     * 결과 메세지 정보
     */
    private String msg;

    /**
     * 결과 데이터 정보
     */
    private Map data = new HashMap();

    /**
     * 유효성 체크 여부
     */
    private boolean isValid = true;

    /**
     * 성공여부
     */
    private boolean isSuccess = true;

    /**
     * 유효성 체크 대상 input Element id
     */
    private String id;

    public ResultInfo() {
    }

    public ResultInfo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultInfo(String code, String msg, String id) {
        this.code = code;
        this.msg = msg;
        this.id = id;
    }

    /**
     * 코드 반환
     *
     * @param
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * 코드 설정
     *
     * @param code
     * @return
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 메시지 반환
     *
     * @param
     * @return String
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 메시지 설정
     *
     * @param msg
     * @return
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 유효성 여부 체크 반환
     *
     * @param
     * @return boolean
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * 유효성 여부 체크 설정
     *
     * @param valid
     * @return
     */
    public void setValid(boolean valid) {
        isValid = valid;
    }

    /**
     * id 반환
     *
     * @param
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * id 설정
     *
     * @param id
     * @return
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * data 반환
     *
     * @param
     * @return Map
     */
    public Map getData() {
        return data;
    }

    /**
     * data 설정
     *
     * @param data
     * @return
     */
    public void setData(Map data) {
        this.data = data;
    }

    /**
     * 성공여부 반환
     *
     * @param
     * @return boolean
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * 성공여부 설정
     *
     * @param success
     * @return
     */
    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("code", code).append("msg", msg).append("isValid", isValid).append("isSuccess", isSuccess).append("id", id).toString();
    }
}
