package alistair.business;

import java.text.NumberFormat;

public class Product {
    private int id;
    private String productCode;
    private String description;
    private String barcode;
    private double unitPrice;
    private int stocksOnHand;
    private int reorderLevel;
    private int categoryNo;

    public Product() {
    }

    public Product(int id, String productCode, String description, String barcode, double unitPrice,
                   int stocksOnHand, int reorderLevel, int categoryNo) {
        this.id = id;
        this.productCode = productCode;
        this.description = description;
        this.barcode = barcode;
        this.unitPrice = unitPrice;
        this.stocksOnHand = stocksOnHand;
        this.reorderLevel = reorderLevel;
        this.categoryNo = categoryNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStocksOnHand() {
        return stocksOnHand;
    }

    public void setStocksOnHand(int stocksOnHand) {
        this.stocksOnHand = stocksOnHand;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public int getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(int categoryNo) {
        this.categoryNo = categoryNo;
    }

    public String getUnitPriceFormatted(){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(getUnitPrice());
    }
}
