package com.hisco.cmm.modules;

import java.util.Map;

// public abstract class DefaultObject { 원본 JYS 2021. 06. 21.
public class DefaultObject {

    /**
     * Object 정보 Map 으로 변환
     * 
     * @return
     */
    public Map<String, Object> toMap() {
        return ObjectUtil.ConvertMap(this);
    }

    /**
     * Object 정보 JSON 문자열로 변환
     */
    public String toString() {
        return ObjectUtil.ConvertJsonString(this);
    }

    /**
     * Object 정보에 자료 삽입
     * 
     * @param data
     */
    public void push(Object data) {
        ObjectUtil.ObjectValueCopy(this, data);
    }
}
