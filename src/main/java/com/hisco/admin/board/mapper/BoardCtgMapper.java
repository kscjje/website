package com.hisco.admin.board.mapper;

import java.util.List;

import com.hisco.admin.board.vo.BoardCtgVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 게시판 카테고리 관리 Mapper
 * 
 * @author 진수진
 * @since 2020.07.22
 * @version 1.0, 2020.07.22
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.22 최초작성
 */
@Mapper("boardCtgMapper")
public interface BoardCtgMapper {

    public List<?> selectCtgInfList(BoardCtgVO boardCtgVO);

    public void insertCtgInf(BoardCtgVO boardCtgVO);

    public void updateCtgInf(BoardCtgVO boardCtgVO);

    public BoardCtgVO selectCtgInfDetail(BoardCtgVO boardCtgVO);

    public void deleteCtgInf(BoardCtgVO boardCtgVO);

    public void clearArticleCtg(BoardCtgVO boardCtgVO);

}
