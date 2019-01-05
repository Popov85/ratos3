package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.answer.AnswerMCQ;

public interface AnswerMCQRepository extends JpaRepository<AnswerMCQ, Long> {

    @Query(value = "SELECT a FROM AnswerMCQ a left join fetch a.resources where a.answerId=?1")
    AnswerMCQ findByIdWithResources(Long answerId);
}
