package ua.edu.ratos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.domain.entity.Resource;

public interface ResourceRepository extends CrudRepository<Resource, Long> {
}
