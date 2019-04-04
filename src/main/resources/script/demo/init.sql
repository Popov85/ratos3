insert into organisation (name) values('University');
insert into faculty (name, org_id) values('Faculty', 1);
insert into department (name, fac_id) values('Department', 1);
insert into class (name, fac_id) values('Class', 1);

insert into role (name) values('ROLE_OAUTH');
insert into role (name) values('ROLE_STUDENT');
insert into role (name) values('ROLE_INSTRUCTOR');
insert into role (name) values('ROLE_LAB-ASSISTANT');
insert into role (name) values('ROLE_DEP-ADMIN');
insert into role (name) values('ROLE_FAC-ADMIN');
insert into role (name) values('ROLE_ORG-ADMIN');
insert into role (name) values('ROLE_GLOBAL-ADMIN');

insert into position (name) values('System admin');
insert into position (name) values('Dean');
insert into position (name) values('Head');
insert into position (name) values('Professor');
insert into position (name) values('Instructor');
insert into position (name) values('Researcher');
insert into position (name) values('Postgraduate');
insert into position (name) values('Lab. assistant');

insert into user (name, surname, password, email, is_active) values('Staff','Staff','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','staff.staff@example.com', 1);
insert into user_role(user_id, role_id) VALUES (1, 8);
insert into staff (staff_id, dep_id, pos_id) values(1, 1, 1);

insert into user (name, surname, password, email, is_active) values('Student','Student','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','student@example.com', 1);
insert into user_role(user_id, role_id) VALUES (2, 2);
insert into student(stud_id, class_id, fac_id, org_id, entrance_year) values(2, 1, 1,1, 2018);

insert into user (name, surname, password, email, is_active) values('Student2','Student2','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','student2@example.com', 1);
insert into user_role(user_id, role_id) VALUES (3, 2);
insert into student(stud_id, class_id, fac_id, org_id, entrance_year) values(3, 1, 1,1, 2018);

insert into language (name, eng_abbreviation) values('English', 'en');
insert into language (name, eng_abbreviation) values('français', 'fr');
insert into language (name, eng_abbreviation) values('Deutsch', 'de');
insert into language (name, eng_abbreviation) values('polski', 'pl');
insert into language (name, eng_abbreviation) values('українська', 'ua');
insert into language (name, eng_abbreviation) values('русский', 'ru');

insert into question_type (type_id, eng_abbreviation, description) values (1, 'MCQ', 'Multiple choice question');
insert into question_type (type_id, eng_abbreviation, description) values (2, 'FBSQ', 'Fill blank single question');
insert into question_type (type_id, eng_abbreviation, description) values (3, 'FBMQ', 'Fill blank multiple question');
insert into question_type (type_id, eng_abbreviation, description) values (4, 'MQ', 'Matcher question');
insert into question_type (type_id, eng_abbreviation, description) values (5, 'SQ', 'Sequence question');

insert into complaint_type(type_id, name, description) values(1, 'Wrong statement', 'Incorrect statement of question');
insert into complaint_type(type_id, name, description) values(2, 'Typo in question', 'Typo in question, grammatical error');
insert into complaint_type(type_id, name, description) values(3, 'Typo in answer(s)', 'Typo in one or many answer(s)');
insert into complaint_type(type_id, name, description) values(4, 'Wrong question formatting', 'Wrong question formatting: alignment, media, positioning, etc.');
insert into complaint_type(type_id, name, description) values(5, 'Wrong answer(s) formatting', 'Wrong answer(s) formatting: alignment, media, positioning, etc.');
insert into complaint_type(type_id, name, description) values(6, 'Other', 'Another unnamed questionType of errors');

insert into access_level(name) values('dep-private');
insert into access_level(name) values('private');

insert into strategy(name, description)
values('default','Default sequence sorting strategy');
insert into strategy(name, description)
values('random','Random sequence sorting strategy');
insert into strategy(name, description)
values('types&levels','TypesThenLevels sequence sorting strategy');

insert into grading(name, description) values('four-point', 'classic 4 points grading system {2, 3, 4, 5}');
insert into grading(name, description) values('two-point', 'classic 2 points grading system {0, 1} or {passed, not passed}');
insert into grading(name, description) values('free-point', 'universal discrete grading system {min, ..., max}');

insert into four_point(name, threshold_3, threshold_4, threshold_5,  is_default, is_deleted, grading_id, created_by, belongs_to)
values('default', 50, 70, 85, 1, 0, 1, 1, 1);
insert into two_point(name, threshold, is_default, is_deleted, grading_id, created_by, belongs_to)
values('default', 50, 1, 0, 2, 1,1);
insert into free_point(name, min_value, pass_value, max_value, grading_id, is_default, created_by, belongs_to)
values('ects', 0, 60, 200, 3, 1, 1, 1);
insert into free_point(name, min_value, pass_value, max_value, grading_id, is_default, created_by, belongs_to)
values('lms', 0, 0.5, 1, 3, 1, 1, 1);


insert into settings(name, seconds_per_question, strict_seconds_per_question, questions_per_sheet, days_keep_result_details, level_2_coefficient, level_3_coefficient, display_percent, display_mark, display_theme_results, display_question_results, is_deleted, is_default, created_by, belongs_to)
values('default', 60, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1);

insert into mode(name, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable, is_default, created_by, belongs_to)
values('exam', 0, 0, 0, 0, 0, 1, 1, 1, 1);
insert into mode(name, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable, is_default, created_by, belongs_to)
values('training', 1, 1, 1, 1, 1, 1, 1, 1, 1);

insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id) values('eng default', 5, 100, 0, 0, 0, 1, 1);
insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id) values('ua default', 5, 100, 0, 0, 0, 5, 1);
insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id) values('ru default', 5, 100, 0, 0, 0, 6, 1);

insert into course (name, created, created_by, belongs_to, access_id) values('Test LTI course #1', CURRENT_TIMESTAMP, 1, 1, 1);

insert into lti_credentials(lti_consumer_key, lti_client_secret) values('ratos_consumer_key', 'ratos_client_secret');
insert into lti_credentials(lti_consumer_key, lti_client_secret) values('ratos_consumer_key_1', 'ratos_client_secret_1');
insert into lti_credentials(lti_consumer_key, lti_client_secret) values('ratos_consumer_key_2', 'ratos_client_secret_2');

insert into lti_version(version) values('1p0');
insert into lms(name, lti_version_id, org_id, credentials_id) values('Open edX', 1, 1, 1);
insert into lms_origin(link, lms_id) values('http://localhost', 1);
insert into lms_origin(link, lms_id) values('http://localhost:18010', 1);

insert into lms(name, lti_version_id, org_id, credentials_id) values('Open edX-1', 1, 1, 2);
insert into lms_origin(link, lms_id) values('https://localhost', 2);
insert into lms_origin(link, lms_id) values('https://localhost:18010', 2);

insert into lms(name, lti_version_id, org_id, credentials_id) values('Open edX-2', 1, 1, 3);
insert into lms_origin(link, lms_id) values('ftp://localhost', 3);
insert into lms_origin(link, lms_id) values('ftp://localhost:18010', 3);

insert into lms_course(course_id, lms_id) values(1, 1);
