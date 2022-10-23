package com.hisco.intrfc.sale.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardAppHistVO {
    private String comcd; // 운영사업자고유번호
    private int orgNo; // 평생학습포털을 강좌를 운영하는 등록된 기관의 고유번호로. 시퀀스(SEQ_ORGNO) 를 사용
    private String appDate; // 거래승인일시. PG 또는 VAN사 응답전문에 기록된 승인거래일시. . YYYYMMDDHHMMSS
    private String appNo; // 거래승인번호. PG 또는 VAN사에서 응답받은 승인번호 . . - 오프라인 카드승인 거래인경우 응답받은 승인번호 기록 . - 온라인 카드승인，실시간계좌이체
                          // 거래인겨우 TID 를 기록함. - LG데이콤 카드부분취소인경우 . 취소거래 원승인번호를 기록한다..
    private String memNo; // 회원번호. 결제한 회원번호를 기록함
    private String appGbn; // 승인구분. 1: 카드승인. 2: 카드승인취소. 3: 현금영수증승인. 4: 현금영수증취소
    private String cnlDate; // 취소일자
    private String cnlTime; // 취소시간
    private String partialCnlyn; // 부분취소거래여부. . 신용승인거래시 부분취소거래인경우 Y'를 기록한다.
    private String cardNo1; // 카드번호1
    private String cardNo2; // 카드번호2
    private String cardNo3; // 카드번호3
    private String cardNo4; // 카드번호4(MASKING). * 마스킹후 저장
    private String pComcd; // 결제대행사분류
    private String pType; // 결제수단구분
    private String cardSec; // VAN사(카드사/은행)코드. 카드승인전문정보의 카드매입사 코드를 기록한다.
    private String bankCd; // 은행코드
    private String cardCd; // 카드수수료. VAN 사 카드사코드를 기록한다.
    private int halbuCnt; // 할부개월
    private int appAmt; // 승인금액
    private String cashUserInfo; // 현금영수증 사용자정보. 현금영수증 사용자정보 :전화번호 및 주민번호를 나타냄
    private String cashAppGbn; // 현금영수증발급구분
    private String cashAppCnldate; // 현금영수증취소일자
    private String cardSaleNo; // 카드사매출번호. 올앳결제서비스 인경우 거래일련번호를 생성 기록함
    private String storeNo; // 카드사별가맹점번호. 카드사별 가맹계약한 가맹번호가 기록된다.
    private String payListYn; // 지급내역적용여부
    private String forceYn; // 강제입력여부. 신용카드 수동입력데이터 구분을 나타낸다
    private String pgVan; // PG사VAN사 구분
    private String keyinType; // 카드입력 방법
    private String inDate; // 입금예정일자
    private String checkGbn; // 체크카드구분
    private int cardandbankRateAmt; // 카드및계좌수수료요금
    private int cardandbankRate; // 카드및계좌수수료요율. 신용카드 승인 결제 및 계좌 이체 결제시 결제수단에 따른 수수료요율을 기록한다.
    private String remark; // 비고
    private String mid; // PG상점ID
    private String oid; // 거래주문번호. PG거래시 전달되었던 PG 사 응답전문으로 받은 거래 주문번호를 기록한다.. .
    private String tid; // 트랜젝션ID. PG사별 주문완료 거래번호를 기록한다.
    private String orderTerminalid; // 거래단말ID. PC별 단말ID 운영시 거래승인에 대한 단말ID를 기록한다.
    private String pcancelNo; // 부분취소요청번호. . 거래당 부분 거래취소요청된 번호순번을 . 3자리 '001' 로 번호룰 부여한다.
    private String uip; // 결제IP
    private String resultmsg; // 결과메세지
    private String terminalType; // 등록터미널타입
    private Timestamp regdate; // 등록일시
    private String reguser; // 등록자
    private Timestamp moddate; // 수정일시
    private String moduser; // 수정자
}
