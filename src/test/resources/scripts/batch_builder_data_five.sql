insert into scheme(name, is_active, strategy_id, settings_id, mode_id, grading_id, course_id, created, created_by, belongs_to, is_deleted, access_id)
    values('Sample scheme #1', 1, 1, 1, 1, 1, 1, '2019-08-01 10:30:15.999999999', 1, 1, 0, 1);

insert into theme (name, course_id, created_by, access_id) values('Sample theme #1 (All MCQ)', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Sample theme #2 (All FBSQ)', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Sample theme #3 (All FBMQ)', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Sample theme #4 (All MQ)', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Sample theme #5 (All SQ)', 1, 1, 1);

insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 1, 0);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 2, 1);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 3, 2);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 4, 3);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 5, 4);

insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 1, 1, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(2, 2, 1, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(3, 3, 1, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(4, 4, 1, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(5, 5, 1, 0, 0);


/*Questions Theme #1 (All MCQ) 1 pieces, full equipment*/

insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #1 T#1', 1, 1, 1, 1);

insert into help(name, text, staff_id) values('Help MCQ#1 T#1', 'Please, refer to section #1 MCQ#1 T#1', 1);
insert into question_help(question_id, help_id) values(1,1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#01-MCQ#1-T#1.jpg', 'Resource #1 MCQ#1 T#1', 1);
insert into question_resource(question_id, resource_id) values(1, 1);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 Q#1 T#1 Correct (100!)', 100, 1, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 Q#1 T#1', 0, 0, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 Q#1 T#1', 0, 0, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 Q#1 T#1', 0, 0, 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#11-Answer#1-MCQ#1-T#1.jpg', 'Resource #11 Answer#1 MCQ#1 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(1, 2);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#12-Answer#2-MCQ#1-T#1.jpg', 'Resource #12 Answer#2 MCQ#1 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(2, 3);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#13-Answer#3-MCQ#1-T#1.jpg', 'Resource #13 Answer#3 MCQ#1 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(3, 4);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#14-Answer#4-MCQ#1-T#1.jpg', 'Resource #14 Answer#4 MCQ#1 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(4, 5);

/*Questions Theme #2 (All FBSQ) 1 pieces, full equipment*/

insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #1 T#2 (phrase = wolf)', 1, 2, 2, 1);

insert into help(name, text, staff_id) values('Help FBSQ#1 T#2', 'Please, refer to section #1 FBSQ#1 T#2', 1);
insert into question_help(question_id, help_id) values(2,2);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#1-FBSQ#1-T#2.jpg', 'Resource #1 FBSQ#1 T#2', 1);
insert into question_resource(question_id, resource_id) values(2, 6);

insert into answer_fbsq(answer_id, set_id) values(2, 1);

insert into phrase(phrase, staff_id, last_used) values('wolf', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(1, 2);
insert into phrase(phrase, staff_id, last_used) values('wolf+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(2, 2);
insert into phrase(phrase, staff_id, last_used) values('wolf-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(3, 2);


/*Questions Theme #3 (All FBMQ) 1 pieces, full equipment*/

insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #1 T#3 (phrases = {tree, grass, bush})', 1, 3, 3, 1);

insert into help(name, text, staff_id) values('Help FBMQ#1 T#3', 'Please, refer to section #1 FBMQ#1 T#3', 1);
insert into question_help(question_id, help_id) values(3,3);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#1-FBMQ#1-T#3.jpg', 'Resource #1 FBMQ#1 T#3', 1);
insert into question_resource(question_id, resource_id) values(3, 7);

insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('tree', 1, 1, 3);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('grass', 1, 1, 3);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('bush', 1, 1, 3);

insert into phrase(phrase, staff_id, last_used) values('tree-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(4, 1);
insert into phrase(phrase, staff_id, last_used) values('tree+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(5, 1);

insert into phrase(phrase, staff_id, last_used) values('grass-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(6, 2);
insert into phrase(phrase, staff_id, last_used) values('grass+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(7, 2);

insert into phrase(phrase, staff_id, last_used) values('bush-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(8, 3);
insert into phrase(phrase, staff_id, last_used) values('bush+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(9, 3);


/*Questions Theme #4 (All MQ) 10 pieces, full equipment*/

insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #1 T#4', 1, 4, 4, 1);

insert into help(name, text, staff_id) values('Help MQ#1 T#4', 'Please, refer to section #1 MQ#1 T#4', 1);
insert into question_help(question_id, help_id) values(4, 4);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#1-MQ#1-T#4.jpg', 'Resource #1 MQ#1 T#4', 1);
insert into question_resource(question_id, resource_id) values(4, 8);

/* Q#1 answers*/

insert into phrase(phrase, staff_id) values('white', 1);
insert into phrase(phrase, staff_id) values('black', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#81-Answer#1-MQ#1-T#4.jpg', 'Resource #81 Answer#1 MQ#1 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(11, 9);

insert into phrase(phrase, staff_id) values('heaven', 1);
insert into phrase(phrase, staff_id) values('earth', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#82-Answer#2-MQ#1-T#4.jpg', 'Resource #82 Answer#2 MQ#1 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(13, 10);

insert into phrase(phrase, staff_id) values('sea', 1);
insert into phrase(phrase, staff_id) values('ground', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#83-Answer#3-MQ#1-T#4.jpg', 'Resource #83 Answer#3 MQ#1 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(15, 11);

insert into phrase(phrase, staff_id) values('air', 1);
insert into phrase(phrase, staff_id) values('fire', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#84-Answer#4-MQ#1-T#4.jpg', 'Resource #84 Answer#4 MQ#1 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(17, 12);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(10, 11, 4);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(12, 13, 4);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(14, 15, 4);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(16, 17, 4);


/*Questions Theme #5 (All SQ) 10 pieces, full equipment*/

insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #1 T#5', 1, 5, 5, 1);

insert into help(name, text, staff_id) values('Help SQ#1 T#5', 'Please, refer to section #1 SQ#1 T#5', 1);
insert into question_help(question_id, help_id) values(5,5);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#1-SQ#1-T#5.jpg', 'Resource #1 SQ#1 T#5', 1);
insert into question_resource(question_id, resource_id) values(5, 13);


/* Q#1 answers*/
insert into phrase(phrase, staff_id) values('Monday', 1);
insert into phrase(phrase, staff_id) values('Tuesday', 1);
insert into phrase(phrase, staff_id) values('Wednesday', 1);
insert into phrase(phrase, staff_id) values('Thursday', 1);
insert into phrase(phrase, staff_id) values('Friday', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#1-Answer#1-SQ#1-T#5.jpg', 'Resource #1 Answer#1 SQ#1 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#2-Answer#2-SQ#1-T#5.jpg', 'Resource #2 Answer#2 SQ#1 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#3-Answer#3-SQ#1-T#5.jpg', 'Resource #3 Answer#3 SQ#1 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#4-Answer#4-SQ#1-T#5.jpg', 'Resource #4 Answer#4 SQ#1 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#5-Answer#5-SQ#1-T#5.jpg', 'Resource #5 Answer#5 SQ#1 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(18, 14);
insert into phrase_resource(phrase_id, resource_id) values(19, 15);
insert into phrase_resource(phrase_id, resource_id) values(20, 16);
insert into phrase_resource(phrase_id, resource_id) values(21, 17);
insert into phrase_resource(phrase_id, resource_id) values(22, 18);

insert into answer_sq(phrase_id, phrase_order, question_id) values(18, 0, 5);
insert into answer_sq(phrase_id, phrase_order, question_id) values(19, 1, 5);
insert into answer_sq(phrase_id, phrase_order, question_id) values(20, 2, 5);
insert into answer_sq(phrase_id, phrase_order, question_id) values(21, 3, 5);
insert into answer_sq(phrase_id, phrase_order, question_id) values(22, 4, 5);
