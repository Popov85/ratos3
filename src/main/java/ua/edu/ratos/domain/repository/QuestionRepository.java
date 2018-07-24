package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.question.*;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "SELECT q FROM Question q left join fetch q.resources where q.questionId=?1")
    Question findByIdWithResources(Long questionId);

    @Query(value = "select ty.abbreviation from Question q join q.theme th join q.type ty where th.themeId = ?1")
    List<String> findTypes(Long themeId);

    @Query(value = "SELECT q FROM QuestionMultipleChoice q " +
            "join fetch q.answers left join fetch q.help left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    List<QuestionMultipleChoice> findAllMCQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT q FROM QuestionFillBlankSingle q " +
            "join fetch q.answer left join fetch q.help left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    List<QuestionFillBlankSingle> findAllFBSQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT q FROM QuestionFillBlankMultiple q " +
            "join fetch q.answers left join fetch q.help left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    List<QuestionFillBlankMultiple> findAllFBMQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT q FROM QuestionMatcher q " +
            "join fetch q.answers left join fetch q.help left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    List<QuestionMatcher> findAllMQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT q FROM QuestionSequence q " +
            "join fetch q.answers left join fetch q.help left join fetch q.resources join fetch q.theme join fetch q.type join fetch q.lang " +
            "where q.theme.themeId = ?1")
    List<QuestionSequence> findAllSQWithEverythingByThemeId(Long themeId);


    @Modifying
    @Transactional
    @Query("update Question q set q.deleted = true where q.questionId = ?1")
    void pseudoDeleteById(Long questionId);
}