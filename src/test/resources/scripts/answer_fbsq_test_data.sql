insert into organisation (name) values('Open IT-university');
insert into faculty (name, org_id) values('Programming faculty', 1);
insert into department (name, fac_id) values('Enterprise programming', 1);
insert into role (name) values('Department instructor');
insert into position (name) values('Instructor');
insert into user (name, surname, password, email) values('Clara','Denis','hY45lKj4','clara.denis@gmail.com');
insert into staff (user_id, dep_id, pos_id, role_id) values(1, 1, 1, 1);
insert into course (name, created, created_by, dep_id) values('Java for Beginners', CURRENT_TIMESTAMP, 1, 1);
insert into question_type (type_id, eng_abbreviation, description) values (2, 'FBSQ', 'Fill blank single question');
insert into language (name, eng_abbreviation) values('English', 'en');
insert into language (name, eng_abbreviation) values('русский', 'ru');
insert into theme (name, course_id) values('Java Operators', 1);
insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id) values('eng default', 5, 100, 0, 0, 0, 1, 1);
insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id) values('ru default', 5, 100, 0, 0, 0, 2, 1);

insert into question(title, level, type_id, theme_id, lang_id, is_deleted) values('some question', 1, 2, 1, 1, 0);
insert into answer_fbsq(answer_id, set_id) values(1, 1);

insert into accepted_phrase(phrase, staff_id, last_used) values('CascadeType',1, '2018-07-18 10:30:15.999999999');
insert into accepted_phrase(phrase, staff_id, last_used) values('Enum<CascadeType>',1, '2018-07-18 10:32:19.999999999');
insert into accepted_phrase(phrase, staff_id, last_used) values('javax.persistence.CascadeType',1, '2018-07-18 10:32:19.999999999');

insert into fbsq_phrase(phrase_id, answer_id) values(1, 1);
insert into fbsq_phrase(phrase_id, answer_id) values(2, 1);
