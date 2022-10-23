package com.hisco.admin.sms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.sms.mapper.SmsTemplateMapper;
import com.hisco.admin.sms.vo.SmsTemplateVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("smsTemplateService")
public class SmsTemplateService extends EgovAbstractServiceImpl {

    @Resource(name = "smsTemplateMapper")
    private SmsTemplateMapper smsTemplateMapper;

    public List<?> selectTemplateList(SmsTemplateVO vo) {
        return smsTemplateMapper.selectTemplateList(vo);
    }

    public SmsTemplateVO selectTemplateDetail(SmsTemplateVO vo) {
        return smsTemplateMapper.selectTemplateDetail(vo);
    }

    public int insertTemplate(SmsTemplateVO vo) {

        vo.setMsgcd(1);
        SmsTemplateVO grpVO = smsTemplateMapper.selectTemplateGroup(vo);

        // 메시지 그룹 데이타가 있으면
        if (grpVO != null && grpVO.getMsgcd() > 0) {
        	log.debug("잘되라~~~~~~~~~2");
            vo.setMsgcd(grpVO.getMsgcd());
        } else {
            // 메시지 그룹 데이타가 없으면 생성
        	log.debug("잘되라~~~~~~~~~3");
        	grpVO = new SmsTemplateVO();
            grpVO.setComcd(vo.getComcd());
            grpVO.setReguser(vo.getReguser());
            grpVO.setMsgnm("온라인 자동발송 메시지");

            smsTemplateMapper.insertTemplateGroup(grpVO);
            vo.setMsgcd(grpVO.getMsgcd());
        }

        return smsTemplateMapper.insertTemplate(vo);
    }

    public int updateTemplate(SmsTemplateVO vo) {
        return smsTemplateMapper.updateTemplate(vo);
    }

    public int deleteTemplate(SmsTemplateVO vo) {
        return smsTemplateMapper.deleteTemplate(vo);
    }

    public int insertTemplateGroup(SmsTemplateVO vo) {
        return smsTemplateMapper.insertTemplateGroup(vo);
    }

    public SmsTemplateVO selectTemplateGroup(SmsTemplateVO vo) {
        return smsTemplateMapper.selectTemplateGroup(vo);
    }
}
