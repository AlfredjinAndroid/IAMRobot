package com.alfredjin.iamrobot.bean;

import java.util.List;

/**
 * Created by AlfredJin on 2017/4/4.
 */

public class NewsResult extends BaseResult {
    private String text;
    private List<News> list;

    public NewsResult(String text, List<News> list) {
        this.text = text;
        this.list = list;
    }

    public NewsResult() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<News> getList() {
        return list;
    }

    public void setList(List<News> list) {
        this.list = list;
    }
}
