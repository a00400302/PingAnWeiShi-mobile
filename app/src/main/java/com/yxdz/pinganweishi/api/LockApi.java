package com.yxdz.pinganweishi.api;



import java.util.Map;

import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.DoorLockBindBean;
import com.yxdz.pinganweishi.bean.DoorLockInfoBean;
import com.yxdz.pinganweishi.bean.DoorLockListBean;
import com.yxdz.pinganweishi.bean.MagnetismBean;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface LockApi {



    @FormUrlEncoded
    @POST("mobile/doorLockInfo")
    Observable<DoorLockInfoBean> doorLockInfo(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("mobile/deviceBindDoorLockList")
    Observable<DoorLockBindBean> deviceBindDoorLockList(@FieldMap Map<String, Object> params);



    @FormUrlEncoded
    @POST("mobile/doorLockList")
    Observable<DoorLockListBean> doorLockList(@FieldMap Map<String, Object> params);







    @FormUrlEncoded
    @POST("mobile/updateDoorLock")
    Observable<DefaultBean> updateDoorLock(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("mobile/addDoorLock")
    Observable<DefaultBean> addDoorLock(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("mobile/deleteDoorLock")
    Observable<DefaultBean> deleteDoorLock(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("mobile/getCtrInfo")
    Observable<MagnetismBean> getCtrInfo(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("mobile/remoteDoorOpening")
    Observable<DefaultBean> remoteDoorOpening(@FieldMap Map<String, Object> params);

}
