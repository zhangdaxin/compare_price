package middle_commodity;
public class Seller
{
    private String num;

    private String trace;

    private String url;

    public String getNum ()
    {
        return num;
    }

    public void setNum (String num)
    {
        this.num = num;
    }

    public String getTrace ()
    {
        return trace;
    }

    public void setTrace (String trace)
    {
        this.trace = trace;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [num = "+num+", trace = "+trace+", url = "+url+"]";
    }
}