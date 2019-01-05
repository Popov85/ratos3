package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.answer.AnswerMQ;

public interface AnswerMQRepository extends JpaRepository<AnswerMQ, Long> {

    @Query(value = "SELECT a FROM AnswerMQ a join fetch a.leftPhrase join fetch a.rightPhrase where a.answerId=?1")
    AnswerMQ findByIdWithResources(Long answerId);
}
