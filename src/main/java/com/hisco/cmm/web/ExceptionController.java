package com.hisco.cmm.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Exception 컨트롤러
 *
 * @author 전영석
 * @since 2021.06.23
 * @version 1.0, 2021.06.23
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.06.23 최초작성
 */

import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/exception/system")
public class ExceptionController {

    /**
     * System Exception
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/errorPage")
    public String openExceptionSystemU(HttpServletRequest request, HttpServletResponse response, ModelMap model,
            CommandMap commandMap, Exception ex) throws Exception {

        if (log.isDebugEnabled()) {
            log.debug("response status = {}", response.getStatus());
            log.debug("requestUri = {}", request.getRequestURI());
        }

        model.addAttribute("status", response.getStatus());
        return HttpUtility.getViewUrl(request);
    }

    /**
     * 403 Exception
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/accessDenied")
    public String accessDeniedExceptionSystemU(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap, Exception e) throws Exception {

        log.debug("call accessDeniedExceptionSystemU");
        return HttpUtility.getViewUrl(request);
    }

}