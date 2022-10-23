package com.hisco.intrfc.email.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.EMailUtil;
import com.hisco.intrfc.email.service.EMailService;

/*
 * Email 송신 처리 컨트롤러
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 * ------------------------------------------------------------------------
 * 작성자 일자 내용
 * ------------------------------------------------------------------------
 * 전영석 2020.08.05 최초작성
 */

@Controller
public class EMailController {

    @Resource(name = "eMailService")
    private EMailService eMailService;

    //// @Resource(name = "FileMngUtil")
    ///// private FileMngUtil fileUtil;

    ///// @Resource(name = "FileMngService")
    ///// private FileMngService fileMngService;

    /**
     * Open 결제 요청 화면
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/email/openNsmEMail", method = { RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String openNsmEMail(HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> paramMap)
            throws Exception {

        // log.debug("call openNsmEMail");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        return "intrfc/email/emailMainForm";
    }

    /**
     * EMail 송신
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/email/eMailSend", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView eMailSend(MultipartHttpServletRequest mtfRequest, @RequestParam Map<String, Object> paramMap,
            CommandMap commandMap) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        // log.debug("call webMailSend");
        // log.debug(paramMap);

        // 일반사용자용
        // LoginVO loginUser = commandMap.getUserInfo();
        // if (loginUser == null) {
        // throw new MyException("로그인 정보를 찾을 수 없습니다.", -3);
        // }

        Map<String, Object> finalResult = new HashMap<String, Object>();

        EMailUtil webEMailUtil = new EMailUtil();

        List<MultipartFile> lstMf = mtfRequest.getFiles("MAIL_FILE");
        webEMailUtil.sendToEmail(paramMap, lstMf);

        finalResult.put("EXE_YN", "Y");
        finalResult.put("DB_DATA", "{DB_YN : Y}");
        finalResult.put("DB_DROW", 1);

        // log.debug(finalResult);

        mav.addObject("RESULT", finalResult);

        return mav;
    }

    /**
     * Procedure Test
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/proctest/callProc", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView callProc(@RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        // log.debug("call callProc");
        // log.debug(paramMap);

        eMailService.selectSeokLogProcedure(paramMap);

        return mav;
    }
}
