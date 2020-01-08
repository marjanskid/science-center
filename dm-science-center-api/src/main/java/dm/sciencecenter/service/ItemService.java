package dm.sciencecenter.service;

import dm.sciencecenter.entities.Item;

import java.util.List;

public interface ItemService {

    String testService();

    void initialize();

    List<Item> getAllItems();

    Item getItemById(Long id);

    Double getItemPrice(Long itemId);


}
