package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

public class DoorLockInfoBean extends YxdzInfo {


    /**
     * code : 0
     * data : {"id":5,"createTime":"2019-09-10 16:43:32","updateTime":"2019-09-10 16:43:32","createBy":null,"updateBy":null,"doorLockName":"test门锁2","doorLockSn":"543246546465","placeId":21,"status":0,"placeName":null}
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
         * id : 5
         * createTime : 2019-09-10 16:43:32
         * updateTime : 2019-09-10 16:43:32
         * createBy : null
         * updateBy : null
         * doorLockName : test门锁2
         * doorLockSn : 543246546465
         * placeId : 21
         * status : 0
         * placeName : null
         */

        private int id;
        private String createTime;
        private String updateTime;
        private Object createBy;
        private Object updateBy;
        private String doorLockName;
        private String doorLockSn;
        private int placeId;
        private int status;
        private Object placeName;

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

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public String getDoorLockName() {
            return doorLockName;
        }

        public void setDoorLockName(String doorLockName) {
            this.doorLockName = doorLockName;
        }

        public String getDoorLockSn() {
            return doorLockSn;
        }

        public void setDoorLockSn(String doorLockSn) {
            this.doorLockSn = doorLockSn;
        }

        public int getPlaceId() {
            return placeId;
        }

        public void setPlaceId(int placeId) {
            this.placeId = placeId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getPlaceName() {
            return placeName;
        }

        public void setPlaceName(Object placeName) {
            this.placeName = placeName;
        }
    }
}
