package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Discount {

    private String D_ID;
    private String D_Name;
    private double D_Amount;
    private String V_Period;
    private String Grocery_Categories;
    private Date D_Start_Date;
    private Date D_End_Date;

}
