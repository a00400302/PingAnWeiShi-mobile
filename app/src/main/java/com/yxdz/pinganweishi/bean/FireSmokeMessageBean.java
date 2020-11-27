package com.yxdz.pinganweishi.bean;

import com.yxdz.http.bean.YxdzInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: FireSmokeMessageBean
 * @Desription:
 * @author: Dreamcoding
 * @date: 2018/9/3
 */
public class FireSmokeMessageBean extends YxdzInfo implements Serializable{


    /**
     * code : 0
     * data : {"PAGEModel":{"page":1,"pageSize":10,"count":0,"totalPage":1,"list":[{"id":4016,"createTime":"2019-10-11 11:31:31","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047098615","name":"人体探测器","location":"房间","type":2,"state":2,"dealTime":"2019-10-11 12:09:50","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4015,"createTime":"2019-10-11 10:01:33","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047098615","name":"人体探测器","location":"房间","type":2,"state":2,"dealTime":"2019-10-11 11:17:44","handler":"振辉科技","result":"1","delFlag":0,"placeName":"振辉科技"},{"id":4014,"createTime":"2019-10-11 10:00:40","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":8,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4013,"createTime":"2019-10-11 09:58:37","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":8,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4012,"createTime":"2019-10-11 09:56:37","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":8,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4011,"createTime":"2019-10-11 09:54:36","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":8,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4010,"createTime":"2019-10-11 09:52:35","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":4,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4009,"createTime":"2019-10-11 09:50:36","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":2,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4008,"createTime":"2019-10-11 09:50:16","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047098615","name":"人体探测器","location":"房间","type":2,"state":2,"dealTime":"2019-10-11 11:17:44","handler":"振辉科技","result":"1","delFlag":0,"placeName":"振辉科技"},{"id":4006,"createTime":"2019-10-11 09:48:35","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":4,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"}],"limit":10,"offset":0}}
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
         * PAGEModel : {"page":1,"pageSize":10,"count":0,"totalPage":1,"list":[{"id":4016,"createTime":"2019-10-11 11:31:31","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047098615","name":"人体探测器","location":"房间","type":2,"state":2,"dealTime":"2019-10-11 12:09:50","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4015,"createTime":"2019-10-11 10:01:33","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047098615","name":"人体探测器","location":"房间","type":2,"state":2,"dealTime":"2019-10-11 11:17:44","handler":"振辉科技","result":"1","delFlag":0,"placeName":"振辉科技"},{"id":4014,"createTime":"2019-10-11 10:00:40","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":8,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4013,"createTime":"2019-10-11 09:58:37","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":8,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4012,"createTime":"2019-10-11 09:56:37","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":8,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4011,"createTime":"2019-10-11 09:54:36","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":8,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4010,"createTime":"2019-10-11 09:52:35","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":4,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4009,"createTime":"2019-10-11 09:50:36","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":2,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4008,"createTime":"2019-10-11 09:50:16","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047098615","name":"人体探测器","location":"房间","type":2,"state":2,"dealTime":"2019-10-11 11:17:44","handler":"振辉科技","result":"1","delFlag":0,"placeName":"振辉科技"},{"id":4006,"createTime":"2019-10-11 09:48:35","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":4,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"}],"limit":10,"offset":0}
         */

        private PAGEModelBean PAGEModel;

        public PAGEModelBean getPAGEModel() {
            return PAGEModel;
        }

        public void setPAGEModel(PAGEModelBean PAGEModel) {
            this.PAGEModel = PAGEModel;
        }

        public static class PAGEModelBean {
            /**
             * page : 1
             * pageSize : 10
             * count : 0
             * totalPage : 1
             * list : [{"id":4016,"createTime":"2019-10-11 11:31:31","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047098615","name":"人体探测器","location":"房间","type":2,"state":2,"dealTime":"2019-10-11 12:09:50","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4015,"createTime":"2019-10-11 10:01:33","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047098615","name":"人体探测器","location":"房间","type":2,"state":2,"dealTime":"2019-10-11 11:17:44","handler":"振辉科技","result":"1","delFlag":0,"placeName":"振辉科技"},{"id":4014,"createTime":"2019-10-11 10:00:40","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":8,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4013,"createTime":"2019-10-11 09:58:37","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":8,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4012,"createTime":"2019-10-11 09:56:37","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":8,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4011,"createTime":"2019-10-11 09:54:36","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":8,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4010,"createTime":"2019-10-11 09:52:35","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":4,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4009,"createTime":"2019-10-11 09:50:36","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":2,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"},{"id":4008,"createTime":"2019-10-11 09:50:16","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047098615","name":"人体探测器","location":"房间","type":2,"state":2,"dealTime":"2019-10-11 11:17:44","handler":"振辉科技","result":"1","delFlag":0,"placeName":"振辉科技"},{"id":4006,"createTime":"2019-10-11 09:48:35","updateTime":"2019-10-12 14:53:22","createBy":null,"updateBy":null,"placeId":128,"sn":"RTA861931047091107","name":"人体探测2","location":"房间","type":4,"state":2,"dealTime":"2019-10-11 11:17:48","handler":"振辉科技","result":"3","delFlag":0,"placeName":"振辉科技"}]
             * limit : 10
             * offset : 0
             */

            private int page;
            private int pageSize;
            private int count;
            private int totalPage;
            private int limit;
            private int offset;
            private List<SmokeMessageBean> list;

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

            public List<SmokeMessageBean> getList() {
                return list;
            }

            public void setList(List<SmokeMessageBean> list) {
                this.list = list;
            }


        }
    }
}
