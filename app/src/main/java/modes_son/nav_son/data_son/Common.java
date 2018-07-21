package modes_son.nav_son.data_son;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class Common {
    private Sub[] sub;

    private String text;

    private String show2line;

    private String trace;

    private String isMulti;

    private String forceShowMore;

    private String type;

    public Sub[] getSub ()
    {
        return sub;
    }

    public void setSub (Sub[] sub)
    {
        this.sub = sub;
    }

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public String getShow2line ()
    {
        return show2line;
    }

    public void setShow2line (String show2line)
    {
        this.show2line = show2line;
    }

    public String getTrace ()
    {
        return trace;
    }

    public void setTrace (String trace)
    {
        this.trace = trace;
    }

    public String getIsMulti ()
    {
        return isMulti;
    }

    public void setIsMulti (String isMulti)
    {
        this.isMulti = isMulti;
    }

    public String getForceShowMore ()
    {
        return forceShowMore;
    }

    public void setForceShowMore (String forceShowMore)
    {
        this.forceShowMore = forceShowMore;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sub = "+sub+", text = "+text+", show2line = "+show2line+", trace = "+trace+", isMulti = "+isMulti+", forceShowMore = "+forceShowMore+", type = "+type+"]";
    }
}
