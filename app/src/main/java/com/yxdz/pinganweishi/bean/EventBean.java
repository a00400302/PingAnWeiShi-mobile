package com.yxdz.pinganweishi.bean;

/**
 * 巡检记录
 */
public class EventBean {
    /**
     * checkTime : 2018-07-24T20:11:03
     * equipmentPicError1 : 192.168.2.115:8080//surpass/equipmentPic/俊鹏2018-07-24 20-10-579.jpg
     * equipmentPicError2 :
     * equipmentPicError3 :
     * firefightingEquipmentId : 1
     * id : 14
     * isQualified : 1
     * pusher : 俊鹏
     * remakType : 0
     * remark : 测试
     */

    private boolean displayTime;//自行添加，判断是否显示时间

    private String checkTime;
    private String equipmentPicError1;
    private String equipmentPicError2;
    private String equipmentPicError3;
    private int firefightingEquipmentId;
    private int id;
    private int isQualified;
    private String pusher;
    private int remakType;
    private String remark;

    public boolean isDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(boolean displayTime) {
        this.displayTime = displayTime;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getEquipmentPicError1() {
        return equipmentPicError1;
    }

    public void setEquipmentPicError1(String equipmentPicError1) {
        this.equipmentPicError1 = equipmentPicError1;
    }

    public String getEquipmentPicError2() {
        return equipmentPicError2;
    }

    public void setEquipmentPicError2(String equipmentPicError2) {
        this.equipmentPicError2 = equipmentPicError2;
    }

    public String getEquipmentPicError3() {
        return equipmentPicError3;
    }

    public void setEquipmentPicError3(String equipmentPicError3) {
        this.equipmentPicError3 = equipmentPicError3;
    }

    public int getFirefightingEquipmentId() {
        return firefightingEquipmentId;
    }

    public void setFirefightingEquipmentId(int firefightingEquipmentId) {
        this.firefightingEquipmentId = firefightingEquipmentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsQualified() {
        return isQualified;
    }

    public void setIsQualified(int isQualified) {
        this.isQualified = isQualified;
    }

    public String getPusher() {
        return pusher;
    }

    public void setPusher(String pusher) {
        this.pusher = pusher;
    }

    public int getRemakType() {
        return remakType;
    }

    public void setRemakType(int remakType) {
        this.remakType = remakType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
