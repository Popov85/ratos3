/*Questions Theme #1 (All MQ) 10 pieces, full equipment*/

insert into question (title, level, type_id, theme_id) values ('Matcher question #1 T#4', 1, 4, 1);
insert into question (title, level, type_id, theme_id) values ('Matcher question #2 T#4', 1, 4, 1);
insert into question (title, level, type_id, theme_id) values ('Matcher question #3 T#4', 1, 4, 1);
insert into question (title, level, type_id, theme_id) values ('Matcher question #4 T#4', 1, 4, 1);
insert into question (title, level, type_id, theme_id) values ('Matcher question #5 T#4', 1, 4, 1);
insert into question (title, level, type_id, theme_id) values ('Matcher question #6 T#4', 1, 4, 1);
insert into question (title, level, type_id, theme_id) values ('Matcher question #7 T#4', 1, 4, 1);
insert into question (title, level, type_id, theme_id) values ('Matcher question #8 T#4', 1, 4, 1);
insert into question (title, level, type_id, theme_id) values ('Matcher question #9 T#4', 1, 4, 1);
insert into question (title, level, type_id, theme_id) values ('Matcher question #10 T#4', 1, 4, 1);

insert into help(name, text, staff_id) values('Help #31 MQ#1 T#4', 'Please, refer to section #1 MQ#1 T#4', 1);
insert into question_help(question_id, help_id) values(1,1);
insert into help(name, text, staff_id) values('Help #32 MQ#2 T#4', 'Please, refer to section #2 MQ#2 T#4', 1);
insert into question_help(question_id, help_id) values(2,2);
insert into help(name, text, staff_id) values('Help #33 MQ#3 T#4', 'Please, refer to section #3 MQ#3 T#4', 1);
insert into question_help(question_id, help_id) values(3,3);
insert into help(name, text, staff_id) values('Help #34 MQ#4 T#4', 'Please, refer to section #4 MQ#4 T#4', 1);
insert into question_help(question_id, help_id) values(4,4);
insert into help(name, text, staff_id) values('Help #35 MQ#5 T#4', 'Please, refer to section #5 MQ#5 T#4', 1);
insert into question_help(question_id, help_id) values(5,5);
insert into help(name, text, staff_id) values('Help #36 MQ#6 T#4', 'Please, refer to section #6 MQ#6 T#4', 1);
insert into question_help(question_id, help_id) values(6,6);
insert into help(name, text, staff_id) values('Help #37 MQ#7 T#4', 'Please, refer to section #7 MQ#7 T#4', 1);
insert into question_help(question_id, help_id) values(7,7);
insert into help(name, text, staff_id) values('Help #38 MQ#8 T#4', 'Please, refer to section #8 MQ#8 T#4', 1);
insert into question_help(question_id, help_id) values(8,8);
insert into help(name, text, staff_id) values('Help #39 MQ#9 T#4', 'Please, refer to section #9 MQ#9 T#4', 1);
insert into question_help(question_id, help_id) values(9,9);
insert into help(name, text, staff_id) values('Help #40 MQ#10 T#4', 'Please, refer to section #10 MQ#10 T#4', 1);
insert into question_help(question_id, help_id) values(10,10);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#71-MQ#1-T#4.jpg', 'Resource #71 MQ#1 T#4', 1);
insert into question_resource(question_id, resource_id) values(1, 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#72-MQ#2-T#4.jpg', 'Resource #72 MQ#2 T#4', 1);
insert into question_resource(question_id, resource_id) values(2, 2);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#73-MQ#3-T#4.jpg', 'Resource #73 MQ#3 T#4', 1);
insert into question_resource(question_id, resource_id) values(3, 3);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#74-MQ#4-T#4.jpg', 'Resource #74 MQ#4 T#4', 1);
insert into question_resource(question_id, resource_id) values(4, 4);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#75-MQ#5-T#4.jpg', 'Resource #75 MQ#5 T#4', 1);
insert into question_resource(question_id, resource_id) values(5, 5);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#76-MQ#6-T#4.jpg', 'Resource #76 MQ#6 T#4', 1);
insert into question_resource(question_id, resource_id) values(6, 6);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#77-MQ#7-T#4.jpg', 'Resource #77 MQ#7 T#4', 1);
insert into question_resource(question_id, resource_id) values(7, 7);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#78-MQ#8-T#4.jpg', 'Resource #78 MQ#8 T#4', 1);
insert into question_resource(question_id, resource_id) values(8, 8);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#79-MQ#9-T#4.jpg', 'Resource #79 MQ#9 T#4', 1);
insert into question_resource(question_id, resource_id) values(9, 9);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#80-MQ#10-T#4.jpg', 'Resource #80 MQ#10 T#4', 1);
insert into question_resource(question_id, resource_id) values(10, 10);

/* Q#1 answers*/

insert into phrase(phrase, staff_id) values('white', 1);
insert into phrase(phrase, staff_id) values('black', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#81-Answer#1-MQ#1-T#4.jpg', 'Resource #81 Answer#1 MQ#1 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(2, 1);

insert into phrase(phrase, staff_id) values('heaven', 1);
insert into phrase(phrase, staff_id) values('earth', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#82-Answer#2-MQ#1-T#4.jpg', 'Resource #82 Answer#2 MQ#1 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(4, 2);

insert into phrase(phrase, staff_id) values('sea', 1);
insert into phrase(phrase, staff_id) values('ground', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#83-Answer#3-MQ#1-T#4.jpg', 'Resource #83 Answer#3 MQ#1 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(6, 3);

insert into phrase(phrase, staff_id) values('air', 1);
insert into phrase(phrase, staff_id) values('fire', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#84-Answer#4-MQ#1-T#4.jpg', 'Resource #84 Answer#4 MQ#1 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(8, 4);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(1, 2, 1);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(3, 4, 1);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(5, 6, 1);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(7, 8, 1);



/* Q#2 answers*/
insert into phrase(phrase, staff_id) values('mount', 1);
insert into phrase(phrase, staff_id) values('plain', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#85-Answer#1-MQ#2-T#4.jpg', 'Resource #85 Answer#1 MQ#2 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(10, 5);

insert into phrase(phrase, staff_id) values('fruit', 1);
insert into phrase(phrase, staff_id) values('vegetable', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#86-Answer#2-MQ#2-T#4.jpg', 'Resource #86 Answer#2 MQ#2 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(12, 6);

insert into phrase(phrase, staff_id) values('sweet', 1);
insert into phrase(phrase, staff_id) values('bitter', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#87-Answer#3-MQ#2-T#4.jpg', 'Resource #87 Answer#3 MQ#2 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(14, 7);

insert into phrase(phrase, staff_id) values('man', 1);
insert into phrase(phrase, staff_id) values('woman', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#88-Answer#4-MQ#2-T#4.jpg', 'Resource #88 Answer#4 MQ#2 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(16, 8);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(9, 10, 2);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(11, 12, 2);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(13, 14, 2);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(15, 16, 2);


/* Q#3 answers*/

insert into phrase(phrase, staff_id) values('rich', 1);
insert into phrase(phrase, staff_id) values('poor', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#89-Answer#1-MQ#3-T#4.jpg', 'Resource #89 Answer#1 MQ#3 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(18, 9);

insert into phrase(phrase, staff_id) values('big', 1);
insert into phrase(phrase, staff_id) values('small', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#90-Answer#2-MQ#3-T#4.jpg', 'Resource #90 Answer#2 MQ#3 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(20, 10);

insert into phrase(phrase, staff_id) values('cats', 1);
insert into phrase(phrase, staff_id) values('dogs', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#91-Answer#3-MQ#3-T#4.jpg', 'Resource #91 Answer#3 MQ#3 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(22, 11);

insert into phrase(phrase, staff_id) values('weak', 1);
insert into phrase(phrase, staff_id) values('strong', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#92-Answer#4-MQ#3-T#4.jpg', 'Resource #92 Answer#4 MQ#3 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(24, 12);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(17, 18, 3);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(19, 20, 3);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(21, 22, 3);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(23, 24, 3);


/* Q#4 answers*/
insert into phrase(phrase, staff_id) values('healthy', 1);
insert into phrase(phrase, staff_id) values('ill', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#93-Answer#1-MQ#4-T#4.jpg', 'Resource #93 Answer#1 MQ#4 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(26, 13);

insert into phrase(phrase, staff_id) values('dark', 1);
insert into phrase(phrase, staff_id) values('light', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#94-Answer#2-MQ#4-T#4.jpg', 'Resource #94 Answer#2 MQ#4 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(28, 14);

insert into phrase(phrase, staff_id) values('sugar', 1);
insert into phrase(phrase, staff_id) values('salt', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#95-Answer#3-MQ#4-T#4.jpg', 'Resource #95 Answer#3 MQ#4 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(30, 15);

insert into phrase(phrase, staff_id) values('kind', 1);
insert into phrase(phrase, staff_id) values('angry', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#96-Answer#4-MQ#4-T#4.jpg', 'Resource #96 Answer#4 MQ#4 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(32, 16);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(25, 26, 4);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(27, 28, 4);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(29, 30, 4);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(31, 32, 4);


/* Q#5 answers*/
insert into phrase(phrase, staff_id) values('smart', 1);
insert into phrase(phrase, staff_id) values('stupid', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#97-Answer#1-MQ#5-T#4.jpg', 'Resource #97 Answer#1 MQ#5 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(34, 17);

insert into phrase(phrase, staff_id) values('expensive', 1);
insert into phrase(phrase, staff_id) values('cheap', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#98-Answer#2-MQ#5-T#4.jpg', 'Resource #98 Answer#2 MQ#5 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(36, 18);

insert into phrase(phrase, staff_id) values('fast', 1);
insert into phrase(phrase, staff_id) values('slow', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#99-Answer#3-MQ#5-T#4.jpg', 'Resource #99 Answer#3 MQ#5 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(38, 19);

insert into phrase(phrase, staff_id) values('river', 1);
insert into phrase(phrase, staff_id) values('lake', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#100-Answer#4-MQ#5-T#4.jpg', 'Resource #100 Answer#4 MQ#5 T#4', 1);

insert into phrase_resource(phrase_id, resource_id) values(40, 20);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(33, 34, 5);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(35, 36, 5);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(37, 38, 5);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(39, 40, 5);


/* Q#6 answers*/
insert into phrase(phrase, staff_id) values('city', 1);
insert into phrase(phrase, staff_id) values('village', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#101-Answer#1-MQ#6-T#4.jpg', 'Resource #101 Answer#1 MQ#6 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(42, 21);

insert into phrase(phrase, staff_id) values('dollar', 1);
insert into phrase(phrase, staff_id) values('euro', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#102-Answer#2-MQ#6-T#4.jpg', 'Resource #102 Answer#2 MQ#6 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(44, 22);

insert into phrase(phrase, staff_id) values('heart', 1);
insert into phrase(phrase, staff_id) values('soul', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#103-Answer#3-MQ#6-T#4.jpg', 'Resource #103 Answer#3 MQ#6 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(46, 23);

insert into phrase(phrase, staff_id) values('cold winter', 1);
insert into phrase(phrase, staff_id) values('hot summer', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#104-Answer#4-MQ#6-T#4.jpg', 'Resource #104 Answer#4 MQ#6 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(48, 24);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(41, 42, 6);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(43, 44, 6);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(45, 46, 6);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(47, 48, 6);


/* Q#7 answers*/
insert into phrase(phrase, staff_id) values('long', 1);
insert into phrase(phrase, staff_id) values('short', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#105-Answer#1-MQ#7-T#4.jpg', 'Resource #105 Answer#1 MQ#7 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(50, 25);

insert into phrase(phrase, staff_id) values('walk', 1);
insert into phrase(phrase, staff_id) values('ride', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#106-Answer#2-MQ#7-T#4.jpg', 'Resource #106 Answer#2 MQ#7 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(52, 26);

insert into phrase(phrase, staff_id) values('wood', 1);
insert into phrase(phrase, staff_id) values('steel', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#107-Answer#3-MQ#7-T#4.jpg', 'Resource #107 Answer#3 MQ#7 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(54, 27);

insert into phrase(phrase, staff_id) values('art', 1);
insert into phrase(phrase, staff_id) values('technology', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#108-Answer#4-MQ#7-T#4.jpg', 'Resource #108 Answer#4 MQ#7 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(56, 28);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(49, 50, 7);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(51, 52, 7);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(53, 54, 7);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(55, 56, 7);


/* Q#8 answers*/
insert into phrase(phrase, staff_id) values('work', 1);
insert into phrase(phrase, staff_id) values('relax', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#109-Answer#1-MQ#8-T#4.jpg', 'Resource #109 Answer#1 MQ#8 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(58, 29);

insert into phrase(phrase, staff_id) values('warm', 1);
insert into phrase(phrase, staff_id) values('cold', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#110-Answer#2-MQ#8-T#4.jpg', 'Resource #110 Answer#2 MQ#8 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(60, 30);

insert into phrase(phrase, staff_id) values('excellent', 1);
insert into phrase(phrase, staff_id) values('satisfactory', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#111-Answer#3-MQ#8-T#4.jpg', 'Resource #111 Answer#3 MQ#8 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(62, 31);

insert into phrase(phrase, staff_id) values('reality', 1);
insert into phrase(phrase, staff_id) values('imagination', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#112-Answer#4-MQ#8-T#4.jpg', 'Resource #112 Answer#4 MQ#8 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(64, 32);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(57, 58, 8);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(59, 60, 8);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(61, 62, 8);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(63, 64, 8);


/* Q#9 answers*/

insert into phrase(phrase, staff_id) values('humble', 1);
insert into phrase(phrase, staff_id) values('aristocratic', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#113-Answer#1-MQ#9-T#4.jpg', 'Resource #113 Answer#1 MQ#9 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(66, 33);

insert into phrase(phrase, staff_id) values('wide', 1);
insert into phrase(phrase, staff_id) values('narrow', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#114-Answer#2-MQ#9-T#4.jpg', 'Resource #114 Answer#2 MQ#9 T#4', 1);

insert into phrase_resource(phrase_id, resource_id) values(68, 34);

insert into phrase(phrase, staff_id) values('west', 1);
insert into phrase(phrase, staff_id) values('east', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#115-Answer#3-MQ#9-T#4.jpg', 'Resource #115 Answer#3 MQ#9 T#4', 1);

insert into phrase_resource(phrase_id, resource_id) values(70, 35);

insert into phrase(phrase, staff_id) values('glad', 1);
insert into phrase(phrase, staff_id) values('sad', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#116-Answer#4-MQ#9-T#4.jpg', 'Resource #116 Answer#4 MQ#9 T#4', 1);

insert into phrase_resource(phrase_id, resource_id) values(72, 36);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(65, 66, 9);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(67, 68, 9);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(69, 70, 9);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(71, 72, 9);


/* Q#10 answers*/
insert into phrase(phrase, staff_id) values('humid', 1);
insert into phrase(phrase, staff_id) values('dry', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#117-Answer#1-MQ#10-T#4.jpg', 'Resource #117 Answer#1 MQ#10 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(74, 37);

insert into phrase(phrase, staff_id) values('peace', 1);
insert into phrase(phrase, staff_id) values('war', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#118-Answer#2-MQ#10-T#4.jpg', 'Resource #118 Answer#2 MQ#10 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(76, 38);

insert into phrase(phrase, staff_id) values('love', 1);
insert into phrase(phrase, staff_id) values('hate', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#119-Answer#3-MQ#10-T#4.jpg', 'Resource #119 Answer#3 MQ#10 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(78, 39);

insert into phrase(phrase, staff_id) values('day', 1);
insert into phrase(phrase, staff_id) values('night', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#120-Answer#4-MQ#10-T#4.jpg', 'Resource #120 Answer#4 MQ#10 T#4', 1);
insert into phrase_resource(phrase_id, resource_id) values(80, 40);

insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(73, 74, 10);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(75, 76, 10);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(77, 78, 10);
insert into answer_mq(left_phrase_id, right_phrase_id, question_id) values(79, 80, 10);
