package com.hisco.admin.edcrsvn.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hisco.admin.edcrsvn.vo.DrawMemberVO;
import com.hisco.admin.edcrsvn.vo.DrawTargetProgramVO;
import com.hisco.admin.edcrsvn.vo.EdcRsvnInfoDrawVO;
import com.hisco.admin.eduadm.service.EduAdmService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.intrfc.sale.service.TotalSaleService;
import com.hisco.intrfc.sale.vo.SaleFormItemVO;
import com.hisco.intrfc.sale.vo.SaleFormMemberVO;
import com.hisco.intrfc.sale.vo.SaleFormPaymentVO;
import com.hisco.intrfc.sale.vo.SaleFormVO;
import com.hisco.intrfc.sms.service.BizMsgService;
import com.hisco.user.edcatnlc.service.EdcProgramService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : EdcRsvnInfoDrawService.java
 * @Description : 추첨강좌 추첨
 * @author woojinp@legitsys.co.kr
 * @since 2021. 11. 28.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */

@Slf4j
@Service("edcRsvnInfoDrawService")
public class EdcRsvnInfoDrawService extends EgovAbstractServiceImpl {

    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

    @Resource(name = "eduAdmService")
    private EduAdmService eduAdmService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    @Resource(name = "totalSaleService")
    private TotalSaleService totalSaleService;

    @Resource(name = "bizMsgService")
    private BizMsgService bizMsgService;

    /*
     * 추첨하기
     */
    public void doDraw(EdcRsvnInfoDrawVO paramVO) throws Exception {

        if (paramVO.getTargetProgramList() == null || paramVO.getTargetProgramList().isEmpty())
            throw new RuntimeException("추첨할 프로그램을 선택해 주세요.");

        for (DrawTargetProgramVO targetProgramVO : paramVO.getTargetProgramList()) {
            // 0.추첨결과(회원) 목록
            List<EdcRsvnInfoVO> drawResultList = new ArrayList<EdcRsvnInfoVO>();

            EdcRsvnInfoVO rsvnSearchVO = new EdcRsvnInfoVO();
            rsvnSearchVO.setOrgNo(targetProgramVO.getOrgNo());
            rsvnSearchVO.setEdcPrgmNo(targetProgramVO.getEdcPrgmNo());
            rsvnSearchVO.setEdcRsvnsetSeq(targetProgramVO.getEdcRsvnsetSeq());
            rsvnSearchVO.setChannel(paramVO.getChannel());
            rsvnSearchVO.setPeriodCondition(Constant.CM_PERIOD_CONDITION_예약년월);
            rsvnSearchVO.setSearchYear(paramVO.getSearchYear());
            rsvnSearchVO.setSearchMonth(paramVO.getSearchMonth());
            rsvnSearchVO.setEdcStat(Constant.SM_RSVN_STAT_추첨대기);
            rsvnSearchVO.setEdcRsvnRectype(Constant.SM_LEREC_TYPE_추첨대기제);
            rsvnSearchVO.setUsePagingYn(Config.NO);
            rsvnSearchVO.setPrevMonthOutYn(paramVO.getPrevMonthOutYn());

            // 1.추첨대상 대기목록조회
            List<EdcRsvnInfoVO> waitingRsvnList = edcRsvnInfoService.selectRsvnList(rsvnSearchVO);
            if (waitingRsvnList == null || waitingRsvnList.isEmpty())
                continue;

            List<EdcRsvnInfoVO> waitingRsvnList2 = waitingRsvnList.stream().filter(rsvn -> true).collect(Collectors.toList());

            Optional<EdcRsvnInfoVO> drawDoneVO = waitingRsvnList2.stream().filter(rsvn -> Constant.SM_PRZWIN_STAT_당첨확정.equals(rsvn.getPrzwinStat())).findFirst();
            if (drawDoneVO.isPresent()) {
                throw new RuntimeException("[" + drawDoneVO.get().getEdcPrgmnm() + "]은 이미 당첨확정된 프로그램입니다.\n화면 갱신 후 다시 시도해 주세요.");
            }

            // 1.1 대상 전체를 미승인 미당첨 처리(update)
            for (EdcRsvnInfoVO vo : waitingRsvnList) {
                vo.setPrzwinStat(Constant.SM_PRZWIN_STAT_당첨대기);
                vo.setPrzwinyn("");
                edcRsvnInfoService.doDrawRsvnInfo(vo);
            }

            int JUNGWON = waitingRsvnList.get(0).getEdcPncpa();
            Random random = new Random();
            random.setSeed(System.currentTimeMillis()); // 시드값

            // 2.1 관리자pick회원우선선정
            for (DrawMemberVO considerMemberVO : paramVO.getFirstConsiderMemberList()) {
                // @formatter:off
                Optional<EdcRsvnInfoVO> bingo = waitingRsvnList.stream().filter(rsvnVO -> {
                    return rsvnVO.getEdcRsvnCustnm().equals(considerMemberVO.getEdcRsvnCustnm()) && rsvnVO.getEdcRsvnMoblphon().equals(considerMemberVO.getEdcRsvnMoblphon().replaceAll("[-]", ""));
                }).findFirst();
                // @formatter:on
                if (bingo.isPresent()) {
                    // 결과담기
                    if (!this.addDrawResultList(JUNGWON, drawResultList, bingo.get()))
                        break;
                }
            }

            // 2.2 이전접수탈락자우선
            if (Config.YES.equals(paramVO.getPrevMonthOutYn())) {
                // @formatter:off
                List<EdcRsvnInfoVO> prevOutList = waitingRsvnList.stream().filter(rsvnVO -> {
                    return Config.YES.equals(rsvnVO.getPrevMonthOutYn());
                }).collect(Collectors.toList());

                // @formatter:on
                if (prevOutList != null && !prevOutList.isEmpty()) {
                    for (EdcRsvnInfoVO prevOutRsvn : prevOutList) {
                        // 결과담기
                        if (!this.addDrawResultList(JUNGWON, drawResultList, prevOutRsvn))
                            break;
                    }
                }
            }

            // 2.3 (2.1,2.2 아닌경우)
            for (;;) {
                int rno = random.nextInt(waitingRsvnList2.size());
                EdcRsvnInfoVO rsvn = waitingRsvnList2.get(rno);

                if (!this.addDrawResultList(JUNGWON, drawResultList, rsvn)) {// 추첨인원이 정원을 초과하면 break
                    break;
                } else {
                    waitingRsvnList2.remove(rno);
                }

                if (waitingRsvnList2.isEmpty())
                    break;
            }

            targetProgramVO.setDrawResultList(drawResultList);

            // 3 당첨자들을 미승인 담첨 처리(update)
            for (EdcRsvnInfoVO drawVO : drawResultList) {
                edcRsvnInfoService.doDrawRsvnInfo(drawVO);
            }

            // 4 당첨자들목록뒤에 미당첨자들 추가
            for (EdcRsvnInfoVO rsvn : waitingRsvnList) {
                Optional<EdcRsvnInfoVO> existsVO = drawResultList.stream().filter(draw -> draw.getEdcRsvnReqid() == rsvn.getEdcRsvnReqid()
                        ? true : false).findFirst();

                if (!existsVO.isPresent()) {
                    rsvn.setPrzwinGbnNm("탈락");
                    drawResultList.add(rsvn);
                }
            }
        }
    }

