package alistair.data;

import alistair.business.Category;
import alistair.utility.ConnectionPool;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class CategoryDB {
    private static Connection conn;

    private static PreparedStatement insert;

    public static boolean insert(Category category){
        try {
            conn = ConnectionPool.getConnection();

            assert conn != null;
            insert = conn.prepareStatement("INSERT INTO category (categoryName, description) VALUES (?,?)");
            insert.setString(1,category.getCategoryName());
            insert.setString(2, category.getDescription());

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

}
