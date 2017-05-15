package com.alfredjin.iamrobot.bean;

/**
 * Created by AlfredJin on 2017/4/4.
 */

public class TextResult extends BaseResult {
    private String text;

    public TextResult(String text) {
        this.text = text;
    }

    public TextResult() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
