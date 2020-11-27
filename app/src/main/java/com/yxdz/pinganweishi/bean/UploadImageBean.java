package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

public class UploadImageBean extends YxdzInfo {

    /**
     * 服务器返回的图片url路径
     */
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
