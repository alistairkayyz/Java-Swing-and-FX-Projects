package alistair.data;

import alistair.business.Employee;
import alistair.utility.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class NurseDB {

    public static Employee getNurse(int id){
        ResultSet resultSet = null;
        Employee employee = null;
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement select = null;

        try {
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM nurses WHERE nurseID = ?");
            select.setInt(1, id);

            resultSet = select.executeQuery();

            if (resultSet.next()){
                employee = new Employee(
                        resultSet.getInt("nurseID"),
                        resultSet.getString("title"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getDate("dateOfBirth"),
                        resultSet.getString("gender"),
                        resultSet.getString("maritalStatus"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("homeLanguage"),
                        resultSet.getString("nationality"),
                        resultSet.getString("streetAddress"),
                        resultSet.getString("suburb"),
                        resultSet.getString("city"),
                        resultSet.getString("postCode"),
                        resultSet.getDate("regDate"),
                        resultSet.getString("designation")
                );
            }
            return employee;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        finally {
            try {
                assert select != null;
                select.close();

                assert resultSet != null;
                resultSet.close();

                ConnectionPool.closeConnection(conn);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    public static boolean nurseExists(int id){
        return getNurse(id) != null;
    }

}














