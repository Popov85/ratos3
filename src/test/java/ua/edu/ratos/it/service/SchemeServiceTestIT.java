package ua.edu.ratos.it.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.grade.SchemeFourPoint;
import ua.edu.ratos.dao.entity.grade.SchemeTwoPoint;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.dto.entity.SchemeInDto;
import ua.edu.ratos.service.scheme.SchemeService;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SchemeServiceTestIT {

    private static final String JSON_NEW = "classpath:json/scheme_in_dto_new_1.json";
    private static final String JSON_UPD = "classpath:json/scheme_in_dto_upd.json";
    private static final String JSON_UPD_SAME = "classpath:json/scheme_in_dto_upd_same.json";

    private static final String FIND = "select s from Scheme s where s.schemeId=:schemeId";
    private static final String FIND_GRADING_FOUR = "select s from SchemeFourPoint s join fetch s.fourPointGrading where s.schemeId=:schemeId";
    private static final String FIND_GRADING_TWO = "select s from SchemeTwoPoint s where s.schemeId=:schemeId";

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager em;


    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        SchemeInDto dto = objectMapper.readValue(json, SchemeInDto.class);
        final Long returnedId = schemeService.save(dto);
        Assert.assertEquals(1L, returnedId.longValue());
        final Scheme foundScheme =
                (Scheme) em.createQuery(FIND)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundScheme);
        final SchemeFourPoint foundFour =
                (SchemeFourPoint) em.createQuery(FIND_GRADING_FOUR)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundFour);
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD);
        SchemeInDto dto = objectMapper.readValue(json, SchemeInDto.class);
        schemeService.update(1L, dto);
        final Scheme foundScheme =
                (Scheme) em.createQuery(FIND)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundScheme);

        final List<SchemeFourPoint> foundFour = (List<SchemeFourPoint>) em.createQuery(FIND_GRADING_FOUR)
                .setParameter("schemeId", 1L)
                .getResultList();
        Assert.assertEquals(0, foundFour.size());

        final SchemeTwoPoint foundTwo =
                (SchemeTwoPoint) em.createQuery(FIND_GRADING_TWO)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundTwo);
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_two.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateSameTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD_SAME);
        SchemeInDto dto = objectMapper.readValue(json, SchemeInDto.class);
        schemeService.update(1L, dto);
        final Scheme foundScheme =
                (Scheme) em.createQuery(FIND)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundScheme);

        final SchemeFourPoint foundFour =
                (SchemeFourPoint) em.createQuery(FIND_GRADING_FOUR)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        final String newName = foundFour.getFourPointGrading().getName();
        Assert.assertEquals("new grading", newName);
    }


    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/scheme_theme_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void reOrderTest() throws Exception {
        // 1. Given 8 themes associated with a scheme
        // 2. Reorder elements by putting 2d currentIndex element to the top and 5th currentIndex element to the bottom
        //    {1, 2, 3, 4, 5, 6, 7, 8} - > {3, 1, 2, 4, 5, 7, 8, 6}
        // 3. Make sure 8 of them are still present after manipulations and resulting lists are equal?
        // 4. Observe indexes re-built!
        final List<Long> edit = Arrays.asList(3L, 1L, 2L, 4L, 5L, 7L, 8L, 6L);
        schemeService.reOrder(1L, edit);
        final List<SchemeTheme> schemeThemes = schemeRepository.findByIdWithThemes(1L).getThemes();
        final List<Long> actual = schemeThemes.stream().map(SchemeTheme::getSchemeThemeId)
                .collect(Collectors.toList());
        Assert.assertEquals(8, actual.size());
        Assert.assertEquals(edit, actual);
        //themes.forEach(System.out::println);
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/scheme_theme_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIndexTest() throws Exception {
        // 1. Given 8 themes associated with a scheme
        // 2. Delete the 4th one
        // 3. Make sure only 7 of they are left, make sure the Scheme is still completed
        // 4. Observe indexes updated!
        Assert.assertEquals(8, schemeRepository.findByIdWithThemes(1L).getThemes().size());
        schemeService.deleteByIndex(1L, 4);
        final Scheme foundScheme = schemeRepository.findByIdWithThemes(1L);
        Assert.assertEquals(7, foundScheme.getThemes().size());
        Assert.assertTrue(foundScheme.isCompleted());
        //themes.forEach(System.out::println);
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/scheme_theme_test_data.sql", "/scripts/scheme_theme_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIndexLastElementTest() throws Exception {
        // 1. Given 1 theme associated with a scheme
        // 2. Delete it single theme
        // 3. Make sure 0 of them are left
        // 4. Make sure the Scheme became incomplete
        Assert.assertEquals(1, schemeRepository.findByIdWithThemes(1L).getThemes().size());
        schemeService.deleteByIndex(1L, 0);
        final Scheme foundScheme = schemeRepository.findByIdWithThemes(1L);
        Assert.assertEquals(0, foundScheme.getThemes().size());
        Assert.assertFalse(foundScheme.isCompleted());
        foundScheme.getThemes().forEach(System.out::println);
    }
}
