package ua.edu.ratos.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.domain.entity.answer.AcceptedPhrase;


public interface AcceptedPhraseRepository extends JpaRepository<AcceptedPhrase, Long> {

    @Query(value = "SELECT a FROM AcceptedPhrase a join a.staff s where s.staId =?1 order by a.lastUsed desc")
    Page<AcceptedPhrase> findAllLastUsedAcceptedPhrasesByStaffId(Long staId, Pageable pageable);

    default Page<AcceptedPhrase> findAllLastUsedAcceptedPhrasesByStaffId(Long staId) {
        return findAllLastUsedAcceptedPhrasesByStaffId(staId, PageRequest.of(0, 20));
    }
}
