insert into scheme(name, is_active, strategy_id, settings_id, mode_id, grading_id, course_id, created, created_by, belongs_to, is_deleted, access_id, options_id)
    values('Sample scheme #1', 1, 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 1);

insert into scheme_four_point(scheme_id, four_point_id) values(1, 1);

insert into theme (name, course_id, created_by, access_id, belongs_to) values('Sample theme #1 (All MCQ)', 1, 1, 1, 1);
insert into theme (name, course_id, created_by, access_id, belongs_to) values('Sample theme #2 (All FBSQ)', 1, 1, 1, 1);
insert into theme (name, course_id, created_by, access_id, belongs_to) values('Sample theme #3 (All FBMQ)', 1, 1, 1, 1);
insert into theme (name, course_id, created_by, access_id, belongs_to) values('Sample theme #4 (All MQ)', 1, 1, 1, 1);
insert into theme (name, course_id, created_by, access_id, belongs_to) values('Sample theme #5 (All SQ)', 1, 1, 1, 1);

insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 1, 0);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 2, 1);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 3, 2);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 4, 3);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 5, 4);

insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 1, 5, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(2, 2, 5, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(3, 3, 5, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(4, 4, 5, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(5, 5, 5, 0, 0);


/*Questions Theme #1 (All MCQ) 10 pieces, full equipment*/

insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #1 T#1', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #2 T#1', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #3 T#1', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #4 T#1', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #5 T#1', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #6 T#1', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #7 T#1', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #8 T#1', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #9 T#1', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #10 T#1', 1, 1, 1, 1);

insert into help(name, text, staff_id) values('Help #1 MCQ#1 T#1', 'Please, refer to section #1 MCQ#1 T#1', 1);
insert into question_help(question_id, help_id) values(1,1);
insert into help(name, text, staff_id) values('Help #2 MCQ#2 T#1', 'Please, refer to section #2 MCQ#2 T#1', 1);
insert into question_help(question_id, help_id) values(2,2);
insert into help(name, text, staff_id) values('Help #3 MCQ#3 T#1', 'Please, refer to section #3 MCQ#3 T#1', 1);
insert into question_help(question_id, help_id) values(3,3);
insert into help(name, text, staff_id) values('Help #4 MCQ#4 T#1', 'Please, refer to section #4 MCQ#4 T#1', 1);
insert into question_help(question_id, help_id) values(4,4);
insert into help(name, text, staff_id) values('Help #5 MCQ#5 T#1', 'Please, refer to section #5 MCQ#5 T#1', 1);
insert into question_help(question_id, help_id) values(5,5);
insert into help(name, text, staff_id) values('Help #6 MCQ#6 T#1', 'Please, refer to section #6 MCQ#6 T#1', 1);
insert into question_help(question_id, help_id) values(6,6);
insert into help(name, text, staff_id) values('Help #7 MCQ#7 T#1', 'Please, refer to section #7 MCQ#7 T#1', 1);
insert into question_help(question_id, help_id) values(7,7);
insert into help(name, text, staff_id) values('Help #8 MCQ#8 T#1', 'Please, refer to section #8 MCQ#8 T#1', 1);
insert into question_help(question_id, help_id) values(8,8);
insert into help(name, text, staff_id) values('Help #9 MCQ#9 T#1', 'Please, refer to section #9 MCQ#9 T#1', 1);
insert into question_help(question_id, help_id) values(9,9);
insert into help(name, text, staff_id) values('Help #10 MCQ#10 T#1', 'Please, refer to section #10 MCQ#10 T#1', 1);
insert into question_help(question_id, help_id) values(10,10);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#01-MCQ#1-T#1.jpg', 'Resource #1 MCQ#1 T#1', 1);
insert into question_resource(question_id, resource_id) values(1, 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#02-MCQ#2-T#1.jpg', 'Resource #2 MCQ#2 T#1', 1);
insert into question_resource(question_id, resource_id) values(2, 2);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#03-MCQ#3-T#1.jpg', 'Resource #3 MCQ#3 T#1', 1);
insert into question_resource(question_id, resource_id) values(3, 3);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#04-MCQ#4-T#1.jpg', 'Resource #4 MCQ#4 T#1', 1);
insert into question_resource(question_id, resource_id) values(4, 4);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#05-MCQ#5-T#1.jpg', 'Resource #5 MCQ#5 T#1', 1);
insert into question_resource(question_id, resource_id) values(5, 5);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#06-MCQ#6-T#1.jpg', 'Resource #6 MCQ#6 T#1', 1);
insert into question_resource(question_id, resource_id) values(6, 6);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#07-MCQ#7-T#1.jpg', 'Resource #7 MCQ#7 T#1', 1);
insert into question_resource(question_id, resource_id) values(7, 7);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#08-MCQ#8-T#1.jpg', 'Resource #8 MCQ#8 T#1', 1);
insert into question_resource(question_id, resource_id) values(8, 8);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#09-MCQ#9-T#1.jpg', 'Resource #9 MCQ#9 T#1', 1);
insert into question_resource(question_id, resource_id) values(9, 9);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#10-MCQ#10-T#1.jpg', 'Resource #10 MCQ#10 T#1', 1);
insert into question_resource(question_id, resource_id) values(10, 10);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 Q#1 T#1 Correct (100!)', 100, 1, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 Q#1 T#1', 0, 0, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 Q#1 T#1', 0, 0, 1);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 Q#1 T#1', 0, 0, 1);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 Q#2 T#1 Correct (100!)', 100, 1, 2);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 Q#2 T#1', 0, 0, 2);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 Q#2 T#1', 0, 0, 2);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 Q#2 T#1', 0, 0, 2);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 Q#3 T#1 Correct (50!)', 50, 1, 3);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 Q#3 T#1 Correct (50!)', 50, 1, 3);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 Q#3 T#1', 0, 0, 3);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 Q#3 T#1', 0, 0, 3);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 Q#4 T#1 Correct (100!)', 100, 1, 4);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 Q#4 T#1', 0, 0, 4);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 Q#4 T#1', 0, 0, 4);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 Q#4 T#1', 0, 0, 4);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 Q#5 T#1 Correct (100!)', 100, 1, 5);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 Q#5 T#1', 0, 0, 5);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 Q#5 T#1', 0, 0, 5);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 Q#5 T#1', 0, 0, 5);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 Q#6 T#1 Correct (33!)', 33, 1, 6);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 Q#6 T#1 Correct (33!)', 33, 1, 6);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 Q#6 T#1 Correct (34)', 34, 0, 6);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 Q#6 T#1', 0, 0, 6);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 Q#7 T#1 Correct (50)', 50, 0, 7);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 Q#7 T#1 Correct (50)', 50, 0, 7);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 Q#7 T#1', 0, 0, 7);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 Q#7 T#1', 0, 0, 7);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 Q#8 T#1 Correct (33)', 33, 0, 8);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 Q#8 T#1 Correct (33)', 33, 0, 8);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 Q#8 T#1 Correct (34)', 34, 0, 8);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 Q#8 T#1', 0, 0, 8);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 Q#9 T#1 Correct (100!)', 100, 1, 9);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 Q#9 T#1', 0, 0, 9);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 Q#9 T#1', 0, 0, 9);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 Q#9 T#1', 0, 0, 9);

insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #1 Q#10 T#1 Correct (100!)', 100, 1, 10);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #2 Q#10 T#1', 0, 0, 10);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #3 Q#10 T#1', 0, 0, 10);
insert into answer_mcq (answer, percent, is_required, question_id) values('Answer #4 Q#10 T#1', 0, 0, 10);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#11-Answer#1-MCQ#1-T#1.jpg', 'Resource #11 Answer#1 MCQ#1 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(1, 11);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#12-Answer#2-MCQ#1-T#1.jpg', 'Resource #12 Answer#2 MCQ#1 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(2, 12);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#13-Answer#3-MCQ#1-T#1.jpg', 'Resource #13 Answer#3 MCQ#1 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(3, 13);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#14-Answer#4-MCQ#1-T#1.jpg', 'Resource #14 Answer#4 MCQ#1 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(4, 14);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#15-Answer#1-MCQ#2-T#1.jpg', 'Resource #15 Answer#1 MCQ#2 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(5, 15);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#16-Answer#2-MCQ#2-T#1.jpg', 'Resource #16 Answer#2 MCQ#2 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(6, 16);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#17-Answer#3-MCQ#2-T#1.jpg', 'Resource #17 Answer#3 MCQ#2 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(7, 17);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#18-Answer#4-MCQ#2-T#1.jpg', 'Resource #18 Answer#4 MCQ#2 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(8, 18);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#19-Answer#1-MCQ#3-T#1.jpg', 'Resource #19 Answer#1 MCQ#3 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(9, 19);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#20-Answer#2-MCQ#3-T#1.jpg', 'Resource #20 Answer#2 MCQ#3 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(10, 20);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#21-Answer#3-MCQ#3-T#1.jpg', 'Resource #20 Answer#3 MCQ#3 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(11, 21);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#22-Answer#4-MCQ#3-T#1.jpg', 'Resource #22 Answer#4 MCQ#3 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(12, 22);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#23-Answer#1-MCQ#4-T#1.jpg', 'Resource #23 Answer#1 MCQ#4 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(13, 23);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#24-Answer#2-MCQ#4-T#1.jpg', 'Resource #24 Answer#2 MCQ#4 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(14, 24);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#25-Answer#3-MCQ#4-T#1.jpg', 'Resource #24 Answer#3 MCQ#4 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(15, 25);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#26-Answer#4-MCQ#4-T#1.jpg', 'Resource #26 Answer#4 MCQ#4 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(16, 26);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#27-Answer#1-MCQ#5-T#1.jpg', 'Resource #27 Answer#1 MCQ#5 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(17, 27);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#28-Answer#2-MCQ#5-T#1.jpg', 'Resource #28 Answer#2 MCQ#5 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(18, 28);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#29-Answer#3-MCQ#5-T#1.jpg', 'Resource #29 Answer#3 MCQ#5 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(19, 29);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#30-Answer#4-MCQ#5-T#1.jpg', 'Resource #30 Answer#4 MCQ#5 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(20, 30);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#31-Answer#1-MCQ#6-T#1.jpg', 'Resource #31 Answer#1 MCQ#6 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(21, 31);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#31-Answer#2-MCQ#6-T#1.jpg', 'Resource #32 Answer#2 MCQ#6 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(22, 32);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#33-Answer#3-MCQ#6-T#1.jpg', 'Resource #33 Answer#3 MCQ#6 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(23, 33);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#34-Answer#4-MCQ#6-T#1.jpg', 'Resource #34 Answer#4 MCQ#6 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(24, 34);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#35-Answer#1-MCQ#7-T#1.jpg', 'Resource #35 Answer#1 MCQ#7 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(25, 35);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#36-Answer#2-MCQ#7-T#1.jpg', 'Resource #36 Answer#2 MCQ#7 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(26, 36);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#37-Answer#3-MCQ#7-T#1.jpg', 'Resource #37 Answer#3 MCQ#7 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(27, 37);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#38-Answer#4-MCQ#7-T#1.jpg', 'Resource #38 Answer#4 MCQ#7 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(28, 38);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#39-Answer#1-MCQ#8-T#1.jpg', 'Resource #39 Answer#1 MCQ#8 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(29, 39);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#40-Answer#2-MCQ#8-T#1.jpg', 'Resource #40 Answer#2 MCQ#8 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(30, 40);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#41-Answer#3-MCQ#8-T#1.jpg', 'Resource #41 Answer#3 MCQ#8 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(31, 41);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#42-Answer#4-MCQ#8-T#1.jpg', 'Resource #42 Answer#4 MCQ#8 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(32, 42);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#43-Answer#1-MCQ#9-T#1.jpg', 'Resource #43 Answer#1 MCQ#9 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(33, 43);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#44-Answer#2-MCQ#9-T#1.jpg', 'Resource #44 Answer#2 MCQ#9 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(34, 44);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#45-Answer#3-MCQ#9-T#1.jpg', 'Resource #45 Answer#3 MCQ#9 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(35, 45);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#46-Answer#4-MCQ#9-T#1.jpg', 'Resource #46 Answer#4 MCQ#9 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(36, 46);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#47-Answer#1-MCQ#10-T#1.jpg', 'Resource #47 Answer#1 MCQ#10 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(37, 47);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#48-Answer#2-MCQ#10-T#1.jpg', 'Resource #48 Answer#2 MCQ#10 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(38, 48);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#49-Answer#3-MCQ#10-T#1.jpg', 'Resource #49 Answer#3 MCQ#10 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(39, 49);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#50-Answer#4-MCQ#10-T#1.jpg', 'Resource #50 Answer#4 MCQ#10 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(40, 50);


/*Questions Theme #2 (All FBSQ) 10 pieces, full equipment*/

insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #1 T#2 (phrase = wolf)', 1, 2, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #2 T#2 (phrase = bear)', 1, 2, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #3 T#2 (phrase = duck)', 1, 2, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #4 T#2 (phrase = cow)', 1, 2, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #5 T#2 (phrase = dog)', 1, 2, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #6 T#2 (phrase = lion)', 1, 2, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #7 T#2 (phrase = hypo)', 1, 2, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #8 T#2 (phrase = tiger)', 1, 2, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #9 T#2 (phrase = cat)', 1, 2, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #10 T#2 (phrase = fox)', 1, 2, 2, 1);

insert into help(name, text, staff_id) values('Help #11 FBSQ#1 T#2', 'Please, refer to section #1 FBSQ#1 T#2', 1);
insert into question_help(question_id, help_id) values(11,11);
insert into help(name, text, staff_id) values('Help #12 FBSQ#2 T#2', 'Please, refer to section #2 FBSQ#2 T#2', 1);
insert into question_help(question_id, help_id) values(12,12);
insert into help(name, text, staff_id) values('Help #13 FBSQ#3 T#2', 'Please, refer to section #3 FBSQ#3 T#2', 1);
insert into question_help(question_id, help_id) values(13,13);
insert into help(name, text, staff_id) values('Help #14 FBSQ#4 T#2', 'Please, refer to section #4 FBSQ#4 T#2', 1);
insert into question_help(question_id, help_id) values(14,14);
insert into help(name, text, staff_id) values('Help #15 FBSQ#5 T#2', 'Please, refer to section #5 FBSQ#5 T#2', 1);
insert into question_help(question_id, help_id) values(15,15);
insert into help(name, text, staff_id) values('Help #16 FBSQ#6 T#2', 'Please, refer to section #6 FBSQ#6 T#2', 1);
insert into question_help(question_id, help_id) values(16,16);
insert into help(name, text, staff_id) values('Help #17 FBSQ#7 T#2', 'Please, refer to section #7 FBSQ#7 T#2', 1);
insert into question_help(question_id, help_id) values(17,17);
insert into help(name, text, staff_id) values('Help #18 FBSQ#8 T#2', 'Please, refer to section #8 FBSQ#8 T#2', 1);
insert into question_help(question_id, help_id) values(18,18);
insert into help(name, text, staff_id) values('Help #19 FBSQ#9 T#2', 'Please, refer to section #9 FBSQ#9 T#2', 1);
insert into question_help(question_id, help_id) values(19,19);
insert into help(name, text, staff_id) values('Help #20 FBSQ#100 T#2', 'Please, refer to section #10 FBSQ#10 T#2', 1);
insert into question_help(question_id, help_id) values(20,20);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#51-FBSQ#1-T#2.jpg', 'Resource #51 FBSQ#1 T#2', 1);
insert into question_resource(question_id, resource_id) values(11, 51);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#52-FBSQ#2-T#2.jpg', 'Resource #51 FBSQ#2 T#2', 1);
insert into question_resource(question_id, resource_id) values(12, 52);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#53-FBSQ#3-T#2.jpg', 'Resource #51 FBSQ#3 T#2', 1);
insert into question_resource(question_id, resource_id) values(13, 53);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#54-FBSQ#4-T#2.jpg', 'Resource #51 FBSQ#4 T#2', 1);
insert into question_resource(question_id, resource_id) values(14, 54);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#55-FBSQ#5-T#2.jpg', 'Resource #51 FBSQ#5 T#2', 1);
insert into question_resource(question_id, resource_id) values(15, 55);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#56-FBSQ#6-T#2.jpg', 'Resource #51 FBSQ#6 T#2', 1);
insert into question_resource(question_id, resource_id) values(16, 56);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#57-FBSQ#7-T#2.jpg', 'Resource #51 FBSQ#7 T#2', 1);
insert into question_resource(question_id, resource_id) values(17, 57);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#58-FBSQ#8-T#2.jpg', 'Resource #51 FBSQ#8 T#2', 1);
insert into question_resource(question_id, resource_id) values(18, 58);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#59-FBSQ#9-T#2.jpg', 'Resource #51 FBSQ#9 T#2', 1);
insert into question_resource(question_id, resource_id) values(19, 59);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#60-FBSQ#10-T#2.jpg', 'Resource #51 FBSQ#10 T#2', 1);
insert into question_resource(question_id, resource_id) values(20, 60);

insert into answer_fbsq(answer_id, set_id) values(11, 1);
insert into answer_fbsq(answer_id, set_id) values(12, 1);
insert into answer_fbsq(answer_id, set_id) values(13, 1);
insert into answer_fbsq(answer_id, set_id) values(14, 1);
insert into answer_fbsq(answer_id, set_id) values(15, 1);
insert into answer_fbsq(answer_id, set_id) values(16, 1);
insert into answer_fbsq(answer_id, set_id) values(17, 1);
insert into answer_fbsq(answer_id, set_id) values(18, 1);
insert into answer_fbsq(answer_id, set_id) values(19, 1);
insert into answer_fbsq(answer_id, set_id) values(20, 1);

insert into phrase(phrase, staff_id, last_used) values('wolf', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(1, 11);
insert into phrase(phrase, staff_id, last_used) values('wolf+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(2, 11);
insert into phrase(phrase, staff_id, last_used) values('wolf-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(3, 11);

insert into phrase(phrase, staff_id, last_used) values('bear', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(4, 12);
insert into phrase(phrase, staff_id, last_used) values('bear+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(5, 12);
insert into phrase(phrase, staff_id, last_used) values('bear-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(6, 12);

insert into phrase(phrase, staff_id, last_used) values('duck', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(7, 13);
insert into phrase(phrase, staff_id, last_used) values('duck+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(8, 13);
insert into phrase(phrase, staff_id, last_used) values('duck-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(9, 13);

insert into phrase(phrase, staff_id, last_used) values('cow', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(10, 14);
insert into phrase(phrase, staff_id, last_used) values('cow+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(11, 14);
insert into phrase(phrase, staff_id, last_used) values('cow-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(12, 14);

insert into phrase(phrase, staff_id, last_used) values('dog', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(13, 15);
insert into phrase(phrase, staff_id, last_used) values('dog+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(14, 15);
insert into phrase(phrase, staff_id, last_used) values('dog-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(15, 15);

insert into phrase(phrase, staff_id, last_used) values('lion', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(16, 16);
insert into phrase(phrase, staff_id, last_used) values('lion+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(17, 16);
insert into phrase(phrase, staff_id, last_used) values('lion-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(18, 16);

insert into phrase(phrase, staff_id, last_used) values('hypo', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(19, 17);
insert into phrase(phrase, staff_id, last_used) values('hypo+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(20, 17);
insert into phrase(phrase, staff_id, last_used) values('hypo-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(21, 17);

insert into phrase(phrase, staff_id, last_used) values('tiger', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(22, 18);
insert into phrase(phrase, staff_id, last_used) values('tiger+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(23, 18);
insert into phrase(phrase, staff_id, last_used) values('tiger-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(24, 18);

insert into phrase(phrase, staff_id, last_used) values('cat', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(25, 19);
insert into phrase(phrase, staff_id, last_used) values('cat+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(26, 19);
insert into phrase(phrase, staff_id, last_used) values('cat-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(27, 19);

insert into phrase(phrase, staff_id, last_used) values('fox', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(28, 20);
insert into phrase(phrase, staff_id, last_used) values('fox+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(29, 20);
insert into phrase(phrase, staff_id, last_used) values('fox-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(30, 20);


/*Questions Theme #3 (All FBMQ) 10 pieces, full equipment*/

insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #1 T#3 (phrases = {tree, grass, bush})', 1, 3, 3, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #2 T#3 (phrases = {sky, wind, rain})', 1, 3, 3, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #3 T#3 (phrases = {water, sun, sand})', 1, 3, 3, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #4 T#3 (phrases = {train, bus, plane})', 1, 3, 3, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #5 T#3 (phrases = {gas, fire, ice})', 1, 3, 3, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #6 T#3 (phrases = {fish, meat, lard})', 1, 3, 3, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #7 T#3 (phrases = {chair, table, stool})', 1, 3, 3, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #8 T#3 (phrases = {candy, sweets, chocolate})', 1, 3, 3, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #9 T#3 (phrases = {bottle, glass, jar})', 1, 3, 3, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #10 T#3 (phrases = {heart, soul, love})', 1, 3, 3, 1);

insert into help(name, text, staff_id) values('Help #21 FBMQ#1 T#3', 'Please, refer to section #1 FBMQ#1 T#3', 1);
insert into question_help(question_id, help_id) values(21,21);
insert into help(name, text, staff_id) values('Help #22 FBMQ#2 T#3', 'Please, refer to section #2 FBMQ#2 T#3', 1);
insert into question_help(question_id, help_id) values(22,22);
insert into help(name, text, staff_id) values('Help #23 FBMQ#3 T#3', 'Please, refer to section #3 FBMQ#3 T#3', 1);
insert into question_help(question_id, help_id) values(23,23);
insert into help(name, text, staff_id) values('Help #24 FBMQ#4 T#3', 'Please, refer to section #4 FBMQ#4 T#3', 1);
insert into question_help(question_id, help_id) values(24,24);
insert into help(name, text, staff_id) values('Help #25 FBMQ#5 T#3', 'Please, refer to section #5 FBMQ#5 T#3', 1);
insert into question_help(question_id, help_id) values(25,25);
insert into help(name, text, staff_id) values('Help #26 FBMQ#6 T#3', 'Please, refer to section #6 FBMQ#6 T#3', 1);
insert into question_help(question_id, help_id) values(26,26);
insert into help(name, text, staff_id) values('Help #27 FBMQ#7 T#3', 'Please, refer to section #7 FBMQ#7 T#3', 1);
insert into question_help(question_id, help_id) values(27,27);
insert into help(name, text, staff_id) values('Help #28 FBMQ#8 T#3', 'Please, refer to section #8 FBMQ#8 T#3', 1);
insert into question_help(question_id, help_id) values(28,28);
insert into help(name, text, staff_id) values('Help #29 FBMQ#9 T#3', 'Please, refer to section #9 FBMQ#9 T#3', 1);
insert into question_help(question_id, help_id) values(29,29);
insert into help(name, text, staff_id) values('Help #30 FBMQ#10 T#3', 'Please, refer to section #10 FBMQ#10 T#3', 1);
insert into question_help(question_id, help_id) values(30, 30);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#61-FBMQ#1-T#3.jpg', 'Resource #61 FBMQ#1 T#3', 1);
insert into question_resource(question_id, resource_id) values(21, 61);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#62-FBMQ#2-T#3.jpg', 'Resource #62 FBMQ#2 T#3', 1);
insert into question_resource(question_id, resource_id) values(22, 62);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#63-FBMQ#3-T#3.jpg', 'Resource #63 FBMQ#3 T#3', 1);
insert into question_resource(question_id, resource_id) values(23, 63);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#64-FBMQ#4-T#3.jpg', 'Resource #64 FBMQ#4 T#3', 1);
insert into question_resource(question_id, resource_id) values(24, 64);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#65-FBMQ#5-T#3.jpg', 'Resource #65 FBMQ#5 T#3', 1);
insert into question_resource(question_id, resource_id) values(25, 65);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#66-FBMQ#6-T#3.jpg', 'Resource #66 FBMQ#6 T#3', 1);
insert into question_resource(question_id, resource_id) values(26, 66);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#67-FBMQ#7-T#3.jpg', 'Resource #67 FBMQ#7 T#3', 1);
insert into question_resource(question_id, resource_id) values(27, 67);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#68-FBMQ#8-T#3.jpg', 'Resource #68 FBMQ#8 T#3', 1);
insert into question_resource(question_id, resource_id) values(28, 68);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#69-FBMQ#9-T#3.jpg', 'Resource #69 FBMQ#9 T#3', 1);
insert into question_resource(question_id, resource_id) values(29, 69);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#70-FBMQ#10-T#3.jpg', 'Resource #70 FBMQ#10 T#3', 1);
insert into question_resource(question_id, resource_id) values(30, 70);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('tree', 1, 1, 21);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('grass', 1, 1, 21);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('bush', 1, 1, 21);

insert into phrase(phrase, staff_id, last_used) values('tree-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(31, 1);
insert into phrase(phrase, staff_id, last_used) values('tree+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(32, 1);

insert into phrase(phrase, staff_id, last_used) values('grass-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(33, 2);
insert into phrase(phrase, staff_id, last_used) values('grass+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(34, 2);

insert into phrase(phrase, staff_id, last_used) values('bush-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(35, 3);
insert into phrase(phrase, staff_id, last_used) values('bush+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(36, 3);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('sky', 1, 1, 22);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('wind', 1, 1, 22);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('rain', 1, 1, 22);

insert into phrase(phrase, staff_id, last_used) values('sky-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(37, 4);
insert into phrase(phrase, staff_id, last_used) values('sky+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(38, 4);

insert into phrase(phrase, staff_id, last_used) values('wind-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(39, 5);
insert into phrase(phrase, staff_id, last_used) values('wind+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(40, 5);

insert into phrase(phrase, staff_id, last_used) values('rain-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(41, 6);
insert into phrase(phrase, staff_id, last_used) values('rain+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(42, 6);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('water', 1, 1, 23);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('sun', 1, 1, 23);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('sand', 1, 1, 23);

insert into phrase(phrase, staff_id, last_used) values('water-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(43, 7);
insert into phrase(phrase, staff_id, last_used) values('water+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(44, 7);

insert into phrase(phrase, staff_id, last_used) values('sun-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(45, 8);
insert into phrase(phrase, staff_id, last_used) values('sun+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(46, 8);

insert into phrase(phrase, staff_id, last_used) values('sand-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(47, 9);
insert into phrase(phrase, staff_id, last_used) values('sand+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(48, 9);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('train', 1, 1, 24);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('bus', 1, 1, 24);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('plane', 1, 1, 24);

insert into phrase(phrase, staff_id, last_used) values('train-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(49, 10);
insert into phrase(phrase, staff_id, last_used) values('train+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(50, 10);

insert into phrase(phrase, staff_id, last_used) values('bus-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(51, 11);
insert into phrase(phrase, staff_id, last_used) values('bus+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(52, 11);

insert into phrase(phrase, staff_id, last_used) values('plane-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(53, 12);
insert into phrase(phrase, staff_id, last_used) values('plane+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(54, 12);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('gas', 1, 1, 25);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('fire', 1, 1, 25);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('ice', 1, 1, 25);

insert into phrase(phrase, staff_id, last_used) values('gas-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(55, 13);
insert into phrase(phrase, staff_id, last_used) values('gas+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(56, 13);

insert into phrase(phrase, staff_id, last_used) values('fire-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(57, 14);
insert into phrase(phrase, staff_id, last_used) values('fire+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(58, 14);

insert into phrase(phrase, staff_id, last_used) values('ice-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(59, 15);
insert into phrase(phrase, staff_id, last_used) values('ice+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(60, 15);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('fish', 1, 1, 26);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('meat', 1, 1, 26);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('lard', 1, 1, 26);

insert into phrase(phrase, staff_id, last_used) values('fish-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(61, 16);
insert into phrase(phrase, staff_id, last_used) values('fish+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(62, 16);

insert into phrase(phrase, staff_id, last_used) values('meat-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(63, 17);
insert into phrase(phrase, staff_id, last_used) values('meat+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(64, 17);

insert into phrase(phrase, staff_id, last_used) values('lard-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(65, 18);
insert into phrase(phrase, staff_id, last_used) values('lard+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(66, 18);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('chair', 1, 1, 27);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('table', 1, 1, 27);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('stool', 1, 1, 27);

insert into phrase(phrase, staff_id, last_used) values('chair-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(67, 19);
insert into phrase(phrase, staff_id, last_used) values('chair+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(68, 19);

insert into phrase(phrase, staff_id, last_used) values('table-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(69, 20);
insert into phrase(phrase, staff_id, last_used) values('table+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(70, 20);

insert into phrase(phrase, staff_id, last_used) values('stool-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(71, 21);
insert into phrase(phrase, staff_id, last_used) values('stool+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(72, 21);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('candy', 1, 1, 28);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('sweets', 1, 1, 28);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('chocolate', 1, 1, 28);

insert into phrase(phrase, staff_id, last_used) values('candy-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(73, 22);
insert into phrase(phrase, staff_id, last_used) values('candy+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(74, 22);

insert into phrase(phrase, staff_id, last_used) values('sweets-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(75, 23);
insert into phrase(phrase, staff_id, last_used) values('sweets+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(76, 23);

insert into phrase(phrase, staff_id, last_used) values('chocolate-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(77, 24);
insert into phrase(phrase, staff_id, last_used) values('chocolate+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(78, 24);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('bottle', 1, 1, 29);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('glass', 1, 1, 29);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('jar', 1, 1, 29);

insert into phrase(phrase, staff_id, last_used) values('bottle-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(79, 25);
insert into phrase(phrase, staff_id, last_used) values('bottle+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(80, 25);

insert into phrase(phrase, staff_id, last_used) values('glass-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(81, 26);
insert into phrase(phrase, staff_id, last_used) values('glass+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(82, 26);

insert into phrase(phrase, staff_id, last_used) values('jar-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(83, 27);
insert into phrase(phrase, staff_id, last_used) values('jar+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(84, 27);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('heart', 1, 1, 30);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('soul', 1, 1, 30);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('love', 1, 1, 30);

insert into phrase(phrase, staff_id, last_used) values('heart-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(85, 28);
insert into phrase(phrase, staff_id, last_used) values('heart+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(86, 28);

insert into phrase(phrase, staff_id, last_used) values('soul-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(87, 29);
insert into phrase(phrase, staff_id, last_used) values('soul+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(88, 29);

insert into phrase(phrase, staff_id, last_used) values('love-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(89, 30);
insert into phrase(phrase, staff_id, last_used) values('love+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(90, 30);


/*Questions Theme #4 (All MQ) 10 pieces, full equipment*/

insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #1 T#4', 1, 4, 4, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #2 T#4', 1, 4, 4, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #3 T#4', 1, 4, 4, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #4 T#4', 1, 4, 4, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #5 T#4', 1, 4, 4, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #6 T#4', 1, 4, 4, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #7 T#4', 1, 4, 4, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #8 T#4', 1, 4, 4, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #9 T#4', 1, 4, 4, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #10 T#4', 1, 4, 4, 1);

insert into help(name, text, staff_id) values('Help #31 MQ#1 T#4', 'Please, refer to section #1 MQ#1 T#4', 1);
insert into question_help(question_id, help_id) values(31,31);
insert into help(name, text, staff_id) values('Help #32 MQ#2 T#4', 'Please, refer to section #2 MQ#2 T#4', 1);
insert into question_help(question_id, help_id) values(32,32);
insert into help(name, text, staff_id) values('Help #33 MQ#3 T#4', 'Please, refer to section #3 MQ#3 T#4', 1);
insert into question_help(question_id, help_id) values(33,33);
insert into help(name, text, staff_id) values('Help #34 MQ#4 T#4', 'Please, refer to section #4 MQ#4 T#4', 1);
insert into question_help(question_id, help_id) values(34,34);
insert into help(name, text, staff_id) values('Help #35 MQ#5 T#4', 'Please, refer to section #5 MQ#5 T#4', 1);
insert into question_help(question_id, help_id) values(35,35);
insert into help(name, text, staff_id) values('Help #36 MQ#6 T#4', 'Please, refer to section #6 MQ#6 T#4', 1);
insert into question_help(question_id, help_id) values(36,36);
insert into help(name, text, staff_id) values('Help #37 MQ#7 T#4', 'Please, refer to section #7 MQ#7 T#4', 1);
insert into question_help(question_id, help_id) values(37,37);
insert into help(name, text, staff_id) values('Help #38 MQ#8 T#4', 'Please, refer to section #8 MQ#8 T#4', 1);
insert into question_help(question_id, help_id) values(38,38);
insert into help(name, text, staff_id) values('Help #39 MQ#9 T#4', 'Please, refer to section #9 MQ#9 T#4', 1);
insert into question_help(question_id, help_id) values(39,39);
insert into help(name, text, staff_id) values('Help #40 MQ#10 T#4', 'Please, refer to section #10 MQ#10 T#4', 1);
insert into question_help(question_id, help_id) values(40,40);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#71-MQ#1-T#4.jpg', 'Resource #71 MQ#1 T#4', 1);
insert into question_resource(question_id, resource_id) values(31, 71);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#72-MQ#2-T#4.jpg', 'Resource #72 MQ#2 T#4', 1);
insert into question_resource(question_id, resource_id) values(32, 72);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#73-MQ#3-T#4.jpg', 'Resource #73 MQ#3 T#4', 1);
insert into question_resource(question_id, resource_id) values(33, 73);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#74-MQ#4-T#4.jpg', 'Resource #74 MQ#4 T#4', 1);
insert into question_resource(question_id, resource_id) values(34, 74);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#75-MQ#5-T#4.jpg', 'Resource #75 MQ#5 T#4', 1);
insert into question_resource(question_id, resource_id) values(35, 75);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#76-MQ#6-T#4.jpg', 'Resource #76 MQ#6 T#4', 1);
insert into question_resource(question_id, resource_id) values(36, 76);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#77-MQ#7-T#4.jpg', 'Resource #77 MQ#7 T#4', 1);
insert into question_resource(question_id, resource_id) values(37, 77);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#78-MQ#8-T#4.jpg', 'Resource #78 MQ#8 T#4', 1);
insert into question_resource(question_id, resource_id) values(38, 78);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#79-MQ#9-T#4.jpg', 'Resource #79 MQ#9 T#4', 1);
insert into question_resource(question_id, resource_id) values(39, 79);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#80-MQ#10-T#4.jpg', 'Resource #80 MQ#10 T#4', 1);
insert into question_resource(question_id, resource_id) values(40, 80);

/* Q#1 answers*/

insert into phrase(phrase, staff_id) values('white', 1);
insert into phrase(phrase, staff_id) values('black', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#81-Answer#1-MQ#1-T#4.jpg', 'Resource #81 Answer#1 MQ#1 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(92, 81);

insert into phrase(phrase, staff_id) values('heaven', 1);
insert into phrase(phrase, staff_id) values('earth', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#82-Answer#2-MQ#1-T#4.jpg', 'Resource #82 Answer#2 MQ#1 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(94, 82);

insert into phrase(phrase, staff_id) values('sea', 1);
insert into phrase(phrase, staff_id) values('ground', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#83-Answer#3-MQ#1-T#4.jpg', 'Resource #83 Answer#3 MQ#1 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(96, 83);

insert into phrase(phrase, staff_id) values('air', 1);
insert into phrase(phrase, staff_id) values('fire', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#84-Answer#4-MQ#1-T#4.jpg', 'Resource #84 Answer#4 MQ#1 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(98, 84);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(91, 92, 31);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(93, 94, 31);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(95, 96, 31);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(97, 98, 31);



/* Q#2 answers*/
insert into phrase(phrase, staff_id) values('mount', 1);
insert into phrase(phrase, staff_id) values('plain', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#85-Answer#1-MQ#2-T#4.jpg', 'Resource #85 Answer#1 MQ#2 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(100, 85);

insert into phrase(phrase, staff_id) values('fruit', 1);
insert into phrase(phrase, staff_id) values('vegetable', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#86-Answer#2-MQ#2-T#4.jpg', 'Resource #86 Answer#2 MQ#2 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(102, 86);

insert into phrase(phrase, staff_id) values('sweet', 1);
insert into phrase(phrase, staff_id) values('bitter', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#87-Answer#3-MQ#2-T#4.jpg', 'Resource #87 Answer#3 MQ#2 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(104, 87);

insert into phrase(phrase, staff_id) values('man', 1);
insert into phrase(phrase, staff_id) values('woman', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#88-Answer#4-MQ#2-T#4.jpg', 'Resource #88 Answer#4 MQ#2 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(106, 88);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(99, 100, 32);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(101, 102, 32);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(103, 104, 32);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(105, 106, 32);


/* Q#3 answers*/

insert into phrase(phrase, staff_id) values('rich', 1);
insert into phrase(phrase, staff_id) values('poor', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#89-Answer#1-MQ#3-T#4.jpg', 'Resource #89 Answer#1 MQ#3 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(108, 89);

insert into phrase(phrase, staff_id) values('big', 1);
insert into phrase(phrase, staff_id) values('small', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#90-Answer#2-MQ#3-T#4.jpg', 'Resource #90 Answer#2 MQ#3 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(110, 90);

insert into phrase(phrase, staff_id) values('cats', 1);
insert into phrase(phrase, staff_id) values('dogs', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#91-Answer#3-MQ#3-T#4.jpg', 'Resource #91 Answer#3 MQ#3 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(112, 91);

insert into phrase(phrase, staff_id) values('weak', 1);
insert into phrase(phrase, staff_id) values('strong', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#92-Answer#4-MQ#3-T#4.jpg', 'Resource #92 Answer#4 MQ#3 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(114, 92);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(107, 108, 33);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(109, 110, 33);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(111, 112, 33);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(113, 114, 33);


/* Q#4 answers*/
insert into phrase(phrase, staff_id) values('healthy', 1);
insert into phrase(phrase, staff_id) values('ill', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#93-Answer#1-MQ#4-T#4.jpg', 'Resource #93 Answer#1 MQ#4 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(116, 93);

insert into phrase(phrase, staff_id) values('dark', 1);
insert into phrase(phrase, staff_id) values('light', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#94-Answer#2-MQ#4-T#4.jpg', 'Resource #94 Answer#2 MQ#4 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(118, 94);

insert into phrase(phrase, staff_id) values('sugar', 1);
insert into phrase(phrase, staff_id) values('salt', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#95-Answer#3-MQ#4-T#4.jpg', 'Resource #95 Answer#3 MQ#4 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(120, 95);

insert into phrase(phrase, staff_id) values('kind', 1);
insert into phrase(phrase, staff_id) values('angry', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#96-Answer#4-MQ#4-T#4.jpg', 'Resource #96 Answer#4 MQ#4 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(122, 96);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(115, 116, 34);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(117, 118, 34);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(119, 120, 34);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(121, 122, 34);


/* Q#5 answers*/
insert into phrase(phrase, staff_id) values('smart', 1);
insert into phrase(phrase, staff_id) values('stupid', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#97-Answer#1-MQ#5-T#4.jpg', 'Resource #97 Answer#1 MQ#5 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(124, 97);

insert into phrase(phrase, staff_id) values('expensive', 1);
insert into phrase(phrase, staff_id) values('cheap', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#98-Answer#2-MQ#5-T#4.jpg', 'Resource #98 Answer#2 MQ#5 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(126, 98);

insert into phrase(phrase, staff_id) values('fast', 1);
insert into phrase(phrase, staff_id) values('slow', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#99-Answer#3-MQ#5-T#4.jpg', 'Resource #99 Answer#3 MQ#5 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(128, 99);

insert into phrase(phrase, staff_id) values('river', 1);
insert into phrase(phrase, staff_id) values('lake', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#100-Answer#4-MQ#5-T#4.jpg', 'Resource #100 Answer#4 MQ#5 T#4', 1);

insert into phrase_resource(phrase_id, resource_id) values(130, 100);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(123, 124, 35);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(125, 126, 35);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(127, 128, 35);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(129, 130, 35);


/* Q#6 answers*/
insert into phrase(phrase, staff_id) values('city', 1);
insert into phrase(phrase, staff_id) values('village', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#101-Answer#1-MQ#6-T#4.jpg', 'Resource #101 Answer#1 MQ#6 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(132, 101);

insert into phrase(phrase, staff_id) values('dollar', 1);
insert into phrase(phrase, staff_id) values('euro', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#102-Answer#2-MQ#6-T#4.jpg', 'Resource #102 Answer#2 MQ#6 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(134, 102);

insert into phrase(phrase, staff_id) values('heart', 1);
insert into phrase(phrase, staff_id) values('soul', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#103-Answer#3-MQ#6-T#4.jpg', 'Resource #103 Answer#3 MQ#6 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(136, 103);

insert into phrase(phrase, staff_id) values('cold winter', 1);
insert into phrase(phrase, staff_id) values('hot summer', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#104-Answer#4-MQ#6-T#4.jpg', 'Resource #104 Answer#4 MQ#6 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(138, 104);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(131, 132, 36);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(133, 134, 36);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(135, 136, 36);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(137, 138, 36);


/* Q#7 answers*/
insert into phrase(phrase, staff_id) values('long', 1);
insert into phrase(phrase, staff_id) values('short', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#105-Answer#1-MQ#7-T#4.jpg', 'Resource #105 Answer#1 MQ#7 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(140, 105);

insert into phrase(phrase, staff_id) values('walk', 1);
insert into phrase(phrase, staff_id) values('ride', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#106-Answer#2-MQ#7-T#4.jpg', 'Resource #106 Answer#2 MQ#7 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(142, 106);

insert into phrase(phrase, staff_id) values('wood', 1);
insert into phrase(phrase, staff_id) values('steel', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#107-Answer#3-MQ#7-T#4.jpg', 'Resource #107 Answer#3 MQ#7 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(144, 107);

insert into phrase(phrase, staff_id) values('art', 1);
insert into phrase(phrase, staff_id) values('technology', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#108-Answer#4-MQ#7-T#4.jpg', 'Resource #108 Answer#4 MQ#7 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(146, 108);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(139, 140, 37);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(141, 142, 37);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(143, 144, 37);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(145, 146, 37);


/* Q#8 answers*/
insert into phrase(phrase, staff_id) values('work', 1);
insert into phrase(phrase, staff_id) values('relax', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#109-Answer#1-MQ#8-T#4.jpg', 'Resource #109 Answer#1 MQ#8 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(148, 109);

insert into phrase(phrase, staff_id) values('warm', 1);
insert into phrase(phrase, staff_id) values('cold', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#110-Answer#2-MQ#8-T#4.jpg', 'Resource #110 Answer#2 MQ#8 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(150, 110);

insert into phrase(phrase, staff_id) values('excellent', 1);
insert into phrase(phrase, staff_id) values('satisfactory', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#111-Answer#3-MQ#8-T#4.jpg', 'Resource #111 Answer#3 MQ#8 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(152, 111);

insert into phrase(phrase, staff_id) values('reality', 1);
insert into phrase(phrase, staff_id) values('imagination', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#112-Answer#4-MQ#8-T#4.jpg', 'Resource #112 Answer#4 MQ#8 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(154, 112);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(147, 148, 38);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(149, 150, 38);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(151, 152, 38);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(153, 154, 38);


/* Q#9 answers*/

insert into phrase(phrase, staff_id) values('humble', 1);
insert into phrase(phrase, staff_id) values('aristocratic', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#113-Answer#1-MQ#9-T#4.jpg', 'Resource #113 Answer#1 MQ#9 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(156, 113);

insert into phrase(phrase, staff_id) values('wide', 1);
insert into phrase(phrase, staff_id) values('narrow', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#114-Answer#2-MQ#9-T#4.jpg', 'Resource #114 Answer#2 MQ#9 T#4', 1);

insert into phrase_resource(phrase_id, resource_id) values(158, 114);

insert into phrase(phrase, staff_id) values('west', 1);
insert into phrase(phrase, staff_id) values('east', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#115-Answer#3-MQ#9-T#4.jpg', 'Resource #115 Answer#3 MQ#9 T#4', 1);

insert into phrase_resource(phrase_id, resource_id) values(160, 115);

insert into phrase(phrase, staff_id) values('glad', 1);
insert into phrase(phrase, staff_id) values('sad', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#116-Answer#4-MQ#9-T#4.jpg', 'Resource #116 Answer#4 MQ#9 T#4', 1);

insert into phrase_resource(phrase_id, resource_id) values(162, 116);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(155, 156, 39);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(157, 158, 39);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(159, 160, 39);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(161, 162, 39);


/* Q#10 answers*/
insert into phrase(phrase, staff_id) values('humid', 1);
insert into phrase(phrase, staff_id) values('dry', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#117-Answer#1-MQ#10-T#4.jpg', 'Resource #117 Answer#1 MQ#10 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(164, 117);

insert into phrase(phrase, staff_id) values('peace', 1);
insert into phrase(phrase, staff_id) values('war', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#118-Answer#2-MQ#10-T#4.jpg', 'Resource #118 Answer#2 MQ#10 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(166, 118);

insert into phrase(phrase, staff_id) values('love', 1);
insert into phrase(phrase, staff_id) values('hate', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#119-Answer#3-MQ#10-T#4.jpg', 'Resource #119 Answer#3 MQ#10 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(168, 119);

insert into phrase(phrase, staff_id) values('day', 1);
insert into phrase(phrase, staff_id) values('night', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#120-Answer#4-MQ#10-T#4.jpg', 'Resource #120 Answer#4 MQ#10 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(170, 120);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(163, 164, 40);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(165, 166, 40);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(167, 168, 40);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(169, 170, 40);



/*Questions Theme #5 (All SQ) 10 pieces, full equipment*/

insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #1 T#5', 1, 5, 5, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #2 T#5', 1, 5, 5, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #3 T#5', 1, 5, 5, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #4 T#5', 1, 5, 5, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #5 T#5', 1, 5, 5, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #6 T#5', 1, 5, 5, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #7 T#5', 1, 5, 5, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #8 T#5', 1, 5, 5, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #9 T#5', 1, 5, 5, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #10 T#5', 1, 5, 5, 1);

insert into help(name, text, staff_id) values('Help #41 SQ#1 T#5', 'Please, refer to section #1 SQ#1 T#5', 1);
insert into question_help(question_id, help_id) values(41,41);
insert into help(name, text, staff_id) values('Help #42 SQ#2 T#5', 'Please, refer to section #2 SQ#2 T#5', 1);
insert into question_help(question_id, help_id) values(42,42);
insert into help(name, text, staff_id) values('Help #43 SQ#3 T#5', 'Please, refer to section #3 SQ#3 T#5', 1);
insert into question_help(question_id, help_id) values(43,43);
insert into help(name, text, staff_id) values('Help #44 SQ#4 T#5', 'Please, refer to section #4 SQ#4 T#5', 1);
insert into question_help(question_id, help_id) values(44,44);
insert into help(name, text, staff_id) values('Help #45 SQ#5 T#5', 'Please, refer to section #5 SQ#5 T#5', 1);
insert into question_help(question_id, help_id) values(45,45);
insert into help(name, text, staff_id) values('Help #46 SQ#6 T#5', 'Please, refer to section #6 SQ#6 T#5', 1);
insert into question_help(question_id, help_id) values(46,46);
insert into help(name, text, staff_id) values('Help #47 SQ#7 T#5', 'Please, refer to section #7 SQ#7 T#5', 1);
insert into question_help(question_id, help_id) values(47,47);
insert into help(name, text, staff_id) values('Help #48 SQ#8 T#5', 'Please, refer to section #8 SQ#8 T#5', 1);
insert into question_help(question_id, help_id) values(48,48);
insert into help(name, text, staff_id) values('Help #49 SQ#9 T#5', 'Please, refer to section #9 SQ#9 T#5', 1);
insert into question_help(question_id, help_id) values(49,49);
insert into help(name, text, staff_id) values('Help #50 SQ#10 T#5', 'Please, refer to section #10 SQ#10 T#5', 1);
insert into question_help(question_id, help_id) values(50,50);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#121-SQ#1-T#5.jpg', 'Resource #121 SQ#1 T#5', 1);
insert into question_resource(question_id, resource_id) values(41, 121);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#122-SQ#2-T#5.jpg', 'Resource #122 SQ#2 T#5', 1);
insert into question_resource(question_id, resource_id) values(42, 122);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#123-SQ#3-T#5.jpg', 'Resource #123 SQ#3 T#5', 1);
insert into question_resource(question_id, resource_id) values(43, 123);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#124-SQ#4-T#5.jpg', 'Resource #124 SQ#4 T#5', 1);
insert into question_resource(question_id, resource_id) values(44, 124);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#125-SQ#5-T#5.jpg', 'Resource #125 SQ#5 T#5', 1);
insert into question_resource(question_id, resource_id) values(45, 125);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#126-SQ#6-T#5.jpg', 'Resource #126 SQ#6 T#5', 1);
insert into question_resource(question_id, resource_id) values(46, 126);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#127-SQ#7-T#5.jpg', 'Resource #127 SQ#7 T#5', 1);
insert into question_resource(question_id, resource_id) values(47, 127);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#128-SQ#8-T#5.jpg', 'Resource #128 SQ#8 T#5', 1);
insert into question_resource(question_id, resource_id) values(48, 128);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#129-SQ#9-T#5.jpg', 'Resource #129 SQ#9 T#5', 1);
insert into question_resource(question_id, resource_id) values(49, 129);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#130-SQ#10-T#5.jpg', 'Resource #130 SQ#10 T#5', 1);
insert into question_resource(question_id, resource_id) values(50, 130);

/* Q#1 answers*/
insert into phrase(phrase, staff_id) values('Monday', 1);
insert into phrase(phrase, staff_id) values('Tuesday', 1);
insert into phrase(phrase, staff_id) values('Wednesday', 1);
insert into phrase(phrase, staff_id) values('Thursday', 1);
insert into phrase(phrase, staff_id) values('Friday', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#131-Answer#1-SQ#1-T#5.jpg', 'Resource #131 Answer#1 SQ#1 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#132-Answer#2-SQ#1-T#5.jpg', 'Resource #132 Answer#2 SQ#1 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#133-Answer#3-SQ#1-T#5.jpg', 'Resource #133 Answer#3 SQ#1 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#134-Answer#4-SQ#1-T#5.jpg', 'Resource #134 Answer#4 SQ#1 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#135-Answer#5-SQ#1-T#5.jpg', 'Resource #135 Answer#5 SQ#1 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(171, 131);
insert into phrase_resource(phrase_id, resource_id) values(172, 132);
insert into phrase_resource(phrase_id, resource_id) values(173, 133);
insert into phrase_resource(phrase_id, resource_id) values(174, 134);
insert into phrase_resource(phrase_id, resource_id) values(175, 135);

insert into answer_sq(phrase_id, phrase_order, question_id) values(171, 0, 41);
insert into answer_sq(phrase_id, phrase_order, question_id) values(172, 1, 41);
insert into answer_sq(phrase_id, phrase_order, question_id) values(173, 2, 41);
insert into answer_sq(phrase_id, phrase_order, question_id) values(174, 3, 41);
insert into answer_sq(phrase_id, phrase_order, question_id) values(175, 4, 41);


/* Q#2 answers*/
insert into phrase(phrase, staff_id) values('January', 1);
insert into phrase(phrase, staff_id) values('February', 1);
insert into phrase(phrase, staff_id) values('March', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#136-Answer#1-SQ#2-T#5.jpg', 'Resource #136 Answer#1 SQ#2 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#137-Answer#2-SQ#2-T#5.jpg', 'Resource #137 Answer#2 SQ#2 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#138-Answer#3-SQ#2-T#5.jpg', 'Resource #138 Answer#3 SQ#2 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(176, 136);
insert into phrase_resource(phrase_id, resource_id) values(177, 137);
insert into phrase_resource(phrase_id, resource_id) values(178, 138);

insert into answer_sq(phrase_id, phrase_order, question_id) values(176, 0, 42);
insert into answer_sq(phrase_id, phrase_order, question_id) values(177, 1, 42);
insert into answer_sq(phrase_id, phrase_order, question_id) values(178, 2, 42);


/* Q#3 answers*/
insert into phrase(phrase, staff_id) values('summer', 1);
insert into phrase(phrase, staff_id) values('autumn', 1);
insert into phrase(phrase, staff_id) values('winter', 1);
insert into phrase(phrase, staff_id) values('spring', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#139-Answer#1-SQ#3-T#5.jpg', 'Resource #139 Answer#1 SQ#3 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#140-Answer#2-SQ#3-T#5.jpg', 'Resource #140 Answer#2 SQ#3 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#141-Answer#3-SQ#3-T#5.jpg', 'Resource #141 Answer#3 SQ#3 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#142-Answer#4-SQ#3-T#5.jpg', 'Resource #142 Answer#3 SQ#3 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(179, 139);
insert into phrase_resource(phrase_id, resource_id) values(180, 140);
insert into phrase_resource(phrase_id, resource_id) values(181, 141);
insert into phrase_resource(phrase_id, resource_id) values(182, 142);

insert into answer_sq(phrase_id, phrase_order, question_id) values(179, 0, 43);
insert into answer_sq(phrase_id, phrase_order, question_id) values(180, 1, 43);
insert into answer_sq(phrase_id, phrase_order, question_id) values(181, 2, 43);
insert into answer_sq(phrase_id, phrase_order, question_id) values(182, 3, 43);

/* Q#4 answers*/
insert into phrase(phrase, staff_id) values('kindergarten', 1);
insert into phrase(phrase, staff_id) values('school', 1);
insert into phrase(phrase, staff_id) values('university', 1);
insert into phrase(phrase, staff_id) values('company', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#143-Answer#1-SQ#4-T#5.jpg', 'Resource #143 Answer#1 SQ#4 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#144-Answer#2-SQ#4-T#5.jpg', 'Resource #144 Answer#2 SQ#4 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#145-Answer#3-SQ#4-T#5.jpg', 'Resource #145 Answer#3 SQ#4 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#146-Answer#4-SQ#4-T#5.jpg', 'Resource #146 Answer#4 SQ#4 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(183, 143);
insert into phrase_resource(phrase_id, resource_id) values(184, 144);
insert into phrase_resource(phrase_id, resource_id) values(185, 145);
insert into phrase_resource(phrase_id, resource_id) values(186, 146);

insert into answer_sq(phrase_id, phrase_order, question_id) values(183, 0, 44);
insert into answer_sq(phrase_id, phrase_order, question_id) values(184, 1, 44);
insert into answer_sq(phrase_id, phrase_order, question_id) values(185, 2, 44);
insert into answer_sq(phrase_id, phrase_order, question_id) values(186, 3, 44);


/* Q#5 answers*/
insert into phrase(phrase, staff_id) values('birth', 1);
insert into phrase(phrase, staff_id) values('grow-up', 1);
insert into phrase(phrase, staff_id) values('marriage', 1);
insert into phrase(phrase, staff_id) values('death', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#147-Answer#1-SQ#5-T#5.jpg', 'Resource #147 Answer#1 SQ#5 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#148-Answer#2-SQ#5-T#5.jpg', 'Resource #148 Answer#2 SQ#5 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#149-Answer#3-SQ#5-T#5.jpg', 'Resource #149 Answer#3 SQ#5 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#150-Answer#4-SQ#5-T#5.jpg', 'Resource #150 Answer#4 SQ#5 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(187, 147);
insert into phrase_resource(phrase_id, resource_id) values(188, 148);
insert into phrase_resource(phrase_id, resource_id) values(189, 149);
insert into phrase_resource(phrase_id, resource_id) values(190, 150);

insert into answer_sq(phrase_id, phrase_order, question_id) values(187, 0, 45);
insert into answer_sq(phrase_id, phrase_order, question_id) values(188, 1, 45);
insert into answer_sq(phrase_id, phrase_order, question_id) values(189, 2, 45);
insert into answer_sq(phrase_id, phrase_order, question_id) values(190, 3, 45);


/* Q#6 answers*/
insert into phrase(phrase, staff_id) values('one', 1);
insert into phrase(phrase, staff_id) values('two', 1);
insert into phrase(phrase, staff_id) values('three', 1);
insert into phrase(phrase, staff_id) values('four', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#151-Answer#1-SQ#6-T#5.jpg', 'Resource #151 Answer#1 SQ#6 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#152-Answer#2-SQ#6-T#5.jpg', 'Resource #152 Answer#2 SQ#6 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#153-Answer#3-SQ#6-T#5.jpg', 'Resource #153 Answer#3 SQ#6 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#154-Answer#4-SQ#6-T#5.jpg', 'Resource #154 Answer#4 SQ#6 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(191, 151);
insert into phrase_resource(phrase_id, resource_id) values(192, 152);
insert into phrase_resource(phrase_id, resource_id) values(193, 153);
insert into phrase_resource(phrase_id, resource_id) values(194, 154);

insert into answer_sq(phrase_id, phrase_order, question_id) values(191, 0, 46);
insert into answer_sq(phrase_id, phrase_order, question_id) values(192, 1, 46);
insert into answer_sq(phrase_id, phrase_order, question_id) values(193, 2, 46);
insert into answer_sq(phrase_id, phrase_order, question_id) values(194, 3, 46);

/* Q#7 answers*/
insert into phrase(phrase, staff_id) values('letter A', 1);
insert into phrase(phrase, staff_id) values('letter B', 1);
insert into phrase(phrase, staff_id) values('letter C', 1);
insert into phrase(phrase, staff_id) values('letter D', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#155-Answer#1-SQ#7-T#5.jpg', 'Resource #155 Answer#1 SQ#7 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#156-Answer#2-SQ#7-T#5.jpg', 'Resource #156 Answer#2 SQ#7 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#157-Answer#3-SQ#7-T#5.jpg', 'Resource #157 Answer#3 SQ#7 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#158-Answer#4-SQ#7-T#5.jpg', 'Resource #158 Answer#4 SQ#7 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(195, 155);
insert into phrase_resource(phrase_id, resource_id) values(196, 156);
insert into phrase_resource(phrase_id, resource_id) values(197, 157);
insert into phrase_resource(phrase_id, resource_id) values(198, 158);

insert into answer_sq(phrase_id, phrase_order, question_id) values(195, 0, 47);
insert into answer_sq(phrase_id, phrase_order, question_id) values(196, 1, 47);
insert into answer_sq(phrase_id, phrase_order, question_id) values(197, 2, 47);
insert into answer_sq(phrase_id, phrase_order, question_id) values(198, 3, 47);


/* Q#8 answers*/
insert into phrase(phrase, staff_id) values('morning', 1);
insert into phrase(phrase, staff_id) values('noon', 1);
insert into phrase(phrase, staff_id) values('afternoon', 1);
insert into phrase(phrase, staff_id) values('evening', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#159-Answer#1-SQ#8-T#5.jpg', 'Resource #159 Answer#1 SQ#8 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#160-Answer#2-SQ#8-T#5.jpg', 'Resource #160 Answer#2 SQ#8 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#161-Answer#3-SQ#8-T#5.jpg', 'Resource #161 Answer#3 SQ#8 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#162-Answer#4-SQ#8-T#5.jpg', 'Resource #162 Answer#4 SQ#8 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(199, 159);
insert into phrase_resource(phrase_id, resource_id) values(200, 160);
insert into phrase_resource(phrase_id, resource_id) values(201, 161);
insert into phrase_resource(phrase_id, resource_id) values(202, 162);

insert into answer_sq(phrase_id, phrase_order, question_id) values(199, 0, 48);
insert into answer_sq(phrase_id, phrase_order, question_id) values(200, 1, 48);
insert into answer_sq(phrase_id, phrase_order, question_id) values(201, 2, 48);
insert into answer_sq(phrase_id, phrase_order, question_id) values(202, 3, 48);


/* Q#9 answers*/
insert into phrase(phrase, staff_id) values('2000', 1);
insert into phrase(phrase, staff_id) values('2001', 1);
insert into phrase(phrase, staff_id) values('2002', 1);
insert into phrase(phrase, staff_id) values('2003', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#163-Answer#1-SQ#9-T#5.jpg', 'Resource #163 Answer#1 SQ#9 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#164-Answer#2-SQ#9-T#5.jpg', 'Resource #164 Answer#2 SQ#9 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#165-Answer#3-SQ#9-T#5.jpg', 'Resource #165 Answer#3 SQ#9 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#166-Answer#4-SQ#9-T#5.jpg', 'Resource #166 Answer#4 SQ#9 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(203, 163);
insert into phrase_resource(phrase_id, resource_id) values(204, 164);
insert into phrase_resource(phrase_id, resource_id) values(205, 165);
insert into phrase_resource(phrase_id, resource_id) values(206, 166);

insert into answer_sq(phrase_id, phrase_order, question_id) values(203, 0, 49);
insert into answer_sq(phrase_id, phrase_order, question_id) values(204, 1, 49);
insert into answer_sq(phrase_id, phrase_order, question_id) values(205, 2, 49);
insert into answer_sq(phrase_id, phrase_order, question_id) values(206, 3, 49);

/* Q#10 answers*/
insert into phrase(phrase, staff_id) values('step 1', 1);
insert into phrase(phrase, staff_id) values('step 2', 1);
insert into phrase(phrase, staff_id) values('step 3', 1);
insert into phrase(phrase, staff_id) values('step 4', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#167-Answer#1-SQ#10-T#5.jpg', 'Resource #167 Answer#1 SQ#10 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#168-Answer#2-SQ#10-T#5.jpg', 'Resource #168 Answer#2 SQ#10 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#169-Answer#3-SQ#10-T#5.jpg', 'Resource #169 Answer#3 SQ#10 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#170-Answer#4-SQ#10-T#5.jpg', 'Resource #170 Answer#4 SQ#10 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(207, 167);
insert into phrase_resource(phrase_id, resource_id) values(208, 168);
insert into phrase_resource(phrase_id, resource_id) values(209, 169);
insert into phrase_resource(phrase_id, resource_id) values(210, 170);

insert into answer_sq(phrase_id, phrase_order, question_id) values(207, 0, 50);
insert into answer_sq(phrase_id, phrase_order, question_id) values(208, 1, 50);
insert into answer_sq(phrase_id, phrase_order, question_id) values(209, 2, 50);
insert into answer_sq(phrase_id, phrase_order, question_id) values(210, 3, 50);
