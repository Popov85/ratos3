package ua.edu.ratos.dao.repository.lms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.lms.LMS;


public interface LMSRepository extends JpaRepository<LMS, Long> {

    @Query(value = "SELECT lms FROM LMS lms join lms.credentials c where c.key = ?1")
    LMS findByConsumerKey(String consumerKey);
}
