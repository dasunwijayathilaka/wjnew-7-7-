package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {

    private String O_ID;
    private String U_ID;
    private String C_ID;
    private String D_ID;
    private String P_ID;
    private int L_Points_Earned;
    private Time O_Time;
    private int O_Date;
    private double Total_Amount;

    public Order(String orderId, String userId, String custId, String discountId, String itemCode, int lPointsEarned, String ordertime, String orderDate, double total) {
        this.O_ID = orderId;
        this.U_ID = userId;
        this.C_ID = custId;
        this.D_ID = discountId;
        this.P_ID = itemCode;
        this.L_Points_Earned = lPointsEarned;
        this.O_Time =Time.valueOf(ordertime);
        this.O_Date = Integer.parseInt(orderDate);
        this.Total_Amount =total;
}
}
