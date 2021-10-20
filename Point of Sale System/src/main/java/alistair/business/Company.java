package alistair.business;

public class Company {
    private int id;
    private String name;
    private String address;
    private String phoneNo;
    private String email;
    private String website;
    private String TINNumber;
    private int HInvoice;

    public Company(int id, String name, String address, String phoneNo, String email,
                   String website, String TINNumber, int HInvoice) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
        this.website = website;
        this.TINNumber = TINNumber;
        this.HInvoice = HInvoice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTINNumber() {
        return TINNumber;
    }

    public void setTINNumber(String TINNumber) {
        this.TINNumber = TINNumber;
    }

    public int getHInvoice() {
        return HInvoice;
    }

    public void setHInvoice(int HInvoice) {
        this.HInvoice = HInvoice;
    }
}
