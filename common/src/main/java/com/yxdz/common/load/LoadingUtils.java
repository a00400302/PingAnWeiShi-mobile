package com.yxdz.common.load;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yxdz.common.R;
import com.yxdz.common.utils.ComNetWorkUtils;
import com.yxdz.common.utils.ToastUtils;


/**
 * @author li4236
 * <p/>
 * 注意应用到加载网络数据等待的过程
 */
public class LoadingUtils implements View.OnClickListener, AlLoadingImpl {

    private LoadOptions options;

    private ImageView mImageView;

    private ViewGroup mViewGroup;

    private Context mContext;

    //private CircleCustomView mCircleLayout;

//    private ImageView loadingico;

    private LinearLayout mCommonLayout;

//    private PointLoadingView loadView;

    private LinearLayout mImageLayout;

    private IntentFilter intentFilter;
    private NetworkChangeReceive networkChangeReceive;

    public LoadingUtils(LoadOptions mOptions) {

        if (mOptions == null)
            return;

        this.options = mOptions;

        this.mContext = options.getViewGroup().getContext();


//        networkChangeReceive = new NetworkChangeReceive();
//        mContext.registerReceiver(networkChangeReceive, intentFilter);

        show();

    }

    public void show() {

        if (!isCircleShow()) {

            if (options.getViewGroup().getTag() != null) //防止重复添加
            {
                options.getViewGroup().removeView((ViewGroup) options.getViewGroup().getTag());
            }

            mViewGroup = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.commom_loading_view, null);

            mCommonLayout = (LinearLayout) mViewGroup.findViewById(R.id.common_load_view_common_layout);

//            loadView = (PointLoadingView) mViewGroup.findViewById(R.id.pointloading_point);

            mImageLayout = (LinearLayout) mViewGroup.findViewById(R.id.common_load_view_image_layout);

            if (isTransparent())//设置背景透明
            {
                mCommonLayout.setBackgroundColor(Color.TRANSPARENT);
                mCommonLayout.setOnClickListener(this);
            } else {
                mCommonLayout.setBackgroundColor(Color.WHITE);
            }
//            loadingico = (ImageView) mViewGroup.findViewById(R.id.pointloading_ico);
//
//            loadingico.setImageResource(LoadOptions.getCircleBackImg());

            mImageView = (ImageView) mViewGroup.findViewById(R.id.common_load_view_image);

            //断网的处理
            if (!ComNetWorkUtils.isNetWorking(mContext)) {
                if (!isTransparent()) {
                    showBrokenNetworkDrawable();

                    mImageView.setOnClickListener(this);

                    intentFilter = new IntentFilter();
                    //addAction
                    intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                    networkChangeReceive = new NetworkChangeReceive();
                    mContext.registerReceiver(networkChangeReceive, intentFilter);
                }
            }

            options.getViewGroup().addView(mViewGroup);

            options.getViewGroup().setTag(mViewGroup);
        }
    }

    /**
     * 设置没有网络的图片
     */
    public void showBrokenNetworkDrawable() {

        if (mImageView != null) {

            mCommonLayout.setVisibility(View.GONE);

//            loadView.shutdown();

            mImageLayout.setVisibility(View.VISIBLE);

            mImageView.setBackgroundResource(options.getBrokenNetworkDrawable());

            mImageView.setOnClickListener(this);
        }
    }

    @Override
    public void showOverTimeDrawable() {
        if (mImageView != null) {

            mCommonLayout.setVisibility(View.GONE);

//            loadView.shutdown();

            mImageLayout.setVisibility(View.VISIBLE);

            mImageView.setBackgroundResource(options.getOverTimeDrawable());

            mImageView.setOnClickListener(this);
        }
    }

    /**
     * 设置出现转圈的处理
     */
    public void showCircleDrawable() {

        if (mImageView != null) {

            mCommonLayout.setVisibility(View.VISIBLE);

//            loadView.start();

            mImageLayout.setVisibility(View.GONE);

        }


    }

    /**
     * 设置没有数据的图片
     */
    public void showNoDataDrawable() {

        if (mImageView != null) {

            if (mViewGroup.getVisibility() == View.GONE)
                mViewGroup.setVisibility(View.VISIBLE);

            mCommonLayout.setVisibility(View.GONE);

//            loadView.shutdown();

            mImageLayout.setVisibility(View.VISIBLE);

            if (options.getNoDataDrawable() == 0) {
                mImageView.setBackgroundResource(options.getAllNoData());
            } else //额外设置单个图片
            {
                mImageView.setBackgroundResource(options.getNoDataDrawable());
            }


            mImageView.setOnClickListener(null);
        }

    }

    @Override
    public void removeView() {


        if (mViewGroup != null) {

//            options.getViewGroup().removeView(mViewGroup);

            mViewGroup.setVisibility(View.GONE);
//            loadView.shutdown();
        }
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.common_load_view_image) {//断网的处理

            if (!ComNetWorkUtils.isNetWorking(mContext)) {

                ToastUtils.showShort(mContext, "请检查网络");

            } else {

                if (options.getRetryNetWork() != null) {//当有网络的回调的处理

                    showCircleDrawable();
                    options.getRetryNetWork().retryNetWork();
                } else {
                    onClickNetWork();
                }
            }


        }
    }


//    /**
//     * @param 设置是否加载的时候透明
//     */
//    public void setBackgroundTransparent(boolean transparent) {
//        options.setTransparent(transparent);
//    }

    public boolean isTransparent() {
        return options.isTransparent();
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void onClickNetWork() {

    }

//    /**
//     * @param 仅适用于下拉刷新、加载更多的处理,不显示转圈
//     */
//    public void setCircleShow(boolean transparent) {
//        options.setCircleShow(transparent);
//    }

    public boolean isCircleShow() {
        return options.isCircleShow();
    }

    class NetworkChangeReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(mContext, "net is available", Toast.LENGTH_SHORT).show();

                if (options.getRetryNetWork() != null) {//当有网络的回调的处理

                    showCircleDrawable();
                    options.getRetryNetWork().retryNetWork();
                }
            } else {
                Toast.makeText(mContext, "net is not available", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
