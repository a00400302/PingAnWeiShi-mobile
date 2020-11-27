package com.yxdz.update.bean;

import com.yxdz.http.bean.YxdzInfo;

public class UpdateBean extends YxdzInfo{

    /**
     * code : 0
     * data : {"id":3,"createTime":"2019-08-15 17:19:26","updateTime":"2019-08-15 17:20:21","createBy":1,"updateBy":1,"verCode":9,"verName":"1.2.8","content":"","path":"/surpass/upload//2019/08/15/app-release.apk","must":0,"md5":"49eab378a048d007e46897392bb9b93e","fileSize":20693594}
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
         * id : 3
         * createTime : 2019-08-15 17:19:26
         * updateTime : 2019-08-15 17:20:21
         * createBy : 1
         * updateBy : 1
         * verCode : 9
         * verName : 1.2.8
         * content :
         * path : /surpass/upload//2019/08/15/app-release.apk
         * must : 0
         * md5 : 49eab378a048d007e46897392bb9b93e
         * fileSize : 20693594
         */

        private int id;
        private String createTime;
        private String updateTime;
        private int createBy;
        private int updateBy;
        private int verCode;
        private String verName;
        private String content;
        private String path;
        private int must;
        private String md5;
        private int fileSize;

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

        public int getVerCode() {
            return verCode;
        }

        public void setVerCode(int verCode) {
            this.verCode = verCode;
        }

        public String getVerName() {
            return verName;
        }

        public void setVerName(String verName) {
            this.verName = verName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getMust() {
            return must;
        }

        public void setMust(int must) {
            this.must = must;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public int getFileSize() {
            return fileSize;
        }

        public void setFileSize(int fileSize) {
            this.fileSize = fileSize;
        }
    }
}
