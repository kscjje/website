package com.hisco.admin.board.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.hisco.admin.board.vo.BoardCtgVO;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.BoardItemVO;
import egovframework.com.cop.bbs.service.BoardMaster;
import egovframework.com.cop.bbs.service.EgovBBSMasterService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * 게시판 Master Service 구현 클래스
 *
 * @author 이윤호
 * @since 2021.11.08
 * @version 1.0, 2021.11.08
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          이윤호 2021.11.08 최초작성
 */
@Service("BoardMasterService")
public class BoardMasterService extends EgovAbstractServiceImpl {

    @Resource(name = "EgovBBSMasterService")
    private EgovBBSMasterService egovBBSMasterService;

    @Resource(name = "boardCtgService")
    private BoardCtgService boardCtgService;

    @Resource(name = "boardItemService")
    private BoardItemService boardItemService;

    @Resource(name = "egovBbsCtgIdGnrService")
    private EgovIdGnrService idgenServiceBbsCtg;

    /**
     * 마스터 저장 일괄 처리
     *
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public void masterSave(BoardMaster boardMaster, HttpServletRequest request, HttpServletResponse response,
            CommandMap commandMap, ModelMap model)
            throws Exception {

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        // 수정모드
        if (commandMap.getString("mode").equals("edit")) {
            egovBBSMasterService.updateBBSMasterInf(boardMaster);
        } else {
            egovBBSMasterService.insertBBSMasterInf(boardMaster);
        }

        // 게시판 카테고리 작업 시작
        String[] ctgIdArr = request.getParameterValues("ctgId");
        String[] ctgNmArr = request.getParameterValues("ctgNm");
        String[] ctgOrderArr = request.getParameterValues("ctgOrder");
        String[] ctgYnArr = request.getParameterValues("ctgYn");

        if (ctgIdArr != null && ctgIdArr.length > 0) {
            for (int i = 0; i < ctgIdArr.length; i++) {
                BoardCtgVO boardCtgVO = new BoardCtgVO();
                boardCtgVO.setBbsId(boardMaster.getBbsId());
                boardCtgVO.setCtgNm(ctgNmArr[i]);
                boardCtgVO.setCtgSort(ctgOrderArr[i]);
                boardCtgVO.setUseAt(ctgYnArr[i]);
                boardCtgVO.setFrstRegisterId((user == null || user.getId() == null) ? "" : user.getId());

                String ctgId = ctgIdArr[i];
                if (StringUtil.IsEmpty(ctgId)) {
                    boardCtgService.insertBoardCtg(boardCtgVO);
                } else {
                    boardCtgVO.setCtgId(ctgId);
                    boardCtgVO.setLastUpdusrId((user == null || user.getId() == null) ? "" : user.getId());
                    boardCtgService.updateBoardCtg(boardCtgVO);
                }
            }

        }

        String[] delCtgIdArr = request.getParameterValues("delCtgId");
        if (delCtgIdArr != null && delCtgIdArr.length > 0) {
            for (int i = 0; i < delCtgIdArr.length; i++) {
                String ctgId = delCtgIdArr[i];
                if (!StringUtil.IsEmpty(ctgId)) {
                    BoardCtgVO boardCtgVO = new BoardCtgVO();
                    boardCtgVO.setBbsId(boardMaster.getBbsId());
                    boardCtgVO.setCtgId(ctgId);
                    boardCtgVO.setLastUpdusrId((user == null || user.getId() == null) ? "" : user.getId());
                    boardCtgService.clearArticleCtg(boardCtgVO); // 등록된 게시물의 카테고리 초기화
                    boardCtgService.deleteBoardCtg(boardCtgVO);
                }
            }
        }

        // 게시판 추가입력항목 시작
        String[] itemIdArr = request.getParameterValues("itemId");
        String[] itemEnIdArr = request.getParameterValues("itemEnId");
        String[] itemTypeArr = request.getParameterValues("itemType");
        String[] itemNmArr = request.getParameterValues("itemNm");
        String[] itemOrderArr = request.getParameterValues("itemOrder");

        if (itemIdArr != null && itemIdArr.length > 0) {
            for (int i = 0; i < itemIdArr.length; i++) {
                BoardItemVO boardItemVO = new BoardItemVO();
                boardItemVO.setBbsId(boardMaster.getBbsId());
                boardItemVO.setBbsItemType(itemTypeArr[i]);
                boardItemVO.setBbsItemEnid(itemEnIdArr[i]);
                boardItemVO.setBbsItemNm(itemNmArr[i]);
                boardItemVO.setBbsItemSort(itemOrderArr[i]);

                String itemId = itemIdArr[i];
                if (StringUtil.IsEmpty(itemId)) {
                    boardItemService.insertBoardItem(boardItemVO);
                } else {
                    boardItemVO.setBbsEtcseq(StringUtil.String2Int(itemId, 0));
                    boardItemService.updateBoardItem(boardItemVO);

                }
            }

        }

        String[] delItemIdArr = request.getParameterValues("delItemId");
        if (delItemIdArr != null && delItemIdArr.length > 0) {
            for (int i = 0; i < delItemIdArr.length; i++) {
                String itemId = delItemIdArr[i];
                if (!StringUtil.IsEmpty(itemId)) {
                    BoardItemVO boardItemVO = new BoardItemVO();
                    boardItemVO.setBbsId(boardMaster.getBbsId());
                    boardItemVO.setBbsEtcseq(StringUtil.String2Int(itemId, 0));
                    boardItemService.deleteBoardItem(boardItemVO);
                }
            }
        }

    }
}
