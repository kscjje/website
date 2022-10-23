package com.hisco.admin.edcrsvn.vo;

import java.util.List;

import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import lombok.Data;

@Data
public class DrawTargetProgramVO {
    private int orgNo;
    private int edcPrgmNo;
    private int edcRsvnsetSeq;
    private String edcPrgmnm;
    private List<EdcRsvnInfoVO> drawResultList; // 담첨결과
}
