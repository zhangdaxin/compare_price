package modes_son.p4p_son;

import modes_son.nav_son.data_son.Adv;
import modes_son.nav_son.data_son.Breadcrumbs;
import modes_son.nav_son.data_son.Common;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class Data {
    private Common[] common;

    private Breadcrumbs breadcrumbs;

    private Adv[] adv;

    public Common[] getCommon ()
    {
        return common;
    }

    public void setCommon (Common[] common)
    {
        this.common = common;
    }

    public Breadcrumbs getBreadcrumbs ()
    {
        return breadcrumbs;
    }

    public void setBreadcrumbs (Breadcrumbs breadcrumbs)
    {
        this.breadcrumbs = breadcrumbs;
    }

    public Adv[] getAdv ()
    {
        return adv;
    }

    public void setAdv (Adv[] adv)
    {
        this.adv = adv;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [common = "+common+", breadcrumbs = "+breadcrumbs+", adv = "+adv+"]";
    }
}
