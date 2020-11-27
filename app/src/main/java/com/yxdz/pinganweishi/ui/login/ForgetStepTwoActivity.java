package com.yxdz.pinganweishi.ui.login;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.leaf.library.StatusBarUtil;
import com.yxdz.common.utils.LogUtils;
import com.yxdz.common.utils.RegexUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.bean.YxdzInfo;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.yxdz.pinganweishi.api.LoginApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.SmsResultBean;
import com.yxdz.pinganweishi.utils.ActValue;

/**
 * 忘记密码——不记得原密码
 */
public class ForgetStepTwoActivity extends BaseHeadActivity implements View.OnClickListener {

    private EditText etPwd;
    private EditText etPwdAgain;

    private String phone;
    private String code;

    private TitleBarView toolbar;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_forget);
//    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_forget;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        code = intent.getStringExtra("msg");
        toolbar = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this,R.color.primarystart), ContextCompat.getColor(this,R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        toolbar.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, toolbar);

        etPwd = findViewById(R.id.forget_pwd);
        etPwdAgain = findViewById(R.id.forget_pwd_again);
        findViewById(R.id.next_step).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPwd(etPwd.getText().toString(), etPwdAgain.getText().toString())) {
                    return;
                }
                changePassword();

            }
        });
    }

    private void changePassword() {
        Map<String, Object> map = new HashMap();
        map.put(ActValue.Account, phone);
        map.put(ActValue.Pwd, etPwdAgain.getText().toString().trim());
        map.put("code", code);
        RetrofitHelper.subscriber(RetrofitHelper.Https(LoginApi.class).forgetPwd(map), new RxSubscriber<YxdzInfo>(this, "正在修改...") {
            @Override
            protected void onSuccess(YxdzInfo yxdzInfo) {
                ToastUtils.showShort(ForgetStepTwoActivity.this, "密码重置成功");
                finish();
                startActivity(ForgetStepThreeActivity.class);
            }
        });

    }

    @Override
    protected void setTitlebarView() {
//        titleBarView.setTitleBarText("修改密码");
    }

    @Override
    public void onClick(View v) {

    }


    private boolean checkPwd(String password, String passwordAgain) {
        if (!password.equals("") && !passwordAgain.equals("")) {

            Pattern p = Pattern
                    .compile(".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]|.*[0-9]|.*[a-zA-Z]");
            Matcher m = p.matcher(password);
            Matcher m2 = p.matcher(passwordAgain);
            if (!m.matches()) {
                ToastUtils.showShort(ForgetStepTwoActivity.this, "密码存在非法字符！");
                return false;
            }
            if (!m2.matches()) {
                ToastUtils.showShort(ForgetStepTwoActivity.this, "确认密码存在非法字符！");
                return false;
            }
            if (password.length() < 6) {
                ToastUtils.showShort(ForgetStepTwoActivity.this, "密码不能小于6位字符");
                return false;
            } else {
                if (passwordAgain.length() < 6) {
                    ToastUtils.showShort(ForgetStepTwoActivity.this, "确认密码不能小于6位字符");
                    return false;
                } else {
//                    Pattern isNum = Pattern.compile("[0-9]*");// 判断是否纯数字
//                    Matcher isNum2 = isNum.matcher(password);
//                    if (isNum2.matches()) {
//                        ToastUtils.showShort(ForgetStepTwoActivity.this, "密码不能纯数字！");
//                        return false;
//                    }
//                    Pattern isChar = Pattern.compile("[a-zA-Z]*");// 判断是否纯字母
//                    Matcher isChar2 = isChar.matcher(password);
//                    if (isChar2.matches()) {
//                        ToastUtils.showShort(ForgetStepTwoActivity.this, "密码不能纯字母！");
//                        return false;
//                    }
                    if (password.equals(passwordAgain)) {

                        return true;
                    }
                }
            }
        } else {
            if (password.equals("")) {
                ToastUtils.showShort(ForgetStepTwoActivity.this, "密码不能为空！");
                return false;
            }
            if (passwordAgain.equals("")) {
                ToastUtils.showShort(ForgetStepTwoActivity.this, "确认密码不能为空！");
                return false;
            }
        }
        ToastUtils.showShort(ForgetStepTwoActivity.this, "两次输入的密码不相同,请重新输入密码");
        return false;
    }


}
