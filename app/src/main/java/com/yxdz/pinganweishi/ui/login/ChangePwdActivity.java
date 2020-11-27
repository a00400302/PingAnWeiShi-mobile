package com.yxdz.pinganweishi.ui.login;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.leaf.library.StatusBarUtil;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.view.TitleBarView;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.api.LoginApi;
import com.yxdz.pinganweishi.base.BaseHeadActivity;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.AppCommonUtil;
import com.yxdz.pinganweishi.utils.ConstantUtils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 修改密码——记得旧密码
 */
public class ChangePwdActivity extends BaseHeadActivity implements View.OnClickListener {

    private EditText etOldPwd;
    private EditText etNewPwd;
    private EditText etNewPwdAgain;
    private Button btnChange;
    private TitleBarView titleBarView;


    @Override
    public int getLayoutResource() {
        return R.layout.activity_change_pwd;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setContentView() {
        titleBarView = findViewById(R.id.toolbar);
        int[] colors = {ContextCompat.getColor(this, R.color.primarystart), ContextCompat.getColor(this, R.color.primaryend)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        titleBarView.setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(this, titleBarView);
        initView();
    }

    @Override
    protected void setTitlebarView() {
        titleBarView.setTitleBarText("修改密码");
    }

    private void initView() {
        etOldPwd = findViewById(R.id.change_pwd_old);
        etNewPwd = findViewById(R.id.change_pwd_new);
        etNewPwdAgain = findViewById(R.id.change_pwd_new_again);
        btnChange = findViewById(R.id.change_pwd_btn);
        btnChange.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_pwd_btn:
                if (etOldPwd.length() < 6) {
                    ToastUtils.showShort(ChangePwdActivity.this, "您输入的原密码小于6位数");
                    return;
                }
                //判断两次密码是否一致
                if (!checkPwd(etNewPwd.getText().toString(), etNewPwdAgain.getText().toString())) {
                    return;
                }
                changePassword();
                break;
        }
    }

    /**
     * 修改密码——记得原密码
     */
    private void changePassword() {
        HashMap<String, Object> map = new HashMap();
        map.put("oldPwd", etOldPwd.getText().toString().trim());
        map.put("newPwd", etNewPwd.getText().toString().trim());
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));
//        map.put(ActValue.Token, UserInfoBean.getInstance().getToken());
        RetrofitHelper.subscriber(RetrofitHelper.Https(LoginApi.class).changePwd(map), new RxSubscriber<DefaultBean>(this, "正在修改...") {
            @Override
            protected void onSuccess(DefaultBean defaultBean) {
                Log.d("ChangePwdActivity", "defaultBean.getCode():" + defaultBean.getCode());
                if (defaultBean.getCode() == 0) {
                    ToastUtils.showShort(ChangePwdActivity.this, "密码修改成功");
                    //修改密码成功，重新保存新密码
                    SPUtils.getInstance().put(ConstantUtils.LOGIN_PASSWORD, etNewPwdAgain.getText().toString().trim());
                    finish();
                } else {
                    ToastUtils.showShort(ChangePwdActivity.this, "密码修改失败请稍后重试");
                }
            }

            @Override
            protected void onOtherError(String code) {
                super.onOtherError(code);
                AppCommonUtil.doOtherRespone(BaseApplication.getAppContext(), code);
            }

        });

    }

    /**
     * 验证两次密码是否一致
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
                ToastUtils.showShort(ChangePwdActivity.this, "密码存在非法字符！");
                return false;
            }
            if (!m2.matches()) {
                ToastUtils.showShort(ChangePwdActivity.this, "确认密码存在非法字符！");
                return false;
            }
            if (password.length() < 6) {
                ToastUtils.showShort(ChangePwdActivity.this, "密码不能小于6位字符");
                return false;
            } else {
                if (passwordAgain.length() < 6) {
                    ToastUtils.showShort(ChangePwdActivity.this, "确认密码不能小于6位字符");
                    return false;
                } else {
//                    Pattern isNum = Pattern.compile("[0-9]*");// 判断是否纯数字
//                    Matcher isNum2 = isNum.matcher(password);
//                    if (isNum2.matches()) {
//                        ToastUtils.showShort(ChangePwdActivity.this, "密码不能纯数字！");
//                        return false;
//                    }
//                    Pattern isChar = Pattern.compile("[a-zA-Z]*");// 判断是否纯字母
//                    Matcher isChar2 = isChar.matcher(password);
//                    if (isChar2.matches()) {
//                        ToastUtils.showShort(ChangePwdActivity.this, "密码不能纯字母！");
//                        return false;
//                    }
                    if (password.equals(passwordAgain)) {

                        return true;
                    }
                }
            }
        } else {
            if (password.equals("")) {
                ToastUtils.showShort(ChangePwdActivity.this, "密码不能为空！");
                return false;
            }
            if (passwordAgain.equals("")) {
                ToastUtils.showShort(ChangePwdActivity.this, "确认密码不能为空！");
                return false;
            }
        }
        ToastUtils.showShort(ChangePwdActivity.this, "两次输入的密码不相同,请重新输入密码");
        return false;
    }

}
