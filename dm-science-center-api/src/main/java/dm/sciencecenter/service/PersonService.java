package dm.sciencecenter.service;

import dm.sciencecenter.entities.Person;

public interface PersonService {

    Person findOneByName(String name);
}
