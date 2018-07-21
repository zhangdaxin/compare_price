package modes_son.sortbar_son.data_son;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class SortList {
    private String isActive;

    private String name;

    private String value;

    private String key;

    private String tip;

    public String getIsActive ()
    {
        return isActive;
    }

    public void setIsActive (String isActive)
    {
        this.isActive = isActive;
    }

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

    public String getKey ()
    {
        return key;
    }

    public void setKey (String key)
    {
        this.key = key;
    }

    public String getTip ()
    {
        return tip;
    }

    public void setTip (String tip)
    {
        this.tip = tip;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [isActive = "+isActive+", name = "+name+", value = "+value+", key = "+key+", tip = "+tip+"]";
    }
}
