package com.alfredjin.iamrobot.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alfredjin.iamrobot.R;
import com.alfredjin.iamrobot.adapter.StudyAdapter;
import com.alfredjin.iamrobot.bean.StudyResult;
import com.alfredjin.iamrobot.dao.StudyDao;
import com.alfredjin.iamrobot.databinding.ActivityStudyBinding;
import com.alfredjin.iamrobot.utils.ContentUtil;
import com.alfredjin.iamrobot.utils.LogUtil;
import com.alfredjin.iamrobot.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class StudyActivity extends AppCompatActivity {

    private ActivityStudyBinding binding;
    private Context mContext;
    private View footView;
    private Button addBtn;
    private StudyDao studyDao;
    private StudyAdapter studyAdapter;
    private List<StudyResult> all;
    public static List<StudyResult> studyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study);
        mContext = this;
        if (studyList != null) {
            for (int i = 0; i < studyList.size(); i++) {
                LogUtil.e("所加内容为:" + studyList.get(i).getQuestion());
            }
        }
        initView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (studyAdapter != null) {
            studyAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化布局
     */
    private void initView() {
        footView = View.inflate(this, R.layout.footview_study, null);
        binding.studyLv.addFooterView(footView);
        addBtn = (Button) footView.findViewById(R.id.foot_btn_stu);
        studyDao = new StudyDao(mContext);
        if (all != null) {
            all.clear();
        }
        all = studyDao.findAll();
        LogUtil.e("all.size == " + all.size());
        studyList = new ArrayList<>();
        String user = SharedPreferenceUtil.getString(ContentUtil.USERNAME, null, mContext);
        if (studyList != null) {
            studyList.clear();
        }
        if (user != null) {
            for (StudyResult studyResult : all) {
                if (studyResult.getUser().equals(user)) {
                    studyList.add(studyResult);
                }
            }
        }
        if (studyAdapter == null) {
            studyAdapter = new StudyAdapter(mContext, studyList);
        }
        binding.studyLv.setAdapter(studyAdapter);
        initListener();
    }

    /**
     * 事件处理
     */
    private void initListener() {
        binding.studyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudyActivity.this.finish();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e("添加训练");
                startActivity(new Intent(StudyActivity.this, AddStudyActivity.class));
            }
        });
    }
}