    private boolean addDrawResultList(int JUNGWON, List<EdcRsvnInfoVO> drawResultList, EdcRsvnInfoVO drawVO) {
        if (drawResultList.size() == JUNGWON)
            return false;

        if (!drawResultList.contains(drawVO)) {
            drawVO.setPrzwinStat(Constant.SM_PRZWIN_STAT_당첨대기);
            drawVO.setPrzwinyn(Constant.SM_PRZWIN_GBN_예비당첨);
            drawVO.setPrzwinGbnNm("예비당첨");
            drawResultList.add(drawVO);
        }

        return true; // 계속 추가가능
    }

    /*
     * 추첨 되돌리기
     */
    public int undoDraw(EdcRsvnInfoDrawVO paramVO) throws Exception {

        if (paramVO.getTargetProgramList() == null || paramVO.getTargetProgramList().isEmpty())
            throw new RuntimeException("되돌리기할 프로그램이 존재하지 않습니다.");

        for (DrawTargetProgramVO drawVO : paramVO.getTargetProgramList()) {
            List<EdcRsvnInfoVO> drawResultList = drawVO.getDrawResultList();
            if (drawResultList == null || drawResultList.isEmpty())
                continue;

            for (EdcRsvnInfoVO rsvnVO : drawResultList) {
                edcRsvnInfoService.undoDrawRsvnInfo(rsvnVO);
            }
        }

        return 1;
    }

