package com.alfredjin.iamrobot.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alfredjin.iamrobot.bean.ChatMessage;
import com.alfredjin.iamrobot.db.MyOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AlfredJin on 2017/4/18.
 * 聊天记录数据库的操作类
 */

public class ChatMessageDao {
    public static final String TABLE_NAME = "TBL_CHAT";

    private MyOpenHelper openHelper;
    private SQLiteDatabase db;

    public ChatMessageDao(Context context) {
        openHelper = new MyOpenHelper(context);
    }

    /**
     * 插入数据
     * name,msg,type,date,msgType,imgUrl,newsUrl,
     * title,cookName,cookInfo,cookUrl,state
     *
     * @param chatMessage
     */
    public void insert(ChatMessage chatMessage) {
        db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", chatMessage.getName());
        values.put("msg", chatMessage.getMsg());
        values.put("type", String.valueOf(chatMessage.getType()));
        values.put("date", String.valueOf(chatMessage.getDate()));
        values.put("msgType", chatMessage.getMsgType());
        values.put("imgUrl", chatMessage.getImageUrl());
        values.put("newsUrl", chatMessage.getNewsUrl());
        values.put("title", chatMessage.getTitle());
        values.put("cookName", chatMessage.getCookName());
        values.put("cookInfo", chatMessage.getCookInfo());
        values.put("cookUrl", chatMessage.getCookUrl());
        values.put("state", chatMessage.getState());
        db.insert(TABLE_NAME, null, values);
    }

    /**
     * 更新数据
     *
     * @param chatMessage
     */
    public void update(ChatMessage chatMessage) {
        db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", chatMessage.getName());
        values.put("msg", chatMessage.getName());
        values.put("type", String.valueOf(chatMessage.getType()));
        values.put("date", String.valueOf(chatMessage.getDate()));
        values.put("msgType", chatMessage.getMsgType());
        values.put("imgUrl", chatMessage.getImageUrl());
        values.put("newsUrl", chatMessage.getNewsUrl());
        values.put("title", chatMessage.getTitle());
        values.put("cookName", chatMessage.getCookName());
        values.put("cookInfo", chatMessage.getCookInfo());
        values.put("cookUrl", chatMessage.getCookUrl());
        values.put("state", chatMessage.getState());
        db.update(TABLE_NAME, values, "name=?", new String[]{chatMessage.getName()});
    }

    /**
     * 删除数据
     *
     * @param chatMessage
     */
    public void delete(ChatMessage chatMessage) {
        db = openHelper.getWritableDatabase();
        db.delete(TABLE_NAME, "name=?", new String[]{chatMessage.getCookName()});
    }

    /**
     * 查找所有数据
     *
     * @return
     */
    public List<ChatMessage> findAll() {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        db = openHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            //name,msg,type,date,msgType,imgUrl,newsUrl,
            //title,cookName,cookInfo,cookUrl,state
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setName(cursor.getString(0));
            chatMessage.setMsg(cursor.getString(1));
            chatMessage.setType(cursor.getString(2).equals(String.valueOf(ChatMessage.Type
                    .INCOMING)) ?
                    ChatMessage.Type.INCOMING : ChatMessage.Type.OUTCOMING);
            chatMessage.setDate(new Date(cursor.getString(3)));
            chatMessage.setMsgType(cursor.getInt(4));
            chatMessage.setImageUrl(cursor.getString(5));
            chatMessage.setNewsUrl(cursor.getString(6));
            chatMessage.setTitle(cursor.getString(7));
            chatMessage.setCookName(cursor.getString(8));
            chatMessage.setCookInfo(cursor.getString(9));
            chatMessage.setCookUrl(cursor.getString(10));
            chatMessage.setState(cursor.getInt(11));
            chatMessageList.add(chatMessage);
        }
        cursor.close();
        return chatMessageList;
    }


    /**
     * 查找所有需要上传的数据
     *
     * @return
     */
    public List<ChatMessage> findToUpload() {
        List<ChatMessage> result = new ArrayList<>();
        db = openHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "state=0", null, null, null, null);
        while (cursor.moveToNext()) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setName(cursor.getString(0));
            chatMessage.setMsg(cursor.getString(1));
            chatMessage.setType(cursor.getString(2).equals(String.valueOf(ChatMessage.Type
                    .INCOMING)) ?
                    ChatMessage.Type.INCOMING : ChatMessage.Type.OUTCOMING);
            chatMessage.setDate(new Date(cursor.getString(3)));
            chatMessage.setMsgType(cursor.getInt(4));
            chatMessage.setImageUrl(cursor.getString(5));
            chatMessage.setNewsUrl(cursor.getString(6));
            chatMessage.setTitle(cursor.getString(7));
            chatMessage.setCookName(cursor.getString(8));
            chatMessage.setCookInfo(cursor.getString(9));
            chatMessage.setCookUrl(cursor.getString(10));
            chatMessage.setState(cursor.getInt(11));
            result.add(chatMessage);
        }
        return result;
    }

}
