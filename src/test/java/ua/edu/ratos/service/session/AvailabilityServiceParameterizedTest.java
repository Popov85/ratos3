package ua.edu.ratos.service.session;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.edu.ratos.dao.entity.Group;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.dao.entity.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class AvailabilityServiceParameterizedTest {

    @Parameterized.Parameter(0)
    public Long userId;

    @Parameterized.Parameter(1)
    public boolean expectedResult;

    private Scheme scheme;

    @Before
    public void setUp() throws Exception {
        scheme = new Scheme();
        scheme.setSchemeId(1L);

        Group g1 = new Group();

        g1.setStudents(new HashSet<>(Arrays.asList(
                createStudent(1L, true),
                createStudent(5L, true),
                createStudent(17L, true),
                createStudent(141L, true),
                createStudent(489L,  false))));

        Group g2 = new Group();

        g2.setStudents(new HashSet<>(Arrays.asList(
                createStudent(2L, true),
                createStudent(8L, true),
                createStudent(11L, true),
                createStudent(99L, true),
                createStudent(215L,  false))));
        scheme.setGroups(new HashSet<>(Arrays.asList(g1, g2)));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 1L, true },
                { 5L, true },
                { 17L, true },
                { 141L, true },
                { 489L, false },
                { 2L, true },
                { 8L, true },
                { 11L, true },
                { 99L, true },
                { 215L, false },
                { 3L, false },
                { 33L, false },
                { 100L, false },
                { 501L, false },
        });
    }

    @Test(timeout = 2000L)
    public void isSchemeAvailablePositiveCaseTest() {
        AvailabilityService availabilityService = new AvailabilityService();
        assertThat("Scheme is not available where it must be for user="+userId, availabilityService.isSchemeAvailable(scheme, userId), is(expectedResult));
    }

    private Student createStudent(Long studId, boolean active) {
        Student s = new Student();
        s.setStudId(studId);
        User u = new User();
        u.setActive(active);
        s.setUser(u);
        return s;
    }

}