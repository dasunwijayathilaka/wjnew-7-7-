package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemTM {
    private String itemCode;
    private String itemName;
    private String brand;
    private int qtyOnHand;
    private String description;
    private double weight;
    private String storeID;
    private double unitPrice;
}



