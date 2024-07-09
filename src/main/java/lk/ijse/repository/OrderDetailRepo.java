package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailRepo {
    public static boolean save(List<OrderDetail> orderDetails) {
        for (OrderDetail Od : orderDetails) {
            try {
                PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO order_detail VALUES(?,?,?,)");
                stm.setObject(1, Od.getI_ID());
                stm.setObject(2, Od.getO_ID());
                stm.setObject(3, Od.getTotalprice());
                stm.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
        }
        return true;

    }
}
