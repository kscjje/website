package com.hisco.intrfc.sale.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.sale.mapper.EdcRsvnComptinfoMapper;
import com.hisco.intrfc.sale.vo.EdcRsvnComptinfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : CancelService.java
 * @Description : 수강신청취소
 * @author woojinp@legitsys.co.kr
 * @since 2021. 12. 7.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Service("edcRsvnComptinfoService")
public class EdcRsvnComptinfoService extends EgovAbstractServiceImpl {

    @Resource(name = "edcRsvnComptinfoMapper")
    private EdcRsvnComptinfoMapper dcRsvnComptinfoMapper;

    public int insertEdcRsvnComptinfo(EdcRsvnComptinfoVO vo) throws Exception {
        return dcRsvnComptinfoMapper.insertEdcRsvnComptinfo(vo);
    }

    public int cancelEdcRsvnComptinfo(EdcRsvnComptinfoVO vo) throws Exception {
        return dcRsvnComptinfoMapper.cancelEdcRsvnComptinfo(vo);
    }

    public EdcRsvnComptinfoVO selectEdcRsvnComptinfo(EdcRsvnComptinfoVO vo) throws Exception {
        return dcRsvnComptinfoMapper.selectEdcRsvnComptinfo(vo);
    }

    public List<EdcRsvnComptinfoVO> selectEdcRsvnComptinfoList(EdcRsvnComptinfoVO vo) throws Exception {
        return dcRsvnComptinfoMapper.selectEdcRsvnComptinfoList(vo);
    }

}
