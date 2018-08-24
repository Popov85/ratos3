package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankSingle;


public interface AnswerFBSQRepository extends JpaRepository<AnswerFillBlankSingle, Long>{

    @Query(value = "SELECT a FROM AnswerFillBlankSingle a join fetch a.acceptedPhrases where a.answerId=?1")
    AnswerFillBlankSingle findByIdWithAcceptedPhrases(Long answerId);
}