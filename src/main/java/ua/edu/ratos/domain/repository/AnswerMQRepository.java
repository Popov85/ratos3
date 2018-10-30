package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.domain.entity.answer.AnswerMatcher;

public interface AnswerMQRepository extends JpaRepository<AnswerMatcher, Long> {

    @Query(value = "SELECT a FROM AnswerMatcher a join fetch a.leftPhrase join fetch a.rightPhrase where a.answerId=?1")
    AnswerMatcher findByIdWithResources(Long answerId);
}
