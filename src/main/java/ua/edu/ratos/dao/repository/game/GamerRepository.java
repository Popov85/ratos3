package ua.edu.ratos.dao.repository.game;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.game.Gamer;

public interface GamerRepository extends JpaRepository<Gamer, Long> {

}
