package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.domain.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
