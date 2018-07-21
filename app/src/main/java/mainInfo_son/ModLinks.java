package mainInfo_son;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class ModLinks {
    private String pager;

    private String default1;

    private String sortbar;

    private String nav;

    private String tab;

    private String filter;

    private String myblock;

    private String breadcrumb;

    public String getPager ()
    {
        return pager;
    }

    public void setPager (String pager)
    {
        this.pager = pager;
    }

    public String getDefault ()
    {
        return default1;
    }

    public void setDefault (String default1)
    {
        this.default1 = default1;
    }

    public String getSortbar ()
    {
        return sortbar;
    }

    public void setSortbar (String sortbar)
    {
        this.sortbar = sortbar;
    }

    public String getNav ()
    {
        return nav;
    }

    public void setNav (String nav)
    {
        this.nav = nav;
    }

    public String getTab ()
    {
        return tab;
    }

    public void setTab (String tab)
    {
        this.tab = tab;
    }

    public String getFilter ()
    {
        return filter;
    }

    public void setFilter (String filter)
    {
        this.filter = filter;
    }

    public String getMyblock ()
    {
        return myblock;
    }

    public void setMyblock (String myblock)
    {
        this.myblock = myblock;
    }

    public String getBreadcrumb ()
    {
        return breadcrumb;
    }

    public void setBreadcrumb (String breadcrumb)
    {
        this.breadcrumb = breadcrumb;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pager = "+pager+", default = "+default1+", sortbar = "+sortbar+", nav = "+nav+", tab = "+tab+", filter = "+filter+", myblock = "+myblock+", breadcrumb = "+breadcrumb+"]";
    }
}
