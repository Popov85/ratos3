package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.edu.ratos.dao.entity.Result;

import java.util.Set;

public interface ResultRepository extends JpaRepository<Result, Long> {

    // TODO to return Page (no working somehow)
    @Query(value = "SELECT r FROM Result r join fetch r.user u left join fetch u.student s join fetch s.faculty f join fetch f.organisation")
    Set<Result> findStudentsResults();
}
