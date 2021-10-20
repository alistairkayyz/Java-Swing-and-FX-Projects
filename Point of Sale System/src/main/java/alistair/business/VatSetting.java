package alistair.business;

public class VatSetting {
    private int id;
    private double vatPercent;

    public VatSetting() {
    }

    public VatSetting(int id, double vatPercent) {
        this.id = id;
        this.vatPercent = vatPercent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVatPercent() {
        return vatPercent;
    }

    public void setVatPercent(double vatPercent) {
        this.vatPercent = vatPercent;
    }
}
