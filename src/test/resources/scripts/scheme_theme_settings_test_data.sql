insert into organisation (name) values('Open IT-university');
insert into faculty (name, org_id) values('Programming faculty', 1);
insert into department (name, fac_id) values('Enterprise programming', 1);
insert into role (name) values('Department instructor');
insert into position (name) values('Instructor');
insert into user (name, surname, password, email) values('Clara','Denis','hY45lKj4','clara.denis@gmail.com');
insert into staff (user_id, dep_id, pos_id, role_id) values(1, 1, 1, 1);
insert into course (name, created, created_by, dep_id) values('IT course #1 (D1)', CURRENT_TIMESTAMP, 1, 1);

insert into question_type (type_id, eng_abbreviation, description) values (1, 'MCQ', 'Multiple choice question');
insert into question_type (type_id, eng_abbreviation, description) values (2, 'FBSQ', 'Fill blank single question');
insert into question_type (type_id, eng_abbreviation, description) values (3, 'FBMQ', 'Fill blank multiple question');
insert into question_type (type_id, eng_abbreviation, description) values (4, 'MQ', 'Matcher question');
insert into question_type (type_id, eng_abbreviation, description) values (5, 'SQ', 'Sequence question');

insert into strategy(name, description) values('default','Default sequence sorting strategy: themes, questions, types, levels appear in the resulting individual question sequence without any additional processing');

insert into settings(name, staff_id, seconds_per_question, questions_per_sheet, days_keep_result_details, threshold_3, threshold_4, threshold_5, level_2_coefficient, level_3_coefficient, display_percent, display_mark) values('default', 1, 60, 1, 1, 50, 70, 85, 1, 1, 1, 1);

insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable) values('exam', 1, 0, 0, 0, 0, 0, 0, 0, 1);

insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by) values('Java Basics: training scheme', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1);

insert into theme (name, course_id) values('IT theme #1', 1);

insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 1, 0);






