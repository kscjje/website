package com.hisco.intrfc.adres.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.cmm.util.Config;

import egovframework.com.cmm.service.EgovProperties;
import lombok.extern.slf4j.Slf4j;

// import com.hisco.intrfc.juso.service.JusoService;

import net.sf.json.JSONObject;

/*
 * Juso 컨트롤러
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 * ------------------------------------------------------------------------
 * 작성자 일자 내용
 * ------------------------------------------------------------------------
 * 전영석 2020.08.05 최초작성
 */
@Slf4j
@Controller
public class AdresController {

    // @Resource(name = "AdresService")
    // private AdresService adresService;

    //// @Resource(name = "FileMngUtil")
    //// private FileMngUtil fileUtil;

    //// @Resource(name = "FileMngService")
    //// private FileMngService fileMngService;

    /**
     * 주소 검색(To Page)
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = Config.INTRFC_ROOT + "/adres/openAdresToPage")
    public String openAdresToPage(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) throws Exception {

        response.setHeader("X-Frame-Options", "ALLOW-FROM http://www.juso.go.kr");

        return "/intrfc/adres/adresToPageSearch";
    }

    /**
     * 주소 검색 결과(To Page)
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/adres/resultAdresToPage", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String resultAdresToPage(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
            ModelMap model) throws Exception {

        // log.debug("call resultAdresToPage()");
        // log.debug("paramMap = ");
        // log.debug(paramMap);

        String strRoadFullAddr = String.valueOf(paramMap.get("roadFullAddr"));
        String strRoadAddrPart1 = String.valueOf(paramMap.get("roadAddrPart1"));
        String strRoadAddrPart2 = String.valueOf(paramMap.get("roadAddrPart2"));
        String strEngAddr = String.valueOf(paramMap.get("engAddr"));
        String strJibunAddr = String.valueOf(paramMap.get("jibunAddr"));
        String strZipNo = String.valueOf(paramMap.get("zipNo"));
        String strAddrDetail = String.valueOf(paramMap.get("addrDetail"));
        String strAdmCd = String.valueOf(paramMap.get("admCd"));
        String strRnMgtSn = String.valueOf(paramMap.get("rnMgtSn"));
        String strBdMgtSn = String.valueOf(paramMap.get("bdMgtSn"));
        String strDetBdNmList = String.valueOf(paramMap.get("detBdNmList"));
        String strBdNm = String.valueOf(paramMap.get("bdNm"));
        String strBdKdcd = String.valueOf(paramMap.get("bdKdcd"));
        String strSiNm = String.valueOf(paramMap.get("siNm"));
        String strSggNm = String.valueOf(paramMap.get("sggNm"));
        String strEmdNm = String.valueOf(paramMap.get("emdNm"));
        String strLiNm = String.valueOf(paramMap.get("liNm"));
        String strRn = String.valueOf(paramMap.get("rn"));
        String strUdrtYn = String.valueOf(paramMap.get("udrtYn"));
        String strBuldMnnm = String.valueOf(paramMap.get("buldMnnm"));
        String strBuldSlno = String.valueOf(paramMap.get("buldSlno"));
        String strMtYn = String.valueOf(paramMap.get("mtYn"));
        String strLnbrMnnm = String.valueOf(paramMap.get("lnbrMnnm"));
        String strLnbrSlno = String.valueOf(paramMap.get("lnbrSlno"));
        String strEmdNo = String.valueOf(paramMap.get("emdNo"));

        model.addAttribute("roadFullAddr", strRoadFullAddr);
        model.addAttribute("roadFullAddr", strRoadFullAddr);
        model.addAttribute("roadAddrPart1", strRoadAddrPart1);
        model.addAttribute("roadAddrPart2", strRoadAddrPart2);
        model.addAttribute("engAddr", strEngAddr);
        model.addAttribute("jibunAddr", strJibunAddr);
        model.addAttribute("zipNo", strZipNo);
        model.addAttribute("addrDetail", strAddrDetail);
        model.addAttribute("admCd", strAdmCd);
        model.addAttribute("rnMgtSn", strRnMgtSn);
        model.addAttribute("bdMgtSn", strBdMgtSn);
        model.addAttribute("detBdNmList", strDetBdNmList);
        model.addAttribute("bdNm", strBdNm);
        model.addAttribute("bdKdcd", strBdKdcd);
        model.addAttribute("siNm", strSiNm);
        model.addAttribute("sggNm", strSggNm);
        model.addAttribute("emdNm", strEmdNm);
        model.addAttribute("liNm", strLiNm);
        model.addAttribute("rn", strRn);
        model.addAttribute("udrtYn", strUdrtYn);
        model.addAttribute("buldMnnm", strBuldMnnm);
        model.addAttribute("buldSlno", strBuldSlno);
        model.addAttribute("mtYn", strMtYn);
        model.addAttribute("lnbrMnnm", strLnbrMnnm);
        model.addAttribute("lnbrSlno", strLnbrSlno);
        model.addAttribute("emdNo", strEmdNo);

        return "/intrfc/adres/adresToPageResult";
    }

    /**
     * 주소 검색(To Json)
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = Config.INTRFC_ROOT + "/adres/openAdresToJson")
    public String openAdresToJson(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
            ModelMap model) throws Exception {
        return "/intrfc/adres/adresToJsonSearch";
    }

    /**
     * 주소 검색 결과(To Json)
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/adres/resultAdresToJson", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView resultAdresToJson(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
            ModelMap model) throws Exception {

        // log.debug("call resultAdresToJson()");
        // log.debug("paramMap = ");
        // log.debug(paramMap);

        ModelAndView mav = new ModelAndView("jsonView");

        String strGlobalCd = "1";
        String strGlobalMsg = "Success";

        String strDataCd = "";
        String strDataMsg = "";

        JSONObject rsltCdJsonObj = new JSONObject();
        JSONObject dataJsonObj = new JSONObject();

        String strRoadFullAddr = "";
        String strRoadAddrPart1 = "";
        String strRoadAddrPart2 = "";
        String strEngAddr = "";
        String strJibunAddr = "";
        String strZipNo = "";
        String strAddrDetail = "";
        String strAdmCd = "";
        String strRnMgtSn = "";
        String strBdMgtSn = "";
        String strDetBdNmList = "";
        String strBdNm = "";
        String strBdKdcd = "";
        String strSiNm = "";
        String strSggNm = "";
        String strEmdNm = "";
        String strLiNm = "";
        String strRn = "";
        String strUdrtYn = "";
        String strBuldMnnm = "";
        String strBuldSlno = "";
        String strMtYn = "";
        String strLnbrMnnm = "";
        String strLnbrSlno = "";
        String strEmdNo = "";

        try {

            if (paramMap == null) {

                strDataCd = "-1";
                strDataMsg = "주소 정보를 찾을 수 없습니다.";

            } else {

                strRoadFullAddr = String.valueOf(paramMap.get("roadFullAddr"));
                strRoadAddrPart1 = String.valueOf(paramMap.get("roadAddrPart1"));
                strRoadAddrPart2 = String.valueOf(paramMap.get("roadAddrPart2"));
                strEngAddr = String.valueOf(paramMap.get("engAddr"));
                strJibunAddr = String.valueOf(paramMap.get("jibunAddr"));
                strZipNo = String.valueOf(paramMap.get("zipNo"));
                strAddrDetail = String.valueOf(paramMap.get("addrDetail"));
                strAdmCd = String.valueOf(paramMap.get("admCd"));
                strRnMgtSn = String.valueOf(paramMap.get("rnMgtSn"));
                strBdMgtSn = String.valueOf(paramMap.get("bdMgtSn"));
                strDetBdNmList = String.valueOf(paramMap.get("detBdNmList"));
                strBdNm = String.valueOf(paramMap.get("bdNm"));
                strBdKdcd = String.valueOf(paramMap.get("bdKdcd"));
                strSiNm = String.valueOf(paramMap.get("siNm"));
                strSggNm = String.valueOf(paramMap.get("sggNm"));
                strEmdNm = String.valueOf(paramMap.get("emdNm"));
                strLiNm = String.valueOf(paramMap.get("liNm"));
                strRn = String.valueOf(paramMap.get("rn"));
                strUdrtYn = String.valueOf(paramMap.get("udrtYn"));
                strBuldMnnm = String.valueOf(paramMap.get("buldMnnm"));
                strBuldSlno = String.valueOf(paramMap.get("buldSlno"));
                strMtYn = String.valueOf(paramMap.get("mtYn"));
                strLnbrMnnm = String.valueOf(paramMap.get("lnbrMnnm"));
                strLnbrSlno = String.valueOf(paramMap.get("lnbrSlno"));
                strEmdNo = String.valueOf(paramMap.get("emdNo"));

                strDataCd = "1";
                strDataMsg = "Success";
            }

        } catch (Exception e) {

            strGlobalCd = "-1";
            strGlobalMsg = e.getMessage();

        }

        rsltCdJsonObj.put("CODE", strGlobalCd);
        rsltCdJsonObj.put("MSG", strGlobalMsg);

        dataJsonObj.put("CODE", strDataCd);
        dataJsonObj.put("MSG", strDataMsg);

        dataJsonObj.put("roadFullAddr", strRoadFullAddr);
        dataJsonObj.put("roadAddrPart1", strRoadAddrPart1);
        dataJsonObj.put("roadAddrPart2", strRoadAddrPart2);
        dataJsonObj.put("engAddr", strEngAddr);
        dataJsonObj.put("jibunAddr", strJibunAddr);
        dataJsonObj.put("zipNo", strZipNo);
        dataJsonObj.put("addrDetail", strAddrDetail);
        dataJsonObj.put("admCd", strAdmCd);
        dataJsonObj.put("rnMgtSn", strRnMgtSn);
        dataJsonObj.put("bdMgtSn", strBdMgtSn);
        dataJsonObj.put("detBdNmList", strDetBdNmList);
        dataJsonObj.put("bdNm", strBdNm);
        dataJsonObj.put("bdKdcd", strJibunAddr);
        dataJsonObj.put("siNm", strSiNm);
        dataJsonObj.put("sggNm", strSggNm);
        dataJsonObj.put("emdNm", strEmdNm);
        dataJsonObj.put("liNm", strLiNm);
        dataJsonObj.put("rn", strRn);
        dataJsonObj.put("udrtYn", strUdrtYn);
        dataJsonObj.put("buldMnnm", strBuldMnnm);
        dataJsonObj.put("buldSlno", strBuldSlno);
        dataJsonObj.put("mtYn", strMtYn);
        dataJsonObj.put("lnbrMnnm", strLnbrMnnm);
        dataJsonObj.put("lnbrSlno", strLnbrSlno);
        dataJsonObj.put("emdNo", strEmdNo);
        dataJsonObj.put("bdKdcd", strBdKdcd);

        rsltCdJsonObj.put("DATA", dataJsonObj);

        // log.debug("-----------------------S");
        // log.debug("return => " + rsltCdJsonObj);
        // log.debug("-----------------------E");

        mav.addObject("RESULT", rsltCdJsonObj);

        return mav;

    }

    /**
     * 주소 검색(Open API To Json)
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/adres/getAddrApi", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public void getAddrApi(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) throws Exception {

        String strCurrentPage = String.valueOf(paramMap.get("currentPage")); // 요청 변수 설정 (현재 페이지. currentPage : n > 0)
        String strCountPerPage = String.valueOf(paramMap.get("countPerPage")); // 요청 변수 설정 (페이지당 출력 개수. countPerPage 범위
                                                                               // : 0 < n <= 100)
        String strResultType = String.valueOf(paramMap.get("resultType")); // 요청 변수 설정 (검색결과형식 설정, json)
        String strConfmKey = String.valueOf(paramMap.get("confmKey")); // 요청 변수 설정 (승인키)
        String strKeyword = String.valueOf(paramMap.get("keyword")); // 요청 변수 설정 (키워드)

        String strJusoUrl = EgovProperties.getProperty("juso.go.kr.host");

        // log.debug("strJusoUrl = " + strJusoUrl);

        String strApiUrl = strJusoUrl + "?currentPage=" + strCurrentPage + "&countPerPage=" + strCountPerPage + "&keyword=" + java.net.URLEncoder.encode(strKeyword, "UTF-8") + "&confmKey=" + strConfmKey + "&resultType=" + strResultType;

        log.debug(strApiUrl);

        // log.debug(strApiUrl);

        java.net.URL url = new java.net.URL(strApiUrl);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String strTempStr = null;

        while (true) {
            strTempStr = br.readLine();
            if (strTempStr == null)
                break;
            sb.append(strTempStr); // 응답결과 JSON 저장
        }

        // log.debug("---------------------------------S");
        // log.debug(sb.toString());
        // log.debug("---------------------------------E");

        br.close();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json");
        response.getWriter().write(sb.toString()); // 응답결과 반환

    }

    /**
     * 주소 검색(for Open API Pop-Up)
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = Config.INTRFC_ROOT + "/adres/openApiAdresPop")
    public String openApiAdresPop(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) throws Exception {

        // log.debug("call openApiAdresPop");

        return "/intrfc/adres/adresOpenApiSearch";
    }
}
