package com.yxdz.http.http;

import android.content.Context;

import com.yxdz.common.BaseApplication;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by li4236 on 2017/1/5.
 * <p>
 * 网络存放缓存的文件
 */

public class CacheHelper {

    private Cache mCache;
    //设置缓存目录
    private static File cacheFile;
    private static long maxSize = 8 * 1024 * 1024;

    private CacheHelper() {

        Context context = BaseApplication.getAppContext();

        cacheFile = new File(context.getCacheDir().getAbsolutePath(), "mycache");
    }

    private static CacheHelper helper;

    public static CacheHelper getInstance() {
        if (helper == null) {
            synchronized (CacheHelper.class) {
                if (helper == null) {
                    helper = new CacheHelper();
                }
            }
        }
        return helper;
    }


    //返回缓存对象
    public Cache getCache() {
        if (mCache == null)
            mCache = new Cache(cacheFile, maxSize);
        return mCache;
    }
}
