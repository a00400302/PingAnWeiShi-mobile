package com.yxdz.common.load;

import android.view.ViewGroup;


/**
 * @author li4236
 *         生成器模式
 */
public class LoadOptions {

    public LoadOptions() {
    }

    public final static LoadOptions options = new LoadOptions();

    public static int brokenNetwork; //断网图片

    public static int overTime; //网络超时

    public static int circleImg; //转圈的图片

    public static int circleBackImg; //黑色转圈的图片

    public static int logoImg; //标志图片

    public static int themeColors;//主题颜色

    public static int allNoData;//共用没有数据


    /////////////////全局需要配置的参数//////////////

    private EnumLoad enumLoad = EnumLoad.BLACK;

    private int noData; //单个设置没有数据图片

    //转圈的初始化
    private AlLoadingImpl loadingView;

    private ViewGroup viewGroup;
    //加载数据重新回调
    private RetryNetWorkImpl retryNetWork;
    //转圈透明
    public boolean transparent = false;

    //仅适用于下拉刷新、加载更多的处理,不显示转圈
    public boolean circleShow = false;

    public int getBrokenNetworkDrawable() {
        return brokenNetwork;
    }

    public int getOverTimeDrawable() {
        return overTime;
    }

    public int getNoDataDrawable() {
        return noData;
    }

    public AlLoadingImpl getLoadingView() {
        return loadingView;
    }
//
//    public EnumLoad getEnumLoad() {
//        return enumLoad;
//    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public boolean isCircleShow() {
        return circleShow;
    }

    public void setCircleShow(boolean circleShow) {
        this.circleShow = circleShow;
    }

    public RetryNetWorkImpl getRetryNetWork() {
        return retryNetWork;
    }

    /**
     * @param transparent 应用第二次使用视图、背景透明、显示转圈
     */
    public void setTransparent(boolean transparent) {

        this.transparent = transparent;
        if (transparent)
            //弹出转圈
            this.getLoadingView().show();
    }


    /////////全局方法处理/////////////////////


    public static int getAllNoData() {
        return allNoData;
    }

    public static void setAllNoData(int allNoData) {
        LoadOptions.allNoData = allNoData;
    }

    public static void setBrokenNetwork(int brokenNetwork) {
        LoadOptions.brokenNetwork = brokenNetwork;
    }

    public static void setOverTime(int overTime) {
        LoadOptions.overTime = overTime;
    }


    public static int getCircleImg() {
        return circleImg;
    }

    public static void setCircleImg(int circleImg) {
        LoadOptions.circleImg = circleImg;
    }

    public static void setCircleBackImg(int circleBackImg) {
        LoadOptions.circleBackImg = circleBackImg;
    }
    public static int getCircleBackImg() {
        return circleBackImg;
    }

    public static int getLogoImg() {
        return logoImg;
    }

    public static void setLogoImg(int logoImg) {
        LoadOptions.logoImg = logoImg;
    }

    public static int getThemeColors() {
        return themeColors;
    }

    public static void setThemeColors(int themeColors) {
        LoadOptions.themeColors = themeColors;
    }

    ////////////构造器模式////////////////////
    public static class AlBuilder {

        protected LoadOptions options;

        public AlBuilder() {
            this.options = new LoadOptions();

        }

        //设置加载转圈的类型
        public AlBuilder setEnumLoadType(EnumLoad enumLoad) {

            this.options.enumLoad = enumLoad;

            return this;
        }
        //断网的图片
        public AlBuilder setBrokenNetworkDrawableId(int brokenNetworkId) {

            this.options.brokenNetwork = brokenNetworkId;

            return this;
        }

        //背景透明
        public AlBuilder setBackgroundTransparent(boolean transparent) {

            this.options.transparent = transparent;

            return this;
        }

        //没有数据的时候显示的图片
        public AlBuilder setNoDataDrawableId(int noDataId) {

            this.options.noData = noDataId;

            return this;
        }


        public AlBuilder setViewGroup(ViewGroup viewGroup, ViewGroup parentGroup) {
            this.options.viewGroup = parentGroup;
            this.options.viewGroup.addView(viewGroup);
            return this;
        }

        //往主工程添加转圈的处理
        public AlBuilder setViewGroup(ViewGroup parentGroup) {
            this.options.viewGroup = parentGroup;
            return this;
        }

        public AlBuilder setRetryNetWorkCallback(RetryNetWorkImpl retryNetWork) {
            this.options.retryNetWork = retryNetWork;
            return this;
        }

        //初始化转圈并且创建
        public LoadOptions build() {
            //普通的工程设计模式

            if (options.enumLoad == EnumLoad.ORDINARY) {

//                this.options.loadingView = new LoadingView(options);

            }else if (options.enumLoad == EnumLoad.BLACK)
            {
                this.options.loadingView = new LoadingUtils(options);
            }

            return options;

        }
    }


}
