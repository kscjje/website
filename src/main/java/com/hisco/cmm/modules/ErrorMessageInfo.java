package com.hisco.cmm.modules;

/**
 * 에러메시지 처리 클래스
 * <br>
 * boolean error : 에러 여부
 * <br>
 * String message : 출력 메시지
 * <br>
 * String url : 이동 URL
 */
public class ErrorMessageInfo {

    private boolean error;
    private String message;
    private String url;
    private Object object;

    public ErrorMessageInfo() {
        error = false;
        object = null;
    }

    public boolean isError() {
        return error;
    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
