package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Store {

    private String St_ID;
    private String St_Name;
    private String Floor_Plan;
    private String Description;
    private String Location;
    private int Qty_On_Hand;


}
