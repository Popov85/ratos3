package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {

    // For gamification check (probably)
    @Query(value = "SELECT r FROM Result r join r.user u join r.scheme s where u.userId=?1 and s.schemeId = ?2")
    Slice<Result> findFirstByUserIdAndSchemeId(Long userId, Long schemeId, Pageable pageable);

    // For finding latest taken schemes IDs to populate cache on start-up
    @Query(value = "SELECT s.schemeId FROM Result r join r.scheme s")
    Slice<Long> findLatestTakenSchemesIds(Pageable pageable);

}
