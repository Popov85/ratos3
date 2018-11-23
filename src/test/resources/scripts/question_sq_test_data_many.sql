insert into theme (name, course_id) values('Java Operators', 1);
insert into theme (name, course_id) values('Java Strings', 1);
insert into theme (name, course_id) values('Java Generics', 1);

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


insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #1', 1, 5, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #2', 1, 5, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #3', 2, 5, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #4', 3, 5, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #5', 3, 5, 3, 1);


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


/*-------REFACTOR----------*/

insert into phrase(phrase, staff_id) values('Phrase #1 (Q1) #1', 1);
insert into phrase(phrase, staff_id) values('Phrase #2 (Q1) #2', 1);
insert into phrase(phrase, staff_id) values('Phrase #3 (Q1) #3', 1);
insert into phrase(phrase, staff_id) values('Phrase #4 (Q1) #4', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource11.jpg', 'AnswerResource#11', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource12.jpg', 'AnswerResource#12', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource13.jpg', 'AnswerResource#13', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource14.jpg', 'AnswerResource#14', 1);

insert into phrase_resource(phrase_id, resource_id) values(1, 11);
insert into phrase_resource(phrase_id, resource_id) values(2, 12);
insert into phrase_resource(phrase_id, resource_id) values(3, 13);
insert into phrase_resource(phrase_id, resource_id) values(4, 14);

insert into answer_sq(phrase_id, phrase_order, question_id) values(1, 0, 1);
insert into answer_sq(phrase_id, phrase_order, question_id) values(2, 1, 1);
insert into answer_sq(phrase_id, phrase_order, question_id) values(3, 2, 1);
insert into answer_sq(phrase_id, phrase_order, question_id) values(4, 3, 1);


/*Q #2*/
insert into phrase(phrase, staff_id) values('Phrase #1 (Q2) #5', 1);
insert into phrase(phrase, staff_id) values('Phrase #2 (Q2) #6', 1);
insert into phrase(phrase, staff_id) values('Phrase #3 (Q2) #7', 1);
insert into phrase(phrase, staff_id) values('Phrase #4 (Q2) #8', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource15.jpg', 'AnswerResource#15', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource16.jpg', 'AnswerResource#16', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource17.jpg', 'AnswerResource#17', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource18.jpg', 'AnswerResource#18', 1);

insert into phrase_resource(phrase_id, resource_id) values(5, 15);
insert into phrase_resource(phrase_id, resource_id) values(6, 16);
insert into phrase_resource(phrase_id, resource_id) values(7, 17);
insert into phrase_resource(phrase_id, resource_id) values(8, 18);

insert into answer_sq(phrase_id, phrase_order, question_id) values(5, 0, 2);
insert into answer_sq(phrase_id, phrase_order, question_id) values(6, 1, 2);
insert into answer_sq(phrase_id, phrase_order, question_id) values(7, 2, 2);
insert into answer_sq(phrase_id, phrase_order, question_id) values(8, 3, 2);


/*Q #3*/
insert into phrase(phrase, staff_id) values('Phrase #1 (Q3) #9', 1);
insert into phrase(phrase, staff_id) values('Phrase #2 (Q3) #10', 1);
insert into phrase(phrase, staff_id) values('Phrase #3 (Q3) #11', 1);
insert into phrase(phrase, staff_id) values('Phrase #4 (Q3) #12', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource19.jpg', 'AnswerResource#19', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource20.jpg', 'AnswerResource#20', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource21.jpg', 'AnswerResource#21', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource22.jpg', 'AnswerResource#22', 1);

insert into phrase_resource(phrase_id, resource_id) values(9, 19);
insert into phrase_resource(phrase_id, resource_id) values(10, 20);
insert into phrase_resource(phrase_id, resource_id) values(11, 21);
insert into phrase_resource(phrase_id, resource_id) values(12, 22);

insert into answer_sq(phrase_id, phrase_order, question_id) values(9, 0, 3);
insert into answer_sq(phrase_id, phrase_order, question_id) values(10, 1, 3);
insert into answer_sq(phrase_id, phrase_order, question_id) values(11, 2, 3);
insert into answer_sq(phrase_id, phrase_order, question_id) values(12, 3, 3);

/*Q #4*/
insert into phrase(phrase, staff_id) values('Phrase #1 (Q4) #13', 1);
insert into phrase(phrase, staff_id) values('Phrase #2 (Q4) #14', 1);
insert into phrase(phrase, staff_id) values('Phrase #3 (Q4) #15', 1);
insert into phrase(phrase, staff_id) values('Phrase #4 (Q4) #16', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource23.jpg', 'AnswerResource#23', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource24.jpg', 'AnswerResource#24', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource25.jpg', 'AnswerResource#25', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource26.jpg', 'AnswerResource#26', 1);

insert into phrase_resource(phrase_id, resource_id) values(13, 23);
insert into phrase_resource(phrase_id, resource_id) values(14, 24);
insert into phrase_resource(phrase_id, resource_id) values(15, 25);
insert into phrase_resource(phrase_id, resource_id) values(16, 26);

insert into answer_sq(phrase_id, phrase_order, question_id) values(13, 0, 4);
insert into answer_sq(phrase_id, phrase_order, question_id) values(14, 1, 4);
insert into answer_sq(phrase_id, phrase_order, question_id) values(15, 2, 4);
insert into answer_sq(phrase_id, phrase_order, question_id) values(16, 3, 4);

/*Q #5*/
insert into phrase(phrase, staff_id) values('Phrase #1 (Q5) #17', 1);
insert into phrase(phrase, staff_id) values('Phrase #2 (Q5) #18', 1);
insert into phrase(phrase, staff_id) values('Phrase #3 (Q5) #19', 1);
insert into phrase(phrase, staff_id) values('Phrase #4 (Q5) #20', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource27.jpg', 'AnswerResource#27', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource28.jpg', 'AnswerResource#28', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource29.jpg', 'AnswerResource#29', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/Resource30.jpg', 'AnswerResource#30', 1);

insert into phrase_resource(phrase_id, resource_id) values(17, 27);
insert into phrase_resource(phrase_id, resource_id) values(18, 28);
insert into phrase_resource(phrase_id, resource_id) values(19, 29);
insert into phrase_resource(phrase_id, resource_id) values(20, 30);

insert into answer_sq(phrase_id, phrase_order, question_id) values(17, 0, 5);
insert into answer_sq(phrase_id, phrase_order, question_id) values(18, 1, 5);
insert into answer_sq(phrase_id, phrase_order, question_id) values(19, 2, 5);
insert into answer_sq(phrase_id, phrase_order, question_id) values(20, 3, 5);