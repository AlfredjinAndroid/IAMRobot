package com.alfredjin.iamrobot.utils;

import com.alfredjin.iamrobot.bean.BaseResult;
import com.alfredjin.iamrobot.bean.CookbookResult;
import com.alfredjin.iamrobot.bean.ErrorResult;
import com.alfredjin.iamrobot.bean.LinkResult;
import com.alfredjin.iamrobot.bean.NewsResult;
import com.alfredjin.iamrobot.bean.SongResult;
import com.alfredjin.iamrobot.bean.TextResult;
import com.google.gson.Gson;

/**
 * Create By AlfredJin on 2017/4/13
 * 数据解析工具包
 */


public class DataUtils {

    public static BaseResult jsonToBean(Gson gson, String string, BaseResult baseResult) {
        BaseResult base = null;
        switch (baseResult.getCode()) {
            case ContentUtil.ERROR_CODE_DATA:
                base = new ErrorResult("数据格式异常");
                break;
            case ContentUtil.ERROR_CODE_KEY:
                base = new ErrorResult("参数KEY错误");
                break;
            case ContentUtil.ERROR_CODE_INFO:
                base = new ErrorResult("请求Info内容为空");
                break;
            case ContentUtil.ERROR_CODE_REQUEST:
                base = new ErrorResult("当天请求次数已用完");
                break;
            case ContentUtil.RESULT_CODE_TEXT:
                base = gson.fromJson(string, TextResult.class);
                break;
            case ContentUtil.RESULT_CODE_LINK:
                base = gson.fromJson(string, LinkResult.class);
                break;
            case ContentUtil.RESULT_CODE_COOK:
                base = gson.fromJson(string, CookbookResult.class);
                break;
            case ContentUtil.RESULT_CODE_NEWS:
                base = gson.fromJson(string, NewsResult.class);
                break;
            case ContentUtil.RESULT_CODE_SONG:
                base = gson.fromJson(string, SongResult.class);
                break;
        }
        return base;
    }
}
