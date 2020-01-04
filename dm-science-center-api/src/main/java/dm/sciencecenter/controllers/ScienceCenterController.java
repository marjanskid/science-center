package dm.sciencecenter.controllers;

import dm.sciencecenter.constants.PaymentHubsURL;
import dm.sciencecenter.dtos.TestDTO;
import dm.sciencecenter.service.ScienceCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/science-center-api")
public class ScienceCenterController {

    public static final UUID webShopID = UUID.fromString("123e4567-e89b-12d3-a456-426655440000");

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ScienceCenterService scienceCenterService;

    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/test/{str}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> testMethod(@PathVariable("str") String str) {
        log.info("usao u test metodi kontrolera");
        System.out.println("Ovo smo dobili: " + str);
        String retStr = "Dobili: " + str + ", a vracamo OVO";
        log.info(retStr);
        log.info("pre returna u test metodi kontrolera");
        List<String> retList = new ArrayList<String>();
        retList.add(retStr);
        return new ResponseEntity<>(retList, HttpStatus.OK);
    }

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
