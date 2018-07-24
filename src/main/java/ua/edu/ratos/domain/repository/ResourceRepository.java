package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
