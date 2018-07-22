package middle_commodity;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class Tag_info {
    private String tag;

    public String getTag ()
    {
        return tag;
    }

    public void setTag (String tag)
    {
        this.tag = tag;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tag = "+tag+"]";
    }
}
