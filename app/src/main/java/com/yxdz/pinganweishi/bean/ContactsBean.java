package com.yxdz.pinganweishi.bean;



import com.yxdz.http.bean.YxdzInfo;

import java.util.List;

public class ContactsBean  extends YxdzInfo  {


    /**
     * code : 0
     * data : [{"id":96,"createTime":"2019-05-29 09:16:55","updateTime":"2019-05-29 09:16:55","createBy":null,"updateBy":null,"username":"13148539806","password":"0541d4e2af2792c2976286eee4ba4f9d","salt":"a57055378942cc27739272313b520552","nickname":"测试用户","idCard":null,"role":100,"address":null,"headImgUrl":null,"phone":"13148539806","telephone":null,"email":null,"birthday":null,"sex":null,"status":1,"pushId":null,"areaId":0,"delFlag":0,"acceptType":null,"invitationCode":null,"remark":null,"type":1,"roleName":null,"areaName":null,"childArea":null,"placeId":null}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 96
         * createTime : 2019-05-29 09:16:55
         * updateTime : 2019-05-29 09:16:55
         * createBy : null
         * updateBy : null
         * username : 13148539806
         * password : 0541d4e2af2792c2976286eee4ba4f9d
         * salt : a57055378942cc27739272313b520552
         * nickname : 测试用户
         * idCard : null
         * role : 100
         * address : null
         * headImgUrl : null
         * phone : 13148539806
         * telephone : null
         * email : null
         * birthday : null
         * sex : null
         * status : 1
         * pushId : null
         * areaId : 0
         * delFlag : 0
         * acceptType : null
         * invitationCode : null
         * remark : null
         * type : 1
         * roleName : null
         * areaName : null
         * childArea : null
         * placeId : null
         */

        private int id;
        private String createTime;
        private String updateTime;
        private Object createBy;
        private Object updateBy;
        private String username;
        private String password;
        private String salt;
        private String nickname;
        private Object idCard;
        private int role;
        private Object address;
        private Object headImgUrl;
        private String phone;
        private Object telephone;
        private Object email;
        private Object birthday;
        private Object sex;
        private int status;
        private Object pushId;
        private int areaId;
        private int delFlag;
        private Object acceptType;
        private Object invitationCode;
        private Object remark;
        private int type;
        private Object roleName;
        private Object areaName;
        private Object childArea;
        private Object placeId;

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getIdCard() {
            return idCard;
        }

        public void setIdCard(Object idCard) {
            this.idCard = idCard;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(Object headImgUrl) {
            this.headImgUrl = headImgUrl;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getTelephone() {
            return telephone;
        }

        public void setTelephone(Object telephone) {
            this.telephone = telephone;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getPushId() {
            return pushId;
        }

        public void setPushId(Object pushId) {
            this.pushId = pushId;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public Object getAcceptType() {
            return acceptType;
        }

        public void setAcceptType(Object acceptType) {
            this.acceptType = acceptType;
        }

        public Object getInvitationCode() {
            return invitationCode;
        }

        public void setInvitationCode(Object invitationCode) {
            this.invitationCode = invitationCode;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getRoleName() {
            return roleName;
        }

        public void setRoleName(Object roleName) {
            this.roleName = roleName;
        }

        public Object getAreaName() {
            return areaName;
        }

        public void setAreaName(Object areaName) {
            this.areaName = areaName;
        }

        public Object getChildArea() {
            return childArea;
        }

        public void setChildArea(Object childArea) {
            this.childArea = childArea;
        }

        public Object getPlaceId() {
            return placeId;
        }

        public void setPlaceId(Object placeId) {
            this.placeId = placeId;
        }
    }
}
