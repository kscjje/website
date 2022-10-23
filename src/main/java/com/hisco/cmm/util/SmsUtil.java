package com.hisco.cmm.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @Class Name : SmsUtil.java
 * @Description : 문자 메시지 관련 유틸리티
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- -------- ---------------------------
 *               2020.09.06 전영석 최초 작성
 * @author 전영석
 * @since 2020.09.06
 * @version 1.0
 * @see
 */

import org.springframework.stereotype.Component;

import com.hisco.cmm.exception.MyException;
import com.hisco.intrfc.sms.service.SmsUmsService;

import egovframework.com.utl.fcc.service.EgovStringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SmsUtil {

    public SmsUtil() {
    }

    @Resource(name = "smsUmsService")
    SmsUmsService smsUmsService;

    private String gstrMsgTitle = "";// 문자제목
    private String gstrMsgBody = "";// 문자내용
    private String gstrSndPhoneNum = "";// 송신번호
    private String gstrRcvPhoneNum = "";// 수신번호
    private int msgcd;// 메시지코드
    private String sendUser; // 전송자 회원이면 => 회원명(회원번호), 비회면 이면 => 예약자명

    //// private int intSmsLmsMmsVal = 0;

    public void setMsgTitle(String astrMsgTitle) throws Exception {
        this.gstrMsgTitle = astrMsgTitle;
    }

    public String getGstrMsgBody() {
        return gstrMsgBody;
    }

    public int getMsgcd() {
        return msgcd;
    }

    public void setGstrMsgBody(String gstrMsgBody) {
        this.gstrMsgBody = gstrMsgBody;
    }

    public void setMsgBody(String astrMsgBody) throws Exception {
        this.gstrMsgBody = astrMsgBody;
    }

    public void setSndPhoneNum(String astrSndPhoneNum) throws Exception {
        this.gstrSndPhoneNum = astrSndPhoneNum;
    }

    public void setRcvPhoneNum(String astrRcvPhoneNum) throws Exception {
        this.gstrRcvPhoneNum = astrRcvPhoneNum;
    }

    public void setMsgcd(int msgcd) {
        this.msgcd = msgcd;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public HashMap<String, String> sendMessage() throws Exception {

        HashMap<String, String> reMap = new HashMap<String, String>();
        HashMap<String, String> smsMap = new HashMap<String, String>();

        try {

            // -----------------------------보내는 번호 처리--------------------시작
            String strSndPhoneNum = CommonUtil.ClearStrNull2Space(gstrSndPhoneNum);

            if ("".equals(strSndPhoneNum)) {

                reMap.put("DK_CODE", "-1");
                reMap.put("DK_MSG", "보내는 번호를 입력하세요");
                reMap.put("DE_CODE", "-1");
                reMap.put("DE_MSG", "Please enter the number you are sending");

                throw new MyException(reMap.toString(), -2);

            }
            smsMap.put("SND_PHN_NUM", strSndPhoneNum);
            smsMap.put("SEND_USER", sendUser);
            // -----------------------------보내는 번호 처리--------------------끝

            // -----------------------------받는 번호 처리--------------------시작
            String strRcvPhoneNum = CommonUtil.ClearStrNull2Space(gstrRcvPhoneNum);

            if ("".equals(strRcvPhoneNum)) {

                reMap.put("DK_CODE", "-2");
                reMap.put("DK_MSG", "받는 번호를 입력하세요");
                reMap.put("DE_CODE", "-2");
                reMap.put("DE_MSG", "Please enter the number you are receiving");

                throw new MyException(reMap.toString(), -2);

            }
            smsMap.put("RCV_PHN_NUM", strRcvPhoneNum);
            // -----------------------------받는 번호 처리--------------------끝

            // -----------------------------문자 내용 처리------------------------시작
            String strMsgBody = CommonUtil.ClearStrNull2Space(gstrMsgBody);

            if ("".equals(strMsgBody)) {
                reMap.put("DK_CODE", "-3");
                reMap.put("DK_MSG", "문자 메시지 내용을 입력하세요");
                reMap.put("DE_CODE", "-3");
                reMap.put("DE_MSG", "Please enter text message content");

                throw new MyException(reMap.toString(), -2);
            }

            int intSendReturn = 0;
            int intMsgBodyLen = strMsgBody.getBytes("UTF-8").length;

            smsMap.put("SND_MSG", strMsgBody);
            // -----------------------------문자 내용 처리------------------------끝

            if (intMsgBodyLen > 90 && intMsgBodyLen <= 2000) {
                smsMap.put("MSG_TITLE", gstrMsgTitle);
                //// smsMap = (HashMap<String, String>) smsUmsService.insertMmsSendInfo(smsMap);
                intSendReturn = smsUmsService.sendSmsMessage(smsMap);
            } else if (intMsgBodyLen >= 2001) {
                intSendReturn = -9998;
            } else {
                smsMap.put("MSG_TITLE", gstrMsgTitle);
                intSendReturn = smsUmsService.sendSmsMessage(smsMap);
            }

            log.info("최종 전송 내용-------------------------------시작");
            log.info(smsMap.toString());
            log.info("Message Length = " + intMsgBodyLen);
            log.info("intSendReturn =  " + intSendReturn);
            log.info("최종 전송 내용-------------------------------끝");

            if (intSendReturn == -9998) {

                log.info("intSendReturn == -9998");

                reMap.put("DK_CODE", "-4");
                reMap.put("DK_MSG", "LMS(장문) 메시지의 최대 길이 2,000Bytes를 초과하였습니다");
                reMap.put("DE_CODE", "-4");
                reMap.put("DE_MSG", "Maximum length of LMS message exceeded 2,000 bytes");
                throw new MyException(reMap.toString(), -2);

            } else {

                log.info("not intSendReturn == -9998");

                if (intSendReturn >= 1) {

                    reMap.put("DK_CODE", "0");
                    reMap.put("DK_MSG", "성공");
                    reMap.put("DE_CODE", "0");
                    reMap.put("DE_MSG", "Success");

                } else {

                    reMap.put("DK_CODE", "-9");
                    reMap.put("DK_MSG", "문자 메시지 전송에 실패했습니다");
                    reMap.put("DE_CODE", "-9");
                    reMap.put("DE_MSG", "Failed to send text message");
                    throw new MyException(reMap.toString(), -2);

                }
            }

        } catch (Exception e) {

            reMap.put("DK_CODE", "-9");
            reMap.put("DK_MSG", e.getMessage());
            reMap.put("DE_CODE", "-9");
            reMap.put("DE_MSG", e.getMessage());
            throw new MyException(reMap.toString(), -2);

        }

        return reMap;

    }

    public void buildSmsBody(Map<String, String> paramMap) throws Exception {

        String msgBody = getGstrMsgBody();
        for (String key : paramMap.keySet()) {
            String val = EgovStringUtil.nullConvert(paramMap.get(key));

            msgBody = msgBody.replace("$" + key + "$", val);

            if (key.equals("rsvnName")) {
                msgBody = msgBody.replace(Constant.SMS_RSVN_NAME, val);
            } else if (key.equals("membName")) {
                msgBody = msgBody.replace(Constant.SMS_MEMB_NAME, val);
            } else if (key.equals("membEdtime")) {
                msgBody = msgBody.replace(Constant.SMS_MEMB_EDTIME, val);
            } else if (key.equals("useName")) {
                msgBody = msgBody.replace(Constant.SMS_USE_NAME, val);
            } else if (key.equals("rsvnNo")) {
                msgBody = msgBody.replace(Constant.SMS_RSVN_NO, val);
            } else if (key.equals("rsvnDt")) {
                msgBody = msgBody.replace(Constant.SMS_RSVN_DT, val);
            } else if (key.equals("rsvnPerson")) {
                msgBody = msgBody.replace(Constant.SMS_RSVN_PERSON, val);
            } else if (key.equals("evtName")) {
                msgBody = msgBody.replace(Constant.SMS_EVT_NAME, val);
            } else if (key.equals("evtPerson")) {
                msgBody = msgBody.replace(Constant.SMS_EVT_PERSON, val);
            } else if (key.equals("membName")) {
                msgBody = msgBody.replace(Constant.SMS_MEMB_NAME, val);
            } else if (key.equals("evtSttime")) {
                msgBody = msgBody.replace(Constant.SMS_EVT_STTIME, val);
            } else if (key.equals("evtEdtime")) {
                msgBody = msgBody.replace(Constant.SMS_EVT_EDTIME, val);
            } else if (key.equals("evtVewDe")) {
                msgBody = msgBody.replace(Constant.SMS_EVT_VEW_DE, val);
            } else if (key.equals("exbtName")) {
                msgBody = msgBody.replace(Constant.SMS_EXBT_NAME, val);
            } else if (key.equals("exbtHallName")) {
                msgBody = msgBody.replace(Constant.SMS_EXBT_HALL_NAME, val);
            } else if (key.equals("exbtPayEdtime")) {
                msgBody = msgBody.replace(Constant.SMS_EXBT_PAY_EDTIME, val);
            } else if (key.equals("exbtVewDt")) {
                msgBody = msgBody.replace(Constant.SMS_EXBT_VEW_DT, val);
            } else if (key.equals("edcName")) {
                msgBody = msgBody.replace(Constant.SMS_EDC_NAME, val);
            } else if (key.equals("edcDt")) {
                msgBody = msgBody.replace(Constant.SMS_EDC_DT, val);
            } else if (key.equals("edcTime")) {
                msgBody = msgBody.replace(Constant.SMS_EDC_TIME, val);
            } else if (key.equals("edcDay")) {
                msgBody = msgBody.replace(Constant.SMS_EDC_DAY, val);
            } else if (key.equals("edcPlace")) {
                msgBody = msgBody.replace(Constant.SMS_EDC_PLACE, val);
            } else if (key.equals("telno")) {
                msgBody = msgBody.replace(Constant.SMS_TELNO, val);
            } else if (key.equals("cancelAmnt")) {
                msgBody = msgBody.replace(Constant.SMS_CANCEL_AMNT, val);
            } else if (key.equals("refndInfo")) {
                msgBody = msgBody.replace(Constant.SMS_REFND_INFO, val);
            } else if (key.equals("payAmnt")) {
                msgBody = msgBody.replace(Constant.SMS_PAY_AMNT, val);
            } else if (key.equals("url")) {
                msgBody = msgBody.replace(Constant.SMS_URL, val);
            } else if (key.equals("cpnName")) {
                msgBody = msgBody.replace(Constant.SMS_CPN_NAME, val);
            } else if (key.equals("ticketNo")) {
                msgBody = msgBody.replace(Constant.SMS_TICKET_NO, val);
            } else if (key.equals("ticketUrl")) {
                msgBody = msgBody.replace(Constant.SMS_TICKET_URL, val);
            }
        }

        log.debug("msgBody" + msgBody);
        setMsgBody(msgBody);

    }
}
