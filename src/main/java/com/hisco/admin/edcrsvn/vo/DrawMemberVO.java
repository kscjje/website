package com.hisco.admin.edcrsvn.vo;

import lombok.Data;

@Data
public class DrawMemberVO {
    private String comcd;
    private int edcRsvnReqid; // 예약번호
    private int edcStat;
    private String przwinStat;
    private String przwinyn;

    private String edcMemNo; // 회원번호
    private String id; // 회원아이디
    private String edcRsvnCustnm; // 회원이름
    private String edcRsvnMoblphon; // 회원핸드폰
    private String edcRsvnBirth; // 회원생일
}
