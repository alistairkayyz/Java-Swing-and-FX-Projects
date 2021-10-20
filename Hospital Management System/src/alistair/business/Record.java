package alistair.business;

import java.sql.Date;
import java.sql.Time;

public class Record {
    private int recordID;
    private String symptoms;
    private Date appDate;
    private Time appTime;
    private int status;
    private String diagnosis;
    private String prescription;
    private int patientID;
    private int doctorID;

    private static Record record = null;

    public static synchronized Record getInstance(){
        if (record == null)
            record = new Record();
        return record;
    }

    public Record(){}

    public Record(int recordID, String symptoms, Date appDate,
                  Time appTime, int status, String diagnosis,
                  String prescription, int patientID, int doctorID) {
        this.recordID = recordID;
        this.symptoms = symptoms;
        this.appDate = appDate;
        this.appTime = appTime;
        this.status = status;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.patientID = patientID;
        this.doctorID = doctorID;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public Time getAppTime() {
        return appTime;
    }

    public void setAppTime(Time appTime) {
        this.appTime = appTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }
}
