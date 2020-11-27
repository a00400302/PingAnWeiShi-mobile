package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

/**
 * 设备详情
 */
public class FireSmokeEquipmentInfoBean extends YxdzInfo{


    /**
     * code : 0
     * data : {"id":2404,"createTime":"2020-06-03 15:36:22","updateTime":"2020-06-03 15:36:22","createBy":null,"updateBy":null,"type":1,"areaId":33,"qrCode":"bef8d49b026c4736b09f950d6d67df18","addTime":"2020-05-25 11:10:11","equipmentName":"ttt","equipmentPic":null,"isEnable":1,"isWork":1,"isQualified":1,"location":"f","placeId":1331,"sn":"862177041120141","warning":2,"warningNum":0,"dumpEnergy":95,"lastContact":"2020-05-26 09:03:16","eqType":3,"remark":"","startTime":"00:00:00","endTime":"23:59:59","numForSn":"862177041120141","startTime2":"","endTime2":"","ygSensitivity":10,"rtSensitivity":0,"firmwareId":15,"lat":"22.8602777777778","lng":"113.6251277777778","checkTime":2,"repeatTime":1,"temperature":null,"checked":null,"placeName":"test","deviceSerial":null,"validateCode":null,"cameraName":null,"username":null,"areaName":null,"devCount":0,"offlineCount":0,"warningCount":0,"downCount":0}
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
         * id : 2404
         * createTime : 2020-06-03 15:36:22
         * updateTime : 2020-06-03 15:36:22
         * createBy : null
         * updateBy : null
         * type : 1
         * areaId : 33
         * qrCode : bef8d49b026c4736b09f950d6d67df18
         * addTime : 2020-05-25 11:10:11
         * equipmentName : ttt
         * equipmentPic : null
         * isEnable : 1
         * isWork : 1
         * isQualified : 1
         * location : f
         * placeId : 1331
         * sn : 862177041120141
         * warning : 2
         * warningNum : 0
         * dumpEnergy : 95
         * lastContact : 2020-05-26 09:03:16
         * eqType : 3
         * remark :
         * startTime : 00:00:00
         * endTime : 23:59:59
         * numForSn : 862177041120141
         * startTime2 :
         * endTime2 :
         * ygSensitivity : 10
         * rtSensitivity : 0
         * firmwareId : 15
         * lat : 22.8602777777778
         * lng : 113.6251277777778
         * checkTime : 2
         * repeatTime : 1
         * temperature : null
         * checked : null
         * placeName : test
         * deviceSerial : null
         * validateCode : null
         * cameraName : null
         * username : null
         * areaName : null
         * devCount : 0
         * offlineCount : 0
         * warningCount : 0
         * downCount : 0
         */

        private int id;
        private String createTime;
        private String updateTime;
        private String createBy;
        private String updateBy;
        private int type;
        private int areaId;
        private String qrCode;
        private String addTime;
        private String equipmentName;
        private String equipmentPic;
        private int isEnable;
        private int isWork;
        private int isQualified;
        private String location;
        private int placeId;
        private String sn;
        private int warning;
        private int warningNum;
        private int dumpEnergy;
        private String lastContact;
        private int eqType;
        private String remark;
        private String startTime;
        private String endTime;
        private String numForSn;
        private String startTime2;
        private String endTime2;
        private int ygSensitivity;
        private int rtSensitivity;
        private int firmwareId;
        private String lat;
        private String lng;
        private int checkTime;
        private int repeatTime;
        private String temperature;
        private String checked;
        private String placeName;
        private String deviceSerial;
        private String validateCode;
        private String cameraName;
        private String username;
        private String areaName;
        private int devCount;
        private int offlineCount;
        private int warningCount;
        private int downCount;

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

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getEquipmentName() {
            return equipmentName;
        }

        public void setEquipmentName(String equipmentName) {
            this.equipmentName = equipmentName;
        }

        public Object getEquipmentPic() {
            return equipmentPic;
        }

        public void setEquipmentPic(String equipmentPic) {
            this.equipmentPic = equipmentPic;
        }

        public int getIsEnable() {
            return isEnable;
        }

        public void setIsEnable(int isEnable) {
            this.isEnable = isEnable;
        }

        public int getIsWork() {
            return isWork;
        }

        public void setIsWork(int isWork) {
            this.isWork = isWork;
        }

        public int getIsQualified() {
            return isQualified;
        }

        public void setIsQualified(int isQualified) {
            this.isQualified = isQualified;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getPlaceId() {
            return placeId;
        }

        public void setPlaceId(int placeId) {
            this.placeId = placeId;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public int getWarning() {
            return warning;
        }

        public void setWarning(int warning) {
            this.warning = warning;
        }

        public int getWarningNum() {
            return warningNum;
        }

        public void setWarningNum(int warningNum) {
            this.warningNum = warningNum;
        }

        public int getDumpEnergy() {
            return dumpEnergy;
        }

        public void setDumpEnergy(int dumpEnergy) {
            this.dumpEnergy = dumpEnergy;
        }

        public String getLastContact() {
            return lastContact;
        }

        public void setLastContact(String lastContact) {
            this.lastContact = lastContact;
        }

        public int getEqType() {
            return eqType;
        }

        public void setEqType(int eqType) {
            this.eqType = eqType;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getNumForSn() {
            return numForSn;
        }

        public void setNumForSn(String numForSn) {
            this.numForSn = numForSn;
        }

        public String getStartTime2() {
            return startTime2;
        }

        public void setStartTime2(String startTime2) {
            this.startTime2 = startTime2;
        }

        public String getEndTime2() {
            return endTime2;
        }

        public void setEndTime2(String endTime2) {
            this.endTime2 = endTime2;
        }

        public int getYgSensitivity() {
            return ygSensitivity;
        }

        public void setYgSensitivity(int ygSensitivity) {
            this.ygSensitivity = ygSensitivity;
        }

        public int getRtSensitivity() {
            return rtSensitivity;
        }

        public void setRtSensitivity(int rtSensitivity) {
            this.rtSensitivity = rtSensitivity;
        }

        public int getFirmwareId() {
            return firmwareId;
        }

        public void setFirmwareId(int firmwareId) {
            this.firmwareId = firmwareId;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public int getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(int checkTime) {
            this.checkTime = checkTime;
        }

        public int getRepeatTime() {
            return repeatTime;
        }

        public void setRepeatTime(int repeatTime) {
            this.repeatTime = repeatTime;
        }

        public Object getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public Object getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getDevCount() {
            return devCount;
        }

        public void setDevCount(int devCount) {
            this.devCount = devCount;
        }

        public int getOfflineCount() {
            return offlineCount;
        }

        public void setOfflineCount(int offlineCount) {
            this.offlineCount = offlineCount;
        }

        public int getWarningCount() {
            return warningCount;
        }

        public void setWarningCount(int warningCount) {
            this.warningCount = warningCount;
        }

        public int getDownCount() {
            return downCount;
        }

        public void setDownCount(int downCount) {
            this.downCount = downCount;
        }
    }
}
