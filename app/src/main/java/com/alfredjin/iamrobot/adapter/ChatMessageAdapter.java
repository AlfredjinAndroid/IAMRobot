package com.alfredjin.iamrobot.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfredjin.iamrobot.R;
import com.alfredjin.iamrobot.bean.ChatMessage;
import com.alfredjin.iamrobot.utils.ContentUtil;
import com.alfredjin.iamrobot.utils.LogUtil;
import com.alfredjin.iamrobot.utils.SharedPreferenceUtil;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Created by AlfredJin on 2017/3/14.
 */
public class ChatMessageAdapter extends BaseAdapter {

    private Context context;
    private List<ChatMessage> mDatas;
    private LayoutInflater mInflater;

    public ChatMessageAdapter(Context context, List<ChatMessage> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas == null ? null : mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = mDatas.get(position);
        if (chatMessage.getType() == ChatMessage.Type.INCOMING) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;//布局类型个数
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage = mDatas.get(i);
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            //通过ItemType设置不同的布局
            if (getItemViewType(i) == 0) {
                view = mInflater.inflate(R.layout.item_from_msg, viewGroup, false);
                viewHolder.mDate = (TextView) view.findViewById(R.id.tv_from_msg_date);
                viewHolder.mMsg = (TextView) view.findViewById(R.id.tv_from_msg_info);
                viewHolder.item_news_rl = view.findViewById(R.id.item_news_rl);
                viewHolder.item_text_ll = view.findViewById(R.id.item_text_ll);
                viewHolder.item_news_icon = (ImageView) view.findViewById(R.id.item_news_icon);
                viewHolder.item_news_text = (TextView) view.findViewById(R.id.item_news_text);

                viewHolder.item_cook_rl = view.findViewById(R.id.item_cook_rl);
                viewHolder.item_cook_name = (TextView) view.findViewById(R.id.cook_name);
                viewHolder.item_cook_info = (TextView) view.findViewById(R.id.cook_info);


                viewHolder.chat_icon = (ImageView) view.findViewById(R.id.robot_icon);

            } else if (getItemViewType(i) == 1) {
                view = mInflater.inflate(R.layout.item_to_msg, viewGroup, false);
                viewHolder.mDate = (TextView) view.findViewById(R.id.tv_to_msg_date);
                viewHolder.mMsg = (TextView) view.findViewById(R.id.tv_to_msg_info);
                viewHolder.chat_icon = (ImageView) view.findViewById(R.id.my_icon);
            }
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //设置数据
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.mDate.setText(dateFormat.format(chatMessage.getDate()));
        if (getItemViewType(i) == 0) {
            int robot_icon = SharedPreferenceUtil.getInt("robot_icon", R.drawable.roboticon, context);
            LogUtil.e("设置机器人头像"+robot_icon);
            viewHolder.chat_icon.setImageResource(robot_icon);

            for (int j = 0; j < 3; j++) {
                viewHolder.item_text_ll.setVisibility(View.GONE);
                viewHolder.item_news_rl.setVisibility(View.GONE);
                viewHolder.item_cook_rl.setVisibility(View.GONE);
            }
        } else if (getItemViewType(i) == 1) {
            int user_icon = SharedPreferenceUtil.getInt("user_icon", R.drawable.icon36,
                    context);
            LogUtil.e("设置用户头像" + user_icon);
            viewHolder.chat_icon.setImageResource(user_icon);
        }
        if (chatMessage.getMsgType() == ContentUtil.RESULT_CODE_LINK) {
            viewHolder.item_text_ll.setVisibility(View.VISIBLE);
            String msg = chatMessage.getMsg();
            String[] split = msg.split("\n");
            viewHolder.mMsg.setText(
                    Html.fromHtml(split[0] + "<br><a href=\"\">" + split[1] + "</font>"));
        } else if (chatMessage.getMsgType() == ContentUtil.RESULT_CODE_TEXT) {
            viewHolder.item_text_ll.setVisibility(View.VISIBLE);
            viewHolder.mMsg.setText(chatMessage.getMsg());
        } else if (chatMessage.getMsgType() == ContentUtil.RESULT_CODE_NEWS) {
            viewHolder.item_news_rl.setVisibility(View.VISIBLE);
            viewHolder.item_news_text.setText(chatMessage.getMsg());
            Glide.with(context)
                    .load(chatMessage.getImageUrl())
                    .into(viewHolder.item_news_icon);
        } else if (chatMessage.getMsgType() == ContentUtil.RESULT_CODE_COOK) {
            viewHolder.item_cook_rl.setVisibility(View.VISIBLE);
            viewHolder.item_cook_name.setText(
                    "名称:" + chatMessage.getCookName()
            );
            viewHolder.item_cook_info.setText(
                    "信息" + chatMessage.getCookInfo()
            );
        } else {
            viewHolder.mMsg.setText(chatMessage.getMsg());
        }
        return view;
    }

    private final class ViewHolder {
        View item_text_ll;
        TextView mDate;
        TextView mMsg;
        View item_news_rl;
        ImageView item_news_icon;
        TextView item_news_text;
        /**
         * 菜单类
         */
        View item_cook_rl;
        TextView item_cook_name;
        TextView item_cook_info;

        ImageView chat_icon;
    }
}
