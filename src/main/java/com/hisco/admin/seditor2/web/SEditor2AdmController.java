package com.hisco.admin.seditor2.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hisco.admin.admcmm.service.AdmCmmService;
import com.hisco.admin.seditor2.service.SEditor2AdmService;
import com.hisco.cmm.modules.RequestUtil;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.service.EgovProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * 카테고리 관리
 *
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class SEditor2AdmController {

    @Resource(name = "admCmmService")
    private AdmCmmService admCmmService;

    @Resource(name = "SEditor2AdmService")
    private SEditor2AdmService sEditor2AdmService;

    /**
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping("/seditor2/smartEditor2Skin")
    public String smartEditor2Skin(@ModelAttribute("searchVO") ComDefaultVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> paramMap) throws Exception {

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 스마트에디터 이미지 저장
     *
     * @param request
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/seditor2/imageUpload", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    public void imageUpload(@RequestParam("file_1") MultipartFile file, Model model,
            final MultipartHttpServletRequest multiRequest, HttpServletRequest request, HttpServletResponse response,
            CommandMap commandMap) throws Exception {

        log.debug("call /seditor2/imageUpload()");

        Map<String, Object> paramMap = commandMap.getParam();

        paramMap.put("comcd", Config.COM_CD);

        RequestUtil requestData = RequestUtil.getInstance(request);

        String strCallbackUrl = requestData.getParam("callback") + "?callback_func=" + requestData.getParam("callback_func");

        String strReFullPath = "";

        log.debug("------------------------------------------.S");
        log.debug(strCallbackUrl);
        log.debug("------------------------------------------.E");

        try {

            // ---------------------------------------------------------------------------------파일 업로드.S.
            String strUploadExt = EgovProperties.getProperty("Globals.fileUpload.Extensions");
            String strUploadRoot = EgovProperties.getProperty("upload.folder");

            log.debug("strUploadExt  = " + strUploadExt);
            log.debug("strUploadRoot = " + strUploadRoot);

            long llFileSize = 0;
            boolean boolIsEmpty = true;
            int intPos = 0;
            String strOrgFileName = "";
            String strOrgFileExt = "";
            String strOnlyFileName = "";

            InputStream inputStream01 = null;
            OutputStream outputStream01 = null;

            String strConvertFileName = "";

            String strYYYYMM = "";

            try {

                MultipartFile file01 = multiRequest.getFile("file_1");
                if (file01 != null) {

                    llFileSize = file01.getSize();
                    boolIsEmpty = file01.isEmpty();

                    log.debug("파라미터명 : " + file01.getName());
                    log.debug("파일크기 :  " + llFileSize);
                    log.debug("파일 존재 :  " + boolIsEmpty);

                    if (llFileSize >= 1) {

                        List<?> ThisTimeList = admCmmService.selectDBTime(paramMap);
                        strYYYYMM = (String) ((CamelMap) ThisTimeList.get(0)).get("timeF3");

                        log.debug("strYYYYMM = " + strYYYYMM);

                        List<?> fileSeqList = admCmmService.selectComtecopseq(paramMap);

                        String strNextId = String.valueOf(((CamelMap) fileSeqList.get(0)).get("nextId"));

                        log.debug("strNextId = " + strNextId);

                        paramMap.put("nextId", strNextId);
                        admCmmService.updateComtecopseq(paramMap);

                        String strServerFullDir = strUploadRoot + "/" + strYYYYMM;

                        log.debug("strServerFullDir = " + strServerFullDir);

                        strOrgFileName = file01.getOriginalFilename();
                        strOrgFileExt = strOrgFileName.substring(strOrgFileName.lastIndexOf(".") + 1);
                        intPos = strOrgFileName.lastIndexOf(".");
                        strOnlyFileName = strOrgFileName.substring(0, intPos);

                        log.debug("파일명칭(확장자제외) :  " + strOnlyFileName);
                        log.debug("파일 확장자 :  " + strOrgFileExt);
                        log.debug("오리지날 파일 이름 : " + strOrgFileName);

                        List<?> ThisTimeList2 = admCmmService.selectDBTime(paramMap);
                        String strTimeF4 = (String) ((CamelMap) ThisTimeList2.get(0)).get("timeF4");

                        strConvertFileName = "SE_" + strTimeF4;

                        File fileServerFullDir = new File(strServerFullDir);
                        if (!fileServerFullDir.exists()) {
                            fileServerFullDir.mkdir();
                        }

                        inputStream01 = file01.getInputStream();

                        outputStream01 = new FileOutputStream(strServerFullDir + "/" + strConvertFileName);

                        int readByte = 0;
                        byte[] buffer = new byte[8192];

                        while (true) {

                            readByte = inputStream01.read(buffer, 0, 8120);
                            if (readByte == -1) {
                                break;
                            }

                            outputStream01.write(buffer, 0, readByte);
                        }

                        admCmmService.insertCmFileGrp(paramMap);

                        paramMap.put("fileSn", "0");
                        paramMap.put("fileName", strConvertFileName);
                        paramMap.put("orginFileName", strOrgFileName);
                        paramMap.put("filePath", strYYYYMM + "/");
                        paramMap.put("fileSize", llFileSize);
                        paramMap.put("fileExtsn", strOrgFileExt.toUpperCase());
                        admCmmService.insertCmFileLst(paramMap);

                    }
                }

            } catch (Exception e) {

                e.printStackTrace();

                //// HttpUtility.sendRedirect(multiRequest, response, "파일 업로드에 이슈가 있습니다.", Config.ADMIN_ROOT + "/login"
                //// + commandMap.getString("searchQuery"));

            } finally {

                if (outputStream01 != null) {
                    outputStream01.close();
                }
                if (inputStream01 != null) {
                    inputStream01.close();
                }

            }

            String strTargetFile = multiRequest.getContextPath() + Config.USER_ROOT + "/common/file/view/" + strYYYYMM + "/" + strConvertFileName + "?originName=" + java.net.URLEncoder.encode(strOrgFileName, "UTF-8");

            strReFullPath = strCallbackUrl + "&size=" + file.getSize() + "&sFileURL=" + strTargetFile + "&sFileWidth=" + requestData.getParam("width") + "&sFileHeight=" + requestData.getParam("height") + "&bNewLine=true" + "&sFileName=" + URLEncoder.encode(strOrgFileName, "UTF-8");

            // ---------------------------------------------------------------------------------파일 업로드.E.

        } catch (Exception e) {
            e.printStackTrace();
            strReFullPath = strCallbackUrl + "&errstr=" + URLEncoder.encode(e.getMessage(), "UTF-8");
        }

        log.debug(strReFullPath);

        response.sendRedirect(strReFullPath);

    }

}
