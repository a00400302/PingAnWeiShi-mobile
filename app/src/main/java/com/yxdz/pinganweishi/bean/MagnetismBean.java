package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

public class MagnetismBean extends YxdzInfo {

    /**
     * code : 0
     * data : {"lockList":{"deviceType":0,"linkId":"消防1","inBlePower":null,"name":"门禁1","outBlePower":null,"start":"","outBleName":"","end":"","id":1,"ctrNo":1,"inBleName":""}}
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
         * lockList : {"deviceType":0,"linkId":"消防1","inBlePower":null,"name":"门禁1","outBlePower":null,"start":"","outBleName":"","end":"","id":1,"ctrNo":1,"inBleName":""}
         */

        private LockListBean lockList;

        public LockListBean getLockList() {
            return lockList;
        }

        public void setLockList(LockListBean lockList) {
            this.lockList = lockList;
        }

        public static class LockListBean {
            /**
             * deviceType : 0
             * linkId : 消防1
             * inBlePower : null
             * name : 门禁1
             * outBlePower : null
             * start :
             * outBleName :
             * end :
             * id : 1
             * ctrNo : 1
             * inBleName :
             */

            private int deviceType;
            private String linkId;
            private Object inBlePower;
            private String name;
            private Object outBlePower;
            private String start;
            private String outBleName;
            private String end;
            private int id;
            private int ctrNo;
            private String inBleName;

            public int getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(int deviceType) {
                this.deviceType = deviceType;
            }

            public String getLinkId() {
                return linkId;
            }

            public void setLinkId(String linkId) {
                this.linkId = linkId;
            }

            public Object getInBlePower() {
                return inBlePower;
            }

            public void setInBlePower(Object inBlePower) {
                this.inBlePower = inBlePower;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getOutBlePower() {
                return outBlePower;
            }

            public void setOutBlePower(Object outBlePower) {
                this.outBlePower = outBlePower;
            }

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getOutBleName() {
                return outBleName;
            }

            public void setOutBleName(String outBleName) {
                this.outBleName = outBleName;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCtrNo() {
                return ctrNo;
            }

            public void setCtrNo(int ctrNo) {
                this.ctrNo = ctrNo;
            }

            public String getInBleName() {
                return inBleName;
            }

            public void setInBleName(String inBleName) {
                this.inBleName = inBleName;
            }
        }
    }
}
