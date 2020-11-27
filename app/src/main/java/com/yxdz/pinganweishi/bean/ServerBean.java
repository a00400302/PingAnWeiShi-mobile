package com.yxdz.pinganweishi.bean;

import java.util.List;

public class ServerBean {


    /**
     * code : 0
     * data : [{"id":1,"createTime":"2020-11-03 12:02:03","updateTime":"2020-11-03 12:02:03","createBy":null,"updateBy":null,"serverName":"瓶安卫士","ipPort":"139.159.230.78","serverUrl":"https://zsyuxinkeji.com"},{"id":2,"createTime":"2020-11-03 12:02:03","updateTime":"2020-11-03 12:02:03","createBy":null,"updateBy":null,"serverName":"瓶安苏州","ipPort":"172.16.204.83","serverUrl":"https://xxxxxx.com"}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * createTime : 2020-11-03 12:02:03
         * updateTime : 2020-11-03 12:02:03
         * createBy : null
         * updateBy : null
         * serverName : 瓶安卫士
         * ipPort : 139.159.230.78
         * serverUrl : https://zsyuxinkeji.com
         */

        private int id;
        private String createTime;
        private String updateTime;
        private Object createBy;
        private Object updateBy;
        private String serverName;
        private String ipPort;
        private String serverUrl;

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

        public String getServerName() {
            return serverName;
        }

        public void setServerName(String serverName) {
            this.serverName = serverName;
        }

        public String getIpPort() {
            return ipPort;
        }

        public void setIpPort(String ipPort) {
            this.ipPort = ipPort;
        }

        public String getServerUrl() {
            return serverUrl;
        }

        public void setServerUrl(String serverUrl) {
            this.serverUrl = serverUrl;
        }
    }
}
