package com.example.xiangmu.discount;

public class Discount_Mode {
    String pic_url;
    String discount;
    String url;
    String old_money;
    String new_money;
    String title;
    public Discount_Mode(){

    }

    public Discount_Mode(String pic_url,String title,String new_money,String old_money,String discount,String url){
    this.pic_url=pic_url;
    this.title=title;
    this.new_money=new_money;
    this.discount=discount;
    this.url=url;
    this.old_money=old_money;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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
