
package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {

    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM customer";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Customer> cusList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String email = resultSet.getString(4);
            String phone = resultSet.getString(5);
            int age = resultSet.getInt(6);

            Customer customer = new Customer(id, name, address,email,phone,age);
            cusList.add(customer);
        }
        return cusList;
    }


    public static boolean save(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?)";

             Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);

             pstm.setString(1, customer.getC_ID());
             pstm.setString(2, customer.getC_Name());
             pstm.setString(3, customer.getC_Address());
             pstm.setString(4, customer.getC_Email());
             pstm.setString(5, customer.getC_Contact_Number());
             pstm.setInt(6, customer.getL_Points());

             return pstm.executeUpdate()>0;

    }

    public static List<String> getAllCustomerIDs() throws SQLException {
        List<String> customerIDs = new ArrayList<>();
        String sql = "SELECT C_ID FROM customer";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            customerIDs.add(rs.getString("C_ID"));
        }

        return customerIDs;
    }








    public static Customer searchById(String id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE C_ID = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String mail = resultSet.getString(4);
            String tel = resultSet.getString(5);
            int points = resultSet.getInt(6);

            Customer customer = new Customer(cus_id, name, address,mail, tel,points);

            return customer;
        }

        return null;
    }

    public static boolean update(Customer customer) throws SQLException {

        String sql = "UPDATE customer SET C_ID = ?, C_Name = ?,C_Address = ?,C_Email = ?,C_Contact_Number = ?,L_Points = ? WHERE C_ID = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, customer.getC_ID());
        pstm.setString(2, customer.getC_Name());
        pstm.setString(3, customer.getC_Address());
        pstm.setString(4, customer.getC_Email());
        pstm.setString(5, customer.getC_Contact_Number());
        pstm.setInt(6, customer.getL_Points());
        pstm.setString(7, customer.getC_ID());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String customerID) throws SQLException {
        String sql = "DELETE FROM customer WHERE C_ID = ?";

        PreparedStatement pstm =DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, customerID);
        return pstm.executeUpdate() > 0;
    }
}

