package model;

import java.util.ArrayList;
import java.util.Date;

public class Sales {
    private Customers customer;
    private String invid;
    private Date invdate;
    private double amount;
    private double balance;
    private SalesDet salesdet;

    public Sales() {}

    public Sales(String invid, Date invdate, SalesDet salesDet, Customers customer, double amount) {
        this.invid = invid;
        this.invdate = invdate;
        this.salesdet = salesDet;
        this.customer = customer;
        this.amount = amount;
    }

// assume that the get and set methods are defined here

    public String getInvid() {
        return invid;
    }

    public Date getInvdate() {
        return invdate;
    }
}
