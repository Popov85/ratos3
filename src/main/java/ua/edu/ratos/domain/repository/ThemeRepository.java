package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.domain.entity.Theme;

public interface ThemeRepository extends CrudRepository<Theme, Long> {

    @Modifying
    @Query("update Theme t set t.name = ?1 where t.themeId = ?2")
    void updateById(String updatedName, Long themeId);

    @Modifying
    @Query("delete from Theme t where t.themeId = ?1")
    void deleteById(Long themeId);

    @Modifying
    @Query("update Theme t set t.deleted = true where t.themeId = ?1")
    void pseudoDeleteById(Long themeId);
}
