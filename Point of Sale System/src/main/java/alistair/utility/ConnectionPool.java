package alistair.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionPool {
    private static final String URL = "jdbc:mysql://localhost:3306/point_of_sale";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public static void closeConnection(Connection con){
        if (con != null){
            try {
                con.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
}
