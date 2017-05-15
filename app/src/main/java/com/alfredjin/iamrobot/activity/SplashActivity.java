package com.alfredjin.iamrobot.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alfredjin.iamrobot.R;
import com.alfredjin.iamrobot.app.MainActivity;
import com.alfredjin.iamrobot.utils.ContentUtil;
import com.alfredjin.iamrobot.utils.LogUtil;
import com.alfredjin.iamrobot.utils.SharedPreferenceUtil;

import cn.bmob.v3.Bmob;

/**
 * Create By AlfredJin on 2017/4/11
 * 闪屏页面处理
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LogUtil.e("初始化Bmob");
        initSplash();
    }

    /**
     * 闪屏动画处理
     */
    private void initSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean toGuide = SharedPreferenceUtil.getBoolean(
                        ContentUtil.TO_GUIDE,//检查是否需要进入引导页面
                        true,//默认进入
                        SplashActivity.this);
                boolean toLogin = SharedPreferenceUtil.getBoolean(
                        ContentUtil.TO_LOGIN,
                        true,//默认进入
                        SplashActivity.this
                );
                startActivity(new Intent(SplashActivity.this,
                        toGuide ? GuideActivity.class ://进入引导页
                                toLogin ? LoginActivity.class : MainActivity.class));//进入登录页或主页
                SplashActivity.this.finish();
            }
        }, 3000);
    }
}
