package com.hisco.cmm.mapper;

import java.util.List;

import com.hisco.cmm.vo.FileVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

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
@Mapper("fileCmmManageMapper")
public interface FileCmmManageMapper {

    public int insertFileMaster(FileVO vo);

    public int insertFileDetail(FileVO vo);

    public int updateFileDetail(FileVO vo);

    public int deleteFileDetail(FileVO vo);

    /**
     * 파일에 대한 목록을 조회한다.
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public List<FileVO> selectFileList(FileVO vo);

    /**
     * 파일 구분자에 대한 최대값을 구한다.
     *
     * @param fvo
     * @return
     * @throws Exception
     */
    public int getMaxFileSN(FileVO fvo);

    /**
     * 파일에 대한 상세정보를 조회한다.
     *
     * @param fvo
     * @return
     * @throws Exception
     */
    public FileVO selectFileInf(FileVO fvo);

    /**
     * 전체 파일을 삭제한다.
     *
     * @param fvo
     * @throws Exception
     */
    public void deleteFileGroup(FileVO fvo);

    /**
     * 파일명 검색에 대한 목록을 조회한다.
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public List<FileVO> selectFileListByFileNm(FileVO fvo);

    /**
     * 파일명 검색에 대한 목록 전체 건수를 조회한다.
     *
     * @param fvo
     * @return
     * @throws Exception
     */
    public int selectFileListCntByFileNm(FileVO fvo);

    /**
     * 이미지 파일에 대한 목록을 조회한다.
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public List<FileVO> selectImageFileList(FileVO vo);
}
