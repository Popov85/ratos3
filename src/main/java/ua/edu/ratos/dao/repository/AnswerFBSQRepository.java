package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.answer.AnswerFBSQ;


public interface AnswerFBSQRepository extends JpaRepository<AnswerFBSQ, Long>{

    @Query(value = "SELECT a FROM AnswerFBSQ a join fetch a.acceptedPhrases where a.answerId=?1")
    AnswerFBSQ findByIdWithAcceptedPhrases(Long answerId);
}
