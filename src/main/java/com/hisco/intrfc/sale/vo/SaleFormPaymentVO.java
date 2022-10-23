package com.hisco.intrfc.sale.vo;

import javax.validation.constraints.NotNull;

import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;

import lombok.Data;

@Data
public class SaleFormPaymentVO {
    // payment
    @NotNull
    private String onoff; // 예약 및 결제

    @NotNull
    private String rergistGbn; // 등록구분:REG, REF, CAN
    private String oid; // 필요시 db조회를 통한 생성
    private String oidStat; // 거래요청상태. . 1001 : 거래요청(가상계좌용). 2001 : 거래완료. 3001 : 거래취소. 4001 : 거래실패. SM_OID_STAT
    private String rsvnNo;// 주문예약번호. edc_rsvn_info.edc_rsvn_no

    private String saleDate; // 판매일자
    private String saleTime; // 판매일시
    private int dcAmt = 0; // 할인금액: salamt * dcRate
    private int payAmt = 0; // 결제금액: salamt - salamt * dcRate = cashAmt + cardAmt
    private int cashAmt = 0; // 현금결제금액
    private int cardAmt = 0; // 카드결제금액

    private String registGbn;
    
    // private String nwpayOrdid;// 노원페이거래번호(트랜젝션번호)

    private String terminalType; // 등록터미널타입(1001:현장PC， 1002:키오스크， 2001:온라인WEB， 2002:모바일)

    private String discountEtc;
    private String payListYn = Config.YES; // pay_list 테이블 입력 여부
    private String payListRemark; // 통합매출테이블.remark
    private String selngInfoRemark; // 통합매출테이블.remark

    // -pg-
    private String payComcd = Constant.PG_TOSS; // PG사코드(TOSS)
    private String payMethod; // front(SC0010:CARD, SC0030:BANK, SC0040:VBANK), backend(CH:현금, CA:무통장입금)
    private String financeCd; // 카드, 은행 코드
    private String financeNm; // 카드, 은행 이름
    private String financeBrandCd; // 카드일경우, 브랜드(visa, master,,)
    private String financeBrandNm; // 카드일경우, 브랜드명
    private String certDtime; // 승인일시(14자리:년월일시분초)
    private String certNo; // 승인번호
    private String checkCardYn; // 체크카드여부
    private String resultCd;
    private String resultMsg;
    private String cardNo1;
    private String cardNo2;
    private String cardNo3;
    private int halbuCnt;

    // pg vbank
    private int vbankSeq; //
    private String vbankStatus; // 계좌상태
    private String vbankAccountNo; // 계좌번호
    private String vbankBankcd; // 계좌코드
    private String vbankName; // 계좌이름
    private String vbankPname; // 고객이름
    private String vbankPtel; // 고객번호
    private String vbankPemail; // 고객이메일
    private String vbankPayWaitdate; // 입금대기제한일자
    private String vbankPayWaittime; // 입금대기제한시간
    private int VbankOveramt; // 과오금액

    // direct, vbank
    private String cashReceiptKind; // 현금영수증 종류: 0:소득공제, 1:지출증빙

    private String storeNo; // 가맹점번호
    private int feeRate; // 수수료
    private String mid; // 상점아이디
    private String tid;

    private String ip;
    private String appGbn; // 1:카드승인, 2:카드승인취소, 3:현금영수증승인, 4:현금영수증취소. SM_APP_GBN

    // 환불
    private int cancelPayAmt;

    // 환불계좌
    private String dpstrNm;
    private String bankCd;
    private String bankNm;
    private String accountNum;

    private String cancelReasonCd;
    private String cancelReasonDesc;
}
