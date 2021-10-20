package alistair.business;


import java.sql.Date;
import java.sql.Time;
import java.text.NumberFormat;
import java.util.Calendar;

public class Transaction {
    private int id;
    private Date date;
    private Time time;
    private double subTotal;
    private double vatAmount;
    private double totalAmount;
    private int staffID;
    private int status;

    public Transaction() {
    }

    public Transaction(int id, Date date, Time time, double subTotal, double vatAmount,
                       double totalAmount, int staffID, int status) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.subTotal = subTotal;
        this.vatAmount = vatAmount;
        this.totalAmount = totalAmount;
        this.staffID = staffID;
        this.status = status;
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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSubTotalFormatted(){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(getSubTotal());
    }

    public String getVatAmountFormatted(){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(getVatAmount());
    }

    public String getTotalAmountFormatted(){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(getTotalAmount());
    }
}
