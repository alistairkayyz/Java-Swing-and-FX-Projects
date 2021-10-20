DROP DATABASE IF EXISTS hospital;
CREATE DATABASE hospital;
USE hospital;

DROP TABLE IF EXISTS Patients;
CREATE TABLE  Patients (
    patientID int primary key,
    title varchar(10),
    firstname varchar(25),
    lastname varchar(25),
    dateOfBirth date,
    gender varchar(10),
    maritalStatus varchar(20),
    phone varchar(20),
    email varchar(100),
    homeLanguage varchar(20),
    nationality varchar(20),
    streetAddress varchar(100),
    suburb varchar(50),
    city varchar(50),
    postCode varchar(4),
    regDate date
);

DROP TABLE IF EXISTS Doctors;
CREATE TABLE  Doctors (
      doctorID int primary key,
      title varchar(10),
      firstname varchar(25),
      lastname varchar(25),
      dateOfBirth date,
      gender varchar(10),
      maritalStatus varchar(20),
      phone varchar(20),
      email varchar(100),
      homeLanguage varchar(20),
      nationality varchar(20),
      streetAddress varchar(100),
      suburb varchar(50),
      city varchar(50),
      postCode varchar(4),
      designation varchar(100),
      regDate date
);

DROP TABLE IF EXISTS Nurses;
CREATE TABLE  Nurses (
      nurseID int primary key,
      title varchar(10),
      firstname varchar(25),
      lastname varchar(25),
      dateOfBirth date,
      gender varchar(10),
      maritalStatus varchar(20),
      phone varchar(20),
      email varchar(100),
      homeLanguage varchar(20),
      nationality varchar(20),
      streetAddress varchar(100),
      suburb varchar(50),
      city varchar(50),
      postCode varchar(4),
      designation varchar(100),
      regDate date
); 

DROP TABLE IF EXISTS Records;
CREATE TABLE Records(
    recordID int primary key,
    symptoms varchar(255),
    appDate date,
    appTime time,
    status int not null default 0,
    diagnosis varchar(1000),
    prescription varchar(255),
    patientID int,
    doctorID int,
    foreign key(patientID) references patients(patientID) on delete set null,
    foreign key(doctorID) references doctors(doctorID) on delete set null
);

DROP TABLE IF EXISTS Users;
CREATE TABLE Users(
	userID int not null unique,
    password varchar(255),
    role int,
    primary key(userID)
);

INSERT INTO doctors (
    doctorID, title, firstname, lastname, dateOfBirth,
    gender, maritalStatus, phone, email, homeLanguage,
    nationality, streetAddress, suburb, city, postCode
    , designation, regDate)
    VALUES (101,'Dr.','Joseph','Workman','1985-09-24',
            'Male','Married','+27 11 806 4140',
            'jworkman@hospital.co.za','English',
            'South African','134 Lonely Oak Drive',
            'Alabama','Mobile','0366',
            'General Practitioner','2010-02-01'),
           (102,'Dr.','Mary','Mickey','1989-03-21',
            'Female','Married','+27 11 806 4141',
            'mmickey@hospital.co.za','English',
            'South African','202 Emerson Road',
            'Doyline','Louisiana','0874',
            'General Surgeon','2013-03-05');

INSERT INTO nurses (
    nurseID, title, firstname, lastname, dateOfBirth,
    gender, maritalStatus, phone, email, homeLanguage,
    nationality, streetAddress, suburb, city, postCode
    , designation, regDate)
VALUES (1001,'Ms.','Violet','Kelly','1990-09-08',
        'Female','Single','+27 11 806 1245',
        'vkelly@hospital.co.za','English',
        'South African','3922 Harvest Lane',
        'Bakersfield','California','3301',
        ' Licensed Practical Nurse','2013-02-01'),
       (1002,'Mr.','Ray','Bence','1992-03-21',
        'Male','Married','+27 11 806 1246',
        'rbence@hospital.co.za','English',
        'South African','1556 Heavner Court',
        'Des Moines','Iowa','0309',
        'Registered Nurse','2015-03-05');


