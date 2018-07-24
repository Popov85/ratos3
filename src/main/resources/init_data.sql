insert into user (name, surname, password, email) values('Jack','Stone','4jY4580e','jack.stone@gmail.com');
insert into user (name, surname, password, email) values('James','Strong','7pYvs253','james.strong@gmail.com');
insert into user (name, surname, password, email) values('Clara','Denis','hY45lKj4','clara.denis@gmail.com');
insert into user (name, surname, password, email) values('Silvia','Bring','85opT214','silvia.bring@gmail.com');
insert into user (name, surname, password, email) values('Matt','Cruse','opY3451g','matt.cruse@gmail.com');

insert into organisation (name) values('Open IT-university');

insert into faculty (name) values('Programming faculty');

insert into department (name, org_id, fac_id) values('Enterprise programming', 1, 1);
insert into department (name, org_id, fac_id) values('Applied programming', 1, 1);

insert into role (name) values('Global admin');
insert into role (name) values('Department admin');
insert into role (name) values('Department instructor');
insert into role (name) values('Department lab. assistant');

insert into position (name) values('Professor');
insert into position (name) values('Researcher');
insert into position (name) values('Instructor');
insert into position (name) values('Assistant');
insert into position (name) values('Lab. Assistant');

insert into staff (user_id, dep_id, pos_id, role_id) values(1, 1, 1, 1);
insert into staff (user_id, dep_id, pos_id, role_id) values(2, 1, 2, 2);
insert into staff (user_id, dep_id, pos_id, role_id) values(3, 1, 3, 3);
insert into staff (user_id, dep_id, pos_id, role_id) values(4, 1, 4, 3);
insert into staff (user_id, dep_id, pos_id, role_id) values(5, 1, 5, 4);

insert into course (name, created, created_by, dep_id) values('Java for Beginners', CURRENT_TIMESTAMP, 1, 1);
insert into course (name, created, created_by, dep_id) values('Java for Professionals', CURRENT_TIMESTAMP, 2, 1);

insert into theme (name, course_id) values('Java Operators', 1);
insert into theme (name, course_id) values('Java Strings', 1);
insert into theme (name, course_id) values('Java Generics', 1);

insert into question_type (type_id, eng_abbreviation, description) values (1, 'MCQ', 'Multiple choice question');
insert into question_type (type_id, eng_abbreviation, description) values (2, 'FBSQ', 'Fill blank single question');
insert into question_type (type_id, eng_abbreviation, description) values (3, 'FBMQ', 'Fill blank multiple question');
insert into question_type (type_id, eng_abbreviation, description) values (4, 'MQ', 'Matcher question');
insert into question_type (type_id, eng_abbreviation, description) values (5, 'SQ', 'Sequence question');

insert into language (name, eng_abbreviation) values('English', 'en');
insert into language (name, eng_abbreviation) values('français', 'fr');
insert into language (name, eng_abbreviation) values('русский', 'ru');
insert into language (name, eng_abbreviation) values('українська', 'ukr');
insert into language (name, eng_abbreviation) values('Polską', 'pl');





insert into question (title, level, type_id, theme_id, lang_id) values ('Interface used to interact with the second-level cache', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Defines the set of cascadable operations that are propagated to the associated entity', 1, 2, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Lock modes can be specified by means of passing a LockModeType argument to one of the EntityManager methods that take locks (lock, find, or refresh) or to the Query.setLockMode() or TypedQuery.setLockMode() method', 2, 3, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Match the following concepts', 2, 4, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Order Maven build process stages one after another', 3, 5, 1, 1);

insert into answer_mcq (answer, percent, is_required, question_id) values('Cache', 100, 1, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('EntityManager', 0, 0, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('StoredProcedure', 0, 0, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('Subgraph<T>', 0, 0, 1);

insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id) values('eng default', 5, 100, 0, 0, 0, 1, 1);
insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id) values('ru default', 5, 100, 0, 0, 0, 3, 1);


insert into answer_fbsq (answer_id, set_id) values(2, 1);


insert into answer_mq(left_phrase, right_phrase, question_id) values('Interface used to interact with the second-level cache', 'Cache', 4);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Type for query parameter objects', 'Parameter<T>', 4);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Interface used to interact with the persistence context', 'EntityManager', 4);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Interface used to control query execution', 'Query', 4);


