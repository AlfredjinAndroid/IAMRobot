package com.alfredjin.iamrobot.bean;

/**
 * Created by AlfredJin on 2017/4/4.
 */

public class ErrorResult extends BaseResult {
    private String text; // 异常说明

    public ErrorResult( String text) {
        this.text = text;
    }

    public ErrorResult() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
