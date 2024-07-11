package lk.ijse.entity.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReturnTM {
    private String returnID;
    private String orderID;
    private String returnReason;
    private String returnStatus;
    private boolean exchangeRequest;
    private Date returnDate;
    private double refundAmount;
}