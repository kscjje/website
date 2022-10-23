package com.hisco.admin.wasresource.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hisco.admin.admcmm.service.AdmCmmService;
import com.hisco.admin.log.service.LogService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.modules.FileUtil;
import com.hisco.cmm.modules.site.thissite.file.ResourceFileDto;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.TemplateVO;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.RequestUtil;
import com.hisco.cmm.util.ResponseUtil;
import com.hisco.cmm.util.StringUtil;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * WAS 리소스 관리
 * 
 * @author 전영석
 * @since 2021.06.14
 * @version 1.0, 2021.06.14
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.06.14 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}" })
public class WasResourceController {

    @Resource(name = "admCmmService")
    private AdmCmmService admCmmService;

    @Resource(name = "logService")
    private LogService logService;

    /**
     * WAS 리소스 관리
     * 
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "WAS 리소스 조회", action = "R")
    @GetMapping("/wasresource/resourceList")
    public String selectResourceList(@ModelAttribute("templateVO") TemplateVO templateVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        Map<String, Object> paramMap = commandMap.getParam();

        log.debug("paramMap = {}", paramMap);

        String strThisPath = String.valueOf(paramMap.get("dir"));
        String strSearchDir = String.valueOf(paramMap.get("searchDir"));

        String strRootPath = FileUtil.GetRealRootPath();

        if (("null".equals(strSearchDir)) || (strSearchDir == null) || ("".equals(strSearchDir))) {

            if (("null".equals(strThisPath)) || (strThisPath == null) || ("".equals(strThisPath))) {
                strThisPath = strRootPath;
            } else {

                if (strThisPath.indexOf(File.separator) >= 1) {
                    strThisPath = File.separator + strThisPath;
                }

            }

        } else {

            strSearchDir = strSearchDir.replace("/", File.separator);
            strThisPath = strRootPath + strSearchDir;

        }

        log.debug("strSearchDir = " + strSearchDir);
        log.debug("strThisPath  = " + strThisPath);

        /*
         * paramMap.put("comcd", Config.COM_CD);
         * if (!logService.checkAdminLog(commandMap, "R", "WAS 리소스 관리")) {
         * return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
         * }
         */

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paramMap.put("firstIndex", paginationInfo.getFirstRecordIndex());
        paramMap.put("lastIndex", paginationInfo.getLastRecordIndex());
        paramMap.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());

        String strCurrDir = strThisPath.replace(strRootPath, "");

        log.debug("strCurrDir 변환 후 => " + strCurrDir);

        if ("".equals(strCurrDir)) {
            strCurrDir = File.separator;
        }

        strCurrDir = strCurrDir.replace(File.separator, "/");
        model.addAttribute("CurrDir", strCurrDir);

        int intRootCount = strCurrDir.indexOf("/");

        String strParentDir = "";

        if ("/".equals(strCurrDir)) {
            model.addAttribute("ParentDirYn", "N");
        } else {
            model.addAttribute("ParentDirYn", "Y");

            String strParentDirArr[] = strCurrDir.split("/");

            int intParentDirLen = strParentDirArr.length;

            log.debug("intParentDirLen = " + intParentDirLen);

            for (int j = 0; j < intParentDirLen; j++) {
                log.debug("strParentDirArr[" + j + "] = " + strParentDirArr[j]);
                log.debug("");
            }

            if (intParentDirLen >= 2) {
                strParentDir = "";
                for (int i = 0; i < strParentDirArr.length - 1; i++) {
                    if (i == 0) {
                        strParentDir = "/" + strParentDirArr[i];
                    } else {
                        if ("/".equals(strParentDir)) {
                            strParentDir = strParentDir + strParentDirArr[i];
                        } else {
                            strParentDir = strParentDir + "/" + strParentDirArr[i];
                        }
                    }
                }

                log.debug("strParentDir 최종 추출 = " + strParentDir);

                // model.addAttribute("ParentDir", strRootPath + strParentDir.replace("/", File.separator));
                model.addAttribute("ParentDir", strParentDir);
            }
        }

        log.debug("intRootCount = " + intRootCount);
        log.debug("strThisPath  = " + strThisPath);
        log.debug("strRootPath  = " + strRootPath);
        log.debug("strCurrDir   = " + strCurrDir);
        log.debug("strParentDir   = " + strParentDir);
        log.debug("------------------------------------------------------------.E");

        File file = new File(strThisPath);
        if (file == null || !file.exists() || !file.isDirectory()) {
            ResponseUtil.SendMessage(request, response, "해당 경로를 찾을 수 없습니다.", "history.back()");
            return null;
        }

        File[] dirs = file.listFiles();
        Arrays.sort(dirs);

        if (dirs != null && dirs.length > 0) {

            List<ResourceFileDto> fileList = new ArrayList<ResourceFileDto>();

            // 디렉토리 처리
            for (int i = 0, length = dirs.length; i < length; i++) {
                if (dirs[i].isDirectory())
                    fileList.add(new ResourceFileDto(dirs[i]));
            }

            // 파일 처리
            for (int i = 0, length = dirs.length; i < length; i++) {
                if (dirs[i].isFile())
                    fileList.add(new ResourceFileDto(dirs[i]));
            }

            model.addAttribute("FileList", fileList);
        }

        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paramMap", paramMap);
        model.addAttribute("paginationInfo", paginationInfo);

        model.addAttribute("Dir", strCurrDir);

        log.debug("model = " + model);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * WAS 리소스 저장
     * 
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "WAS 리소스 저장", action = "U")
    @RequestMapping(value = "/wasresource/resourceSave", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    public String saveWasResource(@ModelAttribute("templateVO") TemplateVO templateVO, CommandMap commandMap,
            final MultipartHttpServletRequest multiRequest, HttpServletResponse response, HttpServletRequest request,
            ModelMap model) throws Exception {

        log.debug("call saveWasResource()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        paramMap.put("comcd", Config.COM_CD);

        String strMode = String.valueOf(paramMap.get("mode"));
        String strSearchDir = String.valueOf(paramMap.get("searchDir"));
        String strToDir = strSearchDir.replace("/", File.separator);

        log.debug("strToDir = " + strToDir);

        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        if (loginVO == null) {
            HttpUtility.sendRedirect(multiRequest, response, "로그인 정보를 찾을 수 없습니다.", Config.ADMIN_ROOT + "/login" + commandMap.getString("searchQuery"));
        }

        if (loginVO != null) {
            paramMap.put("userId", loginVO.getId());
        }

        if (strMode == null) {
            strMode = "";
        }

        String strRootPath = FileUtil.GetRealRootPath();
        log.debug("strRootPath = " + strRootPath);

        String strToFullPath = strRootPath + strToDir;
        log.debug("strToFullPath = " + strToFullPath);

        int intFileAllRow = 0;
        int intPos = 0;
        long llFileSize = 0;
        boolean boolIsEmpty = true;
        String strOrgFileName = "";
        String strOrgFileExt = "";
        String strOnlyFileName = "";

        InputStream inputStream03 = null;
        OutputStream outputStream03 = null;

        if ("fileUpload".equals(strMode)) {

            // 다중 파일
            // 업로드-----------------------------------------------------------------------------------------------------------시작
            try {

                List<MultipartFile> file03 = multiRequest.getFiles("regiFile03");
                if (file03 != null) {

                    intFileAllRow = file03.size();

                    log.debug("intFileAllRow = " + intFileAllRow);

                    if (intFileAllRow >= 1) {

                        for (int i = 0; i < intFileAllRow; i++) {

                            MultipartFile thisMultipartFile = file03.get(i);

                            llFileSize = thisMultipartFile.getSize();
                            boolIsEmpty = thisMultipartFile.isEmpty();

                            log.debug("파라미터명 : " + thisMultipartFile.getName());
                            log.debug("파일크기 :  " + llFileSize);
                            log.debug("파일 존재 :  " + boolIsEmpty);

                            if (!boolIsEmpty) {

                                strOrgFileName = thisMultipartFile.getOriginalFilename();
                                strOrgFileExt = strOrgFileName.substring(strOrgFileName.lastIndexOf(".") + 1);
                                intPos = strOrgFileName.lastIndexOf(".");
                                strOnlyFileName = strOrgFileName.substring(0, intPos);

                                log.debug("파일명칭(확장자제외) :  " + strOnlyFileName);
                                log.debug("파일 확장자 :  " + strOrgFileExt);
                                log.debug("오리지날 파일 이름 : " + strOrgFileName);

                                inputStream03 = thisMultipartFile.getInputStream();

                                outputStream03 = new FileOutputStream(strToFullPath + File.separator + strOrgFileName);

                                int readByte = 0;
                                byte[] buffer = new byte[8192];

                                /*
                                 * while ((readByte = inputStream03.read(buffer, 0, 8120)) != -1) {
                                 * outputStream03.write(buffer, 0, readByte);
                                 * }
                                 */

                                while (true) {

                                    readByte = inputStream03.read(buffer, 0, 8120);
                                    if (readByte == -1) {
                                        break;
                                    }

                                    outputStream03.write(buffer, 0, readByte);
                                }

                            }

                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                if (outputStream03 != null) {
                    outputStream03.close();
                }
                if (inputStream03 != null) {
                    inputStream03.close();
                }

            }
            // 다중 파일
            // 업로드-----------------------------------------------------------------------------------------------------------종료

        } else if ("fileDelete".equals(strMode)) {

            String strFileDeleteNm = String.valueOf(paramMap.get("fileDeleteNm"));

            String strDestDelFullPath = strToFullPath + File.separator + strFileDeleteNm;

            log.debug("strFileDeleteNm = " + strFileDeleteNm);
            log.debug("삭제 대상 => " + strDestDelFullPath);

            File file = new File(strDestDelFullPath);

            if (file.exists()) {
                file.delete();
            } else {
                HttpUtility.sendRedirect(multiRequest, response, "[" + strDestDelFullPath.replace(File.separator, "/") + "] 해당 파일 및 디렉터리가 존재하지 않습니다.", Config.ADMIN_ROOT + "/wasresource/resourceList?searchDir=" + strSearchDir);
            }

        } else if ("createDirectory".equals(strMode)) {

            String strNewFileOrDir = String.valueOf(paramMap.get("newFileOrDir"));
            log.debug("strNewFileOrDir = " + strNewFileOrDir);

            File file = new File(strToFullPath + File.separator + strNewFileOrDir);

            if (file.exists()) {
                HttpUtility.sendRedirect(multiRequest, response, "[" + strNewFileOrDir + "] 디렉터리가 이미 존재합니다.", Config.ADMIN_ROOT + "/wasresource/resourceList?searchDir=" + strSearchDir);
            } else {
                file.mkdir();
            }

        } else if ("createFile".equals(strMode)) {

            String strNewFileOrDir = String.valueOf(paramMap.get("newFileOrDir"));
            log.debug("strNewFileOrDir = " + strNewFileOrDir);

            File file = new File(strToFullPath + File.separator + strNewFileOrDir);

            if (file.exists()) {
                HttpUtility.sendRedirect(multiRequest, response, "[" + strNewFileOrDir + "] 파일이 이미 존재합니다.", Config.ADMIN_ROOT + "/wasresource/resourceList?searchDir=" + strSearchDir);
            } else {
                file.createNewFile();
            }

        } else if ("renameFile".equals(strMode)) {

            String strNewFileOrDir = String.valueOf(paramMap.get("newFileOrDir"));
            String strOrgFileOrDir = String.valueOf(paramMap.get("orgFileOrDir"));

            log.debug("Prev strNewFileOrDir = " + strNewFileOrDir);
            log.debug("Prev strOrgFileOrDir = " + strOrgFileOrDir);

            strNewFileOrDir = strNewFileOrDir.replace("/", File.separator);
            strOrgFileOrDir = strOrgFileOrDir.replace("/", File.separator);

            log.debug("After strNewFileOrDir = " + strNewFileOrDir);
            log.debug("After strOrgFileOrDir = " + strOrgFileOrDir);

            String strReNameOrgFullPath = strRootPath + strToDir + File.separator + strOrgFileOrDir;
            String strReNameDstFullPath = strRootPath + strToDir + File.separator + strNewFileOrDir;

            log.debug("strReNameOrgFullPath = " + strReNameOrgFullPath);
            log.debug("strReNameDstFullPath = " + strReNameDstFullPath);

            File fileOrg = new File(strReNameOrgFullPath);
            File fileDest = new File(strReNameDstFullPath);

            if (fileDest.exists()) {
                HttpUtility.sendRedirect(multiRequest, response, "[" + strNewFileOrDir + "] 파일이 이미 존재합니다.", Config.ADMIN_ROOT + "/wasresource/resourceList?searchDir=" + strSearchDir);
                return null;
            }

            if (!fileOrg.exists()) {
                HttpUtility.sendRedirect(multiRequest, response, "[" + strOrgFileOrDir + "] 원본 파일이 존재하지 않습니다.", Config.ADMIN_ROOT + "/wasresource/resourceList?searchDir=" + strSearchDir);
            } else {
                fileOrg.renameTo(fileDest);
            }

        } else if ("renameDir".equals(strMode)) {

            String strNewFileOrDir = String.valueOf(paramMap.get("newFileOrDir"));
            String strOrgFileOrDir = String.valueOf(paramMap.get("orgFileOrDir"));

            log.debug("Prev strNewFileOrDir = " + strNewFileOrDir);
            log.debug("Prev strOrgFileOrDir = " + strOrgFileOrDir);

            strNewFileOrDir = strNewFileOrDir.replace("/", File.separator);
            strOrgFileOrDir = strOrgFileOrDir.replace("/", File.separator);

            log.debug("After strNewFileOrDir = " + strNewFileOrDir);
            log.debug("After strOrgFileOrDir = " + strOrgFileOrDir);

            File fileOrg = new File(strToFullPath + strToDir + File.separator + strOrgFileOrDir);
            File fileDest = new File(strToFullPath + strToDir + File.separator + strNewFileOrDir);

            if (fileDest.exists()) {
                HttpUtility.sendRedirect(multiRequest, response, "[" + strNewFileOrDir + "] 디렉터리가 이미 존재합니다.", Config.ADMIN_ROOT + "/wasresource/resourceList?searchDir=" + strSearchDir);
                return null;
            }

            if (!fileOrg.exists()) {
                HttpUtility.sendRedirect(multiRequest, response, "[" + strOrgFileOrDir + "] 원본 디렉터리가 존재하지 않습니다.", Config.ADMIN_ROOT + "/wasresource/resourceList?searchDir=" + strSearchDir);
            } else {
                fileOrg.renameTo(fileDest);
            }

        }

        if ("fileUpload".equals(strMode)) {
            HttpUtility.sendRedirect(multiRequest, response, "", Config.ADMIN_ROOT + "/wasresource/resourceList?searchDir=" + strSearchDir);
        } else if ("fileDelete".equals(strMode)) {
            HttpUtility.sendRedirect(multiRequest, response, "", Config.ADMIN_ROOT + "/wasresource/resourceList?searchDir=" + strSearchDir);
        } else if ("createDirectory".equals(strMode)) {
            HttpUtility.sendRedirect(multiRequest, response, "", Config.ADMIN_ROOT + "/wasresource/resourceList?searchDir=" + strSearchDir);
        } else if ("createFile".equals(strMode)) {
            HttpUtility.sendRedirect(multiRequest, response, "", Config.ADMIN_ROOT + "/wasresource/resourceList?searchDir=" + strSearchDir);
        } else if ("renameFile".equals(strMode)) {
            HttpUtility.sendRedirect(multiRequest, response, "", Config.ADMIN_ROOT + "/wasresource/resourceList?searchDir=" + strSearchDir);
        } else if ("renameDir".equals(strMode)) {
            HttpUtility.sendRedirect(multiRequest, response, "", Config.ADMIN_ROOT + "/wasresource/resourceList?searchDir=" + strSearchDir);
        }

        return null;

    }

    /**
     * WAS 리소스 수정
     * 
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */

    @PageActionInfo(title = "WAS 리소스 수정", action = "U")
    @GetMapping("/wasresource/resourceEditPopup")
    public String editWasResource(@ModelAttribute("templateVO") ComDefaultVO searchVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.debug("call editWasResource()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        paramMap.put("comcd", Config.COM_CD);

        String strFileNm = String.valueOf(paramMap.get("fileNm"));
        String strSearchDir = String.valueOf(paramMap.get("searchDir"));
        String strToDir = strSearchDir.replace("/", File.separator);

        log.debug("strToDir = " + strToDir);

        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        if (loginVO == null) {
            HttpUtility.sendRedirect(request, response, "로그인 정보를 찾을 수 없습니다.", Config.ADMIN_ROOT + "/login" + commandMap.getString("searchQuery"));
        }

        if (loginVO != null) {
            paramMap.put("userId", loginVO.getId());
        }

        String strRootPath = FileUtil.GetRealRootPath();
        log.debug("strRootPath = " + strRootPath);

        String strToFullPath = strRootPath + strToDir + File.separator + strFileNm;
        log.debug("strToFullPath = " + strToFullPath);

        RequestUtil requestData = RequestUtil.getInstance(request);

        String strCharset = StringUtil.NotNull(requestData.getParam("charset"), "UTF-8");
        log.debug("strCharset = " + strCharset);

        File file = new File(strToFullPath);

        String strFileContents = null;

        try {

            strFileContents = FileUtil.LoadFileSource(file, strCharset.toUpperCase());
            if (StringUtil.IsEmpty(strFileContents)) {
                strFileContents = "";
            }

        } catch (Exception e) {
            ResponseUtil.SendMessage(request, response, "파일 소스를 불러올 수 없습니다.", "history.back();");
            return null;
        }

        model.addAttribute("charset", strCharset);
        model.addAttribute("fileContents", strFileContents.trim());
        model.addAttribute("fileFullPath", (strRootPath + strToDir).replace(File.separator, "/"));
        model.addAttribute("fileName", strFileNm);
        model.addAttribute("searchDir", strSearchDir);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * WAS 리소스 파일업로드
     * 
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "WAS 리소스 파일업로드", action = "C")
    @RequestMapping(value = "/wasresource/resourceFileSave", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    public String saveWasResourceFile(@ModelAttribute("templateVO") ComDefaultVO searchVO, CommandMap commandMap,
            final MultipartHttpServletRequest multiRequest, HttpServletResponse response, HttpServletRequest request,
            ModelMap model) throws Exception {

        log.debug("call saveWasResourceFile()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        paramMap.put("comcd", Config.COM_CD);

        String strCharset = String.valueOf(paramMap.get("charset"));
        String strFileName = String.valueOf(paramMap.get("fileName"));
        String strSearchDir = String.valueOf(paramMap.get("searchDir"));
        String strToDir = strSearchDir.replace("/", File.separator);
        String strFileContents = String.valueOf(paramMap.get("fileContents"));

        log.debug("strToDir = " + strToDir);

        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        if (loginVO == null) {
            HttpUtility.sendRedirect(multiRequest, response, "로그인 정보를 찾을 수 없습니다.", Config.ADMIN_ROOT + "/login" + commandMap.getString("searchQuery"));
        }

        if (loginVO != null) {
            paramMap.put("userId", loginVO.getId());
        }

        String strRootPath = FileUtil.GetRealRootPath();
        log.debug("strRootPath = " + strRootPath);

        String strToFullPath = strRootPath + strToDir + File.separator + strFileName;
        log.debug("strToFullPath = " + strToFullPath);

        File file = new File(strToFullPath);

        if (file.exists()) {
            FileUtil.SaveFileSource(file, strFileContents, strCharset.toUpperCase());
            HttpUtility.sendRedirect(multiRequest, response, "정상으로 저장되었습니다.", Config.ADMIN_ROOT + "/wasresource/resourceEditPopup?fileNm=" + strFileName + "&searchDir=" + strSearchDir);
        } else {
            HttpUtility.sendRedirect(multiRequest, response, "대상 파일을 찾을 수 없습니다.", Config.ADMIN_ROOT + "/wasresource/resourceEditPopup?fileNm=" + strFileName + "&searchDir=" + strSearchDir);
        }

        return null;

    }
}
