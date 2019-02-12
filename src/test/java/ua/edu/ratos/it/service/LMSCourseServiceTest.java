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
import ua.edu.ratos.dao.entity.lms.LMSCourse;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.LMSCourseService;
import ua.edu.ratos.service.dto.in.LMSCourseInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class LMSCourseServiceTest {

    private static final String JSON_NEW = "classpath:json/lms_course_in_dto_new.json";

    public static final String FIND= "select c from LMSCourse c join fetch c.course co join fetch c.lms where c.courseId=:courseId";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LMSCourseService lmsCourseService;

    @Test
    @Sql(scripts = {"/scripts/init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        LMSCourseInDto dto = objectMapper.readValue(json, LMSCourseInDto.class);
        Long courseId = lmsCourseService.save(dto);
        Assert.assertEquals(2L, courseId.longValue());
        LMSCourse course = (LMSCourse) em.createQuery(FIND).setParameter("courseId", 2L).getSingleResult();
        Assert.assertNotNull(course);
        Assert.assertEquals("LMS course name", course.getCourse().getName());
        Assert.assertEquals("Open edX", course.getLms().getName());
    }
}
