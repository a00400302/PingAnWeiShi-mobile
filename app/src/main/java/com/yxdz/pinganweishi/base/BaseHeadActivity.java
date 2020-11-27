package com.yxdz.pinganweishi.base;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.leaf.library.StatusBarUtil;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yxdz.common.AppManager;
import com.yxdz.common.load.RetryNetWorkImpl;
import com.yxdz.pinganweishi.R;

import com.yxdz.pinganweishi.view.NetErrorView;


/**
 * 自带头部基类
 */
public abstract class BaseHeadActivity extends AppCompatActivity {

    protected AppManager appManager = AppManager.getAppManager();
    public NetErrorView netErrorView;
    private ViewGroup mViewGroup;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appManager.addActivity(this);
        setContentView(R.layout.activity_common_layout);




        mViewGroup = (ViewGroup) LayoutInflater.from(this).inflate(getLayoutResource(), null);
        FrameLayout frameLayout = findViewById(R.id.common_base_framelayout);
        frameLayout.addView(mViewGroup);

        //无网络处理
        netErrorView = new NetErrorView.Builder(this, mViewGroup).setRetryNetWorkImpl(new RetryNetWorkImpl() {
            @Override
            public void retryNetWork() {
                setRetryNetWork();
            }
        }).create();

        setContentView();
        setTitlebarView();


    }






    public void setRetryNetWork(){

    }

    public abstract int getLayoutResource();

    protected abstract void setContentView();

    protected abstract void setTitlebarView();


    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseHeadActivity.this, clz));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        appManager.finishActivity(this);
    }
}
