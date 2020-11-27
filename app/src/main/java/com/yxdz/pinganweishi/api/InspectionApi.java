package com.yxdz.pinganweishi.api;


import com.yxdz.http.bean.YxdzInfo;

import java.util.List;
import java.util.Map;

import com.yxdz.pinganweishi.bean.ImgBean;
import com.yxdz.pinganweishi.bean.InspectionEquipmentBean;
import com.yxdz.pinganweishi.bean.InspectionRecordBean;
import com.yxdz.pinganweishi.bean.ScanBean;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 巡检
 */
public interface InspectionApi {

    /**
     * 巡检设备列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/queryListEquipmentByPlaceId")
    Observable<InspectionEquipmentBean> getInspectionDeviceList(@FieldMap Map<String, Object> params);

    /**
     * 二维码扫描
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/queryInspectionDeviceByQr")
    Observable<ScanBean> getInspectionScanDeviceList(@FieldMap Map<String, Object> params);


    /**
     * 巡检记录
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/getSixMonthRemark")
    Observable<InspectionRecordBean> getInspectionRecord(@FieldMap Map<String, Object> params);

    /**
     * 巡检报告、整改报告
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/pushCheckInform")
    Observable<YxdzInfo> getInformation(@FieldMap Map<String, Object> params);


    /**
     * 上传头像
     */
//    @FormUrlEncoded
    @Multipart
    @POST("mobile/pushCheckInform")
    Observable<YxdzInfo> getInformation(/*@PartMap Map<String,String> params,*/ @Part List<MultipartBody.Part> file);


    @Multipart
    @POST("mobile/pushCheckInform")
    Observable<YxdzInfo> getInformation(@PartMap Map<String, Object> params, @Part MultipartBody.Part file);


    @Multipart
    @POST("mobile/pushCheckInform")
    Observable<YxdzInfo> upImg(@Part("token") RequestBody funName,
                               @Part("id") RequestBody path,
                               @Part MultipartBody.Part file);

    @Multipart
    @POST("mobile/pushCheckInform")
    Observable<YxdzInfo> upImg(@PartMap Map<String, String> params, @Part List<MultipartBody.Part> file);

    @Multipart
    @POST("mobile/pushCheckInform")
    Observable<YxdzInfo> upLoadImage(@Query("token") String userId, @Query("id") String id, @Part MultipartBody.Part file);



    @Multipart
    @POST("api/file/image")
    Observable<ImgBean> upLoadImage(@Part List<MultipartBody.Part> file);





}
