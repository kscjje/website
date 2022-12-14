package com.hisco.admin.edcrsvn.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hisco.admin.member.service.MemberService;
import com.hisco.admin.member.vo.MemberUserVO;
import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgMemberDcVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.annotation.PageActionType;
import com.hisco.cmm.modules.DateUtil;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.ExceptionUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.intrfc.sale.service.PayListService;
import com.hisco.intrfc.sale.service.SaleChargeService;
import com.hisco.intrfc.sale.service.TotalCancelService;
import com.hisco.intrfc.sale.service.TotalSaleService;
import com.hisco.intrfc.sale.vo.PayMethodVO;
import com.hisco.intrfc.sale.vo.PaySummaryVO;
import com.hisco.intrfc.sale.vo.SaleFormItemVO;
import com.hisco.intrfc.sale.vo.SaleFormMemberVO;
import com.hisco.intrfc.sale.vo.SaleFormVO;
import com.hisco.intrfc.sms.service.BizMsgService;
import com.hisco.user.edcatnlc.service.EdcProgramService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import egovframework.com.cmm.LoginVO;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * ??????????????????
 *
 * @author
 * @since
 * @version
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}/edcrsvn", "#{dynamicConfig.managerRoot}/edcrsvn" })
public class EdcRsvnInfoRegisterController {

    private String adminRoot = Config.ADMIN_ROOT;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    @Resource(name = "memberService")
    private MemberService memberService;

    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

    @Resource(name = "payListService")
    private PayListService payListService;

    @Resource(name = "totalSaleService")
    private TotalSaleService totalSaleService;

    @Resource(name = "totalCancelService")
    private TotalCancelService totalCancelService;

    @Resource(name = "saleChargeService")
    private SaleChargeService saleChargeService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    @Resource(name = "bizMsgService")
    private BizMsgService bizMsgService;
    
