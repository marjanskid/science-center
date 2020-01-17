package root.sciencecenter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.entities.ScientificArea;
import root.sciencecenter.repositories.ScientificAreaRepository;

import java.util.List;

@Service
public class ScientificAreaServiceImpl implements ScientificAreaService {

    @Autowired
    ScientificAreaRepository scientificAreaRepository;


    @Override
    public List<ScientificArea> getAllScientificAreas() {
        return scientificAreaRepository.findAll();
    }
}
