package ua.edu.ratos.service.generator.suite;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.generator.*;
import java.util.List;

@Slf4j
@Component
@Profile({"dev", "demo"})
public class QuestionsGeneratorsSuite {

    @Autowired
    private OrganisationGenerator organisationGenerator;

    @Autowired
    private FacultyGenerator facultyGenerator;

    @Autowired
    private DepartmentGenerator departmentGenerator;

    @Autowired
    private CourseGenerator courseGenerator;

    @Autowired
    private ThemeGenerator themeGenerator;

    @Autowired
    private ResourceGenerator resourceGenerator;

    @Autowired
    private HelpGenerator helpGenerator;

    @Autowired
    private PhraseGenerator phraseGenerator;

    @Autowired
    private MCQGenerator mcqGenerator;

    @Autowired
    private FBSQGenerator fbsqGenerator;

    @Autowired
    private FBMQGenerator fbmqGenerator;

    @Autowired
    private MQGenerator mqGenerator;

    @Autowired
    private SQGenerator sqGenerator;

    public void generateMin() {
        Suite suite = getMin();
        generate(suite);
        log.info("Generated min suite for questions = {}", suite);
    }

    public void generateAvg() {
        Suite suite = getAvg();
        generate(suite);
        log.info("Generated avg suite for questions = {}", suite);
    }

    public void generateMax() {
        Suite suite = getMax();
        generate(suite);
        log.info("Generated max suite for questions = {}", suite);
    }

    private Suite getMin() {
        return new Suite()
                .setOrganisations(1)
                .setFaculties(3)
                .setDepartments(3)
                .setCourses(5)
                .setThemes(10)
                .setResources(20)
                .setPhrases(30)
                .setHelps(20)
                .setMcq(100)
                .setFbsq(10)
                .setFbmq(10)
                .setMq(10)
                .setSq(10);
    }


    private Suite getAvg() {
        return new Suite()
                .setOrganisations(1)
                .setFaculties(10)
                .setDepartments(50)
                .setCourses(100)
                .setThemes(100)
                .setResources(100)
                .setHelps(100)
                .setPhrases(100)
                .setMcq(20000)
                .setFbsq(1000)
                .setFbmq(1000)
                .setMq(1000)
                .setSq(1000);
    }

    private Suite getMax() {
        return new Suite()
                .setOrganisations(1)
                .setFaculties(10)
                .setDepartments(50)
                .setCourses(500)
                .setThemes(500)
                .setResources(1000)
                .setHelps(1000)
                .setPhrases(1000)
                .setMcq(100000)
                .setFbsq(10000)
                .setFbmq(10000)
                .setMq(10000)
                .setSq(10000);
    }

    private void generate(Suite suite) {
        List<Organisation> organisations = organisationGenerator.generate(suite.getOrganisations());
        suite.setOrganisations(organisations.size());
        List<Faculty> faculties = facultyGenerator.generate(suite.getFaculties(), organisations);
        suite.setFaculties(faculties.size());
        List<Department> departments = departmentGenerator.generate(suite.getDepartments(), faculties);
        suite.setDepartments(departments.size());
        List<Course> courses = courseGenerator.generate(suite.getCourses(), departments);
        suite.setCourses(courses.size());

        List<Theme> themes = themeGenerator.generate(suite.getThemes(), courses);
        suite.setThemes(themes.size());
        List<Resource> resources = resourceGenerator.generate(suite.getResources());
        suite.setResources(resources.size());
        List<Help> helps = helpGenerator.generate(suite.getHelps());
        suite.setHelps(helps.size());
        List<Phrase> phrases = phraseGenerator.generate(suite.getPhrases(), resources);
        suite.setPhrases(phrases.size());

        List<QuestionMCQ> mcqs = mcqGenerator.generate(suite.getMcq(), themes, resources, helps);
        List<QuestionFBSQ> fbsqs = fbsqGenerator.generate(suite.getFbsq(), themes, resources, helps, phrases);
        List<QuestionFBMQ> fbmqs = fbmqGenerator.generate(suite.getFbmq(), themes, resources, helps, phrases);
        List<QuestionMQ> mqs = mqGenerator.generate(suite.getMq(), themes, resources, helps, phrases);
        List<QuestionSQ> sqs = sqGenerator.generate(suite.getSq(), themes, resources, helps, phrases);


        suite.setMcq(mcqs.size());
        suite.setFbsq(fbsqs.size());
        suite.setFbmq(fbmqs.size());
        suite.setMq(mqs.size());
        suite.setSq(sqs.size());
    }
}
