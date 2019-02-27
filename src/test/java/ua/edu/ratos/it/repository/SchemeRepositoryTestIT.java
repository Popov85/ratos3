package ua.edu.ratos.it.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.it.ActiveProfile;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SchemeRepositoryTestIT {

    @Autowired
    private SchemeRepository schemeRepository;


    //----------------------------------------------------ONE for update-----------------------------------------------
    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_groups_themes.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForUpdateByIdTest() {
        Scheme scheme = schemeRepository.findForEditById(1L);
        Assert.assertEquals(5, scheme.getGroups().size());
        Assert.assertEquals(5L, scheme.getThemes().size());
        scheme.getThemes().forEach(t->Assert.assertEquals(1, t.getSettings().size()));
    }

    //-------------------------------------------------------SESSION---------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_session.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForSessionByIdTest() {
        Scheme scheme = schemeRepository.findForSessionById(1L);
        Assert.assertEquals(5, scheme.getThemes().size());
        Assert.assertEquals(1, scheme.getGroups().size());
        scheme.getGroups().forEach(g->Assert.assertEquals(2, g.getStudents().size()));
    }

    //------------------------------------------------------SECURITY--------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForSecurityByIdTest() {
        Scheme scheme = schemeRepository.findForSecurityById(1L);
        Assert.assertEquals(1L, scheme.getAccess().getAccessId().longValue());
        Assert.assertEquals(1L, scheme.getStaff().getStaffId().longValue());
        Assert.assertEquals(1L, scheme.getStaff().getDepartment().getDepId().longValue());
    }

    //--------------------------------------------------INSTRUCTOR table----------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        List<Scheme> schemes = schemeRepository.findAllByStaffId(1L, PageRequest.of(0, 100)).getContent();
        Assert.assertEquals(10, schemes.size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameContainsTest() {
        List<Scheme> schemes = schemeRepository.findAllByStaffIdAndNameLettersContains(1L, "trainee", PageRequest.of(0, 100)).getContent();
        Assert.assertEquals(4, schemes.size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        Page<Scheme> schemes = schemeRepository.findAllByDepartmentId(3L, PageRequest.of(0, 100));
        Assert.assertEquals(5, schemes.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameContainsTest() {
        Page<Scheme> schemes = schemeRepository.findAllByDepartmentIdAndNameContains(2L, "exam", PageRequest.of(0, 100));
        Assert.assertEquals(3, schemes.getContent().size());
    }

    //-------------------------------------------------DROPDOWN slice-------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForDropDownByDepartmentIdTest() {
        Slice<Scheme> schemes = schemeRepository.findAllForDropDownByDepartmentId(3L, PageRequest.of(0, 100));
        Assert.assertFalse(schemes.hasNext());
        Assert.assertEquals(5, schemes.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForDropDownByCourseIdTest() {
        Slice<Scheme> schemes = schemeRepository.findAllForDropDownByCourseId(1L, PageRequest.of(0, 100));
        Assert.assertFalse(schemes.hasNext());
        Assert.assertEquals(20, schemes.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForDropDownByCourseIdMultipleSlicesTest() {
        Slice<Scheme> schemes = schemeRepository.findAllForDropDownByCourseId(1L, PageRequest.of(0, 10));
        Assert.assertTrue(schemes.hasNext());
        Assert.assertEquals(10, schemes.getContent().size());
    }


    //---------------------------------------------------ADMIN--------------------------------------------------------


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllTest() {
        List<Scheme> schemes = schemeRepository.findAll(PageRequest.of(0, 100)).getContent();
        Assert.assertEquals(20, schemes.size());
    }
}
