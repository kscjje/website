package com.hisco.admin.eduadm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.comctgr.vo.ComCtgrVO;
import com.hisco.admin.eduadm.mapper.EduLimitMapper;
import com.hisco.admin.eduadm.vo.EdcLimitVO;
import com.hisco.admin.eduadm.vo.EdcProgramVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 기관별 교육프로그램예약제한설정 Service 구현 클래스
 *
 * @author 진수진
 * @since 2021.11.15
 * @version 1.0, 2021.11.15
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2021.11.15 최초작성
 */
@Service("eduLimitService")
public class EduLimitService extends EgovAbstractServiceImpl {

    @Resource(name = "eduLimitMapper")
    private EduLimitMapper eduLimitMapper;

    /**
     * 교육프로그램 목록을 조회한다.
     *
     * @param EdcProgramVO
     * @return List
     * @throws Exception
     */
    public EdcLimitVO selectEdcLimitRecord(EdcLimitVO paramMap) throws Exception {
        return eduLimitMapper.selectEdcLimitRecord(paramMap);
    }

    public List<ComCtgrVO> selectEdcLimitComctgList(EdcLimitVO paramMap) throws Exception {
        return eduLimitMapper.selectEdcLimitComctgList(paramMap);
    }

    public List<EdcProgramVO> selectEdcLimitProgramList(EdcLimitVO paramMap) throws Exception {
        return eduLimitMapper.selectEdcLimitProgramList(paramMap);
    }

    public int insertEdcLimit(EdcLimitVO paramMap, String[] listArr) throws Exception {

        int cnt = 0;

        int chk = eduLimitMapper.selectCountEdcLimit(paramMap);
        if (chk > 0) {
            cnt = eduLimitMapper.updateEdcLimit(paramMap);
        } else {
            cnt = eduLimitMapper.insertEdcLimit(paramMap);
        }

        if (cnt > 0) {
            if ("2001".equals(paramMap.getEdcRsvnlmitGbn())) {
                eduLimitMapper.deleteEdcLimitCtgcd(paramMap);

                for (String ctgCd : listArr) {
                    paramMap.setCtgCd(ctgCd);
                    eduLimitMapper.insertEdcLimitCtgcd(paramMap);
                }

            } else if ("3001".equals(paramMap.getEdcRsvnlmitGbn())) {
                eduLimitMapper.deleteEdcLimitProgram(paramMap);

                for (String prgId : listArr) {
                    paramMap.setEdcPrgmNo(Integer.parseInt(prgId));
                    eduLimitMapper.insertEdcLimitProgram(paramMap);
                }
            }
        }

        return cnt;

    }

}
