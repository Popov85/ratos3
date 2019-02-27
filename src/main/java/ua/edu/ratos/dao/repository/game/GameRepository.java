package ua.edu.ratos.dao.repository.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.game.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query(value = "select g from Game g join fetch g.stud s join fetch s.user", countQuery = "select g from Game g")
    Page<Game> findBestGamers(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE Game g set g.totalWins = g.totalWins + 1 WHERE g.gameId = ?1")
    void incrementWins(Long userId);
}
