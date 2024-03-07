package ua.edu.ratos.dao.repository.lms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.lms.LTICredentials;

public interface LTICredentialsRepository extends JpaRepository<LTICredentials, Long> {

    @Query(value = "SELECT c FROM LTICredentials c where c.key = ?1")
    LTICredentials findByConsumerKey(String consumerKey);
}
