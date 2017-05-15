package com.alfredjin.iamrobot.utils;

import android.util.Log;

import static com.alfredjin.iamrobot.utils.ContentUtil.LOG_TAG;
import static com.alfredjin.iamrobot.utils.ContentUtil.isTest;

/**
 * @author Created by AlfredJin on 2016/12/5.
 *         包装Log类，增加一个boolean类型的isTest,
 *         如果isTest为true即是测试时，打印日志信息
 *         如果是false即不是测试时，关闭打印日志信息，节省启动时间
 */

public class LogUtil {
    //e级别
    public static void e(String msg) {
        if (isTest)
            Log.e(LOG_TAG, msg);
    }

    //w级别
    public static void w(String msg) {
        if (isTest)
            Log.w(LOG_TAG, msg);
    }

    //i级别
    public static void i(String msg) {
        if (isTest)
            Log.i(LOG_TAG, msg);
    }

    //d级别
    public static void d(String msg) {
        if (isTest)
            Log.d(LOG_TAG, msg);
    }

    //v级别
    public static void v(String msg) {
        if (isTest)
            Log.v(LOG_TAG, msg);
    }
}
