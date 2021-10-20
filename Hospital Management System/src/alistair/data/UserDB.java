package alistair.data;

import alistair.business.User;
import alistair.utility.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class UserDB {
    public static boolean insert(User user){
        Connection conn = null;
        PreparedStatement insert = null;

        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            insert = conn.prepareStatement("INSERT INTO users (userID,password,role)" +
                    "VALUES (?,?,?)");
            insert.setInt(1,user.getId());
            insert.setString(2, user.getPassword());
            insert.setInt(3, user.getRole());

            return insert.executeUpdate() > 0;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        finally {
            try {
                assert insert != null;
                insert.close();
                ConnectionPool.closeConnection(conn);
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    public static boolean updatePassword(User user, int id){
        Connection conn = null;
        PreparedStatement update = null;

        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            update = conn.prepareStatement("UPDATE users SET password = ? WHERE userID = ?");
            update.setString(1, user.getPassword());
            update.setInt(2,id);

            return update.executeUpdate() > 0;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        finally {
            try {
                assert update != null;
                update.close();
                ConnectionPool.closeConnection(conn);
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    public static User getUser(int id){
        Connection conn = null;
        User user = null;
        PreparedStatement select = null;
        ResultSet resultSet = null;

        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM users WHERE userID = ?");
            select.setInt(1,id);

             resultSet = select.executeQuery();

             if (resultSet.next()){
                 user = new User(
                         resultSet.getInt("userID"),
                         resultSet.getString("password"),
                         resultSet.getInt("role")
                 );
             }
             return user;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        finally {
            try {
                assert select != null;
                select.close();
                ConnectionPool.closeConnection(conn);
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
}
