package lk.ijse.repository;

import lk.ijse.db.DbConnection;

import lk.ijse.entity.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {
    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM Supplier";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Supplier> supList = new ArrayList<>();

        while (resultSet.next()) {
            String S_ID = resultSet.getString(1);
            String S_Name = resultSet.getString(2);
            String S_Address = resultSet.getString(3);
            String S_Email = resultSet.getString(4);
            String S_Contact_Number = resultSet.getString(5);
            String Delivery_Schedule = resultSet.getString(6);

            Supplier Supplier = new Supplier(S_ID, S_Name, S_Address,S_Email,S_Contact_Number,Delivery_Schedule);
            supList.add(Supplier);
        }
        return supList;
    }


    public static boolean save(Supplier Supplier) throws SQLException {
        String sql = "INSERT INTO Supplier VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, Supplier.getS_ID());
        pstm.setString(2, Supplier.getS_Name());
        pstm.setString(3, Supplier.getS_Address());
        pstm.setString(4, Supplier.getS_Email());
        pstm.setString(5, Supplier.getS_Contact_Number());
        pstm.setString(6, Supplier.getDelivery_Schedule());

        return pstm.executeUpdate()>0;

    }


    public static Supplier searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Supplier WHERE S_ID = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String S_ID = resultSet.getString(1);
            String S_Name = resultSet.getString(2);
            String S_Address = resultSet.getString(3);
            String S_Email = resultSet.getString(4);
            String S_Contact_Number = resultSet.getString(5);
            String Delivery_Schedule = resultSet.getString(6);


            Supplier Supplier = new Supplier(S_ID, S_Name, S_Address,S_Email, S_Contact_Number,Delivery_Schedule);

            return Supplier;
        }

        return null;
    }

    public static boolean update(Supplier Supplier) throws SQLException {

        String sql = "UPDATE supplier SET S_ID = ?, S_Name = ?,S_Address = ?,S_Email = ?,S_Contact_Number = ?,Delivery_Schedule = ? WHERE S_ID = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, Supplier.getS_ID());
        pstm.setString(2, Supplier.getS_Name());
        pstm.setString(3, Supplier.getS_Address());
        pstm.setString(4, Supplier.getS_Email());
        pstm.setString(5, Supplier.getS_Contact_Number());
        pstm.setString(6, Supplier.getDelivery_Schedule());
        pstm.setString(7, Supplier.getS_ID());


        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String SupplierID) throws SQLException {
        String sql = "DELETE FROM Supplier WHERE S_ID = ?";

        PreparedStatement pstm =DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, SupplierID);
        return pstm.executeUpdate() > 0;
    }
}
