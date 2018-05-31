package ua.edu.ratos.domain.dao;

import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.domain.model.Theme;

public interface ThemeRepository extends CrudRepository<Theme, Long> {
}
