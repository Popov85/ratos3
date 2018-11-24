package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.SessionPreserved;

public interface SessionPreservedRepository extends JpaRepository<SessionPreserved, String> {

    @Query(value = "SELECT s FROM SessionPreserved s join fetch s.scheme join s.user u  where u.userId=?1 order by s.whenPreserved desc",
            countQuery = "SELECT count(s) FROM SessionPreserved s join s.user u where u.userId=?1")
    Page<SessionPreserved> findAllByUserId(Long userId, Pageable pageable);
}
