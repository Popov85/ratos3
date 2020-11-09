insert into theme (name, course_id, created_by, access_id, belongs_to, created)
values ('Theme #1', 1, 1, 1, 1, CURRENT_TIMESTAMP);
insert into theme (name, course_id, created_by, access_id, belongs_to, created)
values ('Theme #2', 1, 1, 1, 1, CURRENT_TIMESTAMP);
insert into theme (name, course_id, created_by, access_id, belongs_to, created)
values ('Theme #3', 1, 1, 1, 1, CURRENT_TIMESTAMP);

insert into resource (hyperlink, description, staff_id, width, height, last_used)
values ('https://image.slidesharecdn.com/schema01.jpg', 'QuestionSchema#1', 1, 640, 480, '2020-07-18 11:45:15.999999999');
insert into resource (hyperlink, description, staff_id, width, height, last_used)
values ('https://image.slidesharecdn.com/schema02.jpg', 'QuestionSchema#2', 1, 640, 480, '2020-07-18 11:45:15.999999999');
insert into resource (hyperlink, description, staff_id, width, height, last_used)
values ('https://image.slidesharecdn.com/schema03.jpg', 'QuestionSchema#3', 1, 640, 480, '2020-07-18 11:45:15.999999999');
insert into resource (hyperlink, description, staff_id, width, height, last_used)
values ('https://image.slidesharecdn.com/schema04.jpg', 'QuestionSchema#4', 1, 640, 480, '2020-07-18 11:45:15.999999999');
insert into resource (hyperlink, description, staff_id, width, height, last_used)
values ('https://image.slidesharecdn.com/schema05.jpg', 'QuestionSchema#5', 1, 640, 480, '2020-07-18 11:45:15.999999999');

insert into resource (hyperlink, description, staff_id, width, height, last_used)
values ('https://image.slidesharecdn.com/schema06.jpg', 'HelpSchema#1', 1, 640, 480, '2020-07-18 11:45:15.999999999');
insert into resource (hyperlink, description, staff_id, width, height, last_used)
values ('https://image.slidesharecdn.com/schema07.jpg', 'HelpSchema#2', 1, 640, 480, '2020-07-18 11:45:15.999999999');
insert into resource (hyperlink, description, staff_id, width, height, last_used)
values ('https://image.slidesharecdn.com/schema08.jpg', 'HelpSchema#3', 1, 640, 480, '2020-07-18 11:45:15.999999999');
insert into resource (hyperlink, description, staff_id, width, height, last_used)
values ('https://image.slidesharecdn.com/schema09.jpg', 'HelpSchema#4', 1, 640, 480, '2020-07-18 11:45:15.999999999');
insert into resource (hyperlink, description, staff_id, width, height, last_used)
values ('https://image.slidesharecdn.com/schema10.jpg', 'HelpSchema#5', 1, 640, 480, '2020-07-18 11:45:15.999999999');

insert into help(name, text, staff_id)
values ('help name #1', 'Please, refer to schema #1', 1);
insert into help(name, text, staff_id)
values ('help name #2', 'Please, refer to schema #2', 1);
insert into help(name, text, staff_id)
values ('help name #3', 'Please, refer to schema #3', 1);
insert into help(name, text, staff_id)
values ('help name #4', 'Please, refer to schema #4', 1);
insert into help(name, text, staff_id)
values ('help name #5', 'Please, refer to schema #5', 1);

insert into help_resource(help_id, resource_id)
values (1, 6);
insert into help_resource(help_id, resource_id)
values (2, 7);
insert into help_resource(help_id, resource_id)
values (3, 8);
insert into help_resource(help_id, resource_id)
values (4, 9);
insert into help_resource(help_id, resource_id)
values (5, 10);


insert into question (title, level, type_id, theme_id)
values ('Fill blank multiple question #1 required', 2, 3, 1);
insert into question (title, level, type_id, theme_id)
values ('FBMQ question #2', 2, 3, 1);
insert into question (title, level, type_id, theme_id)
values ('Fill blank multiple question #3', 2, 3, 1);
insert into question (title, level, type_id, theme_id)
values ('Fill blank multiple question #4', 2, 3, 2);
insert into question (title, level, type_id, theme_id)
values ('Fill blank multiple question #5', 2, 3, 2);


insert into question_resource(question_id, resource_id)
values (1, 1);
insert into question_resource(question_id, resource_id)
values (2, 2);
insert into question_resource(question_id, resource_id)
values (3, 3);
insert into question_resource(question_id, resource_id)
values (4, 4);
insert into question_resource(question_id, resource_id)
values (5, 5);

insert into question_help(question_id, help_id)
values (1, 1);
insert into question_help(question_id, help_id)
values (2, 2);
insert into question_help(question_id, help_id)
values (3, 3);
insert into question_help(question_id, help_id)
values (4, 4);
insert into question_help(question_id, help_id)
values (5, 5);


