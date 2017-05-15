package com.alfredjin.iamrobot.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by AlfredJin on 2017/4/17.
 */

public class Person extends BmobObject {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
