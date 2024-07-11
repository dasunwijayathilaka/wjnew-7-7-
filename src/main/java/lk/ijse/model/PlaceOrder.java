package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceOrder {
   private Order order;
    private List<OrderDetail> orderDetails;
    private Payment payment;
    private Item item;

}
