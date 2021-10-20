package alistair.data;

import alistair.business.Patient;
import alistair.business.User;
import alistair.utility.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class PatientDB {

    public static boolean insertPatient(Patient patient){
        Connection connection = null;
        PreparedStatement insertPatient = null;
        try {
            connection = ConnectionPool.getConnection();
            assert connection != null;
            insertPatient = connection.prepareStatement(
                    "INSERT INTO patients (patientID, title, firstname, lastname, dateOfBirth, " +
                            "gender, maritalStatus, phone, email, homeLanguage," +
                            "nationality, streetAddress, suburb, city, postCode, regDate) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            insertPatient.setInt(1, patient.getId());
            insertPatient.setString(2, patient.getTitle());
            insertPatient.setString(3, patient.getFirstname());
            insertPatient.setString(4, patient.getLastname());
            insertPatient.setDate(5, patient.getDateOfBirth());
            insertPatient.setString(6, patient.getGender());
            insertPatient.setString(7, patient.getMaritalStatus());
            insertPatient.setString(8, patient.getPhone());
            insertPatient.setString(9, patient.getEmail());
            insertPatient.setString(10, patient.getLanguage());
            insertPatient.setString(11, patient.getNationality());
            insertPatient.setString(12, patient.getStreetAddress());
            insertPatient.setString(13, patient.getSuburb());
            insertPatient.setString(14, patient.getCity());
            insertPatient.setString(15, patient.getPostCode());
            insertPatient.setDate(16, patient.getRegDate());

            User user = new User(patient.getId(),"default",1);

            return insertPatient.executeUpdate() > 0 && UserDB.insert(user);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        finally {
            try {
                assert insertPatient != null;
                insertPatient.close();

                ConnectionPool.closeConnection(connection);
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
    public static int getLastPatientID(){
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement select = null;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT MAX(patientID) AS id FROM patients");

            resultSet = select.executeQuery();

            if (resultSet.next())
                return resultSet.getInt("id");
            else
                return -1;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return -1;
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
    public static boolean updatePatient(Patient patient, int id){
        Connection conn = null;
        PreparedStatement update = null;

        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;

            update = conn.prepareStatement("UPDATE patients SET " +
                    "title = ?, firstname = ?, lastname = ?, dateOfBirth = ?, gender = ?," +
                    " maritalStatus = ?, phone = ?, email = ?, homeLanguage = ?,nationality = ?," +
                    " streetAddress = ?, suburb = ?, city = ?, postCode = ? WHERE patientID = ?");

            update.setString(1, patient.getTitle());
            update.setString(2, patient.getFirstname());
            update.setString(3, patient.getLastname());
            update.setDate(4, patient.getDateOfBirth());
            update.setString(5, patient.getGender() );
            update.setString(6, patient.getMaritalStatus());
            update.setString(7, patient.getPhone());
            update.setString(8, patient.getEmail());
            update.setString(9, patient.getLanguage());
            update.setString(10, patient.getNationality());
            update.setString(11, patient.getStreetAddress());
            update.setString(12, patient.getSuburb());
            update.setString(13, patient.getCity());
            update.setString(14, patient.getPostCode());
            update.setInt(15, id);

            //resultSet = update.executeQuery();

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
                //resultSet.close();
                ConnectionPool.closeConnection(conn);
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    public static Patient getPatient(int id){
        ResultSet resultSet = null;
        Patient patient = null;
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement select = null;

        try {
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM patients WHERE patientID = ?");
            select.setInt(1, id);

            resultSet = select.executeQuery();

            if (resultSet.next()){
                patient = new Patient(
                        resultSet.getInt("patientID"),
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
                        resultSet.getDate("regDate")
                );
            }
            return patient;
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

    public static boolean patientExists(int id){
        return getPatient(id) != null;
    }

    public static boolean patientDeleted(int id){
        PreparedStatement deletePatient = null;
        Connection conn = null;

        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            deletePatient = conn.prepareStatement("DELETE FROM patients WHERE patientID = ?");

            deletePatient.setInt(1,id);

            return deletePatient.executeUpdate() > 0;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        finally {
            try {
                assert deletePatient != null;
                deletePatient.close();
                ConnectionPool.closeConnection(conn);
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
}














