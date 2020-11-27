package com.yxdz.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by huang on 2018/5/23.
 */

public class BaseApplication extends Application {

    private static Context baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    public static Context getAppContext() {
        return baseApplication;
    }

}
