package com.alfredjin.iamrobot.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alfredjin.iamrobot.R;
import com.alfredjin.iamrobot.bean.ChatMessage;
import com.alfredjin.iamrobot.holder.ViewHolder;
import com.alfredjin.iamrobot.utils.ContentUtil;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by AlfredJin on 2017/4/19.
 */

public class ChatAdapter extends BaseAdapter {


    private Context mContext;
    private List<ChatMessage> chatMessageList;

    public ChatAdapter(Context mContext, List<ChatMessage> chatMessageList) {
        this.mContext = mContext;
        this.chatMessageList = chatMessageList;
    }

    @Override
    public int getCount() {
        return chatMessageList == null ? 0 : chatMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatMessageList == null ? null : chatMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessage = chatMessageList.get(position);
        ChatViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ChatViewHolder();
            convertView = View.inflate(mContext, R.layout.item_chat_msg, null);
            viewHolder.chat_date = (TextView) convertView.findViewById(R.id.chat_date);
            viewHolder.chat_msg = (TextView) convertView.findViewById(R.id.chat_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChatViewHolder) convertView.getTag();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.chat_date.setText(dateFormat.format(chatMessage.getDate()));
        String msg = "";
        if (chatMessage.getMsgType() == ContentUtil.RESULT_CODE_COOK) {
            msg = "名称" + chatMessage.getCookName() + "\n信息" + chatMessage.getCookInfo();
        } else if (chatMessage.getMsgType() == ContentUtil.RESULT_CODE_LINK) {
            msg = "链接" + chatMessage.getMsg();
        } else if (chatMessage.getMsgType() == ContentUtil.RESULT_CODE_NEWS) {
            msg = "新闻" + chatMessage.getMsg();
        } else {
            msg = chatMessage.getMsg();
        }

        viewHolder.chat_msg.setText(msg);

        return convertView;
    }

    private class ChatViewHolder {
        TextView chat_date;
        TextView chat_msg;
    }

}
