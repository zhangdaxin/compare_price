package com.example.xiangmu.main_layout;


public class home_modes {
        String pic_url;
        String count;
        String url;
        String old_money;
        String new_money;
        String title;
        String quan;

        public home_modes(String pic_url, String title, String new_money, String old_money, String quan, String count, String url){
            this.pic_url=pic_url;
            this.title=title;
            this.new_money=new_money;
            this.count=count;
            this.url=url;
            this.old_money=old_money;
            this.quan=quan;
        }

        public String getQuan() {
            return quan;
        }

        public void setQuan(String quan) {
            this.quan = quan;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getNew_money() {
            return new_money;
        }

        public void setNew_money(String new_money) {
            this.new_money = new_money;
        }

        public String getOld_money() {
            return old_money;
        }

        public void setOld_money(String old_money) {
            this.old_money = old_money;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }


