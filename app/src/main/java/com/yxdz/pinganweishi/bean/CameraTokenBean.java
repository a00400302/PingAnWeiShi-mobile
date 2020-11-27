package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

public class CameraTokenBean extends YxdzInfo {

    /**
     * code : 0
     * data : {"accessToken":"at.bhvekk2e1e6uu53f8dc3scwn7slxj1dl-837yxrpqf7-14rb50l-ppjcbnrpi"}
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
         * accessToken : at.bhvekk2e1e6uu53f8dc3scwn7slxj1dl-837yxrpqf7-14rb50l-ppjcbnrpi
         */

        private String accessToken;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}
