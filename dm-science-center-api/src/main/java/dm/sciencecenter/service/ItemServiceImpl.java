package dm.sciencecenter.service;

import dm.sciencecenter.entities.Item;
import dm.sciencecenter.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public String testService() {
        String testMessage = "Pozz iz Ducicine naucne centrale!";
        return testMessage;
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.getOne(id);
    }

    @Override
    public Double getItemPrice(Long itemId) {
        Double total = 0.0;
        Optional<Item> foundItem = itemRepository.findById(itemId);
        if (foundItem != null) {
            total = foundItem.get().getPrice();
        }

        return total;
    }

    @Override
    public void initialize() {
        System.out.println("****************************************************");
        System.out.println("Science Center data initialization started");
        System.out.println("****************************************************");

        this.initializeItems();

        System.out.println("****************************************************");
        System.out.println("Science Center data initialization finished");
        System.out.println("****************************************************");
    }

    private void initializeItems() {
        Item i1 = new Item("Algebra", 540);
        Item i2 = new Item("Nacionalna geografija", 250);
        Item i3 = new Item("Bukvar", 1350);

        itemRepository.save(i1);
        System.out.println("Item1 - Algebra saved.....");
        itemRepository.save(i2);
        System.out.println("Item2 - Nacionalna geografija saved.....");
        itemRepository.saveAndFlush(i3);
        System.out.println("Item1 - Bukvar saved.....");
    }


}
