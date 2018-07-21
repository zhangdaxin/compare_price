package modes_son.grid_son.data_son;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class Spus {
    private String[] specialTag;

    private String cat;

    private String tag;

    private String trace;

    private Seller seller;

    private String pic_url;

    private String url;

    private String title;

    private String price;

    private String month_sales;

    private String cmt_count;

    private Tag_info[] tag_info;

    private String importantKey;

    public String[] getSpecialTag ()
    {
        return specialTag;
    }

    public void setSpecialTag (String[] specialTag)
    {
        this.specialTag = specialTag;
    }

    public String getCat ()
    {
        return cat;
    }

    public void setCat (String cat)
    {
        this.cat = cat;
    }

    public String getTag ()
    {
        return tag;
    }

    public void setTag (String tag)
    {
        this.tag = tag;
    }

    public String getTrace ()
    {
        return trace;
    }

    public void setTrace (String trace)
    {
        this.trace = trace;
    }

    public Seller getSeller ()
    {
        return seller;
    }

    public void setSeller (Seller seller)
    {
        this.seller = seller;
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

    public String getCmt_count ()
    {
        return cmt_count;
    }

    public void setCmt_count (String cmt_count)
    {
        this.cmt_count = cmt_count;
    }

    public Tag_info[] getTag_info ()
    {
        return tag_info;
    }

    public void setTag_info (Tag_info[] tag_info)
    {
        this.tag_info = tag_info;
    }

    public String getImportantKey ()
    {
        return importantKey;
    }

    public void setImportantKey (String importantKey)
    {
        this.importantKey = importantKey;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [specialTag = "+specialTag+", cat = "+cat+", tag = "+tag+", trace = "+trace+", seller = "+seller+", pic_url = "+pic_url+", url = "+url+", title = "+title+", price = "+price+", month_sales = "+month_sales+", cmt_count = "+cmt_count+", tag_info = "+tag_info+", importantKey = "+importantKey+"]";
    }}
