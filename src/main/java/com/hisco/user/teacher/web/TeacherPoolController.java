package com.hisco.user.teacher.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hisco.admin.instrctr.service.InstrPoolService;
import com.hisco.admin.instrctr.vo.InstrPoolVO;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT)
public class TeacherPoolController {

    @Resource(name = "instrPoolService")
    private InstrPoolService instrPoolService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @GetMapping("/teacher/poolList")
    public String list(@ModelAttribute("searchVO") InstrPoolVO searchVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    @GetMapping("/teacher/poolListAjax")
    public String listAjax(@ModelAttribute("searchVO") InstrPoolVO searchVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setRecordCountPerPage(6);
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setSearchStat("3001"); // 등록완료

        int totCnt = 0;
        List<InstrPoolVO> list = instrPoolService.list(searchVO);

        if (list != null && !list.isEmpty()) {
            totCnt = list.get(0).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }

        model.addAttribute("list", list);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);

        return HttpUtility.getViewUrl(request);
    }

    @GetMapping("/teacher/poolRegist")
    public String poolRegist(@ModelAttribute("instrPoolVO") InstrPoolVO instrPoolVO,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        LoginVO user = commandMap.getUserInfo();
        if (user == null || !user.isMember()) {
            HttpUtility.sendBack(request, response, "로그인 후 신청하실 수 있습니다.");
            return null;
        }

        instrPoolVO.setMemNo(user.getUniqId());
        instrPoolVO.setMemNm(user.getName());
        instrPoolVO.setBirthDate(user.getBirthDate());
        instrPoolVO.setHp(user.getIhidNum());
        instrPoolVO.setEmail(user.getEmail());
        instrPoolVO.setEduinfoOpenyn("Y");

        // 이미 신청 했는지 체크
        InstrPoolVO detailVO = instrPoolService.detail(instrPoolVO);
        if (detailVO != null && detailVO.getMemNo() != null && !detailVO.getMemNo().equals("")) {
            HttpUtility.sendBack(request, response, "이미 신청한 내역이 있습니다. 마이페이지에서 확인해 주세요.");
            return null;
        }

        model.addAttribute("reqPoolUserInfo", user);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("actionPage", "poolRegistSave");

        return HttpUtility.getViewUrl(request);
    }

    @PostMapping("/teacher/poolRegistSave")
    public String poolRegistSave(@ModelAttribute("instrPoolVO") InstrPoolVO instrPoolVO,
            CommandMap commandMap, final MultipartHttpServletRequest multiRequest, HttpServletResponse response,
            HttpServletRequest request,
            ModelMap model) throws Exception {

        LoginVO user = commandMap.getUserInfo();
        if (user == null || !user.isMember()) {
            HttpUtility.sendBack(request, response, "로그인 후 신청하실 수 있습니다.");
            return null;
        }

        instrPoolVO.setReguser(user.getId());
        instrPoolVO.setMemNo(user.getUniqId());
        instrPoolVO.setState("1001");

        // 파일 업로드
        final Map<String, MultipartFile> files = multiRequest.getFileMap();
        String atachFileId = "";
        if (files != null) {
            List<FileVO> result = fileUtil.parseFileInf(files, "TCH_", 1, "", "", user.getId(), "file_1");

            if (result != null && result.size() > 0) {
                fileMngService.insertFileInfs(result);
                atachFileId = result.get(0).getFileGrpinnb();
            }

            List<FileVO> result2 = fileUtil.parseFileInf(files, "TCH_", 2, atachFileId, "", user.getId(), "file_2");

            if (result2 != null && result2.size() > 0) {
                if (!atachFileId.equals("")) {
                    fileMngService.updateFileInfs(result2);
                } else {
                    fileMngService.insertFileInfs(result2);
                }

                atachFileId = result2.get(0).getFileGrpinnb();
            }
        }

        instrPoolVO.setProflImageid(atachFileId);
        int result = instrPoolService.create(instrPoolVO);

        // 마이페이지로 이동시켜야함
        HttpUtility.sendRedirect(multiRequest, response, "저장 되었습니다.", Config.USER_ROOT + "/mypage/myWriting/myWritingList");
        return null;

    }

