package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Supplier {

    private String S_ID;
    private String S_Name;
    private String S_Address;
    private String S_Email;
    private String S_Contact_Number;
    private String Delivery_Schedule;


}
