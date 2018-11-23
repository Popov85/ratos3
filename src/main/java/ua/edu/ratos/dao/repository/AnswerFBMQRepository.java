package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.answer.AnswerFillBlankMultiple;

public interface AnswerFBMQRepository extends JpaRepository<AnswerFillBlankMultiple, Long> {

    @Query(value = "SELECT a FROM AnswerFillBlankMultiple a left join fetch a.acceptedPhrases where a.answerId=?1")
    AnswerFillBlankMultiple findByIdWithAcceptedPhrases(Long answerId);
}
