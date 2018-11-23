package ua.edu.ratos.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.dao.entity.ThemeView;
import ua.edu.ratos.dao.entity.ThemeViewId;
import java.util.Set;

public interface ThemeViewRepository extends CrudRepository<ThemeView, ThemeViewId> {

    @Query(value="select t from ThemeView t where t.themeViewId.themeId = ?1")
    Set<ThemeView> findAllByThemeId(Long themeId);

    @Query(value="select t from ThemeView t where t.themeViewId.courseId = ?1")
    Set<ThemeView> findAllByCourseId(Long courseId);

    @Query(value="select t from ThemeView t where t.themeViewId.courseId = ?1 and t.theme like %?2%")
    Set<ThemeView> findAllByCourseIdAndThemeLettersContains(Long courseId, String contains);

    @Query(value="select t from ThemeView t where t.themeViewId.depId = ?1")
    Page<ThemeView> findAllByDepartmentId(Long depId, Pageable pageable);

    @Query(value="select t from ThemeView t where t.themeViewId.depId = ?1 and t.theme like %?2%")
    Set<ThemeView> findAllByDepartmentIdAndThemeLettersContains(Long depId, String contains);

    @Query(value="select t from ThemeView t where t.themeViewId.facId = ?1")
    Page<ThemeView> findAllByFacultyId(Long facId, Pageable pageable);

    @Query(value="select t from ThemeView t where t.themeViewId.facId = ?1 and t.theme like %?2%")
    Set<ThemeView> findAllByFacultyIdAndThemeLettersContains(Long facId, String contains);

    @Query(value="select t from ThemeView t where t.themeViewId.orgId = ?1")
    Page<ThemeView> findAllByOrganisationId(Long orgId, Pageable pageable);

    @Query(value="select t from ThemeView t where t.themeViewId.orgId = ?1 and t.theme like %?2%")
    Set<ThemeView> findAllByOrganisationIdAndThemeLettersContains(Long orgId, String contains);

    @Query(value="select t from ThemeView t")
    Page<ThemeView> findAll(Pageable pageable);

    @Query(value="select t from ThemeView t where t.theme like %?2%")
    Set<ThemeView> findAllByThemeLettersContains(String contains);
}
