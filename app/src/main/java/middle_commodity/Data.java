package middle_commodity;

import java.util.List;

public class Data
{
    private Spus[] spus;

    public Spus[] getSpus ()
    {
        return spus;
    }

    public void setSpus (Spus[] spus)
    {
        this.spus = spus;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [spus = "+spus+"]";
    }
}