    @GetMapping("/teacher/poolDetail")
    public String poolDetail(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model)
            throws Exception {

        InstrPoolVO instrPoolVO = new InstrPoolVO();
        instrPoolVO.setMemNo(commandMap.getString("detailNo"));

        InstrPoolVO detailVO = instrPoolService.detail(instrPoolVO);

        // 파일 리스트 검색
        if (detailVO.getProflImageid() != null && !"".equals(detailVO.getProflImageid())) {
            FileVO fileVO = new FileVO();
            fileVO.setFileGrpinnb(detailVO.getProflImageid());

            List<FileVO> fileList = fileMngService.selectFileInfs(fileVO);

            for (FileVO file : fileList) {
                if (file.getFileSn().equals("1")) {
                    model.addAttribute("planFile", file); // 강의계획서
                } else {
                    model.addAttribute("imgFile", file); // 대표 이미지
                }
            }
        }

        model.addAttribute("detailVO", detailVO);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    @GetMapping("/mypage/teacher/list")
    public String myPoolList(@ModelAttribute("searchVO") InstrPoolVO searchVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        searchVO.setMemNo(commandMap.getUserInfo().getUniqId());
        List<InstrPoolVO> list = instrPoolService.list(searchVO);

        model.addAttribute("list", list);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/teacher/poolMyList");
    }

    @GetMapping("/mypage/teacher/modify")
    public String myPoolDetail(@ModelAttribute("instrPoolVO") InstrPoolVO instrPoolVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model)
            throws Exception {

        instrPoolVO.setMemNo(commandMap.getUserInfo().getUniqId());

        InstrPoolVO detailVO = instrPoolService.detail(instrPoolVO);

        // 파일 리스트 검색
        if (detailVO.getProflImageid() != null && !"".equals(detailVO.getProflImageid())) {
            FileVO fileVO = new FileVO();
            fileVO.setFileGrpinnb(detailVO.getProflImageid());

            List<FileVO> fileList = fileMngService.selectFileInfs(fileVO);

            for (FileVO file : fileList) {
                if (file.getFileSn().equals("1")) {
                    model.addAttribute("planFile", file); // 강의계획서
                } else {
                    model.addAttribute("imgFile", file); // 대표 이미지
                }
            }
        }

        model.addAttribute("instrPoolVO", detailVO);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("mode", "modify");
        model.addAttribute("actionPage", "modifySave");

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/teacher/poolRegist");
    }

    @PostMapping("/mypage/teacher/modifySave")
    public String poolModifySave(@ModelAttribute("instrPoolVO") InstrPoolVO instrPoolVO,
            CommandMap commandMap, final MultipartHttpServletRequest multiRequest, HttpServletResponse response,
            HttpServletRequest request,
            ModelMap model) throws Exception {

        LoginVO user = commandMap.getUserInfo();
        instrPoolVO.setMemNo(user.getUniqId());

        // 파일 업로드
        final Map<String, MultipartFile> files = multiRequest.getFileMap();
        String atachFileId = instrPoolVO.getProflImageid();
        if (atachFileId == null)
            atachFileId = "";

        if (files != null) {
            List<FileVO> result = fileUtil.parseFileInf(files, "TCH_", 1, atachFileId, "", user.getId(), "file_1");

            if (result != null && result.size() > 0) {
                atachFileId = result.get(0).getFileGrpinnb();
            } else if (result == null) {
                result = new ArrayList<FileVO>();
            }

            List<FileVO> result2 = fileUtil.parseFileInf(files, "TCH_", 2, atachFileId, "", user.getId(), "file_2");

            if (result2 != null && result2.size() > 0) {
                result.add(result2.get(0));

            }

            if (result != null && result.size() > 0) {
                if (!instrPoolVO.getProflImageid().equals("")) {
                    // 기존 파일그룹 코드가 있으면
                    fileMngService.updateFileInfs(atachFileId, "", result);
                } else {
                    fileMngService.insertFileInfs(result);
                    atachFileId = result.get(0).getFileGrpinnb();
                }
            }
        }

        instrPoolVO.setProflImageid(atachFileId);
        int result = instrPoolService.update(instrPoolVO);

        // 마이페이지로 이동시켜야함
        //HttpUtility.sendRedirect(multiRequest, response, "수정 되었습니다.", Config.USER_ROOT + "/mypage/teacher/list");
        HttpUtility.sendRedirect(multiRequest, response, "수정 되었습니다.", Config.USER_ROOT + "/mypage/myWriting/myWritingList");

        return null;

    }
}
