package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.answer.AnswerMultipleChoice;

public interface AnswerMCQRepository extends JpaRepository<AnswerMultipleChoice, Long> {

    @Query(value = "SELECT a FROM AnswerMultipleChoice a left join fetch a.resources where a.answerId=?1")
    AnswerMultipleChoice findByIdWithResources(Long answerId);

    @Modifying
    @Transactional
    @Query("update AnswerMultipleChoice a set a.deleted = true where a.answerId = ?1")
    void pseudoDeleteById(Long answerId);
}