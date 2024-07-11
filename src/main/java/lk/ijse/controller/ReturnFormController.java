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
import lk.ijse.entity.returnexchange;
import lk.ijse.entity.tm.ReturnTM;
import lk.ijse.repository.ReturnRepo;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ReturnFormController {

    @FXML
    private TableColumn<ReturnTM, String> colExchangeRequest;

    @FXML
    private TableColumn<ReturnTM, String> colOrderID;

    @FXML
    private TableColumn<ReturnTM, Double> colRefundAmount;

    @FXML
    private TableColumn<ReturnTM, Date> colReturnDate;

    @FXML
    private TableColumn<ReturnTM, String> colReturnID;

    @FXML
    private TableColumn<ReturnTM, String> colReturnReason;

    @FXML
    private TableColumn<ReturnTM, String> colReturnStatus;

    @FXML
    private TableView<ReturnTM> tblReturnExchange;

    @FXML
    private JFXTextField txtExchangeRequest;

    @FXML
    private JFXTextField txtOrderID;

    @FXML
    private JFXTextField txtRefundAmount;

    @FXML
    private JFXTextField txtReturnDate;

    @FXML
    private JFXTextField txtReturnID;

    @FXML
    private JFXTextField txtReturnReason;

    @FXML
    private JFXTextField txtReturnStatus;
    @FXML
    private AnchorPane mainAnchopane;

    public void initialize() {
        setCellFactory();
        loadAllReturns();
    }

    private void setCellFactory() {
        colReturnID.setCellValueFactory(new PropertyValueFactory<>("returnID"));
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colReturnReason.setCellValueFactory(new PropertyValueFactory<>("returnReason"));
        colExchangeRequest.setCellValueFactory(new PropertyValueFactory<>("exchangeRequest"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colReturnStatus.setCellValueFactory(new PropertyValueFactory<>("returnStatus"));
        colRefundAmount.setCellValueFactory(new PropertyValueFactory<>("refundAmount"));
    }

    private void loadAllReturns() {
        ObservableList<ReturnTM> obList = FXCollections.observableArrayList();
        try {
            List<returnexchange> returnList = ReturnRepo.getAll();
            for (returnexchange ret : returnList) {
                ReturnTM tm = new ReturnTM(ret.getR_ID(), ret.getO_ID(), ret.getR_Reason(), ret.getR_Status(), ret.isExchange_Request(), ret.getR_Date(), ret.getRefund_Amount());
                System.out.println("Order ID: " + ret.getO_ID()); // Debug statement
                obList.add(tm);
            }
            tblReturnExchange.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {

        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/Dashbord_Form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.txtReturnID.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
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
        if (isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Return item deleted").show();
            loadAllReturns(); // Refresh table after deletion
        } else {
            new Alert(Alert.AlertType.ERROR, "Return item not deleted").show();
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

        returnexchange ret = new returnexchange(id, orderID, returnReason, status, exchangeRequest, returnDate, amount);

        boolean isSaved = ReturnRepo.save(ret);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Return item saved").show();
            loadAllReturns(); // Refresh table after saving
            clearFields();
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

        returnexchange ret = new returnexchange(id, orderID, returnReason, status, exchangeRequest, returnDate, amount);

        try {
            boolean isUpdated = ReturnRepo.update(ret);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Return item updated").show();
                loadAllReturns(); // Refresh table after update
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Return item not updated").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtReturnIDOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtReturnID.getText();
        returnexchange ret = ReturnRepo.searchById(id);

        if (ret != null) {
            txtReturnID.setText(ret.getR_ID());
            txtOrderID.setText(ret.getO_ID());
            txtReturnDate.setText(String.valueOf(ret.getR_Date()));
            txtRefundAmount.setText(String.valueOf(ret.getRefund_Amount()));
            txtReturnReason.setText(ret.getR_Reason());
            txtExchangeRequest.setText(String.valueOf(ret.isExchange_Request()));
            txtReturnStatus.setText(ret.getR_Status());
        } else {
            new Alert(Alert.AlertType.ERROR, "Return item not found").show();
        }
    }
    @FXML
    public void txtReturnIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.RETURN,txtReturnID);
    }
    @FXML
    public void txtOrderIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.ORDER,txtOrderID);
    }
    @FXML
    public void txtReturnDateOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.DATE,txtReturnDate);
    }
    @FXML
    public void txtRefundAmountOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.PRICE,txtRefundAmount);
    }
    @FXML
    public void txtReturnReasononOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtReturnReason);
    }
    @FXML
    public void txtExchangeRequestOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.NAME,txtExchangeRequest);
    }
    @FXML
    public void txtReturnStatusOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.NAME,txtReturnStatus);
}

}
