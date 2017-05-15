package com.alfredjin.iamrobot.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.alfredjin.iamrobot.R;
import com.alfredjin.iamrobot.adapter.ChatMessageAdapter;
import com.alfredjin.iamrobot.bean.ChatMessage;
import com.alfredjin.iamrobot.utils.ContentUtil;
import com.alfredjin.iamrobot.utils.SharedPreferenceUtil;
import com.alfredjin.iamrobot.utils.TTSUtil;
import com.alfredjin.iamrobot.utils.TextUtils;
import com.baidu.tts.client.SpeechSynthesizer;

import java.util.List;

/**
 * Created by AlfredJin on 2017/4/9.
 * PopWindow用于显示弹出窗口
 */

public class PopView {
    private static PopupWindow popupWindow;
    private static PopView popView;
    private View view;
    private Context mContext;
    private TTSUtil ttsUtil;

    private PopView() {
    }

    public static PopView getInstance() {
        if (popView == null) {
            popView = new PopView();
        }
        return popView;
    }

    public void showPop(Context context, View parent, int position, List<ChatMessage> mDatas, ChatMessageAdapter mAdapter) {
        mContext = context;
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return;
        }
        view = View.inflate(context, R.layout.popview, null);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        popupWindow = new PopupWindow(view, widthPixels, heightPixels);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        initListener(position, mDatas, mAdapter);
    }

    private void initListener(final int position, final List<ChatMessage> mDatas, final ChatMessageAdapter mAdapter) {

        view.findViewById(R.id.pp_tv_item1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatMessage chatMessage = mDatas.get(position);
                String cp_str;
                if (chatMessage.getMsgType() == ContentUtil.RESULT_CODE_NEWS) {
                    cp_str = chatMessage.getMsg();
                } else if (chatMessage.getMsgType() == ContentUtil.RESULT_CODE_COOK) {
                    cp_str = chatMessage.getCookName() + "\n" + chatMessage.getCookInfo();
                } else {
                    cp_str = chatMessage.getMsg();
                }
                TextUtils.getInstance(mContext).copyText(cp_str);
                MyToast.makeText(mContext, "复制成功", MyToast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        view.findViewById(R.id.pp_tv_item2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(position);
                mAdapter.notifyDataSetChanged();
                MyToast.makeText(mContext, "删除成功", MyToast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        view.findViewById(R.id.pp_tv_item3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.clear();
                mAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                MyToast.makeText(mContext, "清除成功", MyToast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.pp_tv_item4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatMessage chatMessage = mDatas.get(position);
                String speak_str;
                if (chatMessage.getMsgType() == ContentUtil.RESULT_CODE_NEWS) {
                    speak_str = chatMessage.getMsg();
                } else if (chatMessage.getMsgType() == ContentUtil.RESULT_CODE_COOK) {
                    speak_str = chatMessage.getCookName() + chatMessage.getCookInfo();
                } else {
                    speak_str = chatMessage.getMsg();
                }
                if (android.text.TextUtils.isEmpty(speak_str.trim())){
                    speak_str = "啥都没有，你想让我读什么";
                }
                ttsUtil = TTSUtil.getInstance(mContext);
                String speaker = SharedPreferenceUtil.getString(ContentUtil.SPEAKER,"0",mContext);
                ttsUtil.getmSpeechSynthesizer().setParam(SpeechSynthesizer.PARAM_SPEAKER, speaker);
                ttsUtil.speak(speak_str);
                popupWindow.dismiss();
                MyToast.makeText(mContext, "朗读", MyToast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.pp_ll_bank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
    }
}
