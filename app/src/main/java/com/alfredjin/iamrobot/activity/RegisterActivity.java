package com.alfredjin.iamrobot.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.alfredjin.iamrobot.R;
import com.alfredjin.iamrobot.app.MainActivity;
import com.alfredjin.iamrobot.bean.User;
import com.alfredjin.iamrobot.dao.UserDao;
import com.alfredjin.iamrobot.databinding.ActivityRegisterBinding;
import com.alfredjin.iamrobot.utils.ContentUtil;
import com.alfredjin.iamrobot.utils.LogUtil;
import com.alfredjin.iamrobot.utils.NetworkUtils;
import com.alfredjin.iamrobot.utils.SharedPreferenceUtil;
import com.alfredjin.iamrobot.view.MyToast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private Button validate;
    private Button register;
    private EditText robot;
    private EditText password;
    private EditText password2;
    private EditText user;
    private RadioButton boy;
    private RadioButton girl;
    private boolean canDel = false;
    private Context mContext = this;

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_register);
        initView();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        validate = binding.registerValidate;
        register = binding.registerRe;
        robot = binding.registerRobot;
        user = binding.registerUser;
        password = binding.registerPassword;
        password2 = binding.registerPassword2;
        boy = binding.registerBoy;
        girl = binding.registerGirl;
        userDao = new UserDao(this);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        String us = intent.getStringExtra(LoginActivity.USER);
        String pas = intent.getStringExtra(LoginActivity.PASSWORD);
        user.setText(us);
        password.setText(pas);
        initListener();
    }

    /**
     * 初始化事件处理
     */
    private void initListener() {
        //验证按钮处理
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validate = isValidate();
                if (validate) {//可注册状态
                    new BmobQuery<User>().findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            boolean canLogin = true;
                            for (int i = 0; i < list.size(); i++) {
                                User userLogin = list.get(i);
                                if (userLogin.getUserName().equals(user.getText().toString().trim())
                                        && userLogin.getPassword().equals(user.getText().toString().trim())) {
                                    canLogin = false;
                                }
                            }
                            if (canLogin) {
                                canDel = true;
                                register.setEnabled(true);
                                addDateToBmob();
                            } else {
                                showToast("已存在的用户名，请重新输入");
                            }
                        }
                    });
                }
            }
        });

        //注册并登陆
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String robot_str = robot.getText().toString().trim();
                String user_str = user.getText().toString().trim();
                String password_str = password.getText().toString().trim();
                String sex_str = boy.isChecked() ? boy.getText().toString().trim() :
                        girl.isChecked() ? girl.getText().toString().trim() : "Null";
                LogUtil.e("机器人昵称:" + robot_str + "\n用户名:" + user_str + "\n密码:" + password_str
                        + "\n性别" + sex_str);
                showToast("注册成功");
                //注册成功并登陆
                SharedPreferenceUtil.putString(ContentUtil.USERNAME, user_str, mContext);
                SharedPreferenceUtil.putString(ContentUtil.PASSWORD, password_str, mContext);
                SharedPreferenceUtil.putString(ContentUtil.ROBOT, robot_str, mContext);
                SharedPreferenceUtil.putBoolean(ContentUtil.ROBOT, boy.isChecked(), mContext);
                toLogin();
            }
        });

        //机器人输入框状态监听
        robot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //用户输入框状态监听
        user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //密码输入框状态监听
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //确认密码输入框状态监听
        password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textChanged();
            }
        });
        girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textChanged();
            }
        });
    }

    /**
     * 登录
     */
    private void toLogin() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        RegisterActivity.this.finish();
    }

    /**
     * 向后台云数据库插入数据
     */
    private void addDateToBmob() {
        String robot_str = robot.getText().toString().trim();
        String user_str = user.getText().toString().trim();
        String password_str = password.getText().toString().trim();
        User.Sex sex_str = boy.isChecked() ? User.Sex.BOY :
                girl.isChecked() ? User.Sex.GIRL : null;
        User user = new User();
        user.setUserName(user_str);
        user.setPassword(password_str);
        user.setRobotName(robot_str);
        user.setSex(sex_str);
        userDao.insert(user);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    SharedPreferenceUtil.putString("objectId", s, mContext);
                    LogUtil.e("插入数据成功");
                } else {
                    LogUtil.e("插入数据失败" + e.getMessage());
                }
            }
        });
    }

    /**
     * 当输入框发生改变时，需要解除注册的事件
     * 以及清除写入数据库的缓存状态
     */
    private void textChanged() {
        if (register.isEnabled())
            register.setEnabled(false);
        if (canDel) {
            new BmobQuery<User>().findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    for (int i = 0; i < list.size(); i++) {
                        User userTemp = list.get(i);
                        if (userTemp.getUserName().equals(user.getText().toString().trim())
                                && userTemp.getPassword().equals(user.getText().toString().trim())) {
                            userTemp.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        LogUtil.e("删除成功");
                                        canDel = false;
                                    } else {
                                        LogUtil.e("删除失败");
                                    }
                                }
                            });

                        }
                    }
                }
            });
        }
    }

    /**
     * 验证
     *
     * @return
     */
    private boolean isValidate() {
        boolean isValidate = false;
        if (!NetworkUtils.getInstance().isNetworkAvailable(RegisterActivity.this)) {
            showToast("网络不给力，请查看网络连接!!!");
            LogUtil.e("网络不可用");
            return false;
        }
        String robot_str = robot.getText().toString().trim();
        String user_str = user.getText().toString().trim();
        String password2_str = password2.getText().toString().trim();
        String password_str = password.getText().toString().trim();
        if (TextUtils.isEmpty(robot_str)) {
            showToast("请为你的机器人取个名字吧");
            return isValidate;
        }
        if (TextUtils.isEmpty(user_str)) {
            showToast("用户名不能为空");
            return isValidate;
        }
        if (TextUtils.isEmpty(password_str)) {
            showToast("密码不能为空");
            return isValidate;
        }
        if (TextUtils.isEmpty(password2_str)) {
            showToast("请填写确认密码");
            return isValidate;
        }
        if (!password_str.equals(password2_str)) {
            showToast("密码不一致");
            return false;
        }
        isValidate = true;
        return isValidate;
    }

    private void showToast(String msg) {
        MyToast.makeText(this, msg, MyToast.LENGTH_SHORT).show();
    }
}
