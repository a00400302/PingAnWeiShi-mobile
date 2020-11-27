package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

import java.util.List;

/**
 * 巡检记录
 */
public class InspectionRecordBean extends YxdzInfo {


    /**
     * code : 0
     * data : {"listPlace":{"page":1,"pageSize":10,"count":4,"totalPage":1,"list":[{"id":73,"createTime":"2019-06-05 13:56:37","updateTime":"2019-06-05 15:05:12","createBy":null,"updateBy":null,"equipmentId":48,"name":"test","type":1,"userId":117,"handler":"12345","isQualified":1,"equipmentPic1":null,"equipmentPic2":null,"equipmentPic3":null,"remark":"不合格巡检员"},{"id":74,"createTime":"2019-06-05 14:22:28","updateTime":"2019-06-05 15:05:12","createBy":null,"updateBy":null,"equipmentId":48,"name":"test","type":1,"userId":117,"handler":"12345","isQualified":1,"equipmentPic1":null,"equipmentPic2":null,"equipmentPic3":null,"remark":"整改报告"},{"id":75,"createTime":"2019-06-05 14:25:23","updateTime":"2019-06-05 15:05:12","createBy":null,"updateBy":null,"equipmentId":48,"name":"test","type":1,"userId":117,"handler":"12345","isQualified":null,"equipmentPic1":null,"equipmentPic2":null,"equipmentPic3":null,"remark":"整改报告2"},{"id":76,"createTime":"2019-06-05 15:05:05","updateTime":"2019-06-05 15:05:12","createBy":null,"updateBy":null,"equipmentId":70,"name":"test2","type":2,"userId":126,"handler":"test","isQualified":0,"equipmentPic1":"/surpass/upload//2019/06/05/60725f8416a1edd260cd99deaa79a850_jike_349209363574398_pic.jpeg","equipmentPic2":null,"equipmentPic3":null,"remark":"ghhj"}],"limit":10,"offset":0}}
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
        /**
         * listPlace : {"page":1,"pageSize":10,"count":4,"totalPage":1,"list":[{"id":73,"createTime":"2019-06-05 13:56:37","updateTime":"2019-06-05 15:05:12","createBy":null,"updateBy":null,"equipmentId":48,"name":"test","type":1,"userId":117,"handler":"12345","isQualified":1,"equipmentPic1":null,"equipmentPic2":null,"equipmentPic3":null,"remark":"不合格巡检员"},{"id":74,"createTime":"2019-06-05 14:22:28","updateTime":"2019-06-05 15:05:12","createBy":null,"updateBy":null,"equipmentId":48,"name":"test","type":1,"userId":117,"handler":"12345","isQualified":1,"equipmentPic1":null,"equipmentPic2":null,"equipmentPic3":null,"remark":"整改报告"},{"id":75,"createTime":"2019-06-05 14:25:23","updateTime":"2019-06-05 15:05:12","createBy":null,"updateBy":null,"equipmentId":48,"name":"test","type":1,"userId":117,"handler":"12345","isQualified":null,"equipmentPic1":null,"equipmentPic2":null,"equipmentPic3":null,"remark":"整改报告2"},{"id":76,"createTime":"2019-06-05 15:05:05","updateTime":"2019-06-05 15:05:12","createBy":null,"updateBy":null,"equipmentId":70,"name":"test2","type":2,"userId":126,"handler":"test","isQualified":0,"equipmentPic1":"/surpass/upload//2019/06/05/60725f8416a1edd260cd99deaa79a850_jike_349209363574398_pic.jpeg","equipmentPic2":null,"equipmentPic3":null,"remark":"ghhj"}],"limit":10,"offset":0}
         */

        private ListPlaceBean listPlace;

        public ListPlaceBean getListPlace() {
            return listPlace;
        }

        public void setListPlace(ListPlaceBean listPlace) {
            this.listPlace = listPlace;
        }

        public static class ListPlaceBean {
            /**
             * page : 1
             * pageSize : 10
             * count : 4
             * totalPage : 1
             * list : [{"id":73,"createTime":"2019-06-05 13:56:37","updateTime":"2019-06-05 15:05:12","createBy":null,"updateBy":null,"equipmentId":48,"name":"test","type":1,"userId":117,"handler":"12345","isQualified":1,"equipmentPic1":null,"equipmentPic2":null,"equipmentPic3":null,"remark":"不合格巡检员"},{"id":74,"createTime":"2019-06-05 14:22:28","updateTime":"2019-06-05 15:05:12","createBy":null,"updateBy":null,"equipmentId":48,"name":"test","type":1,"userId":117,"handler":"12345","isQualified":1,"equipmentPic1":null,"equipmentPic2":null,"equipmentPic3":null,"remark":"整改报告"},{"id":75,"createTime":"2019-06-05 14:25:23","updateTime":"2019-06-05 15:05:12","createBy":null,"updateBy":null,"equipmentId":48,"name":"test","type":1,"userId":117,"handler":"12345","isQualified":null,"equipmentPic1":null,"equipmentPic2":null,"equipmentPic3":null,"remark":"整改报告2"},{"id":76,"createTime":"2019-06-05 15:05:05","updateTime":"2019-06-05 15:05:12","createBy":null,"updateBy":null,"equipmentId":70,"name":"test2","type":2,"userId":126,"handler":"test","isQualified":0,"equipmentPic1":"/surpass/upload//2019/06/05/60725f8416a1edd260cd99deaa79a850_jike_349209363574398_pic.jpeg","equipmentPic2":null,"equipmentPic3":null,"remark":"ghhj"}]
             * limit : 10
             * offset : 0
             */

            private int page;
            private int pageSize;
            private int count;
            private int totalPage;
            private int limit;
            private int offset;
            private List<ListBean> list;

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(int totalPage) {
                this.totalPage = totalPage;
            }

            public int getLimit() {
                return limit;
            }

            public void setLimit(int limit) {
                this.limit = limit;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }


        }
    }

    public static class ListBean {
        /**
         * id : 73
         * createTime : 2019-06-05 13:56:37
         * updateTime : 2019-06-05 15:05:12
         * createBy : null
         * updateBy : null
         * equipmentId : 48
         * name : test
         * type : 1
         * userId : 117
         * handler : 12345
         * isQualified : 1
         * equipmentPic1 : null
         * equipmentPic2 : null
         * equipmentPic3 : null
         * remark : 不合格巡检员
         */

        private int id;
        private String createTime;
        private String updateTime;
        private String createBy;
        private String updateBy;
        private int equipmentId;
        private String name;
        private int type;
        private int userId;
        private String handler;
        private int isQualified;
        private String equipmentPic1;
        private String equipmentPic2;
        private String equipmentPic3;
        private String remark;

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

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public int getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(int equipmentId) {
            this.equipmentId = equipmentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getHandler() {
            return handler;
        }

        public void setHandler(String handler) {
            this.handler = handler;
        }

        public int getIsQualified() {
            return isQualified;
        }

        public void setIsQualified(int isQualified) {
            this.isQualified = isQualified;
        }

        public String getEquipmentPic1() {
            return equipmentPic1;
        }

        public void setEquipmentPic1(String equipmentPic1) {
            this.equipmentPic1 = equipmentPic1;
        }

        public String getEquipmentPic2() {
            return equipmentPic2;
        }

        public void setEquipmentPic2(String equipmentPic2) {
            this.equipmentPic2 = equipmentPic2;
        }

        public String getEquipmentPic3() {
            return equipmentPic3;
        }

        public void setEquipmentPic3(String equipmentPic3) {
            this.equipmentPic3 = equipmentPic3;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
