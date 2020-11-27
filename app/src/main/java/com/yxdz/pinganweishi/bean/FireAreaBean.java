package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

import java.util.List;

/**
 * 区域选择
 */
public class FireAreaBean extends YxdzInfo{

    /**
     * code : 0
     * data : {"listArea":[{"id":20,"createTime":"2019-05-29 16:16:32","updateTime":"2019-10-14 08:16:49","createBy":1,"updateBy":1,"parentId":0,"name":"广东省","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":20},{"id":21,"createTime":"2019-05-29 16:16:47","updateTime":"2019-07-31 09:28:27","createBy":1,"updateBy":1,"parentId":20,"name":"中山市","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":21},{"id":24,"createTime":"2019-07-31 09:28:36","updateTime":"2019-07-31 09:28:36","createBy":1,"updateBy":null,"parentId":20,"name":"广州市","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":24},{"id":25,"createTime":"2019-07-31 09:28:52","updateTime":"2019-07-31 09:28:52","createBy":1,"updateBy":null,"parentId":20,"name":"深圳市","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":25},{"id":26,"createTime":"2019-07-31 09:29:04","updateTime":"2019-07-31 09:29:04","createBy":1,"updateBy":null,"parentId":21,"name":"东区","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":26},{"id":27,"createTime":"2019-07-31 09:29:21","updateTime":"2019-07-31 09:29:21","createBy":1,"updateBy":null,"parentId":21,"name":"石岐区","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":27},{"id":28,"createTime":"2019-07-31 09:29:40","updateTime":"2019-07-31 09:29:40","createBy":1,"updateBy":null,"parentId":21,"name":"东风镇","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":28},{"id":29,"createTime":"2019-07-31 09:29:53","updateTime":"2019-07-31 09:29:53","createBy":1,"updateBy":null,"parentId":21,"name":"横栏镇","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":29},{"id":30,"createTime":"2019-07-31 09:30:22","updateTime":"2019-07-31 09:30:22","createBy":1,"updateBy":null,"parentId":24,"name":"天河区","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":30},{"id":31,"createTime":"2019-07-31 09:43:38","updateTime":"2019-07-31 09:43:38","createBy":1,"updateBy":null,"parentId":24,"name":"增城区","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":31},{"id":32,"createTime":"2019-08-02 15:18:28","updateTime":"2019-08-02 15:18:28","createBy":1,"updateBy":null,"parentId":21,"name":"南头镇","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":32},{"id":33,"createTime":"2019-09-03 09:27:35","updateTime":"2019-09-03 09:27:35","createBy":1,"updateBy":null,"parentId":21,"name":"测试（APP三合一）","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":33},{"id":34,"createTime":"2019-09-05 14:04:17","updateTime":"2019-09-05 14:04:17","createBy":1,"updateBy":null,"parentId":21,"name":"小榄镇","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":34},{"id":35,"createTime":"2019-11-18 16:27:58","updateTime":"2019-11-18 16:27:58","createBy":1,"updateBy":null,"parentId":34,"name":"联丰","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":35},{"id":38,"createTime":"2019-11-22 10:22:59","updateTime":"2019-11-22 10:22:59","createBy":1,"updateBy":null,"parentId":25,"name":"南山区","address":null,"sort":null,"isEnable":0,"remarks":"","delFlag":0,"parentName":null,"areaId":38}]}
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
        private List<ListAreaBean> listArea;

        public List<ListAreaBean> getListArea() {
            return listArea;
        }

        public void setListArea(List<ListAreaBean> listArea) {
            this.listArea = listArea;
        }

        public static class ListAreaBean {
            /**
             * id : 20
             * createTime : 2019-05-29 16:16:32
             * updateTime : 2019-10-14 08:16:49
             * createBy : 1
             * updateBy : 1
             * parentId : 0
             * name : 广东省
             * address : null
             * sort : null
             * isEnable : 0
             * remarks :
             * delFlag : 0
             * parentName : null
             * areaId : 20
             */

            private int id;
            private String createTime;
            private String updateTime;
            private int createBy;
            private int updateBy;
            private int parentId;
            private String name;
            private Object address;
            private Object sort;
            private int isEnable;
            private String remarks;
            private int delFlag;
            private Object parentName;
            private int areaId;

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

            public int getCreateBy() {
                return createBy;
            }

            public void setCreateBy(int createBy) {
                this.createBy = createBy;
            }

            public int getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(int updateBy) {
                this.updateBy = updateBy;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public Object getSort() {
                return sort;
            }

            public void setSort(Object sort) {
                this.sort = sort;
            }

            public int getIsEnable() {
                return isEnable;
            }

            public void setIsEnable(int isEnable) {
                this.isEnable = isEnable;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public int getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(int delFlag) {
                this.delFlag = delFlag;
            }

            public Object getParentName() {
                return parentName;
            }

            public void setParentName(Object parentName) {
                this.parentName = parentName;
            }

            public int getAreaId() {
                return areaId;
            }

            public void setAreaId(int areaId) {
                this.areaId = areaId;
            }
        }
    }
}
