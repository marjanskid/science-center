package dm.sciencecenter.service;

import dm.sciencecenter.entities.Item;

import java.util.List;

public interface ScienceCenterService {

    String testService();

    void initialize();

    List<Item> getAllItems();

    Double getItemPrice(Long itemId);
}
