package dm.sciencecenter.repositories;

import dm.sciencecenter.entities.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazineRepository extends JpaRepository<Magazine, Long> {
}
