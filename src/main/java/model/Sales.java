package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class Sales {
    private SimpleStringProperty invid;
    private SimpleStringProperty details;
    private SimpleStringProperty customerName;
    private SimpleDoubleProperty productPrice;
    private SimpleDoubleProperty customerBalance;
    private SimpleDoubleProperty amount;
    private Date invdate;
    private SalesDet salesDet;
    private Customers customer;

    public Sales() {}

    public Sales(String invid, Date invdate, SalesDet salesDet, Customers customer, Double amount) {
        this.invid = new SimpleStringProperty(invid);
        this.invdate = invdate;
        this.amount = new SimpleDoubleProperty(amount);
        this.salesDet = salesDet;

        this.customer = customer;
        Products product = salesDet.getProduct();
        productPrice = new SimpleDoubleProperty(product.getPrice());
        customerBalance = new SimpleDoubleProperty(customer.getBalance());
        details = new SimpleStringProperty(salesDet.getSummary());
        customerName = new SimpleStringProperty(customer.getCustname());
    }

    public String getInvid() {
        return invid.get();
    }

    public SimpleStringProperty invidProperty() {
        return invid;
    }

    public String getDetails() {
        return details.get();
    }

    public SimpleStringProperty detailsProperty() {
        return details;
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public double getProductPrice() {
        return productPrice.get();
    }

    public SimpleDoubleProperty productPriceProperty() {
        return productPrice;
    }

    public double getCustomerBalance() {
        return customerBalance.get();
    }

    public SimpleDoubleProperty customerBalanceProperty() {
        return customerBalance;
    }

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public Date getInvdate() {
        return invdate;
    }

    public Customers getCustomer() {
        return customer;
    }

    public SalesDet getSalesDet() {
        return salesDet;
    }
}
