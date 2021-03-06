package com.alfredjin.iamrobot.bean;

/**
 * Created by AlfredJin on 2017/4/4.
 */

public class News {
    private String article;
    private String source;
    private String icon;
    private String detailurl;

    public News(String article, String source, String icon, String detailurl) {
        this.article = article;
        this.source = source;
        this.icon = icon;
        this.detailurl = detailurl;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDetailurl() {
        return detailurl;
    }

    public void setDetailurl(String detailurl) {
        this.detailurl = detailurl;
    }
}
