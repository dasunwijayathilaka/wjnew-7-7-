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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.model.Store;

import lk.ijse.model.tm.StoreTM;
import lk.ijse.repository.StoreRepo;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StoreFormController {

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colFloorplan;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colStoreID;

    @FXML
    private TableColumn<?, ?> colStoreLocation;

    @FXML
    private TableColumn<?, ?> colStoreName;

    @FXML
    private TableView<StoreTM> tblStore;

    @FXML
    private javafx.scene.control.TextField txtDescription;

    @FXML
    private javafx.scene.control.TextField txtFloorplan;

    @FXML
    private javafx.scene.control.TextField txtQtyOnHand;

    @FXML
    private javafx.scene.control.TextField txtStoreID;

    @FXML
    private javafx.scene.control.TextField txtStoreLocation;

    @FXML
    private javafx.scene.control.TextField txtStoreName;
    @FXML
    private AnchorPane mainAnchopane;

    public void initialize() {
        setCelllFactory();
        allLoadSores();


    }

    public void setCelllFactory() {
        colStoreID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStoreName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStoreLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("discrption"));
        colFloorplan.setCellValueFactory(new PropertyValueFactory<>("foorPlan"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qty"));

    }

    public void allLoadSores() {
        ObservableList<StoreTM> obList = FXCollections.observableArrayList();
        try {
            List<Store> supplierList = StoreRepo.getAll();
            for (Store supplier : supplierList) {
                StoreTM tm = new StoreTM(supplier.getSt_ID(), supplier.getSt_Name(), supplier.getLocation(), supplier.getDescription(), supplier.getFloor_Plan(), supplier.getQty_On_Hand());
                obList.add(tm);
            }
            tblStore.setItems(obList);
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
        stage.show(); // Ensure the stage is shown
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        ClearFileds();

    }

    private void ClearFileds() {
        txtStoreID.setText("");
        txtStoreName.setText("");
        txtFloorplan.setText("");
        txtDescription.setText("");
        txtStoreLocation.setText("");
        txtQtyOnHand.setText("");

    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String StoreID = txtStoreID.getText();

        boolean isDeleted = StoreRepo.delete(StoreID);
        if (isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Store item Deleted").show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION, "Store item Not Deleted").show();
        }
    }




    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtStoreID.getText();
        String name = txtStoreName.getText();
        String Floorplan = txtFloorplan.getText();
        String Description = txtDescription.getText();
        String location = txtStoreLocation.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        Store store = new Store(id, name, Floorplan, Description, location, qtyOnHand);

        boolean isSaved = StoreRepo.save(store);
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Store item Saved").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save store item. It might already exist.").show();
        }
    }




    public void btnUpdateOnAction(ActionEvent actionEvent) {

        String id = txtStoreID.getText();
        String name = txtStoreName.getText();
        String Floorplan = txtFloorplan.getText();
        String Description = txtDescription.getText();
        String location = txtStoreLocation.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        Store Store = new Store(id, name, Floorplan, Description, location, qtyOnHand);

        try {
            boolean isUpdate = StoreRepo.update(Store);
            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Store item Updated").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }





    public void txtStoreIdOnAction(ActionEvent actionEvent) {
        String id = txtStoreID.getText();

        try {
            Store store = StoreRepo.searchById(id);
            if (store != null) {
                txtStoreID.setText(store.getSt_ID());
                txtStoreName.setText(store.getSt_Name());
                txtFloorplan.setText(store.getFloor_Plan());
                txtDescription.setText(store.getDescription());
                txtStoreLocation.setText(store.getLocation());
                txtQtyOnHand.setText(String.valueOf(store.getQty_On_Hand()));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Store not found!").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }

    }
}




