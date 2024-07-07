package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
import lk.ijse.Util.TextField;
import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Forget_PasswordFormController {
    @FXML
    public JFXTextField txtUserId;
    public JFXTextField txtusername;
    public JFXTextField txtnewpassword;


    @FXML
    void resetbtnOnAction(ActionEvent event) throws SQLException {
        String userId = txtUserId.getText();
        String username = txtusername.getText();
        String newPassword = txtnewpassword.getText();

        if (userId.isEmpty() || username.isEmpty() || newPassword.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }

        try {
            Connection conn = DbConnection.getInstance().getConnection();
            String checkUserSql = "SELECT * FROM user WHERE U_ID = ? AND U_name = ?";
            PreparedStatement checkUserStmt = conn.prepareStatement(checkUserSql);
            checkUserStmt.setString(1, userId);
            checkUserStmt.setString(2, username);

            ResultSet rs = checkUserStmt.executeQuery();

            if (rs.next()) {

                String updatePasswordSql = "UPDATE user SET U_Password = ? WHERE U_ID = ? AND U_name = ?";
                PreparedStatement updatePasswordStmt = conn.prepareStatement(updatePasswordSql);
                updatePasswordStmt.setString(1, newPassword);
                updatePasswordStmt.setString(2, userId);
                updatePasswordStmt.setString(3, username);

                int rowsUpdated = updatePasswordStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    showAlert("Password has been reset successfully.");
                    clearTextFields();


                } else {
                    showAlert("Failed to reset the password. Please try again.");
                }

            } else {
                showAlert("User ID or Username is incorrect.");
            }

        } catch (SQLException e) {
            showAlert("A database error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    @FXML
    private void clearTextFields() {
        txtUserId.setText("");
        txtusername.setText("");
        txtnewpassword.setText("");
    }
    @FXML
    public void txtUserNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NAME, txtusername);
    }
    @FXML
    public void txtUserIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.UserID, txtUserId);
    }
    @FXML
    public void txtPasswordOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.PASSWORD, txtnewpassword);
}

    public void resetonaction(ActionEvent actionEvent) {
    }
}
