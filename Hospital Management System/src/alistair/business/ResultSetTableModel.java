package alistair.business;

import alistair.utility.ConnectionPool;

import javax.swing.table.AbstractTableModel;
import java.sql.*;

public class ResultSetTableModel extends AbstractTableModel {
    private final Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int numberOFRows;
    private boolean connectedToDB;

    public ResultSetTableModel(String query) {
        connection = ConnectionPool.getConnection();

        assert connection != null;
        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        connectedToDB = true;

        setQuery(query);
    }


    public Class getColumnClass(int column){
        if (!connectedToDB)
            throw new IllegalStateException("Not Connected to Database");

        try {
            String className = metaData.getColumnClassName(column + 1);
            return Class.forName(className);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Object.class;
    }


    @Override
    public int getRowCount(){
        if (!connectedToDB)
            throw new IllegalStateException("Not connected to Database");

        return numberOFRows;
    }

    @Override
    public int getColumnCount() {
        if (!connectedToDB)
            throw new IllegalStateException("Not Connected To Database");

        try {
            return metaData.getColumnCount();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getColumnName(int column) {
        if (!connectedToDB)
            throw new IllegalStateException("Not Connected To Database");

        try {
            return metaData.getColumnName(column + 1);
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (!connectedToDB)
            throw new IllegalStateException("Not Connected to Database");

        try {
            resultSet.absolute(rowIndex + 1);

            return resultSet.getObject(columnIndex + 1);
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return "";
    }

    public void setQuery(String query){
        if (!connectedToDB)
            throw new IllegalStateException("Not Connected To Database");

        try {
            resultSet = statement.executeQuery(query);
            metaData = resultSet.getMetaData();

            resultSet.last();
            numberOFRows = resultSet.getRow();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        fireTableStructureChanged();
    }
}
