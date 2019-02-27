insert into theme (name, course_id, created_by, access_id, belongs_to, created) values('Theme #1', 1, 1, 1, 1, CURRENT_TIMESTAMP);
insert into theme (name, course_id, created_by, access_id, belongs_to, created) values('Theme #2', 1, 1, 1, 1, CURRENT_TIMESTAMP);
insert into theme (name, course_id, created_by, access_id, belongs_to, created) values('Theme #3', 1, 1, 1, 1, CURRENT_TIMESTAMP);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource01.jpg', 'QuestionResource#1', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource02.jpg', 'QuestionResource#2', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource03.jpg', 'QuestionResource#3', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource04.jpg', 'QuestionResource#4', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource05.jpg', 'QuestionResource#5', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource06.jpg', 'HelpResource#6', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource07.jpg', 'HelpResource#7', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource08.jpg', 'HelpResource#8', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource09.jpg', 'HelpResource#9', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource10.jpg', 'HelpResource#10', 1);

insert into help(name, text, staff_id) values('helpAvailable name #1', 'Please, refer to Resource #1', 1);
insert into help(name, text, staff_id) values('helpAvailable name #2', 'Please, refer to Resource #2', 1);
insert into help(name, text, staff_id) values('helpAvailable name #3', 'Please, refer to Resource #3', 1);
insert into help(name, text, staff_id) values('helpAvailable name #4', 'Please, refer to Resource #4', 1);
insert into help(name, text, staff_id) values('helpAvailable name #5', 'Please, refer to Resource #5', 1);

insert into help_resource(help_id, resource_id) values(1, 6);
insert into help_resource(help_id, resource_id) values(2, 7);
insert into help_resource(help_id, resource_id) values(3, 8);
insert into help_resource(help_id, resource_id) values(4, 9);
insert into help_resource(help_id, resource_id) values(5, 10);


