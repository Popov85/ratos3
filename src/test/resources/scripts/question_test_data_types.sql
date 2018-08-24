insert into organisation (name) values('Open IT-university');
insert into faculty (name) values('Programming faculty');
insert into department (name, org_id, fac_id) values('Enterprise programming', 1, 1);
insert into role (name) values('Department instructor');
insert into position (name) values('Instructor');
insert into user (name, surname, password, email) values('Clara','Denis','hY45lKj4','clara.denis@gmail.com');
insert into staff (user_id, dep_id, pos_id, role_id) values(1, 1, 1, 1);
insert into course (name, created, created_by, dep_id) values('Java for Beginners', CURRENT_TIMESTAMP, 1, 1);
insert into language (name, eng_abbreviation) values('English', 'en');

insert into theme (name, course_id) values('Java Operators', 1);
insert into theme (name, course_id) values('Java Strings', 1);
insert into theme (name, course_id) values('Java Generics', 1);

insert into question_type (type_id, eng_abbreviation, description) values (1, 'MCQ', 'Multiple choice question');
insert into question_type (type_id, eng_abbreviation, description) values (2, 'FBSQ', 'Fill blank single question');
insert into question_type (type_id, eng_abbreviation, description) values (3, 'FBMQ', 'Fill blank multiple question');
insert into question_type (type_id, eng_abbreviation, description) values (4, 'MQ', 'Matcher question');
insert into question_type (type_id, eng_abbreviation, description) values (5, 'SQ', 'Sequence question');


insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #1', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #1', 1, 2, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #1', 2, 3, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #1', 2, 4, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #1', 3, 5, 2, 1);
