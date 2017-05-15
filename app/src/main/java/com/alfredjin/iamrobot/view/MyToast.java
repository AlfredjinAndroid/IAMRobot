package com.alfredjin.iamrobot.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alfredjin.iamrobot.R;

/**
 * Created by AlfredJin on 2017/4/11.
 */

public class MyToast {
    private static PopupWindow popupWindow;
    private Context mContext;
    public static final int LENGTH_SHORT = 1000;
    private static final int LENGTH_LONG = 3000;
    private static MyToast myToast;
    private static Activity mActivity;
    private int duration;

    private MyToast() {
    }

    public static MyToast makeText(Context context, String msg, int duration) {
        mActivity = (Activity) context;
        myToast = new MyToast();
        myToast.duration = duration;
        View view = View.inflate(context, R.layout.toastview, null);
        TextView toast_tv = (TextView) view.findViewById(R.id.tv_toast);
        toast_tv.setText(msg);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        popupWindow = new PopupWindow(view, widthPixels, heightPixels / 10);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        return myToast;
    }

    public void show() {
        if (myToast != null && popupWindow != null && !popupWindow.isShowing()) {
            View parent = ((ViewGroup) mActivity.findViewById(android.R.id.content)).getChildAt(0);
            popupWindow.showAtLocation(parent, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    popupWindow.dismiss();
                }
            }, duration);
        }
    }
}
