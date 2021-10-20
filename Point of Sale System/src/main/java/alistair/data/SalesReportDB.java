package alistair.data;

import alistair.business.SalesReport;
import alistair.utility.ConnectionPool;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

import java.sql.*;
import java.util.ArrayList;

public class SalesReportDB {
    private static Connection conn;

    private static ResultSet resultSet;

    private static PreparedStatement select;

    public static ArrayList<SalesReport> getSalesReport(){
        ArrayList<SalesReport> salesReports;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT transactions.date, product.description, category.categoryName, " +
                    "transaction_details.quantity, transaction_details.itemPrice, transactions.totalAmount, staff.firstname\n" +
                    "FROM transactions\n" +
                    "JOIN transaction_details\n" +
                    "ON transactions.id = transaction_details.invoiceNo\n" +
                    "JOIN product\n" +
                    "ON transaction_details.productNo = product.id\n" +
                    "JOIN category\n" +
                    "ON category.id = product.categoryNo\n" +
                    "JOIN staff\n" +
                    "ON transactions.staffID = staff.id\n" +
                    "ORDER BY transactions.date");

            resultSet = select.executeQuery();
            salesReports = new ArrayList<>();

            int id = 1;
            while (resultSet.next()){
                salesReports.add( new SalesReport(
                        id,
                        resultSet.getDate("date"),
                        resultSet.getString("description"),
                        resultSet.getString("categoryName"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("itemPrice"),
                        resultSet.getDouble("totalAmount"),
                        resultSet.getString("firstname")
                ));
                id++;
            }
            return salesReports;
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

    public static ArrayList<SalesReport> getDaySalesReport(Date date){
        ArrayList<SalesReport> salesReports;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT transactions.date, product.description, category.categoryName, " +
                    "transaction_details.quantity, transaction_details.itemPrice, transactions.totalAmount, staff.firstname\n" +
                    "FROM transactions\n" +
                    "JOIN transaction_details\n" +
                    "ON transactions.id = transaction_details.invoiceNo\n" +
                    "JOIN product\n" +
                    "ON transaction_details.productNo = product.id\n" +
                    "JOIN category\n" +
                    "ON category.id = product.categoryNo\n" +
                    "JOIN staff\n" +
                    "ON transactions.staffID = staff.id\n" +
                    "WHERE transactions.date = ?\n" +
                    "ORDER BY transactions.date");
            select.setDate(1,date);

            resultSet = select.executeQuery();
            salesReports = new ArrayList<>();

            int id = 1;
            while (resultSet.next()){
                salesReports.add( new SalesReport(
                        id,
                        resultSet.getDate("date"),
                        resultSet.getString("description"),
                        resultSet.getString("categoryName"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("itemPrice"),
                        resultSet.getDouble("totalAmount"),
                        resultSet.getString("firstname")
                ));
                id++;
            }
            return salesReports;
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

    public static ArrayList<SalesReport> getCustomDateSalesReport(Date fromDate, Date toDate){
        ArrayList<SalesReport> salesReports;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT transactions.date, product.description, category.categoryName, " +
                    "transaction_details.quantity, transaction_details.itemPrice, transactions.totalAmount, staff.firstname\n" +
                    "FROM transactions\n" +
                    "JOIN transaction_details\n" +
                    "ON transactions.id = transaction_details.invoiceNo\n" +
                    "JOIN product\n" +
                    "ON transaction_details.productNo = product.id\n" +
                    "JOIN category\n" +
                    "ON category.id = product.categoryNo\n" +
                    "JOIN staff\n" +
                    "ON transactions.staffID = staff.id\n" +
                    "WHERE transactions.date between ? AND ? \n" +
                    "ORDER BY transactions.date");

            select.setDate(1,fromDate);
            select.setDate(2,toDate);

            resultSet = select.executeQuery();
            salesReports = new ArrayList<>();

            int id = 1;
            while (resultSet.next()){
                salesReports.add( new SalesReport(
                        id,
                        resultSet.getDate("date"),
                        resultSet.getString("description"),
                        resultSet.getString("categoryName"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("itemPrice"),
                        resultSet.getDouble("totalAmount"),
                        resultSet.getString("firstname")
                ));
                id++;
            }
            return salesReports;
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
}
