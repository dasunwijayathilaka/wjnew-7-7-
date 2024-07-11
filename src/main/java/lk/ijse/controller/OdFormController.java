package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.entity.*;
import lk.ijse.entity.tm.OrderTM;
import lk.ijse.repository.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OdFormController implements Initializable {

    public JFXComboBox cmbItemCode;
    public Label lblcustomerName;
    public JFXTextField txtlpoints;
    @FXML
    private JFXButton btnAddtocart1;

    @FXML
    private JFXButton btnNEW;

    @FXML
    private JFXButton btnplaceOrder;

    @FXML
    private JFXComboBox<String> cmbCustomerId1;

    @FXML
    private JFXComboBox<String> cmbdiscountcode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colaction;

    @FXML
    private TableColumn<?, ?> colitemName;

    @FXML
    private Label labelCustomerName;

    @FXML
    private Label labelDescription;

    @FXML
    private Label labelDiscountprice;

    @FXML
    private Label labelOrderId;

    @FXML
    private Label labelOrderdate;

    @FXML
    private Label labelPaymenamount;

    @FXML
    private Label labelUnitprice;

    @FXML
    private Label labelUsername;

    @FXML
    private Label labelnettotal;

    @FXML
    private Label labelpaymentId;

    @FXML
    private Label labelqty;

    @FXML
    private Label labelsubtotal;

    @FXML
    private Label labeluserId;

    @FXML
    private TableView<OrderTM> tblOrderCart;

    @FXML
    private JFXTextField txtQTY;

    @FXML
    void CustomerIdOnAction(ActionEvent event) {
        String id = String.valueOf(cmbCustomerId1.getValue());
        try {
            Customer customer = CustomerRepo.searchById(id);
            labelCustomerName.setText(customer.getC_Name());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemcd"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colitemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colaction.setCellValueFactory(new PropertyValueFactory<>("button"));
    }

    @FXML
    void PrintBillOnAction(ActionEvent event) {

    }
    ObservableList<OrderTM> obList= FXCollections.observableArrayList();
    @FXML
    void btnAddtocartOnAction(ActionEvent event) {
        String id = (String) cmbItemCode.getValue();
        String description = labelDescription.getText();
        String name = lblcustomerName.getText();
        int qty2 = Integer.parseInt(txtQTY.getText());
        double unitprice = Double.parseDouble(labelUnitprice.getText());
        JFXButton btnRemove = getJfxButton();

        double discount1 = Double.parseDouble(labelDiscountprice.getText());
        double totalDiscount = (discount1 * qty2) / 50;

        double total2 = (qty2 * unitprice);
        double total = total2 - totalDiscount;
        labelnettotal.setText(String.valueOf(total2));
        labelsubtotal.setText(String.valueOf(total));
        labelPaymenamount.setText(String.valueOf(total));

        boolean found = false;
        for (OrderTM tm : obList) {
            if (tm.getItemcd().equals(id)) {
                tm.setQty(tm.getQty() + qty2);
                tm.setUnitPrice(unitprice);
                tblOrderCart.refresh();
                found = true;
                break;
            }
        }

        if (!found) {
            OrderTM tm = new OrderTM(id, description, qty2, unitprice, name, btnRemove);
            obList.add(tm);
        }

        tblOrderCart.setItems(obList);
        tblOrderCart.refresh();
    }

    private JFXButton getJfxButton() {
        JFXButton btnRemove = new JFXButton("action");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to remove?", yes, no);
            alert.setHeaderText(null);
            alert.setTitle("Confirmation");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yes) {
                // obList.clear();
                //  tblDiscountCart.refresh();
                //calculateNetTotal();
            }
        });
        return btnRemove;
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {

    }



    @FXML
    void discountcodeOnAction(ActionEvent event) {
        String id = String.valueOf(cmbdiscountcode.getValue());

        try {
            Discount discount = DiscountRepo.getDiscountById(id);
            labelDiscountprice.setText(String.valueOf(discount.getD_Amount()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void txtQTYOnAction(ActionEvent event) {

    }

    public void loadUSerDitals() {
        labeluserId.setText("U001");
        labelUsername.setText("wijesinghe");

    }


    public void LoadCustomer() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            var idList = CustomerRepo.getAllCustomerIDs();
            obList.addAll(idList);
            cmbCustomerId1.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCurrentOrderId() {
        try {
            String currentId = OrderRepo.getCurrentId();
            String nextOrderId = generateNextOrderId(currentId);
            labelOrderId.setText(nextOrderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOrderDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        labelOrderdate.setText(formattedDate);
    }

    private String generateNextOrderId(String currentId) {
        if (currentId != null && currentId.startsWith("ORD")) {
            try {
                int idNum = Integer.parseInt(currentId.substring(3));
                return "ORD" + String.format("%03d", idNum + 1);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return "ORD001";
    }

    private void getCurrentPaymentId() {
        try {
            String currentId = PaymentRepo.getCurrentId();
            String nextOrderId = generateNextPaymentId(currentId);
            labelpaymentId.setText(nextOrderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextPaymentId(String currentId) {
        if (currentId != null && currentId.startsWith("PAY")) {
            try {
                int idNum = Integer.parseInt(currentId.substring(3));
                return "PAY" + String.format("%03d", idNum + 1);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return "PAY001";
    }

    public void LoadAllItemSId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            var idList = ItemRepo.getAllItemIds();
            obList.addAll(idList);
            cmbItemCode.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadDiscount() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            var idList = DiscountRepo.getAllDiscountIDs();
            obList.addAll(idList);
            cmbdiscountcode.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUSerDitals();
        LoadCustomer();
        getCurrentOrderId();
        setOrderDate();
        getCurrentPaymentId();
        LoadAllItemSId();
        loadDiscount();
        setCellValueFactory();
    }

    public void cmbItemCodeOnAction(ActionEvent actionEvent) {
        String id = String.valueOf(cmbItemCode.getValue());

        try {
            Item ie = ItemRepo.searchById(id);
            labelDescription.setText(ie.getDescription());
            labelUnitprice.setText(String.valueOf(ie.getUnit_price()));
            labelqty.setText(String.valueOf(ie.getQty()));
            lblcustomerName.setText(ie.getI_Name());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void btnplaceOrderOnAction(ActionEvent event) throws SQLException {
        LocalTime localTime = LocalTime.now();
        String OrderId = labelOrderId.getText();
        String UserId = labeluserId.getText();
        String CustId = cmbCustomerId1.getValue();
        String DiscountId = cmbdiscountcode.getValue();
        String PaymentId = labelpaymentId.getText();
        String lpoints = txtlpoints.getText();
        String Ordertime = localTime.toString();
        String OrderDate = labelOrderdate.getText();
        double total = Double.parseDouble(labelnettotal.getText());
        String PaymentID = labelpaymentId.getText();
        double NetTotal = Double.parseDouble(labelnettotal.getText());
        String ItemCode = (String) cmbItemCode.getValue();
        int qty = Integer.parseInt(txtQTY.getText());

        // validate the TextFields
        if (CustId.trim().isEmpty() || DiscountId.trim().isEmpty() || lpoints.trim().isEmpty() || ItemCode.trim().isEmpty() || txtQTY.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "TextFields can't be empty").show();
            return;
        }

        Order order = new Order(OrderId, UserId, CustId, DiscountId, ItemCode, Integer.parseInt(lpoints), Ordertime, OrderDate, total);

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderDetail item : tblOrderCart.getItems()) {
            OrderDetail order_detail = new OrderDetail(
                    item.getI_ID(),
                    OrderId,
                    item.getTotalprice()
            );
            orderDetails.add(order_detail);
        }
        System.out.println(orderDetails);

        Payment payment = new Payment(PaymentID, NetTotal, Integer.parseInt(OrderDate), Integer.parseInt(Ordertime));

        Item item = new Item(ItemCode, labelDescription.getText(), Integer.parseInt(labelqty.getText()), Double.parseDouble(labelUnitprice.getText()));

        PlaceOrder order1 = new PlaceOrder(order, orderDetails, payment, item);

        boolean isPlaced = PlaceOrderRepo.placeOrder(order1);
        if (isPlaced) {
            new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
        } else {
            new Alert(Alert.AlertType.WARNING, " Order Placed Unsuccessfully!").show();
    }



}
}
