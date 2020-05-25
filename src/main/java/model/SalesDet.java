package model;

public class SalesDet {
    private int qtysold;
    private Products product;
    private String summary;

    public SalesDet(Products product, int qtysold) {
        this.product = product;
        this.qtysold = qtysold;
        summary = qtysold + " x " + product.getDescription();
    }

    public int getQtysold() {
        return qtysold;
    }


    public String getSummary() {
        return summary;
    }

    public Products getProduct() {
        return product;
    }
}
