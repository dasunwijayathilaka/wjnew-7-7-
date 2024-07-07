package lk.ijse.model;

import lk.ijse.model.tm.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceSupply {
    List<CartItem> cartItems;
}
