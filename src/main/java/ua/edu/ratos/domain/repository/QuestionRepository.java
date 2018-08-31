package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.question.*;
import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "select ty.abbreviation from Question q join q.theme th join q.type ty where th.themeId = ?1")
    Set<String> findTypes(Long themeId);

    @Query(value = "SELECT DISTINCT q FROM QuestionMultipleChoice q " +
            "join fetch q.answers a left join fetch a.resources left join fetch q.help h left join fetch h.resources left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    Set<QuestionMultipleChoice> findAllMCQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT DISTINCT q FROM QuestionFillBlankSingle q " +
            "join fetch q.answer a join fetch a.settings join fetch a.acceptedPhrases left join fetch q.help h left join fetch h.resources left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    Set<QuestionFillBlankSingle> findAllFBSQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT DISTINCT q FROM QuestionFillBlankMultiple q " +
            "join fetch q.answers a join fetch a.acceptedPhrases join fetch a.settings left join fetch q.help h left join fetch h.resources left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    Set<QuestionFillBlankMultiple> findAllFBMQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT DISTINCT q FROM QuestionMatcher q " +
            "join fetch q.answers a left join fetch a.resources left join fetch q.help h left join fetch h.resources left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    Set<QuestionMatcher> findAllMQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT DISTINCT q FROM QuestionSequence q " +
            "join fetch q.answers a left join fetch a.resources left join fetch q.help h left join fetch h.resources left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    Set<QuestionSequence> findAllSQWithEverythingByThemeId(Long themeId);


    @Modifying
    @Transactional
    @Query("update Question q set q.deleted = true where q.questionId = ?1")
    void pseudoDeleteById(Long questionId);

}