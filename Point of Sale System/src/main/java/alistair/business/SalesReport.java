package alistair.business;

import java.sql.Date;
import java.text.NumberFormat;

public class SalesReport {
    private int id;
    private Date date;
    private String description;
    private String categoryName;
    private int quantity;
    private double itemPrice;
    private double totalAmount;
    private String staffName;

    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

    public SalesReport() {
    }

    public SalesReport(int id, Date date, String description, String categoryName, int quantity, double itemPrice,
                       double totalAmount, String staffName) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.categoryName = categoryName;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.totalAmount = totalAmount;
        this.staffName = staffName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }
    public String getItemPriceFormatted() {
        return numberFormat.format(getItemPrice());
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public String getTotalAmountFormatted() {
        return numberFormat.format(getTotalAmount());
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
