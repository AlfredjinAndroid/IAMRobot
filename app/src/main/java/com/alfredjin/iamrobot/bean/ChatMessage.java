package com.alfredjin.iamrobot.bean;


import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * @author Created by AlfredJin on 2017/3/14.
 */

public class ChatMessage extends BmobObject {
    private String name;
    private String msg;
    private Type type;
    private Date date;
    private int msgType;
    private String imageUrl;
    private String newsUrl;
    private String title;
    private String cookName;
    private String cookInfo;
    private String cookUrl;
    private int state;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ChatMessage() {
    }

    public ChatMessage(String msg, Type type, Date date) {
        this.msg = msg;
        this.type = type;
        this.date = date;
    }

    public enum Type {
        INCOMING, OUTCOMING, NEWS
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public String getCookInfo() {
        return cookInfo;
    }

    public void setCookInfo(String cookInfo) {
        this.cookInfo = cookInfo;
    }

    public String getCookUrl() {
        return cookUrl;
    }

    public void setCookUrl(String cookUrl) {
        this.cookUrl = cookUrl;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                ", type=" + type +
                ", date=" + date +
                ", msgType=" + msgType +
                '}';
    }
}
