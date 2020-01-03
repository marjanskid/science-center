package dm.sciencecenter.entities;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    public static Cart instance;
    private List<Item> items;

    private Cart() {
        this.items = new ArrayList<Item>();
    }

    public List<Item> getItems() {
        if (this.items == null) {
            this.items = new ArrayList<Item>();
        }

        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void clearCart() {
        if (this.getItems().size() != 0) {
            this.getItems().clear();
        }
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }

        return instance;
    }
}
