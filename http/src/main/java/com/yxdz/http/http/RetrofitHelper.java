package com.yxdz.http.http;


import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yxdz.common.utils.YuXinUrl;
import com.yxdz.http.other.ToStringConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by huang on 2018/5/23.
 */

public class RetrofitHelper {

    private static RetrofitHelper helper;
    private final OkHttpClient mClient;
    private Retrofit mRetrofit, mRetrofits;

    private RetrofitHelper() {
        mClient = OkHttpClientHelper.getInstance().getOkHttpClient();
    }


    //订阅事件

    public static <S> S Https(Class<S> serviceClass) {
        Retrofit retrofit = RetrofitHelper.getInstance()
                .getRetrofit(true);
        return retrofit.create(serviceClass);
    }

    public static <S> S Http(Class<S> serviceClass) {
        Retrofit retrofit = RetrofitHelper.getInstance()
                .getRetrofit(false);
        return retrofit.create(serviceClass);
    }
//    public static <T> Callback.Cancelable DownLoadFile(String url, String filepath, Callback.CommonCallback<T> callback) {
//
//        RequestParams params = new RequestParams(url);
//        //设置断点续传
//        params.setAutoResume(true);
//
//        params.setSaveFilePath(filepath);
//
//        Callback.Cancelable cancelable = x.http().get(params, callback);
//
//        return cancelable;
//    }

    /////////////////////////////////////////////////////////////////////////

    /**
     * @param observable
     * @param subscriber
     * @param <T>
     * @return
     */
    public static <T> Subscription subscriber(Observable<T> observable, final RxSubscriber<T> subscriber) {

        return observable

//                .retry(2)//如果网络请求失败，尝试次数
                .subscribeOn(Schedulers.io())//切换新的线程处理
                .observeOn(AndroidSchedulers.mainThread())//切换主线程
                .subscribe(subscriber);
    }

    /**
     * 测试网络加载转圈
     *
     * @param observable
     * @param subscriber
     * @param <T>
     * @return
     */
    public static <T> Subscription subscriber(Observable<T> observable, final RxSubscriberTest<T> subscriber) {

        return observable

//                .retry(2)//如果网络请求失败，尝试次数
                .subscribeOn(Schedulers.io())//切换新的线程处理
                .observeOn(AndroidSchedulers.mainThread())//切换主线程
                .subscribe(subscriber);
    }

    /**
     * @param observable
     * @param subscriber
     * @param <T>
     * @return
     */
    public static <T> Subscription subscriber(Observable<T> observable, Subscriber<? super T> subscriber) {

        return observable

//                .retry(2)//如果网络请求失败，尝试次数
                .subscribeOn(Schedulers.io())//切换新的线程处理
                .observeOn(AndroidSchedulers.mainThread())//切换主线程
                .subscribe(subscriber);

    }

    /**
     * 下载文件
     */

    public static <S> S DownLoad(Class<S> serviceClass, String downloadUrl) {
//        Retrofit retrofit = RetrofitHelper.getInstance()
//                .getRetrofit(false);
        //mRetrofits = getBuild(bseUrl);
        Retrofit retrofit = RetrofitHelper.getInstance().getBuild(downloadUrl);

        return retrofit.create(serviceClass);
    }

    //单例 保证对象唯一
    public static RetrofitHelper getInstance() {
        if (helper == null) {
            synchronized (RetrofitHelper.class) {
                if (helper == null) {
                    helper = new RetrofitHelper();
                }
            }
        }
        return helper;
    }

    public OkHttpClient getClient() {
        return mClient;
    }


    /**
     * @param m true = https false = http
     * @return
     */
    //获取Retrofit对象
    public Retrofit getRetrofit(boolean m) {

        String bseUrl = m ? YuXinUrl.HTTPS : YuXinUrl.HTTP;

//        if (m) {
//            if (mRetrofits == null) {
        mRetrofits = getBuild(bseUrl);
//            }
//            return mRetrofits;
//        } else {
//            if (mRetrofit == null) {
        mRetrofit = getBuild(bseUrl);
//            }
        return mRetrofit;
//    }

    }

    @NonNull
    private Retrofit getBuild(String bseUrl) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(bseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))  //添加Gson支持
//                    .addConverterFactory(new ToStringConverterFactory())  //定义字符串形式支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //添加RxJava支持
                .client(mClient)                                            //关联okhttp
                .build();
    }


    /**
     * 仅仅只用于测试请求网络的时候返回字符串
     */

    public Retrofit getStringRetrofit(boolean m) {

        String bseUrl = m ? YuXinUrl.HTTPS : YuXinUrl.HTTP;

        return new Retrofit.Builder()
                .baseUrl(bseUrl)
//                    .addConverterFactory(GsonConverterFactory.create())  //添加Gson支持
                .addConverterFactory(new ToStringConverterFactory())  //定义字符串形式支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //添加RxJava支持
                .client(mClient)                                            //关联okhttp
                .build();
    }

}