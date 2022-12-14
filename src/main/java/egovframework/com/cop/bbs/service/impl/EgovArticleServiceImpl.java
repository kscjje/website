package egovframework.com.cop.bbs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cop.bbs.service.Board;
import egovframework.com.cop.bbs.service.BoardItemVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovArticleService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("EgovArticleService")
public class EgovArticleServiceImpl extends EgovAbstractServiceImpl implements EgovArticleService {

    @Resource(name = "EgovArticleDAO")
    private EgovArticleDAO egovArticleDao;

    @Resource(name = "FileMngService")
    private FileMngService fileService;

    /*
     * @Resource(name = "propertiesService")
     * protected EgovPropertyService propertyService;
     */

    @Resource(name = "egovNttIdGnrService")
    private EgovIdGnrService nttIdgenService;

    @Override
    public Map<String, Object> selectArticleList(BoardVO boardVO) {
        List<?> list = egovArticleDao.selectArticleList(boardVO);

        int cnt = egovArticleDao.selectArticleListCnt(boardVO);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("resultList", list);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }

    @Override
    public BoardVO selectArticleDetail(BoardVO boardVO) {
        return egovArticleDao.selectArticleDetail(boardVO);
    }

    @Override
    public BoardVO selectArticleDetailOne(BoardVO boardVO) {
        return egovArticleDao.selectArticleDetailOne(boardVO);
    }

    @Override
    public BoardVO selectArticleDetailIncrease(BoardVO boardVO) {
        egovArticleDao.updateInqireCo(boardVO);

        return egovArticleDao.selectArticleBBSDetail(boardVO);
    }

    public Map selectArticleNext(BoardVO vo) {
        return egovArticleDao.selectArticleNext(vo);
    }

    @Override
    public BoardVO selectArticleCnOne(BoardVO boardVO) {
        return egovArticleDao.selectArticleCnOne(boardVO);
    }

    @Override
    public List<BoardVO> selectArticleDetailDefault(BoardVO boardVO) {
        return egovArticleDao.selectArticleDetailDefault(boardVO);
    }

    @Override
    public BoardVO selectArticleReplyDetail(BoardVO boardVO) {
        return egovArticleDao.selectArticleReplyDetail(boardVO);
    }

    @Override
    public int selectArticleDetailDefaultCnt(BoardVO boardVO) {
        return egovArticleDao.selectArticleDetailDefaultCnt(boardVO);
    }

    @Override
    public List<BoardVO> selectArticleDetailCn(BoardVO boardVO) {
        return egovArticleDao.selectArticleDetailCn(boardVO);
    }

    @Override
    public void insertArticle(Board board) throws FdlException {

        if ("Y".equals(board.getReplyAt())) {
            // ????????? ?????? 1. Parnts??? ??????, 2.Parnts??? sortOrdr??? ???????????? sortOrdr??? ???????????????, 3.nttNo??? ?????? ???????????? ????????????
            // replyLc??? ???????????? ReplyLc + 1

            board.setNttId(nttIdgenService.getNextIntegerId()); // ????????? ?????? nttId ??????
            egovArticleDao.replyArticle(board);
            egovArticleDao.updateReplyArticle(board); // sortOrdr ????????????

        } else {
            // ????????? ???????????? Parnts = 0, replyLc??? = 0, sortOrdr = nttNo(Query?????? ??????)
            board.setParnts("0");
            board.setReplyLc("0");
            board.setReplyAt("N");
            if (board.getNttId() == 0) {
                board.setNttId(nttIdgenService.getNextIntegerId());// 2011.09.22
            }

            egovArticleDao.insertArticle(board);
        }
    }

    @Override
    public void updateArticle(Board board, String deleteFiles) throws Exception {
        List<FileVO> list = new ArrayList<FileVO>();

        if (deleteFiles != null && deleteFiles.length() > 0) {
            for (String fileSn : deleteFiles.split(",")) {
                FileVO fvo = new FileVO();
                fvo.setFileGrpinnb(board.getAtchFileId());
                fvo.setFileSn(fileSn);
                list.add(fvo);
            }
        }
        fileService.deleteFileInfs(list);
        egovArticleDao.updateArticle(board);
    }

