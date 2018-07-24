package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import ua.edu.ratos.domain.entity.ThemeView;
import ua.edu.ratos.domain.entity.ThemeViewId;

import java.util.List;

public interface ThemeViewRepository extends Repository<ThemeView, ThemeViewId> {

    @Query(value="select t from ThemeView t where t.themeViewId.courseId = ?1")
    List<ThemeView> findAllByCourseId(Long courseId);

    @Query(value="select t from ThemeView t where t.themeViewId.themeId = ?1")
    List<ThemeView> findAllByThemeId(Long themeId);
}
