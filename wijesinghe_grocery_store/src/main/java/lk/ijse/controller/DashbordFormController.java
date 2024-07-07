package lk.ijse.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashbordFormController {


    public AnchorPane rootNode;
    public Button btnSupplierOnAction;
    public Label dateLabel;
    public Label timeLabel;

    @FXML
    private Pane contentPane;

    public void initialize() {
        startDateTimeService();
    }
    private void startDateTimeService() {
        Thread dateTimeThread = new Thread(() -> {
            while (true) {
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                Platform.runLater(() -> {
                    dateLabel.setText(dateFormatter.format(localDate));
                    timeLabel.setText(timeFormatter.format(localTime));

                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        dateTimeThread.setDaemon(true);
        dateTimeThread.start();
}
    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        try {
            Node customerForm = FXMLLoader.load( this.getClass().getResource("/view/Customer_Form.fxml") );
            contentPane.getChildren().setAll(customerForm);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        try {
            Node supplierForm = FXMLLoader.load( this.getClass().getResource("/view/Supplier_Form.fxml") );
            contentPane.getChildren().setAll(supplierForm);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void btnOnActionItem(ActionEvent event) throws IOException {
        try {
            Node itemform = FXMLLoader.load( this.getClass().getResource("/view/Item_Form.fxml") );
            contentPane.getChildren().setAll(itemform);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void btnOnActionReturn(ActionEvent event) throws IOException {
        try {
            Node returnExchangform= FXMLLoader.load( this.getClass().getResource("/view/Return_Form.fxml") );
            contentPane.getChildren().setAll(returnExchangform);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void btnOnActionOrder(ActionEvent event) throws IOException {
        try {
            Node orderForm = FXMLLoader.load( this.getClass().getResource("/view/PlaceOrder_Form.fxml") );
            contentPane.getChildren().setAll(orderForm);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void btnOnActionStore(ActionEvent event) {
        try {
            Node storeform = FXMLLoader.load( this.getClass().getResource("/view/store_Form.fxml") );
            contentPane.getChildren().setAll(storeform);

        }catch (Exception e){
            e.printStackTrace();
        }

    }




    public void logoutonaction(ActionEvent actionEvent) throws IOException {
        // Load the login form
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(rootNode);

        // Get the current stage from an existing scene node (using the action event source)
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Set the new scene on the existing stage
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
        stage.show(); // Ensure the stage is shown, logging the user out of the dashboard
    }
}
