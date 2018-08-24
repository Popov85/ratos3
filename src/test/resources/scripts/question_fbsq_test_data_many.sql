insert into organisation (name) values('Open IT-university');
insert into faculty (name) values('Programming faculty');
insert into department (name, org_id, fac_id) values('Enterprise programming', 1, 1);
insert into role (name) values('Department instructor');
insert into position (name) values('Instructor');
insert into user (name, surname, password, email) values('Clara','Denis','hY45lKj4','clara.denis@gmail.com');
insert into staff (user_id, dep_id, pos_id, role_id) values(1, 1, 1, 1);
insert into course (name, created, created_by, dep_id) values('Java for Beginners', CURRENT_TIMESTAMP, 1, 1);
insert into language (name, eng_abbreviation) values('English', 'en');
insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id) values('eng default', 5, 100, 0, 0, 0, 1, 1);

insert into theme (name, course_id) values('Java Operators', 1);
insert into theme (name, course_id) values('Java Strings', 1);
insert into theme (name, course_id) values('Java Generics', 1);

insert into question_type (type_id, eng_abbreviation, description) values (2, 'FBSQ', 'Fill blank single question');


insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema01.jpg', 'QuestionSchema#1', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema02.jpg', 'QuestionSchema#2', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema03.jpg', 'QuestionSchema#3', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema04.jpg', 'QuestionSchema#4', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema05.jpg', 'QuestionSchema#5', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema06.jpg', 'HelpSchema#1', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema07.jpg', 'HelpSchema#2', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema08.jpg', 'HelpSchema#3', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema09.jpg', 'HelpSchema#4', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema10.jpg', 'HelpSchema#5', 1);

insert into help(name, text, staff_id) values('help name #1', 'Please, refer to schema #1', 1);
insert into help(name, text, staff_id) values('help name #2', 'Please, refer to schema #2', 1);
insert into help(name, text, staff_id) values('help name #3', 'Please, refer to schema #3', 1);
insert into help(name, text, staff_id) values('help name #4', 'Please, refer to schema #4', 1);
insert into help(name, text, staff_id) values('help name #5', 'Please, refer to schema #5', 1);

insert into help_resource(help_id, resource_id) values(1, 6);
insert into help_resource(help_id, resource_id) values(2, 7);
insert into help_resource(help_id, resource_id) values(3, 8);
insert into help_resource(help_id, resource_id) values(4, 9);
insert into help_resource(help_id, resource_id) values(5, 10);



insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #1', 1, 2, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #2', 1, 2, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #3', 1, 2, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #4', 1, 2, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #5', 1, 2, 3, 1);



insert into question_resource(question_id, resource_id) values(1, 1);
insert into question_resource(question_id, resource_id) values(2, 2);
insert into question_resource(question_id, resource_id) values(3, 3);
insert into question_resource(question_id, resource_id) values(4, 4);
insert into question_resource(question_id, resource_id) values(5, 5);

insert into question_help(question_id, help_id) values(1,1);
insert into question_help(question_id, help_id) values(2,2);
insert into question_help(question_id, help_id) values(3,3);
insert into question_help(question_id, help_id) values(4,4);
insert into question_help(question_id, help_id) values(5,5);



insert into answer_fbsq (answer_id, set_id) values(1, 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #1', 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #2', 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #3', 1);
insert into fbsq_phrase(phrase_id, answer_id) values(1, 1);
insert into fbsq_phrase(phrase_id, answer_id) values(2, 1);
insert into fbsq_phrase(phrase_id, answer_id) values(3, 1);

insert into answer_fbsq (answer_id, set_id) values(2, 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #4', 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #5', 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #6', 1);
insert into fbsq_phrase(phrase_id, answer_id) values(4, 2);
insert into fbsq_phrase(phrase_id, answer_id) values(5, 2);
insert into fbsq_phrase(phrase_id, answer_id) values(6, 2);

insert into answer_fbsq (answer_id, set_id) values(3, 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #7', 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #8', 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #9', 1);
insert into fbsq_phrase(phrase_id, answer_id) values(7, 3);
insert into fbsq_phrase(phrase_id, answer_id) values(8, 3);
insert into fbsq_phrase(phrase_id, answer_id) values(9, 3);

insert into answer_fbsq (answer_id, set_id) values(4, 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #10', 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #11', 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #12', 1);
insert into fbsq_phrase(phrase_id, answer_id) values(10, 4);
insert into fbsq_phrase(phrase_id, answer_id) values(11, 4);
insert into fbsq_phrase(phrase_id, answer_id) values(12, 4);

insert into answer_fbsq (answer_id, set_id) values(5, 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #13', 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #14', 1);
insert into accepted_phrase(phrase, staff_id) values('accepted phrase #15', 1);
insert into fbsq_phrase(phrase_id, answer_id) values(13, 5);
insert into fbsq_phrase(phrase_id, answer_id) values(14, 5);
insert into fbsq_phrase(phrase_id, answer_id) values(15, 5);
