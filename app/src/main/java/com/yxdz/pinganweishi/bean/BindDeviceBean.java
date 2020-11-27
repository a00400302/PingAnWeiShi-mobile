package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

import java.util.List;

public class BindDeviceBean extends YxdzInfo {
    /**
     * code : 0
     * data : {"deviceBindList":[{"id":77,"sn":"NS545566532224566","equipmentName":"烟感 1","checked":0},{"id":78,"sn":"NS545566532224523","equipmentName":"烟感 2","checked":1}]}
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
        private List<DeviceBindListBean> deviceBindList;

        public List<DeviceBindListBean> getDeviceBindList() {
            return deviceBindList;
        }

        public void setDeviceBindList(List<DeviceBindListBean> deviceBindList) {
            this.deviceBindList = deviceBindList;
        }

        public static class DeviceBindListBean {
            /**
             * id : 77
             * sn : NS545566532224566
             * equipmentName : 烟感 1
             * checked : 0
             */

            private int id;
            private String sn;
            private String equipmentName;
            private int checked;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public String getEquipmentName() {
                return equipmentName;
            }

            public void setEquipmentName(String equipmentName) {
                this.equipmentName = equipmentName;
            }

            public int getChecked() {
                return checked;
            }

            public void setChecked(int checked) {
                this.checked = checked;
            }
        }
    }
}
