package ua.edu.ratos.dao.repository.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.game.Week;

public interface WeekRepository extends JpaRepository<Week, Long> {

    @Query(value = "select w from Week w join fetch w.stud s join fetch s.user", countQuery = "select w from Week w")
    Page<Week> findWeeklyGamers(Pageable pageable);

    @Modifying
    @Query(value="delete from Week")
    void emptyWeek();
}
