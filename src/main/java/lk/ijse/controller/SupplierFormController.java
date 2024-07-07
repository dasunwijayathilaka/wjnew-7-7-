package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import lk.ijse.Util.Regex;
import lk.ijse.Util.TextField;
import lk.ijse.model.Supplier;

import lk.ijse.model.tm.SupplierrTm;

import lk.ijse.repository.SupplierRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    @FXML
    private TableColumn<?, ?> colDeliveryShedule;

    @FXML
    private TableColumn<?, ?> colSupplierAddress;

    @FXML
    private TableColumn<?, ?> colSupplierEmail;

    @FXML
    private TableColumn<?, ?> colSupplierID;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TableColumn<?, ?> colSupplierTel;

    @FXML
    private TableView<SupplierrTm> tblSupplierManage;

    @FXML
    private JFXTextField txtDeliveryShedule;

    @FXML
    private JFXTextField txtSupplierAddress;

    @FXML
    private JFXTextField txtSupplierEmail;

    @FXML
    private JFXTextField txtSupplierID;

    @FXML
    private JFXTextField txtSupplierName;

    @FXML
    private JFXTextField txtSupplierTel;

    public void initialize() {
        loadAllCustomer();
        setCellValueFactory();
    }
    @FXML
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        // Load the dashboard form
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/Dashbord_Form.fxml"));
        Scene scene = new Scene(rootNode);

        // Get the current stage from an existing scene node (using the action event source)
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Set the new scene on the existing stage
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
        stage.show();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearField();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String customerID = txtSupplierID.getText();

        try {
            boolean isDeleted = SupplierRepo.delete(customerID);
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
        String id = txtSupplierID.getText();
        String name = txtSupplierName.getText();
        String tel = txtSupplierTel.getText();
        String email = txtSupplierEmail.getText();
        String address = txtSupplierAddress.getText();
        String shedule = txtDeliveryShedule.getText();


        Supplier supplier = new Supplier(id, name, address, email, tel, shedule);

        try{
            boolean isSaved = SupplierRepo.save(supplier);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier unaved").show();

        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtSupplierID.getText();
        String name = txtSupplierName.getText();
        String tel = txtSupplierTel.getText();
        String email = txtSupplierEmail.getText();
        String address = txtSupplierAddress.getText();
        String shedule = txtDeliveryShedule.getText();

        Supplier supplier = new Supplier(id, name, address, email, tel, shedule);

        try{
            boolean isUpdate = SupplierRepo.update(supplier);
            if(isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void clearField() {
        txtSupplierID.setText("");
        txtSupplierName.setText("");
        txtSupplierTel.setText("");
        txtSupplierEmail.setText("");
        txtSupplierAddress.setText("");
        txtDeliveryShedule.setText("");
    }
    private void loadAllCustomer() {
        ObservableList<SupplierrTm> obList = FXCollections.observableArrayList();
        try{
            List<Supplier> supplierList = SupplierRepo.getAll();
            for (Supplier supplier : supplierList) {
                SupplierrTm tm = new SupplierrTm(supplier.getS_ID(),supplier.getS_Name(),supplier.getS_Address(),supplier.getS_Email(),
                        supplier.getS_Contact_Number(),supplier.getDelivery_Schedule());
                obList.add(tm);
            }
            tblSupplierManage.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private void setCellValueFactory() {
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("S_ID"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("S_Name"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("S_Address"));
        colSupplierEmail.setCellValueFactory(new PropertyValueFactory<>("S_Email"));
        colSupplierTel.setCellValueFactory(new PropertyValueFactory<>("S_Contact_Number"));
        colDeliveryShedule.setCellValueFactory(new PropertyValueFactory<>("Delivery_Schedule"));


    }
    @FXML
    void txtSupplierIdOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtSupplierID.getText();

        Supplier supplier = SupplierRepo.searchById(id);
        if (supplier != null) {
            txtSupplierID.setText(supplier.getS_ID());
            txtSupplierName.setText(supplier.getS_Name());
            txtSupplierTel.setText(supplier.getS_Contact_Number());
            txtSupplierAddress.setText(supplier.getS_Address());
            txtSupplierEmail.setText(supplier.getS_Email());
            txtDeliveryShedule.setText(String.valueOf(supplier.getDelivery_Schedule()));

        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }
    }
    @FXML
    public void txtSupplierIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.SUPID,txtSupplierID);
    }
    @FXML
    public void txtSupplierNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtSupplierName);
    }
    @FXML
    public void txtSupplierTelTelOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.PHONE,txtSupplierTel);
    }
    @FXML
    public void txtSupplierAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.ADDRESS,txtSupplierAddress);
    }
    @FXML
    public void txtSupplierEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.EMAIL,txtSupplierEmail);
    }
    @FXML
    public void txtDeliverySheduleOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.NAME,txtDeliveryShedule);
}

}
