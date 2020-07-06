package dm.sciencecenter.controllers;

import dm.sciencecenter.entities.Magazine;
import dm.sciencecenter.entities.ScientificArea;
import dm.sciencecenter.service.MagazineService;
import dm.sciencecenter.service.ScientificAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/science-center-api/magazine")
public class MagazineController {

    @Autowired
    MagazineService magazineService;

    @GetMapping(produces = "application/json")
    public @ResponseBody List<String> getMagazines() {
        System.out.println("getMagazines");
        List<Magazine> magazines = magazineService.getAll();
        List<String> ret = new ArrayList<>();

        for (Magazine magazine: magazines) {
            ret.add(magazine.getName());
        }

        System.out.println(ret);

        return ret;
    }
}
