package com.hisco.intrfc.captcha.web;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.intrfc.captcha.service.SetKorVoiceProducer;
import com.hisco.intrfc.captcha.service.SetTextProducer;

import lombok.extern.slf4j.Slf4j;
import nl.captcha.Captcha;
import nl.captcha.audio.AudioCaptcha;
import nl.captcha.audio.producer.VoiceProducer;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.NumbersAnswerProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;

@Slf4j
@Controller
@RequestMapping(value = Config.INTRFC_ROOT + "/captcha")
public class CaptchaController {
    private static final int width = 150;
    private static final int height = 50;

    @RequestMapping(value = "/string")
    public String String(
            Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 폰트 설정
        List<Font> fontList = new ArrayList<Font>();
        fontList.add(new Font("", Font.HANGING_BASELINE, 40));
        fontList.add(new Font("Courier", Font.ITALIC, 40));
        fontList.add(new Font("", Font.PLAIN, 40));

        List<Color> colorList = new ArrayList<Color>();
        colorList.add(Color.black);

        Captcha captcha = new Captcha.Builder(width, height).addText(new NumbersAnswerProducer(6), new DefaultWordRenderer(colorList, fontList))
                // .gimp(new DropShadowGimpyRenderer()).gimp()
                // BlockGimpyRenderer, FishEyeGimpyRenderer, RippleGimpyRenderer, ShearGimpyRenderer,
                // StretchGimpyRenderer
                .addBackground(new GradiatedBackgroundProducer()).addNoise().addBorder().build();

        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("image/png");

        if (log.isDebugEnabled()) {
            log.debug("Captcha Session Name : " + Captcha.NAME);
            log.debug("Captcha Session Value : " + captcha.getAnswer());
        }
        // 세션에 Captcha 문자를 저장
        request.getSession().setAttribute(Captcha.NAME, captcha.getAnswer());

        // 이미지 출력
        CaptchaServletUtil.writeImage(response, captcha.getImage());

        return null;
    }

    @RequestMapping(value = "/audio")
    public void Audio(
            Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String answer = (String) request.getSession().getAttribute(Captcha.NAME);

        if (log.isDebugEnabled()) {
            log.debug("Captcha Session Value : " + answer);
        }

        // if (!CommonUtil.getString(answer).equals("")) {

        try {

            VoiceProducer vProd = new SetKorVoiceProducer();

            AudioCaptcha audioCaptcha = new AudioCaptcha.Builder().addAnswer(new SetTextProducer(answer)).addVoice(vProd).build();

            CaptchaServletUtil.writeAudio(response, audioCaptcha.getChallenge());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // }

    }

    @ResponseBody
    @PostMapping(value = "/check", produces = "application/json; charset=UTF-8")
    public ModelAndView Check(
            Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        try {
            String answer = request.getParameter("answer");
            String store_answer = CommonUtil.getString(request.getSession().getAttribute(Captcha.NAME));

            if (log.isDebugEnabled()) {
                log.debug("Captcha answer : " + answer);
                log.debug("Captcha Session Value : " + store_answer);
            }
            if (store_answer.equals(answer)) {
                mav.addObject("result", "OK");
            } else {
                mav.addObject("result", "ERROR");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mav.addObject("result", "ERROR");
        }

        return mav;
    }
}
