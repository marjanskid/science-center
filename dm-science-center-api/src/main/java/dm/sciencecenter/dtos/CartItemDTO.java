package dm.sciencecenter.dtos;

import dm.sciencecenter.entities.Item;

public class CartItemDTO {

    Item item;
    int count;

    public CartItemDTO() {
    }

    public CartItemDTO(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
