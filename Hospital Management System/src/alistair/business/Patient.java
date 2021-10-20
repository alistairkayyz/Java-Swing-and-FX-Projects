package alistair.business;

import java.sql.Date;

public class Patient extends Person{
    private static Patient patient = null;
    public Patient(){}

    public Patient(int id, String title, String firstname, String lastname,
                   Date dateOfBirth, String gender, String maritalStatus,
                   String phone, String email, String language, String nationality,
                   String streetAddress, String suburb, String city,
                   String postCode, Date regDate) {
        super(id, title, firstname, lastname, dateOfBirth, gender,
                maritalStatus, phone, email, language, nationality,
                streetAddress, suburb, city, postCode, regDate);
    }

    public static synchronized Patient getInstance(){
        if (patient == null)
            patient = new Patient();
        return patient;
    }
}
