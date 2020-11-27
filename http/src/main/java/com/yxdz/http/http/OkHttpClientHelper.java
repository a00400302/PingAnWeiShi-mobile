package com.yxdz.http.http;


import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;


/**
 * Created by li4236 on 2017/1/5.
 * okhttp操作：添加公司统一配置head
 */

public class OkHttpClientHelper {
    private final Cache cache;
    private OkHttpClient mClient;
    private final static long TIMEOUT = 60;  //超时时间

    private OkHttpClientHelper() {

        cache = CacheHelper.getInstance().getCache();
    }

    private static OkHttpClientHelper clientHelper;

    public static OkHttpClientHelper getInstance() {
        if (clientHelper == null) {
            synchronized (OkHttpClientHelper.class) {
                if (clientHelper == null) {
                    clientHelper = new OkHttpClientHelper();
                }
            }
        }
        return clientHelper;
    }


    //获取OKHttpClicent对象
    public OkHttpClient getOkHttpClient() {

        if (mClient == null) {
            mClient = new OkHttpClient.Builder()
                    //设置超时时间
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    //读取时间
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
//                    .sslSocketFactory(HttpsUtils.getSSLSocketFactory(BaseApplication.getAppContext()))
//                    .sslSocketFactory(HttpsUtils.getSSLSocketFactory(BaseApplication.getAppContext()),new MyX509TrustManager())
//                    .hostnameVerifier(new HostnameVerifier() {
//                        @Override
//                        public boolean verify(String hostname, SSLSession session) {
//                            return true;
//                        }
//                    })
                    //添加日志输出
//                    .addInterceptor(new LogInterceptor())
//                    .addInterceptor(new Interceptor() {
//                        @Override
//                        public Response intercept(Chain chain) throws IOException {
//
//                            String time = AppSafety.getTime();
//
//
//                            Request original = chain.request();
//
//                            //添加公司统一参数配置
//                            Request.Builder requestBuilder = original.newBuilder()
//                                    .addHeader("key", AppSafety.getKey(time))
//                                    .addHeader("clueid", AppSafety.getClueid(time));
//
//
//                            HashMap<String, Object> hashMap = new HashMap<>();
//
//                            Request request = null;
//
//                            if ("POST".equals(original.method())) {
//
//
//                                //请求体定制：统一添加sign参数
//                                if (original.body() instanceof FormBody) {
//                                    FormBody.Builder newFormBody = new FormBody.Builder();
//                                    FormBody oidFormBody = (FormBody) original.body();
//
//                                    for (int i = 0; i < oidFormBody.size(); i++) {
//                                        String name = oidFormBody.name(i);
//                                        String value = oidFormBody.value(i);
//                                        hashMap.put(name, value);
//                                        newFormBody.addEncoded(name, value);
//
//
//                                    }
//
//                                    hashMap.put(AlActValue.Imei, AlAccountUtil.getInstance().getPhoneImei());
//
//                                    newFormBody.addEncoded(AlActValue.Imei, AlAccountUtil.getInstance().getPhoneImei());
//
//                                    if (!hashMap.containsKey(AlActValue.Account)&& !TextUtils.isEmpty(AlAccountUtil.getInstance().getEecodeAccount())) {
//                                        hashMap.put(AlActValue.Account, AlAccountUtil.getInstance().getEecodeAccount());
//                                        newFormBody.addEncoded(AlActValue.Account, AlAccountUtil.getInstance().getEecodeAccount());
//                                    }
//
//
//                                    newFormBody.add(AlActValue.ACCESSSIGN, AppSafety.getParams(hashMap, original));
//
//                                    requestBuilder.method(original.method(), newFormBody.build());
//                                    request = requestBuilder.build();
//                                } else if (original.body() instanceof MultipartBody) {
//
////                                    HashMap<String, Object> rootMap = new HashMap<>();
////                                    //buffer流
////                                    Buffer buffer = new Buffer();
////                                    original.body().writeTo(buffer);
////                                    String oldParamsJson = buffer.readUtf8();
//////                                    rootMap = new Gson().fromJson(oldParamsJson, HashMap.class);  //原始参数
////
////
////                                    ComLog.E(oldParamsJson);
////                                    MultipartBody.Builder newMultipartBody = new MultipartBody.Builder();
////                                    MultipartBody multipartBody = (MultipartBody) original.body();
////
////
////
////                                    for (int i = 0; i < multipartBody.size(); i++) {
////
////
////                                        MultipartBody.Part name = multipartBody.part(i);
////
////
////
////                                        newMultipartBody.addPart(name.body());
////
////
//////                                        multipartBody.boundary()
//////                                        hashMap.put(name, value);
////
////                                        RequestBody requestBody1 = name.body();
////
////                                        ComLog.E("123", requestBody1.contentType().toString());
////
////
//////                                        newMultipartBody.addFormDataPart(multipartBody.boundary(), name.body());
////
////                                    }
////
////                                    hashMap.put(AlActValue.Imei, AlAccountUtil.getInstance().getPhoneImei());
////
////                                    newMultipartBody.addFormDataPart(AlActValue.Imei, AlAccountUtil.getInstance().getPhoneImei());
////
////                                    if (!hashMap.containsKey(AlActValue.Account)) {
////                                        hashMap.put(AlActValue.Account, AlAccountUtil.getInstance().getEecodeAccount());
////                                        newMultipartBody.addFormDataPart(AlActValue.Account, AlAccountUtil.getInstance().getEecodeAccount());
////                                    }
////
////
////                                    newMultipartBody.addFormDataPart(AlActValue.ACCESSSIGN, AppSafety.getParams(hashMap, original));
////
////                                    requestBuilder.method(original.method(), newMultipartBody.build());
////
////
//                                    request = requestBuilder.build();
//                                }
//                            } else if ("GET".equals(original.method())) {
//
//                                Iterator<String> iterator = original.url().queryParameterNames().iterator();
//                                while (iterator.hasNext()) {
//                                    String key = iterator.next();
//                                    String value = original.url().queryParameter(key);
//                                    hashMap.put(key, value);
//                                }
//
//
//                                HttpUrl.Builder builder = original.url().newBuilder();
//
//                                hashMap.put(AlActValue.Imei, AlAccountUtil.getInstance().getPhoneImei());
//
//                                builder.addQueryParameter(AlActValue.Imei, AlAccountUtil.getInstance().getPhoneImei());
//
//
//                                if (!hashMap.containsKey(AlActValue.Account)&& !TextUtils.isEmpty(AlAccountUtil.getInstance().getEecodeAccount())) {
//                                    hashMap.put(AlActValue.Account, AlAccountUtil.getInstance().getEecodeAccount());
//                                    builder.addQueryParameter(AlActValue.Account, AlAccountUtil.getInstance().getEecodeAccount());
//                                }
//
//                                builder.addQueryParameter(AlActValue.ACCESSSIGN, AppSafety.getParams(hashMap, original));
//
//                                HttpUrl accessSign = builder.build();
//
////                                HttpUrl accessSign = original.url().newBuilder()
////                                        .addQueryParameter(AlActValue.ACCESSSIGN, AppSafety.getParams(hashMap, original))
////                                        .build();
//                                request = requestBuilder.url(accessSign).build();
//                            }
//
////                            request = requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=GBK"),
////                                    URLDecoder.decode(bodyToString(request.body()), "UTF-8")))
////                                    .build();
//                            return chain.proceed(request);
//                        }
//
//                    })
                    //设置缓存位置
                    .cache(cache)
                    .build();


        }
        return mClient;
    }


}
