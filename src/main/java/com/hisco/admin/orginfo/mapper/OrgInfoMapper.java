package com.hisco.admin.orginfo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hisco.admin.orginfo.vo.OrgContentsVO;
import com.hisco.admin.orginfo.vo.OrgDcVO;
import com.hisco.admin.orginfo.vo.OrgInfoVO;
import com.hisco.admin.orginfo.vo.OrgMemberDcVO;
import com.hisco.admin.orginfo.vo.OrgOptinfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : OrgInfoMapper.java
 * @Description : 이용기관 정보 관리 mapper
 * @author user
 * @since 2021. 10. 26.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Mapper("orgInfoMapper")
public interface OrgInfoMapper {

    public List<?> selectOrgInfoList(OrgInfoVO orgInfoVO);

    public List<?> selectOrgInfoListPaging(OrgInfoVO orgInfoVO);

    public OrgInfoVO selectOrgInfoDetail(OrgInfoVO orgInfoVO);

    public OrgInfoVO selectOrgInfoDetail(@Param("comcd") String comcd, @Param("orgNo") int orgNo);

    public OrgContentsVO selectOrgContents(OrgContentsVO orgContentsVO);

    public OrgOptinfoVO selectOrgOptinfo(OrgOptinfoVO orgOptinfoVO);

    public void insertOrgInfo(OrgInfoVO orgInfoVO);

    public void updateOrgInfo(OrgInfoVO orgInfoVO);

    public void deleteOrgInfo(OrgInfoVO orgInfoVO);

    // 기관 소개
    public OrgContentsVO selectOrgContentsDetail(OrgInfoVO orgInfoVO);

    public void insertOrgContents(OrgContentsVO orgContentsVO);

    public void updateOrgContents(OrgContentsVO orgContentsVO);

    public void updateOrgContentsGude(OrgContentsVO orgContentsVO);

    public void deleteOrgContents(OrgInfoVO orgInfoVO);

    // 기관소개 이미지 수정
    public void updateOrgContentsFileid(@Param("comcd") String comcd, @Param("orgNo") int orgNo,
            @Param("inputid") String inputid, @Param("atchFileId") String atchFileId, @Param("moduser") String moduser);

    // 기관설정 이미지 수정
    public void updateOrgOptinfoFileid(@Param("comcd") String comcd, @Param("orgNo") int orgNo,
            @Param("inputid") String inputid, @Param("atchFileId") String atchFileId, @Param("moduser") String moduser);

    // 기관 환경설정
    public OrgOptinfoVO selectOrgOptinfoDetail(OrgInfoVO orgInfoVO);

    public void insertOrgOptinfo(OrgOptinfoVO orgOptinfoVO);

    public void updateOrgOptinfo(OrgOptinfoVO orgOptinfoVO);

    public void deleteOrgOptinfo(OrgInfoVO orgInfoVO);

    // 감면기준 관리
    public List<OrgDcVO> selectDcList(@Param("comcd") String comcd, @Param("orgNo") int orgNo);

    public void deleteOrgDc(OrgInfoVO orgInfoVO);

    public void insertOrgDc(OrgDcVO orgDcVO);

    public List<OrgInfoVO> selectOrgInfoUserList(Map<String, Object> paramMap);

    public List<OrgMemberDcVO> selectOrgMemberDcList(OrgMemberDcVO param);
}
