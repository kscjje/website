package com.hisco.intrfc.ticket.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.ScrEncDecUtil;
import com.hisco.intrfc.ticket.service.TicketService;

import egovframework.com.cmm.service.EgovProperties;

/*
 * 티켓 컨트롤러
 * @author 김희택
 * @since 2020.10.29
 * @version 1.0, 2020.10.29
 * ------------------------------------------------------------------------
 * 작성자 일자 내용
 * ------------------------------------------------------------------------
 * 김희택 2020.10.29 최초작성
 */

@Controller
public class TicketController {

    @Resource(name = "ticketService")
    private TicketService ticketService;

    //// @Resource(name = "FileMngUtil")
    //// private FileMngUtil fileUtil;

    //// @Resource(name = "FileMngService")
    //// private FileMngService fileMngService;

    /**
     * SMs 티켓 처리
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/ticket/{rsvnno}")
    public String showTicketPop(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @PathVariable(value = "rsvnno") String rsvnno) throws Exception {
        String strKey = EgovProperties.getProperty("Globals.Ticket.Key");
        String rsvnNum = ScrEncDecUtil.fn_decrypt_url(rsvnno, strKey);

        // String rsvnNum = rsvnno;
        commandMap.put("comcd", Config.COM_CD);
        commandMap.put("rsvnNum", rsvnNum);
        commandMap.put("dbEnckey", EgovProperties.getProperty("Globals.DbEncKey"));

        if (rsvnNum.startsWith("R1")) {
            // 관람
            model.addAttribute("ticket", ticketService.selectExbtTicket(commandMap.getParam()));
        } else if (rsvnNum.startsWith("R2")) {
            // 강연/행사/영화
            model.addAttribute("ticket", ticketService.selectEvtTicket(commandMap.getParam()));
        }

        return HttpUtility.getViewUrl(Config.INTRFC_ROOT, "/ticket/ticketPop");
    }

}
