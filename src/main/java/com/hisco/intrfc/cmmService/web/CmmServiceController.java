package com.hisco.intrfc.cmmService.web;

import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.cmm.util.Config;
import com.hisco.intrfc.cmmService.service.CmmServiceService;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.sim.service.EgovFileScrty;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * 공통 연계 서비스
 * 
 * @author 전영석
 * @since 2020.11.03
 * @version 1.0, 2020.11.03
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.11.03 최초작성
 */
@Slf4j
@Controller
public class CmmServiceController {

    @Resource(name = "cmmServiceService")
    private CmmServiceService cmmServiceService;

    /**
     * 비밀번호 이중 암호화 (Old)
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/string/getStrEncryOld", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView getStrEncryOld(HttpServletRequest request, @RequestParam Map<String, Object> paramMap)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        log.info("call getStrEncry");

        String strGlobalCd = "1";
        String strGlobalMsg = "Success";

        JSONObject rsltCdJsonObj = new JSONObject();

        String strReturnValue = "";

        String strId = String.valueOf(paramMap.get("id"));
        String strEncPw = String.valueOf(paramMap.get("encPw"));

        try {

            if (("".equals(strId)) || ("null".equals(strId)) || ("".equals(strEncPw)) || ("null".equals(strEncPw))) {

                strGlobalCd = "-1";
                strGlobalMsg = "요청 조건이 맞지 않습니다. ex)getStrEncry?id=ID&encPw=EncPw";

            } else {

                strGlobalCd = "1";
                strGlobalMsg = "Success";

                byte[] hashValue = null;

                MessageDigest md = MessageDigest.getInstance("SHA-256");

                md.reset();
                md.update(strId.getBytes());

                hashValue = md.digest(strEncPw.getBytes());

                strReturnValue = new String(Base64.encodeBase64(hashValue));
            }

        } catch (Exception e) {

            strGlobalCd = "-2";
            strGlobalMsg = e.getMessage();

        }

        // rsltCdJsonObj.put("CODE", strGlobalCd);
        // rsltCdJsonObj.put("MSG", strGlobalMsg);
        // rsltCdJsonObj.put("VALUE", strReturnValue);

        // mav.addObject("RESULT", rsltCdJsonObj);

        mav.addObject("CODE", strGlobalCd);
        mav.addObject("MSG", strGlobalMsg);
        mav.addObject("VALUE", strReturnValue);

        log.info("-----------------------S");
        log.info("return => " + mav);
        log.info("-----------------------E");

        return mav;
    }

    /**
     * 비밀번호 이중 암호화
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/webadm/upload/encry/getStrEncry", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getStrEncry(HttpServletRequest request, @RequestParam Map<String, Object> paramMap) throws Exception {

        log.info("call getStrEncry");

        String strGlobalCd = "1";
        String strGlobalMsg = "Success";

        String strReturnValue = "";

        String strId = String.valueOf(paramMap.get("id"));

        try {

            if (("".equals(strId)) || ("null".equals(strId))) {

                strGlobalCd = "-1";
                strGlobalMsg = "요청 조건이 맞지 않습니다. ex)getStrEncry?id=ID";

            } else {

                strGlobalCd = "1";
                strGlobalMsg = "Success";

                String strDbEncKey = EgovProperties.getProperty("Globals.DbEncKey");

                paramMap.put("DbEncKey", strDbEncKey);

                List<?> result = cmmServiceService.selectUserInfor(paramMap);

                if (result.size() >= 1) {
                    Map<String, Object> resultMap = (Map<String, Object>) result.get(0);

                    String strHP = String.valueOf(resultMap.get("hp"));
                    // 암호화
                    strReturnValue = EgovFileScrty.encryptPassword(strHP, strId);

                } else {
                    strReturnValue = "ERROR";
                }
            }

        } catch (Exception e) {

            strGlobalCd = "-2";
            strGlobalMsg = e.getMessage();

        }

        log.info("strGlobalMsg = " + strGlobalMsg);

        if (!"1".equals(strGlobalCd)) {
            strReturnValue = "ERROR";
        }

        return strReturnValue;
    }

}
