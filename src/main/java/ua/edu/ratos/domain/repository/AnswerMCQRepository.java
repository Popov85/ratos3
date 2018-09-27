package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.domain.entity.answer.AnswerMultipleChoice;

public interface AnswerMCQRepository extends JpaRepository<AnswerMultipleChoice, Long> {

    @Query(value = "SELECT a FROM AnswerMultipleChoice a left join fetch a.resources where a.answerId=?1")
    AnswerMultipleChoice findByIdWithResources(Long answerId);
}
