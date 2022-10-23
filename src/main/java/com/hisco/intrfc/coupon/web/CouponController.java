package com.hisco.intrfc.coupon.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.intrfc.coupon.service.CouponService;

import egovframework.com.cmm.LoginVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 쿠폰 컨트롤러
 *
 * @author 전영석
 * @since 2020.09.09
 * @version 1.0, 2020.09.09
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.09.09 최초작성
 */
@Slf4j
@Controller
public class CouponController {

    @Resource(name = "couponService")
    private CouponService couponService;

    /**
     * 쿠폰 정보 노출 및 실시 관련 정보를 조회한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.USER_ROOT + "/coupon/member/couponExecuteList", method = { RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String selectCouponExecuteList(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap) throws Exception {

        // log.debug("call selectCouponExecuteList");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        List<?> lstResult = couponService.selectCouponExecuteList(paramMap);

        // log.debug(lstResult.toString());

        model.addAttribute("couponExecuteList", lstResult);

        return "/web/coupon/member/couponExecuteList";
    }

    /**
     * 쿠폰 정보 노출 및 실시 관련 정보를 조회한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/couponDetail")
    public String selectCouponDetail(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {

        log.debug("selectCouponDetail");

        /*
         * //TODO : 임시 예약번호 삽입
         * String rsvnId = commandMap.getString("qestnarRsvnNo").equals("") ? "" :
         * commandMap.getString("qestnarRsvnNo");
         * if ("".equals(rsvnId)) {
         * String msg = "잘못된 접근입니다.";
         * HttpUtility.sendRedirect(request, response, msg , "/web/main");
         * }
         * CouponRsvnVO rsvnVO = new CouponRsvnVO();
         * rsvnVO.setComcd(Config.COM_CD);
         * rsvnVO.setRsvnId(rsvnId);
         * Map<String,Object> rsResult = couponService.selectRsvnMst(rsvnVO);
         * CouponSendVO svo= (CouponSendVO) rsResult.get("sendVO");
         * int chk = couponService.countCouponResult(svo);
         * if (chk > 0) {
         * String msg = "이미 쿠폰에 참여하셨습니다.";
         * HttpUtility.sendRedirect(request, response, msg , "/web/main");
         * } else if (svo == null) {
         * String msg = "해당 예약정보가 없습니다.";
         * HttpUtility.sendRedirect(request, response, msg , "/web/main");
         * }
         * vo.setComcd(Config.COM_CD);
         * vo.setQestnarId(svo.getQestnarId());
         * Map<String,Object> lstResult = couponService.selectCouponDetail(vo);
         * model.addAttribute("vo", lstResult.get("vo"));
         * model.addAttribute("rsvnVO", rsResult.get("vo"));
         * model.addAttribute("sendVO", svo);
         */
        return HttpUtility.getViewUrl(Config.USER_ROOT, "/coupon/couponDetail");
    }

    /**
     * 쿠폰 정보 노출 및 실시 관련 정보를 조회한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/ucoupon/{couponValue}", method = { RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    // public String selectCouponForMobile(CommandMap commandMap, HttpServletRequest request, HttpServletResponse
    // response, ModelMap model, CouponMstVO vo, @PathVariable(value="couponValue") String strCouponValue) throws
    // Exception {
    public String selectCouponForMobile(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @PathVariable(value = "couponValue") String strCouponValue) throws Exception {

        log.debug("call selectCouponForMobile");
        log.debug(strCouponValue);

        /*
         * strCouponValue = strCouponValue.replace("_", "/");
         * String strCouponKey = EgovProperties.getProperty("Globals.DbEncKey");
         * if ((strCouponKey == null) || (strCouponValue == null)) {
         * String strMsg = "잘못된 접근입니다.";
         * HttpUtility.sendRedirect(request, response, strMsg, "/web/main");
         * }
         * Map<String, Object> paramMap = new HashMap<String, Object>();
         * paramMap.put("encCoupon", strCouponValue);
         * paramMap.put("dbEncKey", strCouponKey);
         * List<?> couponResult = couponService.selectCouponNumber(paramMap);
         * String strDecyCoupon = "";
         * if (couponResult.size() >= 1) {
         * Map<String, Object> couponMap = (Map<String, Object>)couponResult.get(0);
         * strDecyCoupon = String.valueOf(couponMap.get("decyCoupon"));
         * if (strDecyCoupon == null) {
         * String strMsg = "잘못된 접근입니다.";
         * HttpUtility.sendRedirect(request, response, strMsg, "/web/main");
         * }
         * LoginVO loginUser = commandMap.getUserInfo();
         * if (loginUser == null) {
         * HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/member/login?returnURL=" +
         * URLEncoder.encode("/web/mypage/myCoupon/myCouponList?couponNum=" + strDecyCoupon, "UTF-8"));
         * } else {
         * HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/mypage/myCoupon/myCouponList?couponNum="
         * + strDecyCoupon);
         * }
         * }
         */

        if (strCouponValue == null) {
            String strMsg = "잘못된 접근입니다.";
            HttpUtility.sendRedirect(request, response, strMsg, "/web/main");
        }

        LoginVO loginUser = commandMap.getUserInfo();
        if (loginUser == null) {
            HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/member/login?returnURL=/web/mypage/myCoupon/myCouponList?couponNum=" + strCouponValue + "&member_yn=Y");
        } else {
            HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/mypage/myCoupon/myCouponList?couponNum=" + strCouponValue);
        }

        return "/web/main";

    }

    /**
     * 쿠폰 정보 노출 및 실시 관련 정보를 조회한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    // @SuppressWarnings("unused")
    // @PostMapping(value = "/couponAction")
    // public String insertCouponDetail(CommandMap commandMap ,HttpServletRequest request, ModelMap model,
    // @ModelAttribute("couponResultVO") CouponResultVO vo) throws Exception {
    //
    // vo.setComcd(Config.COM_CD);
    //
    // Map<String,Object> rsResult = couponService.insertCouponResult(vo);
    //
    // return HttpUtility.getViewUrl(Config.USER_ROOT, "/coupon/couponResult");
    // }

}
