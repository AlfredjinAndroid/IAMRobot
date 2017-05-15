package com.alfredjin.iamrobot.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alfredjin.iamrobot.R;
import com.alfredjin.iamrobot.bean.User;
import com.alfredjin.iamrobot.dao.UserDao;
import com.alfredjin.iamrobot.databinding.ActivityUserBinding;
import com.alfredjin.iamrobot.utils.ContentUtil;
import com.alfredjin.iamrobot.utils.LogUtil;
import com.alfredjin.iamrobot.utils.SharedPreferenceUtil;
import com.alfredjin.iamrobot.view.MyToast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;
    private Context mContext;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        mContext = this;
        userDao = new UserDao(mContext);
        initData();
        initListener();
    }

    /**
     * 点击事件
     */
    private void initListener() {
        binding.activityUserCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserActivity.this.finish();
            }
        });

        binding.activityUserSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    /**
     * 保存数据处理
     */
    private void save() {
        if (TextUtils.isEmpty(binding.activityUserRobot.getText().toString().trim())) {
            showToast("机器人昵称未修改");
            return;
        }
        if (TextUtils.isEmpty(binding.activityUserPassword.getText().toString().trim())) {
            showToast("密码未修改");
            return;
        }
        if (TextUtils.isEmpty(binding.activityUserPassword2.getText().toString().trim())) {
            showToast("请确认密码");
            return;
        }
        if (!binding.activityUserPassword.getText().toString().trim().equals(
                binding.activityUserPassword2.getText().toString().trim())) {
            showToast("密码不一致");
        }
        handUser();
    }

    /**
     * 处理更新数据
     */
    private void handUser() {
        String user = binding.activityUserUser.getText().toString().trim();
        String robot = binding.activityUserRobot.getText().toString().trim();
        String password = binding.activityUserPassword.getText().toString().trim();
        User u = new User();
        u.setUserName(user);
        u.setRobotName(robot);
        u.setPassword(password);
        u.setSex(binding.activityUserBoy.isChecked() ? User.Sex.BOY : User.Sex.GIRL);
        LogUtil.e("User插入数据库");
        userDao.update(u);
        String objectId = SharedPreferenceUtil.getString("objectId", null, mContext);
        if (objectId != null) {
            u.setObjectId(objectId);
        }
        u.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    LogUtil.e("更新成功");
                } else {
                    LogUtil.e("捕获的异常 ： " + e.getMessage());
                }
            }
        });
        UserActivity.this.finish();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        String userName = SharedPreferenceUtil.getString(ContentUtil.USERNAME, null, mContext);
        User user = userDao.findByName(userName);
        if (user.getRobotName() != null) {
            binding.activityUserRobot.setHint(user.getRobotName());
        }

        if (userName != null) {
            binding.activityUserUser.setText(userName);
        }

        if (user.getPassword() != null) {
            binding.activityUserPassword.setHint(user.getPassword());
        }

        if (user.getSex() == User.Sex.BOY) {
            binding.activityUserBoy.setChecked(true);
        }
        if (user.getSex() == User.Sex.GIRL) {
            binding.activityUserGirl.setChecked(true);
        }

    }

    public void showToast(String msg) {
        MyToast.makeText(mContext, msg, MyToast.LENGTH_SHORT).show();
    }
}
