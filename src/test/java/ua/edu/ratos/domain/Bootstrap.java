package ua.edu.ratos.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.*;
import ua.edu.ratos.domain.entity.answer.*;
import ua.edu.ratos.domain.repository.*;
import ua.edu.ratos.domain.entity.question.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class Bootstrap {
    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StaffRepository staffRepository;


    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    @Autowired
    private LanguageRepository languageRepository;


    @Commit
    @Test
    public void bootstrap() {
        saveOrganisation();
        saveDepartments();
        saveUsers();
        saveRoles();
        savePositions();
        saveStaff();
        saveCourses();
        saveThemes();
        saveQuestionTypes();
        saveLanguages();
        saveQuestions();
    }

    private void saveOrganisation() {
        Organisation organisation = new Organisation();
        organisation.setName("Open University");

        organisationRepository.save(organisation);
    }

    private void saveDepartments() {
        Organisation organisation = new Organisation();
        organisation.setOrgId(1L);

        Department department = new Department();
        department.setName("Enterprise programming");
        department.setOrganisation(organisation);

        Department department2 = new Department();
        department2.setName("Applied programming");
        department2.setOrganisation(organisation);

        departmentRepository.saveAll(Arrays.asList(department, department2));
    }

    private void saveUsers() {
        User user = new User();
        user.setName("Steven");
        user.setSurname("Johns");
        user.setEmail("steven.johns@gmail.com");
        user.setPassword("g6yUr30w".toCharArray());

        User user2 = new User();
        user2.setName("James");
        user2.setSurname("Strong");
        user2.setEmail("james.strong@gmail.com");
        user2.setPassword("p8Yf9Ovb".toCharArray());

        userRepository.saveAll(Arrays.asList(user, user2));
    }

    private void saveRoles() {
        Role role = new Role();
        role.setName("Global admin");

        Role role2 = new Role();
        role2.setName("Department admin");

        Role role3 = new Role();
        role3.setName("Department instructor");

        Role role4 = new Role();
        role4.setName("Department laboratory assistant");

        roleRepository.saveAll(Arrays.asList(role, role2, role3, role4));
    }

    private void savePositions() {
        Position position = new Position();
        position.setName("Researcher");

        Position position2 = new Position();
        position2.setName("Instructor");

        Position position3 = new Position();
        position3.setName("Assistant");

        Position position4 = new Position();
        position4.setName("Labour assistant");

        positionRepository.saveAll(Arrays.asList(position, position2, position3, position4));
    }

    private void saveStaff() {
        User user = new User();
        user.setUserId(1L);

        Department department = new Department();
        department.setDepId(1L);

        Position position = new Position();
        position.setPosId(1L);

        Role role = new Role();
        role.setRoleId(1L);

        Staff staff = new Staff();
        staff.setUser(user);
        staff.setDepartment(department);
        staff.setPosition(position);
        staff.setRole(role);

        staffRepository.save(staff);
    }



    private void saveCourses() {
        Staff staff = new Staff();
        staff.setStaId(1L);

        Course course = new Course();
        course.setName("Java for Beginners");
        course.setCreated(LocalDateTime.now());
        course.setStaff(staff);

        Course course1 = new Course();
        course1.setName("Java for Professionals");
        course1.setCreated(LocalDateTime.now());
        course1.setStaff(staff);

        courseRepository.saveAll(Arrays.asList(course, course1));
    }

    private void saveThemes() {
        Course course = new Course();
        course.setCourseId(1L);

        Theme theme = new Theme();
        theme.setName("Java Operators");
        theme.setCourse(course);

        Theme theme2 = new Theme();
        theme2.setName("Java Strings");
        theme2.setCourse(course);

        Theme theme3 = new Theme();
        theme3.setName("Java Generics");
        theme3.setCourse(course);

        themeRepository.saveAll(Arrays.asList(theme, theme2, theme3));
    }

    private void saveQuestionTypes() {
        QuestionType type = new QuestionType();
        type.setAbbreviation("MCQ");
        type.setDescription("Multiple choice question");

        QuestionType type2 = new QuestionType();
        type2.setAbbreviation("FBSQ");
        type2.setDescription("Fill blank single question");

        QuestionType type3 = new QuestionType();
        type3.setAbbreviation("FBMQ");
        type3.setDescription("Fill blank multiple question");

        QuestionType type4 = new QuestionType();
        type4.setAbbreviation("MQ");
        type4.setDescription("Matcher question");

        QuestionType type5 = new QuestionType();
        type5.setAbbreviation("SQ");
        type5.setDescription("Sequence question");

        questionTypeRepository.saveAll(Arrays.asList(type, type2, type3, type4, type5));
    }


    private void saveLanguages() {
        Language lang = new Language();
        lang.setName("English");
        lang.setAbbreviation("EN");

        Language lang1 = new Language();
        lang1.setName("français");
        lang1.setAbbreviation("FR");

        Language lang2 = new Language();
        lang2.setName("русский");
        lang2.setAbbreviation("RU");

        Language lang3 = new Language();
        lang3.setName("українська");
        lang3.setAbbreviation("UKR");

        Language lang4 = new Language();
        lang4.setName("Polską");
        lang4.setAbbreviation("PL");

        languageRepository.saveAll(Arrays.asList(lang, lang1, lang2, lang3, lang4));
    }

    //--------------------------------------------------


    private void saveQuestions() {
        saveMCQ();
        saveFBSQ();
        saveFBMQ();
        saveMQ();
        saveSQ();
   }


    private void saveMCQ() {
        Theme theme = new Theme();
        theme.setThemeId(1L);

        QuestionMultipleChoice mcq = new QuestionMultipleChoice();
        mcq.setQuestion("Interface used to interact with the second-level cache.");
        mcq.setLevel((byte)1);
        mcq.setTheme(theme);

        Language lang = new Language();
        lang.setLangId(1L);

        mcq.setLang(lang);

        AnswerMultipleChoice answer = new AnswerMultipleChoice();
        answer.setAnswer("Cache");
        answer.setPercent((short)100);
        answer.setRequired(true);

        AnswerMultipleChoice answer1 = new AnswerMultipleChoice();
        answer1.setAnswer("EntityManager");
        answer1.setPercent((short)0);
        answer1.setRequired(false);

        AnswerMultipleChoice answer2 = new AnswerMultipleChoice();
        answer2.setAnswer("StoredProcedure");
        answer2.setPercent((short)0);
        answer2.setRequired(false);

        AnswerMultipleChoice answer3 = new AnswerMultipleChoice();
        answer3.setAnswer("Subgraph<T>");
        answer3.setPercent((short)0);
        answer3.setRequired(false);

        mcq.addAnswer(answer);
        mcq.addAnswer(answer1);
        mcq.addAnswer(answer2);
        mcq.addAnswer(answer3);

        Resource resource = new Resource();
        resource.setLink("http://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html");
        resource.setDescription("Java Persistence is the API");

        mcq.setResources(Arrays.asList(resource));

        Help help = new Help();
        help.setHelp("Package javax.persistence");
        help.setQuestion(mcq);

        mcq.setHelp(help);

        questionRepository.save(mcq);
    }


    private void saveFBSQ() {
        Theme theme = new Theme();
        theme.setThemeId(1L);

        QuestionFillBlankSingle fbsq = new QuestionFillBlankSingle();
        fbsq.setQuestion("Defines the set of cascadable operations that are propagated to the associated entity.");
        fbsq.setLevel((byte)1);
        fbsq.setTheme(theme);

        Language lang = new Language();
        lang.setLangId(1L);
        fbsq.setLang(lang);

        final AnswerFillBlankSingle answer = new AnswerFillBlankSingle();

        AcceptedPhrase acceptedPhrase = new AcceptedPhrase();
        acceptedPhrase.setPhrase("CascadeType");

        AcceptedPhrase acceptedPhrase1 = new AcceptedPhrase();
        acceptedPhrase1.setPhrase("Enum<CascadeType>");

        AcceptedPhrase acceptedPhrase2 = new AcceptedPhrase();
        acceptedPhrase2.setPhrase("java.lang.Object java.lang.Enum<CascadeType>");

        answer.setAcceptedPhrases(Arrays.asList(acceptedPhrase, acceptedPhrase1, acceptedPhrase2));

        SettingsAnswerFillBlank settings = new SettingsAnswerFillBlank();

        settings.setLang(lang);
        settings.setWordsLimit((short)3);
        settings.setSymbolsLimit((short) 25);

        answer.setSettings(settings);

        answer.setQuestion(fbsq);

        fbsq.setAnswer(answer);

        questionRepository.save(fbsq);
    }

    private void saveFBMQ() {
        Theme theme = new Theme();
        theme.setThemeId(1L);

        QuestionFillBlankMultiple fbmq = new QuestionFillBlankMultiple();
        fbmq.setQuestion("Lock modes can be specified by means of passing a LockModeType argument to one " +
                "of the EntityManager methods that take locks (lock, find, or refresh) or to the" +
                "Query.setLockMode() or TypedQuery.setLockMode() method.");
        fbmq.setLevel((byte)2);
        fbmq.setTheme(theme);

        Language lang = new Language();
        lang.setLangId(1L);

        fbmq.setLang(lang);



        final AnswerFillBlankMultiple answer = new AnswerFillBlankMultiple();
        answer.setPhrase("Query.setLockMode()");
        answer.setOccurrence((byte)1);

        AcceptedPhrase acceptedPhrase = new AcceptedPhrase();
        acceptedPhrase.setPhrase("setLockMode() of Query");

        AcceptedPhrase acceptedPhrase1 = new AcceptedPhrase();
        acceptedPhrase1.setPhrase("Query.setLockMode");

        answer.setAcceptedPhrases(Arrays.asList(acceptedPhrase, acceptedPhrase1));

        SettingsAnswerFillBlank settings = new SettingsAnswerFillBlank();


        settings.setLang(lang);
        settings.setWordsLimit((short)3);
        settings.setSymbolsLimit((short) 30);

        answer.setSettings(settings);
        answer.setQuestion(fbmq);


        final AnswerFillBlankMultiple answer2 = new AnswerFillBlankMultiple();
        answer2.setPhrase("TypedQuery.setLockMode()");
        answer2.setOccurrence((byte)1);

        AcceptedPhrase acceptedPhrase2 = new AcceptedPhrase();
        acceptedPhrase2.setPhrase("setLockMode() of TypedQuery");

        AcceptedPhrase acceptedPhrase3 = new AcceptedPhrase();
        acceptedPhrase3.setPhrase("TypedQuery.setLockMode");

        answer2.setAcceptedPhrases(Arrays.asList(acceptedPhrase, acceptedPhrase1));

        SettingsAnswerFillBlank settings2 = new SettingsAnswerFillBlank();

        settings2.setLang(lang);
        settings2.setWordsLimit((short)3);
        settings2.setSymbolsLimit((short) 40);

        answer2.setSettings(settings2);
        answer2.setQuestion(fbmq);

        fbmq.setAnswers(Arrays.asList(answer, answer2));

        questionRepository.save(fbmq);
    }


    private void saveMQ() {
        Theme theme = new Theme();
        theme.setThemeId(1L);

        QuestionMatcher mq = new QuestionMatcher();
        mq.setQuestion("Match the following concepts:");
        mq.setLevel((byte)3);
        mq.setTheme(theme);

        Language lang = new Language();
        lang.setLangId(1L);
        mq.setLang(lang);

        final AnswerMatcher answer = new AnswerMatcher();
        answer.setLeftPhrase("Interface used to interact with the second-level cache.");
        answer.setRightPhrase("Cache");
        answer.setQuestion(mq);

        final AnswerMatcher answer2 = new AnswerMatcher();
        answer2.setLeftPhrase("Type for query parameter objects.");
        answer2.setRightPhrase("Parameter<T>");
        answer2.setQuestion(mq);

        final AnswerMatcher answer3 = new AnswerMatcher();
        answer3.setLeftPhrase("Interface used to interact with the persistence context.");
        answer3.setRightPhrase("EntityManager");
        answer3.setQuestion(mq);

        final AnswerMatcher answer4 = new AnswerMatcher();
        answer4.setLeftPhrase("Interface used to control query execution.");
        answer4.setRightPhrase("Query");
        answer4.setQuestion(mq);

        mq.setAnswers(Arrays.asList(answer, answer2, answer3));

        questionRepository.save(mq);
    }


    private void saveSQ() {
        Theme theme = new Theme();
        theme.setThemeId(1L);

        QuestionSequence sq = new QuestionSequence();
        sq.setQuestion("Order Maven build process stages one after another:");
        sq.setLevel((byte)2);
        sq.setTheme(theme);

        Language lang = new Language();
        lang.setLangId(1L);
        sq.setLang(lang);

        final AnswerSequence answer = new AnswerSequence();
        answer.setPhrase("clean");
        answer.setOrder((short) 0);
        answer.setQuestion(sq);

        final AnswerSequence answer2 = new AnswerSequence();
        answer2.setPhrase("validate");
        answer2.setOrder((short) 1);
        answer2.setQuestion(sq);

        final AnswerSequence answer3 = new AnswerSequence();
        answer3.setPhrase("compile");
        answer3.setOrder((short) 2);
        answer3.setQuestion(sq);

        final AnswerSequence answer4 = new AnswerSequence();
        answer4.setPhrase("test");
        answer4.setOrder((short) 3);
        answer4.setQuestion(sq);

        final AnswerSequence answer5 = new AnswerSequence();
        answer5.setPhrase("package");
        answer5.setOrder((short) 4);
        answer5.setQuestion(sq);

        final AnswerSequence answer6 = new AnswerSequence();
        answer6.setPhrase("verify");
        answer6.setOrder((short) 5);
        answer6.setQuestion(sq);

        sq.setAnswers(Arrays.asList(answer, answer2, answer3, answer4, answer5, answer6));

        questionRepository.save(sq);
    }
}
