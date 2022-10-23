package com.hisco.admin.sms.mapper;

import java.util.List;

import com.hisco.admin.sms.vo.SmsTemplateVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("smsTemplateMapper")
public interface SmsTemplateMapper {

    public List<?> selectTemplateList(SmsTemplateVO vo);

    public SmsTemplateVO selectTemplateDetail(SmsTemplateVO vo);

    public int insertTemplate(SmsTemplateVO vo);

    public int updateTemplate(SmsTemplateVO vo);

    public int deleteTemplate(SmsTemplateVO vo);

    public int insertTemplateGroup(SmsTemplateVO vo);

    public SmsTemplateVO selectTemplateGroup(SmsTemplateVO vo);
}
