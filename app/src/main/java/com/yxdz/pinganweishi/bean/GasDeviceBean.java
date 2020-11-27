package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

import java.util.List;

public class GasDeviceBean extends YxdzInfo {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * alarmBeginTime : null
         * alarmCode : null
         * alarmTitle : null
         * alarmType : null
         * contactOne : 15918230912
         * contactTwo : 15918230914
         * gatewayAddress : 某某市东区西镇100号
         * gatewayArea : 026548
         * gatewayCode : 0123
         * gatewayName : 烟感
         * id : 2
         * latitude : 26.09215
         * logTime : null
         * longitude : 119.308364
         * product : 紧急求救按钮
         * sensorArea : 001-008
         * sensorCode : 0456
         * sensorName : app
         * telephoneOne : 7380296
         * telephoneThree : 7380298
         * telephoneTwo : 7380297
         * trigger : null
         * typeCode : 43
         */

        private Object alarmBeginTime;
        private Object alarmCode;
        private Object alarmTitle;
        private Object alarmType;
        private String contactOne;
        private String contactTwo;
        private String gatewayAddress;
        private String gatewayArea;
        private String gatewayCode;
        private String gatewayName;
        private int id;
        private double latitude;
        private Object logTime;
        private double longitude;
        private String product;
        private String sensorArea;
        private String sensorCode;
        private String sensorName;
        private String telephoneOne;
        private String telephoneThree;
        private String telephoneTwo;
        private Object trigger;
        private String typeCode;

        public Object getAlarmBeginTime() {
            return alarmBeginTime;
        }

        public void setAlarmBeginTime(Object alarmBeginTime) {
            this.alarmBeginTime = alarmBeginTime;
        }

        public Object getAlarmCode() {
            return alarmCode;
        }

        public void setAlarmCode(Object alarmCode) {
            this.alarmCode = alarmCode;
        }

        public Object getAlarmTitle() {
            return alarmTitle;
        }

        public void setAlarmTitle(Object alarmTitle) {
            this.alarmTitle = alarmTitle;
        }

        public Object getAlarmType() {
            return alarmType;
        }

        public void setAlarmType(Object alarmType) {
            this.alarmType = alarmType;
        }

        public String getContactOne() {
            return contactOne;
        }

        public void setContactOne(String contactOne) {
            this.contactOne = contactOne;
        }

        public String getContactTwo() {
            return contactTwo;
        }

        public void setContactTwo(String contactTwo) {
            this.contactTwo = contactTwo;
        }

        public String getGatewayAddress() {
            return gatewayAddress;
        }

        public void setGatewayAddress(String gatewayAddress) {
            this.gatewayAddress = gatewayAddress;
        }

        public String getGatewayArea() {
            return gatewayArea;
        }

        public void setGatewayArea(String gatewayArea) {
            this.gatewayArea = gatewayArea;
        }

        public String getGatewayCode() {
            return gatewayCode;
        }

        public void setGatewayCode(String gatewayCode) {
            this.gatewayCode = gatewayCode;
        }

        public String getGatewayName() {
            return gatewayName;
        }

        public void setGatewayName(String gatewayName) {
            this.gatewayName = gatewayName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public Object getLogTime() {
            return logTime;
        }

        public void setLogTime(Object logTime) {
            this.logTime = logTime;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getSensorArea() {
            return sensorArea;
        }

        public void setSensorArea(String sensorArea) {
            this.sensorArea = sensorArea;
        }

        public String getSensorCode() {
            return sensorCode;
        }

        public void setSensorCode(String sensorCode) {
            this.sensorCode = sensorCode;
        }

        public String getSensorName() {
            return sensorName;
        }

        public void setSensorName(String sensorName) {
            this.sensorName = sensorName;
        }

        public String getTelephoneOne() {
            return telephoneOne;
        }

        public void setTelephoneOne(String telephoneOne) {
            this.telephoneOne = telephoneOne;
        }

        public String getTelephoneThree() {
            return telephoneThree;
        }

        public void setTelephoneThree(String telephoneThree) {
            this.telephoneThree = telephoneThree;
        }

        public String getTelephoneTwo() {
            return telephoneTwo;
        }

        public void setTelephoneTwo(String telephoneTwo) {
            this.telephoneTwo = telephoneTwo;
        }

        public Object getTrigger() {
            return trigger;
        }

        public void setTrigger(Object trigger) {
            this.trigger = trigger;
        }

        public String getTypeCode() {
            return typeCode;
        }

        public void setTypeCode(String typeCode) {
            this.typeCode = typeCode;
        }
    }
}
