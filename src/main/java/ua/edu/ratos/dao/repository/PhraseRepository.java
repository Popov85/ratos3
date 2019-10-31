package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Phrase;

import java.util.Optional;

public interface PhraseRepository extends JpaRepository<Phrase, Long> {

    //-------------------------------------------------------One for update---------------------------------------------
    @Query(value = "SELECT p FROM Phrase p join fetch p.staff s left join fetch p.resources where p.phraseId =?1")
    Optional<Phrase> findOneForEdit(Long phraseId);

    //----------------------------------------------Instructor for table & dropdown-------------------------------------
    @Query(value = "SELECT p FROM Phrase p join fetch p.staff s left join fetch p.resources where s.staffId =?1",
            countQuery = "SELECT count(p) FROM Phrase p join p.staff s where s.staffId =?1")
    Page<Phrase> findAllByStaffId(Long staffId, Pageable pageable);

    @Query(value = "SELECT p FROM Phrase p join fetch p.staff s left join fetch p.resources join s.department d where d.depId =?1",
            countQuery = "SELECT count(p) FROM Phrase p join p.staff s join s.department d where d.depId =?1")
    Page<Phrase> findAllByDepartmentId(Long depId, Pageable pageable);

    //-----------------------------------------------------Search in table----------------------------------------------
    @Query(value = "SELECT p FROM Phrase p join p.staff s left join fetch p.resources where s.staffId =?1 and p.phrase like ?2%",
            countQuery = "SELECT count(p) FROM Phrase p join p.staff s where s.staffId =?1 and p.phrase like ?2%")
    Page<Phrase> findAllByStaffIdAndPhraseStarts(Long staffId, String starts, Pageable pageable);

    @Query(value = "SELECT p FROM Phrase p join p.staff s left join fetch p.resources where s.staffId =?1 and p.phrase like %?2%",
            countQuery = "SELECT count(p) FROM Phrase p join p.staff s where s.staffId =?1 and p.phrase like %?2%")
    Page<Phrase> findAllByStaffIdAndPhraseLettersContains(Long staffId, String letters, Pageable pageable);

    @Query(value = "SELECT p FROM Phrase p join fetch p.staff s left join fetch p.resources join s.department d where d.depId =?1 and p.phrase like ?2%",
            countQuery = "SELECT count(p) FROM Phrase p join p.staff s join s.department d where d.depId =?1 and p.phrase like ?2%")
    Page<Phrase> findAllByDepartmentIdAndPhraseStarts(Long depId, String starts, Pageable pageable);

    @Query(value = "SELECT p FROM Phrase p join fetch p.staff s left join fetch p.resources join s.department d where d.depId =?1 and p.phrase like %?2%",
            countQuery = "SELECT count(p) FROM Phrase p join p.staff s join s.department d where d.depId =?1 and p.phrase like %?2%")
    Page<Phrase> findAllByDepartmentIdAndPhraseLettersContains(Long depId, String letters, Pageable pageable);

    // -----------------------------------------------------------ADMIN-------------------------------------------------
    @Query(value="SELECT p FROM Phrase p join fetch p.staff left join fetch p.resources", countQuery = "SELECT count(p) FROM Phrase p")
    Page<Phrase> findAll(Pageable pageable);
}
