package com.alfredjin.iamrobot.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.database.DatabaseUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alfredjin.iamrobot.R;
import com.alfredjin.iamrobot.bean.StudyResult;
import com.alfredjin.iamrobot.dao.StudyDao;
import com.alfredjin.iamrobot.databinding.ActivityAddStudyBinding;
import com.alfredjin.iamrobot.utils.ContentUtil;
import com.alfredjin.iamrobot.utils.LogUtil;
import com.alfredjin.iamrobot.utils.SharedPreferenceUtil;
import com.alfredjin.iamrobot.view.MyToast;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddStudyActivity extends AppCompatActivity {

    private ActivityAddStudyBinding binding;
    private StudyDao studyDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_study);
        studyDao = new StudyDao(this);
        initListener();
    }

    /**
     * 事件处理
     */
    private void initListener() {
        binding.addStudySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudy();
            }
        });
    }

    /**
     * 添加训练方法
     */
    private void addStudy() {
        String question = binding.addStudyQuestion.getText().toString().trim();
        String answer = binding.addStudyAnswer.getText().toString().trim();
        if (TextUtils.isEmpty(question)) {
            showToast("请输入问题");
            return;
        }
        if (TextUtils.isEmpty(answer)) {
            showToast("请输入答案");
            return;
        }
        LogUtil.e("问题是:" + question + "\n答案是:" + answer);
        final StudyResult studyResult = new StudyResult();
        studyResult.setState(1);
        studyResult.setQuestion(question);
        studyResult.setAnswer(answer);
        studyResult.setUser(SharedPreferenceUtil.getString(ContentUtil.USERNAME, null, this));
        List<StudyResult> all = studyDao.findAll();
        for (StudyResult study : all) {
            if (study.getQuestion().equals(question)) {
                studyDao.delete(study);
            }
        }
        studyDao.insert(studyResult);
        studyResult.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    LogUtil.e("添加成功");
                } else {
                    LogUtil.e("添加失败:" + e.getMessage());
                    studyResult.setState(0);
                    studyDao.update(studyResult);
                }
            }
        });
        StudyActivity.studyList.add(studyResult);
        AddStudyActivity.this.finish();
    }

    public void showToast(String msg) {
        MyToast.makeText(this, msg, MyToast.LENGTH_SHORT).show();
    }
}
