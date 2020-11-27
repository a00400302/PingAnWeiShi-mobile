package com.yxdz.pinganweishi.person;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.leaf.library.StatusBarUtil;
import com.yxdz.common.utils.AppUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.activity.policyActivity;
import com.yxdz.pinganweishi.activity.protocolActivity;
import com.yxdz.pinganweishi.base.BaseHeadActivity;

/**
 * 关于我们
 */
public class AboutActivity extends BaseHeadActivity {

    private TextView tvVerson;
    private TitleBarView titleBarView;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_about);
//        initView();
//    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_about;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        titleBarView = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this,R.color.primarystart), ContextCompat.getColor(this,R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        titleBarView.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, titleBarView);
        initView();
    }

    @Override
    protected void setTitlebarView() {
        titleBarView.setTitleBarText("关于我们");
    }

    private void initView() {
        tvVerson = findViewById(R.id.about_current_version);
        tvVerson.setText("" + AppUtils.getVersionName(this));

        findViewById(R.id.prolic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(policyActivity.class);
            }
        });
        findViewById(R.id.user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(protocolActivity.class);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:076088853782"));
                startActivity(intent);
            } else {
                ToastUtils.showShort(AboutActivity.this, "没有拨打电话权限");
            }

        }
    }

}
