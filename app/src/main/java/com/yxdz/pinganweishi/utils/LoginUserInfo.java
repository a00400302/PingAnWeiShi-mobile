package com.yxdz.pinganweishi.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCallback;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.ToastUtils;
import com.yxdz.common.utils.YuXinUrl;
import com.yxdz.http.http.RetrofitHelper;
import com.yxdz.http.http.RxSubscriber;
import com.yxdz.pinganweishi.api.LoginApi;
import com.yxdz.pinganweishi.bean.LoginBean;
import com.yxdz.pinganweishi.bean.UserInfoBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录请求接口
 */
public class LoginUserInfo {

    /**
     * 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载
     */
    private static LoginUserInfo instance = null;

    public static LoginUserInfo getInstance() {
        if (instance == null) {
            synchronized (LoginUserInfo.class) {
                if (instance == null) {
                    instance = new LoginUserInfo();
                }
            }
        }
        return instance;
    }

    /**
     * @param context     未空表示不转圈
     * @param account     帐号
     * @param password    密码
     * @param loginResult 回调接口
     */
    public void getUserInfo(final Context context, final String account, final String password, final LoginResult loginResult) {

        YuXinUrl.SERVER = SPUtils.getInstance().getString("SERVER", null);
        YuXinUrl.HTTP = SPUtils.getInstance().getString("HTTP", null);
        YuXinUrl.HTTPS = SPUtils.getInstance().getString("HTTPS", null);

        MobPush.getRegistrationId(new MobPushCallback<String>() {
            @Override
            public void onCallback(String pushToken) {
                Log.d("LoginUserInfo", pushToken);

                if (TextUtils.isEmpty(pushToken)) {
                    ToastUtils.showShort(BaseApplication.getAppContext(), "推送TOKEN为空");
                    return;
                }


                Map<String, Object> map = new HashMap<>();
                map.put(ActValue.Account, account);
                map.put(ActValue.PushToken, pushToken);
                map.put(ActValue.Pwd, password);

                RetrofitHelper.subscriber(RetrofitHelper.Https(LoginApi.class).getAccoutLogin(map), new RxSubscriber<LoginBean>(context, "正在登录...") {
                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        //登录成功,保存帐号
                        if (loginBean.getData() == null) {
                            Toast.makeText(context, "帐号不存在", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            //保存个人信息
                            SPUtils.getInstance().put(ConstantUtils.LOGIN_ACCOUNT, account);
                            SPUtils.getInstance().put(ConstantUtils.LOGIN_PASSWORD, password);
                        }

                        UserInfoBean.getInstance().saveUserInfo(loginBean.getData().getModel());
                        //保存服务器返回的token(注意和push_token不一样)
                        UserInfoBean.getInstance().setToken(loginBean.getData().getToken());
                        SPUtils.getInstance().put(ConstantUtils.TOKEN, loginBean.getData().getToken());
                        loginResult.setUserInfo(loginBean);
                    }

                    @Override
                    public void onFailure(String message) {
                        super.onFailure(message);
                        if (message.equals("Json格式出错了")) {
                            ToastUtils.showShort(context, "密码错误或帐号不存在");
                        }
                    }
                });
            }
        });

    }


    public interface LoginResult {
        void setUserInfo(LoginBean loginBean);
    }

}
