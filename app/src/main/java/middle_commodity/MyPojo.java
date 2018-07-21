package middle_commodity;

import main_commodity.Feature;
import main_commodity.MainInfo;
import main_commodity.Mods;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class MyPojo {
    private String pageName;

    private MainInfo mainInfo;

    private Feature feature;

    private Mods mods;

    public String getPageName ()
    {
        return pageName;
    }

    public void setPageName (String pageName)
    {
        this.pageName = pageName;
    }

    public MainInfo getMainInfo ()
    {
        return mainInfo;
    }

    public void setMainInfo (MainInfo mainInfo)
    {
        this.mainInfo = mainInfo;
    }

    public Feature getFeature ()
    {
        return feature;
    }

    public void setFeature (Feature feature)
    {
        this.feature = feature;
    }

    public Mods getMods ()
    {
        return mods;
    }

    public void setMods (Mods mods)
    {
        this.mods = mods;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pageName = "+pageName+", mainInfo = "+mainInfo+", feature = "+feature+", mods = "+mods+"]";
    }
}
