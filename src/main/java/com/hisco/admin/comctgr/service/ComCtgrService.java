package com.hisco.admin.comctgr.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.comctgr.mapper.ComCtgrMapper;
import com.hisco.admin.comctgr.vo.ComCtgrVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 카테고리 관리 Service 구현 클래스
 *
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 */
@Service("comCtgrService")
public class ComCtgrService extends EgovAbstractServiceImpl {

    @Resource(name = "comCtgrMapper")
    private ComCtgrMapper comCtgrMapper;

    /**
     * 카테고리 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectComctgrList(Map<String, Object> paramMap) throws Exception {
        return comCtgrMapper.selectComCtgrList(paramMap);
    }

    /**
     * 카테고리 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectComctgrListForTree(ComCtgrVO paramMap) throws Exception {
        return comCtgrMapper.selectComCtgrListForTree(paramMap);
    }


    /**
     * 카테고리 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public ComCtgrVO selectComctgrDetail(ComCtgrVO comCtgrVO) throws Exception {
        return comCtgrMapper.selectComCtgrDetail(comCtgrVO);
    }

    /**
     * 카테고리 관리 정보를 등록한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int insertComCtgr(ComCtgrVO comCtgrVO) throws Exception {
        return comCtgrMapper.insertComCtgr(comCtgrVO);
    }


    /**
     * 카테고리 관리 정보를 수정한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int updateComCtgr(ComCtgrVO comCtgrVO) throws Exception {
        return comCtgrMapper.updateComCtgr(comCtgrVO);
    }

    /**
     * 카테고리 관리 정보를 삭제한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int deleteComctgr(ComCtgrVO comCtgrVO) throws Exception {
    	comCtgrMapper.deleteComCtgrTop(comCtgrVO);
    	comCtgrMapper.deleteComCtgrPrnct(comCtgrVO);

        return comCtgrMapper.deleteComCtgr(comCtgrVO);
    }

    /**
     * 카테고리 정렬 순서를 수정한다.
     *
     * @param comcd
     *
     * @return List
     * @throws Exception
     */
    public int updateComCtgrSort(String comcd , String[] listCtgCd , String moduser) throws Exception {
    	int cnt = 0;
    	if(listCtgCd != null){
    		ComCtgrVO comCtgrVO = new ComCtgrVO();
        	comCtgrVO.setComcd(comcd);
        	comCtgrVO.setSortOrder(0); // 0으로 모두 초기화
        	comCtgrVO.setModuser(moduser);

        	comCtgrMapper.updateComCtgrSortDefault(comCtgrVO);


        	for(String ctgCd : listCtgCd){
            	comCtgrVO.setCtgCd(ctgCd);
            	cnt+=comCtgrMapper.updateComCtgrSortAuto(comCtgrVO);
        	}
    	}


        return cnt;
    }



}
