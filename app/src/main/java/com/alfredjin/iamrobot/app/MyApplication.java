package com.alfredjin.iamrobot.app;

import android.app.Application;

import com.alfredjin.iamrobot.utils.ContentUtil;

import cn.bmob.v3.Bmob;

/**
 * Created by AlfredJin on 2017/4/19.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, ContentUtil.APPLICATION_ID);
    }
}
