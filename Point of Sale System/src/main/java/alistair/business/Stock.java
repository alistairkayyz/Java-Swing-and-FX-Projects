package alistair.business;

public class Stock {
    private int id;
    private int productNo;
    private int quantity;
    private String dateIn;

    public Stock() {
    }

    public Stock(int id, int productNo, int quantity, String dateIn) {
        this.id = id;
        this.productNo = productNo;
        this.quantity = quantity;
        this.dateIn = dateIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }
}
