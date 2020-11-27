package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

import java.util.List;

public class CameraListBean  extends YxdzInfo {

    /**
     * code : 0
     * data : {"cameraList":[{"id":16,"createTime":"2019-08-19 17:16:19","updateTime":"2019-08-19 17:16:19","deviceSerial":"C613443","validateCode":"SDRYH","cameraName":"摄像头 1","placeId":61,"picUrl":null,"status":1}]}
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
        private List<CameraBean> cameraList;

        public List<CameraBean> getCameraList() {
            return cameraList;
        }

        public void setCameraList(List<CameraBean> cameraList) {
            this.cameraList = cameraList;
        }

        public static class CameraBean {
            /**
             * id : 16
             * createTime : 2019-08-19 17:16:19
             * updateTime : 2019-08-19 17:16:19
             * deviceSerial : C613443
             * validateCode : SDRYH
             * cameraName : 摄像头 1
             * placeId : 61
             * picUrl : null
             * status : 1
             */

            private int id;
            private String createTime;
            private String updateTime;
            private String deviceSerial;
            private String validateCode;
            private String cameraName;
            private int placeId;
            private Object picUrl;
            private int status;
            private String cameraImg;



            public String getCameraImg() {
                return cameraImg;
            }

            public void setCameraImg(String cameraImg) {
                this.cameraImg = cameraImg;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getDeviceSerial() {
                return deviceSerial;
            }

            public void setDeviceSerial(String deviceSerial) {
                this.deviceSerial = deviceSerial;
            }

            public String getValidateCode() {
                return validateCode;
            }

            public void setValidateCode(String validateCode) {
                this.validateCode = validateCode;
            }

            public String getCameraName() {
                return cameraName;
            }

            public void setCameraName(String cameraName) {
                this.cameraName = cameraName;
            }

            public int getPlaceId() {
                return placeId;
            }

            public void setPlaceId(int placeId) {
                this.placeId = placeId;
            }

            public Object getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(Object picUrl) {
                this.picUrl = picUrl;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
