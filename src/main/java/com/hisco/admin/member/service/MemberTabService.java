package com.hisco.admin.member.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.member.mapper.MemberTabMapper;
import com.hisco.admin.member.vo.MemberDiscVO;
import com.hisco.admin.member.vo.MemberUserVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원현황 관리 Service 구현 클래스
 *
 * @author 진수진
 * @since 2021.11.18
 * @version 1.0, 2021.11.18
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2021.11.18 최초작성
 */
@Slf4j
@Service("memberTabService")
public class MemberTabService extends EgovAbstractServiceImpl {

    @Resource(name = "memberTabMapper")
    private MemberTabMapper memberTabMapper;

    /**
     * 회원카드 목록 조회
     *
     * @param MemberUserVO
     * @return List
     * @throws Exception
     */
    public List<?> selectCardList(MemberUserVO paramMap) throws Exception {
        return memberTabMapper.selectCardList(paramMap);
    }

    public List<?> selectDiscountList(MemberUserVO paramMap) {
        return memberTabMapper.selectDiscountList(paramMap);
    }

    public int insertDiscount(MemberDiscVO paramMap) {
        int chk = memberTabMapper.selectDiscountCheck(paramMap);

        if (chk > 0) {
            return 0;
        } else {
            return memberTabMapper.insertDiscount(paramMap);
        }

    }

    public MemberDiscVO selectDiscountRecord(MemberDiscVO paramMap) {
        return memberTabMapper.selectDiscountRecord(paramMap);
    }

    public int updateDiscount(MemberDiscVO paramMap) {
        int chk = memberTabMapper.selectDiscountCheck(paramMap);

        if (chk > 0) {
            return 0;
        } else {
            memberTabMapper.updateDiscount(paramMap);
            return 1;
        }

    }

    public int deleteDiscount(MemberDiscVO paramMap) {
        return memberTabMapper.deleteDiscount(paramMap);
    }
}
