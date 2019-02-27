insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted, grading_id, access_id)
    values('Scheme #1', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 1);
insert into scheme_four_point(scheme_id, four_point_id) values(1, 1);
insert into theme(name, is_deleted, course_id, created_by, access_id, belongs_to, created) values('Theme#1', 0, 1, 1, 1, 1, CURRENT_TIMESTAMP);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 1, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 1, 5, 0, 0);

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

insert into result(scheme_id, user_id, dep_id, percent, grade, is_passed, session_ended, session_lasted, is_timeouted)
       values(1, 1, 1, 85.00, 5, 1, '2018-06-27 13:35:10.999999999', 12000, 0);
