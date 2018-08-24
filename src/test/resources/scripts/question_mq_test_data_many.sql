insert into organisation (name) values('Open IT-university');
insert into faculty (name) values('Programming faculty');
insert into department (name, org_id, fac_id) values('Enterprise programming', 1, 1);
insert into role (name) values('Department instructor');
insert into position (name) values('Instructor');
insert into user (name, surname, password, email) values('Clara','Denis','hY45lKj4','clara.denis@gmail.com');
insert into staff (user_id, dep_id, pos_id, role_id) values(1, 1, 1, 1);
insert into course (name, created, created_by, dep_id) values('Java for Beginners', CURRENT_TIMESTAMP, 1, 1);
insert into language (name, eng_abbreviation) values('English', 'en');
insert into theme (name, course_id) values('JPA', 1);

insert into question_type (type_id, eng_abbreviation, description) values (4, 'MQ', 'Matcher question');

insert into theme (name, course_id) values('Java Operators', 1);
insert into theme (name, course_id) values('Java Strings', 1);
insert into theme (name, course_id) values('Java Generics', 1);

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


insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #1', 1, 4, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #2', 1, 4, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #3', 2, 4, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #4', 3, 4, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #5', 3, 4, 3, 1);


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


insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #1 (Q1)', 'Right #1(Q1)', 1);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #2 (Q1)', 'Right #2(Q1)', 1);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #3 (Q1)', 'Right #3(Q1)', 1);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #4 (Q1)', 'Right #4(Q1)', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema11.jpg', 'AnswerSchema#11', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema12.jpg', 'AnswerSchema#12', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema13.jpg', 'AnswerSchema#13', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema14.jpg', 'AnswerSchema#14', 1);
insert into answer_mq_resource(answer_id, resource_id) values(1, 11);
insert into answer_mq_resource(answer_id, resource_id) values(2, 12);
insert into answer_mq_resource(answer_id, resource_id) values(3, 13);
insert into answer_mq_resource(answer_id, resource_id) values(4, 14);

insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #1 (Q2)', 'Right #1(Q2)', 2);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #2 (Q2)', 'Right #2(Q2)', 2);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #3 (Q2)', 'Right #3(Q2)', 2);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #4 (Q2)', 'Right #4(Q2)', 2);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema15.jpg', 'AnswerSchema#21', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema16.jpg', 'AnswerSchema#22', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema17.jpg', 'AnswerSchema#23', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema18.jpg', 'AnswerSchema#24', 1);
insert into answer_mq_resource(answer_id, resource_id) values(5, 15);
insert into answer_mq_resource(answer_id, resource_id) values(6, 16);
insert into answer_mq_resource(answer_id, resource_id) values(7, 17);
insert into answer_mq_resource(answer_id, resource_id) values(8, 18);

insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #1 (Q3)', 'Right #1(Q3)', 3);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #2 (Q3)', 'Right #2(Q3)', 3);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #3 (Q3)', 'Right #3(Q3)', 3);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #4 (Q3)', 'Right #4(Q3)', 3);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema19.jpg', 'AnswerSchema#31', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema20.jpg', 'AnswerSchema#32', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema21.jpg', 'AnswerSchema#33', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema22.jpg', 'AnswerSchema#34', 1);
insert into answer_mq_resource(answer_id, resource_id) values(9, 19);
insert into answer_mq_resource(answer_id, resource_id) values(10, 20);
insert into answer_mq_resource(answer_id, resource_id) values(11, 21);
insert into answer_mq_resource(answer_id, resource_id) values(12, 22);

insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #1 (Q4)', 'Right #1(Q4)', 4);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #2 (Q4)', 'Right #2(Q4)', 4);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #3 (Q4)', 'Right #3(Q4)', 4);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #4 (Q4)', 'Right #4(Q4)', 4);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema23.jpg', 'AnswerSchema#41', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema24.jpg', 'AnswerSchema#42', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema25.jpg', 'AnswerSchema#43', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema26.jpg', 'AnswerSchema#44', 1);
insert into answer_mq_resource(answer_id, resource_id) values(13, 23);
insert into answer_mq_resource(answer_id, resource_id) values(14, 24);
insert into answer_mq_resource(answer_id, resource_id) values(15, 25);
insert into answer_mq_resource(answer_id, resource_id) values(16, 26);

insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #1 (Q5)', 'Right #1(Q5)', 5);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #2 (Q5)', 'Right #2(Q5)', 5);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #3 (Q5)', 'Right #3(Q5)', 5);
insert into answer_mq(left_phrase, right_phrase, question_id) values('Left #4 (Q5)', 'Right #4(Q5)', 5);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema27.jpg', 'AnswerSchema#51', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema28.jpg', 'AnswerSchema#52', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema29.jpg', 'AnswerSchema#53', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema30.jpg', 'AnswerSchema#54', 1);
insert into answer_mq_resource(answer_id, resource_id) values(17, 27);
insert into answer_mq_resource(answer_id, resource_id) values(18, 28);
insert into answer_mq_resource(answer_id, resource_id) values(19, 29);
insert into answer_mq_resource(answer_id, resource_id) values(20, 30);



