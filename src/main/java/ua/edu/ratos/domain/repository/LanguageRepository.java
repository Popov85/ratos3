package ua.edu.ratos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.domain.entity.Language;

public interface LanguageRepository extends CrudRepository<Language, Long> {
}
