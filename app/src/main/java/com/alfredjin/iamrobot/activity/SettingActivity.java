package com.alfredjin.iamrobot.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alfredjin.iamrobot.R;
import com.alfredjin.iamrobot.app.MainActivity;
import com.alfredjin.iamrobot.databinding.ActivitySettingBinding;
import com.alfredjin.iamrobot.utils.ContentUtil;
import com.alfredjin.iamrobot.utils.LogUtil;
import com.alfredjin.iamrobot.utils.SharedPreferenceUtil;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        mContext = this;
        initView();
        initListener();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        int user_icon = SharedPreferenceUtil.getInt("user_icon", R.drawable.icon36, mContext);
        switch (user_icon) {
            case R.drawable.baby:
                binding.rbIconBaby.setChecked(true);
                break;
            case R.drawable.police:
                binding.rbIconPolice.setChecked(true);
                break;
            case R.drawable.icon36:
                binding.rbIconPanda.setChecked(true);
                break;
        }

        int robot_icon = SharedPreferenceUtil.getInt("robot_icon", R.drawable.roboticon, mContext);
        switch (robot_icon) {
            case R.drawable.robotcat:
                binding.rbIconRobotcat.setChecked(true);
                break;
            case R.drawable.wlle:
                binding.rbIconWlle.setChecked(true);
                break;
            case R.drawable.roboticon:
                binding.rbIconRoboticon.setChecked(true);
                break;
        }

        String speaker = SharedPreferenceUtil.getString(ContentUtil.SPEAKER, "0", mContext);
        switch (speaker){
            case "0":
                binding.voice0.setChecked(true);
                break;
            case "1":
                binding.voice1.setChecked(true);
                break;
            case "4":
                binding.voice2.setChecked(true);
                break;
            case "3":
                binding.voice3.setChecked(true);
                break;
        }
    }

    /**
     * 设置
     */
    private void initListener() {

        binding.settingSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.rbIconBaby.isChecked()) {
                    SharedPreferenceUtil.putInt("user_icon", R.drawable.baby, mContext);
                }
                if (binding.rbIconPanda.isChecked()) {
                    SharedPreferenceUtil.putInt("user_icon", R.drawable.icon36, mContext);
                }
                if (binding.rbIconPolice.isChecked()) {
                    SharedPreferenceUtil.putInt("user_icon", R.drawable.police, mContext);
                }
                if (binding.rbIconRoboticon.isChecked()) {
                    SharedPreferenceUtil.putInt("robot_icon", R.drawable.roboticon, mContext);
                }
                if (binding.rbIconRobotcat.isChecked()) {
                    SharedPreferenceUtil.putInt("robot_icon", R.drawable.robotcat, mContext);
                }
                if (binding.rbIconWlle.isChecked()) {
                    SharedPreferenceUtil.putInt("robot_icon", R.drawable.wlle, mContext);
                }
                if (binding.voice0.isChecked()){
                    SharedPreferenceUtil.putString(ContentUtil.SPEAKER,"0",mContext);
                }
                if (binding.voice1.isChecked()){
                    SharedPreferenceUtil.putString(ContentUtil.SPEAKER,"1",mContext);
                }
                if (binding.voice2.isChecked()){
                    SharedPreferenceUtil.putString(ContentUtil.SPEAKER,"4",mContext);
                }
                if (binding.voice3.isChecked()){
                    SharedPreferenceUtil.putString(ContentUtil.SPEAKER,"3",mContext);
                }
                
                SettingActivity.this.finish();
            }
        });
    }

}
