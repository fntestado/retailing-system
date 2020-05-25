package model;

public class Customers {
    private Integer custid;
    private String custname;
    private String address;
    private String telno;
    private Double balance;

    public Customers() {
        this (0, "", "", "", 0.0);
    }

    public Customers(Integer custid, String custname, String address, String telno, Double balance) {
        this.custid = custid;
        this.custname = custname;
        this.address = address;
        this.telno = telno;
        this.balance = balance;
    }

    public static Customers parseCustomer(String stringToParse) {
        Customers customer = new Customers();
        String[] data = stringToParse.split(",");

        customer.custid = Integer.parseInt(data[0]);
        customer.custname = data[1];
        customer.address = data[2];
        customer.telno = data[3];
        customer.balance = Double.parseDouble(data[4]);

        return customer;
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

    public Double getBalance() {
        return balance;
    }

    public Integer getCustid() {
        return custid;
    }

    @Override
    public boolean equals(Object obj) {
        Customers otherCustomer = (Customers) obj;

        return this.custname.equals(otherCustomer.custname);
    }
}
