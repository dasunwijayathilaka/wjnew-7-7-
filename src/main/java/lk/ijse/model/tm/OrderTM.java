package lk.ijse.entity.tm;

import com.jfoenix.controls.JFXButton;
import lk.ijse.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderTM extends OrderDetail {
    private String itemcd;
    private String description;
    private int qty;
    private double unitPrice;
    private String itemName;
    private JFXButton button;
}