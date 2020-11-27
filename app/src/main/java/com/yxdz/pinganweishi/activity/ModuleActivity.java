package com.yxdz.pinganweishi.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.leaf.library.StatusBarUtil;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.FirePlaceBean;

import java.io.Serializable;

public class ModuleActivity extends BaseHeadActivity {


    private TitleBarView toolbar;
    private Serializable listPlaceBean;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_module;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        listPlaceBean = getIntent().getSerializableExtra("listPlaceBean");
        toolbar = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this,R.color.primarystart), ContextCompat.getColor(this,R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        toolbar.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, toolbar);
        findViewById(R.id.fire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModuleActivity.this, FireDeviceActivity.class);
                intent.putExtra("listPlaceBean", (Serializable) listPlaceBean);
                startActivityForResult(intent, 1);
            }
        });
        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModuleActivity.this, CameraListActivity.class);
                intent.putExtra("placeId", ((FirePlaceBean.ListPlaceBean) listPlaceBean).getId());
                intent.putExtra("createType", ((FirePlaceBean.ListPlaceBean) listPlaceBean).getCreateType());
                startActivityForResult(intent, 1);
            }
        });
        findViewById(R.id.lock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModuleActivity.this, DoorLockActivity.class);
                intent.putExtra("placeId", ((FirePlaceBean.ListPlaceBean) listPlaceBean).getId());
                intent.putExtra("createType", ((FirePlaceBean.ListPlaceBean) listPlaceBean).getCreateType());
                startActivityForResult(intent, 1);
            }
        });
        findViewById(R.id.magnet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModuleActivity.this, MagnetismActivity.class);
                intent.putExtra("placeId", ((FirePlaceBean.ListPlaceBean) listPlaceBean).getId());
                intent.putExtra("createType", ((FirePlaceBean.ListPlaceBean) listPlaceBean).getCreateType());
                startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    protected void setTitlebarView() {

    }
}
