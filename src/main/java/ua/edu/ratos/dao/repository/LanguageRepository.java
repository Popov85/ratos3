package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
