package middle_commodity;

import main_commodity.Feature;
import main_commodity.MainInfo;
import main_commodity.Mods;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class MyPojo {
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

