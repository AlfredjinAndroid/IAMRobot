package com.alfredjin.iamrobot.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alfredjin.iamrobot.bean.User;
import com.alfredjin.iamrobot.db.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlfredJin on 2017/4/17.
 */

public class UserDao {

    public static final String TABLE_NAME = "TBL_USER";

    private MyOpenHelper openHelper;
    private SQLiteDatabase db;

    public UserDao() {

    }

    public UserDao(Context context) {
        openHelper = new MyOpenHelper(context);
    }

    /**
     * 插入一条数据
     * userName,password,sex,robotName,userIcon,robotIcon
     *
     * @param user
     */
    public void insert(User user) {
        db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", user.getUserName());
        values.put("password", user.getPassword());
        values.put("sex", user.getSex().toString());
        values.put("robotName", user.getRobotName());
        values.put("userIcon", user.getUserIcon());
        values.put("robotIcon", user.getRobotIcon());
        values.put("state", user.getState());
        db.insert(TABLE_NAME, null, values);
    }

    /**
     * 更新一条数据
     *
     * @param user
     */
    public void update(User user) {
        db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", user.getPassword());
        values.put("sex", user.getSex().toString());
        values.put("robotName", user.getRobotName());
        values.put("userIcon", user.getUserIcon());
        values.put("robotIcon", user.getRobotIcon());
        values.put("state", user.getState());
        db.update(TABLE_NAME, values, "userName=?", new String[]{user.getUserName() + ""});
    }

    /**
     * 删除一条数据
     *
     * @param user
     */
    public void delete(User user) {
        db = openHelper.getWritableDatabase();
        db.delete(TABLE_NAME, "_id=?", new String[]{user.getId() + ""});
    }

    /**
     * 查找所有用户数据
     * userName,password,sex,robotName,userIcon,robotIcon
     *
     * @return
     */
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        db = openHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setUserName(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            String sex_str = cursor.getString(3);
            user.setSex(sex_str.equals("BOY") ? User.Sex.BOY : User.Sex.GIRL);
            user.setRobotName(cursor.getString(4));
            user.setUserIcon(cursor.getLong(5));
            user.setRobotIcon(cursor.getLong(6));
            userList.add(user);
        }
        cursor.close();
        return userList;
    }

    /**
     * 根据userName查找用户数据
     *
     * @param userName
     * @return
     */
    public User findByName(String userName) {
        User user = new User();
        db = openHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "userName=?", new String[]{userName}, null, null, null);
        while (cursor.moveToNext()) {
            user.setId(cursor.getInt(0));
            user.setUserName(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            String sex_str = cursor.getString(3);
            user.setSex(sex_str.equals("BOY") ? User.Sex.BOY : User.Sex.GIRL);
            user.setRobotName(cursor.getString(4));
            user.setUserIcon(cursor.getLong(5));
            user.setRobotIcon(cursor.getLong(6));
        }
        cursor.close();
        return user;
    }

    /**
     * 查找所有需要上传的数据
     *
     * @return
     */
    public List<User> findToUpload() {
        List<User> userList = new ArrayList<>();
        db = openHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "state=0", null, null, null, null);
        while (cursor.moveToNext()) {
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setUserName(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            String sex_str = cursor.getString(3);
            user.setSex(sex_str.equals("BOY") ? User.Sex.BOY : User.Sex.GIRL);
            user.setRobotName(cursor.getString(4));
            user.setUserIcon(cursor.getLong(5));
            user.setRobotIcon(cursor.getLong(6));
            userList.add(user);
        }
        cursor.close();
        return userList;
    }
}
