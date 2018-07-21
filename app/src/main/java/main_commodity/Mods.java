package main_commodity;

import modes_son.Bgshopstar;
import modes_son.Bottomsearch;
import modes_son.Debugbar;
import modes_son.Feedback;
import modes_son.Grid;
import modes_son.Header;
import modes_son.Marketheader;
import modes_son.Noresult;
import modes_son.P4p;
import modes_son.Pager;
import modes_son.Nav;
import modes_son.Shopstar;
import modes_son.Sortbar;
import modes_son.Spucombo;


public class Mods {

    private Noresult noresult;

    private Shopstar shopstar;

    private Bgshopstar bgshopstar;

    private Pager pager;

    private Spucombo spucombo;

    private Marketheader marketheader;

    private Header header;

    private Feedback feedback;

    private P4p p4p;

    private Sortbar sortbar;

    private Nav nav;

    private Grid grid;

    private Bottomsearch bottomsearch;

    private Debugbar debugbar;

//    public List<Nav> list_nav;
//    public List<Sortbar> list_sortbar;
//    public List<Debugbar> list_debugbar;
//    public List<Bottomsearch> list_bottomsearch;
//    public List<P4p> list_p4p;
//    public List<Feedback> list_feedback;
//    public List<Marketheader> list_marketheader;
//    public List<Shopstar> list_shopstar;
//    public List<Bgshopstar> list_bgshopstar;
//    public List<Pager> list_pager;
//    public List<Grid> list_grid;
//    public List<Header> list_header;
//    public List<Spucombo> list_spucombo;
//    public List<Noresult> list_noresult;

    public Noresult getNoresult ()
    {
        return noresult;
    }

    public void setNoresult (Noresult noresult)
    {
        this.noresult = noresult;
    }

    public Shopstar getShopstar ()
    {
        return shopstar;
    }

    public void setShopstar (Shopstar shopstar)
    {
        this.shopstar = shopstar;
    }

    public Bgshopstar getBgshopstar ()
    {
        return bgshopstar;
    }

    public void setBgshopstar (Bgshopstar bgshopstar)
    {
        this.bgshopstar = bgshopstar;
    }

    public Pager getPager ()
    {
        return pager;
    }

    public void setPager (Pager pager)
    {
        this.pager = pager;
    }

    public Spucombo getSpucombo ()
    {
        return spucombo;
    }

    public void setSpucombo (Spucombo spucombo)
    {
        this.spucombo = spucombo;
    }

    public Marketheader getMarketheader ()
    {
        return marketheader;
    }

    public void setMarketheader (Marketheader marketheader)
    {
        this.marketheader = marketheader;
    }

    public Header getHeader ()
    {
        return header;
    }

    public void setHeader (Header header)
    {
        this.header = header;
    }

    public Feedback getFeedback ()
    {
        return feedback;
    }

    public void setFeedback (Feedback feedback)
    {
        this.feedback = feedback;
    }

    public P4p getP4p ()
    {
        return p4p;
    }

    public void setP4p (P4p p4p)
    {
        this.p4p = p4p;
    }

    public Sortbar getSortbar ()
    {
        return sortbar;
    }

    public void setSortbar (Sortbar sortbar)
    {
        this.sortbar = sortbar;
    }

    public Nav getNav ()
    {
        return nav;
    }

    public void setNav (Nav nav)
    {
        this.nav = nav;
    }

    public Grid getGrid ()
    {
        return grid;
    }

    public void setGrid (Grid grid)
    {
        this.grid = grid;
    }

    public Bottomsearch getBottomsearch ()
    {
        return bottomsearch;
    }

    public void setBottomsearch (Bottomsearch bottomsearch)
    {
        this.bottomsearch = bottomsearch;
    }

    public Debugbar getDebugbar ()
    {
        return debugbar;
    }

    public void setDebugbar (Debugbar debugbar)
    {
        this.debugbar = debugbar;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [noresult = "+noresult+", shopstar = "+shopstar+", bgshopstar = "+bgshopstar+", pager = "+pager+", spucombo = "+spucombo+", marketheader = "+marketheader+", header = "+header+", feedback = "+feedback+", p4p = "+p4p+", sortbar = "+sortbar+", nav = "+nav+", grid = "+grid+", bottomsearch = "+bottomsearch+", debugbar = "+debugbar+"]";
    }
}
