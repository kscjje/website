/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.instrctr.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hisco.admin.instrctr.mapper.InstrctrMapper;
import com.hisco.admin.instrctr.vo.InstrctrVO;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : InstrctrService.java
 * @Description : 강사관리 Service
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 5
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Service("instrctrService")
public class InstrctrService extends EgovAbstractServiceImpl {

    @Resource(name = "instrctrMapper")
    private InstrctrMapper instrctrMapper;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    public List<InstrctrVO> list(InstrctrVO vo) {

        return instrctrMapper.selectList(vo);
    }

    public InstrctrVO detail(int instrctrNo) {
        InstrctrVO vo = new InstrctrVO();
        vo.setInstrctrNo(instrctrNo);

        InstrctrVO data = instrctrMapper.select(vo);

        return data;
    }

    public int create(InstrctrVO vo, LoginVO user) throws Exception {
        return create(vo, user, null);
    }

    public int create(InstrctrVO vo, LoginVO user, Map<String, MultipartFile> files) throws Exception {
        String userid = user.getId();

        vo.setReguser(userid);
        vo.setModuser(userid);

        if (files != null) {
            List<FileVO> result = fileUtil.parseFileInf(files, "INS_", 0, "", "", user.getId(), "file_1");

            if (result != null && result.size() > 0) {
                fileMngService.insertFileInfs(result);
                String fileId = result.get(0).getFileGrpinnb();
                vo.setInstrctrImgid(fileId);
            }
        }

        return instrctrMapper.insert(vo);
    }

    public int update(InstrctrVO vo, LoginVO user) {
        String userid = user.getId();
        vo.setModuser(userid);

        return instrctrMapper.update(vo);
    }

    public int delete(int instrctrNo) {
        InstrctrVO vo = new InstrctrVO();
        vo.setInstrctrNo(instrctrNo);

        return instrctrMapper.delete(vo);
    }

    // 강시 이미지 수정
    public void updateFileid(String comcd, int instrctrNo, String inputid, String atchFileId, String moduser) {
        instrctrMapper.updateFileid(comcd, instrctrNo, inputid, atchFileId, moduser);
    }

    public int selectLastSeq() {
        Integer result = instrctrMapper.selectLastSeq();

        return result == null ? 0 : result;
    }

    public List<InstrctrVO> selectEdcList(InstrctrVO vo) {
        return instrctrMapper.selectEdcList(vo);
    }
}
