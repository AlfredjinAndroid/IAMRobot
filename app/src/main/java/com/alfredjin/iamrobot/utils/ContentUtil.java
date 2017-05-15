package com.alfredjin.iamrobot.utils;

/**
 * @author Created by AlfredJin on 2016/12/5.
 *         <p>
 *         工具类
 */

public class ContentUtil {
    //使用Bmob后端云所依赖的应用ID
    public static final String APPLICATION_ID = "974badbfb464d104aec6e812ac36960f";

    //是否显示日志
    static final boolean isTest = true;

    //日志Tag
    static final String LOG_TAG = "Robot";

    /**
     * 异常状态码
     */
    public static final int ERROR_CODE_KEY = 40001;//参数key错误
    public static final int ERROR_CODE_INFO = 40002;//请求内容Info为空
    public static final int ERROR_CODE_REQUEST = 40004;//当天请求次数已使用完
    public static final int ERROR_CODE_DATA = 40007;//数据格式异常

    public static final int RESULT_CODE_SONG = 313000;//歌曲
    public static final int RESULT_CODE_COOK = 308000;//菜谱类
    public static final int RESULT_CODE_TEXT = 100000;//文本类
    public static final int RESULT_CODE_LINK = 200000;//链接类
    public static final int RESULT_CODE_NEWS = 302000;//新闻类


    public static final String ROBOT_URL = "http://www.tuling123.com/openapi/api";
    public static final String ROBOT_API_KEY = "fb14640eb9944815a0378650ddd12c82";

    public static final String TO_GUIDE = "to_guide";

    public static final String TO_LOGIN = "to_login";

    public static final String USERNAME = "user_name";

    public static final String PASSWORD = "password";

    public static final String ROBOT = "robot";

    public static final String SEX = "sex";

    public static final String DB_NAME = "robot.db";

    public static final int VERSION = 1;

    public static final String SPEAKER = "speaker";

    public static boolean CLOSE_SETTING = false;

}
