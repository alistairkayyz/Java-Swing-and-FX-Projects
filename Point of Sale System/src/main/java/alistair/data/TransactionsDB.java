package alistair.data;

import alistair.business.Transaction;
import alistair.utility.ConnectionPool;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionsDB {
    private static Connection conn;

    private static ResultSet resultSet;

    private static PreparedStatement insert;
    private static PreparedStatement update;
    private static PreparedStatement select;
    private static PreparedStatement delete;

    public static boolean insert(Transaction transaction){
        try {
            conn = ConnectionPool.getConnection();

            assert conn != null;
            insert = conn.prepareStatement("INSERT INTO transactions (date, time, subTotal, vatAmount, totalAmount, " +
                    "staffID, status) VALUES (?,?,?,?,?,?,?)");
            insert.setDate(1,transaction.getDate());
            insert.setTime(2,transaction.getTime());
            insert.setDouble(3,transaction.getSubTotal());
            insert.setDouble(4,transaction.getVatAmount());
            insert.setDouble(5,transaction.getTotalAmount());
            insert.setInt(6,transaction.getStaffID());
            insert.setInt(7,transaction.getStatus());
            
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

    public static boolean update(int id, Transaction transaction){
        try {
            conn = ConnectionPool.getConnection();

            assert conn != null;
            update = conn.prepareStatement("UPDATE transactions SET date = ?, time = ?, subTotal = ?, vatAmount = ?," +
                    "totalAmount = ?,staffID = ?,status = ? WHERE id = ?");
            update.setDate(1,transaction.getDate());
            update.setTime(2,transaction.getTime());
            update.setDouble(3,transaction.getSubTotal());
            update.setDouble(4,transaction.getVatAmount());
            update.setDouble(5,transaction.getTotalAmount());
            update.setInt(6,transaction.getStaffID());
            update.setInt(7,transaction.getStatus());
            update.setInt(6,id);

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

    public static ArrayList<Transaction> getAllTransactions(){
        ArrayList<Transaction> transactions;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM transactions");

            resultSet = select.executeQuery();
            transactions = new ArrayList<>();

            while (resultSet.next()){
                transactions.add( new Transaction(
                        resultSet.getInt("id"),
                        resultSet.getDate("date"),
                        resultSet.getTime("time"),
                        resultSet.getDouble("subTotal"),
                        resultSet.getDouble("vatAmount"),
                        resultSet.getDouble("totalAmount"),
                        resultSet.getInt("staffID"),
                        resultSet.getInt("status")
                ));
            }
            return transactions;
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

    public static Transaction getTransaction(int id){
        Transaction transaction = null;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM transactions WHERE id = ?");
            select.setInt(1,id);

            resultSet = select.executeQuery();

            if (resultSet.next()){
                transaction = new Transaction(
                        resultSet.getInt("id"),
                        resultSet.getDate("date"),
                        resultSet.getTime("time"),
                        resultSet.getDouble("subTotal"),
                        resultSet.getDouble("vatAmount"),
                        resultSet.getDouble("totalAmount"),
                        resultSet.getInt("staffID"),
                        resultSet.getInt("status")
                );
            }
            return transaction;
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

    public static boolean transactionExists(int id){
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM transactions WHERE id = ?");
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

    public static int getLastInvoiceNumber(){
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT MAX(id) AS 'id' FROM transactions");

            resultSet = select.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("id");
            else
                return 0;
        }
        catch (SQLException sqlException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText(sqlException.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            return 0;
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
            delete = conn.prepareStatement("DELETE FROM transactions WHERE id = ?");
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
