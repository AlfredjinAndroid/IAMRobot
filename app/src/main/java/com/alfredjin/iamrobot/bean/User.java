package com.alfredjin.iamrobot.bean;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

/**
 * Created by AlfredJin on 2017/4/4.
 */

public class User extends BmobObject {
    private int id;
    private String userName;
    private String password;
    private String robotName;
    private Sex sex;
    private long userIcon;
    private long robotIcon;
    private int state;

    public enum Sex {
        BOY, GIRL
    }

    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public long getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(long userIcon) {
        this.userIcon = userIcon;
    }

    public long getRobotIcon() {
        return robotIcon;
    }

    public void setRobotIcon(long robotIcon) {
        this.robotIcon = robotIcon;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
