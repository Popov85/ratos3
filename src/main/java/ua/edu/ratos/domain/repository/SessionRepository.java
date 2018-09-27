package ua.edu.ratos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.domain.model.SessionData;

public interface SessionRepository extends CrudRepository<SessionData, String> {
}
