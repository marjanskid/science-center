package root.sciencecenter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import root.sciencecenter.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM USER WHERE USER.USERNAME =?1", nativeQuery = true)
    User findByUsername(String username);
}
