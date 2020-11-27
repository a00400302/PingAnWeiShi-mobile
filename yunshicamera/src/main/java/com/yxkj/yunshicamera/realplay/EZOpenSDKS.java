package com.yxkj.yunshicamera.realplay;

import android.app.Application;

import com.videogo.openapi.EZOpenSDK;

public class EZOpenSDKS {

    public static void initYunshi(Application application) {
        /** * sdk日志开关，正式发布需要去掉 */
        EZOpenSDK.showSDKLog(true);
        /** * 设置是否支持P2P取流,详见api */
        EZOpenSDK.enableP2P(false);

        /** * APP_KEY请替换成自己申请的 */
        EZOpenSDK.initLib(application, "2a339873e20d4fc0bf2c97a806320908");
        EZOpenSDK.getInstance().setAccessToken("at.136ktm653a2tvel69ohh0tz00j5zw4zm-9ke3cat3ot-0c12own-sffwf14vf");
    }
}
