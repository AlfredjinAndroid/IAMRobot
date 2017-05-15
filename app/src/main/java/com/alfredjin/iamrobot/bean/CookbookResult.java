package com.alfredjin.iamrobot.bean;


import java.util.List;

/**
 * Created by AlfredJin on 2017/4/4.
 */

public class CookbookResult extends BaseResult {
    private String text;
    private List<CookBook> list;

    public CookbookResult(String text, List<CookBook> list) {
        this.text = text;
        this.list = list;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<CookBook> getList() {
        return list;
    }

    public void setList(List<CookBook> list) {
        this.list = list;
    }
}