    @Override
    public void updateArticle(Board board) {
        egovArticleDao.updateArticle(board);
    }

    public void updateStatArticle(Board board) {
        egovArticleDao.updateStatArticle(board);
    }

    @Override
    public void deleteArticle(Board board) throws Exception {
        FileVO fvo = new FileVO();

        fvo.setFileGrpinnb(board.getAtchFileId());

        // board.setNttSj("??? ?????? ???????????? ????????? ?????????????????????.");

        egovArticleDao.deleteArticle(board);

        if (!"".equals(fvo.getFileGrpinnb()) || fvo.getFileGrpinnb() != null) {
            fileService.deleteAllFileInf(fvo);
        }

    }

    @Override
    public void deleteArticleComplete(Board board) throws Exception {

        // ???????????? ?????? ??????
        BoardItemVO item = new BoardItemVO();
        item.setBbsId(board.getBbsId());
        item.setNttId(board.getNttId());
        egovArticleDao.deleteBbsItemInfoByNttId(item);

        FileVO fvo = new FileVO();

        fvo.setFileGrpinnb(board.getAtchFileId());

        egovArticleDao.deleteArticleComplete(board);

        if (!"".equals(fvo.getFileGrpinnb()) || fvo.getFileGrpinnb() != null) {
            fileService.deleteAllFileInf(fvo);
        }
    }

    @Override
    public List<BoardVO> selectNoticeArticleList(BoardVO boardVO) {
        return egovArticleDao.selectNoticeArticleList(boardVO);
    }

    @Override
    public List<BoardVO> selectBlogNmList(BoardVO boardVO) {
        return egovArticleDao.selectBlogNmList(boardVO);
    }

    @Override
    public Map<String, Object> selectGuestArticleList(BoardVO vo) {
        List<?> list = egovArticleDao.selectGuestArticleList(vo);

        int cnt = egovArticleDao.selectGuestArticleListCnt(vo);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("resultList", list);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }

    @Override
    public int selectLoginUser(BoardVO boardVO) {
        return egovArticleDao.selectLoginUser(boardVO);
    }

    @Override
    public Map<String, Object> selectBlogListManager(BoardVO vo) {
        List<?> result = egovArticleDao.selectBlogListManager(vo);
        int cnt = egovArticleDao.selectBlogListManagerCnt(vo);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }

    @Override
    public List<BoardVO> selectNoticeArticleMainList(BoardVO boardVO) {
        return egovArticleDao.selectNoticeArticleMainList(boardVO);
    }

    @Override
    public BoardVO selectArticleBBSDetail(BoardVO boardVO) {
        return egovArticleDao.selectArticleBBSDetail(boardVO);
    }

    @Override
    public Map<String, Object> selectArticleNewList(BoardVO boardVO) {
        List<?> list = egovArticleDao.selectArticleNewList(boardVO);

        int cnt = egovArticleDao.selectArticleListNewCnt(boardVO);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("resultList", list);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }

    @Override
    public int selectArticleListNewCnt(BoardVO boardVO) {
        return egovArticleDao.selectArticleListNewCnt(boardVO);
    }

    @Override
    public List<BoardVO> selectNoticeArticleNewList(BoardVO boardVO) {
        return egovArticleDao.selectNoticeArticleNewList(boardVO);
    }

    @Override
    public void insertBbsItemInfo(BoardItemVO item) throws FdlException {
        egovArticleDao.insertBbsItemInfo(item);
    }

    @Override
    public void deleteBbsItemInfoByNttId(BoardItemVO item) throws Exception {

        egovArticleDao.deleteBbsItemInfoByNttId(item);
    }

    @Override
    public List<BoardItemVO> selectArticleDetailItemList(BoardVO boardVO) {
        return egovArticleDao.selectArticleDetailItemList(boardVO);
    }

}
