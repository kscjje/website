package com.hisco.intrfc.sale.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgramItemVO {
    private String comcd; // 운영사업자고유번호
    private int itemCd; // 품목고유번호. 시퀀스: SEQ_ITEMCD
    private String itemNm; // 품목명
    private int orgNo; // 평생학습포털을 강좌를 등록하는 시설 기관의 고유번호. 시퀀스(SEQ_ORGNO) 를 사용
    private String partCd; // 사업장고유번호
    private String itemLtype; // 품목유형. COT_GRPCD.GRP_CD = 'SM_ITEM_LTYPE'
    private String itemMtype; // 품목세부유형. COT_GRPCD.GRP_CD = 'SM_ITEM_MTYPE'
    private int costAmt; // 원가
    private int salamt; // 판매금액
    private int monthCnt; // 정기상품이용개월수
    private String vatYn; // 과세여부. Y :과세(부가세포함). N :비과세.
    private int sortno; // 정렬순서
    private String useYn; // 사용여부
    private String etcItemGbn1; // 기타품목분류1
    private String etcItemGbn2; // 기타품목분류2
    private String prdlstEtc; // 비고
    private String suipCd1; // 수입계정대분류코드.
    private String suipCd2; // 수입계정중분류코드
    private String suipCd3; // 수입계정소분류코드
    private String saleTrgetAgegbn; // 상품판매대상연령구분. COT_GRPCD.GRP_CD = 'CM_AGEGBN'
    private int saleTrgetStage; // 상품판매대상시작연령. 품목연령구분에따른 시작나이를 기록한다.. . * 강좌설정시 적용대상나이로 적용됨 . * 일일이용권인경우 전자키 배정시 연령을
                                // 적용함. .
    private int saleTrgetEdage; // 상품판매대상종료연령. 품목연령구분에따른 종료나이를 기록한다.. . * 강좌설정시 적용대상나이로 적용됨 . * 일일이용권인경우 전자키 배정시 연령을
                                // 적용함. . .
    private String kioskItemNm; // 무인키오스크노출요금명(확장성)
    private String webItemNm; // 온라인노출요금명(확장성). . 온라인 수강신청목록에 표시할 요금명을 별도기록관리한다.. 기본적으로는 품목명이 표기됨.
    private String discountYn; // 할인적용대상여부(확장성). 감면을 받을수있는 상품요금인지여부를 관리한다.
    private String gender; // 상품판매대상성별(확장성). M : 남. F : 여. C : 공통
    private int groupcnt; // 단체신청가능최대기준인원. 단체상품판매시 판매가능 매표인원수를 기록한다.. 해당인원이하에서는 단체상품을 매표할수없다.
    private String useDay; // 상품판매가능요일(확장성). 시설이용 및 요금을 적용가능한 요일구분을 기록한다.. . 1: 월요일. 2: 화요일. 3: 수요일. 4: 목요일. 5: 금요일. 6:
                           // 토요일. 7: 일요일. 8: 공휴일
    private String useLimitcntYn; // 정기이용횟수상품여부(확장성)
    private int useCnt; // 품목이용제한횟수(확장성)
    private String usecntDeductgbn; // 정기상품이용기간입장횟수차감기준(확장성). 이용가능횟수 차감기준을 분류한다..
    private String freechargeYn; // 무료요금상품여부(확장성). 무료이용요금상품인경우 'Y' 를 기록한다.
    private String kioskDispyn; // 무인키오스크노출여부. 무인키오스크에 노출할지여부('Y'，'N')를 기록한다
    private String itemUseUnit; // 품목이용수량적용단위(확장성)
    private int itemUnitQty; // 품목이용기본단위수량(확장성)
    private String pergrgbn; // 개인단체상품구분
    private String groupdcyn; // 단체할인적용여부. 단체할인적용여부를 'Y'，'N' 으로 설정한다.
    private String discountKind; // 할인종류코드(확장성). 상품요금을 감면금액을 반영하여 만들어진 상품인 경우. 어떤 할인사유코드를 설정한다.. .
    private String webDispyn; // 온라인노출여부(확장성)
    private String grPrtYn; // 입장권출력여부(확장성). 일반품목 중 이용권 출력여부 Y:출력 N:출력안함. .
    private String allEnterYn; // 전체시설이용권상품여부(확장성)
    private String punchedyn; // 이용업장검표관리대상여부(확장성)
    private Timestamp regdate; // 등록일시
    private String reguser; // 등록자
    private Timestamp moddate; // 수정일시
    private String moduser; // 수정자
}
