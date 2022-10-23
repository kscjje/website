package com.hisco.admin.code.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.hisco.admin.code.mapper.CodeManageMapper;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.vo.CodeVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 공통코드 조회 및 등록 구현클래스
 * 
 * @author 진수진
 * @since 2021.03.24
 * @version 1.0, 2021.03.24
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2021.03.24 최초작성
 */
@Service("codeManageService")
public class CodeManageService extends EgovAbstractServiceImpl {

    @Resource(name = "codeManageMapper")
    private CodeManageMapper codeManageMapper;

    /**
     * 공통코드 그룹을 조회한다
     * 
     * @param vo
     *            CodeVO
     * @return List
     * @exception Exception
     */
    public List<?> selectCodeGrpList(CodeVO vo) throws Exception {
        // 기본 정렬 추가
        if (vo.getSearchOrder() == null || vo.getSearchOrder().length() < 1) {
            vo.setSearchOrder("GRP_CD");
        }
        if (vo.getSearchOrderDir() == null || vo.getSearchOrderDir().length() < 1) {
            vo.setSearchOrderDir("ASC");
        }

        return codeManageMapper.selectCodeGrpList(vo);
    }

    
    public List<HashMap<String, Object>> selectCodeGrpList2(HashMap<String, Object> parameter) throws Exception {
        return codeManageMapper.selectCodeGrpList2(parameter);
    }
    
    /**
     * 공통코드 그룹의 갯수를 조회한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int selectCodeGrpCnt(CodeVO vo) throws Exception {
        return (Integer) codeManageMapper.selectCodeGrpCount(vo);
    }

    /**
     * 공통코드 그룹 상세를 조회한다
     * 
     * @param vo
     *            CodeVO
     * @return List
     * @exception Exception
     */
    public CodeVO selectCodeGrpDetail(CodeVO vo) throws Exception {
        return (CodeVO) codeManageMapper.selectCodeGrpDetail(vo);
    }

    /**
     * 공통코드 그룹을 신규 생성한다
     * 
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public String insertCodeGrp(CodeVO vo) throws Exception {
        CodeVO data = (CodeVO) codeManageMapper.selectCodeGrpDetail(vo);
        String result = "";

        if (data != null) {
            result = "ER|동일한 그룹코드가 존재합니다.";
        } else {
            int n = codeManageMapper.insertCodeGrp(vo);

            if (n > 0) {
                result = "OK";
            } else {
                result = "ER|데이타입력 오류";
            }
        }

        return result;
    }

    /**
     * 공통코드 그룹 을 삭제한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int deleteCodeGrp(CodeVO vo) throws Exception {
        return codeManageMapper.deleteCodeGrp(vo);
    }

    /**
     * 공통코드 그룹을 수정한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int updateCodeGrp(CodeVO vo) throws Exception {
        return codeManageMapper.updateCodeGrp(vo);
    }

    /**
     * 공통코드 상세목록을 조회한다
     * 
     * @param vo
     *            CodeVO
     * @return List
     * @exception Exception
     */
    public List<?> selectCodeDetailList(CodeVO vo) throws Exception {
        return codeManageMapper.selectCodeDetailList(vo);
    }

    /**
     * 공통코드 상세를 조회한다
     * 
     * @param vo
     *            CodeVO
     * @return List
     * @exception Exception
     */
    public CodeVO selectCodeDetail(CodeVO vo) throws Exception {
        return (CodeVO) codeManageMapper.selectCodeDetail(vo);
    }

