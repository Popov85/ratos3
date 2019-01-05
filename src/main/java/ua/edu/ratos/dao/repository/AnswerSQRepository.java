package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.answer.AnswerSQ;

public interface AnswerSQRepository extends JpaRepository<AnswerSQ, Long> {
    @Query(value = "SELECT a FROM AnswerSQ a join fetch a.phrase where a.answerId=?1")
    AnswerSQ findByIdWithResources(Long answerId);
}
