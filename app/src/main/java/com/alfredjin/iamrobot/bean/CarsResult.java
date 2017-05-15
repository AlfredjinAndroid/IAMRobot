package com.alfredjin.iamrobot.bean;

/**
 * Created by AlfredJin on 2017/4/4.
 */

public class CarsResult extends BaseResult {
    private String text;//提示语
    private String url;//链接地址

    public CarsResult(int code, String text, String url) {
        this.text = text;
        this.url = url;
    }

    public CarsResult() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
