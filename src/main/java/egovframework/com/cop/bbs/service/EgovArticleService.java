package egovframework.com.cop.bbs.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.fdl.cmmn.exception.FdlException;

public interface EgovArticleService {

    Map<String, Object> selectArticleList(BoardVO boardVO);

    BoardVO selectArticleDetail(BoardVO boardVO);

    BoardVO selectArticleDetailOne(BoardVO boardVO);

    BoardVO selectArticleDetailIncrease(BoardVO boardVO);

    BoardVO selectArticleReplyDetail(BoardVO boardVO);

    Map selectArticleNext(BoardVO vo);

    void insertArticle(Board board) throws FdlException;

    void updateArticle(Board board);

    void updateArticle(Board board, String deleteFiles) throws Exception;

    void deleteArticle(Board board) throws Exception;

    void deleteArticleComplete(Board board) throws Exception;

    List<BoardVO> selectNoticeArticleList(BoardVO boardVO);

    Map<String, Object> selectGuestArticleList(BoardVO vo);

    void updateStatArticle(Board board);

    /*
     * 블로그 관련
     */
    BoardVO selectArticleCnOne(BoardVO boardVO);

    List<BoardVO> selectBlogNmList(BoardVO boardVO);

    Map<String, Object> selectBlogListManager(BoardVO boardVO);

    List<BoardVO> selectArticleDetailDefault(BoardVO boardVO);

    int selectArticleDetailDefaultCnt(BoardVO boardVO);

    List<BoardVO> selectArticleDetailCn(BoardVO boardVO);

    int selectLoginUser(BoardVO boardVO);

    List<BoardVO> selectNoticeArticleMainList(BoardVO boardVO);

    BoardVO selectArticleBBSDetail(BoardVO boardVO);

    Map<String, Object> selectArticleNewList(BoardVO boardVO);

    int selectArticleListNewCnt(BoardVO boardVO);

    List<BoardVO> selectNoticeArticleNewList(BoardVO boardVO);

    /*
     * 추가항목 (BBS_ETC_ITEMINFO) 추가
     */
    void insertBbsItemInfo(BoardItemVO item) throws FdlException;

    void deleteBbsItemInfoByNttId(BoardItemVO item) throws Exception;

    List<BoardItemVO> selectArticleDetailItemList(BoardVO boardVO);
}
