package alistair.data;

import alistair.business.Staff;
import alistair.utility.ConnectionPool;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StaffDB {
    private static Connection conn;

    private static ResultSet resultSet;

    private static PreparedStatement insert;
    private static PreparedStatement update;
    private static PreparedStatement select;
    private static PreparedStatement delete;

    public static boolean insert(Staff staff){
        try {
            conn = ConnectionPool.getConnection();

            assert conn != null;
            insert = conn.prepareStatement("INSERT INTO staff(firstname, lastname, mobile, email, country, " +
                    "addressLine1, addressLine2, city, postalCode, username, role, password, companyID) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            insert.setString(1,staff.getFirstname());
            insert.setString(2,staff.getLastname());
            insert.setString(3,staff.getMobile());
            insert.setString(4,staff.getEmail());
            insert.setString(5,staff.getCountry());
            insert.setString(6,staff.getAddressLine1());
            insert.setString(7,staff.getAddressLine2());
            insert.setString(8,staff.getCity());
            insert.setString(9,staff.getPostalCode());
            insert.setString(10,staff.getUsername());
            insert.setString(11,staff.getRole());
            insert.setString(12,staff.getPassword());
            insert.setInt(13,staff.getCompanyID());

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

    public static boolean update(int id, Staff staff){
        try {
            conn = ConnectionPool.getConnection();

            assert conn != null;
            update = conn.prepareStatement("UPDATE staff SET firstname = ?, lastname = ?, mobile = ?, email = ?, " +
                    "country = ?, addressLine1 = ?, addressLine2 = ?, city = ?, postalCode = ?, username = ?, role = ?, " +
                    "password = ?, companyID = ? WHERE id = ?");
            update.setString(1,staff.getFirstname());
            update.setString(2,staff.getLastname());
            update.setString(3,staff.getMobile());
            update.setString(4,staff.getEmail());
            update.setString(5,staff.getCountry());
            update.setString(6,staff.getAddressLine1());
            update.setString(7,staff.getAddressLine2());
            update.setString(8,staff.getCity());
            update.setString(9,staff.getPostalCode());
            update.setString(10,staff.getUsername());
            update.setString(11,staff.getRole());
            update.setString(12,staff.getPassword());
            update.setInt(13,staff.getCompanyID());
            update.setInt(14,id);

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

    public static ArrayList<Staff> getAllStaff(){
        ArrayList<Staff> staff;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM staff");

            resultSet = select.executeQuery();
            staff = new ArrayList<>();

            while (resultSet.next()){
                staff.add( new Staff(
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("mobile"),
                        resultSet.getString("email"),
                        resultSet.getString("country"),
                        resultSet.getString("addressLine1"),
                        resultSet.getString("addressLine2"),
                        resultSet.getString("city"),
                        resultSet.getString("postalCode"),
                        resultSet.getString("username"),
                        resultSet.getString("role"),
                        resultSet.getString("password"),
                        resultSet.getInt("companyID")
                ));
            }
            return staff;
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

    public static Staff getStaff(int id){
        Staff staff = null;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM staff WHERE id = ?");
            select.setInt(1,id);

            resultSet = select.executeQuery();

            if (resultSet.next()){
                staff = new Staff(
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("mobile"),
                        resultSet.getString("email"),
                        resultSet.getString("country"),
                        resultSet.getString("addressLine1"),
                        resultSet.getString("addressLine2"),
                        resultSet.getString("city"),
                        resultSet.getString("postalCode"),
                        resultSet.getString("username"),
                        resultSet.getString("role"),
                        resultSet.getString("password"),
                        resultSet.getInt("companyID")
                );
            }
            return staff;
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

    public static int getStaffID(String username){
        int id = -1;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT id FROM staff WHERE username = ?");
            select.setString(1,username);

            resultSet = select.executeQuery();

            if (resultSet.next())
                id = resultSet.getInt("id");

            return id;
        }
        catch (SQLException sqlException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText(sqlException.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            return id;
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

    public static String getStaffPassword(String username){
        String password = "";
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT password FROM staff WHERE username = ?");
            select.setString(1,username);

            resultSet = select.executeQuery();

            if (resultSet.next())
                password = resultSet.getString("password");

            return password;
        }
        catch (SQLException sqlException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText(sqlException.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            return password;
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

    public static String getStaffRole(String username){
        String role = "";
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT role FROM staff WHERE username = ?");
            select.setString(1,username);

            resultSet = select.executeQuery();

            if (resultSet.next())
                role = resultSet.getString("role");

            return role;
        }
        catch (SQLException sqlException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText(sqlException.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            return role;
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

    public static boolean staffExists(int id){
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM staff WHERE id = ?");
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

    public static boolean staffExists(String username){
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM staff WHERE username = ?");
            select.setString(1,username);

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
            delete = conn.prepareStatement("DELETE FROM staff WHERE id = ?");
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
