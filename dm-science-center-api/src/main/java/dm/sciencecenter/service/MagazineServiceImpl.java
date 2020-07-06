package dm.sciencecenter.service;

import dm.sciencecenter.entities.Magazine;
import dm.sciencecenter.repositories.MagazineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MagazineServiceImpl implements MagazineService {

    @Autowired
    MagazineRepository magazineRepository;

    @Override
    public List<Magazine> getAll() {
        return magazineRepository.findAll();
    }
}
