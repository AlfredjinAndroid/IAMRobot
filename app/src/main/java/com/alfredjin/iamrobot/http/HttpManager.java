package com.alfredjin.iamrobot.http;

import com.alfredjin.iamrobot.utils.ContentUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Create By AlfredJin on 2017/4/11
 * Http请求管理类，负责GET请求并获取数据
 */


public class HttpManager {
    private static HttpManager httpManager = null;
    private OkHttpClient okHttpClient;

    private HttpManager() {
        okHttpClient = new OkHttpClient();
        okHttpClient.connectTimeoutMillis();
        okHttpClient.readTimeoutMillis();
        okHttpClient.writeTimeoutMillis();
    }

    public static HttpManager getInstance() {
        if (httpManager == null) {
            httpManager = new HttpManager();
        }
        return httpManager;
    }

    public void get(String msg, Callback callback) {
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(getParam(msg)).build();
        //3、将request封装成call
        Call newCall = okHttpClient.newCall(request);
        newCall.enqueue(callback);
    }

    private String getParam(String msg) {
        String url = "";
        try {
            url = ContentUtil.ROBOT_URL + "?key=" + ContentUtil.ROBOT_API_KEY
                    + "&info=" + URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

}
