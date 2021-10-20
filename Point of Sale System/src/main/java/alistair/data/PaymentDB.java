package alistair.data;

import alistair.business.Payment;
import alistair.utility.ConnectionPool;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDB {
    private static Connection conn;

    private static ResultSet resultSet;

    private static PreparedStatement insert;
    private static PreparedStatement update;
    private static PreparedStatement select;
    private static PreparedStatement delete;

    public static boolean insert(Payment payment){
        try {
            conn = ConnectionPool.getConnection();

            assert conn != null;
            insert = conn.prepareStatement("INSERT INTO payment(invoiceNo, cash, `change`) VALUES (?,?,?)");
            insert.setInt(1,payment.getInvoiceNo());
            insert.setDouble(2,payment.getCash());
            insert.setDouble(3,payment.getChange());

            return insert.executeUpdate() > 0;
        }
        catch (SQLException sqlException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText(sqlException.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            return false;
        }
        finally {
            try {
                assert insert != null;
                insert.close();
                ConnectionPool.closeConnection(conn);
            }
            catch (SQLException sqlException) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Database Error");
                alert.setHeaderText(null);
                alert.setContentText(sqlException.getMessage());
                alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            }
        }
    }

    public static boolean update(int id, Payment payment){
        try {
            conn = ConnectionPool.getConnection();

            assert conn != null;
            update = conn.prepareStatement("UPDATE payment SET invoiceNo = ?, cash = ?, `change` = ? WHERE id = ?");
            update.setInt(1,payment.getInvoiceNo());
            update.setDouble(2,payment.getCash());
            update.setDouble(3,payment.getChange());
            update.setInt(4,id);

            return update.executeUpdate() > 0;
        }
        catch (SQLException sqlException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText(sqlException.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            return false;
        }
        finally {
            try {
                assert update != null;
                update.close();
                ConnectionPool.closeConnection(conn);
            }
            catch (SQLException sqlException) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Database Error");
                alert.setHeaderText(null);
                alert.setContentText(sqlException.getMessage());
                alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            }
        }
    }

    public static ArrayList<Payment> getAllPayments(){
        ArrayList<Payment> payments;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM payment");

            resultSet = select.executeQuery();
            payments = new ArrayList<>();

            while (resultSet.next()){
                payments.add( new Payment(
                        resultSet.getInt("id"),
                        resultSet.getInt("invoiceNo"),
                        resultSet.getDouble("cash"),
                        resultSet.getDouble("change")
                ));
            }
            return payments;
        }
        catch (SQLException sqlException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText(sqlException.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            return null;
        }
        finally {
            try {
                assert select != null;
                select.close();
                ConnectionPool.closeConnection(conn);
            }
            catch (SQLException sqlException) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Database Error");
                alert.setHeaderText(null);
                alert.setContentText(sqlException.getMessage());
                alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            }
        }
    }

    public static Payment getPayment(int id){
        Payment payment = null;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM payment WHERE id = ?");
            select.setInt(1,id);

            resultSet = select.executeQuery();

            if (resultSet.next()){
                payment = new Payment(
                        resultSet.getInt("id"),
                        resultSet.getInt("invoiceNo"),
                        resultSet.getDouble("cash"),
                        resultSet.getDouble("change")
                );
            }
            return payment;
        }
        catch (SQLException sqlException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText(sqlException.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            return null;
        }
        finally {
            try {
                assert select != null;
                select.close();
                ConnectionPool.closeConnection(conn);
            }
            catch (SQLException sqlException) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Database Error");
                alert.setHeaderText(null);
                alert.setContentText(sqlException.getMessage());
                alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            }
        }
    }

    public static boolean paymentExists(int id){
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM payment WHERE id = ?");
            select.setInt(1,id);

            return select.executeQuery().next();
        }
        catch (SQLException sqlException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText(sqlException.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            return false;
        }
        finally {
            try {
                assert select != null;
                select.close();
                ConnectionPool.closeConnection(conn);
            }
            catch (SQLException sqlException) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Database Error");
                alert.setHeaderText(null);
                alert.setContentText(sqlException.getMessage());
                alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            }
        }
    }

    public static boolean delete(int id){
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            delete = conn.prepareStatement("DELETE FROM payment WHERE id = ?");
            delete.setInt(1,id);

            return delete.executeUpdate() > 0;
        }
        catch (SQLException sqlException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText(sqlException.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            return false;
        }
        finally {
            try {
                assert delete != null;
                delete.close();
                ConnectionPool.closeConnection(conn);
            }
            catch (SQLException sqlException) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Database Error");
                alert.setHeaderText(null);
                alert.setContentText(sqlException.getMessage());
                alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            }
        }
    }
}
