package com.yxdz.pinganweishi.ui.login;



import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.leaf.library.StatusBarUtil;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.pinganweishi.R;


import com.yxdz.pinganweishi.base.BaseHeadActivity;

public class ForgetStepThreeActivity extends BaseHeadActivity {


    private TitleBarView toolbar;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_forget_step_three;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        toolbar = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this,R.color.primarystart), ContextCompat.getColor(this,R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR,colors);
        toolbar.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, toolbar);


        findViewById(R.id.next_step).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void setTitlebarView() {

    }
}