insert into answer_sq(element, element_order, question_id) values('clean', 0, 5);
insert into answer_sq(element, element_order, question_id) values('validate', 1, 5);
insert into answer_sq(element, element_order, question_id) values('compile', 2, 5);
insert into answer_sq(element, element_order, question_id) values('test', 3, 5);
insert into answer_sq(element, element_order, question_id) values('package', 4, 5);
insert into answer_sq(element, element_order, question_id) values('verify', 5, 5);

insert into help (name, text, question_id, staff_id) values('Java persistence URL', 'See javax.persistence package', 1, 1);

insert into resource (hyperlink, description, staff_id) values('http://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html', 'Java Persistence API web documentation', 1);

insert into accepted_phrase(phrase, staff_id) values('CascadeType', 1);
insert into accepted_phrase(phrase, staff_id) values('Enum<CascadeType>', 1);
insert into accepted_phrase(phrase, staff_id) values('java.lang.Object java.lang.Enum<CascadeType>', 1);

insert into accepted_phrase(phrase, staff_id) values('setLockMode of Query', 2);
insert into accepted_phrase(phrase, staff_id) values('setLockMode() of Query', 2);
insert into accepted_phrase(phrase, staff_id) values('Query.setLockMode()', 2);

insert into accepted_phrase(phrase, staff_id) values('setLockMode of TypedQuery', 2);
insert into accepted_phrase(phrase, staff_id) values('setLockMode() of TypedQuery', 2);
insert into accepted_phrase(phrase, staff_id) values('TypedQuery.setLockMode()', 2);

insert into fbsq_phrase(phrase_id, answer_id) values(1, 2);
insert into fbsq_phrase(phrase_id, answer_id) values(2, 2);
insert into fbsq_phrase(phrase_id, answer_id) values(3, 2);

insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('Query.setLockMode()', 1, 1, 3);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('TypedQuery.setLockMode()', 1, 1, 3);

insert into fbmq_phrase(answer_id, phrase_id) values(1, 4);
insert into fbmq_phrase(answer_id, phrase_id) values(1, 5);
insert into fbmq_phrase(answer_id, phrase_id) values(1, 6);

insert into fbmq_phrase(answer_id, phrase_id) values(2, 7);
insert into fbmq_phrase(answer_id, phrase_id) values(2, 8);
insert into fbmq_phrase(answer_id, phrase_id) values(2, 9);

insert into strategy(name, description) values('default','Default sequence sorting strategy: themes, questions, types, levels appear in the resulting individual question sequence without any additional processing');
insert into strategy(name, description) values('random','Randomized sequence sorting strategy: keeps themes ordering, but randomizes questions within a theme');

insert into settings(name, staff_id, seconds_per_question, questions_per_sheet, days_keep_result_details, threshold_3, threshold_4, threshold_5, level_2_coefficient, level_3_coefficient, display_percent, display_mark)
       values('default', 1, 60, 1, 1, 50, 70, 85, 1, 1, 1, 1);
insert into settings(name, staff_id, seconds_per_question, questions_per_sheet, days_keep_result_details, threshold_3, threshold_4, threshold_5, level_2_coefficient, level_3_coefficient, display_percent, display_mark)
       values('custom', 3, 30, 1, 365, 60, 75, 90, 1, 1, 0, 0);

insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
       values('exam', 1, 0, 0, 0, 0, 0, 0, 0, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
       values('training', 1, 1, 1, 1, 1, 1, 1, 1, 1);

insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by)
       values('Java Basics: training scheme', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 3);

insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 1, 0);

insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 1, 1, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 2, 1, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 3, 0, 1, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 4, 0, 1, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 5, 0, 0, 1);

