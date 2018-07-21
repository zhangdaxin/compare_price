package com.example.xiangmu.compare_price;


import android.graphics.Bitmap;

/**
 * 模拟商品类
 */

public class Modes {
    private String img;
    private String  message;
    private String shop;
    private String price;
    private String month_sales;

    public Modes(String img ,String message,String shop,String price,String month_sales)
    {
        this.img=img;
        this.message=message;
        this.shop=shop;
        this.price=price;
        this.month_sales=month_sales;
    }

    public String getImg() {
       return img;
    }

    public String getMessage() {
        return message;
    }

    public String getPrice() {
        return price;
    }

    public String getShop() {
        return shop;
    }

    public String getMonth_sales() {
        return month_sales;
    }
}
