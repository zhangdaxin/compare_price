package com.example.xiangmu.compare_price;


import android.graphics.Bitmap;

/**
 * 模拟商品类
 */

public class Modes {
    private Bitmap img;
    private String  message;
    private String shop;
    private String price;

    public Modes(Bitmap img ,String message,String shop,String price)
    {
        this.img=img;
        this.message=message;
        this.shop=shop;
        this.price=price;
    }

    public Bitmap getImg() {
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
}
