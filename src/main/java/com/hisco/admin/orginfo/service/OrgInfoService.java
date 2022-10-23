package com.hisco.admin.orginfo.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.orginfo.mapper.OrgInfoMapper;
import com.hisco.admin.orginfo.vo.OrgContentsVO;
import com.hisco.admin.orginfo.vo.OrgDcVO;
import com.hisco.admin.orginfo.vo.OrgInfoVO;
import com.hisco.admin.orginfo.vo.OrgMemberDcVO;
import com.hisco.admin.orginfo.vo.OrgOptinfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : OrgInfoService.java
 * @Description : 이용기관 정보 관리 mapper
 * @author 진수진
 * @since 2021. 10. 26.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Slf4j
@Service("orgInfoService")
public class OrgInfoService extends EgovAbstractServiceImpl {

    @Resource(name = "orgInfoMapper")
    private OrgInfoMapper orgInfoMapper;

    public List<?> selectOrgInfoList(OrgInfoVO orgInfoVO) {
        return orgInfoMapper.selectOrgInfoList(orgInfoVO);
    }

    public List<?> selectOrgInfoListPaging(OrgInfoVO orgInfoVO) {
        return orgInfoMapper.selectOrgInfoListPaging(orgInfoVO);
    }

    public OrgInfoVO selectOrgInfoDetail(OrgInfoVO orgInfoVO) {
        return orgInfoMapper.selectOrgInfoDetail(orgInfoVO);
    }

    public OrgInfoVO selectOrgInfoDetail(String comcd, int orgNo) {
        return orgInfoMapper.selectOrgInfoDetail(comcd, orgNo);
    }

    public OrgContentsVO selectOrgContents(OrgContentsVO orgContentsVO) {
        return orgInfoMapper.selectOrgContents(orgContentsVO);
    }

    public OrgOptinfoVO selectOrgOptinfo(OrgOptinfoVO orgOptinfoVO) {

        OrgOptinfoVO optVO = orgInfoMapper.selectOrgOptinfo(orgOptinfoVO);

        return optVO;
    }

    public List<OrgDcVO> selectDcList(String comcd, int orgNo) {
        return orgInfoMapper.selectDcList(comcd, orgNo);
    }

    public void insertOrgInfo(OrgInfoVO orgInfoVO) {
        if (orgInfoVO.getParentOrgNo() > 0) {
            orgInfoVO.setParentComcd(orgInfoVO.getComcd());
        }
        orgInfoMapper.insertOrgInfo(orgInfoVO);

        OrgOptinfoVO optionInfo = orgInfoVO.getOrgOptinfo();
        OrgContentsVO contentsVO = orgInfoVO.getOrgContents();

        optionInfo.setComcd(orgInfoVO.getComcd());
        optionInfo.setOrgNo(orgInfoVO.getOrgNo());
        optionInfo.setReguser(orgInfoVO.getReguser());

        contentsVO.setComcd(orgInfoVO.getComcd());
        contentsVO.setOrgNo(orgInfoVO.getOrgNo());
        contentsVO.setReguser(orgInfoVO.getReguser());

        orgInfoMapper.insertOrgOptinfo(optionInfo);
        orgInfoMapper.insertOrgContents(contentsVO);

        if (orgInfoVO.getOrgDcList() != null) {
            for (OrgDcVO orgDcVO : orgInfoVO.getOrgDcList()) {
                if (orgDcVO.getDcYn() != null && !orgDcVO.getDcYn().equals("")) {
                    orgDcVO.setComcd(orgInfoVO.getComcd());
                    orgDcVO.setOrgNo(orgInfoVO.getOrgNo());
                    orgDcVO.setReguser(orgInfoVO.getReguser());
                    orgInfoMapper.insertOrgDc(orgDcVO);
                }
            }

        }
    }

    public void updateOrgDc(OrgInfoVO orgInfoVO) {
        orgInfoMapper.deleteOrgDc(orgInfoVO);

        if (orgInfoVO.getOrgDcList() != null) {
            for (OrgDcVO orgDcVO : orgInfoVO.getOrgDcList()) {
                if (orgDcVO.getDcYn() != null && !orgDcVO.getDcYn().equals("")) {
                    orgDcVO.setComcd(orgInfoVO.getComcd());
                    orgDcVO.setOrgNo(orgInfoVO.getOrgNo());
                    orgDcVO.setReguser(orgInfoVO.getReguser());
                    orgInfoMapper.insertOrgDc(orgDcVO);
                }
            }
        }
    }

    public void updateOrgInfo(OrgInfoVO orgInfoVO) {
        orgInfoMapper.updateOrgInfo(orgInfoVO);
    }

    public void updateOrgContents(OrgContentsVO contentsVO) {
        orgInfoMapper.updateOrgContents(contentsVO);
    }

    public void updateOrgContentsGude(OrgContentsVO orgContentsVO) {
        orgInfoMapper.updateOrgContentsGude(orgContentsVO);
    }

    public void updateOrgOptinfo(OrgOptinfoVO optionInfo) {
        orgInfoMapper.updateOrgOptinfo(optionInfo);
    }

    // 기관소개 이미지 수정
    public void updateOrgContentsFileid(String comcd, int orgNo, String inputid, String atchFileId, String moduser) {
        orgInfoMapper.updateOrgContentsFileid(comcd, orgNo, inputid, atchFileId, moduser);
    }

    // 기관 직인 이미지 수정
    public void updateOrgOptinfoFileid(String comcd, int orgNo, String inputid, String atchFileId, String moduser) {
        orgInfoMapper.updateOrgOptinfoFileid(comcd, orgNo, inputid, atchFileId, moduser);
    }

    public void deleteOrgInfo(OrgInfoVO orgInfoVO) {

        orgInfoMapper.deleteOrgContents(orgInfoVO);
        orgInfoMapper.deleteOrgOptinfo(orgInfoVO);
        orgInfoMapper.deleteOrgInfo(orgInfoVO);
        orgInfoMapper.deleteOrgDc(orgInfoVO);
    }

    public List<OrgInfoVO> selectOrgInfoUserList(Map<String, Object> paramMap) {
        return orgInfoMapper.selectOrgInfoUserList(paramMap);
    }

    public List<OrgMemberDcVO> selectOrgMemberDcList(OrgMemberDcVO param) {
        return orgInfoMapper.selectOrgMemberDcList(param);
    }
}
