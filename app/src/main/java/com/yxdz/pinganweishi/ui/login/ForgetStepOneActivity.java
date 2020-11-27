package com.yxdz.pinganweishi.ui.login;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.leaf.library.StatusBarUtil;
import com.yxdz.common.utils.RegexUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.bean.YxdzInfo;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;


import com.yxdz.pinganweishi.api.LoginApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.DefaultBean;

import java.util.HashMap;
import java.util.Map;

public class ForgetStepOneActivity extends BaseHeadActivity implements View.OnClickListener {

    private EditText etPhone;
    private EditText etCode;
    private TextView tvGetCode;
    private TitleBarView toolbar;
    private String phone;
    private TimeCount time;
    private String msgCode;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_forget_step_one;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        time = new TimeCount(60000, 1000);
        etPhone = findViewById(R.id.forget_phone);
        etPhone.setOnClickListener(this);
        etCode = findViewById(R.id.forget_check_code);
        etCode.setOnClickListener(this);
        tvGetCode = findViewById(R.id.forget_get_check_code);
        tvGetCode.setOnClickListener(this);
        toolbar = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this,R.color.primarystart), ContextCompat.getColor(this,R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        toolbar.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, toolbar);

        findViewById(R.id.next_step).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ForgetStepOneActivity.this, ForgetStepTwoActivity.class);
                if (!RegexUtils.isMobilePhoneNumber(etPhone.getText().toString().trim())) {
                    ToastUtils.showShort(ForgetStepOneActivity.this, "请输入正确的手机号码");
                    return;
                }
                if (etCode.getText().toString().length() !=6) {
                    ToastUtils.showShort(ForgetStepOneActivity.this, "请输入完整的验证码");
                    return;
                }
                if (TextUtils.isEmpty(msgCode)){
                    ToastUtils.showShort(ForgetStepOneActivity.this, "请先获取验证码");
                    return;
                }
                if (!etCode.getText().toString().equals(msgCode)){
                    ToastUtils.showShort(ForgetStepOneActivity.this, "验证码不正确");
                    return;
                }
                intent.putExtra("phone", phone);
                intent.putExtra("msg", msgCode);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    protected void setTitlebarView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_get_check_code://获取验证码
                if (!RegexUtils.isMobilePhoneNumber(etPhone.getText().toString().trim())) {
                    ToastUtils.showShort(ForgetStepOneActivity.this, "请输入正确的手机号码");
                    return;
                }
                time.start();
                phone = etPhone.getText().toString().trim();
                Map<String, Object> map = new HashMap<>();
                map.put("phone", etPhone.getText().toString().trim());
                map.put("type", 2);
                RetrofitHelper.subscriber(RetrofitHelper.Https(LoginApi.class).getMessageCode(map), new RxSubscriber<DefaultBean>(ForgetStepOneActivity.this) {
                    @Override
                    protected void onSuccess(DefaultBean defaultBean) {
                        Toast.makeText(ForgetStepOneActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                        msgCode = defaultBean.getData();
                        Log.d("ForgetStepOneActivity", msgCode);
                    }
                });
        }
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {
            // 计时完毕时触发
            tvGetCode.setText("重新获取");
            tvGetCode.setClickable(true);
            tvGetCode.setTextColor(getResources().getColor(R.color.primary));
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // 计时过程显示
            tvGetCode.setClickable(false);
            tvGetCode.setText(millisUntilFinished / 1000 + "秒后重试");
            tvGetCode.setTextColor(0xff666666);
        }
    }
}
