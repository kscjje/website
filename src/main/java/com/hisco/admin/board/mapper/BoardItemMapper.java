package com.hisco.admin.board.mapper;

import java.util.List;

import egovframework.com.cop.bbs.service.BoardItemVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 게시판 추가입력항목 관리 Mapper
 * 
 * @author 이윤호
 * @since 2021.11.08
 * @version 1.0, 2021.11.08
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          이윤호 2021.11.08 최초작성
 */
@Mapper("boardItemMapper")
public interface BoardItemMapper {

    public List<BoardItemVO> selectItemInfList(BoardItemVO boardItemVO);

    public void insertItemInf(BoardItemVO boardItemVO);

    public void updateItemInf(BoardItemVO boardItemVO);

    public BoardItemVO selectItemInfDetail(BoardItemVO boardItemVO);

    public void deleteItemInf(BoardItemVO boardItemVO);
}
