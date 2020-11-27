package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 巡检设备列表
 */
public class InspectionEquipmentBean extends YxdzInfo implements Serializable {


    /**
     * code : 0
     * data : {"listFirefightingEquipmenForMoble":[{"id":48,"createTime":"2019-05-30 14:34:02","updateTime":"2019-05-30 14:34:02","createBy":null,"updateBy":null,"type":2,"areaId":null,"qrCode":"739807f487a147ed8121cb71e81fb2aa","addTime":"2019-05-30 14:30:49","equipmentName":"test","equipmentPic":null,"isEnable":1,"isWork":0,"isQualified":1,"location":"test","placeId":94,"sn":null,"warning":null,"warningNum":0,"eqType":1,"remark":"test","placeName":null}]}
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
        private List<ListFirefightingEquipmenForMobleBean> listFirefightingEquipmenForMoble;

        public List<ListFirefightingEquipmenForMobleBean> getListFirefightingEquipmenForMoble() {
            return listFirefightingEquipmenForMoble;
        }

        public void setListFirefightingEquipmenForMoble(List<ListFirefightingEquipmenForMobleBean> listFirefightingEquipmenForMoble) {
            this.listFirefightingEquipmenForMoble = listFirefightingEquipmenForMoble;
        }


    }

    public static class ListFirefightingEquipmenForMobleBean  implements  Serializable{
        /**
         * id : 48
         * createTime : 2019-05-30 14:34:02
         * updateTime : 2019-05-30 14:34:02
         * createBy : null
         * updateBy : null
         * type : 2
         * areaId : null
         * qrCode : 739807f487a147ed8121cb71e81fb2aa
         * addTime : 2019-05-30 14:30:49
         * equipmentName : test
         * equipmentPic : null
         * isEnable : 1
         * isWork : 0
         * isQualified : 1
         * location : test
         * placeId : 94
         * sn : null
         * warning : null
         * warningNum : 0
         * eqType : 1
         * remark : test
         * placeName : null
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
        private int isQualified;
        private String location;
        private int placeId;
        private Object sn;
        private Object warning;
        private int warningNum;
        private int eqType;
        private String remark;
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

        public Object getSn() {
            return sn;
        }

        public void setSn(Object sn) {
            this.sn = sn;
        }

        public Object getWarning() {
            return warning;
        }

        public void setWarning(Object warning) {
            this.warning = warning;
        }

        public int getWarningNum() {
            return warningNum;
        }

        public void setWarningNum(int warningNum) {
            this.warningNum = warningNum;
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

        public Object getPlaceName() {
            return placeName;
        }

        public void setPlaceName(Object placeName) {
            this.placeName = placeName;
        }
    }
}
