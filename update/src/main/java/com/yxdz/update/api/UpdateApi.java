package com.yxdz.update.api;

import com.yxdz.update.bean.UpdateBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface UpdateApi {

    /**
     * 版本更新
     *
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/askAndroidVersion")
    Observable<UpdateBean> getUpdate(@FieldMap Map<String, Object> parameters);

}
