package main_commodity;


public class Feature {
    private String retinaOff;

    private String shopcardOff;

    private String webpOff;

    public String getRetinaOff ()
    {
        return retinaOff;
    }

    public void setRetinaOff (String retinaOff)
    {
        this.retinaOff = retinaOff;
    }

    public String getShopcardOff ()
    {
        return shopcardOff;
    }

    public void setShopcardOff (String shopcardOff)
    {
        this.shopcardOff = shopcardOff;
    }

    public String getWebpOff ()
    {
        return webpOff;
    }

    public void setWebpOff (String webpOff)
    {
        this.webpOff = webpOff;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [retinaOff = "+retinaOff+", shopcardOff = "+shopcardOff+", webpOff = "+webpOff+"]";
    }
}

