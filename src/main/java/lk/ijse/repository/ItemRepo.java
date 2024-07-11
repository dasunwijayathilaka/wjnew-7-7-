package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {

    public static List<Item> getAll() throws SQLException {
        String sql = "SELECT * FROM item";

        try (PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            List<Item> itemList = new ArrayList<>();

            while (resultSet.next()) {
                String i_ID = resultSet.getString("I_ID");
                String i_Name = resultSet.getString("I_Name");
                String brands = resultSet.getString("Brands");
                int qty = resultSet.getInt("Qty");
                String description = resultSet.getString("Description");
                double weight = resultSet.getDouble("Weight");
                String st_ID = resultSet.getString("St_ID");
                double unit_price = resultSet.getDouble("Unit_price");

                Item item = new Item(i_ID, i_Name, brands, qty, description, weight, st_ID, unit_price);
                itemList.add(item);
            }
            System.out.println(itemList);
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
        String sql = "SELECT * FROM item WHERE I_ID = ?";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, id);
            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    String i_ID = resultSet.getString("I_ID");
                    String i_Name = resultSet.getString("I_Name");
                    String brands = resultSet.getString("Brands");
                    int qty = resultSet.getInt("Qty");
                    String description = resultSet.getString("Description");
                    double weight = resultSet.getDouble("Weight");
                    String st_ID = resultSet.getString("St_ID");
                    double unit_price = resultSet.getDouble("Unit_price");

                    return new Item(i_ID, i_Name, brands, qty, description, weight, st_ID, unit_price);
                }
            }
        }
        return null;
    }



    public static List<String> getAllItemIds() throws SQLException {
        String sql = "SELECT I_ID FROM item";
        List<String> itemIds = new ArrayList<>();

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            while (resultSet.next()) {
                itemIds.add(resultSet.getString("I_ID"));
            }
        }
        return itemIds;
}


















    public static boolean update(Item item) throws SQLException {
        String sql = "UPDATE item SET I_Name = ?, Brands = ?, Qty = ?, Description = ?, Weight = ?, St_ID = ?, Unit_price = ? WHERE I_ID = ?";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, item.getI_Name());
            pstm.setString(2, item.getBrands());
            pstm.setInt(3, item.getQty());
            pstm.setString(4, item.getDescription());
            pstm.setDouble(5, item.getWeight());
            pstm.setString(6, item.getSt_ID());
            pstm.setDouble(7, item.getUnit_price());
            pstm.setString(8, item.getI_ID());

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
