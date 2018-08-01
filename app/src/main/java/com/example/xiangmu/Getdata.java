package com.example.xiangmu;

import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.xiangmu.main_layout.search_main;
import com.example.xiangmu.pageName_mainsrp.Auctions;
import com.example.xiangmu.pageName_mainsrp.JsonRootBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import middle_commodity.MyPojo;
import middle_commodity.Spus;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Getdata {
    public static String responseData;
    public static String responseData1;
    public static String responseData2;
    public static List<Spus> sp=new ArrayList<Spus>();
    /*
    从淘宝中获取商品数据
     */
    public static void get() {
        sp.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://s.taobao.com/search?q=" + search_main.searchkeyword)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    responseData = response.body().string();
                    String s = "g_page_config";
                    String s1 = "g_srp_loadCss";
                    /*
                    获取完数据截取数据
                     */
                    responseData = responseData.substring(responseData.indexOf(s));
                    responseData = responseData.substring(0, responseData.indexOf(s1));
                    Log.d("", "run: " + responseData);

                    if (responseData.contains("\"pageName\":\"spulist\"")) {
                        responseData = responseData.substring(responseData.indexOf("= ") + 2);
                        responseData = responseData.substring(0, responseData.indexOf("};") + 1);
                        responseData = responseData.substring(responseData.indexOf("grid") - 1);
                        responseData = "{" + responseData.substring(0, responseData.indexOf("header") - 2) + "}";
                        Log.d("result:", "run:" + responseData);
                        MyPojo m = JSON.parseObject(responseData, MyPojo.class);
                        Spus[] modes = m.getGrid().getData().getSpus();

                        for (int i = 1; i < (modes.length >= 10 ? 10 : modes.length); i++) {
                            String pic_url = modes[i].getPic_url();
                            String title = modes[i].getTitle();
                            String price = modes[i].getPrice();
                            String month_sales = modes[i].getMonth_sales();
                            String url = "http:" + modes[i].getUrl();
                            String shop = "淘宝商城";

                            Spus spus = new Spus("http:" + pic_url, title, shop + ":", "价格为:￥" + price, "月销量: " + month_sales, url);
                            sp.add(spus);
                        }
                        Log.d("", "run: " + m);
                    } else {
                        responseData = responseData.substring(responseData.indexOf("= ") + 2);
                        responseData = responseData.substring(0, responseData.indexOf("};") + 1);
                        responseData = responseData.substring(responseData.indexOf("itemlist") - 1);
                        responseData = "{" + responseData.substring(0, responseData.indexOf("bottomsearch") - 2) + "}";
                        Log.d("result:", "run:" + responseData);
                        JsonRootBean j = JSON.parseObject(responseData, JsonRootBean.class);
                        Auctions[] au = j.getItemlist().getData().getAuctions();

                        for (int i = 1; i <= (au.length > 10 ? 10 : au.length); i++) {
                            String pic_url = au[i].getPic_url();
                            String title = au[i].getRaw_title();
                            String price = au[i].getView_price();
                            String month_sales = au[i].getView_sales();
                            month_sales = month_sales.substring(0, month_sales.indexOf("人"));
                            String url = "http:" + au[i].getDetail_url();
                            String shop = "淘宝商城";

                            Spus spus = new Spus("http:" + pic_url, title, shop + ":", "价格为:￥" + price, "总销量: " + month_sales, url);
                            sp.add(spus);
                        }
                        Log.d("", "run: " + j);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://search.jd.com/Search?keyword="+search_main.searchkeyword+"&enc=utf-8")
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    responseData1 = response.body().string();
                    dealwith(responseData1);
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

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://search.gome.com.cn/search?question="+search_main.searchkeyword+"&searchType=goods")
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                responseData2 = response.body().string();
            //    dealwith2(responseData2);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();
}

    public static void dealwith2(String html) {
        String commit=null;
        String url1=null;
        String pic_url = null;
        String price=null;
        String title=null;
        String shop="国美商城:";

        org.jsoup.nodes.Document document = Jsoup.parse(html);
        Elements elements = document.select("div[class=product-item]");
        for(org.jsoup.nodes.Element element : elements) {
            // 月销量  title   url  and  pic_url  价格
            Elements elementsByClass = element.getElementsByClass("product-lists clear-fix");
            for(org.jsoup.nodes.Element element2 : elementsByClass) {
                Elements elementsByTag = element2.getElementsByTag("li");
                for(Element element3 : elementsByTag) {
                    commit = element3.getElementsByTag("input").attr("salesvolume");
                    title= element3.getElementsByTag("input").attr("skuname");
                    price = element3.getElementsByTag("input").attr("price");
                    Elements elementsByTag1= element3.getElementsByTag("div");
                    for(Element element1:elementsByTag1)
                    {
                        Elements elementsByTag2= element1.getElementsByTag("div");
                        for (Element element4:elementsByTag2)
                        {
                            Elements elementsByTag3= element4.getElementsByClass("item-pic");
                            for (Element element5:elementsByTag3)
                            {
                                url1=element5.getElementsByTag("a").attr("href");
                                url1="http:"+url1;
                                Elements elementsByTag4= element5.getElementsByClass("item-link");
                                for(Element element6:elementsByTag4)
                                {
                                    pic_url=element6.getElementsByTag("img").attr("src");
                                }
                            }
                        }
                    }
                }
            }
            Spus s= new Spus(pic_url, title, shop , "价格为:￥" + price,"月销量为: "+commit, url1);
            sp.add(s);
        }
    }

    public static void dealwith(String html) throws Exception{
        String commit=null;
        String url1=null;
        String pic_url = null;
        String price=null;
        String title=null;
        String shop="京东商城:";

        org.jsoup.nodes.Document document = Jsoup.parse(html);
        Elements elements = document.select("div[class=gl-i-wrap]");
        for (org.jsoup.nodes.Element element : elements) {
            // 评价
            Elements elementsByClass = element.getElementsByClass("p-commit");
            for (org.jsoup.nodes.Element element2 : elementsByClass) {
                Elements elementsByTag = element2.getElementsByTag("strong");
                for (Element element3 : elementsByTag) {
                    commit = element3.getElementsByTag("a").text();
                    commit=commit+"评价";
                }
            }
           //   url
		Elements elementsByClass1 = element.getElementsByClass("p-img");
		for (org.jsoup.nodes.Element element2 : elementsByClass1) {
			url1 = element2.getElementsByTag("a").attr("href");
            url1="http:"+url1;
		}
             //title  and  pic_url
		Elements elementsByClass2 = element.getElementsByClass("p-img");
		for (org.jsoup.nodes.Element element2 : elementsByClass2) {
			title= element2.getElementsByTag("a").attr("title");
            pic_url= element2.getElementsByTag("img").attr("source-data-lazy-img");
			pic_url="http:"+pic_url;
		}
        //     价格
		Elements elementsByClass3 = element.getElementsByClass("p-price");
		for (org.jsoup.nodes.Element element2 : elementsByClass3) {
			price = element2.getElementsByTag("i").text();
		}
		Spus s= new Spus(pic_url, title, shop , "价格为:￥" + price, commit, url1);
		sp.add(s);
        }
    }

}
