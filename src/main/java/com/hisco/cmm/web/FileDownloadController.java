package com.hisco.cmm.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.krysalis.barcode4j.BarcodeDimension;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapBuilder;
import org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.BarcodeMaker;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.EgovBrowserUtil;
import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.util.EgovBasicLogger;
import egovframework.com.cmm.util.EgovResourceCloseHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * 파일 다운로드를 위한 컨트롤러 클래스
 *
 * @author 진수진
 * @since 2020.07.23
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일      	수정자           수정내용
 *  ------------   --------    ---------------------------
 *   2020.07.23  진수진          최초 생성
 *      </pre>
 */
@Slf4j
@Controller
public class FileDownloadController {

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
     * 첨부파일로 등록된 파일에 대하여 다운로드를 제공한다.
     *
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @GetMapping(value = "/web/common/file/download")
    public void commonFileDownload(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String atchFileId = (String) commandMap.get("atchFileId");
        String fileSn = (String) commandMap.get("fileSn");

        String realPath = FileMngUtil.GetRealUploadPath();

        FileVO fileVO = new FileVO();
        fileVO.setFileGrpinnb(atchFileId);
        fileVO.setFileSn(fileSn);
        FileVO fvo = fileMngService.selectFileInf(fileVO);

        File uFile = new File(EgovWebUtil.filePathBlackList(realPath + fvo.getFilePath()), EgovWebUtil.filePathBlackList(fvo.getFileName()));
        long fSize = uFile.length();

        if (fSize > 0) {
            String mimetype = "application/x-msdownload";

            String userAgent = request.getHeader("User-Agent");
            HashMap<String, String> result = EgovBrowserUtil.getBrowser(userAgent);
            if (!EgovBrowserUtil.MSIE.equals(result.get(EgovBrowserUtil.TYPEKEY))) {
                mimetype = "application/x-stuff";
            }

            String contentDisposition = EgovBrowserUtil.getDisposition(fvo.getOrginFileName(), userAgent, "UTF-8");
            // response.setBufferSize(fSize); // OutOfMemeory 발생
            response.setContentType(mimetype);
            // response.setHeader("Content-Disposition", "attachment; filename=\"" + contentDisposition + "\"");
            response.setHeader("Content-Disposition", contentDisposition);
            response.setContentLengthLong(fSize);

            /*
             * FileCopyUtils.copy(in, response.getOutputStream());
             * in.close();
             * response.getOutputStream().flush();
             * response.getOutputStream().close();
             */
            BufferedInputStream in = null;
            BufferedOutputStream out = null;

            try {
                in = new BufferedInputStream(new FileInputStream(uFile));
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

        } else {
            response.setContentType("application/x-msdownload");

            PrintWriter printwriter = response.getWriter();

            printwriter.println("<html>");
            printwriter.println("<br><br><br><h2>Could not get file name:<br>" + fvo.getOrginFileName() + "</h2>");
            printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
            printwriter.println("<br><br><br>&copy; webAccess");
            printwriter.println("</html>");

            printwriter.flush();
            printwriter.close();
        }

    }

    /**
     * 첨부파일로 등록된 파일에 대하여 미리보기를 제공한다.
     *
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @GetMapping(value = "/web/common/file/view")
    public void cvplFileView(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String atchFileId = (String) commandMap.get("atchFileId");
        String fileSn = (String) commandMap.get("fileSn");

        String realPath = FileMngUtil.GetRealUploadPath();

        FileVO fileVO = new FileVO();
        fileVO.setFileGrpinnb(atchFileId);
        fileVO.setFileSn(fileSn);
        FileVO fvo = fileMngService.selectFileInf(fileVO);

        if (fvo != null) {
            File uFile = new File(EgovWebUtil.filePathBlackList(realPath + fvo.getFilePath()), EgovWebUtil.filePathBlackList(fvo.getFileName()));

            if (uFile.exists()) {

                long fSize = uFile.length();
                String mimetype = URLConnection.guessContentTypeFromName(fvo.getOrginFileName());
                if (mimetype.equals(""))
                    mimetype = "application/unknown";

                String userAgent = request.getHeader("User-Agent");

                String contentDisposition = EgovBrowserUtil.getDisposition(fvo.getOrginFileName(), userAgent, "UTF-8");
                // response.setBufferSize(fSize); // OutOfMemeory 발생
                response.setContentType(mimetype);
                // response.setHeader("Content-Disposition", "attachment; filename=\"" + contentDisposition + "\"");
                response.setHeader("Content-Disposition", contentDisposition);
                response.setContentLengthLong(fSize);

                /*
                 * FileCopyUtils.copy(in, response.getOutputStream());
                 * in.close();
                 * response.getOutputStream().flush();
                 * response.getOutputStream().close();
                 */
                BufferedInputStream in = null;
                BufferedOutputStream out = null;

                try {
                    in = new BufferedInputStream(new FileInputStream(uFile));
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

            } else {

                response.setContentType("application/x-msdownload");

                PrintWriter printwriter = response.getWriter();

                printwriter.println("<html>");
                printwriter.println("<br><br><br><h2>Could not get file name:<br>" + fvo.getOrginFileName() + "</h2>");
                printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
                printwriter.println("<br><br><br>&copy; webAccess");
                printwriter.println("</html>");

                printwriter.flush();
                printwriter.close();
            }

        }

    }

    /**
     * 첨부파일로 등록된 파일에 대하여 미리보기를 제공한다.
     *
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @GetMapping(value = "/web/common/file/view/{filePath}/{fileName}")
    public void cvplFileViewWithName(@PathVariable String filePath, @PathVariable String fileName,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String realPath = FileMngUtil.GetRealUploadPath();

        String originName = request.getParameter("originName");
        if (originName == null) {
            originName = "";
        }

        File uFile = new File(EgovWebUtil.filePathBlackList(realPath + filePath), EgovWebUtil.filePathBlackList(fileName));

        String userAgent = request.getHeader("user-agent");
        if (userAgent == null)
            userAgent = "";
        else
            userAgent = userAgent.toLowerCase();

        boolean isMSIE = userAgent.indexOf("msie") > -1 || userAgent.indexOf("trident/") > -1 || userAgent.indexOf("edge/") > -1
                ? true : false;

        String originFileName = originName.substring(0, originName.lastIndexOf("."));
        String fileExt = originName.substring(originName.lastIndexOf(".") + 1);
        StringBuffer downloadFilename = new StringBuffer(originName);

        if (isMSIE) {
            downloadFilename.setLength(0);
            downloadFilename.append(URLEncoder.encode(originFileName, "UTF-8").replaceAll("\\+", " ")).append(".").append(fileExt);
        } else {
            downloadFilename.setLength(0);
            downloadFilename.append(new String(originFileName.getBytes("UTF-8"), "ISO-8859-1")).append(".").append(fileExt);
        }

        if (uFile != null && uFile.exists()) {
            long fSize = uFile.length();
            String mimetype = URLConnection.guessContentTypeFromName(originName);
            if (mimetype == null || mimetype.equals(""))
                mimetype = "application/unknown";

            // String contentDisposition = EgovBrowserUtil.getDisposition(fileName,userAgent,"UTF-8");

            response.setHeader("Content-Disposition", "attachment; filename=\"".concat(downloadFilename.toString()).concat("\";"));
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setContentType(mimetype);
            response.setContentLength((int) fSize);

            BufferedInputStream in = null;
            BufferedOutputStream out = null;

            try {
                in = new BufferedInputStream(new FileInputStream(uFile));
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

        } else {
            response.setContentType("application/x-msdownload");

            PrintWriter printwriter = response.getWriter();

            printwriter.println("<html>");
            printwriter.println("<br><br><br><h2>Could not get file name:<br>" + CommonUtil.unscript(fileName) + "</h2>");
            printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
            printwriter.println("<br><br><br>&copy; webAccess");
            printwriter.println("</html>");

            printwriter.flush();
            printwriter.close();
        }

    }

    /**
     * 첨부파일 include 페이지
     *
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @GetMapping("/web/common/upload/includeUploadRegist")
    public String selectIncludeCommonUploadForm(HttpServletRequest request, CommandMap commandMap, ModelMap model)
            throws Exception {

        // 수정인 경우 파일 리스트 검색
        String file_group_id = request.getParameter("file_group_id");
        if (file_group_id != null && !"".equals(file_group_id)) {
            FileVO fileVO = new FileVO();
            fileVO.setFileGrpinnb(file_group_id);
            model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
        }

        String param_uploadSize = request.getParameter("param_uploadSize");
        if (param_uploadSize != null && !param_uploadSize.equals("")) {
            maxFileSizeMB = Integer.parseInt(param_uploadSize);
        }

        model.addAttribute("maxFileSizeMB", maxFileSizeMB);
        model.addAttribute("commandMap", commandMap);

        return "/web/common/upload/includeUploadRegist";
    }

    /**
     * 첨부파일 include 페이지 (동아리 전용)
     *
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @GetMapping("/web/common/upload/includeUploadDongari")
    public String selectIncludeDongariUploadForm(HttpServletRequest request, CommandMap commandMap, ModelMap model)
            throws Exception {

        // 수정인 경우 파일 리스트 검색
        String file_group_id = request.getParameter("file_group_id");
        if (file_group_id != null && !"".equals(file_group_id)) {
            FileVO fileVO = new FileVO();
            fileVO.setFileGrpinnb(file_group_id);
            model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
        }

        String param_uploadSize = request.getParameter("param_uploadSize");
        if (param_uploadSize != null && !param_uploadSize.equals("")) {
            maxFileSizeMB = Integer.parseInt(param_uploadSize);
        }

        model.addAttribute("maxFileSizeMB", maxFileSizeMB);
        model.addAttribute("commandMap", commandMap);

        return "/web/common/upload/includeUploadDongari";
    }

    /**
     * 첨부파일 include 페이지
     *
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @RequestMapping("/web/common/upload/includeUploadDetail")
    public String selectIncludeFileDetail(HttpServletRequest request, CommandMap commandMap, ModelMap model)
            throws Exception {

        // 파일 리스트 검색
        FileVO fileVO = new FileVO();
        String file_group_id = request.getParameter("file_group_id");
        if (file_group_id != null) {
            fileVO.setFileGrpinnb(file_group_id);
            model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
        }
        model.addAttribute("commandMap", commandMap);

        return "/web/common/upload/includeUploadDetail";
    }

    /**
     * 첨부파일 include 페이지
     *
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @RequestMapping("/web/common/file/getBarcode/{text}")
    public ResponseEntity<byte[]> getBarcode(HttpServletRequest request, CommandMap commandMap, ModelMap model,
            @PathVariable String text) throws Exception {

        /*
         * String text = commandMap.getString("text");
         * String _formatName = commandMap.getString("fileName");
         */
        // String _formatName = "tempFile";
        String barcodeText = new String(text.getBytes("UTF-8"), "ISO-8859-1");
        byte[] imageInByte = BarcodeMaker.getBarCodeImage(barcodeText, 340, 121);
        // smsp.print_String(image.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        // String mt_filename = text + ".png";
        // String formatName = "PNG";
        // MediaUtils.getMediaType(formatName);
        // MediaType mType = new MediaType(formatName);

        // smsp.print_String(mType.toString());
        // if (mType != null) {

        /*
         * } else {
         * mt_filename = new String(mt_filename.getBytes("UTF-8"), "ISO-8859-1");
         * headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
         * headers.add("Content-Disposition", "attachment;filename=\"" + mt_filename + "\";");
         * headers.add("Content-Transfer-Encoding", "binary");
         * }
         */
        return new ResponseEntity<byte[]>(imageInByte, headers, HttpStatus.OK);

    }

    /**
     * Barcode4J 라이브러리 이용 바코드생성
     *
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @RequestMapping("/web/common/file/barcode/{text}")
    public void getBarcode(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap,
            @PathVariable String text) throws Exception {

        OutputStream out = response.getOutputStream();

        final int dpi = 150;

        Code128Bean bean = new Code128Bean();
        bean.setBarHeight(15.0);
        bean.setModuleWidth(UnitConv.in2mm(2.5f / dpi));

        int orientation = 0;
        // int resolution = 150;
        BarcodeDimension dim = bean.calcDimensions(text);

        int bmw = UnitConv.mm2px(dim.getWidthPlusQuiet(orientation), dpi);
        int bmh = UnitConv.mm2px(dim.getHeightPlusQuiet(orientation) + 1, dpi);
        BufferedImage bi = new BufferedImage(bmw, bmh, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setBackground(Color.white);
        g2d.setColor(Color.black);
        g2d.clearRect(0, 0, bi.getWidth(), bi.getHeight());
        // Set up coordinate system: Barcode4J calculates in millimeters internally
        g2d.scale(bi.getWidth() / dim.getWidthPlusQuiet(orientation), bi.getHeight() / dim.getHeightPlusQuiet(orientation));

        Java2DCanvasProvider canvas = new Java2DCanvasProvider(g2d, orientation);
        bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        bean.generateBarcode(canvas, text); // 바코드

        AffineTransform at = new AffineTransform();
        if (text.length() < 10) {
            at.translate(12, 18);
        } else {
            at.translate(16, 18);
        }
        // at.rotate(Math.PI / -4);
        g2d.transform(at);

        g2d.setFont(new Font(bean.getFontName(), Font.BOLD, 3));

        String newText = text;
        if (text.length() == 12) {
            newText = text.substring(0, 4) + " " + text.substring(4, 8) + " " + text.substring(8, 12);
        } else if (text.length() == 13) {
            newText = text.substring(0, 5) + " " + text.substring(5, 9) + " " + text.substring(9, 13);
        }
        g2d.drawString(newText, 0, 0);

        try {
            response.setHeader("Content-Disposition", "attachment; filename=\"".concat(text).concat(".jpg\";"));
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setContentType("image/jpeg");
            // response.setContentLength((int)fSize);

            BitmapBuilder.saveImage(bi, out, "image/jpeg", dpi);
        } finally {
            out.close();
        }

    }

    /**
     * com.google.zxing QR 코드 생성
     *
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @RequestMapping("/web/common/file/qrcode/{text}")
    public void getQRcode(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap,
            @PathVariable String text) throws Exception {

        log.debug("call getQRcode");

        OutputStream out = response.getOutputStream();

        QRCodeWriter q = new QRCodeWriter();

        String strText = new String(text.getBytes("UTF-8"), "ISO-8859-1");

        log.debug("atrTtext = " + strText);

        BitMatrix bitMatrix = q.encode(strText, BarcodeFormat.QR_CODE, 300, 350);

        MatrixToImageWriter.writeToStream(bitMatrix, "png", out);

    }

}
