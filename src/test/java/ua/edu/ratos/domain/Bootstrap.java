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
import java.util.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class Bootstrap {
    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

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
    private StrategyRepository strategyRepository;

    @Autowired
    private ModeRepository modeRepository;

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private ThemeViewRepository themeViewRepository;

    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private SettingsAnswerFillBlankRepository settingsAnswerFillBlankRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private Iterable<Theme> themes;

    // @Commit can be used as direct replacement for @Rollback(false);
    // however, it should not be declared alongside @Rollback.
    // Declaring @Commit and @Rollback on the same test method or on the same test class is unsupported and may lead to unpredictable results.

    @Commit
    @Test
    public void bootstrap() {
        saveOrganisation();
        saveFaculties();
        saveDepartments();

        saveUsers();
        saveRoles();
        savePositions();
        saveStaff();
        saveCourses();
        saveThemes();

        saveSchemeStrategies();
        saveSchemeModes();
        saveSchemeSettings();
        saveSchemes();

        saveQuestionTypes();
        saveLanguages();
        saveAnswerSettings();
        saveQuestions();
    }


    private <T> T getFirst(Iterable<T> iterable) {
        return iterable.iterator().next();
    }

    private <T> T getRandom(Iterable<T> iterable) {
        List<T> entities = new ArrayList<>();
        iterable.forEach(entities::add);
        Random rnd = new Random();
        int i;
        if (entities.size()==1) {
            i = 0;
        } else {
            i = rnd.nextInt(entities.size() - 1);
        }
        return entities.get(i);
    }


    private void saveOrganisation() {
        Organisation organisation = new Organisation();
        organisation.setName("Open IT-university");

        organisationRepository.save(organisation);
    }

    private void saveFaculties() {
        Faculty faculty = new Faculty();
        faculty.setName("Programming faculty");

        facultyRepository.save(faculty);
    }

    private void saveDepartments() {

        Organisation organisation = getFirst(organisationRepository.findAll());

        Faculty faculty = getFirst(facultyRepository.findAll());

        Department department = new Department();
        department.setName("Enterprise programming");
        department.setOrganisation(organisation);
        department.setFaculty(faculty);

        Department department2 = new Department();
        department2.setName("Applied programming");
        department2.setOrganisation(organisation);
        department2.setFaculty(faculty);

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
        position2.setName("Professor");

        Position position3 = new Position();
        position3.setName("Instructor");

        Position position4 = new Position();
        position4.setName("Assistant");

        Position position5 = new Position();
        position5.setName("Labour assistant");

        positionRepository.saveAll(Arrays.asList(position, position2, position3, position4, position5));
    }

    private void saveStaff() {
        User user = getFirst(userRepository.findAll());

        Department department = getFirst(departmentRepository.findAll());

        Position position = getFirst(positionRepository.findAll());

        Role role = getRandom(roleRepository.findAll());

        Staff staff = new Staff();
        staff.setUser(user);
        staff.setDepartment(department);
        staff.setPosition(position);
        staff.setRole(role);

        staffRepository.save(staff);
    }


    private void saveCourses() {
        Staff staff = getFirst(staffRepository.findAll());
        Department department = getFirst(departmentRepository.findAll());

        Course course = new Course();
        course.setName("Java for Beginners");
        course.setCreated(LocalDateTime.now());
        course.setStaff(staff);
        course.setDepartment(department);

        Course course1 = new Course();
        course1.setName("Java for Professionals");
        course1.setCreated(LocalDateTime.now());
        course1.setStaff(staff);
        course1.setDepartment(department);

        courseRepository.saveAll(Arrays.asList(course, course1));
    }

    private void saveThemes() {
        Course course = getFirst(courseRepository.findAll());

        Theme theme = new Theme();
        theme.setName("Java Operators");
        theme.setCourse(course);

        Theme theme2 = new Theme();
        theme2.setName("Java Strings");
        theme2.setCourse(course);

        Theme theme3 = new Theme();
        theme3.setName("Java Generics");
        theme3.setCourse(course);

        this.themes = themeRepository.saveAll(Arrays.asList(theme, theme2, theme3));
    }



    private void saveSchemeStrategies() {
        Strategy strategy = new Strategy();
        strategy.setName("default");
        strategy.setDescription("Default sequence sorting strategy: " +
                "themes, questions, types, levels appear in the resulting individual question sequence " +
                "without any additional processing");

        Strategy strategy2 = new Strategy();
        strategy2.setName("randomized");
        strategy2.setDescription("Keeps theme sequence, but randomizes questions within a theme");

        strategyRepository.saveAll(Arrays.asList(strategy, strategy2));
    }


    private void saveSchemeModes() {
        Staff staff = getFirst(staffRepository.findAll());

        Mode mode = new Mode();
        mode.setName("exam");
        mode.setStaff(staff);
        mode.setHelpable(false);
        mode.setPyramid(false);
        mode.setSkipable(false);
        mode.setRightAnswer(false);
        mode.setResultDetails(false);
        mode.setPauseable(false);
        mode.setPreservable(false);
        mode.setReportable(true);


        Mode mode2 = new Mode();
        mode2.setName("training");
        mode2.setStaff(staff);
        mode2.setHelpable(true);
        mode2.setPyramid(true);
        mode2.setSkipable(true);
        mode2.setRightAnswer(true);
        mode2.setResultDetails(true);
        mode2.setPauseable(true);
        mode2.setPreservable(true);
        mode2.setReportable(true);

        modeRepository.saveAll(Arrays.asList(mode, mode2));
    }

    private void saveSchemeSettings() {
        Staff staff = getFirst(staffRepository.findAll());

        Settings settings=new Settings();
        settings.setName("default");
        settings.setStaff(staff);
        settings.setSecondsPerQuestion(60);
        settings.setQuestionsPerSheet((short) 1);
        settings.setDaysKeepResultDetails((short)1);
        settings.setThreshold3((byte) 50);
        settings.setThreshold4((byte) 75);
        settings.setThreshold5((byte) 90);
        settings.setLevel2Coefficient(1);
        settings.setLevel3Coefficient(1);
        settings.setDisplayMark(true);
        settings.setDisplayPercent(true);

        Settings settings2=new Settings();
        settings2.setName("custom");
        settings2.setStaff(staff);
        settings2.setSecondsPerQuestion(30);
        settings2.setQuestionsPerSheet((short) 1);
        settings2.setDaysKeepResultDetails((short)365);
        settings2.setThreshold3((byte) 60);
        settings2.setThreshold4((byte) 75);
        settings2.setThreshold5((byte) 95);
        settings2.setLevel2Coefficient(1);
        settings2.setLevel3Coefficient(1);
        settings2.setDisplayMark(false);
        settings2.setDisplayPercent(false);

        settingsRepository.saveAll(Arrays.asList(settings, settings2));
    }

    private void saveSchemes() {
        Course course = getFirst(courseRepository.findAll());
        Staff staff = getFirst(staffRepository.findAll());
        Mode mode = getFirst(modeRepository.findAll());
        Settings settings = getFirst(settingsRepository.findAll());
        Strategy strategy = getFirst(strategyRepository.findAll());

        Scheme scheme = new Scheme();
        scheme.setName("Java Basics: training scheme");
        scheme.setActive(true);
        scheme.setCourse(course);
        scheme.setStaff(staff);
        scheme.setMode(mode);
        scheme.setSettings(settings);
        scheme.setStrategy(strategy);
        scheme.setCreated(LocalDateTime.now());

        SchemeTheme schemeTheme = new SchemeTheme();
        schemeTheme.setScheme(scheme);
        final Theme theme = getFirst(themeRepository.findAll());
        schemeTheme.setTheme(theme);

        // Find out what question types and levels exist in this theme? Set all existing types and levels by default for further processing
        final List<ThemeView> themeViews = themeViewRepository.findAllByThemeId(theme.getThemeId());
        final List<SchemeThemeSettings> allSchemeThemeSettings = new ArrayList<>();
        for (ThemeView themeView : themeViews) {
            final String abbreviation = themeView.getType();
            final short l1 = themeView.getL1();
            final short l2 = themeView.getL2();
            final short l3 = themeView.getL3();
            SchemeThemeSettings schemeThemeSettings = new SchemeThemeSettings();
            schemeThemeSettings.setSchemeTheme(schemeTheme);
            schemeThemeSettings.setType(questionTypeRepository.findByAbbreviation(abbreviation));
            schemeThemeSettings.setLevel1(l1);
            schemeThemeSettings.setLevel2(l2);
            schemeThemeSettings.setLevel3(l3);
            allSchemeThemeSettings.add(schemeThemeSettings);
        }
        schemeTheme.setSchemeThemeSettings(allSchemeThemeSettings);
        scheme.setSchemeThemes(Arrays.asList(schemeTheme));

        schemeRepository.save(scheme);
    }

    private void saveQuestionTypes() {
        QuestionType type = new QuestionType();
        type.setTypeId(1L);
        type.setAbbreviation("MCQ");
        type.setDescription("Multiple choice question");

        QuestionType type2 = new QuestionType();
        type2.setTypeId(2L);
        type2.setAbbreviation("FBSQ");
        type2.setDescription("Fill blank single question");

        QuestionType type3 = new QuestionType();
        type3.setTypeId(3L);
        type3.setAbbreviation("FBMQ");
        type3.setDescription("Fill blank multiple question");

        QuestionType type4 = new QuestionType();
        type4.setTypeId(4L);
        type4.setAbbreviation("MQ");
        type4.setDescription("Matcher question");

        QuestionType type5 = new QuestionType();
        type5.setTypeId(5L);
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

    private void saveAnswerSettings() {
        Staff staff = getFirst(staffRepository.findAll());

        final Iterable<Language> languages = languageRepository.findAll();
        Language lang = getRandom(languages);

        SettingsAnswerFillBlank settings = new SettingsAnswerFillBlank();
        settings.setName("eng default");
        settings.setStaff(staff);
        settings.setLang(lang);
        settings.setWordsLimit((short)5);
        settings.setSymbolsLimit((short) 50);

        Language lang2 = getRandom(languages);

        SettingsAnswerFillBlank settings2 = new SettingsAnswerFillBlank();
        settings2.setName("ru default");
        settings2.setStaff(staff);
        settings2.setLang(lang2);
        settings2.setWordsLimit((short)5);
        settings2.setSymbolsLimit((short) 50);

        settingsAnswerFillBlankRepository.saveAll(Arrays.asList(settings, settings2));
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
        Theme theme = getFirst(themes);

        QuestionMultipleChoice mcq = new QuestionMultipleChoice();
        mcq.setQuestion("Interface used to interact with the second-level cache.");
        mcq.setLevel((byte)1);
        mcq.setTheme(theme);

        Language lang = getRandom(languageRepository.findAll());

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

        Staff staff = getRandom(staffRepository.findAll());

        Resource resource = new Resource();
        resource.setLink("http://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html");
        resource.setDescription("Java Persistence API web documentation");
        resource.setStaff(staff);

        mcq.setResources(new HashSet(Arrays.asList(resource)));

        Help help = new Help();
        help.setHelp("Package javax.persistence");
        help.setQuestion(mcq);

        mcq.setHelp(new HashSet<>(Arrays.asList(help)));

        questionRepository.save(mcq);
    }


    private void saveFBSQ() {
        Theme theme = getFirst(themes);

        QuestionFillBlankSingle fbsq = new QuestionFillBlankSingle();
        fbsq.setQuestion("Defines the set of cascadable operations that are propagated to the associated entity.");
        fbsq.setLevel((byte)1);
        fbsq.setTheme(theme);

        Language lang = getFirst(languageRepository.findAll());

        fbsq.setLang(lang);

        final AnswerFillBlankSingle answer = new AnswerFillBlankSingle();

        Staff staff = getRandom(staffRepository.findAll());

        AcceptedPhrase acceptedPhrase = new AcceptedPhrase();
        acceptedPhrase.setPhrase("CascadeType");
        acceptedPhrase.setStaff(staff);

        AcceptedPhrase acceptedPhrase1 = new AcceptedPhrase();
        acceptedPhrase1.setPhrase("Enum<CascadeType>");
        acceptedPhrase1.setStaff(staff);

        AcceptedPhrase acceptedPhrase2 = new AcceptedPhrase();
        acceptedPhrase2.setPhrase("java.lang.Object java.lang.Enum<CascadeType>");
        acceptedPhrase2.setStaff(staff);

        answer.setAcceptedPhrases(new HashSet<>(Arrays.asList(acceptedPhrase, acceptedPhrase1, acceptedPhrase2)));

        SettingsAnswerFillBlank settings = getRandom(settingsAnswerFillBlankRepository.findAll());

        answer.setSettings(settings);

        answer.setQuestion(fbsq);

        fbsq.setAnswer(answer);

        questionRepository.save(fbsq);
    }

    private void saveFBMQ() {
        Theme theme = getFirst(themes);

        QuestionFillBlankMultiple fbmq = new QuestionFillBlankMultiple();
        fbmq.setQuestion("Lock modes can be specified by means of passing a LockModeType argument to one " +
                "of the EntityManager methods that take locks (lock, find, or refresh) or to the" +
                "Query.setLockMode() or TypedQuery.setLockMode() method.");
        fbmq.setLevel((byte)2);
        fbmq.setTheme(theme);

        Language lang = getFirst(languageRepository.findAll());

        fbmq.setLang(lang);



        final AnswerFillBlankMultiple answer = new AnswerFillBlankMultiple();
        answer.setPhrase("Query.setLockMode()");
        answer.setOccurrence((byte)1);

        Staff staff = getRandom(staffRepository.findAll());

        AcceptedPhrase acceptedPhrase = new AcceptedPhrase();
        acceptedPhrase.setPhrase("setLockMode() of Query");
        acceptedPhrase.setStaff(staff);

        AcceptedPhrase acceptedPhrase1 = new AcceptedPhrase();
        acceptedPhrase1.setPhrase("Query.setLockMode");
        acceptedPhrase1.setStaff(staff);

        answer.setAcceptedPhrases(new HashSet<>(Arrays.asList(acceptedPhrase, acceptedPhrase1)));

        SettingsAnswerFillBlank settings = getRandom(settingsAnswerFillBlankRepository.findAll());

        answer.setSettings(settings);
        answer.setQuestion(fbmq);


        final AnswerFillBlankMultiple answer2 = new AnswerFillBlankMultiple();
        answer2.setPhrase("TypedQuery.setLockMode()");
        answer2.setOccurrence((byte)1);

        AcceptedPhrase acceptedPhrase2 = new AcceptedPhrase();
        acceptedPhrase2.setPhrase("setLockMode() of TypedQuery");
        acceptedPhrase2.setStaff(staff);

        AcceptedPhrase acceptedPhrase3 = new AcceptedPhrase();
        acceptedPhrase3.setPhrase("TypedQuery.setLockMode");
        acceptedPhrase3.setStaff(staff);

        answer2.setAcceptedPhrases(new HashSet<>(Arrays.asList(acceptedPhrase, acceptedPhrase1)));

        SettingsAnswerFillBlank settings2 = getRandom(settingsAnswerFillBlankRepository.findAll());

        answer2.setSettings(settings2);
        answer2.setQuestion(fbmq);

        fbmq.setAnswers(Arrays.asList(answer, answer2));

        questionRepository.save(fbmq);
    }


    private void saveMQ() {
        Theme theme = getFirst(themes);

        QuestionMatcher mq = new QuestionMatcher();
        mq.setQuestion("Match the following concepts:");
        mq.setLevel((byte)3);
        mq.setTheme(theme);

        Language lang = getRandom(languageRepository.findAll());

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
        Theme theme = getFirst(themes);

        QuestionSequence sq = new QuestionSequence();
        sq.setQuestion("Order Maven build process stages one after another:");
        sq.setLevel((byte)2);
        sq.setTheme(theme);

        Language lang = getRandom(languageRepository.findAll());
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
