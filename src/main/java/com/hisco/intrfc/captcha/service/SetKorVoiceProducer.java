package com.hisco.intrfc.captcha.service;

import java.util.HashMap;
import java.util.Map;

import com.hisco.cmm.util.CommonUtil;

import nl.captcha.audio.Sample;
import nl.captcha.audio.producer.VoiceProducer;
import nl.captcha.util.FileUtil;

public class SetKorVoiceProducer implements VoiceProducer {
    private static final Map<Integer, String> DEFAULT_VOICES_MAP;

    static {
        DEFAULT_VOICES_MAP = new HashMap<Integer, String>();
        for (int i = 0; i < 10; i++) {
            DEFAULT_VOICES_MAP.put(i, "/egovframework/sounds/ko/numbers/".concat(String.valueOf(i)).concat(".wav"));
        }
    }

    private Map<Integer, String> voices;

    public SetKorVoiceProducer() {
        this.voices = DEFAULT_VOICES_MAP;
    }

    public SetKorVoiceProducer(Map<Integer, String> voices) {
        this.voices = voices;
    }

    @Override
    public Sample getVocalization(char num) {
        int idx = CommonUtil.getInt(String.valueOf(num), -1);

        String filename = voices.get(idx);
        return FileUtil.readSample(filename);
    }
}
