package com.yxdz.pinganweishi.bean;

import java.util.List;

public class PlaceBean {


    /**
     * code : 0
     * data : {"listPlace":[{"createType":3,"id":1740,"isEnable":0,"lat":"38.851999312371","lng":"105.72899983067","administrativeRegions":"张家港市","placeAddress":"阿拉善盟","placeName":"sadf","placeType":1,"placeimgurl":null,"remark":null,"status":0,"charge":"tset","chargePhone":"13715675263","smsSwitch":1,"callSwitch":1},{"createType":2,"id":1741,"isEnable":0,"lat":"22.5317","lng":"113.4110","administrativeRegions":"张家港市","placeAddress":"中山市富湾南路与富湾东路交叉路口往西南约100米(民科","placeName":"jjdjd","placeType":1,"placeimgurl":"/surpass/upload//2020/11/20/1605862901753.jpg","remark":null,"status":0,"charge":"hjdd","chargePhone":"13229852571","smsSwitch":1,"callSwitch":1},{"createType":3,"id":1738,"isEnable":0,"lat":"22.5264","lng":"113.4091","administrativeRegions":"张家港市","placeAddress":"广东省中山市孙文东路573号同方教学楼","placeName":"wwgha","placeType":1,"placeimgurl":null,"remark":null,"status":0,"charge":"+$7$","chargePhone":"13715675263","smsSwitch":1,"callSwitch":1}]}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ListPlaceBean> listPlace;

        public List<ListPlaceBean> getListPlace() {
            return listPlace;
        }

        public void setListPlace(List<ListPlaceBean> listPlace) {
            this.listPlace = listPlace;
        }

        public static class ListPlaceBean {
            /**
             * createType : 3
             * id : 1740
             * isEnable : 0
             * lat : 38.851999312371
             * lng : 105.72899983067
             * administrativeRegions : 张家港市
             * placeAddress : 阿拉善盟
             * placeName : sadf
             * placeType : 1
             * placeimgurl : null
             * remark : null
             * status : 0
             * charge : tset
             * chargePhone : 13715675263
             * smsSwitch : 1
             * callSwitch : 1
             */

            private int createType;
            private int id;
            private int isEnable;
            private String lat;
            private String lng;
            private String administrativeRegions;
            private String placeAddress;
            private String placeName;
            private int placeType;
            private Object placeimgurl;
            private Object remark;
            private int status;
            private String charge;
            private String chargePhone;
            private int smsSwitch;
            private int callSwitch;

            public int getCreateType() {
                return createType;
            }

            public void setCreateType(int createType) {
                this.createType = createType;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsEnable() {
                return isEnable;
            }

            public void setIsEnable(int isEnable) {
                this.isEnable = isEnable;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getAdministrativeRegions() {
                return administrativeRegions;
            }

            public void setAdministrativeRegions(String administrativeRegions) {
                this.administrativeRegions = administrativeRegions;
            }

            public String getPlaceAddress() {
                return placeAddress;
            }

            public void setPlaceAddress(String placeAddress) {
                this.placeAddress = placeAddress;
            }

            public String getPlaceName() {
                return placeName;
            }

            public void setPlaceName(String placeName) {
                this.placeName = placeName;
            }

            public int getPlaceType() {
                return placeType;
            }

            public void setPlaceType(int placeType) {
                this.placeType = placeType;
            }

            public Object getPlaceimgurl() {
                return placeimgurl;
            }

            public void setPlaceimgurl(Object placeimgurl) {
                this.placeimgurl = placeimgurl;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCharge() {
                return charge;
            }

            public void setCharge(String charge) {
                this.charge = charge;
            }

            public String getChargePhone() {
                return chargePhone;
            }

            public void setChargePhone(String chargePhone) {
                this.chargePhone = chargePhone;
            }

            public int getSmsSwitch() {
                return smsSwitch;
            }

            public void setSmsSwitch(int smsSwitch) {
                this.smsSwitch = smsSwitch;
            }

            public int getCallSwitch() {
                return callSwitch;
            }

            public void setCallSwitch(int callSwitch) {
                this.callSwitch = callSwitch;
            }
        }
    }
}
