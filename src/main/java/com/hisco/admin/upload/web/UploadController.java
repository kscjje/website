package com.hisco.admin.upload.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.CamelUtil;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.FileVO;
import com.hisco.intrfc.survey.vo.SurveyMstVO;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.evtrsvn.service.EvtrsvnSMainService;
import com.hisco.user.exbtrsvn.service.DspyDsService;
import com.hisco.user.mypage.service.MyRsvnService;
import com.hisco.user.mypage.vo.MyRsvnVO;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.fcc.service.EgovStringUtil;
/* import lgdacom.XPayClient.XPayClient; 2021.06.06 JYS */
import lombok.extern.slf4j.Slf4j;

/**
 * CS 프로그램 파일 업로드를 위한 컨트롤러 클래스
 *
 * @author 진수진
 * @since 2020.08.12
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일      	수정자           수정내용
 *  ------------   --------    ---------------------------
 *   2020.08.12  진수진          최초 생성
 *      </pre>
 */

@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class UploadController {

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "dspyDsService")
    private DspyDsService dspyDsService;

    @Resource(name = "evtrsvnSMainService")
    private EvtrsvnSMainService evtrsvnSMainService;

    @Resource(name = "myRsvnService")
    private MyRsvnService myRsvnService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    private int maxFileSizeMB = 10;

    private static final String UPLOAD_ALLOW_EXT = EgovProperties.getProperty("Globals.fileUpload.Extensions");

    /**
     * 업로드 등록 페이지
     *
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @GetMapping("/upload/uploadRegistPop")
    public String selectFileRegist(HttpServletRequest request,
            CommandMap commandMap,
            ModelMap model) throws Exception {

        commandMap.put("comcd", Config.COM_CD);
        String uploadType = commandMap.getString("uploadType");
        String uploadField = commandMap.getString("uploadField");
        String camelField = CamelUtil.convert2CamelCase(uploadField);

        String fileId = commandMap.getString("fileGrpId");

        /*
         * String uniqueId = commandMap.getString("uniqueId");
         * String[] idArr = uniqueId.split(",");
         * if (idArr.length > 1) {
         * commandMap.put("uniqueId", idArr[0]);
         * }
         */
        CamelMap cMap = new CamelMap();
        if (uploadType.equals("EXT")) { // 관람 프로그램
            cMap = dspyDsService.selectBaseRule(commandMap.getParam());
            if (cMap != null) {
                fileId = EgovStringUtil.isNullToString(cMap.get(camelField));
            }
        } else if (uploadType.equals("PSP")) { // 사업장
            cMap = dspyDsService.selectselectPartCd(commandMap.getParam());
            if (cMap != null) {
                fileId = EgovStringUtil.isNullToString(cMap.get(camelField));
            }
        } else if (uploadType.equals("EVT")) { // 강연/행사/영화
            cMap = evtrsvnSMainService.selectProgramData(commandMap.getParam());
            if (cMap != null) {
                fileId = EgovStringUtil.isNullToString(cMap.get(camelField));
            }

        } else if (uploadType.equals("EDC")) { // 교육
            /*
             * cMap = edcRsvnService.selectEdcProgramFileData(commandMap.getParam());
             * if (cMap != null) {
             * fileId = EgovStringUtil.isNullToString(cMap.get(camelField));
             * }
             */

        } else if (uploadType.equals("QES")) { // 피드백
            commandMap.put("qestnarId", commandMap.getString("uniqueId"));
            SurveyMstVO vo = fileMngService.selectQuestData(commandMap.getParam());

            if (vo != null) {
                fileId = EgovStringUtil.isNullToString(vo.getQestnarImgfilnb());
            }
            model.addAttribute("programInfo", vo);
        }
        if (!uploadType.equals("QES")) { // 피드백
            model.addAttribute("programInfo", cMap);
        }

        // 수정인 경우 파일 리스트 검색
        if (!"".equals(fileId)) {
            FileVO fileVO = new FileVO();
            fileVO.setFileGrpinnb(fileId);
            model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
        }

        if (!commandMap.getString("param_uploadSize").equals("")) {
            maxFileSizeMB = Integer.parseInt(commandMap.getString("param_uploadSize"));
        }

        model.addAttribute("maxFileSizeMB", maxFileSizeMB);
        model.addAttribute("uploadAllowExt", UPLOAD_ALLOW_EXT);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("fileId", fileId);

        return Config.ADMIN_ROOT + "/upload/uploadRegistPop";
    }

    /**
     * 게시물을 등록한다.
     *
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/upload/uploadSave")
    public String insertArticle(final MultipartHttpServletRequest multiRequest, HttpServletResponse response,
            CommandMap commandMap,
            ModelMap model) throws Exception {

        commandMap.put("comcd", Config.COM_CD);
        String uploadType = commandMap.getString("uploadType");
        String uniqueId = commandMap.getString("uniqueId");
        String fileDeleteList = commandMap.getString("fileDeleteList");
        String uploadField = commandMap.getString("uploadField");
        String camelField = CamelUtil.convert2CamelCase(uploadField);
        String fileId = commandMap.getString("fileGrpId");

        // 여러개 등록 가능 , 로 문자열 연결
        /*
         * String[] idArr = uniqueId.split(",");
         * if (idArr.length > 1) {
         * commandMap.put("uniqueId", idArr[0]);
         * }
         */
        if (uploadType.equals("EXT")) { // 관람 프로그램
            CamelMap cMap = dspyDsService.selectBaseRule(commandMap.getParam());
            if (cMap != null) {
                fileId = EgovStringUtil.isNullToString(cMap.get(camelField));
            }
        } else if (uploadType.equals("PSP")) { // 사업장
            CamelMap cMap = dspyDsService.selectselectPartCd(commandMap.getParam());
            if (cMap != null) {
                fileId = EgovStringUtil.isNullToString(cMap.get(camelField));
            }
        } else if (uploadType.equals("EVT")) { // 강연/행사/영화
            CamelMap cMap = evtrsvnSMainService.selectProgramData(commandMap.getParam());
            if (cMap != null) {
                fileId = EgovStringUtil.isNullToString(cMap.get(camelField));
            }
        } else if (uploadType.equals("EDC")) { // 교육
            /*
             * CamelMap cMap = edcRsvnService.selectEdcProgramFileData(commandMap.getParam());
             * if (cMap != null) {
             * fileId = EgovStringUtil.isNullToString(cMap.get(camelField));
             * }
             */
        } else if (uploadType.equals("QES")) { // 피드백
            commandMap.put("qestnarId", commandMap.getString("uniqueId"));
            SurveyMstVO vo = fileMngService.selectQuestData(commandMap.getParam());

            if (vo != null) {
                fileId = EgovStringUtil.isNullToString(vo.getQestnarImgfilnb());
            }
        }

        final Map<String, MultipartFile> files = multiRequest.getFileMap();

        // 이미지는 단일
        try {
            if (multiRequest.getFile("imgFileUpload") != null && !multiRequest.getFile("imgFileUpload").isEmpty()) {
                if (fileId.equals("")) {
                    List<FileVO> result = fileUtil.parseFileInf(files, uploadType + "_", 0, fileId, "", "CS", "imgFileUpload");
                    fileId = fileMngService.insertFileInfs(result);

                } else {
                    // 기존 파일 삭제 후 재등록
                    FileVO fvo = new FileVO();
                    fvo.setFileGrpinnb(fileId);
                    List<FileVO> result = fileUtil.parseFileInf(files, uploadType + "_", 0, fileId, "", "CS", "imgFileUpload");
                    fileMngService.deleteAndInsert(fvo, result);

                }
            } else if (multiRequest.getFile("imgFileUpload") != null) {
                fileMngService.updateFileInfs(fileId, fileDeleteList, null);
            }

            // 파일 다중 업로드
            if (!files.isEmpty() && (uploadField.indexOf("_PLAN_") > 0 || uploadField.indexOf("PART_IMG_FINNB") >= 0)) {
                if ("".equals(fileId)) {
                    List<FileVO> result = fileUtil.parseFileInf(multiRequest, uploadType + "_", 0, fileId, "", "CS");
                    fileId = fileMngService.insertFileInfs(result);

                } else {
                    FileVO fvo = new FileVO();
                    fvo.setFileGrpinnb(fileId);
                    int cnt = fileMngService.getMaxFileSN(fvo);
                    List<FileVO> result = fileUtil.parseFileInf(multiRequest, uploadType + "_", cnt, fileId, "", "CS");
                    fileMngService.updateFileInfs(fileId, fileDeleteList, result);
                }
            }
        } catch (Exception e) {
            HttpUtility.sendBack(multiRequest, response, e.getMessage());
            return null;
        }

        commandMap.put("fileId", fileId); // 파일 아이디

        // 해당 파일아이디의 파일 갯수를 체크하여 갯수가 0 일 경우 null 처리

        FileVO fileVO = new FileVO();
        fileVO.setFileGrpinnb(fileId);
        List<FileVO> list = fileMngService.selectFileInfs(fileVO);

        if (list == null || list.size() < 1) {
            commandMap.put("fileId", "");
        }

        if (uploadType.equals("EXT")) {
            dspyDsService.updateBaseRule(commandMap.getParam());
        } else if (uploadType.equals("PSP")) { // 사업장
            dspyDsService.updatePartCdFile(commandMap.getParam());

        } else if (uploadType.equals("EVT")) { // 행사
            evtrsvnSMainService.updateProgramData(commandMap.getParam());

        } else if (uploadType.equals("EDC")) { // 교육
            // edcRsvnService.updateEdcProgramFileData(commandMap.getParam());
        } else if (uploadType.equals("QES")) { // 피드백
            fileMngService.updateQuestData(commandMap.getParam());
        }
        /*
         * if (idArr.length > 1) {
         * for (String dataId : idArr) {
         * uniqueId = dataId;
         * if (!dataId.equals(commandMap.getString("uniqueId"))) {
         * commandMap.put("uniqueId", dataId);
         * if (list != null && list.size() > 0) {
         * //파일을 복사한다
         * List<FileVO> result = fileUtil.copyFileInf(list, uploadType + "_" , 0, "" , "CS");
         * if (result != null && result.size() > 0) {
         * String newFileId = fileMngService.insertFileInfs(result);
         * commandMap.put("fileId", newFileId); // 파일 복사 NEW 아이디
         * // 복수개 업데이트
         * if (uploadType.equals("EXT")) {
         * dspyDsService.updateBaseRule(commandMap.getParam());
         * } else if (uploadType.equals("PSP")) { //사업장
         * dspyDsService.updatePartCdFile(commandMap.getParam());
         * } else if (uploadType.equals("EVT")) { //행사
         * evtrsvnSMainService.updateProgramData(commandMap.getParam());
         * } else if (uploadType.equals("EDC")) { //교육
         * edcarsvnService.updateEdcarsvnFileData(commandMap.getParam());
         * } else if (uploadType.equals("QES")) { //피드백
         * fileMngService.updateQuestData(commandMap.getParam());
         * }
         * }
         * }
         * }
         * }
         * }
         */
        // status.setComplete();
        HttpUtility.sendRedirect(multiRequest, response, "처리되었습니다.", Config.ADMIN_ROOT + "/upload/uploadRegistPop?uploadType=" + uploadType + "&uniqueId=" + uniqueId + "&uploadField=" + uploadField + "&fileGrpId=" + fileId);

        return null;

    }

    /**
     * 첨부파일 복사
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/upload/uploadCopySave")
    public void uploadCopySave(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap,
            ModelMap model) throws Exception {

        log.debug("call uploadCopySave() => 반드시 확인 필요!!");

        commandMap.put("comcd", Config.COM_CD);

        String uploadType = commandMap.getString("uploadType");
        String uniqueId = commandMap.getString("uniqueId");
        String uploadField = commandMap.getString("uploadField");
        String copyId = commandMap.getString("copyId"); // 복사 대상 ID
        String camelField = CamelUtil.convert2CamelCase(uploadField);
        String fileId = "";
        String newFileId = "";

        commandMap.put("uniqueId", copyId);

        if (uploadType.equals("EXT")) { // 관람 프로그램
            CamelMap cMap = dspyDsService.selectBaseRule(commandMap.getParam());
            if (cMap != null) {
                fileId = EgovStringUtil.isNullToString(cMap.get(camelField));
            }
        } else if (uploadType.equals("PSP")) { // 사업장
            CamelMap cMap = dspyDsService.selectselectPartCd(commandMap.getParam());
            if (cMap != null) {
                fileId = EgovStringUtil.isNullToString(cMap.get(camelField));
            }
        } else if (uploadType.equals("EVT")) { // 강연/행사/영화
            CamelMap cMap = evtrsvnSMainService.selectProgramData(commandMap.getParam());
            if (cMap != null) {
                fileId = EgovStringUtil.isNullToString(cMap.get(camelField));
            }
        } else if (uploadType.equals("EDC")) { // 교육
            /*
             * CamelMap cMap = edcRsvnService.selectEdcProgramFileData(commandMap.getParam());
             * if (cMap != null) {
             * fileId = EgovStringUtil.isNullToString(cMap.get(camelField));
             * }
             */
        } else if (uploadType.equals("QES")) { // 피드백
            commandMap.put("qestnarId", commandMap.getString("uniqueId"));
            SurveyMstVO vo = fileMngService.selectQuestData(commandMap.getParam());

            if (vo != null) {
                fileId = EgovStringUtil.isNullToString(vo.getQestnarImgfilnb());
            }
        }

        // 복사할 대상의 파일 목록을 가져온다
        FileVO fileVO = new FileVO();
        fileVO.setFileGrpinnb(fileId);
        List<FileVO> list = fileMngService.selectFileInfs(fileVO);

        if (list == null || list.size() < 1) {
            commandMap.put("fileId", "");
        } else {
            // 파일을 복사한다
            List<FileVO> result = fileUtil.copyFileInf(list, uploadType + "_", 0, "", "CS");
            if (result != null && result.size() > 0) {
                newFileId = fileMngService.insertFileInfs(result);
                commandMap.put("fileId", newFileId); // 파일 복사 NEW 아이디
            }
        }

        commandMap.put("uniqueId", uniqueId);
        if (uploadType.equals("EXT")) {
            dspyDsService.updateBaseRule(commandMap.getParam());
        } else if (uploadType.equals("PSP")) { // 사업장
            dspyDsService.updatePartCdFile(commandMap.getParam());

        } else if (uploadType.equals("EVT")) { // 행사
            evtrsvnSMainService.updateProgramData(commandMap.getParam());

        } else if (uploadType.equals("EDC")) { // 교육
            // edcRsvnService.updateEdcProgramFileData(commandMap.getParam());
        } else if (uploadType.equals("QES")) { // 피드백
            fileMngService.updateQuestData(commandMap.getParam());
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            response.getWriter().print(newFileId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 결제 취소
     *
     * @param
     * @return callback page
     * @exception Exception
     */
    @GetMapping(value = "/upload/payCancel")
    public void myRsvnCancel(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) {

        // 결제 금액 호출
        String resultMsg = "ERR";

        long cancelAmt = CommonUtil.getInt(commandMap.getString("cancelAmt")); // 취소할금액
        long payAmt = CommonUtil.getInt(commandMap.get("orginAmt")); // 원결제금액

        // orderId 값을 파라미터로
        MyRsvnVO myRsvnVO = new MyRsvnVO();
        myRsvnVO.setOrderId(commandMap.getString("orderId"));
        myRsvnVO.setOidSeq(commandMap.getString("oidSeq"));
        myRsvnVO.setComcd(Config.COM_CD);

        try {

            Map<String, Object> payInfo = myRsvnService.selectCardAppHistData(myRsvnVO);

            /*
             * [결제취소 요청 페이지]
             * 매뉴얼 "6. 결제 취소를 위한 개발사항(API)"의 "단계 3. 결제 취소 요청 및 요청 결과 처리" 참고
             * LG유플러스으로 부터 내려받은 거래번호(LGD_TID)를 가지고 취소 요청을 합니다.(파라미터 전달시 POST를 사용하세요)
             * (승인시 LG유플러스으로 부터 내려받은 PAYKEY와 혼동하지 마세요.)
             */

            if (payInfo == null || payInfo.get("mid") == null) {

                resultMsg = "ERR";
            } else {

                String lgdMid = String.valueOf(payInfo.get("mid")); // 테스트 아이디는 't'를 제외하고 입력하세요.
                String lgdTid = String.valueOf(payInfo.get("tid")); // LG유플러스으로 부터 내려받은 거래번호(LGD_TID)
                String cstPLATFORM = lgdMid.startsWith("t") ? "test" : "service"; // LG유플러스 결제서비스 선택(test:테스트,
                                                                                  // service:서비스)
                String configPath = FileMngUtil.GetRealRootPath().concat("WEB-INF/lgdacom");// LG유플러스에서 제공한
                                                                                            // 환경파일("/conf/lgdacom.conf")
                                                                                            // 위치 지정.
                String pcancelNo = CommonUtil.getString(payInfo.get("pcancelNo"));
                if (pcancelNo.equals("")) {
                    pcancelNo = CommonUtil.getString(payInfo.get("pcancelNoMax"));
                }
                String appGbn = CommonUtil.getString(payInfo.get("appGbn"));

                lgdTid = (lgdTid == null) ? "" : lgdTid;

                /*
                 * 2021.06.06 JYS
                 * // (1) XpayClient의 사용을 위한 xpay 객체 생성
                 * XPayClient xpay = new XPayClient();
                 * // (2) Init: XPayClient 초기화(환경설정 파일 로드)
                 * // configPath: 설정파일
                 * // CST_PLATFORM: - test, service 값에 따라 lgdacom.conf의 test_url(test) 또는 url(srvice) 사용
                 * // - test, service 값에 따라 테스트용 또는 서비스용 아이디 생성
                 * xpay.Init(configPath, cstPLATFORM);
                 * // (3) Init_TX: 메모리에 mall.conf, lgdacom.conf 할당 및 트랜잭션의 고유한 키 TXID 생성
                 * xpay.Init_TX(lgdMid);
                 * myRsvnVO.setCancelAmt(cancelAmt); // 취소금액
                 * myRsvnVO.setIp(commandMap.getIp());
                 * myRsvnVO.setLgdOID(myRsvnVO.getOrderId());
                 * if (CommonUtil.getInt(payInfo.get("cancelAmt2")) > 0) {
                 * myRsvnVO.setPartialYn("Y"); // 기존 부분환불 내역이 있을 경우
                 * } else {
                 * myRsvnVO.setPartialYn("N");
                 * }
                 * if ((myRsvnVO.getPartialYn().equals("N") && cancelAmt >= payAmt) || appGbn.equals("2")) {
                 * xpay.Set("LGD_TXNAME", "Cancel");
                 * log.debug("-------------------전체 취소--------------------");
                 * } else {
                 * //부분 취소
                 * xpay.Set("LGD_TXNAME", "PartialCancel");
                 * xpay.Set("LGD_CANCELAMOUNT",String.valueOf(cancelAmt));
                 * xpay.Set("LGD_REMAINAMOUNT", String.valueOf(payAmt));
                 * xpay.Set("LGD_PCANCELCNT", pcancelNo); //부분환불 요청 SEQ
                 * myRsvnVO.setPartialYn("Y");
                 * log.debug("-------------------부분 취소--------------------");
                 * log.debug("-------------------" + cancelAmt + "--------------------");
                 * log.debug("-------------------" + payAmt + "--------------------");
                 * log.debug("-------------------부분 취소--------------------");
                 * }
                 * // 기존 전체취소 건이면
                 * if (appGbn.equals("2")) {
                 * pcancelNo = "000";
                 * }
                 * xpay.Set("LGD_TID", lgdTid);
                 */

                /*
                 * 1. 결제취소 요청 결과처리
                 * 취소결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
                 * [[[중요]]] 고객사에서 정상취소 처리해야할 응답코드
                 * 1. 신용카드 : 0000, AV11
                 * 2. 계좌이체 : 0000, RF00, RF10, RF09, RF15, RF19, RF23, RF25 (환불진행중 응답건-> 환불결과코드.xls 참고)
                 * 3. 나머지 결제수단의 경우 0000(성공) 만 취소성공 처리
                 */
                // (4) TX: lgdacom.conf에 설정된 URL로 소켓 통신하여 최종 인증요청, 결과값으로 true, false 리턴
                /*
                 * 2021.06.06 JYS
                 * if (xpay.TX()) {
                 * // (5) 결제취소요청 결과 처리
                 * //1)결제취소결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
                 * String resCode = xpay.m_szResCode;
                 * log.debug("-------------------resCode--------------------:" + resCode);
                 * if (resCode.equals("0000")||resCode.equals("S020")||resCode.equals("AV11")||resCode.equals("RF00")||
                 * resCode.equals("RF10")||resCode.equals("RF09")||resCode.equals("RF15")||resCode.equals("RF19")||
                 * resCode.equals("RF23")||resCode.equals("RF25")) {
                 * //myRsvnVO.setCancelAppNo(xpay.Response("LGD_PARTIALCANCEL_SEQNO_SUB", 0));
                 * myRsvnVO.setCancelDate(xpay.Response("LGD_CANREQDATE", 0) ); // 취소날짜
                 * //기 취소 날짜
                 * String cancelDate = CommonUtil.getString(payInfo.get("cancelDate"));
                 * if ( myRsvnVO.getPartialYn().equals("N")) {
                 * myRsvnVO.setCancelDate( CommonUtil.getString(payInfo.get("appDate")) );
                 * } else if (!cancelDate.equals("")) {
                 * myRsvnVO.setCancelDate(cancelDate ); // 취소날짜
                 * } else if (myRsvnVO.getCancelDate() == null || myRsvnVO.getCancelDate().equals("")) {
                 * myRsvnVO.setCancelDate( CommonUtil.getString(payInfo.get("appDate")) );
                 * }
                 * // 기 승인번호
                 * if (myRsvnVO.getCancelAppNo() == null || myRsvnVO.getCancelAppNo().equals("")) {
                 * myRsvnVO.setCancelAppNo(String.valueOf(payInfo.get("cancelAppno")) ); // 취소번호
                 * }
                 * resultMsg = myRsvnVO.getCancelDate()+"," + myRsvnVO.getCancelAppNo()+","+pcancelNo;
                 * log.debug("-------------------resultMsg--------------------:" + resultMsg);
                 * } else {
                 * resultMsg = "ERR";
                 * }
                 * } else {
                 * //2)API 요청 실패 화면처리
                 * //out.println( "TX Response_code = " + xpay.m_szResCode + "<br>");
                 * //out.println( "TX Response_msg = " + xpay.m_szResMsg + "<p>");
                 * resultMsg = "ERR";
                 * }
                 */
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html");
                response.getWriter().print(resultMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
