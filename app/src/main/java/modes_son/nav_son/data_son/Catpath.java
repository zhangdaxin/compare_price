package modes_son.nav_son.data_son;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class Catpath {
    private String name;

    private String value;

    private String url;

    private String key;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getKey ()
    {
        return key;
    }

    public void setKey (String key)
    {
        this.key = key;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", value = "+value+", url = "+url+", key = "+key+"]";
    }
}
