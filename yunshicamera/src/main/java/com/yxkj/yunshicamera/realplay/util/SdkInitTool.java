package com.yxkj.yunshicamera.realplay.util;

import android.app.Application;

import com.videogo.openapi.EZOpenSDK;

import java.util.Set;

public class SdkInitTool {

    public static void init(Application application){
        /*
         * sdk日志开关，正式发布需要去掉
         */
        EZOpenSDK.showSDKLog(true);

        /*
         * 设置是否支持P2P取流,详见api
         */
        EZOpenSDK.enableP2P(false);

        /*
         * APP_KEY请替换成自己申请的
         */
        EZOpenSDK.initLib(application, "82dc079d5a3c4785891b3e82ee87fb04");
//                EZOpenSDK.initLib(application, "2a339873e20d4fc0bf2c97a806320908");
    }


    public static void initToken(String accessToken){
        EZOpenSDK.getInstance().setAccessToken(accessToken);
    }

    public static String getToken(){
        return EZOpenSDK.getInstance().getEZAccessToken().getAccessToken();
    }
}
