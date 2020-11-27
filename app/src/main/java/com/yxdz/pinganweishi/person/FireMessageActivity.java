package com.yxdz.pinganweishi.person;


import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.leaf.library.StatusBarUtil;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.base.BaseHeadActivity;

/**
 * @ClassName: FireMessageActivity
 * @Desription:
 * @author: Dreamcoding
 * @date: 2018/9/3
 */
public class FireMessageActivity extends BaseHeadActivity {

    private FireMessageFragment fireMessageFragment;
    public static int REQUEST_CODE=111;
    private String placeId;
    private String placeName;
    private String startTime;
    private String endTime;
    private TitleBarView titleBarView;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_fire_message;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        titleBarView = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this,R.color.primarystart), ContextCompat.getColor(this,R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        titleBarView.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, titleBarView);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fireMessageFragment = FireMessageFragment.newInstance();
        fragmentTransaction.add(R.id.flMessage,fireMessageFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void setTitlebarView() {
        titleBarView.setTitleBarText("历史信息");
        titleBarView.getRightImageView().setVisibility(View.VISIBLE);
        titleBarView.getRightImageView().setBackground(getResources().getDrawable(R.mipmap.search_wh));
        titleBarView.setRightImageViewOnClickListener(new TitleBarView.RightImageViewOnClickListener() {
            @Override
            public void onRightOnClick(View v) {
                Intent intent=new Intent(FireMessageActivity.this,SearchMessageActivity.class);
                intent.putExtra("placeId",placeId);
                intent.putExtra("placeName",placeName);
                intent.putExtra("startTime",startTime);
                intent.putExtra("endTime",endTime);
                startActivityForResult(intent,FireMessageActivity.REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FireMessageActivity.REQUEST_CODE && resultCode == SearchMessageActivity.RESULT_CODE) {
            placeId = data.getStringExtra("placeId");
            placeName = data.getStringExtra("placeName");
            startTime = data.getStringExtra("startTime");
            endTime = data.getStringExtra("endTime");
            fireMessageFragment.refreshData(placeId, startTime, endTime);
        }
    }
}
