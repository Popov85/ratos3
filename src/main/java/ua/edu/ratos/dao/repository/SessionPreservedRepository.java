package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.SessionPreserved;

public interface SessionPreservedRepository extends JpaRepository<SessionPreserved, String> {

    @Query(value = "SELECT count(s) FROM SessionPreserved s join s.user u where u.userId=?1")
    long countByUserId(Long userId);

    @Query(value = "SELECT s FROM SessionPreserved s join fetch s.scheme join s.user u  where u.userId=?1")
    Slice<SessionPreserved> findAllByUserId(Long userId, Pageable pageable);

}
