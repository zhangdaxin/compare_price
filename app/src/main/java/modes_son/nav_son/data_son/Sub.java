package modes_son.nav_son.data_son;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class Sub {
    private String text;

    private String desc;

    private String imgSrc;

    private String trace;

    private String value;

    private TraceData traceData;

    private String key;

    private String isExpandShow;

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public String getDesc ()
    {
        return desc;
    }

    public void setDesc (String desc)
    {
        this.desc = desc;
    }

    public String getImgSrc ()
    {
        return imgSrc;
    }

    public void setImgSrc (String imgSrc)
    {
        this.imgSrc = imgSrc;
    }

    public String getTrace ()
    {
        return trace;
    }

    public void setTrace (String trace)
    {
        this.trace = trace;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    public TraceData getTraceData ()
    {
        return traceData;
    }

    public void setTraceData (TraceData traceData)
    {
        this.traceData = traceData;
    }

    public String getKey ()
    {
        return key;
    }

    public void setKey (String key)
    {
        this.key = key;
    }

    public String getIsExpandShow ()
    {
        return isExpandShow;
    }

    public void setIsExpandShow (String isExpandShow)
    {
        this.isExpandShow = isExpandShow;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [text = "+text+", desc = "+desc+", imgSrc = "+imgSrc+", trace = "+trace+", value = "+value+", traceData = "+traceData+", key = "+key+", isExpandShow = "+isExpandShow+"]";
    }
}
