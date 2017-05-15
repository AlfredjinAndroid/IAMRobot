package com.alfredjin.iamrobot.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by AlfredJin on 2017/4/4.
 */

public class BaseResult extends BmobObject {
    private int code;

    public BaseResult(int code) {
        this.code = code;
    }

    public BaseResult() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
