package com.alfredjin.iamrobot.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alfredjin.iamrobot.R;
import com.alfredjin.iamrobot.adapter.ChatAdapter;
import com.alfredjin.iamrobot.bean.ChatMessage;
import com.alfredjin.iamrobot.dao.ChatMessageDao;
import com.alfredjin.iamrobot.databinding.ActivityChatBinding;
import com.alfredjin.iamrobot.utils.ContentUtil;
import com.alfredjin.iamrobot.utils.LogUtil;
import com.alfredjin.iamrobot.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    ChatMessageDao chatMessageDao;
    public static List<ChatMessage> userChat;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        chatMessageDao = new ChatMessageDao(this);
        initData();
    }

    private void initData() {
        binding.chatLv.setEmptyView(binding.chatEmptyImg);
        LogUtil.w("查询数据，设置ChatActivity数据");
        all = chatMessageDao.findAll();
        userChat = new ArrayList<>();
        String userName = SharedPreferenceUtil.getString(ContentUtil.USERNAME, null, this);
        for (ChatMessage chatMessage : all) {
            LogUtil.e(";;;;;;" + chatMessage.getMsg());
            if (chatMessage.getName() != null && chatMessage.getName().equals(userName)) {
                userChat.add(chatMessage);
            }
        }
        if (chatAdapter == null) {
            chatAdapter = new ChatAdapter(this, userChat);
        }
        binding.chatLv.setAdapter(chatAdapter);
        LogUtil.w("执行结束");
    }


}
