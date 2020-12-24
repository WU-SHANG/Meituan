package com.example.meituan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.meituan.R;
import com.example.meituan.fragment.HomeFragment;
import com.example.meituan.fragment.MessageFragment;
import com.example.meituan.fragment.MineFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    /**
     * 首页按钮
     */
    private RelativeLayout rl_home;
    /**
     * 消息按钮
     */
    private RelativeLayout rl_message;
    /**
     * 我的按钮
     */
    private RelativeLayout rl_mine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl_home = findViewById(R.id.rl_home);
        rl_message = findViewById(R.id.rl_message);
        rl_mine = findViewById(R.id.rl_mine);

        rl_home.setOnClickListener(this);
        rl_message.setOnClickListener(this);
        rl_mine.setOnClickListener(this);

        replaceFragment(new HomeFragment());

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }

    /**
     * 替换碎片
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_home:
                replaceFragment(new HomeFragment());
                break;
            case R.id.rl_message:
                replaceFragment(new MessageFragment());
                break;
            case R.id.rl_mine:
                replaceFragment(new MineFragment());
                break;
            default:
                break;
        }
    }
}