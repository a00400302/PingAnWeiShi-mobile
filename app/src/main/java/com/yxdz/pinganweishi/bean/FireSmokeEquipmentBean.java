package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 设备列表
 */
public class FireSmokeEquipmentBean extends YxdzInfo {


    /**
     * code : 0
     * data : {"listSmokeEquipment":[{"id":747,"createTime":"2020-06-12 15:23:34","updateTime":"2020-06-12 15:23:34","createBy":null,"updateBy":null,"type":1,"areaId":26,"qrCode":"bdb15b5d3b82459786e7448fa256a3f7","addTime":"2019-11-22 14:53:48","equipmentName":"燃气1","equipmentPic":null,"isEnable":1,"isWork":1,"isQualified":1,"location":" 房间","placeId":208,"sn":"A0015870","warning":0,"warningNum":0,"dumpEnergy":100,"lastContact":"2020-03-27 11:47:00","eqType":27,"remark":null,"startTime":"00:00","endTime":"23:59","numForSn":"15870","startTime2":"","endTime2":"","ygSensitivity":0,"rtSensitivity":0,"firmwareId":null,"lat":null,"lng":null,"checkTime":0,"repeatTime":0,"temperature":null,"temperatureWarn":0,"checked":null,"placeName":"宇信燃气","deviceSerial":null,"validateCode":null,"cameraName":null,"username":null,"areaName":null,"devCount":0,"offlineCount":0,"warningCount":0,"downCount":0},{"id":748,"createTime":"2020-06-12 15:23:34","updateTime":"2020-06-12 15:23:34","createBy":null,"updateBy":null,"type":1,"areaId":26,"qrCode":"e169c90da41c4eacbaccf5782a9ffde7","addTime":"2019-11-22 15:07:01","equipmentName":"燃气2","equipmentPic":null,"isEnable":1,"isWork":1,"isQualified":1,"location":"房间","placeId":208,"sn":"A0008936","warning":0,"warningNum":0,"dumpEnergy":100,"lastContact":"2020-03-27 11:27:27","eqType":27,"remark":"","startTime":"00:00","endTime":"23:59","numForSn":"08936","startTime2":"","endTime2":"","ygSensitivity":0,"rtSensitivity":0,"firmwareId":null,"lat":null,"lng":null,"checkTime":0,"repeatTime":0,"temperature":null,"temperatureWarn":0,"checked":null,"placeName":"宇信燃气","deviceSerial":null,"validateCode":null,"cameraName":null,"username":null,"areaName":null,"devCount":0,"offlineCount":0,"warningCount":0,"downCount":0},{"id":750,"createTime":"2020-06-12 15:23:34","updateTime":"2020-06-12 15:23:34","createBy":null,"updateBy":null,"type":1,"areaId":null,"qrCode":"e3bcd07960e840d4af52febda8e007a5","addTime":"2019-11-25 09:20:21","equipmentName":"燃气3","equipmentPic":null,"isEnable":1,"isWork":0,"isQualified":1,"location":"房间","placeId":208,"sn":"A0015935","warning":0,"warningNum":0,"dumpEnergy":100,"lastContact":"2019-12-30 12:07:46","eqType":27,"remark":null,"startTime":"00:00","endTime":"23:59","numForSn":"15935","startTime2":null,"endTime2":null,"ygSensitivity":0,"rtSensitivity":0,"firmwareId":null,"lat":null,"lng":null,"checkTime":0,"repeatTime":0,"temperature":null,"temperatureWarn":0,"checked":null,"placeName":"宇信燃气","deviceSerial":null,"validateCode":null,"cameraName":null,"username":null,"areaName":null,"devCount":0,"offlineCount":0,"warningCount":0,"downCount":0},{"id":751,"createTime":"2020-06-12 15:23:34","updateTime":"2020-06-12 15:23:34","createBy":null,"updateBy":null,"type":1,"areaId":null,"qrCode":"b8a989159891453793ee296394045441","addTime":"2019-11-25 09:21:56","equipmentName":"燃气4","equipmentPic":null,"isEnable":1,"isWork":1,"isQualified":1,"location":"房间","placeId":208,"sn":"A0015900","warning":0,"warningNum":0,"dumpEnergy":100,"lastContact":"2020-03-27 11:21:20","eqType":27,"remark":null,"startTime":"00:00","endTime":"23:59","numForSn":"15900","startTime2":null,"endTime2":null,"ygSensitivity":0,"rtSensitivity":0,"firmwareId":null,"lat":null,"lng":null,"checkTime":0,"repeatTime":0,"temperature":null,"temperatureWarn":0,"checked":null,"placeName":"宇信燃气","deviceSerial":null,"validateCode":null,"cameraName":null,"username":null,"areaName":null,"devCount":0,"offlineCount":0,"warningCount":0,"downCount":0},{"id":1153,"createTime":"2020-06-12 15:23:34","updateTime":"2020-06-12 15:23:34","createBy":null,"updateBy":null,"type":1,"areaId":null,"qrCode":"a07249bdeee04740a11bec38fac4639c","addTime":"2019-12-09 11:35:55","equipmentName":"燃气5带电池","equipmentPic":null,"isEnable":1,"isWork":0,"isQualified":1,"location":"客厅","placeId":208,"sn":"A0018228","warning":0,"warningNum":0,"dumpEnergy":100,"lastContact":"2020-01-08 19:10:39","eqType":null,"remark":null,"startTime":"00:00","endTime":"23:59","numForSn":"18228","startTime2":null,"endTime2":null,"ygSensitivity":0,"rtSensitivity":0,"firmwareId":null,"lat":null,"lng":null,"checkTime":0,"repeatTime":0,"temperature":null,"temperatureWarn":0,"checked":null,"placeName":"宇信燃气","deviceSerial":null,"validateCode":null,"cameraName":null,"username":null,"areaName":null,"devCount":0,"offlineCount":0,"warningCount":0,"downCount":0}]}
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
        private List<ListSmokeEquipmentBean> listSmokeEquipment;

        public List<ListSmokeEquipmentBean> getListSmokeEquipment() {
            return listSmokeEquipment;
        }

        public void setListSmokeEquipment(List<ListSmokeEquipmentBean> listSmokeEquipment) {
            this.listSmokeEquipment = listSmokeEquipment;
        }


    }

    public static class ListSmokeEquipmentBean implements Serializable {
        /**
         * id : 747
         * createTime : 2020-06-12 15:23:34
         * updateTime : 2020-06-12 15:23:34
         * createBy : null
         * updateBy : null
         * type : 1
         * areaId : 26
         * qrCode : bdb15b5d3b82459786e7448fa256a3f7
         * addTime : 2019-11-22 14:53:48
         * equipmentName : 燃气1
         * equipmentPic : null
         * isEnable : 1
         * isWork : 1
         * isQualified : 1
         * location :  房间
         * placeId : 208
         * sn : A0015870
         * warning : 0
         * warningNum : 0
         * dumpEnergy : 100
         * lastContact : 2020-03-27 11:47:00
         * eqType : 27
         * remark : null
         * startTime : 00:00
         * endTime : 23:59
         * numForSn : 15870
         * startTime2 :
         * endTime2 :
         * ygSensitivity : 0
         * rtSensitivity : 0
         * firmwareId : null
         * lat : null
         * lng : null
         * checkTime : 0
         * repeatTime : 0
         * temperature : null
         * temperatureWarn : 0
         * checked : null
         * placeName : 宇信燃气
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
        private Object createBy;
        private Object updateBy;
        private int type;
        private int areaId;
        private String qrCode;
        private String addTime;
        private String equipmentName;
        private Object equipmentPic;
        private int isEnable;
        private int isWork;
        private int isQualified;
        private String location;
        private int placeId;
        private String sn;
        private int warning;
        private int warningNum;
        private String dumpEnergy;
        private String lastContact;
        private int eqType;
        private Object remark;
        private String startTime;
        private String endTime;
        private String numForSn;
        private String startTime2;
        private String endTime2;

        private Object firmwareId;
        private Object lat;
        private Object lng;


        private Object checked;
        private String placeName;
        private Object deviceSerial;
        private Object validateCode;
        private Object cameraName;
        private Object username;
        private Object areaName;
        private int devCount;
        private int offlineCount;
        private int warningCount;
        private int downCount;
        private int temperature;


        private int ygSensitivity;
        private int rtSensitivity;
        private int repeatTime;
        private int temperatureWarn;
        private int checkTime;





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

        public String getDumpEnergy() {
            return dumpEnergy;
        }

        public void setDumpEnergy(String dumpEnergy) {
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

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
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

        public Object getFirmwareId() {
            return firmwareId;
        }

        public void setFirmwareId(Object firmwareId) {
            this.firmwareId = firmwareId;
        }

        public Object getLat() {
            return lat;
        }

        public void setLat(Object lat) {
            this.lat = lat;
        }

        public Object getLng() {
            return lng;
        }

        public void setLng(Object lng) {
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

        public int getTemperature() {
            return temperature;
        }

        public void setTemperature(int temperature) {
            this.temperature = temperature;
        }

        public int getTemperatureWarn() {
            return temperatureWarn;
        }

        public void setTemperatureWarn(int temperatureWarn) {
            this.temperatureWarn = temperatureWarn;
        }

        public Object getChecked() {
            return checked;
        }

        public void setChecked(Object checked) {
            this.checked = checked;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
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

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }

        public Object getAreaName() {
            return areaName;
        }

        public void setAreaName(Object areaName) {
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
