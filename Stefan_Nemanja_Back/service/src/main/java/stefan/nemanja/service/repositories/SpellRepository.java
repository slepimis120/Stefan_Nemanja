package stefan.nemanja.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import stefan.nemanja.model.models.Spell;

public interface SpellRepository extends JpaRepository<Spell, Long>{
}
