package com.alfredjin.iamrobot.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by AlfredJin on 2017/4/18.
 * 机器人学习实体类，用来封装用户对机器人学习训练的数据
 */

public class StudyResult extends BmobObject {
    private String user;
    private String question;
    private String answer;
    private int id;
    private int state;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
