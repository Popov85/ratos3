/*Questions Theme #1 (All SQ) 10 pieces, full equipment*/

insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #1 T#1', 1, 5, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #2 T#1', 1, 5, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #3 T#1', 1, 5, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #4 T#1', 1, 5, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #5 T#1', 1, 5, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #6 T#1', 1, 5, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #7 T#1', 1, 5, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #8 T#1', 1, 5, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #9 T#1', 1, 5, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #10 T#1', 1, 5, 1, 1);

insert into help(name, text, staff_id) values('Help #41 SQ#1 T#5', 'Please, refer to section #1 SQ#1 T#5', 1);
insert into question_help(question_id, help_id) values(1,1);
insert into help(name, text, staff_id) values('Help #42 SQ#2 T#5', 'Please, refer to section #2 SQ#2 T#5', 1);
insert into question_help(question_id, help_id) values(2,2);
insert into help(name, text, staff_id) values('Help #43 SQ#3 T#5', 'Please, refer to section #3 SQ#3 T#5', 1);
insert into question_help(question_id, help_id) values(3,3);
insert into help(name, text, staff_id) values('Help #44 SQ#4 T#5', 'Please, refer to section #4 SQ#4 T#5', 1);
insert into question_help(question_id, help_id) values(4,4);
insert into help(name, text, staff_id) values('Help #45 SQ#5 T#5', 'Please, refer to section #5 SQ#5 T#5', 1);
insert into question_help(question_id, help_id) values(5,5);
insert into help(name, text, staff_id) values('Help #46 SQ#6 T#5', 'Please, refer to section #6 SQ#6 T#5', 1);
insert into question_help(question_id, help_id) values(6,6);
insert into help(name, text, staff_id) values('Help #47 SQ#7 T#5', 'Please, refer to section #7 SQ#7 T#5', 1);
insert into question_help(question_id, help_id) values(7,7);
insert into help(name, text, staff_id) values('Help #48 SQ#8 T#5', 'Please, refer to section #8 SQ#8 T#5', 1);
insert into question_help(question_id, help_id) values(8,8);
insert into help(name, text, staff_id) values('Help #49 SQ#9 T#5', 'Please, refer to section #9 SQ#9 T#5', 1);
insert into question_help(question_id, help_id) values(9,9);
insert into help(name, text, staff_id) values('Help #50 SQ#10 T#5', 'Please, refer to section #10 SQ#10 T#5', 1);
insert into question_help(question_id, help_id) values(10,10);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#121-SQ#1-T#5.jpg', 'Resource #121 SQ#1 T#5', 1);
insert into question_resource(question_id, resource_id) values(1, 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#122-SQ#2-T#5.jpg', 'Resource #122 SQ#2 T#5', 1);
insert into question_resource(question_id, resource_id) values(2, 2);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#123-SQ#3-T#5.jpg', 'Resource #123 SQ#3 T#5', 1);
insert into question_resource(question_id, resource_id) values(3, 3);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#124-SQ#4-T#5.jpg', 'Resource #124 SQ#4 T#5', 1);
insert into question_resource(question_id, resource_id) values(4, 4);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#125-SQ#5-T#5.jpg', 'Resource #125 SQ#5 T#5', 1);
insert into question_resource(question_id, resource_id) values(5, 5);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#126-SQ#6-T#5.jpg', 'Resource #126 SQ#6 T#5', 1);
insert into question_resource(question_id, resource_id) values(6, 6);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#127-SQ#7-T#5.jpg', 'Resource #127 SQ#7 T#5', 1);
insert into question_resource(question_id, resource_id) values(7, 7);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#128-SQ#8-T#5.jpg', 'Resource #128 SQ#8 T#5', 1);
insert into question_resource(question_id, resource_id) values(8, 8);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#129-SQ#9-T#5.jpg', 'Resource #129 SQ#9 T#5', 1);
insert into question_resource(question_id, resource_id) values(9, 9);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#130-SQ#10-T#5.jpg', 'Resource #130 SQ#10 T#5', 1);
insert into question_resource(question_id, resource_id) values(10, 10);

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

insert into phrase_resource(phrase_id, resource_id) values(1, 11);
insert into phrase_resource(phrase_id, resource_id) values(2, 12);
insert into phrase_resource(phrase_id, resource_id) values(3, 13);
insert into phrase_resource(phrase_id, resource_id) values(4, 14);
insert into phrase_resource(phrase_id, resource_id) values(5, 15);

insert into answer_sq(phrase_id, phrase_order, question_id) values(1, 0, 1);
insert into answer_sq(phrase_id, phrase_order, question_id) values(2, 1, 1);
insert into answer_sq(phrase_id, phrase_order, question_id) values(3, 2, 1);
insert into answer_sq(phrase_id, phrase_order, question_id) values(4, 3, 1);
insert into answer_sq(phrase_id, phrase_order, question_id) values(5, 4, 1);


/* Q#2 answers*/
insert into phrase(phrase, staff_id) values('January', 1);
insert into phrase(phrase, staff_id) values('February', 1);
insert into phrase(phrase, staff_id) values('March', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#136-Answer#1-SQ#2-T#5.jpg', 'Resource #136 Answer#1 SQ#2 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#137-Answer#2-SQ#2-T#5.jpg', 'Resource #137 Answer#2 SQ#2 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#138-Answer#3-SQ#2-T#5.jpg', 'Resource #138 Answer#3 SQ#2 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(6, 16);
insert into phrase_resource(phrase_id, resource_id) values(7, 17);
insert into phrase_resource(phrase_id, resource_id) values(8, 18);

insert into answer_sq(phrase_id, phrase_order, question_id) values(6, 0, 2);
insert into answer_sq(phrase_id, phrase_order, question_id) values(7, 1, 2);
insert into answer_sq(phrase_id, phrase_order, question_id) values(8, 2, 2);


/* Q#3 answers*/
insert into phrase(phrase, staff_id) values('summer', 1);
insert into phrase(phrase, staff_id) values('autumn', 1);
insert into phrase(phrase, staff_id) values('winter', 1);
insert into phrase(phrase, staff_id) values('spring', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#139-Answer#1-SQ#3-T#5.jpg', 'Resource #139 Answer#1 SQ#3 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#140-Answer#2-SQ#3-T#5.jpg', 'Resource #140 Answer#2 SQ#3 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#141-Answer#3-SQ#3-T#5.jpg', 'Resource #141 Answer#3 SQ#3 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#142-Answer#4-SQ#3-T#5.jpg', 'Resource #142 Answer#3 SQ#3 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(9, 19);
insert into phrase_resource(phrase_id, resource_id) values(10, 20);
insert into phrase_resource(phrase_id, resource_id) values(11, 21);
insert into phrase_resource(phrase_id, resource_id) values(12, 22);

insert into answer_sq(phrase_id, phrase_order, question_id) values(9, 0, 3);
insert into answer_sq(phrase_id, phrase_order, question_id) values(10, 1, 3);
insert into answer_sq(phrase_id, phrase_order, question_id) values(11, 2, 3);
insert into answer_sq(phrase_id, phrase_order, question_id) values(12, 3, 3);

/* Q#4 answers*/
insert into phrase(phrase, staff_id) values('kindergarten', 1);
insert into phrase(phrase, staff_id) values('school', 1);
insert into phrase(phrase, staff_id) values('university', 1);
insert into phrase(phrase, staff_id) values('company', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#143-Answer#1-SQ#4-T#5.jpg', 'Resource #143 Answer#1 SQ#4 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#144-Answer#2-SQ#4-T#5.jpg', 'Resource #144 Answer#2 SQ#4 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#145-Answer#3-SQ#4-T#5.jpg', 'Resource #145 Answer#3 SQ#4 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#146-Answer#4-SQ#4-T#5.jpg', 'Resource #146 Answer#4 SQ#4 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(13, 23);
insert into phrase_resource(phrase_id, resource_id) values(14, 24);
insert into phrase_resource(phrase_id, resource_id) values(15, 25);
insert into phrase_resource(phrase_id, resource_id) values(16, 26);

insert into answer_sq(phrase_id, phrase_order, question_id) values(13, 0, 4);
insert into answer_sq(phrase_id, phrase_order, question_id) values(14, 1, 4);
insert into answer_sq(phrase_id, phrase_order, question_id) values(15, 2, 4);
insert into answer_sq(phrase_id, phrase_order, question_id) values(16, 3, 4);


/* Q#5 answers*/
insert into phrase(phrase, staff_id) values('birth', 1);
insert into phrase(phrase, staff_id) values('grow-up', 1);
insert into phrase(phrase, staff_id) values('marriage', 1);
insert into phrase(phrase, staff_id) values('death', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#147-Answer#1-SQ#5-T#5.jpg', 'Resource #147 Answer#1 SQ#5 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#148-Answer#2-SQ#5-T#5.jpg', 'Resource #148 Answer#2 SQ#5 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#149-Answer#3-SQ#5-T#5.jpg', 'Resource #149 Answer#3 SQ#5 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#150-Answer#4-SQ#5-T#5.jpg', 'Resource #150 Answer#4 SQ#5 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(17, 27);
insert into phrase_resource(phrase_id, resource_id) values(18, 28);
insert into phrase_resource(phrase_id, resource_id) values(19, 29);
insert into phrase_resource(phrase_id, resource_id) values(20, 30);

insert into answer_sq(phrase_id, phrase_order, question_id) values(17, 0, 5);
insert into answer_sq(phrase_id, phrase_order, question_id) values(18, 1, 5);
insert into answer_sq(phrase_id, phrase_order, question_id) values(19, 2, 5);
insert into answer_sq(phrase_id, phrase_order, question_id) values(20, 3, 5);


/* Q#6 answers*/
insert into phrase(phrase, staff_id) values('one', 1);
insert into phrase(phrase, staff_id) values('two', 1);
insert into phrase(phrase, staff_id) values('three', 1);
insert into phrase(phrase, staff_id) values('four', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#151-Answer#1-SQ#6-T#5.jpg', 'Resource #151 Answer#1 SQ#6 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#152-Answer#2-SQ#6-T#5.jpg', 'Resource #152 Answer#2 SQ#6 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#153-Answer#3-SQ#6-T#5.jpg', 'Resource #153 Answer#3 SQ#6 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#154-Answer#4-SQ#6-T#5.jpg', 'Resource #154 Answer#4 SQ#6 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(21, 31);
insert into phrase_resource(phrase_id, resource_id) values(22, 32);
insert into phrase_resource(phrase_id, resource_id) values(23, 33);
insert into phrase_resource(phrase_id, resource_id) values(24, 34);

insert into answer_sq(phrase_id, phrase_order, question_id) values(21, 0, 6);
insert into answer_sq(phrase_id, phrase_order, question_id) values(22, 1, 6);
insert into answer_sq(phrase_id, phrase_order, question_id) values(23, 2, 6);
insert into answer_sq(phrase_id, phrase_order, question_id) values(24, 3, 6);

/* Q#7 answers*/
insert into phrase(phrase, staff_id) values('letter A', 1);
insert into phrase(phrase, staff_id) values('letter B', 1);
insert into phrase(phrase, staff_id) values('letter C', 1);
insert into phrase(phrase, staff_id) values('letter D', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#155-Answer#1-SQ#7-T#5.jpg', 'Resource #155 Answer#1 SQ#7 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#156-Answer#2-SQ#7-T#5.jpg', 'Resource #156 Answer#2 SQ#7 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#157-Answer#3-SQ#7-T#5.jpg', 'Resource #157 Answer#3 SQ#7 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#158-Answer#4-SQ#7-T#5.jpg', 'Resource #158 Answer#4 SQ#7 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(25, 35);
insert into phrase_resource(phrase_id, resource_id) values(26, 36);
insert into phrase_resource(phrase_id, resource_id) values(27, 37);
insert into phrase_resource(phrase_id, resource_id) values(28, 38);

insert into answer_sq(phrase_id, phrase_order, question_id) values(25, 0, 7);
insert into answer_sq(phrase_id, phrase_order, question_id) values(26, 1, 7);
insert into answer_sq(phrase_id, phrase_order, question_id) values(27, 2, 7);
insert into answer_sq(phrase_id, phrase_order, question_id) values(28, 3, 7);


/* Q#8 answers*/
insert into phrase(phrase, staff_id) values('morning', 1);
insert into phrase(phrase, staff_id) values('noon', 1);
insert into phrase(phrase, staff_id) values('afternoon', 1);
insert into phrase(phrase, staff_id) values('evening', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#159-Answer#1-SQ#8-T#5.jpg', 'Resource #159 Answer#1 SQ#8 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#160-Answer#2-SQ#8-T#5.jpg', 'Resource #160 Answer#2 SQ#8 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#161-Answer#3-SQ#8-T#5.jpg', 'Resource #161 Answer#3 SQ#8 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#162-Answer#4-SQ#8-T#5.jpg', 'Resource #162 Answer#4 SQ#8 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(29, 39);
insert into phrase_resource(phrase_id, resource_id) values(30, 40);
insert into phrase_resource(phrase_id, resource_id) values(31, 41);
insert into phrase_resource(phrase_id, resource_id) values(32, 42);

insert into answer_sq(phrase_id, phrase_order, question_id) values(29, 0, 8);
insert into answer_sq(phrase_id, phrase_order, question_id) values(30, 1, 8);
insert into answer_sq(phrase_id, phrase_order, question_id) values(31, 2, 8);
insert into answer_sq(phrase_id, phrase_order, question_id) values(32, 3, 8);


/* Q#9 answers*/
insert into phrase(phrase, staff_id) values('2000', 1);
insert into phrase(phrase, staff_id) values('2001', 1);
insert into phrase(phrase, staff_id) values('2002', 1);
insert into phrase(phrase, staff_id) values('2003', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#163-Answer#1-SQ#9-T#5.jpg', 'Resource #163 Answer#1 SQ#9 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#164-Answer#2-SQ#9-T#5.jpg', 'Resource #164 Answer#2 SQ#9 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#165-Answer#3-SQ#9-T#5.jpg', 'Resource #165 Answer#3 SQ#9 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#166-Answer#4-SQ#9-T#5.jpg', 'Resource #166 Answer#4 SQ#9 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(33, 43);
insert into phrase_resource(phrase_id, resource_id) values(34, 44);
insert into phrase_resource(phrase_id, resource_id) values(35, 45);
insert into phrase_resource(phrase_id, resource_id) values(36, 46);

insert into answer_sq(phrase_id, phrase_order, question_id) values(33, 0, 9);
insert into answer_sq(phrase_id, phrase_order, question_id) values(34, 1, 9);
insert into answer_sq(phrase_id, phrase_order, question_id) values(35, 2, 9);
insert into answer_sq(phrase_id, phrase_order, question_id) values(36, 3, 9);

/* Q#10 answers*/
insert into phrase(phrase, staff_id) values('step 1', 1);
insert into phrase(phrase, staff_id) values('step 2', 1);
insert into phrase(phrase, staff_id) values('step 3', 1);
insert into phrase(phrase, staff_id) values('step 4', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#167-Answer#1-SQ#10-T#5.jpg', 'Resource #167 Answer#1 SQ#10 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#168-Answer#2-SQ#10-T#5.jpg', 'Resource #168 Answer#2 SQ#10 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#169-Answer#3-SQ#10-T#5.jpg', 'Resource #169 Answer#3 SQ#10 T#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/phraseResource-#170-Answer#4-SQ#10-T#5.jpg', 'Resource #170 Answer#4 SQ#10 T#5', 1);

insert into phrase_resource(phrase_id, resource_id) values(37, 47);
insert into phrase_resource(phrase_id, resource_id) values(38, 48);
insert into phrase_resource(phrase_id, resource_id) values(39, 49);
insert into phrase_resource(phrase_id, resource_id) values(40, 50);

insert into answer_sq(phrase_id, phrase_order, question_id) values(37, 0, 10);
insert into answer_sq(phrase_id, phrase_order, question_id) values(38, 1, 10);
insert into answer_sq(phrase_id, phrase_order, question_id) values(39, 2, 10);
insert into answer_sq(phrase_id, phrase_order, question_id) values(40, 3, 10);
