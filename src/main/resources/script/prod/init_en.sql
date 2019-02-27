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

insert into user (name, surname, password, email, is_active) values('Student','Student','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','student.student@example.com', 1);
insert into user_role(user_id, role_id) VALUES (2, 2);
insert into student(stud_id, class_id, fac_id, org_id, entrance_year) values(2, 1, 1, 1, 2018);

insert into language (name, eng_abbreviation) values('English', 'en');

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
insert into complaint_type(type_id, name, description) values(6, 'Other', 'Another unnamed type of errors');

insert into access_level(name) values('dep-private');
insert into access_level(name) values('private');

insert into strategy(name, description)
    values('default','Default sequence sorting strategy');
insert into strategy(name, description)
    values('random','Random sequence sorting strategy');
insert into strategy(name, description)
    values('types&levels','Types->Levels sequence sorting strategy');

insert into grading(name, description) values('four-point', 'classic 4 points grading system {2, 3, 4, 5}');
insert into grading(name, description) values('two-point', 'classic 2 points grading system {0, 1} or {passed, not passed}');
insert into grading(name, description) values('free-point', 'universal discrete grading system {min, ..., max}');

insert into four_point(name, threshold_3, threshold_4, threshold_5, staff_id, is_default, is_deleted, grading_id)
    values('default', 50, 70, 85, 1, 1, 0, 1);
insert into two_point(name, threshold, staff_id, is_default, is_deleted, grading_id)
    values('default', 50, 1, 1, 0, 2);
insert into free_point(name, min_value, pass_value, max_value, staff_id, grading_id)
    values('ECTS', 0, 60, 200, 1, 3);
insert into free_point(name, min_value, pass_value, max_value, staff_id, grading_id)
    values('LMS', 0, 0.5, 1, 1, 3);

insert into settings(name, staff_id, seconds_per_question, strict_seconds_per_question, questions_per_sheet, days_keep_result_details, level_2_coefficient, level_3_coefficient, display_percent, display_mark, display_theme_results, display_question_results, is_deleted, is_default)
    values('default', 1, 60, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable)
    values('exam', 1, 0, 0, 0, 0, 0, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable)
    values('training', 1, 1, 1, 1, 1, 1, 1);

insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id)
    values('eng default', 5, 100, 0, 0, 0, 1, 1);