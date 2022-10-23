package com.hisco.cmm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.vo.FileVO;
import com.hisco.intrfc.survey.vo.SurveyMstVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : FileMngServiceImpl.java
 * @Description : 파일정보의 관리를 위한 구현 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 7. 23. 진수진 최초생성
 * @author 진수진
 * @since 2020. 7. 23.
 * @version
 * @see
 */
@Service("FileMngService")
public class FileMngService extends EgovAbstractServiceImpl {

    @Resource(name = "fileManageService")
    private FileManageService fileManageService;

    @Resource(name = "commonDAO")
    private CommonDAO commonDAO;

    /**
     * 여러 개의 파일을 삭제한다.
     *
     * @see egovframework.com.cmm.service.EgovFileMngService#deleteFileInfs(java.util.List)
     */
    public void deleteFileInfs(List<?> fvoList) throws Exception {
        fileManageService.deleteFileInfs(fvoList);
    }

    /**
     * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
     *
     * @see egovframework.com.cmm.service.EgovFileMngService#insertFileInf(egovframework.com.cmm.service.FileVO)
     */
    public String insertFileInf(FileVO fvo) throws Exception {
        String atchFileId = fvo.getFileGrpinnb();

        fileManageService.insertFileInf(fvo);

        return atchFileId;
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     *
     * @see egovframework.com.cmm.service.EgovFileMngService#insertFileInfs(java.util.List)
     */
    public String insertFileInfs(List<?> fvoList) throws Exception {
        String atchFileId = "";

        if (fvoList.size() != 0) {
            atchFileId = fileManageService.insertFileInfs(fvoList);
        }
        if (atchFileId == "") {
            atchFileId = null;
        }
        return atchFileId;
    }

    /**
     * 파일에 대한 목록을 조회한다.
     *
     * @see egovframework.com.cmm.service.EgovFileMngService#selectFileInfs(egovframework.com.cmm.service.FileVO)
     */
    public List<FileVO> selectFileInfs(FileVO fvo) throws Exception {
        return fileManageService.selectFileInfs(fvo);
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
     *
     * @see egovframework.com.cmm.service.EgovFileMngService#updateFileInfs(java.util.List)
     */
    public void updateFileInfs(List<?> fvoList) throws Exception {
        // Delete & Insert
        fileManageService.updateFileInfs(fvoList);
    }

    public void updateFileInfs(String groupId, String deleteFiles, List<?> fvoList) throws Exception {

        List<FileVO> list = new ArrayList<FileVO>();

        if (deleteFiles != null && deleteFiles.length() > 0) {
            for (String fileSn : deleteFiles.split(",")) {
                FileVO fvo = new FileVO();
                fvo.setFileGrpinnb(groupId);
                fvo.setFileSn(fileSn);
                list.add(fvo);
            }

            fileManageService.deleteFileInfs(list);
        }

        if (fvoList != null) {
            fileManageService.updateFileInfs(fvoList);
        }

    }

    /**
     * 하나의 파일을 삭제한다.
     *
     * @see egovframework.com.cmm.service.EgovFileMngService#deleteFileInf(egovframework.com.cmm.service.FileVO)
     */
    public void deleteFileInf(FileVO fvo) throws Exception {
        fileManageService.deleteFileInf(fvo);
    }

    /**
     * 파일에 대한 상세정보를 조회한다.
     *
     * @see egovframework.com.cmm.service.EgovFileMngService#selectFileInf(egovframework.com.cmm.service.FileVO)
     */
    public FileVO selectFileInf(FileVO fvo) throws Exception {
        return fileManageService.selectFileInf(fvo);
    }

    /**
     * 파일 구분자에 대한 최대값을 구한다.
     *
     * @see egovframework.com.cmm.service.EgovFileMngService#getMaxFileSN(egovframework.com.cmm.service.FileVO)
     */
    public int getMaxFileSN(FileVO fvo) throws Exception {
        return fileManageService.getMaxFileSN(fvo);
    }

    /**
     * 전체 파일을 삭제한다.
     *
     * @see egovframework.com.cmm.service.EgovFileMngService#deleteAllFileInf(egovframework.com.cmm.service.FileVO)
     */
    public void deleteAllFileInf(FileVO fvo) throws Exception {
        fileManageService.deleteAllFileInf(fvo);
    }

    /**
     * 파일을 삭제후 재등록
     *
     * @see egovframework.com.cmm.service.EgovFileMngService#deleteAllFileInf(egovframework.com.cmm.service.FileVO)
     */
    public void deleteAndInsert(FileVO fvo, List<?> fvoList) throws Exception {
        fileManageService.deleteFileInfs(fvoList);

        fileManageService.updateFileInfs(fvoList);
    }

    /**
     * 파일명 검색에 대한 목록을 조회한다.
     *
     * @see egovframework.com.cmm.service.EgovFileMngService#selectFileListByFileNm(egovframework.com.cmm.service.FileVO)
     */
    public Map<String, Object> selectFileListByFileNm(FileVO fvo) throws Exception {
        List<FileVO> result = fileManageService.selectFileListByFileNm(fvo);
        int cnt = fileManageService.selectFileListCntByFileNm(fvo);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }

    /**
     * 이미지 파일에 대한 목록을 조회한다.
     *
     * @see egovframework.com.cmm.service.EgovFileMngService#selectImageFileList(egovframework.com.cmm.service.FileVO)
     */
    public List<FileVO> selectImageFileList(FileVO vo) throws Exception {
        return fileManageService.selectImageFileList(vo);
    }

    /**
     * 설문조사 파일 정보
     *
     * @param param
     * @return
     * @throws Exception
     */
    public SurveyMstVO selectQuestData(Map<?, ?> param) throws Exception {
        return (SurveyMstVO) commonDAO.queryForObject("SurveyDAO.selectSurveyDetail", param);
    }

    /**
     * 설문조사 파일 수정
     */
    public void updateQuestData(Map<?, ?> param) throws Exception {
        commonDAO.getExecuteResult("SurveyDAO.updateSurveyDetailImg", param);
    }
}
