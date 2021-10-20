package alistair;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    private static Stage dashboardStage;
    private static Stage loginStage;
    private static Stage cashierStage;

    @Override
    public void start(Stage stage) throws IOException {
        loginStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        loginStage.setTitle("Login");
        loginStage.setScene(scene);
        loginStage.setResizable(false);
        loginStage.show();

        initCashierStage();
        initLoginStage();
    }

    public void initCashierStage(){
        cashierStage = new Stage();
        cashierStage.setTitle("Cashier");
        cashierStage.alwaysOnTopProperty();
        cashierStage.initStyle(StageStyle.TRANSPARENT);
    }

    public static Stage getCashierStage() {
        return cashierStage;
    }

    public void initLoginStage(){
        dashboardStage = new Stage();
        dashboardStage.setTitle("Dashboard!");
        dashboardStage.alwaysOnTopProperty();
        dashboardStage.initStyle(StageStyle.DECORATED);
    }

    public static Stage getLoginStage() {
        return loginStage;
    }

    public static Stage getDashboardStage() {
        return dashboardStage;
    }

    public static void main(String[] args) {

        launch(); }
}