package lk.ijse.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {

    private String P_ID;
    private double P_Amount;
    private int Transaction_Date;
    private int Transaction_Time;


}