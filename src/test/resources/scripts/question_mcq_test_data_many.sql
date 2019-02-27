insert into theme (name, course_id, created_by, access_id, belongs_to, created) values('Theme #1', 1, 1, 1, 1, CURRENT_TIMESTAMP);
insert into theme (name, course_id, created_by, access_id, belongs_to, created) values('Theme #2', 1, 1, 1, 1, CURRENT_TIMESTAMP);

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

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema11.jpg', 'AnswerSchema#11', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema12.jpg', 'AnswerSchema#12', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema13.jpg', 'AnswerSchema#13', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema14.jpg', 'AnswerSchema#14', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema15.jpg', 'AnswerSchema#21', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema16.jpg', 'AnswerSchema#22', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema17.jpg', 'AnswerSchema#23', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema18.jpg', 'AnswerSchema#24', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema19.jpg', 'AnswerSchema#31', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema20.jpg', 'AnswerSchema#32', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema21.jpg', 'AnswerSchema#33', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema22.jpg', 'AnswerSchema#34', 1);

insert into help(name, text, staff_id) values('helpAvailable name #1', 'Please, refer to schema #1', 1);
insert into help(name, text, staff_id) values('helpAvailable name #2', 'Please, refer to schema #2', 1);
insert into help(name, text, staff_id) values('helpAvailable name #3', 'Please, refer to schema #3', 1);
insert into help(name, text, staff_id) values('helpAvailable name #4', 'Please, refer to schema #4', 1);
insert into help(name, text, staff_id) values('helpAvailable name #5', 'Please, refer to schema #5', 1);

insert into help_resource(help_id, resource_id) values(1, 6);
insert into help_resource(help_id, resource_id) values(2, 7);
insert into help_resource(help_id, resource_id) values(3, 8);
insert into help_resource(help_id, resource_id) values(4, 9);
insert into help_resource(help_id, resource_id) values(5, 10);

insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #1', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #2 (eng)', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #3', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('MCQ choice question #4', 1, 1, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('MCQ choice question #5', 1, 1, 2, 1);

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

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#1)', 100, 1, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#1)', 0, 0, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#1)', 0, 0, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#1)', 0, 0, 1);

insert into answer_mcq_resource(answer_id, resource_id) values(1, 11);
insert into answer_mcq_resource(answer_id, resource_id) values(2, 12);
insert into answer_mcq_resource(answer_id, resource_id) values(3, 13);
insert into answer_mcq_resource(answer_id, resource_id) values(4, 14);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#2)', 100, 1, 2);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#2)', 0, 0, 2);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#2)', 0, 0, 2);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#2)', 0, 0, 2);

insert into answer_mcq_resource(answer_id, resource_id) values(5, 12);
insert into answer_mcq_resource(answer_id, resource_id) values(6, 13);
insert into answer_mcq_resource(answer_id, resource_id) values(7, 14);
insert into answer_mcq_resource(answer_id, resource_id) values(8, 15);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#3)', 100, 1, 3);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#3)', 0, 0, 3);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#3)', 0, 0, 3);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#3)', 0, 0, 3);

insert into answer_mcq_resource(answer_id, resource_id) values(9, 16);
insert into answer_mcq_resource(answer_id, resource_id) values(10, 17);
insert into answer_mcq_resource(answer_id, resource_id) values(11, 18);
insert into answer_mcq_resource(answer_id, resource_id) values(12, 19);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#4)', 100, 1, 4);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#4)', 0, 0, 4);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#4)', 0, 0, 4);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#4)', 0, 0, 4);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#5)', 100, 1, 5);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#5)', 0, 0, 5);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#5)', 0, 0, 5);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#5)', 0, 0, 5);

