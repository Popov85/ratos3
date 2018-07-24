package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.domain.entity.Help;

public interface HelpRepository extends JpaRepository<Help, Long> {

    @Query(value = "SELECT h FROM Help h left join fetch h.resources where h.helpId=?1")
    Help findByIdWithResources(Long helpId);
}
