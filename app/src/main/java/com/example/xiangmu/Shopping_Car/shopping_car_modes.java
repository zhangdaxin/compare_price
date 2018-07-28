package com.example.xiangmu.Shopping_Car;



public class shopping_car_modes {

    private String pic_url;

    private String shop;

    private String url;

    private String title;

    private String price;

    private String month_sales;

    private boolean isChecked;

    public shopping_car_modes(String pic_url,String title,String shop,String price,String month_sales,String url){
        this.pic_url=pic_url;
        this.title=title;
        this.shop=shop;
        this.price=price;
        this.month_sales=month_sales;
        this.url=url;
    }

//    public String getModeId() {
//        return modeId;
//    }
//
//    public void setModeId(String modeId) {
//        this.modeId = modeId;
//    }


    public String getShop() {
        return shop;
    }

    public String getPic_url ()
    {
        return pic_url;
    }
    public void setPic_url (String pic_url)
    {
        this.pic_url = pic_url;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getMonth_sales ()
    {
        return month_sales;
    }

    public void setMonth_sales (String month_sales)
    {
        this.month_sales = month_sales;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    public Boolean getChecked ()
    {
        return isChecked;
    }

    @Override
    public String toString()
    {
        return "ClassPojo  pic_url = "+pic_url+", url = "+url+", title = "+title+", price = "+price+", month_sales = "+month_sales+"";
    }
}
