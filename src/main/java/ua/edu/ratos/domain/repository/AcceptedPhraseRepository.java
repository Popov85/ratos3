package ua.edu.ratos.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.domain.entity.answer.AcceptedPhrase;

public interface AcceptedPhraseRepository extends JpaRepository<AcceptedPhrase, Long> {

    @Query(value = "SELECT a FROM AcceptedPhrase a join a.staff s where s.staffId =?1 order by a.lastUsed desc")
    Page<AcceptedPhrase> findAllLastUsedByStaffId(Long staId, Pageable pageable);

    @Query(value = "SELECT a FROM AcceptedPhrase a join a.staff s where s.staffId =?1 and a.phrase like ?2% order by a.phrase asc")
    Page<AcceptedPhrase> findAllLastUsedByStaffIdAndFirstLetters(Long staId, String starts, Pageable pageable);

}
