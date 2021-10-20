package alistair.business;

import java.text.NumberFormat;

public class InvoiceLine {
    private int productID;
    private String description;
    private double itemPrice;
    private int quantity;

    public InvoiceLine() {
    }

    public InvoiceLine(int productID, String description, double itemPrice, int quantity) {
        this.productID = productID;
        this.description = description;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemPriceFormatted() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(getItemPrice());
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return getQuantity() * getItemPrice();
    }

    public String getTotalPriceFormatted() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(getTotalPrice());
    }
}
