package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankMultiple;

public interface AnswerFBMQRepository extends JpaRepository<AnswerFillBlankMultiple, Long> {

    @Query(value = "SELECT a FROM AnswerFillBlankMultiple a left join fetch a.acceptedPhrases where a.answerId=?1")
    AnswerFillBlankMultiple findByIdWithAcceptedPhrases(Long answerId);
}
