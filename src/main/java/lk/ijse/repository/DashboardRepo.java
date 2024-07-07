package lk.ijse.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardRepo {

    private static final String URL = "jdbc:mysql://localhost:3306/wijesinghegrocery"; // Replace with your database URL
    private static final String USER = "root"; // Replace with your database username
    private static final String PASSWORD = "Ijse@123"; // Replace with your database password

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String getTopCustomer() {
        String query = "SELECT C_ID, COUNT(*) AS order_count FROM orders GROUP BY C_ID ORDER BY order_count DESC LIMIT 1";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString("C_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTopSellItem() {
        String query = "SELECT I_ID, COUNT(*) AS item_count FROM order_detail GROUP BY I_ID ORDER BY item_count DESC LIMIT 1";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString("I_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getOrderCount() {
        String query = "SELECT COUNT(O_ID) AS total_orders FROM orders";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("total_orders");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
