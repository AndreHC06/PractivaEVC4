package pe.idat.PracticaEVC4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.idat.PracticaEVC4.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
