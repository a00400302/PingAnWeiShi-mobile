package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 消防——场景
 */
public class FirePlaceBean  extends YxdzInfo implements Serializable{


    /**
     * code : 0
     * data : {"listPlace":[{"createType":null,"id":58,"isEnable":0,"lat":"22.530492","lng":"113.411195","mapAddress":"kbzijsib","administrativeRegions":null,"placeAddress":null,"placeName":"kbksbibs","placeType":10,"placeimgurl":null,"remark":null,"status":1,"charge":"test","chargePhone":"13229852571"},{"createType":null,"id":59,"isEnable":0,"lat":"22.530463","lng":"113.411244","mapAddress":"qweerr","administrativeRegions":null,"placeAddress":null,"placeName":"test","placeType":1,"placeimgurl":null,"remark":null,"status":1,"charge":"test","chargePhone":"13229852571"},{"createType":null,"id":60,"isEnable":0,"lat":"22.530469","lng":"113.411315","mapAddress":"test","administrativeRegions":null,"placeAddress":null,"placeName":"test","placeType":1,"placeimgurl":null,"remark":null,"status":1,"charge":"test","chargePhone":"13229852571"},{"createType":null,"id":61,"isEnable":0,"lat":"22.530505","lng":"113.411216","mapAddress":"test","administrativeRegions":null,"placeAddress":null,"placeName":"test","placeType":1,"placeimgurl":null,"remark":null,"status":1,"charge":"test","chargePhone":"13229852571"}]}
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

    }
    public static class ListPlaceBean  implements  Serializable{

        /**
         * createType : null
         * id : 58
         * isEnable : 0
         * lat : 22.530492
         * lng : 113.411195
         * mapAddress : kbzijsib
         * administrativeRegions : null
         * placeAddress : null
         * placeName : kbksbibs
         * placeType : 10
         * placeimgurl : null
         * remark : null
         * status : 1
         * charge : test
         * chargePhone : 13229852571
         */

        private int createType;
        private int id;
        private int isEnable;
        private String lat;
        private String lng;
        private String mapAddress;
        private String administrativeRegions;
        private String placeAddress;
        private String placeName;
        private int placeType;
        private String placeimgurl;
        private String remark;
        private int status;
        private String charge;
        private String chargePhone;
        private  int smsSwitch;

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

        private  int callSwitch;

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

        public String getMapAddress() {
            return mapAddress;
        }

        public void setMapAddress(String mapAddress) {
            this.mapAddress = mapAddress;
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

        public String getPlaceimgurl() {
            return placeimgurl;
        }

        public void setPlaceimgurl(String placeimgurl) {
            this.placeimgurl = placeimgurl;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
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
    }
}
