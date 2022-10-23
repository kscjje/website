package com.hisco.intrfc.pubdata.web;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.cmm.util.Config;
import com.hisco.intrfc.pubdata.service.PubDataService;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

// import kr.co.smartguru.apim.gateway.util.APIMGatewayUtil; 2021.06.06.06

/**
 * 공공 데이터 연계 컨트롤러
 * 
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.05 최초작성
 */
@Slf4j
@Controller
public class PubDataController {

    @Resource(name = "pubDataService")
    private PubDataService pubDataService;

    //// @Resource(name = "FileMngUtil")
    //// private FileMngUtil fileUtil;

    //// @Resource(name = "FileMngService")
    //// private FileMngService fileMngService;

    /**
     * 공공 데이터 연계 송신
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = Config.INTRFC_ROOT + "/pubdata/getPubDataold", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView getPubDataOld(HttpServletRequest request, @RequestParam Map<String, Object> paramMap)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        // log.debug("call getPubData");
        // log.debug(paramMap);

        String strGlobalCd = "1";
        String strGlobalMsg = "Success";

        String strDataCd = "";
        String strDataMsg = "";

        JSONObject rsltCdJsonObj = new JSONObject();
        JSONObject dataJsonObj = new JSONObject();

        String strEduevtSe = String.valueOf(paramMap.get("eduevtSe"));
        String strEduevtDate = String.valueOf(paramMap.get("eduevtDate"));

        try {

            if (strEduevtSe == null)
                strEduevtSe = "";
            if (strEduevtDate == null)
                strEduevtDate = "";

            strEduevtSe = strEduevtSe.replace("null", "");
            strEduevtDate = strEduevtDate.replace("null", "");

            strEduevtSe = strEduevtSe.trim();
            strEduevtDate = strEduevtDate.trim();

            strEduevtSe = URLDecoder.decode(strEduevtSe, "UTF-8");
            strEduevtDate = URLDecoder.decode(strEduevtDate, "UTF-8");

            if (("".equals(strEduevtSe)) || ("null".equals(strEduevtSe)) || ("".equals(strEduevtDate)) || ("null".equals(strEduevtDate))) {

                strEduevtDate = strEduevtDate.replace("-", "");
                strEduevtDate = strEduevtDate.replace("/", "");

                strGlobalCd = "-1";
                strGlobalMsg = "요청 조건이 맞지 않습니다. ex)getPubData?eduevtSe=ED&eduevtDate=YYYYMMDD";

                /**
                 * ED : 교육
                 * EV : 강연/행사/영화
                 */

            } else {

                strDataCd = "1";
                strDataMsg = "Success";

                strGlobalCd = "1";
                strGlobalMsg = "Success";

                // log.debug("eduevtSe = " + strEduevtSe);
                // log.debug("eduevtDate = " + strEduevtDate);

                paramMap.put("eduevtSe", strEduevtSe);
                paramMap.put("eduevtDate", strEduevtDate);
                paramMap.put("comCd", Config.COM_CD);

                if ("ED".equals(strEduevtSe)) {

                    List<?> lstRsvnInfoResult = pubDataService.selectEdcRsvnInfoCnt(paramMap);

                    // log.debug("lstRsvnInfoResult = " + lstRsvnInfoResult);

                    if (lstRsvnInfoResult.size() >= 1) {

                        dataJsonObj.put("RAWDATA", lstRsvnInfoResult);

                    } else {

                        dataJsonObj.put("RAWDATA", "");

                        strDataCd = "-1";
                        strDataMsg = "요청된 정보를 찾을 수 없습니다.";
                    }

                } else if ("EV".equals(strEduevtSe)) {

                    List<?> lstRsvnInfoResult = pubDataService.selectEvtRsvnInfoCnt(paramMap);

                    // log.debug("lstRsvnInfoResult = " + lstRsvnInfoResult);

                    if (lstRsvnInfoResult.size() >= 1) {

                        dataJsonObj.put("RAWDATA", lstRsvnInfoResult);

                    } else {

                        dataJsonObj.put("RAWDATA", "");

                        strDataCd = "-1";
                        strDataMsg = "요청된 정보를 찾을 수 없습니다.";
                    }

                }

            }

        } catch (Exception e) {

            strGlobalCd = "-2";
            strGlobalMsg = e.getMessage();

        }

        rsltCdJsonObj.put("EDUEVT_SE", strEduevtSe);
        rsltCdJsonObj.put("EDUEVT_DATE", strEduevtDate);

        rsltCdJsonObj.put("CODE", strGlobalCd);
        rsltCdJsonObj.put("MSG", strGlobalMsg);

        dataJsonObj.put("CODE", strDataCd);
        dataJsonObj.put("MSG", strDataMsg);

        rsltCdJsonObj.put("DATA", dataJsonObj);

        // log.debug("-----------------------S");
        // log.debug("return => " + rsltCdJsonObj);
        // log.debug("-----------------------E");

        mav.addObject("RESULT", rsltCdJsonObj);

        return mav;
    }

    /**
     * 공공 데이터 연계 송신
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/pubdata/getPubData", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView getPubData(HttpServletRequest request, @RequestParam Map<String, Object> paramMap)
            throws Exception {

        if (log.isDebugEnabled()) {
            log.debug("---------------------------------------------------------------------------------");
            log.debug("call getPubData");
            log.debug("---------------------------------------------------------------------------------");
            log.debug(" paramMap = {}", paramMap);
            log.debug("---------------------------------------------------------------------------------");
        }

        ModelAndView mav = new ModelAndView("jsonView");

        String strGlobalCd = "1";
        String strGlobalMsg = "Success";

        String strDataCd = "1";
        String strDataMsg = "Success";

        Boolean boolValidUrl = true;
        Boolean boolCertiVal = true;

        JSONObject rsltCdJsonObj = new JSONObject();
        JSONObject dataJsonObj = new JSONObject();

        String strSG_APIM = String.valueOf(paramMap.get("SG_APIM"));
        String strScrSe = String.valueOf(paramMap.get("scrSe"));
        String strNumOfRows = String.valueOf(paramMap.get("numOfRows"));
        String strPageNo = String.valueOf(paramMap.get("pageNo"));

        log.debug("strSG_APIM   = " + strSG_APIM);
        log.debug("strScrSe     = " + strScrSe);
        log.debug("strNumOfRows = " + strNumOfRows);
        log.debug("strPageNo    = " + strPageNo);

        try {

            if (strSG_APIM == null)
                strSG_APIM = "";
            if (strScrSe == null)
                strScrSe = "";
            if (strNumOfRows == null)
                strNumOfRows = "";
            if (strPageNo == null)
                strPageNo = "";

            strSG_APIM = strSG_APIM.replace("null", "");
            strSG_APIM = strSG_APIM.trim();

            strScrSe = strScrSe.replace("null", "");
            strScrSe = strScrSe.trim();

            strNumOfRows = strNumOfRows.replace("null", "");
            strNumOfRows = strNumOfRows.trim();

            strPageNo = strPageNo.replace("null", "");
            strPageNo = strPageNo.trim();

            if ("".equals(strSG_APIM)) {

                strGlobalCd = "-1";
                strGlobalMsg = "요청 조건이 맞지 않습니다. ex)URL?serviceKey=인증키&scrSe=업무구분&numOfRows=페이당표출건수&pageNo=페이지번호";

                strDataCd = "-2";
                strDataMsg = "System Issue";

                boolValidUrl = false;

            } else if ("".equals(strScrSe)) {

                strGlobalCd = "-1";
                strGlobalMsg = "요청 조건이 맞지 않습니다. ex)URL?serviceKey=인증키&scrSe=업무구분&numOfRows=페이당표출건수&pageNo=페이지번호";

                strDataCd = "-2";
                strDataMsg = "System Issue";

                boolValidUrl = false;

            } else if ("".equals(strNumOfRows)) {

                strGlobalCd = "-1";
                strGlobalMsg = "요청 조건이 맞지 않습니다. ex)URL?serviceKey=인증키&scrSe=업무구분&numOfRows=페이당표출건수&pageNo=페이지번호";

                /**
                 * EXBT : 관람
                 * EDC : 교육
                 * EVT : 강연/행사/영화
                 */

                strDataCd = "-2";
                strDataMsg = "System Issue";

                boolValidUrl = false;

            } else if ("".equals(strPageNo)) {

                strGlobalCd = "-1";
                strGlobalMsg = "요청 조건이 맞지 않습니다. ex)URL?serviceKey=인증키&scrSe=업무구분&numOfRows=페이당표출건수&pageNo=페이지번호";

                strDataCd = "-2";
                strDataMsg = "System Issue";

                boolValidUrl = false;

            }

            strSG_APIM = URLDecoder.decode(strSG_APIM, "UTF-8");

            int intKeyRslt = -1;
            if ("".equals(strSG_APIM)) {
            } else {

                /*
                 * 2021.06.06 JYS
                 * intKeyRslt = APIMGatewayUtil.SG_APIM_Check(strSG_APIM);
                 */

                if (intKeyRslt == 1) {
                    boolCertiVal = true;
                } else {
                    boolCertiVal = false;

                    strGlobalCd = "30";
                    strGlobalMsg = "등록되지 않은 서비스 키";

                    strDataCd = "-2";
                    strDataMsg = "System Issue";
                }

            }

            log.debug("boolValidUrl = " + boolValidUrl);
            log.debug("boolCertiVal = " + boolCertiVal);

            if (boolValidUrl && boolCertiVal) {

                strDataCd = "1";
                strDataMsg = "Success";

                strGlobalCd = "1";
                strGlobalMsg = "Success";

                // log.debug("strScrSe = " + strScrSe);

                paramMap.put("eduevtSe", strScrSe);
                paramMap.put("comCd", Config.COM_CD);

                int intPageIndex = (Integer.parseInt(strPageNo) - 1) * Integer.parseInt(strNumOfRows);

                paramMap.put("pageIndex", intPageIndex);
                paramMap.put("numOfRows", Integer.parseInt(strNumOfRows));

                if ("EDC".equals(strScrSe)) {

                    List<?> lstRsvnInfoResult = pubDataService.selectEdcarsvnList(paramMap);

                    // log.debug("lstRsvnInfoResult = " + lstRsvnInfoResult);

                    if (lstRsvnInfoResult.size() >= 1) {

                        dataJsonObj.put("RAWDATA", lstRsvnInfoResult);

                    } else {

                        dataJsonObj.put("RAWDATA", "");

                        strDataCd = "-3";
                        strDataMsg = "요청된 정보를 찾을 수 없습니다.";
                    }

                } else if ("EVT".equals(strScrSe)) {

                    List<?> lstRsvnInfoResult = pubDataService.selectEvtRsvnList(paramMap);

                    // log.debug("lstRsvnInfoResult = " + lstRsvnInfoResult);

                    if (lstRsvnInfoResult.size() >= 1) {

                        dataJsonObj.put("RAWDATA", lstRsvnInfoResult);

                    } else {

                        dataJsonObj.put("RAWDATA", "");

                        strDataCd = "-3";
                        strDataMsg = "요청된 정보를 찾을 수 없습니다.";
                    }

                } else if ("EXBT".equals(strScrSe)) {

                    List<?> lstRsvnInfoResult = pubDataService.selectExbtRsvnList(paramMap);

                    // log.debug("lstRsvnInfoResult = " + lstRsvnInfoResult);

                    if (lstRsvnInfoResult.size() >= 1) {

                        dataJsonObj.put("RAWDATA", lstRsvnInfoResult);

                    } else {

                        dataJsonObj.put("RAWDATA", "");

                        strDataCd = "-3";
                        strDataMsg = "요청된 정보를 찾을 수 없습니다.";
                    }

                }

            } else {

                strDataCd = "-2";
                strDataMsg = "System Issue";

            }

        } catch (Exception e) {

            strGlobalCd = "-2";
            strGlobalMsg = e.getMessage();

            strDataCd = "-2";
            strDataMsg = "System Issue";

        }

        rsltCdJsonObj.put("SCR_SE", strScrSe);

        rsltCdJsonObj.put("CODE", strGlobalCd);
        rsltCdJsonObj.put("MSG", strGlobalMsg);

        dataJsonObj.put("CODE", strDataCd);
        dataJsonObj.put("MSG", strDataMsg);

        rsltCdJsonObj.put("DATA", dataJsonObj);

        // log.debug("-----------------------S");
        // log.debug("return => " + rsltCdJsonObj);
        // log.debug("-----------------------E");

        mav.addObject("RESULT", rsltCdJsonObj);

        return mav;
    }

}
