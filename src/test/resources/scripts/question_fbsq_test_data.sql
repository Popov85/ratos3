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

insert into accepted_phrase(phrase, staff_id, last_used) values ('CascadeType', 1, CURRENT_TIMESTAMP);
insert into accepted_phrase(phrase, staff_id, last_used) values ('Enum<CascadeType>', 1, CURRENT_TIMESTAMP);
insert into accepted_phrase(phrase, staff_id, last_used) values ('java.lang.Enum<CascadeType>', 1, CURRENT_TIMESTAMP);

insert into help (name, text, staff_id) values('Java persistence URL', 'See javax.persistence package',  1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema01.jpg', 'Schema#1', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema02.jpg', 'Schema#2', 1);


