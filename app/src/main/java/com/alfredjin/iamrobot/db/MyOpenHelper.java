package com.alfredjin.iamrobot.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alfredjin.iamrobot.utils.ContentUtil;

/**
 * Created by AlfredJin on 2017/4/18.
 * SQLite帮助类
 */

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        super(context, ContentUtil.DB_NAME, null, ContentUtil.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表语句
        String sql_user = "create TABLE TBL_USER(_id integer primary key autoincrement," +
                "userName,password,sex,robotName,userIcon,robotIcon,state)";
        String sql_chat = "create TABLE  TBL_CHAT(name,msg,type,date,msgType,imgUrl,newsUrl," +
                "title,cookName,cookInfo,cookUrl,state)";
        String sql_study = "create TABLE TBL_STUDY(_id integer primary key autoincrement," +
                "userName,question,answer,state)";
        //创建表
        db.execSQL(sql_user);
        db.execSQL(sql_chat);
        db.execSQL(sql_study);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
