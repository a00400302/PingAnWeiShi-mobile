package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

public class DefaultBean extends YxdzInfo {


    /**
     * code : 0
     * data : 保存成功！
     */

    private int code;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
