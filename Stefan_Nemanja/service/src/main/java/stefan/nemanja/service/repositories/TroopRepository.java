package stefan.nemanja.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import stefan.nemanja.model.models.Troop;

public interface TroopRepository extends JpaRepository<Troop, Long> {
}
