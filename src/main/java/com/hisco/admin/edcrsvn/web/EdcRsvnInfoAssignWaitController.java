package com.hisco.admin.edcrsvn.web;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hisco.admin.edcrsvn.service.EdcRsvnInfoAssignWaitService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.annotation.PageActionType;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.ExceptionUtil;
import com.hisco.cmm.util.HttpUtility;
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

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
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
public class EdcRsvnInfoAssignWaitController {

    private String adminRoot = Config.ADMIN_ROOT;

    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    @Resource(name = "edcRsvnInfoAssignWaitService")
    private EdcRsvnInfoAssignWaitService edcRsvnInfoAssignWaitService;

	@Resource(name = "bizMsgService")
	private BizMsgService bizMsgService;

	@Resource(name = "totalSaleService")
	private TotalSaleService totalSaleService;

    @PageActionInfo(title = "배정대기회원현황", action = PageActionType.READ , inqry = true)
    @GetMapping("/edcRsvnInfoAssignWaitList")
    public String edcRsvnInfoAssignWaitList(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        searchVO.setChannel(Constant.CM_CHANNEL_BO);
        searchVO.setEdcRsvnRectype(Constant.SM_LEREC_TYPE_선착대기);
        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(searchVO);

        if (detailVO == null)
            HttpUtility.sendRedirect(request, response, "유효하지 않는 강좌입니다.", adminRoot + "/edcrsvn/edcProgramReceptionList");

        if (!Constant.SM_LEREC_TYPE_선착대기.equals(detailVO.getEdcRsvnRectype()))
            HttpUtility.sendRedirect(request, response, "선착대기 강좌가 아닙니다.", adminRoot + "/edcrsvn/edcProgramReceptionList");

        PaginationInfo paginationInfo = commandMap.getPagingInfo();

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("programList", Collections.EMPTY_LIST);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("detailVO", detailVO);
        model.addAttribute("referer", request.getHeader("Referer"));

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "배정대기회원목록", action = PageActionType.READ, ajax = true)
    @GetMapping("/edcRsvnInfoAssignWaitListAjax")
    public String edcRsvnInfoAssignWaitListAjax(@ModelAttribute("searchVO") EdcRsvnInfoVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();

        searchVO.setChannel(Constant.CM_CHANNEL_BO);
        searchVO.setEdcRsvnRectype(Constant.SM_LEREC_TYPE_선착대기);
        searchVO.setUrl("edcRsvnInfoAssignWaitListAjax");
        searchVO.setPaginationInfo(paginationInfo);

        if ("edcRsvnCustnm".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_CUST_NM_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_CUST_NM_ASC");
            }
        } else if ("id".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_ID_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_ID_ASC");
            }
        } else if ("edcReqDate".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_REQ_DATE_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_REQ_DATE_ASC");
            }
        } else if ("waitEnddate".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_WAIT_DATE_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_WAIT_DATE_ASC");
            }
        } else if ("edcStat".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_STAT_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_STAT_ASC");
            }
        } else if ("tel".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_TEL_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_TEL_ASC");
            }
        } else {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_RSVN_NO_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_RSVN_NO_ASC");
            }
        }

        if (Config.YES.equalsIgnoreCase((String) commandMap.get("exceldown"))) {
            searchVO.setUsePagingYn(Config.NO);
        }

        List<EdcRsvnInfoVO> rsvnInfoList = edcRsvnInfoService.selectRsvnList(searchVO);

        int totCount = 0;
        if (rsvnInfoList != null && rsvnInfoList.size() >= 1) {
            totCount = ((EdcRsvnInfoVO) rsvnInfoList.get(0)).getTotCount();
        }

        paginationInfo.setTotalRecordCount(totCount);

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("rsvnInfoList", rsvnInfoList);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("totCount", totCount);
        model.addAttribute("exceldown", (String) commandMap.get("exceldown"));

        return HttpUtility.getViewUrl(request);
    }

	@PageActionInfo(title = "배정대기>입금대기처리", action = PageActionType.UPDATE, ajax = true)
	@PostMapping("/edcRsvnInfo1000To2001.json")
	public String edcRsvnInfo1000To2001(@RequestBody EdcRsvnInfoVO paramVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResultInfo resultInfo = new ResultInfo(Config.SUCCESS, "배정등록 처리를 완료하였습니다.");

		try {
			List<EdcRsvnInfoVO> rsvnInfoList = edcRsvnInfoService.selectRsvnListForPay(paramVO);
			
			if (rsvnInfoList == null || rsvnInfoList.isEmpty()) {
				throw new RuntimeException("유효하지 않는 예약건입니다.");
			}
			
			EdcRsvnInfoVO rsvnInfo = rsvnInfoList.get(0);

			if (!Constant.SM_RSVN_STAT_배정대기.equals(rsvnInfo.getEdcStat())) {
				throw new RuntimeException("배정대기 상태가 아닙니다.");
			}

			EdcProgramVO param = new EdcProgramVO();
			param.setEdcPrgmNo(rsvnInfo.getEdcPrgmNo());
			param.setEdcRsvnsetSeq(rsvnInfo.getEdcRsvnsetSeq());
			EdcProgramVO programDetailVO = edcProgramService.selectProgramDetail(param);

			int edcPncpa = programDetailVO.getEdcPncpa();
			int applyCnt = programDetailVO.getStatApplyCnt();

			// 정원체크(총원보다 지원자수가 같거나 많으면)
			if (applyCnt >= edcPncpa) {
				throw new RuntimeException("정원이 마감되어 등록할 수 없습니다.");
			}

			String rsvnStat = Constant.SM_RSVN_STAT_입금대기;

			if (programDetailVO.getSalamt() == 0) { // 무료강좌
				rsvnStat = Constant.SM_RSVN_STAT_등록완료;
			}

			if (rsvnStat.equals(Constant.SM_RSVN_STAT_등록완료)) {// 무료인경우 관련테이블(6개) 모두에 insert
				SaleFormVO saleFormVO = new SaleFormVO();
				saleFormVO.setRsvnStat(rsvnStat); // tobe 상태

				SaleFormMemberVO member = new SaleFormMemberVO();
				member.setMemNo(rsvnInfo.getEdcMemNo());
				member.setMemNm(rsvnInfo.getEdcRsvnCustnm());
				member.setMemHp(rsvnInfo.getEdcRsvnMoblphon());
				member.setMemBirthdate(rsvnInfo.getEdcRsvnBirthdate());
				member.setMemGender(rsvnInfo.getEdcRsvnGender());

				SaleFormItemVO item = new SaleFormItemVO();
				item.setComcd(programDetailVO.getComcd());
				item.setOrgNo(Integer.parseInt(programDetailVO.getOrgNo()));
				item.setItemCd(programDetailVO.getItemCd());
				item.setCostAmt(programDetailVO.getCostAmt());
				item.setSalamt(programDetailVO.getSalamt());
				item.setMonthCnt(programDetailVO.getMonthCnt());
				item.setEdcPrgmNo(programDetailVO.getEdcPrgmNo());
				item.setEdcSdate(programDetailVO.getEdcSdate());
				item.setEdcEdate(programDetailVO.getEdcEdate());
				item.setEdcRsvnsetSeq(programDetailVO.getEdcRsvnsetSeq());
				item.setVatYn(Config.NO);
				item.setEdcRsvnReqid(rsvnInfo.getEdcRsvnReqid()); // 현재 예약번호

				SaleFormPaymentVO payment = new SaleFormPaymentVO();
				payment.setOnoff(Constant.EDC_ONOFFIN_TYPE_OFF);
				payment.setRergistGbn(Constant.SM_REGIST_GBN_신규등록);
				payment.setPayAmt(0);
				payment.setDcAmt(0);
				payment.setPayMethod("CASH"); // 무료인경우 현금으로 셋팅
				payment.setPayComcd(Constant.PG_SELF);
				payment.setFinanceCd("CH");
				payment.setTerminalType(rsvnInfo.getEdcTrmnlType());
				payment.setRsvnNo(rsvnInfo.getEdcRsvnNo());

				saleFormVO.setMember(member);
				saleFormVO.setItemList(Arrays.asList(item));
				saleFormVO.setPayment(payment);

				totalSaleService.register(saleFormVO);
			} else {
				rsvnInfo.setEdcStat(rsvnStat);
				edcRsvnInfoService.updateRsvnInfo1000To2001(rsvnInfo);

				bizMsgService.sendRsvnMessage(paramVO.getEdcRsvnNo());
			}
		} catch (TransientDataAccessResourceException ex) {
			throw new RuntimeException(ExceptionUtil.getSQLErrorMessage(ex, "RSVN"));
		} catch (Exception ex) {
			resultInfo = new ResultInfo(Config.FAIL, ex.getMessage());
		}

        model.clear();
        model.addAttribute("result", resultInfo);

        return HttpUtility.getViewUrl(request);
    }

	@PageActionInfo(title = "배정대기>취소하기", action = PageActionType.UPDATE, ajax = true)
	@PostMapping("/edcRsvnInfoAssignWaitingCancel.json")
	public String edcRsvnInfoAssignWaitingCancel(@RequestBody EdcRsvnInfoVO paramVO,
	        CommandMap commandMap, ModelMap model,
	        HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResultInfo resultInfo = new ResultInfo(Config.SUCCESS, "대기취소 처리를 완료하였습니다.");

		try {
			edcRsvnInfoAssignWaitService.cancelWait(paramVO);
		} catch (Exception ex) {
			resultInfo = new ResultInfo(Config.FAIL, ex.getMessage());
		}

		model.clear();
		model.addAttribute("result", resultInfo);

		return HttpUtility.getViewUrl(request);
	}
}
