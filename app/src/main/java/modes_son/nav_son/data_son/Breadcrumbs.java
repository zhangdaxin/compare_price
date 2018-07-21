package modes_son.nav_son.data_son;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class Breadcrumbs {
    private Catpath[] catpath;

    private String[] queryPath;

    private String baobeiUrl;

    private String spuTotalHit;

    public Catpath[] getCatpath ()
    {
        return catpath;
    }

    public void setCatpath (Catpath[] catpath)
    {
        this.catpath = catpath;
    }

    public String[] getQueryPath ()
    {
        return queryPath;
    }

    public void setQueryPath (String[] queryPath)
    {
        this.queryPath = queryPath;
    }

    public String getBaobeiUrl ()
    {
        return baobeiUrl;
    }

    public void setBaobeiUrl (String baobeiUrl)
    {
        this.baobeiUrl = baobeiUrl;
    }

    public String getSpuTotalHit ()
    {
        return spuTotalHit;
    }

    public void setSpuTotalHit (String spuTotalHit)
    {
        this.spuTotalHit = spuTotalHit;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [catpath = "+catpath+", queryPath = "+queryPath+", baobeiUrl = "+baobeiUrl+", spuTotalHit = "+spuTotalHit+"]";
    }
}
