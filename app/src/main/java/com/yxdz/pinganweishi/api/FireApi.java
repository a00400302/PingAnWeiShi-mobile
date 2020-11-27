package com.yxdz.pinganweishi.api;


import com.yxdz.http.bean.YxdzInfo;
import com.yxdz.pinganweishi.bean.BindDeviceBean;
import com.yxdz.pinganweishi.bean.CameraListBean;
import com.yxdz.pinganweishi.bean.CameraTokenBean;
import com.yxdz.pinganweishi.bean.ContactsBean;
import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.FireAreaBean;
import com.yxdz.pinganweishi.bean.FirePlaceBean;
import com.yxdz.pinganweishi.bean.FireSmokeEquipmentBean;
import com.yxdz.pinganweishi.bean.FireSmokeEquipmentInfoBean;
import com.yxdz.pinganweishi.bean.FireSmokeMessageBean;
import com.yxdz.pinganweishi.bean.GasDeviceBean;
import com.yxdz.pinganweishi.bean.GasDeviceInfoBean;
import com.yxdz.pinganweishi.bean.GateBean;
import com.yxdz.pinganweishi.bean.QrBean;
import com.yxdz.pinganweishi.bean.UploadImageBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * 消防
 */
public interface FireApi {

    /**
     * 请求烟感场所列表
     *
     * @param parameters
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/queryPlaceByUserId")
    Observable<FirePlaceBean> getFireControlQueryPlace(@FieldMap Map<String, Object> parameters);

    /**
     * 查看烟感设备地图信息
     *
     * @param parameters
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/queryListSmokeEquipment")
    Observable<FireSmokeEquipmentBean> getFireControlListSmokeEquipment(@FieldMap Map<String, Object> parameters);


    /**
     * 请求烟感设备详情
     *
     * @param parameters
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/fireDeviceInfo")
    Observable<FireSmokeEquipmentInfoBean> getFireControlSmokeInfo(@FieldMap Map<String, Object> parameters);

    @FormUrlEncoded
    @POST("mobile/getAccessToken")
    Observable<CameraTokenBean> getAccessToken(@FieldMap Map<String, Object> parameters);


    /**
     * 烟感情况处理
     *
     * @param parameters
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/dealSmokeEvent")
    Observable<YxdzInfo> getFireControlSmokeDeal(@FieldMap Map<String, Object> parameters);

    /**
     * 请求查询区域列表
     *
     * @param parameters
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/fireArea")
    Observable<FireAreaBean> getFireControlSmokeAreaList(@FieldMap Map<String, Object> parameters);


    /**
     * 请求报警历史信息
     *
     * @param parameters
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/fireDeviceMsg")
    Observable<FireSmokeMessageBean> getFireControlSmokeMessage(@FieldMap Map<String, Object> parameters);

    /**
     * 上传头像
     */
    @Multipart
    @POST("mobile/mobileUpload")
    Observable<UploadImageBean> uploadPhoto(@Part List<MultipartBody.Part> file);

    /**
     * 修改用户头像url路径
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/updateUserPortrait")
    Observable<YxdzInfo> uploadPhotoUrl(@FieldMap Map<String, Object> params);


    /**
     * 添加场所
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/addFirePlace")
    Observable<DefaultBean> uploadPlace(@FieldMap Map<String, Object> params);


    /**
     * 二维码添加场所
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/addFirePlaceQr")
    Observable<DefaultBean> uploadQrPlace(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("mobile/updatePlaceUser")
    Observable<DefaultBean> updatePlaceUser(@FieldMap Map<String, Object> params);

    /**
     * 二维码添加场所
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/getQrCode")
    Observable<QrBean> getQrCode(@FieldMap Map<String, Object> params);


    /**
     * 删除场所
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/deleteFirePlace")
    Observable<DefaultBean> deletePlace(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("mobile/delFireDevice")
    Observable<DefaultBean> deleteDevice(@FieldMap Map<String, Object> params);


    /**
     * 查询场所
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/queryContact")
    Observable<ContactsBean> queryContact(@FieldMap Map<String, Object> params);


    /**
     * 操作联系人
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/operatingContact")
    Observable<DefaultBean> operatingContact(@FieldMap Map<String, Object> params);


    /**
     * 添加设备
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/addFireDevice")
    Observable<DefaultBean> addFireDevice(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("mobile/pushManager")
    Observable<DefaultBean> setPushSetting(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("mobile/updateFireDevice")
    Observable<DefaultBean> setDevice(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("mobile/cameraList")
    Observable<CameraListBean> getCameraList(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("mobile/deleteCamera")
    Observable<DefaultBean> deleteCamera(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("mobile/addCamera")
    Observable<DefaultBean> addCamera(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("mobile/deviceBindList")
    Observable<BindDeviceBean> deviceBindList(@FieldMap Map<String, Object> params);


    //    设备阀事件
    @FormUrlEncoded
    @POST("mobile/getRecordBySn")
    Observable<GateBean> getRecordBySn(@FieldMap Map<String, Object> params);


    //    远程阀关闭
    @FormUrlEncoded
    @POST("mobile/remoteShutDown")
    Observable<DefaultBean> remoteShutDown(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("mobile/updateCamera")
    Observable<DefaultBean> updateCamera(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("mobile/updatePlace")
    Observable<DefaultBean> updatePlace(@FieldMap Map<String, Object> params);


    @Multipart
    @POST("api/file/photo")
    Observable<DefaultBean> photo(@Part List<MultipartBody.Part> file);

    Observable<GasDeviceInfoBean> getGasDeviceInfo(@FieldMap Map<String, Object> params);

    Observable<GasDeviceBean> getGasDevice(@FieldMap Map<String, Object> params);
}