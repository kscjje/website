package com.hisco.admin.board.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.board.mapper.BoardCtgMapper;
import com.hisco.admin.board.vo.BoardCtgVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * 게시판 카테고리 관리 Service 구현 클래스
 * 
 * @author 진수진
 * @since 2020.07.22
 * @version 1.0, 2020.07.22
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.22 최초작성
 */
@Service("boardCtgService")
public class BoardCtgService extends EgovAbstractServiceImpl {

    @Resource(name = "boardCtgMapper")
    private BoardCtgMapper boardCtgMapper;

    @Resource(name = "egovBbsCtgIdGnrService")
    private EgovIdGnrService idgenServiceBbsCtg;

    public List<?> selectBoardCtgList(BoardCtgVO boardCtgVO) {
        return boardCtgMapper.selectCtgInfList(boardCtgVO);
    }

    public void insertBoardCtg(BoardCtgVO boardCtgVO) throws Exception {
        boardCtgVO.setCtgId(idgenServiceBbsCtg.getNextStringId());

        boardCtgMapper.insertCtgInf(boardCtgVO);
    }

    public void updateBoardCtg(BoardCtgVO boardCtgVO) throws Exception {
        boardCtgMapper.updateCtgInf(boardCtgVO);
    }

    public BoardCtgVO selectBoardCtg(BoardCtgVO boardCtgVO) throws Exception {
        return (BoardCtgVO) boardCtgMapper.selectCtgInfDetail(boardCtgVO);
    }

    public void deleteBoardCtg(BoardCtgVO boardCtgVO) throws Exception {
        boardCtgMapper.deleteCtgInf(boardCtgVO);
    }

    public void clearArticleCtg(BoardCtgVO boardCtgVO) throws Exception {
        boardCtgMapper.clearArticleCtg(boardCtgVO);
    }

}