    /**
     * 추첨확정
     */
    public int confirmDraw(EdcRsvnInfoDrawVO paramVO) throws Exception {

        if (paramVO.getTargetProgramList() == null || paramVO.getTargetProgramList().isEmpty())
            throw new RuntimeException("당청확정 할 프로그램이 존재하지 않습니다.");

        for (DrawTargetProgramVO drawVO : paramVO.getTargetProgramList()) {
            List<EdcRsvnInfoVO> drawResultList = drawVO.getDrawResultList();
            if (drawResultList == null || drawResultList.isEmpty())
                continue;

            EdcProgramVO param = new EdcProgramVO();
            param.setEdcPrgmNo(drawVO.getEdcPrgmNo());
            param.setEdcRsvnsetSeq(drawVO.getEdcRsvnsetSeq());
            EdcProgramVO programDetailVO = edcProgramService.selectProgramDetail(param);

			for (EdcRsvnInfoVO rsvnVO : drawResultList) {
				if (log.isDebugEnabled()) 
					log.debug("rsvnVO = {}", JsonUtil.toPrettyJson(rsvnVO));

				if (rsvnVO.getEdcProgmCost() > 0 && rsvnVO.getEdcTotamt() > 0) { // 유료+결제예정금액존재->결제대기유지+입금대기시간갱신
					if ("탈락".equals(rsvnVO.getPrzwinGbnNm()) || StringUtils.isBlank(rsvnVO.getPrzwinyn())) {
						rsvnVO.setEdcStat(Constant.SM_RSVN_STAT_당첨취소);
					} else {
						rsvnVO.setEdcStat(Constant.SM_RSVN_STAT_입금대기);
						rsvnVO.setEdcPaywaitEnddatetime(edcRsvnInfoService.selectPaywaitTime(rsvnVO));
					}
				} else { // 무료인경우 혹은 유료인데 최종결제금액이 0원인경우
					if ("탈락".equals(rsvnVO.getPrzwinGbnNm()) || StringUtils.isBlank(rsvnVO.getPrzwinyn())) {
						rsvnVO.setEdcStat(Constant.SM_RSVN_STAT_당첨취소);
					} else {
						rsvnVO.setEdcStat(Constant.SM_RSVN_STAT_등록완료);
					}

                    if (Constant.SM_PRZWIN_GBN_예비당첨.equals(rsvnVO.getPrzwinyn())) { // 등록유관 테이블 입력
                        SaleFormVO saleFormVO = new SaleFormVO();
                        saleFormVO.setRsvnStat(Constant.SM_RSVN_STAT_등록완료);

                        String memNo = rsvnVO.getEdcMemNo();
                        if ("비회원".equals(memNo))
                            memNo = null;

                        SaleFormMemberVO member = new SaleFormMemberVO();
                        member.setMemNo(memNo);
                        member.setMemNm(rsvnVO.getEdcRsvnCustnm());
                        member.setMemHp(rsvnVO.getEdcRsvnMoblphon());
                        member.setMemBirthdate(rsvnVO.getEdcRsvnBirthdate());

                        SaleFormItemVO item = new SaleFormItemVO();
                        item.setComcd(rsvnVO.getComcd());
                        item.setEdcRsvnReqid(rsvnVO.getEdcRsvnReqid());
                        item.setOrgNo(Integer.parseInt(programDetailVO.getOrgNo()));
                        item.setItemCd(programDetailVO.getItemCd());
						item.setCostAmt(rsvnVO.getEdcProgmCost());
						item.setSalamt(rsvnVO.getEdcProgmCost());
						item.setDcAmt(rsvnVO.getEdcDcamt());
						item.setDiscountCd(rsvnVO.getEdcReasondc());
						item.setDiscountRate(rsvnVO.getEdcDcamt() > 0 ? 100 : 0);
                        item.setMonthCnt(programDetailVO.getMonthCnt());
                        item.setEdcPrgmNo(programDetailVO.getEdcPrgmNo());
                        item.setEdcSdate(programDetailVO.getEdcSdate());
                        item.setEdcEdate(programDetailVO.getEdcEdate());
                        item.setEdcRsvnsetSeq(programDetailVO.getEdcRsvnsetSeq());
                        item.setVatYn(Config.NO);
                        item.setSkipRsvnInfoTableYn(Config.YES);

                        SaleFormPaymentVO payment = new SaleFormPaymentVO();
                        payment.setOnoff("온라인".equals(rsvnVO.getEdcOnoffintype())
                                ? Constant.EDC_ONOFFIN_TYPE_ON : Constant.EDC_ONOFFIN_TYPE_OFF);
                        payment.setRergistGbn(Constant.SM_REGIST_GBN_신규등록);
                        payment.setPayAmt(0);
						payment.setDcAmt(rsvnVO.getEdcDcamt());
                        payment.setPayMethod("CASH"); // 무료인경우 현금으로 셋팅
                        payment.setPayComcd(Constant.PG_SELF);
                        payment.setFinanceCd("CH");
                        payment.setRsvnNo(rsvnVO.getEdcRsvnNo());

                        payment.setTerminalType(rsvnVO.getEdcTrmnlType());

                        saleFormVO.setMember(member);
                        saleFormVO.setItemList(Arrays.asList(item));
                        saleFormVO.setPayment(payment);

                        int saveCnt = totalSaleService.register(saleFormVO);
                        log.debug("saveCnt = {}", saveCnt);
                    }
                }

                if (Constant.SM_PRZWIN_GBN_예비당첨.equals(rsvnVO.getPrzwinyn())) {
                    rsvnVO.setPrzwinyn(Constant.SM_PRZWIN_GBN_당첨);
                } else {
                    rsvnVO.setPrzwinyn(null);
                }

                //추첨 확정 저장
                edcRsvnInfoService.confirmDrawRsvnInfo(rsvnVO);

                // 알림톡발송
                EdcRsvnInfoVO tmpRsvnInfoVO = new EdcRsvnInfoVO();
                tmpRsvnInfoVO.setEdcRsvnReqid(rsvnVO.getEdcRsvnReqid());
                tmpRsvnInfoVO.setPrzwinStat(Constant.SM_PRZWIN_STAT_당첨확정);
                bizMsgService.sendRsvnMessage(tmpRsvnInfoVO);
            }
        }

        return 1;
    }
}
