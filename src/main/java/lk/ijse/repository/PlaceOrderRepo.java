package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {
    public static boolean placeOrder(PlaceOrder order1) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = OrderRepo.save(order1.getOrder());
            System.out.println("isOrderSaved: " + isOrderSaved);
            if (isOrderSaved) {
                boolean isOrderDetailSaved = OrderDetailRepo.save(order1.getOrderDetails());
                System.out.println("isOrderDetailSaved: " + isOrderDetailSaved);
                if (isOrderDetailSaved) {
                    boolean isPaymentSaved = PaymentRepo.save(order1.getPayment());
                    System.out.println("isPaymentSaved: " + isPaymentSaved);
                    if (isPaymentSaved) {
                        boolean isItemUpdated = ItemRepo.update(order1.getItem());
                        System.out.println("isItemUpdated: " + isItemUpdated);
                        if (isItemUpdated) {
                            connection.commit();
                            return true;
                        }
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
 }
}
}
