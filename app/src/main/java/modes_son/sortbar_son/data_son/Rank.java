package modes_son.sortbar_son.data_son;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class Rank {
    private String percent;

    private String start;

    private String end;

    public String getPercent ()
    {
        return percent;
    }

    public void setPercent (String percent)
    {
        this.percent = percent;
    }

    public String getStart ()
    {
        return start;
    }

    public void setStart (String start)
    {
        this.start = start;
    }

    public String getEnd ()
    {
        return end;
    }

    public void setEnd (String end)
    {
        this.end = end;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [percent = "+percent+", start = "+start+", end = "+end+"]";
    }
}
