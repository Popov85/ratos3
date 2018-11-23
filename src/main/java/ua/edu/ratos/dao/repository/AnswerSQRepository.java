package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.answer.AnswerSequence;

public interface AnswerSQRepository extends JpaRepository<AnswerSequence, Long> {
    @Query(value = "SELECT a FROM AnswerSequence a join fetch a.phrase where a.answerId=?1")
    AnswerSequence findByIdWithResources(Long answerId);
}
