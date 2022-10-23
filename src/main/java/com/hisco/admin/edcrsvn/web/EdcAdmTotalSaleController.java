package com.hisco.admin.edcrsvn.web;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hisco.admin.member.service.MemberService;
import com.hisco.admin.member.vo.MemberUserVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.annotation.PageActionType;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.ExceptionUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.intrfc.sale.service.SaleChargeService;
import com.hisco.intrfc.sale.service.TotalSaleService;
import com.hisco.intrfc.sale.vo.SaleFormItemVO;
import com.hisco.intrfc.sale.vo.SaleFormMemberVO;
import com.hisco.intrfc.sale.vo.SaleFormPaymentVO;
import com.hisco.intrfc.sale.vo.SaleFormVO;
import com.hisco.user.edcatnlc.service.EdcProgramService;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 배정대기목록
 *
 * @author
 * @since
 * @version
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}/edcrsvn", "#{dynamicConfig.managerRoot}/edcrsvn" })
public class EdcAdmTotalSaleController {

    private String adminRoot = Config.ADMIN_ROOT;

    @Resource(name = "totalSaleService")
    private TotalSaleService totalSaleService;

    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

    @Resource(name = "memberService")
    private MemberService memberService;

    @Resource(name = "saleChargeService")
    private SaleChargeService saleChargeService;

    /*
     * @Resource(name = "totalRefundService")
     * private TotalRefundService totalRefundService;
     */

    /*
     * @Resource(name = "totalCancelService")
     * private TotalCancelService totalCancelService;
     */

    @PageActionInfo(title = "강좌최종판매등록테스트", action = PageActionType.READ)
    @GetMapping("/edcSaleTest")
    public String edcSaleTest(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        SaleFormVO sale = new SaleFormVO();
        sale.setMember(new SaleFormMemberVO());
        sale.setPayment(new SaleFormPaymentVO());
        sale.setItemList(new ArrayList<SaleFormItemVO>());
        sale.setRsvnStat(Constant.SM_RSVN_STAT_등록완료);

        log.debug("{}", JsonUtil.Object2String(sale));

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "강좌최종판매등록/환불/취소", action = PageActionType.UPDATE, ajax = true)
    @PostMapping("/edcSale.json")
    public String edcSale(@Valid @RequestBody SaleFormVO saleFormVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        log.debug("saleFormVO = {}", gson.toJson(saleFormVO));

        ResultInfo resultInfo = new ResultInfo(Config.SUCCESS, "처리를 완료하였습니다.");

        try {

            for (SaleFormItemVO itemVO : saleFormVO.getItemList()) {
                EdcProgramVO param = new EdcProgramVO();
                param.setEdcPrgmNo(itemVO.getEdcPrgmNo());
                EdcProgramVO programDetailVO = edcProgramService.selectProgramDetail(param);

                // 1차 제한 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                // 2차제한은 service에 존재
                // 3차제한은 trigger에 존재

                // 유효강좌여부체크
                if (programDetailVO == null)
                    throw new RuntimeException("유효하지 않는 강좌입니다.");

                // 예약가능기간체크. BO 제한 미적용
                /*
                 * if (!Constant.EDC_PROGRAM_STATUS_신청.equals(programDetailVO.getEdcStatus())) {// 신청가능
                 * throw new RuntimeException("예약 가능 기간이 아닙니다.");
                 * }
                 */
                MemberUserVO memberUserVO = new MemberUserVO();
                if (StringUtils.isNotBlank(saleFormVO.getMember().getMemNo())) {
                    memberUserVO.setMemNo(saleFormVO.getMember().getMemNo());
                    memberUserVO = memberService.selectMemberDetail(memberUserVO);
                    if (memberUserVO == null)
                        throw new RuntimeException("존재하지 않는 회원번호입니다.");
                }

                if (StringUtils.isBlank(memberUserVO.getMemNo()) && Config.NO.equals(programDetailVO.getRsvnNonmebyn())) {// 비회원
                    throw new RuntimeException("회원만 예약 가능한 강좌입니다.");
                }

                // 성별제한체크
                String edcReqGender = StringUtils.defaultIfEmpty(programDetailVO.getEdcReqGender(), Constant.EDC_REQ_GENDER_제한없음);
                String gender = StringUtils.defaultIfEmpty(memberUserVO.getGender(), saleFormVO.getMember().getMemGender()); // 2(남성),
                                                                                                                             // 3(여성)
                if (Constant.CM_MEMBER_GENDER_남성.equals(gender)) {
                    gender = Constant.EDC_REQ_GENDER_남성;
                } else if (Constant.CM_MEMBER_GENDER_남성.equals(gender)) {
                    gender = Constant.EDC_REQ_GENDER_여성;
                }

                // 성별제한 && 회원의성별과 신청강좌의필요성별이다른경우
                if (!Constant.EDC_REQ_GENDER_제한없음.equals(edcReqGender) && !edcReqGender.equals(gender)) {
                    throw new RuntimeException("성별제한이 있는 강좌입니다.");
                }
            }

            for (SaleFormItemVO item : saleFormVO.getItemList()) {
                if (item.getDcAmt() > 0 && StringUtils.isBlank(item.getDiscountCd())) {
                    throw new RuntimeException("할인코드없이 할인금액만 존재합니다.");
                }

                if (item.getDcAmt() < 1 && StringUtils.isNotBlank(item.getDiscountCd())) {
                    throw new RuntimeException("할인코드 존재하나 할인금액이 0입니다.");
                }
            }

            // 추가 값 설정 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            // TODO:핸드폰번호, 이메일주소 암호화해야 합니다.
            // saleFormVO.getMember().setMemHp(memHp);
            saleFormVO.getPayment().setTerminalType(Constant.SM_TERMINAL_TYPE_현장PC);

            if (Constant.SM_RSVN_STAT_등록완료.equals(saleFormVO.getRsvnStat())) {
                if (StringUtils.isBlank(saleFormVO.getPayment().getOid()))
                    saleFormVO.getPayment().setOid(String.valueOf(saleChargeService.selectNextOid()));
            }

            if (Constant.SM_RSVN_STAT_배정대기.equals(saleFormVO.getRsvnStat())) { //
                totalSaleService.wait(saleFormVO);
            } else if (Constant.SM_RSVN_STAT_등록완료.equals(saleFormVO.getRsvnStat())) {
                totalSaleService.register(saleFormVO);
            } else if (Constant.SM_RSVN_STAT_환불취소.equals(saleFormVO.getRsvnStat())) {
                // totalSaleService.cancel(saleFormVO); //TODO:변경필요.2021.12.16
            } else {
                throw new RuntimeException("유효하지 않는 command입니다.");
            }
        } catch (TransientDataAccessResourceException ex) {
            resultInfo = new ResultInfo(Config.FAIL, ExceptionUtil.getSQLErrorMessage(ex, "RSVN"));
        } catch (RuntimeException ex) {
            resultInfo = new ResultInfo(Config.FAIL, ex.getMessage());
        } catch (Exception ex) {
            resultInfo = new ResultInfo(Config.FAIL, ex.getMessage());
        }

        model.clear();
        model.addAttribute("result", resultInfo);

        return HttpUtility.getViewUrl(request);
    }
}
