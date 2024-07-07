package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.model.Item;


import lk.ijse.model.tm.ItemTM;

import lk.ijse.repository.ItemRepo;
import lk.ijse.repository.StoreRepo;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ItemFormController {

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colStoreID;

    @FXML
    private TableColumn<?, ?> colWeight;

    @FXML
    private TableColumn<?, ?> colUnitprice;


    @FXML
    private AnchorPane mainAnchopane;

    @FXML
    private TableView<ItemTM> tblItem;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtStoreID;

    @FXML
    private TextField txtWeight;


    @FXML
    private TextField txtUnitprice;


    public void initialize() {
        loadAllitems();
        setCellValueit();
    }

    private void loadAllitems() {
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


    private void setCellValueit() {
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
        ClearFileds();
    }

    private void ClearFileds() {
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
        String ItemID = txtStoreID.getText();

        boolean isDeleted = StoreRepo.delete(ItemID);
        if (isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION,"Store item Deleted").show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION,"Store item Not Deleted").show();
        }
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
         String ItemID = txtStoreID.getText();
         String name = txtItemName.getText();
         String brand = txtBrand.getText();
         int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
         String description = txtDescription.getText();
         double weight = Double.parseDouble(txtWeight.getText());
         String storeID = txtStoreID.getText();
         double unitPrice = Double.parseDouble(txtUnitprice.getText());


        Item item = new Item(ItemID, name, brand, qtyOnHand, description, weight, storeID, unitPrice);

        try{
            boolean isSaved = ItemRepo.save(item);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "item Saved").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.CONFIRMATION, "item not saved").show();

        }
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {

    String ItemID = txtStoreID.getText();
    String name = txtItemName.getText();
    String brand = txtBrand.getText();
    int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
    String description = txtDescription.getText();
    double weight = Double.parseDouble(txtWeight.getText());
    String storeID = txtStoreID.getText();
    double unitPrice = Double.parseDouble(txtUnitprice.getText());

    Item item = new Item(ItemID, name, brand, qtyOnHand, description, weight, storeID, unitPrice);

        try {
            boolean isUpdate = ItemRepo.update(item);
            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Updated").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Item Not Updated").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    public void txtItemCodeOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtItemCode.getText();


        Item item = ItemRepo.searchById(id);

        if(item != null){

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


    }
}





