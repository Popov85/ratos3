package ua.edu.ratos.service.generator.suite;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.Clazz;
import ua.edu.ratos.service.generator.*;
import java.util.List;

@Slf4j
@Component
@Profile({"dev", "demo"})
public class ResultsGeneratorsSuite {

    @Autowired
    private OrganisationGenerator organisationGenerator;

    @Autowired
    private FacultyGenerator facultyGenerator;

    @Autowired
    private DepartmentGenerator departmentGenerator;

    @Autowired
    private ClassGenerator classGenerator;

    @Autowired
    private StudentGenerator studentGenerator;

    @Autowired
    private CourseGenerator courseGenerator;

    @Autowired
    private ThemeGenerator themeGenerator;

    @Autowired
    private SchemeGenerator schemeGenerator;

    @Autowired
    private ResultsGenerator resultsGenerator;

    public void generateMin() {
        Suite suite = getMin();
        generate(suite);
        log.info("Generated min suite = {}", suite);
    }

    public void generateAvg() {
        Suite suite = getAvg();
        generate(suite);
        log.info("Generated avg suite = {}", suite);
    }

    public void generateMax() {
        Suite suite = getMax();
        generate(suite);
        log.info("Generated max suite = {}", suite);
    }

    private Suite getMin() {
        return new Suite()
                .setOrganisations(1)
                .setFaculties(2)
                .setDepartments(6)
                .setClasses(100)
                .setCourses(200)
                .setThemes(400)
                .setSchemes(400)
                .setStudents(1000)
                .setResults(5000);
    }

    private Suite getAvg() {
        return new Suite()
                .setOrganisations(1)
                .setFaculties(10)
                .setDepartments(50)
                .setClasses(1000)
                .setCourses(2000)
                .setThemes(2000)
                .setSchemes(2000)
                .setStudents(10000)
                .setResults(100000);
    }

    private Suite getMax() {
        return new Suite()
                .setOrganisations(1)
                .setFaculties(10)
                .setDepartments(50)
                .setClasses(1000)
                .setCourses(20000)
                .setThemes(10000)
                .setSchemes(10000)
                .setStudents(10000)
                .setResults(1000000);
    }

    private void generate(Suite suite) {
        List<Organisation> organisations = organisationGenerator.generate(suite.getOrganisations());
        suite.setOrganisations(organisations.size());
        List<Faculty> faculties = facultyGenerator.generate(suite.getFaculties(), organisations);
        suite.setFaculties(faculties.size());
        List<Department> departments = departmentGenerator.generate(suite.getDepartments(), faculties);
        suite.setDepartments(departments.size());
        List<Clazz> classes = classGenerator.generate(suite.getClasses(), faculties);
        suite.setClasses(classes.size());
        List<Student> students = studentGenerator.generate(suite.getStudents(), classes);
        suite.setStudents(students.size());
        List<Course> courses = courseGenerator.generate(suite.getCourses(), departments);
        suite.setCourses(courses.size());
        List<Theme> themes = themeGenerator.generate(suite.getThemes(), courses);
        suite.setThemes(themes.size());
        List<Scheme> schemes = schemeGenerator.generate(suite.getSchemes(), themes, departments, courses, 5);
        suite.setSchemes(schemes.size());
        List<Result> results = resultsGenerator.generate(suite.getResults(), students, schemes);
        suite.setResults(results.size());
    }
}
