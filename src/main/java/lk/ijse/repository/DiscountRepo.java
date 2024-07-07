package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountRepo {
    public static List<String> getAllDiscountIDs() throws SQLException {
        List<String> customerIDs = new ArrayList<>();
        String sql = "SELECT D_ID FROM discount";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            customerIDs.add(rs.getString("D_ID"));
        }

        return customerIDs;
    }     public static Discount getDiscountById(String D_ID) throws SQLException {
        String sql = "SELECT D_ID, D_Name, D_Amount, V_Period, Grocery_Categories, D_Start_Date, D_End_Date FROM discount WHERE D_ID = ?";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, D_ID);

            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    Discount discount = new Discount();
                    discount.setD_ID(resultSet.getString("D_ID"));
                    discount.setD_Name(resultSet.getString("D_Name"));
                    discount.setD_Amount(resultSet.getDouble("D_Amount"));
                    discount.setV_Period(String.valueOf(resultSet.getString("V_Period")));
                    discount.setGrocery_Categories(resultSet.getString("Grocery_Categories"));
                    discount.setD_Start_Date(resultSet.getDate("D_Start_Date"));
                    discount.setD_End_Date(resultSet.getDate("D_End_Date"));
                    return discount;
                } else {
                    return null;
                }
            }
        }
    }


}

