package com.hisco.admin.admcmm.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.ResponseUtil;

import egovframework.com.cmm.EgovBrowserUtil;
import egovframework.com.cmm.util.EgovBasicLogger;
import egovframework.com.cmm.util.EgovResourceCloseHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * 파일 다운로드를 위한 컨트롤러 클래스
 * 
 * @author 전영석
 * @since 2021.06.14
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일      	      수정자           수정내용
 *  ------------   -------- ---------------------------
 *   2021.06.14     전영석          최초 생성
 *      </pre>
 */
@Slf4j
@Controller
public class AdmFileDownloadController {

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    private int maxFileSizeMB = 10;

    /**
     * 브라우저 구분 얻기.
     *
     * @param request
     * @return
     */
    /*
     * private String getBrowser(HttpServletRequest request) {
     * String header = request.getHeader("User-Agent");
     * if (header.indexOf("MSIE") > -1) {
     * return "MSIE";
     * } else if (header.indexOf("Trident") > -1) { // IE11 문자열 깨짐 방지
     * return "Trident";
     * } else if (header.indexOf("Chrome") > -1) {
     * return "Chrome";
     * } else if (header.indexOf("Opera") > -1) {
     * return "Opera";
     * }
     * return "Firefox";
     * }
     */

    /**
     * 파일 다운로드
     *
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @GetMapping(value = { "#{dynamicConfig.adminRoot}common/file/download", "#{dynamicConfig.managerRoot}common/file/download" })
    public void adminFileDownload(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        log.debug("call adminFileDownload()");

        String strFileAtchFileNm = String.valueOf(commandMap.get("atchFileNm"));

        log.debug("strFileAtchFileNm = " + strFileAtchFileNm);

        File file = new File(strFileAtchFileNm);

        if (!file.exists()) {
            ResponseUtil.SendMessage(request, response, "[" + strFileAtchFileNm.replace(File.separator, "/") + "] 해당 파일을 찾을 수 없습니다.", "self.close();");
        } else {

            String mimetype = "application/x-msdownload";

            String userAgent = request.getHeader("User-Agent");
            HashMap<String, String> result = EgovBrowserUtil.getBrowser(userAgent);
            if (!EgovBrowserUtil.MSIE.equals(result.get(EgovBrowserUtil.TYPEKEY))) {
                mimetype = "application/x-stuff";
            }

            String contentDisposition = EgovBrowserUtil.getDisposition(file.getName(), userAgent, "UTF-8");
            // response.setBufferSize(fSize); // OutOfMemeory 발생
            response.setContentType(mimetype);
            // response.setHeader("Content-Disposition", "attachment; filename=\"" + contentDisposition + "\"");
            response.setHeader("Content-Disposition", contentDisposition);
            response.setContentLengthLong(file.length());

            BufferedInputStream in = null;
            BufferedOutputStream out = null;

            try {

                in = new BufferedInputStream(new FileInputStream(file));
                out = new BufferedOutputStream(response.getOutputStream());

                FileCopyUtils.copy(in, out);
                out.flush();

            } catch (IOException ex) {
                // 다음 Exception 무시 처리
                // Connection reset by peer: socket write error
                EgovBasicLogger.ignore("IO Exception", ex);
            } finally {
                EgovResourceCloseHelper.close(in, out);
            }

        }
    }
}
