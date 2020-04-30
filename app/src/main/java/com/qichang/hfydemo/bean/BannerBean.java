package com.qichang.hfydemo.bean;

import java.util.List;

public class BannerBean {

    private List<NewListBean> newList;
    private List<BannerImgBean> imgList;

    public List<NewListBean> getNewList() {
        return newList;
    }

    public void setNewList(List<NewListBean> newList) {
        this.newList = newList;
    }

    public List<BannerImgBean> getBannerImg() {
        return imgList;
    }

    public void setBannerImg(List<BannerImgBean> BannerImg) {
        this.imgList = BannerImg;
    }

    public static class NewListBean {
        /**
         * postTime : 2020-04-21 09:00
         * contents :
         * id :
         * title :
         * type :
         */

        private String postTime;
//        private String contents;
        private String id;
        private String title;
        private String type;

        public String getPostTime() {
            return postTime;
        }

        public void setPostTime(String postTime) {
            this.postTime = postTime;
        }

//        public String getContents() {
//            return contents;
//        }
//
//        public void setContents(String contents) {
//            this.contents = contents;
//        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class BannerImgBean {
        /**
         * img_url : 2020-04-21 09:00
         * id :
         * img_title :
         */

        private String img_url;
        private String id;
        private String img_title;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg_title() {
            return img_title;
        }

        public void setImg_title(String img_title) {
            this.img_title = img_title;
        }
    }
}
