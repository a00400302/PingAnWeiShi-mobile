package com.yxdz.pinganweishi.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class GlideUtils {

    /**
     * @param context
     * @param imageView
     * @param url       仅用于圆形图片——头像显示
     */
    public static void displayCropCircle(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url).into(imageView);
        RequestOptions requestOptions = new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(com.yxdz.common.R.mipmap.person_img_head_normal)//图片加载出来前，显示的图片
                .error(com.yxdz.common.R.mipmap.person_img_head_no_data)//图片加载失败后，显示的图片
                .transform(new GlideRoundTransformation());

        Glide.with(context).load(url).apply(requestOptions).into(imageView);

    }

    /**
     * @param context
     * @param imageView
     * @param url       默认图片
     */
    public static void displayDefault(Context context, ImageView imageView, String url) {
//        Glide.with(context).load(pathUrl).placeholder(R.drawable.person_img_head_no_data)
//                .error(R.drawable.person_img_head_no_data).into(view);
        Glide.with(context).load(url).into(imageView);
        RequestOptions requestOptions = new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(com.yxdz.common.R.mipmap.common_loading_picture)//图片加载出来前，显示的图片
                .error(com.yxdz.common.R.mipmap.person_img_head_no_data);//图片加载失败后，显示的图片;

        Glide.with(context).load(url).apply(requestOptions).into(imageView);


    }

    public static void displayDefault2(Context context, ImageView imageView, String url) {
//        Glide.with(context).load(pathUrl).placeholder(R.drawable.person_img_head_no_data)
//                .error(R.drawable.person_img_head_no_data).into(view);
        Glide.with(context).load(url).into(imageView);
        RequestOptions requestOptions = new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(com.yxdz.common.R.mipmap.common_loading_picture)//图片加载出来前，显示的图片
                .error(com.yxdz.common.R.mipmap.camera_bg_black);//图片加载失败后，显示的图片;

        Glide.with(context).load(url).apply(requestOptions).into(imageView);


    }

}
