package com.hisco.intrfc.parkng.web;

import java.net.URLDecoder;
import java.util.HashMap;
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

//// import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.intrfc.parkng.service.ParkngService;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * Parking 연계 컨트롤러
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
public class ParkngController {

    @Resource(name = "parkngService")
    private ParkngService parkngService;

    //// @Resource(name = "FileMngUtil")
    //// private FileMngUtil fileUtil;

    //// @Resource(name = "FileMngService")
    //// private FileMngService fileMngService;

    /**
     * Parking 연계 송신
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = Config.INTRFC_ROOT + "/parkng/getMemberInfor", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView getMemberInfor(HttpServletRequest request, @RequestParam Map<String, Object> paramMap)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        // log.debug("call getMemberInfor");
        // log.debug(paramMap);

        String strGlobalCd = "1";
        String strGlobalMsg = "Success";

        String strDataCd = "1";
        String strDataMsg = "Success";

        List<String> causeList = new java.util.ArrayList<String>();
        Map<String, Object> logParamMap = new HashMap<String, Object>();

        logParamMap.put("logParamMap", String.valueOf(paramMap));

        String strMEMYEARYN = "N";
        String strMEMEXBTYN = "N";
        String strMEMCAPAYN = "N";
        String strMEMEVTYN = "N";
        String strMEMSPECLYN = "N";

        JSONObject rsltCdJsonObj = new JSONObject();
        JSONObject dataJsonObj = new JSONObject();

        String strVhcleNo = String.valueOf(paramMap.get("vhcleNo"));
        String strReqDate = String.valueOf(paramMap.get("reqDate"));

        try {

            if (strVhcleNo == null)
                strVhcleNo = "";
            if (strReqDate == null)
                strReqDate = "";

            strVhcleNo = strVhcleNo.replace("null", "");
            strReqDate = strReqDate.replace("null", "");

            strVhcleNo = strVhcleNo.trim();
            strReqDate = strReqDate.trim();

            strVhcleNo = URLDecoder.decode(strVhcleNo, "UTF-8");
            strReqDate = URLDecoder.decode(strReqDate, "UTF-8");

            if (("".equals(strVhcleNo)) || ("null".equals(strVhcleNo)) || ("".equals(strReqDate)) || ("null".equals(strReqDate))) {

                strReqDate = strReqDate.replace("-", "");
                strReqDate = strReqDate.replace("/", "");

                strGlobalCd = "-1";
                strGlobalMsg = "요청 조건이 맞지 않습니다. ex)getMemberInfor?vhcleNo=차량번호&reqDate=YYYYMMDD";

                strDataCd = "-5";
                strDataMsg = "System Issue";

            } else {

                strGlobalCd = "1";
                strGlobalMsg = "Success";

                strDataCd = "1";
                strDataMsg = "Success";

                // log.debug("strVhcleNo = " + strVhcleNo);
                // log.debug("strReqDate = " + strReqDate);

                paramMap.put("VHCLE_NO", strVhcleNo);
                paramMap.put("STARD_DATE", strReqDate);

                int intRowResult = parkngService.selectMemerRow(paramMap);

                if (intRowResult >= 1) {

                    List<?> freeEdcResult = parkngService.selectEdcInfor(paramMap);

                    // log.debug("freeEdcResult = " + freeEdcResult);

                    String strfreeEdcYn = "";
                    String strfreeEdcCause = "";

                    String strfreeSpeYn = "";
                    String strfreeSpeCause = "";

                    String strfreePayYn = "";
                    String strfreePayCause = "";

                    String strDaygbn = "";

                    String strWeekCRow = "";
                    int intWeekCRow = -1;

                    int intEdcRow = freeEdcResult.size();

                    log.debug("intEdcRow -------------------------- S. ");
                    log.debug("intEdcRow) => " + intEdcRow);
                    log.debug("intEdcRow -------------------------- E. ");

                    strfreeEdcYn = "N";

                    for (int k = 0; k < intEdcRow; k++) {

                        Map<String, Object> freeEdcMap = (Map<String, Object>) freeEdcResult.get(k);

                        strDaygbn = String.valueOf(freeEdcMap.get("daygbn"));
                        strWeekCRow = String.valueOf(freeEdcMap.get("weekCrow"));
                        // strfreeEdcYn = String.valueOf(freeEdcMap.get("freePrkYn"));
                        strfreeEdcCause = String.valueOf(freeEdcMap.get("freePrkCause"));

                        if (strDaygbn == null)
                            strDaygbn = "";
                        if ("null".equals(strDaygbn))
                            strDaygbn = "";

                        if (strfreeEdcCause == null)
                            strfreeEdcCause = "";
                        if ("null".equals(strfreeEdcCause))
                            strfreeEdcCause = "";

                        if (strWeekCRow == null)
                            strWeekCRow = "-1";
                        if ("null".equals(strWeekCRow))
                            strWeekCRow = "-1";

                        intWeekCRow = Integer.parseInt(strWeekCRow);

                        log.debug("strDaygbn   = " + strDaygbn);
                        log.debug("intWeekCRow = " + intWeekCRow);

                        if ((intWeekCRow >= 2) && (!("".equals(strDaygbn)))) {
                            strfreeEdcYn = "Y";
                            break;
                        } else if (intWeekCRow == 0) {
                            strfreeEdcYn = "Y";
                            break;
                        }

                    }

                    /*
                     * OLD : 20201203
                     * if (freeEdcResult.size() >= 1) {
                     * Map<String, Object> freeEdcMap = (Map<String, Object>)freeEdcResult.get(0);
                     * //log.debug("freeEdcMap = " + freeEdcMap);
                     * strfreeEdcYn = String.valueOf(freeEdcMap.get("freePrkYn"));
                     * strfreeEdcCause = String.valueOf(freeEdcMap.get("freePrkCause"));
                     * if (strfreeEdcYn == null) strfreeEdcYn = "";
                     * if ("null".equals(strfreeEdcYn)) strfreeEdcYn = "";
                     * if (strfreeEdcCause == null) strfreeEdcCause = "";
                     * if ("null".equals(strfreeEdcCause)) strfreeEdcCause = "";
                     * }
                     */

