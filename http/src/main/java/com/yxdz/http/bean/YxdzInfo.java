package com.yxdz.http.bean;

public class YxdzInfo {

    /**
     * retcode : 1
     * retinfo : 成功
     */

    public String retcode;
    public String retinfo;

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getRetinfo() {
        return retinfo;
    }

    public void setRetinfo(String retinfo) {
        this.retinfo = retinfo;
    }

    public boolean isYxdzCode() {
        if (retcode.equals("1")) {
            return true;
        } else {
            return false;
        }
    }
}
