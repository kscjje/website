package com.hisco.cmm.exception;

/**
 * 공통 Custom 예외 처리
 * 
 * @author 전영석
 * @since 2020.08.18
 * @version 1.0, 2020.08.18
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.18 최초작성
 */

public class MyException extends Exception {

    private static final long serialVersionUID = -1460356990632230194L;

    private final int intErrorCode;

    public MyException(int errorCode) {
        super();
        this.intErrorCode = errorCode;
    }

    public MyException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.intErrorCode = errorCode;
    }

    public MyException(String message, int errorCode) {
        super(message);
        this.intErrorCode = errorCode;
    }

    public MyException(Throwable cause, int errorCode) {
        super(cause);
        this.intErrorCode = errorCode;
    }

    public int getErrorCode() {
        return this.intErrorCode;
    }
}
