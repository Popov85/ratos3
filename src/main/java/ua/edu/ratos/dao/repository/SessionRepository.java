package ua.edu.ratos.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.service.session.domain.SessionData;

public interface SessionRepository extends CrudRepository<SessionData, String> {
}
