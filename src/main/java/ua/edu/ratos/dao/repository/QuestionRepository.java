package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ua.edu.ratos.dao.entity.question.*;
import javax.persistence.QueryHint;
import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "select ty.typeId from Question q join q.theme th join q.type ty where th.themeId = ?1")
    Set<Long> findTypes(Long themeId);

    @Query(value = "SELECT DISTINCT q FROM QuestionMCQ q left join fetch q.helps h left join fetch h.resources left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang" +
            " join fetch q.answers a left join fetch a.resources" +
            " where q.theme.themeId = ?1")
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionMCQ> findAllMCQWithEverythingByThemeId(Long themeId);

  @Query(value = "SELECT DISTINCT q FROM QuestionFBSQ q left join fetch q.helps h left join fetch h.resources left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang" +
            " join fetch q.answer a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r" +
            " where q.theme.themeId = ?1")
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionFBSQ> findAllFBSQWithEverythingByThemeId(Long themeId);

      @Query(value = "SELECT DISTINCT q FROM QuestionFBMQ q left join fetch q.helps h left join fetch h.resources left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang" +
            " join fetch q.answers a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r" +
            " where q.theme.themeId = ?1")
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionFBMQ> findAllFBMQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT DISTINCT q FROM QuestionMQ q left join fetch q.helps h left join fetch h.resources left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang" +
            " join fetch q.answers a join fetch a.leftPhrase lp left join fetch lp.resources join fetch a.rightPhrase rp left join fetch rp.resources" +
            " where q.theme.themeId = ?1")
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionMQ> findAllMQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT DISTINCT q FROM QuestionSQ q left join fetch q.helps h left join fetch h.resources left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang" +
            " join fetch q.answers a join fetch a.phrase p left join fetch p.resources r" +
            " where q.theme.themeId = ?1")
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionSQ> findAllSQWithEverythingByThemeId(Long themeId);


}