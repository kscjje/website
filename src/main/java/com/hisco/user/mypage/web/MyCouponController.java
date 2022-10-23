package com.hisco.user.mypage.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.user.member.vo.MemberCouponVO;
import com.hisco.user.mypage.service.MyCouponService;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 마이페이지 쿠폰 컨트롤러
 * 
 * @author 김희택
 * @since 2020.09.15
 * @version 1.0, 2020.09.15
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          김희택 2020.08.15 최초작성
 */
@Controller
@RequestMapping(value = Config.USER_ROOT + "/mypage/myCoupon")
public class MyCouponController {

    //// @Resource(name = "userLoginService")
    //// private UserLoginService userLoginService;

    @Resource(name = "myCouponService")
    private MyCouponService myCouponService;

    //// @Resource(name="namefactService")
    //// private NamefactService namefactService;

    /**
     * 쿠폰정보 조회
     *
     * @param loginVO
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/myCouponList")
    public String selectCouponList(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("memberCouponVO") MemberCouponVO vo) throws Exception {
        // Declare
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        String uniqid = commandMap.getUserInfo().getUniqId();

        vo.setCpnPymntmemno(uniqid);
        vo.setPaginationInfo(paginationInfo);
        Map<String, Object> result = myCouponService.selectCouponList(vo);

        paginationInfo.setTotalRecordCount((int) result.get("listSize"));
        model.addAttribute("cList", result.get("cList"));
        model.addAttribute("listSize", (int) result.get("listSize"));
        model.addAttribute("sKey", vo.getSearchKey());
        model.addAttribute("paginationInfo", paginationInfo);
        // 비밀번호 체크
        return HttpUtility.getViewUrl(request);

    }

    /**
     * 쿠폰 상세
     * 
     * @param commandMap
     * @return ModelAndView
     * @exception Exception
     */
    @GetMapping(value = "/myCouponDetailAjax")
    public String selectCouponDetail(CommandMap commandMap, HttpServletRequest request,
            ModelMap model, MemberCouponVO vo) throws Exception {

        vo.setCpnPymntmemno(commandMap.getUserInfo().getUniqId());

        MemberCouponVO result = myCouponService.selectCouponDetail(vo);
        model.addAttribute("dtl", result);
        return HttpUtility.getViewUrl(request);
    }

    /**
     * 쿠폰 상세
     * 
     * @param commandMap
     * @return ModelAndView
     * @exception Exception
     */
    @GetMapping(value = "/myCouponDetailMobileAjax")
    public String myCouponDetailMobileAjax(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            MemberCouponVO vo) throws Exception {

        vo.setCpnPymntmemno(commandMap.getUserInfo().getUniqId());

        MemberCouponVO result = myCouponService.selectCouponMobileDetail(vo);
        model.addAttribute("dtl", result);

        return HttpUtility.getViewUrl(request);
    }

}
