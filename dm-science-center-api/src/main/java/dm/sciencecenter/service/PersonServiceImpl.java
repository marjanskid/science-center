package dm.sciencecenter.service;

import dm.sciencecenter.entities.Person;
import dm.sciencecenter.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public Person findOneByName(String name) {
        return personRepository.findOneByName(name);
    }
}
