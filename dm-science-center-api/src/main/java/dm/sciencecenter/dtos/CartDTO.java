package dm.sciencecenter.dtos;

import dm.sciencecenter.entities.Item;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {

    List<CartItemDTO> cartItems;

    public CartDTO() {
        this.cartItems = new ArrayList<CartItemDTO>();
    }

    public CartDTO(List<Item> items) {
        this.cartItems = new ArrayList<CartItemDTO>();
        for (Item i : items) {
            CartItemDTO newCartItemDTO = new CartItemDTO();
            newCartItemDTO.setItem(i);
            newCartItemDTO.setCount(0);
            this.cartItems.add(newCartItemDTO);
        }
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }
}
