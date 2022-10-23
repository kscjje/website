package com.hisco.cmm.annotation;

/**
 * Controller method 의 Action annotation 클래스
 * 관리자 action log 를 저장하기 위해 사용
 *
 * @author 진수진
 * @since 2021.10.05
 * @version 1.0.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일		수정자		수정내용
 *  -------    	--------    ---------------------------
 *  2021.10.05	진수진 		최초 생성
 *      </pre>
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PageActionInfo {
    String title() default ""; // action 명칭

    String action() default ""; // action 구분 CRUD

    boolean ajax() default false; // ajax여부

    boolean inqry() default false; //개인정보 접속 여부
}
