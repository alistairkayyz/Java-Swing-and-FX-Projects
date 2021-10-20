package alistair.controllers;

import alistair.Main;
import alistair.data.StaffDB;
import alistair.utility.Session;
import alistair.utility.Validate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    private void login(){

        String userID = username.getText();
        String pass = password.getText();

        if (Validate.word(userID) && StaffDB.staffExists(userID)){

            if (StaffDB.getStaffPassword(userID).equals(pass)){
                Session.setId(StaffDB.getStaffID(userID));

                username.setText("");
                password.setText("");

                if (StaffDB.getStaffRole(userID).equalsIgnoreCase("cashier")){
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("cashier-view.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(),1080,720);
                        Main.getCashierStage().setScene(scene);
                        Main.getCashierStage().show();
                        Main.getLoginStage().hide();
                    }
                    catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
                else {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("dashboard-view.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        Main.getDashboardStage().setScene(scene);
                        Main.getDashboardStage().show();
                        Main.getLoginStage().hide();
                    }
                    catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText(e.getMessage());
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.showAndWait();
                    }
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid PASSWORD!");
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid USERNAME!");
            alert.showAndWait();
        }
    }

}