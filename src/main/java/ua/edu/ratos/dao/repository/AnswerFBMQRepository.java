package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.answer.AnswerFBMQ;

public interface AnswerFBMQRepository extends JpaRepository<AnswerFBMQ, Long> {

    @Query(value = "SELECT a FROM AnswerFBMQ a left join fetch a.acceptedPhrases where a.answerId=?1")
    AnswerFBMQ findByIdWithAcceptedPhrases(Long answerId);
}
