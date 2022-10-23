package com.hisco.admin.edumovie.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hisco.admin.admcmm.web.AdmFileDownloadController;
import com.hisco.admin.eduadm.vo.EdcProgramVO;
import com.hisco.admin.edumovie.mapper.EduMovieAdmMapper;
import com.hisco.admin.edumovie.vo.EduMovieVO;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import lombok.extern.slf4j.Slf4j;

/**
 * 동영상강좌 service 구현 클래스
 *
 * @author 김범수
 * @since 2022.10.14
 * @version 1.0, 2022.10.14
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          김범수 2022.10.14 최초작성
 */
@Slf4j
@Service("eduMovieAdmService")
public class EduMovieAdmService extends EgovAbstractServiceImpl {

    @Resource(name = "eduMovieAdmMapper")
    private EduMovieAdmMapper eduMovieAdmMapper;
    
    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;
	
	/**
     * 서버 업로드 경로 찾기
     *
     * @return
     */
	public static String GetRealUploadPath() {
        return EgovProperties.getProperty("upload.folder");
    }

    /**
     * 동영상 강좌 목록을 조회한다
     * @param searchVO
     * @return
     */
	public List<HashMap<String, Object>> selectEduMovieList(EduMovieVO searchVO) {
		// TODO Auto-generated method stub
		return eduMovieAdmMapper.selectEduMovieList(searchVO);
	}
	
	/**
     * 동영상 강좌 목록 개수를 조회한다
     * @param searchVO
     * @return
     */
	public int selectEduMovieCount(EduMovieVO searchVO) {
		// TODO Auto-generated method stub
		return eduMovieAdmMapper.selectEduMovieCount(searchVO);
	}
	
	/**
	 * 동영상 강좌 이용자를 조회한다
	 * @param searchVO
	 * @return
	 */
	public List<EduMovieVO> selectEduMovieUserAjax(EduMovieVO searchVO) {
		// TODO Auto-generated method stub
		return eduMovieAdmMapper.selectEduMovieUserAjax(searchVO);
	}

	/**
     * 동영상 강좌를 등록한다
     * @param searchVO
     * @return
	 * @throws Exception 
     */
	public int insertEduMovie(EduMovieVO eduMovieVO, LoginVO user, Map<String, MultipartFile> files) throws Exception {
		// TODO Auto-generated method stub
		float movieIdx = eduMovieAdmMapper.selectEduMovieIdx(eduMovieVO);
		eduMovieVO.setOrgMovieLecNo(movieIdx);
		fileUploadFunction(eduMovieVO, files, user);
		return eduMovieAdmMapper.insertEduMovie(eduMovieVO);
	}

	/**
	 * 동영상 강좌를 수정한다
	 * @param eduMovieVO
	 * @param files
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int updateEduMovie(EduMovieVO eduMovieVO, Map<String, MultipartFile> files, LoginVO user) throws Exception {
		// TODO Auto-generated method stub
		List<HashMap<String, Object>> vo = selectEduMovieList(eduMovieVO);
		Map<String, Object> resultMap = EgovMap.decorate(vo.get(0));
		if (files.get("file_1").getSize() > 0) {
			fileDeleteFunction(resultMap);
			fileUploadFunction(eduMovieVO, files, user);
		}
		return eduMovieAdmMapper.updateEduMovie(eduMovieVO);
		
	}
	
	@SuppressWarnings("unchecked")
	public int deleteEduMovie(EduMovieVO eduMovieVO, Map<String, MultipartFile> files) throws Exception {
		List<HashMap<String, Object>> vo = selectEduMovieList(eduMovieVO);
		Map<String, Object> resultMap = EgovMap.decorate(vo.get(0));
		fileDeleteFunction(resultMap);
		
		return eduMovieAdmMapper.deleteEduMovie(eduMovieVO);
	}
	
    public List<EgovMap> selectRsvnsetNameList(EduMovieVO eduMovieVO){
    	return  eduMovieAdmMapper.selectRsvnsetNameList(eduMovieVO);
    }
	
	/**
	 * 동영상 강좌 썸네일을 등록한다
	 * @param eduMovieVO
	 * @param files
	 * @param user
	 * @throws Exception
	 */
	private void fileUploadFunction(EduMovieVO eduMovieVO, Map<String, MultipartFile> files, LoginVO user) throws Exception {
        List<FileVO> result = fileUtil.parseFileInf(files, "INS_", 0, "", "", user.getId(), "file_1");
        
        if (result != null && result.size() > 0) {
            FileVO fileId = result.get(0);
           eduMovieVO.setThumbPath(fileId.getFilePath());
           eduMovieVO.setThumbImg(fileId.getFileName());
           eduMovieVO.setThumbOrgimg(fileId.getOrginFileName());
        }
	}

	@SuppressWarnings("unused")
	private void fileDeleteFunction(Map<String, Object> resultMap) {
		File deleteFile = new File(GetRealUploadPath() + (String) resultMap.get("thumbPath") + (String) resultMap.get("thumbImg"));
		boolean deleteResult = deleteFile.delete();
		if(deleteResult) {
			log.debug("정상적으로 삭제됨");
		} else {
			log.debug("삭제된 파일 없음");
		}
	}

}

