package root.sciencecenter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import root.sciencecenter.entities.Magazine;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Long> {

    @Query(value = "SELECT * FROM MAGAZINE WHERE MAGAZINE.ISSN_NUMBER =?1", nativeQuery = true)
    Magazine findByIssnNumber(Long issnNumber);
}
