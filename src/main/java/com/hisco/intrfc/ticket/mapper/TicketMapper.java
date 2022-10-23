package com.hisco.intrfc.ticket.mapper;

import java.util.Map;

import com.hisco.cmm.object.CamelMap;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

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
@Mapper("ticketMapper")
public interface TicketMapper {

    public CamelMap selectExbtTicketDetail(Map<String, Object> paramMap);

    public CamelMap selectEvtTicketDetail(Map<String, Object> paramMap);

}
