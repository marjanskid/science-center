package dm.sciencecenter.controllers;

import dm.sciencecenter.entities.ScientificArea;
import dm.sciencecenter.service.ScientificAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/science-center-api/scientific-area")
public class ScientificAreaController {

    @Autowired
    ScientificAreaService scientificAreaService;

    @GetMapping(produces = "application/json")
    public @ResponseBody List<String> getScientificAreas() {
        System.out.println("getScientificAreas");
        List<ScientificArea> scientificAreas = scientificAreaService.getAll();
        List<String> ret = new ArrayList<>();

        for (ScientificArea scientificArea: scientificAreas) {
            ret.add(scientificArea.getName());
        }

        System.out.println(ret);

        return ret;
    }

}
