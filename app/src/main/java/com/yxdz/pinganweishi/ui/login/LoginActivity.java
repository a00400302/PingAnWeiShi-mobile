package com.yxdz.pinganweishi.ui.login;

import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leaf.library.StatusBarUtil;
import com.mob.pushsdk.MobPush;
import com.yxdz.common.utils.LogUtils;
import com.yxdz.common.utils.NoDoubleClick;
import com.yxdz.common.utils.RegexUtils;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.utils.YuXinUrl;
import com.yxdz.pinganweishi.MainActivity;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.activity.SettingActivity;
import com.yxdz.pinganweishi.activity.policyActivity;
import com.yxdz.pinganweishi.activity.protocolActivity;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.LoginBean;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxdz.pinganweishi.utils.LoginUserInfo;


/**
 * 登录
 */
public class LoginActivity extends BaseHeadActivity implements OnClickListener {

    private EditText mLogin_account;
    private EditText mLogin_pwd;
    private ImageView mLogin_remmber_account_iv;

    private boolean isRememberAccount;//是否记住帐号
    private ImageView mLogin_show_password;

    private boolean showPwdFlag = true;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void setContentView() {
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        StatusBarUtil.setDarkMode(this);
        mLogin_account = findViewById(R.id.login_account);
        mLogin_pwd = findViewById(R.id.login_pwd);
        mLogin_remmber_account_iv = findViewById(R.id.login_remmber_account_iv);
        TextView mLogin_remmber_account_tv = findViewById(R.id.login_remmber_account_tv);
        TextView mLogin_forget_pwd = findViewById(R.id.login_forget_pwd);
        Button mLogin_btn = findViewById(R.id.login_btn);
        LinearLayout mLogin_forget_register = findViewById(R.id.login_forget_register);
        mLogin_show_password = findViewById(R.id.login_show_password);

        mLogin_btn.setOnClickListener(this);
        mLogin_remmber_account_iv.setOnClickListener(this);
        mLogin_remmber_account_tv.setOnClickListener(this);
        mLogin_forget_pwd.setOnClickListener(this);
        mLogin_show_password.setOnClickListener(this);
        mLogin_forget_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SPUtils.getInstance().getString("SERVER", null) == null) {
                    Toast.makeText(LoginActivity.this, "请选择服务器", Toast.LENGTH_SHORT).show();
                    startActivity(SettingActivity.class);
                    return;
                } else {
                    YuXinUrl.SERVER = SPUtils.getInstance().getString("SERVER", null);
                    YuXinUrl.HTTP = SPUtils.getInstance().getString("HTTP", null);
                    YuXinUrl.HTTPS = SPUtils.getInstance().getString("HTTPS", null);
                }
                startActivity(new Intent(LoginActivity.this, com.yxdz.pinganweishi.ui.login.RegisterActivity.class));
            }
        });

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

        initData();

        findViewById(R.id.setting).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SettingActivity.class);
            }
        });
    }


    private void initData() {
        if (SPUtils.getInstance().getString("SERVER", null) == null) {
            Toast.makeText(LoginActivity.this, "请选择服务器", Toast.LENGTH_SHORT).show();
            startActivity(SettingActivity.class);
            return;
        } else {
            YuXinUrl.SERVER = SPUtils.getInstance().getString("SERVER", null);
            YuXinUrl.HTTP = SPUtils.getInstance().getString("HTTP", null);
            YuXinUrl.HTTPS = SPUtils.getInstance().getString("HTTPS", null);
        }


        LogUtils.e("帐号：" + SPUtils.getInstance().getString(ConstantUtils.LOGIN_ACCOUNT, "") + ",is=" + SPUtils.getInstance().getBoolean(ConstantUtils.IS_REMEMBER_ACCOUNT));
        if (!TextUtils.isEmpty(SPUtils.getInstance().getString(ConstantUtils.LOGIN_ACCOUNT, ""))
                && (SPUtils.getInstance().getBoolean(ConstantUtils.IS_REMEMBER_ACCOUNT, false))) {
            //账户不为空，自动填充帐号
            mLogin_account.setText(SPUtils.getInstance().getString(ConstantUtils.LOGIN_ACCOUNT, ""));
            mLogin_account.setSelection(mLogin_account.getText().toString().length());
            isRememberAccount = true;
            //记住帐号
            mLogin_remmber_account_iv.setImageResource(R.mipmap.checkbox2_wh);
        } else {
            isRememberAccount = false;
            mLogin_remmber_account_iv.setImageResource(R.mipmap.checkbox_wh);
        }
    }

    @Override
    protected void setTitlebarView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_forget_pwd://忘记密码

                if (SPUtils.getInstance().getString("SERVER", null) == null) {
                    Toast.makeText(LoginActivity.this, "请选择服务器", Toast.LENGTH_SHORT).show();
                    startActivity(SettingActivity.class);
                    return;
                } else {
                    YuXinUrl.SERVER = SPUtils.getInstance().getString("SERVER", null);
                    YuXinUrl.HTTP = SPUtils.getInstance().getString("HTTP", null);
                    YuXinUrl.HTTPS = SPUtils.getInstance().getString("HTTPS", null);
                }
                if (NoDoubleClick.isFastDoubleClick(800)) {
                    return;
                }
                startActivity(new Intent(LoginActivity.this, ForgetStepOneActivity.class));
                break;
            case R.id.login_btn://登录
                if (SPUtils.getInstance().getString("SERVER", null) == null) {
                    Toast.makeText(LoginActivity.this, "请选择服务器", Toast.LENGTH_SHORT).show();
                    startActivity(SettingActivity.class);
                    return;
                } else {
                    YuXinUrl.SERVER = SPUtils.getInstance().getString("SERVER", null);
                    YuXinUrl.HTTP = SPUtils.getInstance().getString("HTTP", null);
                    YuXinUrl.HTTPS = SPUtils.getInstance().getString("HTTPS", null);
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                if (!RegexUtils.isMobilePhoneNumber(mLogin_account.getText().toString().toString())) {
                    ToastUtils.showShort(LoginActivity.this, "请输入正确的手机号码");
                    return;
                }
                if (mLogin_pwd.getText().toString().toString().length() < 6) {
                    ToastUtils.showShort(LoginActivity.this, "您输入的密码小于6位数");
                    return;
                }
                if (NoDoubleClick.isFastDoubleClick(800)) {
                    return;
                }
                loginAccount();
                break;
            case R.id.login_remmber_account_iv://记住帐号
            case R.id.login_remmber_account_tv:
                LogUtils.e("isRememberAccount==" + isRememberAccount);
                if (isRememberAccount) {
                    //取消记住帐号
                    mLogin_remmber_account_iv.setImageResource(R.mipmap.checkbox_wh);
                } else {
                    //记住帐号
                    mLogin_remmber_account_iv.setImageResource(R.mipmap.checkbox2_wh);
                }
                isRememberAccount = !isRememberAccount;
                break;
            case R.id.login_show_password:
                if (showPwdFlag) {
                    //显示密码
                    mLogin_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mLogin_show_password.setImageResource(R.mipmap.show_pw_wh);
                    showPwdFlag = false;
                    mLogin_pwd.setSelection(mLogin_pwd.getText().toString().length());
                } else {
                    //隐藏密码
                    mLogin_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mLogin_show_password.setImageResource(R.mipmap.hide_pw_wh);
                    showPwdFlag = true;
                    mLogin_pwd.setSelection(mLogin_pwd.getText().toString().length());
                }
                break;
        }
    }


    /**
     * 登录账户
     */
    private void loginAccount() {
        LoginUserInfo.getInstance().getUserInfo(LoginActivity.this, mLogin_account.getText().toString().trim(),
                mLogin_pwd.getText().toString().trim(), new LoginUserInfo.LoginResult() {
                    @Override
                    public void setUserInfo(LoginBean loginBean) {
                        if (loginBean.getCode() != 0) {
                            Toast.makeText(LoginActivity.this, "loginBean.getData():" + loginBean.getData(), Toast.LENGTH_SHORT).show();
                        }
                        LogUtils.e("LoginActivity,登录成功，loginBean===" + loginBean);

                        if (isRememberAccount) {
                            SPUtils.getInstance().put(ConstantUtils.IS_REMEMBER_ACCOUNT, true);
                        } else {
                            SPUtils.getInstance().put(ConstantUtils.IS_REMEMBER_ACCOUNT, false);
                        }
                        MobPush.restartPush();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}
