package com.yxdz.pinganweishi.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yxdz.common.load.RetryNetWorkImpl;
import com.yxdz.pinganweishi.R;


/**
 * 无网络视图
 */
public class NetErrorView extends LinearLayout {

    private Context context;
    private ViewGroup viewGroup;
    private LinearLayout netLayout;
    private ImageView imageView;

    public NetErrorView(Context context, ViewGroup viewGroup) {
        super(context);
        this.viewGroup = viewGroup;
        this.context = context;
        initView();
    }

    public LinearLayout getLayout() {
        return netLayout;
    }

    public ImageView getImageView() {
        return imageView;
    }



    private void initView() {
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.net_error_layout, this);
        netLayout = findViewById(R.id.net_error_layout);
        imageView = findViewById(R.id.net_error_image);


    }

    public static class Builder {

        private Context context;
        private ViewGroup viewGroup;
        private String text = "没有网络,请检查网络设置";
        private int imageview = R.mipmap.no_data;
        private RetryNetWorkImpl retryNetWorkImpl;


        /**
         * @param context
         * @param viewGroup 盛放无网络状态视图
         */
        public Builder(Context context, ViewGroup viewGroup) {
            this.context = context;
            this.viewGroup = viewGroup;
        }

        /**
         * 设置提示信息
         *
         * @param text
         * @return
         */

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        /**
         * 设置显示无网络状态图片
         *
         * @param imageview
         * @return
         */
        public Builder setImageViewResource(int imageview) {
            this.imageview = imageview;
            return this;
        }

        /**
         * 设置视图点击事件
         *
         * @param retryNetWorkImpl
         * @return
         */
        public Builder setRetryNetWorkImpl(RetryNetWorkImpl retryNetWorkImpl) {
            this.retryNetWorkImpl = retryNetWorkImpl;
            return this;
        }


        public NetErrorView create() {

            NetErrorView netErrorView = new NetErrorView(context, viewGroup);
            netErrorView.getImageView().setImageResource(imageview);

            netErrorView.getLayout().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    retryNetWorkImpl.retryNetWork();
                }
            });
            return netErrorView;
        }

    }

    /**
     * 添加无网络状态视图
     */
    public void addNetErrorView() {
        if (getParent() == null) {
            viewGroup.addView(this);
        }
    }

    /**
     * 移除无网络状态视图
     */
    public void removeNetErrorView() {
        if (getParent() != null) {
            viewGroup.removeView(this);
        }
    }


}
