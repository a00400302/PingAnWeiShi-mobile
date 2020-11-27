package com.yxdz.pinganweishi.api;


import com.yxdz.http.bean.YxdzInfo;

import java.util.Map;

import com.yxdz.pinganweishi.bean.DefaultBean;
import com.yxdz.pinganweishi.bean.LoginBean;
import com.yxdz.pinganweishi.bean.UserBean;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface LoginApi {

    /**
     * 用户登录接口
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/mobileUserLogin")
    Observable<LoginBean> getAccoutLogin(@FieldMap Map<String, Object> params);

    /**
     * 用户注册接口
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
        @POST("mobile/userRegister")
    Observable<DefaultBean> getRegister(@FieldMap Map<String, Object> params);

    /**
     * 意见提交接口
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/adviceFeedback")
    Observable<YxdzInfo> getSuggesionFeedbackReq(@FieldMap Map<String, Object> params);

    /**
     * 用户修改密码接口——记得原密码(需要输入原密码)
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/updateUserPWD")
    Observable<DefaultBean> changePwd(@FieldMap Map<String, Object> params);

    /**
     * 重置密码——不记得原密码(不需要输入原密码)
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/updatePwdByForget")
    Observable<YxdzInfo> forgetPwd(@FieldMap Map<String, Object> params);




    /**
     * 注销登录
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/loginout")
    Observable<YxdzInfo> loginout(@FieldMap Map<String, Object> params);


    /**
     * 发送短信
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/getCode")
    Observable<DefaultBean> getMessageCode(@FieldMap Map<String, Object> params);




    @GET("users")
    Observable<UserBean> getUser(@Query("id") String id);


}
