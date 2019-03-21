package ua.edu.ratos.service.generator.suite;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.Class;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.generator.*;

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
        generateAndStep(suite);
        log.info("Generated avg suite for session = {}", suite);
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
                .setMcq(10000);
    }

    private Suite getAvg() {
        return new Suite()
                .setOrganisations(1)
                .setFaculties(5)
                .setClasses(100)
                .setStudents(1000)
                .setDepartments(50)
                .setCourses(1000)
                .setSchemes(10000)
                .setThemes(10000)
                .setResources(1000)
                .setHelps(1000)
                .setMcq(100000);
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

    private void generateAndStep(Suite suite) {
        List<Organisation> organisations = organisationGenerator.generate(suite.getOrganisations());
        List<Faculty> faculties = facultyGenerator.generate(suite.getFaculties(), organisations);
        List<Class> classes = classGenerator.generate(suite.getClasses(), faculties);
        List<Student> students = studentGenerator.generate(suite.getStudents(), classes);
        List<Department> departments = departmentGenerator.generate(suite.getDepartments(), faculties);
        List<Course> courses = courseGenerator.generate(suite.getCourses(), departments);
        // Themes
        List<Theme> themes = themeGenerator.generate(suite.getThemes(), courses);
        // Reserve 10 themes for STEP scenarios
        List<Theme> themesStep = themes.subList(themes.size()-10, themes.size());
        List<Theme> themesRegular = themes.subList(0, themes.size()-10);
        // Reserve 10 schemes for STEP scenarios
        List<Scheme> schemesRegular = schemeGenerator.generate(suite.getSchemes()-10, themesRegular, departments, courses);
        List<Resource> resources = resourceGenerator.generate(suite.getResources());
        List<Help> helps = helpGenerator.generate(suite.getHelps(), resources);
        // Reserve 10000 questions for 10 themes of STEP scenarios
        List<QuestionMCQ> mcqsRegular = mcqGenerator.generate(suite.getMcq()-10000, themesRegular, resources, helps);
        //-------------------------------------------Only for Step schemes----------------------------------------------
        List<Scheme> schemesStep = schemeGeneratorStep.generate(10, themesStep, departments, courses);
        List<QuestionMCQ> mcqsStep= mcqGenerator.generate(10000, themesStep, resources, helps);

        log.debug("Schemes for STEP = {}", schemesStep.stream().map(s->s.getSchemeId()).collect(Collectors.toList()));
    }
}
