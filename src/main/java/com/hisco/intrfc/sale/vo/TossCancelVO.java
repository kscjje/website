package com.hisco.intrfc.sale.vo;

import lombok.Data;

@Data
public class TossCancelVO {
    // 공통
    private String LGD_TXNAME; // Cancel, PartialCancel
    private String LGD_MID;
    private String LGD_TID;
    private String LGD_CANCELAMOUNT; // 부분취소금액
    private String LGD_CANCELREASON; // 취소사유

    private String LGD_PCANCELCNT; // 부분취소금액

    // 신용카드
    private String LGD_REMAINAMOUNT; // 취소전 남은 금액
    private String LGD_REQREMAIN; // 취소후 남은 금액 리턴여부(1:남은 금액 리턴함, 0: 리턴안함)

    // 가상계좌
    private String LGD_RFBANKCODE; // 환불계좌은행코드
    private String LGD_RFACCOUNTNUM; // 환불계좌번호
    private String LGD_RFCUSTOMERNAME; // 환불계좌예금주
    private String LGD_RFPHONE; // 요청자연락처
}
