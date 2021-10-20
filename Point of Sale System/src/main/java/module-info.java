module alistair.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens alistair to javafx.fxml;
    exports alistair;
    exports alistair.controllers;
    opens alistair.controllers to javafx.fxml;
}