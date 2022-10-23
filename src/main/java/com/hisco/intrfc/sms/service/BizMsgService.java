package com.hisco.intrfc.sms.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hisco.cmm.util.SessionUtil;
import com.hisco.intrfc.sms.mapper.BizMsgMapper;
import com.hisco.intrfc.sms.vo.BizMsgType;
import com.hisco.intrfc.sms.vo.BizMsgVO;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("bizMsgService")
public class BizMsgService extends EgovAbstractServiceImpl {

    @Value("${bizclient.kakao.senderkey}")
    private String kakaoSenderKey;

    @Resource(name = "bizMsgMapper")
    private BizMsgMapper bizMsgMapper;

    public int sendMessage(BizMsgVO vo) throws Exception {
        String msg = vo.getMsgBody();

		vo.setDestPhone(vo.getDestPhone().replaceAll("-", ""));

        if (vo.getMsgType() == null) {
            if (msg.getBytes().length > 90) {
                vo.setMsgType(BizMsgType.LMS);
            } else {
                vo.setMsgType(BizMsgType.SMS);
            }
        }

        if (StringUtils.isBlank(vo.getSendName()))
            vo.setSendName(SessionUtil.getLoginId());

        return bizMsgMapper.sendMessage(vo);
    }

    public int sendMessage(List<BizMsgVO> voList) throws Exception {
        int sendCnt = 0;

        for (BizMsgVO vo : voList) {
            sendCnt += this.sendMessage(vo);
        }

        return sendCnt;
    }

    public int sendRsvnMessage(int edcRsvnReqid) throws Exception {
        EdcRsvnInfoVO vo = new EdcRsvnInfoVO();
        vo.setEdcRsvnReqid(edcRsvnReqid);
        vo.setKakaoSenderKey(kakaoSenderKey);
        return bizMsgMapper.sendRsvnMessage(vo);
    }

    public int sendRsvnMessage(String edcRsvnNo) throws Exception {
        EdcRsvnInfoVO vo = new EdcRsvnInfoVO();
        vo.setEdcRsvnNo(edcRsvnNo);
        vo.setKakaoSenderKey(kakaoSenderKey);
        return bizMsgMapper.sendRsvnMessage(vo);
    }

    public int sendRsvnMessage(EdcRsvnInfoVO vo) throws Exception {
        vo.setKakaoSenderKey(kakaoSenderKey);
        return bizMsgMapper.sendRsvnMessage(vo);
    }

    public int sendKakaoMessage(BizMsgVO vo) throws Exception {
        vo.setKakaoSenderKey(kakaoSenderKey);
        return bizMsgMapper.sendKakaoMessage(vo);
    }

    /* 예약마감 강좌 > 수강생 강의취소시 관리자에게 알림톡 발송 */
    public int sendMessageToAdmin(EdcRsvnInfoVO vo) throws Exception {
        vo.setKakaoSenderKey(kakaoSenderKey);
        return bizMsgMapper.sendMessageToAdmin(vo);
    }
}
