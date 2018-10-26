insert into scheme(name, is_active, strategy_id, settings_id, mode_id, grading_id, course_id, created, created_by, is_deleted, is_completed)
           values('Sample scheme #1', 1, 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0, 1);

insert into theme (name, course_id) values('Sample theme #1 (All MCQ)', 1);
insert into theme (name, course_id) values('Sample theme #2 (All FBSQ)', 1);
insert into theme (name, course_id) values('Sample theme #3 (All FBMQ)', 1);
insert into theme (name, course_id) values('Sample theme #4 (All MQ)', 1);
insert into theme (name, course_id) values('Sample theme #5 (All SQ)', 1);

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

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#01-MCQ#1-T#1.jpg', 'Resource #1 MCQ#1 T#1', 1);
insert into question_resource(question_id, resource_id) values(1, 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#02-MCQ#2-T#1.jpg', 'Resource #2 MCQ#2 T#1', 1);
insert into question_resource(question_id, resource_id) values(2, 2);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#03-MCQ#3-T#1.jpg', 'Resource #3 MCQ#3 T#1', 1);
insert into question_resource(question_id, resource_id) values(3, 3);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#04-MCQ#4-T#1.jpg', 'Resource #4 MCQ#4 T#1', 1);
insert into question_resource(question_id, resource_id) values(4, 4);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#05-MCQ#5-T#1.jpg', 'Resource #5 MCQ#5 T#1', 1);
insert into question_resource(question_id, resource_id) values(5, 5);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#06-MCQ#6-T#1.jpg', 'Resource #6 MCQ#6 T#1', 1);
insert into question_resource(question_id, resource_id) values(6, 6);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#07-MCQ#7-T#1.jpg', 'Resource #7 MCQ#7 T#1', 1);
insert into question_resource(question_id, resource_id) values(7, 7);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#08-MCQ#8-T#1.jpg', 'Resource #8 MCQ#8 T#1', 1);
insert into question_resource(question_id, resource_id) values(8, 8);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#09-MCQ#9-T#1.jpg', 'Resource #9 MCQ#9 T#1', 1);
insert into question_resource(question_id, resource_id) values(9, 9);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#10-MCQ#10-T#1.jpg', 'Resource #10 MCQ#10 T#1', 1);
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

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#11-Answer#1-MCQ#1-T#1.jpg', 'Resource #11 Answer#1 MCQ#1 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(1, 11);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#12-Answer#2-MCQ#1-T#1.jpg', 'Resource #12 Answer#2 MCQ#1 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(2, 12);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#13-Answer#3-MCQ#1-T#1.jpg', 'Resource #13 Answer#3 MCQ#1 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(3, 13);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#14-Answer#4-MCQ#1-T#1.jpg', 'Resource #14 Answer#4 MCQ#1 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(4, 14);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#15-Answer#1-MCQ#2-T#1.jpg', 'Resource #15 Answer#1 MCQ#2 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(5, 15);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#16-Answer#2-MCQ#2-T#1.jpg', 'Resource #16 Answer#2 MCQ#2 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(6, 16);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#17-Answer#3-MCQ#2-T#1.jpg', 'Resource #17 Answer#3 MCQ#2 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(7, 17);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#18-Answer#4-MCQ#2-T#1.jpg', 'Resource #18 Answer#4 MCQ#2 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(8, 18);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#19-Answer#1-MCQ#3-T#1.jpg', 'Resource #19 Answer#1 MCQ#3 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(9, 19);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#20-Answer#2-MCQ#3-T#1.jpg', 'Resource #20 Answer#2 MCQ#3 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(10, 20);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#21-Answer#3-MCQ#3-T#1.jpg', 'Resource #20 Answer#3 MCQ#3 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(11, 21);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#22-Answer#4-MCQ#3-T#1.jpg', 'Resource #22 Answer#4 MCQ#3 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(12, 22);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#23-Answer#1-MCQ#4-T#1.jpg', 'Resource #23 Answer#1 MCQ#4 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(13, 23);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#24-Answer#2-MCQ#4-T#1.jpg', 'Resource #24 Answer#2 MCQ#4 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(14, 24);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#25-Answer#3-MCQ#4-T#1.jpg', 'Resource #24 Answer#3 MCQ#4 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(15, 25);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#26-Answer#4-MCQ#4-T#1.jpg', 'Resource #26 Answer#4 MCQ#4 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(16, 26);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#27-Answer#1-MCQ#5-T#1.jpg', 'Resource #27 Answer#1 MCQ#5 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(17, 27);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#28-Answer#2-MCQ#5-T#1.jpg', 'Resource #28 Answer#2 MCQ#5 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(18, 28);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#29-Answer#3-MCQ#5-T#1.jpg', 'Resource #29 Answer#3 MCQ#5 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(19, 29);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#30-Answer#4-MCQ#5-T#1.jpg', 'Resource #30 Answer#4 MCQ#5 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(20, 30);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#31-Answer#1-MCQ#6-T#1.jpg', 'Resource #31 Answer#1 MCQ#6 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(21, 31);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#31-Answer#2-MCQ#6-T#1.jpg', 'Resource #32 Answer#2 MCQ#6 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(22, 32);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#33-Answer#3-MCQ#6-T#1.jpg', 'Resource #33 Answer#3 MCQ#6 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(23, 33);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#34-Answer#4-MCQ#6-T#1.jpg', 'Resource #34 Answer#4 MCQ#6 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(24, 34);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#35-Answer#1-MCQ#7-T#1.jpg', 'Resource #35 Answer#1 MCQ#7 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(25, 35);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#36-Answer#2-MCQ#7-T#1.jpg', 'Resource #36 Answer#2 MCQ#7 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(26, 36);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#37-Answer#3-MCQ#7-T#1.jpg', 'Resource #37 Answer#3 MCQ#7 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(27, 37);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#38-Answer#4-MCQ#7-T#1.jpg', 'Resource #38 Answer#4 MCQ#7 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(28, 38);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#39-Answer#1-MCQ#8-T#1.jpg', 'Resource #39 Answer#1 MCQ#8 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(29, 39);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#40-Answer#2-MCQ#8-T#1.jpg', 'Resource #40 Answer#2 MCQ#8 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(30, 40);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#41-Answer#3-MCQ#8-T#1.jpg', 'Resource #41 Answer#3 MCQ#8 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(31, 41);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#42-Answer#4-MCQ#8-T#1.jpg', 'Resource #42 Answer#4 MCQ#8 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(32, 42);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#43-Answer#1-MCQ#9-T#1.jpg', 'Resource #43 Answer#1 MCQ#9 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(33, 43);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#44-Answer#2-MCQ#9-T#1.jpg', 'Resource #44 Answer#2 MCQ#9 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(34, 44);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#45-Answer#3-MCQ#9-T#1.jpg', 'Resource #45 Answer#3 MCQ#9 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(35, 45);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#46-Answer#4-MCQ#9-T#1.jpg', 'Resource #46 Answer#4 MCQ#9 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(36, 46);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#47-Answer#1-MCQ#10-T#1.jpg', 'Resource #47 Answer#1 MCQ#10 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(37, 47);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#48-Answer#2-MCQ#10-T#1.jpg', 'Resource #48 Answer#2 MCQ#10 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(38, 48);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#49-Answer#3-MCQ#10-T#1.jpg', 'Resource #49 Answer#3 MCQ#10 T#1', 1);
insert into answer_mcq_resource(answer_id, resource_id) values(39, 49);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#50-Answer#4-MCQ#10-T#1.jpg', 'Resource #50 Answer#4 MCQ#10 T#1', 1);
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

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#51-FBSQ#1-T#2.jpg', 'Resource #51 FBSQ#1 T#2', 1);
insert into question_resource(question_id, resource_id) values(11, 51);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#52-FBSQ#2-T#2.jpg', 'Resource #51 FBSQ#2 T#2', 1);
insert into question_resource(question_id, resource_id) values(12, 52);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#53-FBSQ#3-T#2.jpg', 'Resource #51 FBSQ#3 T#2', 1);
insert into question_resource(question_id, resource_id) values(13, 53);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#54-FBSQ#4-T#2.jpg', 'Resource #51 FBSQ#4 T#2', 1);
insert into question_resource(question_id, resource_id) values(14, 54);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#55-FBSQ#5-T#2.jpg', 'Resource #51 FBSQ#5 T#2', 1);
insert into question_resource(question_id, resource_id) values(15, 55);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#56-FBSQ#6-T#2.jpg', 'Resource #51 FBSQ#6 T#2', 1);
insert into question_resource(question_id, resource_id) values(16, 56);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#57-FBSQ#7-T#2.jpg', 'Resource #51 FBSQ#7 T#2', 1);
insert into question_resource(question_id, resource_id) values(17, 57);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#58-FBSQ#8-T#2.jpg', 'Resource #51 FBSQ#8 T#2', 1);
insert into question_resource(question_id, resource_id) values(18, 58);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#59-FBSQ#9-T#2.jpg', 'Resource #51 FBSQ#9 T#2', 1);
insert into question_resource(question_id, resource_id) values(19, 59);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#60-FBSQ#10-T#2.jpg', 'Resource #51 FBSQ#10 T#2', 1);
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

insert into accepted_phrase(phrase, staff_id, last_used) values('wolf', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(1, 11);
insert into accepted_phrase(phrase, staff_id, last_used) values('wolf+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(2, 11);
insert into accepted_phrase(phrase, staff_id, last_used) values('wolf-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(3, 11);

insert into accepted_phrase(phrase, staff_id, last_used) values('bear', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(4, 12);
insert into accepted_phrase(phrase, staff_id, last_used) values('bear+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(5, 12);
insert into accepted_phrase(phrase, staff_id, last_used) values('bear-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(6, 12);

insert into accepted_phrase(phrase, staff_id, last_used) values('duck', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(7, 13);
insert into accepted_phrase(phrase, staff_id, last_used) values('duck+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(8, 13);
insert into accepted_phrase(phrase, staff_id, last_used) values('duck-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(9, 13);

insert into accepted_phrase(phrase, staff_id, last_used) values('cow', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(10, 14);
insert into accepted_phrase(phrase, staff_id, last_used) values('cow+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(11, 14);
insert into accepted_phrase(phrase, staff_id, last_used) values('cow-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(12, 14);

insert into accepted_phrase(phrase, staff_id, last_used) values('dog', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(13, 15);
insert into accepted_phrase(phrase, staff_id, last_used) values('dog+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(14, 15);
insert into accepted_phrase(phrase, staff_id, last_used) values('dog-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(15, 15);

insert into accepted_phrase(phrase, staff_id, last_used) values('lion', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(16, 16);
insert into accepted_phrase(phrase, staff_id, last_used) values('lion+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(17, 16);
insert into accepted_phrase(phrase, staff_id, last_used) values('lion-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(18, 16);

insert into accepted_phrase(phrase, staff_id, last_used) values('hypo', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(19, 17);
insert into accepted_phrase(phrase, staff_id, last_used) values('hypo+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(20, 17);
insert into accepted_phrase(phrase, staff_id, last_used) values('hypo-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(21, 17);

insert into accepted_phrase(phrase, staff_id, last_used) values('tiger', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(22, 18);
insert into accepted_phrase(phrase, staff_id, last_used) values('tiger+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(23, 18);
insert into accepted_phrase(phrase, staff_id, last_used) values('tiger-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(24, 18);

insert into accepted_phrase(phrase, staff_id, last_used) values('cat', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(25, 19);
insert into accepted_phrase(phrase, staff_id, last_used) values('cat+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(26, 19);
insert into accepted_phrase(phrase, staff_id, last_used) values('cat-', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(27, 19);

insert into accepted_phrase(phrase, staff_id, last_used) values('fox', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(28, 20);
insert into accepted_phrase(phrase, staff_id, last_used) values('fox+', 1, '2018-07-18 10:30:15.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(29, 20);
insert into accepted_phrase(phrase, staff_id, last_used) values('fox-', 1, '2018-07-18 10:30:15.999999999');
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

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#61-FBMQ#1-T#3.jpg', 'Resource #61 FBMQ#1 T#3', 1);
insert into question_resource(question_id, resource_id) values(21, 61);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#62-FBMQ#2-T#3.jpg', 'Resource #62 FBMQ#2 T#3', 1);
insert into question_resource(question_id, resource_id) values(22, 62);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#63-FBMQ#3-T#3.jpg', 'Resource #63 FBMQ#3 T#3', 1);
insert into question_resource(question_id, resource_id) values(23, 63);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#64-FBMQ#4-T#3.jpg', 'Resource #64 FBMQ#4 T#3', 1);
insert into question_resource(question_id, resource_id) values(24, 64);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#65-FBMQ#5-T#3.jpg', 'Resource #65 FBMQ#5 T#3', 1);
insert into question_resource(question_id, resource_id) values(25, 65);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#66-FBMQ#6-T#3.jpg', 'Resource #66 FBMQ#6 T#3', 1);
insert into question_resource(question_id, resource_id) values(26, 66);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#67-FBMQ#7-T#3.jpg', 'Resource #67 FBMQ#7 T#3', 1);
insert into question_resource(question_id, resource_id) values(27, 67);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#68-FBMQ#8-T#3.jpg', 'Resource #68 FBMQ#8 T#3', 1);
insert into question_resource(question_id, resource_id) values(28, 68);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#69-FBMQ#9-T#3.jpg', 'Resource #69 FBMQ#9 T#3', 1);
insert into question_resource(question_id, resource_id) values(29, 69);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#70-FBMQ#10-T#3.jpg', 'Resource #70 FBMQ#10 T#3', 1);
insert into question_resource(question_id, resource_id) values(30, 70);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('tree', 1, 1, 21);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('grass', 1, 1, 21);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('bush', 1, 1, 21);

insert into accepted_phrase(phrase, staff_id, last_used) values('tree-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(31, 1);
insert into accepted_phrase(phrase, staff_id, last_used) values('tree+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(32, 1);

insert into accepted_phrase(phrase, staff_id, last_used) values('grass-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(33, 2);
insert into accepted_phrase(phrase, staff_id, last_used) values('grass+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(34, 2);

insert into accepted_phrase(phrase, staff_id, last_used) values('bush-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(35, 3);
insert into accepted_phrase(phrase, staff_id, last_used) values('bush+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(36, 3);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('sky', 1, 1, 22);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('wind', 1, 1, 22);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('rain', 1, 1, 22);

insert into accepted_phrase(phrase, staff_id, last_used) values('sky-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(37, 4);
insert into accepted_phrase(phrase, staff_id, last_used) values('sky+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(38, 4);

insert into accepted_phrase(phrase, staff_id, last_used) values('wind-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(39, 5);
insert into accepted_phrase(phrase, staff_id, last_used) values('wind+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(40, 5);

insert into accepted_phrase(phrase, staff_id, last_used) values('rain-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(41, 6);
insert into accepted_phrase(phrase, staff_id, last_used) values('rain+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(42, 6);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('water', 1, 1, 23);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('sun', 1, 1, 23);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('sand', 1, 1, 23);

insert into accepted_phrase(phrase, staff_id, last_used) values('water-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(43, 7);
insert into accepted_phrase(phrase, staff_id, last_used) values('water+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(44, 7);

insert into accepted_phrase(phrase, staff_id, last_used) values('sun-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(45, 8);
insert into accepted_phrase(phrase, staff_id, last_used) values('sun+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(46, 8);

insert into accepted_phrase(phrase, staff_id, last_used) values('sand-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(47, 9);
insert into accepted_phrase(phrase, staff_id, last_used) values('sand+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(48, 9);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('train', 1, 1, 24);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('bus', 1, 1, 24);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('plane', 1, 1, 24);

insert into accepted_phrase(phrase, staff_id, last_used) values('train-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(49, 10);
insert into accepted_phrase(phrase, staff_id, last_used) values('train+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(50, 10);

insert into accepted_phrase(phrase, staff_id, last_used) values('bus-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(51, 11);
insert into accepted_phrase(phrase, staff_id, last_used) values('bus+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(52, 11);

insert into accepted_phrase(phrase, staff_id, last_used) values('plane-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(53, 12);
insert into accepted_phrase(phrase, staff_id, last_used) values('plane+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(54, 12);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('gas', 1, 1, 25);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('fire', 1, 1, 25);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('ice', 1, 1, 25);

insert into accepted_phrase(phrase, staff_id, last_used) values('gas-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(55, 13);
insert into accepted_phrase(phrase, staff_id, last_used) values('gas+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(56, 13);

insert into accepted_phrase(phrase, staff_id, last_used) values('fire-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(57, 14);
insert into accepted_phrase(phrase, staff_id, last_used) values('fire+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(58, 14);

insert into accepted_phrase(phrase, staff_id, last_used) values('ice-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(59, 15);
insert into accepted_phrase(phrase, staff_id, last_used) values('ice+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(60, 15);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('fish', 1, 1, 26);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('meat', 1, 1, 26);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('lard', 1, 1, 26);

insert into accepted_phrase(phrase, staff_id, last_used) values('fish-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(61, 16);
insert into accepted_phrase(phrase, staff_id, last_used) values('fish+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(62, 16);

insert into accepted_phrase(phrase, staff_id, last_used) values('meat-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(63, 17);
insert into accepted_phrase(phrase, staff_id, last_used) values('meat+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(64, 17);

insert into accepted_phrase(phrase, staff_id, last_used) values('lard-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(65, 18);
insert into accepted_phrase(phrase, staff_id, last_used) values('lard+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(66, 18);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('chair', 1, 1, 27);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('table', 1, 1, 27);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('stool', 1, 1, 27);

insert into accepted_phrase(phrase, staff_id, last_used) values('chair-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(67, 19);
insert into accepted_phrase(phrase, staff_id, last_used) values('chair+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(68, 19);

insert into accepted_phrase(phrase, staff_id, last_used) values('table-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(69, 20);
insert into accepted_phrase(phrase, staff_id, last_used) values('table+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(70, 20);

insert into accepted_phrase(phrase, staff_id, last_used) values('stool-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(71, 21);
insert into accepted_phrase(phrase, staff_id, last_used) values('stool+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(72, 21);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('candy', 1, 1, 28);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('sweets', 1, 1, 28);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('chocolate', 1, 1, 28);

insert into accepted_phrase(phrase, staff_id, last_used) values('candy-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(73, 22);
insert into accepted_phrase(phrase, staff_id, last_used) values('candy+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(74, 22);

insert into accepted_phrase(phrase, staff_id, last_used) values('sweets-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(75, 23);
insert into accepted_phrase(phrase, staff_id, last_used) values('sweets+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(76, 23);

insert into accepted_phrase(phrase, staff_id, last_used) values('chocolate-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(77, 24);
insert into accepted_phrase(phrase, staff_id, last_used) values('chocolate+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(78, 24);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('bottle', 1, 1, 29);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('glass', 1, 1, 29);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('jar', 1, 1, 29);

insert into accepted_phrase(phrase, staff_id, last_used) values('bottle-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(79, 25);
insert into accepted_phrase(phrase, staff_id, last_used) values('bottle+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(80, 25);

insert into accepted_phrase(phrase, staff_id, last_used) values('glass-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(81, 26);
insert into accepted_phrase(phrase, staff_id, last_used) values('glass+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(82, 26);

insert into accepted_phrase(phrase, staff_id, last_used) values('jar-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(83, 27);
insert into accepted_phrase(phrase, staff_id, last_used) values('jar+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(84, 27);


insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('heart', 1, 1, 30);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('soul', 1, 1, 30);
insert into answer_fbmq(phrase, occurrence, set_id, question_id) values('love', 1, 1, 30);

insert into accepted_phrase(phrase, staff_id, last_used) values('heart-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(85, 28);
insert into accepted_phrase(phrase, staff_id, last_used) values('heart+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(86, 28);

insert into accepted_phrase(phrase, staff_id, last_used) values('soul-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(87, 29);
insert into accepted_phrase(phrase, staff_id, last_used) values('soul+', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(88, 29);

insert into accepted_phrase(phrase, staff_id, last_used) values('love-', 1, '2018-07-18 10:30:15.999999999');
insert into fbmq_phrase(phrase_id, answer_id) values(89, 30);
insert into accepted_phrase(phrase, staff_id, last_used) values('love+', 1, '2018-07-18 10:30:15.999999999');
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

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#71-MQ#1-T#4.jpg', 'Resource #71 MQ#1 T#4', 1);
insert into question_resource(question_id, resource_id) values(31, 71);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#72-MQ#2-T#4.jpg', 'Resource #72 MQ#2 T#4', 1);
insert into question_resource(question_id, resource_id) values(32, 72);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#73-MQ#3-T#4.jpg', 'Resource #73 MQ#3 T#4', 1);
insert into question_resource(question_id, resource_id) values(33, 73);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#74-MQ#4-T#4.jpg', 'Resource #74 MQ#4 T#4', 1);
insert into question_resource(question_id, resource_id) values(34, 74);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#75-MQ#5-T#4.jpg', 'Resource #75 MQ#5 T#4', 1);
insert into question_resource(question_id, resource_id) values(35, 75);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#76-MQ#6-T#4.jpg', 'Resource #76 MQ#6 T#4', 1);
insert into question_resource(question_id, resource_id) values(36, 76);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#77-MQ#7-T#4.jpg', 'Resource #77 MQ#7 T#4', 1);
insert into question_resource(question_id, resource_id) values(37, 77);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#78-MQ#8-T#4.jpg', 'Resource #78 MQ#8 T#4', 1);
insert into question_resource(question_id, resource_id) values(38, 78);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#79-MQ#9-T#4.jpg', 'Resource #79 MQ#9 T#4', 1);
insert into question_resource(question_id, resource_id) values(39, 79);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#80-MQ#10-T#4.jpg', 'Resource #80 MQ#10 T#4', 1);
insert into question_resource(question_id, resource_id) values(40, 80);

/* Q#1 answers*/

insert into answer_mq(left_phrase, right_phrase, question_id) values('white', 'black', 31);
insert into answer_mq(left_phrase, right_phrase, question_id) values('heaven', 'earth', 31);
insert into answer_mq(left_phrase, right_phrase, question_id) values('sea', 'ground', 31);
insert into answer_mq(left_phrase, right_phrase, question_id) values('air', 'fire', 31);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#81-Answer#1-MQ#1-T#4.jpg', 'Resource #81 Answer#1 MQ#1 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(1, 81);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#82-Answer#2-MQ#1-T#4.jpg', 'Resource #82 Answer#2 MQ#1 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(2, 82);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#83-Answer#3-MQ#1-T#4.jpg', 'Resource #83 Answer#3 MQ#1 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(3, 83);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#84-Answer#4-MQ#1-T#4.jpg', 'Resource #84 Answer#4 MQ#1 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(4, 84);

/* Q#2 answers*/

insert into answer_mq(left_phrase, right_phrase, question_id) values('mount', 'plain', 32);
insert into answer_mq(left_phrase, right_phrase, question_id) values('fruit', 'vegetable', 32);
insert into answer_mq(left_phrase, right_phrase, question_id) values('sweet', 'bitter', 32);
insert into answer_mq(left_phrase, right_phrase, question_id) values('man', 'woman', 32);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#85-Answer#1-MQ#2-T#4.jpg', 'Resource #85 Answer#1 MQ#2 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(4, 85);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#86-Answer#2-MQ#2-T#4.jpg', 'Resource #86 Answer#2 MQ#2 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(5, 86);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#87-Answer#3-MQ#2-T#4.jpg', 'Resource #87 Answer#3 MQ#2 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(6, 87);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#88-Answer#4-MQ#2-T#4.jpg', 'Resource #88 Answer#4 MQ#2 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(7, 88);


/* Q#3 answers*/

insert into answer_mq(left_phrase, right_phrase, question_id) values('rich', 'poor', 33);
insert into answer_mq(left_phrase, right_phrase, question_id) values('big', 'small', 33);
insert into answer_mq(left_phrase, right_phrase, question_id) values('cat', 'dog', 33);
insert into answer_mq(left_phrase, right_phrase, question_id) values('weak', 'strong', 33);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#89-Answer#1-MQ#3-T#4.jpg', 'Resource #89 Answer#1 MQ#3 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(8, 89);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#90-Answer#2-MQ#3-T#4.jpg', 'Resource #90 Answer#2 MQ#3 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(9, 90);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#91-Answer#3-MQ#3-T#4.jpg', 'Resource #91 Answer#3 MQ#3 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(10, 91);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#92-Answer#4-MQ#3-T#4.jpg', 'Resource #92 Answer#4 MQ#3 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(11, 92);

/* Q#4 answers*/

insert into answer_mq(left_phrase, right_phrase, question_id) values('healthy', 'ill', 34);
insert into answer_mq(left_phrase, right_phrase, question_id) values('dark', 'light', 34);
insert into answer_mq(left_phrase, right_phrase, question_id) values('sugar', 'salt', 34);
insert into answer_mq(left_phrase, right_phrase, question_id) values('kind', 'angry', 34);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#93-Answer#1-MQ#4-T#4.jpg', 'Resource #93 Answer#1 MQ#4 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(12, 93);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#94-Answer#2-MQ#4-T#4.jpg', 'Resource #94 Answer#2 MQ#4 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(13, 94);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#95-Answer#3-MQ#4-T#4.jpg', 'Resource #95 Answer#3 MQ#4 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(14, 95);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#96-Answer#4-MQ#4-T#4.jpg', 'Resource #96 Answer#4 MQ#4 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(15, 96);


/* Q#5 answers*/

insert into answer_mq(left_phrase, right_phrase, question_id) values('smart', 'stupid', 35);
insert into answer_mq(left_phrase, right_phrase, question_id) values('expensive', 'cheap', 35);
insert into answer_mq(left_phrase, right_phrase, question_id) values('fast', 'slow', 35);
insert into answer_mq(left_phrase, right_phrase, question_id) values('river', 'ground', 35);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#97-Answer#1-MQ#5-T#4.jpg', 'Resource #97 Answer#1 MQ#5 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(16, 97);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#98-Answer#2-MQ#5-T#4.jpg', 'Resource #98 Answer#2 MQ#5 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(17, 98);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#99-Answer#3-MQ#5-T#4.jpg', 'Resource #99 Answer#3 MQ#5 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(18, 99);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#100-Answer#4-MQ#5-T#4.jpg', 'Resource #100 Answer#4 MQ#5 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(19, 100);

/* Q#6 answers*/

insert into answer_mq(left_phrase, right_phrase, question_id) values('city', 'village', 36);
insert into answer_mq(left_phrase, right_phrase, question_id) values('dollar', 'euro', 36);
insert into answer_mq(left_phrase, right_phrase, question_id) values('heart', 'soul', 36);
insert into answer_mq(left_phrase, right_phrase, question_id) values('winter', 'summer', 36);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#101-Answer#1-MQ#6-T#4.jpg', 'Resource #101 Answer#1 MQ#6 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(20, 101);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#102-Answer#2-MQ#6-T#4.jpg', 'Resource #102 Answer#2 MQ#6 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(21, 102);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#103-Answer#3-MQ#6-T#4.jpg', 'Resource #103 Answer#3 MQ#6 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(22, 103);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#104-Answer#4-MQ#6-T#4.jpg', 'Resource #104 Answer#4 MQ#6 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(24, 104);


/* Q#7 answers*/

insert into answer_mq(left_phrase, right_phrase, question_id) values('long', 'short', 37);
insert into answer_mq(left_phrase, right_phrase, question_id) values('walk', 'ride', 37);
insert into answer_mq(left_phrase, right_phrase, question_id) values('wood', 'metal', 37);
insert into answer_mq(left_phrase, right_phrase, question_id) values('art', 'technology', 37);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#105-Answer#1-MQ#7-T#4.jpg', 'Resource #105 Answer#1 MQ#7 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(25, 105);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#106-Answer#2-MQ#7-T#4.jpg', 'Resource #106 Answer#2 MQ#7 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(26, 106);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#107-Answer#3-MQ#7-T#4.jpg', 'Resource #107 Answer#3 MQ#7 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(27, 107);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#108-Answer#4-MQ#7-T#4.jpg', 'Resource #108 Answer#4 MQ#7 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(28, 108);

/* Q#8 answers*/

insert into answer_mq(left_phrase, right_phrase, question_id) values('work', 'relax', 38);
insert into answer_mq(left_phrase, right_phrase, question_id) values('ward', 'cold', 38);
insert into answer_mq(left_phrase, right_phrase, question_id) values('excellent', 'satisfactory', 38);
insert into answer_mq(left_phrase, right_phrase, question_id) values('reality', 'imagination', 38);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#109-Answer#1-MQ#8-T#4.jpg', 'Resource #109 Answer#1 MQ#8 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(29, 109);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#110-Answer#2-MQ#8-T#4.jpg', 'Resource #110 Answer#2 MQ#8 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(30, 110);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#111-Answer#3-MQ#8-T#4.jpg', 'Resource #111 Answer#3 MQ#8 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(31, 111);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#112-Answer#4-MQ#8-T#4.jpg', 'Resource #112 Answer#4 MQ#8 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(32, 112);

/* Q#9 answers*/

insert into answer_mq(left_phrase, right_phrase, question_id) values('humble', 'aristocratic', 39);
insert into answer_mq(left_phrase, right_phrase, question_id) values('wide', 'narrow', 39);
insert into answer_mq(left_phrase, right_phrase, question_id) values('west', 'east', 39);
insert into answer_mq(left_phrase, right_phrase, question_id) values('glad', 'sad', 39);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#113-Answer#1-MQ#9-T#4.jpg', 'Resource #113 Answer#1 MQ#9 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(33, 113);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#114-Answer#2-MQ#9-T#4.jpg', 'Resource #114 Answer#2 MQ#9 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(34, 114);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#115-Answer#3-MQ#9-T#4.jpg', 'Resource #115 Answer#3 MQ#9 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(35, 115);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#116-Answer#4-MQ#9-T#4.jpg', 'Resource #116 Answer#4 MQ#9 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(36, 116);

/* Q#10 answers*/

insert into answer_mq(left_phrase, right_phrase, question_id) values('humid', 'dry', 40);
insert into answer_mq(left_phrase, right_phrase, question_id) values('peace', 'war', 40);
insert into answer_mq(left_phrase, right_phrase, question_id) values('love', 'hate', 40);
insert into answer_mq(left_phrase, right_phrase, question_id) values('day', 'night', 40);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#117-Answer#1-MQ#10-T#4.jpg', 'Resource #117 Answer#1 MQ#10 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(37, 117);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#118-Answer#2-MQ#10-T#4.jpg', 'Resource #118 Answer#2 MQ#10 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(38, 118);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#119-Answer#3-MQ#10-T#4.jpg', 'Resource #119 Answer#3 MQ#10 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(39, 119);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#120-Answer#4-MQ#10-T#4.jpg', 'Resource #120 Answer#4 MQ#10 T#4', 1);
insert into answer_mq_resource(answer_id, resource_id) values(40, 120);


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

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#121-SQ#1-T#5.jpg', 'Resource #121 SQ#1 T#5', 1);
insert into question_resource(question_id, resource_id) values(41, 121);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#122-SQ#2-T#5.jpg', 'Resource #122 SQ#2 T#5', 1);
insert into question_resource(question_id, resource_id) values(42, 122);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#123-SQ#3-T#5.jpg', 'Resource #123 SQ#3 T#5', 1);
insert into question_resource(question_id, resource_id) values(43, 123);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#124-SQ#4-T#5.jpg', 'Resource #124 SQ#4 T#5', 1);
insert into question_resource(question_id, resource_id) values(44, 124);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#125-SQ#5-T#5.jpg', 'Resource #125 SQ#5 T#5', 1);
insert into question_resource(question_id, resource_id) values(45, 125);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#126-SQ#6-T#5.jpg', 'Resource #126 SQ#6 T#5', 1);
insert into question_resource(question_id, resource_id) values(46, 126);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#127-SQ#7-T#5.jpg', 'Resource #127 SQ#7 T#5', 1);
insert into question_resource(question_id, resource_id) values(47, 127);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#128-SQ#8-T#5.jpg', 'Resource #128 SQ#8 T#5', 1);
insert into question_resource(question_id, resource_id) values(48, 128);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#129-SQ#9-T#5.jpg', 'Resource #129 SQ#9 T#5', 1);
insert into question_resource(question_id, resource_id) values(49, 129);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#130-SQ#10-T#5.jpg', 'Resource #130 SQ#10 T#5', 1);
insert into question_resource(question_id, resource_id) values(50, 130);

/* Q#1 answers*/

insert into answer_sq(element, element_order, question_id) values('Monday', 0, 41);
insert into answer_sq(element, element_order, question_id) values('Tuesday', 1, 41);
insert into answer_sq(element, element_order, question_id) values('Wednesday', 2, 41);
insert into answer_sq(element, element_order, question_id) values('Thursday', 3, 41);
insert into answer_sq(element, element_order, question_id) values('Friday', 4, 41);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#131-Answer#1-SQ#1-T#5.jpg', 'Resource #131 Answer#1 SQ#1 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(1, 131);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#132-Answer#2-SQ#1-T#5.jpg', 'Resource #132 Answer#2 SQ#1 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(2, 132);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#133-Answer#3-SQ#1-T#5.jpg', 'Resource #133 Answer#3 SQ#1 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(3, 133);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#134-Answer#4-SQ#1-T#5.jpg', 'Resource #134 Answer#4 SQ#1 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(4, 134);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#135-Answer#5-SQ#1-T#5.jpg', 'Resource #135 Answer#5 SQ#1 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(5, 135);


/* Q#2 answers*/

insert into answer_sq(element, element_order, question_id) values('January', 0, 42);
insert into answer_sq(element, element_order, question_id) values('February', 1, 42);
insert into answer_sq(element, element_order, question_id) values('March', 2, 42);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#136-Answer#1-SQ#2-T#5.jpg', 'Resource #136 Answer#1 SQ#2 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(6, 136);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#137-Answer#2-SQ#2-T#5.jpg', 'Resource #137 Answer#2 SQ#2 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(7, 137);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#138-Answer#3-SQ#2-T#5.jpg', 'Resource #138 Answer#3 SQ#2 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(8, 138);

/* Q#3 answers*/

insert into answer_sq(element, element_order, question_id) values('summer', 0, 43);
insert into answer_sq(element, element_order, question_id) values('autumn', 1, 43);
insert into answer_sq(element, element_order, question_id) values('winter', 2, 43);
insert into answer_sq(element, element_order, question_id) values('spring', 3, 43);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#139-Answer#1-SQ#3-T#5.jpg', 'Resource #139 Answer#1 SQ#3 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(9, 139);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#140-Answer#2-SQ#3-T#5.jpg', 'Resource #140 Answer#2 SQ#3 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(10, 140);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#141-Answer#3-SQ#3-T#5.jpg', 'Resource #141 Answer#3 SQ#3 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(11, 141);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#142-Answer#4-SQ#3-T#5.jpg', 'Resource #142 Answer#3 SQ#3 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(12, 142);

/* Q#4 answers*/

insert into answer_sq(element, element_order, question_id) values('kindergarten', 0, 44);
insert into answer_sq(element, element_order, question_id) values('school', 1, 44);
insert into answer_sq(element, element_order, question_id) values('university', 2, 44);
insert into answer_sq(element, element_order, question_id) values('company', 3, 44);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#143-Answer#1-SQ#4-T#5.jpg', 'Resource #143 Answer#1 SQ#4 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(13, 143);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#144-Answer#2-SQ#4-T#5.jpg', 'Resource #144 Answer#2 SQ#4 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(14, 144);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#145-Answer#3-SQ#4-T#5.jpg', 'Resource #145 Answer#3 SQ#4 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(15, 145);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#146-Answer#4-SQ#4-T#5.jpg', 'Resource #146 Answer#4 SQ#4 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(16, 146);

/* Q#5 answers*/

insert into answer_sq(element, element_order, question_id) values('birth', 0, 45);
insert into answer_sq(element, element_order, question_id) values('grow-up', 1, 45);
insert into answer_sq(element, element_order, question_id) values('marriage', 2, 45);
insert into answer_sq(element, element_order, question_id) values('death', 3, 45);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#147-Answer#1-SQ#5-T#5.jpg', 'Resource #147 Answer#1 SQ#5 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(17, 147);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#148-Answer#2-SQ#5-T#5.jpg', 'Resource #148 Answer#2 SQ#5 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(18, 148);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#149-Answer#3-SQ#5-T#5.jpg', 'Resource #149 Answer#3 SQ#5 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(19, 149);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#150-Answer#4-SQ#5-T#5.jpg', 'Resource #150 Answer#4 SQ#5 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(20, 150);

/* Q#6 answers*/

insert into answer_sq(element, element_order, question_id) values('one', 0, 46);
insert into answer_sq(element, element_order, question_id) values('two', 1, 46);
insert into answer_sq(element, element_order, question_id) values('three', 2, 46);
insert into answer_sq(element, element_order, question_id) values('four', 3, 46);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#151-Answer#1-SQ#6-T#5.jpg', 'Resource #151 Answer#1 SQ#6 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(21, 151);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#152-Answer#2-SQ#6-T#5.jpg', 'Resource #152 Answer#2 SQ#6 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(22, 152);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#153-Answer#3-SQ#6-T#5.jpg', 'Resource #153 Answer#3 SQ#6 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(23, 153);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#154-Answer#4-SQ#6-T#5.jpg', 'Resource #154 Answer#4 SQ#6 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(24, 154);

/* Q#7 answers*/

insert into answer_sq(element, element_order, question_id) values('letter A', 0, 47);
insert into answer_sq(element, element_order, question_id) values('letter B', 1, 47);
insert into answer_sq(element, element_order, question_id) values('letter C', 2, 47);
insert into answer_sq(element, element_order, question_id) values('letter D', 3, 47);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#155-Answer#1-SQ#7-T#5.jpg', 'Resource #155 Answer#1 SQ#7 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(25, 155);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#156-Answer#2-SQ#7-T#5.jpg', 'Resource #156 Answer#2 SQ#7 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(26, 156);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#157-Answer#3-SQ#7-T#5.jpg', 'Resource #157 Answer#3 SQ#7 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(27, 157);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#158-Answer#4-SQ#7-T#5.jpg', 'Resource #158 Answer#4 SQ#7 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(28, 158);

/* Q#8 answers*/

insert into answer_sq(element, element_order, question_id) values('morning', 0, 48);
insert into answer_sq(element, element_order, question_id) values('noon', 1, 48);
insert into answer_sq(element, element_order, question_id) values('afternoon', 2, 48);
insert into answer_sq(element, element_order, question_id) values('evening', 3, 48);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#159-Answer#1-SQ#8-T#5.jpg', 'Resource #159 Answer#1 SQ#8 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(29, 159);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#160-Answer#2-SQ#8-T#5.jpg', 'Resource #160 Answer#2 SQ#8 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(30, 160);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#161-Answer#3-SQ#8-T#5.jpg', 'Resource #161 Answer#3 SQ#8 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(31, 161);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#162-Answer#4-SQ#8-T#5.jpg', 'Resource #162 Answer#4 SQ#8 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(32, 162);

/* Q#9 answers*/

insert into answer_sq(element, element_order, question_id) values('2000', 0, 49);
insert into answer_sq(element, element_order, question_id) values('2001', 1, 49);
insert into answer_sq(element, element_order, question_id) values('2002', 2, 49);
insert into answer_sq(element, element_order, question_id) values('2003', 3, 49);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#163-Answer#1-SQ#9-T#5.jpg', 'Resource #163 Answer#1 SQ#9 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(33, 163);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#164-Answer#2-SQ#9-T#5.jpg', 'Resource #164 Answer#2 SQ#9 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(34, 164);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#165-Answer#3-SQ#9-T#5.jpg', 'Resource #165 Answer#3 SQ#9 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(35, 165);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#166-Answer#4-SQ#9-T#5.jpg', 'Resource #166 Answer#4 SQ#9 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(36, 166);

/* Q#10 answers*/

insert into answer_sq(element, element_order, question_id) values('step 1', 0, 50);
insert into answer_sq(element, element_order, question_id) values('step 2', 1, 50);
insert into answer_sq(element, element_order, question_id) values('step 3', 2, 50);
insert into answer_sq(element, element_order, question_id) values('step 4', 3, 50);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#167-Answer#1-SQ#10-T#5.jpg', 'Resource #167 Answer#1 SQ#10 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(37, 167);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#168-Answer#2-SQ#10-T#5.jpg', 'Resource #168 Answer#2 SQ#10 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(38, 168);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#169-Answer#3-SQ#10-T#5.jpg', 'Resource #169 Answer#3 SQ#10 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(39, 169);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/resource-#170-Answer#4-SQ#10-T#5.jpg', 'Resource #170 Answer#4 SQ#10 T#5', 1);
insert into answer_sq_resource(answer_id, resource_id) values(40, 170);