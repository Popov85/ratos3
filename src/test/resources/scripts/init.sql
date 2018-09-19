insert into organisation (name) values('University');
insert into faculty (name, org_id) values('Faculty', 1);
insert into department (name, fac_id) values('Department', 1);
insert into role (name) values('Global admin');
insert into position (name) values('System admin');
insert into user (name, surname, password, email) values('Andrey','Popov','dT09Rx01','andrey.popov@gmail.com');
insert into staff (user_id, dep_id, pos_id, role_id) values(1, 1, 1, 1);

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

insert into settings(name, staff_id, seconds_per_question, questions_per_sheet, days_keep_result_details, threshold_3, threshold_4, threshold_5, level_2_coefficient, level_3_coefficient, display_percent, display_mark, is_default)
            values('default', 1, 60, 1, 1, 50, 70, 85, 1, 1, 1, 1, 1);
insert into strategy(name, description)
            values('default','Default sequence sorting strategy');
insert into strategy(name, description)
            values('random','Random sequence sorting strategy');
insert into strategy(name, description)
            values('types&levels','TypesThenLevels sequence sorting strategy');
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
            values('exam', 1, 0, 0, 0, 0, 0, 0, 0, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
            values('training', 1, 1, 1, 1, 1, 1, 1, 1, 1);

insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id) values('eng default', 5, 100, 0, 0, 0, 1, 1);
insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id) values('ua default', 5, 100, 0, 0, 0, 5, 1);
insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id) values('ru default', 5, 100, 0, 0, 0, 6, 1);

insert into course (name, created, created_by, dep_id) values('Test course #1', CURRENT_TIMESTAMP, 1, 1);
