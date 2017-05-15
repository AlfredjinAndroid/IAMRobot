package com.alfredjin.iamrobot.utils;

import com.alfredjin.iamrobot.bean.ChatMessage;
import com.alfredjin.iamrobot.bean.Result;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author Created by AlfredJin on 2017/3/14.
 */

public class HttpUtils {
    private static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String API_KEY = "fb14640eb9944815a0378650ddd12c82";


    /**
     * 执行get请求
     *
     * @param msg
     * @return
     */
    public static String doGet(String msg) {
        String result = "";


        String url = setParams(msg);
        HttpURLConnection conn;
        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;

        try

        {
            java.net.URL urlNet = new URL(url);
            conn = (HttpURLConnection)
                    urlNet.openConnection();
            conn.setReadTimeout(5 * 1000);
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            inputStream =
                    conn.getInputStream();
            int len;
            byte[] buffer = new byte[128];
            baos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            result = new String(baos.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        } finally

        {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 拼接URL和字符串
     *
     * @param msg
     * @return
     */
    public static String setParams(String msg) {
        String url = "";
        try {
            url = URL + "?key=" + API_KEY + "&info=" + URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url;
    }


    /**
     * 发送一个消息，得到返回消息
     *
     * @param msg
     * @return
     */
    public static ChatMessage sendMessage(String msg) {
        ChatMessage chatMessage = new ChatMessage();
        String jsonResult = doGet(msg);
        Gson gson = new Gson();
        Result result;

        try {
            result = gson.fromJson(jsonResult, Result.class);
            chatMessage.setMsg(result.getText());
        } catch (Exception e) {
            chatMessage.setMsg("服务器繁忙，请稍后再试");
        }
        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOMING);
        return chatMessage;
    }


}
