package com.hisco.user.main.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.user.evtrsvn.service.EvtRsvnItemVO;
import com.hisco.user.evtrsvn.service.EvtrsvnMstVO;
import com.hisco.user.exbtrsvn.service.ExbtChargeVO;
import com.hisco.user.exbtrsvn.service.RsvnMasterVO;
import com.hisco.user.main.service.MainSearchVO;
import com.hisco.user.main.service.MainService;

import egovframework.com.cmm.LoginVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 강연/행사/영화 예약 서비 구현
 * 
 * @author 진수진
 * @since 2020.09.01
 * @version 1.0, 2020.09.01
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          김희택 2020.09.01 최초작성
 */
@Service("mainService")
public class MainServiceImpl extends EgovAbstractServiceImpl implements MainService {

    @Resource(name = "commonDAO")
    private CommonDAO commonDAO;

    /**
     * 팝업 목록을 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    public List<CamelMap> selectMainPopupList() throws Exception {
        return (List<CamelMap>) commonDAO.queryForList("MainDAO.selectMainPopup", null);
    }

    /**
     * 기준설정 데이타를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> selectMainSearch(MainSearchVO vo) throws Exception {
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        List<MainSearchVO> rList = null;
        int listSize = 0;
        int listSize1 = commonDAO.queryForInt("MainDAO.countMainSearch", vo);
        int listSize2 = commonDAO.queryForInt("MainDAO.countMainBoardSearch", vo);
        String type = vo.getSearchType();
        if ("rsv".equals(type)) {
            rList = (List<MainSearchVO>) commonDAO.queryForList("MainDAO.selectMainSearch", vo);
            listSize = listSize1;
        } else {
            rList = (List<MainSearchVO>) commonDAO.queryForList("MainDAO.selectMainBoardSearch", vo);
            listSize = listSize2;
        }

        rtnMap.put("rList", rList);
        rtnMap.put("listsize", listSize);
        rtnMap.put("listsize1", listSize1);
        rtnMap.put("listsize2", listSize2);
        return rtnMap;
    }

    /**
     * 오늘의 티켓목록을 조회한다
     * 
     * @param vo
     *            LoginVO
     * @return List
     * @exception Exception
     */
    public List<CamelMap> selectTodayTicketList(LoginVO loginVO) throws Exception {
        List<CamelMap> list = (List<CamelMap>) commonDAO.queryForList("MainDAO.selectTodayTicketList", loginVO);
        if (list != null) {
            for (CamelMap data : list) {
                String gubun = CommonUtil.getString(data.get("gubun"));

                if (gubun.equals("EXBT")) {
                    RsvnMasterVO vo = new RsvnMasterVO();
                    vo.setRsvnIdx(CommonUtil.getString(data.get("rsvnIdx")));
                    vo.setComcd(Config.COM_CD);
                    List<ExbtChargeVO> itemList = (List<ExbtChargeVO>) commonDAO.queryForList("DspyDsDAO.selectReserveItemList", vo);
                    data.put("itemList", itemList);
                } else {
                    EvtrsvnMstVO vo = new EvtrsvnMstVO();
                    vo.setEvtRsvnIdx(CommonUtil.getString(data.get("rsvnIdx")));
                    vo.setComcd(Config.COM_CD);
                    List<EvtRsvnItemVO> itemList = (List<EvtRsvnItemVO>) commonDAO.queryForList("EvtrsvnSMainDAO.selectEvtrsvnItem", vo);
                    data.put("itemList", itemList);
                }
            }
        }

        return list;
    }

    /**
     * 관람 선택 1개
     * 
     * @param
     * @return String
     * @exception Exception
     */
    public String selectDspyPartCd() throws Exception {
        return (String) commonDAO.queryForObject("MainDAO.selectDspyPartCd", null);
    }

    /**
     * BBS 리스트 조회
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectBBSList(Map<String, Object> paramMap) throws Exception {
        return commonDAO.queryForList("MainDAO.selectBBSList", paramMap);
    }
}
