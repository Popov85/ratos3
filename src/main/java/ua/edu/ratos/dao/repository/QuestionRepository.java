package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ua.edu.ratos.dao.entity.question.*;
import javax.persistence.QueryHint;
import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    //-----------------------------------------QUESTION TYPES--------------------------------------
    // You get only unique longs in the resulting set, full search for about 50-300 questions in theme
    @Query(value = "select ty.typeId from Question q join q.theme th join q.type ty where th.themeId = ?1")
    Set<Long> findTypes(Long themeId);

    //-----------------------------------------STUDENT SESSION--------------------------------------

    @Query(value = "SELECT q FROM QuestionMCQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a left join fetch a.resources where t.themeId = ?1 and ty.typeId=1")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionMCQ> findAllMCQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT q FROM QuestionFBSQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answer a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=2")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionFBSQ> findAllFBSQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT q FROM QuestionFBMQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=3")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionFBMQ> findAllFBMQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT q FROM QuestionMQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.leftPhrase lp left join fetch lp.resources join fetch a.rightPhrase rp left join fetch rp.resources where t.themeId = ?1 and ty.typeId=4")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionMQ> findAllMQWithEverythingByThemeId(Long themeId);

    @Query(value = "SELECT q FROM QuestionSQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.phrase p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=5")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionSQ> findAllSQWithEverythingByThemeId(Long themeId);

    //-------------------------------------------INSTRUCTOR table------------------------------------

    @Query(value = "SELECT q FROM QuestionMCQ q join fetch q.theme join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a left join fetch a.resources where q.theme.themeId =?1 and ty.typeId=1", countQuery = "SELECT count(q) FROM Question q join q.theme t join q.type ty where t.themeId=?1 and ty.typeId=1")
    Page<QuestionMCQ> findAllMCQForEditByThemeId(Long themeId, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionFBSQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answer a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=2", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t where t.themeId=?1 and ty.typeId=2")
    Page<QuestionFBSQ> findAllFBSQForEditByThemeId(Long themeId, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionFBMQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=3", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t where t.themeId=?1 and ty.typeId=3")
    Page<QuestionFBMQ> findAllFBMQForEditByThemeId(Long themeId, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionMQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.leftPhrase lp left join fetch lp.resources join fetch a.rightPhrase rp left join fetch rp.resources where t.themeId = ?1 and ty.typeId=4", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t where t.themeId=?1 and ty.typeId=4")
    Page<QuestionMQ> findAllMQForEditByThemeId(Long themeId, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionSQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.phrase p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=5", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t where t.themeId=?1 and ty.typeId=5")
    Page<QuestionSQ> findAllSQForEditByThemeId(Long themeId, Pageable pageable);

    //-------------------------------------INSTRUCTOR table (search)-----------------------------------

    @Query(value = "SELECT q FROM QuestionMCQ q join fetch q.theme join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a left join fetch a.resources where q.theme.themeId =?1 and ty.typeId=1 and q.question like %?2%", countQuery = "SELECT count(q) FROM Question q join q.theme t join q.type ty where t.themeId=?1 and ty.typeId=1 and q.question like %?2%")
    Page<QuestionMCQ> findAllMCQForEditByThemeIdAndQuestionLettersContains(Long themeId, String letters, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionFBSQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answer a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=2 and q.question like %?2%", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t where t.themeId=?1 and ty.typeId=2 and q.question like %?2%")
    Page<QuestionFBSQ> findAllFBSQForEditByThemeIdAndQuestionLettersContains(Long themeId, String letters, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionFBMQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=3 and q.question like %?2%", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t where t.themeId=?1 and ty.typeId=3 and q.question like %?2%")
    Page<QuestionFBMQ> findAllFBMQForEditByThemeIdAndQuestionLettersContains(Long themeId, String letters, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionMQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.leftPhrase lp left join fetch lp.resources join fetch a.rightPhrase rp left join fetch rp.resources where t.themeId = ?1 and ty.typeId=4 and q.question like %?2%", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t where t.themeId=?1 and ty.typeId=4 and q.question like %?2%")
    Page<QuestionMQ> findAllMQForEditByThemeIdAndQuestionLettersContains(Long themeId, String letters, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionSQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.phrase p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=5 and q.question like %?2%", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t where t.themeId=?1 and ty.typeId=5 and q.question like %?2%")
    Page<QuestionSQ> findAllSQForEditByThemeIdAndQuestionLettersContains(Long themeId, String letters, Pageable pageable);

}