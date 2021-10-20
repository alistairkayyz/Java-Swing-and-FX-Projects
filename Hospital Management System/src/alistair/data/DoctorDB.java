package alistair.data;

import alistair.business.Employee;
import alistair.utility.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;

public final class DoctorDB {
    private static int numberOfColumns;
    private static ArrayList<String> columnNames;

    public static int getNumberOfColumns() {
        return numberOfColumns;
    }

    public static void setNumberOfColumns(int numberOfColumns) {
        DoctorDB.numberOfColumns = numberOfColumns;
    }

    public static ArrayList<String> getColumnNames() {
        return columnNames;
    }

    public static void setColumnNames(ArrayList<String> columnNames) {
        DoctorDB.columnNames = columnNames;
    }

    public static boolean insertDoctor(Employee employee){
        Connection connection = null;
        PreparedStatement insert = null;
        try {
            connection = ConnectionPool.getConnection();
            assert connection != null;
            insert = connection.prepareStatement(
                    "INSERT INTO doctors (doctorID, title, firstname, lastname, dateOfBirth, " +
                            "gender, maritalStatus, phone, email, homeLanguage," +
                            "nationality, streetAddress, suburb, city, postCode, designation, regDate) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            insert.setInt(1, employee.getId());
            insert.setString(2, employee.getTitle());
            insert.setString(3, employee.getFirstname());
            insert.setString(4, employee.getLastname());
            insert.setDate(5, employee.getDateOfBirth());
            insert.setString(6, employee.getGender());
            insert.setString(7, employee.getMaritalStatus());
            insert.setString(8, employee.getPhone());
            insert.setString(9, employee.getEmail());
            insert.setString(10, employee.getLanguage());
            insert.setString(11, employee.getNationality());
            insert.setString(12, employee.getStreetAddress());
            insert.setString(13, employee.getSuburb());
            insert.setString(14, employee.getCity());
            insert.setString(15, employee.getPostCode());
            insert.setString(16, employee.getDesignation());
            insert.setDate(17, employee.getRegDate());

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

                ConnectionPool.closeConnection(connection);
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    public static Employee getDoctor(int id){
        ResultSet resultSet = null;
        Employee employee = null;
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement select = null;

        try {
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM doctors WHERE doctorID = ?");
            select.setInt(1, id);

            resultSet = select.executeQuery();

            if (resultSet.next()){
                employee = new Employee(
                        resultSet.getInt("doctorID"),
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

    public static ArrayList<Object> getDoctors(){
        ResultSet resultSet = null;
        ArrayList<Object> employees;
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement select = null;

        try {
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM doctors");

            resultSet = select.executeQuery();

            // get number of columns
            ResultSetMetaData metaData = resultSet.getMetaData();
            setNumberOfColumns(metaData.getColumnCount());

            // get column names
            ArrayList<String> names = new ArrayList<>();

            for (int i = 1; i <= getNumberOfColumns(); i++) {
                names.add(metaData.getColumnName(i));
            }

            setColumnNames(names);

            employees = new ArrayList<>();

            while (resultSet.next()){
                employees.add(new Employee(
                        resultSet.getInt("doctorID"),
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
                ));
            }
            return employees;
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

    public static boolean doctorExists(int id){
        return getDoctor(id) != null;
    }

}














