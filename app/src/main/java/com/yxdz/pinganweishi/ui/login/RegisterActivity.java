package com.yxdz.pinganweishi.ui.login;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.leaf.library.StatusBarUtil;
import com.yxdz.common.utils.NoDoubleClick;
import com.yxdz.common.utils.RegexUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.bean.YxdzInfo;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.activity.policyActivity;
import com.yxdz.pinganweishi.activity.protocolActivity;
import com.yxdz.pinganweishi.api.LoginApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.DefaultBean;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 注册
 */
public class RegisterActivity extends BaseHeadActivity implements View.OnClickListener {


    private static final int CHECK_SUCCESSFUL_FORGET = 403;
    private static final int CHECK_SUCCESSFUL_SEND = 404;
    private static final int CHECK_SUCCESSFUL_ERROR = 406;
    private EditText etName;
    private EditText etPhone;
    private EditText etCode;
    private EditText etPwd;
    private EditText etPwdAgain;

    private ImageView ivAgree;//同意协议
    private Button btnRegister;

    private boolean isAgree;//是否同意协议


    private String phone;
    private TextView btnGetCode;
    private MessageHandler messageHandler;
    private TitleBarView toolbar;


    @Override
    public int getLayoutResource() {
        return R.layout.activity_register;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        toolbar = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this, R.color.primarystart), ContextCompat.getColor(this, R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        toolbar.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, toolbar);
        initView();
    }

    @Override
    protected void setTitlebarView() {
    }

    private void initView() {
        etName = findViewById(R.id.register_name);
        etPhone = findViewById(R.id.register_phone);
        etCode = findViewById(R.id.register_check_code);
        etPwd = findViewById(R.id.register_pwd);
        etPwdAgain = findViewById(R.id.register_pwd_again);
        ivAgree = findViewById(R.id.register_agree);
        btnRegister = findViewById(R.id.register_btn);
        btnGetCode = findViewById(R.id.forget_get_check_code);
        btnRegister.setOnClickListener(this);
        btnGetCode.setOnClickListener(this);
        ivAgree.setOnClickListener(this);

        findViewById(R.id.user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(protocolActivity.class);
            }
        });
        findViewById(R.id.privacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(policyActivity.class);
            }
        });
        messageHandler = new MessageHandler();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_get_check_code:
                if (!RegexUtils.isMobilePhoneNumber(etPhone.getText().toString().trim())) {
                    ToastUtils.showShort(RegisterActivity.this, "请输入正确的手机号码");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("phone", etPhone.getText().toString().trim());
                map.put("type", 1);
                RetrofitHelper.subscriber(RetrofitHelper.Https(LoginApi.class).getMessageCode(map), new RxSubscriber<YxdzInfo>(RegisterActivity.this) {
                    @Override
                    protected void onSuccess(YxdzInfo yxdzInfo) {
                        Toast.makeText(RegisterActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                        TimeCount timeCount = new TimeCount(60000, 1000);
                        timeCount.start();
                    }
                });

                break;
            case R.id.register_agree://同意协议
                if (isAgree) {
                    //不同意
                    ivAgree.setImageResource(R.mipmap.checkbox_bu);
                } else {
                    ivAgree.setImageResource(R.mipmap.checkbox2_bu);
                }
                isAgree = !isAgree;
                break;

            case R.id.register_btn://注册
                if (TextUtils.isEmpty(etName.getText().toString().trim())) {
                    ToastUtils.showShort(RegisterActivity.this, "请输入昵称");
                    return;
                }
                if (!RegexUtils.isMobilePhoneNumber(etPhone.getText().toString().trim())) {
                    ToastUtils.showShort(RegisterActivity.this, "请输入正确的手机号码");
                    return;
                }
                if (etCode.getText().toString().length() != 6) {
                    ToastUtils.showShort(RegisterActivity.this, "请输入完整的验证码");
                    return;
                }
                //判断密码
                if (!checkPwd(etPwd.getText().toString(), etPwdAgain.getText().toString())) {
                    return;
                }
                if (!isAgree) {
                    ToastUtils.showShort(RegisterActivity.this, "请勾选同意协议");
                    return;
                }
                registerAccount();
                break;
        }
    }

    /**
     * 注册帐号——请求数据
     */
    private void registerAccount() {
        if (NoDoubleClick.isFastDoubleClick(800)) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", etName.getText().toString().trim());
        map.put("mobile", etPhone.getText().toString().trim());
        map.put("password", etPwdAgain.getText().toString().trim());
        map.put("mobileCode", etCode.getText().toString().trim());
        RetrofitHelper.subscriber(RetrofitHelper.Https(LoginApi.class).getRegister(map), new RxSubscriber<DefaultBean>(RegisterActivity.this) {
            @Override
            protected void onSuccess(DefaultBean yxdzInfo) {
                //注册成功
                if (yxdzInfo.getCode() == 0) {
                    ToastUtils.showShort(RegisterActivity.this, yxdzInfo.getData());
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, yxdzInfo.getData(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 验证密码
     * password密码
     * passwordAgain确认密码
     */
    private boolean checkPwd(String password, String passwordAgain) {
        if (!password.equals("") && !passwordAgain.equals("")) {

            Pattern p = Pattern
                    .compile(".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]|.*[0-9]|.*[a-zA-Z]");
            Matcher m = p.matcher(password);
            Matcher m2 = p.matcher(passwordAgain);
            if (!m.matches()) {
                ToastUtils.showShort(RegisterActivity.this, "密码存在非法字符！");
                return false;
            }
            if (!m2.matches()) {
                ToastUtils.showShort(RegisterActivity.this, "确认密码存在非法字符！");
                return false;
            }
            if (password.length() < 6) {
                ToastUtils.showShort(RegisterActivity.this, "密码不能小于6位字符");
                return false;
            } else {
                if (passwordAgain.length() < 6) {
                    ToastUtils.showShort(RegisterActivity.this, "确认密码不能小于6位字符");
                    return false;
                } else {
//                    Pattern isNum = Pattern.compile("[0-9]*");// 判断是否纯数字
//                    Matcher isNum2 = isNum.matcher(password);
//                    if (isNum2.matches()) {
//                        ToastUtils.showShort(RegisterActivity.this, "密码不能纯数字！");
//                        return false;
//                    }
//                    Pattern isChar = Pattern.compile("[a-zA-Z]*");// 判断是否纯字母
//                    Matcher isChar2 = isChar.matcher(password);
//                    if (isChar2.matches()) {
//                        ToastUtils.showShort(RegisterActivity.this, "密码不能纯字母！");
//                        return false;
//                    }
                    if (password.equals(passwordAgain)) {
                        return true;
                    }
                }
            }
        } else {
            if (password.equals("")) {
                ToastUtils.showShort(RegisterActivity.this, "密码不能为空！");
                return false;
            }
            if (passwordAgain.equals("")) {
                ToastUtils.showShort(RegisterActivity.this, "确认密码不能为空！");
                return false;
            }
        }
        ToastUtils.showShort(RegisterActivity.this, "两次输入的密码不相同,请重新输入密码");
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            btnGetCode.setText(msg.what);
            if (msg.what == 60) {
                btnGetCode.setClickable(true);
                btnGetCode.setText("获取验证码");
            }
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {
            // 计时完毕时触发
            btnGetCode.setText("重新获取");
            btnGetCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // 计时过程显示
            btnGetCode.setClickable(false);
            btnGetCode.setText(millisUntilFinished / 1000 + "秒后重试");
            btnGetCode.setTextColor(0xff666666);
        }
    }
}