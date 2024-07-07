package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItem {
    private String storeId;
    private String supplierId;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private String action;
}