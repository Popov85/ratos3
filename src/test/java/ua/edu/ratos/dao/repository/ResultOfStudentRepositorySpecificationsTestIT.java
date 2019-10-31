package ua.edu.ratos.dao.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.ActiveProfile;
import ua.edu.ratos.dao.entity.ResultOfStudent;
import ua.edu.ratos.dao.repository.specs.ResultOfStudentStaffSpecs;
import ua.edu.ratos.service.dto.in.criteria.results.ResultOfStudentCriteriaStaffInDto;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ResultOfStudentRepositorySpecificationsTestIT {

    @Autowired
    private ResultOfStudentRepository resultOfStudentRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private PersistenceUnitUtil persistenceUnitUtil;

    @Before
    public void setUp() {
        persistenceUnitUtil = entityManagerFactory.getPersistenceUnitUtil();
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        Specification<ResultOfStudent> specs = ResultOfStudentStaffSpecs.ofDepartment(1L);
        Page<ResultOfStudent> page = resultOfStudentRepository.findAll(specs,
                PageRequest.of(0, 100, new Sort(Sort.Direction.ASC, Arrays.asList("grade"))));
        assertThat("Page of Students Results is not as expected", page, allOf(
                hasProperty("content", hasSize(6)),
                hasProperty("totalPages", equalTo(1)),
                hasProperty("totalElements", equalTo(6L))));
        page.getContent()
                .forEach(r ->{
                    assertTrue("Scheme was not loaded", persistenceUnitUtil.isLoaded(r, "scheme"));
                    assertTrue("Student was not loaded", persistenceUnitUtil.isLoaded(r, "student"));
                    assertTrue("Department was not loaded", persistenceUnitUtil.isLoaded(r, "department"));
                    assertTrue("User of Staff was not loaded", persistenceUnitUtil.isLoaded(r.getStudent(), "user"));
                });
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndSpecsByCourseIdTest() {
        Specification<ResultOfStudent> specs = ResultOfStudentStaffSpecs.ofDepartment(1L)
                .and(ResultOfStudentStaffSpecs.hasSpecs(new ResultOfStudentCriteriaStaffInDto().setCourseId(1L)));
        Page<ResultOfStudent> page = resultOfStudentRepository.findAll(specs,
                PageRequest.of(0, 100, new Sort(Sort.Direction.ASC, Arrays.asList("grade"))));
        assertThat("Page of Students Results is not as expected", page, allOf(
                hasProperty("content", hasSize(6)),
                hasProperty("totalPages", equalTo(1)),
                hasProperty("totalElements", equalTo(6L))));
    }


    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndSpecsByOnlyLMSTest() {
        Specification<ResultOfStudent> specs = ResultOfStudentStaffSpecs.ofDepartment(1L)
                .and(ResultOfStudentStaffSpecs.hasSpecs(new ResultOfStudentCriteriaStaffInDto().setLms(true)));
        Page<ResultOfStudent> page = resultOfStudentRepository.findAll(specs,
                PageRequest.of(0, 100, new Sort(Sort.Direction.ASC, Arrays.asList("percent"))));
        assertThat("Page of Students Results is not as expected", page, allOf(
                hasProperty("content", hasSize(6)),
                hasProperty("totalPages", equalTo(1)),
                hasProperty("totalElements", equalTo(6L))));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndSpecsBySchemeIdTest() {
        Specification<ResultOfStudent> specs = ResultOfStudentStaffSpecs.ofDepartment(1L)
                .and(ResultOfStudentStaffSpecs.hasSpecs(new ResultOfStudentCriteriaStaffInDto().setSchemeId(1L)));
        Page<ResultOfStudent> page = resultOfStudentRepository.findAll(specs,
                PageRequest.of(0, 100, new Sort(Sort.Direction.ASC, Arrays.asList("grade"))));
        assertThat("Page of Students Results is not as expected", page, allOf(
                hasProperty("content", hasSize(2)),
                hasProperty("totalPages", equalTo(1)),
                hasProperty("totalElements", equalTo(2L))));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndSpecsByDateFromTest() {
        String from = "2018-12-26 23:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateFrom = LocalDateTime.parse(from, formatter);
        Specification<ResultOfStudent> specs = ResultOfStudentStaffSpecs.ofDepartment(1L)
                .and(ResultOfStudentStaffSpecs.hasSpecs(new ResultOfStudentCriteriaStaffInDto().setResultsFrom(dateFrom)));
        Page<ResultOfStudent> page = resultOfStudentRepository.findAll(specs,
                PageRequest.of(0, 100, new Sort(Sort.Direction.ASC, Arrays.asList("sessionEnded"))));
        assertThat("Page of Students Results is not as expected", page, allOf(
                hasProperty("content", hasSize(3)),
                hasProperty("totalPages", equalTo(1)),
                hasProperty("totalElements", equalTo(3L))));
    }


    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndSpecsByDateToTest() {
        String to = "2018-12-31 23:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTo = LocalDateTime.parse(to, formatter);
        Specification<ResultOfStudent> specs = ResultOfStudentStaffSpecs.ofDepartment(1L)
                .and(ResultOfStudentStaffSpecs.hasSpecs(new ResultOfStudentCriteriaStaffInDto().setResultsTo(dateTo)));
        Page<ResultOfStudent> page = resultOfStudentRepository.findAll(specs,
                PageRequest.of(0, 100, new Sort(Sort.Direction.ASC, Arrays.asList("sessionEnded"))));
        assertThat("Page of Students Results is not as expected", page, allOf(
                hasProperty("content", hasSize(5)),
                hasProperty("totalPages", equalTo(1)),
                hasProperty("totalElements", equalTo(5L))));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndSpecsByDateBetweenTest() {
        String from = "2018-12-26 23:00";
        String to = "2018-12-31 23:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateFrom = LocalDateTime.parse(from, formatter);
        LocalDateTime dateTo = LocalDateTime.parse(to, formatter);
        Specification<ResultOfStudent> specs = ResultOfStudentStaffSpecs.ofDepartment(1L)
                .and(ResultOfStudentStaffSpecs.hasSpecs(new ResultOfStudentCriteriaStaffInDto().setResultsFrom(dateFrom).setResultsTo(dateTo)));
        Page<ResultOfStudent> page = resultOfStudentRepository.findAll(specs,
                PageRequest.of(0, 100, new Sort(Sort.Direction.ASC, Arrays.asList("sessionEnded"))));
        assertThat("Page of Students Results is not as expected", page, allOf(
                hasProperty("content", hasSize(2)),
                hasProperty("totalPages", equalTo(1)),
                hasProperty("totalElements", equalTo(2L))));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndSpecsBySurnameNameTest() {
        Specification<ResultOfStudent> specs = ResultOfStudentStaffSpecs.ofDepartment(2L)
                .and(ResultOfStudentStaffSpecs.hasSpecs(new ResultOfStudentCriteriaStaffInDto().setSurname("ska").setContains(true)));
        Page<ResultOfStudent> page = resultOfStudentRepository.findAll(specs,
                PageRequest.of(0, 100, new Sort(Sort.Direction.ASC, Arrays.asList("grade"))));
        assertThat("Page of Students Results is not as expected", page, allOf(
                hasProperty("content", hasSize(2)),
                hasProperty("totalPages", equalTo(1)),
                hasProperty("totalElements", equalTo(2L))));
    }

    //-------------------------------------------COMPLEX criteria---------------------------------------------------

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndSpecsManyTest() {
        String from = "2018-12-25 08:00";
        String to = "2019-01-30 23:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateFrom = LocalDateTime.parse(from, formatter);
        LocalDateTime dateTo = LocalDateTime.parse(to, formatter);
        Specification<ResultOfStudent> specs = ResultOfStudentStaffSpecs.ofDepartment(1L)
                .and(ResultOfStudentStaffSpecs.hasSpecs(
                        new ResultOfStudentCriteriaStaffInDto()
                                .setCourseId(1L)
                                .setSchemeId(2L)
                                .setResultsFrom(dateFrom)
                                .setResultsTo(dateTo)
                                .setSurname("eva")
                                .setContains(true)));
        Page<ResultOfStudent> page = resultOfStudentRepository.findAll(specs,
                PageRequest.of(0, 100, new Sort(Sort.Direction.ASC, Arrays.asList("sessionEnded"))));
        assertThat("Page of Students Results is not as expected", page, allOf(
                hasProperty("content", hasSize(1)),
                hasProperty("totalPages", equalTo(1)),
                hasProperty("totalElements", equalTo(1L))));
    }

}
