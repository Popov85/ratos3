package ua.edu.ratos.service.generator.suite;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.Clazz;
import ua.edu.ratos.service.generator.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Profile({"dev"})
public class SessionSuite {

    @Autowired
    private OrganisationGenerator organisationGenerator;

    @Autowired
    private FacultyGenerator facultyGenerator;

    @Autowired
    private ClassGenerator classGenerator;

    @Autowired
    private StudentGenerator studentGenerator;

    @Autowired
    private DepartmentGenerator departmentGenerator;

    @Autowired
    private SchemeGenerator schemeGenerator;

    @Autowired
    private SchemeGeneratorStep schemeGeneratorStep;

    @Autowired
    private CourseGenerator courseGenerator;

    @Autowired
    private ThemeGenerator themeGenerator;

    @Autowired
    private RealResourceGenerator resourceGenerator;

    @Autowired
    private HelpGenerator helpGenerator;

    @Autowired
    private MCQGenerator mcqGenerator;


    public void generateMin() {
        Suite suite = getMin();
        generate(suite);
        log.info("Generated min test suite = {}", suite);
    }

    public void generateAvg() {
        Suite suite = getAvg();
        generate(suite);
        log.info("Generated avg suite for session = {}", suite);
    }

    public void generateMax() {
        Suite suite = getMax();
        generate(suite);
        log.info("Generated max suite for session = {}", suite);
    }

    private Suite getMin() {
        return new Suite()
                .setOrganisations(1)
                .setFaculties(1)
                .setClasses(1)
                .setStudents(10)
                .setDepartments(1)
                .setCourses(1)
                .setSchemes(20)
                .setComplexSchemes(5)
                .setStepSchemes(1)
                .setThemes(20)
                .setComplexThemes(25)
                .setStepThemes(10)
                .setResources(20)
                .setHelps(100)
                .setMcq(400)
                .setComplexMcq(400)
                .setStepMcq(400);
    }

    private Suite getAvg() {
        return new Suite()
                .setOrganisations(1)
                .setFaculties(5)
                .setClasses(10)
                .setStudents(50)
                .setDepartments(10)
                .setCourses(10)
                .setSchemes(50)
                .setComplexSchemes(20)
                .setStepSchemes(3)
                .setThemes(200)
                .setComplexThemes(60)
                .setStepThemes(10)
                .setResources(200)
                .setHelps(200)
                .setMcq(2000)
                .setComplexMcq(1500)
                .setStepMcq(1500);
    }

    private Suite getMax() {
        return new Suite()
                .setOrganisations(1)
                .setFaculties(5)
                .setClasses(50)
                .setStudents(500)
                .setDepartments(50)
                .setCourses(100)
                .setSchemes(100)
                .setComplexSchemes(30)
                .setStepSchemes(5)
                .setThemes(500)
                .setComplexThemes(200)
                .setStepThemes(10)
                .setResources(300)
                .setHelps(300)
                .setMcq(5000)
                .setComplexMcq(2000)
                .setStepMcq(2000);
    }


    private void generate(Suite suite) {
        long start = System.nanoTime();
        List<Organisation> organisations = organisationGenerator.generate(suite.getOrganisations());
        List<Faculty> faculties = facultyGenerator.generate(suite.getFaculties(), organisations);
        List<Clazz> classes = classGenerator.generate(suite.getClasses(), faculties);
        List<Student> students = studentGenerator.generate(suite.getStudents(), classes);
        List<Department> departments = departmentGenerator.generate(suite.getDepartments(), faculties);
        List<Course> courses = courseGenerator.generate(suite.getCourses(), departments);
        List<Resource> resources = resourceGenerator.generate();
        List<Help> helps = helpGenerator.generate(suite.getHelps());

        // Simple schemes
        List<Theme> themesSmall = themeGenerator.generate(suite.getThemes(), courses);
        List<Scheme> schemesSmall = schemeGenerator.generate(suite.getSchemes(), themesSmall, departments, courses, 1);
        mcqGenerator.generate(suite.getMcq(), themesSmall, resources, helps);
        List<Long> smallSchemesIds = schemesSmall.stream().map(s -> s.getSchemeId()).collect(Collectors.toList());
        log.debug("Finished generating questions for simple schemes");

        // Complex schemes
        List<Theme> themesMedium = themeGenerator.generate(suite.getComplexThemes(), courses);
        List<Scheme> schemesMedium = schemeGenerator.generate(suite.getComplexSchemes(), themesMedium, departments, courses, 5);
        mcqGenerator.generate(suite.getComplexMcq(), themesMedium, resources, helps);
        List<Long> mediumSchemesIds = schemesMedium.stream().map(s -> s.getSchemeId()).collect(Collectors.toList());
        log.debug("Finished generating questions for complex schemes");


        // STEP - 10 schemes
        List<Scheme> schemesStep = new ArrayList<>();
        for (int i = 0; i < suite.getStepSchemes(); i++) {
            List<Theme> themesStep = themeGenerator.generate(suite.getStepThemes(), courses);
            List<Scheme> scheme = schemeGeneratorStep.generate(1, themesStep, departments, courses);
            schemesStep.addAll(scheme);
            mcqGenerator.generate(suite.getStepMcq(), themesStep, resources, helps);
            log.debug("Finished generating questions for step schemes, i = {}", i);
        }
        List<Long> stepSchemesIds = schemesStep.stream().map(s -> s.getSchemeId()).collect(Collectors.toList());

        log.debug("Simple schemes = {}", smallSchemesIds);
        log.debug("Complex schemes = {}", mediumSchemesIds);
        log.debug("STEP schemes = {}", stepSchemesIds);

        long finish = System.nanoTime();

        log.debug("Finished generating test data, time spent = {} min", (finish-start)/60000000000d);
    }
}
