package alistair.business;

import java.text.NumberFormat;

public class TransactionDetails {
    private int id;
    private int invoiceNo;
    private int productNo;
    private double itemPrice;
    private int quantity;
    private double discount;

    public TransactionDetails() {
    }

    public TransactionDetails(int id, int invoiceNo, int productNo, double itemPrice, int quantity, double discount) {
        this.id = id;
        this.invoiceNo = invoiceNo;
        this.productNo = productNo;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(int invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getItemPriceFormatted(){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(getItemPrice());
    }

    public String getDiscountFormatted(){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(getDiscount());
    }
}
