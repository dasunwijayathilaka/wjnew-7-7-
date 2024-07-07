package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import lk.ijse.Util.Regex;
import lk.ijse.Util.TextField;
import lk.ijse.db.DbConnection;
import lk.ijse.model.PlaceSupply;
import lk.ijse.model.tm.CartItem;
import lk.ijse.repository.PlaceSupplyRepo;
import lk.ijse.repository.SupplyFormRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static lk.ijse.db.DbConnection.dbConnection;

public class SupplyFormController {

    private final SupplyFormRepo repo = new SupplyFormRepo();
    private final ObservableList<CartItem> cartItems = FXCollections.observableArrayList();

    public JFXTextField txtunitprice;
    public DatePicker datepiker;
    @FXML
    private JFXButton btnAddtocart;

    @FXML
    private JFXButton btnplaceOrder;
    @FXML
    private AnchorPane mainAnchopane;

    @FXML
    private JFXComboBox<String> cmbSupplierID;

    @FXML
    private JFXComboBox<String> cmbstoreID;

    @FXML
    private TableColumn<CartItem, String> colStoreID;

    @FXML
    private TableColumn<CartItem, String> colSupplierID;

    @FXML
    private TableColumn<CartItem, Integer> colQty;

    @FXML
    private TableColumn<CartItem, Double> colUnitPrice;

    @FXML
    private TableColumn<CartItem, Double> colTotalPrice;

    @FXML
    private TableColumn<CartItem, String> colAction;

    @FXML
    private Label labelSuppliername;

    @FXML
    private Label labelitemcode;

    @FXML
    private Label labelqty;

    @FXML
    private Label lablestorename;

    @FXML
    private TableView<CartItem> tblsupplystore;

    @FXML
    private JFXTextField txtQTY;
    private EventObject actionEvent;

    @FXML
    void initialize() {
        try {
            loadStoreIDs();
            loadSupplierIDs();
            initializeTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadStoreIDs() throws SQLException {
        cmbstoreID.getItems().addAll(repo.getAllStoreIDs());
    }

    private void loadSupplierIDs() throws SQLException {
        cmbSupplierID.getItems().addAll(repo.getAllSupplierIDs());
    }

    private void initializeTable() {
        colStoreID.setCellValueFactory(new PropertyValueFactory<>("storeId"));
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Add a remove button to the action column
        colAction.setCellFactory(getRemoveButtonCellFactory());

        tblsupplystore.setItems(cartItems);
    }

    private Callback<TableColumn<CartItem, String>, TableCell<CartItem, String>> getRemoveButtonCellFactory() {
        return new Callback<TableColumn<CartItem, String>, TableCell<CartItem, String>>() {
            @Override
            public TableCell<CartItem, String> call(final TableColumn<CartItem, String> param) {
                final TableCell<CartItem, String> cell = new TableCell<CartItem, String>() {

                    final Button btn = new Button("Remove");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                CartItem cartItem = getTableView().getItems().get(getIndex());
                                // Show confirmation dialog
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText(null);
                                alert.setContentText("Are you sure you want to remove this item?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    cartItems.remove(cartItem);
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
    }

    @FXML
    void SupplierIDOnAction(ActionEvent event) {
        try {
            updateSupplierDetails();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateSupplierDetails() throws SQLException {
        String selectedID = cmbSupplierID.getSelectionModel().getSelectedItem();
        String supplierName = repo.getSupplierNameByID(selectedID);
        if (supplierName != null) {
            labelSuppliername.setText(supplierName);
        }
    }

    @FXML
    void storeIDOnAction(ActionEvent event) {
        try {
            updateStoreDetails();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStoreDetails() throws SQLException {
        String selectedID = cmbstoreID.getSelectionModel().getSelectedItem();
        String[] storeDetails = repo.getStoreDetailsByID(selectedID);
        if (storeDetails != null) {
            lablestorename.setText(storeDetails[0]);
            labelqty.setText(storeDetails[1]);
        }
    }

    @FXML
    void btnAddtocartOnAction(ActionEvent event) {
        String storeID = cmbstoreID.getSelectionModel().getSelectedItem();
        String supplierID = cmbSupplierID.getSelectionModel().getSelectedItem();
        int qty = Integer.parseInt(txtQTY.getText());
        double unitPrice = Double.parseDouble(txtunitprice.getText());
        double totalPrice = qty * unitPrice;
        String action = "Remove";

        CartItem cartItem = new CartItem(storeID, supplierID, qty, unitPrice, totalPrice, action);
        cartItems.add(cartItem);
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
    void btnplaceOrderOnAction(ActionEvent event) {
        // Place order button functionality
        ObservableList<CartItem> items = tblsupplystore.getItems();
        System.out.println(items);

        PlaceSupply placeSupply = new PlaceSupply(items);
        try {
            boolean isPlaced = PlaceSupplyRepo.placeOredr(placeSupply);
            if (isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order placed successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to place order").show();
        }
    }

    @FXML
    void txtQTYOnAction(ActionEvent event) {
        // Quantity text field action
    }

    public void txtunitpriceOnAction(ActionEvent actionEvent) {
        // Unit price text field action
    }

    @FXML
    public void txtQTYOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.QTY, txtQTY);
    }

    @FXML
    public void txtunitpriceReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.QTY, txtunitprice);

    }

    public void PrintBillOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\USER\\Desktop\\New folder (2)\\wijesinghe_grocery_store\\wijesinghe_grocery_store\\src\\main\\resources\\reports\\Supply.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String,Object> data = new HashMap<>();
        String orderID = cmbSupplierID.getValue();
        data.put("supID",orderID);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);



    }
}