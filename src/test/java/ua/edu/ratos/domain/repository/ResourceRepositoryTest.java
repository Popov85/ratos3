package ua.edu.ratos.domain.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.Resource;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ResourceRepositoryTest {

    @Autowired
    private ResourceRepository resourceRepository;

    @Test
    public void findAllTest() {
        System.out.println("All resources");
        final Iterable<Resource> all = resourceRepository.findAll();
        all.forEach(System.out::println);
    }
}
