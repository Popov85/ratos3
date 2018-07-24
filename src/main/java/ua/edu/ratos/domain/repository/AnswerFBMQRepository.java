package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankMultiple;

public interface AnswerFBMQRepository extends JpaRepository<AnswerFillBlankMultiple, Long> {

    @Query(value = "SELECT a FROM AnswerFillBlankMultiple a join fetch a.acceptedPhrases where a.answerId=?1")
    AnswerFillBlankMultiple findByIdWithAcceptedPhrases(Long answerId);

    @Modifying
    @Transactional
    @Query("update AnswerFillBlankMultiple a set a.deleted = true where a.answerId = ?1")
    void pseudoDeleteById(Long answerId);
}
