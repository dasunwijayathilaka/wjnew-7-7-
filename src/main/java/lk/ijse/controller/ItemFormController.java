package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import lk.ijse.model.Item;
import lk.ijse.model.tm.ItemTM;
import lk.ijse.repository.ItemRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ItemFormController {

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<ItemTM, String> colBrand;

    @FXML
    private TableColumn<ItemTM, String> colDescription;

    @FXML
    private TableColumn<ItemTM, String> colItemCode;

    @FXML
    private TableColumn<ItemTM, String> colItemName;

    @FXML
    private TableColumn<ItemTM, Integer> colQtyOnHand;

    @FXML
    private TableColumn<ItemTM, String> colStoreID;

    @FXML
    private TableColumn<ItemTM, Double> colWeight;

    @FXML
    private TableColumn<ItemTM, Double> colUnitprice;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TableView<ItemTM> tblItem;

    @FXML
    private JFXTextField txtBrand;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtStoreID;

    @FXML
    private JFXTextField txtWeight;
    @FXML
    private AnchorPane mainAnchopane;

    @FXML
    JFXTextField txtUnitprice;

    public void initialize() {
        loadAllItems();
        setCellValueFactory();
    }

    private void loadAllItems() {
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();
        try {
            List<Item> itemList = ItemRepo.getAll();
            for (Item item : itemList) {
                ItemTM tm = new ItemTM(item.getI_ID(), item.getI_Name(), item.getBrands(), item.getQty(),
                        item.getDescription(), item.getWeight(), item.getSt_ID(), item.getUnit_price());
                obList.add(tm);
            }
            tblItem.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colStoreID.setCellValueFactory(new PropertyValueFactory<>("storeID"));
        colUnitprice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/Dashbord_Form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.mainAnchopane.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtItemCode.setText("");
        txtItemName.setText("");
        txtBrand.setText("");
        txtQtyOnHand.setText("");
        txtDescription.setText("");
        txtWeight.setText("");
        txtStoreID.setText("");
        txtUnitprice.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String itemID = txtItemCode.getText();
        try {
            boolean isDeleted = ItemRepo.delete(itemID);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Deleted").show();
                loadAllItems();  // Refresh the table view
                clearFields();   // Clear input fields
            } else {
                new Alert(Alert.AlertType.WARNING, "Item Not Deleted").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error Deleting Item: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String itemID = txtItemCode.getText();
        String name = txtItemName.getText();
        String brand = txtBrand.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        String description = txtDescription.getText();
        double weight = Double.parseDouble(txtWeight.getText());
        String storeID = txtStoreID.getText();
        double unitPrice = Double.parseDouble(txtUnitprice.getText());

        Item item = new Item(itemID, name, brand, qtyOnHand, description, weight, storeID, unitPrice);

        try {
            boolean isSaved = ItemRepo.save(item);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Saved").show();
                loadAllItems();  // Refresh the table view
                clearFields();   // Clear input fields
            } else {
                new Alert(Alert.AlertType.WARNING, "Item Not Saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error Saving Item: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String itemID = txtItemCode.getText();
        String name = txtItemName.getText();
        String brand = txtBrand.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        String description = txtDescription.getText();
        double weight = Double.parseDouble(txtWeight.getText());
        String storeID = txtStoreID.getText();
        double unitPrice = Double.parseDouble(txtUnitprice.getText());

        Item item = new Item(itemID, name, brand, qtyOnHand, description, weight, storeID, unitPrice);

        try {
            boolean isUpdated = ItemRepo.update(item);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Updated").show();
                loadAllItems();  // Refresh the table view
                clearFields();   // Clear input fields
            } else {
                new Alert(Alert.AlertType.WARNING, "Item Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error Updating Item: " + e.getMessage()).show();
        }
    }

    @FXML
    void txtItemCodeOnAction(ActionEvent event) {
        String id = txtItemCode.getText();
        try {
            Item item = ItemRepo.searchById(id);
            if (item != null) {
                txtItemCode.setText(item.getI_ID());
                txtItemName.setText(item.getI_Name());
                txtBrand.setText(item.getBrands());
                txtQtyOnHand.setText(String.valueOf(item.getQty()));
                txtDescription.setText(item.getDescription());
                txtWeight.setText(String.valueOf(item.getWeight()));
                txtStoreID.setText(item.getSt_ID());
                txtUnitprice.setText(String.valueOf(item.getUnit_price()));
            } else {
                new Alert(Alert.AlertType.ERROR, "Item Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error Searching Item: " + e.getMessage()).show();
        }
    }
    @FXML
    public void txtItemCodeOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.ITEM,txtItemCode);
    }
    @FXML
    public void txtItemNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtItemName);
    }
    @FXML
    public void txtBrandOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.NAME,txtBrand);
    }
    @FXML
    public void txtQtyOnHandOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.QTY,txtQtyOnHand);
    }
    @FXML
    public void txtDescriptionOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.NAME,txtDescription);
    }
    @FXML
    public void txtWeightOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.PRICE,txtWeight);
    }
    @FXML
    public void txtStoreIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.STORE,txtStoreID);
    }
    @FXML
    public void txtUnitpriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.PRICE,txtUnitprice);
}



}
