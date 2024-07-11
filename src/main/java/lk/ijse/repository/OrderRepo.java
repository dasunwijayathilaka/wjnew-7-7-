package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.Order;
import lk.ijse.entity.tm.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderRepo {
    public static boolean Save(List<CartItem> cartItems) {
        try {
            for (CartItem OrderCart : cartItems) {
                Connection connection = DbConnection.getInstance().getConnection();
                String sql = "INSERT INTO supply_store VALUES(?,?,?,?,?)";
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setObject(1, OrderCart.getSupplierId());
                pstm.setObject(2, OrderCart.getStoreId());
                pstm.setObject(3, OrderCart.getQuantity());
                pstm.setObject(4, OrderCart.getUnitPrice());
                pstm.setObject(5, OrderCart.getTotalPrice());

                if (pstm.executeUpdate() == 0) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String getCurrentId() throws SQLException {

        String sql = "SELECT O_ID FROM orders ORDER BY O_ID DESC LIMIT 1";
        try (Connection connection = DbConnection.dbConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        }
    }


    public static boolean save(Order order) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1, order.getO_ID());
            pstm.setObject(2, order.getU_ID());
            pstm.setObject(3, order.getC_ID());
            pstm.setObject(4, order.getD_ID());
            pstm.setObject(5, order.getP_ID());
            pstm.setObject(6, order.getL_Points_Earned());
            pstm.setObject(7, order.getO_Time());
            pstm.setObject(8, order.getO_Date());
            pstm.setObject(9, order.getTotal_Amount());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
 }
}
}
