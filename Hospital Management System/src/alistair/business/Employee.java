package alistair.business;

import java.sql.Date;

public class Employee extends Person{
    private String designation;

    public Employee(int id, String title, String firstname, String lastname,
                    Date dateOfBirth, String gender, String maritalStatus,
                    String phone, String email, String language, String nationality,
                    String streetAddress, String suburb, String city, String postCode,
                    Date regDate, String designation) {
        super(id, title, firstname, lastname, dateOfBirth, gender, maritalStatus, phone,
                email, language, nationality, streetAddress, suburb, city, postCode, regDate);
        this.designation = designation;
    }

    public Employee(){}

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
