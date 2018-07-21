package modes_son.sortbar_son.data_son;

/**
 * Created by 张欣 on 2018/7/17.
 */

public class Pager {
    private String totalCount;

    private String pageSize;

    private String currentPage;

    private String totalPage;

    public String getTotalCount ()
    {
        return totalCount;
    }

    public void setTotalCount (String totalCount)
    {
        this.totalCount = totalCount;
    }

    public String getPageSize ()
    {
        return pageSize;
    }

    public void setPageSize (String pageSize)
    {
        this.pageSize = pageSize;
    }

    public String getCurrentPage ()
    {
        return currentPage;
    }

    public void setCurrentPage (String currentPage)
    {
        this.currentPage = currentPage;
    }

    public String getTotalPage ()
    {
        return totalPage;
    }

    public void setTotalPage (String totalPage)
    {
        this.totalPage = totalPage;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [totalCount = "+totalCount+", pageSize = "+pageSize+", currentPage = "+currentPage+", totalPage = "+totalPage+"]";
    }
}
