package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.answer.AnswerMatcher;

public interface AnswerMQRepository extends JpaRepository<AnswerMatcher, Long> {

    @Query(value = "SELECT a FROM AnswerMatcher a left join fetch a.resources where a.answerId=?1")
    AnswerMatcher findByIdWithResources(Long answerId);

    @Modifying
    @Transactional
    @Query("update AnswerMatcher a set a.deleted = true where a.answerId = ?1")
    void pseudoDeleteById(Long answerId);
}
