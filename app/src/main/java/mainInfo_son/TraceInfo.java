package mainInfo_son;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class TraceInfo {
    private String pvStat;

    private mainInfo_son.TraceData traceData;

    public String getPvStat ()
    {
        return pvStat;
    }

    public void setPvStat (String pvStat)
    {
        this.pvStat = pvStat;
    }

    public mainInfo_son.TraceData getTraceData ()
    {
        return traceData;
    }

    public void setTraceData (mainInfo_son.TraceData traceData)
    {
        this.traceData = traceData;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pvStat = "+pvStat+", traceData = "+traceData+"]";
    }
}
