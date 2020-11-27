package com.yxdz.update.utils;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;

/**
 * Created by huang on 2018/6/1.
 */

public class UpdatePath {

    //没有自己模块的路径，根据格式自己添加
    private static final String Y_PATH = Environment.getExternalStorageDirectory().getPath();
    //整个项目的根路径存放位置
    private static String GLOBAL_PATH = Y_PATH + "/yOther";

    /**
     * 设置项目路径文件名
     */
    public static void setAppPath(String appPath) {
        GLOBAL_PATH = Y_PATH + "/" + appPath;
    }


    public static final String GLOBAL_VIDEO_PATH = GLOBAL_PATH + "/video";
    public static final String GLOBAL_WIFI_PATH = GLOBAL_PATH + "/wifi";
    public static final String GLOBAL_GAME_PATH = GLOBAL_PATH + "/game";

    /**
     * 获得下载路径
     */
    public static String getDownPath() {
        return getSDCardPath() + "/down" + File.separator;
    }


    /**
     * 获得启动页路径
     */
//    public static String getSplashPath() {
//        return getSDCardPath() + "/splash/";
//    }


    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        if (!isSDCardEnable()) {
            return null;
        }
        Log.e("SunySan", "最基本的SD卡路径：" + GLOBAL_PATH);

        return GLOBAL_PATH;
    }


    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
//            // 获取空闲的数据块的数量
//            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
//            // 获取单个数据块的大小（byte）
//            long freeBlocks = stat.getAvailableBlocks();
//            return freeBlocks * availableBlocks;

            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
//            long freeBlocks = stat.getAvailableBlocks();
//            return freeBlocks * availableBlocks;
//            -----------------------------------------------------------------
            // 获取单个数据块的大小（byte）
            int mBlockSize = stat.getBlockSize();
            return mBlockSize * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }
}
