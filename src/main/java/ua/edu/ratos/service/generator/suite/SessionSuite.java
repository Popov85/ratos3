package ua.edu.ratos.service.generator.suite;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.Class;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.generator.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
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
    private ResourceGenerator resourceGenerator;

    @Autowired
    private HelpGenerator helpGenerator;

    @Autowired
    private MCQGenerator mcqGenerator;


    public void generateMin() {
        Suite suite = getMin();
        generate(suite);
        log.info("Generated min suite for session = {}", suite);
    }

    public void generateAvgAndStep() {
        Suite suite = getAvg();
        generateAndStep(suite, 1000, 1000, 3, 1000);
        log.info("Generated avg suite for session = {}", suite);
    }

    public void generateMaxAndStep() {
        Suite suite = getMax();
        generateAndStep(suite, 50000, 100000, 5, 10000);
        log.info("Generated max suite for session = {}", suite);
    }

    private Suite getMin() {
        return new Suite()
                .setOrganisations(1)
                .setFaculties(1)
                .setDepartments(1)
                .setCourses(1)
                .setSchemes(1)
                .setThemes(10)
                .setResources(100)
                .setHelps(100)
                .setMcq(1000);
    }

    private Suite getAvg() {
        return new Suite()
                .setOrganisations(1)
                .setFaculties(5)
                .setClasses(100)
                .setStudents(100)
                .setDepartments(50)
                .setCourses(100)
                .setResources(100)
                .setHelps(100);
    }

    private Suite getMax() {
        return new Suite()
                .setOrganisations(1)
                .setFaculties(5)
                .setClasses(200)
                .setStudents(2000)
                .setDepartments(50)
                .setCourses(2000)
                .setResources(2000)
                .setHelps(2000);
    }


    private void generate(Suite suite) {
        List<Organisation> organisations = organisationGenerator.generate(suite.getOrganisations());
        List<Faculty> faculties = facultyGenerator.generate(suite.getFaculties(), organisations);
        List<Department> departments = departmentGenerator.generate(suite.getDepartments(), faculties);
        List<Course> courses = courseGenerator.generate(suite.getCourses(), departments);
        List<Theme> themes = themeGenerator.generate(suite.getThemes(), courses);
        List<Scheme> schemes = schemeGeneratorStep.generate(1, themes, departments, courses);
        List<Resource> resources = resourceGenerator.generate(suite.getResources());
        List<Help> helps = helpGenerator.generate(suite.getHelps(), resources);
        List<QuestionMCQ> mcqs = mcqGenerator.generate(suite.getMcq(), themes, resources, helps);
    }


    private void generateAndStep(Suite suite, int questionsForSmallSchemes, int questionsForMediumSchemes, int stepSchemes, int questionsForStepSchemes) {
        long start = System.nanoTime();
        List<Organisation> organisations = organisationGenerator.generate(suite.getOrganisations());
        List<Faculty> faculties = facultyGenerator.generate(suite.getFaculties(), organisations);
        List<Class> classes = classGenerator.generate(suite.getClasses(), faculties);
        List<Student> students = studentGenerator.generate(suite.getStudents(), classes);
        List<Department> departments = departmentGenerator.generate(suite.getDepartments(), faculties);
        List<Course> courses = courseGenerator.generate(1000, departments);

        // Small 500 schemes
        List<Theme> themesSmall = themeGenerator.generate(500, courses);
        List<Scheme> schemesSmall = schemeGenerator.generate(500, themesSmall, departments, courses, 3);
        List<Resource> resources = resourceGenerator.generate(suite.getResources());
        List<Help> helps = helpGenerator.generate(suite.getHelps(), resources);
        // 50.000
        mcqGenerator.generate(questionsForSmallSchemes, themesSmall, resources, helps);
        List<Long> smallSchemesIds = schemesSmall.stream().map(s -> s.getSchemeId()).collect(Collectors.toList());
        themesSmall = null;
        schemesSmall = null;
        log.debug("Finished generating questions for small schemes");

        // Medium 50 schemes
        List<Theme> themesMedium = themeGenerator.generate(500, courses);
        List<Scheme> schemesMedium = schemeGenerator.generate(50, themesMedium, departments, courses, 8);
        // 100.000
        mcqGenerator.generate(questionsForMediumSchemes, themesMedium, resources, helps);
        List<Long> mediumSchemesIds = schemesMedium.stream().map(s -> s.getSchemeId()).collect(Collectors.toList());
        themesMedium = null;
        schemesMedium = null;
        log.debug("Finished generating questions for medium schemes");

        // STEP - 10 schemes
        List<Scheme> schemesStep = new ArrayList<>();
        for (int i = 0; i < stepSchemes; i++) {
            List<Theme> themesStep = themeGenerator.generate(10, courses);
            List<Scheme> scheme = schemeGeneratorStep.generate(1, themesStep, departments, courses);
            schemesStep.addAll(scheme);
            // 10.000
            mcqGenerator.generate(questionsForStepSchemes, themesStep, resources, helps);
            log.debug("Finished generating questions for step schemes, i = {}", i);
        }
        List<Long> stepSchemesIds = schemesStep.stream().map(s -> s.getSchemeId()).collect(Collectors.toList());
        themesMedium = null;
        schemesStep = null;
        log.debug("Small schemes = {}", smallSchemesIds);
        log.debug("Medium schemes = {}", mediumSchemesIds);
        log.debug("STEP schemes = {}", stepSchemesIds);

        long finish = System.nanoTime();

        log.debug("Finished generating test data, time spent = {} min", (finish-start)/60000000000d);
    }
}
