package com.hisco.intrfc.sale.vo;

import com.hisco.cmm.util.JsonUtil;
import com.hisco.cmm.util.WebEncDecUtil;

import lombok.Data;

@Data
public class TossResponseVO {
    private String LGD_BUYEREMAIL; //
    private String LGD_BUYERIP; //
    private String LGD_AUTHTYPE; // XMPI
    private String LGD_TIMESTAMP; //
    private String LGD_BUYER; //
    private String LGD_PRODUCTINFO; //
    private String LGD_BUYERID;
    private String LGD_BUYERPHONE;

    // 공통
    private String CST_MID; // 상점아이디
    private String CST_PLATFORM;

    private String LGD_MID; // 상점아이디
    private String LGD_OID; // 주문아이디
    private String LGD_AMOUNT; // 결제금액

    private String LGD_TXNAME; // 메소드. PaymentByKey
    private String LGD_PAYKEY; // 인증키
    private String LGD_RESPCODE; // 0000:성공, 이외는 실패
    private String LGD_RESPMSG; // 응답메시지
    private String LGD_PARAMS; // return url에서 설정된 결과값

    private String LGD_TID; // 거래번호
    private String LGD_PAYTYPE; // 결제수단코드(SC0010, SC0030, SC0040)
    private String LGD_PAYDATE; // 결제일시(년월일시분초)
    private String LGD_HASHDATA; // 해쉬데이타
    private String LGD_FINANCECODE; // 결제기관코드 ex)51200 (앞2자리 카드사코드, 뒤3자리)
    private String LGD_FINANCENAME; // 결제기관명. ex)삼성MASTER
    private String LGD_CARDACQUIRER; // 결제기관코드 2자리(LGD_FINANCECODE의 앞2자리)
    private String LGD_ESCROWYN; // 에스크로적용여부

    // 신용카드
    private String LGD_CARDNUM; // 신용카드번호
    private String LGD_CARDINSTALLMONTH; // 신용카드할부개월 00:일시불
    private String LGD_CARDNOINTYN; // 신용카드무이자여부 (1:무이자, 0:일반)
    private String LGD_FINANCEAUTHNUM; // 결제기관승인번호

    // 계좌이체, 무통장
    private String LGD_CASHRECEIPTNUM; // 현금영수증 승인번호. 현금영수증 건이 아니거나 실패인경우 "0"
    private String LGD_CASHRECEIPTSELFYN; // 현금영수증자진발급제유무. Y:자진발급
    private String LGD_CASHRECEIPTKIND; // 현금영수증 종류, 0:소득공제, 1:지출증빙
    private String LGD_PAYER; // 입금자명

    // 무통장
    private String LGD_ACCOUNTNUM; // 입금할 계좌번호
    private String LGD_CASTAMOUNT; // 입금누적금액
    private String LGD_CASCAMOUNT; // 현입금금액
    private String LGD_CASFLAG; // 거래종류. R:할당, I:입금, C:취소
    private String LGD_CASSEQNO; // 가상계좌일련번호
    private String LGD_CLOSEDATE;

    // 취소
    private String LGD_CANREQDATE;
    private String LGD_RESPDATE;

    // 환불계좌
    private String dpstrNm;
    private String bankCd;
    private String bankNm;
    private String accountNum;

    // 현금영수증 발급종류
    private String cachReceiptKind;

    private String comcd;
    private String rsvnNo;
    private String dcCd;
    private int dcRate;
    private int saleAmt;
    private int dcAmt;
    private int payAmt;
    private int nwpayAmt;
    private String nwpayId;

    // 인코딩되어있는 LGD_PARAMS를 디코딩하여 파싱한 후 객체에 매핑
    public TossResponseVO getLGD_PARAMS(String SpowiseCmsKey) {
        String str = WebEncDecUtil.fn_decrypt(this.LGD_PARAMS, SpowiseCmsKey);
        return (TossResponseVO) JsonUtil.String2Object(str, TossResponseVO.class);
    }

}
