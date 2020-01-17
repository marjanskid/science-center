package root.sciencecenter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import root.sciencecenter.entities.ScientificArea;

public interface ScientificAreaRepository extends JpaRepository<ScientificArea, Long> {

    @Query(value = "SELECT * FROM SCIENTIFIC_AREA WHERE SCIENTIFIC_AREA.NAME =?1", nativeQuery = true)
    ScientificArea findByName(String scientificArea);
}
