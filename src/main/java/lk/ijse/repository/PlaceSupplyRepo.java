package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.PlaceSupply;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceSupplyRepo {
    public static boolean placeOredr(PlaceSupply placeSupply) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            try {
                boolean Issaved = OrderRepo.Save( placeSupply.getCartItems());
                if (Issaved) {
                    System.out.println("i came");
                    boolean isStoreQuentityUpdated = StoreRepo.updateQty(placeSupply.getCartItems());
                    if (isStoreQuentityUpdated) {
                        System.out.println("i came");
                        connection.commit();
                        return true;
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
