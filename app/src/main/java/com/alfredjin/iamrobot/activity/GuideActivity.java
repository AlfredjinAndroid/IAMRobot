package com.alfredjin.iamrobot.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alfredjin.iamrobot.R;
import com.alfredjin.iamrobot.adapter.GuideAdapter;
import com.alfredjin.iamrobot.databinding.ActivityGuideBinding;
import com.alfredjin.iamrobot.utils.ContentUtil;
import com.alfredjin.iamrobot.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    private ActivityGuideBinding binding;
    private ViewPager viewPager;
    private List<View> viewList;
    private int[] imageId = {
            R.drawable.guide1,
            R.drawable.guide2,
            R.drawable.guide3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout
                .activity_guide);
        initView();
    }

    private void initView() {
        viewPager = binding.viewpagerGuide;
        binding.btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                GuideActivity.this.finish();
                SharedPreferenceUtil.putBoolean(ContentUtil.TO_GUIDE, false, GuideActivity.this);
            }
        });

        viewList = new ArrayList<>();
        for (Integer i : imageId) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(i);
            viewList.add(imageView);
        }
        initAdapter();
    }

    private void initAdapter() {
        viewPager.setAdapter(new GuideAdapter(viewList));
    }
}