    /**
     * 공통코드 상세데이타를 신규 생성한다
     * 
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public String insertCodeDetail(CodeVO vo) throws Exception {
        CodeVO data = (CodeVO) codeManageMapper.selectCodeDetail(vo);
        String result = "";

        if (data != null) {
            result = "ER|동일한 코드가 존재합니다.";
        } else {
            int n = codeManageMapper.insertCodeDetail(vo);

            if (n > 0) {
                result = "OK";
            } else {
                result = "ER|데이타입력 오류";
            }
        }
        return result;
    }

    /**
     * 공통코드 상세를 삭제한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int deleteCodeDetail(CodeVO vo) throws Exception {
        return codeManageMapper.deleteCodeDetail(vo);
    }

    /**
     * 공통코드 상세 데이타를 수정한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int updateCodeDetail(CodeVO vo) throws Exception {
        return codeManageMapper.updateCodeDetail(vo);
    }

    /**
     * 공통코드 상세 데이타 사용여부만 수정한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int useCodeDetail(CodeVO vo) throws Exception {
        return codeManageMapper.useCodeDetail(vo);
    }

    /**
     * 공통코드 상세 순서를 일괄 수정한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public void sortAllCodeDetail(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        CodeVO codeVO = new CodeVO();
        codeVO.setComcd(Config.COM_CD);
        codeVO.setReguser((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

        String grpCd = request.getParameter("grpCd");
        codeVO.setGrpCd(grpCd);

        String[] cdArr = request.getParameterValues("cd");
        String[] sortOrderArr = request.getParameterValues("sortOrder");

        for (int i = 0; i < cdArr.length; i++) {
            String cd = cdArr[i];
            long sortOrder = StringUtil.String2Long(sortOrderArr[i], 0);
            codeVO.setCd(cd);
            codeVO.setSortOrder(sortOrder);
            codeManageMapper.sortCodeDetail(codeVO);
        }
    }

    /**
     * 기관별코드 상세목록을 조회한다
     * 
     * @param vo
     *            CodeVO
     * @return List
     * @exception Exception
     */
    public List<?> selectOrgCodeDetailList(CodeVO vo) throws Exception {
        return codeManageMapper.selectOrgCodeDetailList(vo);
    }

    /**
     * 기관별코드 상세를 조회한다
     * 
     * @param vo
     *            CodeVO
     * @return List
     * @exception Exception
     */
    public CodeVO selectOrgCodeDetail(CodeVO vo) throws Exception {
        return (CodeVO) codeManageMapper.selectOrgCodeDetail(vo);
    }

    /**
     * 기관별코드 상세데이타를 신규 생성한다
     * 
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public String insertOrgCodeDetail(CodeVO vo) throws Exception {
        CodeVO data = (CodeVO) codeManageMapper.selectOrgCodeDetail(vo);
        String result = "";

        if (data != null) {
            result = "ER|동일한 코드가 존재합니다.";
        } else {
            int n = codeManageMapper.insertOrgCodeDetail(vo);

            if (n > 0) {
                result = "OK";
            } else {
                result = "ER|데이타입력 오류";
            }
        }
        return result;
    }

    /**
     * 기관별코드 상세를 삭제한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int deleteOrgCodeDetail(CodeVO vo) throws Exception {
        return codeManageMapper.deleteOrgCodeDetail(vo);
    }

    /**
     * 기관별코드 상세 데이타를 수정한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int updateOrgCodeDetail(CodeVO vo) throws Exception {
        return codeManageMapper.updateOrgCodeDetail(vo);
    }

    /**
     * 기관별코드 상세 데이타 사용여부만 수정한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int useOrgCodeDetail(CodeVO vo) throws Exception {
        return codeManageMapper.useOrgCodeDetail(vo);
    }

    /**
     * 공통코드 상세 순서를 일괄 수정한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public void sortAllOrgCodeDetail(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        CodeVO codeVO = new CodeVO();
        codeVO.setComcd(Config.COM_CD);
        codeVO.setReguser((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

        String grpCd = request.getParameter("grpCd");
        codeVO.setGrpCd(grpCd);

        int orgNo = StringUtil.String2Int(request.getParameter("orgNo"), 0);
        codeVO.setOrgNo(orgNo);

        String[] cdArr = request.getParameterValues("cd");
        String[] sortOrderArr = request.getParameterValues("sortOrder");

        for (int i = 0; i < cdArr.length; i++) {
            String cd = cdArr[i];
            long sortOrder = StringUtil.String2Long(sortOrderArr[i], 0);
            codeVO.setCd(cd);
            codeVO.setSortOrder(sortOrder);
            codeManageMapper.sortOrgCodeDetail(codeVO);
        }
    }
}
