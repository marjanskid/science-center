package root.sciencecenter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import root.sciencecenter.entities.RegistrationHashCode;

public interface RegistrationHashCodeRepository extends JpaRepository<RegistrationHashCode, Long> {

    @Query(value = "SELECT * FROM REGISTRATION_HASH_CODE WHERE REGISTRATION_HASH_CODE.USERNAME =?1", nativeQuery = true)
    RegistrationHashCode findByUsername(String username);
}
