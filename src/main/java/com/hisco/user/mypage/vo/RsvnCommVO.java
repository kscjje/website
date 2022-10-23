package com.hisco.user.mypage.vo;

import lombok.Data;

/**
 * @Class Name : RsvnCommVO.java
 * @Description : 예약 공통 처리 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 9. 24 진수진
 * @author 진수진
 * @since 2020. 9. 24
 * @version
 * @see
 */
@Data
public class RsvnCommVO {

    private String dcid; // 시퀀스
    private String dcReasonCd; // 할인사유 코드
    private String dcName; // 할인명칭
    private int dcRate; // 할인 %
    private String memberyn; // 회원 할인 여부
    private String nonmebyn; // 비회원 할인 여부

    private String payUpdownUnit;
    private String payUpdown;
    private String refundUpdownUnit;
    private String refundUpdown;

    private int limitCnt; // 할인 횟수 제한
    private int applyCnt; // 이용횟수

    private int limitQty; // 할인 명수

    private String useYn;// 사용여부

}
