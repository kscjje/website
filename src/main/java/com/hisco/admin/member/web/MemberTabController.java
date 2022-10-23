package com.hisco.admin.member.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.member.service.MemberService;
import com.hisco.admin.member.service.MemberTabService;
import com.hisco.admin.member.vo.MemberCardVO;
import com.hisco.admin.member.vo.MemberDiscVO;
import com.hisco.admin.member.vo.MemberUserVO;
import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgDcVO;
import com.hisco.admin.orginfo.vo.OrgInfoVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;
import com.hisco.user.mypage.service.MyRsvnService;
import com.hisco.user.mypage.vo.MyRsvnVO;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원현황 관리
 *
 * @author 진수진
 * @since 2021.11.17
 * @version 1.0, 2021.11.17
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2021.11.17 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}" })
public class MemberTabController {
    @Resource(name = "memberService")
    private MemberService memberService;

    @Resource(name = "memberTabService")
    private MemberTabService memberTabService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    @Resource(name = "myRsvnService")
    private MyRsvnService myRsvnService;

    /**
     * 수강이력
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping("/member/userTabClassAjax")
    public String userTabClass(@ModelAttribute("searchVO") MemberUserVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> paramMap) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();

        MyRsvnVO myRsvnVO = new MyRsvnVO();
        myRsvnVO.setComcd(Config.COM_CD);
        myRsvnVO.setEdcRsvnMemno(searchVO.getMemNo());
        myRsvnVO.setPaginationInfo(paginationInfo); // 페이징 파라미터 추가
        List<EdcRsvnInfoVO> rsvnList = myRsvnService.selectMyEdcPagingList(myRsvnVO);

        int totCount = 0;
        if (rsvnList != null && !rsvnList.isEmpty()) {
            totCount = ((EdcRsvnInfoVO) rsvnList.get(0)).getTotCount();
        }

        paginationInfo.setTotalRecordCount(totCount);

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("rsvnList", rsvnList);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 신용결제이력
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping("/member/userTabPayAjax")
    public String userTabPayAjax(@ModelAttribute("searchVO") MemberUserVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();

        MyRsvnVO myRsvnVO = new MyRsvnVO();
        myRsvnVO.setEdcRsvnMemno(searchVO.getMemNo());
        myRsvnVO.setPaginationInfo(paginationInfo); // 페이징 파라미터 추가
        List<EdcRsvnInfoVO> pgList = myRsvnService.selectMyPgHistory(myRsvnVO);

        int totCount = 0;
        if (pgList != null && !pgList.isEmpty()) {
            totCount = ((EdcRsvnInfoVO) pgList.get(0)).getTotCount();
        }

        paginationInfo.setTotalRecordCount(totCount);

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("pgList", pgList);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 회원카드 목록
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping("/member/userTabCardAjax")
    public String userTabCardAjax(@ModelAttribute("searchVO") MemberUserVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);

        List<?> cardList = memberTabService.selectCardList(searchVO);

        if (cardList.size() >= 1) {
            MemberCardVO cardVO = (MemberCardVO) cardList.get(0);
            paginationInfo.setTotalRecordCount(cardVO.getTotalCnt());
        }

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("list", cardList);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 회원감면 정보 목록
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping("/member/userTabDiscAjax")
    public String userTabDiscAjax(@ModelAttribute("searchVO") MemberUserVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        List<?> discList = memberTabService.selectDiscountList(searchVO);

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("list", discList);

        return HttpUtility.getViewUrl(request);
    }

    
    /**
     * 회원 가족사항 목록
     * 
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping("/member/userTabFamilyAjax")
    public String userTabFamilyAjax(@ModelAttribute("searchVO") MemberUserVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        List<?> discList = memberTabService.selectDiscountList(searchVO);

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("list", discList);

        return HttpUtility.getViewUrl(request);
    }    
    
    
    /**
     * 기관별 감면 설정 목록
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping("/member/userTabDclist.json")
    @ResponseBody
    public ModelAndView userTabDclist(@ModelAttribute("orgInfoVO") OrgInfoVO orgInfoVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        List<OrgDcVO> discList = orgInfoService.selectDcList(orgInfoVO.getComcd(), orgInfoVO.getOrgNo());
        List<OrgDcVO> newList = new ArrayList<OrgDcVO>();

        if (discList != null) {
            for (OrgDcVO dcVO : discList) {
                if (dcVO.getDcYn() != null && !dcVO.getDcYn().equals("")) {
                    newList.add(dcVO);
                }
            }
        }

        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("discList", newList);
        return mav;
    }

    /**
     * 기관별 감면 정보 저장
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "회원  감면정보 추가", action = "U", ajax = true)
    @PostMapping("/member/userTabDcSave.json")
    @ResponseBody
    public ModelAndView userTabDcSave(@ModelAttribute("memberDiscVO") MemberDiscVO memberDiscVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        ResultInfo resultInfo = new ResultInfo();
        memberDiscVO.setComcd(Config.COM_CD);

        if (memberDiscVO.getDiscountSeq() > 0) {
            int cnt = memberTabService.updateDiscount(memberDiscVO);

            if (cnt > 0) {
                resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다");
            } else {
                resultInfo = HttpUtility.getErrorResultInfo("이미 등록된 할인 사유 입니다.");
            }

        } else {
            int cnt = memberTabService.insertDiscount(memberDiscVO);

            if (cnt > 0) {
                resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다");
            } else {
                resultInfo = HttpUtility.getErrorResultInfo("이미 등록된 할인 사유 입니다.");
            }
        }

        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 기관별 감면 정보 저장
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "회원  감면정보 삭제", action = "U", ajax = true)
    @PostMapping("/member/userTabDcDelete.json")
    @ResponseBody
    public ModelAndView userTabDcDelete(@ModelAttribute("memberDiscVO") MemberDiscVO memberDiscVO,
            CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        ResultInfo resultInfo = new ResultInfo();
        memberDiscVO.setComcd(Config.COM_CD);

        int cnt = memberTabService.deleteDiscount(memberDiscVO);

        if (cnt > 0) {
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다");
        } else {
            resultInfo = HttpUtility.getErrorResultInfo("데이타 없음");
        }

        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 기관별 감면 설정 상세 조회
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping("/member/userTabDcDetail.json")
    @ResponseBody
    public ModelAndView userTabDcDetail(@ModelAttribute("MemberDiscVO") MemberDiscVO memberDiscVO,
            CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        memberDiscVO.setComcd(Config.COM_CD);
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("item", memberTabService.selectDiscountRecord(memberDiscVO));
        return mav;
    }
}
