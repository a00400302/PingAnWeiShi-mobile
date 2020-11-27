package com.yxdz.common.load;

import android.content.Context;

/**
 * Created by li4236 on 2017/1/18.
 * 面向接口编程
 * 转圈接口
 */

public interface AlLoadingImpl {

    /**
     * 显示界面
     */
    public void show();

    /**
     * 显示断网图片
     */
    public void showBrokenNetworkDrawable();

    /**
     * 显示网络超时
     */
    public void showOverTimeDrawable();

    /**
     * 显示没有数据
     */
    public void showNoDataDrawable();

    /**
     * 移除所有的图片
     */
    public void removeView();

    /**
     * 处理背景透明
     */
    public boolean isTransparent();

    /**
     * 隐藏转圈，仅适用于下拉刷新或者加载更多
     */
    public boolean isCircleShow();

    /**
     * 断网提示需要用到
     */
    public Context getContext();

    /**
     * 迎合炯换断网需要、实际我们这边不需要
     */
    public void onClickNetWork();
}
