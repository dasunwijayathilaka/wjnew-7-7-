package lk.ijse.controller;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import lk.ijse.model.Item;
import lk.ijse.model.returnexchange;
import lk.ijse.model.tm.ReturnTM;
import lk.ijse.repository.ItemRepo;
import lk.ijse.repository.ReturnRepo;


import java.io.IOException;
import java.sql.Date;

import java.sql.SQLException;
import java.util.List;

public class ReturnFormController {

    @FXML
    private TableColumn<?, ?> colExchangeRequest;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private TableColumn<?, ?> colRefundAmount;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableColumn<?, ?> colReturnID;

    @FXML
    private TableColumn<?, ?> colReturnReason;

    @FXML
    private TableColumn<?, ?> colReturnStatus;

    @FXML
    private TableColumn<?, ?> colReturnTime;

    @FXML
    private TableView<ReturnTM> tblReturnExchange;

    @FXML
    private TextField txtExchangeRequest;

    @FXML
    private TextField txtOrderID;

    @FXML
    private TextField txtRefundAmount;

    @FXML
    private TextField txtReturnDate;

    @FXML
    private TextField txtReturnID;

    @FXML
    private TextField txtReturnReason;

    @FXML
    private TextField txtReturnStatus;

    @FXML
    private TextField txtReturnTime;



    public void initialize() {
        setCelllFactory();
        allLoadReturn();


    }

    public void setCelllFactory() {

        colReturnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        colReturnReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
        colExchangeRequest.setCellValueFactory(new PropertyValueFactory<>("Request"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colReturnStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colRefundAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));


    }


    public void allLoadReturn(){
        ObservableList<ReturnTM> obList = FXCollections.observableArrayList();
        try{
            List<returnexchange> returnexchangeList = ReturnRepo.getAll();
            for ( returnexchange returnexchange : returnexchangeList) {
                ReturnTM tm = new ReturnTM(returnexchange.getR_ID(),returnexchange. getO_ID(),returnexchange.getR_Reason(),returnexchange.getR_Status(),returnexchange.isExchange_Request(),returnexchange.getR_Date(),returnexchange.getRefund_Amount());
                obList.add(tm);
            }
            tblReturnExchange.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        ClearFileds();
    }

    private void ClearFileds() {
        txtReturnID.setText("");
        txtOrderID.setText("");
        txtReturnReason.setText("");
        txtExchangeRequest.setText("");
        txtReturnDate.setText("");
        txtReturnStatus.setText("");
        txtRefundAmount.setText("");

    }



    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String returnID = txtReturnID.getText();

        boolean isDeleted = ReturnRepo.delete(returnID);
        if (isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION,"return item Deleted").show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION,"return item Not Deleted").show();
        }


    }



    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtReturnID.getText();
        String orderID = txtOrderID.getText();
        String returnReason = txtReturnReason.getText();
        boolean exchangeRequest = Boolean.parseBoolean(txtExchangeRequest.getText());
        Date returnDate = Date.valueOf(txtReturnDate.getText());
        String status = txtReturnStatus.getText();
        double amount = Double.parseDouble(txtRefundAmount.getText());

        returnexchange returnexchange = new returnexchange(id, orderID, returnReason, status, exchangeRequest, returnDate, amount);

        // Save the returnexchange object
        boolean isSaved = ReturnRepo.save(returnexchange);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Return item Saved").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save return item. Please try again.").show();
        }




    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String id = txtReturnID.getText();
        String orderID = txtOrderID.getText();
        String returnReason = txtReturnReason.getText();
        boolean exchangeRequest = Boolean.parseBoolean(txtExchangeRequest.getText());
        Date returnDate = Date.valueOf(txtReturnDate.getText());
        String status = txtReturnStatus.getText();
        double amount = Double.parseDouble(txtRefundAmount.getText());



        returnexchange returnexchange = new returnexchange(id, orderID, returnReason, status, exchangeRequest, returnDate,amount );

        try{
            boolean isUpdate = ReturnRepo.update(returnexchange);
            if(isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION, "Store item Updated").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    public void txtReturnIDOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtReturnID.getText();


        returnexchange returnexchange = ReturnRepo.searchById(id);

       if (returnexchange != null) {

           txtReturnID.setText(returnexchange.getR_ID());
            txtOrderID.setText(returnexchange.getO_ID());
            txtReturnDate.setText(String.valueOf(returnexchange.getR_Date()));
            txtRefundAmount.setText(String.valueOf(returnexchange.getRefund_Amount()));
            txtReturnReason.setText(returnexchange.getR_Reason());
            txtExchangeRequest.setText(String.valueOf(returnexchange.isExchange_Request()));
            txtReturnStatus.setText(returnexchange.getR_Status());

       } else {

           new Alert(Alert.AlertType.ERROR, "Return item not found").show();
       }




    }
}
