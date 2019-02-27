package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {

     @Query(value = "SELECT r FROM Result r join r.user u join r.scheme s where u.userId=?1 and s.schemeId = ?2")
    Slice<Result> findFirstByUserIdAndSchemeId(Long userId, Long schemeId, Pageable pageable);
}
