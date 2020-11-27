package com.yxdz.pinganweishi.bean;

import java.io.Serializable;

public class UserInfoBean implements Serializable{
    /**
     * id : 85
     * account : 13229852571
     * name : test
     * pushToken : null
     * sex : null
     * userPic : null
     * userType : 100
     */

    private int id;
    private String account;
    private String name;
    private String pushToken;
    private String sex;
    private String userPic;
    private int userType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    private String token;

    //////////////////////////////////////////
    private static UserInfoBean userInfoBean;

    private UserInfoBean() {
    }

    public static UserInfoBean getInstance() {
        if (userInfoBean == null) {
            userInfoBean = new UserInfoBean();
        }
        return userInfoBean;
    }

    /**
     * 注意没有存储密码和pushtoken，为了避免数据不统一，将其两个存储到PrefUtil中
     *
     * @param userInfoBean
     */
    public void saveUserInfo(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

    public void clearUserInfo() {
        userInfoBean.setName("");
        userInfoBean.setAccount("");
        userInfoBean.setSex("");
        userInfoBean.setId(0);
        userInfoBean.setUserPic("");
        userInfoBean.setToken("");

    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
