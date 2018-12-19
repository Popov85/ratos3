package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Result;


public interface ResultRepository extends JpaRepository<Result, Long> {

    @Query(value = "SELECT r FROM Result r join fetch r.user u left join fetch u.student s join fetch s.studentClass c join fetch c.faculty f join fetch f.organisation",
            countQuery = "SELECT count(r) FROM Result r join r.user u left join u.student s")
    Page<Result> findStudentsResults(Pageable pageable);

}
