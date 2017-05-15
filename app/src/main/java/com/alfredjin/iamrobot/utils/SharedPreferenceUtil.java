package com.alfredjin.iamrobot.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreference工具类，用于封装快速存储和取数据的方法
 *
 * @author Created by AlfredJin on 2016/11/17.
 */

public class SharedPreferenceUtil {

    // SharePreference文件的名字
    public static final String SPF_NAME = "Robot_Config";

    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;

    /**
     * 插boolean类型数据
     *
     * @param key
     * @param value
     * @param context
     */
    public static void putBoolean(String key, boolean value, Context context) {
        mSharedPreferences = context.getSharedPreferences(SPF_NAME,
                Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    /**
     * 取boolean类型数据
     *
     * @param key
     * @param defValue
     * @param context
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue,
                                     Context context) {
        mSharedPreferences = context.getSharedPreferences(SPF_NAME,
                Context.MODE_PRIVATE);
        boolean result = mSharedPreferences.getBoolean(key, defValue);
        return result;
    }

    /**
     * 插入字符串类型数据
     *
     * @param key
     * @param value
     * @param context
     */
    public static void putString(String key, String value, Context context) {
        mSharedPreferences = context.getSharedPreferences(SPF_NAME,
                Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.commit();
    }

    /**
     * 取出字符串类型数据
     *
     * @param key
     * @param defValue
     * @param context
     * @return
     */
    public static String getString(String key, String defValue, Context context) {
        mSharedPreferences = context.getSharedPreferences(SPF_NAME,
                Context.MODE_PRIVATE);
        String result = mSharedPreferences.getString(key, defValue);
        return result;
    }

    /**
     * 插入int类型数据
     *
     * @param key
     * @param value
     * @param context
     */
    public static void putInt(String key, int value, Context context) {
        mSharedPreferences = context.getSharedPreferences(SPF_NAME,
                Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    /**
     * 取出int类型数据
     *
     * @param key
     * @param defValue
     * @param context
     * @return
     */
    public static int getInt(String key, int defValue, Context context) {
        mSharedPreferences = context.getSharedPreferences(SPF_NAME,
                Context.MODE_PRIVATE);
        int result = mSharedPreferences.getInt(key, defValue);
        return result;
    }

    /**
     * 通过key值删除某个条目
     *
     * @param key
     * @param context
     */
    public static void remove(String key, Context context) {
        mSharedPreferences = context.getSharedPreferences(SPF_NAME,
                Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.remove(key);
        mEditor.commit();
    }

}
