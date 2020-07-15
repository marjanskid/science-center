package dm.sciencecenter.repositories;

import dm.sciencecenter.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findOneByName(String name);
}
