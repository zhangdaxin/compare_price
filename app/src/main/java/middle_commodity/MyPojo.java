package middle_commodity;


public class MyPojo
{
    private Grid grid;

    public Grid getGrid ()
    {
        return grid;
    }

    public void setGrid (Grid grid)
    {
        this.grid = grid;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [grid = "+grid+"]";
    }
}



