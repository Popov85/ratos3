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

    @Query(value = "SELECT DISTINCT q FROM QuestionMultipleChoice q " +
            "join fetch q.answers a left join fetch a.resources left join fetch q.help h left join fetch h.helpResource r join fetch r.resource left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionMultipleChoice> findAllMCQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT DISTINCT q FROM QuestionFillBlankSingle q " +
            "join fetch q.answer a join fetch a.settings join fetch a.acceptedPhrases left join fetch q.help h left join fetch h.helpResource r join fetch r.resource left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionFillBlankSingle> findAllFBSQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT DISTINCT q FROM QuestionFillBlankMultiple q " +
            "join fetch q.answers a join fetch a.acceptedPhrases join fetch a.settings left join fetch q.help h left join fetch h.helpResource r join fetch r.resource left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionFillBlankMultiple> findAllFBMQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT DISTINCT q FROM QuestionMatcher q " +
            "join fetch q.answers a join fetch a.leftPhrase join fetch a.rightPhrase left join fetch q.help h left join fetch h.helpResource r join fetch r.resource left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionMatcher> findAllMQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT DISTINCT q FROM QuestionSequence q " +
            "join fetch q.answers a join fetch a.phrase left join fetch q.help h left join fetch h.helpResource r join fetch r.resource left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    @QueryHints(value = { @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionSequence> findAllSQWithEverythingByThemeId(Long themeId);


}