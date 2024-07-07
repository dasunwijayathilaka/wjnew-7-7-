package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Store;
import lk.ijse.model.Supplier;
import lk.ijse.model.tm.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreRepo {
    public static List<Store> getAll() throws SQLException {
        String sql = "SELECT * FROM Store";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Store> storeList = new ArrayList<>();

        while (resultSet.next()) {
            String storeID = resultSet.getString(1);
            String storeName = resultSet.getString(2);
            String floorPlan = resultSet.getString(3);
            String description = resultSet.getString(4);
            String location = resultSet.getString(5);
            int qtyOnHand = resultSet.getInt(6);


            Store store = new Store(storeID, storeName, floorPlan, description, location, qtyOnHand);
            storeList.add(store);
        }
        return storeList;
    }

    public static boolean delete(String storeID) {
        String sql = "DELETE FROM Store WHERE St_ID = ?";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, storeID);

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    public static boolean save(Store store) {
        String sql = "INSERT INTO Store (St_ID, St_Name, Floor_Plan, Description, Location, Qty_On_Hand) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, store.getSt_ID());
            pstm.setString(2, store.getSt_Name());
            pstm.setString(3, store.getFloor_Plan());
            pstm.setString(4, store.getDescription());
            pstm.setString(5, store.getLocation());
            pstm.setInt(6, store.getQty_On_Hand());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }





    public static Store searchById(String id) throws SQLException {
        String sql = " SELECT * FROM Store WHERE St_ID = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String storeID = resultSet.getString(1);
            String storeName = resultSet.getString(2);
            String floorPlan = resultSet.getString(3);
            String description = resultSet.getString(4);
            String location = resultSet.getString(5);
            int qtyOnHand = resultSet.getInt(6);




            Store store = new Store(storeID, storeName, floorPlan,description, location,qtyOnHand);

            return store ;
        }

        return null;
    }













    public static boolean update(Store store) throws SQLException {
        String sql = "UPDATE Store SET St_Name = ?, Floor_Plan = ?, Description = ?, Location = ?, Qty_On_Hand = ? WHERE St_ID = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, store.getSt_Name());
        pstm.setString(2, store.getFloor_Plan());
        pstm.setString(3, store.getDescription());
        pstm.setString(4, store.getLocation());
        pstm.setInt(5, store.getQty_On_Hand());
        pstm.setString(6, store.getSt_ID());

        return pstm.executeUpdate() > 0;
    }


    public static boolean updateQty(List<CartItem> cartItems) {
        try {
            for (CartItem cartItem : cartItems) {
                String sql = "UPDATE Store SET Qty_On_Hand = Qty_On_Hand - ? WHERE St_ID = ?";
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setObject(1, cartItem.getQuantity());
                pstm.setObject(2, cartItem.getStoreId());
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
}
