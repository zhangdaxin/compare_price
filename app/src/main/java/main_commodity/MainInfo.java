package main_commodity;

import mainInfo_son.ModLinks;
import mainInfo_son.SrpGlobal;
import mainInfo_son.TraceInfo;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class MainInfo {
    private TraceInfo traceInfo;

    private SrpGlobal srpGlobal;

    private ModLinks modLinks;

    private String[] remainMods;

    private String currentUrl;

//    public List<ModLinks> list_modlinks;
//    public List<SrpGlobal> list_srpglobal;
//    public List<TraceInfo> list_traceinfo;

    public TraceInfo getTraceInfo ()
    {
        return traceInfo;
    }

    public void setTraceInfo (TraceInfo traceInfo)
    {
        this.traceInfo = traceInfo;
    }

    public SrpGlobal getSrpGlobal ()
    {
        return srpGlobal;
    }

    public void setSrpGlobal (SrpGlobal srpGlobal)
    {
        this.srpGlobal = srpGlobal;
    }

    public ModLinks getModLinks ()
    {
        return modLinks;
    }

    public void setModLinks (ModLinks modLinks)
    {
        this.modLinks = modLinks;
    }

    public String[] getRemainMods ()
    {
        return remainMods;
    }

    public void setRemainMods (String[] remainMods)
    {
        this.remainMods = remainMods;
    }

    public String getCurrentUrl ()
    {
        return currentUrl;
    }

    public void setCurrentUrl (String currentUrl)
    {
        this.currentUrl = currentUrl;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [traceInfo = "+traceInfo+", srpGlobal = "+srpGlobal+", modLinks = "+modLinks+", remainMods = "+remainMods+", currentUrl = "+currentUrl+"]";
    }
}
