package com.hisco.admin.board.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.board.mapper.BoardItemMapper;

import egovframework.com.cop.bbs.service.BoardItemVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * 게시판 추가입력항목 Service 구현 클래스
 *
 * @author 이윤호
 * @since 2021.11.08
 * @version 1.0, 2021.11.08
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          이윤호 2021.11.08 최초작성
 */
@Service("boardItemService")
public class BoardItemService extends EgovAbstractServiceImpl {

    @Resource(name = "boardItemMapper")
    private BoardItemMapper boardItemMapper;

    @Resource(name = "egovBbsItemIdGnrService")
    private EgovIdGnrService idgenServiceBbsItem;

    public List<BoardItemVO> selectBoardItemList(BoardItemVO BoardItemVO) {
        return boardItemMapper.selectItemInfList(BoardItemVO);
    }

    public void insertBoardItem(BoardItemVO BoardItemVO) throws Exception {
        BoardItemVO.setBbsEtcseq(idgenServiceBbsItem.getNextIntegerId());
        // BoardItemVO.setBbsItemEnid(idgenServiceBbsItem.getNextStringId());

        boardItemMapper.insertItemInf(BoardItemVO);
    }

    public void updateBoardItem(BoardItemVO BoardItemVO) throws Exception {
        boardItemMapper.updateItemInf(BoardItemVO);
    }

    public BoardItemVO selectBoardItem(BoardItemVO BoardItemVO) throws Exception {
        return (BoardItemVO) boardItemMapper.selectItemInfDetail(BoardItemVO);
    }

    public void deleteBoardItem(BoardItemVO BoardItemVO) throws Exception {
        boardItemMapper.deleteItemInf(BoardItemVO);
    }

}
