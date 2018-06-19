package ua.edu.ratos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.service.dto.Session;

public interface SessionRepository extends CrudRepository<Session, String>{
}
