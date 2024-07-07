package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentRepo {
    public static String getCurrentId() throws SQLException {

        String sql = "SELECT P_ID FROM payment ORDER BY P_ID DESC LIMIT 1";
        try (Connection connection = DbConnection.dbConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        }
    }

    public static boolean save(Payment payment) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "INSERT INTO payment VALUES(?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1, payment.getP_ID());
            pstm.setObject(2, payment.getP_Amount());
            pstm.setObject(3, payment.getTransaction_Date());
            pstm.setObject(4, payment.getTransaction_Time());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
 }
}
}
