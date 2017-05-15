package com.alfredjin.iamrobot.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alfredjin.iamrobot.bean.StudyResult;
import com.alfredjin.iamrobot.db.MyOpenHelper;
import com.alfredjin.iamrobot.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlfredJin on 2017/4/18.
 */

public class StudyDao {
    public static final String TABLE_NAME = "TBL_STUDY";

    private MyOpenHelper openHelper;
    private SQLiteDatabase db;

    public StudyDao() {
    }

    public StudyDao(Context context) {
        openHelper = new MyOpenHelper(context);
    }

    /**
     * 插入一条数据
     * _id,userName,question,answer,state
     *
     * @param studyResult
     */
    public void insert(StudyResult studyResult) {
        db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", studyResult.getUser());
        values.put("question", studyResult.getQuestion());
        values.put("answer", studyResult.getAnswer());
        values.put("state", studyResult.getState());
        db.insert(TABLE_NAME, null, values);
    }

    /**
     * 更新一条数据
     *
     * @param studyResult
     */
    public void update(StudyResult studyResult) {
        db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", studyResult.getUser());
        values.put("question", studyResult.getQuestion());
        values.put("answer", studyResult.getAnswer());
        values.put("state", studyResult.getState());
        db.update(TABLE_NAME, values, "question=?", new String[]{studyResult.getQuestion()});
    }

    /**
     * 删除一条数据
     *
     * @param studyResult
     */
    public void delete(StudyResult studyResult) {
        db = openHelper.getWritableDatabase();
        db.delete(TABLE_NAME, "question=?", new String[]{studyResult.getQuestion()});
    }

    /**
     * 查找所有数据
     * _id,userName,question,answer,state
     *
     * @return
     */
    public List<StudyResult> findAll() {
        List<StudyResult> resultList = new ArrayList<>();
        db = openHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            StudyResult result = new StudyResult();
            result.setId(cursor.getInt(0));
            result.setUser(cursor.getString(1));
            result.setQuestion(cursor.getString(2));
            result.setAnswer(cursor.getString(3));
            result.setState(cursor.getInt(4));
            resultList.add(result);
        }
        cursor.close();
        LogUtil.e("数据库中查询的长度:" + resultList.size());
        return resultList;
    }

    /**
     * 查找所有需要上传的数据
     *
     * @return
     */
    public List<StudyResult> findToUpload() {
        List<StudyResult> results = new ArrayList<>();
        db = openHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "state=0",
                null, null, null, null);
        while (cursor.moveToNext()) {
            StudyResult result = new StudyResult();
            result.setId(cursor.getInt(0));
            result.setUser(cursor.getString(1));
            result.setQuestion(cursor.getString(2));
            result.setAnswer(cursor.getString(3));
            result.setState(cursor.getInt(4));
            results.add(result);
        }
        cursor.close();
        return results;
    }
}
