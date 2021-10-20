package alistair.data;

import alistair.business.AppointmentRecord;
import alistair.business.Record;
import alistair.utility.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;

public final class RecordDB {
    private static int numberOfColumns;
    private static ArrayList<String> columnNames;

    public static ArrayList<String> getColumnNames() {
        return columnNames;
    }

    public static void setColumnNames(ArrayList<String> columnNames) {
        RecordDB.columnNames = columnNames;
    }

    public static int getNumberOfColumns() {
        return numberOfColumns;
    }

    public static void setNumberOfColumns(int numberOfColumns) {
        RecordDB.numberOfColumns = numberOfColumns;
    }

    public static boolean insert(Record record){
        Connection conn = null;
        PreparedStatement insert = null;

        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            insert = conn.prepareStatement("INSERT INTO records (" +
                    "recordID,symptoms,appDate,appTime,status,diagnosis," +
                    "prescription,patientID,doctorID) VALUES (?,?,?,?,?,?,?,?,?)");

            insert.setInt(1, record.getRecordID());
            insert.setString(2, record.getSymptoms());
            insert.setDate(3, record.getAppDate());
            insert.setTime(4,record.getAppTime());
            insert.setInt(5, record.getStatus());
            insert.setString(6, record.getDiagnosis());
            insert.setString(7, record.getPrescription());
            insert.setInt(8, record.getPatientID());
            insert.setInt(9, record.getDoctorID());

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

    public static boolean update(Record record, int id){
        Connection conn = null;
        PreparedStatement update = null;

        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            update = conn.prepareStatement("UPDATE records SET " +
                    "symptoms = ?, appDate = ?, appTime = ?, status = ?, " +
                    "diagnosis = ?, prescription = ?, patientID = ?," +
                    "doctorID = ? WHERE recordID = ?");

            update.setString(1, record.getSymptoms());
            update.setDate(2, record.getAppDate());
            update.setTime(3,record.getAppTime());
            update.setInt(4, record.getStatus());
            update.setString(5, record.getDiagnosis());
            update.setString(6, record.getPrescription());
            update.setInt(7, record.getPatientID());
            update.setInt(8, record.getDoctorID());
            update.setInt(9,id);

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

    public static Record getRecord(int id){
        Connection conn = null;
        ResultSet resultSet = null;
        Record record = null;
        PreparedStatement select = null;

        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM records WHERE recordID = ?");
            select.setInt(1, id);

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

             if (resultSet.next()){
                 record = new Record(
                         resultSet.getInt("recordID"),
                         resultSet.getString("symptoms"),
                         resultSet.getDate("appDate"),
                         resultSet.getTime("appTime"),
                         resultSet.getInt("status"),
                         resultSet.getString("diagnosis"),
                         resultSet.getString("prescription"),
                         resultSet.getInt("patientID"),
                         resultSet.getInt("doctorID")
                 );
             }
             return record;
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

    public static int getLastRecordID(){
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement select = null;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT MAX(recordID) AS id FROM records");

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
    public static ArrayList<Record> getPatientRecords(int id){
        Connection conn = null;
        ResultSet resultSet = null;
        ArrayList<Record> records;
        PreparedStatement select = null;

        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM records WHERE patientID = ?");
            select.setInt(1,id);

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

            records = new ArrayList<>();

            while (resultSet.next()){
                records.add(new Record(
                        resultSet.getInt("recordID"),
                        resultSet.getString("symptoms"),
                        resultSet.getDate("appDate"),
                        resultSet.getTime("appTime"),
                        resultSet.getInt("status"),
                        resultSet.getString("diagnosis"),
                        resultSet.getString("prescription"),
                        resultSet.getInt("patientID"),
                        resultSet.getInt("doctorID")
                ));
            }
            return records;
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

    public static boolean recordExists(int id){
        return getRecord(id) != null;
    }

    public static boolean doctorHasAppointment(int id, String date, String time){
        Connection conn = null;
        PreparedStatement select = null;
        ResultSet resultSet = null;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT * FROM records WHERE doctorID = ? AND appDate = ? AND appTime = ?");

            select.setInt(1,id);
            select.setDate(2,Date.valueOf(date));
            select.setTime(3,Time.valueOf(time));

            resultSet = select.executeQuery();

            return resultSet.next();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
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

    public static boolean patientHasRecords(int id){
        Connection conn = null;
        PreparedStatement select = null;
        ResultSet resultSet = null;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT COUNT(*) AS count FROM records WHERE patientID = ?");
            select.setInt(1,id);

            resultSet = select.executeQuery();

            if (resultSet.next())
                return resultSet.getInt("count") > 0;
            else
                return false;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
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

    public static int getRecordID(String date, String time, int doctorID, int recordID){
        Connection conn = null;
        PreparedStatement select = null;
        ResultSet resultSet = null;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT recordID FROM records WHERE recordID != ? AND doctorID = ? AND appDate = ? AND appTime = ?");

            select.setInt(1,recordID);
            select.setInt(2,doctorID);
            select.setDate(3,Date.valueOf(date));
            select.setTime(4,Time.valueOf(time));

            resultSet = select.executeQuery();

            return resultSet.next() ? resultSet.getInt("recordID") : -1;
        }
        catch (SQLException sqlException) {
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

    public static boolean isDoctorScheduleFull(int id, String date){
        Connection conn = null;
        PreparedStatement select = null;
        ResultSet resultSet = null;
        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT COUNT(*) AS count FROM records WHERE doctorID = ? AND appDate = ?");

            select.setInt(1,id);
            select.setDate(2,Date.valueOf(date));

            resultSet = select.executeQuery();

            if (resultSet.next()) // return true if the number of appointment does exceed #9
                return resultSet.getInt("count") > 9;
            else
                return false;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
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
    public static boolean recordDeleted(int id){
        PreparedStatement delete = null;
        Connection conn = null;

        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            delete = conn.prepareStatement("DELETE FROM records WHERE recordID = ?");

            delete.setInt(1,id);

            return delete.executeUpdate() > 0;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        finally {
            try {
                assert delete != null;
                delete.close();
                ConnectionPool.closeConnection(conn);
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    public static ArrayList<AppointmentRecord> getAppointmentRecords(int doctorID){
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement select = null;
        Record record = null;
        String firstname = "";
        String lastname = "";
        ArrayList<AppointmentRecord> records = null;

        try {
            conn = ConnectionPool.getConnection();
            assert conn != null;
            select = conn.prepareStatement("SELECT records.*, patients.firstname, patients.lastname " +
                    "FROM records JOIN patients ON records.patientID = patients.patientID " +
                    "WHERE records.status = 0 AND records.doctorID = ? ORDER BY appDATE, appTIME");
            select.setInt(1,doctorID);

            resultSet = select.executeQuery();
            records = new ArrayList<>();

            while (resultSet.next()){
                record = new Record(
                        resultSet.getInt("recordID"),
                        resultSet.getString("symptoms"),
                        resultSet.getDate("appDate"),
                        resultSet.getTime("appTime"),
                        resultSet.getInt("status"),
                        resultSet.getString("diagnosis"),
                        resultSet.getString("prescription"),
                        resultSet.getInt("patientID"),
                        resultSet.getInt("doctorID"));
                firstname = resultSet.getString("firstname");
                lastname = resultSet.getString("lastname");

                records.add(new AppointmentRecord(record,firstname,lastname));
            }
            return records;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        finally {
            try {
                assert select != null;
                select.close();
                resultSet.close();
                ConnectionPool.closeConnection(conn);
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
}
