package lk.ijse.entity;

import lk.ijse.entity.tm.CartItem;
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
