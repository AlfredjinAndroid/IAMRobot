package com.alfredjin.iamrobot.bean;

/**
 * Created by AlfredJin on 2017/4/4.
 */

public class LinkResult extends BaseResult {
    private String text;
    private String url;

    public LinkResult(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public LinkResult() {
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

    @Override
    public String toString() {
        return "LinkResult{" +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
