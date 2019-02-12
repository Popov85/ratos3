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
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.StudentService;
import ua.edu.ratos.service.dto.in.StudentInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class StudentServiceTestIT {

    public static final String JSON_NEW = "classpath:json/student_in_dto_new.json";

    public static final String FIND = "select s from Student s join fetch s.user u left join fetch u.roles r where s.studId=:studId";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentService studentService;

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        StudentInDto dto = objectMapper.readValue(json, StudentInDto.class);
        Long studId = studentService.save(dto);
        Assert.assertEquals(4L, studId.longValue());
        Student stud = (Student) em.createQuery(FIND).setParameter("studId", 4L).getSingleResult();
        Assert.assertNotNull(stud);
        Assert.assertTrue(stud.getUser().getPassword().length>60);
    }

}
