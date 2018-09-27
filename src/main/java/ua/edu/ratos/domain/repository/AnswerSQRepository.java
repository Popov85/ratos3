package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.domain.entity.answer.AnswerSequence;

public interface AnswerSQRepository extends JpaRepository<AnswerSequence, Long> {
    @Query(value = "SELECT a FROM AnswerSequence a left join fetch a.resources where a.answerId=?1")
    AnswerSequence findByIdWithResources(Long answerId);
}
