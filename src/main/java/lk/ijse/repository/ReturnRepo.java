package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.entity.returnexchange;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReturnRepo {
    public static List<returnexchange> getAll() throws SQLException {
        String sql = "SELECT * FROM RETURN_EXCHANGE";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<returnexchange> returnList = new ArrayList<>();

        while (resultSet.next()) {
            String return_id = resultSet.getString("R_ID");
            String order_id = resultSet.getString("O_ID");
            Date return_date = resultSet.getDate("R_Date");
            double amount = resultSet.getDouble("Refund_Amount");
            String return_reason = resultSet.getString("R_Reason");
            boolean exchangeRequest = resultSet.getBoolean("Exchange_Request");
            String status = resultSet.getString("R_Status");

            System.out.println("Fetched Order ID: " + order_id); // Debug statement

            returnexchange ret = new returnexchange(return_id, order_id, return_reason, status, exchangeRequest, return_date, amount);
            returnList.add(ret);
        }
        return returnList;
}


    public static boolean delete(String returnID) {
        String sql = "DELETE FROM return_exchange WHERE R_ID = ?";
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, returnID);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean save(returnexchange ret) {
        String sql = "INSERT INTO return_exchange (R_ID, O_ID, R_Date, Refund_Amount, R_Reason, Exchange_Request, R_Status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, ret.getR_ID());
            pstm.setString(2, ret.getO_ID());
            pstm.setDate(3, ret.getR_Date());
            pstm.setDouble(4, ret.getRefund_Amount());
            pstm.setString(5, ret.getR_Reason());
            pstm.setBoolean(6, ret.isExchange_Request());
            pstm.setString(7, ret.getR_Status());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static returnexchange searchById(String id) throws SQLException {
        String sql = "SELECT * FROM return_exchange WHERE R_ID = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String return_id = resultSet.getString("R_ID");
            String order_id = resultSet.getString("O_ID");
            Date return_date = resultSet.getDate("R_Date");
            double amount = resultSet.getDouble("Refund_Amount");
            String return_reason = resultSet.getString("R_Reason");
            boolean exchangeRequest = resultSet.getBoolean("Exchange_Request");
            String status = resultSet.getString("R_Status");

            returnexchange ret = new returnexchange(return_id, order_id, return_reason, status, exchangeRequest, return_date, amount);
            return ret;
        }
        return null;
    }

    public static boolean update(returnexchange ret) throws SQLException {
        String sql = "UPDATE return_exchange SET O_ID = ?, R_Date = ?, Refund_Amount = ?, R_Reason = ?, Exchange_Request = ?, R_Status = ? WHERE R_ID = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, ret.getO_ID());
        pstm.setDate(2, ret.getR_Date());
        pstm.setDouble(3, ret.getRefund_Amount());
        pstm.setString(4, ret.getR_Reason());
        pstm.setBoolean(5, ret.isExchange_Request());
        pstm.setString(6, ret.getR_Status());
        pstm.setString(7, ret.getR_ID());
        return pstm.executeUpdate()>0;
}
}
