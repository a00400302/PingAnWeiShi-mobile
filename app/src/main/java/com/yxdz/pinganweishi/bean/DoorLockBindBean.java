package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

import java.util.List;

public class DoorLockBindBean extends YxdzInfo {


    /**
     * code : 0
     * data : {"deviceBindList":[{"id":20,"createTime":"2019-09-10 16:43:05","updateTime":"2019-09-10 16:43:05","createBy":null,"updateBy":null,"type":1,"areaId":null,"qrCode":"9c663df597714e098367836f2cc31964","addTime":"2019-07-24 14:42:22","equipmentName":"烟感-","equipmentPic":null,"isEnable":1,"isWork":1,"isQualified":null,"location":"客厅2","placeId":21,"sn":"YG860618040006296","warning":0,"warningNum":0,"dumpEnergy":100,"lastContact":"2019-07-29 09:54:59","eqType":1,"remark":"","startTime":"09:17","endTime":"16:17","numForSn":"860618040006296","startTime2":"","endTime2":"","checked":0,"placeName":null,"deviceSerial":null,"validateCode":null,"cameraName":null},{"id":21,"createTime":"2019-09-10 16:43:05","updateTime":"2019-09-10 16:43:05","createBy":null,"updateBy":null,"type":1,"areaId":null,"qrCode":"c125ec075b3d445bb8819f8586f9b5a8","addTime":"2019-07-24 14:42:46","equipmentName":"人体感应","equipmentPic":null,"isEnable":1,"isWork":0,"isQualified":null,"location":"客厅","placeId":21,"sn":"RT860618040028407","warning":0,"warningNum":0,"dumpEnergy":100,"lastContact":"2019-07-29 13:45:34","eqType":2,"remark":"","startTime":"05:00","endTime":"08:00","numForSn":"860618040028407","startTime2":"08:00","endTime2":"10:00","checked":0,"placeName":null,"deviceSerial":null,"validateCode":null,"cameraName":null},{"id":22,"createTime":"2019-09-10 16:43:05","updateTime":"2019-09-10 16:43:05","createBy":null,"updateBy":null,"type":1,"areaId":null,"qrCode":"c64058ab20b44e68904a05a745486d94","addTime":"2019-07-26 14:46:27","equipmentName":"人体+烟感","equipmentPic":null,"isEnable":1,"isWork":1,"isQualified":null,"location":"客厅","placeId":21,"sn":"RY860618040005231","warning":0,"warningNum":0,"dumpEnergy":100,"lastContact":"2019-08-06 08:59:23","eqType":3,"remark":"","startTime":"00:00","endTime":"23:59","numForSn":"860618040005231","startTime2":"00:00","endTime2":"23:59","checked":0,"placeName":null,"deviceSerial":null,"validateCode":null,"cameraName":null},{"id":91,"createTime":"2019-09-10 16:43:05","updateTime":"2019-09-10 16:43:05","createBy":null,"updateBy":null,"type":1,"areaId":20,"qrCode":"fddc226e1ff64f12b795e779204903bb","addTime":"2019-09-09 16:50:25","equipmentName":"fewf","equipmentPic":null,"isEnable":1,"isWork":0,"isQualified":null,"location":"fe","placeId":21,"sn":"NS8564654","warning":null,"warningNum":0,"dumpEnergy":100,"lastContact":"2000-01-01 00:00:00","eqType":1,"remark":null,"startTime":"00:00","endTime":"23:59","numForSn":"8564654","startTime2":"","endTime2":"","checked":0,"placeName":null,"deviceSerial":null,"validateCode":null,"cameraName":null}]}
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
        private List<DeviceBindListBean> deviceBindList;

        public List<DeviceBindListBean> getDeviceBindList() {
            return deviceBindList;
        }

        public void setDeviceBindList(List<DeviceBindListBean> deviceBindList) {
            this.deviceBindList = deviceBindList;
        }
    }


    public static class DeviceBindListBean {
        /**
         * id : 20
         * createTime : 2019-09-10 16:43:05
         * updateTime : 2019-09-10 16:43:05
         * createBy : null
         * updateBy : null
         * type : 1
         * areaId : null
         * qrCode : 9c663df597714e098367836f2cc31964
         * addTime : 2019-07-24 14:42:22
         * equipmentName : 烟感-
         * equipmentPic : null
         * isEnable : 1
         * isWork : 1
         * isQualified : null
         * location : 客厅2
         * placeId : 21
         * sn : YG860618040006296
         * warning : 0
         * warningNum : 0
         * dumpEnergy : 100
         * lastContact : 2019-07-29 09:54:59
         * eqType : 1
         * remark :
         * startTime : 09:17
         * endTime : 16:17
         * numForSn : 860618040006296
         * startTime2 :
         * endTime2 :
         * checked : 0
         * placeName : null
         * deviceSerial : null
         * validateCode : null
         * cameraName : null
         */

        private int id;
        private String createTime;
        private String updateTime;
        private Object createBy;
        private Object updateBy;
        private int type;
        private Object areaId;
        private String qrCode;
        private String addTime;
        private String equipmentName;
        private Object equipmentPic;
        private int isEnable;
        private int isWork;
        private Object isQualified;
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
        private int checked;
        private Object placeName;
        private Object deviceSerial;
        private Object validateCode;
        private Object cameraName;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getAreaId() {
            return areaId;
        }

        public void setAreaId(Object areaId) {
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

        public void setEquipmentPic(Object equipmentPic) {
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

        public Object getIsQualified() {
            return isQualified;
        }

        public void setIsQualified(Object isQualified) {
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

        public int getChecked() {
            return checked;
        }

        public void setChecked(int checked) {
            this.checked = checked;
        }

        public Object getPlaceName() {
            return placeName;
        }

        public void setPlaceName(Object placeName) {
            this.placeName = placeName;
        }

        public Object getDeviceSerial() {
            return deviceSerial;
        }

        public void setDeviceSerial(Object deviceSerial) {
            this.deviceSerial = deviceSerial;
        }

        public Object getValidateCode() {
            return validateCode;
        }

        public void setValidateCode(Object validateCode) {
            this.validateCode = validateCode;
        }

        public Object getCameraName() {
            return cameraName;
        }

        public void setCameraName(Object cameraName) {
            this.cameraName = cameraName;
        }
    }
}
