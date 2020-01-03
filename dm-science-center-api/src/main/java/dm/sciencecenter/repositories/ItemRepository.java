package dm.sciencecenter.repositories;

import dm.sciencecenter.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
