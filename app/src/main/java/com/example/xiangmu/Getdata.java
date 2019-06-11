package com.example.xiangmu;

import android.os.Handler;
import android.util.Log;

import com.example.xiangmu.Shopping_Car.Sort;
import com.example.xiangmu.main_layout.SearchActivity;
import com.example.xiangmu.middle_commodity.MyPojo;
import com.example.xiangmu.middle_commodity.Spus;
import com.example.xiangmu.pageName_mainsrp.Auctions;
import com.example.xiangmu.pageName_mainsrp.JsonRootBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Getdata {
    public static String responseData;
    public static String responseData1;
    public static List<Spus> sp = new ArrayList<Spus>();
    /*
    从淘宝中获取商品数据
     */
    public static void get(final Handler handler) {
        sp.clear();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url("https://s.taobao.com/search?q=" + SearchActivity.searchkeyword)
//                        .build();
//                try {
//                    Response response = client.newCall(request).execute();
//                    responseData = response.body().string();
//                    String s = "g_page_config";
//                    String s1 = "g_srp_loadCss";
//                    /*
//                    获取完数据截取数据
//                     */
//                    responseData = responseData.substring(responseData.indexOf(s));
//                    responseData = responseData.substring(0, responseData.indexOf(s1));
//                    Log.d("", "run: " + responseData);
//
//                    if (responseData.contains("\"pageName\":\"spulist\"")) {
//                        responseData = responseData.substring(responseData.indexOf("= ") + 2);
//                        responseData = responseData.substring(0, responseData.indexOf("};") + 1);
//                        responseData = responseData.substring(responseData.indexOf("grid") - 1);
//                        responseData = "{" + responseData.substring(0, responseData.indexOf("header") - 2) + "}";
//                        Log.d("result:", "run:" + responseData);
//                        MyPojo m = com.alibaba.fastjson.JSON.parseObject(responseData, MyPojo.class);
//                        Spus[] modes = m.getGrid().getData().getSpus();
//
//                        for (int i = 0; i < (modes.length >= 10 ? 10 : modes.length); i++) {
//                            String pic_url = modes[i].getPic_url();
//                            String title = modes[i].getTitle();
//                            Double price = modes[i].getPrice();
//                            String month_sales = modes[i].getMonth_sales();
//                            String url = "http:" + modes[i].getUrl();
//                            String shop = "淘宝商城";
//                            if (!pic_url.equals("") && !price.equals("")) {
//                                Spus spus = new Spus("http:" + pic_url, title, shop + ":",price, "月销量: " + month_sales, url);
//                                sp.add(spus);
//                            }
//                        }
//                        Collections.sort(sp, new Sort());
//                        handler.sendEmptyMessage(0);
//                        Log.d("", "run: " + m);
//                    } else {
//                        responseData = responseData.substring(responseData.indexOf("= ") + 2);
//                        responseData = responseData.substring(0, responseData.indexOf("};") + 1);
//                        responseData = responseData.substring(responseData.indexOf("itemlist") - 1);
//                        responseData = "{" + responseData.substring(0, responseData.indexOf("bottomsearch") - 2) + "}";
//                        Log.d("result:", "run:" + responseData);
//                        JsonRootBean j = com.alibaba.fastjson.JSON.parseObject(responseData, JsonRootBean.class);
//                        Auctions[] au = j.getItemlist().getData().getAuctions();
//
//                        for (int i = 0; i <= (au.length > 10 ? 10 : au.length); i++) {
//                            String pic_url = au[i].getPic_url();
//                            String title = au[i].getRaw_title();
//                            String price = au[i].getView_price();
//                            Log.d("", "run: "+price);
//                            String month_sales = au[i].getView_sales();
//                            month_sales = month_sales.substring(0, month_sales.indexOf("人"));
//                            String url = "http:" + au[i].getDetail_url();
//                            String shop = "淘宝商城";
//                            Double price1=Double.parseDouble(price);
//                            if (!pic_url.equals("")&&!price.equals(""))
//                            {
//                                Spus spus = new Spus("http:" + pic_url, title, shop + ":",price1, "总销量: " + month_sales, url);
//                                sp.add(spus);
//                            }
//                        }
//                        Collections.sort(sp, new Sort());
//                        handler.sendEmptyMessage(0);
//                        Log.d("", "run: " + j);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://list.tmall.com/search_product.htm?q="+ SearchActivity.searchkeyword );
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    String result = response.toString();
                    dealwith3(handler,result);
                    Log.d("result", "run: " + result);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://search.jd.com/Search?keyword=" + SearchActivity.searchkeyword + "&enc=utf-8")
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    responseData1 = response.body().string();
                    dealwith(handler,responseData1);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://search.dangdang.com/?key=" + SearchActivity.searchkeyword + "&act=input");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in, "GBK"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    String result = response.toString();
                    Log.d("result", "run: " + result);
                    dealwith2(handler,result);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private static void dealwith3(Handler handler, String html) {
            String commit = null;
            String url1 = null;
            String pic_url = null;
            String price = null;
            String title = null;
            String shop = "淘宝商城:";
            Double price1 = null;
            String href = null;
            int a = 0;
            org.jsoup.nodes.Document document = Jsoup.parse(html);
            Elements elements = document.select("a[class=list_item sync]");
            for (org.jsoup.nodes.Element element : elements) {
                if(a>=10) {
                    break;
                }
                // url pic_url
                href = element.attr("href");
                url1 = href.substring(href.indexOf("//"), href.indexOf("&"));
                url1 = "http:" + url1;
                pic_url = href.substring(href.indexOf("pic=//") + 4, href.indexOf("&item"));
                pic_url = "http:" + pic_url;
                if (pic_url.equals(""))
                {
                    continue;
                }
                // title  and  commit
                Elements elementsByClass2 = element.getElementsByClass("li_info");
                for (org.jsoup.nodes.Element element2 : elementsByClass2) {
                    title = element2.getElementsByTag("h3").text();
                    commit = element2.getElementsByTag("p").text();
                    commit = commit.substring(commit.indexOf("销") + 1, commit.indexOf("笔"));
//                commit = commit + "评价";
                }
                //     价格
                price = element.getElementsByClass("lii_price").text();
                price = price.substring(1, price.indexOf("免"));
                if (price.equals(""))
                {
                    continue;
                }

//                if (a <= 10 && !pic_url.equals("") && !price.equals("")) {
                    price1 = Double.parseDouble(price);
                    Spus s = new Spus(pic_url, title, shop, price1, "总销量:" + commit, url1);
                    sp.add(s);
                    a++;
//                }
            }
        handler.sendEmptyMessage(0);
    }

    public static void dealwith2(Handler handler,String html) {
        String commit = null;
        String url1 = null;
        String pic_url = null;
        String price = null;
        String title = null;
        String shop = "当当商城:";
        Double price1 = null;
        int k=0;

        org.jsoup.nodes.Document document = Jsoup.parse(html);
        Elements elements = document.select("li[ddt-pit]");
        for (org.jsoup.nodes.Element element : elements) {

            if(k>=10) {
              break;
            }
            // 月销量  title   url  and  pic_url  价格
                url1 = element.getElementsByTag("a").attr("href");
                title = element.getElementsByTag("a").attr("title");
                price = element.getElementsByClass("price_n").text();
                if (price.equals("")||price==null)
                {
                    continue;
                }
                price=price.substring(1,price.length());
                pic_url = element.getElementsByTag("img").attr("data-original");
               if (pic_url.equals("")||pic_url==null)
               {
                continue;
               }
                Elements elements1 = element.getElementsByClass("star");
                for (org.jsoup.nodes.Element element1 : elements1) {
                    commit = element1.getElementsByTag("a").text();
                }
                price1=Double.parseDouble(price);

                Spus s = new Spus(pic_url, title, shop, price1, commit, url1);
                sp.add(s);
                k++;
            }
        Collections.sort(sp, new Sort());
        handler.sendEmptyMessage(0);
    }

    public static void dealwith(Handler handler,String html) throws Exception {
        String commit = null;
        String url1 = null;
        String pic_url = null;
        String price = null;
        String title = null;
        String shop = "京东商城:";
        Double price1 = null;
        int j=0;
        org.jsoup.nodes.Document document = Jsoup.parse(html);
        Elements elements = document.select("div[class=gl-i-wrap]");
        for (org.jsoup.nodes.Element element : elements) {
            if (j>10)
            {
                break;
            }
            // 评价
            Elements elementsByClass = element.getElementsByClass("p-commit");
            for (org.jsoup.nodes.Element element2 : elementsByClass) {
                Elements elementsByTag = element2.getElementsByTag("strong");
                for (Element element3 : elementsByTag) {
                    commit = element3.getElementsByTag("a").text();
                    commit = commit + "评价";
                }
            }
            //   url
            Elements elementsByClass1 = element.getElementsByClass("p-img");
            for (org.jsoup.nodes.Element element2 : elementsByClass1) {
                url1 = element2.getElementsByTag("a").attr("href");
                url1 = "http:" + url1;
            }
            //title  and  pic_url
            Elements elementsByClass2 = element.getElementsByClass("p-img");
            for (org.jsoup.nodes.Element element2 : elementsByClass2) {
                title = element2.getElementsByTag("a").attr("title");
                pic_url = element2.getElementsByTag("img").attr("source-data-lazy-img");
                pic_url = "http:" + pic_url;
            }
            if (pic_url.equals(""))
            {
                continue;
            }
            //     价格
            Elements elementsByClass3 = element.getElementsByClass("p-price");
            for (org.jsoup.nodes.Element element2 : elementsByClass3) {
                price = element2.getElementsByTag("i").text();
            }
            if (price.equals(""))
            {
                continue;
            }
                price1=Double.parseDouble(price);
                Spus s = new Spus(pic_url, title, shop,  price1, commit, url1);
                sp.add(s);
                j++;
        }
        Collections.sort(sp, new Sort());
        handler.sendEmptyMessage(0);
    }

}
