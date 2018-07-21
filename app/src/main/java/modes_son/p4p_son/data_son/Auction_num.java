package modes_son.p4p_son.data_son;

/*
    拍卖num
 */

public class Auction_num {

    private String search;

    public String getSearch ()
    {
        return search;
    }

    public void setSearch (String search)
    {
        this.search = search;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [search = "+search+"]";
    }
}
