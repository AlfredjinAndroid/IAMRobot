package com.alfredjin.iamrobot.bean;

/**
 * Created by AlfredJin on 2017/4/4.
 */

public class SongResult extends BaseResult {
    private String text;
    private Function function;

    public SongResult(String text, Function function) {
        this.text = text;
        this.function = function;
    }

    public SongResult() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
