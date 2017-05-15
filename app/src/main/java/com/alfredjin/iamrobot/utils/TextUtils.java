package com.alfredjin.iamrobot.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by AlfredJin on 2017/4/9.
 */

public class TextUtils {

    private static TextUtils textUtils;

    private static ClipboardManager mClip = null;
    private static Context mContext;

    private TextUtils() {
    }

    public static TextUtils getInstance(Context context) {
        if (textUtils == null) {
            textUtils = new TextUtils();
        }
        mContext = context;
        return textUtils;
    }

    public void copyText(String msg) {
        if (mClip == null) {
            mClip = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        }
        ClipData clipData = ClipData.newPlainText("text", msg);
        mClip.setPrimaryClip(clipData);
    }

    /**
     * 吐丝吧少年
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
