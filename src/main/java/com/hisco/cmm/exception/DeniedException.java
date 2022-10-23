package com.hisco.cmm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 권한없음 오류
 * 
 * @author 진수진
 * @since 2021.10.05
 * @version 1.0
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2021.10.05 최초작성
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class DeniedException extends RuntimeException {

    private static final long serialVersionUID = 1L;
}
