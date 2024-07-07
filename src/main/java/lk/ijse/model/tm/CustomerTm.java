package lk.ijse.model.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class CustomerTm {

    private String C_ID;
    private String C_Name;
    private String C_Address;
    private String C_Email;
    private String C_Contact_Number;
    private int L_Points;


}
