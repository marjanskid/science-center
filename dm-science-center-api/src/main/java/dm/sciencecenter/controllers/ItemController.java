package dm.sciencecenter.controllers;

import dm.sciencecenter.dtos.ItemDto;
import dm.sciencecenter.entities.Item;
import dm.sciencecenter.service.ItemService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/science-center-api/item")
public class ItemController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ItemService itemService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllItems() {
        System.out.println("items/all");
        log.info("items/all");
        List<Item> allItems = itemService.getAllItems();
        List<ItemDto> allItemsDto = new ArrayList<ItemDto>();
        if (allItems.size() != 0) {
            for(Item i : allItems) {
                allItemsDto.add(this.convertToDto(i));
            }
        }

        return new ResponseEntity<>(allItemsDto, HttpStatus.OK);
    }

    @GetMapping(value = "/initialize", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> initialize() {
        itemService.initialize();

        return new ResponseEntity<>("<h1>Initialization went successful!</h1>", HttpStatus.OK);
    }

    private ItemDto convertToDto(Item item) {
        ItemDto itemDto = modelMapper.map(item, ItemDto.class);

        return itemDto;
    }

    private Item convertToEntity(ItemDto itemDto) {
        Item item = modelMapper.map(itemDto, Item.class);

        return item;
    }
}
