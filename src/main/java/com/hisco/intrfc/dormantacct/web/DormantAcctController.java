package com.hisco.intrfc.dormantacct.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hisco.cmm.util.Config;
import com.hisco.intrfc.dormantacct.service.DormantAcctService;

/**
 * 설문 컨트롤러
 * 
 * @author 전영석
 * @since 2020.09.09
 * @version 1.0, 2020.09.09
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.09.09 최초작성
 */
@Controller
public class DormantAcctController {

    @Resource(name = "dormantAcctService")
    private DormantAcctService dormantAcctService;

    /**
     * 휴면계정 전환 처리 관련 정보를 조회한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.USER_ROOT + "/dormantacct/member/dormantAcctExeList", method = { RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String selectDormantAcctExeList(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap) throws Exception {

        // log.debug("call selectDormAntacctExeList");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        List<?> lstResult = dormantAcctService.selectDormantAcctExeList(paramMap);

        // log.debug(lstResult.toString());

        model.addAttribute("dormantAcctExeList", lstResult);

        return "/web/dormantacct/member/dormantAcctExeList";
    }

}
