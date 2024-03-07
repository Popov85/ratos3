package ua.edu.ratos.dao.repository.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.game.Wins;

import java.time.LocalDate;

public interface WinsRepository extends JpaRepository<Wins, Long> {

    @Query(value = "select w from Wins w join fetch w.student s join fetch s.user where w.wonDate >= ?1",
            countQuery = "select w from Wins w where w.wonDate >= ?1")
    Page<Wins> findAllWinnersSince(LocalDate date, Pageable pageable);
}
