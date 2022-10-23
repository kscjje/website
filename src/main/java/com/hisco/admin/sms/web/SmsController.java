package com.hisco.admin.sms.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.SessionUtil;
import com.hisco.cmm.util.SmsUtil;
import com.hisco.intrfc.sms.service.BizMsgService;
import com.hisco.intrfc.sms.vo.BizMsgVO;
import com.hisco.intrfc.sms.vo.SmsTargetVO;
import com.hisco.intrfc.sms.vo.SmsVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : SmsController.java
 * @Description : SMS 발송
 * @author woojinp@legitsys.co.kr
 * @since 2021. 12. 3.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */

@Slf4j
@RequestMapping(value = { "#{dynamicConfig.adminRoot}/sms", "#{dynamicConfig.managerRoot}/sms" })
@Controller
public class SmsController {

    @Resource(name = "bizMsgService")
    private BizMsgService bizMsgService;

    @Autowired
    private SmsUtil smsUtil;

    /**
     * SMS 발송화면
     */
    @PostMapping("/smsLayerAjax")
    public String openNsmSms(@RequestBody SmsVO smsVO, HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap)
            throws Exception {

        return "intrfc/sms/smsMainForm";
    }

    /**
     * SMS 발송
     */
    @PostMapping("/sendSms.json")
    public String sendSms(@RequestBody SmsVO smsVO, CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        ResultInfo resultInfo = null;

        try {
            List<SmsTargetVO> smsTargetList = smsVO.getTargetList();
            String loginId = SessionUtil.getLoginId();
            log.debug("loginId = {}", loginId);

            int sendCnt = 0;

            for (SmsTargetVO targetVO : smsTargetList) {
                BizMsgVO bmVO = new BizMsgVO();
                bmVO.setDestPhone(targetVO.getMemhp());
                bmVO.setDestName(targetVO.getMemnm());
                bmVO.setSendPhone(smsVO.getSenderTelno());
                bmVO.setSendName(loginId);
                // bmVO.setSubject(subject);
                bmVO.setMsgBody(smsVO.getSendMsg());

                if (Config.YES.equals(smsVO.getRsvnSendYn())) {
                    if (StringUtils.isBlank(smsVO.getRsvnDtime()))
                        throw new RuntimeException("예약전송일시를 입력해 주세요.");

                    bmVO.setSendTime(smsVO.getRsvnDtime());
                }

                sendCnt += bizMsgService.sendMessage(bmVO);
            }

            if (Config.YES.equals(smsVO.getRsvnSendYn())) {
                resultInfo = new ResultInfo(Config.SUCCESS, sendCnt + "건을 예약전송 하였습니다.");
            } else {
                resultInfo = new ResultInfo(Config.SUCCESS, sendCnt + "건을 즉시전송 하였습니다.");
            }
        } catch (Exception ex) {
            resultInfo = new ResultInfo(Config.FAIL, ex.getMessage());
        }

        model.clear();
        model.addAttribute("result", resultInfo);

        return HttpUtility.getViewUrl(request);
    }
}