package ua.edu.ratos.dao.repository.lms;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.lms.LTIVersion;

public interface LTIVersionRepository extends JpaRepository<LTIVersion, Long> {
}
