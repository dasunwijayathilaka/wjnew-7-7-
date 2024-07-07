package lk.ijse.repository;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplyFormRepo {

    public List<String> getAllStoreIDs() throws SQLException {
        List<String> storeIDs = new ArrayList<>();
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "SELECT St_ID FROM store";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            storeIDs.add(resultSet.getString("St_ID"));
        }
        return storeIDs;
    }

    public List<String> getAllSupplierIDs() throws SQLException {
        List<String> supplierIDs = new ArrayList<>();
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "SELECT S_ID FROM supplier";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            supplierIDs.add(resultSet.getString("S_ID"));
        }
        return supplierIDs;
    }

    public String getSupplierNameByID(String supplierID) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "SELECT S_Name FROM supplier WHERE S_ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, supplierID);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("S_Name");
        }
        return null;
    }

    public String[] getStoreDetailsByID(String storeID) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "SELECT St_Name, Qty_On_Hand FROM store WHERE St_ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, storeID);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new String[]{resultSet.getString("St_Name"), resultSet.getString("Qty_On_Hand")};
        }
        return null;
    }
}
