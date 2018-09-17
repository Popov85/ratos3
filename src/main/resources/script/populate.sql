insert into course (name, created, created_by, dep_id) values('Test course #1', CURRENT_TIMESTAMP, 1, 1);

insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_completed) values('Training scheme #1', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1);

insert into theme (name, course_id) values('IT theme #1 with all questions types', 1);
insert into theme (name, course_id) values('IT theme #2', 1);
insert into theme (name, course_id) values('IT theme #3', 1);
insert into theme (name, course_id) values('IT theme #4', 1);

insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 1, 0);

insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 2, 1);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 3, 2);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 4, 3);


insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 1, 5, 1, 1);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 2, 5, 1, 1);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 3, 5, 1, 1);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 4, 5, 1, 1);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 5, 5, 1, 1);

insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(2, 1, 5, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(3, 1, 5, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(4, 1, 5, 0, 0);

/*Questions MCQ 40 pieces*/

insert into help(name, text, staff_id) values('Help name #1 T#1/Q#1', 'Please, refer to schema #1', 1);
insert into help(name, text, staff_id) values('Help name #2', 'Please, refer to schema #2', 1);
insert into help(name, text, staff_id) values('Help name #3', 'Please, refer to schema #3', 1);
insert into help(name, text, staff_id) values('Help name #4', 'Please, refer to schema #4', 1);
insert into help(name, text, staff_id) values('help name #5', 'Please, refer to schema #5', 1);
insert into help(name, text, staff_id) values('Help name #6', 'Please, refer to schema #6', 1);
insert into help(name, text, staff_id) values('Help name #7', 'Please, refer to schema #7', 1);
insert into help(name, text, staff_id) values('Help name #8', 'Please, refer to schema #8', 1);
insert into help(name, text, staff_id) values('Help name #9', 'Please, refer to schema #9', 1);
insert into help(name, text, staff_id) values('help name #10', 'Please, refer to schema #10', 1);


insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/question-resource#01.jpg', 'Question resource #1', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/question-resource#02.jpg', 'Question resource #2', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/question-resource#03.jpg', 'Question resource #3', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/question-resource#04.jpg', 'Question resource #4', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/question-resource#05.jpg', 'Question resource #5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/question-resource#06.jpg', 'Question resource #6', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/question-resource#07.jpg', 'Question resource #7', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/question-resource#08.jpg', 'Question resource #8', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/question-resource#09.jpg', 'Question resource #9', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/question-resource#10.jpg', 'Question resource #10', 1);


insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #1 (Theme #1)', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #2 (Theme #1)', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #3 (Theme #1)', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #4 (Theme #1)', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #5 (Theme #1)', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #6 (Theme #1)', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #7 (Theme #1)', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #8 (Theme #1)', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #9 (Theme #1)', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #10 (Theme #1)', 1, 1, 1, 1);

insert into question_resource(question_id, resource_id) values(1, 1);
insert into question_resource(question_id, resource_id) values(2, 2);
insert into question_resource(question_id, resource_id) values(3, 3);
insert into question_resource(question_id, resource_id) values(4, 4);
insert into question_resource(question_id, resource_id) values(5, 5);
insert into question_resource(question_id, resource_id) values(6, 6);
insert into question_resource(question_id, resource_id) values(7, 7);
insert into question_resource(question_id, resource_id) values(8, 8);
insert into question_resource(question_id, resource_id) values(9, 9);
insert into question_resource(question_id, resource_id) values(10, 10);

insert into question_help(question_id, help_id) values(1,1);
insert into question_help(question_id, help_id) values(2,2);
insert into question_help(question_id, help_id) values(3,3);
insert into question_help(question_id, help_id) values(4,4);
insert into question_help(question_id, help_id) values(5,5);
insert into question_help(question_id, help_id) values(6,6);
insert into question_help(question_id, help_id) values(7,7);
insert into question_help(question_id, help_id) values(8,8);
insert into question_help(question_id, help_id) values(9,9);
insert into question_help(question_id, help_id) values(10,10);

/*Theme #1*/
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#1)*', 100, 1, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#1)', 0, 0, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#1)', 0, 0, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 (Q#1)', 0, 0, 1);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#2)*', 100, 1, 2);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#2)', 0, 0, 2);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#2)', 0, 0, 2);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 (Q#2)', 0, 0, 2);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#3)*', 100, 1, 3);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#3)', 0, 0, 3);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#3)', 0, 0, 3);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 (Q#3)', 0, 0, 3);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#4)*', 100, 1, 4);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#4)', 0, 0, 4);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#4)', 0, 0, 4);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 (Q#4)', 0, 0, 4);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#5)*', 100, 1, 5);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#5)', 0, 0, 5);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#5)', 0, 0, 5);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 (Q#5)', 0, 0, 5);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#6)*', 100, 1, 6);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#6)', 0, 0, 6);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#6)', 0, 0, 6);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 (Q#6)', 0, 0, 6);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#7)*', 100, 1, 7);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#7)', 0, 0, 7);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#7)', 0, 0, 7);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 (Q#7)', 0, 0, 7);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#8)*', 100, 1, 8);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#8)', 0, 0, 8);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#8)', 0, 0, 8);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 (Q#8)', 0, 0, 8);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#9*)', 100, 1, 9);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#9)', 0, 0, 9);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#9)', 0, 0, 9);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 (Q#9)', 0, 0, 9);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 (Q#10)*', 100, 1, 10);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 (Q#10)', 0, 0, 10);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 (Q#10)', 0, 0, 10);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 (Q#10)', 0, 0, 10);



