package ua.edu.ratos.it.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.ResultOfStudent;
import ua.edu.ratos.dao.repository.ResultOfStudentRepository;
import ua.edu.ratos.dao.repository.specs.ResultOfStudentStaffSpecs;
import ua.edu.ratos.service.dto.in.criteria.results.ResultOfStudentCriteriaStaffInDto;
import ua.edu.ratos.it.ActiveProfile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ResultOfStudentRepositoryTest {

    @Autowired
    private ResultOfStudentRepository resultOfStudentRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        Specification<ResultOfStudent> specs = ResultOfStudentStaffSpecs.ofDepartment(1L);
        Page<ResultOfStudent> page = resultOfStudentRepository.findAll(specs,
                PageRequest.of(0, 100, new Sort(Sort.Direction.ASC, Arrays.asList("grade"))));
        page.getContent().forEach(System.out::println);
        Assert.assertEquals(1, page.getTotalPages());
        Assert.assertEquals(6, page.getTotalElements());
        Assert.assertEquals(6, page.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndSpecsByCourseIdTest() {
        Specification<ResultOfStudent> specs = ResultOfStudentStaffSpecs.ofDepartment(1L)
                .and(ResultOfStudentStaffSpecs.hasSpecs(new ResultOfStudentCriteriaStaffInDto().setCourseId(1L)));
        Page<ResultOfStudent> page = resultOfStudentRepository.findAll(specs,
                PageRequest.of(0, 100, new Sort(Sort.Direction.ASC, Arrays.asList("grade"))));
        //page.getContent().forEach(System.out::println);
        Assert.assertEquals(1, page.getTotalPages());
        Assert.assertEquals(6, page.getTotalElements());
        Assert.assertEquals(6, page.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndSpecsBySchemeIdTest() {
        Specification<ResultOfStudent> specs = ResultOfStudentStaffSpecs.ofDepartment(1L)
                .and(ResultOfStudentStaffSpecs.hasSpecs(new ResultOfStudentCriteriaStaffInDto().setSchemeId(1L)));
        Page<ResultOfStudent> page = resultOfStudentRepository.findAll(specs,
                PageRequest.of(0, 100, new Sort(Sort.Direction.ASC, Arrays.asList("grade"))));
        Assert.assertEquals(1, page.getTotalPages());
        Assert.assertEquals(2, page.getTotalElements());
        Assert.assertEquals(2, page.getContent().size());
    }

    @Test
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
        Assert.assertEquals(1, page.getTotalPages());
        Assert.assertEquals(3, page.getTotalElements());
        Assert.assertEquals(3, page.getContent().size());
    }


    @Test
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
        Assert.assertEquals(1, page.getTotalPages());
        Assert.assertEquals(5, page.getTotalElements());
        Assert.assertEquals(5, page.getContent().size());
    }

    @Test
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
        Assert.assertEquals(1, page.getTotalPages());
        Assert.assertEquals(2, page.getTotalElements());
        Assert.assertEquals(2, page.getContent().size());
    }



    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndSpecsBySurnameNameTest() {
        Specification<ResultOfStudent> specs = ResultOfStudentStaffSpecs.ofDepartment(2L)
                .and(ResultOfStudentStaffSpecs.hasSpecs(new ResultOfStudentCriteriaStaffInDto().setSurname("ska").setContains(true)));
        Page<ResultOfStudent> page = resultOfStudentRepository.findAll(specs,
                PageRequest.of(0, 100, new Sort(Sort.Direction.ASC, Arrays.asList("grade"))));
        Assert.assertEquals(1, page.getTotalPages());
        Assert.assertEquals(2, page.getTotalElements());
        Assert.assertEquals(2, page.getContent().size());
    }

    //-------------------------------------------COMPLEX criteria---------------------------------------------------

    @Test
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
        // page.getContent().forEach(System.out::println);
        Assert.assertEquals(1, page.getTotalPages());
        Assert.assertEquals(1, page.getTotalElements());
        Assert.assertEquals(1, page.getContent().size());
    }

}
