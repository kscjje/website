package com.hisco.intrfc.sms.mapper;

import com.hisco.intrfc.sms.vo.BizMsgVO;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bizMsgMapper")
public interface BizMsgMapper {

    public int sendMessage(BizMsgVO vo);

    public int sendRsvnMessage(EdcRsvnInfoVO vo);

    public int sendKakaoMessage(BizMsgVO vo);
    
    public int sendMessageToAdmin(EdcRsvnInfoVO vo);

}
