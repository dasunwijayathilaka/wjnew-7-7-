package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {

    private String I_ID;
    private String I_Name;
    private String Brands;
    private int Qty;
    private String Description;
    private double Weight;
    private String St_ID;
    private double Unit_price;

    public Item(String itemCode, String text, int i, double v) {
        this.I_ID = itemCode;
        this.I_Name = text;
        this.Qty = i;
        this.Unit_price=v;
}
}
