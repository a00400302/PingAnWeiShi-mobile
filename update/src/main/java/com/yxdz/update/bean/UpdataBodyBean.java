package com.yxdz.update.bean;

/**
 * Created by haung on 2018/6/1.
 */
public class UpdataBodyBean {
    private String bodyId;//更新ID
    private String bodyIntroduct;//更新描述

    public String getBodyId() {
        return bodyId;
    }

    public void setBodyId(String bodyId) {
        this.bodyId = bodyId;
    }

    public String getBodyIntroduct() {
        return bodyIntroduct;
    }

    public void setBodyIntroduct(String bodyIntroduct) {
        this.bodyIntroduct = bodyIntroduct;
    }

    @Override
    public String toString() {
        return "{" +
                "\"bodyId\":\"" + bodyId +
                "\", \"bodyIntroduct\":\"" + bodyIntroduct +
                "\"}";
    }
}
