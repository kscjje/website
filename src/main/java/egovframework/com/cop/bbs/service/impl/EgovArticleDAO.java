package egovframework.com.cop.bbs.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.cop.bbs.service.Board;
import egovframework.com.cop.bbs.service.BoardItemVO;
import egovframework.com.cop.bbs.service.BoardVO;

@Repository("EgovArticleDAO")
public class EgovArticleDAO extends EgovComAbstractDAO {

    public List<?> selectArticleList(BoardVO boardVO) {
        return list("BBSArticle.selectArticleList", boardVO);
    }

    public int selectArticleListCnt(BoardVO boardVO) {
        return (Integer) selectOne("BBSArticle.selectArticleListCnt", boardVO);
    }

    public int selectMaxInqireCo(BoardVO boardVO) {
        return (Integer) selectOne("BBSArticle.selectMaxInqireCo", boardVO);
    }

    public void updateInqireCo(BoardVO boardVO) {
        update("BBSArticle.updateInqireCo", boardVO);
    }

    public BoardVO selectArticleDetail(BoardVO boardVO) {
        return (BoardVO) selectOne("BBSArticle.selectArticleDetail", boardVO);
    }

    public BoardVO selectArticleReplyDetail(BoardVO boardVO) {
        return (BoardVO) selectOne("BBSArticle.selectArticleReplyDetail", boardVO);
    }

    public void replyArticle(Board board) {
        insert("BBSArticle.replyArticle", board);
    }

    public void updateReplyArticle(Board board) {
        update("BBSArticle.replyArticleUpdate", board);
    }

    public void insertArticle(Board board) {
        insert("BBSArticle.insertArticle", board);
    }

    public void updateArticle(Board board) {
        update("BBSArticle.updateArticle", board);
    }

    public void updateStatArticle(Board board) {
        update("BBSArticle.updateStatArticle", board);
    }

    public void deleteArticle(Board board) {
        update("BBSArticle.deleteArticle", board);

    }

    public void deleteArticleComplete(Board board) {
        delete("BBSArticle.deleteArticleComplete", board);

    }

    public List<BoardVO> selectNoticeArticleList(BoardVO boardVO) {
        return (List<BoardVO>) list("BBSArticle.selectNoticeArticleList", boardVO);
    }

    public List<?> selectGuestArticleList(BoardVO vo) {
        return list("BBSArticle.selectGuestArticleList", vo);
    }

    public int selectGuestArticleListCnt(BoardVO vo) {
        return (Integer) selectOne("BBSArticle.selectGuestArticleListCnt", vo);
    }

    public Map selectArticleNext(BoardVO vo) {
        return (Map) selectOne("BBSArticle.selectArticleNext", vo);
    }

    /*
     * 블로그 관련
     */
    public BoardVO selectArticleCnOne(BoardVO boardVO) {
        return (BoardVO) selectOne("BBSArticle.selectArticleCnOne", boardVO);
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<BoardVO> selectBlogNmList(BoardVO boardVO) {
        return (List<BoardVO>) list("BBSArticle.selectBlogNmList", boardVO);
    }

    @SuppressWarnings("deprecation")
    public List<?> selectBlogListManager(BoardVO vo) {
        return list("BBSArticle.selectBlogListManager", vo);
    }

    public int selectBlogListManagerCnt(BoardVO vo) {
        return (Integer) selectOne("BBSArticle.selectBlogListManagerCnt", vo);
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<BoardVO> selectArticleDetailDefault(BoardVO boardVO) {
        return (List<BoardVO>) list("BBSArticle.selectArticleDetailDefault", boardVO);
    }

    public int selectArticleDetailDefaultCnt(BoardVO boardVO) {
        return (Integer) selectOne("BBSArticle.selectArticleDetailDefaultCnt", boardVO);
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<BoardVO> selectArticleDetailCn(BoardVO boardVO) {
        return (List<BoardVO>) list("BBSArticle.selectArticleDetailCn", boardVO);
    }

    public int selectLoginUser(BoardVO boardVO) {
        return (Integer) selectOne("BBSArticle.selectLoginUser", boardVO);
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public List<BoardVO> selectNoticeArticleMainList(BoardVO boardVO) {
        return (List<BoardVO>) list("BBSArticle.selectNoticeArticleMainList", boardVO);
    }

    public BoardVO selectArticleBBSDetail(BoardVO boardVO) {
        return (BoardVO) selectOne("BBSArticle.selectArticleBBSDetail", boardVO);
    }

    public List<?> selectArticleNewList(BoardVO boardVO) {
        return list("BBSArticle.selectArticleNewList", boardVO);
    }

    public int selectArticleListNewCnt(BoardVO boardVO) {
        return (Integer) selectOne("BBSArticle.selectArticleListNewCnt", boardVO);
    }

    public List<BoardVO> selectNoticeArticleNewList(BoardVO boardVO) {
        return (List<BoardVO>) list("BBSArticle.selectNoticeArticleNewList", boardVO);
    }

    public BoardVO selectArticleDetailOne(BoardVO boardVO) {
        return (BoardVO) selectOne("BBSArticle.selectArticleDetailOne", boardVO);
    }

    public void insertBbsItemInfo(BoardItemVO item) {
        insert("BBSArticle.insertBbsItemInfo", item);
    }

    public void deleteBbsItemInfoByNttId(BoardItemVO item) {
        delete("BBSArticle.deleteBbsItemInfoByNttId", item);

    }

    public List<BoardItemVO> selectArticleDetailItemList(BoardVO boardVO) {
        return (List<BoardItemVO>) list("BBSArticle.selectArticleDetailItemList", boardVO);
    }
}
