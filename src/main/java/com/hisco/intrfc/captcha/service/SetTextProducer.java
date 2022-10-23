package com.hisco.intrfc.captcha.service;

import nl.captcha.text.producer.TextProducer;

public class SetTextProducer implements TextProducer {
    private String answer;

    public SetTextProducer(String answer) {
        this.answer = answer;
    }

    @Override
    public String getText() {
        return answer;
    }
}
