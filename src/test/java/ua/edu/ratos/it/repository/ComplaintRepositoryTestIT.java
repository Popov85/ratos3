package ua.edu.ratos.it.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.Complaint;
import ua.edu.ratos.dao.repository.ComplaintRepository;
import ua.edu.ratos.it.ActiveProfile;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ComplaintRepositoryTestIT {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/complaint_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        Page<Complaint> result = complaintRepository.findAllByDepartmentId(1L, PageRequest.of(0, 50, new Sort(Sort.Direction.DESC, Arrays.asList("timesComplained"))));
        result.forEach(System.out::println);
        assertEquals(5, result.getContent().size());
    }
}
