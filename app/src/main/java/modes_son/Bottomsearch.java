package modes_son;

import com.example.xiangmu.Data;



public class Bottomsearch {
    private String status;

    private Data data;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", data = "+data+"]";
    }
}