insert into question (title, level, type_id, theme_id, lang_id) values ('MQ question #1', 1, 4, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #20', 1, 4, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('MQ question #21', 2, 4, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #4', 3, 4, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('MQ question #5', 3, 4, 3, 1);


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



/*Q #1*/
insert into phrase(phrase, staff_id) values('Left #1 (Q1) #1', 1);
insert into phrase(phrase, staff_id) values('Right #1 (Q1) #2', 1);
insert into phrase(phrase, staff_id) values('Left #2 (Q1) #3', 1);
insert into phrase(phrase, staff_id) values('Right #2 (Q1) #4', 1);
insert into phrase(phrase, staff_id) values('Left #3 (Q1) #5', 1);
insert into phrase(phrase, staff_id) values('Right #3 (Q1) #6', 1);
insert into phrase(phrase, staff_id) values('Left #4 (Q1) #7', 1);
insert into phrase(phrase, staff_id) values('Right #4 (Q1) #8', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource11.jpg', 'AnswerResource#11', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource12.jpg', 'AnswerResource#12', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource13.jpg', 'AnswerResource#13', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource14.jpg', 'AnswerResource#14', 1);

insert into phrase_resource(phrase_id, resource_id) values(2, 11);
insert into phrase_resource(phrase_id, resource_id) values(4, 12);
insert into phrase_resource(phrase_id, resource_id) values(6, 13);
insert into phrase_resource(phrase_id, resource_id) values(8, 14);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(1, 2, 1);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(3, 4, 1);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(5, 6, 1);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(7, 8, 1);


/*Q #2*/
insert into phrase(phrase, staff_id) values('Left #1 (Q2) #9', 1);
insert into phrase(phrase, staff_id) values('Right #1 (Q2) #10', 1);
insert into phrase(phrase, staff_id) values('Left #2 (Q2) #11', 1);
insert into phrase(phrase, staff_id) values('Right #2 (Q2) #12', 1);
insert into phrase(phrase, staff_id) values('Left #3 (Q2) #13', 1);
insert into phrase(phrase, staff_id) values('Right #3 (Q2) #14', 1);
insert into phrase(phrase, staff_id) values('Left #4 (Q2) #15', 1);
insert into phrase(phrase, staff_id) values('Right #4 (Q2) #16', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource15.jpg', 'AnswerResource#15', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource16.jpg', 'AnswerResource#16', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource17.jpg', 'AnswerResource#17', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource18.jpg', 'AnswerResource#18', 1);

insert into phrase_resource(phrase_id, resource_id) values(10, 15);
insert into phrase_resource(phrase_id, resource_id) values(12, 16);
insert into phrase_resource(phrase_id, resource_id) values(14, 17);
insert into phrase_resource(phrase_id, resource_id) values(16, 18);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(9, 10, 2);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(11, 12, 2);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(13, 14, 2);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(15, 16, 2);


/*Q #3*/
insert into phrase(phrase, staff_id) values('Left #1 (Q3) #17', 1);
insert into phrase(phrase, staff_id) values('Right #1 (Q3) #18', 1);
insert into phrase(phrase, staff_id) values('Left #2 (Q3) #19', 1);
insert into phrase(phrase, staff_id) values('Right #2 (Q3) #20', 1);
insert into phrase(phrase, staff_id) values('Left #3 (Q3) #21', 1);
insert into phrase(phrase, staff_id) values('Right #3 (Q3) #22', 1);
insert into phrase(phrase, staff_id) values('Left #4 (Q3) #23', 1);
insert into phrase(phrase, staff_id) values('Right #4 (Q3) #24', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource19.jpg', 'AnswerResource#19', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource20.jpg', 'AnswerResource#20', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource21.jpg', 'AnswerResource#21', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource22.jpg', 'AnswerResource#22', 1);

insert into phrase_resource(phrase_id, resource_id) values(18, 19);
insert into phrase_resource(phrase_id, resource_id) values(20, 20);
insert into phrase_resource(phrase_id, resource_id) values(22, 21);
insert into phrase_resource(phrase_id, resource_id) values(24, 22);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(17, 18, 3);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(19, 20, 3);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(21, 22, 3);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(23, 24, 3);


/*Q #4*/
insert into phrase(phrase, staff_id) values('Left #1 (Q4) #25', 1);
insert into phrase(phrase, staff_id) values('Right #1 (Q4) #26', 1);
insert into phrase(phrase, staff_id) values('Left #2 (Q4) #27', 1);
insert into phrase(phrase, staff_id) values('Right #2 (Q4) #28', 1);
insert into phrase(phrase, staff_id) values('Left #3 (Q4) #29', 1);
insert into phrase(phrase, staff_id) values('Right #3 (Q4) #30', 1);
insert into phrase(phrase, staff_id) values('Left #4 (Q4) #31', 1);
insert into phrase(phrase, staff_id) values('Right #4 (Q4) #32', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource23.jpg', 'AnswerResource#23', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource24.jpg', 'AnswerResource#24', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource25.jpg', 'AnswerResource#25', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource26.jpg', 'AnswerResource#26', 1);

insert into phrase_resource(phrase_id, resource_id) values(26, 23);
insert into phrase_resource(phrase_id, resource_id) values(28, 24);
insert into phrase_resource(phrase_id, resource_id) values(30, 25);
insert into phrase_resource(phrase_id, resource_id) values(32, 26);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(25, 26, 4);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(27, 28, 4);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(29, 30, 4);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(31, 22, 4);


/*Q #5*/
insert into phrase(phrase, staff_id) values('Left #1 (Q5) #33', 1);
insert into phrase(phrase, staff_id) values('Right #1 (Q5) #34', 1);
insert into phrase(phrase, staff_id) values('Left #2 (Q5) #35', 1);
insert into phrase(phrase, staff_id) values('Right #2 (Q5) #36', 1);
insert into phrase(phrase, staff_id) values('Left #3 (Q5) #37', 1);
insert into phrase(phrase, staff_id) values('Right #3 (Q5) #38', 1);
insert into phrase(phrase, staff_id) values('Left #4 (Q5) #39', 1);
insert into phrase(phrase, staff_id) values('Right #4 (Q5) #40', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource27.jpg', 'AnswerResource#27', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource28.jpg', 'AnswerResource#28', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource29.jpg', 'AnswerResource#29', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource30.jpg', 'AnswerResource#30', 1);

insert into phrase_resource(phrase_id, resource_id) values(34, 27);
insert into phrase_resource(phrase_id, resource_id) values(36, 28);
insert into phrase_resource(phrase_id, resource_id) values(38, 29);
insert into phrase_resource(phrase_id, resource_id) values(40, 30);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(33, 34, 5);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(35, 36, 5);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(37, 38, 5);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(39, 40, 5);




