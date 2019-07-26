package ua.edu.ratos.dao.repository.lms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.lms.LMS;

import java.util.Optional;

public interface LMSRepository extends JpaRepository<LMS, Long> {

    //---------------------------------------------------STUDENT LTI session--------------------------------------------

    @Query(value = "SELECT lms FROM LMS lms join lms.credentials c where c.key = ?1")
    LMS findByConsumerKey(String consumerKey);

    //----------------------------------------------------ONE for edit--------------------------------------------------

    @Query(value = "SELECT l FROM LMS l join fetch l.ltiVersion join fetch l.origins join fetch l.credentials c where l.lmsId = ?1")
    LMS findOneForEditById(Long lmsId);

    //---------------------------------------------ONE for self-registration--------------------------------------------

    @Query(value = "SELECT l FROM LMS l join fetch l.organisation where l.lmsId = ?1")
    Optional<LMS> findOneForRegById(Long lmsId);

    //---------------------------------------------------ORG-ADMIN table------------------------------------------------

    @Query(value="select l from LMS l join l.organisation o where o.orgId = ?1")
    Slice<LMS> findAllByOrgId(Long orgId, Pageable pageable);

    @Query(value="select l from LMS l join l.organisation o where o.orgId = ?1 and l.name like %?2%")
    Slice<LMS> findAllByOrgIdAndNameLettersContains(Long orgId, String letters, Pageable pageable);

    //--------------------------------------------------------ADMIN-----------------------------------------------------

    @Query(value="select l from LMS l join fetch l.ltiVersion join fetch l.origins join fetch l.credentials c join fetch l.organisation",
            countQuery = "select count(l) from LMS l")
    Page<LMS> findAllAdmin(Pageable pageable);

}
