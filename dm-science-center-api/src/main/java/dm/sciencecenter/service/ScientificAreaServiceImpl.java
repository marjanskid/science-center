package dm.sciencecenter.service;

import dm.sciencecenter.entities.ScientificArea;
import dm.sciencecenter.repositories.ScientificAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScientificAreaServiceImpl implements ScientificAreaService {

    @Autowired
    ScientificAreaRepository scientificAreaRepository;

    @Override
    public List<ScientificArea> getAll() {
        return scientificAreaRepository.findAll();
    }
}
