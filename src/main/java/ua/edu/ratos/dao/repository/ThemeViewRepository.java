package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.ThemeView;
import ua.edu.ratos.dao.entity.ThemeViewId;
import java.util.Set;

public interface ThemeViewRepository extends JpaRepository<ThemeView, ThemeViewId> {

    // -----------------Instructors (for getting questions statistics on theme)--------------

    @Query(value="select t from ThemeView t where t.themeViewId.themeId = ?1")
    Set<ThemeView> findAllByThemeId(Long themeId);

    //--------------------------------Instructors create scheme------------------------------

    @Query(value="select t from ThemeView t where t.depId = ?1")
    Set<ThemeView> findAllByDepartmentId(Long depId);

    @Query(value="select t from ThemeView t where t.depId = ?1 and t.theme like %?2%")
    Set<ThemeView> findAllByDepartmentIdAndThemeLettersContains(Long depId, String letters);

    @Query(value="select t from ThemeView t where t.depId = ?1 and t.courseId = ?2")
    Set<ThemeView> findAllByDepartmentIdAndCourseId(Long depId, Long courseId);

    @Query(value="select t from ThemeView t where t.depId = ?1 and t.courseId= ?2 and t.theme like %?3%")
    Set<ThemeView> findAllByDepartmentIdAndCourseIdAndThemeLettersContains(Long depId, Long courseId, String letters);
}
