package modes_son;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class Marketheader {
    private String status;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+"]";
    }
}
