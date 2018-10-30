package ua.edu.ratos.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.domain.entity.Phrase;

public interface PhraseRepository extends JpaRepository<Phrase, Long> {

    @Query(value = "SELECT a FROM Phrase a join a.staff s where s.staffId =?1 order by a.lastUsed desc")
    Page<Phrase> findAllLastUsedByStaffId(Long staId, Pageable pageable);

    @Query(value = "SELECT a FROM Phrase a join a.staff s where s.staffId =?1 and a.phrase like ?2% order by a.phrase asc")
    Page<Phrase> findAllLastUsedByStaffIdAndFirstLetters(Long staId, String starts, Pageable pageable);
}
