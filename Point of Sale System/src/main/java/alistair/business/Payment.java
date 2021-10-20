package alistair.business;

import java.text.NumberFormat;

public class Payment {
    private int id;
    private int invoiceNo;
    private double cash;
    private double change;

    public Payment() {    }

    public Payment(int id, int invoiceNo, double cash, double change) {
        this.id = id;
        this.invoiceNo = invoiceNo;
        this.cash = cash;
        this.change = change;
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

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public String getCashFormatted(){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(getCash());
    }

    public String getChangeFormatted(){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(getChange());
    }
}
