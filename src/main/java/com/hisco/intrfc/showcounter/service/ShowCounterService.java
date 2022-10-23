package com.hisco.intrfc.showcounter.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.showcounter.mapper.ShowCounterMapper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * EMail 송신 처리
 * 
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.05 최초작성
 */
@Service("showCounterService")
public class ShowCounterService extends EgovAbstractServiceImpl {

    @Resource(name = "showCounterMapper")
    private ShowCounterMapper showCounterMapper;

    public void showCounterMove(Map<String, Object> paramMap) throws Exception {
        showCounterMapper.showCounterMove(paramMap);
    }

}
