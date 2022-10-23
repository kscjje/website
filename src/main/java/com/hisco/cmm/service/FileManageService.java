package com.hisco.cmm.service;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.mapper.FileCmmManageMapper;
import com.hisco.cmm.vo.FileVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : FileManageDAO.java
 * @Description : 파일정보 관리를 위한 데이터 처리 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 7. 23. 진수진 최초생성
 * @author 진수진
 * @since 2020. 7. 23.
 * @version
 * @see
 */
@Service("fileManageService")
public class FileManageService extends EgovAbstractServiceImpl {

    @Resource(name = "fileCmmManageMapper")
    private FileCmmManageMapper fileCmmManageMapper;

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     *
     * @param fileList
     * @return
     * @throws Exception
     */
    public String insertFileInfs(List<?> fileList) throws Exception {
        FileVO vo = (FileVO) fileList.get(0);
        String atchFileId = vo.getFileGrpinnb();

        fileCmmManageMapper.insertFileMaster(vo);

        Iterator<?> iter = fileList.iterator();
        while (iter.hasNext()) {
            vo = (FileVO) iter.next();

            fileCmmManageMapper.insertFileDetail(vo);
        }

        return atchFileId;
    }

    /**
     * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
     *
     * @param vo
     * @throws Exception
     */
    public void insertFileInf(FileVO vo) throws Exception {
        fileCmmManageMapper.insertFileMaster(vo);
        fileCmmManageMapper.insertFileDetail(vo);
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
     *
     * @param fileList
     * @throws Exception
     */
    public void updateFileInfs(List<?> fileList) throws Exception {
        FileVO vo;
        Iterator<?> iter = fileList.iterator();
        while (iter.hasNext()) {
            vo = (FileVO) iter.next();

            FileVO chkVO = fileCmmManageMapper.selectFileInf(vo);
            if (chkVO != null && !chkVO.getFileSn().equals("")) {
                fileCmmManageMapper.updateFileDetail(vo);
            } else {
                fileCmmManageMapper.insertFileDetail(vo);
            }

        }
    }

    /**
     * 여러 개의 파일을 삭제한다.
     *
     * @param fileList
     * @throws Exception
     */
    public void deleteFileInfs(List<?> fileList) throws Exception {
        Iterator<?> iter = fileList.iterator();
        FileVO vo;
        while (iter.hasNext()) {
            vo = (FileVO) iter.next();

            fileCmmManageMapper.deleteFileDetail(vo);
        }
    }

    /**
     * 하나의 파일을 삭제한다.
     *
     * @param fvo
     * @throws Exception
     */
    public void deleteFileInf(FileVO fvo) throws Exception {
        fileCmmManageMapper.deleteFileDetail(fvo);
    }

    /**
     * 파일에 대한 목록을 조회한다.
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public List<FileVO> selectFileInfs(FileVO vo) throws Exception {
        return (List<FileVO>) fileCmmManageMapper.selectFileList(vo);
    }

    /**
     * 파일 구분자에 대한 최대값을 구한다.
     *
     * @param fvo
     * @return
     * @throws Exception
     */
    public int getMaxFileSN(FileVO fvo) throws Exception {
        return (Integer) fileCmmManageMapper.getMaxFileSN(fvo);
    }

    /**
     * 파일에 대한 상세정보를 조회한다.
     *
     * @param fvo
     * @return
     * @throws Exception
     */
    public FileVO selectFileInf(FileVO fvo) throws Exception {
        return (FileVO) fileCmmManageMapper.selectFileInf(fvo);
    }

    /**
     * 전체 파일을 삭제한다.
     *
     * @param fvo
     * @throws Exception
     */
    public void deleteAllFileInf(FileVO fvo) throws Exception {
        fileCmmManageMapper.deleteFileGroup(fvo);
    }

    /**
     * 파일명 검색에 대한 목록을 조회한다.
     *
     * @param vo
     * @return
     * @throws Exception
     */

    public List<FileVO> selectFileListByFileNm(FileVO fvo) throws Exception {
        return (List<FileVO>) fileCmmManageMapper.selectFileListByFileNm(fvo);
    }

    /**
     * 파일명 검색에 대한 목록 전체 건수를 조회한다.
     *
     * @param fvo
     * @return
     * @throws Exception
     */
    public int selectFileListCntByFileNm(FileVO fvo) throws Exception {
        return (Integer) fileCmmManageMapper.selectFileListCntByFileNm(fvo);
    }

    /**
     * 이미지 파일에 대한 목록을 조회한다.
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public List<FileVO> selectImageFileList(FileVO vo) throws Exception {
        return (List<FileVO>) fileCmmManageMapper.selectImageFileList(vo);
    }
}
