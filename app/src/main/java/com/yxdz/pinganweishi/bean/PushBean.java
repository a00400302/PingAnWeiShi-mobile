package com.yxdz.pinganweishi.bean;


public class PushBean {


    /**
     * placeId : 10
     * dateTime : 2018-06-03 16:36
     * ctrName : dsds
     * badge : 1
     * type : 9
     */

    private String placeId;
    private String dateTime;
    private String ctrName;
    private String badge;
    private String type;

    //华为推送特有
    private String msgId;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCtrName() {
        return ctrName;
    }

    public void setCtrName(String ctrName) {
        this.ctrName = ctrName;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
