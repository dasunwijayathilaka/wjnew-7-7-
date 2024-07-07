package lk.ijse.controller;

import com.gluonhq.charm.glisten.control.TextField;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;
import lk.ijse.model.Store;
import lk.ijse.repository.StoreRepo;

import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML
    private JFXPasswordField TxtPassword;

    @FXML
    private JFXTextField TxtUserId;

    @FXML
    private void initialize() {
        // Initialization logic here
    }

    @FXML
    void ForgetPasswordOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Forget_Password_Form.fxml"));
            Parent rootNode = loader.load();
            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Wijesinghe Grocery Store");
            stage.show();
            stage.maximizedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) stage.setMaximized(false);
            });
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Unable to load the Forget Password form: " + e.getMessage());
        }
    }

    @FXML
    void LoginOnAction(ActionEvent event) {
        String userId = TxtUserId.getText();
        String password = TxtPassword.getText();

        try (Connection conn = DbConnection.getInstance().getConnection()) {
            // Check if the user ID exists
            String selectUserIdSql = "SELECT * FROM User WHERE U_ID = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectUserIdSql)) {
                selectStmt.setString(1, userId);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        // UserID exists, now check the password
                        if (rs.getString("U_Password").equals(password)) { // Consider using password hashing in production
                            navigateToTheDashboard();
                        } else {
                            showAlert(Alert.AlertType.ERROR, "Incorrect password. Please try again.");
                            TxtPassword.setText("");
                        }
                    } else {
                        showAlert(Alert.AlertType.ERROR, "User ID does not exist. Please try again or sign up.");
                        TxtUserId.setText("");
                        TxtPassword.setText("");
                    }
                }
            }
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String content) {
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void navigateToTheDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashbord_Form.fxml"));
        Parent rootNode = loader.load();
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Wijesinghe Grocery Store Dashboard");
        stage.show();
        closeStage(); // Close the current (login) stage
    }

    private void closeStage() {
        Stage currentStage = (Stage) TxtUserId.getScene().getWindow();
        currentStage.close();
    }
}
