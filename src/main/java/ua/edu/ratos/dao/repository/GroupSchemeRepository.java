package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.GroupScheme;
import ua.edu.ratos.dao.entity.GroupSchemeId;

public interface GroupSchemeRepository extends JpaRepository<GroupScheme, GroupSchemeId> {
}
