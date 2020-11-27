package com.yxdz.pinganweishi.bean;


import com.yxdz.http.bean.YxdzInfo;

/**
 * 登录
 */
public class LoginBean extends YxdzInfo{


    /**
     * code : 0
     * data : {"model":{"id":85,"account":"13229852571","name":"test","pushToken":null,"sex":null,"userPic":null,"userType":100},"token":"807314f8-ffad-4628-8b72-3191f526d323"}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * model : {"id":85,"account":"13229852571","name":"test","pushToken":null,"sex":null,"userPic":null,"userType":100}
         * token : 807314f8-ffad-4628-8b72-3191f526d323
         */

        private UserInfoBean model;
        private String token;

        public UserInfoBean getModel() {
            return model;
        }

        public void setModel(UserInfoBean model) {
            this.model = model;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    }
}
