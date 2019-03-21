package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.ResultDetails;

import java.time.LocalDateTime;

public interface ResultDetailsRepository extends JpaRepository<ResultDetails, Long> {

    @Modifying
    @Query(value = "DELETE FROM ResultDetails r where r.whenRemove<=?1")
    void cleanExpired(LocalDateTime localDateTime);
}
