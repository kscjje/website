package com.hisco.intrfc.sms.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hisco.cmm.exception.MyException;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.SmsUtil;
import com.hisco.intrfc.sms.mapper.SmsMapper;
import com.hisco.intrfc.sms.mapper.SmsUmsMapper;
import com.hisco.intrfc.sms.vo.SmsVO;

import egovframework.com.cmm.LoginVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * Sms 송신 처리
 * 
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.20
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.20 최초작성
 */
@Slf4j
@Service("smsUmsService")
public class SmsUmsService extends EgovAbstractServiceImpl {

    @Resource(name = "smsMapper")
    private SmsMapper smsMapper;

    @Resource(name = "smsUmsMapper")
    private SmsUmsMapper smsUmsMapper;

    @Autowired
    private SmsUtil smsUtil;

    public int sendSmsMessage(Map<String, String> paramMap) throws Exception {
        // return smsUmsMapper.sendSmsMessage(paramMap);
        // TODO: sql 미존재
        return 1;
    }

    public int sendMessage(Map<String, String> paramMap, LoginVO userVO) throws Exception {

        log.debug("sendMessage :: paramMap = " + paramMap);

        Map<String, String> returnMap = new HashMap<String, String>();
        int resultNum = 0;

        paramMap.put("comcd", Config.COM_CD);
        SmsVO vo = smsMapper.selectMsgTemplate(paramMap);

        smsUtil.setMsgBody(vo.getSendMsg());
        smsUtil.setMsgTitle(vo.getMsgName());

        String sendTelno = paramMap.get("sndHp");

        if (sendTelno != null && sendTelno.indexOf(",") > 0) {
            sendTelno = sendTelno.split(",")[0];
        }

        /*
         * if (sendTelno == null || sendTelno.equals("")) {
         * sendTelno = vo.getSendTelno();
         * }
         */
        if (sendTelno == null || sendTelno.equals("")) {
            sendTelno = "02-2116-2181";
        }

        sendTelno = sendTelno.trim().replaceAll("-", "");
        smsUtil.setSndPhoneNum(sendTelno);
        smsUtil.setRcvPhoneNum(paramMap.get("hp"));
        smsUtil.setMsgcd(vo.getMsgcd());
        smsUtil.buildSmsBody(paramMap);
        if (userVO != null && userVO.getUniqId() != null) {
            smsUtil.setSendUser(userVO.getName() + "(" + userVO.getUniqId() + ")");
        } else {
            smsUtil.setSendUser(userVO.getName());
        }
        returnMap = smsUtil.sendMessage();
        if (returnMap.size() >= 1) {

            String strCODE = CommonUtil.ClearStrNull2Space(returnMap.get("DK_CODE"));

            if ("0".equals(strCODE)) {
                resultNum = 1;
            } else if ("-9999".equals(strCODE)) {
                resultNum = -1;
                throw new MyException(returnMap.toString(), -2);
            } else {
                resultNum = -1;
                throw new MyException(returnMap.toString(), -2);
            }

        }

        return resultNum;
    }

}
