package alistair.data;

import alistair.business.Product;
import alistair.utility.ConnectionPool;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDB {
    private static Connection conn;

    private static ResultSet resultSet;

    private static PreparedStatement insert;
    private static PreparedStatement update;
    private static PreparedStatement select;
    private static PreparedStatement delete;

    public static boolean insert(Product product){
        try {
            conn = ConnectionPool.getConnection();

            assert conn != null;
            insert = conn.prepareStatement("INSERT INTO product(productCode, description, barcode, unitPrice, " +
                    "stocksOnHand, reorderLevel, categoryNo) VALUES (?,?,?,?,?,?,?)");
            insert.setString(1,product.getProductCode());
            insert.setString(2, product.getDescription());
            insert.setString(3, product.getBarcode());
            insert.setDouble(4, product.getUnitPrice());
            insert.setInt(5, product.getStocksOnHand());
            insert.setInt(6, product.getReorderLevel());
            insert.setInt(7, product.getCategoryNo());

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

    public static boolean update(int id, Product product){
        try {
            conn = ConnectionPool.getConnection();

            assert conn != null;
            update = conn.prepareStatement("UPDATE product SET productCode = ?, description = ?, barcode = ?," +
                    "unitPrice = ?, stocksOnHand = ?, reorderLevel = ?, categoryNo = ? WHERE id = ?");
            update.setString(1,product.getProductCode());
            update.setString(2, product.getDescription());
            update.setString(3, product.getBarcode());
            update.setDouble(4, product.getUnitPrice());
            update.setInt(5, product.getStocksOnHand());
            update.setInt(6, product.getReorderLevel());
            update.setInt(7, product.getCategoryNo());
            update.setInt(8,id);

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

    public static ArrayList<Product> getAllProducts(){
        ArrayList<Product> products;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM product");

            resultSet = select.executeQuery();
            products = new ArrayList<>();

            while (resultSet.next()){
                products.add( new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("productCode"),
                        resultSet.getString("description"),
                        resultSet.getString("barcode"),
                        resultSet.getDouble("unitPrice"),
                        resultSet.getInt("stocksOnHand"),
                        resultSet.getInt("reorderLevel"),
                        resultSet.getInt("categoryNo")
                ));
            }
            return products;
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

    public static Product getProduct(int id){
        Product product = null;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM product WHERE id = ?");
            select.setInt(1,id);

            resultSet = select.executeQuery();

            if (resultSet.next()){
                product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("productCode"),
                        resultSet.getString("description"),
                        resultSet.getString("barcode"),
                        resultSet.getDouble("unitPrice"),
                        resultSet.getInt("stocksOnHand"),
                        resultSet.getInt("reorderLevel"),
                        resultSet.getInt("categoryNo")
                );
            }
            return product;
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

    public static boolean productExists(int id){
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM product WHERE id = ?");
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
            delete = conn.prepareStatement("DELETE FROM product WHERE id = ?");
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
