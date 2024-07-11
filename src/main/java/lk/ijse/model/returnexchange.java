package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class returnexchange {
    private String R_ID;
    private String O_ID;
    private String R_Reason;
    private String R_Status;
    private boolean Exchange_Request;
    private Date R_Date;
    private double Refund_Amount;
}