                    if ("N".equals(strfreeEdcYn)) {
                        strfreeEdcCause = "";
                    }

                    if ("Y".equals(strfreeEdcYn)) {
                        strMEMCAPAYN = "Y";
                        causeList.add(strfreeEdcCause);
                    } else {

                        List<?> freeSpeResult = parkngService.selectSpeInfor(paramMap);

                        // log.debug("freeSpeResult = " + freeSpeResult);

                        if (freeSpeResult.size() >= 1) {

                            Map<String, Object> freeSpeMap = (Map<String, Object>) freeSpeResult.get(0);

                            // log.debug("freeSpeMap = " + freeSpeMap);

                            strfreeSpeYn = String.valueOf(freeSpeMap.get("freePrkYn"));
                            strfreeSpeCause = String.valueOf(freeSpeMap.get("freePrkCause"));

                            if (strfreeSpeYn == null)
                                strfreeSpeYn = "";
                            if ("null".equals(strfreeSpeYn))
                                strfreeSpeYn = "";

                            if (strfreeSpeCause == null)
                                strfreeSpeCause = "";
                            if ("null".equals(strfreeSpeCause))
                                strfreeSpeCause = "";

                        }

                        if ("Y".equals(strfreeSpeYn)) {
                            strMEMSPECLYN = "Y";
                            causeList.add(strfreeSpeCause);
                        } else {
                            List<?> freePayResult = parkngService.selectPayInfor(paramMap);

                            // log.debug("freePayResult = " + freePayResult);

                            if (freePayResult.size() >= 1) {

                                Map<String, Object> freePayMap = (Map<String, Object>) freePayResult.get(0);

                                // log.debug("freePayMap = " + freePayMap);

                                strfreePayYn = String.valueOf(freePayMap.get("freePrkYn"));
                                strfreePayCause = String.valueOf(freePayMap.get("freePrkCause"));

                                if (strfreePayYn == null)
                                    strfreePayYn = "";
                                if ("null".equals(strfreePayYn))
                                    strfreePayYn = "";

                                if (strfreePayCause == null)
                                    strfreePayCause = "";
                                if ("null".equals(strfreePayCause))
                                    strfreePayCause = "";
                            }

                            if ("Y".equals(strfreePayYn)) {
                                strMEMYEARYN = "Y";
                                causeList.add(strfreePayCause);
                            }

                        }

                    }

                } else {

                    strDataCd = "-1";
                    strDataMsg = "차량에 등록된 회원 정보를 찾을 수 없습니다.";

                }

                /*
                 * log.debug("intRowResult = " + intRowResult);
                 * List<?> memberResult = parkngService.selectMemerNo(paramMap);
                 * log.debug("memberResult = " + memberResult);
                 * int intMemberResultRow = memberResult.size();
                 */
                /*
                 * if (memberResult.size() >= 1) {
                 * strGlobalCd = "1";
                 * strGlobalMsg = "Success";
                 * Map<String, Object> resultMap = (Map<String, Object>)memberResult.get(0);
                 * log.debug("resultMap :: 1 = " + resultMap);
                 * String strMEM_NO = String.valueOf(resultMap.get("memNo"));
                 * if (strMEM_NO == null) strMEM_NO = "";
                 * if ("null".equals(strMEM_NO)) strMEM_NO = "";
                 * if ("".equals(strMEM_NO)) {
                 * strDataCd = "-1";
                 * strDataMsg = "차량에 등록된 회원 정보를 찾을 수 없습니다.";
                 * } else {
                 * strDataCd = "1";
                 * strDataMsg = "Success";
                 * paramMap.put("MEM_NO", strMEM_NO);
                 * // 연회원여부 -----------------------------------------------------------------------> S.
                 * List<?> memberYearResult = parkngService.selectMemerYearInfor(paramMap);
                 * if (memberYearResult.size() >= 1) {
                 * Map<String, Object> resultYearMap = (Map<String, Object>)memberYearResult.get(0);
                 * log.debug("연회원여부 => ");
                 * log.debug(resultYearMap);
                 * String strROW_COUNT = String.valueOf(resultYearMap.get("rowCount"));
                 * if (strROW_COUNT == null) strROW_COUNT = "0";
                 * if ("null".equals(strROW_COUNT)) strROW_COUNT = "0";
                 * int intMEM_YEAR_YN = Integer.parseInt(strROW_COUNT);
                 * if (intMEM_YEAR_YN >= 1) {
                 * strMEM_YEAR_YN = "Y";
                 * causeList.add("연회원");
                 * } else {
                 * strMEM_YEAR_YN = "N";
                 * }
                 * }
                 * // 연회원여부 -----------------------------------------------------------------------> E.
                 * // 관람여부-----------------------------------------------------------------------> S.
                 * // 관람 사용 안함으로 변경 : 이사님 요청 사항 2020-10-27
                 * //
                 * // List<?> exbtRsvnResult = parkngService.selectExbtRsvnInfor(paramMap);
                 * // if (exbtRsvnResult.size() >= 1) {
                 * //
                 * // Map<String, Object> resultExbtRsvnMap = (Map<String, Object>)exbtRsvnResult.get(0);
                 * //
                 * // log.debug("관람여부 => ");
                 * // log.debug(resultExbtRsvnMap);
                 * //
                 * // String strROW_COUNT = String.valueOf(resultExbtRsvnMap.get("rowCount"));
                 * // if (strROW_COUNT == null) strROW_COUNT = "0";
                 * // if ("null".equals(strROW_COUNT)) strROW_COUNT = "0";
                 * //
                 * // int intMEM_EXBT_YN = Integer.parseInt(strROW_COUNT);
                 * // if (intMEM_EXBT_YN >= 1) {
                 * // strMEM_EXBT_YN = "Y";
                 * // } else {
                 * // strMEM_EXBT_YN = "N";
                 * // }
                 * //
                 * // }
                 * strMEM_EXBT_YN = "N";
                 * // 관람여부-----------------------------------------------------------------------> E.
                 * // 교육수강자여부---------------------------------------------------------------------> S.
                 * List<?> trainResult = parkngService.selectTrainInfor(paramMap);
                 * if (trainResult.size() >= 1) {
                 * Map<String, Object> resultTrainMap = (Map<String, Object>)trainResult.get(0);
                 * log.debug("교육수강자여부 => ");
                 * log.debug(resultTrainMap);
                 * String strROW_COUNT = String.valueOf(resultTrainMap.get("rowCount"));
                 * if (strROW_COUNT == null) strROW_COUNT = "0";
                 * if ("null".equals(strROW_COUNT)) strROW_COUNT = "0";
                 * int intMEM_CAPA_YN = Integer.parseInt(strROW_COUNT);
                 * if (intMEM_CAPA_YN >= 1) {
                 * strMEM_CAPA_YN = "Y";
                 * causeList.add("교육");
                 * } else {
                 * strMEM_CAPA_YN = "N";
                 * }
                 * }
                 * // 교육수강자여부---------------------------------------------------------------------> E.
                 * // 강연/행사/영화여부-----------------------------------------------------------------------> S.
                 * // 강연/행사/영화 사용 안함으로 변경 : 이사님 요청 사항 2020-10-27
                 * // List<?> evtRsvnResult = parkngService.selectEvtRsvnInfor(paramMap);
                 * // if (evtRsvnResult.size() >= 1) {
                 * //
                 * // Map<String, Object> resultEvtRsvnMap = (Map<String, Object>)evtRsvnResult.get(0);
                 * //
                 * // log.debug("강연/행사/영화여부 => ");
                 * // log.debug(resultEvtRsvnMap);
                 * //
                 * // String strROW_COUNT = String.valueOf(resultEvtRsvnMap.get("rowCount"));
                 * // if (strROW_COUNT == null) strROW_COUNT = "0";
                 * // if ("null".equals(strROW_COUNT)) strROW_COUNT = "0";
                 * //
                 * // int intMEM_EVT_YN = Integer.parseInt(strROW_COUNT);
                 * // if (intMEM_EVT_YN >= 1) {
                 * // strMEM_EVT_YN = "Y";
                 * // } else {
                 * // strMEM_EVT_YN = "N";
                 * // }
                 * //
                 * // }
                 * strMEM_EVT_YN = "N";
                 * // 강연/행사/영화여부-----------------------------------------------------------------------> E.
                 * // 특별회원여부-----------------------------------------------------------------------> S.
                 * List<?> speclResult = parkngService.selectSpeclInfor(paramMap);
                 * if (speclResult.size() >= 1) {
                 * Map<String, Object> resultSpeclMap = (Map<String, Object>)speclResult.get(0);
                 * log.debug("특별회원여부 => ");
                 * log.debug(resultSpeclMap);
                 * String strROW_COUNT = String.valueOf(resultSpeclMap.get("rowCount"));
                 * if (strROW_COUNT == null) strROW_COUNT = "0";
                 * if ("null".equals(strROW_COUNT)) strROW_COUNT = "0";
                 * int intMEM_SPECL_YN = Integer.parseInt(strROW_COUNT);
                 * if (intMEM_SPECL_YN >= 1) {
                 * strMEM_SPECL_YN = "Y";
                 * causeList.add("특별회원");
                 * } else {
                 * strMEM_SPECL_YN = "N";
                 * }
                 * }
                 * // 특별회원여부-----------------------------------------------------------------------> E.
                 * }
                 * } else {
                 * strDataCd = "-1";
                 * strDataMsg = "차량에 등록된 회원 정보를 찾을 수 없습니다.";
                 * }
                 */

            }

        } catch (Exception e) {

            strGlobalCd = "-2";
            strGlobalMsg = e.getMessage();

            strDataCd = "-5";
            strDataMsg = "System Issue";

        }

        rsltCdJsonObj.put("VHCLE_NO", strVhcleNo);
        rsltCdJsonObj.put("STARD_DATE", strReqDate);

        rsltCdJsonObj.put("CODE", strGlobalCd);
        rsltCdJsonObj.put("MSG", strGlobalMsg);

        dataJsonObj.put("CODE", strDataCd);
        dataJsonObj.put("MSG", strDataMsg);

        dataJsonObj.put("MEM_YEAR_YN", strMEMYEARYN);
        dataJsonObj.put("MEM_EXBT_YN", strMEMEXBTYN);
        dataJsonObj.put("MEM_CAPA_YN", strMEMCAPAYN);
        dataJsonObj.put("MEM_EVT_YN", strMEMEVTYN);
        dataJsonObj.put("MEM_SPECL_YN", strMEMSPECLYN);

        String strFreeCaulse = causeList.toString();

        strFreeCaulse = strFreeCaulse.replace("[", "").replace("]", "");

        dataJsonObj.put("FREE_CAUSE", strFreeCaulse);

        rsltCdJsonObj.put("DATA", dataJsonObj);

        // I/F 로그 저장(req)
        if ("1".equals(strGlobalCd)) {
            logParamMap.put("errorYn", "N");
            logParamMap.put("errorCode", "1");
            logParamMap.put("errorMsg", "성공");
        } else {
            logParamMap.put("errorYn", "Y");
            logParamMap.put("errorCode", strGlobalCd);
            logParamMap.put("errorMsg", strGlobalMsg);
        }

        if (("".equals(strVhcleNo)) || ("null".equals(strVhcleNo))) {
        } else {
            parkngService.insertParkingReqInfor(logParamMap);
        }

        // I/F 로그 저장(res)
        if ("1".equals(strDataCd)) {
            logParamMap.put("errorYn", "N");
            logParamMap.put("errorCode", "1");
            logParamMap.put("errorMsg", "성공");
        } else {
            logParamMap.put("errorYn", "Y");
            logParamMap.put("errorCode", strDataCd);
            logParamMap.put("errorMsg", strDataMsg);
        }
        logParamMap.put("logResultMap", rsltCdJsonObj.toString());

        if (("".equals(strVhcleNo)) || ("null".equals(strVhcleNo))) {
        } else {
            parkngService.insertParkingResInfor(logParamMap);
        }

        // log.debug("-----------------------S");
        // log.debug("return => " + rsltCdJsonObj);
        // log.debug("-----------------------E");

        mav.addObject("RESULT", rsltCdJsonObj);

        return mav;
    }

}
