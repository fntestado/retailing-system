package model;

public class Customers {
    private Integer custid;
    private String custname;
    private String address;
    private String telno;
    private double balance;

    public Customers() {
        this (0, "", "", "", 0.0);
    }

    public Customers(Integer custid, String custname, String address, String telno, double balance) {
        this.custid = custid;
        this.custname = custname;
        this.address = address;
        this.telno = telno;
        this.balance = balance;
    }

    public String getCustname() {
        return custname;
    }

    public String getAddress() {
        return address;
    }

    public String getTelno() {
        return telno;
    }
}
