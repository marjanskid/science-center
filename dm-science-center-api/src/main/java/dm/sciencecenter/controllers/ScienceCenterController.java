package dm.sciencecenter.controllers;

import dm.sciencecenter.constants.PaymentHubsURL;
import dm.sciencecenter.dtos.TestDTO;
import dm.sciencecenter.service.ScienceCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Controller
@RequestMapping("/science-center")
public class ScienceCenterController {

    public static final UUID webShopID = UUID.fromString("123e4567-e89b-12d3-a456-426655440000");

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ScienceCenterService scienceCenterService;

    @GetMapping(value = "/connectionTestMethod", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> connectionTestMethod() {
        TestDTO getTest = restTemplate.getForObject(PaymentHubsURL.PAYMENT_HUB_URL + "/api/connectionTestMethod", TestDTO.class);
        if (getTest != null) {
            return new ResponseEntity<>(getTest.getMessage(), HttpStatus.OK);
        }

        return new ResponseEntity<>("TestDTO is null!", HttpStatus.OK);
    }

    @GetMapping(value = "/allItems", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllItems() {
        return new ResponseEntity<>(scienceCenterService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping(value = "/buy")
    public String buyItems(Model model) {
        System.out.println("Buy items get method");
        model.addAttribute("allItems", scienceCenterService.getAllItems());

        return "buy";
    }

    @PostMapping(value = "/buy/{itemId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buyItems(@PathVariable("itemId") Long itemId) {
        System.out.println("Item id: " + itemId);
        Double total = scienceCenterService.getItemPrice(itemId);
        String response = "price: " + total;

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/initialize", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> initialize() {
        scienceCenterService.initialize();

        return new ResponseEntity<>("<h1>Initialization went successful!</h1>", HttpStatus.OK);
    }


}
