package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {

    public static List<Item> getAll() throws SQLException {
        String sql = "select * from item";

        try (PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            List<Item> itemList = new ArrayList<>();

            while (resultSet.next()) {
               String ItemID = resultSet.getString(1);
                String i_Name = resultSet.getString(2);
                String brands = resultSet.getString(3);
                int qty = resultSet.getInt(4);
                String description = resultSet.getString(5);
                double weight = resultSet.getDouble(6);
                String st_ID = resultSet.getString(7);
                double unit_price =resultSet.getDouble(8);

                Item item = new Item(ItemID, i_Name, brands, qty, description, weight, st_ID, unit_price);
                itemList.add(item);
            }

            return itemList;
        }
    }

    public static boolean save(Item item) throws SQLException {
        String sql = "INSERT INTO item (I_ID, I_Name, Brands, Qty, Description, Weight, St_ID, Unit_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, item.getI_ID());
            pstm.setString(2, item.getI_Name());
            pstm.setString(3, item.getBrands());
            pstm.setInt(4, item.getQty());
            pstm.setString(5, item.getDescription());
            pstm.setDouble(6, item.getWeight());
            pstm.setString(7, item.getSt_ID());
            pstm.setDouble(8, item.getUnit_price());

            return pstm.executeUpdate() > 0;
        }
    }

    public static Item searchById(String id) throws SQLException {
        String sql = "select * from item where I_ID = ?";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

           pstm.setString(1, id);
            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    String ItemID = resultSet.getString(1);
                    String i_Name = resultSet.getString(2);
                    String brands = resultSet.getString(3);
                    int qty = resultSet.getInt(4);
                    String description = resultSet.getString(5);
                    double weight = resultSet.getDouble(6);
                    String st_ID = resultSet.getString(7);
                    double unit_price = resultSet.getDouble(8);

                    return new Item(ItemID, i_Name, brands, qty, description, weight, st_ID, unit_price);
                }
            }
        }
        return null;
    }

    public static boolean update(Item item) throws SQLException {
        String sql = "UPDATE item SET I_ID = ?, I_Name= ?,Brands = ?, Qty = ?, Description = ?, Weight = ?, St_ID = ?, Unit_price = ? WHERE I_ID = ?";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

           pstm.setString(1, item.getI_ID());
            pstm.setString(1, item.getI_Name());
            pstm.setString(2, item.getBrands());
            pstm.setInt(3, item.getQty());
            pstm.setString(4, item.getDescription());
            pstm.setDouble(5, item.getWeight());
            pstm.setString(6, item.getSt_ID());
           pstm.setDouble(7, item.getUnit_price());


            return pstm.executeUpdate() > 0;
        }
    }

    public static boolean delete(String itemID) throws SQLException {
        String sql = "DELETE FROM item WHERE I_ID = ?";

        try (PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1, itemID);
            return pstm.executeUpdate() > 0;
        }
    }
}