    @PageActionInfo(title = "????????????????????????", action = PageActionType.READ)
    @GetMapping("/edcRsvnInfoRegister")
    public String edcRsvnInfoRegister(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        searchVO.setChannel(Constant.CM_CHANNEL_BO);
        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(searchVO);

        if (detailVO == null)
            HttpUtility.sendRedirect(request, response, "???????????? ?????? ???????????????.", adminRoot + "/edcrsvn/edcProgramReceptionList");

        if (Constant.SM_LEREC_TYPE_???????????????.equals(detailVO.getEdcRsvnRectype()))
            HttpUtility.sendRedirect(request, response, "??????????????? ???????????????.", adminRoot + "/edcrsvn/edcProgramReceptionList");

        model.addAttribute("detailVO", detailVO);
        model.addAttribute("source", commandMap.getParam().get("source"));
        model.addAttribute("today", DateUtil.getTodate("yyyy-MM-dd"));

        PayMethodVO pmParam = new PayMethodVO();
        pmParam.setPComcd(Constant.PG_SELF);
        pmParam.setPType(Constant.SITE_P_TYPE_??????);
        model.addAttribute("cardList", payListService.selectPayMethodList(pmParam));

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "??????????????????", action = PageActionType.UPDATE, ajax = true)
    @PostMapping("/edcRsvnInfoRegister.json")
    //@RequestMapping(value = "/edcRsvnInfoRegister.json", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
    public String edcRsvnInfoRegisterJson(@RequestBody SaleFormVO saleFormVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        model.clear();
        ResultInfo resultInfo = new ResultInfo(Config.SUCCESS, "????????????????????? ?????????????????????.");
System.out.println("1");
        try {

            EdcProgramVO programDetailVO = null;
            SaleFormItemVO item = saleFormVO.getItemList().get(0);

            LoginVO loginVO = new LoginVO();
            SaleFormMemberVO memberVO = saleFormVO.getMember();
            loginVO.setUniqId(memberVO.getMemNo());
            loginVO.setGender(memberVO.getMemGender());
            loginVO.setBirthDate(memberVO.getMemBirthdate());
            loginVO.setName(memberVO.getMemNm());
            loginVO.setIhidNum(memberVO.getMemHp());
            System.out.println("2");
            for (SaleFormItemVO itemVO : saleFormVO.getItemList()) {
                if (item.getDcAmt() > 0 && StringUtils.isBlank(item.getDiscountCd())) {
                    throw new RuntimeException("?????????????????? ??????????????? ???????????????.");
                }

                if (item.getDcAmt() < 1 && StringUtils.isNotBlank(item.getDiscountCd())) {
                    throw new RuntimeException("???????????? ???????????? ??????????????? 0?????????.");
                }

                EdcProgramVO param = new EdcProgramVO();
                param.setEdcPrgmNo(itemVO.getEdcPrgmNo());
                param.setEdcRsvnsetSeq(item.getEdcRsvnsetSeq());
                programDetailVO = edcProgramService.selectProgramDetail(param);

                // 1??? ?????? ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                // 2???????????? service??? ??????
                // 3???????????? trigger??? ??????

                // ?????? / ?????? ??? ?????? ?????? ?????? ??????
                String checkStr = edcRsvnInfoService.mngrRsvnValidation(programDetailVO, loginVO);

                if (!checkStr.equals("OK")) {
                    throw new RuntimeException(checkStr);
                }
            }

            System.out.println("saleFormVO.getRsvnStat()=="+saleFormVO.getRsvnStat());
            if (Constant.SM_RSVN_STAT_????????????.equals(saleFormVO.getRsvnStat()) || Constant.SM_RSVN_STAT_????????????.equals(saleFormVO.getRsvnStat()) || Constant.SM_RSVN_STAT_????????????.equals(saleFormVO.getRsvnStat())) {
                EdcRsvnInfoVO rsvnInfoParam = new EdcRsvnInfoVO();
                rsvnInfoParam.setEdcPrgmNo(item.getEdcPrgmNo());
                rsvnInfoParam.setEdcRsvnsetSeq(item.getEdcRsvnsetSeq());

                //??????: ????????? ????????????, ???????????? ???????????? ??????????????? ???????????? ?????? ??????
                //???????????? ??? memtype ??????????????? ??????
                rsvnInfoParam.setEdcMemNo(loginVO.getUniqId().equals("") ? null : loginVO.getUniqId());		//????????? ????????? ??? mem_no null??? ??????
                if(loginVO.getUniqId().equals("")) rsvnInfoParam.setEdcRsvnMemtype(Constant.EDC_RSVN_MEMTYPE_?????????);
                rsvnInfoParam.setEdcRsvnMemno(loginVO.getUniqId());
                if (StringUtils.isBlank(loginVO.getUniqId())) {
                    rsvnInfoParam.setEdcRsvnCustnm(loginVO.getName());
                    rsvnInfoParam.setEdcRsvnGender(loginVO.getGender());
                    rsvnInfoParam.setEdcRsvnBirthdate(loginVO.getBirthDate().replaceAll("-", ""));
                    rsvnInfoParam.setEdcRsvnMoblphon(loginVO.getIhidNum().replaceAll("-", ""));
                }

                rsvnInfoParam.setEdcDcamt(item.getDcAmt());
                rsvnInfoParam.setEdcReasondc(item.getDiscountCd());

                rsvnInfoParam.setEdcMonthcnt(item.getMonthCnt());
                rsvnInfoParam.setEdcOnoffintype(Constant.EDC_ONOFFIN_TYPE_OFF);
                rsvnInfoParam.setEdcTrmnlType(Constant.SM_TERMINAL_TYPE_??????PC);

                rsvnInfoParam.setEdcStat(saleFormVO.getRsvnStat());
                int saveCnt = edcRsvnInfoService.saveRsvnInfo(rsvnInfoParam); // ????????????
                if (saveCnt < 1) {
                    throw new RuntimeException("????????? ?????????????????????. ????????? ??????????????? ???????????????.");
                }
                log.debug("saveCnt = {}", saveCnt);

                resultInfo = new ResultInfo(Config.SUCCESS, "????????? ?????????????????????");
                
                //???????????? ???????????? ????????? ??????
                bizMsgService.sendRsvnMessage(rsvnInfoParam.getEdcRsvnNo());
                
            } else if (Constant.SM_RSVN_STAT_????????????.equals(saleFormVO.getRsvnStat())) {
            	System.out.println("saleFormVO.getPayment().getOid()=="+saleFormVO.getPayment().getOid());
                if (StringUtils.isBlank(saleFormVO.getPayment().getOid()))
                    saleFormVO.getPayment().setOid(String.valueOf(saleChargeService.selectNextOid()));
                totalSaleService.register(saleFormVO);

                PaySummaryVO summaryParam = new PaySummaryVO();
                summaryParam.setComcd(Config.COM_CD);
                summaryParam.setEdcRsvnReqid(saleFormVO.getEdcRsvnReqid());
                model.addAttribute("paySummary", saleChargeService.selectPaySummary(summaryParam));
            } else {
                throw new RuntimeException("???????????? ?????? ???????????????.");
            }
        } catch (TransientDataAccessResourceException ex) {
            String msg = ExceptionUtil.getSQLErrorMessage(ex, "RSVN");
            log.error(msg);
            if (msg.contains("|"))
                msg = msg.substring(0, msg.indexOf("|"));
            resultInfo = new ResultInfo(Config.FAIL, msg);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
            resultInfo = new ResultInfo(Config.FAIL, ex.getMessage());
        } catch (Exception ex) {
            log.error(ExceptionUtil.getErrorLine(ex));
            resultInfo = new ResultInfo(Config.FAIL, ex.getMessage());
        }

        model.addAttribute("result", resultInfo);

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "????????????", action = PageActionType.READ, ajax = true, inqry = true)
    @GetMapping("/edcMemberListAjax")
    public String edcMemberListAjax(MemberUserVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);

        List<?> memberList = memberService.selectMemberList(searchVO);

        int totCount = 0;

        if (memberList != null && !memberList.isEmpty()) {
            if (memberList != null && memberList.size() >= 1) {
                CamelMap camelMap = (CamelMap) memberList.get(0);
                totCount = Integer.valueOf(String.valueOf(camelMap.get("tbAllCount")));
            }
        }

        paginationInfo.setTotalRecordCount(totCount);

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("memberList", memberList);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("totCount", totCount);

        return Config.ADMIN_ROOT + "/edcrsvn/modalMemberListAjax";
    }

    @PageActionInfo(title = "??????????????????", action = PageActionType.READ, ajax = true)
    @GetMapping("/edcMemberDcListAjax")
    public String edcMemberDcListAjax(@ModelAttribute("searchVO") OrgMemberDcVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        model.addAttribute("dcList", orgInfoService.selectOrgMemberDcList(searchVO));

        return Config.ADMIN_ROOT + "/edcrsvn/modalMemberDcListAjax";
    }
}
