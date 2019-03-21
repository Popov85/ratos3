package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.dao.repository.projections.TypeAndCount;

import javax.persistence.QueryHint;
import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    //------------------------------------------------------QUESTION TYPES----------------------------------------------
    // You get only unique longs in the resulting set, full search for about 50-300 questions in theme
    @Query(value = "select ty.typeId from Question q join q.theme th join q.type ty where th.themeId = ?1")
    Set<Long> findTypes(Long themeId);

    //------------------------------------------------------COUNT by types----------------------------------------------

    @Query(value = "select ty.typeId as type, ty.abbreviation as abbreviation, count(q) as count from Question q join q.theme th join q.type ty where th.themeId = ?1 group by ty")
    Set<TypeAndCount> countAllTypesByThemeId(Long themeId);

    //-------------------------------------------------For Scheme creating support--------------------------------------

    @Query(value = "SELECT q FROM Question q join fetch q.type join q.theme t where t.themeId = ?1")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<Question> findAllForTypeLevelMapByThemeId(Long themeId);

    //-------------------------------------------------STUDENT SESSION (jpa universal)----------------------------------

    @Query(value = "SELECT q FROM Question q join q.theme t join q.type ty where t.themeId = ?1 and ty.typeId = ?2")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<Question> findAllForSessionByThemeIdAndType(Long themeId, Long typeId);

    //-------------------------------------------------STUDENT SESSION (db specific)------------------------------------

    @Query(value = "SELECT * FROM question where question.theme_id =?1 and question.type_id=?2 and question.level=?3 and question.is_required = true", nativeQuery = true)
    Set<Question> findAllRequiredForSessionByThemeIdAndTypeAndLevel(Long themeId, Long typeId, byte level);

    /**
     * ORDER BY RAND() copies the whole table into a temporary table and adds a new column with a random value.
     * Finally, it sorts the data by that column. This of course, has an impact on performance, so it's not recommended.
     * @see "https://stackoverflow.com/questions/32283055/sql-order-by-rand"
     */
    @Query(value = "SELECT * FROM question where question.theme_id =?1 and question.type_id=?2 and question.level=?3 and question.is_required = false ORDER BY RAND() LIMIT ?4", nativeQuery = true)
    Set<Question> findNOutOfMForSessionByThemeIdAndTypeAndLevel(Long themeId, Long typeId, byte level, int quantity);


    //---------------------------------------------------------One for edit---------------------------------------------

    @Query(value = "SELECT q FROM QuestionMCQ q join fetch q.theme join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a left join fetch a.resources where q.questionId =?1")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    QuestionMCQ findOneMCQForEditById(Long questionId);

    @Query(value = "SELECT q FROM QuestionFBSQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answer a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r where q.questionId =?1")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    QuestionFBSQ findOneFBSQForEditById(Long questionId);

    @Query(value = "SELECT q FROM QuestionFBMQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r where q.questionId =?1")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    QuestionFBMQ findOneFBMQForEditById(Long questionId);

    @Query(value = "SELECT q FROM QuestionMQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.leftPhrase lp left join fetch lp.resources join fetch a.rightPhrase rp left join fetch rp.resources where q.questionId =?1")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    QuestionMQ findOneMQForEditById(Long questionId);

    @Query(value = "SELECT q FROM QuestionSQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.phrase p left join fetch p.resources r where q.questionId =?1")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    QuestionSQ findOneSQForEditById(Long questionId);


    //-------------------------------------------------------INSTRUCTOR table-------------------------------------------
    // paging {100, 200, 500, all}

    @Query(value = "SELECT q FROM QuestionMCQ q join fetch q.theme join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a left join fetch a.resources where q.theme.themeId =?1 and ty.typeId=1", countQuery = "SELECT count(q) FROM Question q join q.theme t join q.type ty where t.themeId=?1 and ty.typeId=1")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<QuestionMCQ> findAllMCQForEditByThemeId(Long themeId, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionFBSQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answer a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=2", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t where t.themeId=?1 and ty.typeId=2")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<QuestionFBSQ> findAllFBSQForEditByThemeId(Long themeId, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionFBMQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=3", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t where t.themeId=?1 and ty.typeId=3")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<QuestionFBMQ> findAllFBMQForEditByThemeId(Long themeId, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionMQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.leftPhrase lp left join fetch lp.resources join fetch a.rightPhrase rp left join fetch rp.resources where t.themeId = ?1 and ty.typeId=4", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t where t.themeId=?1 and ty.typeId=4")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<QuestionMQ> findAllMQForEditByThemeId(Long themeId, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionSQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.phrase p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=5", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t where t.themeId=?1 and ty.typeId=5")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<QuestionSQ> findAllSQForEditByThemeId(Long themeId, Pageable pageable);


    //----------------------------------INSTRUCTOR (global search throughout department)--------------------------------
    // first slice of 30

    @Query(value = "SELECT q FROM QuestionMCQ q join fetch q.theme t join t.department d join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a left join fetch a.resources where d.depId =?1 and ty.typeId=1 and q.question like %?2%", countQuery = "SELECT count(q) FROM Question q join q.theme t join t.department d join q.type ty where d.depId=?1 and ty.typeId=1 and q.question like %?2%")
    Slice<QuestionMCQ> findAllMCQForSearchByDepartmentIdAndTitleContains(Long depId, String starts, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionFBSQ q join fetch q.theme t join t.department d join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answer a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r where d.depId = ?1 and ty.typeId=2 and q.question like %?2%", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t join t.department d where d.depId=?1 and ty.typeId=2 and q.question like %?2%")
    Slice<QuestionFBSQ> findAllFBSQForSearchByDepartmentIdAndTitleContains(Long depId, String starts, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionFBMQ q join fetch q.theme t join t.department d join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r where d.depId = ?1 and ty.typeId=3 and q.question like %?2%", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t join t.department d where d.depId=?1 and ty.typeId=3 and q.question like %?2%")
    Slice<QuestionFBMQ> findAllFBMQForSearchByDepartmentIdAndTitleContains(Long depId, String starts, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionMQ q join fetch q.theme t join t.department d join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.leftPhrase lp left join fetch lp.resources join fetch a.rightPhrase rp left join fetch rp.resources where d.depId = ?1 and ty.typeId=4 and q.question like %?2%", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t join t.department d where d.depId=?1 and ty.typeId=4 and q.question like %?2%")
    Slice<QuestionMQ> findAllMQForSearchByDepartmentIdAndTitleContains(Long depId, String starts, Pageable pageable);

    @Query(value = "SELECT q FROM QuestionSQ q join fetch q.theme t join t.department d join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.phrase p left join fetch p.resources r where d.depId = ?1 and ty.typeId=5 and q.question like %?2%", countQuery = "SELECT count(q) FROM Question q join q.type ty join q.theme t join t.department d where d.depId=?1 and ty.typeId=5 and q.question like %?2%")
    Slice<QuestionSQ> findAllSQForSearchByDepartmentIdAndTitleContains(Long depId, String starts, Pageable pageable);

    //-----------------------------------------------------FORCE CACHE L2C for Session----------------------------------

    @Query(value = "SELECT q FROM QuestionMCQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a left join fetch a.resources where t.themeId = ?1 and ty.typeId=1 and q.level = ?2")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionMCQ> findAllMCQForCacheWithEverythingByThemeId(Long themeId, byte level);

    @Query(value = "SELECT q FROM QuestionFBSQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answer a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=2 and q.level = ?2")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionFBSQ> findAllFBSQForCacheWithEverythingByThemeId(Long themeId, byte level);

    @Query(value = "SELECT q FROM QuestionFBMQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.settings join fetch a.acceptedPhrases p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=3 and q.level = ?2")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionFBMQ> findAllFBMQForCacheWithEverythingByThemeId(Long themeId, byte level);

    @Query(value = "SELECT q FROM QuestionMQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.leftPhrase lp left join fetch lp.resources join fetch a.rightPhrase rp left join fetch rp.resources where t.themeId = ?1 and ty.typeId=4 and q.level = ?2")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionMQ> findAllMQForCacheWithEverythingByThemeId(Long themeId, byte level);

    @Query(value = "SELECT q FROM QuestionSQ q join fetch q.theme t join fetch q.type ty join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources" +
            " join fetch q.answers a join fetch a.phrase p left join fetch p.resources r where t.themeId = ?1 and ty.typeId=5 and q.level = ?2")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Set<QuestionSQ> findAllSQForCacheWithEverythingByThemeId(Long themeId, byte level);

}