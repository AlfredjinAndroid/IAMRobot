package com.alfredjin.iamrobot.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alfredjin.iamrobot.R;
import com.alfredjin.iamrobot.app.MainActivity;
import com.alfredjin.iamrobot.bean.User;
import com.alfredjin.iamrobot.databinding.ActivityLoginBinding;
import com.alfredjin.iamrobot.utils.ContentUtil;
import com.alfredjin.iamrobot.utils.LogUtil;
import com.alfredjin.iamrobot.utils.NetworkUtils;
import com.alfredjin.iamrobot.utils.SharedPreferenceUtil;
import com.alfredjin.iamrobot.view.MyToast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Create By AlfredJin on 2017/4/11
 * 登录界面
 */

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    public static final String USER = "USER";
    public static final String PASSWORD = "PASSWORD";

    private Button login;
    private Button register;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        login = binding.loginBtn;
        register = binding.registerBtn;
        username = binding.username;
        password = binding.password;
        initListener();
    }


    /**
     * @param type 提交事件验证
     */
    private void submit(int type) {
        String user = username.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            MyToast.makeText(this, "用户名不能为空", MyToast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            MyToast.makeText(this, "密码不能为空", MyToast.LENGTH_SHORT).show();
            return;
        }

        validate(type);
    }

    /**
     * 登录或注册用户的验证
     *
     * @param type
     */
    private void validate(int type) {
        switch (type) {
            case 0://登录操作
                validateLogin();
                break;
            case 1://注册操作
                toRegister();
                break;
        }
    }

    private void validateLogin() {
        findUser();
    }

    /**
     * 注册操作
     */
    private void toRegister() {
        if (NetworkUtils.getInstance().isNetworkAvailable(this)
                && NetworkUtils.getInstance().isNetworkAvailable(this)){
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra(USER, username.getText().toString().trim());
            intent.putExtra(PASSWORD, username.getText().toString().trim());
            startActivity(intent);
            LoginActivity.this.finish();
        }else{
            MyToast.makeText(this,"网络不给力",MyToast.LENGTH_SHORT).show();
        }

    }

    /**
     * 可以登录后的操作
     */
    private void loginSuccess(String user, String pwd) {
        SharedPreferenceUtil.putString(ContentUtil.USERNAME, user, this);
        SharedPreferenceUtil.putString(ContentUtil.PASSWORD, pwd, this);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        LoginActivity.this.finish();
        SharedPreferenceUtil.putBoolean(ContentUtil.TO_LOGIN, false, this);
    }

    /**
     * 事件监听
     */
    private void initListener() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录
                submit(0);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册
                submit(1);
            }
        });
    }

    private void findUser() {
        new BmobQuery<User>().findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    LogUtil.e("查询到的数据" + list.size());
                    contrastLogin(list);
                } else {
                    LogUtil.e("查询失败:" + e.getMessage());
                }
            }
        });
    }

    private void contrastLogin(List<User> list) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (username.getText().toString().trim().equals(user.getUserName())
                    && password.getText().toString().trim().equals(user.getPassword())) {
                //登录
                SharedPreferenceUtil.putString("objectId", user.getObjectId(), this);
                loginSuccess(user.getUserName(), user.getPassword());
                return;
            }
        }
        showToast("用户号码密码错误");
    }

    public void showToast(String msg) {
        MyToast.makeText(this, msg, MyToast.LENGTH_SHORT).show();
    }
}
