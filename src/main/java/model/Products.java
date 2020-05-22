package model;

public class Products {
    private String prodid;
    private String description;
    private double price;
    private String summary;

    public Products() {}

    public Products(String prodid, String description, double price) {
        this.prodid = prodid;
        this.description = description;
        this.price = price;
    }

    public String getProdid() {
        return prodid;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}
