package model;

public class Products {
    private String prodid;
    private String description;
    private double price;

    public Products() {}

    public Products(String prodid, String description, double price) {
        this.prodid = prodid;
        this.description = description;
        this.price = price;
    }

    public static Products parseProduct(String stringToParse) {
        Products product = new Products();
        String[] data = stringToParse.split(",");

        product.prodid = data[0];
        product.description = data[1];
        product.price = Double.parseDouble(data[2]);

        return product;
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

    @Override
    public boolean equals(Object object) {
        Products otherProduct = (Products) object;

        return this.description.equals(otherProduct.description);
    }

    @Override
    public String toString() {
        return prodid + " " + description + " " + price;
    }
}
