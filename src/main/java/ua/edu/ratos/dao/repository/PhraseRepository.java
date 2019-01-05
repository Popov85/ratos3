package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.Phrase;

public interface PhraseRepository extends JpaRepository<Phrase, Long> {

    @Query(value="SELECT p FROM Phrase p order by p.phrase desc")
    Page<Phrase> findAll(Pageable pageable);

    @Query(value = "SELECT p FROM Phrase p join p.staff s where s.staffId =?1 order by p.lastUsed desc")
    Page<Phrase> findAllLastUsedByStaffId(Long staId, Pageable pageable);

    @Query(value = "SELECT p FROM Phrase p join p.staff s join s.department d where d.depId =?1 order by p.lastUsed desc")
    Page<Phrase> findAllLastUsedByDepId(Long depId, Pageable pageable);

    @Query(value = "SELECT p FROM Phrase p join p.staff s where s.staffId =?1 and p.phrase like ?2% order by p.phrase asc")
    Page<Phrase> findAllLastUsedByStaffIdAndFirstLetters(Long staId, String starts, Pageable pageable);

    @Query(value = "SELECT p FROM Phrase p join p.staff s join s.department d where d.depId =?1 and p.phrase like ?2% order by p.phrase asc")
    Page<Phrase> findAllLastUsedByDepIdAndFirstLetters(Long depId, String starts, Pageable pageable);
}
