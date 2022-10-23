package com.hisco.intrfc.sale.vo;

public enum NwPayApiType {

    APIKEY("apikey"), APIKEYCHECK("apikeycheck"), BALANCE("balance"), SENDORDER("sendorder"), WITHDRAWAL("withdrawal");

    final private String api;

    public String getApi() {
        return api;
    }

    private NwPayApiType(String api) {
        this.api = api;
    }
}
