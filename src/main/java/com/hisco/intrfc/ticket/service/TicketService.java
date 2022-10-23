package com.hisco.intrfc.ticket.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.intrfc.ticket.mapper.TicketMapper;
import com.hisco.user.evtrsvn.service.EvtRsvnItemVO;
import com.hisco.user.evtrsvn.service.EvtrsvnMstVO;
import com.hisco.user.exbtrsvn.service.ExbtChargeVO;
import com.hisco.user.exbtrsvn.service.RsvnMasterVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * Nice 연계 처리
 * 
 * @author 전영석
 * @since 2020.08.21
 * @version 1.0, 2020.08.21
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.21 최초작성
 */
@Service("ticketService")
public class TicketService extends EgovAbstractServiceImpl {

    @Resource(name = "commonDAO")
    private CommonDAO commonDAO;

    @Resource(name = "ticketMapper")
    private TicketMapper ticketMapper;

    public CamelMap selectExbtTicket(Map<String, Object> paramMap) throws Exception {

        CamelMap data = ticketMapper.selectExbtTicketDetail(paramMap);
        if (data != null) {

            RsvnMasterVO vo = new RsvnMasterVO();
            vo.setRsvnIdx(CommonUtil.getString(data.get("rsvnIdx")));
            vo.setComcd(Config.COM_CD);
            List<ExbtChargeVO> itemList = (List<ExbtChargeVO>) commonDAO.queryForList("DspyDsDAO.selectReserveItemList", vo);
            data.put("itemList", itemList);

        }

        return data;
    }

    public CamelMap selectEvtTicket(Map<String, Object> paramMap) throws Exception {
        CamelMap data = ticketMapper.selectEvtTicketDetail(paramMap);
        if (data != null) {
            EvtrsvnMstVO vo = new EvtrsvnMstVO();
            vo.setEvtRsvnIdx(CommonUtil.getString(data.get("rsvnIdx")));
            vo.setComcd(Config.COM_CD);
            List<EvtRsvnItemVO> itemList = (List<EvtRsvnItemVO>) commonDAO.queryForList("EvtrsvnSMainDAO.selectEvtrsvnItem", vo);
            data.put("itemList", itemList);
        }

        return data;
    }

}
