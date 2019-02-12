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
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.StaffService;
import ua.edu.ratos.service.dto.in.RoleByDepInDto;
import ua.edu.ratos.service.dto.in.StaffInDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class StaffServiceTestIT {

    public static final String JSON_NEW = "classpath:json/staff_in_dto_new.json";

    public static final String FIND = "select s from Staff s join fetch s.user u left join fetch u.roles r join s.department d where s.staffId=:staffId";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StaffService staffService;

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        StaffInDto dto = objectMapper.readValue(json, StaffInDto.class);
        Long staffId = staffService.save(dto);
        Assert.assertEquals(4L, staffId.longValue());
        Staff staff = (Staff) em.createQuery(FIND).setParameter("staffId", 4L).getSingleResult();
        Assert.assertNotNull(staff);
        Assert.assertTrue(staff.getUser().getPassword().length>60);
    }


    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addRoleTest() {
        // Given: staffId = 1 with ROLE_GLOBAL-ADMIN (8), let's add ROLE_INSTRUCTOR (3)
        staffService.addRoleByDepAdmin(1L, new RoleByDepInDto(3L));
        Staff staff = (Staff) em.createQuery(FIND).setParameter("staffId", 1L).getSingleResult();
        Assert.assertNotNull(staff);
        Assert.assertEquals(2, staff.getUser().getRoles().size());
        Set<String> strings = staff.getUser().getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet());
        assertThat(strings, containsInAnyOrder("ROLE_GLOBAL-ADMIN", "ROLE_INSTRUCTOR"));
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void removeRoleTest() {
        // Given: staffId = 1 with ROLE_GLOBAL-ADMIN (8), let's remove it and leave the user without any role
        staffService.removeRoleByDepAdmin(1L, new RoleByDepInDto(8L));
        Staff staff = (Staff) em.createQuery(FIND).setParameter("staffId", 1L).getSingleResult();
        Assert.assertNotNull(staff);
        Assert.assertTrue(staff.getUser().getRoles().isEmpty());
    }
}
