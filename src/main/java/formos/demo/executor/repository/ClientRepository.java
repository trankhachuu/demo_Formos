package formos.demo.executor.repository;

import formos.demo.executor.domain.Client;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "SELECT *  FROM client WHERE client.user_id=:userId", nativeQuery = true)
    Client getClientByUserId(@Param("userId") Long userId);
}
