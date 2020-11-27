package com.yxdz.pinganweishi.base;


import com.baidu.mapapi.SDKInitializer;
import com.tencent.bugly.crashreport.CrashReport;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yxdz.common.BaseApplication;
import com.yxdz.common.load.LoadOptions;
import com.yxdz.common.utils.LogUtils;
import com.yxdz.common.utils.SPUtils;
import com.yxdz.common.utils.Utils;
import com.yxdz.pinganweishi.R;
import com.yxdz.pinganweishi.utils.ActValue;
import com.yxdz.pinganweishi.utils.ConstantUtils;
import com.yxkj.yunshicamera.realplay.util.SdkInitTool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huang on 2018/6/12.
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenAdapterTools.init(this);
        //初始化腾讯Bugly
        CrashReport.initCrashReport(getApplicationContext(), "67ae8c6db5", false);

        //百度地图:初始化定位sdk
        SDKInitializer.initialize(this);

        Utils.init(this);

        //友盟初始化
//        ShareInitializer.init(this);

        //Log日志打印
        LogUtils.isDebug = true;

        //统一配置断网共用的图片
        LoadOptions.setBrokenNetwork(R.mipmap.no_data);

        Map<String, Object> map = new HashMap<>();
        map.put(ActValue.Token, SPUtils.getInstance().getString(ConstantUtils.TOKEN, ""));


        SdkInitTool.init(this);
//        RetrofitHelper.subscriber(RetrofitHelper.Https(FireApi.class).getAccessToken(map), new RxSubscriber<CameraTokenBean>(this) {
//
//            @Override
//            protected void onSuccess(CameraTokenBean cameraTokenBean) {
//                SdkInitTool.initToken(cameraTokenBean.getData().getAccessToken());
//            }
//        });


    }


}
