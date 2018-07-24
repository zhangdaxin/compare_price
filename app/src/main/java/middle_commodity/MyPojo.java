package middle_commodity;


/**
 * Created by 张欣 on 2018/7/17.
 */
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