insert into answer_fbmq(phrase, occurrence, set_id, question_id)
values ('phrase #11 (Q1)', 1, 1, 1);
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #11 (Q1)', 1, '2018-07-18 10:30:15.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #12 (Q1)', 1, '2018-07-18 10:32:19.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #13 (Q1)', 1, '2018-07-18 10:32:19.999999999');
insert into fbmq_phrase(answer_id, phrase_id)
values (1, 1);
insert into fbmq_phrase(answer_id, phrase_id)
values (1, 2);
insert into fbmq_phrase(answer_id, phrase_id)
values (1, 3);

insert into answer_fbmq(phrase, occurrence, set_id, question_id)
values ('phrase #21 (Q1)', 1, 1, 1);
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #21 (Q1)', 1, '2018-07-18 10:30:15.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #22 (Q1)', 1, '2018-07-18 10:32:19.999999999');
insert into fbmq_phrase(answer_id, phrase_id)
values (2, 4);
insert into fbmq_phrase(answer_id, phrase_id)
values (2, 5);


insert into answer_fbmq(phrase, occurrence, set_id, question_id)
values ('phrase #11 (Q2)', 1, 1, 2);
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #11 (Q2)', 1, '2018-07-18 10:30:15.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #12 (Q2)', 1, '2018-07-18 10:32:19.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #13 (Q2)', 1, '2018-07-18 10:32:19.999999999');
insert into fbmq_phrase(answer_id, phrase_id)
values (3, 6);
insert into fbmq_phrase(answer_id, phrase_id)
values (3, 7);
insert into fbmq_phrase(answer_id, phrase_id)
values (3, 8);

insert into answer_fbmq(phrase, occurrence, set_id, question_id)
values ('phrase #21 (Q2)', 1, 1, 2);
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #21 (Q2)', 1, '2018-07-18 10:30:15.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #22 (Q2)', 1, '2018-07-18 10:32:19.999999999');
insert into fbmq_phrase(answer_id, phrase_id)
values (4, 9);
insert into fbmq_phrase(answer_id, phrase_id)
values (4, 10);


insert into answer_fbmq(phrase, occurrence, set_id, question_id)
values ('phrase #11 (Q3)', 1, 1, 3);
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #11 (Q3)', 1, '2018-07-18 10:30:15.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #12 (Q3)', 1, '2018-07-18 10:32:19.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #13 (Q3)', 1, '2018-07-18 10:32:19.999999999');
insert into fbmq_phrase(answer_id, phrase_id)
values (5, 11);
insert into fbmq_phrase(answer_id, phrase_id)
values (5, 12);
insert into fbmq_phrase(answer_id, phrase_id)
values (5, 13);

insert into answer_fbmq(phrase, occurrence, set_id, question_id)
values ('phrase #21 (Q3)', 1, 1, 3);
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #21 (Q3)', 1, '2018-07-18 10:30:15.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #22 (Q3)', 1, '2018-07-18 10:32:19.999999999');
insert into fbmq_phrase(answer_id, phrase_id)
values (6, 14);
insert into fbmq_phrase(answer_id, phrase_id)
values (6, 15);


insert into answer_fbmq(phrase, occurrence, set_id, question_id)
values ('phrase #11 (Q4)', 1, 1, 4);
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #11 (Q4)', 1, '2018-07-18 10:30:15.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #12 (Q4)', 1, '2018-07-18 10:32:19.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #13 (Q4)', 1, '2018-07-18 10:32:19.999999999');
insert into fbmq_phrase(answer_id, phrase_id)
values (7, 16);
insert into fbmq_phrase(answer_id, phrase_id)
values (7, 17);
insert into fbmq_phrase(answer_id, phrase_id)
values (7, 18);

insert into answer_fbmq(phrase, occurrence, set_id, question_id)
values ('phrase #21 (Q4)', 1, 1, 4);
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #21 (Q4)', 1, '2018-07-18 10:30:15.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #22 (Q4)', 1, '2018-07-18 10:32:19.999999999');
insert into fbmq_phrase(answer_id, phrase_id)
values (8, 19);
insert into fbmq_phrase(answer_id, phrase_id)
values (8, 20);


insert into answer_fbmq(phrase, occurrence, set_id, question_id)
values ('phrase #11 (Q5)', 1, 1, 5);
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #11 (Q5)', 1, '2018-07-18 10:30:15.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #12 (Q5)', 1, '2018-07-18 10:32:19.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #13 (Q5)', 1, '2018-07-18 10:32:19.999999999');
insert into fbmq_phrase(answer_id, phrase_id)
values (9, 21);
insert into fbmq_phrase(answer_id, phrase_id)
values (9, 22);
insert into fbmq_phrase(answer_id, phrase_id)
values (9, 23);

insert into answer_fbmq(phrase, occurrence, set_id, question_id)
values ('phrase #21 (Q5)', 1, 1, 5);
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #21 (Q5)', 1, '2018-07-18 10:30:15.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #22 (Q5)', 1, '2018-07-18 10:32:19.999999999');
insert into fbmq_phrase(answer_id, phrase_id)
values (10, 24);
insert into fbmq_phrase(answer_id, phrase_id)
values (10, 25);