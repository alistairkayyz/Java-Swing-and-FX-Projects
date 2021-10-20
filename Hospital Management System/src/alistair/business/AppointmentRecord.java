package alistair.business;

public class AppointmentRecord {
    private final Record record;
    private String firstname;
    private String lastname;

    public AppointmentRecord(Record record, String firstname, String lastname) {
        this.record = record;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Record getRecord() {
        return record;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
