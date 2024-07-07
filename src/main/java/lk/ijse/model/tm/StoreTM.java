package lk.ijse.model.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreTM {
    private String id;
    private String name;
    private String location;
    private String discrption;
    private String foorPlan;
    private  int qty;


}
