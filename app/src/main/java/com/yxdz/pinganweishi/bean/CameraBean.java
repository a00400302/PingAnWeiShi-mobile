package com.yxdz.pinganweishi.bean;

public class CameraBean {
    /**
     * data : {"picUrl":"https://fastgw-ali.ys7.com/1/capture/2020/3/20/2luxyuhdjui5s6betkmguajnk.jpg?Expires=1584780262&OSSAccessKeyId=LTAIzI38nEHqg64n&Signature=u6gVx3qUGBSbMfg6tjNs5VMzEL8%3D&bucket=ezviz-fastdfs-gateway"}
     * code : 200
     * msg : 操作成功!
     */

    private DataBean data;
    private String code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * picUrl : https://fastgw-ali.ys7.com/1/capture/2020/3/20/2luxyuhdjui5s6betkmguajnk.jpg?Expires=1584780262&OSSAccessKeyId=LTAIzI38nEHqg64n&Signature=u6gVx3qUGBSbMfg6tjNs5VMzEL8%3D&bucket=ezviz-fastdfs-gateway
         */

        private String picUrl;

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }
    }
}
