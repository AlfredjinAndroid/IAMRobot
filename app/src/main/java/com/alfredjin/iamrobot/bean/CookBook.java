package com.alfredjin.iamrobot.bean;

/**
 * Created by AlfredJin on 2017/4/4.
 */

public class CookBook {

    private String name;
    private String icon;
    private String info;
    private String detailurl;

    public CookBook(String name, String icon, String info, String detailurl) {
        this.name = name;
        this.icon = icon;
        this.info = info;
        this.detailurl = detailurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDetailurl() {
        return detailurl;
    }

    public void setDetailurl(String detailurl) {
        this.detailurl = detailurl;
    }
}
