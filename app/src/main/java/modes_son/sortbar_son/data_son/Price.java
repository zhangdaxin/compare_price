package modes_son.sortbar_son.data_son;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class Price {
    private Rank[] rank;

    public Rank[] getRank ()
    {
        return rank;
    }

    public void setRank (Rank[] rank)
    {
        this.rank = rank;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [rank = "+rank+"]";
    }
}
