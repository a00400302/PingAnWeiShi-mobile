package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

public class QrBean extends YxdzInfo {

    /**
     * code : 0
     * data : /surpass/upload//qrCode/2019/05/e5ff1e3a85714d8b886fd068f3f4f37a.jpg
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
