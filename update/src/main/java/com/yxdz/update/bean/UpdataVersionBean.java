package com.yxdz.update.bean;

import java.util.List;

/**
 * Created by huang on 2018/6/1.
 */
public class UpdataVersionBean {
    private String versionDate;//更新日期
    private String versionSize;//大小
    private String versionDownload;//下载链接
    private String versionCode;//版本号
    private String versionName;//版本
    private List<UpdataBodyBean> versionBody;//更新说明

    public String getVersionDate() {
        return versionDate;
    }

    public void setVersionDate(String versionDate) {
        this.versionDate = versionDate;
    }

    public String getVersionSize() {
        return versionSize;
    }

    public void setVersionSize(String versionSize) {
        this.versionSize = versionSize;
    }

    public String getVersionDownload() {
        return versionDownload;
    }

    public void setVersionDownload(String versionDownload) {
        this.versionDownload = versionDownload;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public List<UpdataBodyBean> getVersionBody() {
        return versionBody;
    }

    public void setVersionBody(List<UpdataBodyBean> versionBody) {
        this.versionBody = versionBody;
    }

    @Override
    public String toString() {
        return "{" +
                "\"versionDate\":\"" + versionDate  +
                "\", \"versionSize\":\"" + versionSize  +
                "\",\" versionDownload\":\"" + versionDownload +
                "\", \"versionCode\":\"" + versionCode +
                "\", \"versionName\":\"" + versionName +
                "\", \"versionBody\":" + versionBody +
                "}";
    }
}
