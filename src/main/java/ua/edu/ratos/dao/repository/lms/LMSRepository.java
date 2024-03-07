package ua.edu.ratos.dao.repository.lms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.lms.LMS;

import java.util.Optional;
import java.util.Set;

public interface LMSRepository extends JpaRepository<LMS, Long> {

    //---------------------------------------------------STUDENT LTI session--------------------------------------------
    @Query(value = "SELECT lms FROM LMS lms join lms.credentials c where c.key = ?1")
    Optional<LMS> findByConsumerKey(String consumerKey);

    //---------------------------------------------ONE for self-registration--------------------------------------------
    @Query(value = "SELECT l FROM LMS l join fetch l.organisation where l.lmsId = ?1")
    Optional<LMS> findOneForRegById(Long lmsId);

    //--------------------------------------------------Staff drop-down-------------------------------------------------
    @Query(value="select new LMS(l.lmsId, l.name) from LMS l join l.organisation o where o.orgId = ?1")
    Set<LMS> findAllForDropdownByOrgId(Long orgId);

    //--------------------------------------------------Org admin table-------------------------------------------------
    @Query(value="select l from LMS l join fetch l.credentials join fetch l.ltiVersion join l.organisation o where o.orgId = ?1")
    Set<LMS> findAllForTableByOrgId(Long orgId);

    //--------------------------------------------------------ADMIN-----------------------------------------------------
    @Query(value="select l from LMS l join fetch l.ltiVersion join fetch l.credentials c join fetch l.organisation",
            countQuery = "select count(l) from LMS l")
    Page<LMS> findAllAdmin(Pageable pageable);
}
