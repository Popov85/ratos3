package ua.edu.ratos.domain.dao;

import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.domain.model.Resource;

public interface ResourceRepository extends CrudRepository<Resource, Long> {
}
