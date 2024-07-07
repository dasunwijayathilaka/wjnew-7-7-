package lk.ijse.controller;


import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Util.Regex;
import lk.ijse.Util.TextField;
import lk.ijse.model.Customer;
import lk.ijse.model.tm.CustomerTm;
import lk.ijse.repository.CustomerRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

    public JFXTextField txtCustomerID5;
    @FXML
    private TableColumn<?, ?> colCustomerAddress;

    @FXML
    private TableColumn<?, ?> colCustomerEmail;

    @FXML
    private TableColumn<?, ?> colCustomerID;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colCustomerTel;

    @FXML
    private TableColumn<?, ?> colloyaltypoint;

    @FXML
    private AnchorPane mainAnchopane;

    @FXML
    private TableView<CustomerTm> tabelcustomer;

    @FXML
    private TableColumn<?, ?> colCustomer;

    @FXML
    private JFXTextField txtCustomerAddress;

    @FXML
    private JFXTextField txtCustomerEmail;

    @FXML
    private JFXTextField txtCustomerID;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtCustomerTel;

    @FXML
    private JFXTextField txtloyaltypoint;

    @FXML
    private Label user;

    public void initialize() {
        loadAllCustomer();
        setCellValueFactory();
    }

    private void loadAllCustomer() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        try{
            List<Customer> customerList = CustomerRepo.getAll();
            for (Customer customer : customerList) {
                CustomerTm tm = new CustomerTm(customer.getC_ID(),customer.getC_Name(),customer.getC_Address(),customer.getC_Email(),
                        customer.getC_Contact_Number(),customer.getL_Points());
                obList.add(tm);
            }
            tabelcustomer.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("C_ID"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("C_Name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("C_Address"));
        colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("C_Email"));
        colCustomerTel.setCellValueFactory(new PropertyValueFactory<>("C_Contact_Number"));
        colloyaltypoint.setCellValueFactory(new PropertyValueFactory<>("L_Points"));


    }

    @FXML
    void btnBackOnAction(ActionEvent event)throws IOException {

        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/Dashbord_Form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.mainAnchopane.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearField();
    }

    private void clearField() {
        txtCustomerEmail.setText("");
        txtCustomerID.setText("");
        txtCustomerName.setText("");
        txtCustomerTel.setText("");
        txtloyaltypoint.setText("");
        txtCustomerAddress.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String customerID = txtCustomerID.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(customerID);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Deleted").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Not Deleted").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtCustomerID.getText();
        String name = txtCustomerName.getText();
        String tel = txtCustomerTel.getText();
        String address = txtCustomerAddress.getText();
        String email = txtCustomerEmail.getText();
        int points = Integer.parseInt(txtloyaltypoint.getText());

        Customer customer = new Customer(id, name, address, email, tel, points);

        try{
            boolean isSaved = CustomerRepo.save(customer);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.CONFIRMATION, "Customer unaved").show();

        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtCustomerID.getText();
        String name = txtCustomerName.getText();
        String tel = txtCustomerTel.getText();
        String address = txtCustomerAddress.getText();
        String email = txtCustomerEmail.getText();
        int points = Integer.parseInt(txtloyaltypoint.getText());

        Customer customer = new Customer(id, name, address, email, tel, points);

        try{
            boolean isUpdate = CustomerRepo.update(customer);
            if(isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void txtCustomerIdOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtCustomerID.getText();

        Customer customer = CustomerRepo.searchById(id);
        if (customer != null) {
            txtCustomerID.setText(customer.getC_ID());
            txtCustomerName.setText(customer.getC_Name());
            txtCustomerTel.setText(customer.getC_Contact_Number());
            txtCustomerAddress.setText(customer.getC_Address());
            txtCustomerEmail.setText(customer.getC_Email());
            txtloyaltypoint.setText(String.valueOf(customer.getL_Points()));

        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }
    }


    @FXML
    public void txtCustomerIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.CUSTID,txtCustomerID);
    }
    @FXML
    public void txtCustomerNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.NAME,txtCustomerName);
    }
    @FXML
    public void txtCustomerTelOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.PHONE,txtCustomerTel);
    }
    @FXML
    public void txtCustomerAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.ADDRESS,txtCustomerAddress);
    }
    @FXML
    public void txtCustomerEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.EMAIL,txtCustomerEmail);
    }
    @FXML
    public void txtloyaltypointOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.QTY,txtloyaltypoint);
}






}
